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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.BuildConfig
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sistema
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.Cabecera
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

/**
 * Ayuda escrita para quien juega, no para quien programa: qué es Funny, cómo se
 * juega con uno o con varios móviles, los dieciocho juegos, preguntas frecuentes y
 * qué hacer si algo falla.
 */
@Composable
fun PantallaAyuda(vm: JuegoViewModel) {
    val t = textos()
    val p = paleta()
    val contexto = LocalContext.current

    FondoFunny {
        Column(Modifier.fillMaxSize()) {
            Cabecera(
                titulo = t[Clave.AYUDA_TITULO],
                subtitulo = t[Clave.AYUDA_SUBTITULO],
                onVolver = { vm.ir(Pantalla.INICIO) },
            )

            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Bloque(t[Clave.AYUDA_QUE_ES_TITULO]) {
                    Parrafo(t[Clave.AYUDA_QUE_ES_TEXTO])
                }

                Bloque(t[Clave.AYUDA_COMO_SE_JUEGA_TITULO]) {
                    Punto(t[Clave.AYUDA_PARTIDA_1])
                    Punto(t[Clave.AYUDA_PARTIDA_2])
                    Punto(t[Clave.AYUDA_PARTIDA_3])
                    Punto(t[Clave.AYUDA_PARTIDA_4])
                    Punto(t[Clave.AYUDA_PARTIDA_5])
                }

                Bloque(t[Clave.CASILLA_COMODIN]) {
                    Punto("🃏  ${t[Clave.CASILLA_COMODIN_DETALLE]}")
                    Punto("👥  ${t[Clave.CASILLA_TODOS_DETALLE]}")
                    Punto("🏁  ${t[Clave.CASILLA_META_AVISO]}")
                }

                // Los dieciocho juegos, con su color y sus instrucciones reales:
                // exactamente las mismas que se ven antes de cada prueba, para
                // que no haya dos versiones de las reglas.
                Bloque(t[Clave.TOUR_JUEGOS_TITULO]) {
                    vm.contenidoActual.juegosJugables.forEach { juego ->
                        Row(Modifier.fillMaxWidth().padding(vertical = 7.dp)) {
                            Text(juego.emoji, style = MaterialTheme.typography.headlineMedium)
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(
                                    t.nombreDe(juego),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = p.colorDe(juego),
                                )
                                Text(
                                    t.instruccionesDe(juego),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextoTenue,
                                )
                            }
                        }
                    }
                }

                Bloque(t[Clave.AYUDA_UN_MOVIL_TITULO]) {
                    Punto(t[Clave.AYUDA_UN_MOVIL_1])
                    Punto(t[Clave.AYUDA_UN_MOVIL_2])
                    Punto(t[Clave.AYUDA_UN_MOVIL_3])
                }

                Bloque(t[Clave.AYUDA_VARIOS_MOVILES_TITULO]) {
                    Punto(t[Clave.AYUDA_VARIOS_MOVILES_1])
                    Punto(t[Clave.AYUDA_VARIOS_MOVILES_2])
                    Punto(t[Clave.AYUDA_VARIOS_MOVILES_3])
                }

                Bloque(t[Clave.AYUDA_FAQ_TITULO]) {
                    Pregunta(t[Clave.AYUDA_FAQ_1_P], t[Clave.AYUDA_FAQ_1_R])
                    Pregunta(t[Clave.AYUDA_FAQ_2_P], t[Clave.AYUDA_FAQ_2_R])
                    Pregunta(t[Clave.AYUDA_FAQ_3_P], t[Clave.AYUDA_FAQ_3_R])
                    Pregunta(t[Clave.AYUDA_FAQ_4_P], t[Clave.AYUDA_FAQ_4_R])
                    Pregunta(t[Clave.AYUDA_FAQ_5_P], t[Clave.AYUDA_FAQ_5_R])
                }

                Bloque(t[Clave.AYUDA_PROBLEMAS_TITULO]) {
                    Parrafo(t[Clave.AYUDA_PROBLEMAS_TEXTO])
                }

                BotonGrande(
                    texto = t[Clave.AYUDA_ESCRIBENOS],
                    onClick = {
                        Sistema.escribirCorreo(
                            contexto,
                            BuildConfig.CORREO_CONTACTO,
                            "Funny ${BuildConfig.VERSION_NAME}", // literal-ok: asunto técnico
                        )
                    },
                    color = SuperficieAlta,
                    colorTexto = TextoFuerte,
                )

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
internal fun Bloque(titulo: String, contenido: @Composable () -> Unit) {
    Tarjeta(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(18.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleLarge, color = Primario)
            Spacer(Modifier.height(8.dp))
            contenido()
        }
    }
}

@Composable
internal fun Parrafo(texto: String) {
    Text(texto, style = MaterialTheme.typography.bodyLarge, color = TextoTenue)
}

@Composable
internal fun Punto(texto: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text("•", style = MaterialTheme.typography.bodyLarge, color = Primario)
        Spacer(Modifier.width(10.dp))
        Text(texto, style = MaterialTheme.typography.bodyLarge, color = TextoTenue)
    }
}

@Composable
private fun Pregunta(pregunta: String, respuesta: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 7.dp)) {
        Text(pregunta, style = MaterialTheme.typography.titleMedium, color = TextoFuerte)
        Spacer(Modifier.height(4.dp))
        Text(respuesta, style = MaterialTheme.typography.bodyMedium, color = TextoTenue)
    }
}
