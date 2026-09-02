# Firma de release

Estado: **el keystore existe y los binarios de esta carpeta están firmados y
verificados.**

---

## Dónde está la clave

| | |
|---|---|
| Fichero | `%USERPROFILE%\keystores\funny-release.jks` |
| Alias | `funny` |
| Algoritmo | RSA 4096 · SHA384withRSA |
| Validez | 10.000 días (hasta ~2053) |
| Titular | `CN=Brais Galdo, O=Ghato Studio, L=Galicia, C=ES` |
| Contraseña | 28 caracteres alfanuméricos · **no está escrita en ningún fichero del repositorio** |

**Vive fuera del árbol de git, a propósito.** El repositorio nunca la ha visto y
no debe verla: ni el `.jks`, ni la contraseña, ni en la historia.

Las contraseñas están en `local.properties`, que está en `.gitignore`. Comprobado:

```bash
git check-ignore -v local.properties
# .gitignore:3:local.properties	local.properties
```

### Por qué la contraseña es alfanumérica y no lleva símbolos

Porque viaja por tres sitios que escapan los caracteres especiales de forma
distinta: variables de entorno, un fichero `.properties` de Java y los *secrets*
de GitHub Actions. Veintiocho caracteres alfanuméricos dan ~166 bits de entropía,
que es más que suficiente, y no se rompen en ninguno de los tres.

---

## Huella del certificado

Esto **sí** es público, y sirve para comprobar que un APK descargado es el
auténtico:

```
Certificado SHA-256:  58d7030c7ecad5e9f94bbcb800a5c27bab4e88cb415a3b2146fa8507d00abf26
Clave pública SHA-256: 10a16cb04d9e856d7db78ee8cd9003e1b0880d2f119763ffededa2a5bab85055
```

Para verificar cualquier APK:

```bash
$ANDROID_HOME/build-tools/*/apksigner verify --verbose --print-certs funny.apk
```

Si la huella no coincide con la de arriba, **ese APK no lo he firmado yo**.

---

## Esquemas de firma

| Esquema | Activo | Por qué |
|---|---|---|
| v1 (JAR) | **No** | Solo hace falta por debajo de API 24, y el mínimo es 26 |
| v2 | **Sí** | El que usa Android 7.0+ |
| v3 | **Sí** | El que permite **rotar la clave** más adelante |
| v4 | No | Solo acelera `adb install` incremental; no aporta nada aquí |

El v3 está activado **explícitamente** en `app/build.gradle.kts`, porque con
`minSdk 26` AGP lo deja apagado por defecto. Importa: es lo que permitiría
sustituir la clave sin perder la identidad de la app, y **no se puede añadir a
posteriori** — la primera release publicada fija el esquema para siempre.

Verificado:

```
Verified using v2 scheme (APK Signature Scheme v2): true
Verified using v3 scheme (APK Signature Scheme v3): true
```

---

## Compilar firmado

Ya está configurado en `local.properties`, así que basta con:

```bash
python docs/google_play/preparar-binarios.py
```

Eso ejecuta `:app:check`, compila el AAB y el APK, **verifica la firma con
`apksigner`** y los deja en `binarios/`. Si no encontrara la firma, sale con
código 1 en lugar de entregar un binario inservible.

A mano, si hace falta:

```bash
./gradlew :app:bundleRelease :app:assembleRelease
```

### En otra máquina

Copia el `.jks` y pon esto en su `local.properties` (o en variables de entorno
`FUNNY_KEYSTORE`, `FUNNY_KEYSTORE_PASSWORD`, `FUNNY_KEY_ALIAS`,
`FUNNY_KEY_PASSWORD`):

```properties
funny.keystore=C\:\\ruta\\funny-release.jks
funny.keystore.password=...
funny.key.alias=funny
funny.key.password=...
```

En un fichero `.properties` de Java hay que escapar las barras **y los dos
puntos** de una ruta de Windows.

---

## En GitHub Actions

El workflow de release espera cuatro *secrets* del repositorio:

| Secret | Contenido |
|---|---|
| `FUNNY_KEYSTORE_BASE64` | el `.jks` en base64 |
| `FUNNY_KEYSTORE_PASSWORD` | la contraseña |
| `FUNNY_KEY_ALIAS` | `funny` |
| `FUNNY_KEY_PASSWORD` | la contraseña |

Para el primero:

```bash
base64 -w0 ~/keystores/funny-release.jks > keystore.b64
# Pega el contenido en Settings → Secrets and variables → Actions
rm keystore.b64
```

El workflow comprueba que los cuatro están **antes** de compilar, reconstruye el
`.jks` en el runner, firma, **verifica con `apksigner`** y borra el `.jks` al
terminar.

---

## Copia de seguridad — léelo ahora, no después

**Si se pierde este keystore o su contraseña, Funny no se podrá volver a
actualizar en Google Play con esa ficha.** No hay recuperación por parte de
Google, no hay procedimiento de rescate y no hay excepciones. Habría que publicar
una app nueva, con otro `applicationId`, y perder todas las instalaciones y
reseñas.

Qué hacer, hoy:

1. **La contraseña, en un gestor de contraseñas.** No en un `.txt` en el
   escritorio ni en un correo a ti mismo.
2. **El `.jks`, en dos sitios más** que no sean este ordenador: un disco externo
   y un almacenamiento cifrado. Son 4 KB.
3. **Nunca en el repositorio**, ni siquiera en una rama privada. Un repositorio
   público puede volverse accidentalmente accesible; un `git filter-branch` a
   posteriori no borra los *forks* ni las cachés.

### Play App Signing: actívalo

Al subir la primera versión, Google ofrece **Play App Signing**. Actívalo.

Con él, Google guarda la clave con la que se firma lo que llega a los usuarios y
esta pasa a ser solo la **clave de subida**. Y una clave de subida perdida **sí se
puede reemplazar** pidiéndoselo a soporte. Sin él, la pérdida es definitiva.

Es la diferencia entre un problema molesto y un problema sin solución.

---

## Si algún día hay que rotar la clave

Es la razón de tener el v3 activado. El procedimiento, resumido:

1. Generar la clave nueva.
2. `apksigner rotate` para crear el linaje, o dejar que Play lo gestione si está
   Play App Signing activado.
3. Firmar con `--lineage`, para que Android acepte la nueva como continuación de
   la vieja.

Con Play App Signing es un formulario. Sin él, y sin v3, no se puede.
