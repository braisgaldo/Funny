package es.ghatostudio.funny.ui.comun

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.ui.tema.contraste
import es.ghatostudio.funny.ui.tema.paleta
import qrcode.raw.ErrorCorrectionLevel
import qrcode.raw.QRCodeProcessor

/**
 * Contraste mínimo entre módulo y fondo para que un lector de QR no sufra. El
 * estándar no lo fija en un número, pero por debajo de 7:1 empiezan a aparecer
 * fallos con cámaras malas y poca luz.
 */
private const val CONTRASTE_MINIMO_QR = 7.0

/** Blanco y negro de emergencia si un tema no diera contraste suficiente. */
private val NEGRO_QR = Color(0xFF000000)
private val BLANCO_QR = Color(0xFFFFFFFF)

/**
 * Matriz de un código QR, calculada en el móvil y sin tocar la red.
 *
 * Devuelve `null` si el texto no se puede codificar, para que quien llama pueda
 * enseñar otra cosa en lugar de un hueco.
 */
fun matrizQr(datos: String): Array<BooleanArray>? =
    runCatching {
        val cuadros = QRCodeProcessor(datos, ErrorCorrectionLevel.MEDIUM).encode()
        Array(cuadros.size) { fila ->
            BooleanArray(cuadros[fila].size) { columna ->
                cuadros[fila][columna]?.dark == true
            }
        }
    }.getOrNull()

/**
 * Código QR dibujado con los colores del tema.
 *
 * Se pinta en un `Canvas` a partir de la matriz, no como imagen: así cambia de
 * color con el tema sin regenerar nada y no hace falta empaquetar ningún PNG.
 *
 * Los colores salen del tema activo, pero **siempre** con el módulo oscuro
 * sobre el plato claro: un QR en negativo lo leen algunas cámaras y otras no, y
 * aquí lo que importa es que funcione. Si un tema futuro no diera los 7:1 que
 * necesita un lector, se cae a blanco y negro y se deja constancia en el
 * comentario en lugar de entregar un QR bonito e ilegible.
 */
@Composable
fun CodigoQr(
    datos: String,
    descripcion: String,
    modifier: Modifier = Modifier,
    tamano: Dp = 200.dp,
) {
    val p = paleta()
    val matriz = remember(datos) { matrizQr(datos) } ?: return

    // El plato siempre es el color claro del tema y el módulo el oscuro,
    // independientemente de si el tema es claro u oscuro.
    val platoTema = if (p.esOscuro) p.textoFuerte else p.superficie
    val moduloTema = if (p.esOscuro) p.fondo else p.textoFuerte
    val suficiente = contraste(platoTema, moduloTema) >= CONTRASTE_MINIMO_QR
    val plato = if (suficiente) platoTema else BLANCO_QR
    val modulo = if (suficiente) moduloTema else NEGRO_QR

    Box(
        modifier =
            modifier
                .size(tamano)
                .clip(RoundedCornerShape(16.dp))
                .semantics { contentDescription = descripcion },
    ) {
        Canvas(
            Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                // La zona de silencio va dentro del recuadro claro: sin ella,
                // muchos lectores no encuentran el código.
                .padding(0.dp),
        ) {
            drawRect(color = plato, size = size)

            val modulos = matriz.size
            if (modulos == 0) return@Canvas
            val margen = size.minDimension * MARGEN_RELATIVO
            val util = size.minDimension - margen * 2
            val lado = util / modulos

            for (fila in 0 until modulos) {
                for (columna in 0 until matriz[fila].size) {
                    if (!matriz[fila][columna]) continue
                    drawRect(
                        color = modulo,
                        topLeft =
                            Offset(
                                x = margen + columna * lado,
                                y = margen + fila * lado,
                            ),
                        // Un pelo de más para que no se vean líneas de fondo
                        // entre módulos contiguos por el redondeo de píxeles.
                        size = Size(lado + 0.6f, lado + 0.6f),
                    )
                }
            }
        }
    }
}

/**
 * Zona de silencio, como fracción del lado. El estándar pide cuatro módulos;
 * con un QR de unos 30 módulos, un 8 % se queda muy cerca y se ve mejor
 * encajado en la tarjeta.
 */
private const val MARGEN_RELATIVO = 0.08f
