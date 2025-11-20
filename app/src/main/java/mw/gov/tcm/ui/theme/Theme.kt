package mw.gov.tcm.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// TCM Brand Colors
val TCMBlue = Color(0xFF1E88E5)
val TCMGreen = Color(0xFF43A047)
val TCMRed = Color(0xFFE53935)
val TCMPurple = Color(0xFF9C27B0)
val TCMOrange = Color(0xFFFF9800)

// Light Theme Colors
private val LightColorScheme = lightColorScheme(
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
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF1C1B1F),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE8F5E9),
    onSurfaceVariant = Color(0xFF42473E)
)

// Dark Theme Colors
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9), // Lighter Blue
    onPrimary = Color(0xFF0D47A1), // Darker Blue
    primaryContainer = Color(0xFF1565C0),
    secondary = Color(0xFFA5D6A7), // Lighter Green
    onSecondary = Color(0xFF1B5E20), // Darker Green
    secondaryContainer = Color(0xFF2E7D32),
    tertiary = Color(0xFFCE93D8), // Lighter Purple
    onTertiary = Color(0xFF4A148C), // Darker Purple
    tertiaryContainer = Color(0xFF6A1B9A),
    error = Color(0xFFEF9A9A), // Lighter Red
    onError = Color(0xFFB71C1C), // Darker Red
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF424242),
    onSurfaceVariant = Color(0xFFBDBDBD)
)

@Composable
fun TCMAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}