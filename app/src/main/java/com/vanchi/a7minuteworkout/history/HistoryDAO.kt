package com.vanchi.a7minuteworkout.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDAO {
    @Insert
    suspend fun insert(history: HistoryEntity)

    @Delete
    suspend fun delete(history: HistoryEntity)

    @Query("SELECT * from `history`")
    fun loadAllHistory(): Flow<List<HistoryEntity>>

    @Query("SELECT * from `history` where id=:id")
    fun loadHistoryById(id: Int) : Flow<HistoryEntity>
}