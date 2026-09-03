# Cómo aportar a Funny

Gracias por pasarte. Funny es un proyecto de una persona, así que lo que más
ayuda, por orden, es: **cartas nuevas**, **traducciones**, **avisos de fallos** y
código.

---

## Antes de nada: el acuerdo de licencia

Esto va primero porque afecta a todo lo demás.

- El código va bajo **GPL-3.0-or-later** y el contenido bajo **CC BY-SA 4.0**
  (ver [ADR-0006](docs/adr/ADR-0006-licencia.md)).
- Al abrir un *pull request* aceptas que tu aportación se distribuya con esas
  licencias.
- Los commits de la rama `main` los firmo yo, **sin trailers de co-autoría**. Eso
  no borra a nadie: quien aporta aparece en el historial del *pull request* y en el
  CHANGELOG. Es una decisión de titularidad, no de mérito, y está explicada en el
  ADR-0006: quiero poder relicenciar el proyecto en el futuro sin tener que
  localizar a nadie.
- Si esa condición no te parece bien, dilo antes de escribir código y buscamos otra
  forma. No la descubras al final.

---

## Cartas nuevas

Es lo más fácil y lo más útil.

Las cartas viven en `app/src/main/assets/contenido/<idioma>/<juego>.json`. Cada
mazo tiene su forma; mírala antes de escribir.

**Tres reglas que no se negocian:**

1. **Nada con derechos de autor.** En particular, **ni un verso de letra de
   canción**. El mazo de `canta.json` lleva solo título, artista y una pista
   escrita con tus palabras, y el porqué está en el
   [ADR-0005](docs/adr/ADR-0005-contenido-sin-derechos.md). El fichero lo repite
   dentro, en un campo `_comentario`.
2. **Nada copiado de otro juego.** Un hecho no es de nadie; la redacción concreta
   de la pregunta de otro juego, sí.
3. **Sin contenido que excluya a nadie.** Es un juego para jugar con gente que a
   veces acaba de conocerse. Los desafíos son absurdos, no humillantes; nada
   sexual, nada de alcohol como prueba, nada a costa de un grupo de personas.

Y una que sí vigila la build: **las cartas pasan por `PruebaContenido`**. Si a una
pregunta le falta una opción, si un índice de respuesta se sale de rango o si una
pista de canción se pasa de 60 caracteres, la build falla. Ejecuta
`./gradlew :app:testDebugUnitTest` antes de abrir el PR.

---

## Traducciones

Los trece idiomas están completos: **413 claves cada uno**. Lo que hace falta aquí
no es traducir de cero, es **corregir** lo que esté torpe. Si el gallego, el
euskera, el griego o el japonés te suenan raros en algún sitio, esa corrección vale
mucho.

Los catálogos son ficheros Kotlin en `app/src/main/java/es/ghatostudio/funny/ui/i18n/`.
Una corrección puntual se hace ahí directamente.

Lo que tienes que saber:

- **Ninguna clave puede faltar.** `PruebaCatalogos` exige las 413 a los trece
  idiomas, así que si añades una clave nueva tienes que rellenar los trece o la
  build falla. Es deliberado: preferimos una build roja a que alguien vea
  `AJUSTES_TITULO` en su pantalla.
- **Los parámetros tienen que coincidir.** Si el inglés dice `%1$d casillas ·
  %2$d pruebas`, tu idioma tiene que usar los mismos números. Hay un test que lo
  comprueba, porque un parámetro perdido acaba en una excepción de formato en
  mitad de una partida.
- **Los plurales van por categorías de la CLDR.** El ruso necesita tres formas y
  el árabe seis. Rellena solo las que tu idioma use; `OTHER` hace de respaldo.
- **Vocabulario prohibido en la donación**: *comprar*, *pagar*, *desbloquear*,
  *pro*, *premium*, *suscripción*, *precio*, en cualquier idioma. Hay un test.
- **Cero cadenas a fuego** en `ui/`. Hay una verificación de Gradle que falla si
  aparece una.

Añadir un idioma nuevo es más trabajo: hay que darlo de alta en `Idioma`,
escribir su catálogo, decidir su insignia (bandera o código neutro) y añadirlo a
`resourceConfigurations` y a `locales_config.xml`. Abre antes una *issue* para
hablarlo.

---

## Avisar de un fallo

Abre una *issue* con:

- qué móvil y qué versión de Android;
- versión de Funny (está en Ajustes → Acerca de, con el hash del commit);
- qué esperabas y qué pasó;
- si es del salón: **cuántos móviles**, cuál hacía de mesa y en qué momento falló.
  Esto último importa porque el salón es la parte que no se puede probar sin varios
  dispositivos delante.

Para un problema de **seguridad**, no abras una *issue*: mira
[SECURITY.md](SECURITY.md).

---

## Código

### Antes de empezar

Abre una *issue* y hablemos, sobre todo si es algo grande. Un PR de mil líneas que
no encaja con la arquitectura es tiempo perdido para los dos.

### Reglas de la casa

1. **`dominio/` es Kotlin puro.** Ni un `import android.*`, ni un `import
   androidx.*`. No es purismo: es lo que permite probar las reglas sin emulador y
   lo que deja abierta la puerta de Kotlin Multiplatform
   ([ADR-0001](docs/adr/ADR-0001-stack.md)). Si necesitas algo de la plataforma,
   defínelo como interfaz en el dominio e impleméntalo en `plataforma/`.
2. **Las reglas del juego van en `MotorJuego`**, como funciones que reciben un
   estado y devuelven otro. Nada de lógica de juego en un composable ni en el
   ViewModel: la mesa del salón y un móvil suelto tienen que aplicar exactamente
   las mismas transiciones.
3. **Los colores son tokens.** Nada de `Color(0xFF...)` en una pantalla. Si hace
   falta un color nuevo, va a `Paleta.kt` en los seis temas, y **con su contraste
   comprobado**: `PruebaContraste` falla si baja de 4,5:1 en texto.
4. **Los textos son claves.** Cadena nueva → clave nueva en `Clave` → los trece
   catálogos.
5. **Lo que se arregla, se prueba.** Si has corregido un fallo, deja un test que
   habría fallado antes.

### Antes de abrir el PR

```bash
./gradlew :app:ktlintFormat   # formatea
./gradlew :app:check          # pruebas + ktlint + lint + verificaciones propias
```

`check` tiene que estar en verde. Incluye:

- las **172 pruebas** unitarias;
- **ktlint** (configurado en `.editorconfig`, 100 columnas);
- el **lint de Android**, que aquí está en modo «los errores rompen la build»;
- `verificarSinFacturacion` — falla si aparece cualquier librería de pagos;
- `verificarTextosLiterales` — falla si hay una cadena a fuego en `ui/`.

### Commits

**Conventional commits, en castellano**, con un cuerpo que explique **por qué**, no
qué líneas cambiaron —el diff ya dice eso—.

```
feat(partida): cuatro modalidades y numeros de partida a medida

Los preajustes fijan casillas y pruebas, no el ritmo: el ritmo ya era un
ajuste propio y meterlo en el paquete habria hecho que elegir «normal»
pisara en silencio algo que alguien habia puesto a mano.
```

Ámbitos que se usan: `partida`, `salon`, `i18n`, `tema`, `datos`, `ui`, `docs`,
`ci`, `contenido`.

Ramas: `feat/…`, `fix/…`, `docs/…` desde `main`, integradas por PR.

---

## Lo que no va a entrar

Para no hacerte perder el tiempo:

- **Cualquier librería de facturación.** Ni Play Billing, ni RevenueCat, ni nada.
  Hay una verificación que rompe la build ([ADR-0004](docs/adr/ADR-0004-donacion-sin-facturacion.md)).
- **Que la donación desbloquee algo.** Ni una función, ni un tema «de
  agradecimiento».
- **Analítica, telemetría o trazas remotas**, ni con consentimiento. La app no
  declara el permiso de internet y así se queda.
- **Anuncios.**
- **Cuentas de usuario o backend**, salvo que traigas un motivo que no esté ya
  contestado en el [ADR-0002](docs/adr/ADR-0002-sin-backend.md).
- **Letras de canciones**, por mucho que mejorasen el juego de Canta.
