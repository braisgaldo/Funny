package es.ghatostudio.funny.dominio.salon

import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.Pantalla
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * El protocolo del salón: ida y vuelta por JSON sin perder nada.
 *
 * Es el test más aburrido del proyecto y uno de los más útiles: un campo que se
 * pierde al serializar se manifiesta como «a veces la palabra no llega al móvil
 * correcto», que es casi imposible de depurar en una fiesta con cuatro móviles.
 */
class PruebaProtocolo {
    private fun idaYVuelta(mensaje: Mensaje): Mensaje = Codec.deTexto(Codec.aTexto(mensaje))

    @Test
    fun `hola sobrevive a la ida y vuelta`() {
        val original = Mensaje.Hola("Brais", 1)
        assertEquals(original, idaYVuelta(original))
    }

    @Test
    fun `salon sobrevive con sus dispositivos`() {
        val original =
            Mensaje.Salon(
                dispositivos =
                    listOf(
                        DispositivoSalon("id-1", "Ana", 7),
                        DispositivoSalon("id-2", "Bea", null),
                    ),
                modo = Modo.INDIVIDUAL,
                partidaEnCurso = true,
            )
        val vuelta = idaYVuelta(original) as Mensaje.Salon
        assertEquals(original.modo, vuelta.modo)
        assertEquals(original.partidaEnCurso, vuelta.partidaEnCurso)
        assertEquals(2, vuelta.dispositivos.size)
        assertEquals("Ana", vuelta.dispositivos[0].nombre)
        assertEquals(7, vuelta.dispositivos[0].idParticipante)
        assertNull(vuelta.dispositivos[1].idParticipante)
    }

    @Test
    fun `la vista sobrevive con su contenido privado`() {
        val original =
            Mensaje.Vista(
                VistaDelMando(
                    pantalla = Pantalla.PRUEBA,
                    modo = Modo.INDIVIDUAL,
                    esMiTurno = true,
                    nombreDelActivo = "Ana",
                    juego = Juego.TABU,
                    contenidoPrivado = listOf("Playa|Arena|Mar|Sol|Toalla", "Pizza|Queso|Masa"),
                    enunciado = "¿En qué año?",
                    opciones = listOf("1969", "1972", "1975", "1980"),
                    respuestaEnviada = true,
                ),
            )
        val vuelta = idaYVuelta(original) as Mensaje.Vista
        assertEquals(original.vista, vuelta.vista)
    }

    @Test
    fun `una vista sin juego ni enunciado vuelve con nulos y no con la cadena null`() {
        // Es el fallo clásico de `org.json`: `optString` de un JSONObject.NULL
        // devuelve la cadena «null», y eso acabaría pintado en la pantalla.
        val original =
            Mensaje.Vista(
                VistaDelMando(
                    pantalla = Pantalla.TABLERO,
                    modo = Modo.EQUIPOS,
                    esMiTurno = false,
                    nombreDelActivo = "Ana",
                    juego = null,
                    enunciado = null,
                ),
            )
        val vuelta = idaYVuelta(original) as Mensaje.Vista
        assertNull(vuelta.vista.juego)
        assertNull(vuelta.vista.enunciado)
    }

    @Test
    fun `todas las acciones sobreviven`() {
        TipoAccion.entries.forEach { tipo ->
            val original = Mensaje.Accion(tipo, entero = 3, texto = "preguntas")
            assertEquals(original, idaYVuelta(original), "ha fallado $tipo")
        }
    }

    @Test
    fun `adios sobrevive`() {
        assertEquals(Mensaje.Adios, idaYVuelta(Mensaje.Adios))
    }

    @Test
    fun `los doce juegos viajan por su clave`() {
        Juego.entries.forEach { juego ->
            val original =
                Mensaje.Vista(
                    VistaDelMando(
                        pantalla = Pantalla.PRUEBA,
                        modo = Modo.EQUIPOS,
                        esMiTurno = true,
                        nombreDelActivo = "A",
                        juego = juego,
                    ),
                )
            val vuelta = idaYVuelta(original) as Mensaje.Vista
            assertEquals(juego, vuelta.vista.juego, "ha fallado $juego")
        }
    }

    @Test
    fun `un mensaje ilegible se convierte en desconocido en lugar de reventar`() {
        // Un salón no puede caerse porque llegue un byte raro por la radio.
        listOf("", "{", "no soy json", "[]", "{\"tipo\":42}").forEach { basura ->
            val mensaje = Codec.deTexto(basura)
            assertTrue(
                mensaje is Mensaje.Desconocido,
                "«$basura» ha devuelto $mensaje en lugar de Desconocido",
            )
        }
    }

    @Test
    fun `un tipo de mensaje futuro se ignora sin romper nada`() {
        val mensaje = Codec.deTexto("""{"tipo":"algo-de-la-version-9","dato":1}""")
        assertEquals(Mensaje.Desconocido("algo-de-la-version-9"), mensaje)
    }

    @Test
    fun `un enum desconocido cae en un valor por defecto`() {
        val mensaje =
            Codec.deTexto(
                """{"tipo":"accion","accion":"BAILAR_LA_CONGA","entero":0,"texto":""}""",
            ) as Mensaje.Accion
        assertEquals(TipoAccion.TIRAR, mensaje.tipo)
    }

    @Test
    fun `los nombres con acentos, emojis y comillas sobreviven`() {
        val nombres = listOf("Iñaki", "María José", "🦊 Zorro", "El \"Crack\"", "Ana\\Bea", "日本語")
        nombres.forEach { nombre ->
            val vuelta = idaYVuelta(Mensaje.Hola(nombre)) as Mensaje.Hola
            assertEquals(nombre, vuelta.nombre, "ha fallado con «$nombre»")
        }
    }

    @Test
    fun `una carta de tabu con sus prohibidas cabe en el contenido privado`() {
        // Las cartas de tabú viajan como una sola cadena separada por barras, y
        // hay que poder reconstruirlas al otro lado.
        val carta = listOf("Playa", "Arena", "Mar", "Sol", "Toalla").joinToString("|")
        val original =
            Mensaje.Vista(
                VistaDelMando(
                    pantalla = Pantalla.PRUEBA,
                    modo = Modo.EQUIPOS,
                    esMiTurno = true,
                    nombreDelActivo = "A",
                    juego = Juego.TABU,
                    contenidoPrivado = listOf(carta),
                ),
            )
        val vuelta = idaYVuelta(original) as Mensaje.Vista
        val partes =
            vuelta.vista.contenidoPrivado
                .first()
                .split("|")
        assertEquals("Playa", partes.first())
        assertEquals(listOf("Arena", "Mar", "Sol", "Toalla"), partes.drop(1))
    }

    @Test
    fun `el servicio del transporte lleva la version del protocolo`() {
        // Dos móviles con protocolos incompatibles no deben ni verse.
        assertTrue(
            es.ghatostudio.funny.plataforma.TransporteNearby.SERVICIO
                .endsWith("v$VERSION_PROTOCOLO"),
            "el id de servicio no lleva la versión: " +
                es.ghatostudio.funny.plataforma.TransporteNearby.SERVICIO,
        )
    }
}
