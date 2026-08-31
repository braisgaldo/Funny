package com.fieston.juego

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.fieston.datos.Contenido
import com.fieston.datos.EventoCuando
import com.fieston.datos.Mazo
import com.fieston.datos.Preferencias
import com.fieston.datos.PreguntaTrivial
import com.fieston.datos.RetoRapido
import com.fieston.modelo.Ajustes
import com.fieston.modelo.COLORES_EQUIPO
import com.fieston.modelo.Casilla
import com.fieston.modelo.Categoria
import com.fieston.modelo.Equipo
import com.fieston.modelo.EstadoJuego
import com.fieston.modelo.Pantalla
import com.fieston.modelo.Prueba
import com.fieston.modelo.TipoCasilla
import kotlin.random.Random

class JuegoViewModel(app: Application) : AndroidViewModel(app) {

    private val contenido = Contenido.cargar(app)
    private val prefs = Preferencias(app)
    private val rnd = Random(System.nanoTime())

    private val mazoMimica = Mazo(contenido.mimica, rnd)
    private val mazoDibujo = Mazo(contenido.dibujo, rnd)
    private val mazoEventos = Mazo(contenido.eventos, rnd)
    private val mazoPreguntas = Mazo(contenido.preguntas, rnd)
    private val mazoTabu = Mazo(contenido.tabu, rnd)
    private val mazoRetos = Mazo(contenido.retos, rnd)

    var estado by mutableStateOf(
        EstadoJuego(
            equipos = prefs.cargarEquipos() ?: equiposPorDefecto(),
            ajustes = prefs.cargarAjustes()
        )
    )
        private set

    // ---------------------------------------------------------------- navegación

    fun ir(pantalla: Pantalla) {
        estado = estado.copy(pantalla = pantalla)
    }

    fun volverAlMenu() {
        estado = estado.copy(pantalla = Pantalla.INICIO)
    }

    // ------------------------------------------------------------------ equipos

    private fun equiposPorDefecto() = listOf(
        Equipo(id = 1, nombre = "Los Cracks", colorIndex = 0),
        Equipo(id = 2, nombre = "Los Fieras", colorIndex = 1)
    )

    fun anadirEquipo() {
        if (estado.equipos.size >= COLORES_EQUIPO.size) return
        val usados = estado.equipos.map { it.colorIndex }.toSet()
        val color = COLORES_EQUIPO.indices.firstOrNull { it !in usados } ?: estado.equipos.size
        val id = (estado.equipos.maxOfOrNull { it.id } ?: 0) + 1
        actualizarEquipos(estado.equipos + Equipo(id = id, nombre = "Equipo $id", colorIndex = color))
    }

    fun eliminarEquipo(id: Int) {
        if (estado.equipos.size <= 2) return
        actualizarEquipos(estado.equipos.filterNot { it.id == id })
    }

    fun renombrarEquipo(id: Int, nombre: String) {
        actualizarEquipos(estado.equipos.map { if (it.id == id) it.copy(nombre = nombre) else it })
    }

    fun anadirJugador(idEquipo: Int, nombre: String) {
        val limpio = nombre.trim()
        if (limpio.isEmpty()) return
        actualizarEquipos(
            estado.equipos.map {
                if (it.id == idEquipo && it.jugadores.size < 10) {
                    it.copy(jugadores = it.jugadores + limpio)
                } else {
                    it
                }
            }
        )
    }

    fun eliminarJugador(idEquipo: Int, indice: Int) {
        actualizarEquipos(
            estado.equipos.map { equipo ->
                if (equipo.id == idEquipo) {
                    equipo.copy(
                        jugadores = equipo.jugadores.filterIndexed { i, _ -> i != indice }
                    )
                } else {
                    equipo
                }
            }
        )
    }

    private fun actualizarEquipos(equipos: List<Equipo>) {
        estado = estado.copy(equipos = equipos)
        prefs.guardarEquipos(equipos)
    }

    fun actualizarAjustes(ajustes: Ajustes) {
        estado = estado.copy(ajustes = ajustes)
        prefs.guardarAjustes(ajustes)
    }

    // ------------------------------------------------------------------ partida

    fun empezarPartida() {
        val tablero = generarTablero(estado.ajustes.longitud.casillas)
        estado = estado.copy(
            pantalla = Pantalla.TABLERO,
            tablero = tablero,
            equipos = estado.equipos.map { it.copy(posicion = 0, turnoJugador = 0) },
            turno = 0,
            dado = null,
            origen = 0,
            destino = 0,
            categoria = null,
            prueba = null,
            esPruebaFinal = false,
            superada = false,
            avanceExtra = emptyList(),
            ganador = null,
            partidaEnCurso = true
        )
    }

    private fun generarTablero(longitud: Int): List<Casilla> {
        val casillas = mutableListOf(Casilla(0, TipoCasilla.SALIDA, null))
        var rotacion = 0
        for (i in 1 until longitud) {
            val tipo = when {
                i % 7 == 0 -> TipoCasilla.TODOS
                i % 5 == 0 -> TipoCasilla.COMODIN
                else -> TipoCasilla.NORMAL
            }
            val categoria = if (tipo == TipoCasilla.NORMAL) {
                Categoria.ROTACION[rotacion++ % Categoria.ROTACION.size]
            } else {
                null
            }
            casillas += Casilla(i, tipo, categoria)
        }
        casillas += Casilla(longitud, TipoCasilla.META, null)
        return casillas
    }

    /** El equipo activo tira el dado (1-3) y se decide qué prueba le toca. */
    fun lanzarDado() {
        val equipo = estado.equipoActivo ?: return
        val dado = (1..3).random(rnd)
        val meta = estado.meta
        val origen = equipo.posicion
        val destino = (origen + dado).coerceAtMost(meta)
        val esFinal = destino >= meta
        val casilla = estado.tablero.getOrNull(destino)

        val categoria = when {
            esFinal -> Categoria.ROTACION.random(rnd)
            casilla?.tipo == TipoCasilla.TODOS -> listOf(Categoria.CUANDO, Categoria.PREGUNTAS).random(rnd)
            casilla?.tipo == TipoCasilla.COMODIN -> null
            else -> casilla?.categoria
        }

        estado = estado.copy(
            dado = dado,
            origen = origen,
            destino = destino,
            esPruebaFinal = esFinal,
            categoria = categoria,
            prueba = categoria?.let { prepararPrueba(it) }
        )
    }

    /** Tras ver la tirada en el tablero se pasa a la prueba (o a elegirla). */
    fun continuarTrasDado() {
        estado = estado.copy(
            pantalla = if (estado.categoria == null) Pantalla.COMODIN else Pantalla.ENTREGA
        )
    }

    /** En las casillas comodín es el equipo rival quien elige la prueba. */
    fun elegirCategoria(categoria: Categoria) {
        estado = estado.copy(
            categoria = categoria,
            prueba = prepararPrueba(categoria),
            pantalla = Pantalla.ENTREGA
        )
    }

    fun empezarPrueba() {
        val esRondaTodos = !estado.esPruebaFinal &&
            estado.tablero.getOrNull(estado.destino)?.tipo == TipoCasilla.TODOS
        estado = estado.copy(
            pantalla = if (esRondaTodos) Pantalla.RONDA_TODOS else Pantalla.PRUEBA
        )
    }

    private fun prepararPrueba(categoria: Categoria): Prueba = when (categoria) {
        Categoria.MIMICA -> Prueba.DeMimica(mazoMimica.sacar(14))
        Categoria.DIBUJO -> Prueba.DeDibujo(mazoDibujo.sacar(8))
        Categoria.TABU -> Prueba.DeTabu(mazoTabu.sacar(10))
        Categoria.CUANDO -> {
            val evento = mazoEventos.sacar() ?: EventoCuando("Sin contenido", 2000, "")
            Prueba.DeCuando(evento, opcionesAnio(evento.anio))
        }

        Categoria.PREGUNTAS -> Prueba.DePreguntas(
            mazoPreguntas.sacar() ?: PreguntaTrivial("Sin contenido", listOf("-"), 0, "")
        )

        Categoria.RETO -> Prueba.DeReto(mazoRetos.sacar() ?: RetoRapido("Sin contenido", 1))
    }

    /**
     * Genera cuatro años plausibles alrededor del correcto. El margen se adapta a
     * la época: no tiene sentido ofrecer 1490/1492/1494 para un hecho medieval ni
     * 1990/2010 para algo de hace cinco años.
     */
    private fun opcionesAnio(anio: Int): List<Int> {
        val margen = when {
            anio < 1500 -> 70
            anio < 1800 -> 40
            anio < 1900 -> 20
            anio < 1960 -> 10
            anio < 2000 -> 7
            else -> 5
        }
        val opciones = linkedSetOf(anio)
        var intentos = 0
        while (opciones.size < 4 && intentos < 300) {
            intentos++
            val salto = (1..margen).random(rnd) * (if (rnd.nextBoolean()) 1 else -1)
            val candidato = anio + salto
            if (candidato in 1..2025) opciones += candidato
        }
        var relleno = 1
        while (opciones.size < 4) {
            opciones += anio + margen + relleno
            relleno++
        }
        return opciones.sorted()
    }

    // ---------------------------------------------------------------- resultados

    fun resolverPrueba(superada: Boolean) {
        val equipo = estado.equipoActivo ?: return
        var actualizado = equipo.copy(
            posicion = if (superada) estado.destino else estado.origen
        )
        if (estado.categoria?.soloActuante == true) {
            actualizado = actualizado.copy(turnoJugador = actualizado.turnoJugador + 1)
        }
        val equipos = estado.equipos.toMutableList().also { it[estado.turno] = actualizado }
        estado = estado.copy(
            equipos = equipos,
            superada = superada,
            ganador = if (superada && estado.esPruebaFinal) actualizado else null,
            avanceExtra = emptyList(),
            pantalla = Pantalla.RESULTADO
        )
    }

    /**
     * Casilla "todos juegan": cada equipo que acierta avanza una casilla, pero
     * nadie gana la partida por aquí (se frena justo antes de la meta).
     */
    fun resolverRondaTodos(aciertos: List<Boolean>) {
        val topeSinMeta = (estado.meta - 1).coerceAtLeast(0)
        val equipos = estado.equipos.mapIndexed { i, equipo ->
            val base = if (i == estado.turno) estado.destino else equipo.posicion
            val avance = if (aciertos.getOrElse(i) { false }) 1 else 0
            equipo.copy(posicion = (base + avance).coerceIn(0, topeSinMeta.coerceAtLeast(base)))
        }
        estado = estado.copy(
            equipos = equipos,
            superada = aciertos.getOrElse(estado.turno) { false },
            avanceExtra = aciertos.mapIndexedNotNull { i, ok -> if (ok) i else null },
            ganador = null,
            pantalla = Pantalla.RESULTADO
        )
    }

    fun siguienteTurno() {
        if (estado.ganador != null) {
            estado = estado.copy(pantalla = Pantalla.VICTORIA, partidaEnCurso = false)
            return
        }
        estado = estado.copy(
            turno = if (estado.equipos.isEmpty()) 0 else (estado.turno + 1) % estado.equipos.size,
            dado = null,
            categoria = null,
            prueba = null,
            esPruebaFinal = false,
            avanceExtra = emptyList(),
            pantalla = Pantalla.TABLERO
        )
    }
}
