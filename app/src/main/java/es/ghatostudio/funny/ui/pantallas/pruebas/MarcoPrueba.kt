package es.ghatostudio.funny.ui.pantallas.pruebas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.comun.Cronometro
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.PastillaJuego
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.paleta

/**
 * Estructura común de las doce pruebas: el juego arriba, la cuenta atrás
 * debajo y el contenido concreto ocupando el resto.
 *
 * Tenerla en un solo sitio es lo que hace que añadir un juego nuevo sea
 * escribir su tablero de juego y nada más.
 */
@Composable
fun MarcoPrueba(
    juego: Juego,
    segundos: Int,
    enMarcha: Boolean,
    sonidos: Sonidos,
    marcador: String?,
    onTiempoAgotado: () -> Unit,
    contenido: @Composable ColumnScope.() -> Unit,
) {
    FondoFunny(tinte = paleta().colorDe(juego)) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 14.dp),
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                PastillaJuego(juego)
                Spacer(Modifier.weight(1f))
                if (marcador != null) {
                    Text(
                        marcador,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextoFuerte,
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Cronometro(
                segundos = segundos,
                enMarcha = enMarcha,
                sonidos = sonidos,
                onFin = onTiempoAgotado,
            )

            Spacer(Modifier.height(16.dp))

            contenido()
        }
    }
}
