package com.example.BlueWF.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class GreenHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint().apply {
        color = Color.parseColor("#0A3832")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.let {
            val width = width.toFloat()
            val height = height.toFloat()

            // kıvrım kodları
            val path = Path().apply {
                moveTo(0f, height * 0.8f) // Start point of the curve
                quadTo(width / 2, height * 1.2f, width, height * 0.8f) // Curved path
                lineTo(width, 0f) // Top-right corner
                lineTo(0f, 0f) // Top-left corner
                close() // Close the path
            }
            it.drawPath(path, backgroundPaint)
        }
    }
}
