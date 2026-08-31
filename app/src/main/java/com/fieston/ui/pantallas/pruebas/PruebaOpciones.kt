package com.fieston.ui.pantallas.pruebas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.fieston.modelo.Categoria
import com.fieston.modelo.Prueba
import com.fieston.ui.comun.Sonidos
import com.fieston.ui.tema.Exito
import com.fieston.ui.tema.Fallo
import com.fieston.ui.tema.Superficie
import com.fieston.ui.tema.SuperficieAlta
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue

private val LETRAS = listOf("A", "B", "C", "D", "E", "F")

/**
 * Enunciado con respuestas. Se reutiliza en "¿Cuándo?", en las preguntas de
 * cultura y en las rondas en las que juegan todos los equipos.
 */
@Composable
fun BloqueOpciones(
    enunciado: String,
    tema: String?,
    opciones: List<String>,
    correcta: Int,
    elegida: Int?,
    revelar: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    onElegir: (Int) -> Unit
) {
    Column(modifier) {
        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(Superficie)
                .border(2.dp, color.copy(alpha = 0.4f), RoundedCornerShape(22.dp))
                .padding(20.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (!tema.isNullOrBlank()) {
                    Text(
                        tema.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = color
                    )
                    Spacer(Modifier.height(10.dp))
                }
                Text(
                    enunciado,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextoFuerte,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(Modifier.height(18.dp))

        opciones.forEachIndexed { indice, texto ->
            val esCorrecta = indice == correcta
            val esElegida = indice == elegida
            val fondo = when {
                revelar && esCorrecta -> Exito.copy(alpha = 0.22f)
                revelar && esElegida -> Fallo.copy(alpha = 0.22f)
                esElegida -> color.copy(alpha = 0.25f)
                else -> Superficie
            }
            val borde = when {
                revelar && esCorrecta -> Exito
                revelar && esElegida -> Fallo
                esElegida -> color
                else -> SuperficieAlta
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(fondo)
                    .border(2.dp, borde, RoundedCornerShape(16.dp))
                    .clickable(enabled = elegida == null && !revelar) { onElegir(indice) }
                    .padding(horizontal = 14.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(borde.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        LETRAS.getOrElse(indice) { "?" },
                        style = MaterialTheme.typography.titleMedium,
                        color = borde
                    )
                }
                Spacer(Modifier.width(14.dp))
                Text(
                    texto,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextoFuerte,
                    modifier = Modifier.weight(1f)
                )
                if (revelar && esCorrecta) {
                    Text("✓", style = MaterialTheme.typography.titleLarge, color = Exito)
                } else if (revelar && esElegida) {
                    Text("✕", style = MaterialTheme.typography.titleLarge, color = Fallo)
                }
            }
        }
    }
}

@Composable
fun PruebaCuando(vm: JuegoViewModel, prueba: Prueba.DeCuando, sonidos: Sonidos) {
    val categoria = Categoria.CUANDO
    val correcta = prueba.opciones.indexOf(prueba.evento.anio).coerceAtLeast(0)
    var elegida by remember { mutableStateOf<Int?>(null) }
    var agotado by remember { mutableStateOf(false) }
    val revelar = elegida != null || agotado

    MarcoPrueba(
        categoria = categoria,
        segundos = vm.estado.segundosDe(categoria),
        enMarcha = !revelar,
        sonidos = sonidos,
        marcador = null,
        onTiempoAgotado = { agotado = true }
    ) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            BloqueOpciones(
                enunciado = prueba.evento.texto,
                tema = prueba.evento.tema.ifBlank { "¿En qué año?" },
                opciones = prueba.opciones.map { it.toString() },
                correcta = correcta,
                elegida = elegida,
                revelar = revelar,
                color = categoria.color
            ) { indice ->
                elegida = indice
                if (indice == correcta) sonidos.acierto() else sonidos.fallo()
            }
        }

        if (revelar) {
            Spacer(Modifier.height(6.dp))
            Text(
                if (elegida == correcta) "¡Correcto! Ocurrió en ${prueba.evento.anio}."
                else "Ocurrió en ${prueba.evento.anio}.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextoTenue,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            BotonPrueba(
                texto = "CONTINUAR",
                color = categoria.color,
                modifier = Modifier.fillMaxWidth()
            ) {
                vm.resolverPrueba(elegida == correcta)
            }
        }
    }
}

@Composable
fun PruebaPreguntas(vm: JuegoViewModel, prueba: Prueba.DePreguntas, sonidos: Sonidos) {
    val categoria = Categoria.PREGUNTAS
    val pregunta = prueba.pregunta
    var elegida by remember { mutableStateOf<Int?>(null) }
    var agotado by remember { mutableStateOf(false) }
    val revelar = elegida != null || agotado

    MarcoPrueba(
        categoria = categoria,
        segundos = vm.estado.segundosDe(categoria),
        enMarcha = !revelar,
        sonidos = sonidos,
        marcador = null,
        onTiempoAgotado = { agotado = true }
    ) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            BloqueOpciones(
                enunciado = pregunta.texto,
                tema = pregunta.tema,
                opciones = pregunta.opciones,
                correcta = pregunta.correcta,
                elegida = elegida,
                revelar = revelar,
                color = categoria.color
            ) { indice ->
                elegida = indice
                if (indice == pregunta.correcta) sonidos.acierto() else sonidos.fallo()
            }
        }

        if (revelar) {
            Spacer(Modifier.height(12.dp))
            BotonPrueba(
                texto = "CONTINUAR",
                color = categoria.color,
                modifier = Modifier.fillMaxWidth()
            ) {
                vm.resolverPrueba(elegida == pregunta.correcta)
            }
        }
    }
}
