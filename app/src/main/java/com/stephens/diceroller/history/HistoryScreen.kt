package com.stephens.diceroller.history

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephens.diceroller.R
import com.stephens.diceroller.data.RollResult
import com.stephens.diceroller.main.MainAction
import com.stephens.diceroller.main.MainViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(viewModel: MainViewModel) {
    val uiState = viewModel.historyStateFlow.collectAsState(emptyList())
    HistoryContent(uiState.value) { viewModel.postAction(MainAction.TapClearHistory) }
}

@Composable
fun HistoryContent(rollResults: List<RollResult>, clearHistory: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { clearHistory() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        ) { Text(stringResource(R.string.clear_history)) }
        LazyColumn(reverseLayout = true) {
            items(rollResults) { rollResult ->
                RollResultCard(rollResult)
            }
        }
    }
}

@Composable
fun RollResultCard(
    rollResult: RollResult,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(4.dp)
    ) {
        Column {
            Row(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                Text(
                    text = formatDate(rollResult.time, LocalContext.current),
                    modifier = Modifier.weight(1f),
                    color = Color.Gray
                )
                Text(
                    text = stringResource(R.string.dice_sided, rollResult.sides),
                    color = Color.Gray
                )
            }
            Text(
                text = "Result",
                modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(
                text = rollResult.result.toString(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp),
                fontSize = 24.sp,
            )
        }
    }
}

private fun formatDate(timeInMillis: Long, context: Context): String{
    val formatter = DateTimeFormatter.ofPattern(context.getString(R.string.date_format))
    val instant = Instant.ofEpochMilli(timeInMillis)
    val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return formatter.format(date)
}
