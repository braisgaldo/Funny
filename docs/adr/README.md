---
title: Decisiones de arquitectura
description: Las decisiones de diseño de Funny, cada una con su contexto y sus consecuencias.
permalink: /adr/
---

# Decisiones de arquitectura (ADR)

Un ADR es el registro de una decisión: el contexto en el que se tomó, lo que se
decidió y **las consecuencias**, incluidas las malas. Están aquí para que dentro de
un año se pueda saber por qué algo es como es sin tener que reconstruirlo del
código, y para que revisar una decisión sea barato: basta leer si su contexto sigue
siendo cierto.

Cuando una decisión cambie, el ADR **no se reescribe**: se marca como sustituido y
se añade uno nuevo que lo reemplace. Un ADR editado a posteriori miente sobre lo
que se sabía en su momento.

---

| # | Decisión | Estado |
|---|---|---|
| [0001](ADR-0001-stack.md) | Android nativo ahora, con las capas listas para Kotlin Multiplatform | aceptado |
| [0002](ADR-0002-sin-backend.md) | Sin backend y sin cuentas de usuario | aceptado |
| [0003](ADR-0003-salon-nearby.md) | El salón multidispositivo sobre Nearby Connections | aceptado |
| [0004](ADR-0004-donacion-sin-facturacion.md) | La donación, sin ninguna librería de facturación | aceptado |
| [0005](ADR-0005-contenido-sin-derechos.md) | El contenido no distribuye material con derechos de autor | aceptado |
| [0006](ADR-0006-licencia.md) | GPL-3.0-or-later para el código, CC BY-SA 4.0 para el contenido | aceptado |

---

## Lo que cada uno resuelve, en una frase

- **0001** — Por qué no hay KMP todavía aunque la plantilla lo pedía, qué se ha
  hecho para que la migración sea posible, y **cuánto costaría**. Aquí está también
  lo que hoy no está hecho: iOS no existe.
- **0002** — Por qué la app no tiene ni servidor ni cuentas, y por qué ni siquiera
  declara el permiso de internet. Es el ADR que hace que la política de privacidad
  pueda ser comprobable en lugar de una promesa.
- **0003** — Por qué el salón usa Nearby Connections y no Wi-Fi Direct a pelo, cómo
  se reparte la autoridad entre la mesa y los mandos, y —importante— **qué está
  probado y qué no**: el protocolo sí, la radio no.
- **0004** — La regla dura de la donación: ninguna librería de facturación, no
  desbloquea nada, navegador del sistema y no WebView, y una tarea de Gradle que
  rompe la build si algo de eso se incumple.
- **0005** — Por qué el mazo de Canta no lleva ni un verso de letra, y qué se hace
  en su lugar.
- **0006** — Por qué dos licencias, una para el código y otra para las cartas y las
  traducciones.

---

## Plantilla para uno nuevo

```markdown
# ADR-000X — Título en una línea

- **Fecha**: AAAA-MM-DD
- **Estado**: propuesto | aceptado | sustituido por ADR-000Y
- **Decide**: quién

## Contexto
Qué problema hay y qué restricciones son reales. Los hechos, no la solución.

## Decisión
Qué se hace. En presente y en afirmativo.

## Consecuencias
A favor y, sobre todo, **en contra**. Un ADR sin apartado de contras no está
terminado: significa que no se ha pensado el coste.

## Alternativas descartadas
Qué más se consideró y por qué no.
```
