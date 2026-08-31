package com.fieston.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.COLORES_EQUIPO
import com.fieston.modelo.Equipo
import com.fieston.modelo.Pantalla
import com.fieston.ui.comun.BotonGrande
import com.fieston.ui.comun.Cabecera
import com.fieston.ui.comun.FondoFiesta
import com.fieston.ui.comun.Tarjeta
import com.fieston.ui.tema.Fallo
import com.fieston.ui.tema.Primario
import com.fieston.ui.tema.SuperficieAlta
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue

@Composable
fun PantallaEquipos(vm: JuegoViewModel) {
    val estado = vm.estado
    FondoFiesta {
        Column(Modifier.fillMaxSize()) {
            Cabecera(
                titulo = "Equipos",
                subtitulo = "De 2 a ${COLORES_EQUIPO.size} equipos. Apunta quién juega en cada uno " +
                    "y el móvil irá diciendo a quién le toca actuar.",
                onVolver = { vm.ir(Pantalla.INICIO) }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                estado.equipos.forEach { equipo ->
                    TarjetaEquipo(
                        equipo = equipo,
                        sePuedeBorrar = estado.equipos.size > 2,
                        onNombre = { vm.renombrarEquipo(equipo.id, it) },
                        onBorrar = { vm.eliminarEquipo(equipo.id) },
                        onNuevoJugador = { vm.anadirJugador(equipo.id, it) },
                        onBorrarJugador = { vm.eliminarJugador(equipo.id, it) }
                    )
                }

                if (estado.equipos.size < COLORES_EQUIPO.size) {
                    BotonGrande(
                        "+  AÑADIR EQUIPO",
                        onClick = { vm.anadirEquipo() },
                        color = SuperficieAlta
                    )
                }
                Spacer(Modifier.height(4.dp))
            }

            Column(Modifier.padding(20.dp)) {
                BotonGrande(
                    "EMPEZAR PARTIDA",
                    onClick = { vm.empezarPartida() },
                    habilitado = estado.equipos.size >= 2
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TarjetaEquipo(
    equipo: Equipo,
    sePuedeBorrar: Boolean,
    onNombre: (String) -> Unit,
    onBorrar: () -> Unit,
    onNuevoJugador: (String) -> Unit,
    onBorrarJugador: (Int) -> Unit
) {
    var nuevoJugador by remember(equipo.id) { mutableStateOf("") }

    Tarjeta(borde = equipo.color.copy(alpha = 0.45f)) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(equipo.color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(equipo.emoji, style = MaterialTheme.typography.titleLarge)
                }
                Spacer(Modifier.width(12.dp))
                OutlinedTextField(
                    value = equipo.nombre,
                    onValueChange = { if (it.length <= 18) onNombre(it) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleMedium,
                    colors = coloresCampo(equipo.color)
                )
                if (sePuedeBorrar) {
                    Spacer(Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Fallo.copy(alpha = 0.15f))
                            .clickable { onBorrar() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("✕", color = Fallo, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            if (equipo.jugadores.isEmpty()) {
                Text(
                    "Sin jugadores apuntados: el juego dirá solo el nombre del equipo.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoTenue
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    equipo.jugadores.forEachIndexed { indice, jugador ->
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(equipo.color.copy(alpha = 0.18f))
                                .clickable { onBorrarJugador(indice) }
                                .padding(horizontal = 12.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                jugador,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextoFuerte
                            )
                            Spacer(Modifier.width(6.dp))
                            Text("✕", style = MaterialTheme.typography.bodyMedium, color = TextoTenue)
                        }
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = nuevoJugador,
                    onValueChange = { if (it.length <= 14) nuevoJugador = it },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = {
                        Text("Añadir jugador…", color = TextoTenue, style = MaterialTheme.typography.bodyMedium)
                    },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        onNuevoJugador(nuevoJugador)
                        nuevoJugador = ""
                    }),
                    colors = coloresCampo(equipo.color)
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(if (nuevoJugador.isBlank()) SuperficieAlta else equipo.color)
                        .clickable(enabled = nuevoJugador.isNotBlank()) {
                            onNuevoJugador(nuevoJugador)
                            nuevoJugador = ""
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "+",
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (nuevoJugador.isBlank()) TextoTenue else Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun coloresCampo(color: Color) = OutlinedTextFieldDefaults.colors(
    focusedTextColor = TextoFuerte,
    unfocusedTextColor = TextoFuerte,
    focusedBorderColor = color,
    unfocusedBorderColor = SuperficieAlta,
    cursorColor = Primario,
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent
)
