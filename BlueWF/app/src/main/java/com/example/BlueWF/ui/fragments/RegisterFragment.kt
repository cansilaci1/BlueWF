package com.example.BlueWF.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.BlueWF.data.model.Country
import com.example.BlueWF.databinding.FragmentRegisterBinding
import com.example.BlueWF.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var countryList: List<String>  // List to hold country names
    private lateinit var spinnerAdapter: ArrayAdapter<String>  // Adapter for the spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        // Initialize the country list and the Spinner adapter
        countryList = mutableListOf()

        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countryList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.countrySpinner.adapter = spinnerAdapter

        // Fetch countries from the API using Retrofit
        RetrofitClient.countryApi.getCountry().enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful) {
                    val countries = response.body()
                    if (countries.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), "No countries found.", Toast.LENGTH_SHORT).show()
                        return
                    }

                    // Extract and format country names with codes
                    val countryNamesWithCodes = countries.mapNotNull { country ->
                        val name = country.name?.common
                        val code = country.ccn3
                        if (name != null && code != null) "$name ($code)" else null
                    }.sortedBy { it } // Sort alphabetically

                    // Create and set adapter
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countryNamesWithCodes)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.countrySpinner.adapter = adapter
                } else {
                    Log.e("RegisterFragment", "Response Code: ${response.code()}, Message: ${response.message()}")
                    response.errorBody()?.let {
                        Log.e("RegisterFragment", "Error Body: ${it.string()}")
                    }
                    Toast.makeText(requireContext(), "Failed to load countries: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Log.e("RegisterFragment", "Network Failure: ${t.message}")
                Toast.makeText(requireContext(), "Failed to fetch country data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}
