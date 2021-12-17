package com.vanchi.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.vanchi.a7minuteworkout.databinding.ActivityBmicalculatorBinding
import java.math.BigDecimal
import java.math.RoundingMode

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

        binding?.btnBMICalculate?.setOnClickListener{view ->
            if(isInputsValid()){
                val height = binding?.etHeight?.text.toString().toFloat()
                val weight = binding?.etWeight?.text.toString().toFloat()

                val bmi = (weight / height / height) * 703
                displayBMI(bmi)

            }else{
                Toast.makeText(this,"Please enter height and weight",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun displayBMI(bmi: Float) {
        var bmiResult : String
        var bmiResultDescription: String
        if(bmi.compareTo(18.0) < 0){
            bmiResult = "Underweight"
            bmiResultDescription = "You need to improve your nutritional intake to improve your weight."
        }else if(bmi.compareTo(25.0f) < 0){
            bmiResult = "Normal"
            bmiResultDescription = "Your BMI is normal. Keep up the good work!!"
        } else {
            bmiResult = "Overweight"
            bmiResultDescription = "You are overweight. You need to take care!!"
        }
        binding?.tvBMIResult?.text = bmiResult
        binding?.tvBMIResultDescription?.text = bmiResultDescription
        binding?.tvBMIValue?.text = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.llBMIResult?.visibility = View.VISIBLE
    }

    fun isInputsValid(): Boolean {
        if(binding?.etHeight?.text.toString().isNotEmpty() && binding?.etWeight?.text.toString().isNotEmpty()){
            return true
        }
        return false
    }
}