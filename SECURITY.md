# Política de seguridad

## Cómo avisar de un problema

**No abras una *issue* pública.** Escribe a:

**GhatoStudioOfficial@gmail.com**, con `[SEGURIDAD]` en el asunto.

Cuenta qué has encontrado, cómo reproducirlo y qué versión de Funny y de Android
(la versión y el hash del commit están en Ajustes → Acerca de).

Qué puedes esperar:

- **Acuse de recibo en 5 días.** Esto lo mantiene una sola persona en su tiempo
  libre, así que no prometo horas.
- Si es un problema real, te digo cuándo pienso arreglarlo.
- Crédito en el CHANGELOG con el nombre que quieras, o sin ninguno si prefieres.
- Nada de recompensas: no hay dinero para eso.

Te pido lo de siempre: dame margen para arreglarlo antes de publicarlo.

---

## Versiones con soporte

| Versión | Soporte |
|---|---|
| Última publicada | Sí |
| Anteriores | No |

Es una app sin servidor: «arreglar» significa sacar una versión nueva en Google
Play y en las Releases. No hay nada que parchear en remoto.

---

## Qué superficie de ataque hay, realmente

Merece la pena decirlo con precisión, porque acota bastante lo que puede pasar.

### La app no tiene red

**No se declara el permiso `INTERNET`** en el manifiesto, y está comentado ahí para
que quede claro que es a propósito. Consecuencias:

- No hay peticiones que interceptar, ni TLS que configurar mal, ni certificados que
  fijar.
- No hay servidor que atacar: no existe ([ADR-0002](docs/adr/ADR-0002-sin-backend.md)).
- No hay credenciales ni tokens en la app, porque no hay nada con lo que
  autenticarse.
- No hay analítica ni telemetría que pueda filtrar nada.

### Dónde sí puede haber problemas

Estas son las tres entradas de datos que la app tiene, y por tanto lo que de
verdad interesa revisar:

1. **El fichero de copia de seguridad `.funny.bak`.** Es JSON y lo puede haber
   editado cualquiera. Se interpreta a la defensiva: la cabecera se valida antes de
   tocar nada, un esquema futuro se rechaza en lugar de leerse a medias, un enum
   desconocido cae en su valor por defecto, y los números fuera de rango se
   recortan al leerlos. Antes de importar se guarda **siempre** una copia de lo que
   había. Si encuentras una forma de que un fichero manipulado deje los datos a
   medias o haga algo inesperado, eso es un fallo de seguridad y quiero saberlo.

2. **Los mensajes del salón.** Llegan por Bluetooth o Wi-Fi Direct desde otro
   móvil, y **no están autenticados**: quien esté en el radio de alcance y tenga
   Funny puede unirse a un salón que esté anunciándose. Lo que sí está acotado:

   - El códec convierte cualquier cosa ilegible o de una versión futura en un
     mensaje `Desconocido` en lugar de fallar, y hay pruebas con basura de entrada.
   - **La mesa es la única autoridad.** Un mando no calcula nada; envía la acción
     que ha pulsado y recibe la vista que le toca.
   - La mesa comprueba **de quién** viene cada acción: una pulsación de quien no
     tiene el turno se ignora.
   - El identificador del servicio lleva la versión del protocolo, así que dos
     móviles incompatibles no llegan ni a verse.

   **Lo que un intruso en alcance sí podría hacer**: unirse a la partida como un
   participante más y ver el contenido público. **Lo que no**: ver la palabra
   secreta de otro (se enruta solo a quien actúa), forzar un resultado, ni sacar
   nada del móvil, porque el protocolo no tiene ningún mensaje que lea datos del
   dispositivo. Para un juego de mesa entre gente que está en la misma habitación,
   ese nivel es el adecuado; si algún día se quisiera un código de sala, sería
   precisamente para esto.

3. **Los nombres que escribe la gente.** Nombres de equipo y de jugador, que viajan
   por el salón y se pintan en pantalla. No hay `WebView` en ninguna parte, así que
   no hay inyección de HTML posible; y no hay base de datos SQL, así que tampoco
   SQL.

### Permisos

Cada permiso del manifiesto lleva escrito al lado para qué se pide. Los que existen
son los del salón (Bluetooth, Wi-Fi cercano y, solo hasta Android 12, ubicación,
que es lo que el sistema exige para buscar por Bluetooth) y nada más. Los de
Bluetooth y ubicación llevan `maxSdkVersion` para no pedirlos donde ya no hacen
falta, y el de Wi-Fi cercano va con `neverForLocation`.

Si crees que alguno sobra, dilo: el criterio es de mínimo privilegio.

---

## Secretos y firma

- **No hay ningún secreto en el repositorio**, ni en su historia.
- El keystore de release y sus contraseñas se leen de **variables de entorno** o de
  `local.properties`, que está en `.gitignore`. Si no están, la release se compila
  **sin firmar** y avisa, en lugar de firmar con una clave de mentira.
- En GitHub Actions van como *secrets* del repositorio.
- Cómo regenerar la clave está en [docs/INSTALL.md](docs/INSTALL.md).

Si encuentras un secreto filtrado en la historia de git, avísame por correo
inmediatamente y no lo publiques.
