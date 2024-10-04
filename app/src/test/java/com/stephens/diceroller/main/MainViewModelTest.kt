package com.stephens.diceroller.main

import com.stephens.diceroller.api.RandomApi
import com.stephens.diceroller.data.HistoryDao
import com.stephens.diceroller.data.HistoryRepository
import com.stephens.diceroller.data.RandomRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEqualTo
import org.junit.Test
import retrofit2.Response

class MainViewModelTest {
    private val randomApi: RandomApi = mockk()
    private val historyDao: HistoryDao = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `roll test 5`(): Unit = runBlocking {
        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery {
            randomApi.getRandomNumber("1", "1", "6")
        } returns Response.success(5)

        val randomRepo = RandomRepository(randomApi)
        val viewModel = MainViewModel(randomRepo, HistoryRepository(historyDao))
        viewModel.postAction(MainAction.TapRoll)
        var rollResult = 0
        viewModel.stateFlow.take(1).collect {
            rollResult = it.result
        }
        rollResult shouldBeEqualTo  5
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `roll test other than 2`(): Unit = runBlocking {
        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery {
            randomApi.getRandomNumber("1", "1", "6")
        } returns Response.success(2)

        val randomRepo = RandomRepository(randomApi)
        val viewModel = MainViewModel(randomRepo, HistoryRepository(historyDao))
        viewModel.postAction(MainAction.TapRoll)
        var rollResult = 0
        viewModel.stateFlow.take(1).collect {
            rollResult = it.result
        }
        rollResult shouldNotBeEqualTo   1
        rollResult shouldBeEqualTo 2
        rollResult shouldNotBeEqualTo   3
        rollResult shouldNotBeEqualTo   4
    }
}