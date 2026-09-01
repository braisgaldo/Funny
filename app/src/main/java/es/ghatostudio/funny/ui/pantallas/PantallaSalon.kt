package es.ghatostudio.funny.ui.pantallas

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ghatostudio.funny.dominio.Pantalla
import es.ghatostudio.funny.dominio.salon.RolSalon
import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.plataforma.TransporteNearby
import es.ghatostudio.funny.plataforma.TransporteSalon
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.SalonViewModel
import es.ghatostudio.funny.ui.comun.AREA_TACTIL_MINIMA
import es.ghatostudio.funny.ui.comun.BotonGrande
import es.ghatostudio.funny.ui.comun.BotonSuave
import es.ghatostudio.funny.ui.comun.Cabecera
import es.ghatostudio.funny.ui.comun.Ficha
import es.ghatostudio.funny.ui.comun.FondoFunny
import es.ghatostudio.funny.ui.comun.Tarjeta
import es.ghatostudio.funny.ui.i18n.ClavePlural
import es.ghatostudio.funny.ui.i18n.textos
import es.ghatostudio.funny.ui.tema.Acento
import es.ghatostudio.funny.ui.tema.Exito
import es.ghatostudio.funny.ui.tema.Fallo
import es.ghatostudio.funny.ui.tema.Primario
import es.ghatostudio.funny.ui.tema.SuperficieAlta
import es.ghatostudio.funny.ui.tema.TextoFuerte
import es.ghatostudio.funny.ui.tema.TextoTenue
import es.ghatostudio.funny.ui.tema.paleta

private const val MAXIMO_NOMBRE_SALON = 14

/**
 * El salón: elegir si este móvil hace de mesa o de mando, y montar la conexión.
 *
 * Todo lo que pasa aquí es entre los móviles de la habitación: Bluetooth y wifi
 * directo, sin router, sin datos y sin ninguna cuenta. La pantalla lo dice
 * explícitamente porque «conectar varios móviles» hace pensar en internet.
 */
@Composable
fun PantallaSalon(vm: JuegoViewModel, salon: SalonViewModel) {
    val t = textos()
    val contexto = LocalContext.current
    val estadoSalon = salon.estado
    var nombre by remember { mutableStateOf(estadoSalon.miNombre) }
    var rolPedido by remember { mutableStateOf<RolSalon?>(null) }

    val lanzadorPermisos =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { concedidos ->
            val todos = concedidos.values.all { it }
            if (!todos) {
                salon.limpiarFallo()
                vm.avisar(t[Clave.SALON_ERROR_PERMISOS])
                return@rememberLauncherForActivityResult
            }
            // Con los permisos ya dados, se reintenta el rol que se había pedido.
            rolPedido?.let { rol -> abrirSalon(rol, contexto, salon, vm, nombre) }
        }

    // Un mando se presenta en cuanto la conexión está lista, para que el hub
    // sepa su nombre de verdad y no el que puso Android al endpoint.
    LaunchedEffect(estadoSalon.conectado) {
        if (estadoSalon.conectado && estadoSalon.esMando) salon.presentarse()
    }

    FondoFunny(tinte = Primario) {
        Column(Modifier.fillMaxSize()) {
            Cabecera(
                titulo = t[Clave.SALON_TITULO],
                subtitulo = t[Clave.SALON_SUBTITULO],
                onVolver = {
                    salon.cerrar()
                    vm.ir(Pantalla.INICIO)
                },
            )

            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                estadoSalon.fallo?.let { causa ->
                    TarjetaDeFallo(
                        causa = causa,
                        permisosQueFaltan = estadoSalon.permisosQueFaltan,
                        onPedirPermisos = {
                            lanzadorPermisos.launch(estadoSalon.permisosQueFaltan.toTypedArray())
                        },
                    )
                }

                when (estadoSalon.rol) {
                    null ->
                        ElegirRol(
                            nombre = nombre,
                            onNombre = { nombre = it },
                            onRol = { rol ->
                                rolPedido = rol
                                abrirSalon(rol, contexto, salon, vm, nombre)
                            },
                        )

                    RolSalon.HUB -> VistaDeHub(vm, salon)
                    RolSalon.MANDO -> VistaDeMando(salon)
                }

                if (estadoSalon.rol != null) {
                    BotonSuave(t[Clave.SALON_SALIR], color = Fallo) { salon.cerrar() }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

/** Crea el transporte adecuado y abre el salón con el rol elegido. */
private fun abrirSalon(
    rol: RolSalon,
    contexto: android.content.Context,
    salon: SalonViewModel,
    vm: JuegoViewModel,
    nombre: String,
) {
    val nombreLimpio = nombre.trim().ifBlank { NOMBRE_ANONIMO }
    val transporte =
        TransporteNearby(
            context = contexto,
            rol = if (rol == RolSalon.HUB) TransporteNearby.Rol.HUB else TransporteNearby.Rol.MANDO,
        )
    if (rol == RolSalon.HUB) {
        salon.abrirComoHub(transporte, nombreLimpio, vm)
    } else {
        salon.abrirComoMando(transporte, nombreLimpio)
    }
}

/** Nombre de emergencia si alguien no escribe nada. No es texto de interfaz. */
private const val NOMBRE_ANONIMO = "?"

@Composable
private fun ElegirRol(
    nombre: String,
    onNombre: (String) -> Unit,
    onRol: (RolSalon) -> Unit,
) {
    val t = textos()

    Tarjeta(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(18.dp)) {
            Text(
                t[Clave.SALON_COMO_FUNCIONA],
                style = MaterialTheme.typography.titleMedium,
                color = TextoFuerte,
            )
            Spacer(Modifier.height(8.dp))
            Punto(t[Clave.AYUDA_VARIOS_MOVILES_1])
            Punto(t[Clave.AYUDA_VARIOS_MOVILES_2])
            Punto(t[Clave.AYUDA_VARIOS_MOVILES_3])
            Spacer(Modifier.height(6.dp))
            Text(
                t[Clave.SALON_SIN_RED],
                style = MaterialTheme.typography.labelLarge,
                color = Exito,
            )
        }
    }

    Spacer(Modifier.height(14.dp))

    Tarjeta(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(18.dp)) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { if (it.length <= MAXIMO_NOMBRE_SALON) onNombre(it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text(
                        t[Clave.SALON_TU_NOMBRE],
                        color = TextoTenue,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                textStyle = MaterialTheme.typography.titleMedium,
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextoFuerte,
                        unfocusedTextColor = TextoFuerte,
                        focusedBorderColor = Primario,
                        unfocusedBorderColor = SuperficieAlta,
                        cursorColor = Primario,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
            )
        }
    }

    Spacer(Modifier.height(14.dp))

    TarjetaDeRol(
        emoji = "📺",
        titulo = t[Clave.SALON_CREAR],
        detalle = t[Clave.SALON_CREAR_DETALLE],
        onPulsar = { onRol(RolSalon.HUB) },
    )
    Spacer(Modifier.height(10.dp))
    TarjetaDeRol(
        emoji = "📱",
        titulo = t[Clave.SALON_UNIRSE],
        detalle = t[Clave.SALON_UNIRSE_DETALLE],
        onPulsar = { onRol(RolSalon.MANDO) },
    )
}

@Composable
private fun TarjetaDeRol(
    emoji: String,
    titulo: String,
    detalle: String,
    onPulsar: () -> Unit,
) {
    Tarjeta(
        modifier = Modifier.fillMaxWidth().clickable { onPulsar() },
        borde = Primario.copy(alpha = 0.35f),
    ) {
        Row(
            Modifier.fillMaxWidth().heightIn(min = AREA_TACTIL_MINIMA).padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(emoji, style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.width(16.dp))
            Column {
                Text(titulo, style = MaterialTheme.typography.titleLarge, color = TextoFuerte)
                Spacer(Modifier.height(4.dp))
                Text(detalle, style = MaterialTheme.typography.bodyMedium, color = TextoTenue)
            }
        }
    }
}

/** El hub: quién se ha conectado y el botón de empezar. */
@Composable
private fun VistaDeHub(vm: JuegoViewModel, salon: SalonViewModel) {
    val t = textos()
    val p = paleta()
    val estadoSalon = salon.estado

    Tarjeta(modifier = Modifier.fillMaxWidth(), borde = Primario) {
        Column(Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📺", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        t[Clave.SALON_HUB_TITULO],
                        style = MaterialTheme.typography.titleLarge,
                        color = Primario,
                    )
                    Text(
                        t.plural(ClavePlural.DISPOSITIVOS, estadoSalon.dispositivos.size),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoTenue,
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            if (estadoSalon.dispositivos.isEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(20.dp).height(20.dp),
                        color = Primario,
                        strokeWidth = 2.dp,
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        t[Clave.SALON_HUB_ESPERANDO],
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoTenue,
                    )
                }
            } else {
                estadoSalon.dispositivos.forEachIndexed { indice, dispositivo ->
                    Row(
                        Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Ficha(
                            color = p.colorDeParticipante(indice + 1),
                            emoji = "📱",
                            tamano = 32,
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            dispositivo.nombre.ifBlank { t[Clave.SALON_SIN_NOMBRE] },
                            style = MaterialTheme.typography.titleMedium,
                            color = TextoFuerte,
                            modifier = Modifier.weight(1f),
                        )
                        Text("✓", style = MaterialTheme.typography.titleMedium, color = Exito)
                    }
                }
            }
        }
    }

    Spacer(Modifier.height(14.dp))

    BotonGrande(
        texto = t[Clave.SALON_HUB_EMPEZAR],
        onClick = {
            vm.empezarPartida()
            salon.difundirSalon()
            salon.difundirVistas()
        },
        habilitado = estadoSalon.dispositivos.isNotEmpty(),
    )
}

/** El mando: buscando, conectado o esperando a que empiece la partida. */
@Composable
private fun VistaDeMando(salon: SalonViewModel) {
    val t = textos()
    val estadoSalon = salon.estado

    if (estadoSalon.conectado) {
        Tarjeta(modifier = Modifier.fillMaxWidth(), borde = Exito) {
            Column(
                Modifier.fillMaxWidth().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("✅", style = MaterialTheme.typography.displayMedium)
                Spacer(Modifier.height(8.dp))
                Text(
                    t[Clave.SALON_CLIENTE_CONECTADO],
                    style = MaterialTheme.typography.titleLarge,
                    color = Exito,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    t[Clave.SALON_CLIENTE_ESPERA],
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextoTenue,
                    textAlign = TextAlign.Center,
                )
            }
        }
        return
    }

    Tarjeta(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (estadoSalon.buscando) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(20.dp).height(20.dp),
                        color = Primario,
                        strokeWidth = 2.dp,
                    )
                    Spacer(Modifier.width(12.dp))
                }
                Text(
                    t[Clave.SALON_CLIENTE_BUSCANDO],
                    style = MaterialTheme.typography.titleMedium,
                    color = TextoFuerte,
                )
            }

            Spacer(Modifier.height(12.dp))

            if (estadoSalon.encontrados.isEmpty()) {
                Text(
                    t[Clave.SALON_CLIENTE_SIN_SALONES],
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoTenue,
                )
            } else {
                estadoSalon.encontrados.forEach { mesa ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(min = AREA_TACTIL_MINIMA)
                            .clickable { salon.conectarA(mesa.id) }
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("📺", style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.width(12.dp))
                        Text(
                            mesa.nombre.ifBlank { t[Clave.SALON_SIN_NOMBRE] },
                            style = MaterialTheme.typography.titleMedium,
                            color = TextoFuerte,
                            modifier = Modifier.weight(1f),
                        )
                        Text("›", style = MaterialTheme.typography.headlineMedium, color = Primario)
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaDeFallo(
    causa: TransporteSalon.Causa,
    permisosQueFaltan: List<String>,
    onPedirPermisos: () -> Unit,
) {
    val t = textos()
    val mensaje =
        when (causa) {
            TransporteSalon.Causa.PERMISOS -> t[Clave.SALON_ERROR_PERMISOS]
            TransporteSalon.Causa.BLUETOOTH -> t[Clave.SALON_ERROR_BLUETOOTH]
            TransporteSalon.Causa.UBICACION -> t[Clave.SALON_ERROR_UBICACION]
            TransporteSalon.Causa.SERVICIOS -> t[Clave.SALON_ERROR_SERVICIOS]
            TransporteSalon.Causa.DESCONOCIDA -> t[Clave.SALON_DESCONECTADO]
        }

    Tarjeta(modifier = Modifier.fillMaxWidth(), borde = Acento) {
        Column(Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("⚠", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.width(12.dp))
                Text(
                    mensaje,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextoFuerte,
                    modifier = Modifier.weight(1f),
                )
            }

            if (causa == TransporteSalon.Causa.PERMISOS && permisosQueFaltan.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                // Se explica por qué Android pide esto antes de pedirlo: es la
                // diferencia entre un permiso que se concede y uno que se niega.
                Text(
                    t[Clave.SALON_PERMISOS_EXPLICACION],
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoTenue,
                )
                Spacer(Modifier.height(12.dp))
                Box(Modifier.fillMaxWidth()) {
                    BotonGrande(t[Clave.SALON_PEDIR_PERMISOS], onClick = onPedirPermisos)
                }
            }
        }
    }
}
