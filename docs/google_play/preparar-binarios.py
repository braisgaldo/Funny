#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Compila los binarios de la ficha y los deja en `binarios/`.

    python docs/google_play/preparar-binarios.py [--debug]

Sin argumentos compila la RELEASE: el AAB, que es lo que quiere Google Play, y el
APK, que es lo que quiere quien lo instala a mano. Con `--debug` compila el APK de
depuracion, que sirve para probar pero NO para subir a la tienda.

`binarios/` esta en .gitignore. El punto 14 del brief pide el APK en esta carpeta
y el punto 8 dice que los binarios no se commitean; las dos cosas no pueden ser a
la vez, asi que se generan aqui y viven en la GitHub Release de cada version. El
README de la carpeta lo explica.

El script NO firma nada por su cuenta: la firma sale del keystore que encuentre
`build.gradle.kts` en las variables de entorno o en local.properties. Si no lo
encuentra, la release sale sin firmar, y **este script lo dice y falla** en lugar
de dejar un AAB inservible con pinta de bueno.
"""
import argparse
import glob
import io
import os
import shutil
import subprocess
import sys

AQUI = os.path.dirname(os.path.abspath(__file__))
RAIZ = os.path.abspath(os.path.join(AQUI, "..", ".."))
DESTINO = os.path.join(AQUI, "binarios")


def gradle(*tareas):
    orden = [
        os.path.join(RAIZ, "gradlew.bat" if os.name == "nt" else "gradlew"),
        *tareas,
        "--console=plain",
    ]
    print("$ " + " ".join(tareas))
    resultado = subprocess.run(orden, cwd=RAIZ)
    return resultado.returncode == 0


def ruta_del_sdk():
    """
    La ruta del SDK de Android.

    Ojo con el escapado de `local.properties`: Gradle guarda ahi la ruta con los
    dos puntos Y las barras escapados (`C\\:\\\\Users\\\\...`). La primera version de
    esto solo deshacia las barras, dejaba un `C\\:` que no es ninguna ruta, y por
    eso no encontraba apksigner.
    """
    sdk = os.environ.get("ANDROID_HOME") or os.environ.get("ANDROID_SDK_ROOT")
    if sdk:
        return sdk if os.path.isdir(sdk) else None
    ruta = os.path.join(RAIZ, "local.properties")
    if not os.path.exists(ruta):
        return None
    for linea in io.open(ruta, encoding="utf-8"):
        if linea.startswith("sdk.dir="):
            valor = linea.split("=", 1)[1].strip()
            valor = valor.replace("\\\\", "\\").replace("\\:", ":")
            return valor if os.path.isdir(valor) else None
    return None


def apksigner():
    """La ruta de apksigner del SDK, si se puede encontrar."""
    sdk = ruta_del_sdk()
    if not sdk:
        return None
    for nombre in ("apksigner.bat", "apksigner"):
        candidatos = sorted(glob.glob(os.path.join(sdk, "build-tools", "*", nombre)))
        if candidatos:
            return candidatos[-1]
    return None


def comprobar_firma(apk):
    """
    True si el APK esta firmado, False si no, None si no se ha podido comprobar.

    Hace falta comprobarlo: el build imprime un aviso y SIGUE si no encuentra el
    keystore, asi que la existencia del fichero no dice nada sobre su firma.

    Dos comprobaciones, y la primera es la que de verdad cierra el agujero: AGP
    llama al artefacto `app-release-unsigned.apk` cuando no hay firma, y ese
    nombre es prueba suficiente sin necesitar ninguna herramienta. La primera
    version de este script solo miraba apksigner, no lo encontraba, devolvia None
    y aceptaba tan tranquila un APK con «unsigned» en el nombre.
    """
    if "unsigned" in os.path.basename(apk).lower():
        return False

    herramienta = apksigner()
    if not herramienta:
        print("   (no se encuentra apksigner: no se ha podido verificar del todo)")
        return None
    resultado = subprocess.run(
        [herramienta, "verify", apk], capture_output=True, text=True, errors="replace"
    )
    return resultado.returncode == 0


def copiar(patron, etiqueta):
    encontrados = glob.glob(os.path.join(RAIZ, patron))
    if not encontrados:
        print("   NO se encuentra %s (%s)" % (etiqueta, patron))
        return []
    copiados = []
    for origen in encontrados:
        destino = os.path.join(DESTINO, os.path.basename(origen))
        shutil.copy2(origen, destino)
        copiados.append(destino)
        print("   %-34s %8.1f KB" % (os.path.basename(destino),
                                     os.path.getsize(destino) / 1024.0))
    return copiados


def main():
    ap = argparse.ArgumentParser(description="Compila los binarios de la ficha.")
    ap.add_argument(
        "--debug",
        action="store_true",
        help="APK de depuracion en lugar de la release (no sirve para la tienda)",
    )
    args = ap.parse_args()

    os.makedirs(DESTINO, exist_ok=True)

    if args.debug:
        print("Compilando el APK de DEPURACION. No sirve para subir a la tienda.\n")
        if not gradle(":app:assembleDebug"):
            return 1
        copiar("app/build/outputs/apk/debug/*.apk", "el APK de depuracion")
        print("\nHecho. Recuerda: esto NO se sube a Play.")
        return 0

    print("Compilando la RELEASE.\n")

    # Una release no sale de un arbol que no pasa las pruebas.
    if not gradle(":app:check"):
        print("\n`check` ha fallado. No se compila ninguna release.")
        return 1

    if not gradle(":app:bundleRelease", ":app:assembleRelease"):
        return 1

    print("\nBinarios:")
    aab = copiar("app/build/outputs/bundle/release/*.aab", "el AAB")
    apk = copiar("app/build/outputs/apk/release/*.apk", "el APK")

    if not aab or not apk:
        return 1

    print("\nFirma:")
    firmado = comprobar_firma(apk[0])
    if firmado is False:
        print("   SIN FIRMAR. No se puede subir a Play ni instalar como")
        print("   actualizacion. Configura el keystore: docs/INSTALL.md,")
        print("   apartado «Firma de release».")
        return 1
    if firmado:
        print("   verificada con apksigner")
    else:
        print("   AVISO: no se ha podido verificar del todo. No subas esto a Play")
        print("   sin comprobarlo a mano:  apksigner verify <apk>")

    print("\nListo en docs/google_play/binarios/ (no va en git).")
    return 0


if __name__ == "__main__":
    sys.exit(main())
