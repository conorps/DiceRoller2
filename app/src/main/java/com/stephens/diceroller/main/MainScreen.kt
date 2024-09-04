package com.stephens.diceroller.main

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stephens.diceroller.R
import com.stephens.diceroller.ui.theme.DiceRollerTheme

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    context: Context
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val uiState = viewModel.stateFlow.collectAsState()
        MainContent(
            state = uiState.value,
            tapRoll = { viewModel.postAction(MainAction.TapRoll)}
        )
        when {
            uiState.value.networkError -> {
                Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show()
                viewModel.postAction(MainAction.ClearErrors)
            }
        }
    }
}

@Composable
fun MainContent(
    state: MainState,
    tapRoll: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DiceImage(side = state.result)
        RollButton(
            tapRoll = { tapRoll() },
            loading = state.loading
        )
    }
}

@Composable
fun DiceImage(side: Int) {
    @DrawableRes val imageId: Int
    val description: String
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
fun RollButton(
    tapRoll: () -> Unit,
    loading: Boolean
) {
    Button(
        modifier = Modifier
            .height(ButtonDefaults.MinHeight)
            .width(100.dp),
        onClick = { if (!loading) tapRoll() }
    ) {
        if(loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeWidth = 2.dp
            )
        } else {
            Text(text = stringResource(R.string.roll))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    DiceRollerTheme {
        MainContent(state = MainState(result = 5)) {}
    }
}