package es.ghatostudio.funny.ui.i18n

import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.dominio.textos.Clave.*

/**
 * Catálogo en castellano. Es el idioma de referencia: cuando haya que añadir
 * una clave se escribe aquí y en [catalogoIngles] primero, y de ahí sale la
 * traducción a los once restantes.
 */
internal val catalogoCastellano =
    Catalogo(
        idioma = Idioma.CASTELLANO,
        textos =
            mapOf<Clave, String>(
                // ------------------------------------------------------------ comunes
                ACCION_VOLVER to "Volver",
                ACCION_CERRAR to "Cerrar",
                ACCION_CANCELAR to "Cancelar",
                ACCION_ACEPTAR to "Aceptar",
                ACCION_CONTINUAR to "Continuar",
                ACCION_EMPEZAR to "¡Empezar!",
                ACCION_LISTO to "Listo",
                ACCION_BORRAR to "Borrar",
                ACCION_ANADIR to "Añadir",
                ACCION_REINTENTAR to "Reintentar",
                ACCION_COPIAR to "Copiar enlace",
                ACCION_COMPARTIR to "Compartir",
                ACCION_AHORA_NO to "Ahora no",
                ACCION_SI to "Sí",
                ACCION_NO to "No",
                ESTADO_CARGANDO to "Cargando…",
                ESTADO_SIN_CONTENIDO to "No hay contenido para este juego.",
                // --------------------------------------------------------------- menú
                APP_LEMA to "El juego de fiesta que cabe en vuestros móviles",
                MENU_JUGAR to "JUGAR",
                MENU_SEGUIR_PARTIDA to "SEGUIR LA PARTIDA",
                MENU_PARTIDA_NUEVA to "PARTIDA NUEVA",
                MENU_COMO_JUGAR to "CÓMO SE JUEGA",
                MENU_AJUSTES to "AJUSTES",
                MENU_SALON to "JUGAR CON VARIOS MÓVILES",
                MENU_TOUR to "VER EL TOUR",
                // -------------------------------------------------------------- modos
                MODO_TITULO to "¿Cómo jugamos?",
                MODO_SUBTITULO to "Se puede cambiar en cualquier partida nueva.",
                MODO_EQUIPOS to "Por equipos",
                MODO_EQUIPOS_DETALLE to
                    "De 2 a 6 equipos. Cada equipo tiene su ficha y va rotando quién actúa. " +
                    "Es la forma clásica y la más ruidosa.",
                MODO_INDIVIDUAL to "Individual",
                MODO_INDIVIDUAL_DETALLE to
                    "De 2 a 8 personas, cada una con su ficha y sin equipos. " +
                    "En las pruebas de actuar, quien tiene el turno actúa y adivina el resto de la mesa.",
                MODO_SOLITARIO to "Reto en solitario",
                MODO_SOLITARIO_DETALLE to
                    "Tú contra el reloj: diez pruebas seguidas y una marca personal que batir. " +
                    "Sin tablero y solo con los juegos que se pueden jugar sin nadie delante.",
                // ------------------------------------------------------- participantes
                PARTICIPANTES_TITULO_EQUIPOS to "Equipos",
                PARTICIPANTES_TITULO_INDIVIDUAL to "Jugadores",
                PARTICIPANTES_TITULO_SOLITARIO to "¿Cómo te llamas?",
                PARTICIPANTES_SUBTITULO_EQUIPOS to
                    "De %1\$d a %2\$d equipos. Apunta quién juega en cada uno y el móvil irá " +
                    "diciendo a quién le toca actuar.",
                PARTICIPANTES_SUBTITULO_INDIVIDUAL to
                    "De %1\$d a %2\$d personas, cada una con su ficha.",
                PARTICIPANTES_SUBTITULO_SOLITARIO to
                    "Solo para poner tu nombre en la marca. Nada de esto sale del móvil.",
                PARTICIPANTES_ANADIR_EQUIPO to "+  AÑADIR EQUIPO",
                PARTICIPANTES_ANADIR_JUGADOR to "+  AÑADIR JUGADOR",
                PARTICIPANTES_NUEVO_JUGADOR to "Añadir jugador…",
                PARTICIPANTES_SIN_JUGADORES to
                    "Sin nombres apuntados: el juego dirá solo el nombre del equipo.",
                PARTICIPANTES_NOMBRE_EQUIPO to "Nombre del equipo",
                PARTICIPANTES_NOMBRE_JUGADOR to "Nombre",
                PARTICIPANTES_QUITAR to "Quitar",
                PARTICIPANTES_EQUIPO_POR_DEFECTO to "Equipo %1\$d",
                PARTICIPANTES_JUGADOR_POR_DEFECTO to "Jugador %1\$d",
                PARTICIPANTES_EQUIPO_1_POR_DEFECTO to "Los Cracks",
                PARTICIPANTES_EQUIPO_2_POR_DEFECTO to "Las Fieras",
                PARTICIPANTES_DESDE_SALON to "Se ha unido desde otro móvil",
                // ------------------------------------------------------------ tablero
                TABLERO_TIRAR to "TIRAR EL DADO",
                TABLERO_TURNO_DE to "TURNO DE %1\$s",
                TABLERO_LE_TOCA to "Le toca a %1\$s",
                TABLERO_CASILLA to "Casilla %1\$d",
                TABLERO_SALIDA to "SALIDA",
                TABLERO_META to "META",
                TABLERO_MARCADOR to "Marcador",
                TABLERO_ABANDONAR to "Abandonar la partida",
                TABLERO_ABANDONAR_PREGUNTA to
                    "Se perderá el avance de esta partida. ¿Seguro que quieres salir?",
                TABLERO_ESPERANDO_HUB to "Esperando al móvil que lleva la partida…",
                TABLERO_AVANZA_CASILLAS to "Avanza %1\$d",
                // ----------------------------------------------------------- casillas
                CASILLA_COMODIN to "COMODÍN",
                CASILLA_COMODIN_DETALLE to "El rival elige qué prueba te toca. Sin piedad.",
                CASILLA_TODOS to "JUEGAN TODOS",
                CASILLA_TODOS_DETALLE to
                    "La misma prueba para toda la mesa. Cada uno que acierte avanza una casilla.",
                CASILLA_META_AVISO to "Prueba final: solo se gana superándola.",
                COMODIN_TITULO to "Casilla comodín",
                COMODIN_ELIGE to "%1\$s elige la prueba",
                // ------------------------------------------------------------ pruebas
                PRUEBA_FINAL to "🏁  PRUEBA FINAL",
                PRUEBA_JUEGAN_TODOS to "👥  JUEGAN TODOS",
                PRUEBA_LE_TOCA_ACTUAR_A to "LE TOCA ACTUAR A",
                PRUEBA_QUIEN_DECIDA to "quien decida %1\$s",
                PRUEBA_SOLO_MIRE_ESA_PERSONA to "👀  Que solo mire esta persona la pantalla",
                PRUEBA_MIRA_TU_MOVIL to "📱  Mira tu propio móvil: la palabra te ha llegado ahí",
                PRUEBA_SEGUNDOS to "⏱  %1\$d segundos",
                PRUEBA_CUANDO_TEMA to "¿En qué año?",
                PRUEBA_CUANDO_RESPUESTA to "Ocurrió en %1\$d.",
                PRUEBA_ACIERTOS_DE to "%1\$d de %2\$d",
                PRUEBA_SALTAR to "SALTAR",
                PRUEBA_ACERTADA to "✓  ACERTADA",
                PRUEBA_FALLADA to "✗  FALLADA",
                PRUEBA_PROHIBIDA to "🚫  PROHIBIDA",
                PRUEBA_TERMINAR to "Terminar la prueba",
                PRUEBA_ACIERTOS to "✔  %1\$d",
                PRUEBA_VEREDICTO_TITULO to "¿Lo ha conseguido?",
                PRUEBA_VEREDICTO_LOGRADO to "✓  ¡SÍ!",
                PRUEBA_VEREDICTO_NO_LOGRADO to "✗  NO",
                PRUEBA_VEREDICTO_DECIDE_MESA to "Lo decide el resto de la mesa, no la app.",
                PRUEBA_VEREDICTO_DECIDE_SOLO to "Aquí no hay nadie mirando: sé honesto contigo.",
                PRUEBA_BORRAR_DIBUJO to "Borrar todo",
                PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA to "SOLO LA MIRA QUIEN DIBUJA",
                PRUEBA_DIBUJO_TIEMPO_AL_PULSAR to "El tiempo empieza a contar al pulsar el botón.",
                PRUEBA_DIBUJO_EMPEZAR to "🎨   EMPEZAR A DIBUJAR",
                PRUEBA_DIBUJO_LIENZO_VACIO to "Dibuja aquí con el dedo",
                PRUEBA_DIBUJO_ESPIAR to "Mantener pulsado para volver a ver la palabra",
                PRUEBA_DIBUJO_GOMA to "Goma de borrar",
                PRUEBA_DESHACER to "Deshacer",
                PRUEBA_PINCEL to "Grosor",
                PRUEBA_COLOR to "Color",
                PRUEBA_RETO_OBJETIVO to "Hay que llegar a %1\$d",
                PRUEBA_RETO_LLEVAMOS to "Llevamos",
                PRUEBA_RETO_UNA_MAS to "+1  ¡OTRA!",
                PRUEBA_RETO_CONSEGUIDO to "¡CONSEGUIDO!",
                PRUEBA_RETO_TOCA_PARA_SUMAR to "de %1\$d  ·  toca para sumar",
                PRUEBA_RETO_RENDIRSE to "NOS RENDIMOS",
                PRUEBA_RETO_NOTA to "Cada respuesta vale una vez. Si se repite, no cuenta.",
                PRUEBA_ORDENA_AYUDA to "Toca en el orden correcto, del primero al último.",
                PRUEBA_ORDENA_COMPROBAR to "COMPROBAR",
                PRUEBA_ORDENA_CORRECTO to "El orden correcto era:",
                PRUEBA_VF_VERDADERO to "VERDADERO",
                PRUEBA_VF_FALSO to "FALSO",
                PRUEBA_VF_ERA_VERDAD to "Era verdad",
                PRUEBA_VF_ERA_MENTIRA to "Era mentira",
                PRUEBA_EMOJIS_AYUDA to "¿Qué es esto escrito con emojis?",
                PRUEBA_EMOJIS_ERA to "Era: %1\$s",
                PRUEBA_CANTA_PISTA to "Empieza por aquí",
                PRUEBA_TRABALENGUAS_AYUDA to "Dilo entero y sin trabarte.",
                PRUEBA_DESAFIO_AYUDA to "A ver cómo sales de esta.",
                RONDA_TODOS_PASA_MOVIL to "Pasa el móvil sin mirar la respuesta de nadie.",
                RONDA_TODOS_RESPONDE to "Responde %1\$s",
                RONDA_TODOS_RESUMEN to "Quién ha acertado",
                RONDA_TODOS_NADIE to "Nadie. No se mueve ninguna ficha.",
                RONDA_TODOS_EN_TU_MOVIL to "Responde en tu propio móvil.",
                RONDA_TODOS_ESPERANDO to "Esperando al resto…",
                RONDA_TODOS_PASAD_A to "PASAD EL MÓVIL A",
                RONDA_TODOS_PROGRESO to
                    "%1\$d de %2\$d. Nadie sabrá quién ha acertado hasta el final.",
                RONDA_TODOS_SIN_RESPUESTA to "Se acabó el tiempo, sin respuesta.",
                RONDA_TODOS_GUARDADA to "Respuesta guardada. No la contéis todavía.",
                RONDA_TODOS_CORRECTA_ERA to "LA RESPUESTA CORRECTA ERA",
                RONDA_TODOS_SIN_RESPONDER to "sin respuesta",
                RONDA_TODOS_VER_RESULTADOS to "VER RESULTADOS",
                // ---------------------------------------------------------- resultado
                RESULTADO_SUPERADA to "¡SUPERADA!",
                RESULTADO_NO_HA_PODIDO_SER to "NO HA PODIDO SER",
                RESULTADO_FINAL_SUPERADA to "¡PRUEBA FINAL SUPERADA!",
                RESULTADO_AVANZAS_A to "Avanzáis a la casilla %1\$d",
                RESULTADO_TE_QUEDAS_EN to "Os quedáis en la casilla %1\$d",
                RESULTADO_LLEGADA_A_META to "¡Habéis llegado a la meta!",
                RESULTADO_SIGUIENTE_TURNO to "SIGUIENTE TURNO",
                RESULTADO_VER_RESULTADO to "VER EL RESULTADO",
                // ----------------------------------------------------------- victoria
                VICTORIA_TITULO to "¡Fin de la partida!",
                VICTORIA_GANADOR to "Gana %1\$s",
                VICTORIA_CLASIFICACION to "Cómo ha quedado",
                VICTORIA_OTRA_PARTIDA to "OTRA PARTIDA",
                VICTORIA_AL_MENU to "VOLVER AL MENÚ",
                VICTORIA_SOLITARIO_TITULO to "Reto terminado",
                VICTORIA_SOLITARIO_PUNTOS to "%1\$d puntos",
                VICTORIA_SOLITARIO_MEJOR to "Tu mejor marca: %1\$d",
                VICTORIA_SOLITARIO_RECORD to "¡Marca personal nueva!",
                // ---------------------------------------------------------- solitario
                SOLITARIO_TITULO to "Reto en solitario",
                SOLITARIO_SUBTITULO to "Diez pruebas. Tú contra el reloj.",
                SOLITARIO_PROGRESO to "Prueba %1\$d de %2\$d",
                SOLITARIO_RONDAS to "Pruebas",
                SOLITARIO_EMPEZAR to "EMPEZAR EL RETO",
                SOLITARIO_MEJOR_MARCA to "Mejor marca: %1\$d",
                SOLITARIO_SIN_MARCA to "Todavía no tienes marca. Estrénala.",
                // ------------------------------------------------------------ ajustes
                AJUSTES_TITULO to "Ajustes",
                AJUSTES_SUBTITULO to "Se guardan para la próxima partida.",
                AJUSTES_APARIENCIA to "Apariencia",
                AJUSTES_TEMA to "Tema",
                AJUSTES_TEMA_DETALLE to "Seis temas: tres claros y tres oscuros.",
                AJUSTES_TEMA_SISTEMA to "Seguir el sistema",
                AJUSTES_IDIOMA to "Idioma",
                AJUSTES_IDIOMA_DETALLE to "Trece idiomas disponibles.",
                AJUSTES_PARTIDA to "La partida",
                AJUSTES_RITMO to "Ritmo de las pruebas",
                AJUSTES_RITMO_DETALLE to "Cuánto tiempo hay para cada prueba.",
                AJUSTES_DURACION to "Duración de la partida",
                AJUSTES_DURACION_DETALLE to "%1\$d casillas hasta la meta · %2\$s",
                AJUSTES_JUEGOS_ACTIVOS to "Juegos de la partida",
                AJUSTES_JUEGOS_ACTIVOS_DETALLE to
                    "Quita los que no os gusten y dejarán de salir en el tablero.",
                AJUSTES_JUEGOS_CONTADOR to "%1\$d de %2\$d activos",
                AJUSTES_JUEGOS_MINIMO to "Tiene que quedar al menos un juego activo.",
                AJUSTES_SONIDO to "Sonido",
                AJUSTES_SONIDO_DETALLE to "Pitidos de la cuenta atrás y avisos",
                AJUSTES_VIBRACION to "Vibración",
                AJUSTES_VIBRACION_DETALLE to "Aciertos, fallos y fin de tiempo",
                AJUSTES_ANIMACIONES to "Animaciones",
                AJUSTES_ANIMACIONES_DETALLE to "Apágalas si prefieres la interfaz quieta",
                AJUSTES_DATOS to "Tus datos",
                AJUSTES_EXPORTAR to "Exportar",
                AJUSTES_EXPORTAR_DETALLE to "Guarda equipos, ajustes y marcas en un fichero",
                AJUSTES_IMPORTAR to "Importar",
                AJUSTES_IMPORTAR_DETALLE to "Recupera una copia guardada antes",
                AJUSTES_MAS to "Más",
                AJUSTES_APOYAR to "Apoyar el desarrollo",
                AJUSTES_APOYAR_DETALLE to "Invítame a un café si te resulta útil",
                AJUSTES_COMPARTIR to "Compartir Funny",
                AJUSTES_COMPARTIR_DETALLE to "Pásalo a quien creas que va a disfrutarlo",
                AJUSTES_AYUDA to "Ayuda",
                AJUSTES_AYUDA_DETALLE to "Cómo se juega y preguntas frecuentes",
                AJUSTES_TOUR to "Tour guiado",
                AJUSTES_TOUR_DETALLE to "Los doce juegos y los tres modos, explicados",
                AJUSTES_ACERCA_DE to "Acerca de",
                AJUSTES_ACERCA_DE_DETALLE to "Versión, licencias y privacidad",
                // -------------------------------------------------------------- temas
                TEMA_MODO_CLARO to "Claros",
                TEMA_MODO_OSCURO to "Oscuros",
                TEMA_FIESTA to "Fiestón",
                TEMA_NEON to "Neón",
                TEMA_MEDIANOCHE to "Medianoche",
                TEMA_PAPEL to "Papel",
                TEMA_MENTA to "Menta",
                TEMA_ATARDECER to "Atardecer",
                // ------------------------------------------------------------ idiomas
                IDIOMA_TITULO to "Idioma",
                IDIOMA_SEGUIR_SISTEMA to "El del móvil",
                IDIOMA_SUBTITULO to "El cambio se aplica al instante.",
                // ----------------------------------------------------------- donación
                CAFE_TITULO to "¿Un café?",
                CAFE_TEXTO to
                    "Esta app es gratuita, sin anuncios y no recoge tus datos. " +
                    "Si te resulta útil, puedes invitarme a un café.",
                CAFE_BOTON to "Invítame a un café · 1 €",
                CAFE_NO_VOLVER to "No volver a mostrar",
                CAFE_OTRO_DISPOSITIVO to "Desde otro dispositivo",
                CAFE_QR_DESCRIPCION to
                    "Código QR con el enlace para invitar a un café al autor de la app",
                CAFE_ILUSTRACION_DESCRIPCION to "Dibujo de una taza de café con vapor",
                CAFE_ENLACE_COPIADO to "Enlace copiado",
                CAFE_GRACIAS to "Gracias por pasarte por ahí 🙂",
                CAFE_SIN_DESBLOQUEOS to
                    "No cambia nada dentro del juego: Funny está entera y siempre lo estará.",
                CAFE_ENTRADA_AJUSTES to "Apoyar el desarrollo",
                CAFE_NO_DISPONIBLE to "En este dispositivo no está disponible.",
                // ------------------------------------------------- copia de seguridad
                COPIA_TITULO to "Copia de tus datos",
                COPIA_EXPORTAR_HECHO to "Copia guardada.",
                COPIA_EXPORTAR_ERROR to "No se ha podido guardar la copia.",
                COPIA_IMPORTAR_TITULO to "Importar una copia",
                COPIA_IMPORTAR_AVISO to
                    "Antes de tocar nada se guarda una copia de lo que tienes ahora, " +
                    "así que siempre se puede volver atrás.",
                COPIA_IMPORTAR_FUSIONAR to "Añadir a lo que ya tengo",
                COPIA_IMPORTAR_REEMPLAZAR to "Reemplazarlo todo",
                COPIA_IMPORTAR_HECHO to "Datos importados.",
                COPIA_IMPORTAR_ERROR_FORMATO to
                    "Ese fichero no parece una copia de Funny. No se ha cambiado nada.",
                COPIA_IMPORTAR_ERROR_VERSION to
                    "Esa copia es de una versión más nueva de Funny. Actualiza la app e inténtalo otra vez.",
                COPIA_IMPORTAR_RESPALDO to "Se ha guardado antes una copia de seguridad.",
                COPIA_CABECERA_DETALLE to "Copia del %1\$s · Funny %2\$s",
                // -------------------------------------------------------------- ayuda
                AYUDA_TITULO to "Ayuda",
                AYUDA_SUBTITULO to "Todo lo que hace falta para no perderse.",
                AYUDA_QUE_ES_TITULO to "¿Qué es Funny?",
                AYUDA_QUE_ES_TEXTO to
                    "Un juego de fiesta con doce pruebas distintas. Se juega con un móvil que va " +
                    "pasando de mano en mano o, si sois varios con móvil, conectándolos entre ellos. " +
                    "No hace falta internet en ningún momento.",
                AYUDA_COMO_SE_JUEGA_TITULO to "La partida",
                AYUDA_PARTIDA_1 to "Elegid el modo: por equipos, individual o el reto en solitario.",
                AYUDA_PARTIDA_2 to
                    "Cada equipo o jugador tiene una ficha y todos salen de la SALIDA.",
                AYUDA_PARTIDA_3 to "En su turno se tira el dado y se avanza de 1 a 3 casillas.",
                AYUDA_PARTIDA_4 to
                    "La casilla en la que caes decide la prueba. Si la superas, te quedas; si no, vuelves.",
                AYUDA_PARTIDA_5 to "Gana quien llegue a la META y supere la prueba final.",
                AYUDA_UN_MOVIL_TITULO to "Con un solo móvil",
                AYUDA_UN_MOVIL_1 to "El móvil va pasando: la app siempre dice a quién le toca.",
                AYUDA_UN_MOVIL_2 to
                    "En mímica, tabú, dibujo, canta y desafío solo mira quien actúa.",
                AYUDA_UN_MOVIL_3 to "En las de responder, la pantalla se puede enseñar a todos.",
                AYUDA_VARIOS_MOVILES_TITULO to "Con varios móviles",
                AYUDA_VARIOS_MOVILES_1 to
                    "Un móvil hace de mesa (el hub) y los demás se conectan a él. No hace falta wifi ni datos.",
                AYUDA_VARIOS_MOVILES_2 to
                    "La palabra secreta llega solo al móvil de quien actúa, así que nadie la ve por error.",
                AYUDA_VARIOS_MOVILES_3 to
                    "En las casillas de «juegan todos», cada uno responde en su móvil a la vez.",
                AYUDA_FAQ_TITULO to "Preguntas frecuentes",
                AYUDA_FAQ_1_P to "¿Hace falta internet?",
                AYUDA_FAQ_1_R to
                    "No. Funny funciona entera sin conexión, y conectar varios móviles usa Bluetooth y " +
                    "wifi directo entre ellos, sin pasar por ninguna red.",
                AYUDA_FAQ_2_P to "¿Se puede jugar una sola persona?",
                AYUDA_FAQ_2_R to
                    "Sí: el reto en solitario son diez pruebas seguidas con marca personal. " +
                    "Solo entran los juegos que no necesitan público.",
                AYUDA_FAQ_3_P to "¿Cuesta algo? ¿Hay algo bloqueado?",
                AYUDA_FAQ_3_R to
                    "No hay nada bloqueado ni nada que conseguir aparte. Si te gusta, puedes invitarme " +
                    "a un café desde Ajustes, y eso no cambia absolutamente nada dentro del juego.",
                AYUDA_FAQ_4_P to "¿Recoge datos?",
                AYUDA_FAQ_4_R to
                    "No. No hay analítica, ni cuentas, ni publicidad. Los equipos y los ajustes se " +
                    "guardan solo en tu móvil y salen de ahí únicamente si tú exportas una copia.",
                AYUDA_FAQ_5_P to "¿Puedo cambiar los juegos que salen?",
                AYUDA_FAQ_5_R to
                    "Sí, en Ajustes → Juegos de la partida. Los que quites dejan de aparecer en el tablero.",
                AYUDA_PROBLEMAS_TITULO to "Si algo falla",
                AYUDA_PROBLEMAS_TEXTO to
                    "Cierra y vuelve a abrir la app: la partida en curso se conserva. Si el problema " +
                    "sigue, exporta tus datos antes de reinstalar y escríbenos contando qué pasaba.",
                AYUDA_ESCRIBENOS to "Escribir al autor",
                // ---------------------------------------------------------- acerca de
                ACERCA_TITULO to "Acerca de",
                ACERCA_VERSION to "Versión",
                ACERCA_COMPILACION to "Compilación",
                ACERCA_FECHA to "Fecha",
                ACERCA_COMMIT to "Commit",
                ACERCA_LICENCIA to "Licencia",
                ACERCA_LICENCIAS_TERCEROS to "Licencias de terceros",
                ACERCA_PRIVACIDAD to "Política de privacidad",
                ACERCA_CONTACTO to "Contacto",
                ACERCA_CODIGO to "Código fuente",
                ACERCA_SIN_ANUNCIOS to "Sin anuncios, sin analítica y sin cuentas.",
                ACERCA_AUTOR to "Ghato Studio · Brais Galdo",
                // --------------------------------------------------------------- tour
                TOUR_TITULO to "Tour guiado",
                TOUR_SUBTITULO to "Los doce juegos y los tres modos, en dos minutos.",
                TOUR_EMPEZAR to "EMPEZAR EL TOUR",
                TOUR_SALTAR to "Saltar",
                TOUR_ANTERIOR to "Anterior",
                TOUR_SIGUIENTE to "Siguiente",
                TOUR_TERMINAR to "¡A JUGAR!",
                TOUR_PROGRESO to "%1\$d de %2\$d",
                TOUR_BIENVENIDA_TITULO to "Bienvenido a Funny",
                TOUR_BIENVENIDA_TEXTO to
                    "Doce juegos, tres formas de jugar y cero necesidad de internet. " +
                    "En un par de minutos te lo cuento todo; puedes saltártelo cuando quieras.",
                TOUR_MODOS_TITULO to "Tres formas de jugar",
                TOUR_MODOS_TEXTO to
                    "Por equipos es lo clásico: de 2 a 6 equipos y va rotando quién actúa. " +
                    "Individual es lo mismo pero cada persona lleva su ficha, de 2 a 8. " +
                    "Y el reto en solitario son diez pruebas contra el reloj, tú solo, con marca personal.",
                TOUR_TABLERO_TITULO to "El tablero",
                TOUR_TABLERO_TEXTO to
                    "Cada ficha empieza en la SALIDA. En tu turno tiras el dado, avanzas de 1 a 3 " +
                    "casillas y la casilla en la que caes decide la prueba. Si la superas te quedas " +
                    "ahí; si fallas vuelves a donde estabas. Gana quien llegue a la META y supere " +
                    "la prueba final.",
                TOUR_CASILLAS_TITULO to "Las casillas especiales",
                TOUR_CASILLAS_TEXTO to
                    "🃏 Comodín: el rival elige la prueba que te toca.\n" +
                    "👥 Juegan todos: la misma prueba para toda la mesa, y cada uno que acierta " +
                    "avanza una casilla.\n" +
                    "🏁 Meta: prueba final al azar. Sin superarla no se gana.",
                TOUR_JUEGOS_TITULO to "Los doce juegos",
                TOUR_JUEGOS_TEXTO to
                    "Estos son todos. Puedes desactivar los que no os gusten en Ajustes.",
                TOUR_SALON_TITULO to "Varios móviles a la vez",
                TOUR_SALON_TEXTO to
                    "Un móvil hace de mesa y los demás se conectan a él por Bluetooth o wifi directo, " +
                    "sin internet. Sirve para lo que de verdad importa: la palabra secreta llega solo " +
                    "al móvil de quien actúa, y en las casillas de «juegan todos» cada uno responde en " +
                    "el suyo al mismo tiempo.",
                TOUR_AJUSTES_TITULO to "Ajústalo a tu gusto",
                TOUR_AJUSTES_TEXTO to
                    "Seis temas, trece idiomas, tres ritmos y tres duraciones. También puedes " +
                    "desactivar juegos, apagar el sonido y la vibración, y guardar o recuperar tus " +
                    "datos en un fichero.",
                TOUR_FINAL_TITULO to "Ya está",
                TOUR_FINAL_TEXTO to
                    "Puedes volver a ver esto cuando quieras desde Ajustes → Tour guiado. " +
                    "Que os lo paséis bien.",
                // -------------------------------------------------------------- salón
                SALON_TITULO to "Varios móviles",
                SALON_SUBTITULO to "Sin internet: se conectan entre ellos.",
                SALON_CREAR to "HACER DE MESA",
                SALON_CREAR_DETALLE to
                    "Este móvil lleva la partida y enseña el tablero. Es el que se deja en la mesa.",
                SALON_UNIRSE to "UNIRME A UNA MESA",
                SALON_UNIRSE_DETALLE to
                    "Este móvil se queda en tu mano y recibe tus pruebas en privado.",
                SALON_TU_NOMBRE to "Tu nombre",
                SALON_HUB_TITULO to "Eres la mesa",
                SALON_HUB_ESPERANDO to "Esperando a que se conecten…",
                SALON_HUB_CONECTADOS to "Conectados",
                SALON_HUB_EMPEZAR to "EMPEZAR LA PARTIDA",
                SALON_CLIENTE_TITULO to "Buscando mesa",
                SALON_CLIENTE_BUSCANDO to "Buscando mesas cerca…",
                SALON_CLIENTE_SIN_SALONES to
                    "Todavía no se ve ninguna. Que el otro móvil abra «Hacer de mesa» y esperad unos segundos.",
                SALON_CLIENTE_CONECTANDO to "Conectando…",
                SALON_CLIENTE_CONECTADO to "Conectado",
                SALON_CLIENTE_ESPERA to "Ya estás dentro. Mira la mesa: la partida empieza allí.",
                SALON_SALIR to "Salir del salón",
                SALON_DESCONECTADO to "Se ha perdido la conexión con la mesa.",
                SALON_ERROR_PERMISOS to "Faltan permisos para buscar los móviles de al lado.",
                SALON_PEDIR_PERMISOS to "DAR PERMISOS",
                SALON_PERMISOS_EXPLICACION to
                    "Para encontrar los móviles que tienes al lado, Android pide permiso de " +
                    "dispositivos cercanos y, en versiones antiguas, también de ubicación. " +
                    "Funny no consulta dónde estás ni lo guarda en ningún sitio: es el precio que " +
                    "pone el sistema por usar Bluetooth y wifi directo.",
                SALON_ERROR_BLUETOOTH to "Enciende el Bluetooth para poder conectar los móviles.",
                SALON_ERROR_UBICACION to
                    "Enciende la ubicación: Android la exige para buscar por Bluetooth.",
                SALON_ERROR_SERVICIOS to
                    "Este móvil no tiene los servicios de Google que hacen falta para conectar. " +
                    "Podéis seguir jugando pasándoos un solo móvil.",
                SALON_COMO_FUNCIONA to "¿Cómo funciona?",
                SALON_ESTE_DISPOSITIVO to "Este móvil",
                SALON_ROL_HUB to "Mesa",
                SALON_ROL_MANDO to "Mando",
                SALON_SIN_RED to "No se usa internet en ningún momento.",
                SALON_SIN_NOMBRE to "Sin nombre",
                SALON_TU_TURNO to "¡Te toca!",
                SALON_MIRA_EL_HUB to "Mira el móvil de la mesa.",
                // --------------------------------------------------- los doce juegos
                JUEGO_MIMICA_NOMBRE to "Mímica",
                JUEGO_MIMICA_LEMA to "Represéntalo sin hablar",
                JUEGO_MIMICA_INSTRUCCIONES to
                    "Una persona representa la palabra con gestos. Prohibido hablar, hacer ruidos " +
                    "o señalar objetos de la sala.",
                JUEGO_DIBUJO_NOMBRE to "Pinturillo",
                JUEGO_DIBUJO_LEMA to "Dibújalo en la pantalla",
                JUEGO_DIBUJO_INSTRUCCIONES to
                    "Una persona dibuja en la pantalla del móvil y los demás adivinan. " +
                    "Nada de letras, números ni gestos.",
                JUEGO_CUANDO_NOMBRE to "¿Cuándo?",
                JUEGO_CUANDO_LEMA to "¿En qué año ocurrió?",
                JUEGO_CUANDO_INSTRUCCIONES to
                    "Aparece un acontecimiento y cuatro años posibles. Hay que decidir en cuál ocurrió.",
                JUEGO_PREGUNTAS_NOMBRE to "Preguntas",
                JUEGO_PREGUNTAS_LEMA to "Cultura general",
                JUEGO_PREGUNTAS_INSTRUCCIONES to
                    "Una pregunta con cuatro respuestas. Se elige una sola y no vale cambiar.",
                JUEGO_TABU_NOMBRE to "Tabú",
                JUEGO_TABU_LEMA to "Descríbelo sin decirlo",
                JUEGO_TABU_INSTRUCCIONES to
                    "Hay que describir la palabra sin usar ninguna de las prohibidas ni palabras " +
                    "de la misma familia.",
                JUEGO_RETO_NOMBRE to "Reto rápido",
                JUEGO_RETO_LEMA to "Enumera contrarreloj",
                JUEGO_RETO_INSTRUCCIONES to
                    "Ir diciendo cosas de la categoría indicada hasta llegar al objetivo antes de " +
                    "que se acabe el tiempo.",
                JUEGO_EMOJIS_NOMBRE to "Emojis",
                JUEGO_EMOJIS_LEMA to "Descífralo",
                JUEGO_EMOJIS_INSTRUCCIONES to
                    "Una película, una canción o un refrán escrito solo con emojis, y cuatro " +
                    "respuestas posibles.",
                JUEGO_VERDADERO_FALSO_NOMBRE to "¿Te lo crees?",
                JUEGO_VERDADERO_FALSO_LEMA to "Verdadero o falso",
                JUEGO_VERDADERO_FALSO_INSTRUCCIONES to
                    "Cuatro afirmaciones raras seguidas. De cada una hay que decir si es verdad " +
                    "o mentira, y luego se explica por qué.",
                JUEGO_TRABALENGUAS_NOMBRE to "Trabalenguas",
                JUEGO_TRABALENGUAS_LEMA to "Dilo sin trabarte",
                JUEGO_TRABALENGUAS_INSTRUCCIONES to
                    "Hay que decir el trabalenguas las veces que pida, entero y sin equivocarse. " +
                    "Lo juzga la mesa.",
                JUEGO_ORDENA_NOMBRE to "Ordena",
                JUEGO_ORDENA_LEMA to "Ponlo en su sitio",
                JUEGO_ORDENA_INSTRUCCIONES to
                    "Cuatro cosas desordenadas y un criterio. Hay que tocarlas en el orden correcto.",
                JUEGO_CANTA_NOMBRE to "Canta",
                JUEGO_CANTA_LEMA to "Sigue la canción",
                JUEGO_CANTA_INSTRUCCIONES to
                    "Sale el título y quién la canta, y hay que ponerse a cantar el estribillo. " +
                    "Lo juzga la mesa, con la generosidad que considere.",
                JUEGO_DESAFIO_NOMBRE to "Desafío",
                JUEGO_DESAFIO_LEMA to "Atrévete",
                JUEGO_DESAFIO_INSTRUCCIONES to
                    "Un pequeño reto delante de todos. Nada peligroso, nada humillante: solo " +
                    "ridículo del bueno. Lo juzga la mesa.",
                // ------------------------------------------------------ ritmo y duración
                RITMO_RAPIDO to "Rápido",
                RITMO_NORMAL to "Normal",
                RITMO_TRANQUILO to "Tranquilo",
                DURACION_CORTA to "Corta",
                DURACION_NORMAL to "Normal",
                DURACION_LARGA to "Larga",
                DURACION_CORTA_DETALLE to "unos 15 min",
                DURACION_NORMAL_DETALLE to "unos 30 min",
                DURACION_LARGA_DETALLE to "unos 45 min",
                // ----------------------------------------------------- accesibilidad
                A11Y_DADO to "Dado: %1\$d",
                A11Y_FICHA to "Ficha de %1\$s en la casilla %2\$d",
                A11Y_CASILLA to "Casilla %1\$d, %2\$s",
                A11Y_VOLVER to "Volver a la pantalla anterior",
                A11Y_CERRAR to "Cerrar",
                A11Y_LIENZO_DIBUJO to "Lienzo para dibujar con el dedo",
                A11Y_TEMA_MUESTRA to "Muestra de colores del tema %1\$s",
                A11Y_BANDERA_IDIOMA to "Idioma %1\$s",
            ),
        plurales =
            mapOf(
                ClavePlural.CASILLAS to
                    mapOf(
                        CategoriaPlural.ONE to "%d casilla",
                        CategoriaPlural.OTHER to "%d casillas",
                    ),
                ClavePlural.SEGUNDOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d segundo",
                        CategoriaPlural.OTHER to "%d segundos",
                    ),
                ClavePlural.DISPOSITIVOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d móvil conectado",
                        CategoriaPlural.OTHER to "%d móviles conectados",
                    ),
                ClavePlural.PUNTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d punto",
                        CategoriaPlural.OTHER to "%d puntos",
                    ),
                ClavePlural.ACIERTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d acierto",
                        CategoriaPlural.OTHER to "%d aciertos",
                    ),
                ClavePlural.REPETICIONES to
                    mapOf(
                        CategoriaPlural.ONE to "%d vez",
                        CategoriaPlural.OTHER to "%d veces",
                    ),
            ),
    )
