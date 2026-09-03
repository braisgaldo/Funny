# Guía de publicación de Funny

**Versión 1.0.0** · Google Play y App Store

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

Google Play **exige una URL pública**. Con GitHub Pages es gratis:

1. En el repositorio: Settings → Pages → Source: `main`, carpeta `/docs`.
2. La política queda en
   `https://braisgaldo.github.io/Funny/PRIVACIDAD` (GitHub Pages renderiza el
   Markdown de `docs/`).
3. Comprueba que la URL abre **de incógnito**, sin sesión. Si pide login, Google la
   rechaza.

Esa misma página es la que el punto 4.4.4 de la plantilla pide para la donación:
añade ahí el enlace de «invítame a un café», con el mismo texto que en la app.

---

## 2. La ficha de Google Play

### Nombre y descripciones

**Título** (máx. 30 caracteres): `Funny`

**Descripción corta** (máx. 80): en `google_play/ficha/<idioma>/corta.txt`
**Descripción larga** (máx. 4000): en `google_play/ficha/<idioma>/larga.txt`

Los trece idiomas están escritos. En Play Console: Presencia en Play → Ficha de
Play principal, y luego una traducción por idioma.

### Categoría y etiquetas

- **Categoría**: Juegos → **Juegos de mesa**
- **Etiquetas**: fiesta, mesa, multijugador local, sin conexión
- **Es un juego**: sí

### Gráficos

| | Requisito | Estado |
|---|---|---|
| Icono | 512×512 PNG 32 bits, sin transparencia | `google_play/graficos/icono-512.png` |
| Gráfico destacado | 1024×500 PNG o JPG | `google_play/graficos/destacado-1024x500.png` |
| Capturas de teléfono | mín. 2, máx. 8 · 16:9 o 9:16 · lado mínimo 320 px | 4 de 8 |
| Capturas de tablet 7″ y 10″ | opcionales, pero mejoran la ficha | pendiente |

**Hay cuatro de las ocho**, tomadas en un SM-S908U con Android 13. Faltan Tabú,
Pinturillo, la rejilla de los dieciocho juegos y el salón; las dos primeras salen
dejando activo solo ese juego en Ajustes. Detalle en
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

`versionCode` 10000 para la 1.0.0, calculado desde el SemVer. Cada subida necesita
uno mayor, y al derivarse de la versión no hay que acordarse de subirlo.

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
- [ ] Política de privacidad publicada y accesible **de incógnito**
- [ ] `./gradlew :app:check` en verde
- [ ] `:app:dependencies` sin ninguna librería de facturación — comprobado
- [x] AAB compilado **y verificado con `apksigner`** — `docs/google_play/binarios/`
- [ ] Play App Signing activado
- [x] Keystore creado fuera del repositorio ([FIRMA.md](google_play/FIRMA.md))
- [ ] **Copia del keystore en dos sitios más y contraseña en el gestor** ← *pendiente, y urgente*
- [x] **Probado en un dispositivo físico** — SM-S908U, Android 13, partida completa
- [ ] **Capturas de la ficha: 4 de 8** — faltan Tabú, Pinturillo, la rejilla de
      juegos y el salón ([CAPTURAS.md](google_play/CAPTURAS.md))
- [ ] Icono 512×512 y gráfico destacado 1024×500
- [ ] Textos de la ficha en los 13 idiomas
- [ ] **Política de pago consultada, apartado citado y captura guardada** ← *pendiente, apartado 3*

### En Play Console

- [ ] Ficha principal completa
- [ ] Las 12 traducciones añadidas
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

- [ ] Tag `v1.0.0` empujado y GitHub Release publicada con el AAB, el APK y los
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
