package es.ghatostudio.funny.ui.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.AREA_TACTIL_MINIMA
import es.ghatostudio.funny.ui.comun.Cabecera
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.InsigniaDeIdioma
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.Idioma
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue

/**
 * Selector de idioma.
 *
 * Cada idioma aparece **escrito en su propio idioma** —«Deutsch», no
 * «Alemán»—, que es lo único que sirve para que alguien reconozca el suyo en una
 * lista de trece. El cambio se aplica al instante: los textos salen de un
 * catálogo en memoria, así que no hay que reiniciar la app ni recrear la
 * actividad, ni siquiera al pasar a árabe y cambiar toda la interfaz a RTL.
 */
@Composable
fun PantallaIdioma(vm: JuegoViewModel) {
    val t = textos()
    val elegido = vm.estado.ajustes.idioma
    val efectivo = vm.idioma

    FondoFunny {
        Column(Modifier.fillMaxSize()) {
            Cabecera(
                titulo = t[Clave.IDIOMA_TITULO],
                subtitulo = t[Clave.IDIOMA_SUBTITULO],
                onVolver = { vm.ir(Pantalla.AJUSTES) },
            )

            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                // «El del móvil» es una opción de pleno derecho, no la ausencia
                // de elección: quien viaja o cambia el idioma del sistema espera
                // que la app lo siga.
                FilaDeIdioma(
                    insignia = null,
                    nombre = t[Clave.IDIOMA_SEGUIR_SISTEMA],
                    detalle = efectivo.endonimo,
                    seleccionado = elegido == null,
                    onElegir = { vm.elegirIdioma(null) },
                )

                Idioma.entries.forEach { idioma ->
                    FilaDeIdioma(
                        insignia = idioma,
                        nombre = idioma.endonimo,
                        detalle = idioma.codigo.uppercase(t.locale),
                        seleccionado = elegido == idioma.codigo,
                        onElegir = { vm.elegirIdioma(idioma.codigo) },
                    )
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun FilaDeIdioma(
    insignia: Idioma?,
    nombre: String,
    detalle: String,
    seleccionado: Boolean,
    onElegir: () -> Unit,
) {
    Tarjeta(
        modifier = Modifier.fillMaxWidth().clickable { onElegir() },
        borde = if (seleccionado) Primario else null,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .heightIn(min = AREA_TACTIL_MINIMA)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (insignia != null) {
                InsigniaDeIdioma(insignia)
            } else {
                Text("📱", style = MaterialTheme.typography.headlineMedium)
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextoFuerte,
                )
                Text(
                    detalle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoTenue,
                )
            }
            if (seleccionado) {
                Text("✓", style = MaterialTheme.typography.headlineMedium, color = Exito)
            }
        }
    }
}
