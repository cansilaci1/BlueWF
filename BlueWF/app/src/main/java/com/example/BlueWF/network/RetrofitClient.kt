package com.example.BlueWF.network

import com.example.BlueWF.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofit yapılandırması
object RetrofitClient {
    private const val FLASK_URL = "http://10.0.2.2:5002" // Sunucunun ip adresini yazdım

    val flaskApi: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(FLASK_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private const val COUNTRY_URL = "https://restcountries.com/v3.1/" // Ensure it ends with "/"

    val countryApi: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(COUNTRY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}


