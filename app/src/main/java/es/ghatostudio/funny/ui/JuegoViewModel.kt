package es.ghatostudio.funny.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import es.ghatostudio.funny.datos.ContenidoDeAssets
import es.ghatostudio.funny.datos.FuenteContenido
import es.ghatostudio.funny.datos.Preferencias
import es.ghatostudio.funny.dominio.Ajustes
import es.ghatostudio.funny.dominio.Contenido
import es.ghatostudio.funny.dominio.EstadoJuego
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.MAXIMO_PARTICIPANTES
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.MotorJuego
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.Participante
import es.ghatostudio.funny.dominio.Repartidor
import es.ghatostudio.funny.dominio.TemaId
import es.ghatostudio.funny.dominio.coloresDisponibles
import es.ghatostudio.funny.ui.i18n.Idioma
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

/**
 * El pegamento entre el motor de reglas y la interfaz.
 *
 * Aquí no hay reglas de juego: todo lo que decide qué pasa en una partida vive
 * en [MotorJuego], que es Kotlin puro. Este ViewModel solo sabe de tres cosas
 * que sí son de Android: cargar el contenido de los assets, guardar en
 * preferencias y decidir cuándo enseñar la hoja de la donación.
 */
class JuegoViewModel(
    aplicacion: Application,
    private val prefs: Preferencias = Preferencias(aplicacion),
    private val fuente: FuenteContenido = ContenidoDeAssets(aplicacion),
    /** Etiqueta de idioma del sistema. Se inyecta para poder probarla. */
    idiomaDelSistema: String = aplicacion.resources.configuration.locales[0].toLanguageTag()
) : AndroidViewModel(aplicacion) {

    private val rnd = Random(System.nanoTime())
    private val idiomaSistema = Idioma.deEtiqueta(idiomaDelSistema)

    private var contenido: Contenido = Contenido()
    private var motor: MotorJuego = crearMotor(contenido, Contenido())

    var estado by mutableStateOf(EstadoJuego())
        private set

    /** Lo que enseña la hoja de la donación, o null si no está abierta. */
    var hojaCafeVisible by mutableStateOf(false)
        private set

    /** Mensaje breve para el snackbar, o null. Se consume con [mensajeVisto]. */
    var mensaje by mutableStateOf<String?>(null)
        private set

    init {
        val guardado = prefs.cargarAhora()
        val participantes = guardado.participantes.ifEmpty { emptyList() }
        estado = EstadoJuego(
            ajustes = guardado.ajustes,
            modo = guardado.modo,
            participantes = participantes
        )
        recargarContenido()
    }

    // ------------------------------------------------------------- idioma

    /** El idioma que toca: el elegido a mano, o el del móvil si no hay elección. */
    val idioma: Idioma
        get() = estado.ajustes.idioma?.let { Idioma.deEtiqueta(it) } ?: idiomaSistema

    private fun recargarContenido() {
        contenido = fuente.cargar(idioma.codigo)
        motor = crearMotor(contenido, contenido)
    }

    private fun crearMotor(paraRepartir: Contenido, paraConsultar: Contenido) = MotorJuego(
        contenido = paraConsultar,
        repartidor = Repartidor(paraRepartir, rnd),
        rnd = rnd
    )

    val contenidoActual: Contenido get() = contenido

    // --------------------------------------------------------- navegación

    fun ir(pantalla: Pantalla) {
        estado = estado.copy(pantalla = pantalla)
    }

    /**
     * Vuelve al menú. Es el único sitio desde el que puede aparecer la hoja de
     * la donación: al cerrar una partida terminada, nunca en el arranque ni
     * encima de una tarea a medias.
     */
    fun volverAlMenu() {
        val veniaDeTerminar = estado.pantalla == Pantalla.VICTORIA ||
            estado.pantalla == Pantalla.SOLITARIO_FIN
        estado = estado.copy(pantalla = Pantalla.INICIO)
        if (veniaDeTerminar) {
            anotarUsoReal()
            if (tocaOfrecerCafe()) abrirHojaCafe(automatica = true)
        }
    }

    fun mensajeVisto() {
        mensaje = null
    }

    fun avisar(texto: String) {
        mensaje = texto
    }

    // ------------------------------------------------------------- ajustes

    fun actualizarAjustes(nuevos: Ajustes) {
        val cambiaIdioma = nuevos.idioma != estado.ajustes.idioma
        estado = estado.copy(ajustes = nuevos)
        if (cambiaIdioma) recargarContenido()
        viewModelScope.launch { prefs.guardarAjustes(nuevos) }
    }

    fun elegirTema(id: TemaId) {
        actualizarAjustes(estado.ajustes.copy(tema = id, temaDelSistema = false))
    }

    fun seguirTemaDelSistema(seguir: Boolean) {
        actualizarAjustes(estado.ajustes.copy(temaDelSistema = seguir))
    }

    fun elegirIdioma(codigo: String?) {
        actualizarAjustes(estado.ajustes.copy(idioma = codigo))
    }

    /**
     * Activa o desactiva un juego. Nunca deja la lista vacía: si se intenta
     * apagar el último, se ignora y se avisa, en lugar de generar un tablero
     * sin pruebas.
     */
    fun alternarJuego(juego: Juego, avisoMinimo: String): Boolean {
        val ajustes = estado.ajustes
        val estaba = juego in ajustes.juegosDesactivados
        val nuevos = if (estaba) {
            ajustes.juegosDesactivados - juego
        } else {
            ajustes.juegosDesactivados + juego
        }
        val quedan = contenido.juegosJugables.filterNot { it in nuevos }
        if (quedan.isEmpty()) {
            mensaje = avisoMinimo
            return false
        }
        actualizarAjustes(ajustes.copy(juegosDesactivados = nuevos))
        return true
    }

    fun marcarTourVisto() {
        actualizarAjustes(estado.ajustes.copy(tourVisto = true))
    }

    // ------------------------------------------------------------- modo

    fun elegirModo(modo: Modo) {
        estado = estado.copy(modo = modo, pantalla = Pantalla.PARTICIPANTES)
        viewModelScope.launch { prefs.guardarModo(modo) }
        ajustarParticipantesAlModo(modo)
    }

    /**
     * Deja la lista de participantes coherente con el modo: recorta si hay
     * demasiados, rellena si faltan y quita los miembros en modo individual,
     * donde cada participante es una persona y no un equipo.
     */
    private fun ajustarParticipantesAlModo(modo: Modo) {
        var lista = estado.participantes
        if (modo == Modo.SOLITARIO) {
            lista = listOf(lista.firstOrNull()?.copy(miembros = emptyList()) ?: nuevoParticipante(1))
        } else {
            if (modo == Modo.INDIVIDUAL) lista = lista.map { it.copy(miembros = emptyList()) }
            lista = lista.take(modo.maximoParticipantes)
            while (lista.size < modo.minimoParticipantes) {
                lista = lista + nuevoParticipante(lista.size + 1)
            }
        }
        aplicarParticipantes(lista)
    }

    /**
     * Crea un participante sin nombre. El hueco es intencionado: la pantalla
     * pinta «Equipo 3» en el idioma activo, y así cambiar de idioma cambia
     * también los nombres que nadie ha puesto a mano.
     */
    private fun nuevoParticipante(numero: Int): Participante {
        val usados = estado.participantes.map { it.indiceColor }.toSet()
        val color = coloresDisponibles(usados) ?: (numero - 1) % MAXIMO_PARTICIPANTES
        val id = (estado.participantes.maxOfOrNull { it.id } ?: 0) + 1
        return Participante(id = id, nombre = "", indiceColor = color)
    }

    // ----------------------------------------------------- participantes

    /**
     * Añade un participante que llega desde otro móvil del salón. Se queda
     * anotado con el id del dispositivo, y eso es lo que permite luego mandarle
     * a él y solo a él el contenido privado de su prueba.
     */
    fun anadirParticipanteDeSalon(nombre: String, dispositivo: String) {
        if (estado.participantes.any { it.dispositivo == dispositivo }) return
        if (estado.participantes.size >= MAXIMO_PARTICIPANTES) return
        val nuevo = nuevoParticipante(estado.participantes.size + 1)
            .copy(nombre = nombre.trim(), dispositivo = dispositivo)
        aplicarParticipantes(estado.participantes + nuevo)
    }

    fun anadirParticipante() {
        if (estado.participantes.size >= estado.modo.maximoParticipantes) return
        aplicarParticipantes(
            estado.participantes + nuevoParticipante(estado.participantes.size + 1)
        )
    }

    fun eliminarParticipante(id: Int) {
        if (estado.participantes.size <= estado.modo.minimoParticipantes) return
        aplicarParticipantes(estado.participantes.filterNot { it.id == id })
    }

    fun renombrarParticipante(id: Int, nombre: String) {
        aplicarParticipantes(
            estado.participantes.map { if (it.id == id) it.copy(nombre = nombre) else it }
        )
    }

    fun anadirMiembro(idParticipante: Int, nombre: String) {
        val limpio = nombre.trim()
        if (limpio.isEmpty()) return
        aplicarParticipantes(
            estado.participantes.map {
                if (it.id == idParticipante && it.miembros.size < MAXIMO_MIEMBROS) {
                    it.copy(miembros = it.miembros + limpio)
                } else {
                    it
                }
            }
        )
    }

    fun eliminarMiembro(idParticipante: Int, indice: Int) {
        aplicarParticipantes(
            estado.participantes.map { p ->
                if (p.id == idParticipante) {
                    p.copy(miembros = p.miembros.filterIndexed { i, _ -> i != indice })
                } else {
                    p
                }
            }
        )
    }

    private fun aplicarParticipantes(participantes: List<Participante>) {
        estado = estado.copy(participantes = participantes)
        viewModelScope.launch { prefs.guardarParticipantes(participantes) }
    }

    // ------------------------------------------------------------ partida

    fun empezarPartida() {
        estado = if (estado.modo == Modo.SOLITARIO) {
            val jugador = estado.participantes.firstOrNull() ?: Participante(1, "", 0)
            motor.empezarSolitario(estado, jugador)
        } else {
            motor.empezarCarrera(estado, estado.participantes)
        }
    }

    fun lanzarDado() {
        estado = motor.lanzarDado(estado)
    }

    fun continuarTrasDado() {
        estado = motor.continuarTrasDado(estado)
    }

    fun elegirJuego(juego: Juego) {
        estado = motor.elegirJuego(estado, juego)
    }

    fun empezarPrueba() {
        estado = motor.empezarPrueba(estado)
    }

    fun resolverPrueba(superada: Boolean, puntos: Int = -1) {
        estado = motor.resolverPrueba(estado, superada, puntos)
    }

    fun resolverRondaDeTodos(aciertos: List<Boolean>) {
        estado = motor.resolverRondaDeTodos(estado, aciertos)
    }

    fun siguienteTurno() {
        val anterior = estado.ajustes.mejorMarcaSolitario
        estado = motor.siguienteTurno(estado)
        // El motor puede haber batido la marca del solitario; hay que persistirla.
        if (estado.ajustes.mejorMarcaSolitario != anterior) {
            viewModelScope.launch { prefs.guardarAjustes(estado.ajustes) }
        }
    }

    fun abandonarPartida() {
        estado = estado.copy(pantalla = Pantalla.INICIO, partidaEnCurso = false)
    }

    val juegosDeLaPartida: List<Juego> get() = motor.juegosDeLaPartida(estado)

    // ----------------------------------------------------------- donación

    private fun anotarUsoReal() {
        val cafe = estado.ajustes.cafe
        actualizarAjustes(estado.ajustes.copy(cafe = cafe.copy(usosReales = cafe.usosReales + 1)))
    }

    /**
     * Reglas del punto 4.4.3: una sola vez tras el primer uso real y, si se
     * eligió «ahora no», una segunda y última vez pasados treinta días y diez
     * usos más. Después, silencio permanente.
     */
    fun tocaOfrecerCafe(): Boolean {
        val cafe = estado.ajustes.cafe
        if (cafe.noVolverAMostrar || cafe.yaPasoPorAhi) return false
        return when (cafe.vecesMostrado) {
            0 -> cafe.usosReales >= 1
            1 -> {
                val diasPasados = LocalDate.now().toEpochDay() - cafe.diaUltimaMuestra
                cafe.usosReales >= USOS_PARA_SEGUNDA && diasPasados >= DIAS_PARA_SEGUNDA
            }
            else -> false
        }
    }

    fun abrirHojaCafe(automatica: Boolean) {
        hojaCafeVisible = true
        if (automatica) {
            val cafe = estado.ajustes.cafe
            actualizarAjustes(
                estado.ajustes.copy(
                    cafe = cafe.copy(
                        vecesMostrado = cafe.vecesMostrado + 1,
                        diaUltimaMuestra = LocalDate.now().toEpochDay()
                    )
                )
            )
        }
    }

    fun cerrarHojaCafe() {
        hojaCafeVisible = false
    }

    fun noVolverAOfrecerCafe() {
        val cafe = estado.ajustes.cafe
        actualizarAjustes(estado.ajustes.copy(cafe = cafe.copy(noVolverAMostrar = true)))
        hojaCafeVisible = false
    }

    /**
     * Se llama al volver del navegador. No afirma que se haya donado nada
     * —no hay forma de saberlo y mentir ahí sería inaceptable—, solo deja de
     * insistir.
     */
    fun vueltaDelNavegadorDeDonacion() {
        val cafe = estado.ajustes.cafe
        actualizarAjustes(estado.ajustes.copy(cafe = cafe.copy(yaPasoPorAhi = true)))
    }

    // ------------------------------------------------- copia de seguridad

    /** Sustituye ajustes y participantes de golpe. La usa la importación. */
    fun reemplazarDatos(ajustes: Ajustes, participantes: List<Participante>) {
        estado = estado.copy(
            ajustes = ajustes,
            participantes = participantes.ifEmpty { estado.participantes }
        )
        recargarContenido()
        viewModelScope.launch {
            prefs.guardarAjustes(ajustes)
            prefs.guardarParticipantes(estado.participantes)
        }
    }

    companion object {
        const val MAXIMO_MIEMBROS = 10
        private const val USOS_PARA_SEGUNDA = 11
        private const val DIAS_PARA_SEGUNDA = 30L
    }
}
