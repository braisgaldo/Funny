package es.ghatostudio.funny.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.salon.TipoAccion
import es.ghatostudio.funny.dominio.salon.VistaDelMando
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.SalonViewModel
import es.ghatostudio.funny.ui.comun.Banda
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.BotonPrueba
import es.ghatostudio.funny.ui.comun.FilaBotones
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.comun.TarjetaPalabra
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.pantallas.pruebas.BloqueOpciones
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Fallo
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

/**
 * Lo que se ve en un móvil que hace de **mando** durante la partida.
 *
 * Es deliberadamente pobre en información: el tablero, el marcador y el
 * resultado están en el móvil de la mesa, que es donde todos pueden mirarlos.
 * Aquí solo aparece lo que es privado o lo que hay que tocar. Ese reparto es
 * toda la idea del salón.
 */
@Composable
fun PantallaMando(salon: SalonViewModel, sonidos: Sonidos) {
    val t = textos()
    val vista = salon.estado.vista

    if (vista == null) {
        Espera(t[Clave.TABLERO_ESPERANDO_HUB])
        return
    }

    when {
        // Responder en el propio móvil: casillas de «juegan todos».
        vista.pantalla == Pantalla.RONDA_TODOS && vista.opciones.isNotEmpty() ->
            RespuestaPrivada(salon, vista, sonidos)

        // Contenido secreto: la palabra que solo puede ver quien actúa.
        vista.contenidoPrivado.isNotEmpty() -> ContenidoSecreto(salon, vista, sonidos)

        // Turno propio en una pantalla que solo requiere avanzar.
        vista.esMiTurno -> TurnoPropio(salon, vista)

        else -> Espera(t[Clave.SALON_MIRA_EL_HUB])
    }
}

@Composable
private fun Espera(mensaje: String) {
    val t = textos()
    FondoFunny {
        Column(
            Modifier.fillMaxSize().padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("📺", style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.height(12.dp))
            Text(
                mensaje,
                style = MaterialTheme.typography.headlineMedium,
                color = TextoFuerte,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                t[Clave.SALON_ROL_MANDO],
                style = MaterialTheme.typography.labelLarge,
                color = TextoTenue,
            )
        }
    }
}

@Composable
private fun TurnoPropio(salon: SalonViewModel, vista: VistaDelMando) {
    val t = textos()
    val p = paleta()
    val color = vista.juego?.let { p.colorDe(it) } ?: Primario

    FondoFunny(tinte = color) {
        Column(
            Modifier.fillMaxSize().padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Banda(t[Clave.SALON_TU_TURNO], color)
            Spacer(Modifier.height(20.dp))

            if (vista.juego != null) {
                Text(vista.juego.emoji, style = MaterialTheme.typography.displayLarge)
                Spacer(Modifier.height(8.dp))
                Text(
                    t.nombreDe(vista.juego),
                    style = MaterialTheme.typography.headlineLarge,
                    color = color,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(20.dp))
            }

            when (vista.pantalla) {
                Pantalla.TABLERO ->
                    BotonGrande(
                        texto = "🎲   ${t[Clave.TABLERO_TIRAR]}",
                        onClick = { salon.enviarAccion(TipoAccion.TIRAR) },
                        color = color,
                        colorTexto = p.textoSobre(color),
                    )

                Pantalla.ENTREGA ->
                    BotonGrande(
                        texto = t[Clave.ACCION_EMPEZAR],
                        onClick = { salon.enviarAccion(TipoAccion.EMPEZAR_PRUEBA) },
                        color = color,
                        colorTexto = p.textoSobre(color),
                    )

                else ->
                    Text(
                        t[Clave.SALON_MIRA_EL_HUB],
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoTenue,
                        textAlign = TextAlign.Center,
                    )
            }
        }
    }
}

/**
 * La palabra secreta, en el móvil de quien actúa y en ningún otro.
 *
 * Los botones de acierto y de fallo están aquí y no en el hub porque quien
 * actúa es quien sabe si ha pasado a la siguiente palabra; el hub solo recibe
 * el total al terminar.
 */
@Composable
private fun ContenidoSecreto(
    salon: SalonViewModel,
    vista: VistaDelMando,
    sonidos: Sonidos,
) {
    val t = textos()
    val p = paleta()
    val juego = vista.juego
    val color = juego?.let { p.colorDe(it) } ?: Primario
    var indice by remember(vista.contenidoPrivado) { mutableIntStateOf(0) }
    var aciertos by remember(vista.contenidoPrivado) { mutableIntStateOf(0) }

    val actual = vista.contenidoPrivado.getOrNull(indice).orEmpty()
    // Las cartas de tabú viajan como «palabra|prohibida|prohibida…».
    val partes = actual.split(SalonViewModel.SEPARADOR_TABU)
    val palabra = partes.firstOrNull().orEmpty()
    val prohibidas = partes.drop(1)
    val seAcabaron = indice >= vista.contenidoPrivado.size

    FondoFunny(tinte = color) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Banda(t.con(Clave.PRUEBA_ACIERTOS, aciertos), color)
            Spacer(Modifier.height(16.dp))

            if (seAcabaron) {
                Text("🎉", style = MaterialTheme.typography.displayLarge)
                Spacer(Modifier.height(12.dp))
                BotonGrande(
                    texto = t[Clave.PRUEBA_TERMINAR],
                    onClick = { salon.enviarAccion(TipoAccion.TERMINAR, aciertos) },
                    color = color,
                    colorTexto = p.textoSobre(color),
                )
                return@Column
            }

            TarjetaPalabra(
                texto = palabra,
                color = color,
                encabezado = juego?.let { t.lemaDe(it).uppercase(t.locale) },
            ) {
                if (prohibidas.isNotEmpty()) {
                    Spacer(Modifier.height(14.dp))
                    prohibidas.forEach { prohibida ->
                        Text(
                            prohibida,
                            style = MaterialTheme.typography.titleMedium,
                            color = Fallo,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Los juegos que juzga la mesa no tienen «siguiente palabra»: una
            // sola carta y un veredicto.
            if (juego?.veredictoDeLaMesa == true) {
                Text(
                    t[Clave.PRUEBA_VEREDICTO_DECIDE_MESA],
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoTenue,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(12.dp))
                FilaBotones {
                    BotonPrueba(
                        texto = t[Clave.PRUEBA_VEREDICTO_NO_LOGRADO],
                        color = Fallo,
                        modifier = Modifier.weight(1f),
                    ) { salon.enviarAccion(TipoAccion.VEREDICTO, 0) }
                    BotonPrueba(
                        texto = t[Clave.PRUEBA_VEREDICTO_LOGRADO],
                        color = Exito,
                        modifier = Modifier.weight(1.4f),
                    ) { salon.enviarAccion(TipoAccion.VEREDICTO, 1) }
                }
                return@Column
            }

            FilaBotones {
                BotonPrueba(
                    texto = t[Clave.PRUEBA_SALTAR],
                    color = SuperficieAlta,
                    colorTexto = TextoFuerte,
                    modifier = Modifier.weight(1f),
                ) {
                    sonidos.toque()
                    indice++
                }
                BotonPrueba(
                    texto = t[Clave.PRUEBA_ACERTADA],
                    color = Exito,
                    modifier = Modifier.weight(1.4f),
                ) {
                    sonidos.acierto()
                    aciertos++
                    indice++
                }
            }

            Spacer(Modifier.height(10.dp))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                BotonPrueba(
                    texto = t[Clave.PRUEBA_TERMINAR],
                    color = SuperficieAlta,
                    colorTexto = TextoTenue,
                    modifier = Modifier.fillMaxWidth(),
                ) { salon.enviarAccion(TipoAccion.TERMINAR, aciertos) }
            }
        }
    }
}

/**
 * Responder en el propio móvil, a la vez que todos los demás.
 *
 * Es la otra mitad de lo que el salón aporta: nadie ve la respuesta de nadie y
 * no hay que pasarse el móvil. Al enviar se queda esperando, porque el
 * resultado lo enseña la mesa.
 */
@Composable
private fun RespuestaPrivada(
    salon: SalonViewModel,
    vista: VistaDelMando,
    sonidos: Sonidos,
) {
    val t = textos()
    val p = paleta()
    val color = vista.juego?.let { p.colorDe(it) } ?: Primario
    var elegida by remember(vista.enunciado) { mutableStateOf<Int?>(null) }

    if (vista.respuestaEnviada || elegida != null) {
        FondoFunny(tinte = color) {
            Column(
                Modifier.fillMaxSize().padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("✅", style = MaterialTheme.typography.displayLarge)
                Spacer(Modifier.height(12.dp))
                Text(
                    t[Clave.RONDA_TODOS_GUARDADA],
                    style = MaterialTheme.typography.titleLarge,
                    color = TextoFuerte,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    t[Clave.RONDA_TODOS_ESPERANDO],
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextoTenue,
                )
            }
        }
        return
    }

    FondoFunny(tinte = color) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
        ) {
            Tarjeta(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(14.dp)) {
                    Text(
                        t[Clave.RONDA_TODOS_EN_TU_MOVIL],
                        style = MaterialTheme.typography.labelLarge,
                        color = color,
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            BloqueOpciones(
                enunciado = vista.enunciado.orEmpty(),
                tema = null,
                opciones = vista.opciones,
                // El mando no sabe cuál es la correcta, y eso es intencionado:
                // así no hay forma de sacarla mirando el tráfico.
                correcta = -1,
                elegida = elegida,
                revelar = false,
                color = color,
            ) { indice ->
                sonidos.toque()
                elegida = indice
                salon.enviarAccion(TipoAccion.RESPONDER, indice)
            }
        }
    }
}
