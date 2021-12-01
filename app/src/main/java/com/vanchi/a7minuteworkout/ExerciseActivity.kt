package com.vanchi.a7minuteworkout

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vanchi.a7minuteworkout.databinding.ActivityExcerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding : ActivityExcerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var restTime: Int = 0
    private var exerciseTime: Int = 0
    private var exercises: ArrayList<Exercise>? = null
    private var currentExercisePosition: Int = -1
    private var textToSpeech : TextToSpeech? = null
    private var mediaPlayer: MediaPlayer? = null
    private var recyclerViewAdapter:ExerciseStatusAdapter? = null
    object ExerciseConstants {
        const val REST_TIME_IN_MS = 1000L
        const val EXERCISE_TIME_IN_MS = 3000L
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
        textToSpeech = TextToSpeech(this, this)
        exercises = Constants.getExercises()
        // Setup
        binding?.tvTitle?.text = "Get ready for some FUN!!"
        binding?.flExercise?.visibility = View.INVISIBLE
        binding?.imageView?.visibility = View.INVISIBLE
        binding?.tvExercise?.visibility = View.INVISIBLE
        val soundURI = Uri.parse("android.resource://com.vanchi.a7minuteworkout/" + R.raw.press_start)
        mediaPlayer = MediaPlayer.create(applicationContext, soundURI)
        mediaPlayer?.isLooping = false
        setupRestTimer()
        setupExerciseItemRecyclerView()
    }
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = textToSpeech?.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS", "The language is not supported")
            }
        }else{
            Log.e("TTS", "Initialiazation Failed")
        }
    }
    private fun speak(text: String){
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
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
            binding?.tvUpcomingExerciseLabel?.visibility = View.VISIBLE
            binding?.tvUpcomingExerciseText?.visibility = View.VISIBLE
            binding?.tvUpcomingExerciseText?.text = exercises!![currentExercisePosition].getName()
            speak("Time to rest up")
            mediaPlayer?.start()
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
                binding?.tvUpcomingExerciseLabel?.visibility = View.INVISIBLE
                binding?.tvUpcomingExerciseText?.visibility = View.INVISIBLE
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
        speak(exercises!![currentExercisePosition].getName())
        startExerciseTimer()
    }

    private fun setupExerciseItemRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(
            this,LinearLayoutManager.HORIZONTAL, false)
        recyclerViewAdapter = ExerciseStatusAdapter(exercises!!)
        binding?.rvExerciseStatus?.adapter = recyclerViewAdapter

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

        if(textToSpeech != null){
            textToSpeech?.stop()
            textToSpeech?.shutdown()
            textToSpeech = null
        }

        if(mediaPlayer != null){
            mediaPlayer?.stop()
            mediaPlayer = null
        }

        binding = null
    }

}