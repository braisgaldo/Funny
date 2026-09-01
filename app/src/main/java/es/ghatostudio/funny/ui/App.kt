package es.ghatostudio.funny.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import es.ghatostudio.funny.dominio.Modo
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.plataforma.ReglasDePluralAndroid
import es.ghatostudio.funny.plataforma.Sistema
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.plataforma.recordarSonidos
import es.ghatostudio.funny.ui.donacion.HojaCafe
import es.ghatostudio.funny.ui.i18n.LocalTextos
import es.ghatostudio.funny.ui.i18n.textosDe
import es.ghatostudio.funny.ui.pantallas.PantallaAcercaDe
import es.ghatostudio.funny.ui.pantallas.PantallaAjustes
import es.ghatostudio.funny.ui.pantallas.PantallaAyuda
import es.ghatostudio.funny.ui.pantallas.PantallaComodin
import es.ghatostudio.funny.ui.pantallas.PantallaEntrega
import es.ghatostudio.funny.ui.pantallas.PantallaFinSolitario
import es.ghatostudio.funny.ui.pantallas.PantallaIdioma
import es.ghatostudio.funny.ui.pantallas.PantallaInicio
import es.ghatostudio.funny.ui.pantallas.PantallaMando
import es.ghatostudio.funny.ui.pantallas.PantallaModo
import es.ghatostudio.funny.ui.pantallas.PantallaParticipantes
import es.ghatostudio.funny.ui.pantallas.PantallaResultado
import es.ghatostudio.funny.ui.pantallas.PantallaSalon
import es.ghatostudio.funny.ui.pantallas.PantallaTablero
import es.ghatostudio.funny.ui.pantallas.PantallaTema
import es.ghatostudio.funny.ui.pantallas.PantallaVictoria
import es.ghatostudio.funny.ui.pantallas.pruebas.PantallaPrueba
import es.ghatostudio.funny.ui.pantallas.pruebas.PantallaRondaTodos
import es.ghatostudio.funny.ui.tema.Superficie
import es.ghatostudio.funny.ui.tema.TemaFunny
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.paletaSegunAjustes
import es.ghatostudio.funny.ui.tour.PantallaTour

/**
 * Raíz de la aplicación: tema, idioma, dirección de lectura y navegación.
 *
 * La navegación es un `when` sobre un enum y no una librería de navegación a
 * propósito. Funny tiene dieciocho pantallas sin rutas profundas ni enlaces
 * entrantes, y todo el estado vive en un único objeto inmutable; un grafo de
 * navegación con sus argumentos serializados sería más código y más sitios
 * donde el estado podría desincronizarse.
 */
@Composable
fun AppFunny(
    vm: JuegoViewModel = viewModel(),
    salon: SalonViewModel = viewModel()
) {
    val contexto = LocalContext.current
    val estado = vm.estado
    val ajustes = estado.ajustes

    // Los textos se rehacen solo al cambiar de idioma: llevan dentro el
    // catálogo y las reglas de plural de la ICU del sistema.
    val textos = remember(vm.idioma) {
        textosDe(vm.idioma, ReglasDePluralAndroid)
    }

    val paleta = paletaSegunAjustes(ajustes)

    // Las animaciones se apagan si lo pide la app o si lo pide el sistema.
    val animaciones = remember(ajustes.animaciones) {
        ajustes.animaciones && !Sistema.animacionesReducidas(contexto)
    }

    val sonidos = recordarSonidos(ajustes)
    val estadoSnackbar = remember { SnackbarHostState() }

    LaunchedEffect(vm.mensaje) {
        val mensaje = vm.mensaje ?: return@LaunchedEffect
        estadoSnackbar.showSnackbar(message = mensaje, duration = SnackbarDuration.Short)
        vm.mensajeVisto()
    }

    TemaFunny(paleta = paleta, animaciones = animaciones) {
        CompositionLocalProvider(
            LocalTextos provides textos,
            // La dirección de lectura sigue al idioma elegido, no al del
            // sistema: si alguien pone la app en árabe con el móvil en español,
            // la interfaz tiene que ir de derecha a izquierda igualmente.
            LocalLayoutDirection provides
                if (textos.esRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
        ) {
            // Atrás: en mitad de una prueba no hace nada, para no arruinar un
            // turno con un gesto involuntario.
            BackHandler(enabled = estado.pantalla != Pantalla.INICIO) {
                when (estado.pantalla) {
                    Pantalla.MODO,
                    Pantalla.PARTICIPANTES,
                    Pantalla.AJUSTES,
                    Pantalla.AYUDA,
                    Pantalla.SALON,
                    Pantalla.TOUR,
                    Pantalla.TABLERO,
                    Pantalla.VICTORIA,
                    Pantalla.SOLITARIO_FIN -> vm.volverAlMenu()

                    Pantalla.TEMA,
                    Pantalla.IDIOMA,
                    Pantalla.ACERCA_DE -> vm.ir(Pantalla.AJUSTES)

                    else -> Unit
                }
            }

            // El hub reenvía su estado a los mandos en un único sitio.
            SincronizarSalon(vm, salon)

            Box(Modifier.fillMaxSize()) {
                // Un móvil que hace de mando durante una partida no pinta el
                // juego: pinta lo que el hub le manda.
                val esMandoEnPartida = salon.estado.esMando &&
                    salon.estado.conectado &&
                    salon.estado.vista != null &&
                    estado.pantalla != Pantalla.SALON

                if (esMandoEnPartida) {
                    PantallaMando(salon, sonidos)
                } else {
                    Pantallas(vm, salon, sonidos)
                }

                if (vm.hojaCafeVisible) {
                    HojaCafe(vm, sonidos)
                }

                SnackbarHost(
                    hostState = estadoSnackbar,
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) { datos ->
                    Snackbar(
                        snackbarData = datos,
                        containerColor = Superficie,
                        contentColor = TextoFuerte
                    )
                }
            }
        }
    }
}

@Composable
private fun Pantallas(
    vm: JuegoViewModel,
    salon: SalonViewModel,
    sonidos: Sonidos
) {
    when (vm.estado.pantalla) {
        Pantalla.INICIO -> PantallaInicio(vm)
        Pantalla.MODO -> PantallaModo(vm)
        Pantalla.PARTICIPANTES -> PantallaParticipantes(vm)
        Pantalla.AJUSTES -> PantallaAjustes(vm)
        Pantalla.TEMA -> PantallaTema(vm)
        Pantalla.IDIOMA -> PantallaIdioma(vm)
        Pantalla.AYUDA -> PantallaAyuda(vm)
        Pantalla.ACERCA_DE -> PantallaAcercaDe(vm)
        Pantalla.TOUR -> PantallaTour(vm)
        Pantalla.SALON -> PantallaSalon(vm, salon)
        Pantalla.TABLERO -> PantallaTablero(vm, sonidos)
        Pantalla.COMODIN -> PantallaComodin(vm)
        Pantalla.ENTREGA -> PantallaEntrega(vm)
        Pantalla.PRUEBA -> PantallaPrueba(vm, sonidos)
        Pantalla.RONDA_TODOS -> PantallaRondaTodos(vm, sonidos)
        Pantalla.RESULTADO -> PantallaResultado(vm, sonidos)
        Pantalla.VICTORIA -> PantallaVictoria(vm, sonidos)
        Pantalla.SOLITARIO_FIN -> PantallaFinSolitario(vm, sonidos)
    }
}

/**
 * En el hub, cada cambio de estado hay que reenviarlo a los mandos. Se hace
 * aquí, en un único sitio, en lugar de en cada acción: así no se puede olvidar
 * al añadir una pantalla nueva.
 */
@Composable
private fun SincronizarSalon(vm: JuegoViewModel, salon: SalonViewModel) {
    val estado = vm.estado
    LaunchedEffect(
        estado.pantalla,
        estado.turno,
        estado.juego,
        estado.prueba,
        estado.dado,
        estado.modo == Modo.SOLITARIO
    ) {
        if (salon.estado.esHub) salon.difundirVistas()
    }
}
