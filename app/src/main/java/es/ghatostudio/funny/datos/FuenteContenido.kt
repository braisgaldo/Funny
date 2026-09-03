package es.ghatostudio.funny.datos

import android.content.Context
import android.util.Log
import es.ghatostudio.funny.dominio.Afirmacion
import es.ghatostudio.funny.dominio.Cancion
import es.ghatostudio.funny.dominio.CartaEmojis
import es.ghatostudio.funny.dominio.CartaTabu
import es.ghatostudio.funny.dominio.Contenido
import es.ghatostudio.funny.dominio.Desafio
import es.ghatostudio.funny.dominio.EventoCuando
import es.ghatostudio.funny.dominio.PreguntaTrivial
import es.ghatostudio.funny.dominio.RetoOrdenar
import es.ghatostudio.funny.dominio.RetoRapido
import es.ghatostudio.funny.dominio.Trabalenguas
import org.json.JSONArray
import org.json.JSONObject

/**
 * De dónde salen las cartas de los juegos. Es una interfaz para que los tests
 * puedan darle contenido inventado sin tocar los assets.
 */
fun interface FuenteContenido {
    /** Carga el contenido para un idioma. Nunca lanza: si algo falla, devuelve lo que pueda. */
    fun cargar(idioma: String): Contenido
}

/**
 * Contenido empaquetado en los assets, organizado por idioma:
 *
 *     assets/contenido/es/mimica.json
 *     assets/contenido/en/mimica.json
 *     …
 *
 * Si un idioma no tiene su carpeta —o le falta un fichero— se cae al inglés, y
 * de ahí al castellano. Un juego sin contenido no rompe nada: `Contenido`
 * devuelve cero cartas y la partida simplemente no lo saca al tablero.
 */
class ContenidoDeAssets(
    private val context: Context,
) : FuenteContenido {
    override fun cargar(idioma: String): Contenido {
        val carpetas = carpetasParaIdioma(idioma)
        return Contenido(
            mimica = textos(carpetas, "mimica.json", "palabras"),
            dibujo = textos(carpetas, "dibujo.json", "palabras"),
            eventos =
                objetos(carpetas, "cuando.json", "eventos") { o ->
                    EventoCuando(
                        texto = o.getString("texto"),
                        anio = o.getInt("anio"),
                        tema = o.optString("tema", ""),
                    )
                },
            preguntas =
                objetos(carpetas, "preguntas.json", "preguntas") { o ->
                    PreguntaTrivial(
                        texto = o.getString("texto"),
                        opciones = listaDeTextos(o.getJSONArray("opciones")),
                        correcta = o.getInt("correcta"),
                        tema = o.optString("tema", ""),
                    )
                },
            tabu =
                objetos(carpetas, "tabu.json", "cartas") { o ->
                    CartaTabu(
                        palabra = o.getString("palabra"),
                        prohibidas = listaDeTextos(o.getJSONArray("prohibidas")),
                    )
                },
            retos =
                objetos(carpetas, "retos.json", "retos") { o ->
                    RetoRapido(texto = o.getString("texto"), objetivo = o.getInt("objetivo"))
                },
            emojis =
                objetos(carpetas, "emojis.json", "cartas") { o ->
                    CartaEmojis(
                        emojis = o.getString("emojis"),
                        respuesta = o.getString("respuesta"),
                        senuelos = listaDeTextos(o.getJSONArray("senuelos")),
                        tipo = o.optString("tipo", ""),
                    )
                },
            afirmaciones =
                objetos(carpetas, "verdadero_falso.json", "afirmaciones") { o ->
                    Afirmacion(
                        texto = o.getString("texto"),
                        esVerdadera = o.getBoolean("verdadera"),
                        explicacion = o.optString("explicacion", ""),
                    )
                },
            trabalenguas =
                objetos(
                    carpetas,
                    "trabalenguas.json",
                    "trabalenguas",
                ) { o ->
                    Trabalenguas(
                        texto = o.getString("texto"),
                        repeticiones = o.optInt("repeticiones", 2),
                    )
                },
            ordenar =
                objetos(carpetas, "ordena.json", "retos") { o ->
                    RetoOrdenar(
                        enunciado = o.getString("enunciado"),
                        elementos = listaDeTextos(o.getJSONArray("elementos")),
                        criterio = o.optString("criterio", ""),
                    )
                },
            canciones =
                objetos(carpetas, "canta.json", "canciones") { o ->
                    Cancion(
                        titulo = o.getString("titulo"),
                        artista = o.getString("artista"),
                        pista = o.optString("pista", ""),
                    )
                },
            desafios =
                objetos(
                    carpetas,
                    "desafios.json",
                    "desafios",
                ) { o ->
                    Desafio(texto = o.getString("texto"), nivel = o.optInt("nivel", 1))
                },
            refranes = preguntasDe(carpetas, "refranes.json"),
            antesDespues = preguntasDe(carpetas, "antes_despues.json"),
            anagramas = preguntasDe(carpetas, "anagramas.json"),
            acentos = desafiosDe(carpetas, "acentos.json"),
            sonidos = desafiosDe(carpetas, "sonidos.json"),
            encadenados =
                objetos(carpetas, "encadenados.json", "retos") { o ->
                    RetoRapido(texto = o.getString("texto"), objetivo = o.getInt("objetivo"))
                },
        )
    }

    /**
     * Los tres juegos nuevos de opciones comparten formato de carta con las
     * preguntas, así que comparten lector. El fichero siempre trae la lista
     * bajo la clave `preguntas`.
     */
    private fun preguntasDe(carpetas: List<String>, fichero: String): List<PreguntaTrivial> =
        objetos(carpetas, fichero, "preguntas") { o ->
            PreguntaTrivial(
                texto = o.getString("texto"),
                opciones = listaDeTextos(o.getJSONArray("opciones")),
                correcta = o.getInt("correcta"),
                tema = o.optString("tema", ""),
            )
        }

    /** Acentos y sonidos comparten formato con los desafíos. */
    private fun desafiosDe(carpetas: List<String>, fichero: String): List<Desafio> =
        objetos(carpetas, fichero, "cartas") { o ->
            Desafio(texto = o.getString("texto"), nivel = o.optInt("nivel", 1))
        }

    /** Orden de búsqueda: el idioma pedido, luego inglés, luego castellano. */
    private fun carpetasParaIdioma(idioma: String): List<String> =
        listOf(idioma, IDIOMA_RESPALDO, IDIOMA_ORIGEN).distinct().map { "$RAIZ/$it" }

    private fun leerPrimeroQueExista(carpetas: List<String>, fichero: String): String? {
        for (carpeta in carpetas) {
            val ruta = "$carpeta/$fichero"
            val texto =
                runCatching {
                    context.assets
                        .open(ruta)
                        .bufferedReader(Charsets.UTF_8)
                        .use { it.readText() }
                }.getOrNull()
            if (texto != null) return texto
        }
        return null
    }

    private fun <T> objetos(
        carpetas: List<String>,
        fichero: String,
        clave: String,
        construir: (JSONObject) -> T,
    ): List<T> {
        val bruto = leerPrimeroQueExista(carpetas, fichero) ?: return emptyList()
        return runCatching {
            val array = JSONObject(bruto).getJSONArray(clave)
            // Una carta mal escrita se descarta sola en lugar de tumbar el juego
            // entero: es contenido, no código.
            (0 until array.length()).mapNotNull { i ->
                runCatching { construir(array.getJSONObject(i)) }
                    .onFailure { Log.w(ETIQUETA, "Carta $i de $fichero mal formada", it) }
                    .getOrNull()
            }
        }.onFailure {
            Log.w(
                ETIQUETA,
                "No se ha podido leer $fichero",
                it,
            )
        }.getOrDefault(emptyList())
    }

    private fun textos(carpetas: List<String>, fichero: String, clave: String): List<String> {
        val bruto = leerPrimeroQueExista(carpetas, fichero) ?: return emptyList()
        return runCatching {
            listaDeTextos(JSONObject(bruto).getJSONArray(clave))
        }.onFailure {
            Log.w(
                ETIQUETA,
                "No se ha podido leer $fichero",
                it,
            )
        }.getOrDefault(emptyList())
    }

    private fun listaDeTextos(array: JSONArray): List<String> =
        (0 until array.length()).map { array.getString(it) }

    companion object {
        private const val ETIQUETA = "FunnyContenido"
        private const val RAIZ = "contenido"
        const val IDIOMA_RESPALDO = "en"
        const val IDIOMA_ORIGEN = "es"
    }
}
