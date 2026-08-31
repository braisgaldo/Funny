# 🎉 Fiestón

Juego de fiesta por equipos para Android, en español y al estilo *Party & Co*,
pensado para jugarse **con un único móvil** que va pasando de mano en mano.

## El juego

Cada equipo tiene una ficha en un tablero. En su turno tira el dado (1–3), avanza
y la casilla en la que cae decide la prueba. Si la superan se quedan ahí; si
fallan vuelven a donde estaban. Gana el primer equipo que llega a la **META** y
supera la prueba final.

### Las seis pruebas

| Prueba | Qué hay que hacer |
|---|---|
| 🎭 **Mímica** | Representar la palabra sin hablar. |
| 🎨 **Pinturillo** | Dibujar en la pantalla del móvil mientras el equipo adivina. |
| 📅 **¿Cuándo?** | Ver un acontecimiento y acertar en qué año ocurrió (4 opciones). |
| ❓ **Preguntas** | Pregunta de cultura general con cuatro respuestas. |
| 🤐 **Tabú** | Describir una palabra sin usar las cuatro prohibidas. |
| ⚡ **Reto rápido** | Enumerar cosas de una categoría contrarreloj. |

### Casillas especiales

- 🃏 **Comodín** — el equipo rival elige qué prueba te toca.
- 👥 **Todos juegan** — la misma pregunta para toda la mesa, por turnos y sin ver
  las respuestas de los demás. Cada equipo que acierte avanza una casilla.
- 🏁 **Meta** — prueba final al azar; solo se gana superándola.

## Varios jugadores

En la pantalla de equipos se crean de 2 a 6 equipos y se apuntan los nombres de
quienes juegan en cada uno. En mímica, dibujo y tabú la app va rotando y dice en
cada turno a quién le toca actuar, para que no se lo coma siempre el mismo.

Los equipos y los ajustes se guardan entre sesiones.

## Contenido

Todo el contenido está en `app/src/main/assets/`, en JSON fácil de ampliar:

| Fichero | Contenido |
|---|---|
| `mimica.json` | 217 palabras y situaciones para representar |
| `dibujo.json` | 261 palabras dibujables |
| `cuando.json` | 151 acontecimientos con su año |
| `preguntas.json` | 121 preguntas de cultura general |
| `tabu.json` | 117 cartas con sus palabras prohibidas |
| `retos.json` | 100 retos de enumerar |

Las cuatro opciones de año de la prueba «¿Cuándo?» se generan al vuelo alrededor
del año correcto, con un margen que se adapta a la época (más amplio para hechos
antiguos, más estrecho para los recientes), así que no se repiten partida a
partida.

## Ajustes

- **Ritmo**: rápido / normal / tranquilo (escala el tiempo de todas las pruebas).
- **Duración**: partidas de 12, 20 o 28 casillas.
- **Sonido y vibración**: se pueden apagar.

## Compilar

Requiere JDK 17 y el SDK de Android (plataforma 35 y build-tools 35). La ruta al
SDK se indica en `local.properties`.

```bash
./gradlew assembleDebug
./gradlew installDebug          # instala en el móvil conectado por ADB
```

El APK queda en `app/build/outputs/apk/debug/app-debug.apk`.

## Estructura

```
app/src/main/
├── assets/                  contenido del juego en JSON
└── java/com/fieston/
    ├── MainActivity.kt
    ├── datos/               carga de assets, mazos y preferencias
    ├── juego/               JuegoViewModel: reglas y estado de la partida
    ├── modelo/              equipos, categorías, tablero, estado
    └── ui/
        ├── comun/           componentes compartidos, cronómetro, sonidos
        ├── pantallas/       menú, equipos, tablero, resultado, victoria…
        │   └── pruebas/     una pantalla por tipo de prueba
        └── tema/            colores y tipografía
```

Hecho con Kotlin y Jetpack Compose. `minSdk 24`, sin dependencias de red.
