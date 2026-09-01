package es.ghatostudio.funny.ui.i18n

/**
 * Los trece idiomas están completos y revisados.
 *
 * Esta lista existía para que `PruebaCatalogos` solo exigiera completitud a los
 * idiomas ya traducidos mientras se iban escribiendo. Ahora incluye a todos, y
 * eso significa que la build falla si alguien añade una clave y se olvida de un
 * solo idioma. Es intencionado: es la única forma de que no salga el nombre de
 * una clave en la pantalla de alguien.
 */
internal val IDIOMAS_TERMINADOS: Set<Idioma> = Idioma.entries.toSet()
