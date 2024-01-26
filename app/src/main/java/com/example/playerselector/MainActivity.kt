package com.example.playerselector

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.playerselector.databinding.ActivityMainBinding
import kotlin.math.min

lateinit var binding: ActivityMainBinding

var toDraw = true

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.incBtn.setOnClickListener {
            var curNumberOfPlayers = binding.numOfPlayerTV.text.toString().toInt()
            if (curNumberOfPlayers < MAX_NUM_OF_PLAYER) {
                ++curNumberOfPlayers
                binding.numOfPlayerTV.text = curNumberOfPlayers.toString()
            }
        }
        binding.decBtn.setOnClickListener {
            var curNumberOfPlayers = binding.numOfPlayerTV.text.toString().toInt()
            if (curNumberOfPlayers > MIN_NUM_OF_PLAYER) {
                --curNumberOfPlayers
                binding.numOfPlayerTV.text = curNumberOfPlayers.toString()
            }
        }
    }
}

class Finger(var index: Int, var id: Int, var x: Float, var y: Float)

class CustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var areFingersChosen = false

    private var timer: CountDownTimer? = null

    private var listOfFingers = mutableListOf<Finger>()
    private var chosenFingers = mutableListOf<Finger>()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val index = event.actionIndex
        val id = event.getPointerId(index)
        val cX = event.getX(index)
        val cY = event.getY(index)
        when {
            event.action == MotionEvent.ACTION_DOWN || (event.action % 256 == MotionEvent.ACTION_POINTER_DOWN) -> {
                listOfFingers.add(Finger(index, id, cX, cY))
                changedNumberOfFingers()
            }
            event.action == MotionEvent.ACTION_UP || (event.action % 256 == MotionEvent.ACTION_POINTER_UP) -> {
                listOfFingers.removeIf {it.id == id}
                changedNumberOfFingers()
            }
        }
        return true
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!toDraw) return

        val paint = Paint()
        listOfFingers.forEach {
            if (chosenFingers.contains(it)) {
                paint.color = SELECTED_CIRCLE_COLOR
            }
            else {
                if (areFingersChosen) {
                    paint.color = UNSELECTED_CIRCLE_COLOR
                }
                else {
                    paint.color = TOUCH_CIRCLE_COLOR
                }
            }
            canvas.drawCircle(it.x, it.y, CIRCLE_RADIUS, paint)
        }
        refresh()
    }

    private fun startCountDownTimer(timeMilliseconds: Long, onFinishFunction: () -> Unit ) {
        timer?.cancel()
        if (listOfFingers.size < MIN_NUMBER_OF_CIRCLES_TO_START_SELECTION) {
            timer?.cancel()
            return
        }
        timer = object: CountDownTimer(timeMilliseconds, COUNT_DOWN_INTERVAL) {
            override fun onTick(leftTime: Long) {

            }
            override fun onFinish() {
                onFinishFunction()
            }
        }.start()
    }
    private fun changedNumberOfFingers() {
        invalidate()
        startCountDownTimer(TIME_TO_CHOOSE_MILLISECONDS) { chooseFinger() }
    }
    private fun chooseFinger() {
        selectNRandomNumbersInRange(
            listOfFingers.size,
            binding.numOfPlayerTV.text.toString().toInt()
        ).forEach {
            chosenFingers.add(listOfFingers[it])
        }
        areFingersChosen = true
        invalidate()
    }
    private fun refresh() {
        areFingersChosen = false
        chosenFingers.clear()
    }
    private fun selectNRandomNumbersInRange(numOfElements: Int, numOfElementsToSelect: Int): MutableList<Int> {
        val listOfIndexes = mutableListOf<Int>()
        var randInt: Int
        for (i in 0 until min(numOfElementsToSelect, numOfElements)) {
            while (true) {
                randInt = (0 until numOfElements).random()
                if (!listOfIndexes.contains(randInt)) {
                    break
                }
            }
            listOfIndexes.add(randInt)
        }
        return listOfIndexes
    }
}
