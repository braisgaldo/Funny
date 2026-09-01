package es.ghatostudio.funny.dominio

import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Las reglas de Funny, probadas sin emulador.
 *
 * Es posible porque `MotorJuego` es Kotlin puro: no hay `Context`, ni
 * `ViewModel`, ni Compose. Esa fue la razón de sacarlo del ViewModel, y este
 * fichero es la prueba de que valió la pena.
 */
class PruebaMotorJuego {
    // Semilla fija: los tests no pueden depender de la suerte.
    private val rnd = Random(20260901)

    private val contenidoDePrueba =
        Contenido(
            mimica = List(30) { "mimica $it" },
            dibujo = List(30) { "dibujo $it" },
            eventos = List(20) { EventoCuando("evento $it", 1900 + it) },
            preguntas =
                List(
                    20,
                ) { PreguntaTrivial("pregunta $it", listOf("a", "b", "c", "d"), it % 4) },
            tabu = List(20) { CartaTabu("palabra $it", listOf("p1", "p2", "p3", "p4")) },
            retos = List(20) { RetoRapido("reto $it", 5) },
            emojis = List(20) { CartaEmojis("🎬", "peli $it", listOf("s1", "s2", "s3")) },
            afirmaciones = List(20) { Afirmacion("afirmacion $it", it % 2 == 0, "porque") },
            trabalenguas = List(20) { Trabalenguas("trabalenguas $it", 2) },
            ordenar = List(20) { RetoOrdenar("ordena $it", listOf("a", "b", "c", "d")) },
            canciones = List(20) { Cancion("cancion $it", "artista", "pista") },
            desafios = List(20) { Desafio("desafio $it") },
        )

    private fun motor(contenido: Contenido = contenidoDePrueba) =
        MotorJuego(
            contenido = contenido,
            repartidor = Repartidor(contenido, rnd),
            rnd = rnd,
        )

    private fun equipos(cuantos: Int = 3) =
        List(cuantos) { Participante(id = it + 1, nombre = "Equipo ${it + 1}", indiceColor = it) }

    private fun partidaEmpezada(cuantos: Int = 3): Pair<MotorJuego, EstadoJuego> {
        val m = motor()
        val estado =
            m.empezarCarrera(
                EstadoJuego(modo = Modo.EQUIPOS, participantes = equipos(cuantos)),
                equipos(cuantos),
            )
        return m to estado
    }

    // ------------------------------------------------------------- el tablero

    @Test
    fun `el tablero empieza en salida y acaba en meta`() {
        val (_, estado) = partidaEmpezada()
        assertEquals(TipoCasilla.SALIDA, estado.tablero.first().tipo)
        assertEquals(TipoCasilla.META, estado.tablero.last().tipo)
    }

    @Test
    fun `el tablero tiene la longitud que dicen los ajustes`() {
        Duracion.entries.forEach { duracion ->
            val m = motor()
            val estado =
                m.empezarCarrera(
                    EstadoJuego(ajustes = Ajustes(duracion = duracion), participantes = equipos()),
                    equipos(),
                )
            // La longitud es «casillas» tramos, o sea casillas + 1 posiciones
            // contando la salida.
            assertEquals(duracion.casillas + 1, estado.tablero.size, "$duracion")
        }
    }

    @Test
    fun `todas las casillas normales tienen un juego y las especiales no`() {
        val (_, estado) = partidaEmpezada()
        estado.tablero.forEach { casilla ->
            if (casilla.tipo == TipoCasilla.NORMAL) {
                assertNotNull(casilla.juego, "la casilla ${casilla.indice} no tiene juego")
            } else {
                assertNull(casilla.juego, "la casilla ${casilla.indice} no debería tener juego")
            }
        }
    }

    @Test
    fun `el tablero solo usa juegos activos`() {
        val desactivados = setOf(Juego.MIMICA, Juego.DIBUJO, Juego.TABU, Juego.CANTA)
        val m = motor()
        val estado =
            m.empezarCarrera(
                EstadoJuego(
                    ajustes = Ajustes(juegosDesactivados = desactivados),
                    participantes = equipos(),
                ),
                equipos(),
            )
        val usados = estado.tablero.mapNotNull { it.juego }.toSet()
        assertTrue(
            usados.none { it in desactivados },
            "han salido juegos desactivados: ${usados.intersect(desactivados)}",
        )
        assertTrue(usados.isNotEmpty(), "el tablero se ha quedado sin juegos")
    }

    @Test
    fun `desactivar todos los juegos no deja el tablero vacio`() {
        // Preferimos ignorar la preferencia a generar una partida imposible.
        val m = motor()
        val estado =
            m.empezarCarrera(
                EstadoJuego(
                    ajustes = Ajustes(juegosDesactivados = Juego.entries.toSet()),
                    participantes = equipos(),
                ),
                equipos(),
            )
        assertTrue(estado.tablero.any { it.juego != null })
    }

    // ---------------------------------------------------------------- el dado

    @Test
    fun `el dado siempre sale entre uno y tres`() {
        var (m, estado) = partidaEmpezada()
        repeat(200) {
            estado = m.lanzarDado(estado)
            val dado = estado.dado
            assertNotNull(dado)
            assertTrue(dado in 1..MotorJuego.CARAS_DEL_DADO, "ha salido $dado")
            estado = estado.copy(dado = null)
        }
    }

    @Test
    fun `el destino nunca pasa de la meta`() {
        var (m, estado) = partidaEmpezada()
        // Se coloca al participante justo antes de la meta.
        estado =
            estado.copy(
                participantes =
                    estado.participantes.mapIndexed { i, p ->
                        if (i == 0) p.copy(posicion = estado.meta - 1) else p
                    },
            )
        repeat(50) {
            val siguiente = m.lanzarDado(estado)
            assertTrue(
                siguiente.destino <= siguiente.meta,
                "destino ${siguiente.destino} con meta ${siguiente.meta}",
            )
        }
    }

    @Test
    fun `caer en la meta marca la prueba como final`() {
        val (m, base) = partidaEmpezada()
        val estado =
            base.copy(
                participantes =
                    base.participantes.mapIndexed { i, p ->
                        if (i == 0) p.copy(posicion = base.meta - 1) else p
                    },
            )
        val tirado = m.lanzarDado(estado)
        assertTrue(tirado.esPruebaFinal)
        assertNotNull(tirado.juego, "la prueba final tiene que traer un juego")
    }

    @Test
    fun `una casilla comodin deja el juego sin decidir`() {
        val (m, base) = partidaEmpezada()
        val comodin = base.tablero.first { it.tipo == TipoCasilla.COMODIN }
        val estado =
            base.copy(
                participantes =
                    base.participantes.mapIndexed { i, p ->
                        if (i == 0) p.copy(posicion = comodin.indice - 1) else p
                    },
            )
        // Se fuerza el dado a 1 recolocando y comprobando solo cuando cae ahí.
        var tirado = m.lanzarDado(estado)
        var intentos = 0
        while (tirado.destino != comodin.indice && intentos < 100) {
            tirado = m.lanzarDado(estado)
            intentos++
        }
        if (tirado.destino == comodin.indice && !tirado.esPruebaFinal) {
            assertNull(tirado.juego, "el comodín no debería traer juego decidido")
            assertEquals(Pantalla.COMODIN, m.continuarTrasDado(tirado).pantalla)
        }
    }

    @Test
    fun `una ronda de todos solo elige juegos que la app pueda verificar`() {
        val (m, base) = partidaEmpezada()
        val todos = base.tablero.firstOrNull { it.tipo == TipoCasilla.TODOS } ?: return
        val estado =
            base.copy(
                participantes =
                    base.participantes.mapIndexed { i, p ->
                        if (i == 0) p.copy(posicion = todos.indice - 1) else p
                    },
            )
        repeat(60) {
            val tirado = m.lanzarDado(estado)
            if (tirado.destino == todos.indice && !tirado.esPruebaFinal) {
                val juego = tirado.juego
                assertNotNull(juego)
                assertTrue(
                    juego in Juego.PARA_RONDA_DE_TODOS,
                    "$juego no vale para una ronda de todos",
                )
            }
        }
    }

    // ----------------------------------------------------------- resoluciones

    @Test
    fun `superar la prueba avanza y fallarla devuelve al origen`() {
        val (m, base) = partidaEmpezada()
        val tirado = m.lanzarDado(base)

        val superada = m.resolverPrueba(tirado, superada = true)
        assertEquals(tirado.destino, superada.participantes[0].posicion)

        val fallada = m.resolverPrueba(tirado, superada = false)
        assertEquals(tirado.origen, fallada.participantes[0].posicion)
    }

    @Test
    fun `los puntos se acumulan aunque la prueba se falle`() {
        val (m, base) = partidaEmpezada()
        val tirado = m.lanzarDado(base)
        val resuelto = m.resolverPrueba(tirado, superada = false, puntos = 3)
        assertEquals(3, resuelto.participantes[0].puntos)
    }

    @Test
    fun `superar la prueba final proclama ganador`() {
        val (m, base) = partidaEmpezada()
        val enMeta =
            base.copy(
                destino = base.meta,
                origen = base.meta - 1,
                esPruebaFinal = true,
                juego = Juego.PREGUNTAS,
            )
        val resuelto = m.resolverPrueba(enMeta, superada = true)
        assertNotNull(resuelto.ganador)
        assertEquals(Pantalla.VICTORIA, m.siguienteTurno(resuelto).pantalla)
        assertFalse(m.siguienteTurno(resuelto).partidaEnCurso)
    }

    @Test
    fun `fallar la prueba final no proclama ganador`() {
        val (m, base) = partidaEmpezada()
        val enMeta = base.copy(destino = base.meta, esPruebaFinal = true, juego = Juego.PREGUNTAS)
        assertNull(m.resolverPrueba(enMeta, superada = false).ganador)
    }

    @Test
    fun `en la ronda de todos avanza cada acertante y nadie gana`() {
        val (m, base) = partidaEmpezada(3)
        val estado =
            base.copy(
                turno = 0,
                destino = 3,
                tablero = base.tablero,
            )
        val resuelto = m.resolverRondaDeTodos(estado, listOf(true, false, true))

        assertEquals(listOf(0, 2), resuelto.avanceExtra)
        assertNull(resuelto.ganador, "nadie puede ganar en una ronda de todos")
        // Nadie llega a la meta por aquí.
        resuelto.participantes.forEach { p ->
            assertTrue(p.posicion < resuelto.meta, "${p.nombre} ha llegado a la meta")
        }
    }

    @Test
    fun `quien actua rota solo en los juegos de una sola persona`() {
        val (m, base) = partidaEmpezada(2)
        val conMiembros =
            base.copy(
                participantes =
                    base.participantes.mapIndexed { i, p ->
                        if (i == 0) p.copy(miembros = listOf("Ana", "Bea", "Caro")) else p
                    },
            )

        val trasMimica =
            m.resolverPrueba(
                conMiembros.copy(juego = Juego.MIMICA),
                superada = true,
            )
        assertEquals("Bea", trasMimica.participantes[0].quienActua)

        val trasPreguntas =
            m.resolverPrueba(
                conMiembros.copy(juego = Juego.PREGUNTAS),
                superada = true,
            )
        assertEquals("Ana", trasPreguntas.participantes[0].quienActua)
    }

    @Test
    fun `el turno da la vuelta al llegar al ultimo participante`() {
        val (m, base) = partidaEmpezada(3)
        var estado = base
        val turnos = mutableListOf<Int>()
        repeat(7) {
            turnos += estado.turno
            estado = m.siguienteTurno(estado)
        }
        assertEquals(listOf(0, 1, 2, 0, 1, 2, 0), turnos)
    }

    // ------------------------------------------------------------- solitario

    @Test
    fun `el solitario solo usa juegos que valen sin publico`() {
        val m = motor()
        var estado =
            m.empezarSolitario(
                EstadoJuego(modo = Modo.SOLITARIO),
                Participante(1, "Brais", 0),
            )
        repeat(RONDAS_SOLITARIO) {
            val juego = estado.juego
            assertNotNull(juego)
            assertTrue(juego.valeEnSolitario, "$juego no vale en solitario")
            estado = m.resolverPrueba(estado, superada = true, puntos = 1)
            estado = m.siguienteTurno(estado)
        }
    }

    @Test
    fun `el solitario dura las rondas que dice y acaba en su pantalla`() {
        val m = motor()
        var estado =
            m.empezarSolitario(
                EstadoJuego(modo = Modo.SOLITARIO),
                Participante(1, "Brais", 0),
            )
        assertEquals(1, estado.rondaSolitario)

        repeat(RONDAS_SOLITARIO) {
            estado = m.resolverPrueba(estado, superada = true, puntos = 2)
            estado = m.siguienteTurno(estado)
        }
        assertEquals(Pantalla.SOLITARIO_FIN, estado.pantalla)
        assertEquals(RONDAS_SOLITARIO * 2, estado.puntosSolitario)
        assertFalse(estado.partidaEnCurso)
    }

    @Test
    fun `el solitario guarda la marca solo si la bate`() {
        val m = motor()
        val conMarca =
            EstadoJuego(
                modo = Modo.SOLITARIO,
                ajustes = Ajustes(mejorMarcaSolitario = 15),
            )

        // Diez rondas de un punto: diez puntos, no bate la marca de quince.
        var flojo = m.empezarSolitario(conMarca, Participante(1, "Brais", 0))
        repeat(RONDAS_SOLITARIO) {
            flojo = m.resolverPrueba(flojo, superada = true, puntos = 1)
            flojo = m.siguienteTurno(flojo)
        }
        assertFalse(flojo.esRecordSolitario)
        assertEquals(15, flojo.ajustes.mejorMarcaSolitario)

        // Diez rondas de tres puntos: treinta, sí la bate.
        var bueno = m.empezarSolitario(conMarca, Participante(1, "Brais", 0))
        repeat(RONDAS_SOLITARIO) {
            bueno = m.resolverPrueba(bueno, superada = true, puntos = 3)
            bueno = m.siguienteTurno(bueno)
        }
        assertTrue(bueno.esRecordSolitario)
        assertEquals(RONDAS_SOLITARIO * 3, bueno.ajustes.mejorMarcaSolitario)
    }

    // -------------------------------------------------- contenido incompleto

    @Test
    fun `un juego sin contenido no rompe la partida`() {
        // Solo hay preguntas: el repartidor tiene que caer en ellas para todo.
        val soloPreguntas =
            Contenido(
                preguntas = List(10) { PreguntaTrivial("p$it", listOf("a", "b", "c", "d"), 0) },
            )
        val m = motor(soloPreguntas)
        var estado = m.empezarCarrera(EstadoJuego(participantes = equipos()), equipos())
        repeat(30) {
            estado = m.lanzarDado(estado)
            if (estado.juego != null) {
                assertNotNull(estado.prueba, "se ha quedado sin prueba con juego ${estado.juego}")
            }
            estado = m.resolverPrueba(estado, superada = true)
            estado = m.siguienteTurno(estado)
        }
    }

    @Test
    fun `el contenido vacio deja la lista de jugables vacia sin reventar`() {
        val vacio = Contenido()
        assertTrue(vacio.juegosJugables.isEmpty())
        assertEquals(0, vacio.total)
        Juego.entries.forEach { assertEquals(0, vacio.cantidadDe(it)) }
    }

    // ------------------------------------------------------- partida completa

    @Test
    fun `una partida entera termina con un ganador`() {
        val m = motor()
        var estado =
            m.empezarCarrera(
                EstadoJuego(
                    ajustes = Ajustes(duracion = Duracion.CORTA),
                    participantes = equipos(2),
                ),
                equipos(2),
            )
        var vueltas = 0
        while (estado.pantalla != Pantalla.VICTORIA && vueltas < 500) {
            vueltas++
            estado = m.lanzarDado(estado)
            estado = m.continuarTrasDado(estado)
            if (estado.pantalla == Pantalla.COMODIN) {
                estado = m.elegirJuego(estado, Juego.PREGUNTAS)
            }
            estado = m.empezarPrueba(estado)
            estado =
                if (estado.pantalla == Pantalla.RONDA_TODOS) {
                    m.resolverRondaDeTodos(estado, estado.participantes.map { true })
                } else {
                    m.resolverPrueba(estado, superada = true)
                }
            estado = m.siguienteTurno(estado)
        }
        assertEquals(Pantalla.VICTORIA, estado.pantalla, "no ha terminado en $vueltas vueltas")
        assertNotNull(estado.ganador)
    }
}
