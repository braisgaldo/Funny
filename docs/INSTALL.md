# Compilar, firmar e instalar Funny

---

## 1. Instalar la app sin compilar nada

Si solo quieres jugar:

- **Google Play** — pendiente de publicación.
- **APK** — en las [Releases](https://github.com/braisgaldo/Funny/releases).
  Descarga el `.apk`, ábrelo en el móvil y acepta instalar desde esta fuente.
- **F-Droid** — previsto.

Requiere **Android 8.0 (API 26)** o superior.

Para el **salón multidispositivo** hacen falta Google Play Services. Sin ellos la
app avisa y sigue funcionando con un solo móvil.

---

## 2. Lo que hace falta para compilar

| | Versión | Nota |
|---|---|---|
| JDK | **17** | Ni 11 ni 21: AGP 8.7 pide 17 |
| Android SDK | **API 35** | `compileSdk` y `targetSdk` |
| Gradle | 8.11.1 | lo trae el *wrapper*, no lo instales |
| Android Studio | Ladybug o superior | opcional, la línea de comandos basta |

No hace falta instalar Kotlin ni AGP: los fija el *version catalog*
(`gradle/libs.versions.toml`) y los baja Gradle.

### Decirle a Gradle dónde están el JDK y el SDK

`JAVA_HOME` apuntando al JDK 17:

```bash
# Linux / macOS
export JAVA_HOME=/ruta/al/jdk-17

# Windows (PowerShell)
$env:JAVA_HOME = "C:\ruta\al\jdk-17"
```

El SDK de Android, en `local.properties` (que **no** está en el repositorio):

```properties
sdk.dir=C:\\Users\\tu-usuario\\devtools\\android-sdk
```

En Windows, las barras van dobles.

---

## 3. Compilar

```bash
git clone https://github.com/braisgaldo/Funny.git
cd Funny

./gradlew :app:assembleDebug
```

El APK sale en `app/build/outputs/apk/debug/app-debug.apk`.

En Windows, `.\gradlew.bat` en lugar de `./gradlew`.

### Instalar en un móvil conectado

```bash
adb devices                    # que aparezca el móvil
./gradlew :app:installDebug
```

Si `adb devices` sale vacío: activa **Opciones de desarrollador** → **Depuración
por USB** en el móvil y acepta la huella del ordenador cuando salga el diálogo.

---

## 4. Comprobar que está sano

```bash
./gradlew :app:check
```

Eso ejecuta, y todo tiene que estar en verde:

| | |
|---|---|
| `testDebugUnitTest` | **164 pruebas**, en la JVM, sin emulador |
| `ktlintCheck` | formato |
| `lintDebug` | lint de Android, con los errores rompiendo la build |
| `verificarSinFacturacion` | falla si aparece cualquier librería de pagos |
| `verificarTextosLiterales` | falla si hay una cadena escrita a fuego en `ui/` |

Solo las pruebas, que es lo rápido:

```bash
./gradlew :app:testDebugUnitTest
```

Formatear antes de commitear:

```bash
./gradlew :app:ktlintFormat
```

### Comprobar a mano que no hay librería de facturación

La tarea ya lo hace y rompe la build, pero si quieres verlo con tus ojos:

```bash
./gradlew :app:dependencies | grep -i billing
# no debe devolver nada
```

---

## 5. Firma de release

**El keystore no está en el repositorio y no va a estarlo.** La configuración lo
busca en dos sitios, por este orden:

### Opción A — variables de entorno (lo que usa CI)

```bash
export FUNNY_KEYSTORE=/ruta/absoluta/funny-release.jks
export FUNNY_KEYSTORE_PASSWORD=...
export FUNNY_KEY_ALIAS=funny
export FUNNY_KEY_PASSWORD=...
```

### Opción B — `local.properties` (lo cómodo en local)

```properties
funny.keystore=C:\\ruta\\funny-release.jks
funny.keystore.password=...
funny.key.alias=funny
funny.key.password=...
```

Y entonces:

```bash
./gradlew :app:assembleRelease   # APK
./gradlew :app:bundleRelease     # AAB, que es lo que quiere Google Play
```

**Si no hay keystore, la release se compila sin firmar y lo avisa por consola.**
Es deliberado: preferible un aviso claro a firmar con una clave de mentira y
descubrirlo al subirla a la tienda.

### Crear el keystore desde cero

```bash
keytool -genkeypair -v \
  -keystore funny-release.jks \
  -alias funny \
  -keyalg RSA -keysize 4096 \
  -validity 10000
```

Tres cosas, en serio:

1. **Guárdalo fuera del repositorio** y con copia en otro sitio.
2. **Si lo pierdes, no puedes volver a actualizar la app en Google Play** con esa
   ficha. No hay recuperación. Con Play App Signing activado, Google guarda la
   clave de firma de la app y la tuya pasa a ser solo la de subida, que sí se puede
   reemplazar pidiéndoselo a soporte; sin eso, la pérdida es definitiva.
3. La contraseña no va en ningún fichero del repositorio.

---

## 6. Cómo se numeran las versiones

`versionName` es SemVer puro. `versionCode` se deriva con:

```
versionCode = major * 10_000 + minor * 100 + patch
```

1.0.0 → 10000 · 1.0.1 → 10001 · 1.1.0 → 10100 · 2.0.0 → 20000.

Es monótona y no hace falta llevar la cuenta a mano. El límite: `minor` y `patch`
por debajo de 100, y hay un `require` en `build.gradle.kts` que lo comprueba.

La compilación también incrusta el **hash corto del commit** y la **fecha del
commit** (no la del reloj, para que la build sea reproducible). Los dos se ven en
Ajustes → Acerca de.

---

## 7. Cuando algo falla

**`JAVA_HOME is not set`** — no has exportado `JAVA_HOME`. Ver el punto 2.

**`Unsupported class file major version`** — JDK equivocado. Tiene que ser el 17;
compruébalo con `java -version`.

**`SDK location not found`** — falta `local.properties` con `sdk.dir`, o la ruta
está mal escrita (en Windows, barras dobles).

**`No connected devices!`** — el móvil no está en `adb devices`. Depuración por USB
activada y huella aceptada.

**`ktlintCheck` falla** — ejecuta `./gradlew :app:ktlintFormat` y vuelve a mirar. Lo
que el formateador no arregla solo suele ser una línea de más de 100 columnas.

**`lintDebug` falla** — el informe está en
`app/build/reports/lint-results-debug.html` y también en texto en
`app/build/intermediates/lint_intermediate_text_report/`. Aquí los errores rompen
la build a propósito.

**Falla una prueba de catálogos** — casi siempre es una clave nueva sin traducir en
alguno de los trece idiomas, o un parámetro (`%1$d`) que se ha perdido al traducir.
El mensaje del test dice el idioma y la clave.

**Compila pero el salón no encuentra a nadie** — mira que los dos móviles tengan
Bluetooth encendido, que sean la misma versión de Funny (el protocolo lleva versión
y dos incompatibles no se ven) y, si son Android 12 o anterior, que hayan concedido
el permiso de ubicación. La pantalla del salón diagnostica los cuatro casos.
