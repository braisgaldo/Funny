package es.ghatostudio.funny.ui.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.Banda
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Acento
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

/**
 * Casilla comodín: elige la prueba quien va detrás en el orden de turno, no la
 * app. Se ofrecen solo los juegos que de verdad están en esta partida.
 */
@Composable
fun PantallaComodin(vm: JuegoViewModel) {
    val t = textos()
    val p = paleta()
    val estado = vm.estado
    val nombreQueElige = nombreDeQuienElige(estado)

    FondoFunny(tinte = Acento) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Banda("🃏  ${t[Clave.CASILLA_COMODIN]}", Acento)
            Spacer(Modifier.height(18.dp))
            Text("🃏", style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.height(10.dp))
            Text(
                t.con(Clave.COMODIN_ELIGE, nombreQueElige),
                style = MaterialTheme.typography.headlineMedium,
                color = TextoFuerte,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                t[Clave.CASILLA_COMODIN_DETALLE],
                style = MaterialTheme.typography.bodyLarge,
                color = TextoTenue,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(22.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                vm.juegosDeLaPartida.forEach { juego ->
                    val color = p.colorDe(juego)
                    Tarjeta(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { vm.elegirJuego(juego) },
                        borde = color.copy(alpha = 0.5f),
                    ) {
                        Row(
                            Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(juego.emoji, style = MaterialTheme.typography.headlineMedium)
                            Spacer(Modifier.width(14.dp))
                            Column(Modifier.weight(1f)) {
                                Text(
                                    t.nombreDe(juego),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = color,
                                )
                                Text(
                                    t.lemaDe(juego),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextoTenue,
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}
