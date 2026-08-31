package com.fieston.ui.pantallas.pruebas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fieston.modelo.Categoria
import com.fieston.ui.comun.Cronometro
import com.fieston.ui.comun.FondoFiesta
import com.fieston.ui.comun.PastillaCategoria
import com.fieston.ui.comun.Sonidos
import com.fieston.ui.tema.Superficie
import com.fieston.ui.tema.TextoFuerte
import com.fieston.ui.tema.TextoTenue

/** Estructura común de todas las pruebas: categoría arriba, cuenta atrás y contenido. */
@Composable
fun MarcoPrueba(
    categoria: Categoria,
    segundos: Int,
    enMarcha: Boolean,
    sonidos: Sonidos,
    marcador: String?,
    onTiempoAgotado: () -> Unit,
    contenido: @Composable ColumnScope.() -> Unit
) {
    FondoFiesta(tinte = categoria.color) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 14.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PastillaCategoria(categoria)
                Spacer(Modifier.weight(1f))
                if (marcador != null) {
                    Text(
                        marcador,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextoFuerte
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Cronometro(
                segundos = segundos,
                enMarcha = enMarcha,
                sonidos = sonidos,
                onFin = onTiempoAgotado
            )

            Spacer(Modifier.height(16.dp))

            contenido()
        }
    }
}

/** Tarjeta central con la palabra a interpretar. */
@Composable
fun TarjetaPalabra(
    texto: String,
    color: Color,
    modifier: Modifier = Modifier,
    encabezado: String? = null,
    contenidoExtra: (@Composable ColumnScope.() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Superficie)
            .border(2.dp, color.copy(alpha = 0.45f), RoundedCornerShape(24.dp))
            .padding(22.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (encabezado != null) {
                Text(
                    encabezado,
                    style = MaterialTheme.typography.labelLarge,
                    color = TextoTenue
                )
                Spacer(Modifier.height(10.dp))
            }
            Text(
                texto,
                style = MaterialTheme.typography.displayMedium,
                color = TextoFuerte,
                textAlign = TextAlign.Center
            )
            contenidoExtra?.invoke(this)
        }
    }
}

@Composable
fun BotonPrueba(
    texto: String,
    color: Color,
    modifier: Modifier = Modifier,
    colorTexto: Color = Color.Black,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .heightIn(min = 62.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            texto,
            style = MaterialTheme.typography.titleMedium,
            color = colorTexto,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FilaBotones(contenido: @Composable RowScope.() -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = contenido
    )
}
