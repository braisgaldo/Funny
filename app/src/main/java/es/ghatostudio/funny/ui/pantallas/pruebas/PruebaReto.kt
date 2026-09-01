package es.ghatostudio.funny.ui.pantallas.pruebas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.RetoRapido
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.BotonPrueba
import es.ghatostudio.funny.ui.comun.FilaBotones
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Superficie
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta
import kotlinx.coroutines.delay

/**
 * Reto rápido: enumerar cosas de una categoría contrarreloj.
 *
 * El contador lo lleva la mesa tocando el círculo, no la app: no hay forma de
 * que un móvil sepa si «Portugal» ya se había dicho. La app pone el reloj y el
 * objetivo, y confía en quien juega, que es como funciona este juego en una
 * mesa de verdad.
 */
@Composable
fun PruebaReto(vm: JuegoViewModel, reto: RetoRapido, sonidos: Sonidos) {
    val t = textos()
    val juego = Juego.RETO
    val p = paleta()
    val color = p.colorDe(juego)
    var contador by remember { mutableIntStateOf(0) }
    var terminada by remember { mutableStateOf(false) }
    val conseguido = contador >= reto.objetivo

    fun cerrar(exito: Boolean) {
        if (terminada) return
        terminada = true
        vm.resolverPrueba(superada = exito, puntos = contador)
    }

    LaunchedEffect(conseguido) {
        if (conseguido && !terminada) {
            sonidos.acierto()
            delay(700)
            cerrar(true)
        }
    }

    MarcoPrueba(
        juego = juego,
        segundos = vm.estado.segundosDe(juego),
        enMarcha = !terminada && !conseguido,
        sonidos = sonidos,
        marcador = null,
        onTiempoAgotado = { cerrar(contador >= reto.objetivo) },
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(Superficie)
                .border(2.dp, color.copy(alpha = 0.4f), RoundedCornerShape(22.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    t.con(Clave.PRUEBA_RETO_OBJETIVO, reto.objetivo),
                    style = MaterialTheme.typography.labelLarge,
                    color = color,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    reto.texto,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextoFuerte,
                    textAlign = TextAlign.Center,
                )
            }
        }

        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            val colorCirculo = if (conseguido) Exito else color
            val tinta = p.textoSobre(colorCirculo)
            Box(
                modifier =
                    Modifier
                        .size(216.dp)
                        .clip(CircleShape)
                        .background(colorCirculo)
                        .clickable(enabled = !conseguido && !terminada) {
                            sonidos.toque()
                            contador++
                        },
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "$contador",
                        style = MaterialTheme.typography.displayLarge,
                        color = tinta,
                    )
                    Text(
                        if (conseguido) {
                            t[Clave.PRUEBA_RETO_CONSEGUIDO]
                        } else {
                            t.con(Clave.PRUEBA_RETO_TOCA_PARA_SUMAR, reto.objetivo)
                        },
                        style = MaterialTheme.typography.labelLarge,
                        color = tinta.copy(alpha = 0.75f),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        FilaBotones {
            BotonPrueba(
                texto = "−1",
                color = SuperficieAlta,
                colorTexto = TextoFuerte,
                modifier = Modifier.weight(1f),
            ) {
                if (contador > 0) contador--
            }
            BotonPrueba(
                texto = t[Clave.PRUEBA_RETO_RENDIRSE],
                color = SuperficieAlta,
                colorTexto = TextoTenue,
                modifier = Modifier.weight(2f),
            ) {
                cerrar(false)
            }
        }

        Spacer(Modifier.height(8.dp))
        Text(
            t[Clave.PRUEBA_RETO_NOTA],
            style = MaterialTheme.typography.bodyMedium,
            color = TextoTenue,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
