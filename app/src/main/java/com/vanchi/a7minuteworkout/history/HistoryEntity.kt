package com.vanchi.a7minuteworkout.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val date:String = "",
    val description:String = ""
)