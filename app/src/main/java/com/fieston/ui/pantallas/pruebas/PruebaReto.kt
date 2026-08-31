package com.fieston.ui.pantallas.pruebas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fieston.datos.RetoRapido
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.Categoria
import com.fieston.ui.comun.Sonidos
import com.fieston.ui.tema.Exito
import com.fieston.ui.tema.Superficie
import com.fieston.ui.tema.SuperficieAlta
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue
import kotlinx.coroutines.delay

@Composable
fun PruebaReto(vm: JuegoViewModel, reto: RetoRapido, sonidos: Sonidos) {
    val categoria = Categoria.RETO
    var contador by remember { mutableIntStateOf(0) }
    var terminada by remember { mutableStateOf(false) }
    val conseguido = contador >= reto.objetivo

    fun cerrar(exito: Boolean) {
        if (terminada) return
        terminada = true
        vm.resolverPrueba(exito)
    }

    LaunchedEffect(conseguido) {
        if (conseguido && !terminada) {
            sonidos.acierto()
            delay(700)
            cerrar(true)
        }
    }

    MarcoPrueba(
        categoria = categoria,
        segundos = vm.estado.segundosDe(categoria),
        enMarcha = !terminada && !conseguido,
        sonidos = sonidos,
        marcador = null,
        onTiempoAgotado = { cerrar(contador >= reto.objetivo) }
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(Superficie)
                .border(2.dp, categoria.color.copy(alpha = 0.4f), RoundedCornerShape(22.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "DECID ${reto.objetivo}",
                    style = MaterialTheme.typography.labelLarge,
                    color = categoria.color
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    reto.texto,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextoFuerte,
                    textAlign = TextAlign.Center
                )
            }
        }

        Box(
            Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(216.dp)
                    .clip(CircleShape)
                    .background(if (conseguido) Exito else categoria.color)
                    .clickable(enabled = !conseguido && !terminada) {
                        sonidos.toque()
                        contador++
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "$contador",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.Black
                    )
                    Text(
                        if (conseguido) "¡CONSEGUIDO!" else "de ${reto.objetivo}  ·  toca para sumar",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        FilaBotones {
            BotonPrueba(
                texto = "−1",
                color = SuperficieAlta,
                colorTexto = TextoFuerte,
                modifier = Modifier.weight(1f)
            ) {
                if (contador > 0) contador--
            }
            BotonPrueba(
                texto = "NOS RENDIMOS",
                color = SuperficieAlta,
                colorTexto = TextoTenue,
                modifier = Modifier.weight(2f)
            ) {
                cerrar(false)
            }
        }

        Spacer(Modifier.height(8.dp))
        Text(
            "Vale una vez cada respuesta. Si se repite, no cuenta.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextoTenue,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
