package com.fieston.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.TipoCasilla
import com.fieston.ui.comun.BotonGrande
import com.fieston.ui.comun.FondoFiesta
import com.fieston.ui.comun.Tarjeta
import com.fieston.ui.tema.Acento
import com.fieston.ui.tema.Primario
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue

@Composable
fun PantallaEntrega(vm: JuegoViewModel) {
    val estado = vm.estado
    val equipo = estado.equipoActivo ?: return
    val categoria = estado.categoria ?: return
    val esRondaTodos = !estado.esPruebaFinal &&
        estado.tablero.getOrNull(estado.destino)?.tipo == TipoCasilla.TODOS

    FondoFiesta(tinte = categoria.color) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                estado.esPruebaFinal -> Banda("🏁  PRUEBA FINAL", Primario)
                esRondaTodos -> Banda("👥  JUEGAN TODOS", Color(0xFF4CC9F0))
                else -> Banda("TURNO DE ${equipo.nombre.uppercase()}", equipo.color)
            }

            Spacer(Modifier.height(24.dp))

            Text(categoria.emoji, style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.height(6.dp))
            Text(
                categoria.etiqueta.uppercase(),
                style = MaterialTheme.typography.headlineLarge,
                color = categoria.color,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                categoria.lema,
                style = MaterialTheme.typography.bodyLarge,
                color = TextoTenue,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            if (categoria.soloActuante && !esRondaTodos) {
                Tarjeta(
                    modifier = Modifier.fillMaxWidth(),
                    borde = Acento.copy(alpha = 0.55f)
                ) {
                    Column(
                        Modifier.fillMaxWidth().padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "LE TOCA ACTUAR A",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextoTenue
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            equipo.jugadorDeTurno ?: "quien decida ${equipo.nombre}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Acento,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "👀  Que solo mire esta persona la pantalla",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextoTenue,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            if (esRondaTodos) {
                Tarjeta(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.fillMaxWidth().padding(18.dp)) {
                        Text(
                            "Responden todos los equipos por turnos, uno detrás de otro. " +
                                "Cada equipo que acierte avanza una casilla.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextoFuerte,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            Tarjeta(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.fillMaxWidth().padding(18.dp)) {
                    Text(
                        categoria.instrucciones,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoTenue
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "⏱  ${estado.segundosDe(categoria)} segundos",
                        style = MaterialTheme.typography.titleMedium,
                        color = categoria.color
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            BotonGrande(
                "¡EMPEZAR!",
                onClick = { vm.empezarPrueba() },
                color = categoria.color,
                colorTexto = Color.Black
            )
        }
    }
}

@Composable
private fun Banda(texto: String, color: Color) {
    Box(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.18f))
            .border(2.dp, color.copy(alpha = 0.6f), RoundedCornerShape(50))
            .padding(horizontal = 18.dp, vertical = 9.dp)
    ) {
        Text(texto, style = MaterialTheme.typography.labelLarge, color = color)
    }
}
