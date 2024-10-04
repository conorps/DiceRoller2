package com.stephens.diceroller.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephens.diceroller.data.HistoryRepository
import com.stephens.diceroller.data.RandomRepository
import com.stephens.diceroller.data.RollResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RandomRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {
    private var state = MainState()
        private set(value) {
            field = value
            _stateFlow.tryEmit(value)
        }

    private val _stateFlow = MutableStateFlow(state)
    val stateFlow = _stateFlow.asStateFlow()

    fun postAction(action: MainAction) {
        when (action) {
            MainAction.TapRoll ->
                rollDice(
                    min = 1,
                    max = 6
                )

            MainAction.ClearErrors ->
                state = state.copy(
                    networkError = false
                )
        }
    }

    private fun rollDice(
        valuesToReturn: Int = 1,
        min: Int,
        max: Int
    ) {
        if (min >= max) {
            //TODO: Show error state
            return
        }
        state = state.copy(loading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.rollDice(valuesToReturn, min, max)
            if (result == 0) {
                state = state.copy(
                    loading = false,
                    networkError = true
                )
            } else {
                state = state.copy(
                    result = result,
                    loading = false
                )
                insertResultInHistory(result, max)
            }
        }
    }

    private suspend fun insertResultInHistory(result: Int, sides: Int) {
        val rollResult = RollResult(
            time = Calendar.getInstance().timeInMillis,
            result = result,
            sides = sides
        )
        historyRepository.insertResult(rollResult)
    }
}