package com.example.BlueWF.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.BlueWF.R
import com.example.BlueWF.network.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val flaskApi = RetrofitClient.flaskApi // Retrofit API nesnesi
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // NavController ve BottomNavigationView'i bağla
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        // Varsayılan olarak menüyü gizle
        bottomNavigationView.visibility = BottomNavigationView.GONE

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            var isBottomNavVisible = false

            // Belirli fragment'lar için BottomNavigationView görünür olacak
            if (destination.id == R.id.homeFragment ||
                destination.id == R.id.profileFragment
          )
            {
              isBottomNavVisible = true
//                showLottieAnimation() // Animasyon fonksiyonu çağrılıyor
            }

            // BottomNavigationView görünürlüğünü ayarla
            bottomNavigationView.visibility = if (isBottomNavVisible) View.VISIBLE else View.GONE

            // Animasyon gizleme fonksiyonu
//            if (!isBottomNavVisible) {
//                hideLottieAnimation()
//            }
        }

    }

    fun showBottomNavigation() {
        // Menüyü göster
        bottomNavigationView.visibility = BottomNavigationView.VISIBLE
    }
}

        /*  // Arayüzdeki bileşenleri tanımlayın
          val etCropCode = findViewById<EditText>(R.id.et_crop_code)
          val etCountryCode = findViewById<EditText>(R.id.et_country_code)
          val etYear = findViewById<EditText>(R.id.et_year)
          val etCropYield = findViewById<EditText>(R.id.et_crop_yield)
          val etHarvarea = findViewById<EditText>(R.id.et_harvarea)
          val etIrrigatedFraction = findViewById<EditText>(R.id.et_irrigated_fraction)
          val etProduction = findViewById<EditText>(R.id.et_production)
          val btnPredict = findViewById<Button>(R.id.btn_predict)

          // Predict butonuna tıklama olayı
          btnPredict.setOnClickListener {
              // Kullanıcıdan alınan verileri topla
              val cropCode = etCropCode.text.toString().toIntOrNull()
              val countryCode = etCountryCode.text.toString().toIntOrNull()
              val year = etYear.text.toString().toIntOrNull()
              val cropYield = etCropYield.text.toString().toDoubleOrNull()
              val harvarea = etHarvarea.text.toString().toDoubleOrNull()
              val irrigatedFraction = etIrrigatedFraction.text.toString().toDoubleOrNull()
              val production = etProduction.text.toString().toDoubleOrNull()

              // Kullanıcıdan alınan değerleri doğrula
              if (cropCode == null || countryCode == null || year == null || cropYield == null ||
                  harvarea == null || irrigatedFraction == null || production == null
              ) {
                  Toast.makeText(this, "Lütfen tüm alanları doğru doldurun", Toast.LENGTH_SHORT)
                      .show()
                  return@setOnClickListener
              }

              // Verileri API'ye göndermek için JSON oluştur
              val requestData = mapOf(
                  "crop_code" to cropCode,
                  "country_code" to countryCode,
                  "year" to year,
                  "crop_yield_t_ha" to cropYield,
                  "harvarea_ha" to harvarea,
                  "irrigated_harvarea_fraction" to irrigatedFraction,
                  "production_t" to production
              )

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
                      production_t = production
                  )

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

