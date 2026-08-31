package com.fieston.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.Categoria
import com.fieston.ui.comun.FondoFiesta
import com.fieston.ui.tema.Acento
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue

@Composable
fun PantallaComodin(vm: JuegoViewModel) {
    val estado = vm.estado
    val activo = estado.equipoActivo ?: return
    val eligen = estado.equipoQueElige

    FondoFiesta(tinte = Acento) {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🃏", style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.height(8.dp))
            Text(
                "CASILLA COMODÍN",
                style = MaterialTheme.typography.headlineLarge,
                color = Acento,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                buildString {
                    append(eligen?.let { "${it.emoji} ${it.nombre}" } ?: "Los rivales")
                    append(" elige la prueba que tendrá que superar ")
                    append("${activo.emoji} ${activo.nombre}")
                },
                style = MaterialTheme.typography.bodyLarge,
                color = TextoTenue,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(28.dp))

            Categoria.entries.chunked(2).forEach { pareja ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    pareja.forEach { categoria ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(18.dp))
                                .background(categoria.color.copy(alpha = 0.16f))
                                .border(2.dp, categoria.color.copy(alpha = 0.7f), RoundedCornerShape(18.dp))
                                .clickable { vm.elegirCategoria(categoria) }
                                .padding(vertical = 18.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(categoria.emoji, style = MaterialTheme.typography.headlineLarge)
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    categoria.etiqueta,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextoFuerte,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    if (pareja.size == 1) Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}
