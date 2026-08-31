package com.fieston.datos

import android.content.Context
import com.fieston.modelo.Ajustes
import com.fieston.modelo.Equipo
import com.fieston.modelo.Longitud
import com.fieston.modelo.Ritmo
import org.json.JSONArray
import org.json.JSONObject

/**
 * Guarda entre sesiones los equipos y los ajustes, para no tener que volver a
 * escribir los nombres cada vez que se abre el juego.
 */
class Preferencias(context: Context) {

    private val prefs = context.getSharedPreferences("fieston", Context.MODE_PRIVATE)

    fun guardarEquipos(equipos: List<Equipo>) {
        val array = JSONArray()
        equipos.forEach { equipo ->
            val jugadores = JSONArray()
            equipo.jugadores.forEach { jugadores.put(it) }
            array.put(
                JSONObject()
                    .put("id", equipo.id)
                    .put("nombre", equipo.nombre)
                    .put("color", equipo.colorIndex)
                    .put("jugadores", jugadores)
            )
        }
        prefs.edit().putString("equipos", array.toString()).apply()
    }

    fun cargarEquipos(): List<Equipo>? {
        val texto = prefs.getString("equipos", null) ?: return null
        return runCatching {
            val array = JSONArray(texto)
            List(array.length()) { i ->
                val o = array.getJSONObject(i)
                val js = o.optJSONArray("jugadores") ?: JSONArray()
                Equipo(
                    id = o.getInt("id"),
                    nombre = o.getString("nombre"),
                    colorIndex = o.getInt("color"),
                    jugadores = List(js.length()) { js.getString(it) }
                )
            }
        }.getOrNull()?.takeIf { it.size >= 2 }
    }

    fun guardarAjustes(ajustes: Ajustes) {
        prefs.edit()
            .putString("ritmo", ajustes.ritmo.name)
            .putString("longitud", ajustes.longitud.name)
            .putBoolean("sonido", ajustes.sonido)
            .putBoolean("vibracion", ajustes.vibracion)
            .apply()
    }

    fun cargarAjustes(): Ajustes {
        val ritmo = prefs.getString("ritmo", null)
            ?.let { nombre -> Ritmo.entries.firstOrNull { it.name == nombre } }
            ?: Ritmo.NORMAL
        val longitud = prefs.getString("longitud", null)
            ?.let { nombre -> Longitud.entries.firstOrNull { it.name == nombre } }
            ?: Longitud.NORMAL
        return Ajustes(
            ritmo = ritmo,
            longitud = longitud,
            sonido = prefs.getBoolean("sonido", true),
            vibracion = prefs.getBoolean("vibracion", true)
        )
    }
}
