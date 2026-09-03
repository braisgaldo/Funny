package es.ghatostudio.funny.ui.i18n

import es.ghatostudio.funny.dominio.Juego
import es.ghatostudio.funny.dominio.textos.Clave
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Los catálogos de idioma.
 *
 * El test más importante es el de completitud: si alguien añade una clave y se
 * olvida de un idioma, la build falla en lugar de dejar que salga
 * `AJUSTES_TITULO` en la pantalla de alguien. Se le exige a los trece.
 */
class PruebaCatalogos {
    @Test
    fun `los trece idiomas cubren todas las claves`() {
        val todas = Clave.entries.toSet()
        todosLosCatalogos
            .filter { it.idioma in IDIOMAS_TERMINADOS }
            .forEach { catalogo ->
                val faltan = todas - catalogo.textos.keys
                assertTrue(
                    faltan.isEmpty(),
                    "a ${catalogo.idioma} le faltan ${faltan.size} claves: " +
                        faltan.take(10).joinToString(),
                )
            }
    }

    @Test
    fun `ningun catalogo tiene claves que ya no existen`() {
        // Este sí se le exige a todos: una clave de sobra significa que alguien
        // ha borrado una clave del enum y ha dejado la traducción huérfana.
        val todas = Clave.entries.toSet()
        todosLosCatalogos.forEach { catalogo ->
            val sobran = catalogo.textos.keys - todas
            assertTrue(sobran.isEmpty(), "${catalogo.idioma} tiene claves de sobra: $sobran")
        }
    }

    @Test
    fun `los parametros de un texto son los mismos en los trece idiomas`() {
        // Un parametro que se cae al traducir no se nota hasta que alguien
        // pone la app en ese idioma y ve el hueco sin rellenar, o peor: una
        // excepción de formato en mitad de una partida. El inglés manda porque
        // es el catálogo de respaldo.
        // Cadena normal y no cruda: en una cruda el dólar abriría plantilla.
        val patron = Regex("%(\\d+)\\\$[ds]")

        fun parametrosDe(texto: String) =
            patron
                .findAll(texto)
                .map { it.groupValues[1] }
                .toSet()

        val referencia = catalogoDe(Idioma.INGLES)
        Clave.entries.forEach { clave ->
            val esperados = parametrosDe(referencia.textos[clave].orEmpty())
            todosLosCatalogos
                .filter { it.idioma in IDIOMAS_TERMINADOS }
                .forEach { catalogo ->
                    val suyos = parametrosDe(catalogo.textos[clave].orEmpty())
                    assertEquals(
                        esperados,
                        suyos,
                        "${catalogo.idioma} en $clave: el inglés usa $esperados y este $suyos",
                    )
                }
        }
    }

    @Test
    fun `ningun texto esta vacio en ningun idioma`() {
        todosLosCatalogos
            .filter { it.idioma in IDIOMAS_TERMINADOS }
            .forEach { catalogo ->
                val vacias = catalogo.textos.filterValues { it.isBlank() }.keys
                assertTrue(vacias.isEmpty(), "${catalogo.idioma} tiene textos vacíos: $vacias")
            }
    }

    @Test
    fun `ningun texto menciona el nombre anterior de la app`() {
        // La app se llamaba Fiestón. Al renombrarla a Funny se quedo un texto
        // sin cambiar —el nombre del tema en castellano, que seguia diciendo
        // «Fiestón»— y no lo vio nadie hasta abrir los ajustes en un movil. Un
        // resto de un renombrado no da ningun error: solo queda raro.
        val anteriores = listOf("fiestón", "fieston")
        todosLosCatalogos.forEach { catalogo ->
            catalogo.textos.forEach { (clave, texto) ->
                anteriores.forEach { viejo ->
                    assertTrue(
                        !texto.lowercase().contains(viejo),
                        "${catalogo.idioma}/$clave menciona «$viejo»: $texto",
                    )
                }
            }
        }
    }

    @Test
    fun `los trece idiomas estan registrados`() {
        assertEquals(13, Idioma.entries.size)
        Idioma.entries.forEach { idioma ->
            assertEquals(
                idioma,
                catalogoDe(idioma).idioma,
                "el catálogo de $idioma no está registrado",
            )
        }
    }

    @Test
    fun `los codigos y los endonimos de los idiomas son unicos`() {
        assertEquals(
            13,
            Idioma.entries
                .map { it.codigo }
                .toSet()
                .size,
            "códigos repetidos",
        )
        assertEquals(
            13,
            Idioma.entries
                .map { it.endonimo }
                .toSet()
                .size,
            "endónimos repetidos",
        )
    }

    @Test
    fun `solo el arabe va de derecha a izquierda`() {
        val rtl = Idioma.entries.filter { it.esRtl }
        assertEquals(listOf(Idioma.ARABE), rtl, "idiomas RTL: $rtl")
    }

    @Test
    fun `solo el ingles y el arabe llevan icono neutro en lugar de bandera`() {
        // Lo pide el punto 4.3 de la plantilla: ninguno de los dos es de un
        // país concreto y elegir uno sería arbitrario. Los ONCE restantes sí
        // tienen que llevar su bandera, y esa es la mitad que importa de esta
        // prueba: si alguien vuelve a poner letras en un idioma, salta.
        val neutros = Idioma.entries.filter { it.insignia is Insignia.Neutra }
        assertEquals(
            listOf(Idioma.INGLES, Idioma.ARABE),
            neutros,
            "idiomas con icono neutro: $neutros",
        )
    }

    @Test
    fun `los otros once idiomas llevan bandera, en emoji o pintada`() {
        val sinBandera =
            Idioma.entries.filter {
                it.insignia !is Insignia.Bandera && it.insignia !is Insignia.Pintada
            } - setOf(Idioma.INGLES, Idioma.ARABE)
        assertTrue(sinBandera.isEmpty(), "sin bandera: $sinBandera")
        // Ocho con emoji y tres pintadas: once. Las cifras van escritas para que
        // añadir un idioma obligue a decidir qué insignia lleva en lugar de
        // colarse sin ninguna.
        assertEquals(
            8,
            Idioma.entries.count { it.insignia is Insignia.Bandera },
            "banderas con emoji",
        )
        assertEquals(
            3,
            Idioma.entries.count { it.insignia is Insignia.Pintada },
            "banderas pintadas",
        )
        assertEquals(13, Idioma.entries.size, "idiomas")
    }

    @Test
    fun `las tres banderas sin emoji se pintan, y cada una la suya`() {
        // El gallego, el catalán y el euskera tienen bandera, pero su emoji es
        // una secuencia de etiquetas de subdivisión que no está en el conjunto
        // RGI: ninguna fuente de Android la dibuja. Se pintan, y cada idioma
        // tiene que apuntar a un dibujo distinto —copiar y pegar la línea de al
        // lado dejaría dos idiomas con la misma bandera y nadie lo notaría en
        // una lista de trece—.
        val pintadas =
            listOf(Idioma.GALLEGO, Idioma.CATALAN, Idioma.EUSKERA).map {
                val insignia = it.insignia
                assertTrue(insignia is Insignia.Pintada, "$it no se pinta")
                (insignia as Insignia.Pintada).cual
            }
        assertEquals(
            listOf(BanderaDibujada.GALICIA, BanderaDibujada.CATALUNA, BanderaDibujada.EUSKADI),
            pintadas,
        )
        assertEquals(
            BanderaDibujada.entries.size,
            pintadas.toSet().size,
            "hay banderas pintadas repetidas o sin usar: $pintadas",
        )
    }

    @Test
    fun `deEtiqueta reconoce las etiquetas del sistema y cae en ingles si no`() {
        assertEquals(Idioma.CASTELLANO, Idioma.deEtiqueta("es"))
        assertEquals(Idioma.CASTELLANO, Idioma.deEtiqueta("es-ES"))
        assertEquals(Idioma.CASTELLANO, Idioma.deEtiqueta("es_MX"))
        assertEquals(Idioma.CHINO, Idioma.deEtiqueta("zh-Hans-CN"))
        assertEquals(Idioma.GALLEGO, Idioma.deEtiqueta("GL"))
        // Portugués: no está entre los trece, así que cae al respaldo.
        assertEquals(Idioma.INGLES, Idioma.deEtiqueta("pt-BR"))
        assertEquals(Idioma.INGLES, Idioma.deEtiqueta(null))
        assertEquals(Idioma.INGLES, Idioma.deEtiqueta(""))
    }

    // -------------------------------------------------------------- textos

    @Test
    fun `ningun idioma deja salir el nombre de una clave en pantalla`() {
        // Es el respaldo del respaldo: aunque un catálogo perdiera una clave,
        // `Textos` cae al inglés antes de rendirse.
        val textos = textosDe(Idioma.JAPONES)
        Clave.entries.forEach { clave ->
            val valor = textos[clave]
            assertTrue(valor.isNotBlank(), "$clave está vacía")
            assertTrue(
                valor != clave.name,
                "$clave sale como nombre de clave, ni siquiera hay respaldo",
            )
        }
    }

    @Test
    fun `los textos con parametros los sustituyen`() {
        val t = textosDe(Idioma.CASTELLANO)
        val resultado = t.con(Clave.TABLERO_CASILLA, 7)
        assertTrue(resultado.contains("7"), "no ha sustituido el número: $resultado")
        assertTrue(!resultado.contains("%"), "ha quedado un marcador sin sustituir: $resultado")
    }

    @Test
    fun `los textos con dos parametros los sustituyen en orden`() {
        val t = textosDe(Idioma.CASTELLANO)
        val resultado = t.con(Clave.SOLITARIO_PROGRESO, 3, 10)
        assertTrue(resultado.contains("3") && resultado.contains("10"), resultado)
    }

    @Test
    fun `un texto con parametros al que no se le pasan no revienta`() {
        // Preferimos la plantilla en crudo a una excepción en mitad de una
        // partida: es feo, es evidente y no tira la app.
        val t = textosDe(Idioma.CASTELLANO)
        val resultado = t.con(Clave.TABLERO_CASILLA)
        assertTrue(resultado.isNotBlank())
    }

    @Test
    fun `los plurales dan la forma correcta en castellano`() {
        val t = textosDe(Idioma.CASTELLANO)
        assertEquals("1 casilla", t.plural(ClavePlural.CASILLAS, 1))
        assertEquals("20 casillas", t.plural(ClavePlural.CASILLAS, 20))
        assertEquals("1 vez", t.plural(ClavePlural.REPETICIONES, 1))
        assertEquals("3 veces", t.plural(ClavePlural.REPETICIONES, 3))
    }

    @Test
    fun `los plurales dan la forma correcta en ingles`() {
        val t = textosDe(Idioma.INGLES)
        assertEquals("1 square", t.plural(ClavePlural.CASILLAS, 1))
        assertEquals("12 squares", t.plural(ClavePlural.CASILLAS, 12))
    }

    @Test
    fun `los trece idiomas tienen los seis plurales`() {
        todosLosCatalogos
            .filter { it.idioma in IDIOMAS_TERMINADOS }
            .forEach { catalogo ->
                ClavePlural.entries.forEach { clave ->
                    val formas = catalogo.plurales[clave]
                    assertTrue(
                        formas != null && formas.isNotEmpty(),
                        "${catalogo.idioma} no tiene el plural de $clave",
                    )
                    assertTrue(
                        formas!!.containsKey(CategoriaPlural.OTHER),
                        "${catalogo.idioma}/$clave no tiene la forma OTHER, que es el respaldo",
                    )
                }
            }
    }

    @Test
    fun `un plural sin catalogo devuelve al menos el numero`() {
        val vacio = Catalogo(Idioma.JAPONES, emptyMap(), emptyMap())
        val t = Textos(vacio, vacio)
        assertEquals("5", t.plural(ClavePlural.PUNTOS, 5))
    }

    // --------------------------------------------------------- los juegos

    @Test
    fun `los dieciocho juegos tienen nombre, lema e instrucciones en todos los idiomas`() {
        // Las instrucciones se comprueban por forma y no por longitud en
        // caracteres. La primera versión de este test exigía más de treinta
        // caracteres y suspendía en chino: «出现一个事件和四个可能的年份。要决定是哪一年发生的。»
        // dice exactamente lo mismo que la frase castellana en la mitad de
        // caracteres. Contar caracteres no mide cuánta información lleva un
        // texto, así que se comprueba que sea una frase de verdad: más larga
        // que el lema y terminada en un signo de final de frase.
        val finalesDeFrase = setOf('.', '。', '！', '!', '؟', '?', '·')
        IDIOMAS_TERMINADOS.forEach { idioma ->
            val t = textosDe(idioma)
            Juego.entries.forEach { juego ->
                val nombre = t.nombreDe(juego)
                val lema = t.lemaDe(juego)
                val instrucciones = t.instruccionesDe(juego)

                assertTrue(nombre.isNotBlank(), "$idioma: $juego sin nombre")
                assertTrue(lema.isNotBlank(), "$idioma: $juego sin lema")
                assertTrue(instrucciones.isNotBlank(), "$idioma: $juego sin instrucciones")
                assertTrue(
                    instrucciones.length > lema.length,
                    "$idioma: las instrucciones de $juego no son más largas que su lema",
                )
                assertTrue(
                    instrucciones.trim().last() in finalesDeFrase,
                    "$idioma: las instrucciones de $juego no acaban en punto: «$instrucciones»",
                )
            }
        }
    }

    @Test
    fun `los nombres de los dieciocho juegos no se repiten`() {
        IDIOMAS_TERMINADOS.forEach { idioma ->
            val t = textosDe(idioma)
            val nombres = Juego.entries.map { t.nombreDe(it) }
            assertEquals(
                nombres.size,
                nombres.toSet().size,
                "$idioma tiene nombres de juego repetidos: $nombres",
            )
        }
    }

    // ------------------------------------------------- la palabra prohibida

    @Test
    fun `la donacion no usa vocabulario de compra en ninguno de los trece idiomas`() {
        // Regla dura del punto 4.4.1: la donación no es una compra y no puede
        // sonar como tal, ni en la app ni en la ficha de tienda. Esto lo
        // comprueba la build para que no se cuele en una traducción.
        val prohibidas =
            listOf(
                "comprar",
                "compra",
                "pagar",
                "pago",
                "precio",
                "desbloquea",
                "premium",
                "suscripción",
                "suscripcion",
                "buy",
                "purchase",
                "pay ",
                "price",
                "unlock",
                "subscription",
            )
        val clavesDeDonacion =
            Clave.entries.filter {
                it.name.startsWith("CAFE_") || it.name.startsWith("AJUSTES_APOYAR")
            }
        assertTrue(clavesDeDonacion.isNotEmpty(), "no se han encontrado claves de donación")

        todosLosCatalogos
            .filter { it.idioma in IDIOMAS_TERMINADOS }
            .forEach { catalogo ->
                clavesDeDonacion.forEach { clave ->
                    val texto = catalogo.textos[clave]?.lowercase() ?: return@forEach
                    prohibidas.forEach { palabra ->
                        assertTrue(
                            !texto.contains(palabra),
                            "${catalogo.idioma}/$clave usa «$palabra»: $texto",
                        )
                    }
                }
            }
    }
}
