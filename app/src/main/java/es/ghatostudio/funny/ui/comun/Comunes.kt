package es.ghatostudio.funny.ui.comun

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.i18n.Idioma
import es.ghatostudio.funny.ui.i18n.Insignia
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Acento
import es.ghatostudio.funny.ui.tema.Contorno
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Fallo
import es.ghatostudio.funny.ui.tema.Fondo
import es.ghatostudio.funny.ui.tema.FondoAlto
import es.ghatostudio.funny.ui.tema.LocalAnimaciones
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.SobrePrimario
import es.ghatostudio.funny.ui.tema.Superficie
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta
import kotlinx.coroutines.delay
import kotlin.math.ceil

/**
 * Área táctil mínima. Cuarenta y ocho puntos es lo que piden las guías de
 * accesibilidad de Android y lo que exige el punto 4.8 de la plantilla; está
 * aquí como constante para que ninguna pantalla lo baje «solo un poquito».
 */
val AREA_TACTIL_MINIMA = 48.dp

/**
 * Fondo de todas las pantallas: un degradado vertical, con un tinte opcional del
 * color de lo que se esté jugando.
 *
 * El degradado va **a sangre**, hasta los bordes de la pantalla, y el contenido
 * va dentro de los márgenes seguros. Resolverlo aquí, en un único sitio, es lo
 * que evita tener que acordarse de los `insets` en cada una de las dieciocho
 * pantallas.
 */
@Composable
fun FondoFunny(
    modifier: Modifier = Modifier,
    tinte: Color? = null,
    contenido: @Composable BoxScope.() -> Unit,
) {
    val alto = FondoAlto
    val bajo = Fondo
    val arriba = tinte?.copy(alpha = 0.22f)?.sobre(alto) ?: alto
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(arriba, bajo, bajo))),
    ) {
        Box(
            modifier = Modifier.fillMaxSize().safeDrawingPadding(),
            content = contenido,
        )
    }
}

/** Compone este color, con su alfa, encima de otro opaco. */
private fun Color.sobre(fondo: Color): Color =
    Color(
        red = red * alpha + fondo.red * (1 - alpha),
        green = green * alpha + fondo.green * (1 - alpha),
        blue = blue * alpha + fondo.blue * (1 - alpha),
        alpha = 1f,
    )

/**
 * Botón principal de una pantalla.
 *
 * `onClick` va al final para que la forma corta con lambda —`BotonGrande(texto)
 * { ... }`— haga lo que parece. Con `onClick` en segunda posición, la lambda
 * final se ataba a `habilitado` y el compilador protestaba de una forma bastante
 * poco evidente.
 */
@Composable
fun BotonGrande(
    texto: String,
    modifier: Modifier = Modifier,
    color: Color = Primario,
    colorTexto: Color = SobrePrimario,
    habilitado: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().heightIn(min = 60.dp),
        enabled = habilitado,
        shape = RoundedCornerShape(18.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = colorTexto,
                disabledContainerColor = SuperficieAlta,
                disabledContentColor = TextoTenue,
            ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun BotonSuave(
    texto: String,
    modifier: Modifier = Modifier,
    color: Color = TextoTenue,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.heightIn(min = AREA_TACTIL_MINIMA),
    ) {
        Text(texto, color = color, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun Cabecera(
    titulo: String,
    modifier: Modifier = Modifier,
    subtitulo: String? = null,
    onVolver: (() -> Unit)? = null,
) {
    val t = textos()
    Column(modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp)) {
        if (onVolver != null) {
            TextButton(
                onClick = onVolver,
                modifier =
                    Modifier
                        .heightIn(min = AREA_TACTIL_MINIMA)
                        .semantics { contentDescription = t[Clave.A11Y_VOLVER] },
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            ) {
                Text(
                    "‹  ${t[Clave.ACCION_VOLVER]}",
                    color = TextoTenue,
                    style = MaterialTheme.typography.labelLarge,
                )
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
    contenido: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = color,
        shape = RoundedCornerShape(22.dp),
        border = borde?.let { BorderStroke(2.dp, it) },
        content = contenido,
    )
}

/** Título de sección dentro de una pantalla larga, como Ajustes. */
@Composable
fun TituloDeSeccion(texto: String, modifier: Modifier = Modifier) {
    Text(
        texto.uppercase(textos().locale),
        modifier = modifier.padding(start = 6.dp, top = 6.dp),
        style = MaterialTheme.typography.labelLarge,
        color = Primario,
    )
}

/** Pastilla con el emoji y el nombre de un juego. */
@Composable
fun PastillaJuego(juego: Juego, modifier: Modifier = Modifier) {
    val color = paleta().colorDe(juego)
    val nombre = textos().nombreDe(juego)
    Surface(
        modifier = modifier,
        color = color.copy(alpha = 0.18f),
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.5.dp, color.copy(alpha = 0.65f)),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(juego.emoji, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.width(8.dp))
            Text(
                nombre.uppercase(textos().locale),
                style = MaterialTheme.typography.labelLarge,
                color = color,
                maxLines = 1,
                softWrap = false,
            )
        }
    }
}

/** Círculo de color con un emoji dentro: la ficha de un equipo o jugador. */
@Composable
fun Ficha(color: Color, emoji: String, modifier: Modifier = Modifier, tamano: Int = 28) {
    Box(
        modifier =
            modifier
                .size(tamano.dp)
                .clip(CircleShape)
                .background(color),
        contentAlignment = Alignment.Center,
    ) {
        Text(emoji, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun SelectorSegmentado(
    opciones: List<String>,
    seleccion: Int,
    modifier: Modifier = Modifier,
    onSeleccion: (Int) -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SuperficieAlta)
                .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        opciones.forEachIndexed { indice, etiqueta ->
            val activo = indice == seleccion
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .heightIn(min = AREA_TACTIL_MINIMA)
                        .clip(RoundedCornerShape(13.dp))
                        .background(if (activo) Primario else Color.Transparent)
                        .clickable { onSeleccion(indice) }
                        .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    etiqueta,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (activo) SobrePrimario else TextoTenue,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

/** Fila de ajustes: título, detalle y algo a la derecha. */
@Composable
fun FilaAjuste(
    titulo: String,
    modifier: Modifier = Modifier,
    detalle: String? = null,
    onClick: (() -> Unit)? = null,
    derecha: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .heightIn(min = AREA_TACTIL_MINIMA)
                .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
                .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(titulo, style = MaterialTheme.typography.titleMedium, color = TextoFuerte)
            if (detalle != null) {
                Text(detalle, style = MaterialTheme.typography.bodyMedium, color = TextoTenue)
            }
        }
        if (derecha != null) {
            Spacer(Modifier.width(12.dp))
            derecha()
        }
    }
}

@Composable
fun FilaInterruptor(
    titulo: String,
    activo: Boolean,
    modifier: Modifier = Modifier,
    detalle: String? = null,
    onCambio: (Boolean) -> Unit,
) {
    FilaAjuste(
        titulo = titulo,
        detalle = detalle,
        modifier = modifier,
        onClick = { onCambio(!activo) },
        derecha = {
            Switch(
                checked = activo,
                onCheckedChange = onCambio,
                colors =
                    SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Exito,
                        uncheckedThumbColor = TextoTenue,
                        uncheckedTrackColor = SuperficieAlta,
                        uncheckedBorderColor = Contorno,
                        checkedBorderColor = Exito,
                    ),
            )
        },
    )
}

/** Insignia de idioma: bandera cuando la hay, código cuando no. Ver [Insignia]. */
@Composable
fun InsigniaDeIdioma(idioma: Idioma, modifier: Modifier = Modifier) {
    val t = textos()
    val descripcion = t.con(Clave.A11Y_BANDERA_IDIOMA, idioma.endonimo)
    Box(
        modifier =
            modifier
                .sizeIn(minWidth = 34.dp, minHeight = 26.dp)
                .semantics { contentDescription = descripcion },
        contentAlignment = Alignment.Center,
    ) {
        when (val insignia = idioma.insignia) {
            is Insignia.Bandera ->
                Text(
                    insignia.emoji,
                    style = MaterialTheme.typography.headlineMedium,
                )

            is Insignia.Codigo ->
                Box(
                    modifier =
                        Modifier
                            .clip(RoundedCornerShape(7.dp))
                            .background(SuperficieAlta)
                            .border(1.dp, Contorno, RoundedCornerShape(7.dp))
                            .padding(horizontal = 7.dp, vertical = 4.dp),
                ) {
                    Text(
                        insignia.texto,
                        style = MaterialTheme.typography.labelLarge,
                        color = TextoTenue,
                    )
                }
        }
    }
}

/** Banda redonda con un texto corto: «PRUEBA FINAL», «TURNO DE …». */
@Composable
fun Banda(texto: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.18f))
            .border(2.dp, color.copy(alpha = 0.6f), RoundedCornerShape(50))
            .padding(horizontal = 18.dp, vertical = 9.dp),
    ) {
        Text(texto, style = MaterialTheme.typography.labelLarge, color = color)
    }
}

@Composable
fun DialogoConfirmacion(
    titulo: String,
    texto: String,
    textoConfirmar: String,
    textoCancelar: String,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text(titulo, color = TextoFuerte) },
        text = { Text(texto, color = TextoTenue) },
        confirmButton = { BotonSuave(textoConfirmar, color = Fallo, onClick = onConfirmar) },
        dismissButton = { BotonSuave(textoCancelar, onClick = onCancelar) },
        containerColor = Superficie,
        titleContentColor = TextoFuerte,
        textContentColor = TextoTenue,
    )
}

/**
 * Cuenta atrás de la prueba. Pita en los últimos cinco segundos y avisa al
 * terminar. Si [enMarcha] pasa a false se congela donde estaba, que es lo que
 * hace falta cuando una prueba se cierra a mano antes de tiempo.
 */
@Composable
fun Cronometro(
    segundos: Int,
    enMarcha: Boolean,
    sonidos: Sonidos,
    modifier: Modifier = Modifier,
    onFin: () -> Unit,
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
    val color =
        when {
            progreso > 0.5f -> Exito
            progreso > 0.2f -> Acento
            else -> Fallo
        }
    val t = textos()

    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = enteros.toString(),
            style = MaterialTheme.typography.headlineLarge,
            color = color,
            modifier =
                Modifier.semantics {
                    contentDescription =
                        t.plural(
                            es.ghatostudio.funny.ui.i18n.ClavePlural.SEGUNDOS,
                            enteros,
                        )
                },
        )
        Spacer(Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { progreso },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clearAndSetSemantics { },
            color = color,
            trackColor = SuperficieAlta,
            strokeCap = StrokeCap.Round,
            drawStopIndicator = {},
        )
    }
}

/**
 * Aparición escalonada: cada elemento entra un poco después que el anterior,
 * subiendo unos puntos y ganando opacidad.
 *
 * Si las animaciones están apagadas —por el ajuste de la app o por la
 * preferencia de accesibilidad del sistema— aparece todo de golpe y sin
 * retardo, no una versión «más suave»: quien pide menos animación quiere
 * ninguna.
 */
@Composable
fun EntradaEscalonada(
    indice: Int,
    modifier: Modifier = Modifier,
    contenido: @Composable () -> Unit,
) {
    val animar = LocalAnimaciones.current
    var visible by remember(animar) { mutableStateOf(!animar) }

    LaunchedEffect(animar, indice) {
        if (!animar) return@LaunchedEffect
        delay(indice.toLong() * DESFASE_MS)
        visible = true
    }

    val avance by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = Spring.StiffnessMediumLow),
        label = "entradaEscalonada",
    )

    Box(
        modifier =
            modifier.graphicsLayer {
                alpha = avance
                translationY = (1f - avance) * DESPLAZAMIENTO_PX
            },
    ) {
        contenido()
    }
}

/** Cuarenta milisegundos entre elemento y elemento, como pide el punto 4.4.2. */
private const val DESFASE_MS = 40L

/** Cuánto sube cada elemento al entrar, en píxeles de la capa gráfica. */
private const val DESPLAZAMIENTO_PX = 28f

@Composable
fun FilaBotones(contenido: @Composable RowScope.() -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = contenido,
    )
}

@Composable
fun BotonPrueba(
    texto: String,
    color: Color,
    modifier: Modifier = Modifier,
    colorTexto: Color? = null,
    onClick: () -> Unit,
) {
    val tinta = colorTexto ?: paleta().textoSobre(color)
    Box(
        modifier =
            modifier
                .heightIn(min = 62.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(color)
                .clickable { onClick() }
                .padding(horizontal = 14.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            texto,
            style = MaterialTheme.typography.titleMedium,
            color = tinta,
            textAlign = TextAlign.Center,
        )
    }
}

/** Tarjeta central con la palabra o el enunciado de la prueba. */
@Composable
fun TarjetaPalabra(
    texto: String,
    color: Color,
    modifier: Modifier = Modifier,
    encabezado: String? = null,
    contenidoExtra: (@Composable ColumnScope.() -> Unit)? = null,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Superficie)
                .border(2.dp, color.copy(alpha = 0.45f), RoundedCornerShape(24.dp))
                .padding(22.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (encabezado != null) {
                Text(encabezado, style = MaterialTheme.typography.labelLarge, color = TextoTenue)
                Spacer(Modifier.height(10.dp))
            }
            Text(
                texto,
                style = MaterialTheme.typography.displayMedium,
                color = TextoFuerte,
                textAlign = TextAlign.Center,
            )
            contenidoExtra?.invoke(this)
        }
    }
}
