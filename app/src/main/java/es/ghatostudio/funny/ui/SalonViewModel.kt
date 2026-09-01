package es.ghatostudio.funny.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.MAXIMO_PARTICIPANTES
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.Participante
import es.ghatostudio.funny.dominio.Prueba
import es.ghatostudio.funny.dominio.salon.DispositivoSalon
import es.ghatostudio.funny.dominio.salon.Mensaje
import es.ghatostudio.funny.dominio.salon.RolSalon
import es.ghatostudio.funny.dominio.salon.TipoAccion
import es.ghatostudio.funny.dominio.salon.VistaDelMando
import es.ghatostudio.funny.plataforma.TransporteSalon
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * El salón: varios móviles jugando la misma partida.
 *
 * Este ViewModel es **solo el cableado**. El hub aplica las acciones que llegan
 * llamando a los mismos métodos de [JuegoViewModel] que pulsaría un dedo en la
 * pantalla, así que no existen dos implementaciones de las reglas que puedan
 * separarse con el tiempo. Un mando no tiene reglas en absoluto: pinta lo que
 * el hub le manda y devuelve intenciones.
 *
 * Se prueba entero con `TransporteDeMentira`, que monta dos «móviles» en la
 * misma JVM. Lo que **no** se puede probar sin dos aparatos de verdad es la
 * radio: eso queda anotado en el informe del hito y en docs/ARCHITECTURE.md.
 */
class SalonViewModel : ViewModel() {

    data class Estado(
        val rol: RolSalon? = null,
        val miNombre: String = "",
        /** Mesas encontradas al buscar. */
        val encontrados: List<DispositivoSalon> = emptyList(),
        /** Dispositivos dentro del salón (en el hub, los mandos conectados). */
        val dispositivos: List<DispositivoSalon> = emptyList(),
        val conectado: Boolean = false,
        val buscando: Boolean = false,
        /** Lo que este mando tiene que enseñar. Null si es el hub. */
        val vista: VistaDelMando? = null,
        val fallo: TransporteSalon.Causa? = null,
        val permisosQueFaltan: List<String> = emptyList()
    ) {
        val esHub: Boolean get() = rol == RolSalon.HUB
        val esMando: Boolean get() = rol == RolSalon.MANDO
    }

    var estado by mutableStateOf(Estado())
        private set

    private var transporte: TransporteSalon? = null
    private var escucha: Job? = null

    /** El hub necesita el juego para aplicar las acciones que le llegan. */
    private var juego: JuegoViewModel? = null

    // --------------------------------------------------------------- arranque

    fun abrirComoHub(
        nuevoTransporte: TransporteSalon,
        nombre: String,
        juegoViewModel: JuegoViewModel
    ) {
        cerrar()
        juego = juegoViewModel
        transporte = nuevoTransporte
        estado = Estado(rol = RolSalon.HUB, miNombre = nombre)
        if (!comprobarPermisos(nuevoTransporte)) return
        escuchar(nuevoTransporte)
        nuevoTransporte.anunciarse(nombre)
    }

    fun abrirComoMando(nuevoTransporte: TransporteSalon, nombre: String) {
        cerrar()
        transporte = nuevoTransporte
        estado = Estado(rol = RolSalon.MANDO, miNombre = nombre, buscando = true)
        if (!comprobarPermisos(nuevoTransporte)) return
        escuchar(nuevoTransporte)
        nuevoTransporte.buscar(nombre)
    }

    private fun comprobarPermisos(t: TransporteSalon): Boolean {
        val faltan = t.permisosQueFaltan()
        if (faltan.isNotEmpty()) {
            estado = estado.copy(
                permisosQueFaltan = faltan,
                fallo = TransporteSalon.Causa.PERMISOS,
                buscando = false
            )
            return false
        }
        if (!t.estaDisponible()) {
            estado = estado.copy(fallo = TransporteSalon.Causa.SERVICIOS, buscando = false)
            return false
        }
        estado = estado.copy(permisosQueFaltan = emptyList(), fallo = null)
        return true
    }

    fun conectarA(id: String) {
        estado = estado.copy(buscando = false)
        transporte?.conectarA(id)
    }

    fun cerrar() {
        escucha?.cancel()
        escucha = null
        transporte?.let {
            if (estado.esHub) it.difundir(Mensaje.Adios)
            it.cerrar()
        }
        transporte = null
        juego = null
        estado = Estado()
    }

    // ---------------------------------------------------------------- escucha

    private fun escuchar(t: TransporteSalon) {
        escucha = viewModelScope.launch {
            t.sucesos.collect { suceso -> atender(suceso) }
        }
    }

    private fun atender(suceso: TransporteSalon.Suceso) {
        when (suceso) {
            is TransporteSalon.Suceso.Encontrado -> {
                if (estado.encontrados.any { it.id == suceso.id }) return
                estado = estado.copy(
                    encontrados = estado.encontrados +
                        DispositivoSalon(suceso.id, suceso.nombre)
                )
            }

            is TransporteSalon.Suceso.Conectado -> {
                if (estado.esHub) {
                    anadirDispositivo(DispositivoSalon(suceso.id, suceso.nombre))
                } else {
                    estado = estado.copy(conectado = true, buscando = false, fallo = null)
                }
            }

            is TransporteSalon.Suceso.Desconectado -> {
                if (estado.esHub) {
                    estado = estado.copy(
                        dispositivos = estado.dispositivos.filterNot { it.id == suceso.id }
                    )
                    difundirSalon()
                } else {
                    estado = estado.copy(conectado = false, vista = null)
                }
            }

            is TransporteSalon.Suceso.Recibido -> recibir(suceso.de, suceso.mensaje)

            is TransporteSalon.Suceso.Fallo ->
                estado = estado.copy(fallo = suceso.causa, buscando = false)
        }
    }

    private fun recibir(de: String, mensaje: Mensaje) {
        when (mensaje) {
            is Mensaje.Hola -> if (estado.esHub) {
                // El nombre bueno es el que manda el mando, no el del endpoint.
                estado = estado.copy(
                    dispositivos = estado.dispositivos.map {
                        if (it.id == de) it.copy(nombre = mensaje.nombre) else it
                    }
                )
                difundirSalon()
            }

            is Mensaje.Accion -> if (estado.esHub) aplicarAccion(de, mensaje)

            is Mensaje.Salon -> if (estado.esMando) {
                estado = estado.copy(dispositivos = mensaje.dispositivos, conectado = true)
            }

            is Mensaje.Vista -> if (estado.esMando) {
                estado = estado.copy(vista = mensaje.vista)
            }

            Mensaje.Adios -> if (estado.esMando) {
                estado = estado.copy(conectado = false, vista = null)
            }

            is Mensaje.Desconocido -> Unit
        }
    }

    // ------------------------------------------------------------------- hub

    private fun anadirDispositivo(dispositivo: DispositivoSalon) {
        val vm = juego ?: return
        if (estado.dispositivos.any { it.id == dispositivo.id }) return
        if (estado.dispositivos.size >= MAXIMO_MANDOS) return
        if (vm.estado.participantes.size >= MAXIMO_PARTICIPANTES) return

        // Cada móvil que entra es una persona con su ficha. En un salón el modo
        // natural es el individual: repartir móviles por equipos y luego pasarlos
        // dentro del equipo sería lo peor de los dos mundos.
        vm.elegirModo(Modo.INDIVIDUAL)
        vm.anadirParticipanteDeSalon(dispositivo.nombre, dispositivo.id)

        val idParticipante = vm.estado.participantes.lastOrNull()?.id
        estado = estado.copy(
            dispositivos = estado.dispositivos +
                dispositivo.copy(idParticipante = idParticipante)
        )
        difundirSalon()
        difundirVistas()
    }

    private fun aplicarAccion(de: String, accion: Mensaje.Accion) {
        val vm = juego ?: return
        val dispositivo = estado.dispositivos.firstOrNull { it.id == de } ?: return
        val estadoJuego = vm.estado
        val activo = estadoJuego.participanteActivo

        // Salvo renombrarse, un mando solo puede actuar en su propio turno. Es
        // la única comprobación de autoridad que hace falta y va aquí, en el
        // hub, porque un mando no es de fiar por definición.
        val esSuTurno = activo?.dispositivo == de

        when (accion.tipo) {
            TipoAccion.RENOMBRAR -> {
                dispositivo.idParticipante?.let { vm.renombrarParticipante(it, accion.texto) }
                difundirSalon()
            }

            TipoAccion.TIRAR -> if (esSuTurno) {
                vm.lanzarDado()
                vm.continuarTrasDado()
            }

            TipoAccion.EMPEZAR_PRUEBA -> if (esSuTurno) vm.empezarPrueba()

            TipoAccion.ELEGIR_JUEGO -> {
                // En una casilla comodín elige el rival, no quien juega.
                val eligeEste = estadoJuego.quienElige?.dispositivo == de
                if (eligeEste) {
                    Juego.porClave(accion.texto)?.let { vm.elegirJuego(it) }
                }
            }

            TipoAccion.RESPONDER -> if (estadoJuego.pantalla == Pantalla.RONDA_TODOS) {
                anotarRespuestaDeRonda(de, accion.entero)
            } else if (esSuTurno) {
                vm.resolverPrueba(superada = accion.entero == 1, puntos = accion.entero)
            }

            TipoAccion.TERMINAR -> if (esSuTurno) {
                vm.resolverPrueba(superada = accion.entero > 0, puntos = accion.entero)
            }

            TipoAccion.VEREDICTO -> if (esSuTurno) {
                vm.resolverPrueba(superada = accion.entero == 1)
            }
        }
        difundirVistas()
    }

    /**
     * Respuestas simultáneas de una casilla «juegan todos».
     *
     * Es la única parte del salón que necesita acumular estado propio: hay que
     * esperar a que respondan todos antes de resolver, y hasta entonces nadie
     * ve nada. Es también lo que hace que esta casilla sea mucho mejor con
     * varios móviles que pasándose uno.
     */
    private val respuestasDeRonda = mutableMapOf<String, Int>()

    private fun anotarRespuestaDeRonda(de: String, opcion: Int) {
        val vm = juego ?: return
        respuestasDeRonda[de] = opcion
        val esperados = vm.estado.participantes.count { it.dispositivo != null }
        if (respuestasDeRonda.size < esperados) return

        val correcta = correctaDeLaPrueba(vm)
        val aciertos = vm.estado.participantes.map { participante ->
            val respuesta = participante.dispositivo?.let { respuestasDeRonda[it] } ?: -1
            correcta != null && respuesta == correcta
        }
        respuestasDeRonda.clear()
        vm.resolverRondaDeTodos(aciertos)
    }

    private fun correctaDeLaPrueba(vm: JuegoViewModel): Int? = when (val p = vm.estado.prueba) {
        is Prueba.DePreguntas -> p.pregunta.correcta
        is Prueba.DeCuando -> p.opciones.indexOf(p.evento.anio)
        is Prueba.DeEmojis -> p.correcta
        else -> null
    }

    fun difundirSalon() {
        val vm = juego ?: return
        transporte?.difundir(
            Mensaje.Salon(
                dispositivos = estado.dispositivos,
                modo = vm.estado.modo,
                partidaEnCurso = vm.estado.partidaEnCurso
            )
        )
    }

    /** Manda a cada mando su vista. Se llama tras cada cambio de estado. */
    fun difundirVistas() {
        val vm = juego ?: return
        val t = transporte ?: return
        estado.dispositivos.forEach { dispositivo ->
            t.enviar(dispositivo.id, Mensaje.Vista(vistaPara(dispositivo, vm)))
        }
    }

    private fun vistaPara(dispositivo: DispositivoSalon, vm: JuegoViewModel): VistaDelMando {
        val e = vm.estado
        val activo = e.participanteActivo
        val esSuTurno = activo?.dispositivo == dispositivo.id
        val juegoActual = e.juego

        // El contenido privado (la palabra de mímica, la carta de tabú, la
        // palabra a dibujar) va SOLO al móvil de quien actúa. Es la razón de ser
        // del salón: nadie ve por error lo que no debe.
        val privado = if (esSuTurno && juegoActual?.soloActuante == true) {
            when (val prueba = e.prueba) {
                is Prueba.DeMimica -> prueba.palabras
                is Prueba.DeDibujo -> prueba.palabras
                is Prueba.DeTabu -> prueba.cartas.map { carta ->
                    (listOf(carta.palabra) + carta.prohibidas).joinToString(SEPARADOR_TABU)
                }
                is Prueba.DeTrabalenguas -> listOf(prueba.trabalenguas.texto)
                is Prueba.DeCanta -> listOf(
                    prueba.cancion.titulo,
                    prueba.cancion.artista,
                    prueba.cancion.pista
                )
                is Prueba.DeDesafio -> listOf(prueba.desafio.texto)
                else -> emptyList()
            }
        } else {
            emptyList()
        }

        // En una ronda de todos, cada mando ve el enunciado y responde en el suyo.
        val enRonda = e.pantalla == Pantalla.RONDA_TODOS
        val enunciado = if (enRonda) enunciadoDe(e.prueba) else null
        val opciones = if (enRonda) opcionesDe(e.prueba) else emptyList()

        return VistaDelMando(
            pantalla = e.pantalla,
            modo = e.modo,
            esMiTurno = esSuTurno,
            nombreDelActivo = activo?.nombre.orEmpty(),
            juego = juegoActual,
            contenidoPrivado = privado,
            enunciado = enunciado,
            opciones = opciones,
            respuestaEnviada = respuestasDeRonda.containsKey(dispositivo.id)
        )
    }

    private fun enunciadoDe(prueba: Prueba?): String? = when (prueba) {
        is Prueba.DePreguntas -> prueba.pregunta.texto
        is Prueba.DeCuando -> prueba.evento.texto
        is Prueba.DeEmojis -> prueba.carta.emojis
        else -> null
    }

    private fun opcionesDe(prueba: Prueba?): List<String> = when (prueba) {
        is Prueba.DePreguntas -> prueba.pregunta.opciones
        is Prueba.DeCuando -> prueba.opciones.map { it.toString() }
        is Prueba.DeEmojis -> prueba.opciones
        else -> emptyList()
    }

    // ----------------------------------------------------------------- mando

    fun enviarAccion(tipo: TipoAccion, entero: Int = 0, texto: String = "") {
        val t = transporte ?: return
        val hub = estado.dispositivos.firstOrNull()?.id
        if (hub != null) {
            t.enviar(hub, Mensaje.Accion(tipo, entero, texto))
        } else {
            // Un mando solo tiene una conexión: difundir es mandárselo al hub.
            t.difundir(Mensaje.Accion(tipo, entero, texto))
        }
    }

    fun presentarse() {
        transporte?.difundir(Mensaje.Hola(estado.miNombre))
    }

    fun limpiarFallo() {
        estado = estado.copy(fallo = null)
    }

    override fun onCleared() {
        cerrar()
        super.onCleared()
    }

    companion object {
        /** `P2P_STAR` admite cuatro mandos además del hub. */
        const val MAXIMO_MANDOS = 4

        /** Separador de la carta de tabú al viajar como una sola cadena. */
        const val SEPARADOR_TABU = "|"
    }
}
