package es.ghatostudio.funny.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
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
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.PastillaJuego
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

private const val NOMBRE = "FUNNY" // literal-ok: es la marca, no se traduce

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PantallaInicio(vm: JuegoViewModel) {
    val t = textos()
    val p = paleta()
    val estado = vm.estado
    val jugables = vm.contenidoActual.juegosJugables

    FondoFunny(tinte = Primario) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 26.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("🎉", style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.height(10.dp))

            // Cada letra de un color: es el logo, y usa los colores de
            // participante del tema activo, así que cambia con él.
            Row(horizontalArrangement = Arrangement.Center) {
                NOMBRE.forEachIndexed { indice, letra ->
                    Text(
                        text = letra.toString(),
                        style = MaterialTheme.typography.displayMedium,
                        color = p.colorDeParticipante(indice),
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            Text(
                t[Clave.APP_LEMA],
                style = MaterialTheme.typography.bodyLarge,
                color = TextoTenue,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(22.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                jugables.forEach { PastillaJuego(it) }
            }

            Spacer(Modifier.height(32.dp))

            if (estado.partidaEnCurso) {
                BotonGrande(t[Clave.MENU_SEGUIR_PARTIDA]) { vm.ir(Pantalla.TABLERO) }
                Spacer(Modifier.height(12.dp))
                BotonGrande(
                    t[Clave.MENU_PARTIDA_NUEVA],
                    onClick = { vm.ir(Pantalla.MODO) },
                    color = SuperficieAlta,
                    colorTexto = TextoFuerte,
                )
            } else {
                BotonGrande(t[Clave.MENU_JUGAR]) { vm.ir(Pantalla.MODO) }
            }

            Spacer(Modifier.height(12.dp))
            BotonGrande(
                t[Clave.MENU_SALON],
                onClick = { vm.ir(Pantalla.SALON) },
                color = SuperficieAlta,
                colorTexto = TextoFuerte,
            )
            Spacer(Modifier.height(12.dp))
            BotonGrande(
                t[Clave.MENU_COMO_JUGAR],
                onClick = { vm.ir(Pantalla.AYUDA) },
                color = SuperficieAlta,
                colorTexto = TextoFuerte,
            )
            Spacer(Modifier.height(12.dp))
            BotonGrande(
                t[Clave.MENU_AJUSTES],
                onClick = { vm.ir(Pantalla.AJUSTES) },
                color = SuperficieAlta,
                colorTexto = TextoFuerte,
            )
        }
    }
}
