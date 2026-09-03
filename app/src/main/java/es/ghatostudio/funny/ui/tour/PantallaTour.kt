package es.ghatostudio.funny.ui.tour

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.BotonSuave
import es.ghatostudio.funny.ui.comun.EntradaEscalonada
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Contorno
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

/**
 * Un paso del tour: título, texto y qué se ilustra debajo.
 *
 * La ilustración no es decorativa: en el paso de los juegos se pintan los doce
 * de verdad, con su color y sus instrucciones, y en el de los modos las tres
 * tarjetas reales. Un tour que solo enseñara texto sería un manual con botones.
 */
private enum class PasoTour(
    val claveTitulo: Clave,
    val claveTexto: Clave,
    val emoji: String,
    val ilustracion: Ilustracion,
) {
    BIENVENIDA(
        Clave.TOUR_BIENVENIDA_TITULO,
        Clave.TOUR_BIENVENIDA_TEXTO,
        "🎉",
        Ilustracion.NINGUNA,
    ),
    MODOS(Clave.TOUR_MODOS_TITULO, Clave.TOUR_MODOS_TEXTO, "🎭", Ilustracion.MODOS),
    TABLERO(Clave.TOUR_TABLERO_TITULO, Clave.TOUR_TABLERO_TEXTO, "🎲", Ilustracion.TABLERO),
    CASILLAS(Clave.TOUR_CASILLAS_TITULO, Clave.TOUR_CASILLAS_TEXTO, "🃏", Ilustracion.NINGUNA),
    JUEGOS(Clave.TOUR_JUEGOS_TITULO, Clave.TOUR_JUEGOS_TEXTO, "🕹", Ilustracion.JUEGOS),
    SALON(Clave.TOUR_SALON_TITULO, Clave.TOUR_SALON_TEXTO, "📱", Ilustracion.SALON),
    AJUSTES(Clave.TOUR_AJUSTES_TITULO, Clave.TOUR_AJUSTES_TEXTO, "⚙", Ilustracion.NINGUNA),
    FINAL(Clave.TOUR_FINAL_TITULO, Clave.TOUR_FINAL_TEXTO, "✅", Ilustracion.NINGUNA),
}

private enum class Ilustracion { NINGUNA, MODOS, TABLERO, JUEGOS, SALON }

/**
 * Tour guiado.
 *
 * Se puede saltar en cualquier momento —eso no se discute— y se puede volver a
 * ver siempre desde Ajustes. Al llegar al final se marca como visto para no
 * ofrecerlo otra vez sin que nadie lo pida.
 */
@Composable
fun PantallaTour(vm: JuegoViewModel) {
    val t = textos()
    val pasos = PasoTour.entries
    var indice by remember { mutableIntStateOf(0) }
    val paso = pasos[indice]
    val esUltimo = indice == pasos.lastIndex

    fun terminar() {
        vm.marcarTourVisto()
        vm.ir(Pantalla.INICIO)
    }

    FondoFunny(tinte = Primario) {
        Column(Modifier.fillMaxSize()) {
            // Cabecera: progreso y salida. Siempre visibles.
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    t.con(Clave.TOUR_PROGRESO, indice + 1, pasos.size),
                    style = MaterialTheme.typography.labelLarge,
                    color = TextoTenue,
                )
                Spacer(Modifier.weight(1f))
                BotonSuave(t[Clave.TOUR_SALTAR]) { terminar() }
            }

            BarraDeProgreso(indice, pasos.size, Modifier.padding(horizontal = 20.dp))

            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 22.dp, vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // `key`-menos: el índice como clave de la entrada escalonada
                // basta para que cada paso vuelva a aparecer con su animación.
                EntradaEscalonada(0) {
                    Text(paso.emoji, style = MaterialTheme.typography.displayLarge)
                }
                Spacer(Modifier.height(10.dp))
                EntradaEscalonada(1) {
                    Text(
                        t[paso.claveTitulo],
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextoFuerte,
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(Modifier.height(10.dp))
                EntradaEscalonada(2) {
                    Text(
                        t[paso.claveTexto],
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoTenue,
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(Modifier.height(20.dp))

                EntradaEscalonada(3) {
                    when (paso.ilustracion) {
                        Ilustracion.NINGUNA -> Unit
                        Ilustracion.MODOS -> IlustracionModos()
                        Ilustracion.TABLERO -> IlustracionTablero()
                        Ilustracion.JUEGOS -> IlustracionJuegos(vm)
                        Ilustracion.SALON -> IlustracionSalon()
                    }
                }

                Spacer(Modifier.height(20.dp))
            }

            Row(
                Modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (indice > 0) {
                    Box(Modifier.weight(1f)) {
                        BotonGrande(
                            texto = t[Clave.TOUR_ANTERIOR],
                            onClick = { indice-- },
                            color = SuperficieAlta,
                            colorTexto = TextoFuerte,
                        )
                    }
                }
                Box(Modifier.weight(if (indice > 0) 1.4f else 1f)) {
                    BotonGrande(
                        texto =
                            if (esUltimo) {
                                t[Clave.TOUR_TERMINAR]
                            } else {
                                t[Clave.TOUR_SIGUIENTE]
                            },
                        onClick = { if (esUltimo) terminar() else indice++ },
                    )
                }
            }
        }
    }
}

@Composable
private fun BarraDeProgreso(indice: Int, total: Int, modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        repeat(total) { i ->
            val lleno by animateFloatAsState(
                targetValue = if (i <= indice) 1f else 0f,
                label = "progresoTour",
            )
            Box(
                Modifier
                    .weight(1f)
                    .height(5.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        if (lleno > 0.5f) Primario else SuperficieAlta,
                    ),
            )
        }
    }
}

@Composable
private fun IlustracionModos() {
    val t = textos()
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Modo.entries.forEach { modo ->
            Tarjeta(modifier = Modifier.fillMaxWidth()) {
                Row(
                    Modifier.fillMaxWidth().padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(modo.emoji, style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            t[modo.claveNombre],
                            style = MaterialTheme.typography.titleMedium,
                            color = TextoFuerte,
                        )
                        Text(
                            t[modo.claveDetalle],
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextoTenue,
                        )
                    }
                }
            }
        }
    }
}

/** Un trozo de tablero de mentira, con las casillas especiales señaladas. */
@Composable
private fun IlustracionTablero() {
    val t = textos()
    val p = paleta()
    val casillas =
        listOf(
            "🚩" to p.casillaNeutra,
            Juego.MIMICA.emoji to p.colorDe(Juego.MIMICA),
            Juego.PREGUNTAS.emoji to p.colorDe(Juego.PREGUNTAS),
            "🃏" to p.acento,
            Juego.EMOJIS.emoji to p.colorDe(Juego.EMOJIS),
            "👥" to p.casillaTodos,
            Juego.ORDENA.emoji to p.colorDe(Juego.ORDENA),
            "🏁" to p.primario,
        )
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            casillas.take(4).forEach { (emoji, color) -> CasillaDemo(emoji, color) }
        }
        Spacer(Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            casillas.drop(4).forEach { (emoji, color) -> CasillaDemo(emoji, color) }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            "${t[Clave.TABLERO_SALIDA]}  →  ${t[Clave.TABLERO_META]}",
            style = MaterialTheme.typography.labelLarge,
            color = TextoTenue,
        )
    }
}

@Composable
private fun CasillaDemo(emoji: String, color: androidx.compose.ui.graphics.Color) {
    Box(
        Modifier
            .size(52.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.18f))
            .border(1.5.dp, color.copy(alpha = 0.6f), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(emoji, style = MaterialTheme.typography.titleLarge)
    }
}

/**
 * Los dieciocho juegos con su nombre, su color y su descripción.
 *
 * Es el paso que el brief pedía explícitamente: el tour tiene que describir
 * todos los juegos, no solo mencionar que existen.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun IlustracionJuegos(vm: JuegoViewModel) {
    val t = textos()
    val p = paleta()
    val juegos = vm.contenidoActual.juegosJugables.ifEmpty { Juego.entries.toList() }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        juegos.forEach { juego ->
            val color = p.colorDe(juego)
            Tarjeta(modifier = Modifier.fillMaxWidth(), borde = color.copy(alpha = 0.35f)) {
                Row(Modifier.fillMaxWidth().padding(14.dp)) {
                    Text(juego.emoji, style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                t.nombreDe(juego),
                                style = MaterialTheme.typography.titleMedium,
                                color = color,
                            )
                            Spacer(Modifier.width(8.dp))
                            if (juego.valeEnSolitario) {
                                Etiquetita("🧍")
                            }
                            if (juego.soloActuante) {
                                Etiquetita("👀")
                            }
                            if (juego.veredictoDeLaMesa) {
                                Etiquetita("⚖")
                            }
                        }
                        Spacer(Modifier.height(2.dp))
                        Text(
                            t.lemaDe(juego),
                            style = MaterialTheme.typography.labelLarge,
                            color = TextoTenue,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            t.instruccionesDe(juego),
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextoTenue,
                        )
                    }
                }
            }
        }

        // Leyenda de los tres iconos de arriba, para que no queden en jeroglífico.
        Tarjeta(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(14.dp)) {
                LeyendaFila("🧍", t[Clave.MODO_SOLITARIO])
                LeyendaFila("👀", t[Clave.PRUEBA_LE_TOCA_ACTUAR_A])
                LeyendaFila("⚖", t[Clave.PRUEBA_VEREDICTO_DECIDE_MESA])
            }
        }
    }
}

@Composable
private fun Etiquetita(emoji: String) {
    Box(
        Modifier
            .clip(CircleShape)
            .background(SuperficieAlta)
            .padding(horizontal = 6.dp, vertical = 2.dp),
    ) {
        Text(emoji, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun LeyendaFila(emoji: String, texto: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
        Text(emoji, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.width(10.dp))
        Text(texto, style = MaterialTheme.typography.bodyMedium, color = TextoTenue)
    }
}

/** Un hub y dos mandos: la idea del salón en un dibujo. */
@Composable
private fun IlustracionSalon() {
    val t = textos()
    val p = paleta()
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(p.primario.copy(alpha = 0.18f))
                .border(2.dp, p.primario, RoundedCornerShape(16.dp))
                .padding(horizontal = 20.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("📺", style = MaterialTheme.typography.headlineLarge)
                Text(
                    t[Clave.SALON_ROL_HUB],
                    style = MaterialTheme.typography.labelLarge,
                    color = p.primario,
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Text("↑   ↑   ↑", style = MaterialTheme.typography.titleMedium, color = Contorno)
        Spacer(Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            repeat(3) { indice ->
                val color = p.colorDeParticipante(indice)
                Box(
                    Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(color.copy(alpha = 0.18f))
                        .border(1.5.dp, color, RoundedCornerShape(14.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("📱", style = MaterialTheme.typography.titleLarge)
                        Text(
                            t[Clave.SALON_ROL_MANDO],
                            style = MaterialTheme.typography.bodyMedium,
                            color = color,
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            t[Clave.SALON_SIN_RED],
            style = MaterialTheme.typography.bodyMedium,
            color = TextoTenue,
            textAlign = TextAlign.Center,
        )
    }
}
