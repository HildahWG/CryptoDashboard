package com.example.cryptodashboard.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtendedColors(
    val cardBackground: Color,
    val chartBackground: Color,
    val chartLabelColor: Color,
    val textColor: Color,
    val backgroundColor: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        cardBackground = CardBgLight,
        chartBackground = CardBgLight,
        chartLabelColor = ChartLabelLight,
        textColor = TextLight,
        backgroundColor = BackgroundLight
    )
}
