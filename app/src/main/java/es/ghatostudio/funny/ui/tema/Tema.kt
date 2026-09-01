@file:Suppress("ktlint:standard:property-naming")

package es.ghatostudio.funny.ui.tema

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import es.ghatostudio.funny.dominio.Ajustes
import es.ghatostudio.funny.dominio.TemaId

/** La paleta activa. Todas las pantallas leen los colores de aquí. */
val LocalPaleta = staticCompositionLocalOf { paletaDe(TemaId.OSCURO_POR_DEFECTO) }

/**
 * Si las animaciones largas están permitidas. Combina la preferencia de la app
 * con la de accesibilidad del sistema, así que una pantalla solo tiene que
 * preguntar aquí.
 */
val LocalAnimaciones = compositionLocalOf { true }

@Composable
@ReadOnlyComposable
fun paleta(): Paleta = LocalPaleta.current

// ---------------------------------------------------------------------------
// Tokens de color
//
// Son atajos de lectura sobre [LocalPaleta]: existen para que las pantallas
// escriban `color = TextoTenue` en lugar de `LocalPaleta.current.textoTenue`
// veinte veces por fichero. Al ser getters de composición, cambiar de tema
// repinta la app sin reiniciar nada.
// ---------------------------------------------------------------------------

val Fondo: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.fondo
val FondoAlto: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.fondoAlto
val Superficie: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.superficie
val SuperficieAlta: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.superficieAlta
val Primario: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.primario
val SobrePrimario: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.sobrePrimario
val Acento: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.acento
val SobreAcento: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.sobreAcento
val TextoFuerte: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.textoFuerte
val TextoTenue: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.textoTenue
val Exito: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.exito
val SobreExito: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.sobreExito
val Fallo: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.fallo
val SobreFallo: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.sobreFallo
val Contorno: Color
    @Composable @ReadOnlyComposable
    get() = LocalPaleta.current.contorno

// ---------------------------------------------------------------------------
// Tipografía
//
// Sin fuentes empaquetadas: la de sistema respeta el tamaño de letra que tenga
// configurado quien juega, que es lo que pide el punto 4.8. Los tamaños van en
// `sp` justamente para eso.
// ---------------------------------------------------------------------------

private val TipografiaFunny =
    Typography(
        displayLarge =
            TextStyle(
                fontSize = 52.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp,
            ),
        displayMedium = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Black),
        headlineLarge = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold),
        headlineMedium = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold),
        titleLarge = TextStyle(fontSize = 21.sp, fontWeight = FontWeight.Bold),
        titleMedium = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold),
        bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 23.sp),
        bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
        labelLarge =
            TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.4.sp,
            ),
    )

private fun esquemaDe(p: Paleta) =
    if (p.esOscuro) {
        darkColorScheme(
            primary = p.primario,
            onPrimary = p.sobrePrimario,
            secondary = p.acento,
            onSecondary = p.sobreAcento,
            background = p.fondo,
            onBackground = p.textoFuerte,
            surface = p.superficie,
            onSurface = p.textoFuerte,
            surfaceVariant = p.superficieAlta,
            onSurfaceVariant = p.textoTenue,
            error = p.fallo,
            onError = p.sobreFallo,
            outline = p.contorno,
        )
    } else {
        lightColorScheme(
            primary = p.primario,
            onPrimary = p.sobrePrimario,
            secondary = p.acento,
            onSecondary = p.sobreAcento,
            background = p.fondo,
            onBackground = p.textoFuerte,
            surface = p.superficie,
            onSurface = p.textoFuerte,
            surfaceVariant = p.superficieAlta,
            onSurfaceVariant = p.textoTenue,
            error = p.fallo,
            onError = p.sobreFallo,
            outline = p.contorno,
        )
    }

/**
 * Decide qué paleta toca.
 *
 * Con «seguir al sistema» activado se usa el tema oscuro o claro por defecto
 * según lo que diga Android; si quien juega ha elegido un tema concreto, manda
 * su elección. Esto es lo que permite tener seis temas *y* respetar el modo
 * oscuro del móvil sin que una cosa pise a la otra.
 */
@Composable
fun paletaSegunAjustes(ajustes: Ajustes): Paleta {
    val sistemaOscuro = isSystemInDarkTheme()
    val id =
        when {
            !ajustes.temaDelSistema -> ajustes.tema
            sistemaOscuro -> if (ajustes.tema.esOscuro) ajustes.tema else TemaId.OSCURO_POR_DEFECTO
            else -> if (!ajustes.tema.esOscuro) ajustes.tema else TemaId.CLARO_POR_DEFECTO
        }
    return paletaDe(id)
}

@Composable
fun TemaFunny(
    paleta: Paleta,
    animaciones: Boolean = true,
    content: @Composable () -> Unit,
) {
    androidx.compose.runtime.CompositionLocalProvider(
        LocalPaleta provides paleta,
        LocalAnimaciones provides animaciones,
    ) {
        MaterialTheme(
            colorScheme = esquemaDe(paleta),
            typography = TipografiaFunny,
            content = content,
        )
    }
}
