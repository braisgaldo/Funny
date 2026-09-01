# Material para Google Play

Todo lo que hay que pegar en Play Console, y los scripts que lo generan.

El procedimiento paso a paso está en [../GUIA-PUBLICACION.md](../GUIA-PUBLICACION.md).
Esta carpeta es el material; ese documento es el orden en el que usarlo.

---

## Qué hay aquí

```
ficha/<idioma>/          titulo.txt, corta.txt, larga.txt · 13 idiomas
graficos/                icono-512.png, icono-1024.png, destacado-1024x500.png
formularios/             las respuestas de los formularios de Play, ya decididas
politicas/               dónde guardar las capturas de las políticas consultadas
CAPTURAS.md              qué capturas hacen falta y cómo hacerlas
generar-ficha.py         escribe y VALIDA los textos
generar-graficos.py      dibuja el icono y el gráfico destacado
preparar-binarios.py     compila y copia el AAB y el APK aquí
binarios/                el AAB y el APK — NO va en git, ver más abajo
```

## Regenerar

```bash
python docs/google_play/generar-ficha.py      # textos, con validación
python docs/google_play/generar-graficos.py   # icono y gráfico destacado
python docs/google_play/preparar-binarios.py  # AAB y APK
```

`generar-ficha.py` **valida y falla** si algo no cumple: título de más de 30
caracteres, descripción corta de más de 80, larga de más de 4000, un juego sin
enumerar, o vocabulario prohibido por el punto 4.4.1 de la plantilla (*comprar*,
*pagar*, *desbloquear*, *pro*, *premium*, *suscripción*, *precio*) en cualquiera
de los trece idiomas.

Esa última comprobación no es cosmética: es lo que mantiene coherente la
declaración de «compras integradas: No». Su lista está alineada a propósito con
la del test `la donacion no usa vocabulario de compra en ninguno de los trece
idiomas`, para que la app y la ficha no acaben con criterios distintos.

Encontró tres infracciones propias al escribirla, todas en frases que **negaban**
la compra: «nothing to unlock», «sans achats» y «без доплат». La regla no
distingue entre afirmar y negar, y la app tampoco, así que se reescribieron.

---

## El icono es el mismo que el de la app

`generar-graficos.py` no dibuja un icono nuevo: reproduce las formas y los
colores exactos de `app/src/main/res/drawable/ic_launcher_foreground.xml` sobre
el mismo fondo `#12071F`. La gente reconoce una app en la tienda por su icono, y
un icono de tienda distinto del de la app es una forma tonta de perder
instalaciones.

**Si alguien cambia el vector de la app, hay que cambiar también `FORMAS` en el
script.** Son dos sitios y no hay forma barata de leer un `<vector>` de Android
desde Python. Está avisado en la cabecera del fichero.

---

## Los binarios y una contradicción de la plantilla

El punto 14 del brief pide «una carpeta google_play con el apk y todo lo
necesario». El punto 8 dice que los binarios generados **no se commitean** y que
se publican como assets de la Release.

Las dos cosas no pueden ser a la vez, así que:

- `preparar-binarios.py` compila y deja el AAB y el APK en `binarios/`, que está
  en `.gitignore`.
- El material que sí es fuente —textos, gráficos, formularios, checklists— va en
  commits.
- Los binarios de cada versión viven en su GitHub Release, que es donde tienen
  sentido: un APK en el repositorio queda desfasado al segundo commit y engorda
  el clon para siempre.

Un `git clone` de este repositorio no trae el APK. Lo trae `preparar-binarios.py`
en un minuto, o la Release correspondiente.

---

## Lo que falta y no se puede inventar

- **Las capturas.** Ver [CAPTURAS.md](CAPTURAS.md). Hay que hacerlas en un
  dispositivo real; una ficha de tienda tiene que enseñar la app de verdad, y en
  el momento de escribir esto el móvil de pruebas está desconectado.
- **Las capturas de las políticas de pago consultadas.** Ver `politicas/`. Las
  políticas cambian y una captura de hoy no vale para una publicación de dentro
  de tres meses: es el último paso antes de subir.
