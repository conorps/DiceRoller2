package com.stephens.diceroller.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephens.diceroller.data.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
): ViewModel() {
    private var state = HistoryState()
        private set(value) {
            field = value
            _stateFlow.tryEmit(value)
        }

    private val _stateFlow = MutableStateFlow(state)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.getHistory().collect { rollResult ->
                state = state.copy(rollResults = rollResult)
            }
        }
    }

    fun postAction(action: HistoryAction) {
        when(action) {
            HistoryAction.TapClearHistory -> clearHistory()
        }
    }

    private fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.clearHistory()
        }
    }
}