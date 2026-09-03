package es.ghatostudio.funny.ui.i18n

import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.dominio.textos.Clave.*

/**
 * Catálogo en euskera.
 *
 * Nota sobre la donación: se evita «erosi» y «ordaindu», igual que en los demás
 * idiomas. Se usa «kafe bat gonbidatu», que dice lo mismo sin enmarcarlo como una
 * compra. Lo comprueba `PruebaCatalogos`.
 */
internal val catalogoEuskera =
    Catalogo(
        idioma = Idioma.EUSKERA,
        textos =
            mapOf<Clave, String>(
                ACCION_VOLVER to "Atzera",
                ACCION_CERRAR to "Itxi",
                ACCION_CANCELAR to "Utzi",
                ACCION_ACEPTAR to "Ados",
                ACCION_CONTINUAR to "Jarraitu",
                ACCION_EMPEZAR to "Hasi gara!",
                ACCION_LISTO to "Prest",
                ACCION_BORRAR to "Ezabatu",
                ACCION_ANADIR to "Gehitu",
                ACCION_REINTENTAR to "Saiatu berriro",
                ACCION_COPIAR to "Kopiatu lotura",
                ACCION_COMPARTIR to "Partekatu",
                ACCION_AHORA_NO to "Orain ez",
                ACCION_SI to "Bai",
                ACCION_NO to "Ez",
                ESTADO_CARGANDO to "Kargatzen…",
                ESTADO_SIN_CONTENIDO to "Joko honek ez du edukirik.",
                APP_LEMA to "Zuen mugikorretan sartzen den festa-jokoa",
                MENU_JUGAR to "JOLASTU",
                MENU_SEGUIR_PARTIDA to "PARTIDAREKIN JARRAITU",
                MENU_PARTIDA_NUEVA to "PARTIDA BERRIA",
                MENU_COMO_JUGAR to "NOLA JOLASTU",
                MENU_AJUSTES to "EZARPENAK",
                MENU_SALON to "MUGIKOR BATEN BAINO GEHIAGOREKIN",
                MENU_TOUR to "IKUSI BISITALDIA",
                MODO_TITULO to "Nola jolastuko dugu?",
                MODO_SUBTITULO to "Partida berri bakoitzean alda daiteke.",
                MODO_EQUIPOS to "Taldeka",
                MODO_EQUIPOS_DETALLE to
                    "2 eta 6 talde artean. Talde bakoitzak bere fitxa du eta nork antzezten " +
                    "duen txandaka doa. Modu klasikoa da, eta zaratatsuena.",
                MODO_INDIVIDUAL to "Banaka",
                MODO_INDIVIDUAL_DETALLE to
                    "2 eta 8 pertsona artean, bakoitza bere fitxarekin eta talderik " +
                    "gabe. Antzezteko probetan, txanda duenak antzezten du eta " +
                    "gainerakoek asmatzen.",
                MODO_SOLITARIO to "Bakarkako erronka",
                MODO_SOLITARIO_DETALLE to
                    "Zu erlojuaren aurka: proba sail bat jarraian eta hausteko marka " +
                    "pertsonal bat. Taularik gabe eta inor aurrean gabe jolas daitezkeen " +
                    "jokoekin bakarrik.",
                PARTICIPANTES_TITULO_EQUIPOS to "Taldeak",
                PARTICIPANTES_TITULO_INDIVIDUAL to "Jokalariak",
                PARTICIPANTES_TITULO_SOLITARIO to "Nola duzu izena?",
                PARTICIPANTES_SUBTITULO_EQUIPOS to
                    "%1\$d eta %2\$d talde artean. Apuntatu nor jolasten den " +
                    "bakoitzean eta mugikorrak esango du nori dagokio antzeztea.",
                PARTICIPANTES_SUBTITULO_INDIVIDUAL to "%1\$d eta %2\$d pertsona artean, bakoitza bere fitxarekin.",
                PARTICIPANTES_SUBTITULO_SOLITARIO to
                    "Markan zure izena jartzeko bakarrik. Honetatik ezer ez da " +
                    "mugikorretik ateratzen.",
                PARTICIPANTES_ANADIR_EQUIPO to "+  GEHITU TALDEA",
                PARTICIPANTES_ANADIR_JUGADOR to "+  GEHITU JOKALARIA",
                PARTICIPANTES_NUEVO_JUGADOR to "Gehitu jokalaria…",
                PARTICIPANTES_SIN_JUGADORES to "Izenik apuntatu gabe: jokoak taldearen izena bakarrik esango du.",
                PARTICIPANTES_NOMBRE_EQUIPO to "Taldearen izena",
                PARTICIPANTES_NOMBRE_JUGADOR to "Izena",
                PARTICIPANTES_QUITAR to "Kendu",
                PARTICIPANTES_EQUIPO_POR_DEFECTO to "%1\$d. taldea",
                PARTICIPANTES_JUGADOR_POR_DEFECTO to "%1\$d. jokalaria",
                PARTICIPANTES_EQUIPO_1_POR_DEFECTO to "Onenak",
                PARTICIPANTES_EQUIPO_2_POR_DEFECTO to "Piztiak",
                PARTICIPANTES_DESDE_SALON to "Beste mugikor batetik batu da",
                TABLERO_TIRAR to "BOTA DADOA",
                TABLERO_TURNO_DE to "%1\$s-EN TXANDA",
                TABLERO_LE_TOCA to "%1\$s-i dagokio",
                TABLERO_CASILLA to "%1\$d. laukia",
                TABLERO_SALIDA to "IRTEERA",
                TABLERO_META to "HELMUGA",
                TABLERO_MARCADOR to "Markagailua",
                TABLERO_ABANDONAR to "Utzi partida",
                TABLERO_ABANDONAR_PREGUNTA to "Partida honen aurrerapena galduko da. Ziur zaude irten nahi duzula?",
                TABLERO_ESPERANDO_HUB to "Partida daraman mugikorraren zain…",
                TABLERO_AVANZA_CASILLAS to "%1\$d aurreratzen du",
                CASILLA_COMODIN to "KOMODINA",
                CASILLA_COMODIN_DETALLE to "Aurkariak aukeratzen du zuri zer proba dagokizu. Erruki gabe.",
                CASILLA_TODOS to "DENAK JOLASTEN",
                CASILLA_TODOS_DETALLE to
                    "Proba bera mahai osoarentzat. Asmatzen duen bakoitzak lauki bat " +
                    "aurreratzen du.",
                CASILLA_META_AVISO to "Azken proba: gainditzen bakarrik irabazten da.",
                COMODIN_TITULO to "Komodin-laukia",
                COMODIN_ELIGE to "%1\$s-k proba aukeratzen du",
                PRUEBA_FINAL to "🏁  AZKEN PROBA",
                PRUEBA_JUEGAN_TODOS to "👥  DENAK JOLASTEN",
                PRUEBA_LE_TOCA_ACTUAR_A to "ANTZEZTEA DAGOKIO",
                PRUEBA_QUIEN_DECIDA to "%1\$s-k erabakitzen duenari",
                PRUEBA_SOLO_MIRE_ESA_PERSONA to "👀  Pertsona honek bakarrik begiratu behar du pantaila",
                PRUEBA_MIRA_TU_MOVIL to "📱  Begiratu zure mugikorra: hitza hara joan da",
                PRUEBA_SEGUNDOS to "⏱  %1\$d segundo",
                PRUEBA_CUANDO_TEMA to "Zein urtean?",
                PRUEBA_CUANDO_RESPUESTA to "%1\$d urtean gertatu zen.",
                PRUEBA_ACIERTOS_DE to "%2\$d-tik %1\$d",
                PRUEBA_SALTAR to "SALTATU",
                PRUEBA_ACERTADA to "✓  ASMATUTA",
                PRUEBA_FALLADA to "✗  HUTS",
                PRUEBA_PROHIBIDA to "🚫  DEBEKATUA",
                PRUEBA_TERMINAR to "Amaitu proba",
                PRUEBA_ACIERTOS to "✔  %1\$d",
                PRUEBA_VEREDICTO_TITULO to "Lortu du?",
                PRUEBA_VEREDICTO_LOGRADO to "✓  BAI!",
                PRUEBA_VEREDICTO_NO_LOGRADO to "✗  EZ",
                PRUEBA_VEREDICTO_DECIDE_MESA to "Mahaiak erabakitzen du, ez aplikazioak.",
                PRUEBA_VEREDICTO_DECIDE_SOLO to "Hemen ez dago inor begira: izan zaitez zintzoa zeurekin.",
                PRUEBA_BORRAR_DIBUJO to "Ezabatu dena",
                PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA to "MARRAZTEN DUENAK BAKARRIK BEGIRATZEN DU",
                PRUEBA_DIBUJO_TIEMPO_AL_PULSAR to "Denbora botoia sakatzean hasten da.",
                PRUEBA_DIBUJO_EMPEZAR to "🎨   HASI MARRAZTEN",
                PRUEBA_DIBUJO_LIENZO_VACIO to "Marraztu hemen hatzarekin",
                PRUEBA_DIBUJO_ESPIAR to "Sakatuta mantendu hitza berriro ikusteko",
                PRUEBA_DIBUJO_GOMA to "Borragoma",
                PRUEBA_DESHACER to "Desegin",
                PRUEBA_PINCEL to "Lodiera",
                PRUEBA_COLOR to "Kolorea",
                PRUEBA_RETO_OBJETIVO to "%1\$d-ra iritsi behar da",
                PRUEBA_RETO_LLEVAMOS to "Orain arte",
                PRUEBA_RETO_UNA_MAS to "+1  BESTE BAT!",
                PRUEBA_RETO_CONSEGUIDO to "LORTUTA!",
                PRUEBA_RETO_TOCA_PARA_SUMAR to "%1\$d-tik  ·  sakatu batzeko",
                PRUEBA_RETO_RENDIRSE to "AMORE EMATEN DUGU",
                PRUEBA_RETO_NOTA to "Erantzun bakoitza behin balio du. Errepikatzen bada, ez du kontatzen.",
                PRUEBA_ORDENA_AYUDA to "Sakatu ordena zuzenean, lehenengotik azkenera.",
                PRUEBA_ORDENA_COMPROBAR to "EGIAZTATU",
                PRUEBA_ORDENA_CORRECTO to "Ordena zuzena hau zen:",
                PRUEBA_VF_VERDADERO to "EGIA",
                PRUEBA_VF_FALSO to "GEZURRA",
                PRUEBA_VF_ERA_VERDAD to "Egia zen",
                PRUEBA_VF_ERA_MENTIRA to "Gezurra zen",
                PRUEBA_EMOJIS_AYUDA to "Zer da hau, emojiekin idatzita?",
                PRUEBA_EMOJIS_ERA to "Hau zen: %1\$s",
                PRUEBA_CANTA_PISTA to "Hemendik hasi",
                PRUEBA_TRABALENGUAS_AYUDA to "Esan osorik eta trabatu gabe.",
                PRUEBA_DESAFIO_AYUDA to "Ikusiko dugu nola ateratzen zaren.",
                PRUEBA_ACENTOS_AYUDA to "Esan ahots horrekin",
                PRUEBA_SONIDOS_AYUDA to "Ahoarekin bakarrik",
                PRUEBA_CADENA_AYUDA to "Kateatu hitzak",
                RONDA_TODOS_PASA_MOVIL to "Pasatu mugikorra inoren erantzuna begiratu gabe.",
                RONDA_TODOS_RESPONDE to "%1\$s-k erantzuten du",
                RONDA_TODOS_RESUMEN to "Nork asmatu du",
                RONDA_TODOS_NADIE to "Inork ez. Ez da fitxarik mugitzen.",
                RONDA_TODOS_EN_TU_MOVIL to "Erantzun zure mugikorrean.",
                RONDA_TODOS_ESPERANDO to "Gainerakoen zain…",
                RONDA_TODOS_PASAD_A to "PASATU MUGIKORRA HONI",
                RONDA_TODOS_PROGRESO to "%2\$d-tik %1\$d. Inork ez du jakingo nork asmatu duen amaierara arte.",
                RONDA_TODOS_SIN_RESPUESTA to "Denbora amaitu da, erantzunik gabe.",
                RONDA_TODOS_GUARDADA to "Erantzuna gordeta. Ez esan oraindik.",
                RONDA_TODOS_CORRECTA_ERA to "ERANTZUN ZUZENA HAU ZEN",
                RONDA_TODOS_SIN_RESPONDER to "erantzunik gabe",
                RONDA_TODOS_VER_RESULTADOS to "IKUSI EMAITZAK",
                RESULTADO_SUPERADA to "GAINDITUTA!",
                RESULTADO_NO_HA_PODIDO_SER to "ORAINGOAN EZ",
                RESULTADO_FINAL_SUPERADA to "AZKEN PROBA GAINDITUTA!",
                RESULTADO_AVANZAS_A to "%1\$d. laukira aurreratzen duzue",
                RESULTADO_TE_QUEDAS_EN to "%1\$d. laukian geratzen zarete",
                RESULTADO_LLEGADA_A_META to "Helmugara iritsi zarete!",
                RESULTADO_SIGUIENTE_TURNO to "HURRENGO TXANDA",
                RESULTADO_VER_RESULTADO to "IKUSI EMAITZA",
                VICTORIA_TITULO to "Partidaren amaiera!",
                VICTORIA_GANADOR to "%1\$s-k irabazi du",
                VICTORIA_CLASIFICACION to "Nola geratu da",
                VICTORIA_OTRA_PARTIDA to "BESTE PARTIDA BAT",
                VICTORIA_AL_MENU to "ITZULI MENURA",
                VICTORIA_SOLITARIO_TITULO to "Erronka amaituta",
                VICTORIA_SOLITARIO_PUNTOS to "%1\$d puntu",
                VICTORIA_SOLITARIO_MEJOR to "Zure marka onena: %1\$d",
                VICTORIA_SOLITARIO_RECORD to "Marka pertsonal berria!",
                SOLITARIO_TITULO to "Bakarkako erronka",
                SOLITARIO_SUBTITULO to "Proba sail bat. Zu erlojuaren aurka.",
                SOLITARIO_PROGRESO to "%2\$d-tik %1\$d. proba",
                SOLITARIO_RONDAS to "Probak",
                SOLITARIO_EMPEZAR to "HASI ERRONKA",
                SOLITARIO_MEJOR_MARCA to "Marka onena: %1\$d",
                SOLITARIO_SIN_MARCA to "Oraindik ez duzu markarik. Estreinatu ezazu.",
                AJUSTES_TITULO to "Ezarpenak",
                AJUSTES_SUBTITULO to "Hurrengo partidarako gordetzen dira.",
                AJUSTES_APARIENCIA to "Itxura",
                AJUSTES_TEMA to "Gaia",
                AJUSTES_TEMA_DETALLE to "Sei gai: hiru argi eta hiru ilun.",
                AJUSTES_TEMA_SISTEMA to "Sistemari jarraitu",
                AJUSTES_IDIOMA to "Hizkuntza",
                AJUSTES_IDIOMA_DETALLE to "Hamahiru hizkuntza eskuragarri.",
                AJUSTES_PARTIDA to "Partida",
                AJUSTES_RITMO to "Proben erritmoa",
                AJUSTES_RITMO_DETALLE to "Zenbat denbora dago proba bakoitzerako.",
                AJUSTES_MODALIDAD to "Partida modua",
                AJUSTES_MODALIDAD_DETALLE to
                    "Zenbat lauki dituen taulak eta zenbat proba dituen bakarkako " +
                    "erronkak.",
                AJUSTES_JUEGOS_ACTIVOS to "Partidako jokoak",
                AJUSTES_JUEGOS_ACTIVOS_DETALLE to
                    "Kendu gustatzen ez zaizkizunak eta taulan ateratzeari utziko " +
                    "diote.",
                AJUSTES_JUEGOS_CONTADOR to "%2\$d-tik %1\$d aktibo",
                AJUSTES_JUEGOS_MINIMO to "Joko bat gutxienez aktibo geratu behar da.",
                AJUSTES_SONIDO to "Soinua",
                AJUSTES_SONIDO_DETALLE to "Atzera-kontaketaren tuten eta oharrak",
                AJUSTES_VIBRACION to "Bibrazioa",
                AJUSTES_VIBRACION_DETALLE to "Asmatzeak, hutsegiteak eta denboraren amaiera",
                AJUSTES_ANIMACIONES to "Animazioak",
                AJUSTES_ANIMACIONES_DETALLE to "Itzali interfazea geldi nahi baduzu",
                AJUSTES_DATOS to "Zure datuak",
                AJUSTES_EXPORTAR to "Esportatu",
                AJUSTES_EXPORTAR_DETALLE to "Taldeak, ezarpenak eta markak fitxategi batean gordetzen ditu",
                AJUSTES_IMPORTAR to "Inportatu",
                AJUSTES_IMPORTAR_DETALLE to "Lehen gordetako kopia bat berreskuratzen du",
                AJUSTES_MAS to "Gehiago",
                AJUSTES_APOYAR to "Lagundu garapenean",
                AJUSTES_APOYAR_DETALLE to "Kafe bat gonbidatu erabilgarria iruditzen bazaizu",
                AJUSTES_COMPARTIR to "Partekatu Funny",
                AJUSTES_COMPARTIR_DETALLE to "Pasatu gozatuko duela uste duzun norbaiti",
                AJUSTES_AYUDA to "Laguntza",
                AJUSTES_AYUDA_DETALLE to "Nola jolastu eta ohiko galderak",
                AJUSTES_TOUR to "Bisitaldi gidatua",
                AJUSTES_TOUR_DETALLE to "Hemezortzi jokoak eta hiru moduak, azalduta",
                AJUSTES_ACERCA_DE to "Honi buruz",
                AJUSTES_ACERCA_DE_DETALLE to "Bertsioa, lizentziak eta pribatutasuna",
                TEMA_MODO_CLARO to "Argiak",
                TEMA_MODO_OSCURO to "Ilunak",
                TEMA_FIESTA to "Festa",
                TEMA_NEON to "Neoia",
                TEMA_MEDIANOCHE to "Gauerdia",
                TEMA_PAPEL to "Papera",
                TEMA_MENTA to "Menda",
                TEMA_ATARDECER to "Ilunabarra",
                IDIOMA_TITULO to "Hizkuntza",
                IDIOMA_SEGUIR_SISTEMA to "Mugikorrarena",
                IDIOMA_SUBTITULO to "Aldaketa berehala aplikatzen da.",
                CAFE_TITULO to "Kafe bat?",
                CAFE_TEXTO to
                    "Aplikazio hau doakoa da, iragarkirik gabe, eta ez ditu zure datuak biltzen. " +
                    "Erabilgarria iruditzen bazaizu, kafe bat gonbidatu didazu.",
                CAFE_BOTON to "Kafe bat gonbidatu · 1 €",
                CAFE_NO_VOLVER to "Ez erakutsi berriro",
                CAFE_OTRO_DISPOSITIVO to "Beste gailu batetik",
                CAFE_QR_DESCRIPCION to "QR kodea aplikazioaren egileari kafe bat gonbidatzeko loturarekin",
                CAFE_ILUSTRACION_DESCRIPCION to "Kafe-katilu baten marrazkia lurrunarekin",
                CAFE_ENLACE_COPIADO to "Lotura kopiatuta",
                CAFE_GRACIAS to "Eskerrik asko hara pasatzeagatik 🙂",
                CAFE_SIN_DESBLOQUEOS to "Ez du ezer aldatzen jokoan: Funny osoa da eta beti izango da.",
                CAFE_ENTRADA_AJUSTES to "Lagundu garapenean",
                CAFE_NO_DISPONIBLE to "Gailu honetan ez dago eskuragarri.",
                COPIA_TITULO to "Zure datuen kopia",
                COPIA_EXPORTAR_HECHO to "Kopia gordeta.",
                COPIA_EXPORTAR_ERROR to "Ezin izan da kopia gorde.",
                COPIA_IMPORTAR_TITULO to "Inportatu kopia bat",
                COPIA_IMPORTAR_AVISO to
                    "Ezer ukitu aurretik orain duzunaren kopia bat gordetzen da, beraz beti " +
                    "atzera egin daiteke.",
                COPIA_IMPORTAR_FUSIONAR to "Gehitu dudanari",
                COPIA_IMPORTAR_REEMPLAZAR to "Ordezkatu dena",
                COPIA_IMPORTAR_HECHO to "Datuak inportatuta.",
                COPIA_IMPORTAR_ERROR_FORMATO to "Fitxategi hori ez du Funny kopia baten itxura. Ez da ezer aldatu.",
                COPIA_IMPORTAR_ERROR_VERSION to
                    "Kopia hori Funny bertsio berriago batekoa da. Eguneratu " +
                    "aplikazioa eta saiatu berriro.",
                COPIA_IMPORTAR_RESPALDO to "Aurretik segurtasun-kopia bat gorde da.",
                COPIA_CABECERA_DETALLE to "%1\$s-eko kopia · Funny %2\$s",
                AYUDA_TITULO to "Laguntza",
                AYUDA_SUBTITULO to "Inor gal ez dadin behar den guztia.",
                AYUDA_QUE_ES_TITULO to "Zer da Funny?",
                AYUDA_QUE_ES_TEXTO to
                    "Hemezortzi proba desberdin dituen festa-jokoa. Esku batetik bestera " +
                    "pasatzen den mugikor batekin jolasten da edo, mugikorra duten bat baino " +
                    "gehiago bazarete, haien artean konektatuz. Ez da inoiz internet behar.",
                AYUDA_COMO_SE_JUEGA_TITULO to "Partida",
                AYUDA_PARTIDA_1 to "Aukeratu modua: taldeka, banaka edo bakarkako erronka.",
                AYUDA_PARTIDA_2 to "Talde edo jokalari bakoitzak fitxa bat du eta denak IRTEERAtik ateratzen dira.",
                AYUDA_PARTIDA_3 to "Zure txandan dadoa botatzen da eta 1etik 3ra lauki aurreratzen da.",
                AYUDA_PARTIDA_4 to
                    "Erortzen zaren laukiak erabakitzen du proba. Gainditzen baduzu, hor " +
                    "geratzen zara; bestela, atzera.",
                AYUDA_PARTIDA_5 to "HELMUGAra iritsi eta azken proba gainditzen duenak irabazten du.",
                AYUDA_UN_MOVIL_TITULO to "Mugikor bakarrarekin",
                AYUDA_UN_MOVIL_1 to "Mugikorra pasatzen doa: aplikazioak beti esaten du nori dagokio.",
                AYUDA_UN_MOVIL_2 to
                    "Mimikan, tabuan, marrazkian, kantuan eta erronketan antzezten duenak " +
                    "bakarrik begiratzen du.",
                AYUDA_UN_MOVIL_3 to "Erantzuteko probetan, pantaila denei erakutsi daiteke.",
                AYUDA_VARIOS_MOVILES_TITULO to "Mugikor bat baino gehiagorekin",
                AYUDA_VARIOS_MOVILES_1 to
                    "Mugikor batek mahaia egiten du (hub-a) eta besteak hari konektatzen " +
                    "dira. Ez da wifirik ez daturik behar.",
                AYUDA_VARIOS_MOVILES_2 to
                    "Hitz sekretua antzezten duenaren mugikorrera bakarrik iristen da, " +
                    "beraz inork ez du akatsez ikusten.",
                AYUDA_VARIOS_MOVILES_3 to
                    "«Denak jolasten» laukietan, bakoitzak bere mugikorrean erantzuten du " +
                    "aldi berean.",
                AYUDA_FAQ_TITULO to "Ohiko galderak",
                AYUDA_FAQ_1_P to "Internet behar da?",
                AYUDA_FAQ_1_R to
                    "Ez. Funny osorik konexiorik gabe dabil, eta mugikor bat baino gehiago " +
                    "konektatzeak Bluetooth eta wifi zuzena erabiltzen ditu haien artean, inolako " +
                    "sarerik zeharkatu gabe.",
                AYUDA_FAQ_2_P to "Pertsona bakar batek jolas dezake?",
                AYUDA_FAQ_2_R to
                    "Bai: bakarkako erronka proba sail bat da, jarraian, marka pertsonalarekin. " +
                    "Publikoa behar ez duten jokoak bakarrik sartzen dira.",
                AYUDA_FAQ_3_P to "Zerbait kostatzen du? Blokeatuta dago zerbait?",
                AYUDA_FAQ_3_R to
                    "Ez dago ezer blokeatuta ez aparte lortzeko ezer. Gustatzen bazaizu, kafe bat " +
                    "gonbidatu didazu Ezarpenetatik, eta horrek ez du ezertxo ere aldatzen " +
                    "jokoaren barruan.",
                AYUDA_FAQ_4_P to "Datuak biltzen ditu?",
                AYUDA_FAQ_4_R to
                    "Ez. Ez dago analitikarik, ez konturik, ez publizitaterik. Taldeak eta " +
                    "ezarpenak zure mugikorrean bakarrik gordetzen dira eta hortik ateratzen dira " +
                    "zuk kopia bat esportatzen baduzu bakarrik.",
                AYUDA_FAQ_5_P to "Ateratzen diren jokoak alda ditzaket?",
                AYUDA_FAQ_5_R to
                    "Bai, Ezarpenak → Partidako jokoak atalean. Kentzen dituzunak taulan " +
                    "agertzeari uzten diote.",
                AYUDA_PROBLEMAS_TITULO to "Zerbait huts egiten badu",
                AYUDA_PROBLEMAS_TEXTO to
                    "Itxi eta berriro ireki aplikazioa: martxan dagoen partida gordetzen " +
                    "da. Arazoak jarraitzen badu, esportatu zure datuak berriro instalatu " +
                    "aurretik eta idatzi guri zer gertatzen zen kontatuz.",
                AYUDA_ESCRIBENOS to "Idatzi egileari",
                ACERCA_TITULO to "Honi buruz",
                ACERCA_VERSION to "Bertsioa",
                ACERCA_COMPILACION to "Konpilazioa",
                ACERCA_FECHA to "Data",
                ACERCA_COMMIT to "Commit",
                ACERCA_LICENCIA to "Lizentzia",
                ACERCA_LICENCIAS_TERCEROS to "Hirugarrenen lizentziak",
                ACERCA_PRIVACIDAD to "Pribatutasun-politika",
                ACERCA_CONTACTO to "Kontaktua",
                ACERCA_CODIGO to "Iturburu-kodea",
                ACERCA_SIN_ANUNCIOS to "Iragarkirik gabe, analitikarik gabe eta konturik gabe.",
                ACERCA_AUTOR to "Ghato Studio · Brais Galdo",
                TOUR_TITULO to "Bisitaldi gidatua",
                TOUR_SUBTITULO to "Hemezortzi jokoak eta hiru moduak, bi minutuan.",
                TOUR_EMPEZAR to "HASI BISITALDIA",
                TOUR_SALTAR to "Saltatu",
                TOUR_ANTERIOR to "Aurrekoa",
                TOUR_SIGUIENTE to "Hurrengoa",
                TOUR_TERMINAR to "JOLASTERA!",
                TOUR_PROGRESO to "%2\$d-tik %1\$d",
                TOUR_BIENVENIDA_TITULO to "Ongi etorri Funny-ra",
                TOUR_BIENVENIDA_TEXTO to
                    "Hemezortzi joko, jolasteko hiru modu eta interneten beharrik ez. Bi " +
                    "minututan dena kontatzen dizut; nahi duzunean saltatu dezakezu.",
                TOUR_MODOS_TITULO to "Jolasteko hiru modu",
                TOUR_MODOS_TEXTO to
                    "Taldeka klasikoa da: 2 eta 6 talde artean eta nork antzezten duen txandaka " +
                    "doa. Banaka berdina da baina pertsona bakoitzak bere fitxa darama, 2 eta 8 " +
                    "artean. Eta bakarkako erronka proba sail bat da erlojuaren aurka, zu " +
                    "bakarrik, marka pertsonalarekin.",
                TOUR_TABLERO_TITULO to "Taula",
                TOUR_TABLERO_TEXTO to
                    "Fitxa bakoitza IRTEERAn hasten da. Zure txandan dadoa botatzen duzu, " +
                    "1etik 3ra lauki aurreratzen duzu eta erortzen zaren laukiak erabakitzen " +
                    "du proba. Gainditzen baduzu hor geratzen zara; huts egiten baduzu " +
                    "zeunden tokira itzultzen zara. HELMUGAra iritsi eta azken proba " +
                    "gainditzen duenak irabazten du.",
                TOUR_CASILLAS_TITULO to "Lauki bereziak",
                TOUR_CASILLAS_TEXTO to
                    "🃏 Komodina: aurkariak aukeratzen du zuri dagokizun proba.\n👥 Denak " +
                    "jolasten: proba bera mahai osoarentzat, eta asmatzen duen bakoitzak " +
                    "lauki bat aurreratzen du.\n🏁 Helmuga: azken proba zoriz. Gainditu gabe " +
                    "ez da irabazten.",
                TOUR_JUEGOS_TITULO to "Hemezortzi jokoak",
                TOUR_JUEGOS_TEXTO to "Hauek dira denak. Gustatzen ez zaizkizunak Ezarpenetan desaktibatu ditzakezu.",
                TOUR_SALON_TITULO to "Mugikor bat baino gehiago aldi berean",
                TOUR_SALON_TEXTO to
                    "Mugikor batek mahaia egiten du eta besteak hari konektatzen dira Bluetooth " +
                    "edo wifi zuzenaren bidez, internetik gabe. Benetan garrantzitsua dena " +
                    "ekartzen du: hitz sekretua antzezten duenaren mugikorrera bakarrik iristen " +
                    "da, eta «denak jolasten» laukietan bakoitzak bereari erantzuten dio aldi " +
                    "berean.",
                TOUR_AJUSTES_TITULO to "Zure gustura",
                TOUR_AJUSTES_TEXTO to
                    "Sei gai, hamahiru hizkuntza, hiru erritmo eta lau partida modu: azkarra, " +
                    "normala, muturrekoa eta zuk neurrira egindakoa. Jokoak ere desaktibatu " +
                    "ditzakezu, soinua eta bibrazioa itzali, eta zure datuak fitxategi batean " +
                    "gorde edo berreskuratu.",
                TOUR_FINAL_TITULO to "Hori da dena",
                TOUR_FINAL_TEXTO to
                    "Hau berriro ikus dezakezu nahi duzunean Ezarpenak → Bisitaldi gidatua " +
                    "atalean. Ondo pasa.",
                SALON_TITULO to "Mugikor bat baino gehiago",
                SALON_SUBTITULO to "Internetik gabe: haien artean konektatzen dira.",
                SALON_CREAR to "IZAN MAHAIA",
                SALON_CREAR_DETALLE to
                    "Mugikor honek partida darama eta taula erakusten du. Mahai gainean " +
                    "uzten dena da.",
                SALON_UNIRSE to "BATU MAHAI BATERA",
                SALON_UNIRSE_DETALLE to
                    "Mugikor hau zure eskuan geratzen da eta zure probak modu pribatuan " +
                    "jasotzen ditu.",
                SALON_TU_NOMBRE to "Zure izena",
                SALON_HUB_TITULO to "Zu zara mahaia",
                SALON_HUB_ESPERANDO to "Konexioen zain…",
                SALON_HUB_CONECTADOS to "Konektatuta",
                SALON_HUB_EMPEZAR to "HASI PARTIDA",
                SALON_CLIENTE_TITULO to "Mahai bat bilatzen",
                SALON_CLIENTE_BUSCANDO to "Inguruko mahaiak bilatzen…",
                SALON_CLIENTE_SIN_SALONES to
                    "Oraindik ez da ikusten bat ere. Beste mugikorrak «Izan mahaia» " +
                    "ireki dezala eta itxaron segundo batzuk.",
                SALON_CLIENTE_CONECTANDO to "Konektatzen…",
                SALON_CLIENTE_CONECTADO to "Konektatuta",
                SALON_CLIENTE_ESPERA to "Barruan zaude. Begiratu mahaia: partida han hasten da.",
                SALON_SALIR to "Irten aretotik",
                SALON_DESCONECTADO to "Mahaiarekin konexioa galdu da.",
                SALON_ERROR_PERMISOS to "Baimenak falta dira ondoko mugikorrak bilatzeko.",
                SALON_PEDIR_PERMISOS to "EMAN BAIMENAK",
                SALON_PERMISOS_EXPLICACION to
                    "Ondoan dituzun mugikorrak aurkitzeko, Android-ek inguruko " +
                    "gailuen baimena eskatzen du eta, bertsio zaharretan, kokapena " +
                    "ere. Funny-k ez du inoiz kontsultatzen non zauden ezta inon " +
                    "gordetzen: sistemak Bluetooth eta wifi zuzena erabiltzeagatik " +
                    "jartzen duen prezioa da.",
                SALON_ERROR_BLUETOOTH to "Piztu Bluetooth mugikorrak konektatu ahal izateko.",
                SALON_ERROR_UBICACION to "Piztu kokapena: Android-ek eskatzen du Bluetooth bidez bilatzeko.",
                SALON_ERROR_SERVICIOS to
                    "Mugikor honek ez ditu konektatzeko behar diren Google zerbitzuak. " +
                    "Mugikor bakarra pasatuz jolasten jarrai dezakezue.",
                SALON_COMO_FUNCIONA to "Nola dabil?",
                SALON_ESTE_DISPOSITIVO to "Mugikor hau",
                SALON_ROL_HUB to "Mahaia",
                SALON_ROL_MANDO to "Agintea",
                SALON_SIN_RED to "Internet ez da inoiz erabiltzen.",
                SALON_SIN_NOMBRE to "Izenik gabe",
                SALON_TU_TURNO to "Zuri dagokizu!",
                SALON_MIRA_EL_HUB to "Begiratu mahaiaren mugikorra.",
                JUEGO_MIMICA_NOMBRE to "Mimika",
                JUEGO_MIMICA_LEMA to "Antzeztu hitz egin gabe",
                JUEGO_MIMICA_INSTRUCCIONES to
                    "Pertsona batek hitza keinuekin antzezten du. Debekatuta hitz " +
                    "egitea, zaratak egitea edo gelako objektuak seinalatzea.",
                JUEGO_DIBUJO_NOMBRE to "Marrazkia",
                JUEGO_DIBUJO_LEMA to "Marraztu pantailan",
                JUEGO_DIBUJO_INSTRUCCIONES to
                    "Pertsona batek mugikorraren pantailan marrazten du eta besteek " +
                    "asmatzen dute. Letrarik, zenbakirik eta keinurik gabe.",
                JUEGO_CUANDO_NOMBRE to "Noiz?",
                JUEGO_CUANDO_LEMA to "Zein urtean gertatu zen?",
                JUEGO_CUANDO_INSTRUCCIONES to
                    "Gertaera bat agertzen da eta lau urte posible. Zeinetan gertatu " +
                    "zen erabaki behar da.",
                JUEGO_PREGUNTAS_NOMBRE to "Galderak",
                JUEGO_PREGUNTAS_LEMA to "Kultura orokorra",
                JUEGO_PREGUNTAS_INSTRUCCIONES to
                    "Lau erantzun dituen galdera bat. Bakarra aukeratzen da eta ez " +
                    "da balio aldatzea.",
                JUEGO_TABU_NOMBRE to "Tabu",
                JUEGO_TABU_LEMA to "Deskribatu esan gabe",
                JUEGO_TABU_INSTRUCCIONES to
                    "Hitza deskribatu behar da debekatutakoak edo familia berekoak " +
                    "diren hitzak erabili gabe.",
                JUEGO_RETO_NOMBRE to "Erronka azkarra",
                JUEGO_RETO_LEMA to "Zerrendatu erlojuaren aurka",
                JUEGO_RETO_INSTRUCCIONES to
                    "Adierazitako kategoriako gauzak esaten jarraitu helburua lortu " +
                    "arte denbora amaitu aurretik.",
                JUEGO_EMOJIS_NOMBRE to "Emojiak",
                JUEGO_EMOJIS_LEMA to "Deszifratu",
                JUEGO_EMOJIS_INSTRUCCIONES to
                    "Film bat, abesti bat edo esaera bat emojiekin bakarrik idatzita, " +
                    "eta lau erantzun posible.",
                JUEGO_VERDADERO_FALSO_NOMBRE to "Sinesten duzu?",
                JUEGO_VERDADERO_FALSO_LEMA to "Egia ala gezurra",
                JUEGO_VERDADERO_FALSO_INSTRUCCIONES to
                    "Lau baieztapen bitxi jarraian. Bakoitzaz egia ala " +
                    "gezurra den esan behar da, eta gero zergatik azaltzen " +
                    "da.",
                JUEGO_TRABALENGUAS_NOMBRE to "Aho-korapiloa",
                JUEGO_TRABALENGUAS_LEMA to "Esan trabatu gabe",
                JUEGO_TRABALENGUAS_INSTRUCCIONES to
                    "Aho-korapiloa eskatzen dituen alditan esan behar da, " +
                    "osorik eta oker gabe. Mahaiak epaitzen du.",
                JUEGO_ORDENA_NOMBRE to "Ordenatu",
                JUEGO_ORDENA_LEMA to "Jarri bere tokian",
                JUEGO_ORDENA_INSTRUCCIONES to
                    "Lau gauza desordenatuta eta irizpide bat. Ordena zuzenean sakatu " +
                    "behar dira.",
                JUEGO_CANTA_NOMBRE to "Kantatu",
                JUEGO_CANTA_LEMA to "Jarraitu abestia",
                JUEGO_CANTA_INSTRUCCIONES to
                    "Izenburua eta nork kantatzen duen ateratzen dira, eta errepika " +
                    "kantatzen hasi behar da. Mahaiak epaitzen du, nahi duen " +
                    "eskuzabaltasunarekin.",
                JUEGO_DESAFIO_NOMBRE to "Erronka",
                JUEGO_DESAFIO_LEMA to "Ausartu",
                JUEGO_DESAFIO_INSTRUCCIONES to
                    "Erronka txiki bat denen aurrean. Ezer arriskutsurik ez, ezer " +
                    "iraingarririk ez: barregarri onarena bakarrik. Mahaiak epaitzen " +
                    "du.",
                JUEGO_REFRANES_NOMBRE to "Esaerak",
                JUEGO_REFRANES_LEMA to "Osatu esaera",
                JUEGO_REFRANES_INSTRUCCIONES to
                    "Esaera baten erdia eta lau amaiera posible. Beste hirurak " +
                    "benetako esaeren amaierak dira, beraz begiratu ondo.",
                JUEGO_ANTES_NOMBRE to "Lehen ala gero?",
                JUEGO_ANTES_LEMA to "Zer gertatu zen lehenago?",
                JUEGO_ANTES_INSTRUCCIONES to
                    "Bi gertaera eta zein izan zen lehenago esan behar da. Ebaztean bi " +
                    "urteak ikusten dira, eta hori da zerbait erakusten duen zatia.",
                JUEGO_ANAGRAMAS_NOMBRE to "Anagramak",
                JUEGO_ANAGRAMAS_LEMA to "Ordenatu letrak",
                JUEGO_ANAGRAMAS_INSTRUCCIONES to
                    "Letra nahasiak eta lau hitz hautagai. Hiru okerrek ia letra " +
                    "berak erabiltzen dituzte, beraz zenbatzeak ez du balio.",
                JUEGO_ACENTOS_NOMBRE to "Ahotsak",
                JUEGO_ACENTOS_LEMA to "Esan ahots horrekin",
                JUEGO_ACENTOS_INSTRUCCIONES to
                    "Kartak eskatzen duen ahotsarekin esaldi bat esan behar da: " +
                    "aurkezle bat, bateriarik gabeko robot bat, marrazki " +
                    "bizidunetako gaizto bat. Mahaiak erabakitzen du.",
                JUEGO_SONIDOS_NOMBRE to "Soinuak",
                JUEGO_SONIDOS_LEMA to "Imitatu ahoarekin",
                JUEGO_SONIDOS_INSTRUCCIONES to
                    "Zarata bat imitatu ahoa bakarrik erabiliz. Txalorik ez, mahai " +
                    "kolperik ez, objekturik ez: eskuekin grazia galtzen du.",
                JUEGO_CADENA_NOMBRE to "Kateatuak",
                JUEGO_CADENA_LEMA to "Hitz bat bestearen atzetik",
                JUEGO_CADENA_INSTRUCCIONES to
                    "Hitz bakoitza aurrekoaren azken silabaz hasten da. Kontua zuek " +
                    "eramaten duzue, eta balio duen ala ez eztabaidatzea jokoaren " +
                    "parte da.",
                RITMO_RAPIDO to "Azkarra",
                RITMO_NORMAL to "Normala",
                RITMO_TRANQUILO to "Lasaia",
                MODALIDAD_RAPIDA to "Partida azkarra",
                MODALIDAD_NORMAL to "Partida normala",
                MODALIDAD_EXTREMA to "Partida muturrekoa",
                MODALIDAD_PERSONALIZADA to "Nire erara",
                MODALIDAD_RAPIDA_DETALLE to "Bat jokatu eta kito, edo jokoa probatzeko",
                MODALIDAD_NORMAL_DETALLE to "Betikoa, ongien datorrena",
                MODALIDAD_EXTREMA_DETALLE to "Gau osorako, presarik gabe",
                MODALIDAD_PERSONALIZADA_DETALLE to "Zenbakiak zuk jartzen dituzu",
                MODALIDAD_RESUMEN to "%1\$d lauki · %2\$d proba · %3\$d minutu inguru",
                MODALIDAD_CASILLAS to "Laukiak helmugara arte",
                MODALIDAD_PRUEBAS to "Probak partida bakoitzean",
                MODALIDAD_PRUEBAS_NOTA to "Bakarkako erronkan bakarrik zenbatzen dute",
                A11Y_DADO to "Dadoa: %1\$d",
                A11Y_FICHA to "%1\$s-en fitxa %2\$d. laukian",
                A11Y_CASILLA to "%1\$d. laukia, %2\$s",
                A11Y_VOLVER to "Itzuli aurreko pantailara",
                A11Y_CERRAR to "Itxi",
                A11Y_LIENZO_DIBUJO to "Hatzarekin marrazteko oihala",
                A11Y_TEMA_MUESTRA to "%1\$s gaiaren kolore-erakusgarria",
                A11Y_BANDERA_IDIOMA to "%1\$s hizkuntza",
                A11Y_REDUCIR to "Gutxitu",
                A11Y_AUMENTAR to "Handitu",
            ),
        plurales =
            mapOf(
                ClavePlural.CASILLAS to
                    mapOf(
                        CategoriaPlural.ONE to "lauki %d",
                        CategoriaPlural.OTHER to "%d lauki",
                    ),
                ClavePlural.SEGUNDOS to
                    mapOf(
                        CategoriaPlural.ONE to "segundo %d",
                        CategoriaPlural.OTHER to "%d segundo",
                    ),
                ClavePlural.DISPOSITIVOS to
                    mapOf(
                        CategoriaPlural.ONE to "mugikor %d konektatuta",
                        CategoriaPlural.OTHER to "%d mugikor konektatuta",
                    ),
                ClavePlural.PUNTOS to
                    mapOf(
                        CategoriaPlural.ONE to "puntu %d",
                        CategoriaPlural.OTHER to "%d puntu",
                    ),
                ClavePlural.ACIERTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d asmatuta",
                        CategoriaPlural.OTHER to "%d asmatuta",
                    ),
                ClavePlural.REPETICIONES to
                    mapOf(
                        CategoriaPlural.ONE to "aldi %d",
                        CategoriaPlural.OTHER to "%d aldiz",
                    ),
            ),
    )
