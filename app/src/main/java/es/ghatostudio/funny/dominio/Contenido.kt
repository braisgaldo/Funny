package es.ghatostudio.funny.dominio

import kotlin.random.Random

// ---------------------------------------------------------------------------
// Contenido de los dieciocho juegos.
//
// Son tipos puros: quien los rellene (assets, un fichero importado o un test)
// es asunto de la capa de datos.
// ---------------------------------------------------------------------------

data class EventoCuando(
    val texto: String,
    val anio: Int,
    val tema: String = "",
)

data class PreguntaTrivial(
    val texto: String,
    val opciones: List<String>,
    val correcta: Int,
    val tema: String = "",
)

data class CartaTabu(
    val palabra: String,
    val prohibidas: List<String>,
)

data class RetoRapido(
    val texto: String,
    val objetivo: Int,
)

/** Una película, canción o refrán escrito con emojis. */
data class CartaEmojis(
    val emojis: String,
    val respuesta: String,
    val senuelos: List<String>,
    val tipo: String = "",
)

/** Afirmación curiosa de la que hay que decir si es verdad o mentira. */
data class Afirmacion(
    val texto: String,
    val esVerdadera: Boolean,
    val explicacion: String = "",
)

data class Trabalenguas(
    val texto: String,
    val repeticiones: Int,
)

/**
 * Cuatro cosas que hay que poner en orden según un criterio. [elementos] viene
 * ya en el orden correcto; la pantalla los desordena antes de mostrarlos.
 */
data class RetoOrdenar(
    val enunciado: String,
    val elementos: List<String>,
    val criterio: String = "",
)

/**
 * Una canción que hay que ponerse a cantar. No se distribuye ninguna letra:
 * solo el título, quién la canta y una pista de por dónde empezar, para no
 * meter en la app material con derechos de autor.
 */
data class Cancion(
    val titulo: String,
    val artista: String,
    val pista: String,
)

/** Micro-reto de los que animan la mesa: imitar, aguantar, no reírse. */
data class Desafio(
    val texto: String,
    val nivel: Int = 1,
)

/**
 * Todo el contenido del juego, ya cargado. Un `Contenido` vacío es válido: la
 * partida lo detecta y desactiva los juegos que se han quedado sin material,
 * en lugar de reventar.
 */
data class Contenido(
    val mimica: List<String> = emptyList(),
    val dibujo: List<String> = emptyList(),
    val eventos: List<EventoCuando> = emptyList(),
    val preguntas: List<PreguntaTrivial> = emptyList(),
    val tabu: List<CartaTabu> = emptyList(),
    val retos: List<RetoRapido> = emptyList(),
    val emojis: List<CartaEmojis> = emptyList(),
    val afirmaciones: List<Afirmacion> = emptyList(),
    val trabalenguas: List<Trabalenguas> = emptyList(),
    val ordenar: List<RetoOrdenar> = emptyList(),
    val canciones: List<Cancion> = emptyList(),
    val desafios: List<Desafio> = emptyList(),
    // Los seis mazos de los juegos nuevos. Reutilizan modelos de carta que
    // ya existian: no hacia falta inventar ninguno.
    val refranes: List<PreguntaTrivial> = emptyList(),
    val antesDespues: List<PreguntaTrivial> = emptyList(),
    val anagramas: List<PreguntaTrivial> = emptyList(),
    val acentos: List<Desafio> = emptyList(),
    val sonidos: List<Desafio> = emptyList(),
    val encadenados: List<RetoRapido> = emptyList(),
) {
    /** Cuántas cartas hay para un juego. Cero significa «no se puede jugar». */
    fun cantidadDe(juego: Juego): Int =
        when (juego) {
            Juego.MIMICA -> mimica.size
            Juego.DIBUJO -> dibujo.size
            Juego.CUANDO -> eventos.size
            Juego.PREGUNTAS -> preguntas.size
            Juego.TABU -> tabu.size
            Juego.RETO -> retos.size
            Juego.EMOJIS -> emojis.size
            Juego.VERDADERO_FALSO -> afirmaciones.size
            Juego.TRABALENGUAS -> trabalenguas.size
            Juego.ORDENA -> ordenar.size
            Juego.CANTA -> canciones.size
            Juego.DESAFIO -> desafios.size
            Juego.REFRANES -> refranes.size
            Juego.ANTES -> antesDespues.size
            Juego.ANAGRAMAS -> anagramas.size
            Juego.ACENTOS -> acentos.size
            Juego.SONIDOS -> sonidos.size
            Juego.CADENA -> encadenados.size
        }

    /** Los juegos que tienen material suficiente para entrar en una partida. */
    val juegosJugables: List<Juego> get() = Juego.entries.filter { cantidadDe(it) > 0 }

    val total: Int get() = Juego.entries.sumOf { cantidadDe(it) }
}

/**
 * Contenido concreto que se juega en un turno: lo que la pantalla necesita para
 * pintar la prueba, ya elegido y ya barajado.
 */
sealed interface Prueba {
    // --- los seis juegos nuevos ---
    // Cada uno lleva su propio tipo aunque comparta modelo de carta con otro:
    // asi la pantalla sabe de que juego es sin mirar un campo, y el `when` de
    // `PantallaPrueba` sigue siendo exhaustivo.

    /** [opciones] y [correcta] salen de la carta; se guardan aparte por comodidad. */
    data class DeRefranes(
        val pregunta: PreguntaTrivial,
    ) : Prueba {
        override val juego = Juego.REFRANES
    }

    data class DeAntesDespues(
        val pregunta: PreguntaTrivial,
    ) : Prueba {
        override val juego = Juego.ANTES
    }

    data class DeAnagramas(
        val pregunta: PreguntaTrivial,
    ) : Prueba {
        override val juego = Juego.ANAGRAMAS
    }

    data class DeAcentos(
        val carta: Desafio,
    ) : Prueba {
        override val juego = Juego.ACENTOS
    }

    data class DeSonidos(
        val carta: Desafio,
    ) : Prueba {
        override val juego = Juego.SONIDOS
    }

    data class DeEncadenados(
        val reto: RetoRapido,
    ) : Prueba {
        override val juego = Juego.CADENA
    }

    val juego: Juego

    data class DeMimica(
        val palabras: List<String>,
    ) : Prueba {
        override val juego = Juego.MIMICA
    }

    data class DeDibujo(
        val palabras: List<String>,
    ) : Prueba {
        override val juego = Juego.DIBUJO
    }

    data class DeCuando(
        val evento: EventoCuando,
        val opciones: List<Int>,
    ) : Prueba {
        override val juego = Juego.CUANDO
    }

    data class DePreguntas(
        val pregunta: PreguntaTrivial,
    ) : Prueba {
        override val juego = Juego.PREGUNTAS
    }

    data class DeTabu(
        val cartas: List<CartaTabu>,
    ) : Prueba {
        override val juego = Juego.TABU
    }

    data class DeReto(
        val reto: RetoRapido,
    ) : Prueba {
        override val juego = Juego.RETO
    }

    /** [opciones] son la respuesta y los señuelos ya mezclados; [correcta] su índice. */
    data class DeEmojis(
        val carta: CartaEmojis,
        val opciones: List<String>,
        val correcta: Int,
    ) : Prueba {
        override val juego = Juego.EMOJIS
    }

    data class DeVerdaderoFalso(
        val afirmaciones: List<Afirmacion>,
    ) : Prueba {
        override val juego = Juego.VERDADERO_FALSO
    }

    data class DeTrabalenguas(
        val trabalenguas: Trabalenguas,
    ) : Prueba {
        override val juego = Juego.TRABALENGUAS
    }

    /** [desordenados] es lo que se muestra; el orden bueno está en `reto.elementos`. */
    data class DeOrdena(
        val reto: RetoOrdenar,
        val desordenados: List<String>,
    ) : Prueba {
        override val juego = Juego.ORDENA
    }

    data class DeCanta(
        val cancion: Cancion,
    ) : Prueba {
        override val juego = Juego.CANTA
    }

    data class DeDesafio(
        val desafio: Desafio,
    ) : Prueba {
        override val juego = Juego.DESAFIO
    }
}

/**
 * Baraja que reparte sin repetir hasta agotarse, y entonces se vuelve a
 * barajar. Es lo que evita que en una partida salga tres veces «bicicleta».
 */
class Mazo<T>(
    private val todos: List<T>,
    private val rnd: Random,
) {
    private var restantes: MutableList<T> = barajar()

    private fun barajar(): MutableList<T> = todos.shuffled(rnd).toMutableList()

    fun sacar(): T? {
        if (todos.isEmpty()) return null
        if (restantes.isEmpty()) restantes = barajar()
        return restantes.removeAt(restantes.lastIndex)
    }

    /**
     * Saca hasta [cantidad] elementos sin repetir dentro de la misma tirada.
     * Si el mazo tiene menos cartas que las pedidas devuelve las que hay: es
     * mejor una prueba corta que una prueba con la misma palabra dos veces.
     */
    fun sacar(cantidad: Int): List<T> {
        if (todos.isEmpty()) return emptyList()
        val tope = minOf(cantidad, todos.size)
        val sacadas = LinkedHashSet<T>()
        var intentos = 0
        while (sacadas.size < tope && intentos < tope * 8) {
            intentos++
            sacar()?.let { sacadas += it }
        }
        return sacadas.toList()
    }
}
