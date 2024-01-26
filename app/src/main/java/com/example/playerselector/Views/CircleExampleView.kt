package com.example.playerselector.Views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.playerselector.Activities.touchCircleSettings

class CircleExampleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.color = getColorByActiveColorType()

        canvas.drawCircle(width / 2f, width / 2f, touchCircleSettings!!.circleRadius, paint)
    }
}
