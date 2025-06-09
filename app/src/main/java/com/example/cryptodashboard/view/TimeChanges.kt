package com.example.cryptodashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun TimeRangeSelector(
    selectedRange: String,
    onRangeSelected: (String) -> Unit
) {
    val ranges = listOf("1D", "7D", "1M", "3M", "1Y")
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp
    ) {
        ranges.forEach { range ->
            val isSelected = selectedRange == range
            Text(
                text = range,
                modifier = Modifier
                    .background(
                        if (isSelected) Color(0xFF6650a4) else Color(0xFFD0BCFF),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .clickable { onRangeSelected(range) },
                color = if (isSelected) Color.White else Color.Black,
                fontSize = 12.sp
            )
        }
    }
}
