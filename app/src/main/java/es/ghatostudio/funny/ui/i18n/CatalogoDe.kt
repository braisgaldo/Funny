package es.ghatostudio.funny.ui.i18n

import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.dominio.textos.Clave.*

/**
 * Catálogo en alemán.
 *
 * Nota sobre la donación: se evita «kaufen» y «bezahlen», igual que en los demás
 * idiomas. Se usa «lade mich auf einen Kaffee ein», que dice lo mismo sin
 * enmarcarlo como una compra. Lo comprueba `PruebaCatalogos`.
 */
internal val catalogoAleman =
    Catalogo(
        idioma = Idioma.ALEMAN,
        textos =
            mapOf<Clave, String>(
                ACCION_VOLVER to "Zurück",
                ACCION_CERRAR to "Schließen",
                ACCION_CANCELAR to "Abbrechen",
                ACCION_ACEPTAR to "OK",
                ACCION_CONTINUAR to "Weiter",
                ACCION_EMPEZAR to "Los geht's!",
                ACCION_LISTO to "Fertig",
                ACCION_BORRAR to "Löschen",
                ACCION_ANADIR to "Hinzufügen",
                ACCION_REINTENTAR to "Nochmal versuchen",
                ACCION_COPIAR to "Link kopieren",
                ACCION_COMPARTIR to "Teilen",
                ACCION_AHORA_NO to "Jetzt nicht",
                ACCION_SI to "Ja",
                ACCION_NO to "Nein",
                ESTADO_CARGANDO to "Wird geladen…",
                ESTADO_SIN_CONTENIDO to "Für dieses Spiel gibt es keine Inhalte.",
                APP_LEMA to "Das Partyspiel, das in euren Handys Platz hat",
                MENU_JUGAR to "SPIELEN",
                MENU_SEGUIR_PARTIDA to "PARTIE FORTSETZEN",
                MENU_PARTIDA_NUEVA to "NEUE PARTIE",
                MENU_COMO_JUGAR to "SO WIRD GESPIELT",
                MENU_AJUSTES to "EINSTELLUNGEN",
                MENU_SALON to "MIT MEHREREN HANDYS SPIELEN",
                MENU_TOUR to "TOUR ANSEHEN",
                MODO_TITULO to "Wie spielen wir?",
                MODO_SUBTITULO to "Lässt sich bei jeder neuen Partie ändern.",
                MODO_EQUIPOS to "In Teams",
                MODO_EQUIPOS_DETALLE to
                    "2 bis 6 Teams. Jedes Team hat seine Figur, und wer vorspielt, wechselt " +
                    "durch. Die klassische Variante, und die lauteste.",
                MODO_INDIVIDUAL to "Einzeln",
                MODO_INDIVIDUAL_DETALLE to
                    "2 bis 8 Personen, jede mit eigener Figur und ohne Teams. Bei den " +
                    "Aufgaben zum Vorspielen spielt vor, wer dran ist, und der Rest rät.",
                MODO_SOLITARIO to "Solo-Herausforderung",
                MODO_SOLITARIO_DETALLE to
                    "Du gegen die Uhr: eine Reihe Aufgaben in Folge und ein persönlicher " +
                    "Rekord zum Schlagen. Ohne Spielbrett und nur mit den Spielen, die " +
                    "ohne Publikum funktionieren.",
                PARTICIPANTES_TITULO_EQUIPOS to "Teams",
                PARTICIPANTES_TITULO_INDIVIDUAL to "Spieler",
                PARTICIPANTES_TITULO_SOLITARIO to "Wie heißt du?",
                PARTICIPANTES_SUBTITULO_EQUIPOS to
                    "%1\$d bis %2\$d Teams. Schreib auf, wer in welchem spielt, " +
                    "und das Handy sagt, wer vorspielen muss.",
                PARTICIPANTES_SUBTITULO_INDIVIDUAL to "%1\$d bis %2\$d Personen, jede mit eigener Figur.",
                PARTICIPANTES_SUBTITULO_SOLITARIO to
                    "Nur damit dein Name beim Ergebnis steht. Nichts davon " +
                    "verlässt das Handy.",
                PARTICIPANTES_ANADIR_EQUIPO to "+  TEAM HINZUFÜGEN",
                PARTICIPANTES_ANADIR_JUGADOR to "+  SPIELER HINZUFÜGEN",
                PARTICIPANTES_NUEVO_JUGADOR to "Spieler hinzufügen…",
                PARTICIPANTES_SIN_JUGADORES to "Keine Namen eingetragen: das Spiel nennt nur den Teamnamen.",
                PARTICIPANTES_NOMBRE_EQUIPO to "Teamname",
                PARTICIPANTES_NOMBRE_JUGADOR to "Name",
                PARTICIPANTES_QUITAR to "Entfernen",
                PARTICIPANTES_EQUIPO_POR_DEFECTO to "Team %1\$d",
                PARTICIPANTES_JUGADOR_POR_DEFECTO to "Spieler %1\$d",
                PARTICIPANTES_EQUIPO_1_POR_DEFECTO to "Die Cracks",
                PARTICIPANTES_EQUIPO_2_POR_DEFECTO to "Die Bestien",
                PARTICIPANTES_DESDE_SALON to "Von einem anderen Handy beigetreten",
                TABLERO_TIRAR to "WÜRFELN",
                TABLERO_TURNO_DE to "%1\$s IST DRAN",
                TABLERO_LE_TOCA to "%1\$s ist dran",
                TABLERO_CASILLA to "Feld %1\$d",
                TABLERO_SALIDA to "START",
                TABLERO_META to "ZIEL",
                TABLERO_MARCADOR to "Punktestand",
                TABLERO_ABANDONAR to "Partie abbrechen",
                TABLERO_ABANDONAR_PREGUNTA to "Der Fortschritt dieser Partie geht verloren. Wirklich beenden?",
                TABLERO_ESPERANDO_HUB to "Warten auf das Handy, das die Partie führt…",
                TABLERO_AVANZA_CASILLAS to "Rückt %1\$d vor",
                CASILLA_COMODIN to "JOKER",
                CASILLA_COMODIN_DETALLE to "Der Gegner wählt die Aufgabe für dich aus. Ohne Gnade.",
                CASILLA_TODOS to "ALLE SPIELEN",
                CASILLA_TODOS_DETALLE to
                    "Dieselbe Aufgabe für den ganzen Tisch. Wer richtig liegt, rückt ein " +
                    "Feld vor.",
                CASILLA_META_AVISO to "Endaufgabe: gewonnen wird nur, wer sie besteht.",
                COMODIN_TITULO to "Jokerfeld",
                COMODIN_ELIGE to "%1\$s wählt die Aufgabe",
                PRUEBA_FINAL to "🏁  ENDAUFGABE",
                PRUEBA_JUEGAN_TODOS to "👥  ALLE SPIELEN",
                PRUEBA_LE_TOCA_ACTUAR_A to "VORSPIELEN MUSS",
                PRUEBA_QUIEN_DECIDA to "wen %1\$s bestimmt",
                PRUEBA_SOLO_MIRE_ESA_PERSONA to "👀  Nur diese Person darf auf den Bildschirm schauen",
                PRUEBA_MIRA_TU_MOVIL to "📱  Schau auf dein eigenes Handy: das Wort ist dort angekommen",
                PRUEBA_SEGUNDOS to "⏱  %1\$d Sekunden",
                PRUEBA_CUANDO_TEMA to "In welchem Jahr?",
                PRUEBA_CUANDO_RESPUESTA to "Es war %1\$d.",
                PRUEBA_ACIERTOS_DE to "%1\$d von %2\$d",
                PRUEBA_SALTAR to "ÜBERSPRINGEN",
                PRUEBA_ACERTADA to "✓  ERRATEN",
                PRUEBA_FALLADA to "✗  DANEBEN",
                PRUEBA_PROHIBIDA to "🚫  VERBOTEN",
                PRUEBA_TERMINAR to "Aufgabe beenden",
                PRUEBA_ACIERTOS to "✔  %1\$d",
                PRUEBA_VEREDICTO_TITULO to "Hat es geklappt?",
                PRUEBA_VEREDICTO_LOGRADO to "✓  JA!",
                PRUEBA_VEREDICTO_NO_LOGRADO to "✗  NEIN",
                PRUEBA_VEREDICTO_DECIDE_MESA to "Der Tisch entscheidet, nicht die App.",
                PRUEBA_VEREDICTO_DECIDE_SOLO to "Hier schaut niemand zu: sei ehrlich zu dir selbst.",
                PRUEBA_BORRAR_DIBUJO to "Alles löschen",
                PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA to "NUR WER ZEICHNET, SCHAUT HIN",
                PRUEBA_DIBUJO_TIEMPO_AL_PULSAR to "Die Zeit läuft, sobald du auf den Knopf drückst.",
                PRUEBA_DIBUJO_EMPEZAR to "🎨   ZEICHNEN STARTEN",
                PRUEBA_DIBUJO_LIENZO_VACIO to "Zeichne hier mit dem Finger",
                PRUEBA_DIBUJO_ESPIAR to "Gedrückt halten, um das Wort wieder zu sehen",
                PRUEBA_DIBUJO_GOMA to "Radiergummi",
                PRUEBA_DESHACER to "Rückgängig",
                PRUEBA_PINCEL to "Strichstärke",
                PRUEBA_COLOR to "Farbe",
                PRUEBA_RETO_OBJETIVO to "Ihr müsst auf %1\$d kommen",
                PRUEBA_RETO_LLEVAMOS to "Bisher",
                PRUEBA_RETO_UNA_MAS to "+1  NOCH EINS!",
                PRUEBA_RETO_CONSEGUIDO to "GESCHAFFT!",
                PRUEBA_RETO_TOCA_PARA_SUMAR to "von %1\$d  ·  antippen zum Zählen",
                PRUEBA_RETO_RENDIRSE to "WIR GEBEN AUF",
                PRUEBA_RETO_NOTA to "Jede Antwort zählt einmal. Wiederholungen zählen nicht.",
                PRUEBA_ORDENA_AYUDA to "Tippe sie in der richtigen Reihenfolge an, von der ersten bis zur letzten.",
                PRUEBA_ORDENA_COMPROBAR to "PRÜFEN",
                PRUEBA_ORDENA_CORRECTO to "Die richtige Reihenfolge war:",
                PRUEBA_VF_VERDADERO to "WAHR",
                PRUEBA_VF_FALSO to "FALSCH",
                PRUEBA_VF_ERA_VERDAD to "Es war wahr",
                PRUEBA_VF_ERA_MENTIRA to "Es war falsch",
                PRUEBA_EMOJIS_AYUDA to "Was ist das, in Emojis geschrieben?",
                PRUEBA_EMOJIS_ERA to "Es war: %1\$s",
                PRUEBA_CANTA_PISTA to "Fang hier an",
                PRUEBA_TRABALENGUAS_AYUDA to "Sag ihn ganz und ohne zu stolpern.",
                PRUEBA_DESAFIO_AYUDA to "Mal sehen, wie du da rauskommst.",
                RONDA_TODOS_PASA_MOVIL to "Gib das Handy weiter, ohne die Antwort der anderen anzusehen.",
                RONDA_TODOS_RESPONDE to "%1\$s antwortet",
                RONDA_TODOS_RESUMEN to "Wer richtig lag",
                RONDA_TODOS_NADIE to "Niemand. Keine Figur bewegt sich.",
                RONDA_TODOS_EN_TU_MOVIL to "Antworte auf deinem eigenen Handy.",
                RONDA_TODOS_ESPERANDO to "Warten auf die anderen…",
                RONDA_TODOS_PASAD_A to "GEBT DAS HANDY AN",
                RONDA_TODOS_PROGRESO to "%1\$d von %2\$d. Bis zum Schluss erfährt niemand, wer richtig lag.",
                RONDA_TODOS_SIN_RESPUESTA to "Zeit vorbei, ohne Antwort.",
                RONDA_TODOS_GUARDADA to "Antwort gespeichert. Sag sie noch nicht laut.",
                RONDA_TODOS_CORRECTA_ERA to "DIE RICHTIGE ANTWORT WAR",
                RONDA_TODOS_SIN_RESPONDER to "keine Antwort",
                RONDA_TODOS_VER_RESULTADOS to "ERGEBNISSE ANSEHEN",
                RESULTADO_SUPERADA to "BESTANDEN!",
                RESULTADO_NO_HA_PODIDO_SER to "DIESMAL NICHT",
                RESULTADO_FINAL_SUPERADA to "ENDAUFGABE BESTANDEN!",
                RESULTADO_AVANZAS_A to "Ihr rückt auf Feld %1\$d vor",
                RESULTADO_TE_QUEDAS_EN to "Ihr bleibt auf Feld %1\$d",
                RESULTADO_LLEGADA_A_META to "Ihr habt das Ziel erreicht!",
                RESULTADO_SIGUIENTE_TURNO to "NÄCHSTER ZUG",
                RESULTADO_VER_RESULTADO to "ERGEBNIS ANSEHEN",
                VICTORIA_TITULO to "Partie beendet!",
                VICTORIA_GANADOR to "%1\$s gewinnt",
                VICTORIA_CLASIFICACION to "Endstand",
                VICTORIA_OTRA_PARTIDA to "NOCH EINE PARTIE",
                VICTORIA_AL_MENU to "ZURÜCK ZUM MENÜ",
                VICTORIA_SOLITARIO_TITULO to "Herausforderung beendet",
                VICTORIA_SOLITARIO_PUNTOS to "%1\$d Punkte",
                VICTORIA_SOLITARIO_MEJOR to "Dein Rekord: %1\$d",
                VICTORIA_SOLITARIO_RECORD to "Neuer persönlicher Rekord!",
                SOLITARIO_TITULO to "Solo-Herausforderung",
                SOLITARIO_SUBTITULO to "Eine Reihe Aufgaben. Du gegen die Uhr.",
                SOLITARIO_PROGRESO to "Aufgabe %1\$d von %2\$d",
                SOLITARIO_RONDAS to "Aufgaben",
                SOLITARIO_EMPEZAR to "HERAUSFORDERUNG STARTEN",
                SOLITARIO_MEJOR_MARCA to "Rekord: %1\$d",
                SOLITARIO_SIN_MARCA to "Noch kein Rekord. Setz den ersten.",
                AJUSTES_TITULO to "Einstellungen",
                AJUSTES_SUBTITULO to "Werden für die nächste Partie gespeichert.",
                AJUSTES_APARIENCIA to "Aussehen",
                AJUSTES_TEMA to "Design",
                AJUSTES_TEMA_DETALLE to "Sechs Designs: drei helle und drei dunkle.",
                AJUSTES_TEMA_SISTEMA to "Dem System folgen",
                AJUSTES_IDIOMA to "Sprache",
                AJUSTES_IDIOMA_DETALLE to "Dreizehn Sprachen verfügbar.",
                AJUSTES_PARTIDA to "Die Partie",
                AJUSTES_RITMO to "Tempo der Aufgaben",
                AJUSTES_RITMO_DETALLE to "Wie viel Zeit es pro Aufgabe gibt.",
                AJUSTES_MODALIDAD to "Spielmodus",
                AJUSTES_MODALIDAD_DETALLE to
                    "Wie viele Felder das Brett hat und wie viele Runden die " +
                    "Solo-Runde dauert.",
                AJUSTES_JUEGOS_ACTIVOS to "Spiele der Partie",
                AJUSTES_JUEGOS_ACTIVOS_DETALLE to
                    "Nimm die heraus, die euch nicht gefallen, dann erscheinen " +
                    "sie nicht mehr auf dem Brett.",
                AJUSTES_JUEGOS_CONTADOR to "%1\$d von %2\$d aktiv",
                AJUSTES_JUEGOS_MINIMO to "Mindestens ein Spiel muss aktiv bleiben.",
                AJUSTES_SONIDO to "Ton",
                AJUSTES_SONIDO_DETALLE to "Countdown-Pieptöne und Hinweise",
                AJUSTES_VIBRACION to "Vibration",
                AJUSTES_VIBRACION_DETALLE to "Treffer, Fehler und Zeitende",
                AJUSTES_ANIMACIONES to "Animationen",
                AJUSTES_ANIMACIONES_DETALLE to "Ausschalten, wenn du die Oberfläche lieber ruhig magst",
                AJUSTES_DATOS to "Deine Daten",
                AJUSTES_EXPORTAR to "Exportieren",
                AJUSTES_EXPORTAR_DETALLE to "Speichert Teams, Einstellungen und Rekorde in einer Datei",
                AJUSTES_IMPORTAR to "Importieren",
                AJUSTES_IMPORTAR_DETALLE to "Holt eine früher gespeicherte Kopie zurück",
                AJUSTES_MAS to "Mehr",
                AJUSTES_APOYAR to "Die Entwicklung unterstützen",
                AJUSTES_APOYAR_DETALLE to "Lade mich auf einen Kaffee ein, wenn es dir nützt",
                AJUSTES_COMPARTIR to "Funny teilen",
                AJUSTES_COMPARTIR_DETALLE to "Gib es an jemanden weiter, der Spaß daran hätte",
                AJUSTES_AYUDA to "Hilfe",
                AJUSTES_AYUDA_DETALLE to "So wird gespielt und häufige Fragen",
                AJUSTES_TOUR to "Geführte Tour",
                AJUSTES_TOUR_DETALLE to "Alle zwölf Spiele und drei Modi, erklärt",
                AJUSTES_ACERCA_DE to "Über die App",
                AJUSTES_ACERCA_DE_DETALLE to "Version, Lizenzen und Datenschutz",
                TEMA_MODO_CLARO to "Hell",
                TEMA_MODO_OSCURO to "Dunkel",
                TEMA_FIESTA to "Party",
                TEMA_NEON to "Neon",
                TEMA_MEDIANOCHE to "Mitternacht",
                TEMA_PAPEL to "Papier",
                TEMA_MENTA to "Minze",
                TEMA_ATARDECER to "Abendrot",
                IDIOMA_TITULO to "Sprache",
                IDIOMA_SEGUIR_SISTEMA to "Die des Handys",
                IDIOMA_SUBTITULO to "Die Änderung greift sofort.",
                CAFE_TITULO to "Einen Kaffee?",
                CAFE_TEXTO to
                    "Diese App ist kostenlos, hat keine Werbung und sammelt deine Daten nicht. Wenn " +
                    "sie dir nützt, kannst du mich auf einen Kaffee einladen.",
                CAFE_BOTON to "Lade mich auf einen Kaffee ein · 1 €",
                CAFE_NO_VOLVER to "Nicht mehr anzeigen",
                CAFE_OTRO_DISPOSITIVO to "Von einem anderen Gerät",
                CAFE_QR_DESCRIPCION to "QR-Code mit dem Link, um den Autor der App auf einen Kaffee einzuladen",
                CAFE_ILUSTRACION_DESCRIPCION to "Zeichnung einer Kaffeetasse mit Dampf",
                CAFE_ENLACE_COPIADO to "Link kopiert",
                CAFE_GRACIAS to "Danke fürs Vorbeischauen 🙂",
                CAFE_SIN_DESBLOQUEOS to "Im Spiel ändert sich dadurch nichts: Funny ist vollständig und bleibt es.",
                CAFE_ENTRADA_AJUSTES to "Die Entwicklung unterstützen",
                CAFE_NO_DISPONIBLE to "Auf diesem Gerät nicht verfügbar.",
                COPIA_TITULO to "Kopie deiner Daten",
                COPIA_EXPORTAR_HECHO to "Kopie gespeichert.",
                COPIA_EXPORTAR_ERROR to "Die Kopie konnte nicht gespeichert werden.",
                COPIA_IMPORTAR_TITULO to "Eine Kopie importieren",
                COPIA_IMPORTAR_AVISO to
                    "Bevor etwas angefasst wird, wird eine Kopie von dem gespeichert, was " +
                    "du jetzt hast, damit man immer zurück kann.",
                COPIA_IMPORTAR_FUSIONAR to "Zu meinen Daten hinzufügen",
                COPIA_IMPORTAR_REEMPLAZAR to "Alles ersetzen",
                COPIA_IMPORTAR_HECHO to "Daten importiert.",
                COPIA_IMPORTAR_ERROR_FORMATO to
                    "Diese Datei sieht nicht wie eine Funny-Kopie aus. Es wurde " +
                    "nichts geändert.",
                COPIA_IMPORTAR_ERROR_VERSION to
                    "Diese Kopie kommt von einer neueren Funny-Version. " +
                    "Aktualisiere die App und versuche es erneut.",
                COPIA_IMPORTAR_RESPALDO to "Vorher wurde eine Sicherung gespeichert.",
                COPIA_CABECERA_DETALLE to "Kopie vom %1\$s · Funny %2\$s",
                AYUDA_TITULO to "Hilfe",
                AYUDA_SUBTITULO to "Alles, damit niemand den Faden verliert.",
                AYUDA_QUE_ES_TITULO to "Was ist Funny?",
                AYUDA_QUE_ES_TEXTO to
                    "Ein Partyspiel mit zwölf verschiedenen Aufgaben. Gespielt wird mit einem " +
                    "Handy, das herumgeht, oder, wenn mehrere von euch ein Handy haben, indem " +
                    "man sie miteinander verbindet. Internet braucht man nie.",
                AYUDA_COMO_SE_JUEGA_TITULO to "Die Partie",
                AYUDA_PARTIDA_1 to "Wählt den Modus: in Teams, einzeln oder die Solo-Herausforderung.",
                AYUDA_PARTIDA_2 to "Jedes Team oder jeder Spieler hat eine Figur, und alle starten am START.",
                AYUDA_PARTIDA_3 to "In deinem Zug würfelst du und rückst 1 bis 3 Felder vor.",
                AYUDA_PARTIDA_4 to
                    "Das Feld, auf dem du landest, bestimmt die Aufgabe. Bestanden bleibst du, " +
                    "sonst gehst du zurück.",
                AYUDA_PARTIDA_5 to "Gewonnen hat, wer das ZIEL erreicht und die Endaufgabe besteht.",
                AYUDA_UN_MOVIL_TITULO to "Mit einem einzigen Handy",
                AYUDA_UN_MOVIL_1 to "Das Handy geht herum: die App sagt immer, wer dran ist.",
                AYUDA_UN_MOVIL_2 to
                    "Bei Pantomime, Tabu, Zeichnen, Singen und Mutproben schaut nur hin, wer " +
                    "vorspielt.",
                AYUDA_UN_MOVIL_3 to "Bei den Antwortaufgaben darf der Bildschirm allen gezeigt werden.",
                AYUDA_VARIOS_MOVILES_TITULO to "Mit mehreren Handys",
                AYUDA_VARIOS_MOVILES_1 to
                    "Ein Handy ist der Tisch (der Hub) und die anderen verbinden sich " +
                    "damit. Weder WLAN noch mobile Daten nötig.",
                AYUDA_VARIOS_MOVILES_2 to
                    "Das geheime Wort kommt nur auf dem Handy an, das vorspielt, damit es " +
                    "niemand versehentlich sieht.",
                AYUDA_VARIOS_MOVILES_3 to
                    "Auf den Feldern «alle spielen» antwortet jeder gleichzeitig auf " +
                    "seinem eigenen Handy.",
                AYUDA_FAQ_TITULO to "Häufige Fragen",
                AYUDA_FAQ_1_P to "Braucht man Internet?",
                AYUDA_FAQ_1_R to
                    "Nein. Funny funktioniert komplett offline, und mehrere Handys zu verbinden " +
                    "nutzt Bluetooth und WLAN Direct zwischen ihnen, ohne über ein Netz zu gehen.",
                AYUDA_FAQ_2_P to "Kann man allein spielen?",
                AYUDA_FAQ_2_R to
                    "Ja: die Solo-Herausforderung ist eine Reihe Aufgaben in Folge mit " +
                    "persönlichem Rekord. Es kommen nur die Spiele vor, die kein Publikum " +
                    "brauchen.",
                AYUDA_FAQ_3_P to "Kostet etwas? Ist etwas gesperrt?",
                AYUDA_FAQ_3_R to
                    "Nichts ist gesperrt und es gibt nichts extra zu bekommen. Wenn es dir " +
                    "gefällt, kannst du mich in den Einstellungen auf einen Kaffee einladen, und " +
                    "im Spiel ändert das absolut nichts.",
                AYUDA_FAQ_4_P to "Sammelt die App Daten?",
                AYUDA_FAQ_4_R to
                    "Nein. Keine Analyse, keine Konten, keine Werbung. Teams und Einstellungen " +
                    "liegen nur auf deinem Handy und verlassen es nur, wenn du selbst eine Kopie " +
                    "exportierst.",
                AYUDA_FAQ_5_P to "Kann ich ändern, welche Spiele vorkommen?",
                AYUDA_FAQ_5_R to
                    "Ja, unter Einstellungen → Spiele der Partie. Die herausgenommenen erscheinen " +
                    "nicht mehr auf dem Brett.",
                AYUDA_PROBLEMAS_TITULO to "Wenn etwas nicht geht",
                AYUDA_PROBLEMAS_TEXTO to
                    "Schließe die App und öffne sie erneut: die laufende Partie bleibt " +
                    "erhalten. Wenn das Problem bleibt, exportiere deine Daten vor einer " +
                    "Neuinstallation und schreib uns, was passiert ist.",
                AYUDA_ESCRIBENOS to "Dem Autor schreiben",
                ACERCA_TITULO to "Über die App",
                ACERCA_VERSION to "Version",
                ACERCA_COMPILACION to "Build",
                ACERCA_FECHA to "Datum",
                ACERCA_COMMIT to "Commit",
                ACERCA_LICENCIA to "Lizenz",
                ACERCA_LICENCIAS_TERCEROS to "Lizenzen Dritter",
                ACERCA_PRIVACIDAD to "Datenschutzerklärung",
                ACERCA_CONTACTO to "Kontakt",
                ACERCA_CODIGO to "Quellcode",
                ACERCA_SIN_ANUNCIOS to "Ohne Werbung, ohne Analyse und ohne Konten.",
                ACERCA_AUTOR to "Ghato Studio · Brais Galdo",
                TOUR_TITULO to "Geführte Tour",
                TOUR_SUBTITULO to "Die zwölf Spiele und drei Modi, in zwei Minuten.",
                TOUR_EMPEZAR to "TOUR STARTEN",
                TOUR_SALTAR to "Überspringen",
                TOUR_ANTERIOR to "Zurück",
                TOUR_SIGUIENTE to "Weiter",
                TOUR_TERMINAR to "LOS SPIELEN!",
                TOUR_PROGRESO to "%1\$d von %2\$d",
                TOUR_BIENVENIDA_TITULO to "Willkommen bei Funny",
                TOUR_BIENVENIDA_TEXTO to
                    "Zwölf Spiele, drei Arten zu spielen und kein Internet nötig. In zwei " +
                    "Minuten erkläre ich dir alles; du kannst jederzeit überspringen.",
                TOUR_MODOS_TITULO to "Drei Arten zu spielen",
                TOUR_MODOS_TEXTO to
                    "In Teams ist der Klassiker: 2 bis 6 Teams, und wer vorspielt, wechselt " +
                    "durch. Einzeln ist dasselbe, aber jede Person hat ihre Figur, 2 bis 8. Und " +
                    "die Solo-Herausforderung ist eine Reihe Aufgaben gegen die Uhr, allein, " +
                    "mit persönlichem Rekord.",
                TOUR_TABLERO_TITULO to "Das Spielbrett",
                TOUR_TABLERO_TEXTO to
                    "Jede Figur startet am START. In deinem Zug würfelst du, rückst 1 bis 3 " +
                    "Felder vor, und das Feld bestimmt die Aufgabe. Bestanden bleibst du " +
                    "dort; daneben gehst du zurück. Gewonnen hat, wer das ZIEL erreicht und " +
                    "die Endaufgabe besteht.",
                TOUR_CASILLAS_TITULO to "Die Sonderfelder",
                TOUR_CASILLAS_TEXTO to
                    "🃏 Joker: der Gegner wählt die Aufgabe für dich aus.\n👥 Alle spielen: " +
                    "dieselbe Aufgabe für den ganzen Tisch, und wer richtig liegt, rückt ein " +
                    "Feld vor.\n🏁 Ziel: eine zufällige Endaufgabe. Ohne sie zu bestehen, " +
                    "gewinnt man nicht.",
                TOUR_JUEGOS_TITULO to "Die zwölf Spiele",
                TOUR_JUEGOS_TEXTO to
                    "Das sind alle. Du kannst die, die euch nicht gefallen, in den " +
                    "Einstellungen abschalten.",
                TOUR_SALON_TITULO to "Mehrere Handys gleichzeitig",
                TOUR_SALON_TEXTO to
                    "Ein Handy ist der Tisch und die anderen verbinden sich per Bluetooth oder " +
                    "WLAN Direct damit, ohne Internet. Das bringt genau das, was zählt: das " +
                    "geheime Wort kommt nur auf dem Handy an, das vorspielt, und auf den " +
                    "Feldern «alle spielen» antwortet jeder gleichzeitig auf seinem eigenen.",
                TOUR_AJUSTES_TITULO to "Ganz nach deinem Geschmack",
                TOUR_AJUSTES_TEXTO to
                    "Sechs Designs, dreizehn Sprachen, drei Tempi und vier Spielmodi: " +
                    "schnell, normal, extrem und einer nach Maß. Du kannst auch Spiele " +
                    "abschalten, Ton und Vibration ausmachen und deine Daten in eine Datei " +
                    "speichern oder zurückholen.",
                TOUR_FINAL_TITULO to "Das war's",
                TOUR_FINAL_TEXTO to
                    "Du kannst das jederzeit unter Einstellungen → Geführte Tour wieder " +
                    "ansehen. Viel Spaß.",
                SALON_TITULO to "Mehrere Handys",
                SALON_SUBTITULO to "Ohne Internet: sie verbinden sich untereinander.",
                SALON_CREAR to "TISCH SEIN",
                SALON_CREAR_DETALLE to
                    "Dieses Handy führt die Partie und zeigt das Brett. Das ist das, was auf " +
                    "dem Tisch liegen bleibt.",
                SALON_UNIRSE to "EINEM TISCH BEITRETEN",
                SALON_UNIRSE_DETALLE to "Dieses Handy bleibt in deiner Hand und bekommt deine Aufgaben privat.",
                SALON_TU_NOMBRE to "Dein Name",
                SALON_HUB_TITULO to "Du bist der Tisch",
                SALON_HUB_ESPERANDO to "Warten auf Verbindungen…",
                SALON_HUB_CONECTADOS to "Verbunden",
                SALON_HUB_EMPEZAR to "PARTIE STARTEN",
                SALON_CLIENTE_TITULO to "Suche nach einem Tisch",
                SALON_CLIENTE_BUSCANDO to "Suche nach Tischen in der Nähe…",
                SALON_CLIENTE_SIN_SALONES to
                    "Noch keiner zu sehen. Lass das andere Handy «Tisch sein» öffnen " +
                    "und wartet ein paar Sekunden.",
                SALON_CLIENTE_CONECTANDO to "Verbinden…",
                SALON_CLIENTE_CONECTADO to "Verbunden",
                SALON_CLIENTE_ESPERA to "Du bist drin. Schau auf den Tisch: dort fängt die Partie an.",
                SALON_SALIR to "Raum verlassen",
                SALON_DESCONECTADO to "Die Verbindung zum Tisch ist abgebrochen.",
                SALON_ERROR_PERMISOS to "Es fehlen Berechtigungen, um die Handys neben dir zu finden.",
                SALON_PEDIR_PERMISOS to "BERECHTIGUNGEN GEBEN",
                SALON_PERMISOS_EXPLICACION to
                    "Um die Handys in der Nähe zu finden, verlangt Android die " +
                    "Berechtigung für Geräte in der Nähe und auf älteren Versionen " +
                    "auch den Standort. Funny fragt nie ab, wo du bist, und speichert " +
                    "es nirgends: das ist der Preis, den das System für Bluetooth und " +
                    "WLAN Direct verlangt.",
                SALON_ERROR_BLUETOOTH to "Schalte Bluetooth ein, damit sich die Handys verbinden können.",
                SALON_ERROR_UBICACION to
                    "Schalte den Standort ein: Android verlangt ihn für die Suche über " +
                    "Bluetooth.",
                SALON_ERROR_SERVICIOS to
                    "Diesem Handy fehlen die Google-Dienste, die zum Verbinden nötig sind. " +
                    "Ihr könnt weiterspielen, indem ihr ein einzelnes Handy herumgibt.",
                SALON_COMO_FUNCIONA to "Wie funktioniert das?",
                SALON_ESTE_DISPOSITIVO to "Dieses Handy",
                SALON_ROL_HUB to "Tisch",
                SALON_ROL_MANDO to "Controller",
                SALON_SIN_RED to "Internet wird zu keinem Zeitpunkt benutzt.",
                SALON_SIN_NOMBRE to "Ohne Namen",
                SALON_TU_TURNO to "Du bist dran!",
                SALON_MIRA_EL_HUB to "Schau auf das Handy auf dem Tisch.",
                JUEGO_MIMICA_NOMBRE to "Pantomime",
                JUEGO_MIMICA_LEMA to "Stell es ohne Worte dar",
                JUEGO_MIMICA_INSTRUCCIONES to
                    "Eine Person stellt das Wort mit Gesten dar. Verboten sind " +
                    "Sprechen, Geräusche und auf Dinge im Raum zeigen.",
                JUEGO_DIBUJO_NOMBRE to "Zeichnen",
                JUEGO_DIBUJO_LEMA to "Zeichne es auf den Bildschirm",
                JUEGO_DIBUJO_INSTRUCCIONES to
                    "Eine Person zeichnet auf dem Handybildschirm und die anderen " +
                    "raten. Keine Buchstaben, keine Zahlen, keine Gesten.",
                JUEGO_CUANDO_NOMBRE to "Wann?",
                JUEGO_CUANDO_LEMA to "In welchem Jahr war das?",
                JUEGO_CUANDO_INSTRUCCIONES to
                    "Ein Ereignis erscheint mit vier möglichen Jahren. Ihr müsst " +
                    "entscheiden, in welchem es war.",
                JUEGO_PREGUNTAS_NOMBRE to "Fragen",
                JUEGO_PREGUNTAS_LEMA to "Allgemeinwissen",
                JUEGO_PREGUNTAS_INSTRUCCIONES to
                    "Eine Frage mit vier Antworten. Es wird nur eine gewählt und " +
                    "ändern geht nicht.",
                JUEGO_TABU_NOMBRE to "Tabu",
                JUEGO_TABU_LEMA to "Beschreib es, ohne es zu sagen",
                JUEGO_TABU_INSTRUCCIONES to
                    "Das Wort muss beschrieben werden, ohne eines der verbotenen Wörter " +
                    "oder Wörter derselben Familie zu benutzen.",
                JUEGO_RETO_NOMBRE to "Blitzrunde",
                JUEGO_RETO_LEMA to "Aufzählen gegen die Uhr",
                JUEGO_RETO_INSTRUCCIONES to
                    "Nennt weiter Dinge aus der angegebenen Kategorie, bis das Ziel " +
                    "erreicht ist, bevor die Zeit abläuft.",
                JUEGO_EMOJIS_NOMBRE to "Emojis",
                JUEGO_EMOJIS_LEMA to "Entschlüssle es",
                JUEGO_EMOJIS_INSTRUCCIONES to
                    "Ein Film, ein Lied oder ein Sprichwort nur mit Emojis " +
                    "geschrieben, mit vier möglichen Antworten.",
                JUEGO_VERDADERO_FALSO_NOMBRE to "Glaubst du das?",
                JUEGO_VERDADERO_FALSO_LEMA to "Wahr oder falsch",
                JUEGO_VERDADERO_FALSO_INSTRUCCIONES to
                    "Vier seltsame Behauptungen hintereinander. Bei jeder " +
                    "muss man sagen, ob sie wahr oder falsch ist, und danach " +
                    "wird erklärt, warum.",
                JUEGO_TRABALENGUAS_NOMBRE to "Zungenbrecher",
                JUEGO_TRABALENGUAS_LEMA to "Sag ihn ohne zu stolpern",
                JUEGO_TRABALENGUAS_INSTRUCCIONES to
                    "Der Zungenbrecher muss so oft wie verlangt gesagt werden, " +
                    "ganz und ohne Fehler. Der Tisch urteilt.",
                JUEGO_ORDENA_NOMBRE to "Sortieren",
                JUEGO_ORDENA_LEMA to "Bring es in die Reihe",
                JUEGO_ORDENA_INSTRUCCIONES to
                    "Vier Dinge in falscher Reihenfolge und ein Kriterium. Sie müssen " +
                    "in der richtigen Reihenfolge angetippt werden.",
                JUEGO_CANTA_NOMBRE to "Singen",
                JUEGO_CANTA_LEMA to "Sing weiter",
                JUEGO_CANTA_INSTRUCCIONES to
                    "Titel und Interpret erscheinen, und du musst den Refrain " +
                    "anstimmen. Der Tisch urteilt, so großzügig wie er mag.",
                JUEGO_DESAFIO_NOMBRE to "Mutprobe",
                JUEGO_DESAFIO_LEMA to "Wag es",
                JUEGO_DESAFIO_INSTRUCCIONES to
                    "Eine kleine Mutprobe vor allen. Nichts Gefährliches, nichts " +
                    "Erniedrigendes: nur die gute Art von Albernheit. Der Tisch " +
                    "urteilt.",
                RITMO_RAPIDO to "Schnell",
                RITMO_NORMAL to "Normal",
                RITMO_TRANQUILO to "Gemütlich",
                MODALIDAD_RAPIDA to "Schnelle Partie",
                MODALIDAD_NORMAL to "Normale Partie",
                MODALIDAD_EXTREMA to "Extreme Partie",
                MODALIDAD_PERSONALIZADA to "Nach meiner Art",
                MODALIDAD_RAPIDA_DETALLE to "Für eine Runde zwischendurch oder zum Ausprobieren",
                MODALIDAD_NORMAL_DETALLE to "Die übliche, die am besten passt",
                MODALIDAD_EXTREMA_DETALLE to "Für einen ganzen Abend, ohne Eile",
                MODALIDAD_PERSONALIZADA_DETALLE to "Du legst die Zahlen fest",
                MODALIDAD_RESUMEN to "%1\$d Felder · %2\$d Runden · etwa %3\$d Min.",
                MODALIDAD_CASILLAS to "Felder bis zum Ziel",
                MODALIDAD_PRUEBAS to "Runden pro Partie",
                MODALIDAD_PRUEBAS_NOTA to "Zählen nur in der Solo-Runde",
                A11Y_DADO to "Würfel: %1\$d",
                A11Y_FICHA to "Figur von %1\$s auf Feld %2\$d",
                A11Y_CASILLA to "Feld %1\$d, %2\$s",
                A11Y_VOLVER to "Zurück zum vorherigen Bildschirm",
                A11Y_CERRAR to "Schließen",
                A11Y_LIENZO_DIBUJO to "Fläche zum Zeichnen mit dem Finger",
                A11Y_TEMA_MUESTRA to "Farbmuster des Designs %1\$s",
                A11Y_BANDERA_IDIOMA to "Sprache %1\$s",
                A11Y_REDUCIR to "Verringern",
                A11Y_AUMENTAR to "Erhöhen",
            ),
        plurales =
            mapOf(
                ClavePlural.CASILLAS to
                    mapOf(
                        CategoriaPlural.ONE to "%d Feld",
                        CategoriaPlural.OTHER to "%d Felder",
                    ),
                ClavePlural.SEGUNDOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d Sekunde",
                        CategoriaPlural.OTHER to "%d Sekunden",
                    ),
                ClavePlural.DISPOSITIVOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d Handy verbunden",
                        CategoriaPlural.OTHER to "%d Handys verbunden",
                    ),
                ClavePlural.PUNTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d Punkt",
                        CategoriaPlural.OTHER to "%d Punkte",
                    ),
                ClavePlural.ACIERTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d Treffer",
                        CategoriaPlural.OTHER to "%d Treffer",
                    ),
                ClavePlural.REPETICIONES to
                    mapOf(
                        CategoriaPlural.ONE to "%d Mal",
                        CategoriaPlural.OTHER to "%d Mal",
                    ),
            ),
    )
