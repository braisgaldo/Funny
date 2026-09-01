package es.ghatostudio.funny.ui.tema

import androidx.compose.ui.graphics.Color
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.TemaId
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Comprueba el contraste de las seis paletas contra el mínimo AA de WCAG.
 *
 * Esto es un test y no una nota en la documentación porque el contraste es
 * justo el tipo de cosa que se rompe sin que nadie se dé cuenta: alguien
 * retoca un rosa para que quede más bonito y de paso deja el texto en 3,4:1.
 * Que la build falle es la única forma de que eso no llegue a una release.
 *
 * Al escribirlo aparecieron dos fallos reales: el blanco sobre el rosa de
 * FIESTA se quedaba en 3,37:1 y el amarillo de rotulador sobre el lienzo de
 * dibujo en 1,37:1. Los dos están corregidos en `Paleta.kt`.
 */
class PruebaContraste {
    /** Mínimo AA para texto de tamaño normal. */
    private val minimoTexto = 4.5

    /** Mínimo para gráficos y componentes de interfaz. */
    private val minimoGrafico = 3.0

    private val paletas get() = TemaId.entries.map { paletaDe(it) }

    @Test
    fun `el texto fuerte cumple AA sobre los cuatro fondos`() {
        paletas.forEach { p ->
            listOf(
                "fondo" to p.fondo,
                "fondoAlto" to p.fondoAlto,
                "superficie" to p.superficie,
                "superficieAlta" to p.superficieAlta,
            ).forEach { (nombre, fondo) ->
                val razon = contraste(p.textoFuerte, fondo)
                assertTrue(
                    razon >= minimoTexto,
                    "${p.id}: textoFuerte sobre $nombre da ${redondear(razon)}:1",
                )
            }
        }
    }

    @Test
    fun `el texto tenue cumple AA sobre los cuatro fondos`() {
        paletas.forEach { p ->
            listOf(
                "fondo" to p.fondo,
                "fondoAlto" to p.fondoAlto,
                "superficie" to p.superficie,
                "superficieAlta" to p.superficieAlta,
            ).forEach { (nombre, fondo) ->
                val razon = contraste(p.textoTenue, fondo)
                assertTrue(
                    razon >= minimoTexto,
                    "${p.id}: textoTenue sobre $nombre da ${redondear(razon)}:1",
                )
            }
        }
    }

    @Test
    fun `los colores sobre primario, acento, exito y fallo cumplen AA`() {
        paletas.forEach { p ->
            listOf(
                "sobrePrimario/primario" to (p.sobrePrimario to p.primario),
                "sobreAcento/acento" to (p.sobreAcento to p.acento),
                "sobreExito/exito" to (p.sobreExito to p.exito),
                "sobreFallo/fallo" to (p.sobreFallo to p.fallo),
            ).forEach { (nombre, pareja) ->
                val razon = contraste(pareja.first, pareja.second)
                assertTrue(
                    razon >= minimoTexto,
                    "${p.id}: $nombre da ${redondear(razon)}:1",
                )
            }
        }
    }

    @Test
    fun `los doce colores de juego se leen como texto sobre la superficie`() {
        paletas.forEach { p ->
            Juego.entries.forEach { juego ->
                val razon = contraste(p.colorDe(juego), p.superficie)
                assertTrue(
                    razon >= minimoTexto,
                    "${p.id}: el color de $juego sobre superficie da ${redondear(razon)}:1",
                )
            }
        }
    }

    @Test
    fun `textoSobre siempre devuelve una tinta legible`() {
        // Es la garantía de los botones de las pruebas, que usan el color del
        // juego como fondo. Si esto falla, hay botones ilegibles.
        paletas.forEach { p ->
            (Juego.entries.map { p.colorDe(it) } + p.participantes).forEach { fondo ->
                val razon = contraste(p.textoSobre(fondo), fondo)
                assertTrue(
                    razon >= minimoTexto,
                    "${p.id}: textoSobre($fondo) da ${redondear(razon)}:1",
                )
            }
        }
    }

    @Test
    fun `los ocho colores de participante se distinguen sobre la superficie`() {
        paletas.forEach { p ->
            p.participantes.forEachIndexed { indice, color ->
                val razon = contraste(color, p.superficie)
                assertTrue(
                    razon >= minimoTexto,
                    "${p.id}: el color de participante $indice da ${redondear(razon)}:1",
                )
            }
        }
    }

    @Test
    fun `las tintas del lienzo de dibujo se ven sobre el papel`() {
        // El lienzo es la excepción consciente al tema: papel claro fijo. Aquí
        // basta el mínimo de gráficos, porque son trazos, no texto.
        TINTAS_DIBUJO.forEach { tinta ->
            val razon = contraste(tinta, LIENZO_DIBUJO)
            assertTrue(
                razon >= minimoGrafico,
                "la tinta $tinta sobre el lienzo da ${redondear(razon)}:1",
            )
        }
    }

    @Test
    fun `el texto de ayuda del lienzo cumple AA sobre el papel`() {
        val razon = contraste(TEXTO_SOBRE_LIENZO, LIENZO_DIBUJO)
        assertTrue(razon >= minimoTexto, "da ${redondear(razon)}:1")
    }

    @Test
    fun `hay tres temas claros y tres oscuros`() {
        assertTrue(TemaId.CLAROS.size == 3, "claros: ${TemaId.CLAROS.size}")
        assertTrue(TemaId.OSCUROS.size == 3, "oscuros: ${TemaId.OSCUROS.size}")
    }

    @Test
    fun `cada paleta tiene un color por juego y ocho de participante`() {
        paletas.forEach { p ->
            assertTrue(
                p.juegos.size == Juego.entries.size,
                "${p.id}: ${p.juegos.size} colores para ${Juego.entries.size} juegos",
            )
            assertTrue(p.participantes.size == 8, "${p.id}: ${p.participantes.size} participantes")
        }
    }

    @Test
    fun `la luminancia de referencia coincide con los valores conocidos de WCAG`() {
        // Blanco y negro puros son los dos casos que fija el estándar, y el
        // contraste entre ellos es exactamente 21:1. Si esto falla, la fórmula
        // está mal y todo el resto del test no vale nada.
        val blanco = Color(0xFFFFFFFF)
        val negro = Color(0xFF000000)
        assertTrue(luminancia(blanco) > 0.999, "luminancia del blanco: ${luminancia(blanco)}")
        assertTrue(luminancia(negro) < 0.001, "luminancia del negro: ${luminancia(negro)}")
        val razon = contraste(blanco, negro)
        assertTrue(razon > 20.9 && razon < 21.1, "blanco contra negro da ${redondear(razon)}:1")
    }

    private fun redondear(valor: Double) = (valor * 100).toInt() / 100.0
}
