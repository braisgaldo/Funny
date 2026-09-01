package es.ghatostudio.funny.dominio.salon

import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.Pantalla
import org.json.JSONArray
import org.json.JSONObject

// ---------------------------------------------------------------------------
// El protocolo del salón: lo que se dicen los móviles entre ellos.
//
// Es Kotlin puro y sin nada de Android salvo `org.json`, que en la práctica es
// un detalle de plataforma con el que se puede vivir (y que se sustituye por
// kotlinx.serialization el día de la migración a KMP, sin tocar los tipos).
//
// POR QUÉ UN HUB Y NO TODOS CONTRA TODOS
//
// Un dispositivo hace de «mesa» (el hub) y los demás de «mando». El hub es el
// único que tiene el estado de la partida y el único que aplica las reglas; los
// mandos solo mandan intenciones («he tocado la opción B») y reciben lo que les
// toca ver. Esto evita de raíz el problema difícil de verdad de un juego en red
// —que dos dispositivos crean cosas distintas— y encaja con cómo se juega en una
// mesa: hay un móvil en el centro y los demás en las manos.
//
// QUÉ GANA LA PARTIDA CON ESTO
//
// Lo importante no es ver el tablero en varias pantallas, es que la palabra
// secreta solo llega al móvil de quien actúa, y que en las casillas de «juegan
// todos» cada uno responde en el suyo a la vez, sin pasarse nada.
// ---------------------------------------------------------------------------

/** Versión del protocolo. Dos móviles con versiones distintas no se emparejan. */
const val VERSION_PROTOCOLO = 1

/** El papel de este dispositivo en el salón. */
enum class RolSalon { HUB, MANDO }

/** Un dispositivo conectado al hub. */
data class DispositivoSalon(
    /** Identificador que da el transporte. Único dentro de la sesión. */
    val id: String,
    val nombre: String,
    /** Id del participante que lleva este dispositivo, si ya se le ha asignado. */
    val idParticipante: Int? = null,
)

/**
 * Lo que un mando necesita saber para pintar su pantalla.
 *
 * No es el estado completo de la partida: es lo justo. Mandar el estado entero a
 * cada mando obligaría a serializar el tablero y las doce pruebas en cada
 * cambio, y además le daría a cada móvil información que no le toca ver.
 */
data class VistaDelMando(
    val pantalla: Pantalla,
    val modo: Modo,
    /** Si a este dispositivo le toca actuar ahora. */
    val esMiTurno: Boolean,
    val nombreDelActivo: String,
    val juego: Juego?,
    /** Contenido privado (la palabra de mímica, la carta de tabú…), si toca. */
    val contenidoPrivado: List<String> = emptyList(),
    /** Enunciado y opciones cuando el mando tiene que responder. */
    val enunciado: String? = null,
    val opciones: List<String> = emptyList(),
    /** Si ya se ha enviado la respuesta de esta ronda. */
    val respuestaEnviada: Boolean = false,
)

/**
 * Los mensajes del protocolo.
 *
 * Cada uno se serializa como un JSON con un campo `tipo`. Un mensaje
 * desconocido se ignora en silencio en lugar de romper la conexión: así una
 * versión futura puede añadir mensajes sin dejar tirados a los móviles viejos.
 */
sealed interface Mensaje {
    /** Mando → hub, al conectar. */
    data class Hola(
        val nombre: String,
        val version: Int = VERSION_PROTOCOLO,
    ) : Mensaje

    /** Hub → todos: quién está en el salón y con qué ajustes se va a jugar. */
    data class Salon(
        val dispositivos: List<DispositivoSalon>,
        val modo: Modo,
        val partidaEnCurso: Boolean,
    ) : Mensaje

    /** Hub → un mando: lo que tiene que enseñar ahora. */
    data class Vista(
        val vista: VistaDelMando,
    ) : Mensaje

    /** Mando → hub: una acción de quien juega. */
    data class Accion(
        val tipo: TipoAccion,
        val entero: Int = 0,
        val texto: String = "",
    ) : Mensaje

    /** Hub → todos: el salón se cierra. */
    data object Adios : Mensaje

    /** Mensaje recibido que no se sabe interpretar. Se ignora. */
    data class Desconocido(
        val tipo: String,
    ) : Mensaje
}

/** Las intenciones que un mando puede mandar al hub. */
enum class TipoAccion {
    /** Tirar el dado en el turno propio. */
    TIRAR,

    /** Pasar de la pantalla de entrega a la prueba. */
    EMPEZAR_PRUEBA,

    /** Elegir una opción; `entero` es su índice. */
    RESPONDER,

    /** Cerrar la prueba propia; `entero` son los aciertos. */
    TERMINAR,

    /** Veredicto de una prueba que juzga la mesa; `entero` 1 = logrado. */
    VEREDICTO,

    /** Elegir juego en una casilla comodín; `texto` es la clave del juego. */
    ELEGIR_JUEGO,

    /** Cambiar el nombre propio; `texto` es el nuevo. */
    RENOMBRAR,
}

// ---------------------------------------------------------------------------
// Serialización
//
// A mano y con `org.json`. Son siete mensajes con campos planos: una librería
// de serialización aquí añadiría un plugin de compilación y un formato binario
// que nadie puede depurar mirando un log.
// ---------------------------------------------------------------------------

object Codec {
    fun aTexto(mensaje: Mensaje): String =
        when (mensaje) {
            is Mensaje.Hola ->
                JSONObject()
                    .put(TIPO, "hola")
                    .put("nombre", mensaje.nombre)
                    .put("version", mensaje.version)
                    .toString()

            is Mensaje.Salon ->
                JSONObject()
                    .put(TIPO, "salon")
                    .put("modo", mensaje.modo.name)
                    .put("enCurso", mensaje.partidaEnCurso)
                    .put(
                        "dispositivos",
                        JSONArray().also { array ->
                            mensaje.dispositivos.forEach { d ->
                                array.put(
                                    JSONObject()
                                        .put("id", d.id)
                                        .put("nombre", d.nombre)
                                        .put("participante", d.idParticipante ?: JSONObject.NULL),
                                )
                            }
                        },
                    ).toString()

            is Mensaje.Vista ->
                JSONObject()
                    .put(TIPO, "vista")
                    .put("pantalla", mensaje.vista.pantalla.name)
                    .put("modo", mensaje.vista.modo.name)
                    .put("miTurno", mensaje.vista.esMiTurno)
                    .put("activo", mensaje.vista.nombreDelActivo)
                    .put("juego", mensaje.vista.juego?.clave ?: JSONObject.NULL)
                    .put("privado", JSONArray(mensaje.vista.contenidoPrivado))
                    .put("enunciado", mensaje.vista.enunciado ?: JSONObject.NULL)
                    .put("opciones", JSONArray(mensaje.vista.opciones))
                    .put("enviada", mensaje.vista.respuestaEnviada)
                    .toString()

            is Mensaje.Accion ->
                JSONObject()
                    .put(TIPO, "accion")
                    .put("accion", mensaje.tipo.name)
                    .put("entero", mensaje.entero)
                    .put("texto", mensaje.texto)
                    .toString()

            Mensaje.Adios -> JSONObject().put(TIPO, "adios").toString()

            is Mensaje.Desconocido -> JSONObject().put(TIPO, mensaje.tipo).toString()
        }

    fun deTexto(texto: String): Mensaje =
        runCatching {
            val o = JSONObject(texto)
            when (val tipo = o.optString(TIPO)) {
                "hola" ->
                    Mensaje.Hola(
                        nombre = o.optString("nombre"),
                        version = o.optInt("version", 0),
                    )

                "salon" -> {
                    val array = o.optJSONArray("dispositivos") ?: JSONArray()
                    Mensaje.Salon(
                        dispositivos =
                            (0 until array.length()).map { i ->
                                val d = array.getJSONObject(i)
                                DispositivoSalon(
                                    id = d.optString("id"),
                                    nombre = d.optString("nombre"),
                                    idParticipante =
                                        d
                                            .opt("participante")
                                            ?.takeIf { it != JSONObject.NULL } as? Int,
                                )
                            },
                        modo = enumDe(o.optString("modo"), Modo.entries, Modo.EQUIPOS),
                        partidaEnCurso = o.optBoolean("enCurso", false),
                    )
                }

                "vista" -> {
                    val privado = o.optJSONArray("privado") ?: JSONArray()
                    val opciones = o.optJSONArray("opciones") ?: JSONArray()
                    Mensaje.Vista(
                        VistaDelMando(
                            pantalla =
                                enumDe(
                                    o.optString("pantalla"),
                                    Pantalla.entries,
                                    Pantalla.SALON,
                                ),
                            modo = enumDe(o.optString("modo"), Modo.entries, Modo.EQUIPOS),
                            esMiTurno = o.optBoolean("miTurno", false),
                            nombreDelActivo = o.optString("activo"),
                            juego = Juego.porClave(o.optString("juego")),
                            contenidoPrivado =
                                (0 until privado.length()).map {
                                    privado.getString(
                                        it,
                                    )
                                },
                            enunciado =
                                o.optString("enunciado").takeIf {
                                    it.isNotBlank() && it != "null"
                                },
                            opciones = (0 until opciones.length()).map { opciones.getString(it) },
                            respuestaEnviada = o.optBoolean("enviada", false),
                        ),
                    )
                }

                "accion" ->
                    Mensaje.Accion(
                        tipo = enumDe(o.optString("accion"), TipoAccion.entries, TipoAccion.TIRAR),
                        entero = o.optInt("entero", 0),
                        texto = o.optString("texto"),
                    )

                "adios" -> Mensaje.Adios

                else -> Mensaje.Desconocido(tipo)
            }
        }.getOrElse { Mensaje.Desconocido("ilegible") }

    private const val TIPO = "tipo"

    private fun <T : Enum<T>> enumDe(nombre: String?, valores: List<T>, porDefecto: T): T =
        valores.firstOrNull { it.name == nombre } ?: porDefecto
}
