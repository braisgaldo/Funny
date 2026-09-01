package es.ghatostudio.funny.dominio.textos

/**
 * Todas las claves de texto de Funny.
 *
 * Es un enum y no un mapa de cadenas a propósito: así el compilador avisa de
 * una clave inventada y `PruebaCatalogos` puede comprobar que los trece
 * idiomas cubren exactamente esta lista. Nada de lo que ve el usuario se
 * escribe en las pantallas: todo pasa por aquí.
 *
 * Convenio de nombres: `AMBITO_CONCEPTO`, y los textos con parámetros llevan
 * marcadores de `String.format` (`%s`, `%d`) documentados en el comentario.
 */
enum class Clave {
    // ---------------------------------------------------------------- comunes
    ACCION_VOLVER,
    ACCION_CERRAR,
    ACCION_CANCELAR,
    ACCION_ACEPTAR,
    ACCION_CONTINUAR,
    ACCION_EMPEZAR,
    ACCION_LISTO,
    ACCION_BORRAR,
    ACCION_ANADIR,
    ACCION_REINTENTAR,
    ACCION_COPIAR,
    ACCION_COMPARTIR,
    ACCION_AHORA_NO,
    ACCION_SI,
    ACCION_NO,
    ESTADO_CARGANDO,
    ESTADO_SIN_CONTENIDO,

    // ------------------------------------------------------------------ menú
    APP_LEMA,
    MENU_JUGAR,
    MENU_SEGUIR_PARTIDA,
    MENU_PARTIDA_NUEVA,
    MENU_COMO_JUGAR,
    MENU_AJUSTES,
    MENU_SALON,
    MENU_TOUR,

    // ----------------------------------------------------------------- modos
    MODO_TITULO,
    MODO_SUBTITULO,
    MODO_EQUIPOS,
    MODO_EQUIPOS_DETALLE,
    MODO_INDIVIDUAL,
    MODO_INDIVIDUAL_DETALLE,
    MODO_SOLITARIO,
    MODO_SOLITARIO_DETALLE,

    // ---------------------------------------------------------- participantes
    PARTICIPANTES_TITULO_EQUIPOS,
    PARTICIPANTES_TITULO_INDIVIDUAL,
    PARTICIPANTES_TITULO_SOLITARIO,

    /** %1$d = mínimo, %2$d = máximo. */
    PARTICIPANTES_SUBTITULO_EQUIPOS,
    PARTICIPANTES_SUBTITULO_INDIVIDUAL,
    PARTICIPANTES_SUBTITULO_SOLITARIO,
    PARTICIPANTES_ANADIR_EQUIPO,
    PARTICIPANTES_ANADIR_JUGADOR,
    PARTICIPANTES_NUEVO_JUGADOR,
    PARTICIPANTES_SIN_JUGADORES,
    PARTICIPANTES_NOMBRE_EQUIPO,
    PARTICIPANTES_NOMBRE_JUGADOR,
    PARTICIPANTES_QUITAR,

    /** %1$d = número de equipo o jugador. */
    PARTICIPANTES_EQUIPO_POR_DEFECTO,
    PARTICIPANTES_JUGADOR_POR_DEFECTO,
    PARTICIPANTES_EQUIPO_1_POR_DEFECTO,
    PARTICIPANTES_EQUIPO_2_POR_DEFECTO,
    PARTICIPANTES_DESDE_SALON,

    // --------------------------------------------------------------- tablero
    TABLERO_TIRAR,
    TABLERO_TURNO_DE,

    /** %1$s = nombre del participante. */
    TABLERO_LE_TOCA,

    /** %1$d = casilla. */
    TABLERO_CASILLA,
    TABLERO_SALIDA,
    TABLERO_META,
    TABLERO_MARCADOR,
    TABLERO_ABANDONAR,
    TABLERO_ABANDONAR_PREGUNTA,
    TABLERO_ESPERANDO_HUB,

    /** %1$d = número de casillas que avanza. */
    TABLERO_AVANZA_CASILLAS,

    // -------------------------------------------------------------- casillas
    CASILLA_COMODIN,
    CASILLA_COMODIN_DETALLE,
    CASILLA_TODOS,
    CASILLA_TODOS_DETALLE,
    CASILLA_META_AVISO,
    COMODIN_TITULO,

    /** %1$s = equipo o jugador que elige. */
    COMODIN_ELIGE,

    // ---------------------------------------------------------------- pruebas
    PRUEBA_FINAL,
    PRUEBA_JUEGAN_TODOS,
    PRUEBA_LE_TOCA_ACTUAR_A,
    PRUEBA_QUIEN_DECIDA,
    PRUEBA_SOLO_MIRE_ESA_PERSONA,
    PRUEBA_MIRA_TU_MOVIL,

    /** %1$d = segundos. */
    PRUEBA_SEGUNDOS,

    /** Tema por defecto de «¿Cuándo?» cuando la carta no trae uno. */
    PRUEBA_CUANDO_TEMA,

    /** %1$d = el año correcto. */
    PRUEBA_CUANDO_RESPUESTA,

    /** %1$d = aciertos, %2$d = total. */
    PRUEBA_ACIERTOS_DE,
    PRUEBA_SALTAR,
    PRUEBA_ACERTADA,
    PRUEBA_FALLADA,
    PRUEBA_PROHIBIDA,
    PRUEBA_TERMINAR,
    PRUEBA_ACIERTOS,
    PRUEBA_VEREDICTO_TITULO,
    PRUEBA_VEREDICTO_LOGRADO,
    PRUEBA_VEREDICTO_NO_LOGRADO,
    PRUEBA_VEREDICTO_DECIDE_MESA,
    PRUEBA_VEREDICTO_DECIDE_SOLO,
    PRUEBA_BORRAR_DIBUJO,
    PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA,
    PRUEBA_DIBUJO_TIEMPO_AL_PULSAR,
    PRUEBA_DIBUJO_EMPEZAR,
    PRUEBA_DIBUJO_LIENZO_VACIO,
    PRUEBA_DIBUJO_ESPIAR,
    PRUEBA_DIBUJO_GOMA,
    PRUEBA_DESHACER,
    PRUEBA_PINCEL,
    PRUEBA_COLOR,

    /** %1$d = objetivo del reto. */
    PRUEBA_RETO_OBJETIVO,
    PRUEBA_RETO_LLEVAMOS,
    PRUEBA_RETO_UNA_MAS,
    PRUEBA_RETO_CONSEGUIDO,

    /** %1$d = objetivo. Texto pequeño dentro del botón contador. */
    PRUEBA_RETO_TOCA_PARA_SUMAR,
    PRUEBA_RETO_RENDIRSE,
    PRUEBA_RETO_NOTA,
    PRUEBA_ORDENA_AYUDA,
    PRUEBA_ORDENA_COMPROBAR,

    /** El orden bueno, cuando se falla. */
    PRUEBA_ORDENA_CORRECTO,
    PRUEBA_VF_VERDADERO,
    PRUEBA_VF_FALSO,
    PRUEBA_VF_ERA_VERDAD,
    PRUEBA_VF_ERA_MENTIRA,
    PRUEBA_EMOJIS_AYUDA,

    /** %1$s = la respuesta correcta. */
    PRUEBA_EMOJIS_ERA,
    PRUEBA_CANTA_PISTA,
    PRUEBA_TRABALENGUAS_AYUDA,
    PRUEBA_DESAFIO_AYUDA,
    RONDA_TODOS_PASA_MOVIL,

    /** %1$s = nombre del participante. */
    RONDA_TODOS_RESPONDE,
    RONDA_TODOS_RESUMEN,
    RONDA_TODOS_NADIE,
    RONDA_TODOS_EN_TU_MOVIL,
    RONDA_TODOS_ESPERANDO,
    RONDA_TODOS_PASAD_A,

    /** %1$d = participante actual, %2$d = total. */
    RONDA_TODOS_PROGRESO,
    RONDA_TODOS_SIN_RESPUESTA,
    RONDA_TODOS_GUARDADA,
    RONDA_TODOS_CORRECTA_ERA,
    RONDA_TODOS_SIN_RESPONDER,
    RONDA_TODOS_VER_RESULTADOS,

    // -------------------------------------------------------------- resultado
    RESULTADO_SUPERADA,
    RESULTADO_NO_HA_PODIDO_SER,
    RESULTADO_FINAL_SUPERADA,

    /** %1$d = casilla de destino. */
    RESULTADO_AVANZAS_A,

    /** %1$d = casilla en la que se queda. */
    RESULTADO_TE_QUEDAS_EN,
    RESULTADO_LLEGADA_A_META,
    RESULTADO_SIGUIENTE_TURNO,
    RESULTADO_VER_RESULTADO,

    // --------------------------------------------------------------- victoria
    VICTORIA_TITULO,

    /** %1$s = nombre del ganador. */
    VICTORIA_GANADOR,
    VICTORIA_CLASIFICACION,
    VICTORIA_OTRA_PARTIDA,
    VICTORIA_AL_MENU,
    VICTORIA_SOLITARIO_TITULO,

    /** %1$d = puntos conseguidos. */
    VICTORIA_SOLITARIO_PUNTOS,

    /** %1$d = mejor marca anterior. */
    VICTORIA_SOLITARIO_MEJOR,
    VICTORIA_SOLITARIO_RECORD,

    // -------------------------------------------------------------- solitario
    SOLITARIO_TITULO,
    SOLITARIO_SUBTITULO,

    /** %1$d = prueba actual, %2$d = total de pruebas. */
    SOLITARIO_PROGRESO,
    SOLITARIO_RONDAS,
    SOLITARIO_EMPEZAR,
    SOLITARIO_MEJOR_MARCA,
    SOLITARIO_SIN_MARCA,

    // ---------------------------------------------------------------- ajustes
    AJUSTES_TITULO,
    AJUSTES_SUBTITULO,
    AJUSTES_APARIENCIA,
    AJUSTES_TEMA,
    AJUSTES_TEMA_DETALLE,
    AJUSTES_TEMA_SISTEMA,
    AJUSTES_IDIOMA,
    AJUSTES_IDIOMA_DETALLE,
    AJUSTES_PARTIDA,
    AJUSTES_RITMO,
    AJUSTES_RITMO_DETALLE,
    AJUSTES_DURACION,

    /** %1$d = casillas, %2$s = duración aproximada. */
    AJUSTES_DURACION_DETALLE,
    AJUSTES_JUEGOS_ACTIVOS,
    AJUSTES_JUEGOS_ACTIVOS_DETALLE,

    /** %1$d = juegos activos, %2$d = total. */
    AJUSTES_JUEGOS_CONTADOR,
    AJUSTES_JUEGOS_MINIMO,
    AJUSTES_SONIDO,
    AJUSTES_SONIDO_DETALLE,
    AJUSTES_VIBRACION,
    AJUSTES_VIBRACION_DETALLE,
    AJUSTES_ANIMACIONES,
    AJUSTES_ANIMACIONES_DETALLE,
    AJUSTES_DATOS,
    AJUSTES_EXPORTAR,
    AJUSTES_EXPORTAR_DETALLE,
    AJUSTES_IMPORTAR,
    AJUSTES_IMPORTAR_DETALLE,
    AJUSTES_MAS,
    AJUSTES_APOYAR,
    AJUSTES_APOYAR_DETALLE,
    AJUSTES_COMPARTIR,
    AJUSTES_COMPARTIR_DETALLE,
    AJUSTES_AYUDA,
    AJUSTES_AYUDA_DETALLE,
    AJUSTES_TOUR,
    AJUSTES_TOUR_DETALLE,
    AJUSTES_ACERCA_DE,
    AJUSTES_ACERCA_DE_DETALLE,

    // ------------------------------------------------------------------ temas
    TEMA_MODO_CLARO,
    TEMA_MODO_OSCURO,
    TEMA_FIESTA,
    TEMA_NEON,
    TEMA_MEDIANOCHE,
    TEMA_PAPEL,
    TEMA_MENTA,
    TEMA_ATARDECER,

    // ---------------------------------------------------------------- idiomas
    IDIOMA_TITULO,
    IDIOMA_SEGUIR_SISTEMA,
    IDIOMA_SUBTITULO,

    // -------------------------------------------------------------- donación
    CAFE_TITULO,
    CAFE_TEXTO,
    CAFE_BOTON,
    CAFE_NO_VOLVER,
    CAFE_OTRO_DISPOSITIVO,
    CAFE_QR_DESCRIPCION,
    CAFE_ILUSTRACION_DESCRIPCION,
    CAFE_ENLACE_COPIADO,
    CAFE_GRACIAS,
    CAFE_SIN_DESBLOQUEOS,
    CAFE_ENTRADA_AJUSTES,
    CAFE_NO_DISPONIBLE,

    // -------------------------------------------------- copia de seguridad
    COPIA_TITULO,
    COPIA_EXPORTAR_HECHO,
    COPIA_EXPORTAR_ERROR,
    COPIA_IMPORTAR_TITULO,
    COPIA_IMPORTAR_AVISO,
    COPIA_IMPORTAR_FUSIONAR,
    COPIA_IMPORTAR_REEMPLAZAR,
    COPIA_IMPORTAR_HECHO,
    COPIA_IMPORTAR_ERROR_FORMATO,
    COPIA_IMPORTAR_ERROR_VERSION,
    COPIA_IMPORTAR_RESPALDO,

    /** %1$s = fecha, %2$s = versión de la app. */
    COPIA_CABECERA_DETALLE,

    // ----------------------------------------------------------------- ayuda
    AYUDA_TITULO,
    AYUDA_SUBTITULO,
    AYUDA_QUE_ES_TITULO,
    AYUDA_QUE_ES_TEXTO,
    AYUDA_COMO_SE_JUEGA_TITULO,
    AYUDA_PARTIDA_1,
    AYUDA_PARTIDA_2,
    AYUDA_PARTIDA_3,
    AYUDA_PARTIDA_4,
    AYUDA_PARTIDA_5,
    AYUDA_UN_MOVIL_TITULO,
    AYUDA_UN_MOVIL_1,
    AYUDA_UN_MOVIL_2,
    AYUDA_UN_MOVIL_3,
    AYUDA_VARIOS_MOVILES_TITULO,
    AYUDA_VARIOS_MOVILES_1,
    AYUDA_VARIOS_MOVILES_2,
    AYUDA_VARIOS_MOVILES_3,
    AYUDA_FAQ_TITULO,
    AYUDA_FAQ_1_P,
    AYUDA_FAQ_1_R,
    AYUDA_FAQ_2_P,
    AYUDA_FAQ_2_R,
    AYUDA_FAQ_3_P,
    AYUDA_FAQ_3_R,
    AYUDA_FAQ_4_P,
    AYUDA_FAQ_4_R,
    AYUDA_FAQ_5_P,
    AYUDA_FAQ_5_R,
    AYUDA_PROBLEMAS_TITULO,
    AYUDA_PROBLEMAS_TEXTO,
    AYUDA_ESCRIBENOS,

    // ------------------------------------------------------------- acerca de
    ACERCA_TITULO,
    ACERCA_VERSION,
    ACERCA_COMPILACION,
    ACERCA_FECHA,
    ACERCA_COMMIT,
    ACERCA_LICENCIA,
    ACERCA_LICENCIAS_TERCEROS,
    ACERCA_PRIVACIDAD,
    ACERCA_CONTACTO,
    ACERCA_CODIGO,
    ACERCA_SIN_ANUNCIOS,
    ACERCA_AUTOR,

    // ------------------------------------------------------------------ tour
    TOUR_TITULO,
    TOUR_SUBTITULO,
    TOUR_EMPEZAR,
    TOUR_SALTAR,
    TOUR_ANTERIOR,
    TOUR_SIGUIENTE,
    TOUR_TERMINAR,

    /** %1$d = paso actual, %2$d = total de pasos. */
    TOUR_PROGRESO,
    TOUR_BIENVENIDA_TITULO,
    TOUR_BIENVENIDA_TEXTO,
    TOUR_MODOS_TITULO,
    TOUR_MODOS_TEXTO,
    TOUR_TABLERO_TITULO,
    TOUR_TABLERO_TEXTO,
    TOUR_CASILLAS_TITULO,
    TOUR_CASILLAS_TEXTO,
    TOUR_JUEGOS_TITULO,
    TOUR_JUEGOS_TEXTO,
    TOUR_SALON_TITULO,
    TOUR_SALON_TEXTO,
    TOUR_AJUSTES_TITULO,
    TOUR_AJUSTES_TEXTO,
    TOUR_FINAL_TITULO,
    TOUR_FINAL_TEXTO,

    // ------------------------------------------------------- salón (varios móviles)
    SALON_TITULO,
    SALON_SUBTITULO,
    SALON_CREAR,
    SALON_CREAR_DETALLE,
    SALON_UNIRSE,
    SALON_UNIRSE_DETALLE,
    SALON_TU_NOMBRE,
    SALON_HUB_TITULO,
    SALON_HUB_ESPERANDO,

    /** %1$d = dispositivos conectados. */
    SALON_HUB_CONECTADOS,
    SALON_HUB_EMPEZAR,
    SALON_CLIENTE_TITULO,
    SALON_CLIENTE_BUSCANDO,
    SALON_CLIENTE_SIN_SALONES,
    SALON_CLIENTE_CONECTANDO,
    SALON_CLIENTE_CONECTADO,
    SALON_CLIENTE_ESPERA,
    SALON_SALIR,
    SALON_DESCONECTADO,
    SALON_ERROR_PERMISOS,
    SALON_PEDIR_PERMISOS,
    SALON_PERMISOS_EXPLICACION,
    SALON_ERROR_BLUETOOTH,
    SALON_ERROR_UBICACION,
    SALON_ERROR_SERVICIOS,
    SALON_COMO_FUNCIONA,
    SALON_ESTE_DISPOSITIVO,
    SALON_ROL_HUB,
    SALON_ROL_MANDO,
    SALON_SIN_RED,
    SALON_SIN_NOMBRE,
    SALON_TU_TURNO,
    SALON_MIRA_EL_HUB,

    // --------------------------------------------------------- nombres de juego
    JUEGO_MIMICA_NOMBRE,
    JUEGO_MIMICA_LEMA,
    JUEGO_MIMICA_INSTRUCCIONES,
    JUEGO_DIBUJO_NOMBRE,
    JUEGO_DIBUJO_LEMA,
    JUEGO_DIBUJO_INSTRUCCIONES,
    JUEGO_CUANDO_NOMBRE,
    JUEGO_CUANDO_LEMA,
    JUEGO_CUANDO_INSTRUCCIONES,
    JUEGO_PREGUNTAS_NOMBRE,
    JUEGO_PREGUNTAS_LEMA,
    JUEGO_PREGUNTAS_INSTRUCCIONES,
    JUEGO_TABU_NOMBRE,
    JUEGO_TABU_LEMA,
    JUEGO_TABU_INSTRUCCIONES,
    JUEGO_RETO_NOMBRE,
    JUEGO_RETO_LEMA,
    JUEGO_RETO_INSTRUCCIONES,
    JUEGO_EMOJIS_NOMBRE,
    JUEGO_EMOJIS_LEMA,
    JUEGO_EMOJIS_INSTRUCCIONES,
    JUEGO_VERDADERO_FALSO_NOMBRE,
    JUEGO_VERDADERO_FALSO_LEMA,
    JUEGO_VERDADERO_FALSO_INSTRUCCIONES,
    JUEGO_TRABALENGUAS_NOMBRE,
    JUEGO_TRABALENGUAS_LEMA,
    JUEGO_TRABALENGUAS_INSTRUCCIONES,
    JUEGO_ORDENA_NOMBRE,
    JUEGO_ORDENA_LEMA,
    JUEGO_ORDENA_INSTRUCCIONES,
    JUEGO_CANTA_NOMBRE,
    JUEGO_CANTA_LEMA,
    JUEGO_CANTA_INSTRUCCIONES,
    JUEGO_DESAFIO_NOMBRE,
    JUEGO_DESAFIO_LEMA,
    JUEGO_DESAFIO_INSTRUCCIONES,

    // -------------------------------------------------------------- ritmo y duración
    RITMO_RAPIDO,
    RITMO_NORMAL,
    RITMO_TRANQUILO,
    DURACION_CORTA,
    DURACION_NORMAL,
    DURACION_LARGA,
    DURACION_CORTA_DETALLE,
    DURACION_NORMAL_DETALLE,
    DURACION_LARGA_DETALLE,

    // ------------------------------------------------------------ accesibilidad
    A11Y_DADO,
    A11Y_FICHA,
    A11Y_CASILLA,
    A11Y_VOLVER,
    A11Y_CERRAR,
    A11Y_LIENZO_DIBUJO,
    A11Y_TEMA_MUESTRA,
    A11Y_BANDERA_IDIOMA,
}
