#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Comprueba que la política de privacidad está publicada como la quiere Play.

    python docs/google_play/comprobar-privacidad.py

Google Play rechaza la ficha si la URL de la política no abre. No basta con que
abra «en mi navegador»: tiene que abrir **sin sesión**, porque el revisor no
tiene la tuya. Este script hace justo eso —una petición limpia, sin cookies y
sin cabeceras de GitHub— y va diciendo en qué paso está el problema:

    1. ¿Existe el repositorio y es público?
    2. ¿Ha desplegado Pages alguna vez?
    3. ¿Responde la portada?
    4. ¿Responde la política, y trae dentro lo que tiene que traer?

Sale con código 0 solo si las cuatro cosas son ciertas, así que vale para el
checklist de publicación y también para engancharlo a un aviso más adelante.

No necesita token ni instalar nada: solo la librería estándar.
"""
import json
import sys
import urllib.error
import urllib.request

USUARIO = "braisgaldo"
REPO = "Funny"
BASE = "https://%s.github.io/%s" % (USUARIO, REPO)
POLITICA = BASE + "/PRIVACIDAD"

# Trozos que tienen que estar dentro de la página. Si Jekyll sirviera el
# Markdown en crudo, el titulo saldria con la almohadilla y sin etiqueta <h1>.
DEBE_CONTENER = [
    "Funny",
    "privacidad",
]


def pedir(url, cabeceras=None):
    """Una peticion limpia. Devuelve (codigo, cuerpo) y no lanza por un 404."""
    peticion = urllib.request.Request(url, headers=cabeceras or {})
    try:
        with urllib.request.urlopen(peticion, timeout=20) as r:
            return r.status, r.read().decode("utf-8", "replace")
    except urllib.error.HTTPError as e:
        return e.code, e.read().decode("utf-8", "replace")
    except Exception as e:  # DNS, TLS, timeout
        return 0, str(e)


def paso(numero, texto):
    print("\n%d. %s" % (numero, texto))


def bien(texto):
    print("   OK    %s" % texto)


def mal(texto):
    print("   FALLA %s" % texto)


def main():
    print("Comprobando la politica de privacidad de %s/%s" % (USUARIO, REPO))
    print("URL: %s" % POLITICA)
    fallos = 0

    paso(1, "El repositorio")
    codigo, cuerpo = pedir("https://api.github.com/repos/%s/%s" % (USUARIO, REPO))
    if codigo == 404:
        mal("no es visible sin autenticar: sigue en PRIVADO")
        print("         Pages con cuenta gratuita necesita el repositorio publico.")
        fallos += 1
    elif codigo == 200:
        datos = json.loads(cuerpo)
        bien("publico, licencia %s" % (datos.get("license") or {}).get("spdx_id"))
        if not datos.get("has_pages"):
            mal("Pages no esta activado: Settings -> Pages")
            fallos += 1
    else:
        mal("la API responde %d" % codigo)
        fallos += 1

    paso(2, "El despliegue de Pages")
    codigo, cuerpo = pedir(
        "https://api.github.com/repos/%s/%s/actions/workflows" % (USUARIO, REPO)
    )
    desplegado = False
    if codigo == 200:
        nombres = [w["name"] for w in json.loads(cuerpo).get("workflows", [])]
        desplegado = any("pages" in n.lower() for n in nombres)
        if desplegado:
            bien("existe el workflow de Pages")
        else:
            mal("Pages no ha compilado nunca; workflows: %s" % ", ".join(nombres))
            print("         Settings -> Pages -> Source: Deploy from a branch,")
            print("         rama main, carpeta /docs. Guardar y esperar Actions.")
            fallos += 1
    else:
        print("   ?     no se puede consultar (%d); se sigue por la URL" % codigo)

    paso(3, "La portada")
    codigo, _ = pedir(BASE + "/")
    if codigo == 200:
        bien("responde 200")
    else:
        mal("responde %d" % codigo)
        fallos += 1

    paso(4, "La politica, sin sesion")
    codigo, cuerpo = pedir(POLITICA)
    if codigo != 200:
        mal("responde %d" % codigo)
        fallos += 1
    else:
        bien("responde 200")
        # Que sea HTML y no el Markdown en crudo: si falta la cabecera YAML,
        # Jekyll copia el fichero tal cual y esto lo caza.
        if "<h1" in cuerpo or "<html" in cuerpo.lower():
            bien("es HTML procesado, no Markdown en crudo")
        else:
            mal("parece Markdown sin procesar: falta la cabecera YAML del fichero")
            fallos += 1
        faltan = [t for t in DEBE_CONTENER if t.lower() not in cuerpo.lower()]
        if faltan:
            mal("no aparece dentro: %s" % ", ".join(faltan))
            fallos += 1
        else:
            bien("el contenido es el que toca")

    print()
    if fallos:
        print("%d cosa(s) por arreglar. La URL NO se puede poner en Play todavia." % fallos)
    else:
        print("Todo en orden. Esta es la URL para la ficha de Play:")
        print("   %s" % POLITICA)
    return 1 if fallos else 0


if __name__ == "__main__":
    sys.exit(main())
