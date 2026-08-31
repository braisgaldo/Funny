package com.fieston.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fieston.juego.JuegoViewModel
import com.fieston.modelo.COLORES_EQUIPO
import com.fieston.modelo.Categoria
import com.fieston.modelo.Pantalla
import com.fieston.ui.comun.BotonGrande
import com.fieston.ui.comun.FondoFiesta
import com.fieston.ui.comun.PastillaCategoria
import com.fieston.ui.tema.Primario
import com.fieston.ui.tema.SuperficieAlta
import com.fieston.ui.tema.TextoTenue

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PantallaInicio(vm: JuegoViewModel) {
    val estado = vm.estado
    FondoFiesta(tinte = Primario) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🎉", style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                "FIESTÓN".forEachIndexed { indice, letra ->
                    Text(
                        text = letra.toString(),
                        style = MaterialTheme.typography.displayMedium,
                        color = COLORES_EQUIPO[indice % COLORES_EQUIPO.size]
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            Text(
                "El juego de fiesta por equipos\npara un solo móvil",
                style = MaterialTheme.typography.bodyLarge,
                color = TextoTenue,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(26.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Categoria.entries.forEach { PastillaCategoria(it) }
            }

            Spacer(Modifier.height(40.dp))

            if (estado.partidaEnCurso) {
                BotonGrande("SEGUIR LA PARTIDA", onClick = { vm.ir(Pantalla.TABLERO) })
                Spacer(Modifier.height(12.dp))
                BotonGrande(
                    "PARTIDA NUEVA",
                    onClick = { vm.ir(Pantalla.EQUIPOS) },
                    color = SuperficieAlta
                )
            } else {
                BotonGrande("JUGAR", onClick = { vm.ir(Pantalla.EQUIPOS) })
            }

            Spacer(Modifier.height(12.dp))
            BotonGrande(
                "CÓMO SE JUEGA",
                onClick = { vm.ir(Pantalla.COMO_JUGAR) },
                color = SuperficieAlta
            )
            Spacer(Modifier.height(12.dp))
            BotonGrande(
                "AJUSTES",
                onClick = { vm.ir(Pantalla.AJUSTES) },
                color = SuperficieAlta
            )
        }
    }
}
