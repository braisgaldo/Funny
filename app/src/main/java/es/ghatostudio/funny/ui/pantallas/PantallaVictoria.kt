package es.ghatostudio.funny.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.ClavePlural
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Acento
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

/** Final de una partida por casillas: ganador y clasificación completa. */
@Composable
fun PantallaVictoria(vm: JuegoViewModel, sonidos: Sonidos) {
    val t = textos()
    val p = paleta()
    val estado = vm.estado
    val ganador = estado.ganador

    LaunchedEffect(Unit) { sonidos.acierto() }

    FondoFunny(tinte = Primario) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("🏆", style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.height(8.dp))
            Text(
                t[Clave.VICTORIA_TITULO],
                style = MaterialTheme.typography.titleLarge,
                color = TextoTenue,
            )
            Spacer(Modifier.height(6.dp))
            if (ganador != null) {
                Text(
                    "${ganador.emoji}  ${t.con(Clave.VICTORIA_GANADOR, nombreDe(estado, ganador))}",
                    style = MaterialTheme.typography.headlineLarge,
                    color = p.colorDeParticipante(ganador.indiceColor),
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(24.dp))

            Tarjeta(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.fillMaxWidth().padding(20.dp)) {
                    Text(
                        t[Clave.VICTORIA_CLASIFICACION],
                        style = MaterialTheme.typography.titleMedium,
                        color = TextoFuerte,
                    )
                    Spacer(Modifier.height(12.dp))
                    estado.clasificacion.forEachIndexed { puesto, participante ->
                        Row(
                            Modifier.fillMaxWidth().padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                medalla(puesto),
                                style = MaterialTheme.typography.titleMedium,
                                color = TextoTenue,
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(participante.emoji, style = MaterialTheme.typography.titleLarge)
                            Spacer(Modifier.width(10.dp))
                            Text(
                                nombreDe(estado, participante),
                                style = MaterialTheme.typography.titleMedium,
                                color = p.colorDeParticipante(participante.indiceColor),
                                modifier = Modifier.weight(1f),
                            )
                            Text(
                                t.con(Clave.TABLERO_CASILLA, participante.posicion),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextoTenue,
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(26.dp))

            BotonGrande(t[Clave.VICTORIA_OTRA_PARTIDA]) { vm.ir(Pantalla.MODO) }
            Spacer(Modifier.height(12.dp))
            BotonGrande(
                texto = t[Clave.VICTORIA_AL_MENU],
                onClick = { vm.volverAlMenu() },
                color = SuperficieAlta,
                colorTexto = TextoFuerte,
            )
        }
    }
}

/**
 * Fin del reto en solitario: puntos, marca personal y si se ha batido.
 *
 * Es una pantalla aparte porque no hay clasificación que enseñar ni ganador que
 * anunciar: lo único que importa es el número y si supera al de la última vez.
 */
@Composable
fun PantallaFinSolitario(vm: JuegoViewModel, sonidos: Sonidos) {
    val t = textos()
    val p = paleta()
    val estado = vm.estado

    LaunchedEffect(Unit) {
        if (estado.esRecordSolitario) sonidos.acierto() else sonidos.tic()
    }

    val color = if (estado.esRecordSolitario) Acento else Exito

    FondoFunny(tinte = color) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                if (estado.esRecordSolitario) "🥇" else "🧍",
                style = MaterialTheme.typography.displayLarge,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                t[Clave.VICTORIA_SOLITARIO_TITULO],
                style = MaterialTheme.typography.titleLarge,
                color = TextoTenue,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                t.con(Clave.VICTORIA_SOLITARIO_PUNTOS, estado.puntosSolitario),
                style = MaterialTheme.typography.displayMedium,
                color = color,
            )

            if (estado.esRecordSolitario) {
                Spacer(Modifier.height(10.dp))
                Text(
                    t[Clave.VICTORIA_SOLITARIO_RECORD],
                    style = MaterialTheme.typography.headlineMedium,
                    color = Acento,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(20.dp))

            Tarjeta(modifier = Modifier.fillMaxWidth()) {
                Column(
                    Modifier.fillMaxWidth().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        t.con(
                            Clave.VICTORIA_SOLITARIO_MEJOR,
                            estado.ajustes.mejorMarcaSolitario,
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = TextoFuerte,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        t.plural(ClavePlural.ACIERTOS, estado.puntosSolitario),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoTenue,
                    )
                }
            }

            Spacer(Modifier.height(26.dp))

            BotonGrande(
                texto = t[Clave.VICTORIA_OTRA_PARTIDA],
                onClick = { vm.empezarPartida() },
                color = color,
                colorTexto = p.textoSobre(color),
            )
            Spacer(Modifier.height(12.dp))
            BotonGrande(
                texto = t[Clave.VICTORIA_AL_MENU],
                onClick = { vm.volverAlMenu() },
                color = SuperficieAlta,
                colorTexto = TextoFuerte,
            )
        }
    }
}

/** Medalla para los tres primeros; a partir de ahí, el número del puesto. */
private fun medalla(puesto: Int): String =
    when (puesto) {
        0 -> "🥇"
        1 -> "🥈"
        2 -> "🥉"
        else -> "${puesto + 1}."
    }
