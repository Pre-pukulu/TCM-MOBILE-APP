package com.example.tcm_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// TCM Brand Colors
val TCMBlue = Color(0xFF1E88E5)
val TCMGreen = Color(0xFF43A047)
val TCMRed = Color(0xFFE53935)
val TCMPurple = Color(0xFF9C27B0)
val TCMOrange = Color(0xFFFF9800)

// Light Theme Colors
private val AppColorScheme = lightColorScheme(
    primary = TCMBlue,
    onPrimary = Color.White,
    primaryContainer = TCMBlue.copy(alpha = 0.1f),
    secondary = TCMGreen,
    onSecondary = Color.White,
    secondaryContainer = TCMGreen.copy(alpha = 0.1f),
    tertiary = TCMPurple,
    onTertiary = Color.White,
    tertiaryContainer = TCMPurple.copy(alpha = 0.1f),
    error = TCMRed,
    onError = Color.White,
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF1C1B1F),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE8F5E9),
    onSurfaceVariant = Color(0xFF42473E)
)

@Composable
fun TCMAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = AppTypography,
        content = content
    )
}