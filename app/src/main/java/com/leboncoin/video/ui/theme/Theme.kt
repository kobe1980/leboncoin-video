package com.leboncoin.video.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    onPrimary = White,
    primaryContainer = OrangeDark,
    onPrimaryContainer = White,
    secondary = Gray600,
    onSecondary = White,
    secondaryContainer = Gray700,
    onSecondaryContainer = White,
    tertiary = Gray500,
    onTertiary = White,
    background = Gray900,
    onBackground = White,
    surface = Gray800,
    onSurface = White,
    surfaceVariant = Gray700,
    onSurfaceVariant = Gray300,
    outline = Gray500,
    error = Error,
    onError = White
)

private val LightColorScheme = lightColorScheme(
    primary = Orange,
    onPrimary = White,
    primaryContainer = OrangeLight,
    onPrimaryContainer = Black,
    secondary = Gray600,
    onSecondary = White,
    secondaryContainer = Gray200,
    onSecondaryContainer = Gray800,
    tertiary = Gray500,
    onTertiary = White,
    background = White,
    onBackground = Gray900,
    surface = White,
    onSurface = Gray900,
    surfaceVariant = Gray100,
    onSurfaceVariant = Gray700,
    outline = Gray300,
    error = Error,
    onError = White
)

@Composable
fun LeboncoinVideoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Désactivé pour garder les couleurs leboncoin
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
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
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}