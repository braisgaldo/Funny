package com.fieston.ui.pantallas.pruebas

import androidx.compose.runtime.Composable
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.Prueba
import com.fieston.ui.comun.Sonidos

@Composable
fun PantallaPrueba(vm: JuegoViewModel, sonidos: Sonidos) {
    when (val prueba = vm.estado.prueba) {
        is Prueba.DeMimica -> PruebaMimica(vm, prueba.palabras, sonidos)
        is Prueba.DeDibujo -> PruebaDibujo(vm, prueba.palabras, sonidos)
        is Prueba.DeTabu -> PruebaTabu(vm, prueba.cartas, sonidos)
        is Prueba.DeCuando -> PruebaCuando(vm, prueba, sonidos)
        is Prueba.DePreguntas -> PruebaPreguntas(vm, prueba, sonidos)
        is Prueba.DeReto -> PruebaReto(vm, prueba.reto, sonidos)
        null -> Unit
    }
}
