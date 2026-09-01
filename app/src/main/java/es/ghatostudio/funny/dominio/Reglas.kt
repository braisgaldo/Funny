package es.ghatostudio.funny.dominio

import kotlin.random.Random

/**
 * Reparte el contenido de las pruebas.
 *
 * Cada juego tiene su propio mazo, de modo que agotar las palabras de mímica no
 * afecta a las preguntas. Guarda estado (qué cartas quedan), así que se crea
 * uno por partida.
 */
class Repartidor(private val contenido: Contenido, private val rnd: Random) {

    private val mazoMimica = Mazo(contenido.mimica, rnd)
    private val mazoDibujo = Mazo(contenido.dibujo, rnd)
    private val mazoEventos = Mazo(contenido.eventos, rnd)
    private val mazoPreguntas = Mazo(contenido.preguntas, rnd)
    private val mazoTabu = Mazo(contenido.tabu, rnd)
    private val mazoRetos = Mazo(contenido.retos, rnd)
    private val mazoEmojis = Mazo(contenido.emojis, rnd)
    private val mazoAfirmaciones = Mazo(contenido.afirmaciones, rnd)
    private val mazoTrabalenguas = Mazo(contenido.trabalenguas, rnd)
    private val mazoOrdenar = Mazo(contenido.ordenar, rnd)
    private val mazoCanciones = Mazo(contenido.canciones, rnd)
    private val mazoDesafios = Mazo(contenido.desafios, rnd)

    /**
     * Prepara la prueba de un juego. Devuelve null si ese juego se ha quedado
     * sin contenido, y entonces quien llama busca otro: es preferible cambiar
     * de prueba a mostrar una tarjeta vacía.
     */
    fun repartir(juego: Juego): Prueba? = when (juego) {
        Juego.MIMICA -> mazoMimica.sacar(14).ifEmpty { null }?.let { Prueba.DeMimica(it) }
        Juego.DIBUJO -> mazoDibujo.sacar(8).ifEmpty { null }?.let { Prueba.DeDibujo(it) }
        Juego.TABU -> mazoTabu.sacar(10).ifEmpty { null }?.let { Prueba.DeTabu(it) }

        Juego.CUANDO -> mazoEventos.sacar()?.let { evento ->
            Prueba.DeCuando(evento, opcionesDeAnio(evento.anio, rnd))
        }

        Juego.PREGUNTAS -> mazoPreguntas.sacar()?.let { Prueba.DePreguntas(it) }
        Juego.RETO -> mazoRetos.sacar()?.let { Prueba.DeReto(it) }

        Juego.EMOJIS -> mazoEmojis.sacar()?.let { carta ->
            // Se mezclan la respuesta y hasta tres señuelos, y se apunta dónde
            // ha caído la buena.
            val opciones = (listOf(carta.respuesta) + carta.senuelos.take(3)).shuffled(rnd)
            Prueba.DeEmojis(carta, opciones, opciones.indexOf(carta.respuesta))
        }

        Juego.VERDADERO_FALSO ->
            mazoAfirmaciones.sacar(AFIRMACIONES_POR_PRUEBA).ifEmpty { null }
                ?.let { Prueba.DeVerdaderoFalso(it) }

        Juego.TRABALENGUAS -> mazoTrabalenguas.sacar()?.let { Prueba.DeTrabalenguas(it) }

        Juego.ORDENA -> mazoOrdenar.sacar()?.let { reto ->
            Prueba.DeOrdena(reto, desordenar(reto.elementos, rnd))
        }

        Juego.CANTA -> mazoCanciones.sacar()?.let { Prueba.DeCanta(it) }
        Juego.DESAFIO -> mazoDesafios.sacar()?.let { Prueba.DeDesafio(it) }
    }

    /**
     * Reparte la prueba de [juego] y, si ese juego no tiene contenido, va
     * probando el resto de [alternativas] antes de rendirse.
     */
    fun repartirConAlternativas(juego: Juego, alternativas: List<Juego>): Prueba? =
        repartir(juego) ?: alternativas.asSequence()
            .filter { it != juego }
            .mapNotNull { repartir(it) }
            .firstOrNull()

    fun cantidadDe(juego: Juego): Int = contenido.cantidadDe(juego)

    companion object {
        const val AFIRMACIONES_POR_PRUEBA = 4
    }
}

/**
 * Desordena una lista asegurando que no salga en el orden correcto. Con cuatro
 * elementos, una de cada veinticuatro veces el `shuffled` devuelve el original,
 * y regalar la respuesta una vez cada veinticuatro partidas se nota.
 */
fun <T> desordenar(elementos: List<T>, rnd: Random): List<T> {
    if (elementos.size < 2) return elementos
    var intentos = 0
    var resultado = elementos.shuffled(rnd)
    while (resultado == elementos && intentos < 20) {
        resultado = elementos.shuffled(rnd)
        intentos++
    }
    return if (resultado == elementos) elementos.reversed() else resultado
}

/**
 * Genera cuatro años plausibles alrededor del correcto. El margen se adapta a
 * la época: no tiene sentido ofrecer 1490/1492/1494 para un hecho medieval ni
 * 1990/2010 para algo de hace cinco años.
 */
fun opcionesDeAnio(anio: Int, rnd: Random): List<Int> {
    val margen = when {
        anio < 1500 -> 70
        anio < 1800 -> 40
        anio < 1900 -> 20
        anio < 1960 -> 10
        anio < 2000 -> 7
        else -> 5
    }
    val opciones = linkedSetOf(anio)
    var intentos = 0
    while (opciones.size < 4 && intentos < 300) {
        intentos++
        val salto = (1..margen).random(rnd) * (if (rnd.nextBoolean()) 1 else -1)
        val candidato = anio + salto
        if (candidato in 1..ANIO_TOPE) opciones += candidato
    }
    var relleno = 1
    while (opciones.size < 4) {
        opciones += anio + margen + relleno
        relleno++
    }
    return opciones.sorted()
}

private const val ANIO_TOPE = 2100

/**
 * Construye el tablero.
 *
 * Cada séptima casilla es de «juegan todos» y cada quinta es comodín; el resto
 * van rotando por los juegos activos, así que activar o desactivar juegos en
 * ajustes cambia de verdad el tablero. Los juegos se recorren en orden barajado
 * para que dos partidas seguidas no lleven la misma secuencia.
 */
fun generarTablero(casillas: Int, juegosActivos: List<Juego>, rnd: Random): List<Casilla> {
    val longitud = casillas.coerceAtLeast(4)
    val juegos = juegosActivos.ifEmpty { Juego.entries.toList() }.shuffled(rnd)
    val tablero = mutableListOf(Casilla(0, TipoCasilla.SALIDA, null))
    var rotacion = 0
    for (i in 1 until longitud) {
        val tipo = when {
            i % 7 == 0 -> TipoCasilla.TODOS
            i % 5 == 0 -> TipoCasilla.COMODIN
            else -> TipoCasilla.NORMAL
        }
        val juego = if (tipo == TipoCasilla.NORMAL) juegos[rotacion++ % juegos.size] else null
        tablero += Casilla(i, tipo, juego)
    }
    tablero += Casilla(longitud, TipoCasilla.META, null)
    return tablero
}

/** Colores de participante disponibles, por si hace falta contarlos sin la UI. */
fun coloresDisponibles(usados: Set<Int>): Int? =
    (0 until MAXIMO_PARTICIPANTES).firstOrNull { it !in usados }
