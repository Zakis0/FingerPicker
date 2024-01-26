package com.example.playerselector.Views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.playerselector.ColorTypes
import com.example.playerselector.RADIUS_OF_SELECT_COLOR_CIRCLE
import com.example.playerselector.START_ANGLE_OF_COLOR_CIRCLE
import com.example.playerselector.colorsList
import com.example.playerselector.Activities.currentColorType
import com.example.playerselector.Activities.settingsBinding
import com.example.playerselector.Activities.touchCircleSettings
import kotlin.math.atan2
import kotlin.math.pow

class SelectColorView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var centerX = 0.0f
    var centerY = 0.0f
    val radius = RADIUS_OF_SELECT_COLOR_CIRCLE

    val paint = Paint()

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        centerX = width / 2f
        centerY = width / 2f
        drawColorCircle(canvas)
        paint.color = getColorByActiveColorType()
        canvas.drawCircle(centerX, centerY, radius / 2f, paint)
    }
    fun drawColorCircle(canvas: Canvas) {
        colorsList.forEachIndexed { index, it ->
            paint.color = colorsList[index]
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                START_ANGLE_OF_COLOR_CIRCLE + index * (360f / colorsList.size),
                (360f / colorsList.size),
                true,
                paint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val centerX = width / 2f
        val centerY = width / 2f
        val x = event.x
        val y = event.y

        if ((x - centerX).pow(2) + (y - centerY).pow(2) > RADIUS_OF_SELECT_COLOR_CIRCLE.pow(2)) {
            return true
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val angle = Math.toDegrees(
                    atan2(
                        y - centerY,
                        x - centerX,
                    ).toDouble() + 360
                ) % 360
                val colorIndex = (angle / (360f / colorsList.size)).toInt()
                changeColorByActiveColorType(colorsList[colorIndex])
                invalidate()
                settingsBinding.circleExampleView.invalidate()
            }
        }
        return true
    }
}

fun changeColorByActiveColorType(color: Int) {
    when (currentColorType) {
        ColorTypes.TOUCH -> touchCircleSettings!!.touchCircleColor = color
        ColorTypes.SELECTED -> touchCircleSettings!!.selectedCircleColor = color
        ColorTypes.UNSELECTED -> touchCircleSettings!!.unselectedCircleColor = color
    }
}

fun getColorByActiveColorType(): Int {
    return when (currentColorType) {
        ColorTypes.TOUCH -> touchCircleSettings!!.touchCircleColor
        ColorTypes.SELECTED -> touchCircleSettings!!.selectedCircleColor
        ColorTypes.UNSELECTED -> touchCircleSettings!!.unselectedCircleColor
    }
}

fun getColorTypeByPosition(position: Int): ColorTypes {
    return when (position) {
        0 -> ColorTypes.TOUCH
        1 -> ColorTypes.SELECTED
        2 -> ColorTypes.UNSELECTED
        else -> ColorTypes.TOUCH
    }
}
