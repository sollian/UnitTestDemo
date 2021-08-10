package com.example.unittest.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

/**
 * @author shouxianli on 2021/8/6.
 */
class MyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()

    init {
        setBackgroundColor(Color.GRAY)
        paint.textSize = 200.0f
        paint.color = Color.BLACK
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            1000, 500
        )
    }

    private val text = "afgj"
    private val posX = 200.0f
    private val posY = 500.0f


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDraw(canvas: Canvas) {
        canvas.drawText(text, posX, posY, paint)
        canvas.drawTextRun(
            text,
            0, text.length-1,
            0, text.length-1,
            posX, posY,
            false,
            paint
        )
    }
}