package com.stephens.diceroller.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephens.diceroller.api.RandomApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val api: RandomApi
) : ViewModel() {
    private var state = MainState()
        private set(value) {
            field = value
            _stateFlow.tryEmit(value)
        }
    private val _stateFlow = MutableStateFlow(state)
    val stateFlow = _stateFlow.asStateFlow()

    fun postAction(action: MainAction) {
        when(action) {
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

    /**
     * Where the call to get a random number actually takes place
     * @param valuesToReturn the number of separate values that will be returned
     * @param min the minimum random number
     * @param max the maximum random number
     */
    private fun rollDice(
        valuesToReturn: Int,
        min: Int,
        max: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(loading = true)
            try {
                val response = api.getRandomNumber(
                    num = valuesToReturn.toString(),
                    min = min.toString(),
                    max = max.toString()
                )
                if (response.isSuccessful) {
                    state = state.copy(
                        result = response.body() ?: 0,
                        loading = false
                    )
                } else {
                    //setting networkError to true displays a Toast message
                    state = state.copy(
                        networkError = true,
                        loading = false
                    )
                }
            } catch (e: Exception) {
                state = state.copy(networkError = true, loading = false)
            }
        }
    }
}