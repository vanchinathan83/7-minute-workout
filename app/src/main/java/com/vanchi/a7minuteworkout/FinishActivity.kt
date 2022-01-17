package com.vanchi.a7minuteworkout

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.vanchi.a7minuteworkout.databinding.ActivityFinishBinding
import com.vanchi.a7minuteworkout.history.HistoryEntity
import kotlinx.coroutines.launch
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val historyDao = (application as SevenMinuteWorkoutApp).db.historyDao()
        binding?.finishButton?.setOnClickListener{ view ->
            val description = binding?.etDescription?.text.toString()
            lifecycleScope.launch {
                val dateFormated = SimpleDateFormat("MM/dd/yyyy - HH:MM").format(Date())
                historyDao.insert(HistoryEntity(date= dateFormated, description = description))
            }
            finish()
        }
    }
}