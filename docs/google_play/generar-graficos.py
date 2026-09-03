#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Genera los graficos de la ficha de Google Play.

    python docs/google_play/generar-graficos.py

Salida en `graficos/`:

    icono-512.png              512x512, sin transparencia (Play lo exige asi)
    destacado-1024x500.png     grafico destacado
    icono-1024.png             1024x1024 para App Store, cuando llegue

El icono NO se dibuja de cero: reproduce las mismas formas y los mismos colores
que `app/src/main/res/drawable/ic_launcher_foreground.xml`, el icono real de la
app, sobre el mismo fondo `#12071F`. Es a proposito: la gente reconoce una app en
la tienda por su icono, y un icono de tienda distinto del de la app es una forma
tonta de perder instalaciones.

Las coordenadas del vector estan en un lienzo de 108x108 (lo que usa Android para
los iconos adaptativos) y aqui se escalan. Si alguien cambia el vector, hay que
cambiar tambien las formas de este fichero: son dos sitios, y no hay forma barata
de leer un `<vector>` de Android desde Python.
"""
import io
import math
import os
import sys

try:
    from PIL import Image, ImageDraw, ImageFont
except ImportError:
    sys.exit("Falta Pillow:  pip install Pillow")

AQUI = os.path.dirname(os.path.abspath(__file__))
SALIDA = os.path.join(AQUI, "graficos")

# Los mismos colores del icono de la app.
FONDO = (0x12, 0x07, 0x1F)
AMARILLO = (0xFF, 0xD1, 0x66)
ROSA = (0xEF, 0x47, 0x6F)
VERDE = (0x06, 0xD6, 0xA0)
AZUL = (0x11, 0x8A, 0xB2)
BLANCO = (0xFF, 0xFF, 0xFF)
TENUE = (0x9A, 0x92, 0xAD)

# El lienzo del vector original.
VECTOR = 108.0


def fuente(tamano, negrita=True):
    """La primera fuente decente que haya en el sistema."""
    candidatas = [
        r"C:\Windows\Fonts\segoeuib.ttf" if negrita else r"C:\Windows\Fonts\segoeui.ttf",
        r"C:\Windows\Fonts\arialbd.ttf" if negrita else r"C:\Windows\Fonts\arial.ttf",
        "/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf",
        "/System/Library/Fonts/Helvetica.ttc",
    ]
    for ruta in candidatas:
        if os.path.exists(ruta):
            try:
                return ImageFont.truetype(ruta, tamano)
            except OSError:
                continue
    return ImageFont.load_default()


# --------------------------------------------------------------------------
# La marca
#
# Las formas van como datos y no como llamadas de dibujo, para poder calcular su
# caja envolvente antes de pintar. Hace falta: el contenido del vector de Android
# NO llena su lienzo de 108x108 —ocupa x 26..82, y 18..76— y escalando sobre el
# lienzo entero el icono salia pequeno y descentrado. Con la caja calculada, la
# marca se ajusta de verdad al hueco que se le da.
# --------------------------------------------------------------------------

# ("poligono", [(x, y), ...], color)  |  ("circulo", (cx, cy, r), color)
FORMAS = [
    # Gorro: cuerpo, franja superior y ala.
    ("poligono", [(54, 32), (38, 74), (70, 74)], "AMARILLO"),
    ("poligono", [(54, 32), (47.5, 49), (60.5, 49)], "ROSA"),
    ("poligono", [(44.5, 60), (42, 66.5), (66, 66.5), (63.5, 60)], "VERDE"),
    # Pompon.
    ("circulo", (54, 24, 6), "ROSA"),
    # Confeti.
    ("circulo", (30, 38, 4), "VERDE"),
    ("poligono", [(74, 36), (81, 40), (77, 47), (70, 43)], "AZUL"),
    ("poligono", [(28, 60), (34, 57), (37, 63), (31, 66)], "AMARILLO"),
    ("circulo", (78, 62, 4), "ROSA"),
    ("poligono", [(36, 26), (40, 26), (40, 30), (36, 30)], "BLANCO"),
    ("poligono", [(70, 72), (74, 72), (74, 76), (70, 76)], "BLANCO"),
]

COLORES = {
    "AMARILLO": AMARILLO,
    "ROSA": ROSA,
    "VERDE": VERDE,
    "AZUL": AZUL,
    "BLANCO": BLANCO,
}


def caja_de_las_formas():
    """(x0, y0, x1, y1) de todo lo que se dibuja, en coordenadas del vector."""
    xs, ys = [], []
    for tipo, datos, _ in FORMAS:
        if tipo == "poligono":
            xs += [x for x, _ in datos]
            ys += [y for _, y in datos]
        else:
            cx, cy, r = datos
            xs += [cx - r, cx + r]
            ys += [cy - r, cy + r]
    return min(xs), min(ys), max(xs), max(ys)


def dibujar_marca(d, cx, cy, lado):
    """
    El gorro de fiesta con su confeti, centrado en (cx, cy) y cabiendo en `lado`.

    `lado` es el hueco disponible: la marca se escala para llenarlo conservando
    proporciones, y se centra por su caja real, no por el lienzo del vector.
    """
    x0, y0, x1, y1 = caja_de_las_formas()
    ancho, alto = x1 - x0, y1 - y0
    escala = lado / max(ancho, alto)
    # Traslada el centro de la caja al punto pedido.
    ox = cx - (x0 + ancho / 2.0) * escala
    oy = cy - (y0 + alto / 2.0) * escala

    def p(x, y):
        return (ox + x * escala, oy + y * escala)

    for tipo, datos, nombre in FORMAS:
        color = COLORES[nombre]
        if tipo == "poligono":
            d.polygon([p(x, y) for x, y in datos], fill=color)
        else:
            cx0, cy0, r = datos
            px, py = p(cx0, cy0)
            rr = r * escala
            d.ellipse([px - rr, py - rr, px + rr, py + rr], fill=color)


def confeti_de_fondo(d, ancho, alto, semilla=20260901):
    """
    Confeti disperso y discreto para el grafico destacado.

    Con un generador propio y semilla fija: dos ejecuciones tienen que dar el
    mismo fichero, o el grafico cambiaria en cada build sin motivo.
    """
    estado = semilla

    def siguiente():
        nonlocal estado
        estado = (estado * 1103515245 + 12345) & 0x7FFFFFFF
        return estado / float(0x7FFFFFFF)

    colores = [AMARILLO, ROSA, VERDE, AZUL]
    for _ in range(70):
        x = siguiente() * ancho
        y = siguiente() * alto
        r = 3 + siguiente() * 6
        color = colores[int(siguiente() * len(colores)) % len(colores)]
        # Muy tenue: es fondo, no decoracion protagonista.
        mezcla = tuple(
            int(FONDO[i] + (color[i] - FONDO[i]) * 0.22) for i in range(3)
        )
        if siguiente() < 0.5:
            d.ellipse([x - r, y - r, x + r, y + r], fill=mezcla)
        else:
            giro = siguiente() * math.pi
            puntos = [
                (
                    x + r * math.cos(giro + k * math.pi / 2),
                    y + r * math.sin(giro + k * math.pi / 2),
                )
                for k in range(4)
            ]
            d.polygon(puntos, fill=mezcla)


def icono(lado, ruta):
    """
    Icono cuadrado sin transparencia.

    Play pide PNG de 32 bits **sin canal alfa transparente**, asi que el fondo va
    pintado y no se deja vacio. La marca ocupa el 72 % del lado: es lo que cabe
    dentro del circulo inscrito, y asi no se pierde confeti cuando el lanzador
    recorta el icono en redondo.
    """
    img = Image.new("RGB", (lado, lado), FONDO)
    d = ImageDraw.Draw(img)
    dibujar_marca(d, lado / 2.0, lado / 2.0, lado * 0.72)
    img.save(ruta, "PNG", optimize=True)
    return ruta


def fuente_que_quepa(d, texto, ancho_maximo, tamano_inicial, negrita=True):
    """
    La fuente mas grande con la que `texto` quepa en `ancho_maximo`.

    Existe porque la primera version del grafico destacado dejaba el lema cortado
    por el borde derecho: «13 idioma». Escribir un tamano a ojo y no medirlo es
    justo el error que no se ve hasta que se mira el PNG.
    """
    tamano = tamano_inicial
    while tamano > 8:
        f = fuente(tamano, negrita)
        caja = d.textbbox((0, 0), texto, font=f)
        if caja[2] - caja[0] <= ancho_maximo:
            return f
        tamano -= 1
    return fuente(8, negrita)


def destacado(ruta, ancho=1024, alto=500):
    """
    Grafico destacado 1024x500.

    Play lo recorta por los lados en algunas superficies, asi que se deja un
    margen generoso y nada importante toca el borde. Los tamanos de letra no se
    escriben a ojo: se miden y se reducen hasta que caben.
    """
    img = Image.new("RGB", (ancho, alto), FONDO)
    d = ImageDraw.Draw(img)
    confeti_de_fondo(d, ancho, alto)

    margen = ancho * 0.055
    lado_marca = alto * 0.62
    dibujar_marca(d, margen + lado_marca / 2.0, alto * 0.5, lado_marca)

    x = margen + lado_marca + ancho * 0.045
    disponible = ancho - x - margen

    nombre = "Funny"
    lemas = ("18 juegos · 3 modos · 13 idiomas", "Sin internet, sin anuncios")

    f_nombre = fuente_que_quepa(d, nombre, disponible, int(alto * 0.30))
    f_lema = fuente_que_quepa(
        d, max(lemas, key=len), disponible, int(alto * 0.082), negrita=False
    )

    alto_nombre = d.textbbox((0, 0), nombre, font=f_nombre)[3]
    alto_lema = d.textbbox((0, 0), lemas[0], font=f_lema)[3]
    separacion = alto_lema * 0.45

    # Bloque de texto centrado verticalmente como un todo, no linea a linea.
    alto_bloque = alto_nombre + separacion + alto_lema * 2 + separacion * 0.6
    y = (alto - alto_bloque) / 2.0

    d.text((x, y), nombre, font=f_nombre, fill=BLANCO)
    y += alto_nombre + separacion
    for linea in lemas:
        d.text((x, y), linea, font=f_lema, fill=TENUE)
        y += alto_lema + separacion * 0.6

    img.save(ruta, "PNG", optimize=True)
    return ruta


def main():
    os.makedirs(SALIDA, exist_ok=True)
    hechos = []
    hechos.append(icono(512, os.path.join(SALIDA, "icono-512.png")))
    hechos.append(icono(1024, os.path.join(SALIDA, "icono-1024.png")))
    hechos.append(destacado(os.path.join(SALIDA, "destacado-1024x500.png")))

    print("Graficos de la ficha:")
    for ruta in hechos:
        img = Image.open(ruta)
        print(
            "   %-28s %4dx%-4d  %s  %6.1f KB"
            % (
                os.path.basename(ruta),
                img.width,
                img.height,
                img.mode,
                os.path.getsize(ruta) / 1024.0,
            )
        )
        if img.mode != "RGB":
            print("      OJO: Play quiere RGB sin transparencia")
    return 0


if __name__ == "__main__":
    sys.exit(main())
