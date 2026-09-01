# ADR-0002 — Sin backend y sin cuentas de usuario

- **Fecha**: 2026-09-01
- **Estado**: aceptado
- **Decide**: Brais Galdo

## Contexto

El punto 6 de la plantilla exige decidir **explícitamente** si hace falta backend y
gestión de usuarios, y no dejarlo implícito. Si hiciera falta, tocaría Firebase en
capa gratuita con alertas de presupuesto de 5 €/mes.

Lo que Funny guarda es esto, y nada más:

- los ajustes (tema, idioma, ritmo, modalidad, sonido, vibración, animaciones,
  juegos activos);
- los participantes de la última partida y sus nombres;
- la mejor marca del reto en solitario;
- el estado de la propuesta de café (cuántos usos, si dijo «no volver a mostrar»).

Todo ello son unos pocos kilobytes por dispositivo, y **ninguno de esos datos le
sirve a nadie más**. No hay clasificación global, ni partidas guardadas en la nube,
ni contenido descargable, ni compartir tableros entre desconocidos.

El salón multidispositivo —que sí es «varios móviles a la vez»— no necesita
servidor: los móviles se hablan **directamente** entre ellos por Bluetooth o
Wi-Fi Direct (ver ADR-0003). Un servidor ahí no aportaría nada y añadiría una
dependencia de red a un juego que se juega alrededor de una mesa.

## Decisión

**No hay backend y no hay cuentas de usuario.** Los datos viven en el dispositivo
y se mueven con la exportación e importación a `.funny.bak` (punto 4.5).

En consecuencia:

- No se declara el permiso `INTERNET` en el manifiesto. No es un olvido: está
  comentado en el propio `AndroidManifest.xml` para que quede claro que es
  deliberado. La app **no puede** hablar con ninguna red aunque quisiera.
- No hay Firebase, ni Crashlytics, ni Analytics, ni nada que envíe un byte fuera.
- No hay nada que gastar en cloud, así que el `PRESUPUESTO_CLOUD` de 5 €/mes queda
  en **0 €/mes**. No hay ninguna consola en la que poner una alerta de gasto,
  porque no hay ningún servicio contratado.
- El formulario de Seguridad de los datos de Google Play se rellena con «la app no
  recoge ni comparte datos de usuario», y es verdad de forma comprobable: sin
  permiso de red no hay recogida posible.

El único enlace externo de toda la app es el de la donación, y lo abre el
navegador del sistema en un Custom Tab: la app no hace la petición, la hace el
navegador (ver ADR-0004).

## Consecuencias

**A favor**

- La app funciona entera sin conexión, que es lo que pide el punto 4.10, sin
  ningún esfuerzo especial: no hay estados de carga que gestionar porque no hay
  nada que cargar.
- La política de privacidad es corta y honesta.
- Coste de operación cero, para siempre. No hay factura que vigilar ni servicio
  que pueda cerrar y llevarse la app por delante.

**En contra**

- **No hay clasificación global ni logros compartidos.** La mejor marca del
  solitario es local. Si algún día se quisiera una tabla de récords entre amigos,
  habría que volver sobre esta decisión.
- **Perder el móvil es perder los datos**, salvo que se haya exportado. Se mitiga
  de dos formas: la exportación manual a fichero y la copia del sistema
  (`reglas_copia.xml`), que sí entra en la copia de seguridad de Google si el
  usuario la tiene activada.
- Al no haber cuentas, no se puede «continuar la partida en otro móvil». En un
  juego de mesa presencial, esto no es una pérdida real.

## Cuándo habría que revisar esto

Si en algún momento se quiere: contenido descargable, clasificaciones entre
dispositivos que no están en la misma habitación, o sincronizar ajustes entre el
móvil y la tablet de la misma persona. Ninguna de las tres cosas está en el brief
del punto 14.
