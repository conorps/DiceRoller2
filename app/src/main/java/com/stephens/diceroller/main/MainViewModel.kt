package com.stephens.diceroller.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random
import kotlin.random.nextInt

class MainViewModel : ViewModel() {
    private var state = MainState()
        private set(value) {
            field = value
            _stateFlow.tryEmit(value)
        }
    private val _stateFlow = MutableStateFlow(state)
    val stateFlow = _stateFlow.asStateFlow()

    fun postAction(action: MainAction) {
        when(action) {
            MainAction.TapRoll -> rollDie()
        }
    }

    private fun rollDie() {
        state = state.copy(result = 0)
        val result = Random.nextInt(0..6)
        state = state.copy(result = result)
    }
}