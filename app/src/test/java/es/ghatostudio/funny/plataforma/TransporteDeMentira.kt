package es.ghatostudio.funny.plataforma

import es.ghatostudio.funny.dominio.salon.Codec
import es.ghatostudio.funny.dominio.salon.Mensaje
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Dos «móviles» en la misma JVM.
 *
 * Es la única forma honesta de probar el salón sin dos aparatos delante: se
 * sustituye la radio por una entrega directa entre objetos y se ejercita todo
 * lo demás —el protocolo, los roles, quién puede hacer qué y a quién le llega
 * la palabra secreta— exactamente como en un salón de verdad.
 *
 * Lo que esto **no** prueba es la radio: descubrimiento, permisos, alcance y
 * reconexiones. Eso solo se comprueba con dos móviles, y queda anotado como tal
 * en el informe del hito y en docs/ARCHITECTURE.md.
 *
 * Los mensajes pasan por [Codec] a propósito, aunque aquí no haría falta
 * serializar: así el test también cubre que ida y vuelta por JSON no pierde
 * nada, que es donde de verdad se rompen estas cosas.
 */
class TransporteDeMentira(val id: String) : TransporteSalon {

    private val flujo = MutableSharedFlow<TransporteSalon.Suceso>(
        replay = 0,
        extraBufferCapacity = 256,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val sucesos: Flow<TransporteSalon.Suceso> = flujo.asSharedFlow()

    /** Los demás transportes de la red de mentira, por su id. */
    private val vecinos = mutableMapOf<String, TransporteDeMentira>()

    private var nombre: String = ""

    var cerrado = false
        private set

    /** Todo lo enviado, para poder afirmar sobre el tráfico en los tests. */
    val enviados = mutableListOf<Pair<String, Mensaje>>()

    // ------------------------------------------------------------- la red

    /** Une dos transportes: se ven y se pueden hablar. */
    fun enchufar(otro: TransporteDeMentira) {
        vecinos[otro.id] = otro
        otro.vecinos[id] = this
    }

    /** Simula que [otro] aparece en el descubrimiento de este. */
    suspend fun anunciarADescubierto(otro: TransporteDeMentira) {
        flujo.emit(TransporteSalon.Suceso.Encontrado(otro.id, otro.nombre))
    }

    /** Simula el corte de la conexión con [otro]. */
    suspend fun desconectarDe(otro: TransporteDeMentira) {
        vecinos.remove(otro.id)
        flujo.emit(TransporteSalon.Suceso.Desconectado(otro.id))
    }

    /** Provoca un fallo del transporte, para probar cómo lo cuenta la interfaz. */
    suspend fun fallar(causa: TransporteSalon.Causa) {
        flujo.emit(TransporteSalon.Suceso.Fallo(causa))
    }

    // ---------------------------------------------------- TransporteSalon

    override fun permisosQueFaltan(): List<String> = permisosQueFingirQueFaltan

    var permisosQueFingirQueFaltan: List<String> = emptyList()

    override fun estaDisponible(): Boolean = disponible

    var disponible = true

    override fun anunciarse(nombre: String) {
        this.nombre = nombre
    }

    override fun buscar(nombre: String) {
        this.nombre = nombre
    }

    override fun conectarA(id: String) = conectarAOtro(id)

    /**
     * El parámetro se llama distinto que la propiedad [id] a propósito: cuando
     * se llamaba igual, la tapaba, y el hub recibía su propio identificador como
     * si fuera el del mando. El síntoma era que todos los participantes del
     * salón quedaban asociados al móvil de la mesa.
     */
    private fun conectarAOtro(idDelOtro: String) {
        val otro = vecinos[idDelOtro] ?: return
        // Las dos partes se enteran, como haría Nearby.
        flujo.tryEmit(TransporteSalon.Suceso.Conectado(otro.id, otro.nombre))
        otro.flujo.tryEmit(TransporteSalon.Suceso.Conectado(id, nombre))
    }

    override fun enviar(a: String, mensaje: Mensaje) {
        enviados += a to mensaje
        val destino = vecinos[a] ?: return
        // Ida y vuelta por el codec: si el protocolo pierde un campo, se ve aquí.
        val recibido = Codec.deTexto(Codec.aTexto(mensaje))
        destino.flujo.tryEmit(TransporteSalon.Suceso.Recibido(id, recibido))
    }

    override fun difundir(mensaje: Mensaje) {
        vecinos.keys.toList().forEach { enviar(it, mensaje) }
    }

    override fun cerrar() {
        cerrado = true
        vecinos.clear()
    }
}
