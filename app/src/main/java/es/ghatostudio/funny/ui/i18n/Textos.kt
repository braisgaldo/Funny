package es.ghatostudio.funny.ui.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.textos.Clave
import java.util.Locale

/**
 * Los seis conceptos de Funny que se cuentan y que, por tanto, necesitan
 * plural. No hay más: el resto de los textos con números llevan la cifra
 * dentro de una frase que no cambia de forma.
 */
enum class ClavePlural { CASILLAS, SEGUNDOS, DISPOSITIVOS, PUNTOS, ACIERTOS, REPETICIONES }

/**
 * Categorías de plural de CLDR. El castellano usa ONE y OTHER; el ruso, ONE,
 * FEW y MANY; el árabe, las seis. Un catálogo solo rellena las que su idioma
 * necesita y [Textos] cae en [OTHER] para el resto.
 */
enum class CategoriaPlural { ZERO, ONE, TWO, FEW, MANY, OTHER }

/**
 * Decide qué categoría de plural toca para una cantidad en un idioma.
 *
 * Es una interfaz porque la implementación buena usa `android.icu.text
 * .PluralRules`, que es de Android. Así el catálogo y [Textos] siguen siendo
 * Kotlin puro y los tests pueden inyectar reglas de mentira.
 */
fun interface ReglasDePlural {
    fun categoria(idioma: Idioma, cantidad: Int): CategoriaPlural

    companion object {
        /**
         * Respaldo sin ICU: singular y plural. No es correcto para el ruso ni
         * para el árabe, pero solo se usa si la implementación de Android no
         * está disponible (tests de JVM), nunca en el móvil.
         */
        val SENCILLAS = ReglasDePlural { _, cantidad ->
            if (cantidad == 1) CategoriaPlural.ONE else CategoriaPlural.OTHER
        }
    }
}

/** Un catálogo de textos completo para un idioma. */
class Catalogo(
    val idioma: Idioma,
    val textos: Map<Clave, String>,
    val plurales: Map<ClavePlural, Map<CategoriaPlural, String>> = emptyMap()
)

/**
 * Los textos del idioma activo.
 *
 * Si a un catálogo le falta una clave se cae al inglés y, si tampoco está, al
 * nombre de la clave: preferimos que se vea `AJUSTES_TITULO` en la pantalla —
 * feo, evidente y fácil de encontrar — a que la app reviente en mitad de una
 * partida. `PruebaCatalogos` se encarga de que eso no llegue a pasar.
 */
class Textos(
    private val catalogo: Catalogo,
    private val respaldo: Catalogo,
    private val reglas: ReglasDePlural = ReglasDePlural.SENCILLAS
) {
    val idioma: Idioma get() = catalogo.idioma

    val locale: Locale = Locale.forLanguageTag(catalogo.idioma.codigo)

    val esRtl: Boolean get() = catalogo.idioma.esRtl

    operator fun get(clave: Clave): String =
        catalogo.textos[clave] ?: respaldo.textos[clave] ?: clave.name

    /**
     * Texto con parámetros. Se formatea con el [locale] del idioma activo para
     * que los números salgan como toca: «1.234» en castellano y «١٢٣٤» en
     * árabe con el formateador del sistema.
     */
    fun con(clave: Clave, vararg argumentos: Any): String =
        runCatching { String.format(locale, get(clave), *argumentos) }.getOrDefault(get(clave))

    /** «1 casilla», «20 casillas», «21 клетка»… con la forma correcta del idioma. */
    fun plural(clave: ClavePlural, cantidad: Int): String {
        val formas = catalogo.plurales[clave] ?: respaldo.plurales[clave] ?: return cantidad.toString()
        val categoria = reglas.categoria(catalogo.idioma, cantidad)
        val plantilla = formas[categoria]
            ?: formas[CategoriaPlural.OTHER]
            ?: formas.values.firstOrNull()
            ?: return cantidad.toString()
        return runCatching { String.format(locale, plantilla, cantidad) }
            .getOrDefault("$cantidad")
    }

    // ------------------------------------------------------------ los juegos

    fun nombreDe(juego: Juego): String = get(juego.claveNombre)

    fun lemaDe(juego: Juego): String = get(juego.claveLema)

    fun instruccionesDe(juego: Juego): String = get(juego.claveInstrucciones)
}

/** Los textos del idioma activo. Toda pantalla empieza con `val t = textos()`. */
val LocalTextos = staticCompositionLocalOf<Textos> {
    error("No hay Textos en la composición: falta envolver la pantalla en AppFunny")
}

@Composable
@ReadOnlyComposable
fun textos(): Textos = LocalTextos.current
