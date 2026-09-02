# Arquitectura de Funny

Cómo está montada la app y, sobre todo, **por qué está montada así**. Las
decisiones grandes tienen su propio ADR en [adr/](adr/); aquí está el mapa.

---

## Índice

1. [De un vistazo](#1-de-un-vistazo)
2. [Las cuatro capas](#2-las-cuatro-capas)
3. [El motor de juego](#3-el-motor-de-juego)
4. [Estado y navegación](#4-estado-y-navegación)
5. [El salón multidispositivo](#5-el-salón-multidispositivo)
6. [Temas](#6-temas)
7. [Idiomas](#7-idiomas)
8. [Contenido](#8-contenido)
9. [Datos que persisten](#9-datos-que-persisten)
10. [Lo que vigila la build](#10-lo-que-vigila-la-build)
11. [Lo que no está hecho](#11-lo-que-no-está-hecho)

---

## 1. De un vistazo

| | |
|---|---|
| Lenguaje | Kotlin 2.0.21 |
| Interfaz | Jetpack Compose, Material 3, **una sola `Activity`** |
| Mínimo | Android 8.0 (API 26) · compila y apunta a API 35 |
| Módulos | uno, `:app` |
| Dependencias | Gradle version catalog, versiones fijadas |
| Persistencia | DataStore Preferences |
| Red | **ninguna**: no se declara el permiso `INTERNET` |
| Pruebas | 172 unitarias, todas en la JVM, sin emulador |

Un solo módulo y una sola `Activity`. La `Activity` es `AppCompatActivity` y no
`ComponentActivity` por un motivo concreto: las **locales por app**
(`AppCompatDelegate.setApplicationLocales`) lo necesitan.

---

## 2. Las cuatro capas

```
es.ghatostudio.funny
│
├── dominio/       7 ficheros · 2.017 líneas · Kotlin PURO
│   ├── MotorJuego.kt      las reglas, como transiciones de estado
│   ├── Modelos.kt         estado, ajustes, participantes, modalidades, modos
│   ├── Reglas.kt          mazos, generación del tablero, funciones puras
│   ├── Juego.kt           los doce juegos y sus propiedades
│   ├── Contenido.kt       el modelo de las cartas
│   ├── textos/Clave.kt    las 392 claves de i18n
│   └── salon/Protocolo.kt mensajes y códec del salón
│
├── datos/         3 ficheros · 682 líneas
│   ├── Preferencias.kt    DataStore
│   ├── CopiaSeguridad.kt  exportación e importación .funny.bak
│   └── FuenteDeContenido.kt  carga de los assets
│
├── plataforma/    4 ficheros · 640 líneas · lo que solo existe en Android
│   ├── TransporteNearby.kt   la radio del salón
│   ├── TransporteSalon.kt     su interfaz + el caso «no disponible»
│   ├── Sonidos.kt             ToneGenerator y háptica
│   └── Navegador.kt           Custom Tabs
│
└── ui/            53 ficheros · 16.867 líneas
    ├── App.kt                el grafo de pantallas
    ├── JuegoViewModel.kt      el pegamento
    ├── SalonViewModel.kt      la mesa y los mandos
    ├── comun/                 composables compartidos
    ├── donacion/              el bottom sheet del café
    ├── i18n/                  Idioma, Textos y los trece catálogos
    ├── pantallas/             16 pantallas + las de las pruebas
    ├── tema/                  Paleta, tokens, contraste
    └── tour/                  el tour guiado
```

### La regla dura, y cómo se sabe que se cumple

**`dominio/` no importa nada de Android.** Cero `import android.*`, cero
`import androidx.*`. Comprobado:

```bash
grep -rl "^import android\|^import androidx" app/src/main/java/es/ghatostudio/funny/dominio/
# no devuelve nada
```

No es purismo. Es lo que hace que:

- las reglas del juego se prueben en la JVM, sin emulador y en segundos;
- `dominio/` sea movible a `commonMain` el día que haya un Mac
  ([ADR-0001](adr/ADR-0001-stack.md));
- la mesa del salón y un móvil suelto apliquen **exactamente** las mismas
  transiciones, porque es el mismo código.

Las dos piezas que en KMP serían `expect`/`actual` ya están detrás de una interfaz:

| Pieza | Interfaz | Android |
|---|---|---|
| Plurales (ICU) | `ReglasDePlural` | `ReglasDePluralAndroid` |
| Radio del salón | `TransporteSalon` | `TransporteNearby` |

---

## 3. El motor de juego

`MotorJuego` es el corazón, y es **una colección de funciones puras**:

```kotlin
fun lanzarDado(estado: EstadoJuego): EstadoJuego
fun elegirJuego(estado: EstadoJuego, juego: Juego): EstadoJuego
fun resolverPrueba(estado: EstadoJuego, superada: Boolean, puntos: Int): EstadoJuego
fun siguienteTurno(estado: EstadoJuego): EstadoJuego
```

Estado dentro, estado nuevo fuera. Ni `Context`, ni Compose, ni corrutinas, ni
efectos. El azar entra por el constructor como un `Random`, así que **con una
semilla fija una partida es reproducible** — y de ahí que los tests puedan jugar
partidas enteras.

Esto era antes lógica repartida por el `ViewModel` y por las pantallas. Sacarlo es
lo que ha permitido dos cosas que no se pueden hacer de otra forma:

1. **Probar las reglas de verdad.** `PruebaMotorJuego` (23 pruebas) juega partidas
   completas hasta la victoria y comprueba invariantes: que el tablero mide lo que
   dicen los ajustes, que fallar devuelve la ficha a donde estaba, que el turno
   rota, que una partida siempre termina.
2. **Que el salón sea honesto.** La mesa aplica las acciones que le llegan por la
   radio llamando **a las mismas funciones** que llamaría un dedo en la pantalla.
   No hay dos caminos que puedan divergir.

---

## 4. Estado y navegación

Un único `EstadoJuego` inmutable, expuesto por `JuegoViewModel` como estado de
Compose. La navegación es un campo de ese estado:

```kotlin
enum class Pantalla { INICIO, MODO, PARTICIPANTES, TABLERO, COMODIN, ENTREGA,
                      PRUEBA, RESULTADO, VICTORIA, AJUSTES, TEMA, IDIOMA, ... }
```

No se usa Navigation-Compose. El motivo: aquí la pantalla **es** parte del estado
del juego —cuando el motor resuelve una prueba, decide también a dónde se va—, y
tener dos fuentes de verdad (el estado y la pila del navegador) obligaría a
sincronizarlas. Con el salón sería aún peor: la mesa tiene que poder decirle a un
mando en qué pantalla está, y eso es un valor, no una pila de navegación.

El precio es que la pila de retroceso la gestionamos nosotros. Para 18 pantallas
que forman un flujo casi lineal, sale a favor.

---

## 5. El salón multidispositivo

Detalle completo en el [ADR-0003](adr/ADR-0003-salon-nearby.md). El resumen:

```
        ┌──────────────┐
        │  MESA (hub)  │   aplica MotorJuego · única autoridad
        └──┬───┬───┬───┘
           │   │   │        Nearby Connections, P2P_STAR
    ┌──────┘   │   └──────┐  (Bluetooth / BLE / Wi-Fi Direct)
┌───▼───┐ ┌───▼───┐ ┌───▼───┐
│ mando │ │ mando │ │ mando │   envían acciones, reciben su vista
└───────┘ └───────┘ └───────┘
```

- **Transporte**: Nearby Connections, que negocia por su cuenta el mejor canal.
  Gratis, sin clave de API y sin factura.
- **Protocolo**: `dominio/salon/Protocolo.kt`, Kotlin puro, JSON escrito a mano.
  Seis tipos de mensaje y un `VERSION_PROTOCOLO` que viaja en el identificador del
  servicio, así que dos versiones incompatibles no se ven.
- **Autoridad**: solo la mesa calcula. Un mando manda `Accion` y recibe `Vista`.
- **Contenido privado**: `vistaPara()` decide qué ve cada dispositivo. La palabra
  del tabú va **solo** al que actúa.

### Cómo se prueba un juego de varios móviles con un móvil

`TransporteDeMentira` pone dos «móviles» en la misma JVM y enruta cada mensaje
**por el códec real**. Con eso, `PruebaSalon` (14) y `PruebaProtocolo` (13)
demuestran el protocolo, los roles, las comprobaciones de autoridad y el enrutado
del contenido privado.

**Lo que no demuestran**: la radio. Descubrimiento, permisos, alcance, reconexión,
y qué pasa si la mesa se apaga a mitad de partida. Eso necesita dos móviles
delante. Está escrito así en el KDoc del propio fichero, para que nadie lea esos
27 tests y crea que el salón está verificado de punta a punta.

---

## 6. Temas

Seis paletas —tres oscuras (Fiesta, Neón, Medianoche) y tres claras (Papel, Menta,
Atardecer)— más «seguir al sistema», que no es un tema sino un `Boolean` aparte,
porque hace falta saber a qué tema claro y a qué oscuro caer.

Todos los colores están en **un solo fichero**, `ui/tema/Paleta.kt`. Las pantallas
no los conocen: leen tokens que son *getters* composables.

```kotlin
Text(t[Clave.AJUSTES_TITULO], color = TextoFuerte)   // no Color(0xFF...)
```

Ese diseño es la razón de que añadir cinco temas a una app que tenía uno no haya
supuesto tocar veinte pantallas.

### El contraste se comprueba, no se supone

`PruebaContraste` (11 pruebas) calcula la luminancia relativa de WCAG **en código
de producción** y falla la build si algo baja del mínimo AA (4,5:1 en texto,
3:1 en gráficos). Se le exige a las seis paletas, a los doce colores de juego, a
los ocho de participante y a las diez tintas del lienzo de dibujo.

Escribirlo encontró fallos reales: blanco sobre el rosa de Fiesta se quedaba en
3,37:1, y —más grave— la función que elige la tinta de un botón usaba un umbral de
luminancia en lugar de comparar los dos contrastes, así que seis colores de juego
daban texto blanco ilegible. Está en el CHANGELOG.

La única excepción consciente al tema es el **lienzo de dibujo**: papel claro fijo,
porque un lienzo que cambia de color con el tema no es un lienzo. Sus diez tintas
llevan su ratio de contraste anotado en el propio fichero, línea a línea.

---

## 7. Idiomas

Trece: inglés, castellano, francés, alemán, chino simplificado, japonés, ruso,
italiano, griego, árabe, gallego, catalán y euskera.

```
dominio/textos/Clave.kt     enum con 392 claves — el orden canónico
ui/i18n/Textos.kt           resuelve clave → texto, con respaldo al inglés
ui/i18n/Catalogo*.kt        trece catálogos, 392 entradas cada uno
```

Decisiones que importan:

- **Nada de `strings.xml`.** Las claves son un `enum`, así que **una clave que no
  existe no compila**. Con XML sería un error en tiempo de ejecución.
- **El respaldo es el inglés**, y si tampoco está, el nombre de la clave. Preferimos
  ver `AJUSTES_TITULO` en pantalla —feo, evidente y fácil de encontrar— a que la app
  reviente en mitad de una partida. `PruebaCatalogos` se ocupa de que no pase.
- **Los plurales van por categorías CLDR** (`ZERO`…`OTHER`), resueltas con
  `android.icu.text.PluralRules` a través de la interfaz `ReglasDePlural`. El ruso
  necesita tres formas y el árabe seis: «uno o varios» no vale.
- **RTL sigue al idioma elegido, no al del sistema.** Poner la app en árabe con el
  móvil en castellano gira la interfaz igualmente, porque `LocalLayoutDirection` lo
  fija el idioma activo.
- **Inglés y árabe llevan insignia neutra**, no bandera: ninguna nación es dueña de
  esos idiomas. Los idiomas cuyo emoji de bandera no se dibuja de forma fiable
  (gallego, catalán, euskera) llevan su código.
- **Locales por app** con `AppCompatDelegate` + `locales_config.xml`.

### Lo que vigila la build

`PruebaCatalogos` (21 pruebas) exige a los trece: las 392 claves completas,
ninguna clave huérfana, ningún texto vacío, **los mismos parámetros que el inglés**
(un `%2$d` perdido acaba en una excepción de formato), los seis plurales, nombre y
lema y instrucciones para los doce juegos, y **cero vocabulario de compra** en los
textos de la donación.

Los catálogos de castellano e inglés están escritos a mano; los otros once se
generan desde un diccionario con un script que toma el orden canónico de `Clave` y
**se niega a emitir si falta una clave**. A mano, con 392 × 11, se acabaría colando
un olvido.

---

## 8. Contenido

**2.601 cartas** en `app/src/main/assets/contenido/<idioma>/<juego>.json`: 1.366 en
castellano y 1.235 en inglés. Un idioma sin contenido propio cae al respaldo, no se
queda vacío.

| Mazo | es | Mazo | es |
|---|---|---|---|
| dibujo | 261 | preguntas | 121 |
| mimica | 217 | retos | 100 |
| cuando | 151 | emojis | 80 |
| tabu | 117 | verdadero_falso | 79 |
| canta | 70 | ordena | 60 |
| desafios | 70 | trabalenguas | 40 |

`PruebaContenido` (17 pruebas) valida los doce mazos de los dos idiomas en cada
build: campos obligatorios, cuatro opciones distintas, índice correcto en rango,
cuatro prohibidas por carta de tabú, ningún señuelo autorreferente, equilibrio
~50/50 en verdadero o falso, y **que ninguna canción traiga letra**
([ADR-0005](adr/ADR-0005-contenido-sin-derechos.md)).

El reparto usa un `Mazo` que **agota todas las cartas antes de repetir ninguna**, y
`sacar(n)` nunca devuelve la misma dos veces en la misma tirada: es preferible una
prueba corta a una prueba con la misma palabra repetida.

---

## 9. Datos que persisten

**DataStore Preferences.** No hay base de datos: lo que hay que guardar son unos
ajustes y una lista de participantes.

La lectura inicial es un `runBlocking` de una sola vez en el arranque. Es una
decisión consciente: es preferible un arranque un pelo más lento a que la app
aparezca con el tema por defecto y cambie de color medio segundo después.

### Exportación e importación

Fichero **`.funny.bak`**, que por dentro es JSON. Se eligió JSON en lugar de SQLite
o un ZIP porque lo que hay que guardar son unas preferencias y una lista de
equipos: un formato que se pueda abrir con un editor de texto y arreglar a mano
vale más aquí que uno binario compacto.

- Cabecera con `esquema`, `app`, `version` y `fecha`, **validada antes de tocar
  nada**. Un fichero de una versión futura se rechaza en lugar de leerse a medias.
- **Copia de seguridad automática antes de importar**, siempre. Si la importación
  falla a mitad, los datos anteriores siguen ahí.
- Elección entre **fusionar** y **reemplazar**, avisando de qué va a ocurrir.
- **Esquema 2**, con migración desde el 1: una copia vieja con `duracion: LARGA` se
  lee como «partida extrema». Las migraciones llevan test.
- Tipo MIME registrado y `activity-alias` para poder abrir el fichero desde el
  gestor de archivos.

`PruebaCopiaSeguridad` (21 pruebas) cubre la ida y vuelta completa, los ficheros
corruptos, los esquemas futuros, la fusión y las tres migraciones.

---

## 10. Lo que vigila la build

`./gradlew :app:check` incluye:

| | Qué comprueba |
|---|---|
| **172 pruebas** | dominio, i18n, salón, copia, contenido, contraste, QR, arranque |
| **ktlint** | formato, 100 columnas (120 en los catálogos generados) |
| **lint de Android** | con `abortOnError`: un error de lint rompe la build |
| **`verificarSinFacturacion`** | recorre los artefactos resueltos y falla si aparece `billingclient`, `revenuecat`, `adapty`… Hoy: **181 artefactos, ninguno de pagos** |
| **`verificarTextosLiterales`** | heurística sobre `ui/`: falla si hay una cadena a fuego |

Las dos últimas son tareas escritas para este proyecto, y están enganchadas a
`check` para que no se puedan olvidar.

---

## 11. Lo que no está hecho

Con nombres, para que no haya sorpresas:

- **iOS no existe.** No hay `iosMain`, no hay proyecto de Xcode, no se ha compilado
  nada para Apple. Las capas están preparadas; la portabilidad no está demostrada.
  El coste estimado está tasado en el [ADR-0001](adr/ADR-0001-stack.md), y el punto
  caro es el salón: Nearby Connections no existe en iOS y habría que reescribir el
  transporte sobre *Multipeer Connectivity*.
- **Escritorio, tampoco.**
- **La radio del salón no está verificada en hardware.** 27 pruebas cubren el
  protocolo y la autoridad; el alcance, los permisos reales y la reconexión
  necesitan dos móviles.
- **No hay tests de interfaz.** Las 172 pruebas son de la JVM. Los flujos críticos
  en Compose UI Test están pendientes, y eso tiene un precio medido: el primer
  arranque en un móvil de verdad encontró **cuatro fallos** que ninguna prueba de
  la JVM podía ver, dos de ellos impedían que la app arrancara. Están en el
  CHANGELOG y ahora cada uno tiene su prueba.
- **Exportar e importar no se han probado a mano en el dispositivo**, solo con
  las 21 pruebas de la ida y vuelta.
