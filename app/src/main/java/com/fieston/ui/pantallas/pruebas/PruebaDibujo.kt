package com.fieston.ui.pantallas.pruebas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.Categoria
import com.fieston.ui.comun.Sonidos
import com.fieston.ui.tema.Exito
import com.fieston.ui.tema.SuperficieAlta
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue

private val LIENZO = Color(0xFFFFFDF6)

private val PALETA = listOf(
    Color(0xFF1B1B1F),
    Color(0xFFE53935),
    Color(0xFFFB8C00),
    Color(0xFFFDD835),
    Color(0xFF43A047),
    Color(0xFF1E88E5),
    Color(0xFF00ACC1),
    Color(0xFF8E24AA),
    Color(0xFFEC407A),
    Color(0xFF6D4C41)
)

private val GROSORES = listOf(6f, 16f, 34f)

/** Un trazo del dibujo: los puntos son estado observable para repintar al vuelo. */
private class Trazo(val color: Color, val grosor: Float) {
    val puntos = mutableStateListOf<Offset>()
}

@Composable
fun PruebaDibujo(vm: JuegoViewModel, palabras: List<String>, sonidos: Sonidos) {
    val categoria = Categoria.DIBUJO
    var indice by remember { mutableIntStateOf(0) }
    var aciertos by remember { mutableIntStateOf(0) }
    var dibujando by remember { mutableStateOf(false) }
    var terminada by remember { mutableStateOf(false) }

    val trazos = remember { mutableStateListOf<Trazo>() }
    var colorActual by remember { mutableStateOf(PALETA.first()) }
    var grosorActual by remember { mutableFloatStateOf(GROSORES[1]) }

    fun cerrar() {
        if (terminada) return
        terminada = true
        vm.resolverPrueba(aciertos > 0)
    }

    LaunchedEffect(indice) {
        if (indice >= palabras.size) cerrar()
    }

    val palabra = palabras.getOrNull(indice) ?: ""

    fun siguientePalabra() {
        trazos.clear()
        dibujando = false
        indice++
    }

    MarcoPrueba(
        categoria = categoria,
        segundos = vm.estado.segundosDe(categoria),
        enMarcha = dibujando && !terminada,
        sonidos = sonidos,
        marcador = "✔  $aciertos",
        onTiempoAgotado = { cerrar() }
    ) {
        if (!dibujando) {
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                TarjetaPalabra(
                    texto = palabra,
                    color = categoria.color,
                    encabezado = "SOLO LA MIRA QUIEN DIBUJA"
                ) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "El tiempo empieza a contar al pulsar el botón.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoTenue,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            BotonPrueba(
                texto = "🎨   EMPEZAR A DIBUJAR",
                color = categoria.color,
                modifier = Modifier.fillMaxWidth()
            ) {
                dibujando = true
            }
        } else {
            val interaccionOjo = remember { MutableInteractionSource() }
            val espiando by interaccionOjo.collectIsPressedAsState()

            Box(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(LIENZO)
                    .border(2.dp, categoria.color.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(colorActual, grosorActual) {
                            detectDragGestures(
                                onDragStart = { posicion ->
                                    val trazo = Trazo(colorActual, grosorActual)
                                    trazo.puntos.add(posicion)
                                    trazos.add(trazo)
                                },
                                onDrag = { cambio, _ ->
                                    cambio.consume()
                                    trazos.lastOrNull()?.puntos?.add(cambio.position)
                                }
                            )
                        }
                        .pointerInput(colorActual, grosorActual) {
                            detectTapGestures { posicion ->
                                val trazo = Trazo(colorActual, grosorActual)
                                trazo.puntos.add(posicion)
                                trazos.add(trazo)
                            }
                        }
                ) {
                    trazos.forEach { trazo ->
                        when {
                            trazo.puntos.size == 1 -> drawCircle(
                                color = trazo.color,
                                radius = trazo.grosor / 2f,
                                center = trazo.puntos.first()
                            )

                            trazo.puntos.size > 1 -> drawPoints(
                                points = trazo.puntos,
                                pointMode = PointMode.Polygon,
                                color = trazo.color,
                                strokeWidth = trazo.grosor,
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }

                if (espiando) {
                    Box(
                        Modifier
                            .align(Alignment.TopCenter)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.Black.copy(alpha = 0.82f))
                            .padding(horizontal = 18.dp, vertical = 10.dp)
                    ) {
                        Text(
                            palabra,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                    }
                }

                if (trazos.isEmpty()) {
                    Text(
                        "Dibuja aquí con el dedo",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFBBB4A8),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PALETA.forEach { color ->
                    BotonColor(color = color, seleccionado = colorActual == color) {
                        colorActual = color
                    }
                }
                BotonColor(color = LIENZO, seleccionado = colorActual == LIENZO, esGoma = true) {
                    colorActual = LIENZO
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GROSORES.forEach { grosor ->
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (grosorActual == grosor) categoria.color else SuperficieAlta)
                            .clickable { grosorActual = grosor },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            Modifier
                                .size((grosor / 2.2f).dp.coerceAtLeast(5.dp))
                                .clip(CircleShape)
                                .background(if (grosorActual == grosor) Color.Black else TextoFuerte)
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                BotonHerramienta("↶") { trazos.removeLastOrNull() }
                BotonHerramienta("🗑") { trazos.clear() }
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SuperficieAlta)
                        .clickable(
                            interactionSource = interaccionOjo,
                            indication = null,
                            onClick = {}
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("👁", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(Modifier.height(12.dp))

            FilaBotones {
                BotonPrueba(
                    texto = "SALTAR",
                    color = SuperficieAlta,
                    colorTexto = TextoFuerte,
                    modifier = Modifier.weight(1f)
                ) {
                    sonidos.toque()
                    siguientePalabra()
                }
                BotonPrueba(
                    texto = "✓  ¡LO HAN ADIVINADO!",
                    color = Exito,
                    modifier = Modifier.weight(2f)
                ) {
                    sonidos.acierto()
                    aciertos++
                    siguientePalabra()
                }
            }
        }
    }
}

@Composable
private fun BotonColor(
    color: Color,
    seleccionado: Boolean,
    esGoma: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (seleccionado) 4.dp else 1.dp,
                color = if (seleccionado) Color.White else Color(0x33FFFFFF),
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (esGoma) {
            Text("🧽", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun BotonHerramienta(icono: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(SuperficieAlta)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(icono, style = MaterialTheme.typography.titleMedium, color = TextoFuerte)
    }
}
