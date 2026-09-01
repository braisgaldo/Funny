package es.ghatostudio.funny.dominio

import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Las cuatro modalidades de partida.
 *
 * Lo que se comprueba aquí no es que los números sean 12, 20 y 32 —eso se
 * puede cambiar— sino que sigan cumpliendo las tres cosas que las hacen útiles:
 * que se diferencien de verdad en duración, que la personalizada pueda llegar
 * exactamente a cualquiera de las tres, y que un número absurdo guardado en
 * disco no llegue nunca al tablero.
 */
class PruebaModalidad {
    private val predefinidas get() = Modalidad.PREDEFINIDAS

    // Semilla fija: los tests no pueden depender de la suerte.
    private val rnd = Random(20260901)

    /** Un mazo por juego, que es lo que el motor necesita para armar un tablero. */
    private val contenido =
        Contenido(
            mimica = List(30) { "mimica $it" },
            dibujo = List(30) { "dibujo $it" },
            eventos = List(20) { EventoCuando("evento $it", 1900 + it) },
            preguntas = List(20) { PreguntaTrivial("pregunta $it", listOf("a", "b", "c", "d"), 0) },
            tabu = List(20) { CartaTabu("palabra $it", listOf("p1", "p2", "p3", "p4")) },
            retos = List(20) { RetoRapido("reto $it", 5) },
            emojis = List(20) { CartaEmojis("🎬", "peli $it", listOf("s1", "s2", "s3")) },
            afirmaciones = List(20) { Afirmacion("afirmacion $it", it % 2 == 0, "porque") },
            trabalenguas = List(20) { Trabalenguas("trabalenguas $it", 2) },
            ordenar = List(20) { RetoOrdenar("ordena $it", listOf("a", "b", "c", "d")) },
            canciones = List(20) { Cancion("cancion $it", "artista", "pista") },
            desafios = List(20) { Desafio("desafio $it") },
        )

    private fun motor() =
        MotorJuego(contenido = contenido, repartidor = Repartidor(contenido, rnd), rnd = rnd)

    @Test
    fun `hay tres modalidades predefinidas y una personalizada`() {
        assertEquals(3, predefinidas.size, "predefinidas: $predefinidas")
        assertEquals(
            listOf(Modalidad.RAPIDA, Modalidad.NORMAL, Modalidad.EXTREMA),
            predefinidas,
            "el orden en el que se ofrecen importa: de la más corta a la más larga",
        )
        assertEquals(1, Modalidad.entries.count { it.esPersonalizada })
        assertTrue(Modalidad.PERSONALIZADA.esPersonalizada)
    }

    @Test
    fun `las tres predefinidas se diferencian de verdad en duracion`() {
        // Si dos modalidades dieran partidas parecidas, elegir entre ellas no
        // significaría nada. Se exige que cada una sea al menos un cuarto más
        // larga que la anterior, en casillas y en pruebas.
        predefinidas.zipWithNext { corta, larga ->
            val casillasCorta = corta.casillas!!
            val casillasLarga = larga.casillas!!
            assertTrue(
                casillasLarga >= casillasCorta * 5 / 4,
                "$corta tiene $casillasCorta casillas y $larga solo $casillasLarga",
            )
            val pruebasCorta = corta.pruebas!!
            val pruebasLarga = larga.pruebas!!
            assertTrue(
                pruebasLarga >= pruebasCorta * 5 / 4,
                "$corta tiene $pruebasCorta pruebas y $larga solo $pruebasLarga",
            )
        }
    }

    @Test
    fun `solo la personalizada deja los numeros sin fijar`() {
        predefinidas.forEach {
            assertNotNull(it.casillas, "$it no dice cuántas casillas")
            assertNotNull(it.pruebas, "$it no dice cuántas pruebas")
        }
        assertNull(Modalidad.PERSONALIZADA.casillas)
        assertNull(Modalidad.PERSONALIZADA.pruebas)
    }

    @Test
    fun `los pasos numericos pueden llegar exactamente a cualquier predefinida`() {
        // Quien elija «a mi manera» tiene que poder reproducir la partida
        // normal con los botones. Si un preajuste cayera entre dos pasos, no
        // habría forma de volver a él sin salir de la personalizada.
        predefinidas.forEach { modalidad ->
            val casillas = modalidad.casillas!!
            assertTrue(
                casillas in Modalidad.CASILLAS_POSIBLES,
                "$casillas casillas de $modalidad se salen de ${Modalidad.CASILLAS_POSIBLES}",
            )
            assertEquals(
                0,
                (casillas - Modalidad.CASILLAS_POSIBLES.first) % Modalidad.PASO_CASILLAS,
                "no se puede llegar a $casillas casillas de $modalidad de dos en dos",
            )
            val pruebas = modalidad.pruebas!!
            assertTrue(
                pruebas in Modalidad.PRUEBAS_POSIBLES,
                "$pruebas pruebas de $modalidad se salen de ${Modalidad.PRUEBAS_POSIBLES}",
            )
            assertEquals(
                0,
                (pruebas - Modalidad.PRUEBAS_POSIBLES.first) % Modalidad.PASO_PRUEBAS,
                "no se puede llegar a $pruebas pruebas de $modalidad de dos en dos",
            )
        }
    }

    @Test
    fun `cada modalidad tiene nombre, detalle y emoji propios`() {
        assertEquals(
            Modalidad.entries.size,
            Modalidad.entries
                .map { it.claveNombre }
                .toSet()
                .size,
            "hay nombres repetidos",
        )
        assertEquals(
            Modalidad.entries.size,
            Modalidad.entries
                .map { it.claveDetalle }
                .toSet()
                .size,
            "hay detalles repetidos",
        )
        assertEquals(
            Modalidad.entries.size,
            Modalidad.entries
                .map { it.emoji }
                .toSet()
                .size,
            "hay emojis repetidos",
        )
    }

    @Test
    fun `los minutos aproximados crecen con las casillas y son razonables`() {
        val minutos = Modalidad.CASILLAS_POSIBLES.map { Modalidad.minutosAproximados(it) }
        assertEquals(minutos.sorted(), minutos, "los minutos no crecen: $minutos")
        assertTrue(minutos.first() >= 5, "la partida más corta anuncia ${minutos.first()} min")
        assertTrue(minutos.last() <= 120, "la más larga anuncia ${minutos.last()} min")
    }

    // ------------------------------------------------------------- ajustes

    @Test
    fun `con una predefinida manda la modalidad y no los numeros guardados`() {
        // Quien tocó los pasos numéricos y luego volvió a «partida normal»
        // espera una partida normal, no la suya con otro nombre.
        val ajustes =
            Ajustes(
                modalidad = Modalidad.NORMAL,
                casillasPersonalizadas = 40,
                pruebasPersonalizadas = 24,
            )
        assertEquals(Modalidad.NORMAL.casillas, ajustes.casillas)
        assertEquals(Modalidad.NORMAL.pruebas, ajustes.pruebasSolitario)
    }

    @Test
    fun `con la personalizada mandan los numeros guardados`() {
        val ajustes =
            Ajustes(
                modalidad = Modalidad.PERSONALIZADA,
                casillasPersonalizadas = 14,
                pruebasPersonalizadas = 8,
            )
        assertEquals(14, ajustes.casillas)
        assertEquals(8, ajustes.pruebasSolitario)
    }

    @Test
    fun `un numero absurdo se recorta en lugar de llegar al tablero`() {
        // Estos dos valores no salen de la interfaz, que no deja pasar de los
        // extremos: salen de unas preferencias en disco o de un fichero de
        // copia editado a mano.
        val enorme =
            Ajustes(
                modalidad = Modalidad.PERSONALIZADA,
                casillasPersonalizadas = 5_000,
                pruebasPersonalizadas = 5_000,
            )
        assertEquals(Modalidad.CASILLAS_POSIBLES.last, enorme.casillas)
        assertEquals(Modalidad.PRUEBAS_POSIBLES.last, enorme.pruebasSolitario)

        val negativo =
            Ajustes(
                modalidad = Modalidad.PERSONALIZADA,
                casillasPersonalizadas = -7,
                pruebasPersonalizadas = 0,
            )
        assertEquals(Modalidad.CASILLAS_POSIBLES.first, negativo.casillas)
        assertEquals(Modalidad.PRUEBAS_POSIBLES.first, negativo.pruebasSolitario)
    }

    @Test
    fun `los ajustes recien instalados dan la partida normal`() {
        val ajustes = Ajustes()
        assertEquals(Modalidad.NORMAL, ajustes.modalidad)
        assertEquals(Modalidad.NORMAL.casillas, ajustes.casillas)
        assertEquals(Modalidad.NORMAL.pruebas, ajustes.pruebasSolitario)
    }

    // -------------------------------------------------------------- motor

    @Test
    fun `el tablero de la personalizada tiene las casillas que se le piden`() {
        listOf(
            Modalidad.CASILLAS_POSIBLES.first,
            20,
            Modalidad.CASILLAS_POSIBLES.last,
        ).forEach { casillas ->
            val ajustes =
                Ajustes(
                    modalidad = Modalidad.PERSONALIZADA,
                    casillasPersonalizadas = casillas,
                )
            val participantes = List(2) { Participante(it + 1, "P${it + 1}", it) }
            val estado =
                motor().empezarCarrera(
                    EstadoJuego(ajustes = ajustes, participantes = participantes),
                    participantes,
                )
            assertEquals(casillas + 1, estado.tablero.size, "con $casillas casillas")
        }
    }

    @Test
    fun `el solitario dura las pruebas que dice la modalidad`() {
        Modalidad.entries.forEach { modalidad ->
            val ajustes = Ajustes(modalidad = modalidad, pruebasPersonalizadas = 18)
            val jugador = Participante(1, "Ana", 0)
            val estado =
                motor().empezarSolitario(
                    EstadoJuego(ajustes = ajustes, participantes = listOf(jugador)),
                    jugador,
                )
            assertEquals(ajustes.pruebasSolitario, estado.rondasSolitario, "$modalidad")
        }
    }
}
