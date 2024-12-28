package com.example.BlueWF.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.BlueWF.R
import com.example.BlueWF.data.model.PredictionRequest
import com.example.BlueWF.data.model.PredictionResponse
import com.example.BlueWF.databinding.FragmentProfileBinding
import com.example.BlueWF.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val flaskApi = RetrofitClient.flaskApi // Retrofit API nesnesi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

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
                Toast.makeText(context, "Please fill in all the blanks", Toast.LENGTH_SHORT).show()
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
                override fun onResponse(
                    call: Call<PredictionResponse>,
                    response: Response<PredictionResponse>
                ) {
                    if (response.isSuccessful) {
                        val prediction = response.body()?.prediction
                        Toast.makeText(
                            context,
                            "Prediction: $prediction",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Error: ${response.errorBody()?.string()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                    Toast.makeText(context, "Request failed: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        /*
     // Predict butonuna tıklama olayı
         // API çağrısı
         btnPredict.setOnClickListener {
             val cropCode = etCropCode.text.toString().toIntOrNull()
             val countryCode = etCountryCode.text.toString().toIntOrNull()
             val year = etYear.text.toString().toIntOrNull()
             val cropYield = etCropYield.text.toString().toDoubleOrNull()
             val harvarea = etHarvarea.text.toString().toDoubleOrNull()
             val irrigatedFraction = etIrrigatedFraction.text.toString().toDoubleOrNull()
             val production = etProduction.text.toString().toDoubleOrNull()

             if (cropCode == null || countryCode == null || year == null || cropYield == null ||
                 harvarea == null || irrigatedFraction == null || production == null
             ) {
                 Toast.makeText(this, "Lütfen tüm alanları doğru doldurun", Toast.LENGTH_SHORT)
                     .show()
                 return@setOnClickListener
             }
             val requestData = PredictionRequest(
                 crop_code = cropCode,
                 country_code = countryCode,
                 year = year,
                 crop_yield_t_ha = cropYield,
                 harvarea_ha = harvarea,
                 irrigated_harvarea_fraction = irrigatedFraction,
                 production_t = production)
             flaskApi.predict(requestData).enqueue(object : Callback<PredictionResponse> {
                 override fun onResponse(
                     call: Call<PredictionResponse>,
                     response: Response<PredictionResponse>
                 ) {
                     if (response.isSuccessful) {
                         val prediction = response.body()?.prediction
                         Toast.makeText(
                             this@MainActivity,
                             "Tahmin: $prediction",
                             Toast.LENGTH_LONG
                         ).show()
                     } else {
                         Toast.makeText(
                             this@MainActivity,
                             "Hata: ${response.errorBody()}",
                             Toast.LENGTH_LONG
                         ).show()
                     }
                 }

                 override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                     Toast.makeText(
                         this@MainActivity,
                         "Bağlantı hatası: ${t.message}",
                         Toast.LENGTH_LONG
                     ).show()
                     Log.e("Hata", t.message ?: "Bilinmeyen hata")
                 }
             })
         }
     }*/


            return binding.root
        }
    }


