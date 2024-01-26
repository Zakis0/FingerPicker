package com.example.playerselector.Classes

import android.util.Log
import com.example.playerselector.DEBUG
import com.example.playerselector.DEFAULT_CIRCLE_RADIUS
import com.example.playerselector.DEFAULT_SELECTED_CIRCLE_COLOR
import com.example.playerselector.DEFAULT_TOUCH_CIRCLE_COLOR
import com.example.playerselector.DEFAULT_UNSELECTED_CIRCLE_COLOR
import java.io.Serializable

data class TouchCircle(
    var circleRadius: Float = DEFAULT_CIRCLE_RADIUS,
    var touchCircleColor: Int = DEFAULT_TOUCH_CIRCLE_COLOR,
    var selectedCircleColor: Int = DEFAULT_SELECTED_CIRCLE_COLOR,
    var unselectedCircleColor: Int = DEFAULT_UNSELECTED_CIRCLE_COLOR,
) : Serializable {
    fun copy(touchCircle: TouchCircle) {
        circleRadius = touchCircle.circleRadius
        touchCircleColor = touchCircle.touchCircleColor
        selectedCircleColor = touchCircle.selectedCircleColor
        unselectedCircleColor = touchCircle.unselectedCircleColor
    }
    fun print() {
        Log.d(DEBUG, "$circleRadius $touchCircleColor $selectedCircleColor $unselectedCircleColor")
    }
}