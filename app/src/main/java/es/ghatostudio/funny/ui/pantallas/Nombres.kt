package es.ghatostudio.funny.ui.pantallas

import androidx.compose.runtime.Composable
import es.ghatostudio.funny.dominio.EstadoJuego
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.Participante
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.ui.i18n.Textos
import es.ghatostudio.funny.ui.i18n.textos

/**
 * Cómo se llama un participante en pantalla.
 *
 * Un participante puede no tener nombre puesto —ver `Participante.nombre`— y
 * entonces se pinta el nombre por defecto del idioma activo. Está aquí, en un
 * único sitio, porque lo necesitan el tablero, la entrega, el comodín, el
 * resultado y la victoria, y en cada uno hay que sacar el número de la lista de
 * una forma distinta.
 */
@Composable
fun nombrePorDefecto(t: Textos, numero: Int, modo: Modo): String = when {
    modo != Modo.EQUIPOS -> t.con(Clave.PARTICIPANTES_JUGADOR_POR_DEFECTO, numero)
    numero == 1 -> t[Clave.PARTICIPANTES_EQUIPO_1_POR_DEFECTO]
    numero == 2 -> t[Clave.PARTICIPANTES_EQUIPO_2_POR_DEFECTO]
    else -> t.con(Clave.PARTICIPANTES_EQUIPO_POR_DEFECTO, numero)
}

/** Nombre visible del participante que está en la posición [indice] de la lista. */
@Composable
fun nombreEnIndice(estado: EstadoJuego, indice: Int): String {
    val t = textos()
    val participante = estado.participantes.getOrNull(indice) ?: return ""
    return participante.nombreVisible(nombrePorDefecto(t, indice + 1, estado.modo))
}

/** Nombre visible de un participante concreto, buscando su sitio en la lista. */
@Composable
fun nombreDe(estado: EstadoJuego, participante: Participante): String {
    val indice = estado.participantes.indexOfFirst { it.id == participante.id }
    val t = textos()
    val numero = if (indice >= 0) indice + 1 else 1
    return participante.nombreVisible(nombrePorDefecto(t, numero, estado.modo))
}

/** Nombre visible del participante al que le toca el turno. */
@Composable
fun nombreDelActivo(estado: EstadoJuego): String = nombreEnIndice(estado, estado.turno)

/** Quién elige en una casilla comodín: el siguiente en el orden de turno. */
@Composable
fun nombreDeQuienElige(estado: EstadoJuego): String {
    if (estado.participantes.size < 2) return ""
    return nombreEnIndice(estado, (estado.turno + 1) % estado.participantes.size)
}
