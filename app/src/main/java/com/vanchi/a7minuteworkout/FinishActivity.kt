package com.vanchi.a7minuteworkout

import android.content.Intent
import android.icu.text.DateFormat
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
                historyDao.insert(HistoryEntity(date= Date().toString(), description = description))
            }
            finish()
        }
    }
}