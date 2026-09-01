package es.ghatostudio.funny.ui.i18n

import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.dominio.textos.Clave.*

/**
 * Catálogo en inglés. Además de ser uno de los trece idiomas, es el **respaldo**
 * de todos los demás: si a un catálogo le falta una clave, [Textos] la busca
 * aquí antes de rendirse. Por eso este fichero no puede quedarse incompleto.
 *
 * Nota sobre la donación: en inglés lo idiomático sería «buy me a coffee», pero
 * la palabra *buy* está descartada a propósito en toda la app y en la ficha de
 * tienda, porque enmarca la donación como una compra y eso es justo lo que no
 * es. Se usa «treat me to a coffee», que dice lo mismo sin la palabra.
 */
internal val catalogoIngles =
    Catalogo(
        idioma = Idioma.INGLES,
        textos =
            mapOf<Clave, String>(
                ACCION_VOLVER to "Back",
                ACCION_CERRAR to "Close",
                ACCION_CANCELAR to "Cancel",
                ACCION_ACEPTAR to "OK",
                ACCION_CONTINUAR to "Continue",
                ACCION_EMPEZAR to "Start!",
                ACCION_LISTO to "Done",
                ACCION_BORRAR to "Delete",
                ACCION_ANADIR to "Add",
                ACCION_REINTENTAR to "Try again",
                ACCION_COPIAR to "Copy link",
                ACCION_COMPARTIR to "Share",
                ACCION_AHORA_NO to "Not now",
                ACCION_SI to "Yes",
                ACCION_NO to "No",
                ESTADO_CARGANDO to "Loading…",
                ESTADO_SIN_CONTENIDO to "There is no content for this game.",
                APP_LEMA to "The party game that fits in your phones",
                MENU_JUGAR to "PLAY",
                MENU_SEGUIR_PARTIDA to "RESUME GAME",
                MENU_PARTIDA_NUEVA to "NEW GAME",
                MENU_COMO_JUGAR to "HOW TO PLAY",
                MENU_AJUSTES to "SETTINGS",
                MENU_SALON to "PLAY WITH SEVERAL PHONES",
                MENU_TOUR to "TAKE THE TOUR",
                MODO_TITULO to "How are we playing?",
                MODO_SUBTITULO to "You can change this in any new game.",
                MODO_EQUIPOS to "Teams",
                MODO_EQUIPOS_DETALLE to
                    "From 2 to 6 teams. Each team has its own token and takes turns acting. " +
                    "The classic way, and the loudest.",
                MODO_INDIVIDUAL to "Individual",
                MODO_INDIVIDUAL_DETALLE to
                    "From 2 to 8 people, each with their own token and no teams. " +
                    "In the acting games, whoever's turn it is performs and everyone else guesses.",
                MODO_SOLITARIO to "Solo challenge",
                MODO_SOLITARIO_DETALLE to
                    "You against the clock: ten games in a row and a personal best to beat. " +
                    "No board, and only the games that work without an audience.",
                PARTICIPANTES_TITULO_EQUIPOS to "Teams",
                PARTICIPANTES_TITULO_INDIVIDUAL to "Players",
                PARTICIPANTES_TITULO_SOLITARIO to "What's your name?",
                PARTICIPANTES_SUBTITULO_EQUIPOS to
                    "From %1\$d to %2\$d teams. Write down who plays in each one and the phone will " +
                    "say whose turn it is to act.",
                PARTICIPANTES_SUBTITULO_INDIVIDUAL to
                    "From %1\$d to %2\$d people, each with their own token.",
                PARTICIPANTES_SUBTITULO_SOLITARIO to
                    "Only to put your name on the score. None of this leaves your phone.",
                PARTICIPANTES_ANADIR_EQUIPO to "+  ADD TEAM",
                PARTICIPANTES_ANADIR_JUGADOR to "+  ADD PLAYER",
                PARTICIPANTES_NUEVO_JUGADOR to "Add player…",
                PARTICIPANTES_SIN_JUGADORES to
                    "No names yet: the game will just say the team name.",
                PARTICIPANTES_NOMBRE_EQUIPO to "Team name",
                PARTICIPANTES_NOMBRE_JUGADOR to "Name",
                PARTICIPANTES_QUITAR to "Remove",
                PARTICIPANTES_EQUIPO_POR_DEFECTO to "Team %1\$d",
                PARTICIPANTES_JUGADOR_POR_DEFECTO to "Player %1\$d",
                PARTICIPANTES_EQUIPO_1_POR_DEFECTO to "The Aces",
                PARTICIPANTES_EQUIPO_2_POR_DEFECTO to "The Beasts",
                PARTICIPANTES_DESDE_SALON to "Joined from another phone",
                TABLERO_TIRAR to "ROLL THE DIE",
                TABLERO_TURNO_DE to "%1\$s'S TURN",
                TABLERO_LE_TOCA to "%1\$s's turn",
                TABLERO_CASILLA to "Square %1\$d",
                TABLERO_SALIDA to "START",
                TABLERO_META to "FINISH",
                TABLERO_MARCADOR to "Scores",
                TABLERO_ABANDONAR to "Quit the game",
                TABLERO_ABANDONAR_PREGUNTA to
                    "You will lose the progress of this game. Are you sure you want to leave?",
                TABLERO_ESPERANDO_HUB to "Waiting for the phone running the game…",
                TABLERO_AVANZA_CASILLAS to "Moves %1\$d",
                CASILLA_COMODIN to "WILD",
                CASILLA_COMODIN_DETALLE to "Your rival picks the game you get. No mercy.",
                CASILLA_TODOS to "EVERYONE PLAYS",
                CASILLA_TODOS_DETALLE to
                    "The same game for the whole table. Everyone who gets it right moves one square.",
                CASILLA_META_AVISO to "Final round: you only win by passing it.",
                COMODIN_TITULO to "Wild square",
                COMODIN_ELIGE to "%1\$s picks the game",
                PRUEBA_FINAL to "🏁  FINAL ROUND",
                PRUEBA_JUEGAN_TODOS to "👥  EVERYONE PLAYS",
                PRUEBA_LE_TOCA_ACTUAR_A to "IT'S UP TO",
                PRUEBA_QUIEN_DECIDA to "whoever %1\$s picks",
                PRUEBA_SOLO_MIRE_ESA_PERSONA to "👀  Only this person should look at the screen",
                PRUEBA_MIRA_TU_MOVIL to "📱  Look at your own phone: the word went there",
                PRUEBA_SEGUNDOS to "⏱  %1\$d seconds",
                PRUEBA_CUANDO_TEMA to "Which year?",
                PRUEBA_CUANDO_RESPUESTA to "It happened in %1\$d.",
                PRUEBA_ACIERTOS_DE to "%1\$d of %2\$d",
                PRUEBA_SALTAR to "SKIP",
                PRUEBA_ACERTADA to "✓  GOT IT",
                PRUEBA_FALLADA to "✗  MISSED",
                PRUEBA_PROHIBIDA to "🚫  FORBIDDEN",
                PRUEBA_TERMINAR to "End this round",
                PRUEBA_ACIERTOS to "✔  %1\$d",
                PRUEBA_VEREDICTO_TITULO to "Did they pull it off?",
                PRUEBA_VEREDICTO_LOGRADO to "✓  YES!",
                PRUEBA_VEREDICTO_NO_LOGRADO to "✗  NO",
                PRUEBA_VEREDICTO_DECIDE_MESA to "The table decides, not the app.",
                PRUEBA_VEREDICTO_DECIDE_SOLO to "Nobody is watching here: be honest with yourself.",
                PRUEBA_BORRAR_DIBUJO to "Clear all",
                PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA to "ONLY THE ARTIST LOOKS",
                PRUEBA_DIBUJO_TIEMPO_AL_PULSAR to "The clock starts when you press the button.",
                PRUEBA_DIBUJO_EMPEZAR to "🎨   START DRAWING",
                PRUEBA_DIBUJO_LIENZO_VACIO to "Draw here with your finger",
                PRUEBA_DIBUJO_ESPIAR to "Press and hold to see the word again",
                PRUEBA_DIBUJO_GOMA to "Eraser",
                PRUEBA_DESHACER to "Undo",
                PRUEBA_PINCEL to "Thickness",
                PRUEBA_COLOR to "Colour",
                PRUEBA_RETO_OBJETIVO to "You need to reach %1\$d",
                PRUEBA_RETO_LLEVAMOS to "So far",
                PRUEBA_RETO_UNA_MAS to "+1  ANOTHER!",
                PRUEBA_RETO_CONSEGUIDO to "DONE IT!",
                PRUEBA_RETO_TOCA_PARA_SUMAR to "of %1\$d  ·  tap to add",
                PRUEBA_RETO_RENDIRSE to "WE GIVE UP",
                PRUEBA_RETO_NOTA to "Each answer counts once. Repeats don't count.",
                PRUEBA_ORDENA_AYUDA to "Tap them in the right order, first to last.",
                PRUEBA_ORDENA_COMPROBAR to "CHECK",
                PRUEBA_ORDENA_CORRECTO to "The right order was:",
                PRUEBA_VF_VERDADERO to "TRUE",
                PRUEBA_VF_FALSO to "FALSE",
                PRUEBA_VF_ERA_VERDAD to "It was true",
                PRUEBA_VF_ERA_MENTIRA to "It was false",
                PRUEBA_EMOJIS_AYUDA to "What is this, written in emoji?",
                PRUEBA_EMOJIS_ERA to "It was: %1\$s",
                PRUEBA_CANTA_PISTA to "Start here",
                PRUEBA_TRABALENGUAS_AYUDA to "Say it all the way through without stumbling.",
                PRUEBA_DESAFIO_AYUDA to "Let's see you get out of this one.",
                RONDA_TODOS_PASA_MOVIL to "Pass the phone without looking at anyone's answer.",
                RONDA_TODOS_RESPONDE to "%1\$s answers",
                RONDA_TODOS_RESUMEN to "Who got it right",
                RONDA_TODOS_NADIE to "Nobody. No token moves.",
                RONDA_TODOS_EN_TU_MOVIL to "Answer on your own phone.",
                RONDA_TODOS_ESPERANDO to "Waiting for the others…",
                RONDA_TODOS_PASAD_A to "PASS THE PHONE TO",
                RONDA_TODOS_PROGRESO to
                    "%1\$d of %2\$d. Nobody finds out who got it right until the end.",
                RONDA_TODOS_SIN_RESPUESTA to "Time's up, no answer.",
                RONDA_TODOS_GUARDADA to "Answer saved. Don't say it out loud yet.",
                RONDA_TODOS_CORRECTA_ERA to "THE RIGHT ANSWER WAS",
                RONDA_TODOS_SIN_RESPONDER to "no answer",
                RONDA_TODOS_VER_RESULTADOS to "SEE THE RESULTS",
                RESULTADO_SUPERADA to "PASSED!",
                RESULTADO_NO_HA_PODIDO_SER to "NOT THIS TIME",
                RESULTADO_FINAL_SUPERADA to "FINAL ROUND PASSED!",
                RESULTADO_AVANZAS_A to "You move to square %1\$d",
                RESULTADO_TE_QUEDAS_EN to "You stay on square %1\$d",
                RESULTADO_LLEGADA_A_META to "You made it to the finish!",
                RESULTADO_SIGUIENTE_TURNO to "NEXT TURN",
                RESULTADO_VER_RESULTADO to "SEE THE RESULT",
                VICTORIA_TITULO to "Game over!",
                VICTORIA_GANADOR to "%1\$s wins",
                VICTORIA_CLASIFICACION to "Final standings",
                VICTORIA_OTRA_PARTIDA to "PLAY AGAIN",
                VICTORIA_AL_MENU to "BACK TO MENU",
                VICTORIA_SOLITARIO_TITULO to "Challenge complete",
                VICTORIA_SOLITARIO_PUNTOS to "%1\$d points",
                VICTORIA_SOLITARIO_MEJOR to "Your best: %1\$d",
                VICTORIA_SOLITARIO_RECORD to "New personal best!",
                SOLITARIO_TITULO to "Solo challenge",
                SOLITARIO_SUBTITULO to "Ten rounds. You against the clock.",
                SOLITARIO_PROGRESO to "Round %1\$d of %2\$d",
                SOLITARIO_RONDAS to "Rounds",
                SOLITARIO_EMPEZAR to "START THE CHALLENGE",
                SOLITARIO_MEJOR_MARCA to "Best: %1\$d",
                SOLITARIO_SIN_MARCA to "No score yet. Go and set one.",
                AJUSTES_TITULO to "Settings",
                AJUSTES_SUBTITULO to "Saved for your next game.",
                AJUSTES_APARIENCIA to "Appearance",
                AJUSTES_TEMA to "Theme",
                AJUSTES_TEMA_DETALLE to "Six themes: three light and three dark.",
                AJUSTES_TEMA_SISTEMA to "Follow the system",
                AJUSTES_IDIOMA to "Language",
                AJUSTES_IDIOMA_DETALLE to "Thirteen languages available.",
                AJUSTES_PARTIDA to "The game",
                AJUSTES_RITMO to "Round pace",
                AJUSTES_RITMO_DETALLE to "How much time you get for each round.",
                AJUSTES_MODALIDAD to "Game mode",
                AJUSTES_MODALIDAD_DETALLE to
                    "How many squares the board has and how many rounds the solo run " +
                    "lasts.",
                AJUSTES_JUEGOS_ACTIVOS to "Games in play",
                AJUSTES_JUEGOS_ACTIVOS_DETALLE to
                    "Turn off the ones you don't like and they will stop showing up on the board.",
                AJUSTES_JUEGOS_CONTADOR to "%1\$d of %2\$d on",
                AJUSTES_JUEGOS_MINIMO to "At least one game has to stay on.",
                AJUSTES_SONIDO to "Sound",
                AJUSTES_SONIDO_DETALLE to "Countdown beeps and alerts",
                AJUSTES_VIBRACION to "Vibration",
                AJUSTES_VIBRACION_DETALLE to "Hits, misses and time's up",
                AJUSTES_ANIMACIONES to "Animations",
                AJUSTES_ANIMACIONES_DETALLE to "Turn them off if you prefer a still interface",
                AJUSTES_DATOS to "Your data",
                AJUSTES_EXPORTAR to "Export",
                AJUSTES_EXPORTAR_DETALLE to "Save teams, settings and scores to a file",
                AJUSTES_IMPORTAR to "Import",
                AJUSTES_IMPORTAR_DETALLE to "Restore a copy you saved earlier",
                AJUSTES_MAS to "More",
                AJUSTES_APOYAR to "Support the development",
                AJUSTES_APOYAR_DETALLE to "Treat me to a coffee if you find it useful",
                AJUSTES_COMPARTIR to "Share Funny",
                AJUSTES_COMPARTIR_DETALLE to "Pass it on to someone who'd enjoy it",
                AJUSTES_AYUDA to "Help",
                AJUSTES_AYUDA_DETALLE to "How to play and frequent questions",
                AJUSTES_TOUR to "Guided tour",
                AJUSTES_TOUR_DETALLE to "All twelve games and three modes, explained",
                AJUSTES_ACERCA_DE to "About",
                AJUSTES_ACERCA_DE_DETALLE to "Version, licences and privacy",
                TEMA_MODO_CLARO to "Light",
                TEMA_MODO_OSCURO to "Dark",
                TEMA_FIESTA to "Party",
                TEMA_NEON to "Neon",
                TEMA_MEDIANOCHE to "Midnight",
                TEMA_PAPEL to "Paper",
                TEMA_MENTA to "Mint",
                TEMA_ATARDECER to "Sunset",
                IDIOMA_TITULO to "Language",
                IDIOMA_SEGUIR_SISTEMA to "Phone language",
                IDIOMA_SUBTITULO to "The change applies straight away.",
                CAFE_TITULO to "A coffee?",
                CAFE_TEXTO to
                    "This app is free, has no ads and does not collect your data. " +
                    "If you find it useful, you can treat me to a coffee.",
                CAFE_BOTON to "Treat me to a coffee · €1",
                CAFE_NO_VOLVER to "Don't show this again",
                CAFE_OTRO_DISPOSITIVO to "From another device",
                CAFE_QR_DESCRIPCION to
                    "QR code with the link to treat the app's author to a coffee",
                CAFE_ILUSTRACION_DESCRIPCION to "Drawing of a coffee cup with steam",
                CAFE_ENLACE_COPIADO to "Link copied",
                CAFE_GRACIAS to "Thanks for dropping by 🙂",
                CAFE_SIN_DESBLOQUEOS to
                    "It changes nothing inside the game: Funny is complete and always will be.",
                CAFE_ENTRADA_AJUSTES to "Support the development",
                CAFE_NO_DISPONIBLE to "Not available on this device.",
                COPIA_TITULO to "Copy of your data",
                COPIA_EXPORTAR_HECHO to "Copy saved.",
                COPIA_EXPORTAR_ERROR to "The copy could not be saved.",
                COPIA_IMPORTAR_TITULO to "Import a copy",
                COPIA_IMPORTAR_AVISO to
                    "Before anything changes, a copy of what you have now is saved, " +
                    "so you can always go back.",
                COPIA_IMPORTAR_FUSIONAR to "Add to what I have",
                COPIA_IMPORTAR_REEMPLAZAR to "Replace everything",
                COPIA_IMPORTAR_HECHO to "Data imported.",
                COPIA_IMPORTAR_ERROR_FORMATO to
                    "That file doesn't look like a Funny copy. Nothing has been changed.",
                COPIA_IMPORTAR_ERROR_VERSION to
                    "That copy comes from a newer version of Funny. Update the app and try again.",
                COPIA_IMPORTAR_RESPALDO to "A backup was saved first.",
                COPIA_CABECERA_DETALLE to "Copy from %1\$s · Funny %2\$s",
                AYUDA_TITULO to "Help",
                AYUDA_SUBTITULO to "Everything you need so nobody gets lost.",
                AYUDA_QUE_ES_TITULO to "What is Funny?",
                AYUDA_QUE_ES_TEXTO to
                    "A party game with twelve different rounds. You play with one phone passed " +
                    "around the table or, if several of you have a phone, by connecting them to " +
                    "each other. You never need the internet.",
                AYUDA_COMO_SE_JUEGA_TITULO to "The game",
                AYUDA_PARTIDA_1 to "Pick the mode: teams, individual or the solo challenge.",
                AYUDA_PARTIDA_2 to "Each team or player has a token and everyone starts at START.",
                AYUDA_PARTIDA_3 to "On your turn you roll the die and move 1 to 3 squares.",
                AYUDA_PARTIDA_4 to
                    "The square you land on decides the round. Pass it and you stay; miss and you go back.",
                AYUDA_PARTIDA_5 to "Whoever reaches the FINISH and passes the final round wins.",
                AYUDA_UN_MOVIL_TITULO to "With one phone",
                AYUDA_UN_MOVIL_1 to "The phone goes around: the app always says whose turn it is.",
                AYUDA_UN_MOVIL_2 to
                    "In mime, taboo, drawing, singing and dares, only the performer looks.",
                AYUDA_UN_MOVIL_3 to "In the answering rounds you can show the screen to everyone.",
                AYUDA_VARIOS_MOVILES_TITULO to "With several phones",
                AYUDA_VARIOS_MOVILES_1 to
                    "One phone acts as the table (the hub) and the rest connect to it. " +
                    "No Wi-Fi network and no mobile data needed.",
                AYUDA_VARIOS_MOVILES_2 to
                    "The secret word only reaches the performer's phone, so nobody sees it by accident.",
                AYUDA_VARIOS_MOVILES_3 to
                    "On «everyone plays» squares, each person answers on their own phone at the same time.",
                AYUDA_FAQ_TITULO to "Frequent questions",
                AYUDA_FAQ_1_P to "Do I need the internet?",
                AYUDA_FAQ_1_R to
                    "No. Funny works entirely offline, and connecting several phones uses Bluetooth " +
                    "and Wi-Fi Direct between them, without going through any network.",
                AYUDA_FAQ_2_P to "Can one person play alone?",
                AYUDA_FAQ_2_R to
                    "Yes: the solo challenge is ten rounds in a row with a personal best. " +
                    "Only the games that don't need an audience take part.",
                AYUDA_FAQ_3_P to "Does it cost anything? Is anything locked?",
                AYUDA_FAQ_3_R to
                    "Nothing is locked and there is nothing extra to get. If you like it you can treat " +
                    "me to a coffee from Settings, and that changes absolutely nothing inside the game.",
                AYUDA_FAQ_4_P to "Does it collect data?",
                AYUDA_FAQ_4_R to
                    "No. There is no analytics, no accounts and no advertising. Teams and settings are " +
                    "stored only on your phone, and they leave it only if you export a copy yourself.",
                AYUDA_FAQ_5_P to "Can I change which games show up?",
                AYUDA_FAQ_5_R to
                    "Yes, in Settings → Games in play. The ones you remove stop appearing on the board.",
                AYUDA_PROBLEMAS_TITULO to "If something goes wrong",
                AYUDA_PROBLEMAS_TEXTO to
                    "Close the app and open it again: the game in progress is kept. If the problem " +
                    "persists, export your data before reinstalling and write to us describing what happened.",
                AYUDA_ESCRIBENOS to "Write to the author",
                ACERCA_TITULO to "About",
                ACERCA_VERSION to "Version",
                ACERCA_COMPILACION to "Build",
                ACERCA_FECHA to "Date",
                ACERCA_COMMIT to "Commit",
                ACERCA_LICENCIA to "Licence",
                ACERCA_LICENCIAS_TERCEROS to "Third-party licences",
                ACERCA_PRIVACIDAD to "Privacy policy",
                ACERCA_CONTACTO to "Contact",
                ACERCA_CODIGO to "Source code",
                ACERCA_SIN_ANUNCIOS to "No ads, no analytics and no accounts.",
                ACERCA_AUTOR to "Ghato Studio · Brais Galdo",
                TOUR_TITULO to "Guided tour",
                TOUR_SUBTITULO to "The twelve games and three modes, in two minutes.",
                TOUR_EMPEZAR to "START THE TOUR",
                TOUR_SALTAR to "Skip",
                TOUR_ANTERIOR to "Back",
                TOUR_SIGUIENTE to "Next",
                TOUR_TERMINAR to "LET'S PLAY!",
                TOUR_PROGRESO to "%1\$d of %2\$d",
                TOUR_BIENVENIDA_TITULO to "Welcome to Funny",
                TOUR_BIENVENIDA_TEXTO to
                    "Twelve games, three ways to play and no need for the internet. " +
                    "I'll walk you through it in a couple of minutes; you can skip whenever you like.",
                TOUR_MODOS_TITULO to "Three ways to play",
                TOUR_MODOS_TEXTO to
                    "Teams is the classic: 2 to 6 teams, taking turns to act. Individual is the same " +
                    "but each person has their own token, 2 to 8 of you. And the solo challenge is " +
                    "ten rounds against the clock, on your own, with a personal best.",
                TOUR_TABLERO_TITULO to "The board",
                TOUR_TABLERO_TEXTO to
                    "Every token starts at START. On your turn you roll the die, move 1 to 3 squares, " +
                    "and the square you land on decides the round. Pass it and you stay; miss and you " +
                    "go back where you were. Whoever reaches the FINISH and passes the final round wins.",
                TOUR_CASILLAS_TITULO to "The special squares",
                TOUR_CASILLAS_TEXTO to
                    "🃏 Wild: your rival picks the round you get.\n" +
                    "👥 Everyone plays: the same round for the whole table, and everyone who gets it " +
                    "right moves one square.\n" +
                    "🏁 Finish: a random final round. You don't win without passing it.",
                TOUR_JUEGOS_TITULO to "The twelve games",
                TOUR_JUEGOS_TEXTO to
                    "Here they all are. You can switch off the ones you don't like in Settings.",
                TOUR_SALON_TITULO to "Several phones at once",
                TOUR_SALON_TEXTO to
                    "One phone acts as the table and the rest connect to it over Bluetooth or Wi-Fi " +
                    "Direct, with no internet. It buys you the thing that really matters: the secret " +
                    "word goes only to the performer's phone, and on «everyone plays» squares each " +
                    "person answers on their own at the same time.",
                TOUR_AJUSTES_TITULO to "Make it yours",
                TOUR_AJUSTES_TEXTO to
                    "Six themes, thirteen languages, three paces and four game modes. You can also " +
                    "switch games off, mute sound and vibration, and save or restore your data to a file.",
                TOUR_FINAL_TITULO to "That's it",
                TOUR_FINAL_TEXTO to
                    "You can see this again whenever you like from Settings → Guided tour. " +
                    "Have fun.",
                SALON_TITULO to "Several phones",
                SALON_SUBTITULO to "No internet: they connect to each other.",
                SALON_CREAR to "BE THE TABLE",
                SALON_CREAR_DETALLE to
                    "This phone runs the game and shows the board. It's the one you leave on the table.",
                SALON_UNIRSE to "JOIN A TABLE",
                SALON_UNIRSE_DETALLE to
                    "This phone stays in your hand and receives your rounds privately.",
                SALON_TU_NOMBRE to "Your name",
                SALON_HUB_TITULO to "You are the table",
                SALON_HUB_ESPERANDO to "Waiting for others to connect…",
                SALON_HUB_CONECTADOS to "Connected",
                SALON_HUB_EMPEZAR to "START THE GAME",
                SALON_CLIENTE_TITULO to "Looking for a table",
                SALON_CLIENTE_BUSCANDO to "Looking for tables nearby…",
                SALON_CLIENTE_SIN_SALONES to
                    "None in sight yet. Have the other phone open «Be the table» and wait a few seconds.",
                SALON_CLIENTE_CONECTANDO to "Connecting…",
                SALON_CLIENTE_CONECTADO to "Connected",
                SALON_CLIENTE_ESPERA to "You're in. Look at the table: the game starts there.",
                SALON_SALIR to "Leave the room",
                SALON_DESCONECTADO to "The connection with the table was lost.",
                SALON_ERROR_PERMISOS to "Permissions are missing to find the phones next to you.",
                SALON_PEDIR_PERMISOS to "GRANT PERMISSIONS",
                SALON_PERMISOS_EXPLICACION to
                    "To find the phones next to you, Android asks for the nearby-devices permission " +
                    "and, on older versions, location as well. Funny never looks up where you are and " +
                    "never stores it: it is the price the system puts on using Bluetooth and Wi-Fi Direct.",
                SALON_ERROR_BLUETOOTH to "Turn Bluetooth on so the phones can connect.",
                SALON_ERROR_UBICACION to
                    "Turn location on: Android requires it to search over Bluetooth.",
                SALON_ERROR_SERVICIOS to
                    "This phone doesn't have the Google services needed to connect. " +
                    "You can still play by passing a single phone around.",
                SALON_COMO_FUNCIONA to "How does it work?",
                SALON_ESTE_DISPOSITIVO to "This phone",
                SALON_ROL_HUB to "Table",
                SALON_ROL_MANDO to "Controller",
                SALON_SIN_RED to "The internet is never used.",
                SALON_SIN_NOMBRE to "No name",
                SALON_TU_TURNO to "Your turn!",
                SALON_MIRA_EL_HUB to "Look at the table phone.",
                JUEGO_MIMICA_NOMBRE to "Mime",
                JUEGO_MIMICA_LEMA to "Act it out in silence",
                JUEGO_MIMICA_INSTRUCCIONES to
                    "One person acts the word out with gestures. No talking, no noises and no " +
                    "pointing at things in the room.",
                JUEGO_DIBUJO_NOMBRE to "Sketch",
                JUEGO_DIBUJO_LEMA to "Draw it on the screen",
                JUEGO_DIBUJO_INSTRUCCIONES to
                    "One person draws on the phone screen and the others guess. " +
                    "No letters, no numbers and no gestures.",
                JUEGO_CUANDO_NOMBRE to "When?",
                JUEGO_CUANDO_LEMA to "What year did it happen?",
                JUEGO_CUANDO_INSTRUCCIONES to
                    "An event appears along with four possible years. You have to decide which one.",
                JUEGO_PREGUNTAS_NOMBRE to "Questions",
                JUEGO_PREGUNTAS_LEMA to "General knowledge",
                JUEGO_PREGUNTAS_INSTRUCCIONES to
                    "One question with four answers. You pick a single one and can't change it.",
                JUEGO_TABU_NOMBRE to "Taboo",
                JUEGO_TABU_LEMA to "Describe it without saying it",
                JUEGO_TABU_INSTRUCCIONES to
                    "Describe the word without using any of the forbidden ones, or words from the " +
                    "same family.",
                JUEGO_RETO_NOMBRE to "Quick fire",
                JUEGO_RETO_LEMA to "Name them against the clock",
                JUEGO_RETO_INSTRUCCIONES to
                    "Keep naming things from the given category until you reach the target before " +
                    "time runs out.",
                JUEGO_EMOJIS_NOMBRE to "Emoji",
                JUEGO_EMOJIS_LEMA to "Crack the code",
                JUEGO_EMOJIS_INSTRUCCIONES to
                    "A film, a song or a saying written only in emoji, with four possible answers.",
                JUEGO_VERDADERO_FALSO_NOMBRE to "Really?",
                JUEGO_VERDADERO_FALSO_LEMA to "True or false",
                JUEGO_VERDADERO_FALSO_INSTRUCCIONES to
                    "Four odd statements in a row. Say whether each one is true or false, and then " +
                    "you find out why.",
                JUEGO_TRABALENGUAS_NOMBRE to "Tongue twister",
                JUEGO_TRABALENGUAS_LEMA to "Say it without stumbling",
                JUEGO_TRABALENGUAS_INSTRUCCIONES to
                    "Say the tongue twister as many times as it asks, all the way through and without " +
                    "slipping. The table judges.",
                JUEGO_ORDENA_NOMBRE to "Order it",
                JUEGO_ORDENA_LEMA to "Put it in its place",
                JUEGO_ORDENA_INSTRUCCIONES to
                    "Four things out of order and one criterion. Tap them in the right order.",
                JUEGO_CANTA_NOMBRE to "Sing",
                JUEGO_CANTA_LEMA to "Carry on singing",
                JUEGO_CANTA_INSTRUCCIONES to
                    "The title and the artist come up, and you have to launch into the chorus. " +
                    "The table judges, as generously as it sees fit.",
                JUEGO_DESAFIO_NOMBRE to "Dare",
                JUEGO_DESAFIO_LEMA to "Go on then",
                JUEGO_DESAFIO_INSTRUCCIONES to
                    "A small dare in front of everyone. Nothing dangerous, nothing humiliating: just " +
                    "the good kind of silly. The table judges.",
                RITMO_RAPIDO to "Fast",
                RITMO_NORMAL to "Normal",
                RITMO_TRANQUILO to "Relaxed",
                MODALIDAD_RAPIDA to "Quick game",
                MODALIDAD_NORMAL to "Normal game",
                MODALIDAD_EXTREMA to "Extreme game",
                MODALIDAD_PERSONALIZADA to "My way",
                MODALIDAD_RAPIDA_DETALLE to "One round and done, or just to try the game",
                MODALIDAD_NORMAL_DETALLE to "The usual one, the one that fits best",
                MODALIDAD_EXTREMA_DETALLE to "For a whole evening, no rush",
                MODALIDAD_PERSONALIZADA_DETALLE to "You pick the numbers",
                MODALIDAD_RESUMEN to "%1\$d squares · %2\$d rounds · about %3\$d min",
                MODALIDAD_CASILLAS to "Squares to the finish",
                MODALIDAD_PRUEBAS to "Rounds per game",
                MODALIDAD_PRUEBAS_NOTA to "Only used in the solo run",
                A11Y_DADO to "Die: %1\$d",
                A11Y_FICHA to "%1\$s's token on square %2\$d",
                A11Y_CASILLA to "Square %1\$d, %2\$s",
                A11Y_VOLVER to "Back to the previous screen",
                A11Y_CERRAR to "Close",
                A11Y_LIENZO_DIBUJO to "Canvas to draw with your finger",
                A11Y_TEMA_MUESTRA to "Colour swatch for the %1\$s theme",
                A11Y_BANDERA_IDIOMA to "%1\$s language",
                A11Y_REDUCIR to "Decrease",
                A11Y_AUMENTAR to "Increase",
            ),
        plurales =
            mapOf(
                ClavePlural.CASILLAS to
                    mapOf(
                        CategoriaPlural.ONE to "%d square",
                        CategoriaPlural.OTHER to "%d squares",
                    ),
                ClavePlural.SEGUNDOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d second",
                        CategoriaPlural.OTHER to "%d seconds",
                    ),
                ClavePlural.DISPOSITIVOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d phone connected",
                        CategoriaPlural.OTHER to "%d phones connected",
                    ),
                ClavePlural.PUNTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d point",
                        CategoriaPlural.OTHER to "%d points",
                    ),
                ClavePlural.ACIERTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d hit",
                        CategoriaPlural.OTHER to "%d hits",
                    ),
                ClavePlural.REPETICIONES to
                    mapOf(
                        CategoriaPlural.ONE to "%d time",
                        CategoriaPlural.OTHER to "%d times",
                    ),
            ),
    )
