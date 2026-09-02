# Reglas de R8 para Funny.
#
# Aquí estuvo escrito, heredado de la app anterior, que «el proyecto no usa
# reflexión, por lo que no hacen falta reglas extra». Era falso, y costó dos
# caídas en el primer arranque en un móvil de verdad. Las dos eran SOLO de
# release: en debug, sin minificar, la app funcionaba.
#
# Si añades algo que se instancie o se identifique por su nombre en tiempo de
# ejecución, tiene que aparecer aquí.


# ---------------------------------------------------------------------------
# ViewModels
#
# `viewModel()` de lifecycle-viewmodel-compose los crea POR REFLEXIÓN, buscando
# el constructor que recibe un Application. R8 no ve ninguna llamada a ese
# constructor, así que lo eliminaba, y la app se caía al arrancar con:
#
#     RuntimeException: Cannot create an instance of class A1.b
#
# Se escribe para cualquier ViewModel y no para los dos que hay ahora mismo, a
# propósito: el tercero que alguien añada tiene que funcionar sin acordarse de
# volver a este fichero.
# ---------------------------------------------------------------------------
-keep class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}


# ---------------------------------------------------------------------------
# Nombres de los enums
#
# Este es el fallo silencioso, y es peor que una caída porque no se nota.
#
# Los ajustes y la copia de seguridad guardan los enums por su nombre
# (`a.tema.name`, `a.modalidad.name`, `it.name` de cada juego desactivado) y los
# vuelven a leer comparando `it.name`. Si R8 renombra las constantes:
#
#   1. El fichero .funny.bak deja de ser texto legible, contra lo que promete la
#      documentación —«se puede abrir con un editor y arreglar a mano»— y lo que
#      dice la política de privacidad.
#   2. Los nombres ofuscados CAMBIAN de una compilación a otra. Es decir, cada
#      actualización de la app le borraría los ajustes a quien la instale, y las
#      copias de seguridad viejas dejarían de poder importarse. Nadie
#      relacionaría eso con R8.
#
# Se conservan los miembros de los enums de la app entera, no solo del dominio:
# `TipoAccion` y `RolSalon` viajan por el protocolo del salón por su nombre.
# ---------------------------------------------------------------------------
-keepclassmembers enum es.ghatostudio.funny.** {
    *;
}


# ---------------------------------------------------------------------------
# Lo que NO hace falta, para que no se añada «por si acaso»
#
# - Compose, AndroidX y Play Services traen sus propias reglas de consumidor.
# - El contenido de `assets/contenido/` se lee con `org.json` a mano, campo por
#   campo, construyendo las data class explícitamente: no hay reflexión y no hay
#   nombres de campo que preservar.
# - El protocolo del salón (`dominio/salon/Codec.kt`) es JSON escrito a mano por
#   la misma razón.
# - No hay Gson, ni Moshi, ni kotlinx-serialization, ni Retrofit, ni Room.
# - No hay `Parcelable` ni `Serializable` propios.
#
# Cada regla de más deja código sin minificar en el APK. Si alguna de estas
# afirmaciones deja de ser cierta, la regla va arriba y este comentario cambia.
# ---------------------------------------------------------------------------


# ---------------------------------------------------------------------------
# Trazas legibles
#
# Sin esto, un informe de fallo llega con líneas «SourceFile:233» en lugar de
# nombres y números reales. Es lo que hizo falta para diagnosticar las dos
# caídas de arriba, y la app no envía informes automáticos (no tiene permiso de
# internet), así que lo único que hay cuando algo falla es lo que alguien copie
# a mano de un logcat.
# ---------------------------------------------------------------------------
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
