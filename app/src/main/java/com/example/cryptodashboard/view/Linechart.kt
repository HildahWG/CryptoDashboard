package com.example.cryptodashboard.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb

@Composable
fun LineChart(
    data: List<Float>,
    modifier: Modifier = Modifier,
    labelColor: Color = Color.White
) {
    if (data.isEmpty()) return

    val minPrice = data.minOrNull() ?: 0f
    val maxPrice = data.maxOrNull() ?: 0f
    val midPrice = (minPrice + maxPrice) / 2

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val width = size.width
            val height = size.height

            val pointGap = width / (data.size - 1)
            val priceRange = maxPrice - minPrice

            val path = Path()
            data.forEachIndexed { index, price ->
                val x = index * pointGap
                val y = height - ((price - minPrice) / priceRange) * height
                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
            }

            drawPath(path, color = Color.Green, style = Stroke(width = 3f))

            // Y-axis labels
            drawContext.canvas.nativeCanvas.apply {
                drawText("$${"%.2f".format(maxPrice)}", 0f, 30f, android.graphics.Paint().apply {
                    color = labelColor.toArgb()
                    textSize = 28f
                })
                drawText("$${"%.2f".format(midPrice)}", 0f, height / 2, android.graphics.Paint().apply {
                    color = labelColor.toArgb()
                    textSize = 28f
                })
                drawText("$${"%.2f".format(minPrice)}", 0f, height - 10f, android.graphics.Paint().apply {
                    color = labelColor.toArgb()
                    textSize = 28f
                })
            }

            // X-axis labels
            val textPaint = android.graphics.Paint().apply {
                color = labelColor.toArgb()
                textSize = 26f
                textAlign = android.graphics.Paint.Align.CENTER
            }

            val labels = listOf("Start", "Mid", "End")
            drawContext.canvas.nativeCanvas.drawText(labels[0], 0f, height - 5f, textPaint)
            drawContext.canvas.nativeCanvas.drawText(labels[1], width / 2, height - 5f, textPaint)
            drawContext.canvas.nativeCanvas.drawText(labels[2], width, height - 5f, textPaint)
        }
    }
}
