# ADR-0005 — El contenido no distribuye material con derechos de autor

- **Fecha**: 2026-09-01
- **Estado**: aceptado
- **Decide**: Brais Galdo

## Contexto

Funny lleva **3.194 cartas** escritas a mano (1.668 en castellano y 1.526 en
inglés), repartidas en dieciocho mazos. Dos de esos mazos rozan material protegido:

- **🎤 Canta**: el juego consiste en arrancar a cantar una canción conocida.
- **🎬 Emojis**: hay que adivinar una película o serie a partir de emojis.

Un juego de fiesta que incluyese **letras de canciones** estaría distribuyendo
obra protegida. Una letra de canción es una obra literaria con su propio titular
de derechos, y meter dos versos en el APK es distribuirlos, aunque sean dos. Que
lo hagan otros juegos del género no lo hace legal.

Los **títulos** son otra cosa: un título no es obra protegible por sí mismo, y
nombrar una canción o una película para referirse a ella es exactamente lo que
hace cualquier reseña.

## Decisión

**En el mazo de Canta no se escribe ni un verso.** Cada carta tiene tres campos y
ninguno es letra:

```json
{ "titulo": "Bohemian Rhapsody", "artista": "Queen", "pista": "Empieza por el «mamaaa»" }
```

- `titulo` — el nombre de la canción.
- `artista` — quién la canta.
- `pista` — **una descripción de por dónde arrancar, escrita con mis palabras**,
  no una cita. «Empieza por el "mamaaa"» describe el arranque; no reproduce el
  verso.

Quien canta es la persona que está jugando, de memoria. La app no le da la letra,
igual que un karaoke sin pantalla.

Lo mismo con Emojis: la carta lleva el **título** de la película o serie, una
secuencia de emojis escrita por mí, y hasta tres pistas propias. Ni sinopsis
copiada, ni carteles, ni fotogramas.

El fichero `canta.json` lleva la razón escrita **dentro**, en un campo
`_comentario`, para que quien añada cartas dentro de dos años se encuentre con la
regla antes de romperla:

> Canciones muy conocidas. AQUÍ NO SE ESCRIBE NI UN VERSO DE LETRA: solo el
> título, quién la canta y una pista de por dónde arrancar. Es una decisión
> deliberada para no distribuir material con derechos de autor (ver ADR-0005).

## Lo mismo aplica al resto del contenido

- **Preguntas de cultura general**: hechos verificables redactados por mí. Un
  hecho no es propiedad de nadie; la redacción de una pregunta concreta de otro
  juego, sí. No se ha copiado ninguna.
- **Trabalenguas**: los tradicionales son de dominio público. Los que no lo son
  están escritos para la app.
- **Tabú, mímica, dibujo, ordena, verdadero o falso, retos, desafíos, ¿cuándo?**:
  palabras comunes y hechos. Nada tomado de un juego comercial.
- **Iconografía**: todo lo que se dibuja en pantalla —la taza de café de la
  donación, las fichas, el tablero— es vectorial y hecho en Compose. No hay ni un
  PNG de terceros en el APK.
- **Sonido**: se usa el `ToneGenerator` del sistema, no ficheros de audio. No hay
  ninguna muestra con licencia que revisar.

## Consecuencias

**A favor**

- No hay ningún titular de derechos que pueda pedir la retirada de la app, y no
  hay que documentar licencias de contenido de terceros en «Acerca de» porque no
  hay contenido de terceros.
- El APK es pequeño: 3.194 cartas de texto pesan menos que una sola imagen.

**En contra**

- **El juego de Canta es más difícil.** Sin la letra en pantalla, quien no se
  acuerde de la canción se queda mirando el título. Se mitiga con la pista y
  eligiendo canciones muy conocidas, pero es una pérdida real de comodidad
  respecto a un juego que sí pusiera la letra. Es un intercambio consciente:
  prefiero un juego un poco más difícil a distribuir letras que no son mías.
- Escribir 3.194 cartas a mano es lento. La alternativa —copiarlas de algún
  sitio— no era una alternativa.

## Cómo se vigila

`PruebaContenido` (17 pruebas) valida los doce mazos de los dos idiomas en cada
build: campos obligatorios presentes, cuatro opciones distintas en las preguntas,
índice de respuesta correcta dentro de rango, cuatro palabras prohibidas por carta
de tabú, ningún señuelo que se autorreferencie, equilibrio ~50/50 en verdadero o
falso, y —lo que importa aquí— que **cada canción tiene título y artista y que su
pista no pasa de 60 caracteres**. Ese tope es el que vigila esta decisión: una
pista larga sería una letra disfrazada, y el modelo `Cancion` no tiene ningún otro
campo de texto donde pudiera esconderse una.
