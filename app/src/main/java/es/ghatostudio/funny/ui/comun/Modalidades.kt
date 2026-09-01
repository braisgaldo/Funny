package es.ghatostudio.funny.ui.comun

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Ajustes
import es.ghatostudio.funny.dominio.Modalidad
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.ui.i18n.LocalTextos
import es.ghatostudio.funny.ui.i18n.Textos
import es.ghatostudio.funny.ui.tema.Contorno
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.SobrePrimario
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue

/**
 * El selector de modalidad, uno y compartido.
 *
 * Aparece en dos sitios —los ajustes y la pantalla en la que se apuntan los
 * participantes, justo antes de empezar— y vive aquí para que no acaben
 * habiendo dos versiones que se separen con el tiempo. Los ajustes lo pintan
 * entero; la pantalla de participantes lo pinta [compacto], sin los detalles
 * largos.
 *
 * Los pasos numéricos solo salen con [Modalidad.PERSONALIZADA]: si estuvieran
 * siempre, la modalidad elegida y los números dirían cosas distintas y no
 * habría forma de saber cuál manda.
 */
@Composable
fun SelectorDeModalidad(
    ajustes: Ajustes,
    modifier: Modifier = Modifier,
    compacto: Boolean = false,
    onCambio: (Ajustes) -> Unit,
) {
    val t = LocalTextos.current
    Column(modifier.fillMaxWidth()) {
        Modalidad.entries.forEach { modalidad ->
            FilaDeModalidad(
                modalidad = modalidad,
                elegida = modalidad == ajustes.modalidad,
                compacto = compacto,
                resumen = resumenDe(t, modalidad, ajustes),
                onElegir = { onCambio(ajustes.copy(modalidad = modalidad)) },
            )
            if (modalidad != Modalidad.entries.last()) Spacer(Modifier.height(8.dp))
        }

        // AnimatedVisibility y no un `if`: al elegir «personalizada» los dos
        // pasos aparecen deslizándose, y así se ve que son suyos.
        AnimatedVisibility(visible = ajustes.modalidad.esPersonalizada) {
            Column {
                Spacer(Modifier.height(14.dp))
                PasoNumerico(
                    titulo = t[Clave.MODALIDAD_CASILLAS],
                    valor = ajustes.casillas,
                    rango = Modalidad.CASILLAS_POSIBLES,
                    paso = Modalidad.PASO_CASILLAS,
                    onCambio = { onCambio(ajustes.copy(casillasPersonalizadas = it)) },
                )
                Spacer(Modifier.height(10.dp))
                PasoNumerico(
                    titulo = t[Clave.MODALIDAD_PRUEBAS],
                    valor = ajustes.pruebasSolitario,
                    rango = Modalidad.PRUEBAS_POSIBLES,
                    paso = Modalidad.PASO_PRUEBAS,
                    nota = t[Clave.MODALIDAD_PRUEBAS_NOTA],
                    onCambio = { onCambio(ajustes.copy(pruebasPersonalizadas = it)) },
                )
            }
        }
    }
}

/**
 * Resumen de una modalidad: casillas, pruebas y minutos aproximados.
 *
 * Para las tres cerradas son sus propios números; para la personalizada, los
 * que haya puesto quien juega, que es lo que hace que el resumen cambie a la
 * vez que los pasos numéricos.
 */
private fun resumenDe(
    t: Textos,
    modalidad: Modalidad,
    ajustes: Ajustes,
): String {
    val casillas = modalidad.casillas ?: ajustes.casillas
    val pruebas = modalidad.pruebas ?: ajustes.pruebasSolitario
    return t.con(
        Clave.MODALIDAD_RESUMEN,
        casillas,
        pruebas,
        Modalidad.minutosAproximados(casillas),
    )
}

/** Resumen de la modalidad activa, para enseñarlo en una línea. */
@Composable
fun resumenDeLaModalidad(ajustes: Ajustes): String =
    resumenDe(LocalTextos.current, ajustes.modalidad, ajustes)

@Composable
private fun FilaDeModalidad(
    modalidad: Modalidad,
    elegida: Boolean,
    compacto: Boolean,
    resumen: String,
    onElegir: () -> Unit,
) {
    val t = LocalTextos.current
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(if (elegida) Primario else SuperficieAlta)
                .border(
                    width = if (elegida) 0.dp else 1.dp,
                    color = if (elegida) Color.Transparent else Contorno,
                    shape = RoundedCornerShape(16.dp),
                ).clickable { onElegir() }
                .sizeIn(minHeight = AREA_TACTIL_MINIMA)
                .padding(horizontal = 14.dp, vertical = if (compacto) 10.dp else 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(modalidad.emoji, style = MaterialTheme.typography.titleMedium)
        Column(Modifier.weight(1f)) {
            Text(
                t[modalidad.claveNombre],
                style = MaterialTheme.typography.titleSmall,
                color = if (elegida) SobrePrimario else TextoFuerte,
            )
            if (!compacto) {
                Text(
                    t[modalidad.claveDetalle],
                    style = MaterialTheme.typography.bodySmall,
                    color = if (elegida) SobrePrimario else TextoTenue,
                )
            }
            Text(
                resumen,
                style = MaterialTheme.typography.labelSmall,
                color = if (elegida) SobrePrimario else TextoTenue,
            )
        }
    }
}

/**
 * Un número con dos botones, menos y más.
 *
 * Se ha preferido a un deslizador porque aquí el número exacto importa —doce
 * casillas y catorce no son lo mismo— y con un deslizador es difícil clavarlo
 * con el dedo. Los botones se apagan al llegar al extremo en lugar de dar la
 * vuelta: pasar de cuarenta casillas a ocho de un toque sería una sorpresa
 * desagradable.
 */
@Composable
fun PasoNumerico(
    titulo: String,
    valor: Int,
    rango: IntRange,
    paso: Int,
    modifier: Modifier = Modifier,
    nota: String? = null,
    onCambio: (Int) -> Unit,
) {
    val t = LocalTextos.current
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(SuperficieAlta)
                .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(titulo, style = MaterialTheme.typography.bodyMedium, color = TextoFuerte)
            if (nota != null) {
                Text(nota, style = MaterialTheme.typography.labelSmall, color = TextoTenue)
            }
        }
        BotonDePaso(
            signo = "−",
            descripcion = t[Clave.A11Y_REDUCIR],
            activo = valor - paso >= rango.first,
            onPulsar = { onCambio((valor - paso).coerceIn(rango)) },
        )
        Box(
            modifier = Modifier.sizeIn(minWidth = 52.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                valor.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = TextoFuerte,
                textAlign = TextAlign.Center,
            )
        }
        BotonDePaso(
            signo = "+",
            descripcion = t[Clave.A11Y_AUMENTAR],
            activo = valor + paso <= rango.last,
            onPulsar = { onCambio((valor + paso).coerceIn(rango)) },
        )
    }
}

@Composable
private fun BotonDePaso(
    signo: String,
    descripcion: String,
    activo: Boolean,
    onPulsar: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .size(AREA_TACTIL_MINIMA)
                .clip(CircleShape)
                .background(if (activo) Primario else Contorno)
                .clickable(enabled = activo) { onPulsar() }
                .semantics { contentDescription = descripcion },
        contentAlignment = Alignment.Center,
    ) {
        // El signo se saca del árbol de accesibilidad: el lector de pantalla ya
        // lee la descripción del botón, y «menos» dos veces sobra.
        Text(
            signo,
            style = MaterialTheme.typography.titleMedium,
            color = if (activo) SobrePrimario else TextoTenue,
            modifier = Modifier.clearAndSetSemantics { },
        )
    }
}
