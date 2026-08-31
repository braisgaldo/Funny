package com.fieston.ui.pantallas

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.Casilla
import com.fieston.modelo.Equipo
import com.fieston.modelo.EstadoJuego
import com.fieston.modelo.Pantalla
import com.fieston.modelo.TipoCasilla
import com.fieston.ui.comun.BotonGrande
import com.fieston.ui.comun.BotonSuave
import com.fieston.ui.comun.FondoFiesta
import com.fieston.ui.comun.PastillaCategoria
import com.fieston.ui.comun.Sonidos
import com.fieston.ui.comun.Tarjeta
import com.fieston.ui.tema.Acento
import com.fieston.ui.tema.Fondo
import com.fieston.ui.tema.Primario
import com.fieston.ui.tema.Superficie
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun PantallaTablero(vm: JuegoViewModel, sonidos: Sonidos) {
    val estado = vm.estado
    val equipo = estado.equipoActivo ?: return

    var tirando by remember { mutableStateOf(false) }
    var caraAnimada by remember { mutableIntStateOf(1) }

    LaunchedEffect(tirando) {
        if (!tirando) return@LaunchedEffect
        repeat(14) {
            caraAnimada = Random.nextInt(1, 4)
            sonidos.toque()
            delay(65)
        }
        vm.lanzarDado()
        tirando = false
    }

    FondoFiesta(tinte = equipo.color) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().padding(start = 8.dp, end = 16.dp, top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BotonSuave("‹  Menú") { vm.ir(Pantalla.INICIO) }
                Spacer(Modifier.weight(1f))
                Text(
                    "Meta: casilla ${estado.meta}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoTenue
                )
            }

            Marcador(estado, Modifier.padding(horizontal = 16.dp))

            Spacer(Modifier.height(10.dp))

            Box(
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Tablero(estado)
            }

            Spacer(Modifier.height(10.dp))

            PanelDeTurno(
                estado = estado,
                equipo = equipo,
                tirando = tirando,
                caraAnimada = caraAnimada,
                onTirar = { tirando = true },
                onContinuar = { vm.continuarTrasDado() }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Marcador(estado: EstadoJuego, modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        estado.equipos.forEachIndexed { indice, equipo ->
            val activo = indice == estado.turno
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (activo) equipo.color.copy(alpha = 0.28f) else Superficie)
                    .border(
                        width = if (activo) 2.dp else 0.dp,
                        color = if (activo) equipo.color else Color.Transparent,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(equipo.emoji, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.width(6.dp))
                Text(
                    equipo.nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (activo) TextoFuerte else TextoTenue
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    "${equipo.posicion}",
                    style = MaterialTheme.typography.titleMedium,
                    color = equipo.color
                )
            }
        }
    }
}

@Composable
private fun Tablero(estado: EstadoJuego) {
    // Se ajustan las columnas para que el tablero entero quepa sin desplazarse.
    val columnas = when {
        estado.tablero.size > 24 -> 6
        estado.tablero.size > 16 -> 5
        else -> 4
    }
    val filas = estado.tablero.chunked(columnas)

    Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
        filas.forEachIndexed { indiceFila, fila ->
            val invertida = indiceFila % 2 == 1
            val orden = if (invertida) fila.reversed() else fila
            val huecos = columnas - fila.size
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                if (invertida) repeat(huecos) { Spacer(Modifier.weight(1f)) }
                orden.forEach { casilla ->
                    CasillaVista(
                        casilla = casilla,
                        ocupantes = estado.equipos.filter { it.posicion == casilla.indice },
                        esDestino = estado.dado != null && casilla.indice == estado.destino,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (!invertida) repeat(huecos) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun CasillaVista(
    casilla: Casilla,
    ocupantes: List<Equipo>,
    esDestino: Boolean,
    modifier: Modifier = Modifier
) {
    val color = colorDeCasilla(casilla)
    val emoji = when (casilla.tipo) {
        TipoCasilla.SALIDA -> "🚩"
        TipoCasilla.META -> "🏁"
        TipoCasilla.COMODIN -> "🃏"
        TipoCasilla.TODOS -> "👥"
        TipoCasilla.NORMAL -> casilla.categoria?.emoji ?: "•"
    }
    val brillo by animateFloatAsState(
        targetValue = if (esDestino) 1f else 0f,
        label = "brillo"
    )

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(14.dp))
            .background(color.copy(alpha = 0.16f + 0.22f * brillo))
            .border(
                width = (1.5f + 2f * brillo).dp,
                color = color.copy(alpha = 0.5f + 0.5f * brillo),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "${casilla.indice}",
            style = MaterialTheme.typography.bodyMedium,
            color = color.copy(alpha = 0.75f),
            modifier = Modifier.align(Alignment.TopStart)
        )
        Text(emoji, style = MaterialTheme.typography.titleLarge)

        if (ocupantes.isNotEmpty()) {
            Row(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                ocupantes.forEach { equipo ->
                    Box(
                        Modifier
                            .size(11.dp)
                            .clip(CircleShape)
                            .background(equipo.color)
                            .border(1.dp, Fondo, CircleShape)
                    )
                }
            }
        }
    }
}

private fun colorDeCasilla(casilla: Casilla): Color = when (casilla.tipo) {
    TipoCasilla.SALIDA -> Color(0xFF8B7BB8)
    TipoCasilla.META -> Primario
    TipoCasilla.COMODIN -> Acento
    TipoCasilla.TODOS -> Color(0xFF4CC9F0)
    TipoCasilla.NORMAL -> casilla.categoria?.color ?: Color(0xFF8B7BB8)
}

@Composable
private fun PanelDeTurno(
    estado: EstadoJuego,
    equipo: Equipo,
    tirando: Boolean,
    caraAnimada: Int,
    onTirar: () -> Unit,
    onContinuar: () -> Unit
) {
    Tarjeta(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        color = Superficie,
        borde = equipo.color.copy(alpha = 0.5f)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "TURNO DE",
                style = MaterialTheme.typography.labelLarge,
                color = TextoTenue
            )
            Text(
                "${equipo.emoji}  ${equipo.nombre}",
                style = MaterialTheme.typography.headlineMedium,
                color = equipo.color,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(14.dp))

            if (estado.dado == null) {
                CaraDeDado(valor = caraAnimada, activo = tirando)
                Spacer(Modifier.height(14.dp))
                BotonGrande(
                    if (tirando) "…" else "🎲   TIRAR EL DADO",
                    onClick = onTirar,
                    color = equipo.color,
                    colorTexto = Color.Black,
                    habilitado = !tirando
                )
            } else {
                CaraDeDado(valor = estado.dado, activo = false)
                Spacer(Modifier.height(12.dp))
                Text(
                    "Avanzas a la casilla ${estado.destino}",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextoFuerte
                )
                Spacer(Modifier.height(10.dp))
                AvisoDeCasilla(estado)
                Spacer(Modifier.height(14.dp))
                BotonGrande("CONTINUAR", onClick = onContinuar, color = equipo.color, colorTexto = Color.Black)
            }
        }
    }
}

@Composable
private fun AvisoDeCasilla(estado: EstadoJuego) {
    val tipo = estado.tablero.getOrNull(estado.destino)?.tipo
    when {
        estado.esPruebaFinal -> Etiqueta("🏁  ¡PRUEBA FINAL!", Primario)
        tipo == TipoCasilla.COMODIN -> Etiqueta("🃏  COMODÍN: eligen los rivales", Acento)
        tipo == TipoCasilla.TODOS -> Etiqueta("👥  ¡JUEGAN TODOS LOS EQUIPOS!", Color(0xFF4CC9F0))
        estado.categoria != null -> PastillaCategoria(estado.categoria)
        else -> Unit
    }
}

@Composable
private fun Etiqueta(texto: String, color: Color) {
    Box(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.18f))
            .border(1.5.dp, color.copy(alpha = 0.6f), RoundedCornerShape(50))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(texto, style = MaterialTheme.typography.labelLarge, color = color)
    }
}

@Composable
private fun CaraDeDado(valor: Int, activo: Boolean) {
    val color = if (activo) Acento else Color.White
    Box(
        modifier = Modifier
            .size(76.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.fillMaxSize().padding(14.dp)) {
            val radio = size.minDimension / 9f
            val posiciones = when (valor) {
                1 -> listOf(Offset(size.width / 2, size.height / 2))
                2 -> listOf(
                    Offset(size.width * 0.25f, size.height * 0.25f),
                    Offset(size.width * 0.75f, size.height * 0.75f)
                )

                else -> listOf(
                    Offset(size.width * 0.22f, size.height * 0.22f),
                    Offset(size.width / 2, size.height / 2),
                    Offset(size.width * 0.78f, size.height * 0.78f)
                )
            }
            posiciones.forEach { drawCircle(color = Fondo, radius = radio, center = it) }
        }
    }
}
