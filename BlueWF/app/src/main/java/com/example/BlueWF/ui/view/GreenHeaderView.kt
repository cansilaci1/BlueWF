package com.example.BlueWF.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import com.example.BlueWF.R

class GreenHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint().apply {
        color = Color.parseColor("#0A3832") // Arka plan rengi
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val greetingPaint = Paint().apply {
        color = Color.WHITE // "Merhaba" metni rengi
        textSize = 64f
        isAntiAlias = true
        typeface = ResourcesCompat.getFont(context, R.font.inter_medium)
        textAlign = Paint.Align.LEFT
    }

    private val namePaint = Paint().apply {
        color = Color.parseColor("#BBD409") // İsim için vurgulu renk
        textSize = 64f
        isAntiAlias = true
        typeface = ResourcesCompat.getFont(context, R.font.inter_medium)
        textAlign = Paint.Align.LEFT
    }

    private val countryPaint = Paint().apply {
        color = Color.WHITE // Ülke bilgisi için renk
        textSize = 32f
        isAntiAlias = true
        typeface = ResourcesCompat.getFont(context, R.font.inter_semibold)
        textAlign = Paint.Align.LEFT
    }

    private val iconBitmap: Bitmap by lazy {
        val drawable = AppCompatResources.getDrawable(context, R.drawable.ic_pine)
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, canvas.width, canvas.height)
        drawable?.draw(canvas)
        bitmap
    }

    private var name: String? = null
    private var country: String? = null

    init {
        // SharedPreferences'ten kullanıcı bilgilerini çek
        val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        name = sharedPref.getString("name", "Ad Yok")
        country = sharedPref.getString("country", "Ülke Yok")

    }
    fun updateUserInfo() {
        val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        name = sharedPref.getString("name", "Ad Yok")
        country = sharedPref.getString("country", "Ülke Yok")
        invalidate() // Yeniden çizim yap
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("GreenHeaderView", "Drawing with Name: $name, Country: $country")

        canvas?.let {
            val width = width.toFloat()
            val height = height.toFloat()

            // Arka plan tasarımı
            val path = Path().apply {
                moveTo(0f, height * 0.8f) // Başlangıç noktası
                quadTo(width / 2, height * 1.2f, width, height * 0.8f) // Kıvrımlı yol
                lineTo(width, 0f) // Sağ üst köşe
                lineTo(0f, 0f) // Sol üst köşe
                close() // Yolu kapat
            }
            it.drawPath(path, backgroundPaint)

            // Yazıların başlangıç x ve y konumları
            val textStartX = 50f
            val textStartY = height * 0.3f

            // "Merhaba" ve isim metnini yatay olarak yan yana çiz
            val greetingText = "Hi,"
            val greetingWidth = greetingPaint.measureText(greetingText)
            it.drawText(
                greetingText,
                textStartX,
                textStartY,
                greetingPaint
            )

            name?.let { userName ->
                it.drawText(
                    userName,
                    textStartX + greetingWidth + 10f, // "Merhaba"nın hemen sağına
                    textStartY,
                    namePaint
                )
            }

            // Ülke bilgisi
            country?.let { userCountry ->
                it.drawText(
                    userCountry,
                    textStartX,
                    textStartY + 60f, // Alt satıra, "Merhaba, isim"den 60dp aşağıya
                    countryPaint
                )
            }

            // İkon çizimi
            it.drawBitmap(iconBitmap, width - 150f, height * 0.3f - 50f, null)
        }


    }

}