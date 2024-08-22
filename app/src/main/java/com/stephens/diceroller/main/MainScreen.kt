package com.stephens.diceroller.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.stephens.diceroller.R

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val uiState = viewModel.stateFlow.collectAsState()
        DieImage(side = uiState.value.result)
        RollButton(tapRoll = { viewModel.postAction(MainAction.TapRoll) })
    }

}

@Composable
fun DieImage(side: Int) {
    @DrawableRes var imageId: Int
    var description: String
    when (side) {
        1 -> {
            imageId = R.drawable.dice_1
            description = "Dice 1"
        }
        2 -> {
            imageId = R.drawable.dice_2
            description = "Dice 2"
        }
        3 -> {
            imageId = R.drawable.dice_3
            description = "Dice 3"
        }
        4 -> {
            imageId = R.drawable.dice_4
            description = "Dice 4"
        }
        5 -> {
            imageId = R.drawable.dice_5
            description = "Dice 5"
        }
        6 -> {
            imageId = R.drawable.dice_6
            description = "Dice 6"
        }
        else -> {
            imageId = R.drawable.dice_1
            description = "Unknown"
        }
    }
    Image(
        painter = painterResource(id = imageId),
        contentDescription = description
    )
}

@Composable
fun RollButton(tapRoll: () -> Unit) {
    Button(onClick = { tapRoll() }) {
        Text(text = "Roll")
    }
}