package com.stephens.diceroller.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RollResult::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile private var instance: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): HistoryDatabase {
            return Room.databaseBuilder(context, HistoryDatabase::class.java, "roll-results")
                .build()
        }
    }
}