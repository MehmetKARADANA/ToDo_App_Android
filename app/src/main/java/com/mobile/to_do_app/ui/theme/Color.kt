package com.mobile.to_do_app.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val background = Color(0xFFFFFFFF)

val LightBackground = Color(0xFFF9FAFB)
val LightPrimary = Color(0xFF3B82F6)
val LightAccent = Color(0xFFF97316)
val LightTextPrimary = Color(0xFF111827)
val LightTextSecondary = Color(0xFF6B7280)
val LightSuccess = Color(0xFF10B981)
val LightError = Color(0xFFEF4444)
val LightCard = Color(0xFFE5E7EB)

val gradientBackground = Brush.verticalGradient(
    colors = listOf(
        LightPrimary.copy(alpha = 0.8f),
        LightAccent.copy(alpha = 0.6f),
        LightCard.copy(alpha = 0.9f)
    )
)
/* val gradientBackground = Brush.verticalGradient(
      colors = listOf(
          MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
          MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f),
          MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
      )
  )*/
