# ADR-0006 — Licencia GPL-3.0-or-later para el código, CC BY-SA 4.0 para el contenido

- **Fecha**: 2026-09-01
- **Estado**: aceptado
- **Decide**: Brais Galdo

## Contexto

El punto 7 de la plantilla pide **elegir y justificar la licencia**, y avisa de que
la licencia no es el sitio donde va la donación (eso va en `FUNDING.yml` y en el
README).

Los hechos que importan para elegir:

- Es una app **gratuita, sin anuncios y sin compras**, publicada en Google Play y
  con el APK también en las GitHub Releases y (previsto) en F-Droid.
- Su valor no está tanto en el código como en las **3.194 cartas escritas a mano**
  y en las **413 claves traducidas a trece idiomas**. Eso es lo que cuesta meses.
- El repositorio es público. Cualquiera puede coger el árbol entero.
- Lo que no quiero es lo evidente: que alguien recompile esto, le meta anuncios y
  lo suba a la misma tienda como si fuera suyo.

## Decisión

**Dos licencias, una para cada cosa.**

### El código: GPL-3.0-or-later

Fichero `LICENSE`. Cubre todo el Kotlin, los ficheros de Gradle y la
configuración.

La razón es la cláusula que las licencias permisivas no tienen: **quien distribuya
un derivado tiene que publicar su código con la misma licencia**. Con MIT o
Apache-2.0, cualquiera puede coger Funny, añadirle publicidad y publicarlo cerrado.
Con la GPL puede seguir haciéndolo —el software libre no lo prohíbe— pero tiene que
publicar sus cambios, y eso quita el incentivo de hacerlo.

Ventaja secundaria y concreta: **F-Droid**. Su repositorio principal solo acepta
software libre, y la GPL es la licencia más habitual allí. El punto 4.4.1 de la
plantilla menciona F-Droid como vía de distribución sin políticas de facturación, y
esta licencia deja esa puerta abierta sin tener que volver sobre el tema.

`-or-later` (y no GPL-3.0 exacta) para que se pueda adoptar una versión posterior
de la licencia sin tener que localizar a nadie: aquí el titular soy yo solo, pero
es la forma recomendada por la FSF y no cuesta nada.

### El contenido: CC BY-SA 4.0

Fichero `LICENSE-CONTENIDO`. Cubre `app/src/main/assets/contenido/` —las 3.194
cartas— y los catálogos de idioma.

La GPL está escrita para programas y encaja mal con textos: hablar del «código
fuente correspondiente» de una lista de palabras de mímica no significa nada.
CC BY-SA 4.0 dice lo mismo en el registro adecuado: se puede reutilizar, hay que
atribuir, y las obras derivadas van con la misma licencia.

**BY-SA y no BY-NC.** La cláusula «no comercial» habría sido tentadora, pero
convierte la obra en no libre, dejaría a Funny fuera de F-Droid y, sobre todo, es
ambigua: nadie sabe con seguridad si una app gratuita con un botón de donación es
«uso comercial». Prefiero un texto que se pueda leer sin abogado.

### Google Play y la GPL son compatibles

Publicar una app GPL en Play es habitual y legal: la licencia obliga a ofrecer el
código a quien recibe el binario, y el repositorio público lo cumple. Lo que la
GPL v3 y las condiciones de Play sí tensan es la gestión de derechos digitales, y
aquí no hay ninguna: no hay DRM, no hay comprobación de licencia, no hay servidor.

## Consecuencias

**A favor**

- Un clon cerrado con anuncios no es una opción legal para nadie.
- La puerta de F-Droid queda abierta.
- Las traducciones y las cartas se pueden reutilizar en otros proyectos libres,
  que es un uso que me parece bien.

**En contra, y hay que asumirlo**

- **La GPL espanta a las empresas.** Si algún día quisiera vender una versión con
  licencia distinta a alguien, tendría que ser yo quien relicencie (puedo: soy el
  único titular, y por eso el punto 7 exige que no haya co-autoría en los
  commits), pero cualquier contribución externa que aceptase por PR quedaría bajo
  GPL y complicaría eso. Si llega el caso, hará falta un acuerdo de contribución;
  está anotado en `CONTRIBUTING.md`.
- **Dos licencias es más difícil de explicar** que una. Se mitiga diciendo en el
  README exactamente qué cubre cada una, con la ruta de las carpetas.
- Nada impide que alguien publique en Play una copia idéntica cumpliendo la GPL.
  Ninguna licencia libre lo impide; lo que lo desincentiva es que tendría que
  hacerlo también gratis y con su código abierto.

## Alternativas descartadas

- **MIT / Apache-2.0.** Más simpáticas y más usadas, pero permiten exactamente lo
  que quiero evitar.
- **AGPL-3.0.** Su cláusula de red no aporta nada: Funny no tiene servidor y ni
  siquiera declara el permiso de internet (ver ADR-0002).
- **Propietaria / sin licencia.** «Sin licencia» significa que nadie puede usar
  nada legalmente, ni siquiera para aprender, y contradice tener el repositorio
  público.
