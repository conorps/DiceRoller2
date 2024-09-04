package com.stephens.diceroller.main

import com.stephens.diceroller.api.RandomApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEqualTo
import org.junit.Test
import retrofit2.Response

class MainViewModelTest {
    private val randomApi: RandomApi = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `roll test 5`() = runTest {
        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery { randomApi.getRandomNumber("1", "1", "6") } returns Response.success(5)

        val viewModel = MainViewModel(randomApi)
        viewModel.postAction(MainAction.TapRoll)
        val rollResult = viewModel.stateFlow.first()
        rollResult.result shouldBeEqualTo  5
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `roll test other than 2`() = runTest {
        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery { randomApi.getRandomNumber("1", "1", "6") } returns Response.success(2)

        val viewModel = MainViewModel(randomApi)
        viewModel.postAction(MainAction.TapRoll)
        val rollResult = viewModel.stateFlow.first()
        rollResult.result shouldNotBeEqualTo   1
        rollResult.result shouldBeEqualTo 2
        rollResult.result shouldNotBeEqualTo   3
        rollResult.result shouldNotBeEqualTo   4
    }
}