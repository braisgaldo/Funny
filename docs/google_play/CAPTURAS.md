# Capturas para la ficha

**Estado: pendientes.** No hay ninguna hecha, y no se pueden inventar: la ficha
tiene que enseñar la app de verdad. En el momento de escribir esto el dispositivo
de pruebas está desconectado (`adb devices` sale vacío).

---

## Lo que Play exige

| | Requisito |
|---|---|
| Cantidad | mínimo 2, máximo 8 por formato |
| Proporción | 16:9 o 9:16 |
| Lado mínimo | 320 px · lado máximo 3840 px |
| Formato | PNG o JPEG de 24 bits, **sin transparencia** |
| Teléfono | obligatorio |
| Tablet 7″ y 10″ | opcional, pero mejora la posición en la ficha |

Nada de maquetas con marcos de móvil ni texto promocional encima: Play lo permite,
pero una captura limpia envejece mejor y no hay que rehacerla al cambiar de idioma.

---

## Cómo capturar

Con el móvil conectado y el idioma que toque puesto en **Ajustes de Funny**, no en
los del sistema (la app tiene locale propia):

```bash
adb exec-out screencap -p > 01-inicio.png
```

O el atajo del propio móvil, y luego:

```bash
adb pull /sdcard/Pictures/Screenshots/ .
```

Antes de empezar, para que no salga ruido en la barra de estado:

```bash
adb shell settings put global sysui_demo_allowed 1
adb shell am broadcast -a com.android.systemui.demo -e command clock -e hhmm 1000
adb shell am broadcast -a com.android.systemui.demo -e command battery -e level 100 -e plugged false
adb shell am broadcast -a com.android.systemui.demo -e command notifications -e visible false
# Y al terminar:
adb shell am broadcast -a com.android.systemui.demo -e command exit
```

---

## Las ocho capturas, en este orden

El orden importa: Play las muestra así y las dos primeras son las que se ven en la
lista de resultados. Van primero las que explican **qué es** y luego las que
explican **por qué es distinto**.

| # | Pantalla | Tema | Por qué esta |
|---|---|---|---|
| 1 | Inicio, con el título y los botones | Fiesta (oscuro) | La primera impresión: se ve qué es en dos segundos |
| 2 | Tablero a media partida, con tres fichas | Fiesta | Se entiende el juego sin leer nada |
| 3 | Una prueba de **Tabú** con su carta y el cronómetro | Neón | La prueba más reconocible del género |
| 4 | **Pinturillo** con algo dibujado a medias | Papel (claro) | Enseña que hay un tema claro, y es la pantalla más vistosa |
| 5 | La rejilla de los **doce juegos** en Ajustes | Medianoche | La cantidad de contenido, de un vistazo |
| 6 | El **salón** con tres móviles conectados | Fiesta | La función que no tiene la competencia |
| 7 | Selector de **modalidad**, con «a mi manera» abierto | Menta (claro) | Que la partida dura lo que tú digas |
| 8 | Selector de **idioma**, con las trece insignias | Atardecer (claro) | Trece idiomas, y de paso el sexto tema |

Con esas ocho salen **los seis temas** repartidos, lo cual también sirve para el
punto del *Definition of Done* que pide verlos todos.

### Contenido de las capturas

Los nombres de equipo que aparezcan deben ser inventados y neutros: «Los Cracks»,
«Las Panteras», «Equipo Rojo». Nada de nombres de personas reales, ni siquiera
conocidas.

En la del salón, que los nombres de los dispositivos no sean el modelo real de
nadie.

---

## Además de las de la ficha

Estas no van a Play, pero las pide el *Definition of Done* del punto 12 y hay que
hacerlas en la misma sesión:

- [ ] Los **seis temas**, la misma pantalla en cada uno, para comparar
- [ ] Los **trece idiomas**, la pantalla de inicio en cada uno
- [ ] **El árabe en RTL**, con la interfaz espejada — esta es la importante:
      demuestra que el RTL funciona de verdad y no solo que el texto está traducido
- [ ] El **bottom sheet de la donación** en los seis temas y en RTL
- [ ] Exportar → borrar datos → importar, con el estado antes y después

## Nombres de fichero

```
graficos/capturas/telefono/01-inicio.png
graficos/capturas/telefono/02-tablero.png
...
graficos/capturas/temas/fiesta.png
graficos/capturas/idiomas/ar-rtl.png
```
