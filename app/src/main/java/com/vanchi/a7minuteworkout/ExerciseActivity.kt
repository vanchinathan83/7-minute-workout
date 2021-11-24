package com.vanchi.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.vanchi.a7minuteworkout.databinding.ActivityExcerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() {
    private var binding : ActivityExcerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var restTime: Int = 0
    private var exerciseTime: Int = 0
    private var exercises: ArrayList<Exercise>? = null
    private var currentExercisePosition: Int = -1
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
        exercises = Constants.getExercises()
        // Setup
        binding?.tvTitle?.text = "Get ready for some FUN!!"
        binding?.flExercise?.visibility = View.INVISIBLE
        binding?.imageView?.visibility = View.INVISIBLE
        binding?.tvExercise?.visibility = View.INVISIBLE
        setupRestTimer()
    }

    private fun setupRestTimer(){
        if(restTimer != null){
            restTime = 0
            restTimer?.cancel()
            restTimer = null
        }
        if(currentExercisePosition < exercises!!.size - 1){
            currentExercisePosition++
            binding?.flRest?.visibility = View.VISIBLE
            binding?.tvTitle?.visibility = View.VISIBLE
            binding?.restTimer?.text = ExerciseConstants.REST_TIME_TEXT
            startRestTimer()
        }else{
            Toast.makeText(this, "Congratulations! You are done with 7 minutes of workout!!", Toast.LENGTH_SHORT).show()
        }
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
                binding?.flRest?.visibility = View.INVISIBLE
                binding?.tvTitle?.visibility = View.INVISIBLE
                setUpExerciseView()
            }

        }.start()
    }

    private fun setUpExerciseView() {
        binding?.imageView?.visibility = View.VISIBLE
        binding?.flExercise?.visibility = View.VISIBLE

        binding?.tvExercise?.text = exercises!![currentExercisePosition].getName()
        binding?.tvExercise?.visibility = View.VISIBLE

        binding?.exerciseTimer?.text = ExerciseConstants.EXERCISE_TIME_TEXT
        if(exerciseTimer != null){
            exerciseTime = 0
            exerciseTimer?.cancel()
            exerciseTimer = null
        }
        binding?.imageView?.setImageResource(exercises!![currentExercisePosition].getImage())
        startExerciseTimer()
    }

    private fun startExerciseTimer(){
        if(exerciseTimer != null){
            exerciseTime = 0
            exerciseTimer?.cancel()
            exerciseTimer = null
        }
        binding?.exerciseProgressBar?.progress = exerciseTime
        exerciseTimer = object : CountDownTimer(ExerciseConstants.EXERCISE_TIME_IN_MS, ExerciseConstants.TICK_IN_MS){
            override fun onTick(millisUntilFinished: Long) {
                exerciseTime++
                binding?.exerciseProgressBar?.progress = ExerciseConstants.EXERCISE_TIME_IN_MS.toInt()/1000 - exerciseTime
                binding?.exerciseTimer?.text = (ExerciseConstants.EXERCISE_TIME_IN_MS.toInt()/1000 - exerciseTime).toString()
            }

            override fun onFinish() {
                binding?.flExercise?.visibility = View.INVISIBLE
                binding?.imageView?.visibility = View.INVISIBLE
                binding?.tvExercise?.visibility = View.INVISIBLE
                binding?.tvTitle?.text = "Time to Rest up"
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