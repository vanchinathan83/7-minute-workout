package com.vanchi.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vanchi.a7minuteworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.finishButton?.setOnClickListener{ view ->
            finish()
        }
    }
}