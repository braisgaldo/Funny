package es.ghatostudio.funny.ui.pantallas.pruebas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.Prueba
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.AREA_TACTIL_MINIMA
import es.ghatostudio.funny.ui.comun.BotonPrueba
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Fallo
import es.ghatostudio.funny.ui.tema.Superficie
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

/**
 * «Ordena»: cuatro cosas desordenadas y un criterio.
 *
 * Se ordena **tocando**, no arrastrando: en una mesa el móvil está en una mano
 * y a menudo en movimiento, y un arrastre preciso con cuatro elementos es un
 * gesto incómodo y fácil de fallar. Tocar en orden es inequívoco, y volver a
 * tocar un elemento ya elegido lo devuelve a la lista.
 */
@Composable
fun PruebaOrdena(vm: JuegoViewModel, prueba: Prueba.DeOrdena, sonidos: Sonidos) {
    val t = textos()
    val p = paleta()
    val juego = Juego.ORDENA
    val color = p.colorDe(juego)

    val elegidos = remember { mutableStateListOf<String>() }
    var comprobado by remember { mutableStateOf(false) }
    var terminada by remember { mutableStateOf(false) }

    val correcto = prueba.reto.elementos
    val acertado = comprobado && elegidos.toList() == correcto

    fun cerrar(exito: Boolean) {
        if (terminada) return
        terminada = true
        vm.resolverPrueba(exito)
    }

    MarcoPrueba(
        juego = juego,
        segundos = vm.estado.segundosDe(juego),
        enMarcha = !terminada && !comprobado,
        sonidos = sonidos,
        marcador = t.con(Clave.PRUEBA_ACIERTOS_DE, elegidos.size, correcto.size),
        onTiempoAgotado = { comprobado = true }
    ) {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            // Enunciado
            Box(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(Superficie)
                    .border(2.dp, color.copy(alpha = 0.4f), RoundedCornerShape(22.dp))
                    .padding(20.dp)
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (prueba.reto.criterio.isNotBlank()) {
                        Text(
                            prueba.reto.criterio.uppercase(t.locale),
                            style = MaterialTheme.typography.labelLarge,
                            color = color
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    Text(
                        prueba.reto.enunciado,
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextoFuerte,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        t[Clave.PRUEBA_ORDENA_AYUDA],
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoTenue,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            prueba.desordenados.forEach { elemento ->
                val puesto = elegidos.indexOf(elemento)
                val elegido = puesto >= 0
                val puestoBueno = correcto.indexOf(elemento)
                val bienColocado = comprobado && elegido && puesto == puestoBueno

                val borde = when {
                    comprobado && elegido && bienColocado -> Exito
                    comprobado && elegido -> Fallo
                    elegido -> color
                    else -> SuperficieAlta
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .heightIn(min = AREA_TACTIL_MINIMA)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (elegido) borde.copy(alpha = 0.18f) else Superficie
                        )
                        .border(2.dp, borde, RoundedCornerShape(16.dp))
                        .clickable(enabled = !comprobado && !terminada) {
                            sonidos.toque()
                            if (elegido) elegidos.remove(elemento) else elegidos.add(elemento)
                        }
                        .padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(borde.copy(alpha = 0.28f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (elegido) "${puesto + 1}" else "·",
                            style = MaterialTheme.typography.titleMedium,
                            color = borde
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Text(
                        elemento,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextoFuerte,
                        modifier = Modifier.weight(1f)
                    )
                    if (comprobado && elegido) {
                        Text(
                            if (bienColocado) "✓" else "✕",
                            style = MaterialTheme.typography.titleLarge,
                            color = borde
                        )
                    }
                }
            }

            if (comprobado && !acertado) {
                Spacer(Modifier.height(6.dp))
                Text(
                    t[Clave.PRUEBA_ORDENA_CORRECTO],
                    style = MaterialTheme.typography.labelLarge,
                    color = TextoTenue
                )
                Spacer(Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    correcto.forEachIndexed { i, elemento ->
                        Text(
                            "${i + 1}.  $elemento",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Exito
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        if (!comprobado) {
            BotonPrueba(
                texto = t[Clave.PRUEBA_ORDENA_COMPROBAR],
                color = if (elegidos.size == correcto.size) color else SuperficieAlta,
                colorTexto = if (elegidos.size == correcto.size) null else TextoTenue,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (elegidos.size != correcto.size) return@BotonPrueba
                comprobado = true
                if (elegidos.toList() == correcto) sonidos.acierto() else sonidos.fallo()
            }
        } else {
            BotonPrueba(
                texto = t[Clave.ACCION_CONTINUAR],
                color = if (acertado) Exito else Fallo,
                modifier = Modifier.fillMaxWidth()
            ) {
                cerrar(acertado)
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}
