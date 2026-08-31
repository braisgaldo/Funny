package com.fieston.ui.tema

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Fondo = Color(0xFF12071F)
val FondoAlto = Color(0xFF1D0B33)
val Superficie = Color(0xFF241041)
val SuperficieAlta = Color(0xFF32195A)
val Primario = Color(0xFFFF3D81)
val Acento = Color(0xFFFFD166)
val TextoFuerte = Color(0xFFF6F1FF)
val TextoTenue = Color(0xFFB49BDD)
val Exito = Color(0xFF06D6A0)
val Fallo = Color(0xFFFF5C5C)

private val EsquemaFiesta = darkColorScheme(
    primary = Primario,
    onPrimary = Color.White,
    secondary = Acento,
    onSecondary = Color(0xFF2B1B00),
    background = Fondo,
    onBackground = TextoFuerte,
    surface = Superficie,
    onSurface = TextoFuerte,
    surfaceVariant = SuperficieAlta,
    onSurfaceVariant = TextoTenue,
    error = Fallo,
    outline = Color(0xFF4B2C7A)
)

private val TipografiaFiesta = Typography(
    displayLarge = TextStyle(fontSize = 52.sp, fontWeight = FontWeight.Black, letterSpacing = (-1).sp),
    displayMedium = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Black),
    headlineLarge = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold),
    headlineMedium = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold),
    titleLarge = TextStyle(fontSize = 21.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 23.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
    labelLarge = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.4.sp)
)

/** El juego se ve siempre en oscuro: es más agradable en una mesa de noche. */
@Composable
fun TemaFieston(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaFiesta,
        typography = TipografiaFiesta,
        content = content
    )
}
