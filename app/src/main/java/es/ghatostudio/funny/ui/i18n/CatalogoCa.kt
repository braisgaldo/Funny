package es.ghatostudio.funny.ui.i18n

import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.dominio.textos.Clave.*

/**
 * Catálogo en catalán.
 *
 * Nota sobre la donación: se evita «comprar» y «pagar», igual que en los demás
 * idiomas. Se usa «convida'm a un cafè», que dice lo mismo sin enmarcarlo como
 * una compra. Lo comprueba `PruebaCatalogos`.
 */
internal val catalogoCatalan =
    Catalogo(
        idioma = Idioma.CATALAN,
        textos =
            mapOf<Clave, String>(
                ACCION_VOLVER to "Torna",
                ACCION_CERRAR to "Tanca",
                ACCION_CANCELAR to "Cancel·la",
                ACCION_ACEPTAR to "D'acord",
                ACCION_CONTINUAR to "Continua",
                ACCION_EMPEZAR to "Comencem!",
                ACCION_LISTO to "Llest",
                ACCION_BORRAR to "Elimina",
                ACCION_ANADIR to "Afegeix",
                ACCION_REINTENTAR to "Torna-ho a provar",
                ACCION_COPIAR to "Copia l'enllaç",
                ACCION_COMPARTIR to "Comparteix",
                ACCION_AHORA_NO to "Ara no",
                ACCION_SI to "Sí",
                ACCION_NO to "No",
                ESTADO_CARGANDO to "S'està carregant…",
                ESTADO_SIN_CONTENIDO to "No hi ha contingut per a aquest joc.",
                APP_LEMA to "El joc de festa que cap als vostres mòbils",
                MENU_JUGAR to "JUGA",
                MENU_SEGUIR_PARTIDA to "CONTINUA LA PARTIDA",
                MENU_PARTIDA_NUEVA to "PARTIDA NOVA",
                MENU_COMO_JUGAR to "COM ES JUGA",
                MENU_AJUSTES to "CONFIGURACIÓ",
                MENU_SALON to "JUGA AMB DIVERSOS MÒBILS",
                MENU_TOUR to "MIRA EL TOUR",
                MODO_TITULO to "Com juguem?",
                MODO_SUBTITULO to "Es pot canviar en qualsevol partida nova.",
                MODO_EQUIPOS to "Per equips",
                MODO_EQUIPOS_DETALLE to
                    "De 2 a 6 equips. Cada equip té la seva fitxa i va rotant qui actua. És " +
                    "la forma clàssica i la més sorollosa.",
                MODO_INDIVIDUAL to "Individual",
                MODO_INDIVIDUAL_DETALLE to
                    "De 2 a 8 persones, cadascuna amb la seva fitxa i sense equips. A " +
                    "les proves d'actuar, qui té el torn actua i endevina la resta de la " +
                    "taula.",
                MODO_SOLITARIO to "Repte en solitari",
                MODO_SOLITARIO_DETALLE to
                    "Tu contra el rellotge: una tanda de proves seguides i una marca " +
                    "personal per batre. Sense tauler i només amb els jocs que es poden " +
                    "jugar sense ningú davant.",
                PARTICIPANTES_TITULO_EQUIPOS to "Equips",
                PARTICIPANTES_TITULO_INDIVIDUAL to "Jugadors",
                PARTICIPANTES_TITULO_SOLITARIO to "Com et dius?",
                PARTICIPANTES_SUBTITULO_EQUIPOS to
                    "De %1\$d a %2\$d equips. Apunta qui juga a cadascun i el " +
                    "mòbil anirà dient a qui li toca actuar.",
                PARTICIPANTES_SUBTITULO_INDIVIDUAL to "De %1\$d a %2\$d persones, cadascuna amb la seva fitxa.",
                PARTICIPANTES_SUBTITULO_SOLITARIO to
                    "Només per posar el teu nom a la marca. Res d'això surt " +
                    "del mòbil.",
                PARTICIPANTES_ANADIR_EQUIPO to "+  AFEGEIX EQUIP",
                PARTICIPANTES_ANADIR_JUGADOR to "+  AFEGEIX JUGADOR",
                PARTICIPANTES_NUEVO_JUGADOR to "Afegeix jugador…",
                PARTICIPANTES_SIN_JUGADORES to "Sense noms apuntats: el joc dirà només el nom de l'equip.",
                PARTICIPANTES_NOMBRE_EQUIPO to "Nom de l'equip",
                PARTICIPANTES_NOMBRE_JUGADOR to "Nom",
                PARTICIPANTES_QUITAR to "Treu",
                PARTICIPANTES_EQUIPO_POR_DEFECTO to "Equip %1\$d",
                PARTICIPANTES_JUGADOR_POR_DEFECTO to "Jugador %1\$d",
                PARTICIPANTES_EQUIPO_1_POR_DEFECTO to "Els Cracks",
                PARTICIPANTES_EQUIPO_2_POR_DEFECTO to "Les Feres",
                PARTICIPANTES_DESDE_SALON to "S'ha unit des d'un altre mòbil",
                TABLERO_TIRAR to "TIRA EL DAU",
                TABLERO_TURNO_DE to "TORN DE %1\$s",
                TABLERO_LE_TOCA to "Li toca a %1\$s",
                TABLERO_CASILLA to "Casella %1\$d",
                TABLERO_SALIDA to "SORTIDA",
                TABLERO_META to "META",
                TABLERO_MARCADOR to "Marcador",
                TABLERO_ABANDONAR to "Abandona la partida",
                TABLERO_ABANDONAR_PREGUNTA to "Es perdrà el progrés d'aquesta partida. Segur que vols sortir?",
                TABLERO_ESPERANDO_HUB to "Esperant el mòbil que porta la partida…",
                TABLERO_AVANZA_CASILLAS to "Avança %1\$d",
                CASILLA_COMODIN to "JÒQUER",
                CASILLA_COMODIN_DETALLE to "El rival tria la prova que et toca. Sense pietat.",
                CASILLA_TODOS to "JUGUEN TOTS",
                CASILLA_TODOS_DETALLE to
                    "La mateixa prova per a tota la taula. Cadascú que l'encerti avança " +
                    "una casella.",
                CASILLA_META_AVISO to "Prova final: només es guanya superant-la.",
                COMODIN_TITULO to "Casella jòquer",
                COMODIN_ELIGE to "%1\$s tria la prova",
                PRUEBA_FINAL to "🏁  PROVA FINAL",
                PRUEBA_JUEGAN_TODOS to "👥  JUGUEN TOTS",
                PRUEBA_LE_TOCA_ACTUAR_A to "LI TOCA ACTUAR A",
                PRUEBA_QUIEN_DECIDA to "qui decideixi %1\$s",
                PRUEBA_SOLO_MIRE_ESA_PERSONA to "👀  Que només miri aquesta persona la pantalla",
                PRUEBA_MIRA_TU_MOVIL to "📱  Mira el teu mòbil: la paraula t'ha arribat allà",
                PRUEBA_SEGUNDOS to "⏱  %1\$d segons",
                PRUEBA_CUANDO_TEMA to "En quin any?",
                PRUEBA_CUANDO_RESPUESTA to "Va passar el %1\$d.",
                PRUEBA_ACIERTOS_DE to "%1\$d de %2\$d",
                PRUEBA_SALTAR to "SALTA",
                PRUEBA_ACERTADA to "✓  ENCERTADA",
                PRUEBA_FALLADA to "✗  FALLADA",
                PRUEBA_PROHIBIDA to "🚫  PROHIBIDA",
                PRUEBA_TERMINAR to "Acaba la prova",
                PRUEBA_ACIERTOS to "✔  %1\$d",
                PRUEBA_VEREDICTO_TITULO to "Ho ha aconseguit?",
                PRUEBA_VEREDICTO_LOGRADO to "✓  SÍ!",
                PRUEBA_VEREDICTO_NO_LOGRADO to "✗  NO",
                PRUEBA_VEREDICTO_DECIDE_MESA to "Ho decideix la resta de la taula, no l'aplicació.",
                PRUEBA_VEREDICTO_DECIDE_SOLO to "Aquí no hi ha ningú mirant: sigues honest amb tu.",
                PRUEBA_BORRAR_DIBUJO to "Esborra-ho tot",
                PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA to "NOMÉS LA MIRA QUI DIBUIXA",
                PRUEBA_DIBUJO_TIEMPO_AL_PULSAR to "El temps comença a comptar quan premis el botó.",
                PRUEBA_DIBUJO_EMPEZAR to "🎨   COMENÇA A DIBUIXAR",
                PRUEBA_DIBUJO_LIENZO_VACIO to "Dibuixa aquí amb el dit",
                PRUEBA_DIBUJO_ESPIAR to "Mantén premut per tornar a veure la paraula",
                PRUEBA_DIBUJO_GOMA to "Goma d'esborrar",
                PRUEBA_DESHACER to "Desfés",
                PRUEBA_PINCEL to "Gruix",
                PRUEBA_COLOR to "Color",
                PRUEBA_RETO_OBJETIVO to "Cal arribar a %1\$d",
                PRUEBA_RETO_LLEVAMOS to "Portem",
                PRUEBA_RETO_UNA_MAS to "+1  UNA ALTRA!",
                PRUEBA_RETO_CONSEGUIDO to "ACONSEGUIT!",
                PRUEBA_RETO_TOCA_PARA_SUMAR to "de %1\$d  ·  toca per sumar",
                PRUEBA_RETO_RENDIRSE to "ENS RENDIM",
                PRUEBA_RETO_NOTA to "Cada resposta val una vegada. Si es repeteix, no compta.",
                PRUEBA_ORDENA_AYUDA to "Toca'ls en l'ordre correcte, del primer a l'últim.",
                PRUEBA_ORDENA_COMPROBAR to "COMPROVA",
                PRUEBA_ORDENA_CORRECTO to "L'ordre correcte era:",
                PRUEBA_VF_VERDADERO to "VERITAT",
                PRUEBA_VF_FALSO to "FALS",
                PRUEBA_VF_ERA_VERDAD to "Era veritat",
                PRUEBA_VF_ERA_MENTIRA to "Era mentida",
                PRUEBA_EMOJIS_AYUDA to "Què és això escrit amb emojis?",
                PRUEBA_EMOJIS_ERA to "Era: %1\$s",
                PRUEBA_CANTA_PISTA to "Comença per aquí",
                PRUEBA_TRABALENGUAS_AYUDA to "Digue'l sencer i sense embarbussar-te.",
                PRUEBA_DESAFIO_AYUDA to "A veure com te'n surts.",
                PRUEBA_ACENTOS_AYUDA to "Digues-ho amb aquella veu",
                PRUEBA_SONIDOS_AYUDA to "Només amb la boca",
                PRUEBA_CADENA_AYUDA to "Encadeneu paraules",
                RONDA_TODOS_PASA_MOVIL to "Passa el mòbil sense mirar la resposta de ningú.",
                RONDA_TODOS_RESPONDE to "Respon %1\$s",
                RONDA_TODOS_RESUMEN to "Qui ho ha encertat",
                RONDA_TODOS_NADIE to "Ningú. No es mou cap fitxa.",
                RONDA_TODOS_EN_TU_MOVIL to "Respon al teu propi mòbil.",
                RONDA_TODOS_ESPERANDO to "Esperant la resta…",
                RONDA_TODOS_PASAD_A to "PASSEU EL MÒBIL A",
                RONDA_TODOS_PROGRESO to "%1\$d de %2\$d. Ningú sabrà qui ho ha encertat fins al final.",
                RONDA_TODOS_SIN_RESPUESTA to "S'ha acabat el temps, sense resposta.",
                RONDA_TODOS_GUARDADA to "Resposta desada. No la digueu encara.",
                RONDA_TODOS_CORRECTA_ERA to "LA RESPOSTA CORRECTA ERA",
                RONDA_TODOS_SIN_RESPONDER to "sense resposta",
                RONDA_TODOS_VER_RESULTADOS to "MIRA ELS RESULTATS",
                RESULTADO_SUPERADA to "SUPERADA!",
                RESULTADO_NO_HA_PODIDO_SER to "NO HA POGUT SER",
                RESULTADO_FINAL_SUPERADA to "PROVA FINAL SUPERADA!",
                RESULTADO_AVANZAS_A to "Avanceu a la casella %1\$d",
                RESULTADO_TE_QUEDAS_EN to "Us quedeu a la casella %1\$d",
                RESULTADO_LLEGADA_A_META to "Heu arribat a la meta!",
                RESULTADO_SIGUIENTE_TURNO to "TORN SEGÜENT",
                RESULTADO_VER_RESULTADO to "MIRA EL RESULTAT",
                VICTORIA_TITULO to "Fi de la partida!",
                VICTORIA_GANADOR to "Guanya %1\$s",
                VICTORIA_CLASIFICACION to "Com ha quedat",
                VICTORIA_OTRA_PARTIDA to "UNA ALTRA PARTIDA",
                VICTORIA_AL_MENU to "TORNA AL MENÚ",
                VICTORIA_SOLITARIO_TITULO to "Repte acabat",
                VICTORIA_SOLITARIO_PUNTOS to "%1\$d punts",
                VICTORIA_SOLITARIO_MEJOR to "La teva millor marca: %1\$d",
                VICTORIA_SOLITARIO_RECORD to "Marca personal nova!",
                SOLITARIO_TITULO to "Repte en solitari",
                SOLITARIO_SUBTITULO to "Una tanda de proves. Tu contra el rellotge.",
                SOLITARIO_PROGRESO to "Prova %1\$d de %2\$d",
                SOLITARIO_RONDAS to "Proves",
                SOLITARIO_EMPEZAR to "COMENÇA EL REPTE",
                SOLITARIO_MEJOR_MARCA to "Millor marca: %1\$d",
                SOLITARIO_SIN_MARCA to "Encara no tens marca. Estrena-la.",
                AJUSTES_TITULO to "Configuració",
                AJUSTES_SUBTITULO to "Es desa per a la propera partida.",
                AJUSTES_APARIENCIA to "Aparença",
                AJUSTES_TEMA to "Tema",
                AJUSTES_TEMA_DETALLE to "Sis temes: tres clars i tres foscos.",
                AJUSTES_TEMA_SISTEMA to "Segueix el sistema",
                AJUSTES_IDIOMA to "Idioma",
                AJUSTES_IDIOMA_DETALLE to "Tretze idiomes disponibles.",
                AJUSTES_PARTIDA to "La partida",
                AJUSTES_RITMO to "Ritme de les proves",
                AJUSTES_RITMO_DETALLE to "Quant temps hi ha per a cada prova.",
                AJUSTES_MODALIDAD to "Modalitat de partida",
                AJUSTES_MODALIDAD_DETALLE to
                    "Quantes caselles té el tauler i quantes proves dura el repte en " +
                    "solitari.",
                AJUSTES_JUEGOS_ACTIVOS to "Jocs de la partida",
                AJUSTES_JUEGOS_ACTIVOS_DETALLE to "Treu els que no us agradin i deixaran de sortir al tauler.",
                AJUSTES_JUEGOS_CONTADOR to "%1\$d de %2\$d actius",
                AJUSTES_JUEGOS_MINIMO to "Ha de quedar com a mínim un joc actiu.",
                AJUSTES_SONIDO to "So",
                AJUSTES_SONIDO_DETALLE to "Pitos del compte enrere i avisos",
                AJUSTES_VIBRACION to "Vibració",
                AJUSTES_VIBRACION_DETALLE to "Encerts, errors i final del temps",
                AJUSTES_ANIMACIONES to "Animacions",
                AJUSTES_ANIMACIONES_DETALLE to "Apaga-les si prefereixes la interfície quieta",
                AJUSTES_DATOS to "Les teves dades",
                AJUSTES_EXPORTAR to "Exporta",
                AJUSTES_EXPORTAR_DETALLE to "Desa equips, configuració i marques en un fitxer",
                AJUSTES_IMPORTAR to "Importa",
                AJUSTES_IMPORTAR_DETALLE to "Recupera una còpia desada abans",
                AJUSTES_MAS to "Més",
                AJUSTES_APOYAR to "Dona suport al desenvolupament",
                AJUSTES_APOYAR_DETALLE to "Convida'm a un cafè si et resulta útil",
                AJUSTES_COMPARTIR to "Comparteix Funny",
                AJUSTES_COMPARTIR_DETALLE to "Passa-ho a qui creguis que ho gaudirà",
                AJUSTES_AYUDA to "Ajuda",
                AJUSTES_AYUDA_DETALLE to "Com es juga i preguntes freqüents",
                AJUSTES_TOUR to "Tour guiat",
                AJUSTES_TOUR_DETALLE to "Els divuit jocs i els tres modes, explicats",
                AJUSTES_ACERCA_DE to "Quant a",
                AJUSTES_ACERCA_DE_DETALLE to "Versió, llicències i privacitat",
                TEMA_MODO_CLARO to "Clars",
                TEMA_MODO_OSCURO to "Foscos",
                TEMA_FIESTA to "Festa",
                TEMA_NEON to "Neó",
                TEMA_MEDIANOCHE to "Mitjanit",
                TEMA_PAPEL to "Paper",
                TEMA_MENTA to "Menta",
                TEMA_ATARDECER to "Capvespre",
                IDIOMA_TITULO to "Idioma",
                IDIOMA_SEGUIR_SISTEMA to "El del mòbil",
                IDIOMA_SUBTITULO to "El canvi s'aplica a l'instant.",
                CAFE_TITULO to "Un cafè?",
                CAFE_TEXTO to
                    "Aquesta aplicació és gratuïta, sense anuncis i no recull les teves dades. Si et " +
                    "resulta útil, pots convidar-me a un cafè.",
                CAFE_BOTON to "Convida'm a un cafè · 1 €",
                CAFE_NO_VOLVER to "No ho tornis a mostrar",
                CAFE_OTRO_DISPOSITIVO to "Des d'un altre dispositiu",
                CAFE_QR_DESCRIPCION to "Codi QR amb l'enllaç per convidar a un cafè l'autor de l'aplicació",
                CAFE_ILUSTRACION_DESCRIPCION to "Dibuix d'una tassa de cafè amb vapor",
                CAFE_ENLACE_COPIADO to "Enllaç copiat",
                CAFE_GRACIAS to "Gràcies per passar-hi 🙂",
                CAFE_SIN_DESBLOQUEOS to "No canvia res dins del joc: Funny és sencera i sempre ho serà.",
                CAFE_ENTRADA_AJUSTES to "Dona suport al desenvolupament",
                CAFE_NO_DISPONIBLE to "En aquest dispositiu no està disponible.",
                COPIA_TITULO to "Còpia de les teves dades",
                COPIA_EXPORTAR_HECHO to "Còpia desada.",
                COPIA_EXPORTAR_ERROR to "No s'ha pogut desar la còpia.",
                COPIA_IMPORTAR_TITULO to "Importa una còpia",
                COPIA_IMPORTAR_AVISO to
                    "Abans de tocar res es desa una còpia del que tens ara, així que sempre " +
                    "es pot tornar enrere.",
                COPIA_IMPORTAR_FUSIONAR to "Afegeix al que ja tinc",
                COPIA_IMPORTAR_REEMPLAZAR to "Reemplaça-ho tot",
                COPIA_IMPORTAR_HECHO to "Dades importades.",
                COPIA_IMPORTAR_ERROR_FORMATO to "Aquest fitxer no sembla una còpia de Funny. No s'ha canviat res.",
                COPIA_IMPORTAR_ERROR_VERSION to
                    "Aquesta còpia és d'una versió més nova de Funny. Actualitza " +
                    "l'aplicació i torna-ho a provar.",
                COPIA_IMPORTAR_RESPALDO to "Abans s'ha desat una còpia de seguretat.",
                COPIA_CABECERA_DETALLE to "Còpia del %1\$s · Funny %2\$s",
                AYUDA_TITULO to "Ajuda",
                AYUDA_SUBTITULO to "Tot el que cal per no perdre's.",
                AYUDA_QUE_ES_TITULO to "Què és Funny?",
                AYUDA_QUE_ES_TEXTO to
                    "Un joc de festa amb divuit proves diferents. Es juga amb un mòbil que va " +
                    "passant de mà en mà o, si sou uns quants amb mòbil, connectant-los entre " +
                    "ells. No cal internet en cap moment.",
                AYUDA_COMO_SE_JUEGA_TITULO to "La partida",
                AYUDA_PARTIDA_1 to "Trieu el mode: per equips, individual o el repte en solitari.",
                AYUDA_PARTIDA_2 to "Cada equip o jugador té una fitxa i tots surten de la SORTIDA.",
                AYUDA_PARTIDA_3 to "Al teu torn es tira el dau i s'avança d'1 a 3 caselles.",
                AYUDA_PARTIDA_4 to
                    "La casella on caus decideix la prova. Si la superes, t'hi quedes; si no, " +
                    "tornes.",
                AYUDA_PARTIDA_5 to "Guanya qui arribi a la META i superi la prova final.",
                AYUDA_UN_MOVIL_TITULO to "Amb un sol mòbil",
                AYUDA_UN_MOVIL_1 to "El mòbil va passant: l'aplicació sempre diu a qui li toca.",
                AYUDA_UN_MOVIL_2 to "A mímica, tabú, dibuix, canta i desafiament només mira qui actua.",
                AYUDA_UN_MOVIL_3 to "A les de respondre, la pantalla es pot ensenyar a tothom.",
                AYUDA_VARIOS_MOVILES_TITULO to "Amb diversos mòbils",
                AYUDA_VARIOS_MOVILES_1 to
                    "Un mòbil fa de taula (el hub) i els altres s'hi connecten. No cal ni " +
                    "wifi ni dades.",
                AYUDA_VARIOS_MOVILES_2 to
                    "La paraula secreta arriba només al mòbil de qui actua, així que " +
                    "ningú la veu per error.",
                AYUDA_VARIOS_MOVILES_3 to
                    "A les caselles de «juguen tots», cadascú respon al seu mòbil a la " +
                    "vegada.",
                AYUDA_FAQ_TITULO to "Preguntes freqüents",
                AYUDA_FAQ_1_P to "Cal internet?",
                AYUDA_FAQ_1_R to
                    "No. Funny funciona sencera sense connexió, i connectar diversos mòbils fa " +
                    "servir Bluetooth i wifi directe entre ells, sense passar per cap xarxa.",
                AYUDA_FAQ_2_P to "Es pot jugar una sola persona?",
                AYUDA_FAQ_2_R to
                    "Sí: el repte en solitari és una tanda de proves seguides amb marca personal. " +
                    "Només hi entren els jocs que no necessiten públic.",
                AYUDA_FAQ_3_P to "Costa alguna cosa? Hi ha res bloquejat?",
                AYUDA_FAQ_3_R to
                    "No hi ha res bloquejat ni res per aconseguir a part. Si t'agrada, pots " +
                    "convidar-me a un cafè des de la Configuració, i això no canvia absolutament " +
                    "res dins del joc.",
                AYUDA_FAQ_4_P to "Recull dades?",
                AYUDA_FAQ_4_R to
                    "No. No hi ha analítica, ni comptes, ni publicitat. Els equips i la " +
                    "configuració es desen només al teu mòbil i en surten únicament si tu exportes " +
                    "una còpia.",
                AYUDA_FAQ_5_P to "Puc canviar els jocs que surten?",
                AYUDA_FAQ_5_R to
                    "Sí, a Configuració → Jocs de la partida. Els que treguis deixen d'aparèixer " +
                    "al tauler.",
                AYUDA_PROBLEMAS_TITULO to "Si alguna cosa falla",
                AYUDA_PROBLEMAS_TEXTO to
                    "Tanca i torna a obrir l'aplicació: la partida en curs es conserva. Si " +
                    "el problema continua, exporta les teves dades abans de reinstal·lar i " +
                    "escriu-nos explicant què passava.",
                AYUDA_ESCRIBENOS to "Escriu a l'autor",
                ACERCA_TITULO to "Quant a",
                ACERCA_VERSION to "Versió",
                ACERCA_COMPILACION to "Compilació",
                ACERCA_FECHA to "Data",
                ACERCA_COMMIT to "Commit",
                ACERCA_LICENCIA to "Llicència",
                ACERCA_LICENCIAS_TERCEROS to "Llicències de tercers",
                ACERCA_PRIVACIDAD to "Política de privacitat",
                ACERCA_CONTACTO to "Contacte",
                ACERCA_CODIGO to "Codi font",
                ACERCA_SIN_ANUNCIOS to "Sense anuncis, sense analítica i sense comptes.",
                ACERCA_AUTOR to "Ghato Studio · Brais Galdo",
                TOUR_TITULO to "Tour guiat",
                TOUR_SUBTITULO to "Els divuit jocs i els tres modes, en dos minuts.",
                TOUR_EMPEZAR to "COMENÇA EL TOUR",
                TOUR_SALTAR to "Salta",
                TOUR_ANTERIOR to "Anterior",
                TOUR_SIGUIENTE to "Següent",
                TOUR_TERMINAR to "A JUGAR!",
                TOUR_PROGRESO to "%1\$d de %2\$d",
                TOUR_BIENVENIDA_TITULO to "Benvingut a Funny",
                TOUR_BIENVENIDA_TEXTO to
                    "Divuit jocs, tres maneres de jugar i zero necessitat d'internet. En " +
                    "un parell de minuts t'ho explico tot; pots saltar-t'ho quan vulguis.",
                TOUR_MODOS_TITULO to "Tres maneres de jugar",
                TOUR_MODOS_TEXTO to
                    "Per equips és el clàssic: de 2 a 6 equips i va rotant qui actua. " +
                    "Individual és el mateix però cada persona porta la seva fitxa, de 2 a 8. I " +
                    "el repte en solitari és una tanda de proves contra el rellotge, tu sol, " +
                    "amb marca personal.",
                TOUR_TABLERO_TITULO to "El tauler",
                TOUR_TABLERO_TEXTO to
                    "Cada fitxa comença a la SORTIDA. Al teu torn tires el dau, avances d'1 a " +
                    "3 caselles i la casella on caus decideix la prova. Si la superes t'hi " +
                    "quedes; si falles tornes on eres. Guanya qui arribi a la META i superi " +
                    "la prova final.",
                TOUR_CASILLAS_TITULO to "Les caselles especials",
                TOUR_CASILLAS_TEXTO to
                    "🃏 Jòquer: el rival tria la prova que et toca.\n👥 Juguen tots: la " +
                    "mateixa prova per a tota la taula, i cadascú que l'encerta avança una " +
                    "casella.\n🏁 Meta: prova final a l'atzar. Sense superar-la no es guanya.",
                TOUR_JUEGOS_TITULO to "Els divuit jocs",
                TOUR_JUEGOS_TEXTO to "Aquests són tots. Pots desactivar els que no us agradin a la Configuració.",
                TOUR_SALON_TITULO to "Diversos mòbils a la vegada",
                TOUR_SALON_TEXTO to
                    "Un mòbil fa de taula i els altres s'hi connecten per Bluetooth o wifi " +
                    "directe, sense internet. Serveix per al que de veritat importa: la paraula " +
                    "secreta arriba només al mòbil de qui actua, i a les caselles de «juguen " +
                    "tots» cadascú respon al seu al mateix temps.",
                TOUR_AJUSTES_TITULO to "Ajusta-ho al teu gust",
                TOUR_AJUSTES_TEXTO to
                    "Sis temes, tretze idiomes, tres ritmes i quatre modalitats de partida: " +
                    "ràpida, normal, extrema i una a la teva mida. També pots desactivar " +
                    "jocs, apagar el so i la vibració, i desar o recuperar les teves dades en " +
                    "un fitxer.",
                TOUR_FINAL_TITULO to "Ja està",
                TOUR_FINAL_TEXTO to
                    "Pots tornar a veure això quan vulguis des de Configuració → Tour guiat. " +
                    "Que us ho passeu bé.",
                SALON_TITULO to "Diversos mòbils",
                SALON_SUBTITULO to "Sense internet: es connecten entre ells.",
                SALON_CREAR to "FES DE TAULA",
                SALON_CREAR_DETALLE to
                    "Aquest mòbil porta la partida i ensenya el tauler. És el que es deixa a " +
                    "la taula.",
                SALON_UNIRSE to "UNEIX-TE A UNA TAULA",
                SALON_UNIRSE_DETALLE to "Aquest mòbil es queda a la teva mà i rep les teves proves en privat.",
                SALON_TU_NOMBRE to "El teu nom",
                SALON_HUB_TITULO to "Ets la taula",
                SALON_HUB_ESPERANDO to "Esperant que es connectin…",
                SALON_HUB_CONECTADOS to "Connectats",
                SALON_HUB_EMPEZAR to "COMENÇA LA PARTIDA",
                SALON_CLIENTE_TITULO to "Buscant taula",
                SALON_CLIENTE_BUSCANDO to "Buscant taules a prop…",
                SALON_CLIENTE_SIN_SALONES to
                    "Encara no es veu cap. Que l'altre mòbil obri «Fes de taula» i " +
                    "espereu uns segons.",
                SALON_CLIENTE_CONECTANDO to "Connectant…",
                SALON_CLIENTE_CONECTADO to "Connectat",
                SALON_CLIENTE_ESPERA to "Ja hi ets. Mira la taula: la partida comença allà.",
                SALON_SALIR to "Surt del saló",
                SALON_DESCONECTADO to "S'ha perdut la connexió amb la taula.",
                SALON_ERROR_PERMISOS to "Falten permisos per buscar els mòbils del costat.",
                SALON_PEDIR_PERMISOS to "DONA PERMISOS",
                SALON_PERMISOS_EXPLICACION to
                    "Per trobar els mòbils que tens al costat, Android demana permís " +
                    "de dispositius propers i, en versions antigues, també " +
                    "d'ubicació. Funny no consulta on ets ni ho desa en cap lloc: és " +
                    "el preu que posa el sistema per fer servir Bluetooth i wifi " +
                    "directe.",
                SALON_ERROR_BLUETOOTH to "Encén el Bluetooth per poder connectar els mòbils.",
                SALON_ERROR_UBICACION to "Encén la ubicació: Android l'exigeix per buscar per Bluetooth.",
                SALON_ERROR_SERVICIOS to
                    "Aquest mòbil no té els serveis de Google que calen per connectar. " +
                    "Podeu continuar jugant passant-vos un sol mòbil.",
                SALON_COMO_FUNCIONA to "Com funciona?",
                SALON_ESTE_DISPOSITIVO to "Aquest mòbil",
                SALON_ROL_HUB to "Taula",
                SALON_ROL_MANDO to "Comandament",
                SALON_SIN_RED to "No es fa servir internet en cap moment.",
                SALON_SIN_NOMBRE to "Sense nom",
                SALON_TU_TURNO to "Et toca!",
                SALON_MIRA_EL_HUB to "Mira el mòbil de la taula.",
                JUEGO_MIMICA_NOMBRE to "Mímica",
                JUEGO_MIMICA_LEMA to "Representa-ho sense parlar",
                JUEGO_MIMICA_INSTRUCCIONES to
                    "Una persona representa la paraula amb gestos. Prohibit parlar, " +
                    "fer sorolls o assenyalar objectes de la sala.",
                JUEGO_DIBUJO_NOMBRE to "Dibuix",
                JUEGO_DIBUJO_LEMA to "Dibuixa-ho a la pantalla",
                JUEGO_DIBUJO_INSTRUCCIONES to
                    "Una persona dibuixa a la pantalla del mòbil i els altres " +
                    "l'endevinen. Res de lletres, números ni gestos.",
                JUEGO_CUANDO_NOMBRE to "Quan?",
                JUEGO_CUANDO_LEMA to "En quin any va passar?",
                JUEGO_CUANDO_INSTRUCCIONES to
                    "Apareix un esdeveniment i quatre anys possibles. Cal decidir en " +
                    "quin va passar.",
                JUEGO_PREGUNTAS_NOMBRE to "Preguntes",
                JUEGO_PREGUNTAS_LEMA to "Cultura general",
                JUEGO_PREGUNTAS_INSTRUCCIONES to
                    "Una pregunta amb quatre respostes. Se'n tria una de sola i no " +
                    "val canviar.",
                JUEGO_TABU_NOMBRE to "Tabú",
                JUEGO_TABU_LEMA to "Descriu-ho sense dir-ho",
                JUEGO_TABU_INSTRUCCIONES to
                    "Cal descriure la paraula sense fer servir cap de les prohibides ni " +
                    "paraules de la mateixa família.",
                JUEGO_RETO_NOMBRE to "Repte ràpid",
                JUEGO_RETO_LEMA to "Enumera contra el rellotge",
                JUEGO_RETO_INSTRUCCIONES to
                    "Anar dient coses de la categoria indicada fins arribar a " +
                    "l'objectiu abans que s'acabi el temps.",
                JUEGO_EMOJIS_NOMBRE to "Emojis",
                JUEGO_EMOJIS_LEMA to "Desxifra-ho",
                JUEGO_EMOJIS_INSTRUCCIONES to
                    "Una pel·lícula, una cançó o una dita escrita només amb emojis, i " +
                    "quatre respostes possibles.",
                JUEGO_VERDADERO_FALSO_NOMBRE to "T'ho creus?",
                JUEGO_VERDADERO_FALSO_LEMA to "Veritat o mentida",
                JUEGO_VERDADERO_FALSO_INSTRUCCIONES to
                    "Quatre afirmacions rares seguides. De cadascuna cal dir " +
                    "si és veritat o mentida, i després s'explica per què.",
                JUEGO_TRABALENGUAS_NOMBRE to "Embarbussament",
                JUEGO_TRABALENGUAS_LEMA to "Digue'l sense embarbussar-te",
                JUEGO_TRABALENGUAS_INSTRUCCIONES to
                    "Cal dir l'embarbussament les vegades que demani, sencer i " +
                    "sense equivocar-se. Ho jutja la taula.",
                JUEGO_ORDENA_NOMBRE to "Ordena",
                JUEGO_ORDENA_LEMA to "Posa-ho al seu lloc",
                JUEGO_ORDENA_INSTRUCCIONES to
                    "Quatre coses desordenades i un criteri. Cal tocar-les en l'ordre " +
                    "correcte.",
                JUEGO_CANTA_NOMBRE to "Canta",
                JUEGO_CANTA_LEMA to "Continua la cançó",
                JUEGO_CANTA_INSTRUCCIONES to
                    "Surten el títol i qui la canta, i cal posar-se a cantar la " +
                    "tornada. Ho jutja la taula, amb la generositat que consideri.",
                JUEGO_DESAFIO_NOMBRE to "Desafiament",
                JUEGO_DESAFIO_LEMA to "Atreveix-te",
                JUEGO_DESAFIO_INSTRUCCIONES to
                    "Un petit repte davant de tothom. Res perillós, res humiliant: " +
                    "només ridícul del bo. Ho jutja la taula.",
                JUEGO_REFRANES_NOMBRE to "Refranys",
                JUEGO_REFRANES_LEMA to "Completa el refrany",
                JUEGO_REFRANES_INSTRUCCIONES to
                    "Surt mitja frase feta i quatre finals possibles. Els altres " +
                    "tres són finals de refranys de veritat, així que cal " +
                    "fixar-s'hi.",
                JUEGO_ANTES_NOMBRE to "Abans o després?",
                JUEGO_ANTES_LEMA to "Què va passar primer?",
                JUEGO_ANTES_INSTRUCCIONES to
                    "Dos fets i cal dir quin va ser abans. Al resoldre es veuen els " +
                    "dos anys, que és la part que ensenya alguna cosa.",
                JUEGO_ANAGRAMAS_NOMBRE to "Anagrames",
                JUEGO_ANAGRAMAS_LEMA to "Ordena les lletres",
                JUEGO_ANAGRAMAS_INSTRUCCIONES to
                    "Unes lletres barrejades i quatre paraules candidates. Les " +
                    "tres falses fan servir gairebé les mateixes lletres, així que " +
                    "comptar no serveix.",
                JUEGO_ACENTOS_NOMBRE to "Veus",
                JUEGO_ACENTOS_LEMA to "Digues-ho amb aquella veu",
                JUEGO_ACENTOS_INSTRUCCIONES to
                    "Cal dir una frase amb la veu que digui la carta: un " +
                    "presentador, un robot sense bateria, un dolent de dibuixos. " +
                    "Decideix la taula.",
                JUEGO_SONIDOS_NOMBRE to "Sons",
                JUEGO_SONIDOS_LEMA to "Imita-ho amb la boca",
                JUEGO_SONIDOS_INSTRUCCIONES to
                    "Imitar un soroll fent servir només la boca. Res de palmes, ni " +
                    "cops a la taula, ni objectes: amb les mans perd la gràcia.",
                JUEGO_CADENA_NOMBRE to "Encadenats",
                JUEGO_CADENA_LEMA to "Paraula rere paraula",
                JUEGO_CADENA_INSTRUCCIONES to
                    "Cada paraula comença per l'última síl·laba de l'anterior. El " +
                    "comptador el porteu vosaltres, i discutir si val és part del " +
                    "joc.",
                RITMO_RAPIDO to "Ràpid",
                RITMO_NORMAL to "Normal",
                RITMO_TRANQUILO to "Tranquil",
                MODALIDAD_RAPIDA to "Partida ràpida",
                MODALIDAD_NORMAL to "Partida normal",
                MODALIDAD_EXTREMA to "Partida extrema",
                MODALIDAD_PERSONALIZADA to "A la meva manera",
                MODALIDAD_RAPIDA_DETALLE to "Per jugar-ne una i prou, o per provar el joc",
                MODALIDAD_NORMAL_DETALLE to "La de sempre, la que va millor",
                MODALIDAD_EXTREMA_DETALLE to "Per a tota una nit, sense presses",
                MODALIDAD_PERSONALIZADA_DETALLE to "Els números els poses tu",
                MODALIDAD_RESUMEN to "%1\$d caselles · %2\$d proves · uns %3\$d min",
                MODALIDAD_CASILLAS to "Caselles fins a la meta",
                MODALIDAD_PRUEBAS to "Proves per partida",
                MODALIDAD_PRUEBAS_NOTA to "Només compten en el repte en solitari",
                A11Y_DADO to "Dau: %1\$d",
                A11Y_FICHA to "Fitxa de %1\$s a la casella %2\$d",
                A11Y_CASILLA to "Casella %1\$d, %2\$s",
                A11Y_VOLVER to "Torna a la pantalla anterior",
                A11Y_CERRAR to "Tanca",
                A11Y_LIENZO_DIBUJO to "Llenç per dibuixar amb el dit",
                A11Y_TEMA_MUESTRA to "Mostra de colors del tema %1\$s",
                A11Y_BANDERA_IDIOMA to "Idioma %1\$s",
                A11Y_REDUCIR to "Reduir",
                A11Y_AUMENTAR to "Augmentar",
            ),
        plurales =
            mapOf(
                ClavePlural.CASILLAS to
                    mapOf(
                        CategoriaPlural.ONE to "%d casella",
                        CategoriaPlural.OTHER to "%d caselles",
                    ),
                ClavePlural.SEGUNDOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d segon",
                        CategoriaPlural.OTHER to "%d segons",
                    ),
                ClavePlural.DISPOSITIVOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d mòbil connectat",
                        CategoriaPlural.OTHER to "%d mòbils connectats",
                    ),
                ClavePlural.PUNTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d punt",
                        CategoriaPlural.OTHER to "%d punts",
                    ),
                ClavePlural.ACIERTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d encert",
                        CategoriaPlural.OTHER to "%d encerts",
                    ),
                ClavePlural.REPETICIONES to
                    mapOf(
                        CategoriaPlural.ONE to "%d vegada",
                        CategoriaPlural.OTHER to "%d vegades",
                    ),
            ),
    )
