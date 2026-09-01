package es.ghatostudio.funny.ui.donacion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.BuildConfig
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sistema
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.AREA_TACTIL_MINIMA
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.BotonSuave
import es.ghatostudio.funny.ui.comun.CodigoQr
import es.ghatostudio.funny.ui.comun.EntradaEscalonada
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Acento
import es.ghatostudio.funny.ui.tema.Contorno
import es.ghatostudio.funny.ui.tema.LocalAnimaciones
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.Superficie
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

/**
 * «Invítame a un café».
 *
 * Es un bottom sheet modal, no un diálogo ni una pantalla completa, y no
 * desbloquea absolutamente nada: ni funciones, ni temas, ni contenido. Eso es
 * justo lo que la mantiene fuera de la facturación obligatoria de las tiendas
 * —no se compra un bien digital, se agradece algo que ya es gratis— y por eso
 * no hay ninguna librería de pagos en el proyecto. Ver ADR-0004.
 *
 * El enlace se abre en el navegador del sistema con Custom Tabs, nunca en un
 * WebView embebido: un formulario de pago dentro de la app es exactamente lo
 * que las revisiones miran con lupa.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HojaCafe(vm: JuegoViewModel, sonidos: Sonidos) {
    val t = textos()
    val p = paleta()
    val contexto = LocalContext.current
    val estadoHoja: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var qrVisible by remember { mutableStateOf(false) }
    var mensajeCopiado by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = { vm.cerrarHojaCafe() },
        sheetState = estadoHoja,
        containerColor = Superficie,
        contentColor = TextoFuerte,
        dragHandle = null,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            EntradaEscalonada(0) { TazaDeCafe() }

            Spacer(Modifier.height(18.dp))

            EntradaEscalonada(1) {
                Text(
                    t[Clave.CAFE_TITULO],
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextoFuerte,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(10.dp))

            EntradaEscalonada(2) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        t[Clave.CAFE_TEXTO],
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoTenue,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        t[Clave.CAFE_SIN_DESBLOQUEOS],
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoTenue,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            EntradaEscalonada(3) {
                BotonGrande(
                    texto = "☕   ${t[Clave.CAFE_BOTON]}",
                    onClick = {
                        sonidos.caricia()
                        val abierto =
                            Sistema.abrirEnNavegador(
                                contexto,
                                BuildConfig.ENLACE_DONACION,
                            )
                        if (abierto) {
                            vm.vueltaDelNavegadorDeDonacion()
                            vm.avisar(t[Clave.CAFE_GRACIAS])
                            vm.cerrarHojaCafe()
                        } else {
                            vm.avisar(t[Clave.CAFE_NO_DISPONIBLE])
                        }
                    },
                    color = Primario,
                    colorTexto = p.sobrePrimario,
                )
            }

            Spacer(Modifier.height(6.dp))

            // Acciones secundarias: mismo peso visual entre sí y mucho menor
            // que el botón principal. Nada de esconder «no volver a mostrar».
            EntradaEscalonada(4) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    BotonSuave(t[Clave.ACCION_AHORA_NO]) { vm.cerrarHojaCafe() }
                    BotonSuave(t[Clave.CAFE_NO_VOLVER]) { vm.noVolverAOfrecerCafe() }
                }
            }

            Spacer(Modifier.height(8.dp))

            EntradaEscalonada(5) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable { qrVisible = !qrVisible }
                            .heightIn(min = AREA_TACTIL_MINIMA)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            t[Clave.CAFE_OTRO_DISPOSITIVO],
                            style = MaterialTheme.typography.labelLarge,
                            color = TextoTenue,
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            if (qrVisible) "▴" else "▾",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextoTenue,
                        )
                    }

                    AnimatedVisibility(visible = qrVisible) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(Modifier.height(8.dp))
                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(SuperficieAlta)
                                    .border(1.dp, Contorno, RoundedCornerShape(18.dp))
                                    .padding(12.dp),
                            ) {
                                CodigoQr(
                                    datos = BuildConfig.ENLACE_DONACION,
                                    descripcion = t[Clave.CAFE_QR_DESCRIPCION],
                                )
                            }
                            Spacer(Modifier.height(10.dp))
                            BotonSuave(
                                texto =
                                    if (mensajeCopiado) {
                                        t[Clave.CAFE_ENLACE_COPIADO]
                                    } else {
                                        t[Clave.ACCION_COPIAR]
                                    },
                                color = if (mensajeCopiado) Acento else TextoTenue,
                            ) {
                                val copiado =
                                    Sistema.copiarAlPortapapeles(
                                        contexto,
                                        t[Clave.CAFE_TITULO],
                                        BuildConfig.ENLACE_DONACION,
                                    )
                                if (copiado) {
                                    mensajeCopiado = true
                                    sonidos.toque()
                                    vm.avisar(t[Clave.CAFE_ENLACE_COPIADO])
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(10.dp))
        }
    }
}

/**
 * Taza de café dibujada a mano en Compose, con vapor que ondula.
 *
 * Es un dibujo vectorial y no un PNG ni un emoji por una razón concreta: así se
 * colorea con los tokens del tema activo y cambia con él. Un PNG se quedaría
 * con los colores de un tema y se vería mal en los otros cinco.
 *
 * El vapor respeta la preferencia de reducir animaciones: si está apagada, los
 * trazos se dibujan quietos en su posición central.
 */
@Composable
private fun TazaDeCafe() {
    val t = textos()
    val p = paleta()
    val animar = LocalAnimaciones.current

    val transicion = rememberInfiniteTransition(label = "vapor")
    val fase by transicion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = CICLO_VAPOR_MS),
                repeatMode = RepeatMode.Restart,
            ),
        label = "faseVapor",
    )
    val avance = if (animar) fase else 0.5f

    Canvas(
        Modifier
            .size(128.dp)
            .semantics { contentDescription = t[Clave.CAFE_ILUSTRACION_DESCRIPCION] },
    ) {
        val ancho = size.width
        val alto = size.height
        val grosor = ancho * 0.055f

        // --- vapor: tres trazos que ondulan con desfase entre ellos ---
        val trazos = 3
        repeat(trazos) { indice ->
            val x = ancho * (0.36f + indice * 0.14f)
            val desfase = indice * 0.33f
            val amplitud = ancho * 0.045f
            val camino = Path()
            val alturaVapor = alto * 0.30f
            val base = alto * 0.34f
            val pasos = 12
            for (paso in 0..pasos) {
                val fraccion = paso / pasos.toFloat()
                val y = base - alturaVapor * fraccion
                val onda =
                    kotlin.math.sin(
                        ((fraccion + avance + desfase) * 2f * Math.PI).toFloat(),
                    )
                val puntoX = x + onda * amplitud * fraccion
                if (paso == 0) camino.moveTo(puntoX, y) else camino.lineTo(puntoX, y)
            }
            drawPath(
                path = camino,
                color = p.textoTenue.copy(alpha = 0.55f - indice * 0.12f),
                style = Stroke(width = grosor * 0.6f, cap = StrokeCap.Round),
            )
        }

        // --- platillo ---
        drawLine(
            color = p.contorno,
            start = Offset(ancho * 0.12f, alto * 0.90f),
            end = Offset(ancho * 0.88f, alto * 0.90f),
            strokeWidth = grosor,
            cap = StrokeCap.Round,
        )

        // --- cuerpo de la taza: un trapecio redondeado dibujado a mano ---
        val izquierda = ancho * 0.24f
        val derecha = ancho * 0.70f
        val arriba = alto * 0.42f
        val abajo = alto * 0.84f
        val taza =
            Path().apply {
                moveTo(izquierda, arriba)
                lineTo(derecha, arriba)
                lineTo(derecha - ancho * 0.045f, abajo)
                quadraticBezierTo(
                    (izquierda + derecha) / 2f,
                    abajo + alto * 0.06f,
                    izquierda + ancho * 0.045f,
                    abajo,
                )
                close()
            }
        drawPath(path = taza, color = p.primario.copy(alpha = 0.22f))
        drawPath(
            path = taza,
            color = p.primario,
            style = Stroke(width = grosor, cap = StrokeCap.Round),
        )

        // --- café dentro ---
        drawLine(
            color = p.acento,
            start = Offset(izquierda + grosor, arriba + grosor * 0.9f),
            end = Offset(derecha - grosor, arriba + grosor * 0.9f),
            strokeWidth = grosor * 1.1f,
            cap = StrokeCap.Round,
        )

        // --- asa ---
        val asa =
            Path().apply {
                moveTo(derecha, alto * 0.52f)
                quadraticBezierTo(ancho * 0.92f, alto * 0.60f, derecha, alto * 0.70f)
            }
        drawPath(
            path = asa,
            color = p.primario,
            style = Stroke(width = grosor, cap = StrokeCap.Round),
        )
    }
}

/** Tres segundos de ciclo: se nota que se mueve sin llamar la atención. */
private const val CICLO_VAPOR_MS = 3000
