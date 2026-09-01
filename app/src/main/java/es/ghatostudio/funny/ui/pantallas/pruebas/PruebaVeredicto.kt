package es.ghatostudio.funny.ui.pantallas.pruebas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Cancion
import es.ghatostudio.funny.dominio.Desafio
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.Trabalenguas
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.BotonPrueba
import es.ghatostudio.funny.ui.comun.FilaBotones
import es.ghatostudio.funny.ui.comun.TarjetaPalabra
import es.ghatostudio.funny.ui.i18n.ClavePlural
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Fallo
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

/**
 * Marco de las tres pruebas que la app no puede juzgar: trabalenguas, canta y
 * desafío.
 *
 * Se han reunido aquí porque comparten exactamente el mismo esqueleto —un
 * enunciado grande y dos botones de sí o no— y porque tienen en común algo más
 * importante: la app **no finge** saber si se ha conseguido. Un móvil no puede
 * decidir si alguien ha cantado bien o si se ha trabado, así que pregunta a la
 * mesa. En solitario cambia el aviso, porque ahí no hay mesa a la que preguntar
 * y lo honesto es decirlo.
 */
@Composable
private fun PruebaConVeredicto(
    vm: JuegoViewModel,
    juego: Juego,
    enunciado: String,
    encabezado: String,
    sonidos: Sonidos,
    detalle: (@Composable () -> Unit)? = null
) {
    val t = textos()
    val p = paleta()
    val color = p.colorDe(juego)
    var terminada by remember { mutableStateOf(false) }
    var seAcaboElTiempo by remember { mutableStateOf(false) }

    fun cerrar(logrado: Boolean) {
        if (terminada) return
        terminada = true
        if (logrado) sonidos.acierto() else sonidos.fallo()
        vm.resolverPrueba(logrado)
    }

    MarcoPrueba(
        juego = juego,
        segundos = vm.estado.segundosDe(juego),
        enMarcha = !terminada && !seAcaboElTiempo,
        sonidos = sonidos,
        marcador = null,
        onTiempoAgotado = { seAcaboElTiempo = true }
    ) {
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TarjetaPalabra(
                    texto = enunciado,
                    color = color,
                    encabezado = encabezado
                )
                if (detalle != null) {
                    Spacer(Modifier.height(14.dp))
                    detalle()
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            t[Clave.PRUEBA_VEREDICTO_TITULO],
            style = MaterialTheme.typography.titleMedium,
            color = TextoFuerte,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(4.dp))
        Text(
            if (vm.estado.modo == Modo.SOLITARIO) {
                t[Clave.PRUEBA_VEREDICTO_DECIDE_SOLO]
            } else {
                t[Clave.PRUEBA_VEREDICTO_DECIDE_MESA]
            },
            style = MaterialTheme.typography.bodyMedium,
            color = TextoTenue,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        FilaBotones {
            BotonPrueba(
                texto = t[Clave.PRUEBA_VEREDICTO_NO_LOGRADO],
                color = Fallo,
                modifier = Modifier.weight(1f)
            ) { cerrar(false) }
            BotonPrueba(
                texto = t[Clave.PRUEBA_VEREDICTO_LOGRADO],
                color = Exito,
                modifier = Modifier.weight(1.4f)
            ) { cerrar(true) }
        }

        Spacer(Modifier.height(8.dp))
    }
}

/** Trabalenguas: decirlo entero, las veces que pida, sin trabarse. */
@Composable
fun PruebaTrabalenguas(vm: JuegoViewModel, trabalenguas: Trabalenguas, sonidos: Sonidos) {
    val t = textos()
    PruebaConVeredicto(
        vm = vm,
        juego = Juego.TRABALENGUAS,
        enunciado = trabalenguas.texto,
        encabezado = t.plural(ClavePlural.REPETICIONES, trabalenguas.repeticiones)
            .uppercase(t.locale),
        sonidos = sonidos,
        detalle = {
            Text(
                t[Clave.PRUEBA_TRABALENGUAS_AYUDA],
                style = MaterialTheme.typography.bodyMedium,
                color = TextoTenue,
                textAlign = TextAlign.Center
            )
        }
    )
}

/**
 * Canta: sale el título y quién la canta, y hay que ponerse a cantar.
 *
 * No se distribuye ni un verso de letra: solo el título, el artista y una pista
 * de por dónde empezar. Es una decisión deliberada para no meter en la app
 * material con derechos de autor (ver ADR-0005).
 */
@Composable
fun PruebaCanta(vm: JuegoViewModel, cancion: Cancion, sonidos: Sonidos) {
    val t = textos()
    PruebaConVeredicto(
        vm = vm,
        juego = Juego.CANTA,
        enunciado = cancion.titulo,
        encabezado = cancion.artista.uppercase(t.locale),
        sonidos = sonidos,
        detalle = {
            if (cancion.pista.isNotBlank()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        t[Clave.PRUEBA_CANTA_PISTA],
                        style = MaterialTheme.typography.labelLarge,
                        color = TextoTenue
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        cancion.pista,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextoFuerte,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    )
}

/** Desafío: un micro-reto delante de todos. */
@Composable
fun PruebaDesafio(vm: JuegoViewModel, desafio: Desafio, sonidos: Sonidos) {
    val t = textos()
    PruebaConVeredicto(
        vm = vm,
        juego = Juego.DESAFIO,
        enunciado = desafio.texto,
        encabezado = t[Clave.PRUEBA_DESAFIO_AYUDA].uppercase(t.locale),
        sonidos = sonidos
    )
}
