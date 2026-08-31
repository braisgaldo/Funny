package com.fieston.ui.pantallas

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.TipoCasilla
import com.fieston.ui.comun.BotonGrande
import com.fieston.ui.comun.FondoFiesta
import com.fieston.ui.comun.Sonidos
import com.fieston.ui.comun.Tarjeta
import com.fieston.ui.tema.Exito
import com.fieston.ui.tema.Fallo
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue

@Composable
fun PantallaResultado(vm: JuegoViewModel, sonidos: Sonidos) {
    val estado = vm.estado
    val equipo = estado.equipoActivo ?: return
    val esRondaTodos = !estado.esPruebaFinal &&
        estado.tablero.getOrNull(estado.destino)?.tipo == TipoCasilla.TODOS

    LaunchedEffect(Unit) {
        if (estado.superada) sonidos.acierto() else sonidos.fallo()
    }

    val color = if (estado.superada) Exito else Fallo

    FondoFiesta(tinte = color) {
        Column(
            Modifier.fillMaxSize().padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                if (estado.superada) "🎉" else "😵",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(Modifier.height(10.dp))
            Text(
                when {
                    estado.ganador != null -> "¡PRUEBA FINAL SUPERADA!"
                    estado.superada -> "¡SUPERADA!"
                    else -> "NO HA PODIDO SER"
                },
                style = MaterialTheme.typography.headlineLarge,
                color = color,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Tarjeta(modifier = Modifier.fillMaxWidth()) {
                Column(
                    Modifier.fillMaxWidth().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (esRondaTodos) {
                        Text(
                            "Equipos que han acertado",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextoFuerte
                        )
                        Spacer(Modifier.height(12.dp))
                        if (estado.avanceExtra.isEmpty()) {
                            Text(
                                "Ninguno. Nadie se mueve.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextoTenue
                            )
                        } else {
                            estado.avanceExtra.forEach { indice ->
                                val e = estado.equipos.getOrNull(indice) ?: return@forEach
                                Row(
                                    Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(e.emoji, style = MaterialTheme.typography.titleLarge)
                                    Spacer(Modifier.width(10.dp))
                                    Text(
                                        e.nombre,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = e.color,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        "+1  →  casilla ${e.posicion}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextoTenue
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            "${equipo.emoji}  ${equipo.nombre}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = equipo.color,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            when {
                                estado.ganador != null -> "¡Habéis llegado a la meta!"
                                estado.superada -> "Avanzáis a la casilla ${estado.destino}"
                                else -> "Os quedáis en la casilla ${estado.origen}"
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextoTenue,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            BotonGrande(
                if (estado.ganador != null) "VER EL RESULTADO" else "SIGUIENTE TURNO",
                onClick = { vm.siguienteTurno() },
                color = color,
                colorTexto = Color.Black
            )
        }
    }
}
