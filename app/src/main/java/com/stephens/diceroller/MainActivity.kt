package com.stephens.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.stephens.diceroller.main.MainScreen
import com.stephens.diceroller.main.MainViewModel
import com.stephens.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    DiceRollerTheme {
    }
}