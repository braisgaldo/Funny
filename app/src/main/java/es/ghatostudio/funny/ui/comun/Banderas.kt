package es.ghatostudio.funny.ui.comun

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.ui.i18n.BanderaDibujada
import es.ghatostudio.funny.ui.tema.Contorno
import es.ghatostudio.funny.ui.tema.TextoTenue

/**
 * Las banderas que no tienen emoji, dibujadas.
 *
 * El gallego, el catalán y el euskera tienen bandera, pero su emoji es una
 * secuencia de etiquetas de subdivisión (`U+1F3F4` más el código en etiquetas)
 * que **no está en el conjunto RGI de Unicode**: de las subdivisiones, solo
 * Inglaterra, Escocia y Gales están, y ninguna fuente de Android dibuja las
 * demás. En un móvil real saldría una bandera negra o un rectángulo vacío, así
 * que antes llevaban su código en letras.
 *
 * Dibujarlas resuelve las dos cosas a la vez: se ven siempre, y se ven igual en
 * los seis temas. Son tres formas geométricas, no imágenes: no añaden ni un byte
 * de PNG al APK.
 *
 * Los colores son los de cada bandera y **no** salen de la paleta: una bandera
 * que cambia de color con el tema deja de ser esa bandera. Lo que sí sale de la
 * paleta es el contorno, que es lo que hace que el blanco de la gallega y el
 * amarillo de la catalana no se pierdan sobre un fondo claro.
 */
private val GALICIA_BLANCO = Color(0xFFFFFFFF)
private val GALICIA_AZUL = Color(0xFF0080C8)

private val SENYERA_AMARILLO = Color(0xFFFCDD09)
private val SENYERA_ROJO = Color(0xFFDA121A)

private val IKURRINA_ROJO = Color(0xFFD52B1E)
private val IKURRINA_VERDE = Color(0xFF009B48)
private val IKURRINA_BLANCO = Color(0xFFFFFFFF)

/** El tamaño de una insignia. Se parece al de un emoji de bandera al lado del texto. */
private val ANCHO = 30.dp
private val ALTO = 22.dp
private val RADIO = 4.dp

@Composable
fun BanderaPintada(bandera: BanderaDibujada, modifier: Modifier = Modifier) {
    val contorno = Contorno
    Canvas(
        modifier =
            modifier
                .size(ANCHO, ALTO)
                .clip(RoundedCornerShape(RADIO))
                .border(1.dp, contorno, RoundedCornerShape(RADIO)),
    ) {
        when (bandera) {
            BanderaDibujada.GALICIA -> galicia()
            BanderaDibujada.CATALUNA -> cataluna()
            BanderaDibujada.EUSKADI -> euskadi()
        }
    }
}

/** Campo blanco y banda azul en diagonal, del asta a la esquina inferior opuesta. */
private fun DrawScope.galicia() {
    drawRect(GALICIA_BLANCO)
    drawLine(
        color = GALICIA_AZUL,
        start = Offset(0f, 0f),
        end = Offset(size.width, size.height),
        strokeWidth = size.height * 0.30f,
        cap = StrokeCap.Square,
    )
}

/** La senyera: nueve franjas, cinco amarillas y cuatro rojas, empezando en amarillo. */
private fun DrawScope.cataluna() {
    drawRect(SENYERA_AMARILLO)
    val franja = size.height / 9f
    // Las rojas son las de índice impar; las amarillas ya están puestas de fondo.
    for (i in 1 until 9 step 2) {
        drawRect(
            color = SENYERA_ROJO,
            topLeft = Offset(0f, franja * i),
            size = Size(size.width, franja),
        )
    }
}

/** La ikurriña: campo rojo, cruz de San Andrés verde y cruz blanca encima. */
private fun DrawScope.euskadi() {
    drawRect(IKURRINA_ROJO)
    val grueso = size.height * 0.20f
    // La cruz verde va en diagonal, de esquina a esquina.
    drawLine(
        color = IKURRINA_VERDE,
        start = Offset(0f, 0f),
        end = Offset(size.width, size.height),
        strokeWidth = grueso,
        cap = StrokeCap.Square,
    )
    drawLine(
        color = IKURRINA_VERDE,
        start = Offset(size.width, 0f),
        end = Offset(0f, size.height),
        strokeWidth = grueso,
        cap = StrokeCap.Square,
    )
    // Y la blanca, recta y por encima.
    drawLine(
        color = IKURRINA_BLANCO,
        start = Offset(size.width / 2f, 0f),
        end = Offset(size.width / 2f, size.height),
        strokeWidth = grueso,
        cap = StrokeCap.Square,
    )
    drawLine(
        color = IKURRINA_BLANCO,
        start = Offset(0f, size.height / 2f),
        end = Offset(size.width, size.height / 2f),
        strokeWidth = grueso,
        cap = StrokeCap.Square,
    )
}

/**
 * El icono neutro de idioma: un globo.
 *
 * Lo llevan el inglés y el árabe, y lo pide así el punto 4.3 de la plantilla:
 * ninguno de los dos es de un país concreto, y elegir una bandera nacional
 * —¿la del Reino Unido o la de Estados Unidos? ¿y para el árabe, cuál de
 * veintidós?— sería arbitrario. Los dos llevan el mismo icono a propósito; lo
 * que los distingue es su nombre al lado, escrito en su propio idioma.
 *
 * Este sí toma el color de la paleta, porque no representa a nadie: es un icono
 * de interfaz y tiene que verse en los seis temas.
 */
@Composable
fun IconoDeIdioma(modifier: Modifier = Modifier) {
    val tinta = TextoTenue
    Canvas(modifier = modifier.size(ANCHO, ALTO)) {
        val radio = size.height * 0.44f
        val centro = Offset(size.width / 2f, size.height / 2f)
        val trazo = size.height * 0.075f
        drawCircle(color = tinta, radius = radio, center = centro, style = trazoFino(trazo))
        // El meridiano: una elipse estrecha, que es lo que da la idea de esfera.
        drawOval(
            color = tinta,
            topLeft = Offset(centro.x - radio * 0.42f, centro.y - radio),
            size = Size(radio * 0.84f, radio * 2f),
            style = trazoFino(trazo),
        )
        // Y el ecuador.
        drawLine(
            color = tinta,
            start = Offset(centro.x - radio, centro.y),
            end = Offset(centro.x + radio, centro.y),
            strokeWidth = trazo,
        )
    }
}

private fun trazoFino(ancho: Float) = Stroke(width = ancho)
