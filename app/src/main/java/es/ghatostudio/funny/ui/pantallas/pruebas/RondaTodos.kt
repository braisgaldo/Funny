package es.ghatostudio.funny.ui.pantallas.pruebas

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Prueba
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.BotonPrueba
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.Textos
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.pantallas.nombreEnIndice
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Fallo
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

/** Lo que la ronda necesita saber de la prueba, sea del tipo que sea. */
private data class DatosRonda(
    val enunciado: String,
    val tema: String?,
    val opciones: List<String>,
    val correcta: Int,
    val aclaracion: String?
)

/**
 * Casilla «juegan todos»: la misma pregunta para cada participante, uno detrás
 * de otro, sin revelar nada hasta que han contestado todos.
 *
 * Solo admite pruebas que la app pueda verificar por separado para cada
 * participante —las de opciones—, y de eso se encarga `Juego.PARA_RONDA_DE_TODOS`
 * al elegir el juego. Si llega otra, la pantalla no pinta nada en lugar de
 * inventarse una interfaz a medias.
 */
@Composable
fun PantallaRondaTodos(vm: JuegoViewModel, sonidos: Sonidos) {
    val t = textos()
    val p = paleta()
    val estado = vm.estado
    val juego = estado.juego ?: return
    val datos = datosDeLaPrueba(estado.prueba, t) ?: return
    val color = p.colorDe(juego)

    var indiceParticipante by remember { mutableIntStateOf(0) }
    var listo by remember { mutableStateOf(false) }
    var elegida by remember { mutableStateOf<Int?>(null) }
    var agotado by remember { mutableStateOf(false) }
    var finalizada by remember { mutableStateOf(false) }
    val respuestas = remember { mutableStateListOf<Int>() }

    val participante = estado.participantes.getOrNull(indiceParticipante)
    val nombre = nombreEnIndice(estado, indiceParticipante)

    fun avanzar() {
        respuestas.add(elegida ?: SIN_RESPUESTA)
        elegida = null
        agotado = false
        if (indiceParticipante + 1 >= estado.participantes.size) {
            finalizada = true
        } else {
            indiceParticipante++
            listo = false
        }
    }

    when {
        finalizada -> ResumenDeLaRonda(vm, datos, respuestas.toList(), sonidos)

        participante == null -> Unit

        // Pantalla de traspaso: el móvil cambia de manos y hasta que no se
        // confirma no aparece la pregunta, para que nadie la lea de reojo.
        !listo -> {
            val colorParticipante = p.colorDeParticipante(participante.indiceColor)
            FondoFunny(tinte = colorParticipante) {
                Column(
                    Modifier.fillMaxSize().padding(26.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("📱", style = MaterialTheme.typography.displayLarge)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        t[Clave.RONDA_TODOS_PASAD_A],
                        style = MaterialTheme.typography.labelLarge,
                        color = TextoTenue
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "${participante.emoji}  $nombre",
                        style = MaterialTheme.typography.displayMedium,
                        color = colorParticipante,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(14.dp))
                    Text(
                        t.con(
                            Clave.RONDA_TODOS_PROGRESO,
                            indiceParticipante + 1,
                            estado.participantes.size
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoTenue,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(30.dp))
                    BotonGrande(
                        texto = t[Clave.ACCION_LISTO],
                        onClick = { listo = true },
                        color = colorParticipante,
                        colorTexto = p.textoSobre(colorParticipante)
                    )
                }
            }
        }

        // `key` reinicia el cronómetro del marco para cada participante.
        else -> key(indiceParticipante) {
            MarcoPrueba(
                juego = juego,
                segundos = estado.segundosDe(juego),
                enMarcha = elegida == null && !agotado,
                sonidos = sonidos,
                marcador = "${participante.emoji} $nombre",
                onTiempoAgotado = { agotado = true }
            ) {
                Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                    BloqueOpciones(
                        enunciado = datos.enunciado,
                        tema = datos.tema,
                        opciones = datos.opciones,
                        correcta = datos.correcta,
                        elegida = elegida,
                        // Nunca se revela aquí: el suspense hasta el final es
                        // media gracia de esta casilla.
                        revelar = false,
                        color = color
                    ) { indice ->
                        sonidos.toque()
                        elegida = indice
                    }
                }

                if (elegida != null || agotado) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        if (agotado && elegida == null) {
                            t[Clave.RONDA_TODOS_SIN_RESPUESTA]
                        } else {
                            t[Clave.RONDA_TODOS_GUARDADA]
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoTenue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    BotonPrueba(
                        texto = if (indiceParticipante + 1 >= estado.participantes.size) {
                            t[Clave.RONDA_TODOS_VER_RESULTADOS]
                        } else {
                            t[Clave.ACCION_CONTINUAR]
                        },
                        color = color,
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
private fun datosDeLaPrueba(prueba: Prueba?, t: Textos): DatosRonda? = when (prueba) {
    is Prueba.DeCuando -> DatosRonda(
        enunciado = prueba.evento.texto,
        tema = prueba.evento.tema.ifBlank { t[Clave.PRUEBA_CUANDO_TEMA] },
        opciones = prueba.opciones.map { it.toString() },
        correcta = prueba.opciones.indexOf(prueba.evento.anio).coerceAtLeast(0),
        aclaracion = t.con(Clave.PRUEBA_CUANDO_RESPUESTA, prueba.evento.anio)
    )

    is Prueba.DePreguntas -> DatosRonda(
        enunciado = prueba.pregunta.texto,
        tema = prueba.pregunta.tema,
        opciones = prueba.pregunta.opciones,
        correcta = prueba.pregunta.correcta,
        aclaracion = null
    )

    is Prueba.DeEmojis -> DatosRonda(
        enunciado = "${prueba.carta.emojis}\n\n${t[Clave.PRUEBA_EMOJIS_AYUDA]}",
        tema = prueba.carta.tipo,
        opciones = prueba.opciones,
        correcta = prueba.correcta,
        aclaracion = t.con(Clave.PRUEBA_EMOJIS_ERA, prueba.carta.respuesta)
    )

    else -> null
}

@Composable
private fun ResumenDeLaRonda(
    vm: JuegoViewModel,
    datos: DatosRonda,
    respuestas: List<Int>,
    sonidos: Sonidos
) {
    val t = textos()
    val p = paleta()
    val estado = vm.estado

    FondoFunny(tinte = Exito) {
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
                t[Clave.RONDA_TODOS_CORRECTA_ERA],
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
                    estado.participantes.forEachIndexed { indice, participante ->
                        val respuesta = respuestas.getOrElse(indice) { SIN_RESPUESTA }
                        val acierto = respuesta == datos.correcta
                        Row(
                            Modifier.fillMaxWidth().padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(participante.emoji, style = MaterialTheme.typography.titleLarge)
                            Spacer(Modifier.width(10.dp))
                            Column(Modifier.weight(1f)) {
                                Text(
                                    nombreEnIndice(estado, indice),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = p.colorDeParticipante(participante.indiceColor)
                                )
                                Text(
                                    if (respuesta >= 0) {
                                        datos.opciones.getOrElse(respuesta) { "—" }
                                    } else {
                                        t[Clave.RONDA_TODOS_SIN_RESPONDER]
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
                texto = t[Clave.ACCION_CONTINUAR],
                onClick = {
                    sonidos.toque()
                    vm.resolverRondaDeTodos(
                        estado.participantes.indices.map {
                            respuestas.getOrElse(it) { SIN_RESPUESTA } == datos.correcta
                        }
                    )
                },
                color = Exito,
                colorTexto = p.textoSobre(Exito)
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}

/** Marca de «no contestó»: cualquier índice negativo vale, pero explícito mejor. */
private const val SIN_RESPUESTA = -1
