package es.ghatostudio.funny.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.TemaId
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.AREA_TACTIL_MINIMA
import es.ghatostudio.funny.ui.comun.Cabecera
import es.ghatostudio.funny.ui.comun.FilaInterruptor
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.comun.TituloDeSeccion
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Contorno
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paletaDe

/**
 * Los seis temas, agrupados en claros y oscuros, más «seguir el sistema».
 *
 * Elegir un tema apaga «seguir el sistema» automáticamente: son dos formas de
 * decir lo mismo y tenerlas peleadas confunde. Cada tema se ve al momento, sin
 * reiniciar nada, porque los colores salen de un `CompositionLocal`.
 */
@Composable
fun PantallaTema(vm: JuegoViewModel) {
    val t = textos()
    val ajustes = vm.estado.ajustes

    FondoFunny {
        Column(Modifier.fillMaxSize()) {
            Cabecera(
                titulo = t[Clave.AJUSTES_TEMA],
                subtitulo = t[Clave.AJUSTES_TEMA_DETALLE],
                onVolver = { vm.ir(Pantalla.AJUSTES) },
            )

            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Tarjeta {
                    Column(Modifier.padding(horizontal = 18.dp, vertical = 8.dp)) {
                        FilaInterruptor(
                            titulo = t[Clave.AJUSTES_TEMA_SISTEMA],
                            activo = ajustes.temaDelSistema,
                        ) { vm.seguirTemaDelSistema(it) }
                    }
                }

                TituloDeSeccion(t[Clave.TEMA_MODO_OSCURO])
                TemaId.OSCUROS.forEach { id ->
                    FilaDeTema(
                        id = id,
                        seleccionado = ajustes.tema == id,
                        onElegir = { vm.elegirTema(id) },
                    )
                }

                TituloDeSeccion(t[Clave.TEMA_MODO_CLARO])
                TemaId.CLAROS.forEach { id ->
                    FilaDeTema(
                        id = id,
                        seleccionado = ajustes.tema == id,
                        onElegir = { vm.elegirTema(id) },
                    )
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

/**
 * Fila de tema: el nombre y una **vista real** de sus colores.
 *
 * La muestra se pinta con la paleta de ese tema, no con la activa: así se ve de
 * verdad cómo va a quedar antes de elegirlo, en lugar de tener que probarlos
 * uno a uno.
 */
@Composable
private fun FilaDeTema(id: TemaId, seleccionado: Boolean, onElegir: () -> Unit) {
    val t = textos()
    val muestra = paletaDe(id)

    Tarjeta(
        modifier = Modifier.fillMaxWidth().clickable { onElegir() },
        borde = if (seleccionado) Primario else null,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .heightIn(min = AREA_TACTIL_MINIMA)
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Retrato del tema: su propio fondo con sus propios colores dentro.
            Box(
                Modifier
                    .size(width = 76.dp, height = 52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(muestra.fondo)
                    .border(1.dp, Contorno, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    listOf(
                        muestra.primario,
                        muestra.acento,
                        muestra.exito,
                        muestra.textoFuerte,
                    ).forEach { color ->
                        Box(Modifier.size(13.dp).clip(CircleShape).background(color))
                    }
                }
            }

            Spacer(Modifier.width(14.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    t[id.claveNombre],
                    style = MaterialTheme.typography.titleMedium,
                    color = TextoFuerte,
                )
                Text(
                    if (id.esOscuro) t[Clave.TEMA_MODO_OSCURO] else t[Clave.TEMA_MODO_CLARO],
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
