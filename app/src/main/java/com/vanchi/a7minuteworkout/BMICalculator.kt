package com.vanchi.a7minuteworkout

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.TooltipCompat
import com.vanchi.a7minuteworkout.databinding.ActivityBmicalculatorBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMICalculator : AppCompatActivity() {
    var binding: ActivityBmicalculatorBinding? = null
    var isUSSytem: Boolean = true
    @RequiresApi(Build.VERSION_CODES.O)
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
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId.equals(R.id.rbUS)){
                binding?.tilHeightInCms?.visibility = View.INVISIBLE
                binding?.tilHeight?.visibility = View.VISIBLE
                binding?.tilWeight?.hint = "Weight in LBS"
                isUSSytem = true
            }else{
                binding?.tilHeightInCms?.visibility = View.VISIBLE
                binding?.tilHeight?.visibility = View.INVISIBLE
                binding?.tilWeight?.hint = "Weight in Kgs"
                isUSSytem = false
            }
            resetBMIResultView()
        }

        binding?.btnBMICalculate?.setOnClickListener{view ->
            if(isInputsValid()){
                if(isUSSytem) {
                    val height = binding?.etHeight?.text.toString().toFloat()
                    val weight = binding?.etWeight?.text.toString().toFloat()

                    val bmi = (weight / height / height) * 703
                    displayBMI(bmi)
                }else{
                    val height = binding?.etHeightInCms?.text.toString().toFloat()
                    val weight = binding?.etWeight?.text.toString().toFloat()
                    val bmi = (weight / height / height) * 10000
                    displayBMI(bmi)
                }

            }else{
                Toast.makeText(this,"Please enter height and weight",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resetBMIResultView(){
        binding?.tvBMIResult?.text = ""
        binding?.tvBMIResultDescription?.text = ""
        binding?.tvBMIValue?.text = ""
        binding?.tilWeight?.editText?.text?.clear()
        binding?.llBMIResult?.visibility = View.INVISIBLE
    }

    private fun displayBMI(bmi: Float) {
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

    private fun isInputsValid(): Boolean {
        if(isUSSytem && binding?.etHeight?.text.toString().isNotEmpty() && binding?.etWeight?.text.toString().isNotEmpty()){
            return true
        } else if(!isUSSytem &&  binding?.etHeightInCms?.text.toString().isNotEmpty() && binding?.etWeight?.text.toString().isNotEmpty()){
            return true
        }
        return false
    }
}