package com.example.BlueWF.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {
    private const val PREFERENCES_NAME = "prediction_prefs"
    private const val PREDICTIONS_KEY = "predictions"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun savePrediction(context: Context, prediction: String) {
        val prefs = getPreferences(context)
        val currentPredictions = getPredictions(context).toMutableList()
        currentPredictions.add(prediction)

        prefs.edit().putStringSet(PREDICTIONS_KEY, currentPredictions.toSet()).apply()
    }

    fun getPredictions(context: Context): List<String> {
        val prefs = getPreferences(context)
        return prefs.getStringSet(PREDICTIONS_KEY, emptySet())?.toList() ?: emptyList()
    }
}
