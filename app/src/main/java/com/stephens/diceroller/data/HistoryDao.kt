package com.stephens.diceroller.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM rollResults")
    fun getAll(): Flow<List<RollResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rollResult: RollResult)

    @Query("DELETE FROM rollresults")
    suspend fun deleteHistory()
}