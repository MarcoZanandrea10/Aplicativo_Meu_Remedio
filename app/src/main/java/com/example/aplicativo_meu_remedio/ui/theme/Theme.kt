package com.example.aplicativo_meu_remedio.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueDark,
    background = Background,
    surface = CardColor,
    onPrimary = CardColor,
    onSecondary = CardColor,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun Aplicativo_Meu_RemedioTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}