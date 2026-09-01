package es.ghatostudio.funny.plataforma

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.android.gms.nearby.connection.Strategy
import es.ghatostudio.funny.dominio.salon.Codec
import es.ghatostudio.funny.dominio.salon.Mensaje
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Salón sobre **Nearby Connections**, la capa gratuita de Google Play Services.
 *
 * ### Por qué Nearby y no Wi-Fi Direct a pelo
 *
 * Lo que hace falta aquí es «que dos móviles que están en la misma mesa se
 * hablen, sin router y sin internet». Nearby negocia por su cuenta Bluetooth,
 * BLE y Wi-Fi Direct, elige el mejor canal disponible y se encarga de la parte
 * horrible: descubrimiento, emparejamiento, reintentos y trocear los mensajes.
 * `WifiP2pManager` a pelo son unas mil líneas de máquina de estados que fallan
 * distinto en cada fabricante, y con Bluetooth clásico habría que emparejar los
 * móviles a mano desde los ajustes del sistema antes de jugar.
 *
 * Es gratis y no es un servicio de pago de Google (ver ADR-0003); el precio que
 * sí tiene es depender de Play Services, y por eso [estaDisponible] existe y la
 * app sigue siendo jugable con un solo móvil si no están.
 *
 * ### Topología
 *
 * `P2P_STAR`: un hub y hasta cuatro mandos, que es exactamente la forma del
 * salón. No se usa `P2P_CLUSTER` porque una malla de todos contra todos no
 * aporta nada aquí y multiplica los estados posibles.
 */
class TransporteNearby(
    private val context: Context,
    private val rol: Rol,
) : TransporteSalon {
    enum class Rol { HUB, MANDO }

    private val cliente: ConnectionsClient = Nearby.getConnectionsClient(context)

    private val flujo =
        MutableSharedFlow<TransporteSalon.Suceso>(
            replay = 0,
            extraBufferCapacity = 64,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )

    override val sucesos: Flow<TransporteSalon.Suceso> = flujo.asSharedFlow()

    private val nombresPorId = mutableMapOf<String, String>()
    private val conectados = mutableSetOf<String>()
    private var miNombre: String = ""

    // ------------------------------------------------------------- permisos

    /**
     * Los permisos cambian bastante según la versión de Android:
     *
     * - Android 13+ pide `NEARBY_WIFI_DEVICES` y los de Bluetooth nuevos, y ya
     *   **no** hace falta la ubicación.
     * - Android 12 pide `BLUETOOTH_SCAN`, `BLUETOOTH_ADVERTISE` y
     *   `BLUETOOTH_CONNECT`, y sigue pidiendo ubicación fina.
     * - Por debajo, los permisos de Bluetooth antiguos y la ubicación.
     *
     * Se calcula en tiempo de ejecución y no se declara una lista fija para no
     * pedirle a un móvil moderno permisos que ya no necesita.
     */
    override fun permisosQueFaltan(): List<String> =
        permisosNecesarios().filter { permiso ->
            ContextCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED
        }

    private fun permisosNecesarios(): List<String> =
        buildList {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                add(Manifest.permission.BLUETOOTH_SCAN)
                add(Manifest.permission.BLUETOOTH_ADVERTISE)
                add(Manifest.permission.BLUETOOTH_CONNECT)
            } else {
                @Suppress("DEPRECATION")
                add(Manifest.permission.BLUETOOTH)
                @Suppress("DEPRECATION")
                add(Manifest.permission.BLUETOOTH_ADMIN)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.NEARBY_WIFI_DEVICES)
            } else {
                // Hasta Android 12 incluido, buscar por Bluetooth exige ubicación.
                add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

    override fun estaDisponible(): Boolean =
        runCatching {
            val gestor = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
            gestor?.adapter != null
        }.getOrDefault(false)

    /** Diagnóstico antes de empezar: es más útil que un fallo genérico después. */
    private fun problemaPrevio(): TransporteSalon.Causa? {
        if (permisosQueFaltan().isNotEmpty()) return TransporteSalon.Causa.PERMISOS

        val gestorBt = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
        val adaptador: BluetoothAdapter? = gestorBt?.adapter
        if (adaptador == null) return TransporteSalon.Causa.SERVICIOS
        if (!adaptador.isEnabled) return TransporteSalon.Causa.BLUETOOTH

        // Por debajo de Android 13, sin ubicación activada Nearby no descubre
        // nada y falla con un error que no dice qué pasa.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            val gestorUbicacion =
                context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
            val activada =
                runCatching {
                    gestorUbicacion?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||
                        gestorUbicacion?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
                }.getOrDefault(false)
            if (!activada) return TransporteSalon.Causa.UBICACION
        }
        return null
    }

    // -------------------------------------------------------------- salón

    override fun anunciarse(nombre: String) {
        miNombre = nombre
        problemaPrevio()?.let {
            emitir(TransporteSalon.Suceso.Fallo(it))
            return
        }

        cliente
            .startAdvertising(
                nombre,
                SERVICIO,
                devolucionesDeConexion,
                AdvertisingOptions.Builder().setStrategy(ESTRATEGIA).build(),
            ).addOnFailureListener { error ->
                Log.w(ETIQUETA, "No se ha podido anunciar el salón", error)
                emitir(
                    TransporteSalon.Suceso.Fallo(
                        TransporteSalon.Causa.DESCONOCIDA,
                        error.message.orEmpty(),
                    ),
                )
            }
    }

    override fun buscar(nombre: String) {
        miNombre = nombre
        problemaPrevio()?.let {
            emitir(TransporteSalon.Suceso.Fallo(it))
            return
        }

        cliente
            .startDiscovery(
                SERVICIO,
                devolucionesDeDescubrimiento,
                DiscoveryOptions.Builder().setStrategy(ESTRATEGIA).build(),
            ).addOnFailureListener { error ->
                Log.w(ETIQUETA, "No se ha podido buscar salones", error)
                emitir(
                    TransporteSalon.Suceso.Fallo(
                        TransporteSalon.Causa.DESCONOCIDA,
                        error.message.orEmpty(),
                    ),
                )
            }
    }

    override fun conectarA(id: String) {
        cliente
            .requestConnection(miNombre, id, devolucionesDeConexion)
            .addOnFailureListener { error ->
                Log.w(ETIQUETA, "No se ha podido conectar a $id", error)
                emitir(
                    TransporteSalon.Suceso.Fallo(
                        TransporteSalon.Causa.DESCONOCIDA,
                        error.message.orEmpty(),
                    ),
                )
            }
    }

    override fun enviar(a: String, mensaje: Mensaje) {
        val bytes = Codec.aTexto(mensaje).toByteArray(Charsets.UTF_8)
        cliente.sendPayload(a, Payload.fromBytes(bytes))
    }

    override fun difundir(mensaje: Mensaje) {
        if (conectados.isEmpty()) return
        val bytes = Codec.aTexto(mensaje).toByteArray(Charsets.UTF_8)
        cliente.sendPayload(conectados.toList(), Payload.fromBytes(bytes))
    }

    override fun cerrar() {
        runCatching { cliente.stopAdvertising() }
        runCatching { cliente.stopDiscovery() }
        runCatching { cliente.stopAllEndpoints() }
        conectados.clear()
        nombresPorId.clear()
    }

    // ------------------------------------------------------- devoluciones

    private val devolucionesDeDescubrimiento =
        object : EndpointDiscoveryCallback() {
            override fun onEndpointFound(id: String, info: DiscoveredEndpointInfo) {
                nombresPorId[id] = info.endpointName
                emitir(TransporteSalon.Suceso.Encontrado(id, info.endpointName))
            }

            override fun onEndpointLost(id: String) {
                nombresPorId.remove(id)
                emitir(TransporteSalon.Suceso.Desconectado(id))
            }
        }

    private val devolucionesDeConexion =
        object : ConnectionLifecycleCallback() {
            override fun onConnectionInitiated(id: String, info: ConnectionInfo) {
                nombresPorId[id] = info.endpointName
                // Se acepta sin pedir confirmación de código a propósito: esto es un
                // juego de mesa, no una transferencia de ficheros. El «emparejamiento»
                // de verdad es que los móviles estén a dos metros y que quien crea el
                // salón vea aparecer el nombre en su lista.
                cliente.acceptConnection(id, devolucionesDeCarga)
            }

            override fun onConnectionResult(id: String, resolucion: ConnectionResolution) {
                if (resolucion.status.isSuccess) {
                    conectados += id
                    val nombre = nombresPorId[id].orEmpty()
                    emitir(TransporteSalon.Suceso.Conectado(id, nombre))
                    // Un mando ya no necesita seguir buscando.
                    if (rol == Rol.MANDO) runCatching { cliente.stopDiscovery() }
                } else {
                    emitir(
                        TransporteSalon.Suceso.Fallo(
                            TransporteSalon.Causa.DESCONOCIDA,
                            resolucion.status.statusMessage.orEmpty(),
                        ),
                    )
                }
            }

            override fun onDisconnected(id: String) {
                conectados -= id
                emitir(TransporteSalon.Suceso.Desconectado(id))
            }
        }

    private val devolucionesDeCarga =
        object : PayloadCallback() {
            override fun onPayloadReceived(id: String, carga: Payload) {
                val bytes = carga.asBytes() ?: return
                val mensaje = Codec.deTexto(bytes.toString(Charsets.UTF_8))
                emitir(TransporteSalon.Suceso.Recibido(id, mensaje))
            }

            override fun onPayloadTransferUpdate(id: String, actualizacion: PayloadTransferUpdate) {
                // Los mensajes de Funny son unos cientos de bytes y llegan de una
                // pieza: no hay progreso que enseñar.
            }
        }

    private fun emitir(suceso: TransporteSalon.Suceso) {
        flujo.tryEmit(suceso)
    }

    companion object {
        private const val ETIQUETA = "FunnySalon"

        /**
         * Identificador del servicio. Lleva la versión del protocolo dentro para
         * que dos móviles con versiones incompatibles ni se vean.
         */
        const val SERVICIO = "es.ghatostudio.funny.salon.v1"

        private val ESTRATEGIA = Strategy.P2P_STAR
    }
}
