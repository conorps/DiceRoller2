package com.stephens.diceroller.history

import com.stephens.diceroller.data.RollResult

data class HistoryState(
    val rollResults: List<RollResult> = emptyList()
)

sealed class HistoryAction {
    data object TapClearHistory: HistoryAction()
}
