# 🎉 Funny

Juego de fiesta para Android. **Dieciocho pruebas**, tres modos de juego, cuatro
modalidades de partida y trece idiomas. Se puede jugar **con un solo móvil que va
de mano en mano** o con **varios móviles conectados entre sí**, sin internet y sin
router.

Gratis, sin anuncios, sin compras y sin recoger ningún dato.

---

## El juego

Cada participante tiene una ficha en un tablero. En su turno tira el dado (1–3),
avanza, y la casilla en la que cae decide la prueba. Si la supera se queda ahí; si
falla, vuelve a donde estaba. Gana quien llega a la **META** y supera la prueba
final.

### Las dieciocho pruebas

| | Prueba | Qué hay que hacer |
|---|---|---|
| 🎭 | **Mímica** | Representarlo sin hablar. |
| 🎨 | **Pinturillo** | Dibujarlo en la pantalla mientras los demás adivinan. |
| 📅 | **¿Cuándo?** | Acertar en qué año ocurrió algo, entre cuatro opciones. |
| ❓ | **Preguntas** | Cultura general, cuatro respuestas. |
| 🤐 | **Tabú** | Describirlo sin decir la palabra ni cuatro más. |
| ⚡ | **Reto rápido** | Enumerar cosas de una categoría contrarreloj. |
| 🍿 | **Emojis** | Descifrar la película o la serie a partir de unos emojis. |
| 🤥 | **¿Te lo crees?** | Verdadero o falso, con la explicación al final. |
| 👅 | **Trabalenguas** | Decirlo sin trabarse, una a tres veces. |
| 🔢 | **Ordena** | Poner cuatro cosas en su orden correcto. |
| 🎤 | **Canta** | Seguir la canción a partir del título y una pista. |
| 🤸 | **Desafío** | Atreverse con una prueba física o absurda. |
| 📜 | **Refranes** | Completar el refrán entre cuatro finales, y los otros tres son de refranes de verdad. |
| ⏳ | **¿Antes o después?** | Dos hechos: decir cuál pasó primero. Al resolver salen los dos años. |
| 🔤 | **Anagramas** | Unas letras revueltas y cuatro palabras candidatas. |
| 🗣 | **Acentos** | Decir una frase con la voz que pida la carta: presentador, robot sin batería, villano. |
| 🔊 | **Sonidos** | Imitar un ruido solo con la boca. Sin palmas, sin golpes, sin objetos. |
| 🔗 | **Encadenados** | Cada palabra empieza por la última sílaba de la anterior, contrarreloj. |

### Los tres modos

- **Por equipos** (2–6 equipos) — el clásico. Cada equipo tiene sus miembros y va
  rotando quién actúa.
- **Individual** (2–8 jugadores) — sin equipos, cada uno a lo suyo.
- **Reto en solitario** (1 jugador) — sin tablero: una tanda de pruebas seguidas,
  puntuación y mejor marca personal.

### Las cuatro modalidades

Deciden cuánto dura la partida: cuántas casillas hay hasta la meta y cuántas
pruebas tiene el reto en solitario.

| | Modalidad | Casillas | Pruebas | Aprox. |
|---|---|---|---|---|
| ⚡ | **Partida rápida** | 12 | 6 | ~18 min |
| 🎲 | **Partida normal** | 20 | 10 | ~30 min |
| 🔥 | **Partida extrema** | 32 | 16 | ~48 min |
| 🎛 | **A mi manera** | 8–40 | 4–24 | lo que salga |

Y, aparte, se puede desactivar cualquiera de los dieciocho juegos para que no
salga en la partida.

### El salón: varios móviles a la vez

Un móvil hace de **mesa** (el hub) y hasta cuatro más se conectan a él por
Bluetooth o Wi-Fi Direct. **No hace falta internet, ni router, ni emparejar
nada.**

No es un adorno: sirve para lo que con un solo móvil no se puede hacer.

- La palabra secreta del tabú llega **solo** al móvil de quien actúa, y no la ve
  media mesa de refilón.
- En las casillas de «juegan todos», cada uno responde en su propio móvil y al
  mismo tiempo.

---

## Instalación

- **Google Play** — pendiente de publicación.
- **APK** — en las [GitHub Releases](https://github.com/braisgaldo/Funny/releases).
- **F-Droid** — previsto (la licencia lo permite, ver más abajo).

Requiere **Android 8.0 (API 26)** o superior. Para el salón hacen falta Google
Play Services; sin ellos la app avisa y sigue funcionando con un solo móvil.

Para compilar desde el código, ver [docs/INSTALL.md](docs/INSTALL.md).

---

## Privacidad

Funny **no declara el permiso de internet**. No es un descuido: está comentado en
el propio `AndroidManifest.xml` para que quede claro que es deliberado. La app no
puede hablar con ninguna red aunque quisiera.

- Sin analítica, sin telemetría, sin trazas remotas.
- Sin cuentas y sin registro.
- Todo lo que guarda vive en el móvil y se mueve con la exportación a un fichero
  `.funny.bak`.

El único enlace externo es el de la donación, y lo abre el navegador del sistema.

Política completa: [docs/PRIVACIDAD.md](docs/PRIVACIDAD.md).

---

## Invítame a un café ☕

Funny es gratuita, no tiene anuncios y no recoge datos. Si te resulta útil, puedes
invitarme a un café en **[revolut.me/brais2oz6](https://revolut.me/brais2oz6)**.

**La donación no desbloquea nada.** Ni funciones, ni temas, ni contenido. Los seis
temas, los trece idiomas, los dieciocho juegos y el salón están ahí desde la primera
vez que abres la app. No hay «versión completa» porque no hay versión incompleta.

No hay ninguna librería de facturación en el binario, y hay una tarea de Gradle
que falla la build si alguna se cuela. El razonamiento entero está en el
[ADR-0004](docs/adr/ADR-0004-donacion-sin-facturacion.md).

---

## Para desarrolladores

### Stack

Kotlin + Jetpack Compose (Material 3), un solo módulo Android con las capas
organizadas para poder migrar a Kotlin Multiplatform. Ver el
[ADR-0001](docs/adr/ADR-0001-stack.md), donde está también el coste estimado de
esa migración y lo que hoy **no** está hecho.

```
dominio/     Kotlin puro, cero imports de android.* — las reglas del juego
datos/       DataStore, copia de seguridad, carga de contenido
plataforma/  lo que solo existe en Android: Nearby, sonido, háptica, navegador
ui/          Compose, ViewModels, tema, los trece catálogos de idioma
```

### Compilar y probar

```bash
./gradlew :app:assembleDebug          # APK de depuración
./gradlew :app:testDebugUnitTest      # 172 pruebas, sin emulador
./gradlew :app:check                  # las pruebas + ktlint + lint + verificaciones propias
```

`check` incluye dos verificaciones escritas para este proyecto:

- `verificarSinFacturacion` — falla si aparece cualquier librería de pagos.
- `verificarTextosLiterales` — falla si hay una cadena escrita a fuego en `ui/`.

### Documentación

| | |
|---|---|
| [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) | Cómo está montado y por qué |
| [docs/INSTALL.md](docs/INSTALL.md) | Compilar, firmar e instalar |
| [docs/adr/](docs/adr/) | Las decisiones, con sus consecuencias |
| [CHANGELOG.md](CHANGELOG.md) | Qué cambió en cada versión |
| [CONTRIBUTING.md](CONTRIBUTING.md) | Cómo aportar |
| [SECURITY.md](SECURITY.md) | Cómo avisar de un problema de seguridad |

---

## Licencia

Dos licencias, una para cada cosa (el porqué está en el
[ADR-0006](docs/adr/ADR-0006-licencia.md)):

- **El código** — [GPL-3.0-or-later](LICENSE). Quien distribuya un derivado tiene
  que publicar su código con la misma licencia.
- **El contenido** — [CC BY-SA 4.0](LICENSE-CONTENIDO). Cubre las 3.194 cartas de
  los dieciocho juegos, las 413 claves traducidas y esta documentación.

Los títulos de canciones, películas y series que aparecen en las cartas son de sus
titulares y se nombran solo para identificarlos. **No se distribuye ninguna letra
de canción**; el mazo de Canta lleva título, artista y una pista escrita a mano
([ADR-0005](docs/adr/ADR-0005-contenido-sin-derechos.md)).

---

Hecho por **Brais Galdo** · Ghato Studio · GhatoStudioOfficial@gmail.com
