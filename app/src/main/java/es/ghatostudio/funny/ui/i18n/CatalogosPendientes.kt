package es.ghatostudio.funny.ui.i18n

// ---------------------------------------------------------------------------
// PENDIENTES DE TRADUCIR — Hito 5
//
// Estos once catálogos están declarados y registrados, pero todavía vacíos: al
// no tener ninguna clave, `Textos` cae al inglés para todo. Eso hace que el
// idioma se pueda elegir y que la app no reviente, pero NO son los idiomas
// terminados.
//
// Se rellenan en el hito 5, cuando el enum `Clave` deje de moverse; traducir
// 359 claves × 11 idiomas antes de eso significaría repasarlas cada vez que una
// pantalla necesita un texto nuevo.
//
// `PruebaCatalogos` conoce esta lista y solo exige completitud a los idiomas ya
// terminados, así que la build está en verde y a la vez queda constancia de lo
// que falta.
// ---------------------------------------------------------------------------

/** Idiomas cuyo catálogo ya está completo y revisado. */
internal val IDIOMAS_TERMINADOS = setOf(Idioma.INGLES, Idioma.CASTELLANO)

private fun pendiente(idioma: Idioma) = Catalogo(idioma = idioma, textos = emptyMap())

internal val catalogoFrances = pendiente(Idioma.FRANCES)
internal val catalogoAleman = pendiente(Idioma.ALEMAN)
internal val catalogoChino = pendiente(Idioma.CHINO)
internal val catalogoJapones = pendiente(Idioma.JAPONES)
internal val catalogoRuso = pendiente(Idioma.RUSO)
internal val catalogoItaliano = pendiente(Idioma.ITALIANO)
internal val catalogoGriego = pendiente(Idioma.GRIEGO)
internal val catalogoArabe = pendiente(Idioma.ARABE)
internal val catalogoGallego = pendiente(Idioma.GALLEGO)
internal val catalogoCatalan = pendiente(Idioma.CATALAN)
internal val catalogoEuskera = pendiente(Idioma.EUSKERA)
