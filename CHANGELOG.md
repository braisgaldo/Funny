# Registro de cambios

Todos los cambios importantes de Funny se anotan aquí.

El formato sigue [Keep a Changelog](https://keepachangelog.com/es-ES/1.1.0/) y las
versiones, [Versionado Semántico](https://semver.org/lang/es/).

`versionCode` se deriva de la versión con la fórmula
`major * 10_000 + minor * 100 + patch`, documentada en `app/build.gradle.kts`.

---

## [Sin publicar]

Nada todavía.

---

## [1.0.1] — pendiente de publicación

**Misma app que la 1.0.0. Lo que cambia es el número, y no por capricho.**

Play **consume el `versionCode` en cuanto se sube un artefacto**, aunque no se
publique en ningún canal y aunque se descarte después. El 10000 se gastó en la
primera subida, y al subir el siguiente AAB la consola contesta «el código de
versión 10000 ya se ha usado».

La fórmula de este proyecto ata el `versionCode` al `versionName`
(`major * 10_000 + minor * 100 + patch`), así que la forma de obtener un código
nuevo es subir la versión: **1.0.1 → 10001**. La alternativa —dejar el
`versionName` en 1.0.0 y sumarle un desplazamiento al código— rompería la
propiedad que hace útil la fórmula: que de un `versionCode` se lee la versión y
al revés.

Que la primera versión que llegue a la gente sea una 1.0.1 no es un problema:
la 1.0.0 no llegó a publicarse y nadie la tiene instalada. Todo lo que se
describe abajo va dentro de esta.

**Para la próxima:** un `versionCode` gastado no se recupera. Conviene subir a
Play solo artefactos que se pretenda publicar, y llevar la cuenta: cada intento
descartado cuesta un número.

### Cambiado

- **Apunta al nivel 36 de API** (Android 16), antes al 35. No es una mejora
  buscada: Play **rechaza** el AAB que apunte a menos de 36. Arrastró tres cosas:
  - **AGP 8.7.3 → 8.10.1.** La 8.7 solo está probada hasta `compileSdk` 35. Es
    la última rama que funciona con Gradle 8.11.1, así que el wrapper no se
    toca.
  - **Robolectric 4.14.1 → 4.15.1**, y aun así **fijado al nivel 35** en
    `app/src/test/resources/robolectric.properties`. La 4.15.1 es la última
    publicada y su tope sigue siendo el 35: los cuatro tests que necesitan un
    `Context` fallaban en la configuración con `targetSdkVersion=36 >
    maxSdkVersion=35`. Ninguno depende de una API que cambie entre el 35 y el
    36. Se quita el fichero en cuanto salga una Robolectric que acepte el 36.
  - **SDK 36 y build-tools 36** instalados en la máquina de desarrollo.

  Lo que el 36 cambia de comportamiento —sobre todo que *edge to edge* deja de
  ser opcional— ya estaba cubierto: `MainActivity` llama a `enableEdgeToEdge()`
  y las pantallas usan `safeDrawingPadding()`. **Y no se ha podido verificar en
  un Android 16**: el único dispositivo a mano tiene Android 13, donde el
  binario del 36 se comporta igual que el del 35. Lo que sí se hizo fue jugar
  una tanda entera del reto en solitario con el APK del 36 instalado.

- **Símbolos de depuración del código nativo** en el AAB
  (`debugSymbolLevel = "SYMBOL_TABLE"`), que es la otra cosa que reclama la
  consola. El código nativo no es de este proyecto: son
  `libandroidx.graphics.path.so` (Compose UI) y
  `libdatastore_shared_counter.so` (DataStore), unos 10 KB cada una.
  **Extraerlos necesita el NDK**, porque AGP usa su `objcopy`; en una máquina
  sin NDK la tarea avisa con «Unable to strip the following libraries» y el AAB
  sale sin símbolos. El AAB del workflow de release sí los llevará, porque el
  runner de Ubuntu trae NDK. Es una recomendación de Play, no un requisito.

---

## [1.0.0] — subida a Play, nunca publicada

Primera versión publicable. Parte de una app anterior llamada «Fiestón» —un juego
por equipos, en castellano, con seis pruebas y un solo móvil— y la convierte en
otra cosa.

### Añadido

**Juegos y modos**

- **Doce pruebas nuevas**, de seis a dieciocho. A las de la app anterior
  —Mímica, Pinturillo, ¿Cuándo?, Preguntas y Reto rápido— se suman Tabú, Emojis,
  ¿Te lo crees?, Trabalenguas, Ordena, Canta, Desafío, Refranes,
  ¿Antes o después?, Anagramas, Acentos, Sonidos y Encadenados.
- **Modo individual** (2–8 jugadores) sin equipos.
- **Reto en solitario** (1 jugador): sin tablero, una tanda de pruebas seguidas
  con puntuación y mejor marca personal. Los juegos que necesitan público —los
  que hay que adivinar— quedan fuera automáticamente.
- **Cuatro modalidades de partida**: rápida (12 casillas / 6 pruebas), normal
  (20 / 10), extrema (32 / 16) y una a medida en la que se eligen los dos números.
- **Elegir qué juegos entran** en la partida, uno a uno.
- **3.194 cartas** escritas para la app: 1.668 en castellano y 1.526 en inglés.

**Salón multidispositivo**

- Hasta **cinco móviles** en la misma partida: uno hace de mesa y los demás se
  conectan por Bluetooth o Wi-Fi Direct, **sin internet y sin router**.
- La palabra secreta del tabú llega **solo** al móvil de quien actúa.
- En las casillas de «juegan todos», cada uno responde en su móvil a la vez.
- Si no hay Google Play Services, la app lo dice y sigue funcionando con un móvil.

**Apariencia e idiomas**

- **Seis temas**: tres oscuros (Fiesta, Neón, Medianoche) y tres claros (Papel,
  Menta, Atardecer), más «seguir al sistema». Todos con contraste AA verificado
  por tests, no supuesto.
- **Trece idiomas** completos, 413 claves cada uno: inglés, castellano, francés,
  alemán, chino simplificado, japonés, ruso, italiano, griego, árabe, gallego,
  catalán y euskera.
- **Árabe en RTL**, con la dirección de lectura siguiendo al idioma elegido y no
  al del sistema.
- Plurales con las reglas de la ICU: tres formas en ruso y seis en árabe, no
  «uno o varios».

**Ajustes, datos y ayuda**

- Pantalla de ajustes como punto único de entrada a tema, idioma, modalidad,
  ritmo, juegos activos, sonido, vibración, animaciones, exportar e importar,
  compartir, apoyar el desarrollo, ayuda y «Acerca de».
- **Exportación e importación** a un fichero `.funny.bak` con cabecera de esquema,
  validación, **copia de seguridad automática antes de importar** y elección entre
  fusionar y reemplazar.
- **Tour guiado** interno que describe los dieciocho juegos y los tres modos.
- «Acerca de» con versión, número de compilación, fecha, hash del commit,
  licencias y contacto.

**Donación**

- Bottom sheet con una taza de café dibujada en Compose —vectorial, no un PNG— con
  vapor animado, entrada escalonada que respeta «reducir animaciones», y código QR
  generado **en local y sin red**.
- **No desbloquea nada.** Se abre en el navegador del sistema con Custom Tabs,
  nunca en un WebView.

### Cambiado

- **Nombre**: Fiestón → **Funny**.
- **Identificador**: `com.fieston` → `es.ghatostudio.funny`. Es una app distinta a
  efectos de tienda; no hay actualización desde la anterior.
- **`minSdk` 26** (Android 8.0).
- Las reglas del juego salen del ViewModel a un **motor de estado puro**
  (`MotorJuego`), sin `Context` ni Compose, que es lo que permite probarlas sin
  emulador y lo que hace que la mesa y un móvil suelto apliquen exactamente las
  mismas transiciones.
- Los colores dejan de estar sueltos por las pantallas: ahora son **tokens** en un
  único fichero, y las pantallas los leen del tema activo.
- Los textos dejan de estar escritos en las pantallas: ahora son **claves** de un
  catálogo, y hay una verificación de Gradle que falla si aparece una cadena a
  fuego.

### Corregido

#### Lo que encontró el primer arranque en un móvil de verdad

Los cuatro salieron al instalar la app en un **SM-S908U con Android 13**, con las
164 pruebas de entonces en verde, ktlint limpio y el lint de Android sin errores.
**Los dos primeros impedían que la app arrancara**, en depuración y en release por
igual: compilaba y se cerraba al abrirla.

- **El tema no descendía de AppCompat.** `themes.xml` heredaba de
  `android:Theme.Material.NoActionBar`, que es el del sistema. `MainActivity` es
  una `AppCompatActivity` —hace falta para la preferencia de idioma por app— y
  AppCompat comprueba el tema en `setContentView`: `IllegalStateException: You
  need to use a Theme.AppCompat theme`.
- **El ViewModel no tenía el constructor que Android busca.**
  `AndroidViewModelFactory` hace `getConstructor(Application::class.java)`, y los
  parámetros por defecto de Kotlin **no** producen esa firma en el bytecode:
  `NoSuchMethodException`. Se ha añadido un constructor secundario explícito.
- **El título se escribía «YNNUF» en árabe.** Las letras de «FUNNY» se pintan una
  a una para darle a cada una su color, y un `Row` en RTL coloca sus hijos de
  derecha a izquierda. Un nombre propio no se espeja: ahora ese bloque fuerza LTR.
- **El tema en castellano se llamaba «Fiestón»**, el nombre anterior de la app. Un
  resto de un renombrado que no da ningún error, solo queda raro.

Ninguna de las 164 pruebas podía verlos: ninguna instanciaba la Activity ni el
ViewModel, y ninguna renderiza en RTL. Ahora hay 174 y cada uno tiene la suya.

#### Lo que encontró la segunda pasada por el móvil

- **La fila «a mi manera» anunciaba los números de otra modalidad.** El resumen
  de las cuatro se enseña a la vez, y el de la personalizada leía
  `ajustes.casillas`, que ya venía resuelto por la modalidad elegida: con la
  extrema puesta decía «32 casillas · 16 pruebas» en lugar de las 20 y 10 que
  había guardadas. Ahora hay `casillasAMedida` y `pruebasAMedida`, que son los
  números a mano sin pasar por la elección, con su prueba.
- **El comentario del mazo de ¿Antes o después? describía otra pantalla.** Decía
  que los dos años se ven «al resolver», y la pantalla los enseña desde el
  principio como tema de la carta. Se ha corregido el comentario, no la pantalla:
  ver los dos años sin saber de quién es cada uno es justo la dificultad que
  tiene que tener el juego.

#### Lo que encontró la tercera pasada por el móvil

- **El salón no reaccionaba al encender el Bluetooth.** La comprobación corría
  una sola vez al abrir el salón y, si fallaba, no se llamaba a `anunciarse`.
  Resultado: la app decía «enciende el Bluetooth», y encenderlo no servía de
  nada; había que salir de la pantalla y volver. Solo la causa de permisos tenía
  salida, con su botón. Ahora la pantalla reintenta al recuperar el foco, que es
  exactamente cuando alguien vuelve de los ajustes del sistema. Probado por ese
  camino en el móvil: con el Bluetooth apagado sale el aviso, se enciende en
  Ajustes, se vuelve, y el hub queda anunciándose sin aviso.
- **Fuera un servicio de rastreo de contactos de la covid.**
  `play-services-nearby` trae `exposurenotification.WakeUpService` en la misma
  librería que Nearby Connections, y acababa declarado en el manifiesto del APK
  aunque la app no use esa API. No hacía nada, pero en una app cuyo argumento es
  que no recoge datos, un servicio de rastreo de contactos dentro del APK es algo
  que hay que explicar cada vez. Se elimina con `tools:node="remove"`.
  Verificado en el APK firmado, y el salón sigue funcionando.

#### Reglas de R8 que faltaban

`proguard-rules.pro` seguía siendo el de la app anterior y afirmaba que «el
proyecto no usa reflexión». Era falso:

- Los ViewModel se instancian por reflexión, y R8 los ofuscaba.
- **Y este es el silencioso**: los ajustes y la copia de seguridad guardan los
  enums por su **nombre** y los releen comparando `it.name`. Con R8 renombrando
  las constantes, el `.funny.bak` deja de ser texto legible y —peor— los nombres
  cambian de una compilación a otra: **cada actualización le habría borrado los
  ajustes a quien la instalase**, y las copias viejas no se podrían importar.
  Nadie lo habría relacionado con R8.

Verificado en el binario firmado: los 22 nombres de enum que se persisten
sobreviven, y no hay ni un byte de librería de facturación en el DEX.

#### El resto

Cosas que estaban mal y se han arreglado por el camino. Se anotan porque algunas
eran de verdad:

- **Dos participantes con el mismo identificador y el mismo color** al añadirlos de
  golpe: se leía la lista guardada en lugar de la que se estaba construyendo. Con
  identificadores repetidos, renombrar o borrar uno afectaba a los dos.
- **Participantes fantasma en el salón**: la mesa se rellenaba hasta el mínimo del
  modo antes de que nadie se hubiera conectado.
- **En el salón, cada mando quedaba asociado al móvil de la mesa** en lugar de a sí
  mismo, porque un parámetro tapaba a una propiedad del mismo nombre.
- **Seis colores de juego daban texto blanco ilegible**: la regla elegía la tinta
  por luminancia en lugar de comparar los dos contrastes.
- **Dos colores por debajo del mínimo AA**: blanco sobre el rosa de Fiesta (3,37:1)
  y sobre el de Neón (3,50:1). Y dos tintas del lienzo de dibujo, el amarillo
  (1,37:1) y el naranja (2,33:1).
- Un texto de la donación usaba la palabra «desbloquea», que está prohibida por el
  propio criterio del proyecto. Lo cazó el test.
- Erratas de contenido: «scegle» → «sceglie» en italiano (cuatro veces), una carta
  de mímica en inglés con caracteres chinos pegados, y una pregunta ambigua sobre
  saga cinematográfica sustituida por otra con respuesta única.
- Se declara `ACCESS_COARSE_LOCATION` junto a la fina: desde Android 12 el sistema
  ofrece «ubicación aproximada» en el mismo diálogo, y sin la gruesa declarada
  quien elegía esa opción se quedaba sin ningún permiso.

### Seguridad y privacidad

- **No se declara el permiso `INTERNET`.** La app no puede hablar con ninguna red.
- Sin analítica ni telemetría de ningún tipo.
- Cada permiso del manifiesto lleva escrito al lado para qué se pide, y los de
  Bluetooth y ubicación llevan `maxSdkVersion` para no pedirlos donde ya no hacen
  falta.
- La firma de release se lee de variables de entorno o de `local.properties`;
  nada de claves en el repositorio.
