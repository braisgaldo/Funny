package es.ghatostudio.funny.ui

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import es.ghatostudio.funny.datos.FuenteContenido
import es.ghatostudio.funny.dominio.Afirmacion
import es.ghatostudio.funny.dominio.Cancion
import es.ghatostudio.funny.dominio.CartaEmojis
import es.ghatostudio.funny.dominio.CartaTabu
import es.ghatostudio.funny.dominio.Contenido
import es.ghatostudio.funny.dominio.Desafio
import es.ghatostudio.funny.dominio.EventoCuando
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.PreguntaTrivial
import es.ghatostudio.funny.dominio.Prueba
import es.ghatostudio.funny.dominio.RetoOrdenar
import es.ghatostudio.funny.dominio.RetoRapido
import es.ghatostudio.funny.dominio.Trabalenguas
import es.ghatostudio.funny.dominio.salon.RolSalon
import es.ghatostudio.funny.dominio.salon.TipoAccion
import es.ghatostudio.funny.plataforma.TransporteDeMentira
import es.ghatostudio.funny.plataforma.TransporteSalon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * El salón: un hub y varios mandos, con la radio sustituida por
 * [TransporteDeMentira].
 *
 * Lo que se prueba aquí es lo que de verdad puede fallar en el diseño: que la
 * palabra secreta llegue **solo** al móvil de quien actúa, que un mando no
 * pueda actuar en el turno de otro, y que las respuestas simultáneas de una
 * casilla de «juegan todos» se cuenten bien.
 *
 * Lo que NO se prueba —y hay que decirlo— es la radio: descubrimiento real,
 * permisos del sistema, alcance y reconexiones. Eso necesita dos móviles
 * físicos.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class PruebaSalon {

    private val dispatcher = StandardTestDispatcher()

    private val contenido = Contenido(
        mimica = List(30) { "palabra secreta $it" },
        dibujo = List(30) { "dibujo $it" },
        eventos = List(20) { EventoCuando("evento $it", 1900 + it) },
        preguntas = List(20) {
            PreguntaTrivial("pregunta $it", listOf("a", "b", "c", "d"), correcta = 2)
        },
        tabu = List(20) { CartaTabu("tabu $it", listOf("p1", "p2", "p3", "p4")) },
        retos = List(20) { RetoRapido("reto $it", 5) },
        emojis = List(20) { CartaEmojis("🎬", "peli $it", listOf("s1", "s2", "s3")) },
        afirmaciones = List(20) { Afirmacion("afirmacion $it", true, "porque") },
        trabalenguas = List(20) { Trabalenguas("trabalenguas $it", 2) },
        ordenar = List(20) { RetoOrdenar("ordena $it", listOf("a", "b", "c", "d")) },
        canciones = List(20) { Cancion("cancion $it", "artista", "pista") },
        desafios = List(20) { Desafio("desafio $it") }
    )

    private val fuente = FuenteContenido { contenido }

    @Before
    fun antes() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun despues() {
        Dispatchers.resetMain()
    }

    private fun juego(): JuegoViewModel = JuegoViewModel(
        aplicacion = ApplicationProvider.getApplicationContext<Application>(),
        fuente = fuente,
        idiomaDelSistema = "es"
    )

    // ---------------------------------------------------------- el salón

    @Test
    fun `un mando se conecta al hub y el hub lo apunta como participante`() = runTest(dispatcher) {
        val radioHub = TransporteDeMentira("hub")
        val radioMando = TransporteDeMentira("mando-1")
        radioHub.enchufar(radioMando)

        val vmJuego = juego()
        val hub = SalonViewModel()
        val mando = SalonViewModel()

        hub.abrirComoHub(radioHub, "Mesa del salón", vmJuego)
        mando.abrirComoMando(radioMando, "Ana")
        advanceUntilIdle()

        mando.conectarA("hub")
        advanceUntilIdle()
        mando.presentarse()
        advanceUntilIdle()

        assertEquals(RolSalon.HUB, hub.estado.rol)
        assertEquals(RolSalon.MANDO, mando.estado.rol)
        assertTrue(mando.estado.conectado, "el mando no se ha dado por conectado")
        assertEquals(1, hub.estado.dispositivos.size)
        assertEquals("Ana", hub.estado.dispositivos.first().nombre)

        // El hub ha creado un participante con el nombre que mandó el mando.
        assertTrue(
            vmJuego.estado.participantes.any { it.nombre == "Ana" && it.dispositivo == "mando-1" },
            "participantes: ${vmJuego.estado.participantes}"
        )
    }

    @Test
    fun `un salon pone la partida en modo individual`() = runTest(dispatcher) {
        // Repartir móviles por equipos y luego pasarlos dentro del equipo sería
        // lo peor de los dos mundos: cada móvil es una persona.
        val radioHub = TransporteDeMentira("hub")
        val radioMando = TransporteDeMentira("m1")
        radioHub.enchufar(radioMando)

        val vmJuego = juego()
        val hub = SalonViewModel()
        hub.abrirComoHub(radioHub, "Mesa", vmJuego)
        SalonViewModel().abrirComoMando(radioMando, "Ana")
        advanceUntilIdle()
        radioMando.conectarA("hub")
        advanceUntilIdle()

        assertEquals(Modo.INDIVIDUAL, vmJuego.estado.modo)
    }

    @Test
    fun `el hub no admite mas mandos de los que aguanta la topologia`() = runTest(dispatcher) {
        val radioHub = TransporteDeMentira("hub")
        val vmJuego = juego()
        val hub = SalonViewModel()
        hub.abrirComoHub(radioHub, "Mesa", vmJuego)
        advanceUntilIdle()

        // Se intentan conectar seis mandos; solo deben entrar los que permite
        // P2P_STAR.
        repeat(6) { indice ->
            val radio = TransporteDeMentira("m$indice")
            radioHub.enchufar(radio)
            radio.conectarA("hub")
            advanceUntilIdle()
        }
        assertTrue(
            hub.estado.dispositivos.size <= SalonViewModel.MAXIMO_MANDOS,
            "han entrado ${hub.estado.dispositivos.size} mandos"
        )
    }

    // ------------------------------------------------- el secreto de cada uno

    @Test
    fun `la palabra secreta llega solo al movil de quien actua`() = runTest(dispatcher) {
        val radioHub = TransporteDeMentira("hub")
        val radioAna = TransporteDeMentira("ana")
        val radioBea = TransporteDeMentira("bea")
        radioHub.enchufar(radioAna)
        radioHub.enchufar(radioBea)

        val vmJuego = juego()
        val hub = SalonViewModel()
        hub.abrirComoHub(radioHub, "Mesa", vmJuego)
        advanceUntilIdle()
        radioAna.conectarA("hub")
        advanceUntilIdle()
        radioBea.conectarA("hub")
        advanceUntilIdle()

        // Se monta una partida y se fuerza una prueba de mímica en el turno de
        // quien lleva el primer móvil.
        vmJuego.empezarPartida()
        val activo = vmJuego.estado.participanteActivo
        assertNotNull(activo)
        val dispositivoActivo = activo.dispositivo
        assertNotNull(dispositivoActivo, "el participante activo no tiene móvil asignado")

        vmJuego.elegirJuego(Juego.MIMICA)
        vmJuego.empezarPrueba()
        hub.difundirVistas()
        advanceUntilIdle()

        // De todo lo enviado, solo la vista del móvil activo lleva palabras.
        val vistasConSecreto = radioHub.enviados
            .mapNotNull { (destino, mensaje) ->
                val vista = (mensaje as? es.ghatostudio.funny.dominio.salon.Mensaje.Vista)?.vista
                if (vista != null && vista.contenidoPrivado.isNotEmpty()) destino else null
            }
            .toSet()

        assertEquals(
            setOf(dispositivoActivo),
            vistasConSecreto,
            "el contenido privado ha llegado a: $vistasConSecreto"
        )
    }

    @Test
    fun `un juego que no es de actuar no manda contenido privado a nadie`() = runTest(dispatcher) {
        val radioHub = TransporteDeMentira("hub")
        val radioAna = TransporteDeMentira("ana")
        radioHub.enchufar(radioAna)

        val vmJuego = juego()
        val hub = SalonViewModel()
        hub.abrirComoHub(radioHub, "Mesa", vmJuego)
        advanceUntilIdle()
        radioAna.conectarA("hub")
        advanceUntilIdle()

        vmJuego.empezarPartida()
        vmJuego.elegirJuego(Juego.PREGUNTAS)
        vmJuego.empezarPrueba()
        radioHub.enviados.clear()
        hub.difundirVistas()
        advanceUntilIdle()

        val hayPrivado = radioHub.enviados.any { (_, mensaje) ->
            (mensaje as? es.ghatostudio.funny.dominio.salon.Mensaje.Vista)
                ?.vista?.contenidoPrivado?.isNotEmpty() == true
        }
        assertFalse(hayPrivado, "las preguntas no tienen nada secreto que mandar")
    }

    // --------------------------------------------------------- autoridad

    @Test
    fun `un mando no puede tirar el dado en el turno de otro`() = runTest(dispatcher) {
        val radioHub = TransporteDeMentira("hub")
        val radioAna = TransporteDeMentira("ana")
        val radioBea = TransporteDeMentira("bea")
        radioHub.enchufar(radioAna)
        radioHub.enchufar(radioBea)

        val vmJuego = juego()
        val hub = SalonViewModel()
        hub.abrirComoHub(radioHub, "Mesa", vmJuego)
        advanceUntilIdle()
        radioAna.conectarA("hub")
        advanceUntilIdle()
        radioBea.conectarA("hub")
        advanceUntilIdle()

        vmJuego.empezarPartida()
        val activo = vmJuego.estado.participanteActivo?.dispositivo
        assertNotNull(activo)
        val elOtro = if (activo == "ana") "bea" else "ana"

        // El que NO tiene el turno intenta tirar.
        val mandoIntruso = if (elOtro == "ana") radioAna else radioBea
        mandoIntruso.enviar(
            "hub",
            es.ghatostudio.funny.dominio.salon.Mensaje.Accion(TipoAccion.TIRAR)
        )
        advanceUntilIdle()
        assertNull(vmJuego.estado.dado, "el intruso ha conseguido tirar el dado")

        // El que sí tiene el turno, tira.
        val mandoLegitimo = if (activo == "ana") radioAna else radioBea
        mandoLegitimo.enviar(
            "hub",
            es.ghatostudio.funny.dominio.salon.Mensaje.Accion(TipoAccion.TIRAR)
        )
        advanceUntilIdle()
        assertNotNull(vmJuego.estado.dado, "el legítimo no ha podido tirar")
    }

    @Test
    fun `un mando puede renombrarse en cualquier momento`() = runTest(dispatcher) {
        val radioHub = TransporteDeMentira("hub")
        val radioAna = TransporteDeMentira("ana")
        radioHub.enchufar(radioAna)

        val vmJuego = juego()
        val hub = SalonViewModel()
        hub.abrirComoHub(radioHub, "Mesa", vmJuego)
        advanceUntilIdle()
        radioAna.conectarA("hub")
        advanceUntilIdle()

        radioAna.enviar(
            "hub",
            es.ghatostudio.funny.dominio.salon.Mensaje.Accion(
                TipoAccion.RENOMBRAR,
                texto = "Ana la Grande"
            )
        )
        advanceUntilIdle()
        assertTrue(
            vmJuego.estado.participantes.any { it.nombre == "Ana la Grande" },
            "participantes: ${vmJuego.estado.participantes.map { it.nombre }}"
        )
    }

    // ------------------------------------------- respuestas simultáneas

    @Test
    fun `en una ronda de todos se espera a todos y se cuentan bien los aciertos`() =
        runTest(dispatcher) {
            val radioHub = TransporteDeMentira("hub")
            val radioAna = TransporteDeMentira("ana")
            val radioBea = TransporteDeMentira("bea")
            radioHub.enchufar(radioAna)
            radioHub.enchufar(radioBea)

            val vmJuego = juego()
            val hub = SalonViewModel()
            hub.abrirComoHub(radioHub, "Mesa", vmJuego)
            advanceUntilIdle()
            radioAna.conectarA("hub")
            advanceUntilIdle()
            radioBea.conectarA("hub")
            advanceUntilIdle()

            vmJuego.empezarPartida()
            // Se fuerza una pregunta y la pantalla de ronda de todos.
            vmJuego.elegirJuego(Juego.PREGUNTAS)
            val correcta = (vmJuego.estado.prueba as Prueba.DePreguntas).pregunta.correcta
            vmJuego.ir(Pantalla.RONDA_TODOS)

            // Solo responde Ana: no se resuelve todavía.
            radioAna.enviar(
                "hub",
                es.ghatostudio.funny.dominio.salon.Mensaje.Accion(
                    TipoAccion.RESPONDER,
                    entero = correcta
                )
            )
            advanceUntilIdle()
            assertEquals(
                Pantalla.RONDA_TODOS,
                vmJuego.estado.pantalla,
                "se ha resuelto antes de que respondieran todos"
            )

            // Responde Bea, mal. Ahora sí se resuelve.
            radioBea.enviar(
                "hub",
                es.ghatostudio.funny.dominio.salon.Mensaje.Accion(
                    TipoAccion.RESPONDER,
                    entero = (correcta + 1) % 4
                )
            )
            advanceUntilIdle()
            assertEquals(Pantalla.RESULTADO, vmJuego.estado.pantalla)

            // Solo uno ha acertado.
            assertEquals(1, vmJuego.estado.avanceExtra.size, "aciertos: ${vmJuego.estado.avanceExtra}")
        }

    // ------------------------------------------------------------ fallos

    @Test
    fun `si faltan permisos el salon lo dice y no arranca`() = runTest(dispatcher) {
        val radio = TransporteDeMentira("hub")
        radio.permisosQueFingirQueFaltan = listOf("android.permission.BLUETOOTH_SCAN")

        val salon = SalonViewModel()
        salon.abrirComoHub(radio, "Mesa", juego())
        advanceUntilIdle()

        assertEquals(TransporteSalon.Causa.PERMISOS, salon.estado.fallo)
        assertEquals(1, salon.estado.permisosQueFaltan.size)
    }

    @Test
    fun `si el movil no tiene lo necesario se avisa de que no hay servicios`() =
        runTest(dispatcher) {
            val radio = TransporteDeMentira("hub")
            radio.disponible = false

            val salon = SalonViewModel()
            salon.abrirComoMando(radio, "Ana")
            advanceUntilIdle()

            assertEquals(TransporteSalon.Causa.SERVICIOS, salon.estado.fallo)
            assertFalse(salon.estado.buscando)
        }

    @Test
    fun `perder la conexion deja al mando sin vista y sin conectado`() = runTest(dispatcher) {
        val radioHub = TransporteDeMentira("hub")
        val radioMando = TransporteDeMentira("mando")
        radioHub.enchufar(radioMando)

        val mando = SalonViewModel()
        mando.abrirComoMando(radioMando, "Ana")
        advanceUntilIdle()
        radioMando.conectarA("hub")
        advanceUntilIdle()
        assertTrue(mando.estado.conectado)

        radioMando.desconectarDe(radioHub)
        advanceUntilIdle()
        assertFalse(mando.estado.conectado)
        assertNull(mando.estado.vista)
    }

    @Test
    fun `si un mando se va el hub lo quita de la lista`() = runTest(dispatcher) {
        val radioHub = TransporteDeMentira("hub")
        val radioMando = TransporteDeMentira("mando")
        radioHub.enchufar(radioMando)

        val hub = SalonViewModel()
        hub.abrirComoHub(radioHub, "Mesa", juego())
        advanceUntilIdle()
        radioMando.conectarA("hub")
        advanceUntilIdle()
        assertEquals(1, hub.estado.dispositivos.size)

        radioHub.desconectarDe(radioMando)
        advanceUntilIdle()
        assertTrue(hub.estado.dispositivos.isEmpty())
    }

    @Test
    fun `cerrar el salon libera la radio y deja el estado limpio`() = runTest(dispatcher) {
        val radio = TransporteDeMentira("hub")
        val salon = SalonViewModel()
        salon.abrirComoHub(radio, "Mesa", juego())
        advanceUntilIdle()

        salon.cerrar()
        assertTrue(radio.cerrado, "no se ha cerrado el transporte")
        assertNull(salon.estado.rol)
        assertTrue(salon.estado.dispositivos.isEmpty())
    }

    @Test
    fun `las mesas encontradas no se duplican`() = runTest(dispatcher) {
        val radioMando = TransporteDeMentira("mando")
        val radioMesa = TransporteDeMentira("mesa")
        radioMando.enchufar(radioMesa)

        val mando = SalonViewModel()
        mando.abrirComoMando(radioMando, "Ana")
        advanceUntilIdle()

        radioMando.anunciarADescubierto(radioMesa)
        radioMando.anunciarADescubierto(radioMesa)
        radioMando.anunciarADescubierto(radioMesa)
        advanceUntilIdle()

        assertEquals(1, mando.estado.encontrados.size)
    }
}
