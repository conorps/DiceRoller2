package com.stephens.diceroller.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject constructor(private val historyDao: HistoryDao) {
    fun getHistory() = historyDao.getAll()
    suspend fun insertResult(result: RollResult) = historyDao.insert(result)
    suspend fun clearHistory() = historyDao.deleteHistory()

    companion object {
        @Volatile private var instance: HistoryRepository? = null

        fun getInstance(historyDao: HistoryDao) =
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(historyDao).also { instance = it }
            }
    }
}