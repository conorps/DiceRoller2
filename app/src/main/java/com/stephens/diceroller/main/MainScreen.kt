package com.stephens.diceroller.main

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stephens.diceroller.R
import com.stephens.diceroller.ui.theme.DiceRollerTheme

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel<MainViewModel>()) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val uiState = viewModel.stateFlow.collectAsStateWithLifecycle()
        MainContent(
            state = uiState.value,
            tapRoll = { viewModel.postAction(MainAction.TapRoll)}
        )
        when {
            uiState.value.networkError -> {
                Toast.makeText(LocalContext.current, "Network Error", Toast.LENGTH_LONG).show()
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
        DiceImage(displayedSide = state.result)
        RollButton(
            tapRoll = { tapRoll() },
            loading = state.loading
        )
    }
}

/**
 * @param displayedSide the side of the die that is currently facing up
 */
@Composable
fun DiceImage(displayedSide: Int) {
    @DrawableRes val imageId: Int
    val description: String
    when (displayedSide) {
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