package es.ghatostudio.funny.dominio

import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/** Las funciones puras de `Reglas.kt` y el mazo. */
class PruebaReglas {
    private val rnd = Random(1492)

    // ---------------------------------------------------- opciones de año

    @Test
    fun `las opciones de anio son cuatro, distintas, ordenadas y contienen la correcta`() {
        listOf(476, 1066, 1492, 1789, 1969, 2001, 2020).forEach { anio ->
            repeat(50) {
                val opciones = opcionesDeAnio(anio, rnd)
                assertEquals(4, opciones.size, "para $anio")
                assertEquals(4, opciones.toSet().size, "hay repetidos para $anio: $opciones")
                assertTrue(anio in opciones, "falta el año correcto $anio en $opciones")
                assertEquals(opciones.sorted(), opciones, "no está ordenado: $opciones")
            }
        }
    }

    @Test
    fun `el margen de las opciones se estrecha en las epocas recientes`() {
        // Un hecho medieval admite opciones muy separadas; uno de hace cinco
        // años, no. Si el margen fuera fijo, la mitad de las cartas serían
        // regaladas y la otra mitad imposibles.
        fun dispersion(anio: Int): Int {
            val medidas =
                (1..200).map {
                    val o = opcionesDeAnio(anio, rnd)
                    o.max() - o.min()
                }
            return medidas.max()
        }
        assertTrue(
            dispersion(1200) > dispersion(2015),
            "medieval ${dispersion(1200)} contra reciente ${dispersion(2015)}",
        )
    }

    @Test
    fun `las opciones de anio nunca proponen un anio negativo`() {
        repeat(200) {
            opcionesDeAnio(30, rnd).forEach { assertTrue(it >= 1, "ha salido $it") }
        }
    }

    // ------------------------------------------------------------ desordenar

    @Test
    fun `desordenar nunca devuelve el orden original`() {
        val original = listOf("a", "b", "c", "d")
        repeat(300) {
            assertNotEquals(original, desordenar(original, rnd))
        }
    }

    @Test
    fun `desordenar conserva todos los elementos`() {
        val original = listOf("uno", "dos", "tres", "cuatro")
        repeat(100) {
            assertEquals(original.toSet(), desordenar(original, rnd).toSet())
        }
    }

    @Test
    fun `desordenar aguanta listas de cero y un elemento`() {
        assertEquals(emptyList(), desordenar(emptyList<String>(), rnd))
        assertEquals(listOf("solo"), desordenar(listOf("solo"), rnd))
    }

    // ------------------------------------------------------------------ mazo

    @Test
    fun `el mazo reparte todas las cartas antes de repetir ninguna`() {
        val cartas = (1..10).toList()
        val mazo = Mazo(cartas, rnd)
        val sacadas = (1..10).mapNotNull { mazo.sacar() }
        assertEquals(cartas.toSet(), sacadas.toSet(), "no ha repartido las diez sin repetir")
    }

    @Test
    fun `el mazo se rebaraja al agotarse`() {
        val mazo = Mazo(listOf(1, 2, 3), rnd)
        val sacadas = (1..9).mapNotNull { mazo.sacar() }
        assertEquals(9, sacadas.size)
        assertEquals(setOf(1, 2, 3), sacadas.toSet())
    }

    @Test
    fun `un mazo vacio devuelve null y una lista vacia`() {
        val mazo = Mazo(emptyList<String>(), rnd)
        assertNull(mazo.sacar())
        assertTrue(mazo.sacar(5).isEmpty())
    }

    @Test
    fun `sacar varias no repite dentro de la misma tirada`() {
        val mazo = Mazo((1..8).toList(), rnd)
        repeat(20) {
            val tirada = mazo.sacar(5)
            assertEquals(tirada.size, tirada.toSet().size, "hay repetidos en $tirada")
        }
    }

    @Test
    fun `sacar mas cartas de las que hay devuelve las que hay`() {
        // Es preferible una prueba corta a una prueba con la misma palabra dos
        // veces, que es lo que pasaría al rebarajar a mitad de tirada.
        val mazo = Mazo(listOf("a", "b", "c"), rnd)
        val tirada = mazo.sacar(10)
        assertEquals(3, tirada.size)
        assertEquals(setOf("a", "b", "c"), tirada.toSet())
    }

    // ------------------------------------------------------------ colores

    @Test
    fun `coloresDisponibles devuelve el primer hueco libre`() {
        assertEquals(0, coloresDisponibles(emptySet()))
        assertEquals(2, coloresDisponibles(setOf(0, 1)))
        assertEquals(1, coloresDisponibles(setOf(0, 2, 3)))
        assertNull(coloresDisponibles((0 until MAXIMO_PARTICIPANTES).toSet()))
    }

    // ---------------------------------------------------- los dieciocho juegos

    @Test
    fun `hay dieciocho juegos y todos tienen clave distinta`() {
        assertEquals(18, Juego.entries.size)
        assertEquals(
            18,
            Juego.entries
                .map { it.clave }
                .toSet()
                .size,
            "hay claves repetidas",
        )
        assertEquals(
            18,
            Juego.entries
                .map { it.emoji }
                .toSet()
                .size,
            "hay emojis repetidos",
        )
    }

    @Test
    fun `porClave encuentra los dieciocho y rechaza lo que no existe`() {
        Juego.entries.forEach { assertEquals(it, Juego.porClave(it.clave)) }
        assertNull(Juego.porClave("no-existe"))
        assertNull(Juego.porClave(""))
    }

    @Test
    fun `hay juegos suficientes para el solitario y para la ronda de todos`() {
        // Si alguno de estos dos conjuntos se quedara vacío, un modo entero
        // dejaría de funcionar sin que nada más se rompiera.
        assertTrue(Juego.EN_SOLITARIO.size >= 5, "solo ${Juego.EN_SOLITARIO.size} en solitario")
        assertTrue(
            Juego.PARA_RONDA_DE_TODOS.size >= 3,
            "solo ${Juego.PARA_RONDA_DE_TODOS.size} para la ronda de todos",
        )
    }

    @Test
    fun `ningun juego que necesite publico vale en solitario`() {
        Juego.entries.filter { it.valeEnSolitario }.forEach { juego ->
            assertTrue(
                !juego.soloActuante || juego.veredictoDeLaMesa,
                "$juego vale en solitario y necesita que alguien adivine",
            )
        }
    }

    @Test
    fun `los tiempos de todos los juegos son razonables`() {
        Juego.entries.forEach { juego ->
            assertTrue(
                juego.segundosBase in 20..120,
                "$juego dura ${juego.segundosBase} s",
            )
        }
    }

    @Test
    fun `el ritmo escala los tiempos y nunca baja de diez segundos`() {
        Ritmo.entries.forEach { ritmo ->
            val estado = EstadoJuego(ajustes = Ajustes(ritmo = ritmo))
            Juego.entries.forEach { juego ->
                val segundos = estado.segundosDe(juego)
                assertTrue(segundos >= 10, "$juego a ritmo $ritmo da $segundos s")
            }
        }
        val rapido = EstadoJuego(ajustes = Ajustes(ritmo = Ritmo.RAPIDO))
        val tranquilo = EstadoJuego(ajustes = Ajustes(ritmo = Ritmo.TRANQUILO))
        assertTrue(rapido.segundosDe(Juego.MIMICA) < tranquilo.segundosDe(Juego.MIMICA))
    }

    // ------------------------------------------------------- participantes

    @Test
    fun `un participante sin nombre usa el nombre por defecto`() {
        val sinNombre = Participante(1, "", 0)
        assertEquals("Equipo 1", sinNombre.nombreVisible("Equipo 1"))
        val conNombre = Participante(1, "Los Cracks", 0)
        assertEquals("Los Cracks", conNombre.nombreVisible("Equipo 1"))
    }

    @Test
    fun `quien actua rota por los miembros y da la vuelta`() {
        val equipo = Participante(1, "E", 0, miembros = listOf("Ana", "Bea"))
        assertEquals("Ana", equipo.quienActua)
        assertEquals("Bea", equipo.copy(turnoMiembro = 1).quienActua)
        assertEquals("Ana", equipo.copy(turnoMiembro = 2).quienActua)
        // Un turno negativo no debería ocurrir, pero `mod` lo aguanta.
        assertEquals("Bea", equipo.copy(turnoMiembro = -1).quienActua)
    }

    @Test
    fun `un participante sin miembros no tiene quien actue`() {
        assertNull(Participante(1, "Solo", 0).quienActua)
        assertEquals("Solo", Participante(1, "Solo", 0).nombreDeQuienActua("Jugador 1"))
    }

    @Test
    fun `los ocho emojis de participante no se repiten`() {
        assertEquals(MAXIMO_PARTICIPANTES, EMOJIS_PARTICIPANTE.size)
        assertEquals(EMOJIS_PARTICIPANTE.size, EMOJIS_PARTICIPANTE.toSet().size)
    }

    @Test
    fun `quien elige en un comodin es el siguiente en el orden`() {
        val estado =
            EstadoJuego(
                participantes = List(3) { Participante(it + 1, "P${it + 1}", it) },
                turno = 2,
            )
        assertEquals(1, estado.quienElige?.id, "con turno 2 de 3 debería elegir el primero")
        assertNull(EstadoJuego(participantes = listOf(Participante(1, "P", 0))).quienElige)
    }

    @Test
    fun `la clasificacion ordena por posicion y desempata por puntos`() {
        val estado =
            EstadoJuego(
                participantes =
                    listOf(
                        Participante(1, "A", 0, posicion = 5, puntos = 2),
                        Participante(2, "B", 1, posicion = 8, puntos = 1),
                        Participante(3, "C", 2, posicion = 5, puntos = 9),
                    ),
            )
        assertEquals(listOf("B", "C", "A"), estado.clasificacion.map { it.nombre })
    }

    // ------------------------------------------------------------ ajustes

    @Test
    fun `los modos tienen limites coherentes`() {
        Modo.entries.forEach { modo ->
            assertTrue(
                modo.minimoParticipantes <= modo.maximoParticipantes,
                "$modo: mínimo ${modo.minimoParticipantes}, máximo ${modo.maximoParticipantes}",
            )
            assertTrue(
                modo.maximoParticipantes <= MAXIMO_PARTICIPANTES,
                "$modo pide ${modo.maximoParticipantes} y solo hay $MAXIMO_PARTICIPANTES colores",
            )
        }
        assertEquals(1, Modo.SOLITARIO.minimoParticipantes)
        assertEquals(1, Modo.SOLITARIO.maximoParticipantes)
    }

    @Test
    fun `solo el solitario no es una carrera`() {
        assertTrue(Modo.EQUIPOS.esCarrera)
        assertTrue(Modo.INDIVIDUAL.esCarrera)
        assertTrue(!Modo.SOLITARIO.esCarrera)
    }
}
