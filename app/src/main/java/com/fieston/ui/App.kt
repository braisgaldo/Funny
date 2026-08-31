package com.fieston.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.Pantalla
import com.fieston.ui.comun.recordarSonidos
import com.fieston.ui.pantallas.PantallaAjustes
import com.fieston.ui.pantallas.PantallaComoJugar
import com.fieston.ui.pantallas.PantallaComodin
import com.fieston.ui.pantallas.PantallaEntrega
import com.fieston.ui.pantallas.PantallaEquipos
import com.fieston.ui.pantallas.PantallaInicio
import com.fieston.ui.pantallas.PantallaResultado
import com.fieston.ui.pantallas.PantallaTablero
import com.fieston.ui.pantallas.PantallaVictoria
import com.fieston.ui.pantallas.pruebas.PantallaPrueba
import com.fieston.ui.pantallas.pruebas.PantallaRondaTodos

@Composable
fun AppFieston(vm: JuegoViewModel = viewModel()) {
    val estado = vm.estado
    val sonidos = recordarSonidos(estado.ajustes)

    // Durante una prueba se ignora el botón atrás para no arruinar el turno.
    BackHandler(enabled = estado.pantalla != Pantalla.INICIO) {
        when (estado.pantalla) {
            Pantalla.EQUIPOS,
            Pantalla.AJUSTES,
            Pantalla.COMO_JUGAR,
            Pantalla.TABLERO,
            Pantalla.VICTORIA -> vm.ir(Pantalla.INICIO)

            else -> Unit
        }
    }

    when (estado.pantalla) {
        Pantalla.INICIO -> PantallaInicio(vm)
        Pantalla.EQUIPOS -> PantallaEquipos(vm)
        Pantalla.AJUSTES -> PantallaAjustes(vm)
        Pantalla.COMO_JUGAR -> PantallaComoJugar(vm)
        Pantalla.TABLERO -> PantallaTablero(vm, sonidos)
        Pantalla.COMODIN -> PantallaComodin(vm)
        Pantalla.ENTREGA -> PantallaEntrega(vm)
        Pantalla.PRUEBA -> PantallaPrueba(vm, sonidos)
        Pantalla.RONDA_TODOS -> PantallaRondaTodos(vm, sonidos)
        Pantalla.RESULTADO -> PantallaResultado(vm, sonidos)
        Pantalla.VICTORIA -> PantallaVictoria(vm, sonidos)
    }
}
