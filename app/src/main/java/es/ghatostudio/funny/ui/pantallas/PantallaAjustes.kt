package es.ghatostudio.funny.ui.pantallas

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.BuildConfig
import es.ghatostudio.funny.datos.CopiaSeguridad
import es.ghatostudio.funny.dominio.Duracion
import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.MAXIMO_PARTICIPANTES
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.Ritmo
import es.ghatostudio.funny.dominio.TemaId
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.Sistema
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.comun.AREA_TACTIL_MINIMA
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.BotonSuave
import es.ghatostudio.funny.ui.comun.Cabecera
import es.ghatostudio.funny.ui.comun.FilaAjuste
import es.ghatostudio.funny.ui.comun.FilaInterruptor
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.InsigniaDeIdioma
import es.ghatostudio.funny.ui.comun.SelectorSegmentado
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.comun.TituloDeSeccion
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Contorno
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.Superficie
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta
import es.ghatostudio.funny.ui.tema.paletaDe

/**
 * Ajustes: el punto único de entrada a tema, idioma, datos, compartir,
 * donación, ayuda, tour y «Acerca de», como pide el punto 4.1 de la plantilla.
 *
 * Está dividida en cuatro bloques —apariencia, partida, datos y más— porque con
 * dieciocho opciones seguidas nadie encuentra nada.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PantallaAjustes(vm: JuegoViewModel) {
    val t = textos()
    val p = paleta()
    val contexto = LocalContext.current
    val estado = vm.estado
    val ajustes = estado.ajustes
    val copia = remember { CopiaSeguridad(contexto) }
    var importando by remember { mutableStateOf<CopiaSeguridad.Resultado.Bien?>(null) }

    // Exportar: el selector del sistema decide dónde se guarda. Funny no pide
    // permisos de almacenamiento en ningún momento.
    val lanzadorExportar = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument(CopiaSeguridad.MIME)
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        val bien = copia.exportarA(uri, ajustes, estado.participantes)
        vm.avisar(
            if (bien) t[Clave.COPIA_EXPORTAR_HECHO] else t[Clave.COPIA_EXPORTAR_ERROR]
        )
    }

    val lanzadorImportar = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        when (val resultado = copia.leer(uri)) {
            is CopiaSeguridad.Resultado.Bien -> importando = resultado
            CopiaSeguridad.Resultado.FormatoInvalido ->
                vm.avisar(t[Clave.COPIA_IMPORTAR_ERROR_FORMATO])

            is CopiaSeguridad.Resultado.EsquemaFuturo ->
                vm.avisar(t[Clave.COPIA_IMPORTAR_ERROR_VERSION])
        }
    }

    importando?.let { pendiente ->
        DialogoImportar(
            fecha = pendiente.fecha,
            onFusionar = {
                copia.respaldar(ajustes, estado.participantes)
                vm.reemplazarDatos(
                    ajustes = pendiente.ajustes,
                    participantes = copia.fusionar(
                        actuales = estado.participantes,
                        importados = pendiente.participantes,
                        maximo = MAXIMO_PARTICIPANTES
                    )
                )
                importando = null
                vm.avisar(t[Clave.COPIA_IMPORTAR_HECHO])
            },
            onReemplazar = {
                copia.respaldar(ajustes, estado.participantes)
                vm.reemplazarDatos(pendiente.ajustes, pendiente.participantes)
                importando = null
                vm.avisar(t[Clave.COPIA_IMPORTAR_HECHO])
            },
            onCancelar = { importando = null }
        )
    }

    FondoFunny {
        Column(Modifier.fillMaxSize()) {
            Cabecera(
                titulo = t[Clave.AJUSTES_TITULO],
                subtitulo = t[Clave.AJUSTES_SUBTITULO],
                onVolver = { vm.ir(Pantalla.INICIO) }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // ------------------------------------------------ apariencia
                TituloDeSeccion(t[Clave.AJUSTES_APARIENCIA])
                Tarjeta {
                    Column(Modifier.padding(horizontal = 18.dp, vertical = 10.dp)) {
                        FilaAjuste(
                            titulo = t[Clave.AJUSTES_TEMA],
                            detalle = t[ajustes.tema.claveNombre],
                            onClick = { vm.ir(Pantalla.TEMA) },
                            derecha = { MuestraDeTema(ajustes.tema) }
                        )
                        FilaAjuste(
                            titulo = t[Clave.AJUSTES_IDIOMA],
                            detalle = vm.idioma.endonimo,
                            onClick = { vm.ir(Pantalla.IDIOMA) },
                            derecha = { InsigniaDeIdioma(vm.idioma) }
                        )
                        FilaInterruptor(
                            titulo = t[Clave.AJUSTES_ANIMACIONES],
                            detalle = t[Clave.AJUSTES_ANIMACIONES_DETALLE],
                            activo = ajustes.animaciones
                        ) { vm.actualizarAjustes(ajustes.copy(animaciones = it)) }
                    }
                }

                // --------------------------------------------------- partida
                TituloDeSeccion(t[Clave.AJUSTES_PARTIDA])
                Tarjeta {
                    Column(Modifier.padding(18.dp)) {
                        Text(
                            t[Clave.AJUSTES_RITMO],
                            style = MaterialTheme.typography.titleMedium,
                            color = TextoFuerte
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            t[Clave.AJUSTES_RITMO_DETALLE],
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextoTenue
                        )
                        Spacer(Modifier.height(12.dp))
                        SelectorSegmentado(
                            opciones = Ritmo.entries.map { t[it.claveNombre] },
                            seleccion = Ritmo.entries.indexOf(ajustes.ritmo)
                        ) { indice ->
                            vm.actualizarAjustes(ajustes.copy(ritmo = Ritmo.entries[indice]))
                        }

                        Spacer(Modifier.height(18.dp))

                        Text(
                            t[Clave.AJUSTES_DURACION],
                            style = MaterialTheme.typography.titleMedium,
                            color = TextoFuerte
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            t.con(
                                Clave.AJUSTES_DURACION_DETALLE,
                                ajustes.duracion.casillas,
                                t[ajustes.duracion.claveDetalle]
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextoTenue
                        )
                        Spacer(Modifier.height(12.dp))
                        SelectorSegmentado(
                            opciones = Duracion.entries.map { t[it.claveNombre] },
                            seleccion = Duracion.entries.indexOf(ajustes.duracion)
                        ) { indice ->
                            vm.actualizarAjustes(ajustes.copy(duracion = Duracion.entries[indice]))
                        }
                    }
                }

                // ---------------------------------------- juegos de la partida
                Tarjeta {
                    Column(Modifier.padding(18.dp)) {
                        Text(
                            t[Clave.AJUSTES_JUEGOS_ACTIVOS],
                            style = MaterialTheme.typography.titleMedium,
                            color = TextoFuerte
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            t[Clave.AJUSTES_JUEGOS_ACTIVOS_DETALLE],
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextoTenue
                        )
                        Spacer(Modifier.height(6.dp))
                        val jugables = vm.contenidoActual.juegosJugables
                        Text(
                            t.con(
                                Clave.AJUSTES_JUEGOS_CONTADOR,
                                jugables.count { it !in ajustes.juegosDesactivados },
                                jugables.size
                            ),
                            style = MaterialTheme.typography.labelLarge,
                            color = Primario
                        )
                        Spacer(Modifier.height(12.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            jugables.forEach { juego ->
                                InterruptorDeJuego(
                                    juego = juego,
                                    activo = juego !in ajustes.juegosDesactivados,
                                    onPulsar = {
                                        vm.alternarJuego(juego, t[Clave.AJUSTES_JUEGOS_MINIMO])
                                    }
                                )
                            }
                        }
                    }
                }

                Tarjeta {
                    Column(Modifier.padding(horizontal = 18.dp, vertical = 10.dp)) {
                        FilaInterruptor(
                            titulo = t[Clave.AJUSTES_SONIDO],
                            detalle = t[Clave.AJUSTES_SONIDO_DETALLE],
                            activo = ajustes.sonido
                        ) { vm.actualizarAjustes(ajustes.copy(sonido = it)) }
                        FilaInterruptor(
                            titulo = t[Clave.AJUSTES_VIBRACION],
                            detalle = t[Clave.AJUSTES_VIBRACION_DETALLE],
                            activo = ajustes.vibracion
                        ) { vm.actualizarAjustes(ajustes.copy(vibracion = it)) }
                    }
                }

                // ------------------------------------------------------ datos
                TituloDeSeccion(t[Clave.AJUSTES_DATOS])
                Tarjeta {
                    Column(Modifier.padding(horizontal = 18.dp, vertical = 10.dp)) {
                        FilaAjuste(
                            titulo = t[Clave.AJUSTES_EXPORTAR],
                            detalle = t[Clave.AJUSTES_EXPORTAR_DETALLE],
                            onClick = { lanzadorExportar.launch(copia.nombreSugerido()) },
                            derecha = { Text("⬆", style = MaterialTheme.typography.titleLarge) }
                        )
                        FilaAjuste(
                            titulo = t[Clave.AJUSTES_IMPORTAR],
                            detalle = t[Clave.AJUSTES_IMPORTAR_DETALLE],
                            // Se filtra por JSON y se deja también el comodín:
                            // algunos gestores no reconocen el MIME de un
                            // fichero con doble extensión como `.funny.bak`.
                            onClick = {
                                lanzadorImportar.launch(
                                    arrayOf(CopiaSeguridad.MIME, "application/octet-stream", "*/*")
                                )
                            },
                            derecha = { Text("⬇", style = MaterialTheme.typography.titleLarge) }
                        )
                    }
                }

                // -------------------------------------------------------- más
                TituloDeSeccion(t[Clave.AJUSTES_MAS])
                Tarjeta {
                    Column(Modifier.padding(horizontal = 18.dp, vertical = 10.dp)) {
                        FilaAjuste(
                            titulo = t[Clave.AJUSTES_APOYAR],
                            detalle = t[Clave.AJUSTES_APOYAR_DETALLE],
                            onClick = { vm.abrirHojaCafe(automatica = false) },
                            derecha = { Text("☕", style = MaterialTheme.typography.titleLarge) }
                        )
                        FilaAjuste(
                            titulo = t[Clave.AJUSTES_COMPARTIR],
                            detalle = t[Clave.AJUSTES_COMPARTIR_DETALLE],
                            onClick = {
                                Sistema.compartirTexto(
                                    context = contexto,
                                    texto = "${t[Clave.APP_LEMA]}\n${BuildConfig.PAGINA_PROYECTO}",
                                    titulo = t[Clave.ACCION_COMPARTIR]
                                )
                            },
                            derecha = { Text("↗", style = MaterialTheme.typography.titleLarge) }
                        )
                        FilaAjuste(
                            titulo = t[Clave.AJUSTES_TOUR],
                            detalle = t[Clave.AJUSTES_TOUR_DETALLE],
                            onClick = { vm.ir(Pantalla.TOUR) },
                            derecha = { Text("🧭", style = MaterialTheme.typography.titleLarge) }
                        )
                        FilaAjuste(
                            titulo = t[Clave.AJUSTES_AYUDA],
                            detalle = t[Clave.AJUSTES_AYUDA_DETALLE],
                            onClick = { vm.ir(Pantalla.AYUDA) },
                            derecha = { Text("❔", style = MaterialTheme.typography.titleLarge) }
                        )
                        FilaAjuste(
                            titulo = t[Clave.AJUSTES_ACERCA_DE],
                            detalle = t[Clave.AJUSTES_ACERCA_DE_DETALLE],
                            onClick = { vm.ir(Pantalla.ACERCA_DE) },
                            derecha = { Text("ℹ", style = MaterialTheme.typography.titleLarge) }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

/** Muestra en miniatura de los colores de un tema. */
@Composable
fun MuestraDeTema(
    id: TemaId,
    modifier: Modifier = Modifier,
    seleccionado: Boolean = false
) {
    val t = textos()
    val muestra = paletaDe(id)
    val descripcion = t.con(Clave.A11Y_TEMA_MUESTRA, t[id.claveNombre])
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(muestra.fondo)
            .border(
                width = if (seleccionado) 3.dp else 1.dp,
                color = if (seleccionado) Primario else Contorno,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(6.dp)
            .semantics { contentDescription = descripcion },
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        listOf(muestra.primario, muestra.acento, muestra.textoFuerte).forEach { color ->
            Box(Modifier.size(12.dp).clip(CircleShape).background(color))
        }
    }
}

/** Pastilla de juego que se puede apagar y encender. */
@Composable
private fun InterruptorDeJuego(juego: Juego, activo: Boolean, onPulsar: () -> Unit) {
    val t = textos()
    val color = paleta().colorDe(juego)
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (activo) color.copy(alpha = 0.2f) else SuperficieAlta)
            .border(
                width = 1.5.dp,
                color = if (activo) color.copy(alpha = 0.7f) else Contorno,
                shape = RoundedCornerShape(50)
            )
            .clickable { onPulsar() }
            .heightIn(min = AREA_TACTIL_MINIMA)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(juego.emoji, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.width(8.dp))
        Text(
            t.nombreDe(juego),
            style = MaterialTheme.typography.labelLarge,
            color = if (activo) color else TextoTenue
        )
        Spacer(Modifier.width(8.dp))
        Text(
            if (activo) "✓" else "✕",
            style = MaterialTheme.typography.labelLarge,
            color = if (activo) Exito else TextoTenue
        )
    }
}

/**
 * Antes de importar hay que decidir qué se hace con lo que ya hay, y cancelar
 * tiene que ser posible: es la única pantalla de la app que puede pisar datos.
 * Se avisa además de que se guarda un respaldo automático primero.
 */
@Composable
private fun DialogoImportar(
    fecha: String,
    onFusionar: () -> Unit,
    onReemplazar: () -> Unit,
    onCancelar: () -> Unit
) {
    val t = textos()
    AlertDialog(
        onDismissRequest = onCancelar,
        containerColor = Superficie,
        titleContentColor = TextoFuerte,
        textContentColor = TextoTenue,
        title = { Text(t[Clave.COPIA_IMPORTAR_TITULO], color = TextoFuerte) },
        text = {
            Column {
                if (fecha.isNotBlank()) {
                    Text(
                        t.con(Clave.COPIA_CABECERA_DETALLE, fecha, BuildConfig.VERSION_NAME),
                        style = MaterialTheme.typography.labelLarge,
                        color = Primario
                    )
                    Spacer(Modifier.height(10.dp))
                }
                Text(
                    t[Clave.COPIA_IMPORTAR_AVISO],
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoTenue
                )
                Spacer(Modifier.height(14.dp))
                BotonGrande(
                    texto = t[Clave.COPIA_IMPORTAR_FUSIONAR],
                    onClick = onFusionar,
                    color = SuperficieAlta,
                    colorTexto = TextoFuerte
                )
                Spacer(Modifier.height(8.dp))
                BotonGrande(
                    texto = t[Clave.COPIA_IMPORTAR_REEMPLAZAR],
                    onClick = onReemplazar
                )
            }
        },
        confirmButton = {},
        dismissButton = { BotonSuave(t[Clave.ACCION_CANCELAR], onClick = onCancelar) }
    )
}
