package com.fieston.ui.comun

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fieston.modelo.Categoria
import com.fieston.ui.tema.Acento
import com.fieston.ui.tema.Exito
import com.fieston.ui.tema.Fallo
import com.fieston.ui.tema.Fondo
import com.fieston.ui.tema.FondoAlto
import com.fieston.ui.tema.Primario
import com.fieston.ui.tema.Superficie
import com.fieston.ui.tema.SuperficieAlta
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue
import kotlin.math.ceil

@Composable
fun FondoFiesta(
    modifier: Modifier = Modifier,
    tinte: Color? = null,
    contenido: @Composable BoxScope.() -> Unit
) {
    val arriba = tinte?.copy(alpha = 0.22f)?.compositeSobre(FondoAlto) ?: FondoAlto
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(arriba, Fondo, Fondo))),
        content = contenido
    )
}

private fun Color.compositeSobre(fondo: Color): Color = Color(
    red = red * alpha + fondo.red * (1 - alpha),
    green = green * alpha + fondo.green * (1 - alpha),
    blue = blue * alpha + fondo.blue * (1 - alpha),
    alpha = 1f
)

@Composable
fun BotonGrande(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Primario,
    colorTexto: Color = Color.White,
    habilitado: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().heightIn(min = 60.dp),
        enabled = habilitado,
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = colorTexto,
            disabledContainerColor = SuperficieAlta,
            disabledContentColor = TextoTenue
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BotonSuave(
    texto: String,
    modifier: Modifier = Modifier,
    color: Color = TextoTenue,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(texto, color = color, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun Cabecera(
    titulo: String,
    modifier: Modifier = Modifier,
    subtitulo: String? = null,
    onVolver: (() -> Unit)? = null
) {
    Column(modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp)) {
        if (onVolver != null) {
            TextButton(
                onClick = onVolver,
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
            ) {
                Text("‹  Volver", color = TextoTenue, style = MaterialTheme.typography.labelLarge)
            }
            Spacer(Modifier.height(4.dp))
        }
        Text(titulo, style = MaterialTheme.typography.headlineLarge, color = TextoFuerte)
        if (subtitulo != null) {
            Spacer(Modifier.height(4.dp))
            Text(subtitulo, style = MaterialTheme.typography.bodyMedium, color = TextoTenue)
        }
    }
}

@Composable
fun Tarjeta(
    modifier: Modifier = Modifier,
    color: Color = Superficie,
    borde: Color? = null,
    contenido: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = color,
        shape = RoundedCornerShape(22.dp),
        border = borde?.let { BorderStroke(2.dp, it) },
        content = contenido
    )
}

@Composable
fun PastillaCategoria(categoria: Categoria, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = categoria.color.copy(alpha = 0.18f),
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.5.dp, categoria.color.copy(alpha = 0.65f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(categoria.emoji, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.width(8.dp))
            Text(
                categoria.etiqueta.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = categoria.color,
                maxLines = 1,
                softWrap = false
            )
        }
    }
}

@Composable
fun Punto(color: Color, texto: String, modifier: Modifier = Modifier, tamano: Int = 28) {
    Box(
        modifier = modifier
            .size(tamano.dp)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(texto, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun SelectorSegmentado(
    opciones: List<String>,
    seleccion: Int,
    modifier: Modifier = Modifier,
    onSeleccion: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SuperficieAlta)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        opciones.forEachIndexed { indice, etiqueta ->
            val activo = indice == seleccion
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(13.dp))
                    .background(if (activo) Primario else Color.Transparent)
                    .clickable { onSeleccion(indice) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    etiqueta,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (activo) Color.White else TextoTenue
                )
            }
        }
    }
}

/**
 * Cuenta atrás de la prueba. Pita en los últimos cinco segundos y avisa al
 * terminar. Si [enMarcha] pasa a false se congela donde estaba.
 */
@Composable
fun Cronometro(
    segundos: Int,
    enMarcha: Boolean,
    sonidos: Sonidos,
    modifier: Modifier = Modifier,
    onFin: () -> Unit
) {
    var restante by remember(segundos) { mutableFloatStateOf(segundos.toFloat()) }
    val alTerminar by rememberUpdatedState(onFin)

    LaunchedEffect(segundos, enMarcha) {
        if (!enMarcha) return@LaunchedEffect
        var anterior = 0L
        while (restante > 0f) {
            withFrameNanos { ahora ->
                if (anterior != 0L) {
                    val delta = (ahora - anterior) / 1_000_000_000f
                    restante = (restante - delta).coerceAtLeast(0f)
                }
                anterior = ahora
            }
        }
        sonidos.finDeTiempo()
        alTerminar()
    }

    val enteros = ceil(restante).toInt()
    LaunchedEffect(enteros, enMarcha) {
        if (enMarcha && enteros in 1..5) sonidos.tic()
    }

    val progreso = if (segundos <= 0) 0f else (restante / segundos).coerceIn(0f, 1f)
    val color = when {
        progreso > 0.5f -> Exito
        progreso > 0.2f -> Acento
        else -> Fallo
    }

    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = enteros.toString(),
            style = MaterialTheme.typography.headlineLarge,
            color = color
        )
        Spacer(Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp)),
            color = color,
            trackColor = SuperficieAlta,
            strokeCap = StrokeCap.Round,
            drawStopIndicator = {}
        )
    }
}
