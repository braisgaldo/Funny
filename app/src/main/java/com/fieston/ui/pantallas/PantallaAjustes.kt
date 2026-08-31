package com.fieston.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.Categoria
import com.fieston.modelo.Longitud
import com.fieston.modelo.Pantalla
import com.fieston.modelo.Ritmo
import com.fieston.ui.comun.Cabecera
import com.fieston.ui.comun.FondoFiesta
import com.fieston.ui.comun.SelectorSegmentado
import com.fieston.ui.comun.Tarjeta
import com.fieston.ui.tema.Exito
import com.fieston.ui.tema.Primario
import com.fieston.ui.tema.SuperficieAlta
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue

@Composable
fun PantallaAjustes(vm: JuegoViewModel) {
    val estado = vm.estado
    val ajustes = estado.ajustes

    FondoFiesta {
        Column(Modifier.fillMaxSize()) {
            Cabecera(
                titulo = "Ajustes",
                subtitulo = "Se guardan para la próxima partida.",
                onVolver = { vm.ir(Pantalla.INICIO) }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Tarjeta {
                    Column(Modifier.padding(18.dp)) {
                        Text("Ritmo de las pruebas", style = MaterialTheme.typography.titleMedium, color = TextoFuerte)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Cuánto tiempo hay para cada prueba.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextoTenue
                        )
                        Spacer(Modifier.height(12.dp))
                        SelectorSegmentado(
                            opciones = Ritmo.entries.map { it.etiqueta },
                            seleccion = Ritmo.entries.indexOf(ajustes.ritmo)
                        ) { indice ->
                            vm.actualizarAjustes(ajustes.copy(ritmo = Ritmo.entries[indice]))
                        }
                        Spacer(Modifier.height(14.dp))
                        Categoria.entries.forEach { categoria ->
                            Row(
                                Modifier.fillMaxWidth().padding(vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "${categoria.emoji}  ${categoria.etiqueta}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextoTenue,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "${estado.segundosDe(categoria)} s",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = categoria.color
                                )
                            }
                        }
                    }
                }

                Tarjeta {
                    Column(Modifier.padding(18.dp)) {
                        Text("Duración de la partida", style = MaterialTheme.typography.titleMedium, color = TextoFuerte)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "${ajustes.longitud.casillas} casillas hasta la meta · ${ajustes.longitud.detalle}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextoTenue
                        )
                        Spacer(Modifier.height(12.dp))
                        SelectorSegmentado(
                            opciones = Longitud.entries.map { it.etiqueta },
                            seleccion = Longitud.entries.indexOf(ajustes.longitud)
                        ) { indice ->
                            vm.actualizarAjustes(ajustes.copy(longitud = Longitud.entries[indice]))
                        }
                    }
                }

                Tarjeta {
                    Column(Modifier.padding(18.dp)) {
                        FilaInterruptor(
                            titulo = "Sonido",
                            detalle = "Pitidos de la cuenta atrás y avisos",
                            activo = ajustes.sonido
                        ) { vm.actualizarAjustes(ajustes.copy(sonido = it)) }
                        Spacer(Modifier.height(10.dp))
                        FilaInterruptor(
                            titulo = "Vibración",
                            detalle = "Aciertos, fallos y fin de tiempo",
                            activo = ajustes.vibracion
                        ) { vm.actualizarAjustes(ajustes.copy(vibracion = it)) }
                    }
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun FilaInterruptor(
    titulo: String,
    detalle: String,
    activo: Boolean,
    onCambio: (Boolean) -> Unit
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(titulo, style = MaterialTheme.typography.titleMedium, color = TextoFuerte)
            Text(detalle, style = MaterialTheme.typography.bodyMedium, color = TextoTenue)
        }
        Switch(
            checked = activo,
            onCheckedChange = onCambio,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Exito,
                uncheckedThumbColor = TextoTenue,
                uncheckedTrackColor = SuperficieAlta,
                uncheckedBorderColor = SuperficieAlta,
                checkedBorderColor = Exito
            )
        )
    }
}

@Composable
fun PantallaComoJugar(vm: JuegoViewModel) {
    FondoFiesta {
        Column(Modifier.fillMaxSize()) {
            Cabecera(
                titulo = "Cómo se juega",
                subtitulo = "Un móvil, varios equipos y mucho ruido.",
                onVolver = { vm.ir(Pantalla.INICIO) }
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Tarjeta {
                    Column(Modifier.padding(18.dp)) {
                        Text("La partida", style = MaterialTheme.typography.titleLarge, color = Primario)
                        Spacer(Modifier.height(8.dp))
                        Punto("Formad de 2 a 6 equipos y apuntad los nombres de quienes juegan.")
                        Punto("Cada equipo tiene una ficha en el tablero. Todos salen de la casilla de SALIDA.")
                        Punto("En su turno, el equipo tira el dado y avanza de 1 a 3 casillas.")
                        Punto("La casilla a la que llega decide la prueba que hay que superar.")
                        Punto("Si la superan, se quedan en esa casilla. Si fallan, vuelven a donde estaban.")
                        Punto("Gana el primer equipo que llegue a la META y supere la PRUEBA FINAL.")
                    }
                }

                Tarjeta {
                    Column(Modifier.padding(18.dp)) {
                        Text("Casillas especiales", style = MaterialTheme.typography.titleLarge, color = Primario)
                        Spacer(Modifier.height(8.dp))
                        Punto("🃏  COMODÍN: el equipo rival elige qué prueba te toca. Sin piedad.")
                        Punto("👥  TODOS JUEGAN: una pregunta para toda la mesa. Cada equipo que acierte avanza una casilla.")
                        Punto("🏁  META: prueba final al azar. Solo se gana superándola.")
                    }
                }

                Tarjeta {
                    Column(Modifier.padding(18.dp)) {
                        Text("Las pruebas", style = MaterialTheme.typography.titleLarge, color = Primario)
                        Spacer(Modifier.height(10.dp))
                        Categoria.entries.forEach { categoria ->
                            Row(Modifier.fillMaxWidth().padding(vertical = 7.dp)) {
                                Text(categoria.emoji, style = MaterialTheme.typography.headlineMedium)
                                Spacer(Modifier.height(0.dp))
                                Column(Modifier.padding(start = 12.dp)) {
                                    Text(
                                        categoria.etiqueta,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = categoria.color
                                    )
                                    Text(
                                        categoria.instrucciones,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextoTenue
                                    )
                                }
                            }
                        }
                    }
                }

                Tarjeta {
                    Column(Modifier.padding(18.dp)) {
                        Text("Con un solo móvil", style = MaterialTheme.typography.titleLarge, color = Primario)
                        Spacer(Modifier.height(8.dp))
                        Punto("El móvil va pasando de mano en mano: la app siempre avisa a quién le toca.")
                        Punto("En mímica, tabú y dibujo, solo quien actúa debe mirar la pantalla.")
                        Punto("En las preguntas y en el '¿Cuándo?', la pantalla se puede enseñar a todos.")
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun Punto(texto: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text("•  ", style = MaterialTheme.typography.bodyLarge, color = TextoTenue)
        Text(texto, style = MaterialTheme.typography.bodyLarge, color = TextoTenue)
    }
}
