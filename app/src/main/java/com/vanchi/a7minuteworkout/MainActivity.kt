package com.vanchi.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vanchi.a7minuteworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.frameLayout?.setOnClickListener{
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.bmiFramelayout?.setOnClickListener{
            val bmiIntent = Intent(this, BMICalculator::class.java)
            startActivity(bmiIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}