package es.ghatostudio.funny.ui.pantallas

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.Participante
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.AREA_TACTIL_MINIMA
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.Cabecera
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.SelectorDeModalidad
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Fallo
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

private const val MAXIMO_NOMBRE = 18
private const val MAXIMO_NOMBRE_MIEMBRO = 14

/**
 * Alta de equipos o de jugadores, según el modo. En solitario se reduce a
 * pedir un nombre, porque no hay nadie más.
 */
@Composable
fun PantallaParticipantes(vm: JuegoViewModel) {
    val t = textos()
    val estado = vm.estado
    val modo = estado.modo

    FondoFunny {
        Column(Modifier.fillMaxSize()) {
            Cabecera(
                titulo =
                    when (modo) {
                        Modo.EQUIPOS -> t[Clave.PARTICIPANTES_TITULO_EQUIPOS]
                        Modo.INDIVIDUAL -> t[Clave.PARTICIPANTES_TITULO_INDIVIDUAL]
                        Modo.SOLITARIO -> t[Clave.PARTICIPANTES_TITULO_SOLITARIO]
                    },
                subtitulo =
                    when (modo) {
                        Modo.EQUIPOS ->
                            t.con(
                                Clave.PARTICIPANTES_SUBTITULO_EQUIPOS,
                                modo.minimoParticipantes,
                                modo.maximoParticipantes,
                            )

                        Modo.INDIVIDUAL ->
                            t.con(
                                Clave.PARTICIPANTES_SUBTITULO_INDIVIDUAL,
                                modo.minimoParticipantes,
                                modo.maximoParticipantes,
                            )

                        Modo.SOLITARIO -> t[Clave.PARTICIPANTES_SUBTITULO_SOLITARIO]
                    },
                onVolver = { vm.ir(Pantalla.MODO) },
            )

            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                estado.participantes.forEachIndexed { indice, participante ->
                    TarjetaParticipante(
                        participante = participante,
                        numero = indice + 1,
                        modo = modo,
                        sePuedeBorrar = estado.participantes.size > modo.minimoParticipantes,
                        onNombre = { vm.renombrarParticipante(participante.id, it) },
                        onBorrar = { vm.eliminarParticipante(participante.id) },
                        onNuevoMiembro = { vm.anadirMiembro(participante.id, it) },
                        onBorrarMiembro = { vm.eliminarMiembro(participante.id, it) },
                    )
                }

                if (estado.participantes.size < modo.maximoParticipantes) {
                    BotonGrande(
                        texto =
                            if (modo == Modo.EQUIPOS) {
                                t[Clave.PARTICIPANTES_ANADIR_EQUIPO]
                            } else {
                                t[Clave.PARTICIPANTES_ANADIR_JUGADOR]
                            },
                        onClick = { vm.anadirParticipante() },
                        color = SuperficieAlta,
                        colorTexto = TextoFuerte,
                    )
                }
                // La modalidad se puede cambiar aqui mismo, justo antes de
                // empezar: es donde de verdad se decide cuanto va a durar la
                // partida, y mandar a la gente a los ajustes para eso es un
                // viaje de ida y vuelta que no hace falta.
                Tarjeta {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            t[Clave.AJUSTES_MODALIDAD],
                            style = MaterialTheme.typography.titleSmall,
                            color = TextoFuerte,
                        )
                        Spacer(Modifier.height(10.dp))
                        SelectorDeModalidad(ajustes = estado.ajustes, compacto = true) {
                            vm.actualizarAjustes(it)
                        }
                    }
                }
                Spacer(Modifier.height(4.dp))
            }

            Column(Modifier.padding(20.dp)) {
                BotonGrande(
                    texto =
                        if (modo == Modo.SOLITARIO) {
                            t[Clave.SOLITARIO_EMPEZAR]
                        } else {
                            t[Clave.ACCION_EMPEZAR]
                        },
                    onClick = { vm.empezarPartida() },
                    habilitado = estado.participantes.size >= modo.minimoParticipantes,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TarjetaParticipante(
    participante: Participante,
    numero: Int,
    modo: Modo,
    sePuedeBorrar: Boolean,
    onNombre: (String) -> Unit,
    onBorrar: () -> Unit,
    onNuevoMiembro: (String) -> Unit,
    onBorrarMiembro: (Int) -> Unit,
) {
    val t = textos()
    val color = paleta().colorDeParticipante(participante.indiceColor)
    var nuevoMiembro by remember(participante.id) { mutableStateOf("") }
    val marcador = nombrePorDefecto(t, numero, modo)

    Tarjeta(borde = color.copy(alpha = 0.45f)) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(44.dp).clip(CircleShape).background(color),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(participante.emoji, style = MaterialTheme.typography.titleLarge)
                }
                Spacer(Modifier.width(12.dp))
                OutlinedTextField(
                    value = participante.nombre,
                    onValueChange = { if (it.length <= MAXIMO_NOMBRE) onNombre(it) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    // El hueco muestra el nombre por defecto traducido: mientras
                    // nadie escriba nada, el equipo se llama «Los Cracks» en
                    // castellano y «The Aces» en inglés.
                    placeholder = {
                        Text(
                            marcador,
                            color = TextoTenue,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    },
                    label = {
                        Text(
                            if (modo == Modo.EQUIPOS) {
                                t[Clave.PARTICIPANTES_NOMBRE_EQUIPO]
                            } else {
                                t[Clave.PARTICIPANTES_NOMBRE_JUGADOR]
                            },
                            color = TextoTenue,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    textStyle = MaterialTheme.typography.titleMedium,
                    colors = coloresCampo(color),
                )
                if (sePuedeBorrar) {
                    Spacer(Modifier.width(6.dp))
                    Box(
                        modifier =
                            Modifier
                                .size(AREA_TACTIL_MINIMA)
                                .clip(CircleShape)
                                .background(Fallo.copy(alpha = 0.15f))
                                .clickable { onBorrar() }
                                .semantics { contentDescription = t[Clave.PARTICIPANTES_QUITAR] },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("✕", color = Fallo, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            // Los miembros solo tienen sentido en un equipo: en individual y en
            // solitario el participante ya es una persona.
            if (modo != Modo.EQUIPOS) return@Column

            Spacer(Modifier.height(12.dp))

            if (participante.miembros.isEmpty()) {
                Text(
                    t[Clave.PARTICIPANTES_SIN_JUGADORES],
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoTenue,
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    participante.miembros.forEachIndexed { indice, miembro ->
                        Row(
                            modifier =
                                Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(color.copy(alpha = 0.18f))
                                    .clickable { onBorrarMiembro(indice) }
                                    .heightIn(min = 40.dp)
                                    .padding(horizontal = 12.dp, vertical = 9.dp)
                                    .semantics {
                                        contentDescription =
                                            "${t[Clave.PARTICIPANTES_QUITAR]} $miembro"
                                    },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                miembro,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextoFuerte,
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "✕",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextoTenue,
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = nuevoMiembro,
                    onValueChange = { if (it.length <= MAXIMO_NOMBRE_MIEMBRO) nuevoMiembro = it },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = {
                        Text(
                            t[Clave.PARTICIPANTES_NUEVO_JUGADOR],
                            color = TextoTenue,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions =
                        KeyboardActions(
                            onDone = {
                                onNuevoMiembro(nuevoMiembro)
                                nuevoMiembro = ""
                            },
                        ),
                    colors = coloresCampo(color),
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier =
                        Modifier
                            .size(AREA_TACTIL_MINIMA)
                            .clip(CircleShape)
                            .background(if (nuevoMiembro.isBlank()) SuperficieAlta else color)
                            .clickable(enabled = nuevoMiembro.isNotBlank()) {
                                onNuevoMiembro(nuevoMiembro)
                                nuevoMiembro = ""
                            }.semantics { contentDescription = t[Clave.ACCION_ANADIR] },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "+",
                        style = MaterialTheme.typography.headlineMedium,
                        color =
                            if (nuevoMiembro.isBlank()) {
                                TextoTenue
                            } else {
                                paleta().textoSobre(color)
                            },
                    )
                }
            }
        }
    }
}

@Composable
private fun coloresCampo(color: Color) =
    OutlinedTextFieldDefaults.colors(
        focusedTextColor = TextoFuerte,
        unfocusedTextColor = TextoFuerte,
        focusedBorderColor = color,
        unfocusedBorderColor = SuperficieAlta,
        cursorColor = Primario,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
    )
