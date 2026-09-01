package es.ghatostudio.funny.ui.pantallas

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
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Casilla
import es.ghatostudio.funny.dominio.EstadoJuego
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.Participante
import es.ghatostudio.funny.dominio.TipoCasilla
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.Banda
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.BotonSuave
import es.ghatostudio.funny.ui.comun.DialogoConfirmacion
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.PastillaJuego
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.Textos
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Acento
import es.ghatostudio.funny.ui.tema.Fondo
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.Superficie
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta
import kotlinx.coroutines.delay
import kotlin.random.Random

/** Cuántas veces «gira» el dado antes de parar, y cuánto dura cada giro. */
private const val GIROS_DEL_DADO = 14
private const val MS_POR_GIRO = 65L

@Composable
fun PantallaTablero(vm: JuegoViewModel, sonidos: Sonidos) {
    val t = textos()
    val p = paleta()
    val estado = vm.estado
    val participante = estado.participanteActivo ?: return
    val color = p.colorDeParticipante(participante.indiceColor)

    var tirando by remember { mutableStateOf(false) }
    var caraAnimada by remember { mutableIntStateOf(1) }
    var preguntandoSalir by remember { mutableStateOf(false) }

    LaunchedEffect(tirando) {
        if (!tirando) return@LaunchedEffect
        repeat(GIROS_DEL_DADO) {
            caraAnimada = Random.nextInt(1, 4)
            sonidos.toque()
            delay(MS_POR_GIRO)
        }
        vm.lanzarDado()
        tirando = false
    }

    if (preguntandoSalir) {
        DialogoConfirmacion(
            titulo = t[Clave.TABLERO_ABANDONAR],
            texto = t[Clave.TABLERO_ABANDONAR_PREGUNTA],
            textoConfirmar = t[Clave.TABLERO_ABANDONAR],
            textoCancelar = t[Clave.ACCION_CANCELAR],
            onConfirmar = {
                preguntandoSalir = false
                vm.abandonarPartida()
            },
            onCancelar = { preguntandoSalir = false }
        )
    }

    FondoFunny(tinte = color) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().padding(start = 8.dp, end = 16.dp, top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BotonSuave("‹  ${t[Clave.ACCION_VOLVER]}") { vm.ir(Pantalla.INICIO) }
                Spacer(Modifier.weight(1f))
                BotonSuave(t[Clave.TABLERO_ABANDONAR]) { preguntandoSalir = true }
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
                participante = participante,
                color = color,
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
    val t = textos()
    val p = paleta()
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        estado.participantes.forEachIndexed { indice, participante ->
            val activo = indice == estado.turno
            val color = p.colorDeParticipante(participante.indiceColor)
            val nombre = nombreEnIndice(estado, indice)
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (activo) color.copy(alpha = 0.28f) else Superficie)
                    .border(
                        width = if (activo) 2.dp else 0.dp,
                        color = if (activo) color else Color.Transparent,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .semantics {
                        contentDescription = t.con(
                            Clave.A11Y_FICHA,
                            nombre,
                            participante.posicion
                        )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(participante.emoji, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.width(6.dp))
                Text(
                    nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (activo) TextoFuerte else TextoTenue
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    "${participante.posicion}",
                    style = MaterialTheme.typography.titleMedium,
                    color = color
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
            // Filas en serpentina: la segunda va al revés, como un tablero de
            // mesa de verdad, para que el recorrido sea continuo.
            val invertida = indiceFila % 2 == 1
            val orden = if (invertida) fila.reversed() else fila
            val huecos = columnas - fila.size
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                if (invertida) repeat(huecos) { Spacer(Modifier.weight(1f)) }
                orden.forEach { casilla ->
                    CasillaVista(
                        casilla = casilla,
                        ocupantes = estado.participantes.filter { it.posicion == casilla.indice },
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
    ocupantes: List<Participante>,
    esDestino: Boolean,
    modifier: Modifier = Modifier
) {
    val t = textos()
    val p = paleta()
    val color = colorDeCasilla(casilla)
    val emoji = when (casilla.tipo) {
        TipoCasilla.SALIDA -> "🚩"
        TipoCasilla.META -> "🏁"
        TipoCasilla.COMODIN -> "🃏"
        TipoCasilla.TODOS -> "👥"
        TipoCasilla.NORMAL -> casilla.juego?.emoji ?: "•"
    }
    val nombre = when (casilla.tipo) {
        TipoCasilla.SALIDA -> t[Clave.TABLERO_SALIDA]
        TipoCasilla.META -> t[Clave.TABLERO_META]
        TipoCasilla.COMODIN -> t[Clave.CASILLA_COMODIN]
        TipoCasilla.TODOS -> t[Clave.CASILLA_TODOS]
        TipoCasilla.NORMAL -> casilla.juego?.let { t.nombreDe(it) }.orEmpty()
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
            .padding(3.dp)
            .semantics {
                contentDescription = t.con(Clave.A11Y_CASILLA, casilla.indice, nombre)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            "${casilla.indice}",
            style = MaterialTheme.typography.bodyMedium,
            color = color.copy(alpha = 0.85f),
            modifier = Modifier.align(Alignment.TopStart).clearAndSetSemantics { }
        )
        Text(emoji, style = MaterialTheme.typography.titleLarge)

        if (ocupantes.isNotEmpty()) {
            Row(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                ocupantes.forEach { participante ->
                    Box(
                        Modifier
                            .size(11.dp)
                            .clip(CircleShape)
                            .background(p.colorDeParticipante(participante.indiceColor))
                            .border(1.dp, Fondo, CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
private fun colorDeCasilla(casilla: Casilla): Color {
    val p = paleta()
    return when (casilla.tipo) {
        TipoCasilla.SALIDA -> p.casillaNeutra
        TipoCasilla.META -> p.primario
        TipoCasilla.COMODIN -> p.acento
        TipoCasilla.TODOS -> p.casillaTodos
        TipoCasilla.NORMAL -> casilla.juego?.let { p.colorDe(it) } ?: p.casillaNeutra
    }
}

@Composable
private fun PanelDeTurno(
    estado: EstadoJuego,
    participante: Participante,
    color: Color,
    tirando: Boolean,
    caraAnimada: Int,
    onTirar: () -> Unit,
    onContinuar: () -> Unit
) {
    val t = textos()
    val nombre = nombreDelActivo(estado)

    Tarjeta(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        color = Superficie,
        borde = color.copy(alpha = 0.5f)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "${participante.emoji}  ${t.con(Clave.TABLERO_TURNO_DE, nombre)}",
                style = MaterialTheme.typography.headlineMedium,
                color = color,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(14.dp))

            if (estado.dado == null) {
                CaraDeDado(valor = caraAnimada, activo = tirando)
                Spacer(Modifier.height(14.dp))
                BotonGrande(
                    texto = if (tirando) "🎲" else "🎲   ${t[Clave.TABLERO_TIRAR]}",
                    onClick = onTirar,
                    color = color,
                    colorTexto = paleta().textoSobre(color),
                    habilitado = !tirando
                )
            } else {
                CaraDeDado(valor = estado.dado, activo = false)
                Spacer(Modifier.height(12.dp))
                Text(
                    t.con(Clave.RESULTADO_AVANZAS_A, estado.destino),
                    style = MaterialTheme.typography.titleMedium,
                    color = TextoFuerte,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(10.dp))
                AvisoDeCasilla(estado, t)
                Spacer(Modifier.height(14.dp))
                BotonGrande(
                    texto = t[Clave.ACCION_CONTINUAR],
                    onClick = onContinuar,
                    color = color,
                    colorTexto = paleta().textoSobre(color)
                )
            }
        }
    }
}

@Composable
private fun AvisoDeCasilla(estado: EstadoJuego, t: Textos) {
    val p = paleta()
    when {
        estado.esPruebaFinal -> Banda(t[Clave.PRUEBA_FINAL], p.primario)
        estado.casillaDestino?.tipo == TipoCasilla.COMODIN ->
            Banda("🃏  ${t[Clave.CASILLA_COMODIN]}", Acento)

        estado.casillaDestino?.tipo == TipoCasilla.TODOS ->
            Banda(t[Clave.PRUEBA_JUEGAN_TODOS], p.casillaTodos)

        estado.juego != null -> PastillaJuego(estado.juego)
        else -> Unit
    }
}

@Composable
private fun CaraDeDado(valor: Int, activo: Boolean) {
    val t = textos()
    val p = paleta()
    val colorCara = if (activo) Acento else p.textoFuerte
    val colorPunto = p.fondo
    Box(
        modifier = Modifier
            .size(76.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(colorCara)
            .semantics { contentDescription = t.con(Clave.A11Y_DADO, valor) },
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
            posiciones.forEach { drawCircle(color = colorPunto, radius = radio, center = it) }
        }
    }
}
