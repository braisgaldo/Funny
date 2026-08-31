package com.fieston.ui.pantallas.pruebas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.Prueba
import com.fieston.ui.comun.BotonGrande
import com.fieston.ui.comun.FondoFiesta
import com.fieston.ui.comun.Sonidos
import com.fieston.ui.comun.Tarjeta
import com.fieston.ui.tema.Exito
import com.fieston.ui.tema.Fallo
import com.fieston.ui.tema.TextoTenue

private data class DatosRonda(
    val enunciado: String,
    val tema: String,
    val opciones: List<String>,
    val correcta: Int,
    val aclaracion: String?
)

/**
 * Casilla "todos juegan": la misma pregunta para cada equipo, uno detrás de
 * otro. No se revela nada hasta que han contestado todos.
 */
@Composable
fun PantallaRondaTodos(vm: JuegoViewModel, sonidos: Sonidos) {
    val estado = vm.estado
    val categoria = estado.categoria ?: return
    val datos = when (val prueba = estado.prueba) {
        is Prueba.DeCuando -> DatosRonda(
            enunciado = prueba.evento.texto,
            tema = prueba.evento.tema.ifBlank { "¿En qué año?" },
            opciones = prueba.opciones.map { it.toString() },
            correcta = prueba.opciones.indexOf(prueba.evento.anio).coerceAtLeast(0),
            aclaracion = "Ocurrió en ${prueba.evento.anio}."
        )

        is Prueba.DePreguntas -> DatosRonda(
            enunciado = prueba.pregunta.texto,
            tema = prueba.pregunta.tema,
            opciones = prueba.pregunta.opciones,
            correcta = prueba.pregunta.correcta,
            aclaracion = null
        )

        else -> return
    }

    var indiceEquipo by remember { mutableIntStateOf(0) }
    var listo by remember { mutableStateOf(false) }
    var elegida by remember { mutableStateOf<Int?>(null) }
    var agotado by remember { mutableStateOf(false) }
    var finalizada by remember { mutableStateOf(false) }
    val respuestas = remember { mutableStateListOf<Int>() }

    val equipo = estado.equipos.getOrNull(indiceEquipo)

    fun avanzar() {
        respuestas.add(elegida ?: -1)
        elegida = null
        agotado = false
        if (indiceEquipo + 1 >= estado.equipos.size) {
            finalizada = true
        } else {
            indiceEquipo++
            listo = false
        }
    }

    when {
        finalizada -> ResumenRonda(vm, datos, respuestas.toList(), sonidos)

        !listo && equipo != null -> FondoFiesta(tinte = equipo.color) {
            Column(
                Modifier.fillMaxSize().padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("📱", style = MaterialTheme.typography.displayLarge)
                Spacer(Modifier.height(12.dp))
                Text(
                    "PASAD EL MÓVIL A",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextoTenue
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "${equipo.emoji}  ${equipo.nombre}",
                    style = MaterialTheme.typography.displayMedium,
                    color = equipo.color,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(14.dp))
                Text(
                    "Equipo ${indiceEquipo + 1} de ${estado.equipos.size}. " +
                        "Nadie sabrá quién ha acertado hasta el final.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextoTenue,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(30.dp))
                BotonGrande("¡LISTOS!", onClick = { listo = true }, color = equipo.color, colorTexto = Color.Black)
            }
        }

        equipo != null -> key(indiceEquipo) {
            MarcoPrueba(
                categoria = categoria,
                segundos = estado.segundosDe(categoria),
                enMarcha = elegida == null && !agotado,
                sonidos = sonidos,
                marcador = "${equipo.emoji} ${equipo.nombre}",
                onTiempoAgotado = { agotado = true }
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    BloqueOpciones(
                        enunciado = datos.enunciado,
                        tema = datos.tema,
                        opciones = datos.opciones,
                        correcta = datos.correcta,
                        elegida = elegida,
                        revelar = false,
                        color = categoria.color
                    ) { indice ->
                        sonidos.toque()
                        elegida = indice
                    }
                }

                if (elegida != null || agotado) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        if (agotado && elegida == null) "Se acabó el tiempo, sin respuesta."
                        else "Respuesta guardada. No la contéis todavía.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoTenue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    BotonPrueba(
                        texto = if (indiceEquipo + 1 >= estado.equipos.size) "VER RESULTADOS" else "SIGUIENTE EQUIPO",
                        color = categoria.color,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        avanzar()
                    }
                }
            }
        }
    }
}

@Composable
private fun ResumenRonda(
    vm: JuegoViewModel,
    datos: DatosRonda,
    respuestas: List<Int>,
    sonidos: Sonidos
) {
    val estado = vm.estado
    FondoFiesta(tinte = Exito) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(10.dp))
            Text("👥", style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                "LA RESPUESTA CORRECTA ERA",
                style = MaterialTheme.typography.labelLarge,
                color = TextoTenue
            )
            Spacer(Modifier.height(10.dp))
            Box(
                Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(Exito.copy(alpha = 0.2f))
                    .border(2.dp, Exito, RoundedCornerShape(18.dp))
                    .padding(horizontal = 22.dp, vertical = 14.dp)
            ) {
                Text(
                    datos.opciones.getOrElse(datos.correcta) { "—" },
                    style = MaterialTheme.typography.headlineMedium,
                    color = Exito,
                    textAlign = TextAlign.Center
                )
            }
            if (datos.aclaracion != null) {
                Spacer(Modifier.height(10.dp))
                Text(
                    datos.aclaracion,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextoTenue,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(22.dp))

            Tarjeta(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.fillMaxWidth().padding(18.dp)) {
                    estado.equipos.forEachIndexed { indice, equipo ->
                        val respuesta = respuestas.getOrElse(indice) { -1 }
                        val acierto = respuesta == datos.correcta
                        Row(
                            Modifier.fillMaxWidth().padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(equipo.emoji, style = MaterialTheme.typography.titleLarge)
                            Spacer(Modifier.width(10.dp))
                            Column(Modifier.weight(1f)) {
                                Text(
                                    equipo.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = equipo.color
                                )
                                Text(
                                    if (respuesta >= 0) {
                                        datos.opciones.getOrElse(respuesta) { "—" }
                                    } else {
                                        "sin respuesta"
                                    },
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextoTenue
                                )
                            }
                            Text(
                                if (acierto) "✓  +1" else "✕",
                                style = MaterialTheme.typography.titleMedium,
                                color = if (acierto) Exito else Fallo
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(26.dp))

            BotonGrande(
                "CONTINUAR",
                onClick = {
                    sonidos.toque()
                    vm.resolverRondaTodos(estado.equipos.indices.map {
                        respuestas.getOrElse(it) { -1 } == datos.correcta
                    })
                },
                color = Exito,
                colorTexto = Color.Black
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}
