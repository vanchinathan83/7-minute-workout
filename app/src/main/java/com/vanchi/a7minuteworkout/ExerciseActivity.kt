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
        setProgressBar()
    }

    private fun setProgressBar(){
        binding?.restProgressBar?.progress = restTime
        restTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restTime ++
                binding?.restProgressBar?.progress = 10 - restTime
                binding?.restTimer?.text = (10 - restTime).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Rest time done!!",Toast.LENGTH_SHORT).show()
                binding?.flExercise?.visibility = View.VISIBLE
                binding?.exerciseTimer?.text = "30"
                binding?.flRest?.visibility = View.INVISIBLE
                setExerciseTimer()
            }

        }.start()
    }

    private fun setExerciseTimer(){
        if(exerciseTimer != null){
            exerciseTime = 0
            exerciseTimer?.cancel()
            exerciseTimer = null
        }
        binding?.exerciseProgressBar?.progress = exerciseTime
        exerciseTimer = object : CountDownTimer(30000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseTime++
                binding?.exerciseProgressBar?.progress = 30 - exerciseTime
                binding?.exerciseTimer?.text = (30 - exerciseTime).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Exercise time done!!",Toast.LENGTH_SHORT).show()
                binding?.flExercise?.visibility = View.INVISIBLE
                binding?.restTimer?.text = "10"
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