# ADR-0001 — Android nativo ahora, con las capas listas para Kotlin Multiplatform

- **Fecha**: 2026-09-01
- **Estado**: aceptado
- **Decide**: Brais Galdo

## Contexto

La plantilla del proyecto (punto 3) pide una sola base de código para Android, iOS
y escritorio, y da preferencia a **Kotlin Multiplatform + Compose Multiplatform**.
El punto 1 pide, a la vez, entregar una **1.0.0 publicable** y no un prototipo, y
el punto 10 exige probar en el dispositivo Android real en cada hito.

Hay dos hechos que condicionan la decisión:

1. **El entorno de desarrollo es Windows.** El target de iOS de KMP no se puede
   compilar sin macOS y sin Xcode: se necesitan las herramientas de Apple para
   generar el framework y para enlazar. Es decir, si se montase KMP hoy, el
   `iosMain` existiría en el árbol pero **nadie podría comprobar que compila**.
2. **El punto de partida era una app Android existente** (el «Fiestón» heredado),
   con Jetpack Compose, `ViewModel` de AndroidX y acceso a `assets` por `Context`.

La combinación de las dos cosas es la que decide: montar la estructura de KMP
sin poder compilar la mitad de los targets produce un multiplataforma *nominal*,
que es peor que no tenerlo, porque da por resuelto un problema que sigue abierto.

## Decisión

Se entrega **un único módulo Android** (`:app`), pero organizado en las capas que
KMP necesita y con la regla dura del punto 3 aplicada **hacia dentro**:

```
es.ghatostudio.funny
├── dominio/      Kotlin puro. Cero imports de android.* y de androidx.*
│   ├── MotorJuego.kt      reglas del juego como transiciones de estado
│   ├── Modelos.kt         estado, ajustes, participantes, modalidades
│   ├── Reglas.kt          mazos, tablero, funciones puras
│   ├── Contenido.kt       modelo de las cartas
│   ├── textos/Clave.kt    las 392 claves de i18n
│   └── salon/Protocolo.kt mensajes y códec del salón
├── datos/        Preferencias (DataStore), copia de seguridad, carga de assets
├── plataforma/   Lo que solo existe en Android: Nearby, sonido, háptica, Custom Tabs
└── ui/           Compose, ViewModels, tema, catálogos de idioma
```

`dominio` es el candidato a `commonMain` y **hoy ya no importa nada de Android**.
Se comprueba, no se supone: hay una tarea de verificación y el paquete completo se
prueba en la JVM sin emulador (los tests de `MotorJuego`, `Reglas`, `Modalidad`,
`Protocolo` y los catálogos corren en JVM pura).

Las dos piezas que en KMP serían `expect`/`actual` están ya aisladas detrás de una
interfaz propia:

| Pieza | Interfaz en dominio/datos | Implementación de Android |
|---|---|---|
| Reglas de plural (ICU) | `ReglasDePlural` | `ReglasDePluralAndroid` |
| Transporte del salón | `TransporteSalon` | `TransporteNearby` |

## Consecuencias

**A favor**

- La 1.0.0 se puede compilar, instalar y probar de verdad, que es lo que pide el
  punto 12.
- Las reglas del juego se prueban sin emulador y sin dispositivo, y eso es lo que
  ha permitido tener 172 pruebas en verde.
- El día que haya un Mac, mover `dominio` a `commonMain` es mover ficheros: no
  hay ni un `Context` que quitar.

**En contra, y hay que decirlo**

- **iOS no existe todavía.** No hay `iosMain`, no hay proyecto de Xcode y no se ha
  compilado nada para Apple. La portabilidad está *preparada*, no *demostrada*.
- La interfaz (`ui/`) usa Jetpack Compose de AndroidX, no Compose Multiplatform.
  Migrarla no es un `sed`: hay que cambiar `androidx.compose.material3` por el de
  JetBrains, sustituir `ViewModel` de AndroidX, y rehacer las pantallas que usan
  `Canvas` con `android.graphics` (el lienzo de dibujo).

## Coste estimado de la migración a KMP

Estimación honesta, para que la decisión de aplazarla se pueda revisar con
números y no con intuición:

| Trabajo | Esfuerzo | Riesgo |
|---|---|---|
| Mover `dominio` a `commonMain` | bajo (horas) | ninguno: ya es Kotlin puro |
| `datos`: DataStore → `multiplatform-settings` o DataStore KMP | medio | medio: hay que rehacer la lectura inicial |
| `plataforma`: `expect`/`actual` para sonido, háptica y navegador | medio | bajo |
| **Transporte del salón en iOS** | **alto** | **alto**: Nearby Connections no existe en iOS; habría que escribir el transporte sobre *Multipeer Connectivity*, que es otra API con otro modelo de conexión |
| `ui` → Compose Multiplatform | alto | medio: 20 pantallas, y el lienzo de dibujo hay que rehacerlo |
| Proyecto de Xcode, firma y App Store | medio | alto: 99 €/año y una revisión que puede rechazar la donación (ver ADR-0004) |

El punto caro no es la interfaz: es el salón. El protocolo (`dominio/salon`) sí es
portable —es JSON y Kotlin puro—, pero **la radio no**, y esa es la parte que hay
que reescribir entera para iOS.

## Alternativas descartadas

- **KMP completo desde el principio.** Descartada por lo dicho: sin macOS, el
  target de iOS no se puede compilar, y presentarlo como hecho sería falso.
- **Flutter.** Habría obligado a tirar la app existente y a reescribir en Dart una
  base que ya funcionaba en Compose. El punto 3 permite proponerlo, pero no hay
  ninguna ventaja que compense empezar de cero.
