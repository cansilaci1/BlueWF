package com.example.BlueWF.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BlueWF.data.model.Country
import com.example.BlueWF.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _countryList = MutableLiveData<List<String>>() // Holds country names with codes
    val countryList: LiveData<List<String>> = _countryList

    private val _errorMessage = MutableLiveData<String>() // Holds error messages
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchCountries() {
        viewModelScope.launch {
            RetrofitClient.countryApi.getCountry().enqueue(object : Callback<List<Country>> {
                override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                    if (response.isSuccessful) {
                        val countries = response.body()
                        if (countries.isNullOrEmpty()) {
                            _errorMessage.postValue("No countries found.")
                            return
                        }

                        val countryNamesWithCodes = countries.mapNotNull { country ->
                            val name = country.name?.common
                            val code = country.ccn3
                            if (name != null && code != null) "$name ($code)" else null
                        }.sortedBy { it }

                        _countryList.postValue(countryNamesWithCodes)
                    } else {
                        Log.e("API_ERROR", "Code: ${response.code()}, Message: ${response.message()}")
                        _errorMessage.postValue("Failed to load countries: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                    Log.e("NETWORK_FAILURE", "Exception: ${t.message}")
                    _errorMessage.postValue("Network Failure: ${t.message}")
                }
            })
        }
    }
}