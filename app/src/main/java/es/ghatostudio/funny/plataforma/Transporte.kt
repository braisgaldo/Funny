package es.ghatostudio.funny.plataforma

import es.ghatostudio.funny.dominio.salon.Mensaje
import kotlinx.coroutines.flow.Flow

/**
 * Lo que Funny necesita de la radio del móvil para montar un salón, y nada más.
 *
 * Es una interfaz por dos razones muy concretas:
 *
 *  1. Los tests pueden montar dos «móviles» en la misma JVM con un transporte
 *     de mentira ([TransporteDeMentira]) y ejercitar el protocolo entero sin
 *     radios ni permisos. Es la única forma honesta de probar esto sin dos
 *     dispositivos físicos delante.
 *  2. El día que haya que cambiar Nearby Connections por Wi-Fi Direct a pelo,
 *     o por lo que traiga iOS, solo se toca la implementación.
 */
interface TransporteSalon {
    /** Lo que le pasa al transporte, para que el ViewModel reaccione. */
    sealed interface Suceso {
        data class Encontrado(
            val id: String,
            val nombre: String,
        ) : Suceso

        data class Conectado(
            val id: String,
            val nombre: String,
        ) : Suceso

        data class Desconectado(
            val id: String,
        ) : Suceso

        data class Recibido(
            val de: String,
            val mensaje: Mensaje,
        ) : Suceso

        data class Fallo(
            val causa: Causa,
            val detalle: String = "",
        ) : Suceso
    }

    /**
     * Por qué ha fallado. Se distingue el motivo porque cada uno tiene una
     * salida distinta para quien juega: dar permisos, encender el Bluetooth,
     * encender la ubicación o rendirse y jugar con un solo móvil.
     */
    enum class Causa { PERMISOS, BLUETOOTH, UBICACION, SERVICIOS, DESCONOCIDA }

    val sucesos: Flow<Suceso>

    /** Permisos que hace falta conceder en este móvil, o vacío si ya están. */
    fun permisosQueFaltan(): List<String>

    /** Si el móvil tiene lo necesario para montar o unirse a un salón. */
    fun estaDisponible(): Boolean

    /** Empieza a anunciarse como mesa con [nombre]. */
    fun anunciarse(nombre: String)

    /** Empieza a buscar mesas cerca. */
    fun buscar(nombre: String)

    /** Se conecta a una mesa encontrada. */
    fun conectarA(id: String)

    /** Manda un mensaje a un dispositivo concreto. */
    fun enviar(a: String, mensaje: Mensaje)

    /** Manda un mensaje a todos los conectados. */
    fun difundir(mensaje: Mensaje)

    /** Corta todo y libera la radio. */
    fun cerrar()
}

/**
 * Transporte que no hace nada, para cuando el móvil no tiene lo necesario.
 *
 * Existe para que la pantalla del salón no tenga que preguntar `if (transporte
 * == null)` por todas partes: se comporta como un transporte normal que informa
 * de que no hay servicios y se queda quieto.
 */
class TransporteNoDisponible(
    private val causa: TransporteSalon.Causa = TransporteSalon.Causa.SERVICIOS,
) : TransporteSalon {
    override val sucesos: Flow<TransporteSalon.Suceso> =
        kotlinx.coroutines.flow.flowOf(TransporteSalon.Suceso.Fallo(causa))

    override fun permisosQueFaltan(): List<String> = emptyList()

    override fun estaDisponible(): Boolean = false

    override fun anunciarse(nombre: String) = Unit

    override fun buscar(nombre: String) = Unit

    override fun conectarA(id: String) = Unit

    override fun enviar(a: String, mensaje: Mensaje) = Unit

    override fun difundir(mensaje: Mensaje) = Unit

    override fun cerrar() = Unit
}
