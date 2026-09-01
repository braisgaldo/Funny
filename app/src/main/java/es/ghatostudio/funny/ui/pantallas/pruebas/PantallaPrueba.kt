package es.ghatostudio.funny.ui.pantallas.pruebas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Prueba
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sonidos
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.TextoTenue

/**
 * Reparto de pruebas: lleva cada [Prueba] a su pantalla.
 *
 * El `when` es exhaustivo sobre la interfaz sellada, así que añadir un juego
 * trece obliga al compilador a pedir su pantalla aquí. Es la red de seguridad
 * que evita que un juego nuevo se quede en negro sin que nadie se entere.
 */
@Composable
fun PantallaPrueba(vm: JuegoViewModel, sonidos: Sonidos) {
    when (val prueba = vm.estado.prueba) {
        is Prueba.DeMimica -> PruebaMimica(vm, prueba.palabras, sonidos)
        is Prueba.DeDibujo -> PruebaDibujo(vm, prueba.palabras, sonidos)
        is Prueba.DeTabu -> PruebaTabu(vm, prueba.cartas, sonidos)
        is Prueba.DeCuando -> PruebaCuando(vm, prueba, sonidos)
        is Prueba.DePreguntas -> PruebaPreguntas(vm, prueba, sonidos)
        is Prueba.DeReto -> PruebaReto(vm, prueba.reto, sonidos)
        is Prueba.DeEmojis -> PruebaEmojis(vm, prueba, sonidos)
        is Prueba.DeVerdaderoFalso -> PruebaVerdaderoFalso(vm, prueba.afirmaciones, sonidos)
        is Prueba.DeTrabalenguas -> PruebaTrabalenguas(vm, prueba.trabalenguas, sonidos)
        is Prueba.DeOrdena -> PruebaOrdena(vm, prueba, sonidos)
        is Prueba.DeCanta -> PruebaCanta(vm, prueba.cancion, sonidos)
        is Prueba.DeDesafio -> PruebaDesafio(vm, prueba.desafio, sonidos)
        null -> SinContenido(vm)
    }
}

/**
 * No debería verse nunca: el repartidor busca alternativas antes de rendirse.
 * Pero si un día se queda sin contenido, es mejor una salida digna que una
 * pantalla negra sin botones.
 */
@Composable
private fun SinContenido(vm: JuegoViewModel) {
    val t = textos()
    FondoFunny {
        Column(
            Modifier.fillMaxSize().padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("🤷", style = MaterialTheme.typography.displayLarge)
            Text(
                t[Clave.ESTADO_SIN_CONTENIDO],
                style = MaterialTheme.typography.bodyLarge,
                color = TextoTenue,
                textAlign = TextAlign.Center,
            )
            BotonGrande(t[Clave.ACCION_CONTINUAR]) { vm.resolverPrueba(false) }
        }
    }
}
