package com.example.indexcardtrainer.ui.theme

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
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Orange80,
    onPrimary = Orange20,
    primaryContainer = Orange30,
    onPrimaryContainer = Orange90,
    inversePrimary = Orange40,

    secondary = DarkBlue80,
    onSecondary = DarkBlue20,
    secondaryContainer = DarkBlue50,
    onSecondaryContainer = DarkBlue90,

    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,

    background = Gray10,
    onBackground = Gray90,
    surface = OrangeGray30,
    onSurface = OrangeGray80,
    inverseSurface = Gray90,
    inverseOnSurface = Gray10,
    surfaceVariant = OrangeGray30,
    onSurfaceVariant = OrangeGray80,
    outline = OrangeGray80,
    outlineVariant = Gray60,
    tertiary = Gray50,
    tertiaryContainer = Gray20,
    onTertiaryContainer = Gray90,
    onTertiary = Gray90

)

private val LightColorScheme = lightColorScheme(
    primary = Orange40,
    onPrimary = Color.White,
    primaryContainer = Orange90,
    onPrimaryContainer = Orange10,
    inversePrimary = Orange80,

    secondary = DarkBlue40,
    onSecondary = Color.White,
    secondaryContainer = DarkBlue90,
    onSecondaryContainer = DarkBlue10,

    error = Red80,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,

    background = Gray99,
    onBackground = Gray10,
    surface = OrangeGray90,
    onSurface = OrangeGray30,
    inverseSurface = Gray10,
    inverseOnSurface = Gray90,
    surfaceVariant = OrangeGray90,
    onSurfaceVariant = OrangeGray30,
    outline = OrangeGray50,
    outlineVariant = Gray30,
    tertiary = Gray50,
    tertiaryContainer = Gray60,
    onTertiaryContainer = Gray20,
    onTertiary = Gray20

)

@Composable
fun IndexCardTrainerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}