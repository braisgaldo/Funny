package es.ghatostudio.funny.ui.tema

import androidx.compose.ui.graphics.Color
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.TemaId

/**
 * Todos los colores de Funny, y los únicos.
 *
 * No hay un solo `Color(0xFF…)` fuera de este fichero: si aparece uno en una
 * pantalla es un error, porque ese color no cambiaría al cambiar de tema. Los
 * doce colores de juego y los ocho de participante también salen de aquí.
 *
 * El contraste de cada pareja texto/fondo de las seis paletas está comprobado
 * contra el mínimo AA (4,5:1) por `PruebaContraste`, que falla la build si
 * alguien mete un color flojo.
 */
data class Paleta(
    val id: TemaId,
    val esOscuro: Boolean,
    /** Fondo de la app. */
    val fondo: Color,
    /** Fondo de la zona alta, con el que se hace el degradado de cabecera. */
    val fondoAlto: Color,
    /** Tarjetas. */
    val superficie: Color,
    /** Tarjetas destacadas, campos y botones secundarios. */
    val superficieAlta: Color,
    val primario: Color,
    val sobrePrimario: Color,
    val acento: Color,
    val sobreAcento: Color,
    /** Texto principal. Cumple AA sobre [fondo] y sobre [superficie]. */
    val textoFuerte: Color,
    /** Texto secundario. Cumple AA sobre [fondo] y sobre [superficie]. */
    val textoTenue: Color,
    val exito: Color,
    val sobreExito: Color,
    val fallo: Color,
    val sobreFallo: Color,
    val contorno: Color,
    /** Casillas de salida y de relleno: gris del tema, sin protagonismo. */
    val casillaNeutra: Color,
    /** Casillas de «juegan todos». Comodín usa [acento] y meta usa [primario]. */
    val casillaTodos: Color,
    /** Ocho colores para las fichas de equipo o jugador. */
    val participantes: List<Color>,
    /** Doce colores, uno por juego, en el orden del enum [Juego]. */
    val juegos: List<Color>
) {
    fun colorDe(juego: Juego): Color = juegos[juego.ordinal % juegos.size]

    fun colorDeParticipante(indice: Int): Color = participantes[indice.mod(participantes.size)]

    /**
     * Color de texto legible encima de [fondoDelBoton].
     *
     * Se calcula en lugar de apuntarlo a mano porque los doce colores de juego
     * se usan como fondo de botón en las pruebas, y unos son claros y otros
     * oscuros. Y se elige comparando los dos contrastes, no con un umbral de
     * luminancia: un rosa medio como el de mímica cae del lado «claro» de
     * cualquier umbral razonable y sin embargo se lee mucho mejor con texto
     * oscuro (5,9:1) que con texto blanco (2,9:1).
     */
    fun textoSobre(fondoDelBoton: Color): Color =
        if (contraste(TINTA, fondoDelBoton) >= contraste(TIZA, fondoDelBoton)) TINTA else TIZA
}

/**
 * Luminancia relativa según WCAG 2.1. Se usa para decidir el color de texto
 * sobre un fondo y en las pruebas de contraste.
 */
fun luminancia(color: Color): Double {
    fun canal(v: Float): Double {
        val x = v.toDouble()
        return if (x <= 0.03928) x / 12.92 else Math.pow((x + 0.055) / 1.055, 2.4)
    }
    return 0.2126 * canal(color.red) + 0.7152 * canal(color.green) + 0.0722 * canal(color.blue)
}

/** Razón de contraste WCAG entre dos colores opacos. 4,5 es el mínimo AA. */
fun contraste(uno: Color, otro: Color): Double {
    val a = luminancia(uno)
    val b = luminancia(otro)
    val claro = maxOf(a, b)
    val oscuro = minOf(a, b)
    return (claro + 0.05) / (oscuro + 0.05)
}

// ---------------------------------------------------------------------------
// Colores de los doce juegos
//
// Doce tonos repartidos por la rueda de color para que se distingan de un
// vistazo en el tablero, en dos versiones: brillante para los temas oscuros y
// profunda para los claros, porque un amarillo pastel sobre papel blanco no se
// lee.
// ---------------------------------------------------------------------------

private val JUEGOS_OSCURO = listOf(
    Color(0xFFFF5C8A), // mímica
    Color(0xFF35E0A1), // dibujo
    Color(0xFFFFC94D), // ¿cuándo?
    Color(0xFF58C4FF), // preguntas
    Color(0xFFB98CFF), // tabú
    Color(0xFFFF8A3D), // reto rápido
    Color(0xFFFF6F61), // emojis
    Color(0xFF8CE05C), // verdadero o falso
    Color(0xFFFFE066), // trabalenguas
    Color(0xFF6E8FFF), // ordena
    Color(0xFFFF6FD8), // canta
    Color(0xFF4FE3D8) //  desafío
)

private val JUEGOS_CLARO = listOf(
    Color(0xFFC2185B),
    Color(0xFF00695C),
    Color(0xFF8D6100),
    Color(0xFF0B6FB0),
    Color(0xFF6339C9),
    Color(0xFFAC4A00),
    Color(0xFFC0392B),
    Color(0xFF3D7212),
    Color(0xFF7A6000),
    Color(0xFF2B49C4),
    Color(0xFFA8248F),
    Color(0xFF00695F)
)

private val PARTICIPANTES_OSCURO = listOf(
    Color(0xFFFF5C8A),
    Color(0xFF35E0A1),
    Color(0xFFFFC94D),
    Color(0xFF58C4FF),
    Color(0xFFB98CFF),
    Color(0xFFFF8A3D),
    Color(0xFF4FE3D8),
    Color(0xFF8CE05C)
)

private val PARTICIPANTES_CLARO = listOf(
    Color(0xFFC2185B),
    Color(0xFF00695C),
    Color(0xFF8D6100),
    Color(0xFF0B6FB0),
    Color(0xFF6339C9),
    Color(0xFFAC4A00),
    Color(0xFF00695F),
    Color(0xFF3D7212)
)

private val BLANCO = Color(0xFFFFFFFF)
private val NEGRO_SUAVE = Color(0xFF10131A)

// ---------------------------------------------------------------------------
// Tintas del lienzo de «Pinturillo»
//
// Son la única excepción consciente a «los colores cambian con el tema», y por
// una razón de fondo: el lienzo es papel, no interfaz. Un lienzo que se
// oscureciera con el tema oscuro haría ilegibles los trazos oscuros, y una
// paleta de rotuladores que cambiara de tono según el tema dejaría de ser una
// paleta de rotuladores. Se dibuja siempre sobre papel claro con tintas fijas,
// como en un papel de verdad.
// ---------------------------------------------------------------------------

/** El papel sobre el que se dibuja. Hace también de goma de borrar. */
val LIENZO_DIBUJO = Color(0xFFFFFDF6)

/** Gris del texto de ayuda sobre el lienzo, comprobado contra [LIENZO_DIBUJO]. */
val TEXTO_SOBRE_LIENZO = Color(0xFF7A736A)

/**
 * Los diez rotuladores.
 *
 * Todos llegan al menos a 3:1 contra [LIENZO_DIBUJO], que es el mínimo de
 * contraste para gráficos. El amarillo y el naranja «de rotulador» de toda la
 * vida (#FDD835 y #FB8C00) se quedaban en 1,4:1 y 2,3:1 sobre papel crema:
 * literalmente no se veía lo que dibujabas, así que están oscurecidos hasta un
 * mostaza y un calabaza que sí se leen.
 */
val TINTAS_DIBUJO = listOf(
    Color(0xFF1B1B1F), // negro       16,9:1
    Color(0xFFE53935), // rojo         4,2:1
    Color(0xFFC75F00), // naranja      4,1:1
    Color(0xFFB38200), // mostaza      3,4:1
    Color(0xFF2E7D32), // verde        5,0:1
    Color(0xFF1976D2), // azul         4,5:1
    Color(0xFF00838F), // cian         4,4:1
    Color(0xFF8E24AA), // morado       6,9:1
    Color(0xFFD81B60), // rosa         4,9:1
    Color(0xFF6D4C41) //  marrón       7,5:1
)

/** Las dos tintas entre las que elige [Paleta.textoSobre]. */
private val TINTA = Color(0xFF10131A)
private val TIZA = Color(0xFFFFFFFF)

// ---------------------------------------------------------------------------
// Los seis temas
// ---------------------------------------------------------------------------

/** El tema de siempre: morado de discoteca. Es el que se ve por defecto. */
private val FIESTA = Paleta(
    id = TemaId.FIESTA,
    esOscuro = true,
    fondo = Color(0xFF12071F),
    fondoAlto = Color(0xFF1D0B33),
    superficie = Color(0xFF241041),
    superficieAlta = Color(0xFF3A1F66),
    primario = Color(0xFFFF3D81),
    // Rosa de discoteca con texto muy oscuro: el blanco sobre este rosa se
    // queda en 3,4:1 y no llega a AA. Con esta tinta sube a 5,6:1 y además
    // el rosa mantiene toda su fuerza, que es la gracia del tema.
    sobrePrimario = Color(0xFF2B0013),
    acento = Color(0xFFFFD166),
    sobreAcento = Color(0xFF2B1B00),
    textoFuerte = Color(0xFFF6F1FF),
    textoTenue = Color(0xFFC3AEE8),
    exito = Color(0xFF06D6A0),
    sobreExito = NEGRO_SUAVE,
    fallo = Color(0xFFFF6B6B),
    sobreFallo = NEGRO_SUAVE,
    contorno = Color(0xFF5A3890),
    casillaNeutra = Color(0xFF8B7BB8),
    casillaTodos = Color(0xFF58C4FF),
    participantes = PARTICIPANTES_OSCURO,
    juegos = JUEGOS_OSCURO
)

/** Cian y magenta sobre casi negro, para quien quiera arcade. */
private val NEON = Paleta(
    id = TemaId.NEON,
    esOscuro = true,
    fondo = Color(0xFF060A12),
    fondoAlto = Color(0xFF0B1220),
    superficie = Color(0xFF101A2C),
    superficieAlta = Color(0xFF1D2E4A),
    primario = Color(0xFF00E5FF),
    sobrePrimario = Color(0xFF04121A),
    acento = Color(0xFFFF2E88),
    // Mismo caso que el rosa de FIESTA: en blanco se queda en 3,5:1.
    sobreAcento = NEGRO_SUAVE,
    textoFuerte = Color(0xFFEAF6FF),
    textoTenue = Color(0xFF9FC0DA),
    exito = Color(0xFF3DFFA2),
    sobreExito = NEGRO_SUAVE,
    fallo = Color(0xFFFF6B85),
    sobreFallo = NEGRO_SUAVE,
    contorno = Color(0xFF2B4C70),
    casillaNeutra = Color(0xFF7089A8),
    casillaTodos = Color(0xFF6EE7FF),
    participantes = PARTICIPANTES_OSCURO,
    juegos = JUEGOS_OSCURO
)

/** Oscuro sobrio, azul marino. El menos ruidoso de los tres. */
private val MEDIANOCHE = Paleta(
    id = TemaId.MEDIANOCHE,
    esOscuro = true,
    fondo = Color(0xFF0E1116),
    fondoAlto = Color(0xFF161B22),
    superficie = Color(0xFF1B222B),
    superficieAlta = Color(0xFF2A3542),
    primario = Color(0xFF7C9CFF),
    sobrePrimario = Color(0xFF08101F),
    acento = Color(0xFFFFB454),
    sobreAcento = Color(0xFF241500),
    textoFuerte = Color(0xFFEDF1F7),
    textoTenue = Color(0xFFAAB8CA),
    exito = Color(0xFF4ED9A4),
    sobreExito = NEGRO_SUAVE,
    fallo = Color(0xFFF88484),
    sobreFallo = NEGRO_SUAVE,
    contorno = Color(0xFF3A4757),
    casillaNeutra = Color(0xFF7A8798),
    casillaTodos = Color(0xFF6FB8FF),
    participantes = PARTICIPANTES_OSCURO,
    juegos = JUEGOS_OSCURO
)

/** Papel cálido: el claro de referencia, cómodo con luz de día. */
private val PAPEL = Paleta(
    id = TemaId.PAPEL,
    esOscuro = false,
    fondo = Color(0xFFFBF7F0),
    fondoAlto = Color(0xFFF3EADB),
    superficie = Color(0xFFFFFFFF),
    superficieAlta = Color(0xFFEDE3D2),
    primario = Color(0xFFC2185B),
    sobrePrimario = BLANCO,
    acento = Color(0xFF9A5B00),
    sobreAcento = BLANCO,
    textoFuerte = Color(0xFF1F1B16),
    textoTenue = Color(0xFF5A5147),
    exito = Color(0xFF0B6E4F),
    sobreExito = BLANCO,
    fallo = Color(0xFFB3261E),
    sobreFallo = BLANCO,
    contorno = Color(0xFFD6C8B2),
    casillaNeutra = Color(0xFF6B6155),
    casillaTodos = Color(0xFF0B6FB0),
    participantes = PARTICIPANTES_CLARO,
    juegos = JUEGOS_CLARO
)

/** Verde menta: claro y fresco. */
private val MENTA = Paleta(
    id = TemaId.MENTA,
    esOscuro = false,
    fondo = Color(0xFFF2FBF7),
    fondoAlto = Color(0xFFE1F4EC),
    superficie = Color(0xFFFFFFFF),
    superficieAlta = Color(0xFFD7EDE4),
    primario = Color(0xFF00695C),
    sobrePrimario = BLANCO,
    acento = Color(0xFF9A4A0B),
    sobreAcento = BLANCO,
    textoFuerte = Color(0xFF11201C),
    textoTenue = Color(0xFF41544D),
    exito = Color(0xFF0B6E4F),
    sobreExito = BLANCO,
    fallo = Color(0xFFB3261E),
    sobreFallo = BLANCO,
    contorno = Color(0xFFB9D8CC),
    casillaNeutra = Color(0xFF4E635B),
    casillaTodos = Color(0xFF0B6FB0),
    participantes = PARTICIPANTES_CLARO,
    juegos = JUEGOS_CLARO
)

/** Atardecer: claro cálido con naranja y violeta. */
private val ATARDECER = Paleta(
    id = TemaId.ATARDECER,
    esOscuro = false,
    fondo = Color(0xFFFFF6F0),
    fondoAlto = Color(0xFFFFE6D6),
    superficie = Color(0xFFFFFFFF),
    superficieAlta = Color(0xFFFFDAC4),
    primario = Color(0xFFBF3D00),
    sobrePrimario = BLANCO,
    acento = Color(0xFF6D28D9),
    sobreAcento = BLANCO,
    textoFuerte = Color(0xFF26140B),
    textoTenue = Color(0xFF5C463A),
    exito = Color(0xFF15803D),
    sobreExito = BLANCO,
    fallo = Color(0xFFB91C1C),
    sobreFallo = BLANCO,
    contorno = Color(0xFFEDBB99),
    casillaNeutra = Color(0xFF6E5749),
    casillaTodos = Color(0xFF1D4ED8),
    participantes = PARTICIPANTES_CLARO,
    juegos = JUEGOS_CLARO
)

/** Las seis paletas, indexadas por su identificador. */
val PALETAS: Map<TemaId, Paleta> = mapOf(
    TemaId.FIESTA to FIESTA,
    TemaId.NEON to NEON,
    TemaId.MEDIANOCHE to MEDIANOCHE,
    TemaId.PAPEL to PAPEL,
    TemaId.MENTA to MENTA,
    TemaId.ATARDECER to ATARDECER
)

fun paletaDe(id: TemaId): Paleta = PALETAS.getValue(id)
