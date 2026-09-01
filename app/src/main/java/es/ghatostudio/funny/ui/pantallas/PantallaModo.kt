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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.Cabecera
import es.ghatostudio.funny.ui.comun.EntradaEscalonada
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Acento
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue

/**
 * Elección de modo: por equipos, individual o reto en solitario.
 *
 * Es una pantalla nueva de Funny; en Fiestón se iba directo a los equipos
 * porque solo había una forma de jugar.
 */
@Composable
fun PantallaModo(vm: JuegoViewModel) {
    val t = textos()

    FondoFunny {
        Column(Modifier.fillMaxSize()) {
            Cabecera(
                titulo = t[Clave.MODO_TITULO],
                subtitulo = t[Clave.MODO_SUBTITULO],
                onVolver = { vm.ir(Pantalla.INICIO) },
            )

            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Modo.entries.forEachIndexed { indice, modo ->
                    EntradaEscalonada(indice) {
                        TarjetaModo(
                            emoji = modo.emoji,
                            titulo = t[modo.claveNombre],
                            detalle = t[modo.claveDetalle],
                            extra = if (modo == Modo.SOLITARIO) marcaSolitario(vm) else null,
                            onClick = { vm.elegirModo(modo) },
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun marcaSolitario(vm: JuegoViewModel): String {
    val t = textos()
    val marca = vm.estado.ajustes.mejorMarcaSolitario
    return if (marca >
        0
    ) {
        t.con(Clave.SOLITARIO_MEJOR_MARCA, marca)
    } else {
        t[Clave.SOLITARIO_SIN_MARCA]
    }
}

@Composable
private fun TarjetaModo(
    emoji: String,
    titulo: String,
    detalle: String,
    extra: String?,
    onClick: () -> Unit,
) {
    Tarjeta(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        borde = Primario.copy(alpha = 0.35f),
    ) {
        Row(Modifier.padding(18.dp)) {
            Text(emoji, style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.width(16.dp))
            Column {
                Text(titulo, style = MaterialTheme.typography.titleLarge, color = TextoFuerte)
                Spacer(Modifier.height(6.dp))
                Text(detalle, style = MaterialTheme.typography.bodyMedium, color = TextoTenue)
                if (extra != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(extra, style = MaterialTheme.typography.labelLarge, color = Acento)
                }
            }
        }
    }
}
