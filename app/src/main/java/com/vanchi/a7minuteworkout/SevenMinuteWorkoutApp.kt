package com.vanchi.a7minuteworkout

import android.app.Application
import com.vanchi.a7minuteworkout.history.HistoryDatabase

class SevenMinuteWorkoutApp:Application() {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}