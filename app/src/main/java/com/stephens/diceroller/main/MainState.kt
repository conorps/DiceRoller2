package com.stephens.diceroller.main

data class MainState (
    val result: Int = 0
)

sealed class MainAction {
    data object TapRoll : MainAction()
}