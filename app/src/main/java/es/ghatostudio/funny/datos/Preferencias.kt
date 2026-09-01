package es.ghatostudio.funny.datos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import es.ghatostudio.funny.dominio.Ajustes
import es.ghatostudio.funny.dominio.Duracion
import es.ghatostudio.funny.dominio.EstadoCafe
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.Participante
import es.ghatostudio.funny.dominio.Ritmo
import es.ghatostudio.funny.dominio.TemaId
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject

private val Context.almacen: DataStore<Preferences> by preferencesDataStore(name = "funny")

/**
 * Lo que Funny recuerda entre sesiones: los ajustes, los equipos y la marca del
 * solitario. Nada más, y nada que salga del móvil.
 *
 * Se lee una única vez al arrancar con [cargarAhora] —es un fichero local de
 * unos pocos cientos de bytes— y a partir de ahí las escrituras van en segundo
 * plano. Hacerlo así evita que la app arranque con el tema por defecto y dé un
 * salto de color en cuanto llega el valor guardado.
 */
class Preferencias(
    private val context: Context,
) {
    // ------------------------------------------------------------- lectura

    data class Guardado(
        val ajustes: Ajustes,
        val participantes: List<Participante>,
        val modo: Modo,
        val nombreEnSalon: String,
    )

    fun cargarAhora(): Guardado = runBlocking { cargar() }

    suspend fun cargar(): Guardado {
        val p = context.almacen.data.first()
        return Guardado(
            ajustes = leerAjustes(p),
            participantes = leerParticipantes(p),
            modo = enumOPorDefecto(p[MODO], Modo.entries, Modo.EQUIPOS),
            nombreEnSalon = p[NOMBRE_SALON].orEmpty(),
        )
    }

    val flujoDeAjustes = context.almacen.data.map { leerAjustes(it) }

    private fun leerAjustes(p: Preferences) =
        Ajustes(
            tema = enumOPorDefecto(p[TEMA], TemaId.entries, TemaId.OSCURO_POR_DEFECTO),
            temaDelSistema = p[TEMA_SISTEMA] ?: true,
            idioma = p[IDIOMA],
            ritmo = enumOPorDefecto(p[RITMO], Ritmo.entries, Ritmo.NORMAL),
            duracion = enumOPorDefecto(p[DURACION], Duracion.entries, Duracion.NORMAL),
            sonido = p[SONIDO] ?: true,
            vibracion = p[VIBRACION] ?: true,
            animaciones = p[ANIMACIONES] ?: true,
            juegosDesactivados =
                p[JUEGOS_OFF]
                    .orEmpty()
                    .split(SEPARADOR)
                    .mapNotNull { clave -> Juego.entries.firstOrNull { it.name == clave } }
                    .toSet(),
            tourVisto = p[TOUR_VISTO] ?: false,
            cafe =
                EstadoCafe(
                    usosReales = p[CAFE_USOS] ?: 0,
                    vecesMostrado = p[CAFE_MOSTRADO] ?: 0,
                    diaUltimaMuestra = p[CAFE_DIA] ?: 0L,
                    noVolverAMostrar = p[CAFE_NUNCA] ?: false,
                    yaPasoPorAhi = p[CAFE_PASO] ?: false,
                ),
            mejorMarcaSolitario = p[MEJOR_SOLITARIO] ?: 0,
        )

    private fun leerParticipantes(p: Preferences): List<Participante> {
        val texto = p[PARTICIPANTES] ?: return emptyList()
        return participantesDesdeJson(texto)
    }

    // ----------------------------------------------------------- escritura

    suspend fun guardarAjustes(a: Ajustes) {
        context.almacen.edit { p ->
            p[TEMA] = a.tema.name
            p[TEMA_SISTEMA] = a.temaDelSistema
            if (a.idioma == null) p.remove(IDIOMA) else p[IDIOMA] = a.idioma
            p[RITMO] = a.ritmo.name
            p[DURACION] = a.duracion.name
            p[SONIDO] = a.sonido
            p[VIBRACION] = a.vibracion
            p[ANIMACIONES] = a.animaciones
            p[JUEGOS_OFF] = a.juegosDesactivados.joinToString(SEPARADOR) { it.name }
            p[TOUR_VISTO] = a.tourVisto
            p[CAFE_USOS] = a.cafe.usosReales
            p[CAFE_MOSTRADO] = a.cafe.vecesMostrado
            p[CAFE_DIA] = a.cafe.diaUltimaMuestra
            p[CAFE_NUNCA] = a.cafe.noVolverAMostrar
            p[CAFE_PASO] = a.cafe.yaPasoPorAhi
            p[MEJOR_SOLITARIO] = a.mejorMarcaSolitario
        }
    }

    suspend fun guardarParticipantes(participantes: List<Participante>) {
        context.almacen.edit { it[PARTICIPANTES] = participantesAJson(participantes) }
    }

    suspend fun guardarModo(modo: Modo) {
        context.almacen.edit { it[MODO] = modo.name }
    }

    suspend fun guardarNombreEnSalon(nombre: String) {
        context.almacen.edit { it[NOMBRE_SALON] = nombre }
    }

    /** Deja las preferencias como recién instaladas. La usa la importación con «reemplazar». */
    suspend fun borrarTodo() {
        context.almacen.edit { it.clear() }
    }

    companion object {
        private const val SEPARADOR = ","

        private val TEMA = stringPreferencesKey("tema")
        private val TEMA_SISTEMA = booleanPreferencesKey("tema_sistema")
        private val IDIOMA = stringPreferencesKey("idioma")
        private val RITMO = stringPreferencesKey("ritmo")
        private val DURACION = stringPreferencesKey("duracion")
        private val SONIDO = booleanPreferencesKey("sonido")
        private val VIBRACION = booleanPreferencesKey("vibracion")
        private val ANIMACIONES = booleanPreferencesKey("animaciones")
        private val JUEGOS_OFF = stringPreferencesKey("juegos_desactivados")
        private val TOUR_VISTO = booleanPreferencesKey("tour_visto")
        private val CAFE_USOS = intPreferencesKey("cafe_usos")
        private val CAFE_MOSTRADO = intPreferencesKey("cafe_mostrado")
        private val CAFE_DIA = longPreferencesKey("cafe_dia")
        private val CAFE_NUNCA = booleanPreferencesKey("cafe_nunca")
        private val CAFE_PASO = booleanPreferencesKey("cafe_paso")
        private val MEJOR_SOLITARIO = intPreferencesKey("mejor_solitario")
        private val PARTICIPANTES = stringPreferencesKey("participantes")
        private val MODO = stringPreferencesKey("modo")
        private val NOMBRE_SALON = stringPreferencesKey("nombre_salon")

        private fun <T : Enum<T>> enumOPorDefecto(
            guardado: String?,
            valores: List<T>,
            porDefecto: T,
        ): T = valores.firstOrNull { it.name == guardado } ?: porDefecto
    }
}

// ---------------------------------------------------------------------------
// Serialización de participantes
//
// Vive aquí fuera y no dentro de `Preferencias` porque la copia de seguridad
// (`CopiaSeguridad`) reutiliza exactamente el mismo formato: así un fichero
// exportado y lo guardado en el móvil no pueden divergir.
// ---------------------------------------------------------------------------

fun participantesAJson(participantes: List<Participante>): String {
    val array = JSONArray()
    participantes.forEach { p ->
        val miembros = JSONArray().also { m -> p.miembros.forEach { m.put(it) } }
        array.put(
            JSONObject()
                .put("id", p.id)
                .put("nombre", p.nombre)
                .put("color", p.indiceColor)
                .put("miembros", miembros),
        )
    }
    return array.toString()
}

fun participantesDesdeJson(texto: String): List<Participante> =
    runCatching {
        val array = JSONArray(texto)
        (0 until array.length()).map { i ->
            val o = array.getJSONObject(i)
            val miembros = o.optJSONArray("miembros") ?: JSONArray()
            Participante(
                id = o.getInt("id"),
                nombre = o.getString("nombre"),
                indiceColor = o.getInt("color"),
                miembros = (0 until miembros.length()).map { miembros.getString(it) },
            )
        }
    }.getOrDefault(emptyList())
