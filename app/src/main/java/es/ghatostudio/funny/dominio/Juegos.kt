package es.ghatostudio.funny.dominio

import es.ghatostudio.funny.dominio.textos.Clave

/**
 * Los doce juegos de Funny.
 *
 * Esta clase es deliberadamente tonta: solo describe *qué* es cada juego y con
 * qué reglas se comporta. No sabe de colores (eso vive en `ui/tema`) ni de
 * idiomas (solo guarda las claves que `ui/i18n` resolverá). Así el enum entero
 * puede viajar a `commonMain` el día que el proyecto se convierta en KMP.
 *
 * Las tres banderas de comportamiento son las que sostienen los tres modos de
 * juego, y conviene entenderlas bien antes de tocar nada:
 *
 * - [soloActuante]: una sola persona ve la pantalla y actúa, y los demás
 *   adivinan. En modo individual sigue funcionando (el jugador de turno actúa
 *   y adivina el resto de la mesa), pero en solitario no tiene sentido.
 * - [veredictoDeLaMesa]: la app no tiene forma de saber si se ha conseguido,
 *   así que pregunta. No es un fallo de diseño: cantar bien o decir un
 *   trabalenguas sin trabarse no lo puede juzgar un móvil.
 * - [valeEnSolitario]: se puede jugar sin nadie delante. Es lo que decide qué
 *   juegos entran en el Reto en solitario.
 */
enum class Juego(
    /** Sufijo del fichero de contenido en assets y de las claves de texto. */
    val clave: String,
    val emoji: String,
    val segundosBase: Int,
    val soloActuante: Boolean,
    val veredictoDeLaMesa: Boolean,
    val valeEnSolitario: Boolean,
    val claveNombre: Clave,
    val claveLema: Clave,
    val claveInstrucciones: Clave
) {
    MIMICA(
        clave = "mimica",
        emoji = "🎭",
        segundosBase = 60,
        soloActuante = true,
        veredictoDeLaMesa = false,
        valeEnSolitario = false,
        claveNombre = Clave.JUEGO_MIMICA_NOMBRE,
        claveLema = Clave.JUEGO_MIMICA_LEMA,
        claveInstrucciones = Clave.JUEGO_MIMICA_INSTRUCCIONES
    ),
    DIBUJO(
        clave = "dibujo",
        emoji = "🎨",
        segundosBase = 80,
        soloActuante = true,
        veredictoDeLaMesa = false,
        valeEnSolitario = false,
        claveNombre = Clave.JUEGO_DIBUJO_NOMBRE,
        claveLema = Clave.JUEGO_DIBUJO_LEMA,
        claveInstrucciones = Clave.JUEGO_DIBUJO_INSTRUCCIONES
    ),
    CUANDO(
        clave = "cuando",
        emoji = "📅",
        segundosBase = 35,
        soloActuante = false,
        veredictoDeLaMesa = false,
        valeEnSolitario = true,
        claveNombre = Clave.JUEGO_CUANDO_NOMBRE,
        claveLema = Clave.JUEGO_CUANDO_LEMA,
        claveInstrucciones = Clave.JUEGO_CUANDO_INSTRUCCIONES
    ),
    PREGUNTAS(
        clave = "preguntas",
        emoji = "❓",
        segundosBase = 35,
        soloActuante = false,
        veredictoDeLaMesa = false,
        valeEnSolitario = true,
        claveNombre = Clave.JUEGO_PREGUNTAS_NOMBRE,
        claveLema = Clave.JUEGO_PREGUNTAS_LEMA,
        claveInstrucciones = Clave.JUEGO_PREGUNTAS_INSTRUCCIONES
    ),
    TABU(
        clave = "tabu",
        emoji = "🤐",
        segundosBase = 60,
        soloActuante = true,
        veredictoDeLaMesa = false,
        valeEnSolitario = false,
        claveNombre = Clave.JUEGO_TABU_NOMBRE,
        claveLema = Clave.JUEGO_TABU_LEMA,
        claveInstrucciones = Clave.JUEGO_TABU_INSTRUCCIONES
    ),
    RETO(
        clave = "retos",
        emoji = "⚡",
        segundosBase = 45,
        soloActuante = false,
        veredictoDeLaMesa = false,
        valeEnSolitario = true,
        claveNombre = Clave.JUEGO_RETO_NOMBRE,
        claveLema = Clave.JUEGO_RETO_LEMA,
        claveInstrucciones = Clave.JUEGO_RETO_INSTRUCCIONES
    ),

    // ---------------------------------------------------------------------
    // Los seis juegos nuevos de Funny
    // ---------------------------------------------------------------------

    EMOJIS(
        clave = "emojis",
        emoji = "🍿",
        segundosBase = 40,
        soloActuante = false,
        veredictoDeLaMesa = false,
        valeEnSolitario = true,
        claveNombre = Clave.JUEGO_EMOJIS_NOMBRE,
        claveLema = Clave.JUEGO_EMOJIS_LEMA,
        claveInstrucciones = Clave.JUEGO_EMOJIS_INSTRUCCIONES
    ),
    VERDADERO_FALSO(
        clave = "verdadero_falso",
        emoji = "🤥",
        segundosBase = 40,
        soloActuante = false,
        veredictoDeLaMesa = false,
        valeEnSolitario = true,
        claveNombre = Clave.JUEGO_VERDADERO_FALSO_NOMBRE,
        claveLema = Clave.JUEGO_VERDADERO_FALSO_LEMA,
        claveInstrucciones = Clave.JUEGO_VERDADERO_FALSO_INSTRUCCIONES
    ),
    TRABALENGUAS(
        clave = "trabalenguas",
        emoji = "👅",
        segundosBase = 40,
        soloActuante = true,
        veredictoDeLaMesa = true,
        valeEnSolitario = true,
        claveNombre = Clave.JUEGO_TRABALENGUAS_NOMBRE,
        claveLema = Clave.JUEGO_TRABALENGUAS_LEMA,
        claveInstrucciones = Clave.JUEGO_TRABALENGUAS_INSTRUCCIONES
    ),
    ORDENA(
        clave = "ordena",
        emoji = "🔢",
        segundosBase = 50,
        soloActuante = false,
        veredictoDeLaMesa = false,
        valeEnSolitario = true,
        claveNombre = Clave.JUEGO_ORDENA_NOMBRE,
        claveLema = Clave.JUEGO_ORDENA_LEMA,
        claveInstrucciones = Clave.JUEGO_ORDENA_INSTRUCCIONES
    ),
    CANTA(
        clave = "canta",
        emoji = "🎤",
        segundosBase = 45,
        soloActuante = true,
        veredictoDeLaMesa = true,
        valeEnSolitario = false,
        claveNombre = Clave.JUEGO_CANTA_NOMBRE,
        claveLema = Clave.JUEGO_CANTA_LEMA,
        claveInstrucciones = Clave.JUEGO_CANTA_INSTRUCCIONES
    ),
    DESAFIO(
        clave = "desafios",
        emoji = "🤸",
        segundosBase = 45,
        soloActuante = true,
        veredictoDeLaMesa = true,
        valeEnSolitario = false,
        claveNombre = Clave.JUEGO_DESAFIO_NOMBRE,
        claveLema = Clave.JUEGO_DESAFIO_LEMA,
        claveInstrucciones = Clave.JUEGO_DESAFIO_INSTRUCCIONES
    );

    companion object {
        /** Juegos que se pueden jugar sin nadie delante. */
        val EN_SOLITARIO: List<Juego> get() = entries.filter { it.valeEnSolitario }

        /**
         * Juegos válidos para una casilla «juegan todos»: hace falta que la app
         * pueda verificar la respuesta de cada equipo por separado y que no
         * dependa de una única persona actuando.
         */
        val PARA_RONDA_DE_TODOS: List<Juego>
            get() = entries.filter { !it.soloActuante && !it.veredictoDeLaMesa }

        fun porClave(clave: String): Juego? = entries.firstOrNull { it.clave == clave }
    }
}
