package com.example.BlueWF.api

import com.example.BlueWF.data.model.Country
import com.example.BlueWF.data.model.PredictionRequest
import com.example.BlueWF.data.model.PredictionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


//API arayüzü
interface ApiService {
    @POST("predict")
    fun predict(@Body request: PredictionRequest): Call<PredictionResponse>

    // API'den tüm ülkeleri çeken endpoint
    @GET("all")
    fun getCountry(): Call<List<Country>>
}