package es.ghatostudio.funny.ui.pantallas.pruebas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Afirmacion
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.BotonPrueba
import es.ghatostudio.funny.ui.comun.FilaBotones
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Fallo
import es.ghatostudio.funny.ui.tema.Superficie
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

/**
 * «¿Te lo crees?»: cuatro afirmaciones raras seguidas, verdadero o falso.
 *
 * Es una cadena y no una sola pregunta a propósito: cuatro afirmaciones de tres
 * segundos cada una dan mucho más juego en una mesa que una sola pregunta
 * larga, y además la explicación de cada una es la mitad de la gracia.
 */
@Composable
fun PruebaVerdaderoFalso(
    vm: JuegoViewModel,
    afirmaciones: List<Afirmacion>,
    sonidos: Sonidos
) {
    val t = textos()
    val p = paleta()
    val juego = Juego.VERDADERO_FALSO
    val color = p.colorDe(juego)

    var indice by remember { mutableIntStateOf(0) }
    var aciertos by remember { mutableIntStateOf(0) }
    var respuesta by remember { mutableStateOf<Boolean?>(null) }
    var terminada by remember { mutableStateOf(false) }

    fun cerrar() {
        if (terminada) return
        terminada = true
        vm.resolverPrueba(superada = aciertos > 0, puntos = aciertos)
    }

    LaunchedEffect(indice) {
        if (indice >= afirmaciones.size) cerrar()
    }

    val afirmacion = afirmaciones.getOrNull(indice)
    val revelada = respuesta != null

    MarcoPrueba(
        juego = juego,
        segundos = vm.estado.segundosDe(juego),
        enMarcha = !terminada && !revelada,
        sonidos = sonidos,
        marcador = t.con(
            Clave.PRUEBA_ACIERTOS_DE,
            aciertos,
            afirmaciones.size.coerceAtLeast(1)
        ),
        onTiempoAgotado = { cerrar() }
    ) {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Superficie)
                    .border(2.dp, color.copy(alpha = 0.45f), RoundedCornerShape(24.dp))
                    .padding(22.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        afirmaciones.indices.forEach { i ->
                            Box(
                                Modifier
                                    .padding(horizontal = 3.dp)
                                    .size(if (i == indice) 11.dp else 8.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (i < indice) {
                                            color
                                        } else if (i == indice) {
                                            color.copy(alpha = 0.6f)
                                        } else {
                                            SuperficieAlta
                                        }
                                    )
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(
                        afirmacion?.texto.orEmpty(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextoFuerte,
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (revelada && afirmacion != null) {
                Spacer(Modifier.height(16.dp))
                val acerto = respuesta == afirmacion.esVerdadera
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background((if (acerto) Exito else Fallo).copy(alpha = 0.16f))
                        .border(
                            2.dp,
                            if (acerto) Exito else Fallo,
                            RoundedCornerShape(18.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            if (afirmacion.esVerdadera) {
                                t[Clave.PRUEBA_VF_ERA_VERDAD]
                            } else {
                                t[Clave.PRUEBA_VF_ERA_MENTIRA]
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = if (acerto) Exito else Fallo
                        )
                        if (afirmacion.explicacion.isNotBlank()) {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                afirmacion.explicacion,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextoTenue,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        if (!revelada) {
            FilaBotones {
                BotonPrueba(
                    texto = t[Clave.PRUEBA_VF_FALSO],
                    color = Fallo,
                    modifier = Modifier.weight(1f)
                ) {
                    respuesta = false
                    if (afirmacion?.esVerdadera == false) {
                        aciertos++
                        sonidos.acierto()
                    } else {
                        sonidos.fallo()
                    }
                }
                BotonPrueba(
                    texto = t[Clave.PRUEBA_VF_VERDADERO],
                    color = Exito,
                    modifier = Modifier.weight(1f)
                ) {
                    respuesta = true
                    if (afirmacion?.esVerdadera == true) {
                        aciertos++
                        sonidos.acierto()
                    } else {
                        sonidos.fallo()
                    }
                }
            }
        } else {
            BotonPrueba(
                texto = t[Clave.ACCION_CONTINUAR],
                color = color,
                modifier = Modifier.fillMaxWidth()
            ) {
                respuesta = null
                indice++
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}
