package es.ghatostudio.funny.ui.i18n

import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.dominio.textos.Clave.*

/**
 * Catálogo en italiano.
 *
 * Nota sobre la donación: se evita «comprare» y «pagare», igual que en los demás
 * idiomas. Se usa «offrimi un caffè», que dice lo mismo sin enmarcarlo como una
 * compra. Lo comprueba `PruebaCatalogos`.
 */
internal val catalogoItaliano =
    Catalogo(
        idioma = Idioma.ITALIANO,
        textos =
            mapOf<Clave, String>(
                ACCION_VOLVER to "Indietro",
                ACCION_CERRAR to "Chiudi",
                ACCION_CANCELAR to "Annulla",
                ACCION_ACEPTAR to "OK",
                ACCION_CONTINUAR to "Continua",
                ACCION_EMPEZAR to "Si parte!",
                ACCION_LISTO to "Pronto",
                ACCION_BORRAR to "Elimina",
                ACCION_ANADIR to "Aggiungi",
                ACCION_REINTENTAR to "Riprova",
                ACCION_COPIAR to "Copia il link",
                ACCION_COMPARTIR to "Condividi",
                ACCION_AHORA_NO to "Non ora",
                ACCION_SI to "Sì",
                ACCION_NO to "No",
                ESTADO_CARGANDO to "Caricamento…",
                ESTADO_SIN_CONTENIDO to "Non c'è contenuto per questo gioco.",
                APP_LEMA to "Il gioco di società che sta nei vostri telefoni",
                MENU_JUGAR to "GIOCA",
                MENU_SEGUIR_PARTIDA to "RIPRENDI LA PARTITA",
                MENU_PARTIDA_NUEVA to "NUOVA PARTITA",
                MENU_COMO_JUGAR to "COME SI GIOCA",
                MENU_AJUSTES to "IMPOSTAZIONI",
                MENU_SALON to "GIOCA CON PIÙ TELEFONI",
                MENU_TOUR to "GUARDA IL TOUR",
                MODO_TITULO to "Come giochiamo?",
                MODO_SUBTITULO to "Si può cambiare in ogni nuova partita.",
                MODO_EQUIPOS to "A squadre",
                MODO_EQUIPOS_DETALLE to
                    "Da 2 a 6 squadre. Ogni squadra ha la sua pedina e si alterna chi " +
                    "recita. Il modo classico, e il più rumoroso.",
                MODO_INDIVIDUAL to "Individuale",
                MODO_INDIVIDUAL_DETALLE to
                    "Da 2 a 8 persone, ognuna con la sua pedina e senza squadre. Nelle " +
                    "prove da recitare, gioca chi ha il turno e indovinano gli altri.",
                MODO_SOLITARIO to "Sfida in solitaria",
                MODO_SOLITARIO_DETALLE to
                    "Tu contro il cronometro: dieci prove di fila e un record personale " +
                    "da battere. Senza tabellone e solo con i giochi che funzionano senza " +
                    "pubblico.",
                PARTICIPANTES_TITULO_EQUIPOS to "Squadre",
                PARTICIPANTES_TITULO_INDIVIDUAL to "Giocatori",
                PARTICIPANTES_TITULO_SOLITARIO to "Come ti chiami?",
                PARTICIPANTES_SUBTITULO_EQUIPOS to
                    "Da %1\$d a %2\$d squadre. Scrivi chi gioca in ognuna e il " +
                    "telefono dirà a chi tocca recitare.",
                PARTICIPANTES_SUBTITULO_INDIVIDUAL to "Da %1\$d a %2\$d persone, ognuna con la sua pedina.",
                PARTICIPANTES_SUBTITULO_SOLITARIO to
                    "Solo per mettere il tuo nome sul punteggio. Niente di " +
                    "tutto questo esce dal telefono.",
                PARTICIPANTES_ANADIR_EQUIPO to "+  AGGIUNGI SQUADRA",
                PARTICIPANTES_ANADIR_JUGADOR to "+  AGGIUNGI GIOCATORE",
                PARTICIPANTES_NUEVO_JUGADOR to "Aggiungi giocatore…",
                PARTICIPANTES_SIN_JUGADORES to "Nessun nome scritto: il gioco dirà solo il nome della squadra.",
                PARTICIPANTES_NOMBRE_EQUIPO to "Nome della squadra",
                PARTICIPANTES_NOMBRE_JUGADOR to "Nome",
                PARTICIPANTES_QUITAR to "Togli",
                PARTICIPANTES_EQUIPO_POR_DEFECTO to "Squadra %1\$d",
                PARTICIPANTES_JUGADOR_POR_DEFECTO to "Giocatore %1\$d",
                PARTICIPANTES_EQUIPO_1_POR_DEFECTO to "I Campioni",
                PARTICIPANTES_EQUIPO_2_POR_DEFECTO to "Le Belve",
                PARTICIPANTES_DESDE_SALON to "Si è unito da un altro telefono",
                TABLERO_TIRAR to "TIRA IL DADO",
                TABLERO_TURNO_DE to "TURNO DI %1\$s",
                TABLERO_LE_TOCA to "Tocca a %1\$s",
                TABLERO_CASILLA to "Casella %1\$d",
                TABLERO_SALIDA to "PARTENZA",
                TABLERO_META to "ARRIVO",
                TABLERO_MARCADOR to "Punteggi",
                TABLERO_ABANDONAR to "Abbandona la partita",
                TABLERO_ABANDONAR_PREGUNTA to "Perderai i progressi di questa partita. Vuoi davvero uscire?",
                TABLERO_ESPERANDO_HUB to "In attesa del telefono che guida la partita…",
                TABLERO_AVANZA_CASILLAS to "Avanza di %1\$d",
                CASILLA_COMODIN to "JOLLY",
                CASILLA_COMODIN_DETALLE to "L'avversario sceglie la prova che ti tocca. Senza pietà.",
                CASILLA_TODOS to "GIOCANO TUTTI",
                CASILLA_TODOS_DETALLE to "La stessa prova per tutto il tavolo. Chi indovina avanza di una casella.",
                CASILLA_META_AVISO to "Prova finale: si vince solo superandola.",
                COMODIN_TITULO to "Casella jolly",
                COMODIN_ELIGE to "%1\$s sceglie la prova",
                PRUEBA_FINAL to "🏁  PROVA FINALE",
                PRUEBA_JUEGAN_TODOS to "👥  GIOCANO TUTTI",
                PRUEBA_LE_TOCA_ACTUAR_A to "TOCCA A",
                PRUEBA_QUIEN_DECIDA to "chi decide %1\$s",
                PRUEBA_SOLO_MIRE_ESA_PERSONA to "👀  Solo questa persona deve guardare lo schermo",
                PRUEBA_MIRA_TU_MOVIL to "📱  Guarda il tuo telefono: la parola è arrivata lì",
                PRUEBA_SEGUNDOS to "⏱  %1\$d secondi",
                PRUEBA_CUANDO_TEMA to "In che anno?",
                PRUEBA_CUANDO_RESPUESTA to "È successo nel %1\$d.",
                PRUEBA_ACIERTOS_DE to "%1\$d su %2\$d",
                PRUEBA_SALTAR to "SALTA",
                PRUEBA_ACERTADA to "✓  INDOVINATA",
                PRUEBA_FALLADA to "✗  SBAGLIATA",
                PRUEBA_PROHIBIDA to "🚫  VIETATA",
                PRUEBA_TERMINAR to "Termina la prova",
                PRUEBA_ACIERTOS to "✔  %1\$d",
                PRUEBA_VEREDICTO_TITULO to "Ce l'ha fatta?",
                PRUEBA_VEREDICTO_LOGRADO to "✓  SÌ!",
                PRUEBA_VEREDICTO_NO_LOGRADO to "✗  NO",
                PRUEBA_VEREDICTO_DECIDE_MESA to "Decide il tavolo, non l'app.",
                PRUEBA_VEREDICTO_DECIDE_SOLO to "Qui non ti guarda nessuno: sii onesto con te stesso.",
                PRUEBA_BORRAR_DIBUJO to "Cancella tutto",
                PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA to "GUARDA SOLO CHI DISEGNA",
                PRUEBA_DIBUJO_TIEMPO_AL_PULSAR to "Il tempo parte quando premi il pulsante.",
                PRUEBA_DIBUJO_EMPEZAR to "🎨   INIZIA A DISEGNARE",
                PRUEBA_DIBUJO_LIENZO_VACIO to "Disegna qui con il dito",
                PRUEBA_DIBUJO_ESPIAR to "Tieni premuto per rivedere la parola",
                PRUEBA_DIBUJO_GOMA to "Gomma",
                PRUEBA_DESHACER to "Annulla",
                PRUEBA_PINCEL to "Spessore",
                PRUEBA_COLOR to "Colore",
                PRUEBA_RETO_OBJETIVO to "Bisogna arrivare a %1\$d",
                PRUEBA_RETO_LLEVAMOS to "Finora",
                PRUEBA_RETO_UNA_MAS to "+1  UN'ALTRA!",
                PRUEBA_RETO_CONSEGUIDO to "FATTO!",
                PRUEBA_RETO_TOCA_PARA_SUMAR to "su %1\$d  ·  tocca per aggiungere",
                PRUEBA_RETO_RENDIRSE to "CI ARRENDIAMO",
                PRUEBA_RETO_NOTA to "Ogni risposta vale una volta. Se si ripete, non conta.",
                PRUEBA_ORDENA_AYUDA to "Toccale nell'ordine giusto, dalla prima all'ultima.",
                PRUEBA_ORDENA_COMPROBAR to "CONTROLLA",
                PRUEBA_ORDENA_CORRECTO to "L'ordine giusto era:",
                PRUEBA_VF_VERDADERO to "VERO",
                PRUEBA_VF_FALSO to "FALSO",
                PRUEBA_VF_ERA_VERDAD to "Era vero",
                PRUEBA_VF_ERA_MENTIRA to "Era falso",
                PRUEBA_EMOJIS_AYUDA to "Che cos'è, scritto con le emoji?",
                PRUEBA_EMOJIS_ERA to "Era: %1\$s",
                PRUEBA_CANTA_PISTA to "Inizia da qui",
                PRUEBA_TRABALENGUAS_AYUDA to "Dillo tutto e senza inciampare.",
                PRUEBA_DESAFIO_AYUDA to "Vediamo come te la cavi.",
                RONDA_TODOS_PASA_MOVIL to "Passa il telefono senza guardare la risposta di nessuno.",
                RONDA_TODOS_RESPONDE to "Risponde %1\$s",
                RONDA_TODOS_RESUMEN to "Chi ha indovinato",
                RONDA_TODOS_NADIE to "Nessuno. Non si muove nessuna pedina.",
                RONDA_TODOS_EN_TU_MOVIL to "Rispondi sul tuo telefono.",
                RONDA_TODOS_ESPERANDO to "In attesa degli altri…",
                RONDA_TODOS_PASAD_A to "PASSATE IL TELEFONO A",
                RONDA_TODOS_PROGRESO to "%1\$d su %2\$d. Nessuno saprà chi ha indovinato fino alla fine.",
                RONDA_TODOS_SIN_RESPUESTA to "Tempo scaduto, senza risposta.",
                RONDA_TODOS_GUARDADA to "Risposta salvata. Non dirla ancora.",
                RONDA_TODOS_CORRECTA_ERA to "LA RISPOSTA GIUSTA ERA",
                RONDA_TODOS_SIN_RESPONDER to "senza risposta",
                RONDA_TODOS_VER_RESULTADOS to "VEDI I RISULTATI",
                RESULTADO_SUPERADA to "SUPERATA!",
                RESULTADO_NO_HA_PODIDO_SER to "NON QUESTA VOLTA",
                RESULTADO_FINAL_SUPERADA to "PROVA FINALE SUPERATA!",
                RESULTADO_AVANZAS_A to "Avanzate alla casella %1\$d",
                RESULTADO_TE_QUEDAS_EN to "Restate sulla casella %1\$d",
                RESULTADO_LLEGADA_A_META to "Siete arrivati all'arrivo!",
                RESULTADO_SIGUIENTE_TURNO to "TURNO SUCCESSIVO",
                RESULTADO_VER_RESULTADO to "VEDI IL RISULTATO",
                VICTORIA_TITULO to "Fine della partita!",
                VICTORIA_GANADOR to "Vince %1\$s",
                VICTORIA_CLASIFICACION to "Classifica finale",
                VICTORIA_OTRA_PARTIDA to "UN'ALTRA PARTITA",
                VICTORIA_AL_MENU to "TORNA AL MENU",
                VICTORIA_SOLITARIO_TITULO to "Sfida completata",
                VICTORIA_SOLITARIO_PUNTOS to "%1\$d punti",
                VICTORIA_SOLITARIO_MEJOR to "Il tuo record: %1\$d",
                VICTORIA_SOLITARIO_RECORD to "Nuovo record personale!",
                SOLITARIO_TITULO to "Sfida in solitaria",
                SOLITARIO_SUBTITULO to "Dieci prove. Tu contro il cronometro.",
                SOLITARIO_PROGRESO to "Prova %1\$d di %2\$d",
                SOLITARIO_RONDAS to "Prove",
                SOLITARIO_EMPEZAR to "INIZIA LA SFIDA",
                SOLITARIO_MEJOR_MARCA to "Record: %1\$d",
                SOLITARIO_SIN_MARCA to "Ancora nessun record. Tocca a te.",
                AJUSTES_TITULO to "Impostazioni",
                AJUSTES_SUBTITULO to "Si conservano per la prossima partita.",
                AJUSTES_APARIENCIA to "Aspetto",
                AJUSTES_TEMA to "Tema",
                AJUSTES_TEMA_DETALLE to "Sei temi: tre chiari e tre scuri.",
                AJUSTES_TEMA_SISTEMA to "Segui il sistema",
                AJUSTES_IDIOMA to "Lingua",
                AJUSTES_IDIOMA_DETALLE to "Tredici lingue disponibili.",
                AJUSTES_PARTIDA to "La partita",
                AJUSTES_RITMO to "Ritmo delle prove",
                AJUSTES_RITMO_DETALLE to "Quanto tempo c'è per ogni prova.",
                AJUSTES_DURACION to "Durata della partita",
                AJUSTES_DURACION_DETALLE to "%1\$d caselle fino all'arrivo · %2\$s",
                AJUSTES_JUEGOS_ACTIVOS to "Giochi della partita",
                AJUSTES_JUEGOS_ACTIVOS_DETALLE to
                    "Togli quelli che non vi piacciono e smetteranno di uscire " +
                    "sul tabellone.",
                AJUSTES_JUEGOS_CONTADOR to "%1\$d di %2\$d attivi",
                AJUSTES_JUEGOS_MINIMO to "Deve restare attivo almeno un gioco.",
                AJUSTES_SONIDO to "Suono",
                AJUSTES_SONIDO_DETALLE to "Bip del conto alla rovescia e avvisi",
                AJUSTES_VIBRACION to "Vibrazione",
                AJUSTES_VIBRACION_DETALLE to "Risposte giuste, sbagliate e tempo scaduto",
                AJUSTES_ANIMACIONES to "Animazioni",
                AJUSTES_ANIMACIONES_DETALLE to "Disattivale se preferisci l'interfaccia ferma",
                AJUSTES_DATOS to "I tuoi dati",
                AJUSTES_EXPORTAR to "Esporta",
                AJUSTES_EXPORTAR_DETALLE to "Salva squadre, impostazioni e record in un file",
                AJUSTES_IMPORTAR to "Importa",
                AJUSTES_IMPORTAR_DETALLE to "Recupera una copia salvata prima",
                AJUSTES_MAS to "Altro",
                AJUSTES_APOYAR to "Sostieni lo sviluppo",
                AJUSTES_APOYAR_DETALLE to "Offrimi un caffè se ti sembra utile",
                AJUSTES_COMPARTIR to "Condividi Funny",
                AJUSTES_COMPARTIR_DETALLE to "Passalo a chi pensi se lo goderà",
                AJUSTES_AYUDA to "Aiuto",
                AJUSTES_AYUDA_DETALLE to "Come si gioca e domande frequenti",
                AJUSTES_TOUR to "Tour guidato",
                AJUSTES_TOUR_DETALLE to "I dodici giochi e i tre modi, spiegati",
                AJUSTES_ACERCA_DE to "Informazioni",
                AJUSTES_ACERCA_DE_DETALLE to "Versione, licenze e privacy",
                TEMA_MODO_CLARO to "Chiari",
                TEMA_MODO_OSCURO to "Scuri",
                TEMA_FIESTA to "Festa",
                TEMA_NEON to "Neon",
                TEMA_MEDIANOCHE to "Mezzanotte",
                TEMA_PAPEL to "Carta",
                TEMA_MENTA to "Menta",
                TEMA_ATARDECER to "Tramonto",
                IDIOMA_TITULO to "Lingua",
                IDIOMA_SEGUIR_SISTEMA to "Quella del telefono",
                IDIOMA_SUBTITULO to "Il cambio si applica subito.",
                CAFE_TITULO to "Un caffè?",
                CAFE_TEXTO to
                    "Questa app è gratuita, senza pubblicità e non raccoglie i tuoi dati. Se ti " +
                    "sembra utile, puoi offrirmi un caffè.",
                CAFE_BOTON to "Offrimi un caffè · 1 €",
                CAFE_NO_VOLVER to "Non mostrare più",
                CAFE_OTRO_DISPOSITIVO to "Da un altro dispositivo",
                CAFE_QR_DESCRIPCION to "Codice QR con il link per offrire un caffè all'autore dell'app",
                CAFE_ILUSTRACION_DESCRIPCION to "Disegno di una tazza di caffè con il vapore",
                CAFE_ENLACE_COPIADO to "Link copiato",
                CAFE_GRACIAS to "Grazie per essere passato 🙂",
                CAFE_SIN_DESBLOQUEOS to "Non cambia nulla nel gioco: Funny è completo e lo resterà sempre.",
                CAFE_ENTRADA_AJUSTES to "Sostieni lo sviluppo",
                CAFE_NO_DISPONIBLE to "Non disponibile su questo dispositivo.",
                COPIA_TITULO to "Copia dei tuoi dati",
                COPIA_EXPORTAR_HECHO to "Copia salvata.",
                COPIA_EXPORTAR_ERROR to "Non è stato possibile salvare la copia.",
                COPIA_IMPORTAR_TITULO to "Importa una copia",
                COPIA_IMPORTAR_AVISO to
                    "Prima di toccare qualsiasi cosa si salva una copia di quello che hai " +
                    "adesso, così si può sempre tornare indietro.",
                COPIA_IMPORTAR_FUSIONAR to "Aggiungi a quello che ho",
                COPIA_IMPORTAR_REEMPLAZAR to "Sostituisci tutto",
                COPIA_IMPORTAR_HECHO to "Dati importati.",
                COPIA_IMPORTAR_ERROR_FORMATO to
                    "Questo file non sembra una copia di Funny. Non è stato " +
                    "cambiato niente.",
                COPIA_IMPORTAR_ERROR_VERSION to
                    "Questa copia viene da una versione più nuova di Funny. " +
                    "Aggiorna l'app e riprova.",
                COPIA_IMPORTAR_RESPALDO to "Prima è stata salvata una copia di sicurezza.",
                COPIA_CABECERA_DETALLE to "Copia del %1\$s · Funny %2\$s",
                AYUDA_TITULO to "Aiuto",
                AYUDA_SUBTITULO to "Tutto quello che serve perché nessuno si perda.",
                AYUDA_QUE_ES_TITULO to "Cos'è Funny?",
                AYUDA_QUE_ES_TEXTO to
                    "Un gioco di società con dodici prove diverse. Si gioca con un telefono " +
                    "che passa di mano in mano o, se siete in più con il telefono, " +
                    "collegandoli fra loro. Internet non serve in nessun momento.",
                AYUDA_COMO_SE_JUEGA_TITULO to "La partita",
                AYUDA_PARTIDA_1 to "Scegliete il modo: a squadre, individuale o la sfida in solitaria.",
                AYUDA_PARTIDA_2 to "Ogni squadra o giocatore ha una pedina e tutti partono dalla PARTENZA.",
                AYUDA_PARTIDA_3 to "Al tuo turno tiri il dado e avanzi da 1 a 3 caselle.",
                AYUDA_PARTIDA_4 to
                    "La casella su cui capiti decide la prova. Se la superi resti; se sbagli " +
                    "torni indietro.",
                AYUDA_PARTIDA_5 to "Vince chi arriva all'ARRIVO e supera la prova finale.",
                AYUDA_UN_MOVIL_TITULO to "Con un solo telefono",
                AYUDA_UN_MOVIL_1 to "Il telefono gira: l'app dice sempre a chi tocca.",
                AYUDA_UN_MOVIL_2 to "In mimo, tabù, disegno, canto e sfide guarda solo chi recita.",
                AYUDA_UN_MOVIL_3 to "Nelle prove a risposta, lo schermo si può mostrare a tutti.",
                AYUDA_VARIOS_MOVILES_TITULO to "Con più telefoni",
                AYUDA_VARIOS_MOVILES_1 to
                    "Un telefono fa il tavolo (l'hub) e gli altri si collegano a lui. Non " +
                    "servono né wifi né dati.",
                AYUDA_VARIOS_MOVILES_2 to
                    "La parola segreta arriva solo al telefono di chi recita, così " +
                    "nessuno la vede per sbaglio.",
                AYUDA_VARIOS_MOVILES_3 to
                    "Nelle caselle «giocano tutti», ognuno risponde sul suo telefono " +
                    "nello stesso momento.",
                AYUDA_FAQ_TITULO to "Domande frequenti",
                AYUDA_FAQ_1_P to "Serve internet?",
                AYUDA_FAQ_1_R to
                    "No. Funny funziona del tutto offline, e collegare più telefoni usa Bluetooth " +
                    "e wifi diretto fra loro, senza passare da nessuna rete.",
                AYUDA_FAQ_2_P to "Si può giocare da soli?",
                AYUDA_FAQ_2_R to
                    "Sì: la sfida in solitaria sono dieci prove di fila con record personale. " +
                    "Partecipano solo i giochi che non hanno bisogno di pubblico.",
                AYUDA_FAQ_3_P to "Costa qualcosa? C'è qualcosa di bloccato?",
                AYUDA_FAQ_3_R to
                    "Non c'è niente di bloccato e niente da ottenere a parte. Se ti piace, puoi " +
                    "offrirmi un caffè dalle Impostazioni, e questo non cambia assolutamente nulla " +
                    "nel gioco.",
                AYUDA_FAQ_4_P to "Raccoglie dati?",
                AYUDA_FAQ_4_R to
                    "No. Niente analitiche, niente account, niente pubblicità. Squadre e " +
                    "impostazioni si salvano solo sul tuo telefono ed escono da lì solo se esporti " +
                    "una copia tu.",
                AYUDA_FAQ_5_P to "Posso cambiare i giochi che escono?",
                AYUDA_FAQ_5_R to
                    "Sì, in Impostazioni → Giochi della partita. Quelli che togli smettono di " +
                    "uscire sul tabellone.",
                AYUDA_PROBLEMAS_TITULO to "Se qualcosa non va",
                AYUDA_PROBLEMAS_TEXTO to
                    "Chiudi l'app e riaprila: la partita in corso si conserva. Se il " +
                    "problema resta, esporta i tuoi dati prima di reinstallare e scrivici " +
                    "raccontando cos'è successo.",
                AYUDA_ESCRIBENOS to "Scrivi all'autore",
                ACERCA_TITULO to "Informazioni",
                ACERCA_VERSION to "Versione",
                ACERCA_COMPILACION to "Build",
                ACERCA_FECHA to "Data",
                ACERCA_COMMIT to "Commit",
                ACERCA_LICENCIA to "Licenza",
                ACERCA_LICENCIAS_TERCEROS to "Licenze di terzi",
                ACERCA_PRIVACIDAD to "Informativa sulla privacy",
                ACERCA_CONTACTO to "Contatto",
                ACERCA_CODIGO to "Codice sorgente",
                ACERCA_SIN_ANUNCIOS to "Senza pubblicità, senza analitiche e senza account.",
                ACERCA_AUTOR to "Ghato Studio · Brais Galdo",
                TOUR_TITULO to "Tour guidato",
                TOUR_SUBTITULO to "I dodici giochi e i tre modi, in due minuti.",
                TOUR_EMPEZAR to "INIZIA IL TOUR",
                TOUR_SALTAR to "Salta",
                TOUR_ANTERIOR to "Indietro",
                TOUR_SIGUIENTE to "Avanti",
                TOUR_TERMINAR to "SI GIOCA!",
                TOUR_PROGRESO to "%1\$d di %2\$d",
                TOUR_BIENVENIDA_TITULO to "Benvenuto in Funny",
                TOUR_BIENVENIDA_TEXTO to
                    "Dodici giochi, tre modi di giocare e zero bisogno di internet. Ti " +
                    "spiego tutto in un paio di minuti; puoi saltare quando vuoi.",
                TOUR_MODOS_TITULO to "Tre modi di giocare",
                TOUR_MODOS_TEXTO to
                    "A squadre è il classico: da 2 a 6 squadre e si alterna chi recita. " +
                    "Individuale è lo stesso ma ognuno ha la sua pedina, da 2 a 8. E la sfida " +
                    "in solitaria sono dieci prove contro il cronometro, da solo, con record " +
                    "personale.",
                TOUR_TABLERO_TITULO to "Il tabellone",
                TOUR_TABLERO_TEXTO to
                    "Ogni pedina parte dalla PARTENZA. Al tuo turno tiri il dado, avanzi da 1 " +
                    "a 3 caselle e la casella su cui capiti decide la prova. Se la superi " +
                    "resti lì; se sbagli torni dov'eri. Vince chi arriva all'ARRIVO e supera " +
                    "la prova finale.",
                TOUR_CASILLAS_TITULO to "Le caselle speciali",
                TOUR_CASILLAS_TEXTO to
                    "🃏 Jolly: l'avversario sceglie la prova che ti tocca.\n👥 Giocano tutti: " +
                    "la stessa prova per tutto il tavolo, e chi indovina avanza di una " +
                    "casella.\n🏁 Arrivo: prova finale a caso. Senza superarla non si vince.",
                TOUR_JUEGOS_TITULO to "I dodici giochi",
                TOUR_JUEGOS_TEXTO to
                    "Questi sono tutti. Puoi disattivare quelli che non vi piacciono nelle " +
                    "Impostazioni.",
                TOUR_SALON_TITULO to "Più telefoni insieme",
                TOUR_SALON_TEXTO to
                    "Un telefono fa il tavolo e gli altri si collegano a lui via Bluetooth o " +
                    "wifi diretto, senza internet. Serve per quello che conta davvero: la " +
                    "parola segreta arriva solo al telefono di chi recita, e nelle caselle " +
                    "«giocano tutti» ognuno risponde sul suo nello stesso momento.",
                TOUR_AJUSTES_TITULO to "Come piace a te",
                TOUR_AJUSTES_TEXTO to
                    "Sei temi, tredici lingue, tre ritmi e tre durate. Puoi anche disattivare " +
                    "giochi, spegnere suono e vibrazione, e salvare o recuperare i tuoi dati " +
                    "in un file.",
                TOUR_FINAL_TITULO to "Ecco tutto",
                TOUR_FINAL_TEXTO to "Puoi rivedere questo quando vuoi da Impostazioni → Tour guidato. Divertitevi.",
                SALON_TITULO to "Più telefoni",
                SALON_SUBTITULO to "Senza internet: si collegano fra loro.",
                SALON_CREAR to "FAI DA TAVOLO",
                SALON_CREAR_DETALLE to
                    "Questo telefono guida la partita e mostra il tabellone. È quello che si " +
                    "lascia sul tavolo.",
                SALON_UNIRSE to "UNISCITI A UN TAVOLO",
                SALON_UNIRSE_DETALLE to "Questo telefono resta nella tua mano e riceve le tue prove in privato.",
                SALON_TU_NOMBRE to "Il tuo nome",
                SALON_HUB_TITULO to "Sei il tavolo",
                SALON_HUB_ESPERANDO to "In attesa di collegamenti…",
                SALON_HUB_CONECTADOS to "Collegati",
                SALON_HUB_EMPEZAR to "INIZIA LA PARTITA",
                SALON_CLIENTE_TITULO to "Ricerca di un tavolo",
                SALON_CLIENTE_BUSCANDO to "Ricerca di tavoli vicini…",
                SALON_CLIENTE_SIN_SALONES to
                    "Ancora nessuno. Fai aprire «Fai da tavolo» sull'altro telefono e " +
                    "aspettate qualche secondo.",
                SALON_CLIENTE_CONECTANDO to "Collegamento…",
                SALON_CLIENTE_CONECTADO to "Collegato",
                SALON_CLIENTE_ESPERA to "Sei dentro. Guarda il tavolo: la partita inizia lì.",
                SALON_SALIR to "Esci dalla sala",
                SALON_DESCONECTADO to "Il collegamento con il tavolo è caduto.",
                SALON_ERROR_PERMISOS to "Mancano permessi per trovare i telefoni che hai accanto.",
                SALON_PEDIR_PERMISOS to "DAI I PERMESSI",
                SALON_PERMISOS_EXPLICACION to
                    "Per trovare i telefoni accanto a te, Android chiede il permesso " +
                    "dei dispositivi nelle vicinanze e, sulle versioni più vecchie, " +
                    "anche la posizione. Funny non consulta mai dove sei e non lo " +
                    "salva da nessuna parte: è il prezzo che il sistema mette per " +
                    "usare Bluetooth e wifi diretto.",
                SALON_ERROR_BLUETOOTH to "Accendi il Bluetooth per poter collegare i telefoni.",
                SALON_ERROR_UBICACION to "Accendi la posizione: Android la richiede per cercare via Bluetooth.",
                SALON_ERROR_SERVICIOS to
                    "Questo telefono non ha i servizi Google necessari per collegarsi. " +
                    "Potete continuare a giocare passandovi un solo telefono.",
                SALON_COMO_FUNCIONA to "Come funziona?",
                SALON_ESTE_DISPOSITIVO to "Questo telefono",
                SALON_ROL_HUB to "Tavolo",
                SALON_ROL_MANDO to "Telecomando",
                SALON_SIN_RED to "Internet non si usa in nessun momento.",
                SALON_SIN_NOMBRE to "Senza nome",
                SALON_TU_TURNO to "Tocca a te!",
                SALON_MIRA_EL_HUB to "Guarda il telefono del tavolo.",
                JUEGO_MIMICA_NOMBRE to "Mimo",
                JUEGO_MIMICA_LEMA to "Mimalo senza parlare",
                JUEGO_MIMICA_INSTRUCCIONES to
                    "Una persona mima la parola con i gesti. Vietato parlare, fare " +
                    "rumori o indicare oggetti della stanza.",
                JUEGO_DIBUJO_NOMBRE to "Disegno",
                JUEGO_DIBUJO_LEMA to "Disegnalo sullo schermo",
                JUEGO_DIBUJO_INSTRUCCIONES to
                    "Una persona disegna sullo schermo del telefono e gli altri " +
                    "indovinano. Niente lettere, numeri o gesti.",
                JUEGO_CUANDO_NOMBRE to "Quando?",
                JUEGO_CUANDO_LEMA to "In che anno è successo?",
                JUEGO_CUANDO_INSTRUCCIONES to
                    "Appare un avvenimento e quattro anni possibili. Bisogna decidere " +
                    "in quale è successo.",
                JUEGO_PREGUNTAS_NOMBRE to "Domande",
                JUEGO_PREGUNTAS_LEMA to "Cultura generale",
                JUEGO_PREGUNTAS_INSTRUCCIONES to
                    "Una domanda con quattro risposte. Se ne sceglie una sola e " +
                    "non si può cambiare.",
                JUEGO_TABU_NOMBRE to "Tabù",
                JUEGO_TABU_LEMA to "Descrivilo senza dirlo",
                JUEGO_TABU_INSTRUCCIONES to
                    "Bisogna descrivere la parola senza usare nessuna di quelle vietate " +
                    "né parole della stessa famiglia.",
                JUEGO_RETO_NOMBRE to "Sfida lampo",
                JUEGO_RETO_LEMA to "Elenca contro il tempo",
                JUEGO_RETO_INSTRUCCIONES to
                    "Continua a dire cose della categoria indicata fino a raggiungere " +
                    "l'obiettivo prima che scada il tempo.",
                JUEGO_EMOJIS_NOMBRE to "Emoji",
                JUEGO_EMOJIS_LEMA to "Decifralo",
                JUEGO_EMOJIS_INSTRUCCIONES to
                    "Un film, una canzone o un proverbio scritto solo con emoji, e " +
                    "quattro risposte possibili.",
                JUEGO_VERDADERO_FALSO_NOMBRE to "Ci credi?",
                JUEGO_VERDADERO_FALSO_LEMA to "Vero o falso",
                JUEGO_VERDADERO_FALSO_INSTRUCCIONES to
                    "Quattro affermazioni strane di fila. Di ognuna bisogna " +
                    "dire se è vera o falsa, e poi si spiega perché.",
                JUEGO_TRABALENGUAS_NOMBRE to "Scioglilingua",
                JUEGO_TRABALENGUAS_LEMA to "Dillo senza inciampare",
                JUEGO_TRABALENGUAS_INSTRUCCIONES to
                    "Bisogna dire lo scioglilingua tutte le volte che chiede, " +
                    "intero e senza sbagliare. Giudica il tavolo.",
                JUEGO_ORDENA_NOMBRE to "Ordina",
                JUEGO_ORDENA_LEMA to "Mettilo al suo posto",
                JUEGO_ORDENA_INSTRUCCIONES to
                    "Quattro cose in disordine e un criterio. Bisogna toccarle " +
                    "nell'ordine giusto.",
                JUEGO_CANTA_NOMBRE to "Canta",
                JUEGO_CANTA_LEMA to "Continua la canzone",
                JUEGO_CANTA_INSTRUCCIONES to
                    "Escono il titolo e chi la canta, e bisogna attaccare col refrain. " +
                    "Giudica il tavolo, con la generosità che vuole.",
                JUEGO_DESAFIO_NOMBRE to "Sfida",
                JUEGO_DESAFIO_LEMA to "Osa",
                JUEGO_DESAFIO_INSTRUCCIONES to
                    "Una piccola sfida davanti a tutti. Niente di pericoloso, niente " +
                    "di umiliante: solo ridicolo di quello buono. Giudica il tavolo.",
                RITMO_RAPIDO to "Rapido",
                RITMO_NORMAL to "Normale",
                RITMO_TRANQUILO to "Tranquillo",
                DURACION_CORTA to "Corta",
                DURACION_NORMAL to "Normale",
                DURACION_LARGA to "Lunga",
                DURACION_CORTA_DETALLE to "circa 15 min",
                DURACION_NORMAL_DETALLE to "circa 30 min",
                DURACION_LARGA_DETALLE to "circa 45 min",
                A11Y_DADO to "Dado: %1\$d",
                A11Y_FICHA to "Pedina di %1\$s sulla casella %2\$d",
                A11Y_CASILLA to "Casella %1\$d, %2\$s",
                A11Y_VOLVER to "Torna alla schermata precedente",
                A11Y_CERRAR to "Chiudi",
                A11Y_LIENZO_DIBUJO to "Area per disegnare con il dito",
                A11Y_TEMA_MUESTRA to "Campione di colori del tema %1\$s",
                A11Y_BANDERA_IDIOMA to "Lingua %1\$s",
            ),
        plurales =
            mapOf(
                ClavePlural.CASILLAS to
                    mapOf(
                        CategoriaPlural.ONE to "%d casella",
                        CategoriaPlural.OTHER to "%d caselle",
                    ),
                ClavePlural.SEGUNDOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d secondo",
                        CategoriaPlural.OTHER to "%d secondi",
                    ),
                ClavePlural.DISPOSITIVOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d telefono collegato",
                        CategoriaPlural.OTHER to "%d telefoni collegati",
                    ),
                ClavePlural.PUNTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d punto",
                        CategoriaPlural.OTHER to "%d punti",
                    ),
                ClavePlural.ACIERTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d risposta giusta",
                        CategoriaPlural.OTHER to "%d risposte giuste",
                    ),
                ClavePlural.REPETICIONES to
                    mapOf(
                        CategoriaPlural.ONE to "%d volta",
                        CategoriaPlural.OTHER to "%d volte",
                    ),
            ),
    )
