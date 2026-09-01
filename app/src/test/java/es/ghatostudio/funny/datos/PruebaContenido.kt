package es.ghatostudio.funny.datos

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import es.ghatostudio.funny.dominio.Juego
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * El contenido de los assets.
 *
 * Comprueba lo que un JSON mal escrito rompería sin avisar: que los doce juegos
 * tienen cartas en los dos idiomas con contenido propio, que las cartas están
 * bien formadas y que los índices de respuesta correcta apuntan a algo.
 *
 * Necesita Robolectric porque los assets se leen a través de un `Context`. Es
 * la única parte de la capa de datos que no se puede probar con Kotlin puro.
 */
@RunWith(RobolectricTestRunner::class)
class PruebaContenido {

    private val contexto: Context get() = ApplicationProvider.getApplicationContext()
    private val fuente get() = ContenidoDeAssets(contexto)

    private val idiomasConContenido = listOf("es", "en")

    @Test
    fun `los doce juegos tienen cartas en castellano y en ingles`() {
        idiomasConContenido.forEach { idioma ->
            val contenido = fuente.cargar(idioma)
            Juego.entries.forEach { juego ->
                assertTrue(
                    contenido.cantidadDe(juego) > 0,
                    "$idioma no tiene contenido para $juego"
                )
            }
            assertEquals(
                Juego.entries.size,
                contenido.juegosJugables.size,
                "$idioma: solo ${contenido.juegosJugables.size} juegos jugables"
            )
        }
    }

    @Test
    fun `cada juego tiene cartas suficientes para una partida larga`() {
        // Una partida larga son 28 casillas; con menos de veinte cartas por
        // juego se empezarían a repetir dentro de la misma partida.
        val minimo = 20
        idiomasConContenido.forEach { idioma ->
            val contenido = fuente.cargar(idioma)
            Juego.entries.forEach { juego ->
                assertTrue(
                    contenido.cantidadDe(juego) >= minimo,
                    "$idioma/$juego solo tiene ${contenido.cantidadDe(juego)} cartas"
                )
            }
        }
    }

    @Test
    fun `las preguntas tienen cuatro opciones y un indice correcto valido`() {
        idiomasConContenido.forEach { idioma ->
            fuente.cargar(idioma).preguntas.forEachIndexed { i, pregunta ->
                assertEquals(
                    4,
                    pregunta.opciones.size,
                    "$idioma pregunta $i («${pregunta.texto}») tiene ${pregunta.opciones.size} opciones"
                )
                assertTrue(
                    pregunta.correcta in pregunta.opciones.indices,
                    "$idioma pregunta $i apunta a la opción ${pregunta.correcta}"
                )
                assertTrue(pregunta.texto.isNotBlank(), "$idioma pregunta $i sin texto")
                assertTrue(
                    pregunta.opciones.none { it.isBlank() },
                    "$idioma pregunta $i tiene una opción vacía"
                )
                assertEquals(
                    4,
                    pregunta.opciones.toSet().size,
                    "$idioma pregunta $i tiene opciones repetidas"
                )
            }
        }
    }

    @Test
    fun `los eventos de cuando tienen anios plausibles`() {
        idiomasConContenido.forEach { idioma ->
            fuente.cargar(idioma).eventos.forEach { evento ->
                assertTrue(
                    evento.anio in 1..2100,
                    "$idioma: «${evento.texto}» tiene el año ${evento.anio}"
                )
                assertTrue(evento.texto.isNotBlank(), "$idioma: hay un evento sin texto")
            }
        }
    }

    @Test
    fun `las cartas de tabu tienen palabra y prohibidas`() {
        idiomasConContenido.forEach { idioma ->
            fuente.cargar(idioma).tabu.forEach { carta ->
                assertTrue(carta.palabra.isNotBlank(), "$idioma: carta de tabú sin palabra")
                assertTrue(
                    carta.prohibidas.size >= 3,
                    "$idioma: «${carta.palabra}» solo tiene ${carta.prohibidas.size} prohibidas"
                )
                assertTrue(
                    carta.prohibidas.none { it.equals(carta.palabra, ignoreCase = true) },
                    "$idioma: «${carta.palabra}» se prohíbe a sí misma"
                )
            }
        }
    }

    @Test
    fun `las cartas de emojis tienen respuesta y tres senuelos distintos`() {
        idiomasConContenido.forEach { idioma ->
            fuente.cargar(idioma).emojis.forEach { carta ->
                assertTrue(carta.emojis.isNotBlank(), "$idioma: carta de emojis vacía")
                assertTrue(
                    carta.respuesta.isNotBlank(),
                    "$idioma: «${carta.emojis}» no tiene respuesta"
                )
                assertTrue(
                    carta.senuelos.size >= 3,
                    "$idioma: «${carta.respuesta}» tiene ${carta.senuelos.size} señuelos"
                )
                assertTrue(
                    carta.respuesta !in carta.senuelos,
                    "$idioma: «${carta.respuesta}» está entre sus propios señuelos"
                )
                assertEquals(
                    carta.senuelos.size,
                    carta.senuelos.toSet().size,
                    "$idioma: «${carta.respuesta}» tiene señuelos repetidos"
                )
            }
        }
    }

    @Test
    fun `las afirmaciones de verdadero o falso llevan explicacion`() {
        // La explicación es la mitad de la gracia del juego: una afirmación sin
        // explicación es una carta a medias.
        idiomasConContenido.forEach { idioma ->
            fuente.cargar(idioma).afirmaciones.forEach { afirmacion ->
                assertTrue(afirmacion.texto.isNotBlank(), "$idioma: afirmación sin texto")
                assertTrue(
                    afirmacion.explicacion.isNotBlank(),
                    "$idioma: «${afirmacion.texto}» no tiene explicación"
                )
            }
        }
    }

    @Test
    fun `hay verdaderas y falsas mas o menos equilibradas`() {
        // Si el 80 % fueran verdaderas, la estrategia ganadora sería decir
        // siempre «verdadero» y el juego dejaría de tener sentido.
        idiomasConContenido.forEach { idioma ->
            val afirmaciones = fuente.cargar(idioma).afirmaciones
            val verdaderas = afirmaciones.count { it.esVerdadera }
            val proporcion = verdaderas.toDouble() / afirmaciones.size
            assertTrue(
                proporcion in 0.35..0.65,
                "$idioma: el ${(proporcion * 100).toInt()} % son verdaderas"
            )
        }
    }

    @Test
    fun `los retos de ordenar tienen cuatro elementos distintos`() {
        idiomasConContenido.forEach { idioma ->
            fuente.cargar(idioma).ordenar.forEach { reto ->
                assertEquals(
                    4,
                    reto.elementos.size,
                    "$idioma: «${reto.enunciado}» tiene ${reto.elementos.size} elementos"
                )
                assertEquals(
                    4,
                    reto.elementos.toSet().size,
                    "$idioma: «${reto.enunciado}» tiene elementos repetidos"
                )
                assertTrue(reto.enunciado.isNotBlank(), "$idioma: reto de ordenar sin enunciado")
            }
        }
    }

    @Test
    fun `los trabalenguas piden entre una y tres repeticiones`() {
        idiomasConContenido.forEach { idioma ->
            fuente.cargar(idioma).trabalenguas.forEach { t ->
                assertTrue(t.texto.isNotBlank(), "$idioma: trabalenguas sin texto")
                assertTrue(
                    t.repeticiones in 1..3,
                    "$idioma: «${t.texto.take(30)}» pide ${t.repeticiones} repeticiones"
                )
            }
        }
    }

    @Test
    fun `las canciones no traen letra, solo titulo, artista y pista`() {
        // Regla dura de contenido: en la app no entra ni un verso con derechos
        // de autor. Una «pista» larga sería una letra disfrazada.
        idiomasConContenido.forEach { idioma ->
            fuente.cargar(idioma).canciones.forEach { cancion ->
                assertTrue(cancion.titulo.isNotBlank(), "$idioma: canción sin título")
                assertTrue(
                    cancion.artista.isNotBlank(),
                    "$idioma: «${cancion.titulo}» sin artista"
                )
                assertTrue(
                    cancion.pista.length <= 60,
                    "$idioma: la pista de «${cancion.titulo}» tiene ${cancion.pista.length} " +
                        "caracteres y empieza a parecer una letra"
                )
            }
        }
    }

    @Test
    fun `los retos rapidos tienen un objetivo alcanzable`() {
        idiomasConContenido.forEach { idioma ->
            fuente.cargar(idioma).retos.forEach { reto ->
                assertTrue(reto.texto.isNotBlank(), "$idioma: reto sin texto")
                assertTrue(
                    reto.objetivo in 3..15,
                    "$idioma: «${reto.texto}» pide ${reto.objetivo}"
                )
            }
        }
    }

    @Test
    fun `los desafios tienen un nivel entre uno y tres`() {
        idiomasConContenido.forEach { idioma ->
            fuente.cargar(idioma).desafios.forEach { desafio ->
                assertTrue(desafio.texto.isNotBlank(), "$idioma: desafío sin texto")
                assertTrue(
                    desafio.nivel in 1..3,
                    "$idioma: «${desafio.texto.take(30)}» tiene nivel ${desafio.nivel}"
                )
            }
        }
    }

    @Test
    fun `las palabras de mimica y de dibujo no se repiten dentro de su mazo`() {
        idiomasConContenido.forEach { idioma ->
            val contenido = fuente.cargar(idioma)
            val mimica = contenido.mimica.map { it.trim().lowercase() }
            assertEquals(
                mimica.size,
                mimica.toSet().size,
                "$idioma: mímica tiene palabras repetidas"
            )
            val dibujo = contenido.dibujo.map { it.trim().lowercase() }
            assertEquals(
                dibujo.size,
                dibujo.toSet().size,
                "$idioma: dibujo tiene palabras repetidas"
            )
        }
    }

    @Test
    fun `el castellano y el ingles tienen contenido propio y no el mismo`() {
        // Si el respaldo se activara por error, el inglés cargaría el castellano
        // y nadie se enteraría hasta ver «Playa» en una partida en inglés.
        val es = fuente.cargar("es")
        val en = fuente.cargar("en")
        assertTrue(
            es.mimica.first() != en.mimica.first(),
            "el inglés está cargando el contenido castellano"
        )
        assertTrue(es.preguntas.first().texto != en.preguntas.first().texto)
    }

    @Test
    fun `un idioma sin contenido propio cae al respaldo y no se queda vacio`() {
        // El japonés no tiene carpeta de contenido: tiene que caer al inglés.
        val japones = fuente.cargar("ja")
        assertTrue(japones.total > 0, "el japonés se ha quedado sin contenido")
        assertEquals(
            fuente.cargar("en").mimica.size,
            japones.mimica.size,
            "el japonés no está cayendo al inglés"
        )
    }

    @Test
    fun `un idioma inventado no revienta la carga`() {
        val inventado = fuente.cargar("xx-YY")
        assertTrue(inventado.total > 0, "debería caer al respaldo")
    }
}
