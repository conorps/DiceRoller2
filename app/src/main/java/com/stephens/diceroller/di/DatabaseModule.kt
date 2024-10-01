package com.stephens.diceroller.di

import android.content.Context
import com.stephens.diceroller.data.HistoryDao
import com.stephens.diceroller.data.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesHistoryDatabase(@ApplicationContext context: Context): HistoryDatabase {
        return HistoryDatabase.getInstance(context)
    }

    @Provides
    fun providesHistoryDao(historyDatabase: HistoryDatabase): HistoryDao {
        return historyDatabase.historyDao()
    }
}