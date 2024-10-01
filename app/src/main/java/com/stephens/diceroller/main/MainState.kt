package com.stephens.diceroller.main

data class MainState (
    val result: Int = 1,
    val loading: Boolean = false,
    val networkError: Boolean = false
)

sealed class MainAction {
    data object TapRoll : MainAction()
    data object TapClearHistory: MainAction()
    data object ClearErrors : MainAction()
}