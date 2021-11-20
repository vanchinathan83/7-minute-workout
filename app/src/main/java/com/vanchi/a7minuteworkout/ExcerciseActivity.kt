package com.vanchi.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vanchi.a7minuteworkout.databinding.ActivityExcerciseBinding

class ExcerciseActivity : AppCompatActivity() {
    private var binding : ActivityExcerciseBinding? = null
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
    }
}