package com.fieston.modelo

import androidx.compose.ui.graphics.Color
import com.fieston.datos.CartaTabu
import com.fieston.datos.EventoCuando
import com.fieston.datos.PreguntaTrivial
import com.fieston.datos.RetoRapido

/** Las seis pruebas del juego. */
enum class Categoria(
    val etiqueta: String,
    val emoji: String,
    val color: Color,
    val lema: String,
    val instrucciones: String,
    val segundosBase: Int,
    val soloActuante: Boolean
) {
    MIMICA(
        etiqueta = "Mímica",
        emoji = "🎭",
        color = Color(0xFFEF476F),
        lema = "Represéntalo sin hablar",
        instrucciones = "Una persona del equipo representa la palabra con gestos. " +
            "Prohibido hablar, hacer ruidos o señalar objetos de la sala.",
        segundosBase = 60,
        soloActuante = true
    ),
    DIBUJO(
        etiqueta = "Pinturillo",
        emoji = "🎨",
        color = Color(0xFF06D6A0),
        lema = "Dibújalo en la pantalla",
        instrucciones = "Una persona dibuja en la pantalla del móvil y su equipo adivina. " +
            "Nada de letras, números ni gestos.",
        segundosBase = 80,
        soloActuante = true
    ),
    CUANDO(
        etiqueta = "¿Cuándo?",
        emoji = "📅",
        color = Color(0xFFFFD166),
        lema = "¿En qué año ocurrió?",
        instrucciones = "Aparece un acontecimiento y cuatro años posibles. " +
            "El equipo debe decidir en qué año ocurrió.",
        segundosBase = 35,
        soloActuante = false
    ),
    PREGUNTAS(
        etiqueta = "Preguntas",
        emoji = "❓",
        color = Color(0xFF4CC9F0),
        lema = "Cultura general",
        instrucciones = "Una pregunta con cuatro respuestas. El equipo elige una sola.",
        segundosBase = 35,
        soloActuante = false
    ),
    TABU(
        etiqueta = "Tabú",
        emoji = "🤐",
        color = Color(0xFFB388FF),
        lema = "Descríbelo sin decirlo",
        instrucciones = "Una persona describe la palabra sin usar ninguna de las palabras " +
            "prohibidas ni palabras de la misma familia.",
        segundosBase = 60,
        soloActuante = true
    ),
    RETO(
        etiqueta = "Reto rápido",
        emoji = "⚡",
        color = Color(0xFFFF9F1C),
        lema = "Enumera contrarreloj",
        instrucciones = "Todo el equipo va diciendo cosas de la categoría indicada " +
            "hasta llegar al objetivo antes de que se acabe el tiempo.",
        segundosBase = 45,
        soloActuante = false
    );

    companion object {
        val ROTACION = listOf(MIMICA, CUANDO, DIBUJO, PREGUNTAS, TABU, RETO)
    }
}

enum class TipoCasilla { SALIDA, NORMAL, COMODIN, TODOS, META }

data class Casilla(
    val indice: Int,
    val tipo: TipoCasilla,
    val categoria: Categoria?
)

/** Colores disponibles para los equipos, en orden de asignación. */
val COLORES_EQUIPO = listOf(
    Color(0xFFFF3D81),
    Color(0xFF06D6A0),
    Color(0xFFFFD166),
    Color(0xFF4CC9F0),
    Color(0xFFB388FF),
    Color(0xFFFF9F1C)
)

val EMOJIS_EQUIPO = listOf("🦊", "🐢", "🐝", "🐬", "🦄", "🦁")

data class Equipo(
    val id: Int,
    val nombre: String,
    val colorIndex: Int,
    val jugadores: List<String> = emptyList(),
    val posicion: Int = 0,
    val turnoJugador: Int = 0
) {
    val color: Color get() = COLORES_EQUIPO[colorIndex % COLORES_EQUIPO.size]
    val emoji: String get() = EMOJIS_EQUIPO[colorIndex % EMOJIS_EQUIPO.size]

    /** Nombre de quien le toca actuar, o null si el equipo no ha listado jugadores. */
    val jugadorDeTurno: String?
        get() = jugadores.getOrNull(if (jugadores.isEmpty()) 0 else turnoJugador % jugadores.size)
}

/** Contenido concreto que se juega en un turno. */
sealed interface Prueba {
    data class DeMimica(val palabras: List<String>) : Prueba
    data class DeDibujo(val palabras: List<String>) : Prueba
    data class DeCuando(val evento: EventoCuando, val opciones: List<Int>) : Prueba
    data class DePreguntas(val pregunta: PreguntaTrivial) : Prueba
    data class DeTabu(val cartas: List<CartaTabu>) : Prueba
    data class DeReto(val reto: RetoRapido) : Prueba
}

enum class Ritmo(val etiqueta: String, val factor: Float) {
    RAPIDO("Rápido", 0.7f),
    NORMAL("Normal", 1.0f),
    TRANQUILO("Tranquilo", 1.4f)
}

enum class Longitud(val etiqueta: String, val casillas: Int, val detalle: String) {
    CORTA("Corta", 12, "unos 15 min"),
    NORMAL("Normal", 20, "unos 30 min"),
    LARGA("Larga", 28, "unos 45 min")
}

data class Ajustes(
    val ritmo: Ritmo = Ritmo.NORMAL,
    val longitud: Longitud = Longitud.NORMAL,
    val sonido: Boolean = true,
    val vibracion: Boolean = true
)

enum class Pantalla {
    INICIO, EQUIPOS, AJUSTES, COMO_JUGAR,
    TABLERO, COMODIN, ENTREGA, PRUEBA, RONDA_TODOS, RESULTADO, VICTORIA
}

data class EstadoJuego(
    val pantalla: Pantalla = Pantalla.INICIO,
    val equipos: List<Equipo> = emptyList(),
    val tablero: List<Casilla> = emptyList(),
    val turno: Int = 0,
    val dado: Int? = null,
    val origen: Int = 0,
    val destino: Int = 0,
    val categoria: Categoria? = null,
    val prueba: Prueba? = null,
    val esPruebaFinal: Boolean = false,
    val superada: Boolean = false,
    val avanceExtra: List<Int> = emptyList(),
    val ganador: Equipo? = null,
    val ajustes: Ajustes = Ajustes(),
    val partidaEnCurso: Boolean = false
) {
    val equipoActivo: Equipo? get() = equipos.getOrNull(turno)
    val meta: Int get() = (tablero.size - 1).coerceAtLeast(0)

    /** El equipo que va detrás del activo, que es quien elige en las casillas comodín. */
    val equipoQueElige: Equipo?
        get() = if (equipos.size < 2) null else equipos[(turno + 1) % equipos.size]

    fun segundosDe(categoria: Categoria): Int =
        (categoria.segundosBase * ajustes.ritmo.factor).toInt().coerceAtLeast(10)
}
