---
title: Manual técnico
description: Arquitectura, compilación, firma y publicación de Funny.
---

# Manual técnico de Funny

**Versión 1.0.0** · Brais Galdo · Ghato Studio

Este documento contiene **todo el manual de usuario** más lo que hace falta para
mantener la app: arquitectura, decisiones de diseño, instalación, despliegue,
implementación y estrategia de pruebas con sus resultados.

Está compuesto a partir de las fuentes del repositorio, así que no puede
desincronizarse con ellas: los apartados de manual, arquitectura e instalación son
literalmente `MANUAL-USUARIO.md`, `ARCHITECTURE.md` e `INSTALL.md`.

---

## Índice general

| Parte | Contenido |
|---|---|
| **A** | Resumen técnico |
| **B** | Decisiones de diseño |
| **C** | Implementación |
| **D** | Estrategia de pruebas y resultados |
| **E** | Despliegue |
| **F** | Manual de usuario completo |
| **G** | Arquitectura |
| **H** | Instalación y compilación |

---

## Parte A — Resumen técnico

| | |
|---|---|
| Nombre y paquete | Funny · `es.ghatostudio.funny` |
| Versión | 1.0.0 (`versionCode` 10000) |
| Lenguaje | Kotlin 2.0.21 |
| Interfaz | Jetpack Compose · Material 3 · una sola `Activity` |
| Android | mínimo 8.0 (API 26) · compila y apunta a API 35 |
| Construcción | Gradle 8.11.1 · AGP 8.7.3 · version catalog |
| Persistencia | DataStore Preferences |
| Red | **ninguna**: no se declara el permiso `INTERNET` |
| Multidispositivo | Nearby Connections (`P2P_STAR`), hasta 1 mesa + 4 mandos |
| Módulos | uno, `:app` |
| Código | 79 ficheros Kotlin · 20.242 líneas de producción · 3.069 de pruebas |
| Contenido | 3.194 cartas (1.668 es + 1.526 en) en 18 mazos |
| Textos | 413 claves × 13 idiomas |
| Pruebas | 164, todas en la JVM, sin emulador |
| Licencias | GPL-3.0-or-later (código) · CC BY-SA 4.0 (contenido) |

### Dependencias, y por qué está cada una

Todas fijadas en `gradle/libs.versions.toml`. No hay ninguna versión dinámica.

| Dependencia | Para qué |
|---|---|
| `androidx.core:core-ktx` | base |
| `androidx.appcompat:appcompat` | **locales por app**: `AppCompatDelegate.setApplicationLocales` |
| `androidx.browser:browser` | Custom Tabs, para abrir la donación fuera de la app |
| `androidx.activity:activity-compose` | punto de entrada de Compose |
| `androidx.lifecycle:*` | ViewModel |
| `androidx.datastore:datastore-preferences` | ajustes |
| `androidx.documentfile:documentfile` | escribir el `.funny.bak` donde diga el usuario |
| `kotlinx-coroutines-android` | corrutinas |
| `compose-bom` + ui, graphics, material3, tooling | la interfaz |
| `play-services-nearby` | la radio del salón |
| `qrcode-kotlin` | la matriz del QR de la donación |

Y de pruebas, que no entran en el APK: `junit`, `kotlin-test`,
`coroutines-test`, `robolectric`, `androidx.test:core-ktx`, `zxing-core`
(para **leer** el QR que genera la app y demostrar que se escanea) y `org.json`
(porque el `android.jar` de las pruebas unitarias devuelve `null` en todo).

**Lo que no hay, y es deliberado**: ninguna librería de facturación, ningún SDK de
analítica, ningún cliente HTTP, ningún Firebase, ninguna librería de imágenes.

---

## Parte B — Decisiones de diseño

Las seis decisiones grandes tienen su propio ADR, con contexto, consecuencias
**en contra** y alternativas descartadas:

| # | Decisión |
|---|---|
| [ADR-0001](adr/ADR-0001-stack.md) | Android nativo ahora, capas listas para Kotlin Multiplatform |
| [ADR-0002](adr/ADR-0002-sin-backend.md) | Sin backend y sin cuentas de usuario |
| [ADR-0003](adr/ADR-0003-salon-nearby.md) | El salón sobre Nearby Connections |
| [ADR-0004](adr/ADR-0004-donacion-sin-facturacion.md) | La donación, sin ninguna librería de facturación |
| [ADR-0005](adr/ADR-0005-contenido-sin-derechos.md) | El contenido no distribuye material con derechos |
| [ADR-0006](adr/ADR-0006-licencia.md) | GPL-3.0 para el código, CC BY-SA 4.0 para el contenido |

Aquí van las decisiones **medianas**, las que no dan para un ADR pero que alguien
que llegue al código se va a preguntar.

### Por qué las reglas del juego son funciones puras

`MotorJuego` recibe un estado y devuelve otro. Sin `Context`, sin corrutinas, sin
efectos, y con el azar entrando por el constructor como un `Random`.

Tres cosas que solo se pueden hacer así:

1. **Probar las reglas de verdad**, jugando partidas completas en la JVM en
   milisegundos.
2. **Reproducir una partida** con una semilla fija, que es lo que permite que un
   test compruebe que una partida siempre acaba.
3. **Que el salón no pueda divergir.** La mesa aplica las acciones que le llegan
   por la radio llamando a las mismas funciones que llamaría un dedo. No hay dos
   caminos.

### Por qué la navegación es un campo del estado y no Navigation-Compose

Porque aquí la pantalla **es** parte del estado del juego: cuando el motor resuelve
una prueba, decide también a dónde se va. Con Navigation-Compose habría dos fuentes
de verdad —el estado y la pila del navegador— que habría que mantener
sincronizadas.

Con el salón sería peor: la mesa tiene que poder decirle a un mando en qué pantalla
está, y eso es un valor que viaja en un mensaje, no una pila.

El precio es gestionar el retroceso a mano. Con 18 pantallas en un flujo casi
lineal, sale a favor.

### Por qué los textos son un `enum` y no `strings.xml`

Porque **una clave que no existe no compila**. Con XML, una clave mal escrita es un
fallo en tiempo de ejecución que aparece en el móvil de alguien.

El coste es que hay que escribir los catálogos en Kotlin y que no se pueden usar las
herramientas de traducción que esperan XML. Para trece idiomas mantenidos por una
persona, con un generador que se niega a emitir si falta una clave, compensa.

### Por qué los colores son *getters* composables

```kotlin
val TextoFuerte: Color
    @Composable @ReadOnlyComposable get() = LocalPaleta.current.textoFuerte
```

Una pantalla escribe `color = TextoFuerte` y no sabe qué tema está activo. Esto es
lo que hizo que pasar de un tema a seis **no** obligara a tocar veinte pantallas.

`@ReadOnlyComposable` porque no leen estado que cambie durante la composición: solo
un `staticCompositionLocalOf`, así que Compose puede saltarse trabajo.

### Por qué el dado tiene tres caras

Con seis caras y 20 casillas, la partida se acaba en unos pocos turnos y la mitad de
la mesa apenas juega. El objetivo del juego no es llegar rápido: es que todo el
mundo haga pruebas. `CARAS_DEL_DADO = 3` es un número de diseño, no una limitación
técnica, y está en una constante para que se pueda discutir.

### Por qué las modalidades fijan casillas y pruebas pero no el ritmo

Porque el ritmo ya era un ajuste propio. Si elegir «partida normal» cambiase también
el ritmo, estaría pisando en silencio algo que alguien había puesto a mano.

Y por qué los números personalizados se guardan **aparte** de los del preajuste:
quien juguetea con los botones y luego vuelve a «partida normal» espera una partida
normal, no la suya con otro nombre.

### Por qué el lienzo de dibujo es la única excepción al tema

Un lienzo que cambia de color con el tema no es un lienzo. Es papel claro fijo, y
sus diez tintas llevan su ratio de contraste anotado línea a línea en `Paleta.kt`.
Es la única excepción, y está documentada donde vive.

### Por qué la lectura inicial de preferencias es `runBlocking`

Es una sola lectura, en el arranque, de unos pocos kilobytes. La alternativa —leer
en asíncrono— hace que la app aparezca con el tema por defecto y cambie de color
medio segundo después. Preferible un arranque un pelo más lento a un parpadeo.

### Por qué el `versionCode` se calcula y no se escribe

```
versionCode = major * 10_000 + minor * 100 + patch
```

Un `versionCode` a mano es un número que alguien olvida subir, y Google Play rechaza
la subida. Calculado desde el SemVer es monótono por construcción. El límite —minor
y patch por debajo de 100— lo comprueba un `require` en el propio `build.gradle.kts`.

### Por qué la fecha de compilación es la del commit y no la del reloj

Para que dos compilaciones del mismo commit den el mismo binario. Con la hora del
reloj, cada build es distinta y no se puede comprobar que un APK corresponde a un
commit.

---

## Parte C — Implementación

### El ciclo de una prueba

```
TABLERO ──pulsa el dado──▶ lanzarDado()
                              │  sale 1, 2 o 3
                              ▼
                        continuarTrasDado()
                              │
              ┌───────────────┼───────────────┐
              ▼               ▼               ▼
          COMODIN          ENTREGA         VICTORIA
       (elige el          (pásale el      (ha llegado
        siguiente)         móvil a          a la meta)
              │            quien actúa)
              ▼               │
        elegirJuego() ────────▶│
                              ▼
                        empezarPrueba()
                              │
                            PRUEBA  ◀── cronómetro
                              │
                        resolverPrueba(superada, puntos)
                              │
                              ▼
                          RESULTADO
                              │
                        siguienteTurno()
                              ▼
                           TABLERO
```

Cada flecha es una función pura de `MotorJuego`. La pantalla solo pinta el estado y
llama a la siguiente.

### El salón, mensaje a mensaje

```
mando                          mesa
  │                              │
  ├── Hola(nombre) ─────────────▶│  la apunta y le asigna un participante
  │                              │
  │◀───────────── Salon(lista) ──┤  a todos: quién hay dentro
  │                              │
  │◀───────────── Vista(...) ────┤  a cada uno LO SUYO
  │                              │     · quien actúa: su carta
  │                              │     · el resto: el estado
  ├── Accion(TIRAR) ────────────▶│  ¿le toca a este? si no, se ignora
  │                              │  MotorJuego.lanzarDado()
  │◀───────────── Vista(...) ────┤
  │                              │
  ├── Adios ────────────────────▶│  la borra de la lista
```

Cinco piezas hacen el trabajo:

- **`Codec`** — JSON a mano, con un `Desconocido` para todo lo que no entienda. Un
  salón no puede caerse porque llegue un byte raro.
- **`VistaDelMando`** — lo que ve **un** dispositivo. Aquí está el
  `contenidoPrivado`, que solo se rellena para quien actúa.
- **`TipoAccion`** — el vocabulario cerrado de lo que un mando puede pedir. Un enum
  desconocido cae en un valor por defecto en lugar de fallar.
- **`SalonViewModel.vistaPara(dispositivo)`** — la función que decide qué ve cada
  uno. Es el único sitio donde se filtra contenido privado, a propósito: una sola
  línea que auditar.
- **`TransporteSalon`** — la interfaz. Tres implementaciones: la real, la de «no
  hay Play Services» y la de pruebas.

### La internacionalización, de la clave a la pantalla

```
Clave.AJUSTES_TITULO          enum: si no existe, no compila
        │
        ▼
Textos[clave]                 catálogo del idioma activo
        │  no está ──────────▶ catálogo inglés (respaldo)
        │                          no está ──────▶ "AJUSTES_TITULO"
        ▼
"Ajustes"
```

Con parámetros, `t.con(clave, 20, 10, 30)`, formateado con el `Locale` del idioma
activo, para que los números salgan como toca en cada uno.

Con plurales, `t.plural(ClavePlural.CASILLAS, 3)`: la categoría CLDR la resuelve
`android.icu.text.PluralRules` a través de la interfaz `ReglasDePlural`. En las
pruebas se inyecta una versión simple, porque el `android.jar` de pruebas no trae
ICU.

Los once catálogos que no son castellano ni inglés se **generan** desde un
diccionario con un script que toma el orden canónico de `Clave` y falla si falta
una clave. A mano, 392 × 11 garantiza un olvido.

### El QR de la donación

Se genera con `qrcode-kotlin` y se pinta en un `Canvas` de Compose, no como imagen,
para poder darle los colores del tema y su zona de silencio.

Que **se lea** no se supone: `PruebaCodigoQr` coge la matriz, la convierte en un
mapa de bits y lo descodifica con **ZXing**, que es un lector distinto del
generador. Si el QR saliera mal, el test lo vería.

### La taza de café

Vectorial, dibujada en Compose con `Canvas`. No es un PNG ni un emoji, para que
cambie de color con el tema. El vapor son tres trazos con `rememberInfiniteTransition`
y un ciclo de unos 3 segundos.

La entrada es escalonada, unos 40 ms entre elementos, y respeta la preferencia de
reducir animaciones del sistema a través de `LocalAnimaciones`.

### La copia de seguridad

```
serializar()   Ajustes + participantes ──▶ JSON con cabecera
interpretar()  JSON ──▶ Resultado.Bien | NoEsDeFunny | EsquemaFuturo | Ilegible
```

La cabecera se valida **antes** de tocar nada. Un esquema futuro se rechaza en lugar
de leerse a medias. Un enum desconocido cae en su valor por defecto. Los números
fuera de rango se recortan **al leerlos**, no al escribirlos, porque el fichero lo
puede haber editado cualquiera.

Antes de importar, `ficheroDeRespaldo()` guarda lo que había. Siempre.

**Esquema 2**, con migración desde el 1: una copia con `duracion: LARGA` se lee como
«partida extrema». La consecuencia —28 casillas pasan a 32— está escrita en el
código, porque se prefirió eso a dejar a alguien en «personalizada» con un 28 que no
escribió.

---

## Parte D — Estrategia de pruebas y resultados

### El principio

**Se prueba lo que se rompe en silencio.** No hay un objetivo de cobertura: hay una
lista de cosas que, si se rompen, nadie se entera hasta que alguien está jugando.

Y una consecuencia práctica: **las 172 pruebas corren en la JVM**, sin emulador, en
menos de un minuto. Eso es posible porque `dominio/` es Kotlin puro, y es la razón
de que se ejecuten de verdad en cada cambio en lugar de una vez al mes.

### Resultados

Ejecutado con `./gradlew :app:testDebugUnitTest`:

| Clase | Pruebas | Qué cubre |
|---|---|---|
| `PruebaMotorJuego` | 23 | las reglas: tablero, dado, turnos, resolución, partidas completas |
| `PruebaReglas` | 26 | mazos, opciones de año, colores, límites de los modos |
| `PruebaCopiaSeguridad` | 21 | ida y vuelta, ficheros corruptos, esquemas, fusión, migraciones |
| `PruebaCatalogos` | 22 | completitud de los 13 idiomas, parámetros, plurales, vocabulario |
| `PruebaContenido` | 17 | los 12 mazos × 2 idiomas |
| `PruebaSalon` | 14 | roles, autoridad, enrutado del contenido privado |
| `PruebaProtocolo` | 13 | serialización, mensajes ilegibles, versiones futuras |
| `PruebaModalidad` | 12 | las cuatro modalidades y sus límites |
| `PruebaContraste` | 11 | AA en las 6 paletas |
| `PruebaCodigoQr` | 6 | que el QR se descodifica |
| `PruebaMainActivity` | 6 | que la Activity y los ViewModels puedan arrancar |
| **Total** | **172** | **0 fallos, 0 errores** |

Y, además de las pruebas, en `check`:

| | Resultado |
|---|---|
| ktlint | limpio |
| lint de Android | **0 errores**, 48 avisos |
| `verificarSinFacturacion` | **181 artefactos revisados, ninguna librería de pagos** |
| `verificarTextosLiterales` | sin textos a fuego en la interfaz |

### Cuatro pruebas que merece la pena mirar

**`PruebaContraste`** calcula la luminancia de WCAG y falla si algo baja de 4,5:1.
Escribirla encontró fallos reales: blanco sobre el rosa de Fiesta se quedaba en
3,37:1, y la función que elige la tinta de un botón usaba un umbral de luminancia
en lugar de comparar los dos contrastes, así que seis colores de juego daban texto
blanco ilegible. El test es la razón de que se supiera.

**`PruebaCodigoQr`** descodifica con ZXing lo que genera la app. Generar «algo
cuadrado» es fácil; demostrar que un lector lo lee es otra cosa.

**`PruebaSalon`** usa `TransporteDeMentira` para poner dos «móviles» en la misma
JVM, enrutando cada mensaje por el códec real. Demuestra el protocolo, los roles,
las comprobaciones de autoridad y que la palabra secreta llega solo a quien actúa.

**`los parametros de un texto son los mismos en los trece idiomas`** compara los
`%1$d` de cada idioma con los del inglés. Un parámetro que se cae al traducir no se
nota hasta que alguien pone la app en ese idioma, y puede acabar en una excepción
de formato en mitad de una partida.

### Fallos reales que encontraron las pruebas

No son hipotéticos. Están en el CHANGELOG:

- Dos participantes con el mismo identificador y el mismo color al añadirlos de
  golpe. Con identificadores repetidos, renombrar o borrar uno afectaba a los dos.
- Participantes fantasma en el salón: la mesa se rellenaba hasta el mínimo del modo
  antes de que nadie se hubiera conectado.
- En el salón, cada mando quedaba asociado al móvil de la mesa en lugar de a sí
  mismo, porque un parámetro tapaba a una propiedad del mismo nombre.
- Un texto de la donación usaba la palabra «desbloquea», prohibida por el propio
  criterio del proyecto.

### Lo que NO está probado

Con nombres, porque un apartado de pruebas que solo cuenta lo bueno no vale:

- **La radio del salón.** Descubrimiento real, permisos concedidos y denegados,
  alcance, interferencias, reconexión al alejarse y volver, y qué pasa si la mesa
  se apaga a mitad de partida. Las 27 pruebas del salón cubren el protocolo, **no
  la radio**. Esto necesita dos móviles delante.
- **La interfaz.** No hay tests de Compose UI. Los flujos críticos están
  pendientes.
- **Exportar e importar en el dispositivo.** La ida y vuelta está cubierta por
  21 pruebas unitarias, pero el paso por el selector de ficheros del sistema no se
  ha hecho a mano.
- **El salón con dos móviles.** Sigue pendiente por lo obvio: hace falta un
  segundo teléfono.

### Lo que la prueba en el dispositivo sí encontró

La app se probó en un **SM-S908U con Android 13** y el resultado justifica por sí
solo el punto 12 de la plantilla: **se cayó al primer arranque, dos veces
seguidas, por dos causas distintas**, con las 172 pruebas de entonces en verde,
ktlint limpio y el lint de Android sin errores.

| Fallo | Por qué no lo vio nada |
|---|---|
| Tema heredado de `android:Theme.Material`, no de AppCompat → `IllegalStateException` en `setContentView` | Ninguna prueba instanciaba la Activity |
| Parámetros por defecto de Kotlin no generan el constructor `(Application)` que `AndroidViewModelFactory` busca por reflexión → `NoSuchMethodException` | Ninguna prueba instanciaba el ViewModel |
| El título «FUNNY» se escribía «YNNUF» en árabe: un `Row` de letras sueltas se espeja en RTL | Ninguna prueba renderiza en RTL |
| El tema en castellano seguía llamándose «Fiestón», el nombre anterior de la app | Un resto de un renombrado no da ningún error |

Los cuatro están arreglados y los cuatro tienen ahora una prueba que los caza.
Las dos primeras rompían la app **en debug y en release por igual**: compilaba y
se cerraba al abrirla.

Lo que sí quedó verificado en el móvil, con capturas en
`docs/google_play/graficos/capturas/`: una partida completa (tirada, avance,
prueba con cronómetro, corrección y vuelta de la ficha al fallar), las cuatro
modalidades con sus pasos numéricos, los seis temas aplicados sin reiniciar, los
trece idiomas, el árabe espejado, la hoja del café en los seis temas y en RTL, y
el QR **descodificado con ZXing desde la propia captura**, que devuelve
`https://revolut.me/brais2oz6`.
- **iOS.** No existe. Ver el [ADR-0001](adr/ADR-0001-stack.md).

### Cómo se añade una prueba cuando se arregla algo

La regla de la casa: **si has corregido un fallo, deja un test que habría fallado
antes**. Todos los fallos de la lista de arriba tienen el suyo.

---

## Parte E — Despliegue

### Firma

El keystore **no está en el repositorio y no va a estarlo**. La configuración lo
busca primero en variables de entorno y luego en `local.properties`:

| Propiedad | Variable de entorno |
|---|---|
| `funny.keystore` | `FUNNY_KEYSTORE` |
| `funny.keystore.password` | `FUNNY_KEYSTORE_PASSWORD` |
| `funny.key.alias` | `FUNNY_KEY_ALIAS` |
| `funny.key.password` | `FUNNY_KEY_PASSWORD` |

La transformación es mecánica: la propiedad en mayúsculas con los puntos como
subrayados.

**Si no hay keystore, la release se compila sin firmar y avisa por consola.** Es
deliberado: mejor un aviso claro que firmar con una clave de mentira.

Los pasos para crear el keystore están en la Parte H.

### Integración continua

`.github/workflows/ci.yml`, en cada push y cada PR sobre `main`:

1. JDK 17 y Gradle con caché (solo `main` escribe en la caché, para que un PR no
   pueda envenenarla).
2. `./gradlew :app:check` — **lo mismo que se ejecuta en local**.
3. `./gradlew :app:assembleDebug`.
4. Guarda `:app:dependencies` como prueba documental de que no hay librería de
   pagos, más los informes de pruebas, ktlint y lint, y el APK de depuración.

Sin emulador: por eso tarda minutos y no media hora.

### Release

`.github/workflows/release.yml`, al empujar un tag `vX.Y.Z`:

1. **Comprueba que están los cuatro secretos antes de compilar.** Fallar en el
   minuto uno es mejor que fallar después de veinte minutos de build.
2. `:app:check` completo. Una release no sale de un árbol que no pasa las pruebas.
3. Reconstruye el keystore desde `FUNNY_KEYSTORE_BASE64`.
4. `:app:bundleRelease` y `:app:assembleRelease`.
5. **Verifica con `apksigner` que el binario ha salido firmado de verdad.** El
   build avisa y sigue si no hay keystore, así que de eso no se puede fiar una
   release.
6. Borra el keystore del runner.
7. Publica la GitHub Release **en borrador**, con el AAB y el APK. Un tag no
   debería soltar una release al mundo sin que alguien la mire.

### Publicación en tiendas

En su documento propio: [GUIA-PUBLICACION.md](GUIA-PUBLICACION.md).

### Esta documentación

- La **fuente única** es Markdown en `docs/`, y eso sí va en commits.
- Los binarios (HTML, PDF, DOCX) **no se commitean**: se generan con
  `docs/generar-docs.py`, que es reproducible, y se publican como assets de la
  Release. `docs/out/` está en `.gitignore`.

```bash
python docs/generar-docs.py
```

La plantilla del proyecto pedía Pandoc. No está instalado en el entorno de
desarrollo y no se ha instalado a la ligera: el script hace lo mismo con lo que sí
hay —Markdown de Python para el HTML, Edge en modo *headless* para el PDF y
`python-docx` para el DOCX—, y queda igual de reproducible y sin dependencias que
haya que pedirle a nadie.

---

## Parte F — Manual de usuario completo

<!-- incluir: MANUAL-USUARIO.md -->

---

## Parte G — Arquitectura

<!-- incluir: ARCHITECTURE.md -->

---

## Parte H — Instalación y compilación

<!-- incluir: INSTALL.md -->
