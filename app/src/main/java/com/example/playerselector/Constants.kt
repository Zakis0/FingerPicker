package com.example.playerselector

import android.graphics.Color

// Params
// Number of players limit
const val MIN_NUM_OF_PLAYER = 1
const val MAX_NUM_OF_PLAYER = 10

// Time settings
const val TIME_TO_CHOOSE_MILLISECONDS: Long = 1000
const val COUNT_DOWN_INTERVAL: Long = 1000

// Colors
val colorsList = listOf(
    Color.parseColor("#ff6961"),
    Color.parseColor("#ffb480"),
    Color.parseColor("#f8f38d"),
    Color.parseColor("#42d6a4"),
    Color.parseColor("#08cad1"),
    Color.parseColor("#59adf6"),
    Color.parseColor("#9d94ff"),
    Color.parseColor("#c780e8"),
//    Color.parseColor("#8E8888"),
//    Color.parseColor("#FF9122"),
//    Color.parseColor("#3E3838"),
)

// Default circle settings
const val DEFAULT_CIRCLE_RADIUS: Float = 100f

val DEFAULT_TOUCH_CIRCLE_COLOR = colorsList[6]
val DEFAULT_SELECTED_CIRCLE_COLOR = colorsList[3]
val DEFAULT_UNSELECTED_CIRCLE_COLOR = colorsList[0]

// Color type names
enum class ColorTypes(val string: String) {
    TOUCH("Touch color"),
    SELECTED("Selected color"),
    UNSELECTED("Unselected color")
}

// Seek bar settings
val SIZE_SEEK_BAR_COLOR = Color.parseColor("#FF9122")

const val SIZE_SEEK_BAR_MIN_SIZE = 50
const val SIZE_SEEK_BAR_MAX_SIZE = 200

// ColorCircle settings
const val RADIUS_OF_SELECT_COLOR_CIRCLE: Float = 200f

// Constants
const val MIN_NUMBER_OF_CIRCLES_TO_START_SELECTION = 2

const val START_ANGLE_OF_COLOR_CIRCLE = -90f

const val CHANGE_CIRCLE_INTENT_KEY = "CHANGE_CIRCLE_INTENT_KEY"

val colorTypesArray = arrayOf(
    ColorTypes.TOUCH.string,
    ColorTypes.SELECTED.string,
    ColorTypes.UNSELECTED.string,
)

// Save keys
const val SAVE_TABLE = "SAVE_TABLE"

const val SAVE_RADIUS = "SAVE_RADIUS"
const val SAVE_TOUCH_COLOR = "SAVE_TOUCH_COLOR"
const val SAVE_SELECTED_COLOR = "SAVE_SELECTED_COLOR"
const val SAVE_UNSELECTED_COLOR = "SAVE_UNSELECTED_COLOR"

const val DEBUG = "MyLog"
