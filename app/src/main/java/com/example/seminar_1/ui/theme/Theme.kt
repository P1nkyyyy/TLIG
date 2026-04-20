package com.example.seminar_1.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val base: Dp = 4.dp,
    val base1: Dp = 8.dp,
    val base2: Dp = 12.dp,
    val base3: Dp = 16.dp,
    val base4: Dp = 24.dp,
    val base5: Dp = 32.dp,
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    get() = LocalSpacing.current

private val LightColorScheme = lightColorScheme(
    primary = GoldAccent,
    secondary = BlueAccent,
    tertiary = GreenAccent,
    background = BackgroundColorLight,
    surface = SurfaceColorLight,
    onPrimary = BackgroundColorLight,
    onSecondary = BackgroundColorLight,
    onTertiary = BackgroundColorLight,
    onBackground = PrimaryTextLight,
    onSurface = SecondaryTextLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = MutedTextLight,
    outline = BorderColorLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = GoldAccent,
    secondary = BlueAccent,
    tertiary = GreenAccent,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    onPrimary = BackgroundColorDark,
    onSecondary = BackgroundColorDark,
    onTertiary = BackgroundColorDark,
    onBackground = PrimaryTextDark,
    onSurface = SecondaryTextDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = MutedTextDark,
    outline = BorderColorDark
)

@Composable
fun Seminar1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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

    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}