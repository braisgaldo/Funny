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
| `01-inicio.png` | Pantalla de inicio con los doce juegos y sus colores |
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
| `05-tour-juegos.png` | **Tour, paso 5: los doce juegos con lema e instrucciones** |

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

De las ocho que Play luce mejor, hay cuatro. **Faltan cuatro**, y hay que jugar
hasta que salgan porque las cartas se reparten al azar:

- [ ] Una prueba de **Tabú** con su carta y las palabras prohibidas
- [ ] **Pinturillo** con algo dibujado a medias
- [ ] La rejilla de los **doce juegos** en Ajustes, con alguno desactivado
- [ ] El **salón** con dos o tres móviles conectados — necesita un segundo móvil

Truco para las dos primeras: en Ajustes → Juegos de la partida, deja activo solo
el que quieras capturar. Así el tablero se llena de ese juego y sale en la
primera tirada.

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
- [ ] Exportar → borrar datos → importar **en el dispositivo** (la ida y vuelta
      sí está cubierta por 21 pruebas unitarias, pero no se ha hecho a mano)
- [ ] El salón con dos móviles de verdad
