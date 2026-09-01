package es.ghatostudio.funny.ui.comun

import com.google.zxing.BinaryBitmap
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * El código QR de la donación.
 *
 * No basta con generar «algo cuadrado»: hay que demostrar que **se lee**. Así
 * que el test coge la matriz que genera la app, la convierte en una imagen y la
 * descodifica con ZXing, que es un lector distinto del generador. Si el QR
 * saliera mal, aquí se vería.
 *
 * ZXing entra solo como dependencia de test, nunca en el APK: la app pinta el
 * QR con su propia matriz en un `Canvas` para poder darle los colores del tema.
 */
class PruebaCodigoQr {
    /** El enlace real de la donación es el caso que de verdad importa. */
    private val enlaceDeDonacion = "https://revolut.me/brais2oz6"

    /**
     * Convierte la matriz en una imagen ampliada con zona de silencio y la
     * descodifica. La ampliación y el margen no son adorno: sin ellos ningún
     * lector encuentra el código, ni el de este test ni el de un móvil.
     */
    private fun descodificar(
        matriz: Array<BooleanArray>,
        escala: Int = 8,
        margen: Int = 4,
    ): String? {
        val modulos = matriz.size
        val lado = (modulos + margen * 2) * escala
        val pixeles = IntArray(lado * lado) { BLANCO }

        for (fila in 0 until modulos) {
            for (columna in 0 until matriz[fila].size) {
                if (!matriz[fila][columna]) continue
                val x0 = (columna + margen) * escala
                val y0 = (fila + margen) * escala
                for (y in y0 until y0 + escala) {
                    for (x in x0 until x0 + escala) {
                        pixeles[y * lado + x] = NEGRO
                    }
                }
            }
        }

        val fuente = RGBLuminanceSource(lado, lado, pixeles)
        val mapa = BinaryBitmap(HybridBinarizer(fuente))
        return runCatching { QRCodeReader().decode(mapa).text }.getOrNull()
    }

    @Test
    fun `el QR del enlace de la donacion se descodifica y da el enlace`() {
        val matriz = matrizQr(enlaceDeDonacion)
        assertNotNull(matriz, "no se ha generado la matriz")
        assertEquals(enlaceDeDonacion, descodificar(matriz))
    }

    @Test
    fun `la matriz es cuadrada y de un tamano de QR valido`() {
        val matriz = matrizQr(enlaceDeDonacion)
        assertNotNull(matriz)
        val lado = matriz.size
        matriz.forEach { fila ->
            assertEquals(lado, fila.size, "la matriz no es cuadrada")
        }
        // Las versiones de QR van de 21×21 (v1) a 177×177 (v40), de cuatro en
        // cuatro. Un tamaño fuera de esa serie significa que algo va mal.
        assertTrue(lado in 21..177, "lado $lado")
        assertEquals(0, (lado - 21) % 4, "lado $lado no corresponde a ninguna versión de QR")
    }

    @Test
    fun `los tres patrones de posicion estan donde deben`() {
        // Las tres esquinas llevan un cuadrado 7×7 con el centro relleno. Si no
        // están, ningún lector encuentra el código, y este es el fallo más
        // habitual al pintar un QR a mano.
        val matriz = matrizQr(enlaceDeDonacion)
        assertNotNull(matriz)
        val lado = matriz.size
        val esquinas = listOf(0 to 0, 0 to (lado - 7), (lado - 7) to 0)
        esquinas.forEach { (fila, columna) ->
            // El anillo exterior está pintado y el que va justo dentro, no.
            assertTrue(matriz[fila][columna], "falta el patrón en $fila,$columna")
            assertTrue(matriz[fila][columna + 6], "patrón incompleto en $fila,$columna")
            assertTrue(matriz[fila + 6][columna], "patrón incompleto en $fila,$columna")
            assertTrue(!matriz[fila + 1][columna + 1], "el anillo interior debería estar vacío")
            assertTrue(matriz[fila + 3][columna + 3], "falta el centro del patrón")
        }
    }

    @Test
    fun `textos de distintas longitudes se descodifican bien`() {
        listOf(
            "https://revolut.me/brais2oz6",
            "https://braisgaldo.github.io/Funny/",
            "GhatoStudioOfficial@gmail.com",
            "a",
            "Funny · un juego de fiesta con acentos y eñes",
        ).forEach { texto ->
            val matriz = matrizQr(texto)
            assertNotNull(matriz, "no se ha generado la matriz de «$texto»")
            assertEquals(texto, descodificar(matriz), "ha fallado con «$texto»")
        }
    }

    @Test
    fun `el mismo texto siempre da la misma matriz`() {
        // El QR se genera dentro de un `remember`: si no fuera determinista,
        // parpadearía en cada recomposición.
        val primera = matrizQr(enlaceDeDonacion)
        val segunda = matrizQr(enlaceDeDonacion)
        assertNotNull(primera)
        assertNotNull(segunda)
        primera.forEachIndexed { fila, valores ->
            assertTrue(valores.contentEquals(segunda[fila]), "la fila $fila no coincide")
        }
    }

    @Test
    fun `un texto vacio no revienta la generacion`() {
        // Puede devolver matriz o null, pero no puede lanzar: se pinta dentro
        // de una composición.
        matrizQr("")
    }

    companion object {
        private const val BLANCO = -0x1
        private const val NEGRO = -0x1000000
    }
}
