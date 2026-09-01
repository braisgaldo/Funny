# ADR-0004 — La donación, sin ninguna librería de facturación

- **Fecha**: 2026-09-01
- **Estado**: aceptado
- **Decide**: Brais Galdo

## Contexto

El punto 4.4 de la plantilla pide una propuesta de donación («invítame a un café»,
1 €) y establece una **regla dura**: prohibido integrar Google Play Billing, la
dependencia `com.android.billingclient:billing` no puede aparecer ni directa ni
transitivamente, y **la donación no desbloquea absolutamente nada**.

El motivo de esa regla no es estético, es normativo. Las tiendas obligan a usar su
sistema de pago cuando se vende **un bien o servicio digital dentro de la app**. Si
la donación abriera un tema, quitara publicidad o diera contenido, sería una venta
y estaría obligada a pasar por Play Billing. Al no desbloquear nada, no hay venta:
hay un agradecimiento por algo que ya es gratis.

## Decisión

1. **No hay ninguna librería de facturación en el binario.** Se verifica con una
   tarea de Gradle propia, `verificarSinFacturacion`, que recorre los artefactos
   resueltos de todas las configuraciones y **falla la build** si aparece alguno de
   estos nombres:

   ```
   billingclient, billing-ktx, com.android.vending.billing,
   play-services-wallet, revenuecat, purchases-android, qonversion, adapty
   ```

   La tarea está enganchada a `check`, así que no se puede olvidar. Resultado
   actual: **181 artefactos revisados, ninguna librería de pagos**.

2. **La donación no desbloquea nada.** Ni funciones, ni temas, ni contenido, ni
   quitar publicidad (no hay publicidad). Los seis temas, los trece idiomas, los
   doce juegos, las cuatro modalidades y el salón están disponibles desde la
   primera vez que se abre la app. No hay «versión completa» porque no hay versión
   incompleta.

3. **Se abre en el navegador del sistema**, con Custom Tabs
   (`androidx.browser:browser`). Nunca en un `WebView` embebido: un WebView dentro
   de la app **parece** un flujo de pago de la app, y esa apariencia es
   exactamente lo que hay que evitar.

4. **Vocabulario.** Están prohibidas las palabras *comprar*, *pagar*,
   *desbloquear*, *pro*, *premium*, *suscripción* y *precio* en la app y en la
   ficha de tienda, en los trece idiomas. Hay un test que lo comprueba
   (`la donacion no usa vocabulario de compra en ninguno de los trece idiomas`), y
   ese test **cazó una infracción propia**: un texto que decía «no desbloquea
   nada» usaba precisamente la palabra prohibida. Se reescribió a «No cambia nada
   dentro del juego», y el test se dejó igual de estricto.

5. **Al volver del navegador no se afirma que el pago se haya hecho.** La app no
   tiene forma de saberlo —no habla con Revolut ni con nadie— y decir «gracias por
   tu donación» sería mentir a quien solo miró la página. El mensaje es «gracias
   por pasarte por ahí».

6. **Sin IBAN en la app.** El destino es el enlace de Revolut
   (`https://revolut.me/brais2oz6`). Publicar un IBAN personal en una app
   distribuida expone un dato bancario sin necesidad.

7. **`donationsEnabled` por plataforma**, activo en Android y **desactivado por
   defecto en iOS**. Apple tolera peor los enlaces de pago externos
   (App Store Review Guidelines 3.1.1). Cuando exista el target de iOS, se
   activará solo si App Review lo acepta, y si no, el enlace apuntará a la página
   del proyecto en GitHub Pages en lugar de a la pasarela.

## Cuándo aparece

Según el punto 4.4.3, y con «uso real» definido para este juego como **una partida
terminada**:

- Una sola vez, **al volver al menú después de terminar una partida**. Nunca en el
  arranque en frío, nunca encima de una tarea a medias, nunca dos veces seguidas.
- Si se elige «Ahora no», puede salir **una única vez más** pasados 30 días y 10
  usos. Después, silencio permanente.
- Siempre disponible en Ajustes → «Apoyar el desarrollo».
- La preferencia se guarda en DataStore y **viaja en la exportación**, para que
  reinstalar no vuelva a mostrarla.

## Consecuencias

**A favor**

- No hay ninguna duda regulatoria: sin librería de facturación en el binario, la
  declaración de «compras integradas: No» en la ficha de Play es coherente y
  comprobable por cualquiera que descompile el APK.
- El código de la donación es pequeño: un enlace, un QR generado en local y un
  contador de usos. No hay estado de compra que sincronizar ni recibos que
  validar.

**En contra**

- **No se sabe si alguien ha donado.** No hay métrica, no hay confirmación, no hay
  forma de agradecerlo personalmente. Es el precio de no integrar una pasarela, y
  se acepta.
- **En iOS la donación puede acabar no existiendo.** Está previsto y por eso el
  flag existe desde el principio.
- Depender de un enlace externo significa que si Revolut cambia el formato de sus
  enlaces, hay que sacar una versión. El enlace está en un `buildConfigField`, en
  un solo sitio.

## Pendiente antes de publicar

El punto 4.4.1 exige **verificar la política vigente antes de publicar** y guardar
captura con fecha. Eso no está hecho: las políticas cambian y una captura de hoy no
vale para una publicación de dentro de tres meses. Queda anotado en el checklist de
la guía de publicación como paso obligatorio, con los dos documentos a consultar:
**Google Play Payments Policy** y **App Store Review Guidelines, apartados 3.1.1 y
3.2.1**.
