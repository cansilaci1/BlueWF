package com.example.BlueWF.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.BlueWF.data.model.PredictionRequest
import com.example.BlueWF.data.model.PredictionResponse
import com.example.BlueWF.databinding.FragmentProfileBinding
import com.example.BlueWF.network.RetrofitClient
import com.example.BlueWF.utils.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val flaskApi = RetrofitClient.flaskApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.btnAnalyse.setOnClickListener {
            val cropCode = binding.etCropCode.text.toString().toIntOrNull()
            val countryCode = binding.etCountryCode.text.toString().toIntOrNull()
            val year = binding.etYear.text.toString().toIntOrNull()
            val cropYield = binding.etCropYield.text.toString().toDoubleOrNull()
            val harvestedArea = binding.etHarvestedArea.text.toString().toDoubleOrNull()
            val fractionOfArea = binding.etFractionOfArea.text.toString().toDoubleOrNull()
            val productionAmount = binding.etProductionAmount.text.toString().toDoubleOrNull()

            if (cropCode == null || countryCode == null || year == null || cropYield == null ||
                harvestedArea == null || fractionOfArea == null || productionAmount == null
            ) {
                Toast.makeText(context, "Please fill in the blanks", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val requestData = PredictionRequest(
                crop_code = cropCode,
                country_code = countryCode,
                year = year,
                crop_yield_t_ha = cropYield,
                harvarea_ha = harvestedArea,
                irrigated_harvarea_fraction = fractionOfArea,
                production_t = productionAmount
            )

            flaskApi.predict(requestData).enqueue(object : Callback<PredictionResponse> {
                override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                    if (response.isSuccessful) {
                        val prediction = response.body()?.prediction ?: 0.0
                        val resultText = "Prediction: %.2f".format(prediction)

                        // Show the result in the current fragment
                        binding.tvResult.text = resultText

                        // Save the prediction in SharedPreferences
                        PreferencesHelper.savePrediction(requireContext(), resultText)
                    } else {
                        binding.tvResult.text = "Error: ${response.errorBody()?.string()}"
                    }
                }

                override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                    Toast.makeText(context, "Request failed: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        return binding.root
    }
}
