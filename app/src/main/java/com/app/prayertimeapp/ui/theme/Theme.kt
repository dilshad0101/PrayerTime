package com.app.prayertimeapp.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val primary = Color.White
val onPrimary = Color.Black
val scrime = Color.Gray


@Composable
fun PrayerTimeAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}