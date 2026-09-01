package es.ghatostudio.funny.ui.pantallas.pruebas

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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.AREA_TACTIL_MINIMA
import es.ghatostudio.funny.ui.comun.BotonPrueba
import es.ghatostudio.funny.ui.comun.FilaBotones
import es.ghatostudio.funny.ui.comun.TarjetaPalabra
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.LIENZO_DIBUJO
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TEXTO_SOBRE_LIENZO
import es.ghatostudio.funny.ui.tema.TINTAS_DIBUJO
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

private val GROSORES = listOf(6f, 16f, 34f)

/** Un trazo del dibujo: los puntos son estado observable para repintar al vuelo. */
private class Trazo(val color: Color, val grosor: Float) {
    val puntos = mutableStateListOf<Offset>()
}

@Composable
fun PruebaDibujo(vm: JuegoViewModel, palabras: List<String>, sonidos: Sonidos) {
    val t = textos()
    val p = paleta()
    val juego = Juego.DIBUJO
    val colorJuego = p.colorDe(juego)
    var indice by remember { mutableIntStateOf(0) }
    var aciertos by remember { mutableIntStateOf(0) }
    var dibujando by remember { mutableStateOf(false) }
    var terminada by remember { mutableStateOf(false) }

    val trazos = remember { mutableStateListOf<Trazo>() }
    var colorActual by remember { mutableStateOf(TINTAS_DIBUJO.first()) }
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
        juego = juego,
        segundos = vm.estado.segundosDe(juego),
        enMarcha = dibujando && !terminada,
        sonidos = sonidos,
        marcador = t.con(Clave.PRUEBA_ACIERTOS, aciertos),
        onTiempoAgotado = { cerrar() }
    ) {
        if (!dibujando) {
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                TarjetaPalabra(
                    texto = palabra,
                    color = colorJuego,
                    encabezado = t[Clave.PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA]
                ) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        t[Clave.PRUEBA_DIBUJO_TIEMPO_AL_PULSAR],
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoTenue,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            BotonPrueba(
                texto = t[Clave.PRUEBA_DIBUJO_EMPEZAR],
                color = colorJuego,
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
                    .background(LIENZO_DIBUJO)
                    .border(2.dp, colorJuego.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
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
                        t[Clave.PRUEBA_DIBUJO_LIENZO_VACIO],
                        style = MaterialTheme.typography.bodyLarge,
                        color = TEXTO_SOBRE_LIENZO,
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
                TINTAS_DIBUJO.forEachIndexed { indice, color ->
                    BotonColor(
                        color = color,
                        seleccionado = colorActual == color,
                        descripcion = "${t[Clave.PRUEBA_COLOR]} ${indice + 1}"
                    ) {
                        colorActual = color
                    }
                }
                BotonColor(
                    color = LIENZO_DIBUJO,
                    seleccionado = colorActual == LIENZO_DIBUJO,
                    descripcion = t[Clave.PRUEBA_DIBUJO_GOMA],
                    esGoma = true
                ) {
                    colorActual = LIENZO_DIBUJO
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
                            .background(if (grosorActual == grosor) colorJuego else SuperficieAlta)
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

                BotonHerramienta("↶", t[Clave.PRUEBA_DESHACER]) { trazos.removeLastOrNull() }
                BotonHerramienta("🗑", t[Clave.PRUEBA_BORRAR_DIBUJO]) { trazos.clear() }
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SuperficieAlta)
                        .clickable(
                            interactionSource = interaccionOjo,
                            indication = null,
                            onClick = {}
                        )
                        .semantics { contentDescription = t[Clave.PRUEBA_DIBUJO_ESPIAR] },
                    contentAlignment = Alignment.Center
                ) {
                    Text("👁", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(Modifier.height(12.dp))

            FilaBotones {
                BotonPrueba(
                    texto = t[Clave.PRUEBA_SALTAR],
                    color = SuperficieAlta,
                    colorTexto = TextoFuerte,
                    modifier = Modifier.weight(1f)
                ) {
                    sonidos.toque()
                    siguientePalabra()
                }
                BotonPrueba(
                    texto = t[Clave.PRUEBA_ACERTADA],
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
    descripcion: String,
    esGoma: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(AREA_TACTIL_MINIMA)
            .semantics { contentDescription = descripcion }
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
private fun BotonHerramienta(icono: String, descripcion: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(AREA_TACTIL_MINIMA)
            .clip(RoundedCornerShape(14.dp))
            .background(SuperficieAlta)
            .clickable { onClick() }
            .semantics { contentDescription = descripcion },
        contentAlignment = Alignment.Center
    ) {
        Text(icono, style = MaterialTheme.typography.titleMedium, color = TextoFuerte)
    }
}
