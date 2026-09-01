package es.ghatostudio.funny.ui.pantallas.pruebas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.CartaTabu
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.BotonPrueba
import es.ghatostudio.funny.ui.comun.BotonSuave
import es.ghatostudio.funny.ui.comun.FilaBotones
import es.ghatostudio.funny.ui.comun.TarjetaPalabra
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Fallo
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.paleta

/**
 * Mímica: pasan palabras y se cuenta cuántas se aciertan.
 *
 * La prueba se supera con **un solo acierto**, pero el contador sigue subiendo:
 * los aciertos van a los puntos del participante y desempatan la clasificación,
 * así que esforzarse de más no se queda sin premio.
 */
@Composable
fun PruebaMimica(vm: JuegoViewModel, palabras: List<String>, sonidos: Sonidos) {
    val t = textos()
    val juego = Juego.MIMICA
    val color = paleta().colorDe(juego)
    var indice by remember { mutableIntStateOf(0) }
    var aciertos by remember { mutableIntStateOf(0) }
    var terminada by remember { mutableStateOf(false) }

    fun cerrar() {
        if (terminada) return
        terminada = true
        vm.resolverPrueba(superada = aciertos > 0, puntos = aciertos)
    }

    LaunchedEffect(indice) {
        if (indice >= palabras.size) cerrar()
    }

    MarcoPrueba(
        juego = juego,
        segundos = vm.estado.segundosDe(juego),
        enMarcha = !terminada,
        sonidos = sonidos,
        marcador = t.con(Clave.PRUEBA_ACIERTOS, aciertos),
        onTiempoAgotado = { cerrar() }
    ) {
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
            TarjetaPalabra(
                texto = palabras.getOrNull(indice).orEmpty(),
                color = color,
                encabezado = t.lemaDe(juego).uppercase(t.locale)
            )
        }

        Spacer(Modifier.height(16.dp))

        FilaBotones {
            BotonPrueba(
                texto = t[Clave.PRUEBA_SALTAR],
                color = SuperficieAlta,
                colorTexto = TextoFuerte,
                modifier = Modifier.weight(1f)
            ) {
                sonidos.toque()
                indice++
            }
            BotonPrueba(
                texto = t[Clave.PRUEBA_ACERTADA],
                color = Exito,
                modifier = Modifier.weight(1.4f)
            ) {
                sonidos.acierto()
                aciertos++
                indice++
            }
        }

        Spacer(Modifier.height(4.dp))
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            BotonSuave(t[Clave.PRUEBA_TERMINAR]) { cerrar() }
        }
    }
}

/** Tabú: describir la palabra sin decir ninguna de las prohibidas. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PruebaTabu(vm: JuegoViewModel, cartas: List<CartaTabu>, sonidos: Sonidos) {
    val t = textos()
    val juego = Juego.TABU
    val color = paleta().colorDe(juego)
    var indice by remember { mutableIntStateOf(0) }
    var aciertos by remember { mutableIntStateOf(0) }
    var terminada by remember { mutableStateOf(false) }

    fun cerrar() {
        if (terminada) return
        terminada = true
        vm.resolverPrueba(superada = aciertos > 0, puntos = aciertos)
    }

    LaunchedEffect(indice) {
        if (indice >= cartas.size) cerrar()
    }

    val carta = cartas.getOrNull(indice)

    MarcoPrueba(
        juego = juego,
        segundos = vm.estado.segundosDe(juego),
        enMarcha = !terminada,
        sonidos = sonidos,
        marcador = t.con(Clave.PRUEBA_ACIERTOS, aciertos),
        onTiempoAgotado = { cerrar() }
    ) {
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
            TarjetaPalabra(
                texto = carta?.palabra.orEmpty(),
                color = color,
                encabezado = t.lemaDe(juego).uppercase(t.locale)
            ) {
                Spacer(Modifier.height(18.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    carta?.prohibidas?.forEach { prohibida ->
                        Text(
                            text = prohibida,
                            style = MaterialTheme.typography.titleMedium,
                            color = Fallo,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(Fallo.copy(alpha = 0.13f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        Column {
            FilaBotones {
                BotonPrueba(
                    texto = t[Clave.PRUEBA_SALTAR],
                    color = SuperficieAlta,
                    colorTexto = TextoFuerte,
                    modifier = Modifier.weight(1f)
                ) {
                    sonidos.toque()
                    indice++
                }
                BotonPrueba(
                    texto = t[Clave.PRUEBA_PROHIBIDA],
                    color = Fallo,
                    modifier = Modifier.weight(1f)
                ) {
                    sonidos.fallo()
                    indice++
                }
            }
            Spacer(Modifier.height(10.dp))
            BotonPrueba(
                texto = t[Clave.PRUEBA_ACERTADA],
                color = Exito,
                modifier = Modifier.fillMaxWidth()
            ) {
                sonidos.acierto()
                aciertos++
                indice++
            }
        }

        Spacer(Modifier.height(4.dp))
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            BotonSuave(t[Clave.PRUEBA_TERMINAR]) { cerrar() }
        }
    }
}
