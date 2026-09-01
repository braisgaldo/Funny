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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Modo
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
import es.ghatostudio.funny.ui.tema.Fallo
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

@Composable
fun PantallaResultado(vm: JuegoViewModel, sonidos: Sonidos) {
    val t = textos()
    val p = paleta()
    val estado = vm.estado
    val esRondaDeTodos = estado.esRondaDeTodos
    val esSolitario = estado.modo == Modo.SOLITARIO
    val participante = estado.participanteActivo ?: return

    LaunchedEffect(Unit) {
        if (estado.superada) sonidos.acierto() else sonidos.fallo()
    }

    val color = if (estado.superada) Exito else Fallo

    FondoFunny(tinte = color) {
        Column(
            Modifier.fillMaxSize().padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                if (estado.superada) "🎉" else "😵",
                style = MaterialTheme.typography.displayLarge,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                when {
                    estado.ganador != null -> t[Clave.RESULTADO_FINAL_SUPERADA]
                    estado.superada -> t[Clave.RESULTADO_SUPERADA]
                    else -> t[Clave.RESULTADO_NO_HA_PODIDO_SER]
                },
                style = MaterialTheme.typography.headlineLarge,
                color = color,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(20.dp))

            Tarjeta(modifier = Modifier.fillMaxWidth()) {
                Column(
                    Modifier.fillMaxWidth().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    when {
                        esSolitario -> {
                            Text(
                                t.con(
                                    Clave.SOLITARIO_PROGRESO,
                                    estado.rondaSolitario,
                                    estado.rondasSolitario,
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                color = TextoFuerte,
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                t.plural(ClavePlural.PUNTOS, estado.puntosSolitario),
                                style = MaterialTheme.typography.headlineMedium,
                                color = Acento,
                            )
                        }

                        esRondaDeTodos -> ResumenDeRonda(vm)

                        else -> {
                            Text(
                                "${participante.emoji}  ${nombreDelActivo(estado)}",
                                style = MaterialTheme.typography.headlineMedium,
                                color = p.colorDeParticipante(participante.indiceColor),
                                textAlign = TextAlign.Center,
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                when {
                                    estado.ganador != null -> t[Clave.RESULTADO_LLEGADA_A_META]
                                    estado.superada ->
                                        t.con(Clave.RESULTADO_AVANZAS_A, estado.destino)

                                    else -> t.con(Clave.RESULTADO_TE_QUEDAS_EN, estado.origen)
                                },
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextoTenue,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(26.dp))

            BotonGrande(
                texto =
                    if (estado.ganador != null) {
                        t[Clave.RESULTADO_VER_RESULTADO]
                    } else {
                        t[Clave.RESULTADO_SIGUIENTE_TURNO]
                    },
                onClick = { vm.siguienteTurno() },
                color = color,
                colorTexto = p.textoSobre(color),
            )
        }
    }
}

/** Quién ha acertado en una casilla de «juegan todos». */
@Composable
private fun ResumenDeRonda(vm: JuegoViewModel) {
    val t = textos()
    val p = paleta()
    val estado = vm.estado

    Text(
        t[Clave.RONDA_TODOS_RESUMEN],
        style = MaterialTheme.typography.titleMedium,
        color = TextoFuerte,
    )
    Spacer(Modifier.height(12.dp))

    if (estado.avanceExtra.isEmpty()) {
        Text(
            t[Clave.RONDA_TODOS_NADIE],
            style = MaterialTheme.typography.bodyLarge,
            color = TextoTenue,
        )
        return
    }

    estado.avanceExtra.forEach { indice ->
        val participante = estado.participantes.getOrNull(indice) ?: return@forEach
        Row(
            Modifier.fillMaxWidth().padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(participante.emoji, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.width(10.dp))
            Text(
                nombreEnIndice(estado, indice),
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
