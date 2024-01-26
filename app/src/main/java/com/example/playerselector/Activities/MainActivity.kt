package com.example.playerselector.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.playerselector.*
import com.example.playerselector.DEFAULT_CIRCLE_RADIUS
import com.example.playerselector.DEFAULT_SELECTED_CIRCLE_COLOR
import com.example.playerselector.DEFAULT_TOUCH_CIRCLE_COLOR
import com.example.playerselector.DEFAULT_UNSELECTED_CIRCLE_COLOR
import com.example.playerselector.MAX_NUM_OF_PLAYER
import com.example.playerselector.MIN_NUM_OF_PLAYER
import com.example.playerselector.SAVE_RADIUS
import com.example.playerselector.SAVE_SELECTED_COLOR
import com.example.playerselector.SAVE_TABLE
import com.example.playerselector.SAVE_TOUCH_COLOR
import com.example.playerselector.SAVE_UNSELECTED_COLOR
import com.example.playerselector.Classes.TouchCircle
import com.example.playerselector.Views.chosenFingers
import com.example.playerselector.Views.listOfFingers
import com.example.playerselector.databinding.ActivityMainBinding

lateinit var mainBinding: ActivityMainBinding

lateinit var touchCircle: TouchCircle

class MainActivity : AppCompatActivity() {
    private var settingsLauncher: ActivityResultLauncher<Intent>? = null
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        sharedPreferences = getSharedPreferences(SAVE_TABLE, Context.MODE_PRIVATE)

        touchCircle = TouchCircle()
        loadData()

        mainBinding.incBtn.setOnClickListener {
            var curNumberOfPlayers = mainBinding.numOfPlayerTV.text.toString().toInt()
            if (curNumberOfPlayers < MAX_NUM_OF_PLAYER) {
                ++curNumberOfPlayers
                mainBinding.numOfPlayerTV.text = curNumberOfPlayers.toString()
            }
        }
        mainBinding.decBtn.setOnClickListener {
            var curNumberOfPlayers = mainBinding.numOfPlayerTV.text.toString().toInt()
            if (curNumberOfPlayers > MIN_NUM_OF_PLAYER) {
                --curNumberOfPlayers
                mainBinding.numOfPlayerTV.text = curNumberOfPlayers.toString()
            }
        }
        settingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_CANCELED) {
                touchCircle.copy(touchCircleSettings!!)
            }
        }
        mainBinding.settingsBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            intent.putExtra(CHANGE_CIRCLE_INTENT_KEY, touchCircle)
            settingsLauncher!!.launch(intent)
        }
    }
    fun saveData() {
        val editor = sharedPreferences?.edit()
        editor?.putFloat(SAVE_RADIUS, touchCircle.circleRadius)
        editor?.putInt(SAVE_TOUCH_COLOR, touchCircle.touchCircleColor)
        editor?.putInt(SAVE_SELECTED_COLOR, touchCircle.selectedCircleColor)
        editor?.putInt(SAVE_UNSELECTED_COLOR, touchCircle.unselectedCircleColor)
        editor?.apply()
    }
    fun loadData() {
        touchCircle.print()
        touchCircle.circleRadius = sharedPreferences?.getFloat(SAVE_RADIUS, DEFAULT_CIRCLE_RADIUS)!!
        touchCircle.touchCircleColor = sharedPreferences?.getInt(SAVE_TOUCH_COLOR, DEFAULT_TOUCH_CIRCLE_COLOR)!!
        touchCircle.selectedCircleColor = sharedPreferences?.getInt(SAVE_SELECTED_COLOR, DEFAULT_SELECTED_CIRCLE_COLOR)!!
        touchCircle.unselectedCircleColor = sharedPreferences?.getInt(SAVE_UNSELECTED_COLOR, DEFAULT_UNSELECTED_CIRCLE_COLOR)!!
    }
    fun clearData() {
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }
    fun clearParam(paramKey: String) {
        val editor = sharedPreferences?.edit()
        editor?.remove(paramKey)
        editor?.apply()
    }
    override fun onPause() {
        super.onPause()
        listOfFingers.clear()
        chosenFingers.clear()
        saveData()
    }
}
