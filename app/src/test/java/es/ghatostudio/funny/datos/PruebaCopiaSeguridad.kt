package es.ghatostudio.funny.datos

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import es.ghatostudio.funny.dominio.Ajustes
import es.ghatostudio.funny.dominio.Duracion
import es.ghatostudio.funny.dominio.EstadoCafe
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.MAXIMO_PARTICIPANTES
import es.ghatostudio.funny.dominio.Participante
import es.ghatostudio.funny.dominio.Ritmo
import es.ghatostudio.funny.dominio.TemaId
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * La copia de seguridad `.funny.bak`.
 *
 * El caso que de verdad importa es el del punto 12 de la plantilla: exportar,
 * borrar y volver a importar tiene que devolver **exactamente** el estado
 * anterior. Todo lo demás de este fichero son las formas en las que eso puede
 * salir mal.
 */
@RunWith(RobolectricTestRunner::class)
class PruebaCopiaSeguridad {

    private val contexto: Context get() = ApplicationProvider.getApplicationContext()
    private val copia get() = CopiaSeguridad(contexto)

    private val ajustesCompletos = Ajustes(
        tema = TemaId.MENTA,
        temaDelSistema = false,
        idioma = "gl",
        ritmo = Ritmo.TRANQUILO,
        duracion = Duracion.LARGA,
        sonido = false,
        vibracion = false,
        animaciones = false,
        juegosDesactivados = setOf(Juego.CANTA, Juego.DESAFIO, Juego.TRABALENGUAS),
        tourVisto = true,
        cafe = EstadoCafe(
            usosReales = 12,
            vecesMostrado = 2,
            diaUltimaMuestra = 20000L,
            noVolverAMostrar = true,
            yaPasoPorAhi = true
        ),
        mejorMarcaSolitario = 27
    )

    private val participantes = listOf(
        Participante(1, "Los Cracks", 0, miembros = listOf("Ana", "Bea")),
        Participante(2, "Las Fieras", 1, miembros = listOf("Caro")),
        Participante(3, "", 2)
    )

    // ------------------------------------------------------ ida y vuelta

    @Test
    fun `exportar e importar devuelve exactamente lo mismo`() {
        val texto = copia.serializar(ajustesCompletos, participantes)
        val resultado = copia.interpretar(texto)

        assertTrue(resultado is CopiaSeguridad.Resultado.Bien, "no ha leído la copia: $resultado")
        resultado as CopiaSeguridad.Resultado.Bien

        assertEquals(ajustesCompletos, resultado.ajustes)
        // El id se conserva, y el nombre vacío también: es información real.
        assertEquals(participantes.map { it.nombre }, resultado.participantes.map { it.nombre })
        assertEquals(participantes.map { it.miembros }, resultado.participantes.map { it.miembros })
        assertEquals(
            participantes.map { it.indiceColor },
            resultado.participantes.map { it.indiceColor }
        )
    }

    @Test
    fun `el estado de la donacion viaja en la copia`() {
        // Lo pide el punto 4.4.3: quien dijo «no volver a mostrar» no tiene por
        // qué volver a verlo después de reinstalar.
        val texto = copia.serializar(ajustesCompletos, participantes)
        val resultado = copia.interpretar(texto) as CopiaSeguridad.Resultado.Bien
        assertTrue(resultado.ajustes.cafe.noVolverAMostrar)
        assertTrue(resultado.ajustes.cafe.yaPasoPorAhi)
        assertEquals(12, resultado.ajustes.cafe.usosReales)
    }

    @Test
    fun `la copia lleva cabecera con esquema, app, version y fecha`() {
        val raiz = JSONObject(copia.serializar(Ajustes(), emptyList()))
        assertEquals(CopiaSeguridad.ESQUEMA, raiz.getInt("esquema"))
        assertEquals(CopiaSeguridad.NOMBRE_APP, raiz.getString("app"))
        assertTrue(raiz.has("version"))
        assertTrue(raiz.getString("fecha").isNotBlank())
    }

    @Test
    fun `una copia con ajustes por defecto y sin participantes vale`() {
        val texto = copia.serializar(Ajustes(), emptyList())
        val resultado = copia.interpretar(texto) as CopiaSeguridad.Resultado.Bien
        assertEquals(Ajustes(), resultado.ajustes)
        assertTrue(resultado.participantes.isEmpty())
    }

    // -------------------------------------------------------- validación

    @Test
    fun `un fichero que no es de Funny se rechaza`() {
        val ajenos = listOf(
            """{"app":"otra-cosa","esquema":1,"ajustes":{}}""",
            """{"esquema":1,"ajustes":{}}""",
            """{"cualquier":"cosa"}"""
        )
        ajenos.forEach { texto ->
            assertEquals(
                CopiaSeguridad.Resultado.FormatoInvalido,
                copia.interpretar(texto),
                "debería rechazar: $texto"
            )
        }
    }

    @Test
    fun `un fichero roto se rechaza sin lanzar`() {
        listOf("", "{", "no soy json", "[1,2,3]", "null").forEach { texto ->
            assertEquals(
                CopiaSeguridad.Resultado.FormatoInvalido,
                copia.interpretar(texto),
                "debería rechazar: «$texto»"
            )
        }
    }

    @Test
    fun `una copia de una version futura se rechaza en lugar de leerse a medias`() {
        val futura = """
            {"app":"funny","esquema":99,"version":"9.0.0","fecha":"2030-01-01","ajustes":{}}
        """.trimIndent()
        val resultado = copia.interpretar(futura)
        assertTrue(
            resultado is CopiaSeguridad.Resultado.EsquemaFuturo,
            "debería avisar de la versión, no intentar leerla: $resultado"
        )
        assertEquals(99, (resultado as CopiaSeguridad.Resultado.EsquemaFuturo).esquema)
    }

    @Test
    fun `una copia sin esquema se rechaza`() {
        val sinEsquema = """{"app":"funny","ajustes":{}}"""
        assertEquals(CopiaSeguridad.Resultado.FormatoInvalido, copia.interpretar(sinEsquema))
    }

    @Test
    fun `una copia con ajustes corruptos cae en los valores por defecto`() {
        // Un campo inventado o mal escrito no puede tumbar la importación: se
        // usa el valor por defecto y se sigue.
        val raro = """
            {"app":"funny","esquema":1,"fecha":"2026-01-01",
             "ajustes":{"tema":"NO_EXISTE","ritmo":"VELOCISIMO","duracion":42,
                        "juegosDesactivados":["MIMICA","INVENTADO"]}}
        """.trimIndent()
        val resultado = copia.interpretar(raro) as CopiaSeguridad.Resultado.Bien
        assertEquals(TemaId.OSCURO_POR_DEFECTO, resultado.ajustes.tema)
        assertEquals(Ritmo.NORMAL, resultado.ajustes.ritmo)
        assertEquals(Duracion.NORMAL, resultado.ajustes.duracion)
        // De los dos juegos, solo el que existe.
        assertEquals(setOf(Juego.MIMICA), resultado.ajustes.juegosDesactivados)
    }

    // ------------------------------------------------------------ fusión

    @Test
    fun `fusionar anade los que no estan y conserva los actuales`() {
        val actuales = listOf(
            Participante(1, "Los Cracks", 0),
            Participante(2, "Las Fieras", 1)
        )
        val importados = listOf(
            Participante(9, "Las Fieras", 3),
            Participante(10, "Los Nuevos", 4)
        )
        val fusion = copia.fusionar(actuales, importados, MAXIMO_PARTICIPANTES)

        assertEquals(3, fusion.size)
        assertEquals(listOf("Los Cracks", "Las Fieras", "Los Nuevos"), fusion.map { it.nombre })
        // Los ids no chocan.
        assertEquals(fusion.size, fusion.map { it.id }.toSet().size)
    }

    @Test
    fun `fusionar no distingue mayusculas ni espacios al comparar nombres`() {
        val actuales = listOf(Participante(1, "Los Cracks", 0))
        val importados = listOf(Participante(9, "  los cracks ", 3))
        assertEquals(1, copia.fusionar(actuales, importados, MAXIMO_PARTICIPANTES).size)
    }

    @Test
    fun `fusionar respeta el maximo de participantes`() {
        val actuales = List(6) { Participante(it + 1, "Equipo $it", it) }
        val importados = List(6) { Participante(it + 100, "Nuevo $it", it) }
        val fusion = copia.fusionar(actuales, importados, MAXIMO_PARTICIPANTES)
        assertEquals(MAXIMO_PARTICIPANTES, fusion.size)
    }

    @Test
    fun `fusionar descarta los importados sin nombre`() {
        // Un participante sin nombre es un hueco, no una entidad: fusionar
        // huecos solo llenaría la mesa de «Equipo 4» vacíos.
        val actuales = listOf(Participante(1, "A", 0))
        val importados = listOf(Participante(9, "", 3), Participante(10, "  ", 4))
        assertEquals(1, copia.fusionar(actuales, importados, MAXIMO_PARTICIPANTES).size)
    }

    // ---------------------------------------------------------- respaldo

    @Test
    fun `respaldar deja un fichero legible antes de importar`() {
        assertTrue(copia.respaldar(ajustesCompletos, participantes))
        val fichero = copia.ficheroDeRespaldo()
        assertTrue(fichero.exists(), "no se ha creado ${fichero.name}")

        val recuperado = copia.interpretar(fichero.readText()) as CopiaSeguridad.Resultado.Bien
        assertEquals(ajustesCompletos, recuperado.ajustes)
    }

    @Test
    fun `el respaldo lleva la extension propia`() {
        assertTrue(
            copia.ficheroDeRespaldo().name.endsWith(CopiaSeguridad.EXTENSION),
            copia.ficheroDeRespaldo().name
        )
    }

    @Test
    fun `el nombre sugerido lleva la fecha y la extension`() {
        val nombre = copia.nombreSugerido()
        assertTrue(nombre.startsWith("funny-"), nombre)
        assertTrue(nombre.endsWith(".funny.bak"), nombre)
    }

    // -------------------------------------- serialización de participantes

    @Test
    fun `los participantes van y vuelven por su propio json`() {
        val texto = participantesAJson(participantes)
        val vuelta = participantesDesdeJson(texto)
        assertEquals(participantes.map { it.id }, vuelta.map { it.id })
        assertEquals(participantes.map { it.nombre }, vuelta.map { it.nombre })
        assertEquals(participantes.map { it.miembros }, vuelta.map { it.miembros })
    }

    @Test
    fun `participantesDesdeJson aguanta basura y devuelve lista vacia`() {
        listOf("", "{", "no json", "null").forEach {
            assertTrue(participantesDesdeJson(it).isEmpty(), "debería estar vacío con «$it»")
        }
    }
}
