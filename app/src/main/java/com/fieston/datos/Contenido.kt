package com.fieston.datos

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random

data class EventoCuando(val texto: String, val anio: Int, val tema: String)

data class PreguntaTrivial(
    val texto: String,
    val opciones: List<String>,
    val correcta: Int,
    val tema: String
)

data class CartaTabu(val palabra: String, val prohibidas: List<String>)

data class RetoRapido(val texto: String, val objetivo: Int)

/** Todo el contenido del juego, cargado una sola vez desde los assets. */
class Contenido(
    val mimica: List<String>,
    val dibujo: List<String>,
    val eventos: List<EventoCuando>,
    val preguntas: List<PreguntaTrivial>,
    val tabu: List<CartaTabu>,
    val retos: List<RetoRapido>
) {
    companion object {
        fun cargar(context: Context): Contenido {
            val mimica = leerArrayDeTextos(context, "mimica.json", "palabras")
            val dibujo = leerArrayDeTextos(context, "dibujo.json", "palabras")

            val eventos = leerArray(context, "cuando.json", "eventos").map { o ->
                EventoCuando(
                    texto = o.getString("texto"),
                    anio = o.getInt("anio"),
                    tema = o.optString("tema", "")
                )
            }

            val preguntas = leerArray(context, "preguntas.json", "preguntas").map { o ->
                val opciones = o.getJSONArray("opciones")
                PreguntaTrivial(
                    texto = o.getString("texto"),
                    opciones = List(opciones.length()) { opciones.getString(it) },
                    correcta = o.getInt("correcta"),
                    tema = o.optString("tema", "")
                )
            }

            val tabu = leerArray(context, "tabu.json", "cartas").map { o ->
                val prohibidas = o.getJSONArray("prohibidas")
                CartaTabu(
                    palabra = o.getString("palabra"),
                    prohibidas = List(prohibidas.length()) { prohibidas.getString(it) }
                )
            }

            val retos = leerArray(context, "retos.json", "retos").map { o ->
                RetoRapido(texto = o.getString("texto"), objetivo = o.getInt("objetivo"))
            }

            return Contenido(mimica, dibujo, eventos, preguntas, tabu, retos)
        }

        private fun leerTexto(context: Context, nombre: String): String =
            context.assets.open(nombre).bufferedReader(Charsets.UTF_8).use { it.readText() }

        private fun leerArray(context: Context, fichero: String, clave: String): List<JSONObject> {
            val raiz = JSONObject(leerTexto(context, fichero))
            val array: JSONArray = raiz.getJSONArray(clave)
            return List(array.length()) { array.getJSONObject(it) }
        }

        private fun leerArrayDeTextos(
            context: Context,
            fichero: String,
            clave: String
        ): List<String> {
            val raiz = JSONObject(leerTexto(context, fichero))
            val array: JSONArray = raiz.getJSONArray(clave)
            return List(array.length()) { array.getString(it) }
        }
    }
}

/**
 * Baraja que reparte elementos sin repetir hasta agotarlos, momento en el que
 * se vuelve a barajar. Así una partida no repite palabras enseguida.
 */
class Mazo<T>(private val todos: List<T>, private val rnd: Random) {
    private var restantes: MutableList<T> = barajar()

    private fun barajar(): MutableList<T> = todos.shuffled(rnd).toMutableList()

    fun sacar(): T? {
        if (todos.isEmpty()) return null
        if (restantes.isEmpty()) restantes = barajar()
        return restantes.removeAt(restantes.lastIndex)
    }

    fun sacar(cantidad: Int): List<T> = (0 until cantidad).mapNotNull { sacar() }
}
