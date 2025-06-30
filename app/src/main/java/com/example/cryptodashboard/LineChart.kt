package com.example.cryptodashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LineChart(
    data: List<Any>,
    modifier: Modifier = Modifier,
    selectedRange: String = "7D",
    labelColor: Color = Color.Gray
) {
    // Placeholder composable to prevent crash
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.LightGray)
    )
}
