package es.ghatostudio.funny.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.Banda
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.EntradaEscalonada
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Acento
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

/**
 * Pantalla de entrega: la que se ve justo antes de empezar una prueba.
 *
 * Cumple dos funciones que parecen una sola pero no lo son: decir qué prueba
 * toca y, cuando la prueba es de actuar, decir **quién** actúa para que el
 * móvil llegue a la persona correcta antes de que aparezca la palabra secreta.
 */
@Composable
fun PantallaEntrega(vm: JuegoViewModel) {
    val t = textos()
    val p = paleta()
    val estado = vm.estado
    val juego = estado.juego ?: return
    val color = p.colorDe(juego)
    val esRondaDeTodos = estado.esRondaDeTodos
    val esSolitario = estado.modo == Modo.SOLITARIO
    val participante = estado.participanteActivo
    val nombreActivo = nombreDelActivo(estado)

    FondoFunny(tinte = color) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            EntradaEscalonada(0) {
                when {
                    esSolitario ->
                        Banda(
                            t.con(
                                Clave.SOLITARIO_PROGRESO,
                                estado.rondaSolitario,
                                estado.rondasSolitario,
                            ),
                            Acento,
                        )

                    estado.esPruebaFinal -> Banda(t[Clave.PRUEBA_FINAL], p.primario)
                    esRondaDeTodos -> Banda(t[Clave.PRUEBA_JUEGAN_TODOS], p.casillaTodos)
                    else ->
                        Banda(
                            t.con(Clave.TABLERO_TURNO_DE, nombreActivo),
                            p.colorDeParticipante(participante?.indiceColor ?: 0),
                        )
                }
            }

            Spacer(Modifier.height(22.dp))

            EntradaEscalonada(1) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(juego.emoji, style = MaterialTheme.typography.displayLarge)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        t.nombreDe(juego).uppercase(t.locale),
                        style = MaterialTheme.typography.headlineLarge,
                        color = color,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        t.lemaDe(juego),
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoTenue,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            // Quién actúa. En solitario no hace falta —solo hay una persona— y
            // en una ronda de todos tampoco, porque juega la mesa entera.
            if (juego.soloActuante && !esRondaDeTodos && !esSolitario && participante != null) {
                EntradaEscalonada(2) {
                    Tarjeta(
                        modifier = Modifier.fillMaxWidth(),
                        borde = Acento.copy(alpha = 0.55f),
                    ) {
                        Column(
                            Modifier.fillMaxWidth().padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                t[Clave.PRUEBA_LE_TOCA_ACTUAR_A],
                                style = MaterialTheme.typography.labelLarge,
                                color = TextoTenue,
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                participante.quienActua
                                    ?: t.con(Clave.PRUEBA_QUIEN_DECIDA, nombreActivo),
                                style = MaterialTheme.typography.headlineMedium,
                                color = Acento,
                                textAlign = TextAlign.Center,
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                t[Clave.PRUEBA_SOLO_MIRE_ESA_PERSONA],
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextoTenue,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            if (esRondaDeTodos) {
                EntradaEscalonada(2) {
                    Tarjeta(modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.fillMaxWidth().padding(18.dp)) {
                            Text(
                                t[Clave.CASILLA_TODOS_DETALLE],
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextoFuerte,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            EntradaEscalonada(3) {
                Tarjeta(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.fillMaxWidth().padding(18.dp)) {
                        Text(
                            t.instruccionesDe(juego),
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextoTenue,
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            t.con(Clave.PRUEBA_SEGUNDOS, estado.segundosDe(juego)),
                            style = MaterialTheme.typography.titleMedium,
                            color = color,
                        )
                    }
                }
            }

            Spacer(Modifier.height(26.dp))

            EntradaEscalonada(4) {
                BotonGrande(
                    texto = t[Clave.ACCION_EMPEZAR],
                    onClick = { vm.empezarPrueba() },
                    color = color,
                    colorTexto = p.textoSobre(color),
                )
            }
        }
    }
}
