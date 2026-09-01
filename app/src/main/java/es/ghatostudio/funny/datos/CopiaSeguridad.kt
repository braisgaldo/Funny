package es.ghatostudio.funny.datos

import android.content.Context
import android.net.Uri
import es.ghatostudio.funny.dominio.Ajustes
import es.ghatostudio.funny.dominio.EstadoCafe
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.Modalidad
import es.ghatostudio.funny.dominio.Participante
import es.ghatostudio.funny.dominio.RONDAS_SOLITARIO
import es.ghatostudio.funny.dominio.Ritmo
import es.ghatostudio.funny.dominio.TemaId
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.time.LocalDate

/**
 * Exportación e importación de los datos de Funny.
 *
 * ### Formato
 *
 * Un único fichero JSON con extensión `.funny.bak`. Se ha elegido JSON en lugar
 * de SQLite o de un ZIP porque lo que hay que guardar son unas pocas
 * preferencias y una lista de equipos: un formato que se pueda abrir con un
 * editor de texto y arreglar a mano vale más aquí que uno binario compacto.
 *
 * La cabecera lleva `esquema`, `app`, `version` y `fecha`, y **se valida antes
 * de tocar nada**. Un fichero de una versión futura se rechaza en lugar de
 * intentar leerlo a medias.
 *
 * ### Garantía de no destrucción
 *
 * Antes de importar se guarda siempre una copia de lo que había en
 * [ficheroDeRespaldo]. Si la importación falla a mitad, los datos anteriores
 * siguen ahí y se puede volver. Nunca se deja el estado a medias.
 */
class CopiaSeguridad(
    private val context: Context,
) {
    // ---------------------------------------------------------------- exportar

    /** Contenido del fichero de copia, ya listo para escribir. */
    fun serializar(ajustes: Ajustes, participantes: List<Participante>): String {
        val raiz =
            JSONObject()
                .put("esquema", ESQUEMA)
                .put("app", NOMBRE_APP)
                .put("version", versionDeApp())
                .put("fecha", LocalDate.now().toString())
                .put("ajustes", ajustesAJson(ajustes))
                .put("participantes", JSONArray(participantesAJson(participantes)))
        return raiz.toString(2)
    }

    fun exportarA(uri: Uri, ajustes: Ajustes, participantes: List<Participante>): Boolean =
        runCatching {
            context.contentResolver.openOutputStream(uri, "wt")?.use { salida ->
                salida.write(serializar(ajustes, participantes).toByteArray(Charsets.UTF_8))
            } ?: return false
            true
        }.getOrDefault(false)

    /**
     * Nombre sugerido para el fichero, con la fecha dentro: quien tenga tres
     * copias en la carpeta de descargas necesita distinguirlas.
     */
    fun nombreSugerido(): String = "funny-${LocalDate.now()}$EXTENSION"

    // ---------------------------------------------------------------- importar

    sealed interface Resultado {
        data class Bien(
            val ajustes: Ajustes,
            val participantes: List<Participante>,
            val fecha: String,
        ) : Resultado

        /** El fichero no es una copia de Funny, o está roto. */
        data object FormatoInvalido : Resultado

        /** La copia viene de una versión más nueva y no se sabe leer. */
        data class EsquemaFuturo(
            val esquema: Int,
        ) : Resultado
    }

    fun leer(uri: Uri): Resultado {
        val texto =
            runCatching {
                context.contentResolver.openInputStream(uri)?.use {
                    it.readBytes().toString(Charsets.UTF_8)
                }
            }.getOrNull() ?: return Resultado.FormatoInvalido
        return interpretar(texto)
    }

    /** Se separa de [leer] para poder probarla sin `ContentResolver`. */
    fun interpretar(texto: String): Resultado {
        val raiz =
            runCatching { JSONObject(texto) }.getOrNull()
                ?: return Resultado.FormatoInvalido

        if (raiz.optString("app") != NOMBRE_APP) return Resultado.FormatoInvalido

        val esquema = raiz.optInt("esquema", -1)
        if (esquema <= 0) return Resultado.FormatoInvalido
        if (esquema > ESQUEMA) return Resultado.EsquemaFuturo(esquema)

        val ajustes =
            runCatching {
                ajustesDesdeJson(raiz.getJSONObject("ajustes"), esquema)
            }.getOrNull() ?: return Resultado.FormatoInvalido

        val participantes =
            participantesDesdeJson(
                raiz.optJSONArray("participantes")?.toString() ?: "[]",
            )

        return Resultado.Bien(
            ajustes = ajustes,
            participantes = participantes,
            fecha = raiz.optString("fecha"),
        )
    }

    /**
     * Guarda una copia del estado actual antes de importar. Se llama siempre,
     * incluso cuando la importación va a fusionar en lugar de reemplazar: el
     * coste es un fichero de dos kilobytes y la tranquilidad es total.
     */
    fun respaldar(ajustes: Ajustes, participantes: List<Participante>): Boolean =
        runCatching {
            ficheroDeRespaldo().writeText(serializar(ajustes, participantes), Charsets.UTF_8)
            true
        }.getOrDefault(false)

    fun ficheroDeRespaldo(): File = File(context.filesDir, "respaldo-antes-de-importar$EXTENSION")

    /**
     * Fusiona lo importado con lo que ya hay: se conservan los participantes
     * actuales y se añaden los de la copia que no estén repetidos por nombre,
     * hasta el máximo. Los ajustes de la copia mandan, porque son un bloque
     * coherente y mezclarlos campo a campo no significaría nada.
     */
    fun fusionar(
        actuales: List<Participante>,
        importados: List<Participante>,
        maximo: Int,
    ): List<Participante> {
        val nombresActuales = actuales.map { it.nombre.trim().lowercase() }.toSet()
        var siguienteId = (actuales.maxOfOrNull { it.id } ?: 0) + 1
        val nuevos =
            importados
                .filter {
                    it.nombre.isNotBlank() && it.nombre.trim().lowercase() !in nombresActuales
                }.map { it.copy(id = siguienteId++) }
        return (actuales + nuevos).take(maximo)
    }

    // ------------------------------------------------------------- privados

    private fun versionDeApp(): String =
        runCatching {
            context.packageManager
                .getPackageInfo(context.packageName, 0)
                .versionName
                .orEmpty()
        }.getOrDefault("")

    private fun ajustesAJson(a: Ajustes) =
        JSONObject()
            .put("tema", a.tema.name)
            .put("temaDelSistema", a.temaDelSistema)
            .put("idioma", a.idioma ?: JSONObject.NULL)
            .put("ritmo", a.ritmo.name)
            .put("modalidad", a.modalidad.name)
            .put("casillasPersonalizadas", a.casillasPersonalizadas)
            .put("pruebasPersonalizadas", a.pruebasPersonalizadas)
            .put("sonido", a.sonido)
            .put("vibracion", a.vibracion)
            .put("animaciones", a.animaciones)
            .put("juegosDesactivados", JSONArray(a.juegosDesactivados.map { it.name }))
            .put("tourVisto", a.tourVisto)
            .put("mejorMarcaSolitario", a.mejorMarcaSolitario)
            // El estado de la donación viaja en la copia a propósito: quien ya dijo
            // «no volver a mostrar» no tiene por qué volver a verlo tras reinstalar.
            .put(
                "cafe",
                JSONObject()
                    .put("usosReales", a.cafe.usosReales)
                    .put("vecesMostrado", a.cafe.vecesMostrado)
                    .put("diaUltimaMuestra", a.cafe.diaUltimaMuestra)
                    .put("noVolverAMostrar", a.cafe.noVolverAMostrar)
                    .put("yaPasoPorAhi", a.cafe.yaPasoPorAhi),
            )

    /**
     * Lee la modalidad, traduciendo las copias del esquema 1.
     *
     * El esquema 1 guardaba una `duracion` de tres valores fijos (CORTA 12,
     * NORMAL 20, LARGA 28). Se traduce a la modalidad equivalente en lugar de
     * dejarla en PERSONALIZADA con el número exacto: quien eligió «larga»
     * quería la partida larga, no un tablero de 28 casillas concreto. La
     * consecuencia es que una partida LARGA pasa de 28 a 32 casillas.
     */
    private fun modalidadDesdeJson(o: JSONObject, esquema: Int): Modalidad {
        if (esquema >= 2 || o.has("modalidad")) {
            return enumDe(o.optString("modalidad"), Modalidad.entries, Modalidad.NORMAL)
        }
        return when (o.optString("duracion")) {
            "CORTA" -> Modalidad.RAPIDA
            "LARGA" -> Modalidad.EXTREMA
            else -> Modalidad.NORMAL
        }
    }

    private fun ajustesDesdeJson(o: JSONObject, esquema: Int): Ajustes {
        val cafe = o.optJSONObject("cafe")
        val desactivados = o.optJSONArray("juegosDesactivados") ?: JSONArray()
        return Ajustes(
            tema = enumDe(o.optString("tema"), TemaId.entries, TemaId.OSCURO_POR_DEFECTO),
            temaDelSistema = o.optBoolean("temaDelSistema", true),
            idioma = o.optString("idioma").takeIf { it.isNotBlank() && it != "null" },
            ritmo = enumDe(o.optString("ritmo"), Ritmo.entries, Ritmo.NORMAL),
            modalidad = modalidadDesdeJson(o, esquema),
            casillasPersonalizadas = o.optInt("casillasPersonalizadas", 20),
            pruebasPersonalizadas = o.optInt("pruebasPersonalizadas", RONDAS_SOLITARIO),
            sonido = o.optBoolean("sonido", true),
            vibracion = o.optBoolean("vibracion", true),
            animaciones = o.optBoolean("animaciones", true),
            juegosDesactivados =
                (0 until desactivados.length())
                    .mapNotNull { i ->
                        val clave = desactivados.optString(i)
                        Juego.entries.firstOrNull { it.name == clave }
                    }.toSet(),
            tourVisto = o.optBoolean("tourVisto", false),
            mejorMarcaSolitario = o.optInt("mejorMarcaSolitario", 0),
            cafe =
                if (cafe == null) {
                    EstadoCafe()
                } else {
                    EstadoCafe(
                        usosReales = cafe.optInt("usosReales", 0),
                        vecesMostrado = cafe.optInt("vecesMostrado", 0),
                        diaUltimaMuestra = cafe.optLong("diaUltimaMuestra", 0L),
                        noVolverAMostrar = cafe.optBoolean("noVolverAMostrar", false),
                        yaPasoPorAhi = cafe.optBoolean("yaPasoPorAhi", false),
                    )
                },
        )
    }

    companion object {
        /**
         * Versión del esquema del fichero. Se sube **solo** cuando un cambio
         * rompe la lectura de copias antiguas, y entonces hay que añadir su
         * migración en `ajustesDesdeJson` y su test en `PruebaCopiaSeguridad`.
         */
        const val ESQUEMA = 2

        const val NOMBRE_APP = "funny"
        const val EXTENSION = ".funny.bak"

        /**
         * Tipo MIME de las copias. No hay uno registrado para `.funny.bak`, así
         * que se declara como JSON, que es lo que de verdad es: el gestor de
         * archivos lo abre y se puede compartir sin problemas.
         */
        const val MIME = "application/json"

        private fun <T : Enum<T>> enumDe(nombre: String?, valores: List<T>, porDefecto: T): T =
            valores.firstOrNull { it.name == nombre } ?: porDefecto
    }
}
