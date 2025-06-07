package com.example.cryptodashboard.view

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

@Composable
fun LineChart(
    data: List<Float>,
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.primary
) {
    if (data.isEmpty()) return

    Canvas(modifier = modifier) {
        val maxY = data.maxOrNull() ?: 1f
        val minY = data.minOrNull() ?: 0f
        val height = size.height
        val width = size.width

        val stepX = width / (data.size - 1).coerceAtLeast(1)

        val points = data.mapIndexed { index, value ->
            val x = index * stepX
            val y = height - (value - minY) / (maxY - minY) * height
            Offset(x, y)
        }

        val path = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (point in points.drop(1)) {
                lineTo(point.x, point.y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
        )
    }
}
