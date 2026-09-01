package es.ghatostudio.funny.dominio

import es.ghatostudio.funny.dominio.textos.Clave

// ---------------------------------------------------------------------------
// Modos de juego
// ---------------------------------------------------------------------------

/**
 * Las tres formas de jugar a Funny.
 *
 * [EQUIPOS] es el juego de siempre. [INDIVIDUAL] es lo mismo pero cada persona
 * lleva su propia ficha, sin equipos, que es lo que se pedía para las mesas
 * pequeñas o para quien no quiere emparejarse con nadie. [SOLITARIO] no es una
 * partida por casillas: es una tirada de pruebas contra el reloj con marca
 * personal, porque una carrera de fichas contra uno mismo no tiene ninguna
 * gracia.
 */
enum class Modo(
    val claveNombre: Clave,
    val claveDetalle: Clave,
    val emoji: String,
    val minimoParticipantes: Int,
    val maximoParticipantes: Int
) {
    EQUIPOS(Clave.MODO_EQUIPOS, Clave.MODO_EQUIPOS_DETALLE, "👥", 2, 6),
    INDIVIDUAL(Clave.MODO_INDIVIDUAL, Clave.MODO_INDIVIDUAL_DETALLE, "🙋", 2, 8),
    SOLITARIO(Clave.MODO_SOLITARIO, Clave.MODO_SOLITARIO_DETALLE, "🧍", 1, 1);

    val esCarrera: Boolean get() = this != SOLITARIO
}

// ---------------------------------------------------------------------------
// Participantes
// ---------------------------------------------------------------------------

/** Cuántos colores y emojis distintos puede haber en la mesa. */
const val MAXIMO_PARTICIPANTES = 8

/** Pruebas que tiene una tirada del Reto en solitario. */
const val RONDAS_SOLITARIO = 10

val EMOJIS_PARTICIPANTE = listOf("🦊", "🐢", "🐝", "🐬", "🦄", "🦁", "🐙", "🦉")

/**
 * Un equipo (modo por equipos) o una persona (modo individual y solitario).
 *
 * Unificar los dos casos en un solo tipo es lo que permite que el motor del
 * juego, el tablero y las doce pruebas no sepan en qué modo están: un
 * participante tiene un nombre, un color, una posición y, si es un equipo,
 * una lista de miembros por la que va rotando quien actúa.
 */
data class Participante(
    val id: Int,
    /**
     * Nombre puesto por quien juega. **Puede estar vacío**, y eso no es un
     * error: significa «todavía sin bautizar», y entonces la interfaz pinta el
     * nombre por defecto del idioma activo («Equipo 3», «Team 3»…). Guardar un
     * hueco en lugar de un nombre traducido es lo que permite cambiar de idioma
     * y que los equipos sin nombre cambien con él.
     */
    val nombre: String,
    val indiceColor: Int,
    val miembros: List<String> = emptyList(),
    val posicion: Int = 0,
    val turnoMiembro: Int = 0,
    val puntos: Int = 0,
    /** Identificador del dispositivo del salón que lleva a este participante. */
    val dispositivo: String? = null
) {
    val emoji: String get() = EMOJIS_PARTICIPANTE[indiceColor % EMOJIS_PARTICIPANTE.size]

    val esEquipo: Boolean get() = miembros.isNotEmpty()

    /**
     * Quién le toca actuar en las pruebas de una sola persona. En un equipo va
     * rotando; si es una persona sola, es ella misma.
     */
    val quienActua: String?
        get() = if (miembros.isEmpty()) null else miembros[turnoMiembro.mod(miembros.size)]

    /** El nombre puesto, o [porDefecto] si nadie lo ha bautizado todavía. */
    fun nombreVisible(porDefecto: String): String = nombre.ifBlank { porDefecto }

    /** Quién actúa: el miembro de turno o, si no hay miembros, el participante. */
    fun nombreDeQuienActua(porDefecto: String): String = quienActua ?: nombreVisible(porDefecto)
}

// ---------------------------------------------------------------------------
// Tablero
// ---------------------------------------------------------------------------

enum class TipoCasilla { SALIDA, NORMAL, COMODIN, TODOS, META }

data class Casilla(val indice: Int, val tipo: TipoCasilla, val juego: Juego?)

// ---------------------------------------------------------------------------
// Ajustes
// ---------------------------------------------------------------------------

enum class Ritmo(val claveNombre: Clave, val factor: Float) {
    RAPIDO(Clave.RITMO_RAPIDO, 0.7f),
    NORMAL(Clave.RITMO_NORMAL, 1.0f),
    TRANQUILO(Clave.RITMO_TRANQUILO, 1.4f)
}

enum class Duracion(val claveNombre: Clave, val claveDetalle: Clave, val casillas: Int) {
    CORTA(Clave.DURACION_CORTA, Clave.DURACION_CORTA_DETALLE, 12),
    NORMAL(Clave.DURACION_NORMAL, Clave.DURACION_NORMAL_DETALLE, 20),
    LARGA(Clave.DURACION_LARGA, Clave.DURACION_LARGA_DETALLE, 28)
}

/**
 * Los seis temas de Funny: tres claros y tres oscuros. La opción «seguir al
 * sistema» no es un tema, es un [Boolean] aparte ([Ajustes.temaDelSistema]),
 * porque hace falta saber a qué tema claro y a qué tema oscuro caer.
 */
enum class TemaId(val claveNombre: Clave, val esOscuro: Boolean) {
    FIESTA(Clave.TEMA_FIESTA, true),
    NEON(Clave.TEMA_NEON, true),
    MEDIANOCHE(Clave.TEMA_MEDIANOCHE, true),
    PAPEL(Clave.TEMA_PAPEL, false),
    MENTA(Clave.TEMA_MENTA, false),
    ATARDECER(Clave.TEMA_ATARDECER, false);

    companion object {
        val OSCUROS: List<TemaId> get() = entries.filter { it.esOscuro }
        val CLAROS: List<TemaId> get() = entries.filter { !it.esOscuro }
        val OSCURO_POR_DEFECTO = FIESTA
        val CLARO_POR_DEFECTO = PAPEL
    }
}

/**
 * Estado de la propuesta de donación.
 *
 * Vive aquí y no en un rincón de la interfaz porque viaja en la exportación de
 * datos: si alguien reinstala la app, no se le vuelve a preguntar. Ver el
 * ADR-0004 y el punto 4.4.3 de la plantilla.
 */
data class EstadoCafe(
    /** Partidas terminadas desde que se instaló, que es lo que cuenta como uso real. */
    val usosReales: Int = 0,
    val vecesMostrado: Int = 0,
    /** Día (epoch day) en el que se mostró por última vez. 0 = nunca. */
    val diaUltimaMuestra: Long = 0,
    val noVolverAMostrar: Boolean = false,
    /** Se marca al volver del navegador, para no volver a insistir. */
    val yaPasoPorAhi: Boolean = false
)

data class Ajustes(
    val tema: TemaId = TemaId.OSCURO_POR_DEFECTO,
    val temaDelSistema: Boolean = true,
    /** Código ISO del idioma elegido, o null para seguir al sistema. */
    val idioma: String? = null,
    val ritmo: Ritmo = Ritmo.NORMAL,
    val duracion: Duracion = Duracion.NORMAL,
    val sonido: Boolean = true,
    val vibracion: Boolean = true,
    val animaciones: Boolean = true,
    /** Juegos que entran en la partida. Vacío significa «todos los jugables». */
    val juegosDesactivados: Set<Juego> = emptySet(),
    val tourVisto: Boolean = false,
    val cafe: EstadoCafe = EstadoCafe(),
    val mejorMarcaSolitario: Int = 0
) {
    fun juegosActivos(contenido: Contenido): List<Juego> {
        val jugables = contenido.juegosJugables
        val activos = jugables.filterNot { it in juegosDesactivados }
        // Nunca dejamos la partida sin juegos: si alguien los desactiva todos,
        // se ignora la preferencia en lugar de romper el tablero.
        return activos.ifEmpty { jugables }
    }
}

// ---------------------------------------------------------------------------
// Estado de la partida
// ---------------------------------------------------------------------------

enum class Pantalla {
    INICIO,
    MODO,
    PARTICIPANTES,
    AJUSTES,
    IDIOMA,
    TEMA,
    AYUDA,
    ACERCA_DE,
    TOUR,
    SALON,
    TABLERO,
    COMODIN,
    ENTREGA,
    PRUEBA,
    RONDA_TODOS,
    RESULTADO,
    VICTORIA,
    SOLITARIO_FIN
}

/**
 * Estado completo de la aplicación. Es un único objeto inmutable a propósito:
 * así el hub del salón puede serializarlo y mandarlo a los demás móviles sin
 * preguntarse qué trozos de estado se ha dejado por el camino.
 */
data class EstadoJuego(
    val pantalla: Pantalla = Pantalla.INICIO,
    val modo: Modo = Modo.EQUIPOS,
    val participantes: List<Participante> = emptyList(),
    val tablero: List<Casilla> = emptyList(),
    val turno: Int = 0,
    val dado: Int? = null,
    val origen: Int = 0,
    val destino: Int = 0,
    val juego: Juego? = null,
    val prueba: Prueba? = null,
    val esPruebaFinal: Boolean = false,
    val superada: Boolean = false,
    /** Índices de los participantes que han acertado en una ronda de «juegan todos». */
    val avanceExtra: List<Int> = emptyList(),
    val ganador: Participante? = null,
    val ajustes: Ajustes = Ajustes(),
    val partidaEnCurso: Boolean = false,
    // --- solitario ---
    val rondaSolitario: Int = 0,
    val rondasSolitario: Int = RONDAS_SOLITARIO,
    val puntosSolitario: Int = 0,
    val esRecordSolitario: Boolean = false
) {
    val participanteActivo: Participante? get() = participantes.getOrNull(turno)

    val meta: Int get() = (tablero.size - 1).coerceAtLeast(0)

    /** Quien elige la prueba en las casillas comodín: el siguiente en el orden. */
    val quienElige: Participante?
        get() = if (participantes.size < 2) null else participantes[(turno + 1) % participantes.size]

    val casillaDestino: Casilla? get() = tablero.getOrNull(destino)

    val esRondaDeTodos: Boolean
        get() = !esPruebaFinal && casillaDestino?.tipo == TipoCasilla.TODOS

    fun segundosDe(juego: Juego): Int =
        (juego.segundosBase * ajustes.ritmo.factor).toInt().coerceAtLeast(10)

    /** Clasificación de mayor a menor avance, para la pantalla de victoria. */
    val clasificacion: List<Participante>
        get() = participantes.sortedWith(
            compareByDescending<Participante> { it.posicion }.thenByDescending { it.puntos }
        )

}
