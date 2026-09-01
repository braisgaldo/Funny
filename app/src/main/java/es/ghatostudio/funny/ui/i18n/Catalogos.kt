package es.ghatostudio.funny.ui.i18n

/**
 * Registro de los trece catálogos.
 *
 * `PruebaCatalogos` comprueba que todos cubren todas las claves del enum
 * `Clave`; si alguien añade una clave y se olvida de un idioma, la build falla
 * en lugar de dejar que salga el nombre de la clave en pantalla.
 */
private val CATALOGOS: Map<Idioma, Catalogo> =
    listOf(
        catalogoIngles,
        catalogoCastellano,
        catalogoFrances,
        catalogoAleman,
        catalogoChino,
        catalogoJapones,
        catalogoRuso,
        catalogoItaliano,
        catalogoGriego,
        catalogoArabe,
        catalogoGallego,
        catalogoCatalan,
        catalogoEuskera,
    ).associateBy { it.idioma }

fun catalogoDe(idioma: Idioma): Catalogo =
    CATALOGOS[idioma] ?: CATALOGOS.getValue(Idioma.POR_DEFECTO)

/** Construye los textos de un idioma, siempre con el inglés como respaldo. */
fun textosDe(idioma: Idioma, reglas: ReglasDePlural = ReglasDePlural.SENCILLAS): Textos =
    Textos(
        catalogo = catalogoDe(idioma),
        respaldo = catalogoDe(Idioma.INGLES),
        reglas = reglas,
    )

/** Todos los catálogos, para las pruebas de completitud. */
internal val todosLosCatalogos: List<Catalogo> get() = CATALOGOS.values.toList()
