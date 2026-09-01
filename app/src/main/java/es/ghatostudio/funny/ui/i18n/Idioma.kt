package es.ghatostudio.funny.ui.i18n

/**
 * Cómo se representa un idioma en el selector.
 *
 * Los idiomas con una bandera nacional evidente la llevan. Los demás llevan su
 * código en una insignia, y no por pereza:
 *
 * - El **inglés** y el **árabe** no son de ningún país en concreto, y elegir
 *   uno sería tan arbitrario como ofensivo. Lo pide así el punto 4.3 de la
 *   plantilla.
 * - El **gallego**, el **catalán** y el **euskera** sí tienen bandera, pero su
 *   emoji es una secuencia de etiquetas de subdivisión que casi ningún teclado
 *   ni fuente de Android dibuja: saldría un rectángulo vacío o, peor, las
 *   letras del código en crudo. Una insignia dibujada por nosotros se ve
 *   siempre igual de bien en los seis temas.
 */
sealed interface Insignia {
    data class Bandera(
        val emoji: String,
    ) : Insignia

    data class Codigo(
        val texto: String,
    ) : Insignia
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
    INGLES("en", "English", Insignia.Codigo("EN")),
    CASTELLANO("es", "Español", Insignia.Bandera("🇪🇸")),
    FRANCES("fr", "Français", Insignia.Bandera("🇫🇷")),
    ALEMAN("de", "Deutsch", Insignia.Bandera("🇩🇪")),
    CHINO("zh", "简体中文", Insignia.Bandera("🇨🇳")),
    JAPONES("ja", "日本語", Insignia.Bandera("🇯🇵")),
    RUSO("ru", "Русский", Insignia.Bandera("🇷🇺")),
    ITALIANO("it", "Italiano", Insignia.Bandera("🇮🇹")),
    GRIEGO("el", "Ελληνικά", Insignia.Bandera("🇬🇷")),
    ARABE("ar", "العربية", Insignia.Codigo("AR"), esRtl = true),
    GALLEGO("gl", "Galego", Insignia.Codigo("GL")),
    CATALAN("ca", "Català", Insignia.Codigo("CA")),
    EUSKERA("eu", "Euskara", Insignia.Codigo("EU")),
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
