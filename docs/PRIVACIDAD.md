# Política de privacidad de Funny

**Última actualización: 1 de septiembre de 2026**
**Responsable: Brais Galdo (Ghato Studio) · GhatoStudioOfficial@gmail.com**

---

## En una línea

**Funny no recoge ningún dato tuyo, no los envía a ninguna parte y no puede
hacerlo: la app no tiene acceso a internet.**

---

## Por qué eso es comprobable y no una promesa

Funny **no declara el permiso `INTERNET`** en su manifiesto de Android. Sin ese
permiso, el sistema operativo **impide** que la app abra cualquier conexión de red.
No es una decisión que dependa de mi buena voluntad: es el propio Android el que lo
bloquea.

Puedes comprobarlo tú:

- En el móvil: Ajustes → Aplicaciones → Funny → Permisos. No hay ninguno de red.
- Con las herramientas de Android: `adb shell dumpsys package es.ghatostudio.funny`
  y busca en la lista de permisos.
- En el código: `app/src/main/AndroidManifest.xml`, donde además está escrito por
  qué no se declara.

Consecuencia directa: **no hay analítica, no hay telemetría, no hay informes de
fallos remotos, no hay publicidad y no hay identificadores publicitarios.** No
porque haya decidido no usarlos, sino porque no podrían funcionar.

---

## Qué guarda la app, y dónde

Todo esto vive **solo en tu dispositivo**, en el almacenamiento privado de la app:

| Qué | Para qué |
|---|---|
| Tus ajustes (tema, idioma, modalidad, ritmo, sonido, vibración, juegos activos) | Para que la app esté como la dejaste |
| Los nombres de equipos y jugadores de la última partida | Para no volver a escribirlos |
| Tu mejor marca del reto en solitario | Para poder batirla |
| Si ya te enseñé el tour, y el estado de la propuesta de café | Para no repetirme |

**No se guarda**: tu nombre real, tu correo, tu ubicación, tus contactos, tus
fotos, tu identificador de dispositivo, ni nada que permita identificarte. Los
nombres de equipo los escribes tú y pueden ser lo que quieras; la app no los
comprueba ni sabe a quién corresponden.

Cuando desinstalas Funny, Android borra todo eso. No queda copia en ningún sitio,
porque no hay ningún sitio.

---

## Copia de seguridad del sistema

Si tienes activada la copia de seguridad de Android, esos ajustes pueden entrar en
tu propia copia de Google Drive, **cifrada y bajo tu cuenta**. Eso es un servicio
de Google que tú controlas: yo no tengo acceso a esa copia y no participo en ella.

Puedes desactivarlo en Ajustes de Android → Google → Copia de seguridad.

Lo que se incluye está declarado en `app/src/main/res/xml/reglas_copia.xml`: los
ajustes y nada más.

---

## Exportar tus datos

Puedes exportar todo a un fichero `.funny.bak` desde Ajustes. Ese fichero **lo
guardas tú donde quieras** y no se sube a ninguna parte. Es texto JSON: puedes
abrirlo con cualquier editor y leer exactamente qué contiene.

Para borrar todo sin desinstalar: Ajustes → importar con la opción de reemplazar, o
Ajustes de Android → Aplicaciones → Funny → Almacenamiento → Borrar datos.

---

## Permisos, uno por uno

Funny pide permisos **solo** para el salón multidispositivo, la función que conecta
varios móviles en la misma partida. Si no lo usas, no se te pedirá ninguno.

| Permiso | Para qué | Cuándo |
|---|---|---|
| Bluetooth (buscar, anunciar, conectar) | Encontrar los otros móviles y hablar con ellos | Android 12+ |
| Dispositivos Wi-Fi cercanos | Lo mismo por Wi-Fi Direct, que es más rápido | Android 13+ |
| Ubicación (aproximada y precisa) | **El sistema la exige** para buscar por Bluetooth en versiones antiguas de Android | **Solo hasta Android 12** |
| Estado y cambio de Wi-Fi | Para que Wi-Fi Direct pueda levantar el grupo | siempre |

Sobre la ubicación, que es la que más chirría: **la app no usa tu ubicación para
nada**. No la lee, no la guarda y no podría enviarla. Es Android quien, en las
versiones anteriores a la 13, obliga a tener ese permiso para poder buscar
dispositivos Bluetooth cercanos. Por eso:

- Se pide **solo hasta Android 12** (`maxSdkVersion="32"`); en Android 13 y
  posteriores no se pide en absoluto.
- El permiso de Wi-Fi cercano se declara con `neverForLocation`, que le dice al
  sistema, de forma vinculante, que no se va a usar para deducir dónde estás.
- La pantalla del salón te explica para qué es **antes** de pedírtelo.

---

## Datos que salen del dispositivo: solo entre vosotros

Cuando jugáis con varios móviles, los móviles se hablan **directamente** entre
ellos por Bluetooth o Wi-Fi Direct. No pasa por internet, ni por un router, ni por
ningún servidor mío o de nadie.

Lo que se intercambian es únicamente lo del juego: los nombres de los
participantes, en qué casilla está cada uno, la carta que toca y las pulsaciones de
los botones. Nada más: el protocolo no tiene ningún mensaje capaz de leer datos de
un dispositivo.

Ese intercambio dura lo que dura la partida y no se guarda en ninguna parte.

---

## El enlace de la donación

Ajustes tiene una opción para invitarme a un café. Si la pulsas, se abre
**el navegador de tu móvil** con `https://revolut.me/brais2oz6`.

- La app **no** hace esa petición: la hace tu navegador, con sus propias reglas de
  privacidad.
- La app no sabe si has llegado a la página, ni si has donado. No tiene forma de
  saberlo, y por eso al volver dice «gracias por pasarte por ahí» y nunca «gracias
  por tu donación».
- Revolut, como cualquier pasarela, tendrá su propia política de privacidad; queda
  entre tú y ellos.
- Si no quieres abrir el navegador, hay un **código QR generado dentro de la app,
  sin red**, y la opción de copiar el enlace.

También hay un botón de **compartir**, que abre el selector del sistema con un
texto y un enlace a la ficha de la app. Lo que hagas después es cosa tuya y de la
app que elijas.

---

## Menores

Funny no está dirigida específicamente a menores, pero es un juego de fiesta apto
para jugar en familia. Al no recoger ningún dato personal, tampoco recoge datos de
menores. No hay chat, ni contenido generado por usuarios, ni ninguna forma de que
alguien de fuera contacte con quien juega.

---

## Tus derechos

El RGPD te da derecho a acceder a tus datos, corregirlos, borrarlos, oponerte a su
tratamiento y llevártelos.

Aquí eso se resuelve solo, porque **yo no tengo ningún dato tuyo**:

- **Acceder** — están en tu móvil; exporta el `.funny.bak` y ábrelo con un editor.
- **Llevártelos** — el mismo fichero, en JSON legible.
- **Borrarlos** — desinstala la app o borra sus datos desde los ajustes de Android.
- **Corregirlos** — cámbialos en la propia app.

No hay ningún formulario que rellenar ni ninguna solicitud que enviarme, porque no
hay nada mío que borrar.

---

## Si esto cambia

Si alguna versión futura cambiase algo de esto —por ejemplo, si se añadiera alguna
función que necesitase red— aparecería **antes** en este documento, con la fecha, en
el CHANGELOG y en la ficha de Google Play. Cualquier recogida de datos sería
opcional y con consentimiento explícito, nunca por defecto.

Este documento vive en el repositorio, así que su historial de cambios es público:
`git log docs/PRIVACIDAD.md`.

---

## Contacto

**GhatoStudioOfficial@gmail.com**

Si crees que algo de aquí no se cumple, escríbeme y lo miro. Si es un problema de
seguridad, mejor por la vía de [SECURITY.md](../SECURITY.md).
