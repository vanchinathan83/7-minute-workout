package com.vanchi.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vanchi.a7minuteworkout.databinding.ActivityBmicalculatorBinding

class BMICalculator : AppCompatActivity() {
    var binding: ActivityBmicalculatorBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmicalculatorBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.bmiToolbar)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.bmiToolbar?.setNavigationOnClickListener{
            onBackPressed()
        }
    }
}