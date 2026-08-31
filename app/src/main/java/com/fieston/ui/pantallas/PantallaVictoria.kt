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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.Pantalla
import com.fieston.ui.comun.BotonGrande
import com.fieston.ui.comun.FondoFiesta
import com.fieston.ui.comun.Sonidos
import com.fieston.ui.comun.Tarjeta
import com.fieston.ui.tema.Acento
import com.fieston.ui.tema.SuperficieAlta
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue

@Composable
fun PantallaVictoria(vm: JuegoViewModel, sonidos: Sonidos) {
    val estado = vm.estado
    val ganador = estado.ganador

    LaunchedEffect(Unit) {
        sonidos.acierto()
    }

    FondoFiesta(tinte = Acento) {
        Column(
            Modifier.fillMaxSize().padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🏆", style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.height(8.dp))
            Text(
                "¡GANAN!",
                style = MaterialTheme.typography.headlineLarge,
                color = Acento
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "${ganador?.emoji ?: "🎉"}  ${ganador?.nombre ?: "El equipo campeón"}",
                style = MaterialTheme.typography.displayMedium,
                color = ganador?.color ?: TextoFuerte,
                textAlign = TextAlign.Center
            )
            val jugadoresGanadores = ganador?.jugadores.orEmpty()
            if (jugadoresGanadores.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    jugadoresGanadores.joinToString(" · "),
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextoTenue,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(26.dp))

            Tarjeta(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.fillMaxWidth().padding(18.dp)) {
                    Text(
                        "Clasificación final",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextoFuerte
                    )
                    Spacer(Modifier.height(10.dp))
                    estado.equipos
                        .sortedByDescending { it.posicion }
                        .forEachIndexed { puesto, equipo ->
                            Row(
                                Modifier.fillMaxWidth().padding(vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "${puesto + 1}.",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextoTenue
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(equipo.emoji, style = MaterialTheme.typography.titleLarge)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    equipo.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = equipo.color,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "casilla ${equipo.posicion}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextoTenue
                                )
                            }
                        }
                }
            }

            Spacer(Modifier.height(28.dp))

            BotonGrande("REVANCHA", onClick = { vm.empezarPartida() })
            Spacer(Modifier.height(12.dp))
            BotonGrande(
                "CAMBIAR EQUIPOS",
                onClick = { vm.ir(Pantalla.EQUIPOS) },
                color = SuperficieAlta
            )
            Spacer(Modifier.height(12.dp))
            BotonGrande(
                "MENÚ PRINCIPAL",
                onClick = { vm.volverAlMenu() },
                color = SuperficieAlta
            )
        }
    }
}
