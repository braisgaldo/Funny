import java.io.ByteArrayOutputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ktlint)
}

// --------------------------------------------------------------------------
// Versión
//
// versionName es SemVer puro. versionCode se deriva de él con la fórmula
//
//     versionCode = major * 10_000 + minor * 100 + patch
//
// que da 10000 para 1.0.0, 10001 para 1.0.1 y 10100 para 1.1.0. Es monótona
// mientras ningún componente pase de 99, deja hueco para parches y permite
// leer la versión de un APK sin abrirlo. Queda documentada en
// docs/ARCHITECTURE.md por si algún día hay que cambiarla.
// --------------------------------------------------------------------------
val versionSemVer = "1.0.1"

fun codigoDeVersion(semver: String): Int {
    val partes = semver.split(".").map { it.toIntOrNull() ?: 0 }
    val major = partes.getOrElse(0) { 0 }
    val minor = partes.getOrElse(1) { 0 }
    val patch = partes.getOrElse(2) { 0 }
    require(minor < 100 && patch < 100) { "La fórmula de versionCode no admite minor/patch >= 100" }
    return major * 10_000 + minor * 100 + patch
}

/** Ejecuta un comando y devuelve su salida, o [porDefecto] si falla. */
fun ordenDelSistema(porDefecto: String, vararg orden: String): String =
    runCatching {
        val salida = ByteArrayOutputStream()
        providers
            .exec {
                commandLine(*orden)
                isIgnoreExitValue = true
            }.standardOutput.asText
            .get()
            .trim()
            .ifEmpty { salida.toString().trim() }
    }.getOrNull()?.takeIf { it.isNotEmpty() } ?: porDefecto

// Hash y fecha del commit. Se usan en «Acerca de» para poder identificar
// exactamente qué código lleva un APK que alguien nos envíe. Se toma la fecha
// del commit y no la del reloj para que dos builds del mismo código sean
// idénticas.
val hashCommit = ordenDelSistema("sin-git", "git", "rev-parse", "--short=8", "HEAD")
val fechaCompilacion = ordenDelSistema("desconocida", "git", "log", "-1", "--format=%cs")

// --------------------------------------------------------------------------
// Firma de release
//
// El keystore NUNCA vive en el repositorio. Se busca, por este orden, en las
// variables de entorno (para GitHub Actions) y en local.properties (para el
// portátil). Si no aparece, el release se genera sin firmar y se avisa: es
// preferible a fallar la build de quien solo quiere compilar el proyecto.
// El procedimiento de regeneración está en docs/INSTALL.md.
// --------------------------------------------------------------------------
val propiedadesLocales =
    Properties().apply {
        val fichero = rootProject.file("local.properties")
        if (fichero.exists()) fichero.inputStream().use { load(it) }
    }

fun secreto(clave: String): String? =
    (System.getenv(clave.replace('.', '_').uppercase()) ?: propiedadesLocales.getProperty(clave))
        ?.takeIf { it.isNotBlank() }

val rutaKeystore = secreto("funny.keystore")
val hayFirmaDeRelease = rutaKeystore != null && file(rutaKeystore).exists()

android {
    namespace = "es.ghatostudio.funny"
    compileSdk = 36

    defaultConfig {
        applicationId = "es.ghatostudio.funny"
        minSdk = 26
        targetSdk = 36
        versionCode = codigoDeVersion(versionSemVer)
        versionName = versionSemVer

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "HASH_COMMIT", "\"$hashCommit\"")
        buildConfigField("String", "FECHA_COMPILACION", "\"$fechaCompilacion\"")
        buildConfigField("String", "ENLACE_DONACION", "\"https://revolut.me/brais2oz6\"")
        buildConfigField("String", "CORREO_CONTACTO", "\"GhatoStudioOfficial@gmail.com\"")
        buildConfigField("String", "EXTENSION_DATOS", "\".funny.bak\"")
        buildConfigField("String", "PAGINA_PROYECTO", "\"https://braisgaldo.github.io/Funny/\"")
        buildConfigField("String", "REPOSITORIO", "\"https://github.com/braisgaldo/Funny\"")

        // La app admite trece idiomas y el árabe obliga a RTL. Se declara aquí
        // para que el APK conserve los recursos de todos ellos.
        resourceConfigurations +=
            listOf(
                "en",
                "es",
                "fr",
                "de",
                "zh",
                "ja",
                "ru",
                "it",
                "el",
                "ar",
                "gl",
                "ca",
                "eu",
            )
    }

    signingConfigs {
        if (hayFirmaDeRelease) {
            create("release") {
                storeFile = file(rutaKeystore!!)
                storePassword = secreto("funny.keystore.password")
                keyAlias = secreto("funny.key.alias")
                keyPassword = secreto("funny.key.password")

                // Explícitos y no por defecto. Con minSdk 26, AGP firma solo con
                // v2 y deja el v3 apagado, y el v3 es el que permite ROTAR la
                // clave más adelante sin perder la identidad de la app. Es la
                // clase de cosa que no se puede añadir a posteriori: la primera
                // release que se publique fija el esquema para siempre.
                enableV1Signing = false // solo hace falta por debajo de API 24
                enableV2Signing = true
                enableV3Signing = true
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            // Símbolos de depuración del código nativo, para el AAB.
            //
            // Play avisa de que el bundle lleva código nativo sin símbolos. Ese
            // código **no es de este proyecto**: son dos librerías que entran
            // con AndroidX y que ni se llaman directamente.
            //
            //     libandroidx.graphics.path.so      (Compose UI, ~10 KB)
            //     libdatastore_shared_counter.so    (DataStore, ~7 KB)
            //
            // Con esto, si alguna vez un fallo ocurre dentro de una de las dos,
            // la consola enseña nombres de función en lugar de direcciones. Es
            // lo único que aporta: no hay nada nuestro que simbolizar.
            //
            // SYMBOL_TABLE y no FULL: la tabla de símbolos basta para leer una
            // pila de llamadas, y FULL mete la información de depuración
            // completa de librerías que vienen ya recortadas, así que engordaría
            // la subida sin añadir nada. El fichero viaja **solo en el AAB**, no
            // en lo que se instala.
            //
            // OJO, y por eso está escrito: **extraer los símbolos necesita el
            // NDK**, porque AGP usa su `objcopy`. En una máquina sin NDK la
            // tarea corre, avisa con «Unable to strip the following libraries,
            // packaging them as they are» y el AAB sale sin símbolos. El AAB del
            // workflow de release SÍ los lleva, porque el runner de Ubuntu de
            // GitHub trae NDK. Si se compila la release a mano y se quiere el
            // fichero, hay que instalar el NDK (unos 2 GB) para dos librerías de
            // 10 KB que no son nuestras; Play solo lo pide como recomendación.
            ndk {
                debugSymbolLevel = "SYMBOL_TABLE"
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            if (hayFirmaDeRelease) {
                signingConfig = signingConfigs.getByName("release")
            } else {
                logger.lifecycle(
                    "Funny: sin keystore configurado, el release saldrá SIN FIRMAR. " +
                        "Ver docs/INSTALL.md, apartado «Firma de release».",
                )
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    lint {
        // Explícito y no por defecto: que un error de lint rompa la build es
        // parte del criterio del proyecto, y no quiero que dependa de que AGP
        // no cambie de opinión en una versión futura.
        abortOnError = true
        // Los avisos NO rompen la build. Hay 48 y muchos son sugerencias de
        // versión de dependencia; convertirlos en errores dejaría la build roja
        // por cosas que no lo merecen. Se revisan a mano en cada hito.
        warningsAsErrors = false
        checkReleaseBuilds = true
        // Deja el informe en texto además del HTML, para poder leerlo en consola
        // y en CI sin abrir un navegador.
        textReport = true
        htmlReport = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.documentfile)
    implementation(libs.kotlinx.coroutines.android)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    implementation(libs.play.services.nearby)
    implementation(libs.qrcode.kotlin)

    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.zxing.core)
    testImplementation(libs.json)

    androidTestImplementation(libs.androidx.test.junit)
}

ktlint {
    version.set("1.3.1")
    android.set(true)
    ignoreFailures.set(false)
    filter {
        val carpetaBuild = "${File.separator}build${File.separator}"
        exclude { elemento ->
            elemento.file.path.contains(carpetaBuild)
        }
    }
}

// --------------------------------------------------------------------------
// Verificaciones propias
// --------------------------------------------------------------------------

/**
 * La donación de Funny no es una compra: no desbloquea nada y se paga fuera de
 * la app. Para poder declarar «sin compras integradas» en Google Play y en App
 * Store Connect con la conciencia tranquila, esta tarea falla la build si
 * alguna librería de facturación entra en el classpath, aunque sea de forma
 * transitiva. Ver ADR-0004 y docs/PUBLICACION.md.
 */
val bibliotecasDeFacturacionProhibidas =
    listOf(
        "billingclient",
        "billing-ktx",
        "com.android.vending.billing",
        "play-services-wallet",
        "revenuecat",
        "purchases-android",
        "qonversion",
        "adapty",
    )

tasks.register("verificarSinFacturacion") {
    group = "verification"
    description = "Falla si aparece cualquier librería de pagos en el classpath de release."

    val configuraciones = listOf("releaseRuntimeClasspath", "debugRuntimeClasspath")
    val artefactos =
        configuraciones.mapNotNull { nombre ->
            val resueltos =
                configurations
                    .findByName(nombre)
                    ?.incoming
                    ?.artifacts
                    ?.resolvedArtifacts
            resueltos?.map { lista -> lista.map { it.id.displayName } }
        }

    doLast {
        val todos = artefactos.flatMap { it.get() }
        val sospechosos =
            todos.filter { artefacto ->
                bibliotecasDeFacturacionProhibidas.any { artefacto.contains(it, ignoreCase = true) }
            }
        if (sospechosos.isNotEmpty()) {
            throw GradleException(
                "Funny no puede llevar librerías de facturación y se han encontrado:\n" +
                    sospechosos.joinToString("\n") { "  - $it" } +
                    "\nVer ADR-0004: la donación se paga en el navegador y no desbloquea nada.",
            )
        }
        logger.lifecycle(
            "verificarSinFacturacion: ${todos.size} artefactos revisados, " +
                "ninguna librería de pagos. Correcto.",
        )
    }
}

/**
 * Ningún texto que vea el usuario puede estar escrito a fuego: todos salen del
 * catálogo de idiomas (ui/i18n). Esta tarea busca literales sospechosos dentro
 * de las pantallas y falla si encuentra alguno, que es lo que pide el punto 4.3
 * de la plantilla.
 *
 * Se permiten a propósito: cadenas vacías, separadores, emojis y símbolos,
 * números, y todo lo que esté marcado con el comentario `// literal-ok`.
 */
tasks.register("verificarTextosLiterales") {
    group = "verification"
    description = "Falla si alguna pantalla lleva texto visible escrito a fuego."

    val fuentes =
        fileTree("src/main/java/es/ghatostudio/funny/ui") {
            include("**/*.kt")
            exclude("i18n/**")
        }
    inputs.files(fuentes)

    doLast {
        // Un texto es sospechoso si tiene dos letras seguidas: así «✓», «·»,
        // «%d» y los emojis pasan, pero «Ajustes» no.
        val conLetras = Regex("""\p{L}{2,}""")

        // Lo que se interpola NO cuenta: las letras visibles dentro de una
        // interpolación son el nombre de una clave o de una variable, no texto
        // de usuario. Sin quitarlo, la comprobación daba falsos positivos y
        // habría acabado ignorada, que es la peor forma en la que puede acabar
        // una verificación.
        //
        // El dólar va como clase de un carácter porque en una cadena sin
        // escapes de Kotlin no se puede escapar con barra invertida.
        val interpolada = Regex("""[${'$'}]\{[^}]*\}|[${'$'}][A-Za-z_][A-Za-z0-9_.]*""")

        // Solo se miran las líneas que de verdad pintan algo.
        val marcasDeTexto =
            listOf(
                "Text(",
                "texto =",
                "titulo =",
                "etiqueta =",
                "contentDescription =",
            )

        val fallos = mutableListOf<String>()

        fuentes.forEach { fichero ->
            fichero.readLines().forEachIndexed { indice, linea ->
                if (linea.contains("literal-ok")) return@forEachIndexed
                val recortada = linea.substringBefore("//")
                if (marcasDeTexto.none { recortada.contains(it) }) return@forEachIndexed

                // Los literales se extraen partiendo por la comilla y quedándose
                // con los trozos impares. Es más tosco que una expresión regular
                // pero no depende de escapar nada, y para una heurística de lint
                // es de sobra.
                recortada
                    .split('"')
                    .filterIndexed { posicion, _ -> posicion % 2 == 1 }
                    .forEach { crudo ->
                        val contenido = crudo.replace(interpolada, "")
                        if (conLetras.containsMatchIn(contenido)) {
                            val donde = "${fichero.relativeTo(projectDir)}:${indice + 1}"
                            fallos += "$donde  ->  $contenido"
                        }
                    }
            }
        }

        if (fallos.isNotEmpty()) {
            throw GradleException(
                "Hay textos escritos a fuego en la interfaz. Muévelos al catálogo " +
                    "de ui/i18n, o marca la línea con «// literal-ok» si de verdad " +
                    "no es un texto de usuario:\n" +
                    fallos.joinToString("\n") { "  $it" },
            )
        }
        logger.lifecycle("verificarTextosLiterales: sin textos a fuego en la interfaz. Correcto.")
    }
}

tasks.named("check") {
    dependsOn("verificarSinFacturacion", "verificarTextosLiterales")
}
