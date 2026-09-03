# Capturas

**Hechas en un SM-S908U (Galaxy S22 Ultra), Android 13, 1080×2316.** Están en
`graficos/capturas/`.

> **Aviso sobre la calidad.** Las 46 capturas de esta carpeta están reducidas a
> una paleta de 256 colores para no meter 8 MB en el repositorio. A tamaño real
> se nota una ligera banda en los degradados. **Sirven como evidencia de
> verificación, no como material final de tienda**: las que vayan a la ficha
> conviene volver a tomarlas sin optimizar, con `adb exec-out screencap -p`.

---

## Lo que hay, por carpeta

### `capturas/telefono/` — el juego

| Fichero | Qué demuestra |
|---|---|
| `01-inicio.png` | Pantalla de inicio (con los doce de entonces) |
| `02-modo.png` | Los tres modos, con su descripción |
| `03-participantes.png` | Alta de equipos: emoji y color distintos por equipo |
| `07-modalidad.png` | **Las cuatro modalidades** con casillas, pruebas y minutos |
| `07b-a-mi-manera.png` | Los pasos numéricos al elegir «a mi manera» |
| `07c-pasos.png` | Pasos usados: 20→12 casillas, 10→16 pruebas, resumen actualizado |
| `02-tablero.png` | Tablero de 13 casillas para 12 pedidas, con comodines y «juegan todos» |
| `04-dado.png` | Tirada de 3 y avance a la casilla de Emojis |
| `05-entrega.png` | Pantalla de entrega con las instrucciones y el tiempo |
| `06-prueba-emojis.png` | La prueba con su cronómetro corriendo |
| `08-resultado.png` | Respuesta fallada en rojo, la correcta en verde y cuál era |
| `09-tras-fallar.png` | La ficha vuelve a la casilla 0 al fallar |
| `11-ajustes.png` | Ajustes: punto único de entrada |
| `12-tour.png` | Tour, paso 1 de 8 |
| `05-tour-juegos.png` | **Tour, paso 5: los dieciocho juegos con lema e instrucciones** |

### `capturas/juegos/` — los dieciocho juegos, probados en el móvil

Los dieciocho juegos se reparten en **siete pantallas** distintas. Las siete se
han recorrido en el dispositivo, tocando de verdad sus controles. Esta tabla es
de la primera pasada, cuando el juego tenía doce pruebas; los seis nuevos vienen
más abajo:

| Pantalla | Juegos que la usan | Qué se comprobó | Captura |
|---|---|---|---|
| `PruebaDibujo.kt` | Pinturillo | Se dibuja con el dedo, diez tintas, tres grosores, deshacer y borrar | `01c-lienzo-dibujado.png` |
| `PruebaPalabras.kt` | **Tabú**, Mímica | Palabra y sus cuatro prohibidas; SALTAR / PROHIBIDA / ACERTADA | `02b-tabu-carta.png` |
| `PruebaOpciones.kt` | **Emojis**, ¿Cuándo?, Preguntas | Cuatro opciones, cronómetro, fallo en rojo y acierto en verde | `../telefono/08-resultado.png` |
| `PruebaOrdena.kt` | Ordena | Se toca en orden, se comprueba y se corrige | `05b-ordena-resuelto.png` |
| `PruebaVerdaderoFalso.kt` | ¿Te lo crees? | Verdadero/falso **con su explicación** al responder | `07b-te-lo-crees-explicacion.png` |
| `PruebaReto.kt` | Reto rápido | El contador suma al tocarlo (0→3 de 6) y hay −1 para corregir | `08b-reto-contador.png` |
| `PruebaVeredicto.kt` | **Trabalenguas**, Canta, Desafío | Contenido y veredicto ✗ NO / ✓ ¡SÍ! | `09-veredicto-trabalenguas.png` |

**Siete de los doce primeros se abrieron uno a uno** (Emojis, Pinturillo, Tabú,
Ordena, ¿Te lo crees?, Reto rápido, Trabalenguas). Los otros cinco —Mímica,
¿Cuándo?, Preguntas, Canta y Desafío— **no se entraron**: comparten pantalla con
uno ya probado y solo cambia el contenido, que sí lo valida `PruebaContenido` en
cada build. Es cobertura por familia, no juego a juego, y conviene saberlo.

De paso salió una comprobación que no estaba buscando: al desactivar **los doce**
juegos en Ajustes, la app **no se queda con el tablero vacío**, ignora la
preferencia y reparte de todos. Es el resguardo de `juegosActivos`, y se vio
funcionando por accidente.

#### Los seis nuevos, jugados de verdad

Los seis que se añadieron después se dejaron **solos** en el filtro de Ajustes
—«6 de 18 activos»— y se jugó una tanda entera del reto en solitario en modalidad
extrema, 16 pruebas, hasta el marcador final (27 puntos, marca personal nueva).
Los seis salieron y se resolvieron tocando sus controles:

| Juego | Pantalla que reutiliza | Qué se vio en el móvil | Captura |
|---|---|---|---|
| 📜 Refranes | `PruebaOpciones.kt` | «Cuando el río suena…» con cuatro remates, y los tres señuelos son remates de otros refranes | `12-refranes.png` |
| ⏳ ¿Antes o después? | `PruebaOpciones.kt` | Dos hechos, los dos años como tema, ✓ en la respuesta al resolver | `13-antes.png` |
| 🔤 Anagramas | `PruebaOpciones.kt` | «7 LETRAS · A R A N J A N» y cuatro candidatas | `14-anagramas.png` |
| 🗣 Acentos | `PruebaVeredicto.kt` | «DILO CON ESA VOZ» y veredicto ✗ NO / ✓ ¡SÍ! | `15-acentos.png` |
| 🔊 Sonidos | `PruebaVeredicto.kt` | «SOLO CON LA BOCA» y el mismo veredicto | `16-sonidos.png` |
| 🔗 Encadenados | `PruebaReto.kt` | Contador de 0 a 6 tocándolo, −1 y NOS RENDIMOS | `17-encadenados.png` |

Cada uno tiene además su `-intro.png`: la pantalla de presentación con emoji,
nombre, lema, instrucciones y segundos.

La tanda se condujo **leyendo el árbol de accesibilidad** en cada paso, no con
coordenadas fijas. No es un detalle técnico: las otras apps del móvil se ponen en
primer plano por su cuenta —pasó dos veces, con ShardPay y con NexaPDF— y una
secuencia de toques a ciegas acaba tocando en la app de otro. Ahora se comprueba
qué app está delante antes de cada toque y, si no es Funny, se para.

**Y salió un fallo**: con la partida extrema elegida, la fila de «a mi manera»
anunciaba «32 casillas · 16 pruebas» —las de la extrema— en lugar de los números
guardados. Corregido y con prueba propia; está contado en el CHANGELOG.

### `capturas/temas/` — los seis temas

Uno por tema, todos aplicados **sin reiniciar la app**. Color medio de cada uno,
que es la prueba de que son distintos de verdad:

| Tema | Color medio |
|---|---|
| Fiesta | (39, 22, 59) |
| Neón | (21, 30, 43) |
| Medianoche | (31, 36, 44) |
| Papel | (235, 231, 227) |
| Menta | (227, 234, 232) |
| Atardecer | (238, 230, 225) |

### `capturas/idiomas/` — los trece idiomas

Uno por idioma, más el selector. **Las catorce son distintas entre sí y ninguna
está en blanco**, comprobado comparando sus firmas.

- `05-ar-rtl.png` — **el árabe con la interfaz espejada**: «رجوع» arriba a la
  derecha con el chevron volteado, insignias a la derecha, ✓ a la izquierda. Y el
  móvil está en castellano, así que la dirección la manda el idioma **elegido**.
- `05b-ar-titulo.png` — el título «FUNNY» en árabe, después de arreglar el fallo
  que lo escribía «YNNUF».
- `00-selector.png` — insignia neutra **EN** para el inglés y **AR** para el
  árabe, y códigos **GL**/**CA**/**EU** donde el emoji de bandera no se dibuja.

### `capturas/donacion/` — la hoja del café

| Fichero | Qué demuestra |
|---|---|
| `00-ajustes-abajo.png` | Exportar, importar, café, compartir, tour, ayuda y acerca de |
| `01-hoja-fiesta.png` … `07-hoja-atardecer.png` | **La hoja en los seis temas.** La taza es vectorial y se recolorea con los tokens de cada uno |
| `08-hoja-arabe-rtl.png` | **La hoja en árabe RTL**, con el importe en cifras árabes (`١ €`) |
| `02-qr.png` | El QR, que **descodifica a `https://revolut.me/brais2oz6`** |

El QR no se dio por bueno de vista: se descodificó la captura con ZXing, un
lector independiente del generador, y devolvió el enlace correcto.

---

## Lo que falta para la ficha de Play

De las ocho que Play luce mejor, **hay siete**:

- [x] Inicio · `telefono/01-inicio.png`
- [x] Tablero · `telefono/02-tablero.png`
- [x] **Tabú** con su carta y las prohibidas · `juegos/02b-tabu-carta.png`
- [x] **Pinturillo** con algo dibujado · `juegos/01c-lienzo-dibujado.png`
- [x] Modalidad · `telefono/07-modalidad.png`
- [x] Idiomas · `idiomas/00-selector.png`
- [x] La rejilla de los **dieciocho juegos** en Ajustes, con doce desactivados ·
      `juegos/00-filtro-seis-nuevos.png`
- [ ] El **salón** con dos o tres móviles — necesita un segundo móvil

El truco que sirvió para llegar a Tabú y a Pinturillo: en Ajustes → Juegos de la
partida, apaga los diecisiete que no quieras. El tablero se llena del que queda
y sale en la primera tirada. Ojo con el detalle que costó un rato: los dieciocho empiezan
encendidos, así que **solo hay que apagar los otros**; tocar también el que
quieres lo apaga, y con cero activos la app reparte de todos.

---

## Cómo se tomaron

```bash
ADB=~/devtools/android-sdk/platform-tools/adb.exe

# Barra de estado limpia
$ADB shell settings put global sysui_demo_allowed 1
$ADB shell am broadcast -a com.android.systemui.demo -e command clock -e hhmm 1000
$ADB shell am broadcast -a com.android.systemui.demo -e command battery -e level 100 -e plugged false
$ADB shell am broadcast -a com.android.systemui.demo -e command notifications -e visible false

$ADB exec-out screencap -p > captura.png

# Al terminar
$ADB shell am broadcast -a com.android.systemui.demo -e command exit
```

**Una cosa que costó tiempo**: conducir la app con `input tap` a ciegas se
desvía. Basta que otra app tome el foco un segundo, o que una fila mida distinto
en árabe, y los toques siguientes caen en otro sitio —pasó dos veces, y salieron
cuatro capturas de la pantalla equivocada—. Lo que sí funciona es anclar cada
secuencia a un estado conocido:

```bash
$ADB shell am force-stop es.ghatostudio.funny
$ADB shell am start -n es.ghatostudio.funny/es.ghatostudio.funny.MainActivity
# ...y desde la pantalla de inicio, que siempre es la misma
```

Y comprobar antes de cada toque que Funny sigue delante:

```bash
$ADB shell dumpsys activity activities | grep topResumedActivity
```

---

## Además de las de la ficha

Del *Definition of Done* del punto 12:

- [x] Los **seis temas** vistos en pantalla
- [x] Los **trece idiomas** vistos en pantalla
- [x] **El árabe en RTL**, con la interfaz espejada
- [x] El **bottom sheet de la donación** en los seis temas y en RTL
- [x] Probada en el dispositivo físico, con una partida jugada
- [x] Las **siete pantallas de prueba** recorridas tocando sus controles
- [ ] Los cinco juegos que comparten pantalla, abiertos uno a uno (Mímica, ¿Cuándo?, Preguntas, Canta, Desafío)
- [ ] Exportar → borrar datos → importar **en el dispositivo** (la ida y vuelta
      sí está cubierta por 21 pruebas unitarias, pero no se ha hecho a mano)
- [ ] El salón con dos móviles de verdad
