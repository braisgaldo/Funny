#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Comprueba el AAB y el APK **antes** de subirlos a Play.

    python docs/google_play/comprobar-aab.py

Existe porque subir a Play cuesta un `versionCode` **aunque la consola rechace
el bundle**. Ha pasado dos veces:

    10000  (1.0.0)  subido y descartado
    10001  (1.0.1)  subido y RECHAZADO por apuntar al nivel 35 de API

El segundo es el que duele: el artefacto no valía, y el número se gastó igual.
Este script pasa las mismas comprobaciones que hace la consola, en local y sin
consumir nada, y sale con código 1 si alguna falla.

Lo que mira, y por qué cada una:

  1. targetSdk        es lo que rechazó la 1.0.1. Play exige el nivel vigente.
  2. minSdk           tiene que seguir siendo 26; subirlo sin querer deja fuera
                      a dispositivos que ya podían jugar.
  3. versión          el AAB, el APK y build.gradle.kts tienen que coincidir. Un
                      AAB viejo en la carpeta se sube sin que nadie lo note.
  4. firma            v2 y v3, verificadas con apksigner, y sin «unsigned» en el
                      nombre del fichero.
  5. sin INTERNET     es la promesa de la política de privacidad. Si una
                      dependencia lo colara, la ficha pasaría a ser falsa.
  6. sin facturación  ninguna librería de pagos en el DEX (punto 4.4.1).
  7. tamaño           el módulo base no puede pasar de 150 MB.
  8. símbolos         solo informa: es una recomendación de Play, no un error.

No necesita red ni token: lee los ficheros.
"""
import io
import os
import re
import subprocess
import sys
import zipfile

# El nivel que Play exige hoy. Sube aproximadamente una vez al año: cuando la
# consola pida más, se cambia aquí y el script vuelve a servir.
TARGET_SDK_MINIMO = 36
MIN_SDK_ESPERADO = 26
TOPE_MODULO_BASE = 150 * 1024 * 1024

AQUI = os.path.dirname(os.path.abspath(__file__))
RAIZ = os.path.dirname(os.path.dirname(AQUI))
BINARIOS = os.path.join(AQUI, "binarios")
AAB = os.path.join(BINARIOS, "app-release.aab")
APK = os.path.join(BINARIOS, "app-release.apk")

PAGOS = (
    "billingclient", "billing-ktx", "com.android.vending.billing",
    "play-services-wallet", "revenuecat", "purchases-android", "qonversion",
    "adapty",
)

fallos = []
avisos = []


def bien(texto):
    print("   OK    %s" % texto)


def mal(texto):
    print("   FALLA %s" % texto)
    fallos.append(texto)


def aviso(texto):
    print("   AVISO %s" % texto)
    avisos.append(texto)


def paso(numero, titulo):
    print("\n%d. %s" % (numero, titulo))


def buscar_herramienta(nombre):
    """La build-tools mas alta que tenga esa herramienta."""
    sdk = os.environ.get("ANDROID_HOME") or os.environ.get("ANDROID_SDK_ROOT")
    if not sdk:
        propiedades = os.path.join(RAIZ, "local.properties")
        if os.path.exists(propiedades):
            for linea in io.open(propiedades, encoding="utf-8"):
                if linea.startswith("sdk.dir"):
                    sdk = linea.split("=", 1)[1].strip().replace("\\\\", "\\").replace("\\:", ":")
    if not sdk:
        return None
    base = os.path.join(sdk, "build-tools")
    if not os.path.isdir(base):
        return None
    for version in sorted(os.listdir(base), reverse=True):
        for sufijo in (".bat", ".exe", ""):
            ruta = os.path.join(base, version, nombre + sufijo)
            if os.path.exists(ruta):
                return ruta
    return None


def badging():
    """Lo que declara el APK, leido con aapt2."""
    aapt2 = buscar_herramienta("aapt2")
    if not aapt2:
        return None
    p = subprocess.run([aapt2, "dump", "badging", APK], capture_output=True)
    return p.stdout.decode("utf-8", "replace") if p.returncode == 0 else None


def version_del_fuente():
    ruta = os.path.join(RAIZ, "app", "build.gradle.kts")
    m = re.search(r'val versionSemVer = "([^"]+)"', io.open(ruta, encoding="utf-8").read())
    return m.group(1) if m else None


def main():
    print("Comprobando los binarios antes de subirlos a Play")

    for ruta in (AAB, APK):
        if not os.path.exists(ruta):
            print("\nNo existe %s." % os.path.relpath(ruta, RAIZ))
            print("Genera los dos con: python docs/google_play/preparar-binarios.py")
            return 1

    info = badging()

    paso(1, "Nivel de API al que apunta")
    if not info:
        aviso("no se encuentra aapt2; no se puede leer el targetSdk")
    else:
        m = re.search(r"targetSdkVersion:'(\d+)'", info)
        objetivo = int(m.group(1)) if m else 0
        if objetivo >= TARGET_SDK_MINIMO:
            bien("targetSdk %d (Play exige %d o mas)" % (objetivo, TARGET_SDK_MINIMO))
        else:
            mal("targetSdk %d, y Play exige al menos %d: RECHAZARA el bundle"
                % (objetivo, TARGET_SDK_MINIMO))

        paso(2, "Nivel minimo")
        # minSdkVersion, con la S mayuscula: buscar «sdkVersion» no encuentra
        # nada, y la primera version de este script daba minSdk 0 por eso.
        m = re.search(r"minSdkVersion:'(\d+)'", info)
        minimo = int(m.group(1)) if m else 0
        if minimo == MIN_SDK_ESPERADO:
            bien("minSdk %d" % minimo)
        else:
            mal("minSdk %d, y se esperaba %d" % (minimo, MIN_SDK_ESPERADO))

    paso(3, "La version, en los tres sitios")
    fuente = version_del_fuente()
    del_apk = None
    if info:
        m = re.search(r"versionName='([^']+)'", info)
        c = re.search(r"versionCode='(\d+)'", info)
        del_apk = (m.group(1) if m else None, c.group(1) if c else None)
    manifiesto = zipfile.ZipFile(AAB).read("base/manifest/AndroidManifest.xml")
    en_el_aab = fuente and fuente.encode() in manifiesto

    print("      build.gradle.kts: %s" % fuente)
    if del_apk:
        print("      APK:              %s (codigo %s)" % del_apk)
    print("      AAB:              %s" % ("coincide" if en_el_aab else "NO coincide"))

    if del_apk and del_apk[0] != fuente:
        mal("el APK dice %s y el fuente %s: el binario es viejo" % (del_apk[0], fuente))
    elif not en_el_aab:
        mal("el AAB no lleva la version del fuente: regeneralo")
    else:
        bien("los tres coinciden en %s" % fuente)

    paso(4, "Firma")
    if "unsigned" in os.path.basename(APK).lower():
        mal("el nombre del fichero dice «unsigned»")
    apksigner = buscar_herramienta("apksigner")
    if not apksigner:
        aviso("no se encuentra apksigner; la firma no se ha comprobado")
    else:
        p = subprocess.run([apksigner, "verify", "--verbose", APK], capture_output=True)
        salida = p.stdout.decode("utf-8", "replace")
        if p.returncode != 0 or "Verifies" not in salida:
            mal("apksigner no valida el APK")
        else:
            v2 = "v2 scheme (APK Signature Scheme v2): true" in salida
            v3 = "v3 scheme (APK Signature Scheme v3): true" in salida
            if v2 and v3:
                bien("verificada, con los esquemas v2 y v3")
            else:
                mal("falta algun esquema de firma (v2=%s, v3=%s)" % (v2, v3))

    paso(5, "Sin permiso de internet")
    # El manifiesto del AAB va en protobuf, con las cadenas en UTF-8.
    if b"android.permission.INTERNET" in manifiesto:
        mal("el AAB declara INTERNET: la politica de privacidad dejaria de ser cierta")
    else:
        bien("no lo declara")

    paso(6, "Sin librerias de pago")
    z = zipfile.ZipFile(AAB)
    dex = b"".join(z.read(n) for n in z.namelist() if n.endswith(".dex"))
    encontradas = [p for p in PAGOS if p.encode() in dex]
    if encontradas:
        mal("hay rastro de %s en el DEX" % ", ".join(encontradas))
    else:
        bien("ninguna de las %d que se buscan" % len(PAGOS))

    paso(7, "Tamano")
    base = sum(z.getinfo(n).file_size for n in z.namelist() if n.startswith("base/"))
    if base > TOPE_MODULO_BASE:
        mal("el modulo base ocupa %.1f MB y el tope es 150 MB" % (base / 1024 / 1024))
    else:
        bien("modulo base %.1f MB, holgado" % (base / 1024 / 1024))

    paso(8, "Simbolos del codigo nativo (recomendacion, no requisito)")
    tiene = any("nativesymbols" in n.lower() or "debugsymbols" in n.lower()
                for n in z.namelist())
    hay_nativo = any(n.endswith(".so") for n in z.namelist())
    if not hay_nativo:
        bien("no hay codigo nativo")
    elif tiene:
        bien("los lleva")
    else:
        aviso("Play avisara de que faltan, y NO tiene arreglo: las librerias "
              "nativas vienen de AndroidX ya recortadas (solo .dynsym), asi que "
              "no hay simbolos que extraer ni con NDK. No impide publicar.")

    print()
    if fallos:
        print("%d cosa(s) BLOQUEANTE(s). NO subas nada: cada subida gasta un "
              "versionCode aunque la consola la rechace." % len(fallos))
    else:
        print("Listo para subir%s." % (" (con %d aviso)" % len(avisos) if avisos else ""))
        print("   %s" % os.path.relpath(AAB, RAIZ))
    return 1 if fallos else 0


if __name__ == "__main__":
    sys.exit(main())
