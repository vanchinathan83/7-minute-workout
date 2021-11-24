package com.vanchi.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.vanchi.a7minuteworkout.databinding.ActivityExcerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding : ActivityExcerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var restTime: Int = 0
    private var exerciseTime: Int = 0
    object ExerciseConstants {
        const val REST_TIME_IN_MS = 10000L
        const val EXERCISE_TIME_IN_MS = 30000L
        const val TICK_IN_MS = 1000L
        const val REST_TIME_TEXT = "10"
        const val EXERCISE_TIME_TEXT = "30"
        const val EXERCISE_TEST = "Exercise Name:"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbar?.setNavigationOnClickListener{
            onBackPressed()
        }
        binding?.flExercise?.visibility = View.INVISIBLE
        setupRestTimer()
    }

    private fun setupRestTimer(){
        if(restTimer != null){
            restTime = 0
            restTimer?.cancel()
            restTimer = null
        }
        startRestTimer()
    }

    private fun startRestTimer(){
        binding?.restProgressBar?.progress = restTime
        restTimer = object : CountDownTimer(ExerciseConstants.REST_TIME_IN_MS, ExerciseConstants.TICK_IN_MS){
            override fun onTick(millisUntilFinished: Long) {
                restTime ++
                binding?.restProgressBar?.progress = ExerciseConstants.REST_TIME_IN_MS.toInt()/1000 - restTime
                binding?.restTimer?.text = (ExerciseConstants.REST_TIME_IN_MS.toInt()/1000 - restTime).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Rest time done!!",Toast.LENGTH_SHORT).show()
                binding?.flExercise?.visibility = View.VISIBLE
                binding?.exerciseTimer?.text = ExerciseConstants.EXERCISE_TIME_TEXT
                binding?.flRest?.visibility = View.INVISIBLE
                startExerciseTimer()
            }

        }.start()
    }

    private fun startExerciseTimer(){
        if(exerciseTimer != null){
            exerciseTime = 0
            exerciseTimer?.cancel()
            exerciseTimer = null
        }
        binding?.exerciseProgressBar?.progress = exerciseTime
        binding?.tvTitle?.text = "Exercise Name"
        exerciseTimer = object : CountDownTimer(ExerciseConstants.EXERCISE_TIME_IN_MS, ExerciseConstants.TICK_IN_MS){
            override fun onTick(millisUntilFinished: Long) {
                exerciseTime++
                binding?.exerciseProgressBar?.progress = ExerciseConstants.EXERCISE_TIME_IN_MS.toInt()/1000 - exerciseTime
                binding?.exerciseTimer?.text = (ExerciseConstants.EXERCISE_TIME_IN_MS.toInt()/1000 - exerciseTime).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Exercise time done!!",Toast.LENGTH_SHORT).show()
                binding?.flExercise?.visibility = View.INVISIBLE
                binding?.restTimer?.text = ExerciseConstants.REST_TIME_TEXT
                binding?.flRest?.visibility = View.VISIBLE
                setupRestTimer()
            }

        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null){
            restTime = 0
            restTimer?.cancel()
            restTimer = null
        }
        if(exerciseTimer != null){
            exerciseTime = 0
            exerciseTimer?.cancel()
            exerciseTimer = null
        }

        binding = null
    }

}