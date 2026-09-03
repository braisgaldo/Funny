---
title: "Guía de publicación"
description: "Los pasos para publicar Funny en Google Play, con lo que falta marcado."
---

# Guía de publicación de Funny

**Versión 1.0.1** · Google Play y App Store

Todo lo necesario para publicar, en el orden en el que hay que hacerlo. Los textos
de la ficha, en los trece idiomas, están en [google_play/](google_play/).

> **Fecha de consulta de las políticas: pendiente.** Las políticas de las tiendas
> cambian, y una captura de hoy no vale para una publicación de dentro de tres
> meses. El apartado 3 es el único paso de esta guía que **no** se puede dar por
> hecho de antemano: hay que hacerlo justo antes de subir.

---

## Índice

1. [Antes de tocar la consola](#1-antes-de-tocar-la-consola)
2. [La ficha de Google Play](#2-la-ficha-de-google-play)
3. [La donación y las políticas de pago — el punto delicado](#3-la-donación-y-las-políticas-de-pago--el-punto-delicado)
4. [Seguridad de los datos](#4-seguridad-de-los-datos)
5. [Clasificación de contenido](#5-clasificación-de-contenido)
6. [Público objetivo](#6-público-objetivo)
7. [Pruebas cerradas: 12 testers, 14 días](#7-pruebas-cerradas-12-testers-14-días)
8. [Subir el AAB](#8-subir-el-aab)
9. [App Store, cuando llegue](#9-app-store-cuando-llegue)
10. [Gasto en la nube](#10-gasto-en-la-nube)
11. [Checklist final](#11-checklist-final)

---

## 1. Antes de tocar la consola

### Cuenta de desarrollador

- **Google Play Console**: 25 $ una sola vez, para siempre.
- Si la cuenta es **personal y creada después de noviembre de 2023**, aplica el
  requisito de pruebas cerradas del apartado 7. **Planifícalo desde el principio**:
  son 14 días de calendario que no se pueden acelerar.
- Verificación de identidad: DNI y, en cuentas personales, dirección. Tarda días.
  Hazlo el primero.

### Lo que hace falta tener listo

| | Dónde está |
|---|---|
| AAB firmado | `./gradlew :app:bundleRelease` — ver el manual técnico, parte E |
| Política de privacidad **en una URL pública** | ver más abajo |
| Icono 512×512 PNG | `google_play/graficos/` |
| Gráfico destacado 1024×500 PNG | `google_play/graficos/` |
| Capturas, mínimo 2 por formato | 4 de 8 hechas · `google_play/graficos/capturas/` |
| Textos de la ficha en 13 idiomas | `google_play/ficha/` |

### Publicar la política de privacidad

El texto ya está escrito, en [PRIVACIDAD.md](PRIVACIDAD.md). Lo que falta es que
esté **en una URL pública**, que es lo que Google Play exige. Tres cosas que
conviene saber antes de tocar nada.

#### 1. GitHub Pages necesita que el repositorio sea público

Pages sobre un repositorio **privado** solo funciona con un plan de pago
(GitHub Pro o superior). Con la cuenta gratuita y el repositorio en privado, la
opción de Settings → Pages no publica nada.

No es un problema a medio plazo: el código es **GPL-3.0-or-later**, y la GPL
obliga a ofrecer el código fuente a quien recibe el binario. En cuanto la app
esté en Play, el repositorio tiene que ser público de todos modos (ver
[ADR-0006](adr/ADR-0006-licencia.md)). El orden natural es:

1. Terminar de preparar la publicación con el repositorio en privado.
2. Antes de subir el AAB a Play, ponerlo en público.
3. Activar Pages y comprobar la URL.
4. Rellenar el campo de la ficha con esa URL.

**Si necesitas la URL antes de hacer público el repositorio**, la alternativa que
no cuesta dinero es un servicio que sí despliega desde repositorios privados
—Cloudflare Pages o Netlify, los dos con plan gratuito—: le das acceso al
repositorio, carpeta `/docs`, y te da una URL pública sin exponer el código. Es
una pieza más que mantener, así que solo merece la pena si hay prisa.

#### 2. Los pasos, cuando el repositorio ya sea público

1. Settings → Pages → Source: **Deploy from a branch**, rama `main`, carpeta
   `/docs`. Guardar.
2. Esperar el primer despliegue (aparece en la pestaña Actions).
3. La política queda en `https://braisgaldo.github.io/Funny/PRIVACIDAD`.
4. **Abrirla de incógnito**, sin sesión de GitHub. Si pide login, Google la
   rechaza. Comprueba también que no da 404 (ver el punto siguiente).

#### 3. Por qué los documentos llevan cabecera YAML

Cada `.md` de `docs/` empieza con un bloque `---` con `title` y `description`.
No es adorno: **Jekyll no procesa un Markdown sin front matter**, lo copia tal
cual. Sin esa cabecera, `PRIVACIDAD.md` se sirve como texto crudo en
`/Funny/PRIVACIDAD.md` y la URL sin extensión —la que enlaza la portada y la que
va en la ficha— devuelve **404**. Solo `index.md` la tenía, así que las seis
páginas que enlaza la portada estaban rotas antes de existir.

`docs/adr/README.md` lleva además `permalink: /adr/`, porque si no se convertiría
en `adr/README.html` y `/Funny/adr/` seguiría vacío.

Si algún día se añade un documento a `docs/`, tiene que llevar su cabecera o su
enlace no funcionará.

#### 4. La donación va en esa misma página

Es lo que pide el punto 4.4.4 de la plantilla: el enlace de «invítame a un café»
en la página del proyecto, con el mismo texto que en la app y sin prometer nada a
cambio. Ya está en [index.md](index.md).

#### 5. Un solo idioma es suficiente, y aun así

Google no exige la política traducida, ni siquiera con la ficha en trece idiomas:
basta una URL con el texto en un idioma. Está en castellano. Si algún día llegan
usuarios de fuera preguntando por sus datos, tener una versión en inglés ahorra
explicaciones —y es media hora de trabajo, no un proyecto—. Queda anotado, no
hecho.

---

## 2. La ficha de Google Play

### Nombre y descripciones

**Título** (máx. 30 caracteres): `Funny`

**Descripción corta** (máx. 80): en `google_play/ficha/<idioma>/corta.txt`
**Descripción larga** (máx. 4000): en `google_play/ficha/<idioma>/larga.txt`

Los trece idiomas están escritos. En Play Console: Presencia en Play → Ficha de
Play principal, y luego una traducción por idioma.

### Notas de la versión

**Novedades** (máx. 500 por idioma): en `google_play/ficha/<idioma>/novedades.txt`

Y las catorce juntas, etiquetadas por idioma, en `google_play/ficha/novedades.md`.
Play Console acepta pegarlas de una vez en ese formato, y catorce campos a mano
son catorce ocasiones de dejarse uno a medias.

Las etiquetas de Play no son los códigos de la ficha: el catalán es `ca` y no
`ca-ES`, y `en-GB` va aparte de `en-US` —el mismo texto, porque no hay ni una
palabra que se escriba distinto a los dos lados del Atlántico; si algún día
aparece un «customise», habrá que separarlos—.

No van en la ficha sino **en la versión**: Producción → Crear versión → Notas de
la versión, y hay un campo por idioma. Play las guarda por versión, así que las de
la 1.0.1 se quedan asociadas a ella y en la siguiente se escriben otras.

Las de la 1.0.1 cuentan qué es la app, que es lo que corresponde en una primera
publicación: no hay nada anterior con lo que comparar. A partir de la 1.1.0 tocará
contar lo que cambia, y conviene sacarlas del CHANGELOG en lugar de escribirlas
dos veces.

La cifra de cartas de cada idioma **no es el total de 3.194**: es la de su mazo.
Quien lee la ficha francesa juega con el mazo inglés —1.526 cartas— y no con los
dos sumados. Prometer 3.194 en la ficha francesa sería falso.

### El nivel de API que exige Play sube cada año

Play **rechaza** el AAB que no apunte al nivel mínimo vigente. Lo dice así:

> Actualmente, tu aplicación está orientada al nivel 35 de la API, pero debe
> orientarse, al menos, al nivel 36.

Es un **error**, no un aviso: sin arreglarlo no se puede publicar. Ahora mismo el
proyecto apunta al **36**, y el requisito sube aproximadamente una vez al año, así
que conviene comprobarlo antes de cada versión.

Subir de nivel no es cambiar un número: arrastra la cadena de herramientas.

1. **El SDK de la plataforma.** `sdkmanager "platforms;android-36"
   "build-tools;36.0.0"`.
2. **AGP.** Cada rama de AGP solo está probada hasta un `compileSdk`; la 8.7 lo
   estaba hasta el 35. Al elegir versión, mirar también qué Gradle pide: la
   8.10 funciona con el 8.11.1 que ya hay, y la 8.11 exigiría subir el wrapper.
3. **Robolectric.** Va siempre por detrás de la plataforma. Si no hay versión que
   soporte el nivel nuevo, los tests que necesitan un `Context` fallan en la
   configuración y hay que fijar el nivel que emula en
   `app/src/test/resources/robolectric.properties`.
4. **El comportamiento.** Cada nivel cambia cosas que solo se ven en un
   dispositivo con esa versión de Android. Si no hay uno a mano, **decirlo** en
   lugar de dar por bueno lo que no se ha probado.

### Los símbolos nativos necesitan el NDK

El otro mensaje de la consola es un **aviso**, no un error:

> Este App Bundle contiene código nativo, pero no has subido símbolos de
> depuración.

El código nativo no es de este proyecto: entra con AndroidX
(`libandroidx.graphics.path.so` de Compose y `libdatastore_shared_counter.so` de
DataStore, unos 10 KB cada una). El proyecto ya pide los símbolos con
`debugSymbolLevel = "SYMBOL_TABLE"` en el `buildType` de release, pero
**extraerlos necesita el NDK**: AGP usa su `objcopy`. Sin NDK la tarea avisa con
«Unable to strip the following libraries, packaging them as they are» y el bundle
sale sin ellos.

Dos caminos, y el segundo es el que se sigue:

- Instalar el NDK (unos 2 GB) para simbolizar dos librerías de 10 KB que no son
  nuestras.
- **Compilar la release en CI**, donde el runner de Ubuntu ya trae NDK. Es lo que
  hace el workflow de release, así que el AAB que sale de ahí sí los lleva.

Si se sube un AAB compilado a mano, el aviso aparece y **no impide publicar**.

### El `versionCode` se gasta al subir, no al publicar

Esto costó una versión, así que queda escrito. **Play reserva el `versionCode` en
cuanto se sube el artefacto**, aunque no se publique en ningún canal y aunque se
descarte a continuación. Al subir otro AAB con el mismo número, la consola
responde:

> El código de versión 10000 ya se ha usado. Prueba con otro código de versión.

Y no se puede reutilizar: no hay forma de liberarlo.

En este proyecto el `versionCode` **se deriva del `versionName`** con
`major * 10_000 + minor * 100 + patch`, así que no se toca a mano: se sube la
versión en `app/build.gradle.kts` (`versionSemVer`) y el código sale solo.

```
1.0.0 -> 10000     1.0.1 -> 10001     1.1.0 -> 10100
```

Consecuencia práctica: **sube a Play solo lo que pretendas publicar.** Cada
intento descartado cuesta un número, y los números no vuelven.

### Categoría y etiquetas

- **Tipo de aplicación**: **Juego** (no «Aplicación»)
- **Categoría**: Juegos → **Juegos de mesa**
- **Etiquetas** (hasta cinco, de la lista cerrada de Play): juegos de fiesta,
  juegos de mesa, multijugador local, preguntas y respuestas, juegos de palabras

#### Por qué «Juegos de mesa» y no otra

Play no tiene categoría de «fiesta», y ninguno de los dieciocho juegos manda lo
suficiente para llevarse la categoría entera. Repartidos por lo que se hace en
cada uno:

| Tipo | Cuántos | Cuáles |
|---|---|---|
| Actuar delante de la mesa | 6 | Mímica, Canta, Trabalenguas, Acentos, Sonidos, Desafío |
| Cultura general | 5 | Preguntas, ¿Cuándo?, ¿Te lo crees?, Refranes, ¿Antes o después? |
| Palabras | 3 | Tabú, Anagramas, Encadenados |
| Ingenio | 2 | Emojis, Ordena |
| Dibujar | 1 | Pinturillo |
| Contrarreloj | 1 | Reto rápido |

Lo que sí es común a los dieciocho es **el marco**: un tablero, un dado y unas
fichas que avanzan. Eso es lo que sostiene la partida y lo que se ve en la
pantalla la mayor parte del tiempo, así que la categoría honesta es la del
tablero.

**La alternativa era «Preguntas y respuestas»** y se descartó: son cinco mazos de
dieciocho, y quien busque un juego de preguntas y se encuentre mímica y dibujo
se sentirá engañado. Play también penaliza la categoría equivocada: el
descubrimiento va por categoría, y estar en la que no toca te compara con apps
que hacen otra cosa.

Contra: «Juegos de mesa» es una categoría con gigantes (Monopoly, Ludo, ajedrez)
y un desconocido no aparece en sus listas. Se acepta: la ficha no se gana en la
lista de la categoría sino en la búsqueda, y ahí trabajan las etiquetas y el
título.

#### Sobre las etiquetas

**La lista de Play es cerrada**: se eligen hasta cinco de su taxonomía, no se
escriben. Los nombres de arriba son los conceptos que hay que buscar en el
selector; las etiquetas reales pueden llamarse algo distinto según cómo las tenga
Play ese día. El orden es de prioridad, por si alguna no existe:

1. **Juegos de fiesta** — es lo que la app es, y la que mejor filtra a quien la
   busca.
2. **Juegos de mesa** — refuerza la categoría.
3. **Multijugador local** — es la diferencia real con el resto: cinco móviles sin
   internet y sin router.
4. **Preguntas y respuestas** — cubre los cinco mazos de cultura general.
5. **Juegos de palabras** — cubre Tabú, Anagramas y Encadenados.

Lo que **no** conviene poner aunque exista: nada de «multijugador en línea»
—no lo es— ni «educativo», que cambia el público esperado.

### Gráficos

| | Requisito | Estado |
|---|---|---|
| Icono | 512×512 PNG 32 bits, sin transparencia | `google_play/graficos/icono-512.png` |
| Gráfico destacado | 1024×500 PNG o JPG | `google_play/graficos/destacado-1024x500.png` |
| Capturas de teléfono | mín. 2, máx. 8 · 16:9 o 9:16 · lado mínimo 320 px | 7 de 8 |
| Capturas de tablet 7″ y 10″ | opcionales, pero mejoran la ficha | pendiente |

**Hay siete de las ocho**, tomadas en un SM-S908U con Android 13. Falta **el
salón**, y esa necesita un segundo móvil: no se puede fingir. Detalle en
`google_play/CAPTURAS.md`, que incluye también un aviso: las capturas del
repositorio están optimizadas a 256 colores para no engordarlo, así que las de la
ficha conviene volver a tomarlas sin optimizar.

### Vídeo promocional

Opcional. No hay.

---

## 3. La donación y las políticas de pago — el punto delicado

Este es el apartado que hay que poder defender si una revisión lo cuestiona.

### La declaración

**«¿Contiene tu app compras integradas?» → NO.**

Y es coherente y comprobable: **no hay ninguna librería de facturación en el
binario**. Cualquiera puede descompilar el APK y verificarlo. En el repositorio lo
vigila una tarea de Gradle enganchada a `check`:

```
verificarSinFacturacion: 181 artefactos revisados, ninguna librería de pagos. Correcto.
```

### Por qué la donación no es una compra integrada

El argumento, en tres frases:

1. Las tiendas obligan a usar su sistema de pago cuando se **vende un bien o
   servicio digital dentro de la app**.
2. **La donación de Funny no desbloquea absolutamente nada**: ni funciones, ni
   temas, ni contenido, ni quitar publicidad (no hay publicidad). Los seis temas,
   los trece idiomas, los dieciocho juegos, las cuatro modalidades y el modo de varios
   móviles están disponibles desde la primera vez que se abre la app.
3. Por tanto no se compra nada: se agradece algo que ya es gratis, y el pago ocurre
   **fuera de la app**, en el navegador del sistema.

Refuerzos que no son casualidad:

- Se abre con **Custom Tabs**, nunca en un WebView embebido. Un WebView dentro de la
  app parece un flujo de pago de la app, y esa apariencia es justo lo que hay que
  evitar.
- **Vocabulario**: prohibidas las palabras *comprar*, *pagar*, *desbloquear*, *pro*,
  *premium*, *suscripción* y *precio*, en la app y en la ficha, **en los trece
  idiomas**. Hay un test que lo comprueba.
- Al volver del navegador **no se afirma que el pago se haya hecho**, porque no hay
  forma de saberlo.
- No se dice en ningún sitio que exista una «versión completa».

Todo el razonamiento, con sus consecuencias, en el
[ADR-0004](adr/ADR-0004-donacion-sin-facturacion.md).

### Lo que hay que hacer justo antes de subir

**Esto no está hecho y no se puede hacer por adelantado.** Antes de subir:

1. Abrir la **Google Play Payments Policy** vigente y localizar el apartado que
   define qué obliga a usar Play Billing.
2. Comprobar que sigue diciendo lo que decía: que aplica a la venta de bienes y
   servicios digitales dentro de la app, y no a donaciones que no desbloquean nada.
3. **Anotar aquí el apartado exacto y la fecha de consulta, y guardar captura**
   junto a este documento, en `google_play/politicas/`.
4. Repetir con las **App Store Review Guidelines**, apartados **3.1.1** (compras
   integradas) y **3.2.1** (donaciones y recaudación), cuando llegue el turno de
   iOS.

| Política | Apartado citado | Fecha de consulta | Captura |
|---|---|---|---|
| Google Play Payments Policy | *pendiente* | *pendiente* | *pendiente* |
| App Store Guidelines 3.1.1 | *pendiente* | *pendiente* | *pendiente* |
| App Store Guidelines 3.2.1 | *pendiente* | *pendiente* | *pendiente* |

### Vías sin política de facturación

Además de Play, y conviene mencionarlo en el README:

- **GitHub Releases** con el APK.
- **F-Droid**, donde no aplica ninguna política de facturación. La licencia
  GPL-3.0-or-later lo permite ([ADR-0006](adr/ADR-0006-licencia.md)).

---

## 4. Seguridad de los datos

El formulario de Play Console. Para Funny se rellena así, y todo es verificable:

| Pregunta | Respuesta |
|---|---|
| ¿Recoge datos de usuario? | **No** |
| ¿Comparte datos con terceros? | **No** |
| ¿Los datos se cifran en tránsito? | *no aplica: no hay tránsito* |
| ¿Se puede pedir el borrado de los datos? | *no aplica: no hay datos en ningún servidor* |
| ¿Hay recogida de datos obligatoria? | **No** |
| ¿La app ha sido revisada por seguridad de forma independiente? | No |

**Por qué se puede afirmar sin matices**: la app **no declara el permiso
`INTERNET`**. Sin él, Android impide cualquier conexión de red. No hay analítica, ni
telemetría, ni informes de fallos remotos, ni identificadores publicitarios, y no es
por decisión de diseño revisable: es que **no podrían funcionar**.

Lo que la app guarda —ajustes, nombres de equipos, mejor marca— vive solo en el
dispositivo y se mueve con la exportación a un fichero que el usuario controla.

Si Google pregunta por el enlace de la donación: es un enlace externo que abre el
navegador del sistema. La app no realiza ninguna petición, no recibe respuesta y no
sabe si el usuario ha llegado a la página.

Detalles en el [ADR-0002](adr/ADR-0002-sin-backend.md) y en
[PRIVACIDAD.md](PRIVACIDAD.md).

---

## 5. Clasificación de contenido

Cuestionario de IARC en Play Console. Categoría: **Juego**.

| Pregunta | Respuesta | Por qué |
|---|---|---|
| Violencia | Ninguna | |
| Contenido sexual | Ninguno | Revisado carta por carta |
| Lenguaje soez | Ninguno | |
| Drogas, alcohol o tabaco | **Ninguno** | Ningún desafío usa alcohol. Deliberado |
| Juegos de azar | Ninguno | El dado no reparte premios |
| Miedo u horror | Ninguno | |
| Compras integradas | **No** | Ver apartado 3 |
| Publicidad | **No** | |
| Interacción entre usuarios | **No** | Sin chat, sin contenido compartido, sin forma de que alguien de fuera contacte |
| Comparte la ubicación | **No** | Ver la nota de abajo |
| Comparte información personal | **No** | |
| Contenido generado por usuarios | **No** | Los nombres de equipo se quedan en el dispositivo y en los móviles de la mesa |

**Sobre la ubicación**, que es la pregunta que más se malinterpreta: la app declara
`ACCESS_FINE_LOCATION` y `ACCESS_COARSE_LOCATION` **solo hasta Android 12**
(`maxSdkVersion="32"`), porque es el sistema el que los exige para buscar
dispositivos Bluetooth cercanos. La app **no lee la ubicación, no la guarda y no
podría enviarla**. El permiso de Wi-Fi cercano lleva `neverForLocation`.

Si el cuestionario pregunta si la app «usa» la ubicación, la respuesta honesta es
**no**, y esta es la explicación que hay que dar si alguien la pide.

Clasificación esperada: **PEGI 3 / ESRB Everyone**. Si sale más alta, revisar qué
pregunta se ha entendido mal.

---

## 6. Público objetivo

- **Grupos de edad**: 13+ (por prudencia con las preguntas de cultura general, no
  por contenido problemático).
- **¿Dirigida principalmente a menores?** No.
- **Programa Familias de Play**: no se solicita. La app es apta para familias, pero
  entrar en el programa añade requisitos —anuncios certificados, políticas de
  publicidad— que no aportan nada a una app sin anuncios.

---

## 7. Pruebas cerradas: 12 testers, 14 días

**Si la cuenta de desarrollador es personal y se creó después de noviembre de 2023,
esto es obligatorio antes de poder publicar en producción.**

El requisito, tal como está:

- **12 testers** dados de alta en una pista de pruebas cerradas.
- Que hayan **aceptado la invitación**.
- **14 días seguidos** con al menos esos 12 testers activos.
- Y después, solicitar acceso a producción.

Lo que hay que saber para no perder tres semanas:

1. **Los 14 días son de calendario y no se pueden acelerar.** Empieza la pista de
   pruebas cerradas **antes** de tener la ficha perfecta.
2. Los testers tienen que **usar la app**, no solo instalarla. Google mide
   participación.
3. Doce personas reales. Doce cuentas propias no cuenta y es motivo de cierre.
4. Se organiza con un grupo de Google o una lista de correos en Play Console →
   Pruebas → Pruebas cerradas.

**Plan sugerido**: crear la pista cerrada el mismo día que se verifique la cuenta,
invitar a los doce, y usar esos 14 días para hacer las capturas, cerrar los textos
de la ficha y consultar las políticas del apartado 3.

Verifica el requisito vigente en Play Console: cambió en 2023 y puede volver a
cambiar.

---

## 8. Subir el AAB

```bash
export FUNNY_KEYSTORE=/ruta/funny-release.jks
export FUNNY_KEYSTORE_PASSWORD=...
export FUNNY_KEY_ALIAS=funny
export FUNNY_KEY_PASSWORD=...

./gradlew :app:check          # que no salga una release de un árbol roto
./gradlew :app:bundleRelease
```

El AAB queda en `app/build/outputs/bundle/release/`.

**Comprueba que está firmado de verdad** antes de subirlo:

```bash
$ANDROID_HOME/build-tools/*/apksigner verify --verbose app/build/outputs/apk/release/*.apk
```

El build **avisa y sigue** si no encuentra el keystore, así que de eso no se puede
fiar una release. El workflow de release hace esta comprobación automáticamente.

### Play App Signing

Actívalo. Google guarda la clave de firma de la app y la tuya pasa a ser solo la de
subida, que **sí se puede reemplazar** pidiéndoselo a soporte. Sin eso, perder el
keystore significa **no poder volver a actualizar la app nunca** con esa ficha.

### Versión

`versionCode` **10001** para la 1.0.1, calculado desde el SemVer. Cada subida
necesita uno mayor, y al derivarse de la versión no hay que acordarse de subirlo.

El 10000 se gastó en una subida de la 1.0.0 que no se publicó. Ver el apartado
«El `versionCode` se gasta al subir, no al publicar», en el punto 2.

---

## 9. App Store, cuando llegue

**Nada de esto está hecho.** Es lo que hará falta, para que la estimación no
sorprenda.

### Lo que cuesta, en dinero y en trabajo

- **Apple Developer Program: 99 €/año, y es inevitable.** No hay plan gratuito para
  publicar. A diferencia de los 25 $ de Google, esto se paga cada año y la app
  desaparece de la tienda si se deja de pagar.
- **Hace falta un Mac.** El target de iOS de Kotlin Multiplatform no se puede
  compilar sin macOS y sin Xcode.

### Lo que falta técnicamente

El [ADR-0001](adr/ADR-0001-stack.md) lo tasa. El resumen:

| Trabajo | Esfuerzo |
|---|---|
| Mover `dominio/` a `commonMain` | bajo: ya es Kotlin puro |
| `datos` y `plataforma` con `expect`/`actual` | medio |
| **El transporte del salón** | **alto**: Nearby Connections no existe en iOS; hay que reescribirlo sobre *Multipeer Connectivity* |
| `ui` a Compose Multiplatform | alto: 20 pantallas y rehacer el lienzo de dibujo |

**El punto caro no es la interfaz: es el salón.**

### La donación en iOS

Apple tolera peor los enlaces de pago externos (**Guidelines 3.1.1**). Por eso la
donación va detrás de un flag por plataforma, `donationsEnabled`, **activo en
Android y desactivado por defecto en iOS**.

Cuando llegue el momento:

1. Leer las Guidelines **3.1.1** y **3.2.1** vigentes y anotar apartado y fecha.
2. Si App Review lo acepta, activar el flag.
3. Si no lo acepta, el enlace apunta a la **página del proyecto en GitHub Pages** en
   lugar de a la pasarela, y la donación se hace desde ahí.

### Equivalentes de Apple

- Ficha en App Store Connect: nombre, subtítulo, descripción, palabras clave.
- Capturas por cada tamaño de dispositivo que Apple exija.
- Icono 1024×1024 sin transparencia y sin esquinas redondeadas.
- URL de política de privacidad: la misma de GitHub Pages.
- **App Privacy**: mismas respuestas que el apartado 4. No se recogen datos.
- **Declaración de compras integradas: No.** Sin StoreKit en el binario, igual que
  sin Play Billing en Android.

---

## 10. Gasto en la nube

La plantilla pide alertas de gasto con un límite de 5 €/mes.

**El gasto en la nube de Funny es 0 €/mes, y no hay ninguna consola donde poner una
alerta, porque no hay ningún servicio contratado.**

- No hay backend, ni Firebase, ni base de datos remota
  ([ADR-0002](adr/ADR-0002-sin-backend.md)).
- La app **no declara el permiso de internet**: no puede haber tráfico que facturar.
- Nearby Connections, que es lo único de Google que se usa, es **gratuito**: no
  tiene cuota, ni clave de API, ni consola de facturación
  ([ADR-0003](adr/ADR-0003-salon-nearby.md)).
- GitHub Actions es gratis en repositorios públicos.
- GitHub Pages es gratis.

Lo único que se paga:

| | Coste | Cuándo |
|---|---|---|
| Google Play Console | 25 $ | una sola vez |
| Apple Developer Program | 99 €/año | solo si se publica en iOS |

**Si en el futuro se añade cualquier servicio de pago**, hay que volver a este
apartado: crear el proyecto en Google Cloud, ir a Facturación → Presupuestos y
alertas, presupuesto mensual de 5 €, y alertas al 50 %, 90 % y 100 % al correo de
contacto. Y antes de eso, según el punto 6 de la plantilla, **parar y consultarlo**.

---

## 11. Checklist final

### Antes de subir

- [ ] Cuenta de Google Play verificada (identidad y dirección)
- [ ] Repositorio en **público** (Pages no funciona en privado con cuenta gratuita)
- [ ] Pages activado: Settings → Pages → `main` + `/docs`
- [ ] Política de privacidad publicada y accesible **de incógnito**, sin 404
- [x] `./gradlew :app:check` en verde — 174 pruebas, 0 errores de lint
- [x] `:app:dependencies` sin ninguna librería de facturación — 181 artefactos
      revisados por `verificarSinFacturacion`, enganchada a `check`
- [x] AAB compilado **y verificado con `apksigner`** — `docs/google_play/binarios/`
- [ ] Play App Signing activado
- [x] Keystore creado fuera del repositorio ([FIRMA.md](google_play/FIRMA.md))
- [ ] **Copia del keystore en dos sitios más y contraseña en el gestor** ← *pendiente, y urgente*
- [x] **Probado en un dispositivo físico** — SM-S908U, Android 13, partida completa
- [ ] **Capturas de la ficha: 7 de 8** — falta el salón, que necesita un segundo
      móvil ([CAPTURAS.md](google_play/CAPTURAS.md))
- [x] Icono 512×512 y gráfico destacado 1024×500 · `generar-graficos.py`
- [x] Textos de la ficha en los 13 idiomas, validados de largo · `generar-ficha.py`
- [ ] **Política de pago consultada, apartado citado y captura guardada** ← *pendiente, apartado 3*

### En Play Console

- [ ] Ficha principal completa
- [ ] Las 12 traducciones añadidas
- [ ] **Notas de la versión** en los 13 idiomas · `ficha/<idioma>/novedades.txt`
- [ ] Categoría: Juegos → Juegos de mesa
- [ ] Formulario de Seguridad de los datos: **no se recogen datos**
- [ ] Cuestionario de clasificación de contenido
- [ ] Público objetivo: 13+, no dirigida a menores
- [ ] **Compras integradas: No**
- [ ] URL de política de privacidad
- [ ] Correo de contacto: GhatoStudioOfficial@gmail.com
- [ ] Anuncios: **No**

### Si la cuenta es personal y reciente

- [ ] Pista de pruebas cerradas creada
- [ ] 12 testers invitados **y que hayan aceptado**
- [ ] 14 días seguidos completados
- [ ] Acceso a producción solicitado y concedido

### Después de publicar

- [ ] Tag `v1.0.1` empujado y GitHub Release publicada con el AAB, el APK y los
      documentos
- [ ] CHANGELOG cerrado con la fecha de publicación
- [ ] README actualizado con el enlace real de Play
- [ ] APK subido también a las Releases
- [ ] F-Droid: valorar el envío

### Verificación honesta antes de decir «publicado»

- [ ] La app instalada **desde Play**, no desde el APK local, y una partida jugada
      de principio a fin
- [x] Los 6 temas y los 13 idiomas vistos en pantalla
- [x] El árabe comprobado en RTL
- [ ] Exportar → borrar datos → importar devuelve el estado anterior
- [x] La hoja de la donación vista en los 6 temas y en RTL; el QR descodificado
      con ZXing devuelve el enlace correcto
- [ ] El modo de varios móviles probado **con dos móviles de verdad**
