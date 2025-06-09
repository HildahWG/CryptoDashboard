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
import java.text.SimpleDateFormat
import java.util.*

import com.example.cryptodashboard.model.ChartPoint

@Composable
fun LineChart(
    data: List<ChartPoint>,
    modifier: Modifier = Modifier,
    selectedRange: String,
    labelColor: Color = Color.White
) {
    if (data.isEmpty()) return

    val prices = data.map { it.price }
    val minPrice = prices.minOrNull() ?: 0f
    val maxPrice = prices.maxOrNull() ?: 0f
    val midPrice = (minPrice + maxPrice) / 2

    val yLabelPadding = 40f  // Adds space for Y-axis labels
    val xLabelPadding = 30f  // Adds bottom padding for X-axis labels

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val width = size.width
            val height = size.height

            val chartWidth = width - yLabelPadding
            val chartHeight = height - xLabelPadding

            val pointGap = chartWidth / (data.size - 1)
            val priceRange = maxPrice - minPrice

            val path = Path()
            data.forEachIndexed { index, point ->
                val x = yLabelPadding + index * pointGap
                val y = ((maxPrice - point.price) / priceRange) * chartHeight
                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
            }

            drawPath(path, color = Color.Green, style = Stroke(width = 3f))

            // Y-axis labels
            val paint = android.graphics.Paint().apply {
                color = labelColor.toArgb()
                textSize = 28f
            }

            drawContext.canvas.nativeCanvas.apply {
                drawText("$${"%.2f".format(maxPrice)}", 0f, 30f, paint)
                drawText("$${"%.2f".format(midPrice)}", 0f, chartHeight / 2, paint)
                drawText("$${"%.2f".format(minPrice)}", 0f, chartHeight, paint)
            }

            // X-axis labels
            val timePaint = android.graphics.Paint().apply {
                color = labelColor.toArgb()
                textSize = 24f
                textAlign = android.graphics.Paint.Align.CENTER
            }

            val labelCount = 5
            val labelInterval = (data.size - 1) / (labelCount - 1).coerceAtLeast(1)


            for (i in 0 until data.size step labelInterval) {
                val point = data[i]
                val x = yLabelPadding + i * pointGap
                val label = formatTimestamp(point.timestamp, selectedRange)

                drawContext.canvas.nativeCanvas.drawText(label, x, height, timePaint)
            }
        }
    }
}

fun formatTimestamp(timestamp: Long, selectedRange: String): String {
    val date = Date(if (timestamp < 1000000000000L) timestamp * 1000 else timestamp)

    val pattern = when (selectedRange) {
        "1D" -> "HH:mm"
        "7D" -> "dd MMM"
        "1M", "3Mth" -> "dd MMM"
        "1Y " -> "MMM yyyy"
        else -> "dd MMM"
    }

    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(date)
}