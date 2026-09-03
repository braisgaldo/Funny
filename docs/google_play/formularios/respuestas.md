# Respuestas de los formularios de Play Console

Ya decididas, para no improvisar delante del formulario. Cada una con su motivo,
porque algunas hay que poder defenderlas.

---

## Ficha de Play

| Campo | Valor |
|---|---|
| Nombre de la app | `Funny` |
| Descripción corta | `ficha/<idioma>/corta.txt` |
| Descripción completa | `ficha/<idioma>/larga.txt` |
| Categoría | Juegos → **Juegos de mesa** |
| Etiquetas | juegos de fiesta, juegos de mesa, multijugador local, preguntas y respuestas, juegos de palabras |
| ¿Es un juego? | Sí — tipo de aplicación **Juego**, no «Aplicación» |
| Correo de contacto | GhatoStudioOfficial@gmail.com |
| Sitio web | https://braisgaldo.github.io/Funny/ |
| Política de privacidad | https://braisgaldo.github.io/Funny/PRIVACIDAD |
| Teléfono de contacto | *no se facilita* |

Las etiquetas son **hasta cinco de la lista cerrada de Play**: no se escriben, se
eligen de su selector, así que los nombres reales pueden variar. El porqué de la
categoría y el orden de prioridad de las etiquetas están en la guía de
publicación, apartado «Categoría y etiquetas»: se resume en que ninguno de los
dieciocho juegos manda lo suficiente para llevarse la categoría, y lo común a
todos es el tablero.

---

## Seguridad de los datos

| Pregunta | Respuesta |
|---|---|
| ¿Tu app recoge o comparte alguno de los tipos de datos de usuario obligatorios? | **No** |
| ¿Se cifran todos los datos de usuario en tránsito? | *no aplica* |
| ¿Ofreces a los usuarios una forma de solicitar que se eliminen sus datos? | *no aplica* |
| ¿Ha sido revisada tu app de forma independiente...? | No |

### Por qué se puede responder «No» sin matices

**La app no declara el permiso `INTERNET`.** Sin ese permiso, Android impide
cualquier conexión de red. No es una promesa: es el sistema operativo el que lo
bloquea, y se puede comprobar desde el propio móvil en Ajustes → Aplicaciones →
Funny → Permisos.

Consecuencias que interesan a este formulario:

- No hay analítica, telemetría, informes de fallos remotos ni identificadores
  publicitarios. No es que se haya decidido no usarlos: **no podrían funcionar**.
- No hay servidor propio, ni cuentas, ni registro (ADR-0002).
- Lo que la app guarda —ajustes, nombres de equipo, mejor marca del solitario—
  vive en el almacenamiento privado del dispositivo y se mueve con un fichero que
  el usuario controla.

### Si preguntan por el enlace de la donación

Es un enlace externo que abre el navegador del sistema con Custom Tabs. **La app
no realiza la petición, no recibe respuesta y no sabe si el usuario ha llegado a
la página.** Por eso, al volver, el mensaje es «gracias por pasarte por ahí» y no
«gracias por tu donación».

### Si preguntan por el modo de varios móviles

Los dispositivos se hablan **directamente** entre ellos por Bluetooth o Wi-Fi
Direct, sin pasar por internet ni por ningún servidor. Lo que se intercambia es
únicamente lo del juego: nombres de participante, posición en el tablero, la carta
que toca y las pulsaciones. El protocolo no tiene **ningún** mensaje capaz de leer
datos del dispositivo, y eso está fijado en `dominio/salon/Protocolo.kt`, que es
un conjunto cerrado de **siete** tipos de mensaje.

El único texto que escribe una persona y llega a otro móvil es **su nombre**
(`Hola` al conectar, y `RENOMBRAR` si lo cambia). No hay chat, ni voz, ni envío
de imágenes o de audio: **los dibujos de Pinturillo no viajan**, se quedan en el
móvil que dibuja.

Para este formulario lo que importa es que **ese nombre no llega a ninguna parte
más**: no hay servidor, no lo recibe el desarrollador y no sale de la habitación.
Si el formulario pregunta si la app «comparte datos con terceros», la respuesta
es no; un móvil de la misma mesa no es un tercero, es la otra mitad de la
partida.

### Lo que se comprobó, con qué y cuándo

Comprobado el 3 de septiembre de 2026 **sobre el APK firmado**, no sobre el
código fuente, que es lo que se puede repetir y lo que verá quien descompile:

| Qué | Cómo se comprobó | Resultado |
|---|---|---|
| Permiso `INTERNET` | buscado en el `AndroidManifest.xml` binario del APK | **no está** |
| Librerías de analítica, fallos o anuncios | 202 artefactos del `releaseRuntimeClasspath` | **ninguna** |
| Librerías de pagos | tarea `verificarSinFacturacion`, enganchada a `check` | **ninguna** |
| Lectura de ubicación | buscado `getLastLocation`, `requestLocationUpdates`, latitud, longitud | **ninguna llamada** |

De Google solo entran cuatro artefactos, y los cuatro son para el salón:
`play-services-nearby`, `-base`, `-basement` y `-tasks`.

### Dos cosas que aparecen al mirar el APK y conviene saber

**`ACCESS_NETWORK_STATE` está en el APK y no lo declara este proyecto.** Lo añade
`play-services-nearby` al fusionar los manifiestos. Solo permite **consultar** si
hay red; sin `INTERNET` no sirve para transmitir nada. Se deja porque quitárselo
a la librería que sostiene el salón es arriesgado y no gana nada, pero si alguien
mira los permisos y pregunta, esta es la respuesta.

**Había un servicio de notificaciones de exposición, y se ha quitado.**
`play-services-nearby` trae
`com.google.android.gms.nearby.exposurenotification.WakeUpService` —el del
rastreo de contactos de la covid— en la misma librería que Nearby Connections,
aunque no tenga nada que ver. La app no usa esa API, así que ahora el manifiesto
lo elimina con `tools:node="remove"`. No cambiaba nada de lo que se responde en
este formulario, porque sin usarse no se ejecuta; se quita porque en una app cuyo
argumento es que no recoge datos, un servicio de rastreo de contactos dentro del
APK es algo que hay que explicar cada vez. Verificado: la cadena
`exposurenotification` ya no aparece en el APK, y el salón sigue anunciándose en
el móvil.

---

## Clasificación de contenido (IARC)

Categoría del cuestionario: **Juego**.

| Pregunta | Respuesta |
|---|---|
| Violencia (realista, sangre, contra personajes) | Ninguna |
| Contenido sexual o desnudez | Ninguno |
| Lenguaje soez o vulgar | Ninguno |
| Referencias a drogas, alcohol o tabaco | **Ninguna** |
| Juegos de azar (simulados o con dinero) | Ninguno |
| Miedo, horror o contenido perturbador | Ninguno |
| **Compras integradas** | **No** |
| **Publicidad** | **No** |
| Interacción entre usuarios | **No** |
| Compartir la ubicación con otros usuarios | **No** |
| Compartir información personal | **No** |
| Contenido generado por usuarios | **No** |
| Humor escatológico (eructos, ventosidades, vómitos) | **No** |
| Esvásticas o símbolos nazis | **No** |
| Contenido contra la identidad nacional de Corea del Sur | **No** |
| Promoción de actos terroristas | **No** |
| Delitos realistas o técnicas delictivas | **No** |

Cuando la respuesta a la primera de esas es «No», la pregunta de seguimiento
—*qué* funciones corporales se usan— **no aparece**. No hay que marcar ninguna
de sus cuatro opciones.

### Cómo se comprobaron, y no de memoria

Las respuestas de arriba salen de buscar en los **treinta y seis mazos** de los
dos idiomas, no de recordar qué se escribió. El barrido está en el historial y se
puede repetir; esto es lo que dio:

| Se buscó | Aciertos | Qué eran |
|---|---|---|
| eructo, burp | 0 | — |
| pedo, flatulencia, ventosidad, fart | 0 | — |
| caca, heces, excremento, poop | 0 | — |
| moco, mucosidad, snot | 0 | — |
| orina, mear, pee, urin | 0 | los 6 aparentes eran «d**urin**g» y «end**urin**g» |
| vómito, vomitar, vomit | 2 | «Los caballos no pueden vomitar», carta de verdadero o falso |
| nazi, esvástica, Hitler, Reich, fascismo | 0 | los 44 aparentes eran `SS` dentro de «pa**ss**», «ble**ss**», «ba**ss**» |
| robo, ladrón, secuestro, hackear, falsificar | 0 | — |
| drogas, cocaína, cannabis | 0 | — |

El mazo de más riesgo para la pregunta escatológica es **🔊 Sonidos**, porque son
treinta y dos cartas de imitar ruidos con la boca. Se leyeron las treinta y dos:
una moto que no arranca, un microondas, un globo deshinchándose, un fax, un
detector de humos con la pila baja. Las dos más cercanas al límite son «imita a
alguien sorbiendo sopa muy caliente» y «imita el ruido de un estómago con mucha
hambre»: son sonidos del cuerpo, pero no están en la lista del cuestionario
—mucosidad, flatulencia, excrementos, vómito, micción— y no es humor
escatológico. Si algún día se añade una carta de eructar, **esta respuesta pasa a
Sí** y hay que rehacer el cuestionario.

Los tres aciertos de «Corea» son opciones de respuesta de preguntas de cultura
general (de dónde viene el taekwondo, y similares). No hay ningún hecho histórico
distorsionado.

### Referencias a hechos reales, que no cambian la clasificación

El mazo **📅 ¿Cuándo?** pide acertar el año de hechos históricos, y entre ellos
hay tres tragedias reales: la bomba de Hiroshima (los dos idiomas), los atentados
del 11 de septiembre y los del 11-M (solo en castellano).

Para el cuestionario son **referencias**, no promoción: la carta dice el hecho y
el año, sin descripción, sin imagen y sin juicio. Por eso «promoción de actos
terroristas» es **No**.

Cosa distinta, y no es una decisión de clasificación sino de gusto: son tres
cartas que pueden cortar el ambiente en una mesa de fiesta, y en el caso del 11-M
hay quien las tenga cerca. Quitarlas no cambiaría nada del juego —el mazo tiene
muchas más— y dejarlas tampoco incumple nada. Está anotado para decidirlo a
conciencia y no por descuido.

### Las tres que conviene poder explicar

**Drogas y alcohol: ninguna.** Los 70 desafíos están revisados uno a uno y ninguno
usa alcohol como prueba. Es una decisión, no una casualidad: un juego de fiesta que
manda beber cambia de clasificación y de público.

**Juegos de azar: ninguno.** Hay un dado, pero no reparte premios ni hay nada que
apostar. Un dado no es juego de azar en el sentido del cuestionario.

**Ubicación: no.** La app declara `ACCESS_FINE_LOCATION` y
`ACCESS_COARSE_LOCATION` **solo hasta Android 12** (`maxSdkVersion="32"`), porque
es el sistema el que los exige para buscar dispositivos Bluetooth cercanos. La app
**no lee la ubicación, no la guarda y no podría enviarla**; el permiso de Wi-Fi
cercano lleva `neverForLocation`. En Android 13 y posteriores no se pide ninguno.

Si el formulario pregunta si la app «usa» o «comparte» la ubicación, la respuesta
honesta es **no**, y esta es la explicación si alguien la pide.

**Interacción entre usuarios: no.** No hay chat, ni perfiles, ni contenido
compartido, ni forma de que alguien de fuera contacte con quien juega. Los nombres
de equipo se escriben en el móvil y solo viajan a los móviles de la misma mesa,
que están en la misma habitación.

Clasificación esperada: **PEGI 3 / ESRB Everyone**. Si el cuestionario devolviera
algo más alto, revisar qué pregunta se ha entendido mal antes de aceptarlo.

---

## Público objetivo y contenido

| Pregunta | Respuesta |
|---|---|
| Grupos de edad objetivo | **13+** |
| ¿Tu app está dirigida principalmente a menores de 13 años? | **No** |
| ¿Solicitar acceso al programa Familias? | **No** |
| ¿Contiene anuncios? | **No** |

**Por qué 13+ y no todos los públicos**: por prudencia con las preguntas de cultura
general y algún desafío que pide cierta soltura, no por contenido problemático. La
app es apta para jugar en familia.

**Por qué no el programa Familias**: entrar añade requisitos de publicidad
certificada y políticas asociadas que no aportan nada a una app **sin anuncios**.

---

## Precios y distribución

| Campo | Valor |
|---|---|
| ¿Gratis o de pago? | **Gratis** (y no se puede cambiar a de pago después) |
| ¿Contiene compras integradas? | **No** |
| ¿Contiene anuncios? | **No** |
| Países | todos los disponibles |
| Consentimiento de directrices para contenido de EEUU | aceptado |

**Coherencia comprobable**: no hay ninguna librería de facturación en el binario.
La verifica una tarea de Gradle enganchada a `check`, que hoy informa de **181
artefactos revisados, ninguna librería de pagos**. Cualquiera puede descompilar el
APK y confirmarlo. El razonamiento completo está en el ADR-0004.
