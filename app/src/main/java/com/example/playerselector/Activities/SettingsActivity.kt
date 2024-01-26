package com.example.playerselector.Activities

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import com.example.playerselector.CHANGE_CIRCLE_INTENT_KEY
import com.example.playerselector.ColorTypes
import com.example.playerselector.SIZE_SEEK_BAR_COLOR
import com.example.playerselector.SIZE_SEEK_BAR_MAX_SIZE
import com.example.playerselector.SIZE_SEEK_BAR_MIN_SIZE
import com.example.playerselector.Classes.TouchCircle
import com.example.playerselector.Views.getColorTypeByPosition
import com.example.playerselector.colorTypesArray
import com.example.playerselector.databinding.ActivitySettingsBinding
import kotlin.math.roundToInt

lateinit var settingsBinding: ActivitySettingsBinding
var touchCircleSettings: TouchCircle? = null

var currentColorType = ColorTypes.TOUCH

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(settingsBinding.root)

        touchCircleSettings = intent.getSerializableExtra(CHANGE_CIRCLE_INTENT_KEY) as? TouchCircle

        settingsBinding.sizeSeekBar.getProgressDrawable().setColorFilter(SIZE_SEEK_BAR_COLOR, PorterDuff.Mode.SRC_IN)
        settingsBinding.sizeSeekBar.getThumb().setColorFilter(SIZE_SEEK_BAR_COLOR, PorterDuff.Mode.SRC_IN)
        settingsBinding.sizeSeekBar.progress = countSeekBarProgress()

        settingsBinding.colorTypeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            colorTypesArray
        )
        settingsBinding.colorTypeSpinner
        settingsBinding.colorTypeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentColorType = getColorTypeByPosition(position)
                settingsBinding.circleExampleView.invalidate()
                settingsBinding.selectColorView.invalidate()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        settingsBinding.sizeSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                touchCircleSettings!!.circleRadius = seekBarProgressToRadius(progress)
                settingsBinding.circleExampleView.invalidate()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
    fun countSeekBarProgress(): Int {
        return ((touchCircleSettings!!.circleRadius - SIZE_SEEK_BAR_MIN_SIZE) / (SIZE_SEEK_BAR_MAX_SIZE - SIZE_SEEK_BAR_MIN_SIZE) * 100).roundToInt()
    }
    fun seekBarProgressToRadius(progress: Int): Float {
        return (SIZE_SEEK_BAR_MAX_SIZE - SIZE_SEEK_BAR_MIN_SIZE) * (progress / 100f) + SIZE_SEEK_BAR_MIN_SIZE
    }
    override fun onPause() {
        val intent = Intent().apply {
            putExtra(CHANGE_CIRCLE_INTENT_KEY, touchCircleSettings)
        }
        setResult(RESULT_CANCELED, intent)
        finish()
        super.onPause()
    }
}
