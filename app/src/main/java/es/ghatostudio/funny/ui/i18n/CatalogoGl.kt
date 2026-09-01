package es.ghatostudio.funny.ui.i18n

import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.dominio.textos.Clave.*

/**
 * Catálogo en gallego.
 *
 * Nota sobre la donación: se evita «comprar» y «pagar», igual que en los demás
 * idiomas. Se usa «convídame a un café», que dice lo mismo sin enmarcarlo como
 * una compra. Lo comprueba `PruebaCatalogos`.
 */
internal val catalogoGallego =
    Catalogo(
        idioma = Idioma.GALLEGO,
        textos =
            mapOf<Clave, String>(
                ACCION_VOLVER to "Volver",
                ACCION_CERRAR to "Pechar",
                ACCION_CANCELAR to "Cancelar",
                ACCION_ACEPTAR to "Aceptar",
                ACCION_CONTINUAR to "Continuar",
                ACCION_EMPEZAR to "¡A xogar!",
                ACCION_LISTO to "Listo",
                ACCION_BORRAR to "Eliminar",
                ACCION_ANADIR to "Engadir",
                ACCION_REINTENTAR to "Reintentar",
                ACCION_COPIAR to "Copiar a ligazón",
                ACCION_COMPARTIR to "Compartir",
                ACCION_AHORA_NO to "Agora non",
                ACCION_SI to "Si",
                ACCION_NO to "Non",
                ESTADO_CARGANDO to "Cargando…",
                ESTADO_SIN_CONTENIDO to "Non hai contido para este xogo.",
                APP_LEMA to "O xogo de festa que colle nos vosos móbiles",
                MENU_JUGAR to "XOGAR",
                MENU_SEGUIR_PARTIDA to "SEGUIR A PARTIDA",
                MENU_PARTIDA_NUEVA to "PARTIDA NOVA",
                MENU_COMO_JUGAR to "COMO SE XOGA",
                MENU_AJUSTES to "AXUSTES",
                MENU_SALON to "XOGAR CON VARIOS MÓBILES",
                MENU_TOUR to "VER O TOUR",
                MODO_TITULO to "Como xogamos?",
                MODO_SUBTITULO to "Pódese cambiar en calquera partida nova.",
                MODO_EQUIPOS to "Por equipos",
                MODO_EQUIPOS_DETALLE to
                    "De 2 a 6 equipos. Cada equipo ten a súa ficha e vai rotando quen " +
                    "actúa. É a forma clásica e a máis ruidosa.",
                MODO_INDIVIDUAL to "Individual",
                MODO_INDIVIDUAL_DETALLE to
                    "De 2 a 8 persoas, cada unha coa súa ficha e sen equipos. Nas probas " +
                    "de actuar, quen ten a quenda actúa e adiviña o resto da mesa.",
                MODO_SOLITARIO to "Reto en solitario",
                MODO_SOLITARIO_DETALLE to
                    "Ti contra o reloxo: dez probas seguidas e unha marca persoal que " +
                    "bater. Sen taboleiro e só cos xogos que se poden xogar sen ninguén " +
                    "diante.",
                PARTICIPANTES_TITULO_EQUIPOS to "Equipos",
                PARTICIPANTES_TITULO_INDIVIDUAL to "Xogadores",
                PARTICIPANTES_TITULO_SOLITARIO to "Como te chamas?",
                PARTICIPANTES_SUBTITULO_EQUIPOS to
                    "De %1\$d a %2\$d equipos. Apunta quen xoga en cada un e o " +
                    "móbil irá dicindo a quen lle toca actuar.",
                PARTICIPANTES_SUBTITULO_INDIVIDUAL to "De %1\$d a %2\$d persoas, cada unha coa súa ficha.",
                PARTICIPANTES_SUBTITULO_SOLITARIO to "Só para poñer o teu nome na marca. Nada disto sae do móbil.",
                PARTICIPANTES_ANADIR_EQUIPO to "+  ENGADIR EQUIPO",
                PARTICIPANTES_ANADIR_JUGADOR to "+  ENGADIR XOGADOR",
                PARTICIPANTES_NUEVO_JUGADOR to "Engadir xogador…",
                PARTICIPANTES_SIN_JUGADORES to "Sen nomes apuntados: o xogo dirá só o nome do equipo.",
                PARTICIPANTES_NOMBRE_EQUIPO to "Nome do equipo",
                PARTICIPANTES_NOMBRE_JUGADOR to "Nome",
                PARTICIPANTES_QUITAR to "Quitar",
                PARTICIPANTES_EQUIPO_POR_DEFECTO to "Equipo %1\$d",
                PARTICIPANTES_JUGADOR_POR_DEFECTO to "Xogador %1\$d",
                PARTICIPANTES_EQUIPO_1_POR_DEFECTO to "Os Cracks",
                PARTICIPANTES_EQUIPO_2_POR_DEFECTO to "As Feras",
                PARTICIPANTES_DESDE_SALON to "Uniuse desde outro móbil",
                TABLERO_TIRAR to "TIRAR O DADO",
                TABLERO_TURNO_DE to "QUENDA DE %1\$s",
                TABLERO_LE_TOCA to "Tócalle a %1\$s",
                TABLERO_CASILLA to "Casa %1\$d",
                TABLERO_SALIDA to "SAÍDA",
                TABLERO_META to "META",
                TABLERO_MARCADOR to "Marcador",
                TABLERO_ABANDONAR to "Abandonar a partida",
                TABLERO_ABANDONAR_PREGUNTA to "Perderase o avance desta partida. Seguro que queres saír?",
                TABLERO_ESPERANDO_HUB to "Agardando polo móbil que leva a partida…",
                TABLERO_AVANZA_CASILLAS to "Avanza %1\$d",
                CASILLA_COMODIN to "COMODÍN",
                CASILLA_COMODIN_DETALLE to "O rival escolle a proba que che toca. Sen piedade.",
                CASILLA_TODOS to "XOGAN TODOS",
                CASILLA_TODOS_DETALLE to "A mesma proba para toda a mesa. Cada un que acerte avanza unha casa.",
                CASILLA_META_AVISO to "Proba final: só se gaña superándoa.",
                COMODIN_TITULO to "Casa comodín",
                COMODIN_ELIGE to "%1\$s escolle a proba",
                PRUEBA_FINAL to "🏁  PROBA FINAL",
                PRUEBA_JUEGAN_TODOS to "👥  XOGAN TODOS",
                PRUEBA_LE_TOCA_ACTUAR_A to "TÓCALLE ACTUAR A",
                PRUEBA_QUIEN_DECIDA to "quen decida %1\$s",
                PRUEBA_SOLO_MIRE_ESA_PERSONA to "👀  Que só mire esta persoa a pantalla",
                PRUEBA_MIRA_TU_MOVIL to "📱  Mira o teu propio móbil: a palabra chegouche aí",
                PRUEBA_SEGUNDOS to "⏱  %1\$d segundos",
                PRUEBA_CUANDO_TEMA to "En que ano?",
                PRUEBA_CUANDO_RESPUESTA to "Ocorreu en %1\$d.",
                PRUEBA_ACIERTOS_DE to "%1\$d de %2\$d",
                PRUEBA_SALTAR to "SALTAR",
                PRUEBA_ACERTADA to "✓  ACERTADA",
                PRUEBA_FALLADA to "✗  FALLADA",
                PRUEBA_PROHIBIDA to "🚫  PROHIBIDA",
                PRUEBA_TERMINAR to "Rematar a proba",
                PRUEBA_ACIERTOS to "✔  %1\$d",
                PRUEBA_VEREDICTO_TITULO to "Conseguiuno?",
                PRUEBA_VEREDICTO_LOGRADO to "✓  ¡SI!",
                PRUEBA_VEREDICTO_NO_LOGRADO to "✗  NON",
                PRUEBA_VEREDICTO_DECIDE_MESA to "Decídeo o resto da mesa, non a app.",
                PRUEBA_VEREDICTO_DECIDE_SOLO to "Aquí non hai ninguén mirando: sé honesto contigo.",
                PRUEBA_BORRAR_DIBUJO to "Borrar todo",
                PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA to "SÓ A MIRA QUEN DEBUXA",
                PRUEBA_DIBUJO_TIEMPO_AL_PULSAR to "O tempo empeza a contar ao premer o botón.",
                PRUEBA_DIBUJO_EMPEZAR to "🎨   EMPEZAR A DEBUXAR",
                PRUEBA_DIBUJO_LIENZO_VACIO to "Debuxa aquí co dedo",
                PRUEBA_DIBUJO_ESPIAR to "Manter premido para ver outra vez a palabra",
                PRUEBA_DIBUJO_GOMA to "Goma de borrar",
                PRUEBA_DESHACER to "Desfacer",
                PRUEBA_PINCEL to "Grosor",
                PRUEBA_COLOR to "Cor",
                PRUEBA_RETO_OBJETIVO to "Hai que chegar a %1\$d",
                PRUEBA_RETO_LLEVAMOS to "Levamos",
                PRUEBA_RETO_UNA_MAS to "+1  ¡OUTRA!",
                PRUEBA_RETO_CONSEGUIDO to "¡CONSEGUIDO!",
                PRUEBA_RETO_TOCA_PARA_SUMAR to "de %1\$d  ·  toca para sumar",
                PRUEBA_RETO_RENDIRSE to "RENDÉMONOS",
                PRUEBA_RETO_NOTA to "Cada resposta vale unha vez. Se se repite, non conta.",
                PRUEBA_ORDENA_AYUDA to "Toca na orde correcta, do primeiro ao último.",
                PRUEBA_ORDENA_COMPROBAR to "COMPROBAR",
                PRUEBA_ORDENA_CORRECTO to "A orde correcta era:",
                PRUEBA_VF_VERDADERO to "VERDADEIRO",
                PRUEBA_VF_FALSO to "FALSO",
                PRUEBA_VF_ERA_VERDAD to "Era verdade",
                PRUEBA_VF_ERA_MENTIRA to "Era mentira",
                PRUEBA_EMOJIS_AYUDA to "Que é isto escrito con emojis?",
                PRUEBA_EMOJIS_ERA to "Era: %1\$s",
                PRUEBA_CANTA_PISTA to "Empeza por aquí",
                PRUEBA_TRABALENGUAS_AYUDA to "Dío enteiro e sen trabarte.",
                PRUEBA_DESAFIO_AYUDA to "A ver como saes desta.",
                RONDA_TODOS_PASA_MOVIL to "Pasa o móbil sen mirar a resposta de ninguén.",
                RONDA_TODOS_RESPONDE to "Responde %1\$s",
                RONDA_TODOS_RESUMEN to "Quen acertou",
                RONDA_TODOS_NADIE to "Ninguén. Non se move ningunha ficha.",
                RONDA_TODOS_EN_TU_MOVIL to "Responde no teu propio móbil.",
                RONDA_TODOS_ESPERANDO to "Agardando polo resto…",
                RONDA_TODOS_PASAD_A to "PASADE O MÓBIL A",
                RONDA_TODOS_PROGRESO to "%1\$d de %2\$d. Ninguén saberá quen acertou ata o final.",
                RONDA_TODOS_SIN_RESPUESTA to "Acabouse o tempo, sen resposta.",
                RONDA_TODOS_GUARDADA to "Resposta gardada. Non a contedes aínda.",
                RONDA_TODOS_CORRECTA_ERA to "A RESPOSTA CORRECTA ERA",
                RONDA_TODOS_SIN_RESPONDER to "sen resposta",
                RONDA_TODOS_VER_RESULTADOS to "VER OS RESULTADOS",
                RESULTADO_SUPERADA to "¡SUPERADA!",
                RESULTADO_NO_HA_PODIDO_SER to "NON PUIDO SER",
                RESULTADO_FINAL_SUPERADA to "¡PROBA FINAL SUPERADA!",
                RESULTADO_AVANZAS_A to "Avanzades á casa %1\$d",
                RESULTADO_TE_QUEDAS_EN to "Quedades na casa %1\$d",
                RESULTADO_LLEGADA_A_META to "¡Chegastes á meta!",
                RESULTADO_SIGUIENTE_TURNO to "SEGUINTE QUENDA",
                RESULTADO_VER_RESULTADO to "VER O RESULTADO",
                VICTORIA_TITULO to "¡Fin da partida!",
                VICTORIA_GANADOR to "Gaña %1\$s",
                VICTORIA_CLASIFICACION to "Como quedou",
                VICTORIA_OTRA_PARTIDA to "OUTRA PARTIDA",
                VICTORIA_AL_MENU to "VOLVER AO MENÚ",
                VICTORIA_SOLITARIO_TITULO to "Reto rematado",
                VICTORIA_SOLITARIO_PUNTOS to "%1\$d puntos",
                VICTORIA_SOLITARIO_MEJOR to "A túa mellor marca: %1\$d",
                VICTORIA_SOLITARIO_RECORD to "¡Marca persoal nova!",
                SOLITARIO_TITULO to "Reto en solitario",
                SOLITARIO_SUBTITULO to "Dez probas. Ti contra o reloxo.",
                SOLITARIO_PROGRESO to "Proba %1\$d de %2\$d",
                SOLITARIO_RONDAS to "Probas",
                SOLITARIO_EMPEZAR to "EMPEZAR O RETO",
                SOLITARIO_MEJOR_MARCA to "Mellor marca: %1\$d",
                SOLITARIO_SIN_MARCA to "Aínda non tes marca. Estréaa.",
                AJUSTES_TITULO to "Axustes",
                AJUSTES_SUBTITULO to "Gárdanse para a próxima partida.",
                AJUSTES_APARIENCIA to "Aparencia",
                AJUSTES_TEMA to "Tema",
                AJUSTES_TEMA_DETALLE to "Seis temas: tres claros e tres escuros.",
                AJUSTES_TEMA_SISTEMA to "Seguir o sistema",
                AJUSTES_IDIOMA to "Idioma",
                AJUSTES_IDIOMA_DETALLE to "Trece idiomas dispoñibles.",
                AJUSTES_PARTIDA to "A partida",
                AJUSTES_RITMO to "Ritmo das probas",
                AJUSTES_RITMO_DETALLE to "Canto tempo hai para cada proba.",
                AJUSTES_DURACION to "Duración da partida",
                AJUSTES_DURACION_DETALLE to "%1\$d casas ata a meta · %2\$s",
                AJUSTES_JUEGOS_ACTIVOS to "Xogos da partida",
                AJUSTES_JUEGOS_ACTIVOS_DETALLE to "Quita os que non vos gusten e deixarán de saír no taboleiro.",
                AJUSTES_JUEGOS_CONTADOR to "%1\$d de %2\$d activos",
                AJUSTES_JUEGOS_MINIMO to "Ten que quedar polo menos un xogo activo.",
                AJUSTES_SONIDO to "Son",
                AJUSTES_SONIDO_DETALLE to "Pitidos da conta atrás e avisos",
                AJUSTES_VIBRACION to "Vibración",
                AJUSTES_VIBRACION_DETALLE to "Acertos, fallos e fin de tempo",
                AJUSTES_ANIMACIONES to "Animacións",
                AJUSTES_ANIMACIONES_DETALLE to "Apágaas se preferes a interface quieta",
                AJUSTES_DATOS to "Os teus datos",
                AJUSTES_EXPORTAR to "Exportar",
                AJUSTES_EXPORTAR_DETALLE to "Garda equipos, axustes e marcas nun ficheiro",
                AJUSTES_IMPORTAR to "Importar",
                AJUSTES_IMPORTAR_DETALLE to "Recupera unha copia gardada antes",
                AJUSTES_MAS to "Máis",
                AJUSTES_APOYAR to "Apoiar o desenvolvemento",
                AJUSTES_APOYAR_DETALLE to "Convídame a un café se che resulta útil",
                AJUSTES_COMPARTIR to "Compartir Funny",
                AJUSTES_COMPARTIR_DETALLE to "Pásao a quen creas que o vai gozar",
                AJUSTES_AYUDA to "Axuda",
                AJUSTES_AYUDA_DETALLE to "Como se xoga e preguntas frecuentes",
                AJUSTES_TOUR to "Tour guiado",
                AJUSTES_TOUR_DETALLE to "Os doce xogos e os tres modos, explicados",
                AJUSTES_ACERCA_DE to "Acerca de",
                AJUSTES_ACERCA_DE_DETALLE to "Versión, licenzas e privacidade",
                TEMA_MODO_CLARO to "Claros",
                TEMA_MODO_OSCURO to "Escuros",
                TEMA_FIESTA to "Festa",
                TEMA_NEON to "Neón",
                TEMA_MEDIANOCHE to "Medianoite",
                TEMA_PAPEL to "Papel",
                TEMA_MENTA to "Menta",
                TEMA_ATARDECER to "Solpor",
                IDIOMA_TITULO to "Idioma",
                IDIOMA_SEGUIR_SISTEMA to "O do móbil",
                IDIOMA_SUBTITULO to "O cambio aplícase ao instante.",
                CAFE_TITULO to "Un café?",
                CAFE_TEXTO to
                    "Esta app é gratuíta, sen anuncios e non recolle os teus datos. Se che resulta " +
                    "útil, podes convidarme a un café.",
                CAFE_BOTON to "Convídame a un café · 1 €",
                CAFE_NO_VOLVER to "Non volver amosar",
                CAFE_OTRO_DISPOSITIVO to "Desde outro dispositivo",
                CAFE_QR_DESCRIPCION to "Código QR coa ligazón para convidar a un café ao autor da app",
                CAFE_ILUSTRACION_DESCRIPCION to "Debuxo dunha taza de café con vapor",
                CAFE_ENLACE_COPIADO to "Ligazón copiada",
                CAFE_GRACIAS to "Grazas por pasar por alí 🙂",
                CAFE_SIN_DESBLOQUEOS to "Non cambia nada dentro do xogo: Funny está enteira e sempre o estará.",
                CAFE_ENTRADA_AJUSTES to "Apoiar o desenvolvemento",
                CAFE_NO_DISPONIBLE to "Neste dispositivo non está dispoñible.",
                COPIA_TITULO to "Copia dos teus datos",
                COPIA_EXPORTAR_HECHO to "Copia gardada.",
                COPIA_EXPORTAR_ERROR to "Non se puido gardar a copia.",
                COPIA_IMPORTAR_TITULO to "Importar unha copia",
                COPIA_IMPORTAR_AVISO to
                    "Antes de tocar nada gárdase unha copia do que tes agora, así que " +
                    "sempre se pode volver atrás.",
                COPIA_IMPORTAR_FUSIONAR to "Engadir ao que xa teño",
                COPIA_IMPORTAR_REEMPLAZAR to "Substituílo todo",
                COPIA_IMPORTAR_HECHO to "Datos importados.",
                COPIA_IMPORTAR_ERROR_FORMATO to "Ese ficheiro non parece unha copia de Funny. Non se cambiou nada.",
                COPIA_IMPORTAR_ERROR_VERSION to
                    "Esa copia é dunha versión máis nova de Funny. Actualiza a app " +
                    "e téntao outra vez.",
                COPIA_IMPORTAR_RESPALDO to "Gardouse antes unha copia de seguranza.",
                COPIA_CABECERA_DETALLE to "Copia do %1\$s · Funny %2\$s",
                AYUDA_TITULO to "Axuda",
                AYUDA_SUBTITULO to "Todo o que fai falta para non perderse.",
                AYUDA_QUE_ES_TITULO to "Que é Funny?",
                AYUDA_QUE_ES_TEXTO to
                    "Un xogo de festa con doce probas distintas. Xógase cun móbil que vai " +
                    "pasando de man en man ou, se sodes varios con móbil, conectándoos entre " +
                    "eles. Non fai falta internet en ningún momento.",
                AYUDA_COMO_SE_JUEGA_TITULO to "A partida",
                AYUDA_PARTIDA_1 to "Escollede o modo: por equipos, individual ou o reto en solitario.",
                AYUDA_PARTIDA_2 to "Cada equipo ou xogador ten unha ficha e todos saen da SAÍDA.",
                AYUDA_PARTIDA_3 to "Na túa quenda tírase o dado e avánzase de 1 a 3 casas.",
                AYUDA_PARTIDA_4 to "A casa na que caes decide a proba. Se a superas, quedas; se non, volves.",
                AYUDA_PARTIDA_5 to "Gaña quen chegue á META e supere a proba final.",
                AYUDA_UN_MOVIL_TITULO to "Cun só móbil",
                AYUDA_UN_MOVIL_1 to "O móbil vai pasando: a app sempre di a quen lle toca.",
                AYUDA_UN_MOVIL_2 to "En mímica, tabú, debuxo, canta e desafío só mira quen actúa.",
                AYUDA_UN_MOVIL_3 to "Nas de responder, a pantalla pódese amosar a todos.",
                AYUDA_VARIOS_MOVILES_TITULO to "Con varios móbiles",
                AYUDA_VARIOS_MOVILES_1 to
                    "Un móbil fai de mesa (o hub) e os demais conéctanse a el. Non fai " +
                    "falta wifi nin datos.",
                AYUDA_VARIOS_MOVILES_2 to
                    "A palabra secreta chega só ao móbil de quen actúa, así que ninguén a " +
                    "ve por erro.",
                AYUDA_VARIOS_MOVILES_3 to "Nas casas de «xogan todos», cada un responde no seu móbil á vez.",
                AYUDA_FAQ_TITULO to "Preguntas frecuentes",
                AYUDA_FAQ_1_P to "Fai falta internet?",
                AYUDA_FAQ_1_R to
                    "Non. Funny funciona enteira sen conexión, e conectar varios móbiles usa " +
                    "Bluetooth e wifi directo entre eles, sen pasar por ningunha rede.",
                AYUDA_FAQ_2_P to "Pódese xogar unha soa persoa?",
                AYUDA_FAQ_2_R to
                    "Si: o reto en solitario son dez probas seguidas con marca persoal. Só entran " +
                    "os xogos que non necesitan público.",
                AYUDA_FAQ_3_P to "Custa algo? Hai algo bloqueado?",
                AYUDA_FAQ_3_R to
                    "Non hai nada bloqueado nin nada que conseguir aparte. Se che gusta, podes " +
                    "convidarme a un café desde Axustes, e iso non cambia absolutamente nada " +
                    "dentro do xogo.",
                AYUDA_FAQ_4_P to "Recolle datos?",
                AYUDA_FAQ_4_R to
                    "Non. Non hai analítica, nin contas, nin publicidade. Os equipos e os axustes " +
                    "gárdanse só no teu móbil e saen de aí unicamente se ti exportas unha copia.",
                AYUDA_FAQ_5_P to "Podo cambiar os xogos que saen?",
                AYUDA_FAQ_5_R to "Si, en Axustes → Xogos da partida. Os que quites deixan de aparecer no taboleiro.",
                AYUDA_PROBLEMAS_TITULO to "Se algo falla",
                AYUDA_PROBLEMAS_TEXTO to
                    "Pecha e volve abrir a app: a partida en curso consérvase. Se o " +
                    "problema segue, exporta os teus datos antes de reinstalar e " +
                    "escríbenos contando que pasaba.",
                AYUDA_ESCRIBENOS to "Escribir ao autor",
                ACERCA_TITULO to "Acerca de",
                ACERCA_VERSION to "Versión",
                ACERCA_COMPILACION to "Compilación",
                ACERCA_FECHA to "Data",
                ACERCA_COMMIT to "Commit",
                ACERCA_LICENCIA to "Licenza",
                ACERCA_LICENCIAS_TERCEROS to "Licenzas de terceiros",
                ACERCA_PRIVACIDAD to "Política de privacidade",
                ACERCA_CONTACTO to "Contacto",
                ACERCA_CODIGO to "Código fonte",
                ACERCA_SIN_ANUNCIOS to "Sen anuncios, sen analítica e sen contas.",
                ACERCA_AUTOR to "Ghato Studio · Brais Galdo",
                TOUR_TITULO to "Tour guiado",
                TOUR_SUBTITULO to "Os doce xogos e os tres modos, en dous minutos.",
                TOUR_EMPEZAR to "EMPEZAR O TOUR",
                TOUR_SALTAR to "Saltar",
                TOUR_ANTERIOR to "Anterior",
                TOUR_SIGUIENTE to "Seguinte",
                TOUR_TERMINAR to "¡A XOGAR!",
                TOUR_PROGRESO to "%1\$d de %2\$d",
                TOUR_BIENVENIDA_TITULO to "Benvido a Funny",
                TOUR_BIENVENIDA_TEXTO to
                    "Doce xogos, tres formas de xogar e cero necesidade de internet. Nun " +
                    "par de minutos cóntocho todo; podes saltalo cando queiras.",
                TOUR_MODOS_TITULO to "Tres formas de xogar",
                TOUR_MODOS_TEXTO to
                    "Por equipos é o clásico: de 2 a 6 equipos e vai rotando quen actúa. " +
                    "Individual é o mesmo pero cada persoa leva a súa ficha, de 2 a 8. E o reto " +
                    "en solitario son dez probas contra o reloxo, ti só, con marca persoal.",
                TOUR_TABLERO_TITULO to "O taboleiro",
                TOUR_TABLERO_TEXTO to
                    "Cada ficha empeza na SAÍDA. Na túa quenda tiras o dado, avanzas de 1 a 3 " +
                    "casas e a casa na que caes decide a proba. Se a superas quedas aí; se " +
                    "fallas volves onde estabas. Gaña quen chegue á META e supere a proba " +
                    "final.",
                TOUR_CASILLAS_TITULO to "As casas especiais",
                TOUR_CASILLAS_TEXTO to
                    "🃏 Comodín: o rival escolle a proba que che toca.\n👥 Xogan todos: a " +
                    "mesma proba para toda a mesa, e cada un que acerta avanza unha casa.\n🏁 " +
                    "Meta: proba final ao azar. Sen superala non se gaña.",
                TOUR_JUEGOS_TITULO to "Os doce xogos",
                TOUR_JUEGOS_TEXTO to "Estes son todos. Podes desactivar os que non vos gusten en Axustes.",
                TOUR_SALON_TITULO to "Varios móbiles á vez",
                TOUR_SALON_TEXTO to
                    "Un móbil fai de mesa e os demais conéctanse a el por Bluetooth ou wifi " +
                    "directo, sen internet. Serve para o que de verdade importa: a palabra " +
                    "secreta chega só ao móbil de quen actúa, e nas casas de «xogan todos» cada " +
                    "un responde no seu ao mesmo tempo.",
                TOUR_AJUSTES_TITULO to "Axústao ao teu gusto",
                TOUR_AJUSTES_TEXTO to
                    "Seis temas, trece idiomas, tres ritmos e tres duracións. Tamén podes " +
                    "desactivar xogos, apagar o son e a vibración, e gardar ou recuperar os " +
                    "teus datos nun ficheiro.",
                TOUR_FINAL_TITULO to "Xa está",
                TOUR_FINAL_TEXTO to
                    "Podes volver ver isto cando queiras desde Axustes → Tour guiado. Que o " +
                    "pasedes ben.",
                SALON_TITULO to "Varios móbiles",
                SALON_SUBTITULO to "Sen internet: conéctanse entre eles.",
                SALON_CREAR to "FACER DE MESA",
                SALON_CREAR_DETALLE to "Este móbil leva a partida e amosa o taboleiro. É o que se deixa na mesa.",
                SALON_UNIRSE to "UNIRME A UNHA MESA",
                SALON_UNIRSE_DETALLE to "Este móbil queda na túa man e recibe as túas probas en privado.",
                SALON_TU_NOMBRE to "O teu nome",
                SALON_HUB_TITULO to "Es a mesa",
                SALON_HUB_ESPERANDO to "Agardando a que se conecten…",
                SALON_HUB_CONECTADOS to "Conectados",
                SALON_HUB_EMPEZAR to "EMPEZAR A PARTIDA",
                SALON_CLIENTE_TITULO to "Buscando mesa",
                SALON_CLIENTE_BUSCANDO to "Buscando mesas preto…",
                SALON_CLIENTE_SIN_SALONES to
                    "Aínda non se ve ningunha. Que o outro móbil abra «Facer de mesa» " +
                    "e agardade uns segundos.",
                SALON_CLIENTE_CONECTANDO to "Conectando…",
                SALON_CLIENTE_CONECTADO to "Conectado",
                SALON_CLIENTE_ESPERA to "Xa estás dentro. Mira a mesa: a partida empeza alí.",
                SALON_SALIR to "Saír do salón",
                SALON_DESCONECTADO to "Perdeuse a conexión coa mesa.",
                SALON_ERROR_PERMISOS to "Faltan permisos para buscar os móbiles do lado.",
                SALON_PEDIR_PERMISOS to "DAR PERMISOS",
                SALON_PERMISOS_EXPLICACION to
                    "Para atopar os móbiles que tes ao lado, Android pide permiso de " +
                    "dispositivos próximos e, en versións antigas, tamén de " +
                    "localización. Funny non consulta onde estás nin o garda en " +
                    "ningún sitio: é o prezo que pon o sistema por usar Bluetooth e " +
                    "wifi directo.",
                SALON_ERROR_BLUETOOTH to "Acende o Bluetooth para poder conectar os móbiles.",
                SALON_ERROR_UBICACION to "Acende a localización: Android esíxea para buscar por Bluetooth.",
                SALON_ERROR_SERVICIOS to
                    "Este móbil non ten os servizos de Google que fan falta para conectar. " +
                    "Podedes seguir xogando pasándovos un só móbil.",
                SALON_COMO_FUNCIONA to "Como funciona?",
                SALON_ESTE_DISPOSITIVO to "Este móbil",
                SALON_ROL_HUB to "Mesa",
                SALON_ROL_MANDO to "Mando",
                SALON_SIN_RED to "Non se usa internet en ningún momento.",
                SALON_SIN_NOMBRE to "Sen nome",
                SALON_TU_TURNO to "¡Tócache!",
                SALON_MIRA_EL_HUB to "Mira o móbil da mesa.",
                JUEGO_MIMICA_NOMBRE to "Mímica",
                JUEGO_MIMICA_LEMA to "Represéntao sen falar",
                JUEGO_MIMICA_INSTRUCCIONES to
                    "Unha persoa representa a palabra con xestos. Prohibido falar, " +
                    "facer ruídos ou sinalar obxectos da sala.",
                JUEGO_DIBUJO_NOMBRE to "Debuxo",
                JUEGO_DIBUJO_LEMA to "Debúxao na pantalla",
                JUEGO_DIBUJO_INSTRUCCIONES to
                    "Unha persoa debuxa na pantalla do móbil e os demais adiviñan. " +
                    "Nada de letras, números nin xestos.",
                JUEGO_CUANDO_NOMBRE to "Cando?",
                JUEGO_CUANDO_LEMA to "En que ano ocorreu?",
                JUEGO_CUANDO_INSTRUCCIONES to
                    "Aparece un acontecemento e catro anos posibles. Hai que decidir " +
                    "en cal ocorreu.",
                JUEGO_PREGUNTAS_NOMBRE to "Preguntas",
                JUEGO_PREGUNTAS_LEMA to "Cultura xeral",
                JUEGO_PREGUNTAS_INSTRUCCIONES to
                    "Unha pregunta con catro respostas. Escóllese unha soa e non " +
                    "vale cambiar.",
                JUEGO_TABU_NOMBRE to "Tabú",
                JUEGO_TABU_LEMA to "Descríbeo sen dicilo",
                JUEGO_TABU_INSTRUCCIONES to
                    "Hai que describir a palabra sen usar ningunha das prohibidas nin " +
                    "palabras da mesma familia.",
                JUEGO_RETO_NOMBRE to "Reto rápido",
                JUEGO_RETO_LEMA to "Enumera contra o reloxo",
                JUEGO_RETO_INSTRUCCIONES to
                    "Ir dicindo cousas da categoría indicada ata chegar ao obxectivo " +
                    "antes de que se acabe o tempo.",
                JUEGO_EMOJIS_NOMBRE to "Emojis",
                JUEGO_EMOJIS_LEMA to "Descífrao",
                JUEGO_EMOJIS_INSTRUCCIONES to
                    "Unha película, unha canción ou un refrán escrito só con emojis, " +
                    "e catro respostas posibles.",
                JUEGO_VERDADERO_FALSO_NOMBRE to "Crédelo?",
                JUEGO_VERDADERO_FALSO_LEMA to "Verdadeiro ou falso",
                JUEGO_VERDADERO_FALSO_INSTRUCCIONES to
                    "Catro afirmacións raras seguidas. De cada unha hai que " +
                    "dicir se é verdade ou mentira, e despois explícase por " +
                    "que.",
                JUEGO_TRABALENGUAS_NOMBRE to "Trabalinguas",
                JUEGO_TRABALENGUAS_LEMA to "Dío sen trabarte",
                JUEGO_TRABALENGUAS_INSTRUCCIONES to
                    "Hai que dicir o trabalinguas as veces que pida, enteiro e " +
                    "sen equivocarse. Xúlgao a mesa.",
                JUEGO_ORDENA_NOMBRE to "Ordena",
                JUEGO_ORDENA_LEMA to "Ponno no seu sitio",
                JUEGO_ORDENA_INSTRUCCIONES to
                    "Catro cousas desordenadas e un criterio. Hai que tocalas na orde " +
                    "correcta.",
                JUEGO_CANTA_NOMBRE to "Canta",
                JUEGO_CANTA_LEMA to "Segue a canción",
                JUEGO_CANTA_INSTRUCCIONES to
                    "Sae o título e quen a canta, e hai que poñerse a cantar o " +
                    "estribillo. Xúlgao a mesa, coa xenerosidade que considere.",
                JUEGO_DESAFIO_NOMBRE to "Desafío",
                JUEGO_DESAFIO_LEMA to "Atrévete",
                JUEGO_DESAFIO_INSTRUCCIONES to
                    "Un pequeno reto diante de todos. Nada perigoso, nada " +
                    "humillante: só ridículo do bo. Xúlgao a mesa.",
                RITMO_RAPIDO to "Rápido",
                RITMO_NORMAL to "Normal",
                RITMO_TRANQUILO to "Tranquilo",
                DURACION_CORTA to "Curta",
                DURACION_NORMAL to "Normal",
                DURACION_LARGA to "Longa",
                DURACION_CORTA_DETALLE to "uns 15 min",
                DURACION_NORMAL_DETALLE to "uns 30 min",
                DURACION_LARGA_DETALLE to "uns 45 min",
                A11Y_DADO to "Dado: %1\$d",
                A11Y_FICHA to "Ficha de %1\$s na casa %2\$d",
                A11Y_CASILLA to "Casa %1\$d, %2\$s",
                A11Y_VOLVER to "Volver á pantalla anterior",
                A11Y_CERRAR to "Pechar",
                A11Y_LIENZO_DIBUJO to "Lenzo para debuxar co dedo",
                A11Y_TEMA_MUESTRA to "Mostra de cores do tema %1\$s",
                A11Y_BANDERA_IDIOMA to "Idioma %1\$s",
            ),
        plurales =
            mapOf(
                ClavePlural.CASILLAS to
                    mapOf(
                        CategoriaPlural.ONE to "%d casa",
                        CategoriaPlural.OTHER to "%d casas",
                    ),
                ClavePlural.SEGUNDOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d segundo",
                        CategoriaPlural.OTHER to "%d segundos",
                    ),
                ClavePlural.DISPOSITIVOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d móbil conectado",
                        CategoriaPlural.OTHER to "%d móbiles conectados",
                    ),
                ClavePlural.PUNTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d punto",
                        CategoriaPlural.OTHER to "%d puntos",
                    ),
                ClavePlural.ACIERTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d acerto",
                        CategoriaPlural.OTHER to "%d acertos",
                    ),
                ClavePlural.REPETICIONES to
                    mapOf(
                        CategoriaPlural.ONE to "%d vez",
                        CategoriaPlural.OTHER to "%d veces",
                    ),
            ),
    )
