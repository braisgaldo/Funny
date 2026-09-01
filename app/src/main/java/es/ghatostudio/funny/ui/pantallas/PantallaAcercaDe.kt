package es.ghatostudio.funny.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.BuildConfig
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sistema
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.Cabecera
import es.ghatostudio.funny.ui.comun.FilaAjuste
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Acento
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue

/**
 * «Acerca de»: todo lo que hace falta para identificar exactamente qué APK
 * tiene alguien delante, más licencias, privacidad y contacto.
 *
 * Los datos de compilación (versión, código, fecha y hash del commit) salen de
 * `BuildConfig` y se generan en la build a partir de git, así que no hay que
 * acordarse de actualizarlos a mano.
 */
@Composable
fun PantallaAcercaDe(vm: JuegoViewModel) {
    val t = textos()
    val contexto = LocalContext.current

    FondoFunny {
        Column(Modifier.fillMaxSize()) {
            Cabecera(
                titulo = t[Clave.ACERCA_TITULO],
                onVolver = { vm.ir(Pantalla.AJUSTES) }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🎉", style = MaterialTheme.typography.displayMedium)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        t[Clave.ACERCA_AUTOR],
                        style = MaterialTheme.typography.titleMedium,
                        color = TextoFuerte
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        t[Clave.ACERCA_SIN_ANUNCIOS],
                        style = MaterialTheme.typography.bodyMedium,
                        color = Acento,
                        textAlign = TextAlign.Center
                    )
                }

                Tarjeta {
                    Column(Modifier.padding(18.dp)) {
                        Dato(t[Clave.ACERCA_VERSION], BuildConfig.VERSION_NAME)
                        Dato(t[Clave.ACERCA_COMPILACION], BuildConfig.VERSION_CODE.toString())
                        Dato(t[Clave.ACERCA_FECHA], BuildConfig.FECHA_COMPILACION)
                        Dato(t[Clave.ACERCA_COMMIT], BuildConfig.HASH_COMMIT)
                        Dato(t[Clave.ACERCA_LICENCIA], LICENCIA)
                    }
                }

                Tarjeta {
                    Column(Modifier.padding(horizontal = 18.dp, vertical = 8.dp)) {
                        FilaAjuste(
                            titulo = t[Clave.ACERCA_PRIVACIDAD],
                            detalle = ENLACE_PRIVACIDAD,
                            onClick = {
                                Sistema.abrirEnNavegador(contexto, ENLACE_PRIVACIDAD)
                            },
                            derecha = { Text("↗", style = MaterialTheme.typography.titleLarge) }
                        )
                        FilaAjuste(
                            titulo = t[Clave.ACERCA_CODIGO],
                            detalle = BuildConfig.REPOSITORIO,
                            onClick = {
                                Sistema.abrirEnNavegador(contexto, BuildConfig.REPOSITORIO)
                            },
                            derecha = { Text("↗", style = MaterialTheme.typography.titleLarge) }
                        )
                        FilaAjuste(
                            titulo = t[Clave.ACERCA_CONTACTO],
                            detalle = BuildConfig.CORREO_CONTACTO,
                            onClick = {
                                Sistema.escribirCorreo(
                                    contexto,
                                    BuildConfig.CORREO_CONTACTO,
                                    "Funny ${BuildConfig.VERSION_NAME}" // literal-ok
                                )
                            },
                            derecha = { Text("✉", style = MaterialTheme.typography.titleLarge) }
                        )
                    }
                }

                Tarjeta {
                    Column(Modifier.padding(18.dp)) {
                        Text(
                            t[Clave.ACERCA_LICENCIAS_TERCEROS],
                            style = MaterialTheme.typography.titleMedium,
                            color = TextoFuerte
                        )
                        Spacer(Modifier.height(10.dp))
                        // Se mantiene a mano y a propósito: son cinco librerías
                        // y una lista generada automáticamente sería más frágil
                        // que ponerlas aquí. Ver docs/ARCHITECTURE.md.
                        LICENCIAS_TERCEROS.forEach { (nombre, licencia) ->
                            Row(Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
                                Text(
                                    nombre,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextoFuerte,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    licencia,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextoTenue
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun Dato(etiqueta: String, valor: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 5.dp)) {
        Text(
            etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            color = TextoTenue,
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(12.dp))
        Text(valor, style = MaterialTheme.typography.bodyMedium, color = TextoFuerte)
    }
}

private const val LICENCIA = "GPL-3.0-or-later" // literal-ok: identificador SPDX
private const val ENLACE_PRIVACIDAD = "https://braisgaldo.github.io/Funny/privacidad.html"

/** Nombre de la librería y su licencia. Ninguna es de pagos: ver ADR-0004. */
private val LICENCIAS_TERCEROS = listOf(
    "Jetpack Compose · AndroidX" to "Apache-2.0",
    "Kotlin · kotlinx.coroutines" to "Apache-2.0",
    "Google Play Services (Nearby)" to "Android SDK Terms",
    "qrcode-kotlin" to "MIT",
    "AndroidX Browser (Custom Tabs)" to "Apache-2.0"
)
