package es.ghatostudio.funny.ui.i18n

/** Las banderas que hay que dibujar porque no tienen emoji. Ver `BanderaPintada`. */
enum class BanderaDibujada {
    GALICIA,
    CATALUNA,
    EUSKADI,
}

/**
 * Cómo se representa un idioma en el selector.
 *
 * **Los trece llevan bandera menos dos**, y esos dos no es por pereza: lo pide
 * el punto 4.3 de la plantilla.
 *
 * - Nueve tienen una bandera nacional con emoji, y la llevan tal cual.
 * - El **gallego**, el **catalán** y el **euskera** tienen bandera, pero su
 *   emoji es una secuencia de etiquetas de subdivisión que **no está en el
 *   conjunto RGI de Unicode** —de las subdivisiones solo están Inglaterra,
 *   Escocia y Gales— y ninguna fuente de Android la dibuja: saldría una bandera
 *   negra o un rectángulo vacío. Así que se dibujan, con tres formas
 *   geométricas y sin una sola imagen en el APK.
 * - El **inglés** y el **árabe** llevan un icono neutro de idioma, un globo.
 *   Ninguno de los dos es de un país concreto y elegir una bandera nacional
 *   —¿la del Reino Unido o la de Estados Unidos? ¿y para el árabe, cuál de
 *   veintidós?— sería arbitrario. Los dos llevan el mismo icono; lo que los
 *   distingue es su nombre al lado, escrito en su propio idioma.
 */
sealed interface Insignia {
    /** Bandera nacional con emoji. */
    data class Bandera(
        val emoji: String,
    ) : Insignia

    /** Bandera sin emoji fiable: se pinta. */
    data class Pintada(
        val cual: BanderaDibujada,
    ) : Insignia

    /** Icono neutro de idioma, para los que no son de ningún país. */
    data object Neutra : Insignia
}

/**
 * Los trece idiomas de Funny.
 *
 * [endonimo] es el nombre del idioma escrito en ese idioma, que es lo que hay
 * que enseñar en un selector: quien busca su lengua no la reconoce traducida.
 */
enum class Idioma(
    /** Etiqueta BCP-47, la que entiende `Locale.forLanguageTag`. */
    val codigo: String,
    val endonimo: String,
    val insignia: Insignia,
    val esRtl: Boolean = false,
) {
    INGLES("en", "English", Insignia.Neutra),
    CASTELLANO("es", "Español", Insignia.Bandera("🇪🇸")),
    FRANCES("fr", "Français", Insignia.Bandera("🇫🇷")),
    ALEMAN("de", "Deutsch", Insignia.Bandera("🇩🇪")),
    CHINO("zh", "简体中文", Insignia.Bandera("🇨🇳")),
    JAPONES("ja", "日本語", Insignia.Bandera("🇯🇵")),
    RUSO("ru", "Русский", Insignia.Bandera("🇷🇺")),
    ITALIANO("it", "Italiano", Insignia.Bandera("🇮🇹")),
    GRIEGO("el", "Ελληνικά", Insignia.Bandera("🇬🇷")),
    ARABE("ar", "العربية", Insignia.Neutra, esRtl = true),
    GALLEGO("gl", "Galego", Insignia.Pintada(BanderaDibujada.GALICIA)),
    CATALAN("ca", "Català", Insignia.Pintada(BanderaDibujada.CATALUNA)),
    EUSKERA("eu", "Euskara", Insignia.Pintada(BanderaDibujada.EUSKADI)),
    ;

    companion object {
        val POR_DEFECTO = INGLES

        /**
         * Idioma para una etiqueta de locale del sistema. Acepta cosas como
         * `es-ES`, `zh-Hans-CN` o `pt_BR`, y cae en [POR_DEFECTO] si no
         * reconoce nada: el inglés es el respaldo, como pide la plantilla.
         */
        fun deEtiqueta(etiqueta: String?): Idioma {
            if (etiqueta.isNullOrBlank()) return POR_DEFECTO
            val base = etiqueta.replace('_', '-').substringBefore('-').lowercase()
            return entries.firstOrNull { it.codigo == base } ?: POR_DEFECTO
        }
    }
}
