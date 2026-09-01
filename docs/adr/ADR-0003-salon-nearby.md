# ADR-0003 — El salón multidispositivo sobre Nearby Connections

- **Fecha**: 2026-09-01
- **Estado**: aceptado
- **Decide**: Brais Galdo

## Contexto

El brief (punto 14) pide que **varios dispositivos se conecten por Wi-Fi Direct o
Bluetooth** para seguir la partida juntos, con uno de ellos de hub central. La
razón de que eso sea deseable no es tecnológica, es del juego: cuando la palabra
secreta de un tabú va en el móvil que se pasa de mano en mano, la mitad de la mesa
la ve de refilón. Con un móvil por persona, **la palabra llega solo a quien actúa**.

Opciones reales en Android:

| Opción | Qué implica |
|---|---|
| `WifiP2pManager` (Wi-Fi Direct a pelo) | Descubrimiento, invitación, grupo, sockets TCP a mano. Una máquina de estados larga que falla distinto en cada fabricante. |
| Bluetooth clásico (RFCOMM) | Obliga a **emparejar los móviles a mano** en los ajustes del sistema antes de jugar. Inaceptable para «llegas a una cena y jugáis». |
| BLE con GATT | Pensado para paquetes pequeños; trocear mensajes de partida a mano. |
| **Nearby Connections** | Negocia Bluetooth, BLE y Wi-Fi Direct por su cuenta, elige el mejor canal, y resuelve descubrimiento, emparejamiento, reintentos y troceado. |

## Decisión

Se usa **Nearby Connections** (`com.google.android.gms:play-services-nearby`) con
estrategia **`P2P_STAR`**: un hub y hasta cuatro mandos, que es exactamente la
forma del salón. No `P2P_CLUSTER`, porque una malla de todos contra todos no
aporta nada aquí y multiplica los estados posibles.

El transporte está **detrás de una interfaz propia**, `TransporteSalon`, con tres
implementaciones:

- `TransporteNearby` — la de verdad.
- `TransporteNoDisponible` — cuando no hay Play Services. Responde a todo que no
  puede, y la app **sigue siendo jugable con un solo móvil**.
- `TransporteDeMentira` — solo en tests: pone dos «móviles» en la misma JVM y
  enruta cada mensaje por el `Codec` real.

El protocolo (`dominio/salon/Protocolo.kt`) es **Kotlin puro y JSON escrito a
mano**: mensajes `Hola`, `Salon`, `Vista`, `Accion`, `Adios` y `Desconocido`, con
un `VERSION_PROTOCOLO` que viaja en el identificador del servicio
(`es.ghatostudio.funny.salon.v1`), de forma que **dos móviles con protocolos
incompatibles no llegan ni a verse**.

### Sobre el punto 6 («evita servicios de pago de Google»)

Nearby Connections forma parte de Google Play Services y **es gratuita**: no tiene
cuota, ni clave de API, ni consola de facturación, ni cuota por dispositivo. No es
un servicio de Google Cloud. Por eso no hace falta parar a pedir confirmación
según el punto 6, y no hay presupuesto que vigilar.

### Modelo de autoridad

El hub es la **única** fuente de verdad. Un mando no calcula nada: envía la acción
que ha pulsado su dueño y recibe la vista que le toca. Esto es deliberado y no es
una limitación:

- El motor de reglas (`MotorJuego`) se aplica **una sola vez y en un solo sitio**,
  así que no hay forma de que dos móviles discrepen sobre en qué casilla está un
  equipo.
- El hub comprueba **de quién** viene cada acción: una pulsación de «superada» que
  llegue de un móvil al que no le toca se ignora.
- El contenido privado se enruta: `vistaPara()` manda la palabra del tabú **solo**
  al dispositivo de quien actúa.

Consecuencia directa: si el hub se va, la partida se acaba. Es aceptable porque el
hub es el móvil que hace de mesa y está ahí puesto encima de ella.

## Consecuencias

**A favor**

- La palabra secreta deja de ser un secreto a voces.
- En las casillas de «juegan todos», cada uno responde en su móvil a la vez, que
  es un juego que con un solo dispositivo no se puede hacer.
- Cero configuración: no hay que emparejar nada ni conectarse a ninguna red.
- Sin internet. Ni siquiera hace falta que haya router.

**En contra**

- **Depende de Google Play Services.** En un móvil sin ellos (algunos ROM
  alternativas, algún dispositivo chino sin GMS) el salón no está disponible. La
  app avisa y sigue funcionando con un móvil. Es la razón de que
  `TransporteNoDisponible` exista.
- **Permisos incómodos de explicar.** Por debajo de Android 13, el sistema exige
  permiso de **ubicación** para buscar por Bluetooth. Se pide solo hasta Android 12
  (`maxSdkVersion="32"`), se declaran la fina y la gruesa juntas, y la pantalla del
  salón explica **para qué** antes de pedirlo. En Android 13+ basta
  `NEARBY_WIFI_DEVICES` con `neverForLocation`.
- **Máximo cuatro mandos** (`MAXIMO_MANDOS = 4`), por la topología en estrella y
  porque el modo individual admite ocho participantes: con cuatro móviles ya se
  cubre el caso realista de una mesa.

## Qué está probado y qué no — importante

`PruebaSalon` (14 pruebas) y `PruebaProtocolo` (13) cubren, en la JVM y sin
dispositivos:

- que cada mensaje sobrevive la ida y vuelta por JSON sin perder campos;
- que un mensaje ilegible o de una versión futura se convierte en `Desconocido` en
  lugar de tumbar el salón;
- que el hub aplica las acciones y reparte las vistas correctas;
- **que el contenido privado llega solo a quien actúa**;
- que una acción de quien no tiene el turno se ignora.

**Lo que no está probado**: la radio. Descubrimiento real, permisos concedidos y
denegados, alcance, interferencias, reconexión al alejarse y volver, y el
comportamiento cuando el hub se apaga a mitad de partida. Eso **solo se puede
comprobar con dos móviles delante**, y en el momento de escribir esto el
dispositivo de pruebas está desconectado. Está anotado como tal en el KDoc de
`TransporteDeMentira` y en la sección de pruebas del manual técnico: no se
presenta como verificado.
