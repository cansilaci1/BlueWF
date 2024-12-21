package com.example.BlueWF.data.model

data class PredictionRequest(
    val crop_code: Int,
    val country_code: Int,
    val year: Int,
    val crop_yield_t_ha: Double,
    val harvarea_ha: Double,
    val irrigated_harvarea_fraction: Double,
    val production_t: Double
)

