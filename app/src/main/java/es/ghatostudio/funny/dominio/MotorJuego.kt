package es.ghatostudio.funny.dominio

import kotlin.random.Random

/**
 * Las reglas de Funny, sin nada de Android ni de Compose.
 *
 * Cada método recibe un [EstadoJuego] y devuelve el siguiente: no guarda estado
 * propio más allá de los mazos. Eso tiene tres ventajas que justifican haberlo
 * sacado del ViewModel:
 *
 *  1. Se puede probar entero con tests de JVM, sin emulador.
 *  2. El hub del salón aplica exactamente las mismas transiciones que un móvil
 *     solo, así que no hay dos implementaciones de las reglas que puedan
 *     separarse con el tiempo.
 *  3. El día que el proyecto pase a KMP, este fichero se mueve a `commonMain`
 *     sin tocar una línea.
 */
class MotorJuego(
    private val contenido: Contenido,
    private val repartidor: Repartidor,
    private val rnd: Random,
) {
    // ------------------------------------------------------------- partidas

    /** Juegos que pueden salir en esta partida, según ajustes, modo y contenido. */
    fun juegosDeLaPartida(estado: EstadoJuego): List<Juego> {
        val activos = estado.ajustes.juegosActivos(contenido)
        return if (estado.modo == Modo.SOLITARIO) {
            activos.filter { it.valeEnSolitario }.ifEmpty { Juego.EN_SOLITARIO }
        } else {
            activos
        }
    }

    /** Arranca una partida por casillas (equipos o individual). */
    fun empezarCarrera(estado: EstadoJuego, participantes: List<Participante>): EstadoJuego {
        val tablero =
            generarTablero(
                casillas = estado.ajustes.casillas,
                juegosActivos = juegosDeLaPartida(estado),
                rnd = rnd,
            )
        return estado.copy(
            pantalla = Pantalla.TABLERO,
            tablero = tablero,
            participantes =
                participantes.map {
                    it.copy(posicion = 0, turnoMiembro = 0, puntos = 0)
                },
            turno = 0,
            dado = null,
            origen = 0,
            destino = 0,
            juego = null,
            prueba = null,
            esPruebaFinal = false,
            superada = false,
            avanceExtra = emptyList(),
            ganador = null,
            partidaEnCurso = true,
            rondaSolitario = 0,
            puntosSolitario = 0,
            esRecordSolitario = false,
        )
    }

    /** Arranca el Reto en solitario: diez pruebas seguidas y una marca personal. */
    fun empezarSolitario(estado: EstadoJuego, jugador: Participante): EstadoJuego {
        val base =
            estado.copy(
                modo = Modo.SOLITARIO,
                participantes = listOf(jugador.copy(posicion = 0, puntos = 0)),
                tablero = emptyList(),
                turno = 0,
                ganador = null,
                partidaEnCurso = true,
                rondaSolitario = 1,
                rondasSolitario = estado.ajustes.pruebasSolitario,
                puntosSolitario = 0,
                esRecordSolitario = false,
                avanceExtra = emptyList(),
            )
        return prepararPruebaSolitario(base)
    }

    private fun prepararPruebaSolitario(estado: EstadoJuego): EstadoJuego {
        val posibles = juegosDeLaPartida(estado)
        val juego = posibles.random(rnd)
        return estado.copy(
            pantalla = Pantalla.ENTREGA,
            juego = juego,
            prueba = repartidor.repartirConAlternativas(juego, posibles),
            dado = null,
            origen = 0,
            destino = 0,
            esPruebaFinal = estado.rondaSolitario >= estado.rondasSolitario,
            superada = false,
        )
    }

    // ------------------------------------------------------------- el turno

    /** El participante activo tira el dado (1-3) y se decide qué prueba le toca. */
    fun lanzarDado(estado: EstadoJuego): EstadoJuego {
        val participante = estado.participanteActivo ?: return estado
        val dado = (1..CARAS_DEL_DADO).random(rnd)
        val meta = estado.meta
        val origen = participante.posicion
        val destino = (origen + dado).coerceAtMost(meta)
        val esFinal = destino >= meta
        val casilla = estado.tablero.getOrNull(destino)
        val posibles = juegosDeLaPartida(estado)

        val juego =
            when {
                esFinal -> posibles.random(rnd)
                casilla?.tipo == TipoCasilla.TODOS ->
                    posibles
                        .filter { it in Juego.PARA_RONDA_DE_TODOS }
                        .ifEmpty { Juego.PARA_RONDA_DE_TODOS }
                        .random(rnd)
                // En una casilla comodín todavía no hay juego: lo elige el rival.
                casilla?.tipo == TipoCasilla.COMODIN -> null
                else -> casilla?.juego
            }

        return estado.copy(
            dado = dado,
            origen = origen,
            destino = destino,
            esPruebaFinal = esFinal,
            juego = juego,
            prueba = juego?.let { repartidor.repartirConAlternativas(it, posibles) },
        )
    }

    /** Tras ver la tirada en el tablero se pasa a la prueba, o a elegirla. */
    fun continuarTrasDado(estado: EstadoJuego): EstadoJuego =
        estado.copy(
            pantalla = if (estado.juego == null) Pantalla.COMODIN else Pantalla.ENTREGA,
        )

    /** En las casillas comodín es el participante rival quien elige la prueba. */
    fun elegirJuego(estado: EstadoJuego, juego: Juego): EstadoJuego =
        estado.copy(
            juego = juego,
            prueba = repartidor.repartirConAlternativas(juego, juegosDeLaPartida(estado)),
            pantalla = Pantalla.ENTREGA,
        )

    fun empezarPrueba(estado: EstadoJuego): EstadoJuego =
        estado.copy(
            pantalla = if (estado.esRondaDeTodos) Pantalla.RONDA_TODOS else Pantalla.PRUEBA,
        )

    // ---------------------------------------------------------- resultados

    /**
     * Cierra una prueba normal.
     *
     * [puntos] es lo que ha conseguido: para las pruebas encadenadas (mímica,
     * tabú, verdadero o falso) es el número de aciertos, y para las de una sola
     * respuesta es 1 o 0. Sirve para desempatar la clasificación y para la marca
     * del solitario, no para avanzar más casillas: en el tablero se avanza o no
     * se avanza, sin medias tintas.
     */
    fun resolverPrueba(estado: EstadoJuego, superada: Boolean, puntos: Int = -1): EstadoJuego {
        val conseguidos =
            if (puntos >= 0) {
                puntos
            } else if (superada) {
                1
            } else {
                0
            }

        if (estado.modo == Modo.SOLITARIO) {
            return estado.copy(
                pantalla = Pantalla.RESULTADO,
                superada = superada,
                puntosSolitario = estado.puntosSolitario + conseguidos,
                participantes =
                    estado.participantes.map {
                        it.copy(puntos = it.puntos + conseguidos)
                    },
            )
        }

        val participante = estado.participanteActivo ?: return estado
        var actualizado =
            participante.copy(
                posicion = if (superada) estado.destino else estado.origen,
                puntos = participante.puntos + conseguidos,
            )
        // Quien actúa rota solo si de verdad ha actuado alguien.
        if (estado.juego?.soloActuante == true) {
            actualizado = actualizado.copy(turnoMiembro = actualizado.turnoMiembro + 1)
        }
        val participantes =
            estado.participantes
                .toMutableList()
                .also { it[estado.turno] = actualizado }

        return estado.copy(
            participantes = participantes,
            superada = superada,
            ganador = if (superada && estado.esPruebaFinal) actualizado else null,
            avanceExtra = emptyList(),
            pantalla = Pantalla.RESULTADO,
        )
    }

    /**
     * Cierra una casilla de «juegan todos»: cada participante que acierta avanza
     * una casilla, pero nadie gana la partida por aquí. Se frena justo antes de
     * la meta, porque ganar hay que ganarlo con la prueba final.
     */
    fun resolverRondaDeTodos(estado: EstadoJuego, aciertos: List<Boolean>): EstadoJuego {
        val topeSinMeta = (estado.meta - 1).coerceAtLeast(0)
        val participantes =
            estado.participantes.mapIndexed { i, participante ->
                val base = if (i == estado.turno) estado.destino else participante.posicion
                val acierta = aciertos.getOrElse(i) { false }
                val avance = if (acierta) 1 else 0
                participante.copy(
                    posicion = (base + avance).coerceIn(0, maxOf(topeSinMeta, base)),
                    puntos = participante.puntos + avance,
                )
            }
        return estado.copy(
            participantes = participantes,
            superada = aciertos.getOrElse(estado.turno) { false },
            avanceExtra = aciertos.mapIndexedNotNull { i, ok -> if (ok) i else null },
            ganador = null,
            pantalla = Pantalla.RESULTADO,
        )
    }

    /** Pasa el turno, o cierra la partida si ya hay ganador. */
    fun siguienteTurno(estado: EstadoJuego): EstadoJuego {
        if (estado.modo == Modo.SOLITARIO) return siguienteRondaSolitario(estado)

        if (estado.ganador != null) {
            return estado.copy(pantalla = Pantalla.VICTORIA, partidaEnCurso = false)
        }
        return estado.copy(
            turno =
                if (estado.participantes.isEmpty()) {
                    0
                } else {
                    (estado.turno + 1) % estado.participantes.size
                },
            dado = null,
            juego = null,
            prueba = null,
            esPruebaFinal = false,
            avanceExtra = emptyList(),
            pantalla = Pantalla.TABLERO,
        )
    }

    private fun siguienteRondaSolitario(estado: EstadoJuego): EstadoJuego {
        if (estado.rondaSolitario >= estado.rondasSolitario) {
            val marcaAnterior = estado.ajustes.mejorMarcaSolitario
            val esRecord = estado.puntosSolitario > marcaAnterior
            return estado.copy(
                pantalla = Pantalla.SOLITARIO_FIN,
                partidaEnCurso = false,
                esRecordSolitario = esRecord,
                ajustes =
                    if (esRecord) {
                        estado.ajustes.copy(mejorMarcaSolitario = estado.puntosSolitario)
                    } else {
                        estado.ajustes
                    },
            )
        }
        return prepararPruebaSolitario(estado.copy(rondaSolitario = estado.rondaSolitario + 1))
    }

    companion object {
        const val CARAS_DEL_DADO = 3
    }
}
