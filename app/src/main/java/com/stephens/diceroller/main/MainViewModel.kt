package com.stephens.diceroller.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephens.diceroller.api.RandomApi
import com.stephens.diceroller.data.RandomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RandomRepository
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
                    valuesToReturn = 1,
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
        valuesToReturn: Int,
        min: Int,
        max: Int
    ) {
        state = state.copy(loading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.rollDice(valuesToReturn, min, max)
            state = if (result == 0) {
                state.copy(
                    loading = false,
                    networkError = true
                )
            } else state.copy(
                result = result,
                loading = false
            )
        }
    }
}