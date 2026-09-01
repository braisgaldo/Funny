package es.ghatostudio.funny.plataforma

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import es.ghatostudio.funny.ui.i18n.CategoriaPlural
import es.ghatostudio.funny.ui.i18n.Idioma
import es.ghatostudio.funny.ui.i18n.ReglasDePlural

/**
 * Reglas de plural de verdad, las de CLDR, servidas por la ICU que trae Android
 * desde la API 24. Es lo que hace que el ruso diga «1 клетка / 2 клетки / 5
 * клеток» y el árabe use sus seis formas sin que nosotros escribamos ni una
 * línea de gramática.
 */
object ReglasDePluralAndroid : ReglasDePlural {
    private val cache = mutableMapOf<String, android.icu.text.PluralRules>()

    override fun categoria(idioma: Idioma, cantidad: Int): CategoriaPlural {
        val reglas =
            runCatching {
                cache.getOrPut(idioma.codigo) {
                    android.icu.text.PluralRules.forLocale(
                        java.util.Locale.forLanguageTag(idioma.codigo),
                    )
                }
            }.getOrNull() ?: return ReglasDePlural.SENCILLAS.categoria(idioma, cantidad)

        return when (runCatching { reglas.select(cantidad.toDouble()) }.getOrNull()) {
            "zero" -> CategoriaPlural.ZERO
            "one" -> CategoriaPlural.ONE
            "two" -> CategoriaPlural.TWO
            "few" -> CategoriaPlural.FEW
            "many" -> CategoriaPlural.MANY
            else -> CategoriaPlural.OTHER
        }
    }
}

/**
 * Los pocos gestos que Funny le pide al sistema: compartir, abrir un enlace y
 * copiar al portapapeles.
 *
 * Está reunido aquí y no repartido por las pantallas para que se vea de un
 * vistazo todo lo que la app hace hacia fuera, que no es mucho.
 */
object Sistema {
    /**
     * Abre un enlace **en el navegador del sistema**, con Custom Tabs si está
     * disponible.
     *
     * Nunca en un WebView embebido: el único enlace externo de Funny es el de la
     * donación, y un formulario de pago dentro de la app es exactamente lo que
     * las políticas de las tiendas miran con lupa. Ver ADR-0004.
     */
    fun abrirEnNavegador(context: Context, url: String): Boolean =
        runCatching {
            val uri = url.toUri()
            val intento =
                CustomTabsIntent
                    .Builder()
                    .setShowTitle(true)
                    .build()
            intento.launchUrl(context, uri)
            true
        }.recoverCatching {
            // Sin navegador con soporte de Custom Tabs, se prueba con el genérico.
            context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
            true
        }.getOrDefault(false)

    fun compartirTexto(context: Context, texto: String, titulo: String): Boolean =
        runCatching {
            val intento =
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, texto)
                }
            context.startActivity(Intent.createChooser(intento, titulo))
            true
        }.getOrDefault(false)

    fun compartirFichero(context: Context, uri: Uri, titulo: String, mime: String): Boolean =
        runCatching {
            val intento =
                Intent(Intent.ACTION_SEND).apply {
                    type = mime
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
            context.startActivity(Intent.createChooser(intento, titulo))
            true
        }.getOrDefault(false)

    fun copiarAlPortapapeles(context: Context, etiqueta: String, texto: String): Boolean =
        runCatching {
            val gestor = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            gestor.setPrimaryClip(ClipData.newPlainText(etiqueta, texto))
            true
        }.getOrDefault(false)

    fun escribirCorreo(context: Context, direccion: String, asunto: String): Boolean =
        runCatching {
            val intento =
                Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:$direccion".toUri()
                    putExtra(Intent.EXTRA_SUBJECT, asunto)
                }
            context.startActivity(intento)
            true
        }.getOrDefault(false)

    /**
     * Si el sistema tiene activada la reducción de animaciones. Se respeta en
     * todas las entradas animadas, incluida la de la hoja de la donación, tal y
     * como pide el punto 4.4.2.
     */
    fun animacionesReducidas(context: Context): Boolean =
        runCatching {
            val escala =
                Settings.Global.getFloat(
                    context.contentResolver,
                    Settings.Global.ANIMATOR_DURATION_SCALE,
                    1f,
                )
            escala == 0f
        }.getOrDefault(false)
}
