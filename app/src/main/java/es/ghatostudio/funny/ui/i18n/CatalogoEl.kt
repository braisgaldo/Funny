package es.ghatostudio.funny.ui.i18n

import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.dominio.textos.Clave.*

/**
 * Catálogo en griego.
 *
 * Nota sobre la donación: se evita «αγοράζω» (comprar) y «πληρώνω» (pagar),
 * igual que en los demás idiomas. Se usa «κέρασέ με έναν καφέ», que dice lo
 * mismo sin enmarcarlo como una compra. Lo comprueba `PruebaCatalogos`.
 */
internal val catalogoGriego =
    Catalogo(
        idioma = Idioma.GRIEGO,
        textos =
            mapOf<Clave, String>(
                ACCION_VOLVER to "Πίσω",
                ACCION_CERRAR to "Κλείσιμο",
                ACCION_CANCELAR to "Άκυρο",
                ACCION_ACEPTAR to "ΟΚ",
                ACCION_CONTINUAR to "Συνέχεια",
                ACCION_EMPEZAR to "Ξεκινάμε!",
                ACCION_LISTO to "Έτοιμος",
                ACCION_BORRAR to "Διαγραφή",
                ACCION_ANADIR to "Προσθήκη",
                ACCION_REINTENTAR to "Δοκίμασε ξανά",
                ACCION_COPIAR to "Αντιγραφή συνδέσμου",
                ACCION_COMPARTIR to "Κοινοποίηση",
                ACCION_AHORA_NO to "Όχι τώρα",
                ACCION_SI to "Ναι",
                ACCION_NO to "Όχι",
                ESTADO_CARGANDO to "Φορτώνει…",
                ESTADO_SIN_CONTENIDO to "Δεν υπάρχει περιεχόμενο για αυτό το παιχνίδι.",
                APP_LEMA to "Το παιχνίδι πάρτι που χωράει στα κινητά σας",
                MENU_JUGAR to "ΠΑΙΞΕ",
                MENU_SEGUIR_PARTIDA to "ΣΥΝΕΧΙΣΕ ΤΗΝ ΠΑΡΤΙΔΑ",
                MENU_PARTIDA_NUEVA to "ΝΕΑ ΠΑΡΤΙΔΑ",
                MENU_COMO_JUGAR to "ΠΩΣ ΠΑΙΖΕΤΑΙ",
                MENU_AJUSTES to "ΡΥΘΜΙΣΕΙΣ",
                MENU_SALON to "ΠΑΙΞΕ ΜΕ ΠΟΛΛΑ ΚΙΝΗΤΑ",
                MENU_TOUR to "ΔΕΣ ΤΗΝ ΠΕΡΙΗΓΗΣΗ",
                MODO_TITULO to "Πώς θα παίξουμε;",
                MODO_SUBTITULO to "Μπορεί να αλλάξει σε κάθε νέα παρτίδα.",
                MODO_EQUIPOS to "Σε ομάδες",
                MODO_EQUIPOS_DETALLE to
                    "Από 2 έως 6 ομάδες. Κάθε ομάδα έχει το πιόνι της και αλλάζει ποιος " +
                    "υποδύεται. Ο κλασικός τρόπος, και ο πιο θορυβώδης.",
                MODO_INDIVIDUAL to "Ατομικά",
                MODO_INDIVIDUAL_DETALLE to
                    "Από 2 έως 8 άτομα, ο καθένας με το πιόνι του και χωρίς ομάδες. Στις " +
                    "δοκιμασίες υποδύεται όποιος έχει τη σειρά του και μαντεύουν οι " +
                    "υπόλοιποι.",
                MODO_SOLITARIO to "Μονή πρόκληση",
                MODO_SOLITARIO_DETALLE to
                    "Εσύ εναντίον του χρόνου: δέκα δοκιμασίες στη σειρά και ένα προσωπικό " +
                    "ρεκόρ να σπάσεις. Χωρίς ταμπλό και μόνο με τα παιχνίδια που " +
                    "παίζονται χωρίς κοινό.",
                PARTICIPANTES_TITULO_EQUIPOS to "Ομάδες",
                PARTICIPANTES_TITULO_INDIVIDUAL to "Παίκτες",
                PARTICIPANTES_TITULO_SOLITARIO to "Πώς σε λένε;",
                PARTICIPANTES_SUBTITULO_EQUIPOS to
                    "Από %1\$d έως %2\$d ομάδες. Σημείωσε ποιος παίζει σε κάθε " +
                    "μία και το κινητό θα λέει σε ποιον πέφτει να υποδυθεί.",
                PARTICIPANTES_SUBTITULO_INDIVIDUAL to "Από %1\$d έως %2\$d άτομα, ο καθένας με το πιόνι του.",
                PARTICIPANTES_SUBTITULO_SOLITARIO to
                    "Μόνο για να μπει το όνομά σου στο ρεκόρ. Τίποτα από αυτά " +
                    "δεν φεύγει από το κινητό.",
                PARTICIPANTES_ANADIR_EQUIPO to "+  ΠΡΟΣΘΗΚΗ ΟΜΑΔΑΣ",
                PARTICIPANTES_ANADIR_JUGADOR to "+  ΠΡΟΣΘΗΚΗ ΠΑΙΚΤΗ",
                PARTICIPANTES_NUEVO_JUGADOR to "Προσθήκη παίκτη…",
                PARTICIPANTES_SIN_JUGADORES to "Χωρίς ονόματα: το παιχνίδι θα λέει μόνο το όνομα της ομάδας.",
                PARTICIPANTES_NOMBRE_EQUIPO to "Όνομα ομάδας",
                PARTICIPANTES_NOMBRE_JUGADOR to "Όνομα",
                PARTICIPANTES_QUITAR to "Αφαίρεση",
                PARTICIPANTES_EQUIPO_POR_DEFECTO to "Ομάδα %1\$d",
                PARTICIPANTES_JUGADOR_POR_DEFECTO to "Παίκτης %1\$d",
                PARTICIPANTES_EQUIPO_1_POR_DEFECTO to "Οι Άσοι",
                PARTICIPANTES_EQUIPO_2_POR_DEFECTO to "Τα Θηρία",
                PARTICIPANTES_DESDE_SALON to "Συνδέθηκε από άλλο κινητό",
                TABLERO_TIRAR to "ΡΙΞΕ ΤΟ ΖΑΡΙ",
                TABLERO_TURNO_DE to "ΣΕΙΡΑ ΤΟΥ %1\$s",
                TABLERO_LE_TOCA to "Είναι η σειρά του %1\$s",
                TABLERO_CASILLA to "Θέση %1\$d",
                TABLERO_SALIDA to "ΑΦΕΤΗΡΙΑ",
                TABLERO_META to "ΤΕΡΜΑ",
                TABLERO_MARCADOR to "Σκορ",
                TABLERO_ABANDONAR to "Εγκατάλειψη παρτίδας",
                TABLERO_ABANDONAR_PREGUNTA to "Θα χαθεί η πρόοδος αυτής της παρτίδας. Σίγουρα θέλεις να βγεις;",
                TABLERO_ESPERANDO_HUB to "Αναμονή για το κινητό που τρέχει την παρτίδα…",
                TABLERO_AVANZA_CASILLAS to "Προχωρά %1\$d",
                CASILLA_COMODIN to "ΜΠΑΛΑΝΤΕΡ",
                CASILLA_COMODIN_DETALLE to "Ο αντίπαλος διαλέγει τη δοκιμασία που σου πέφτει. Χωρίς έλεος.",
                CASILLA_TODOS to "ΠΑΙΖΟΥΝ ΟΛΟΙ",
                CASILLA_TODOS_DETALLE to "Η ίδια δοκιμασία για όλο το τραπέζι. Όποιος τη βρει προχωρά μία θέση.",
                CASILLA_META_AVISO to "Τελική δοκιμασία: κερδίζεις μόνο αν την περάσεις.",
                COMODIN_TITULO to "Θέση μπαλαντέρ",
                COMODIN_ELIGE to "Ο %1\$s διαλέγει τη δοκιμασία",
                PRUEBA_FINAL to "🏁  ΤΕΛΙΚΗ ΔΟΚΙΜΑΣΙΑ",
                PRUEBA_JUEGAN_TODOS to "👥  ΠΑΙΖΟΥΝ ΟΛΟΙ",
                PRUEBA_LE_TOCA_ACTUAR_A to "ΥΠΟΔΥΕΤΑΙ Ο",
                PRUEBA_QUIEN_DECIDA to "όποιον αποφασίσει ο %1\$s",
                PRUEBA_SOLO_MIRE_ESA_PERSONA to "👀  Μόνο αυτό το άτομο να κοιτάζει την οθόνη",
                PRUEBA_MIRA_TU_MOVIL to "📱  Κοίτα το δικό σου κινητό: η λέξη έφτασε εκεί",
                PRUEBA_SEGUNDOS to "⏱  %1\$d δευτερόλεπτα",
                PRUEBA_CUANDO_TEMA to "Σε ποια χρονιά;",
                PRUEBA_CUANDO_RESPUESTA to "Έγινε το %1\$d.",
                PRUEBA_ACIERTOS_DE to "%1\$d από %2\$d",
                PRUEBA_SALTAR to "ΠΑΣΟ",
                PRUEBA_ACERTADA to "✓  ΒΡΕΘΗΚΕ",
                PRUEBA_FALLADA to "✗  ΛΑΘΟΣ",
                PRUEBA_PROHIBIDA to "🚫  ΑΠΑΓΟΡΕΥΜΕΝΗ",
                PRUEBA_TERMINAR to "Τέλος δοκιμασίας",
                PRUEBA_ACIERTOS to "✔  %1\$d",
                PRUEBA_VEREDICTO_TITULO to "Τα κατάφερε;",
                PRUEBA_VEREDICTO_LOGRADO to "✓  ΝΑΙ!",
                PRUEBA_VEREDICTO_NO_LOGRADO to "✗  ΟΧΙ",
                PRUEBA_VEREDICTO_DECIDE_MESA to "Αποφασίζει το τραπέζι, όχι η εφαρμογή.",
                PRUEBA_VEREDICTO_DECIDE_SOLO to "Εδώ δεν κοιτάζει κανείς: να είσαι ειλικρινής με τον εαυτό σου.",
                PRUEBA_BORRAR_DIBUJO to "Σβήσε όλα",
                PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA to "ΚΟΙΤΑΖΕΙ ΜΟΝΟ ΟΠΟΙΟΣ ΖΩΓΡΑΦΙΖΕΙ",
                PRUEBA_DIBUJO_TIEMPO_AL_PULSAR to "Ο χρόνος αρχίζει όταν πατήσεις το κουμπί.",
                PRUEBA_DIBUJO_EMPEZAR to "🎨   ΑΡΧΙΣΕ ΝΑ ΖΩΓΡΑΦΙΖΕΙΣ",
                PRUEBA_DIBUJO_LIENZO_VACIO to "Ζωγράφισε εδώ με το δάχτυλο",
                PRUEBA_DIBUJO_ESPIAR to "Κρατά πατημένο για να δεις πάλι τη λέξη",
                PRUEBA_DIBUJO_GOMA to "Γόμα",
                PRUEBA_DESHACER to "Αναίρεση",
                PRUEBA_PINCEL to "Πάχος",
                PRUEBA_COLOR to "Χρώμα",
                PRUEBA_RETO_OBJETIVO to "Πρέπει να φτάσετε στα %1\$d",
                PRUEBA_RETO_LLEVAMOS to "Μέχρι τώρα",
                PRUEBA_RETO_UNA_MAS to "+1  ΑΛΛΟ!",
                PRUEBA_RETO_CONSEGUIDO to "ΤΑ ΚΑΤΑΦΕΡΑΤΕ!",
                PRUEBA_RETO_TOCA_PARA_SUMAR to "από %1\$d  ·  πάτα για να προσθέσεις",
                PRUEBA_RETO_RENDIRSE to "ΤΑ ΠΑΡΑΤΑΜΕ",
                PRUEBA_RETO_NOTA to "Κάθε απάντηση μετράει μία φορά. Αν επαναληφθεί, δεν μετράει.",
                PRUEBA_ORDENA_AYUDA to "Πάτα τα με τη σωστή σειρά, από το πρώτο στο τελευταίο.",
                PRUEBA_ORDENA_COMPROBAR to "ΕΛΕΓΞΕ",
                PRUEBA_ORDENA_CORRECTO to "Η σωστή σειρά ήταν:",
                PRUEBA_VF_VERDADERO to "ΣΩΣΤΟ",
                PRUEBA_VF_FALSO to "ΛΑΘΟΣ",
                PRUEBA_VF_ERA_VERDAD to "Ήταν αλήθεια",
                PRUEBA_VF_ERA_MENTIRA to "Ήταν ψέμα",
                PRUEBA_EMOJIS_AYUDA to "Τι είναι αυτό, γραμμένο με emoji;",
                PRUEBA_EMOJIS_ERA to "Ήταν: %1\$s",
                PRUEBA_CANTA_PISTA to "Ξεκίνα από εδώ",
                PRUEBA_TRABALENGUAS_AYUDA to "Πες το ολόκληρο και χωρίς να μπερδευτείς.",
                PRUEBA_DESAFIO_AYUDA to "Να δούμε πώς θα τη βγάλεις.",
                RONDA_TODOS_PASA_MOVIL to "Δώσε το κινητό χωρίς να δεις την απάντηση κανενός.",
                RONDA_TODOS_RESPONDE to "Απαντά ο %1\$s",
                RONDA_TODOS_RESUMEN to "Ποιος τη βρήκε",
                RONDA_TODOS_NADIE to "Κανείς. Δεν κινείται κανένα πιόνι.",
                RONDA_TODOS_EN_TU_MOVIL to "Απάντησε στο δικό σου κινητό.",
                RONDA_TODOS_ESPERANDO to "Αναμονή για τους άλλους…",
                RONDA_TODOS_PASAD_A to "ΔΩΣΤΕ ΤΟ ΚΙΝΗΤΟ ΣΤΟΝ",
                RONDA_TODOS_PROGRESO to "%1\$d από %2\$d. Κανείς δεν θα μάθει ποιος τη βρήκε μέχρι το τέλος.",
                RONDA_TODOS_SIN_RESPUESTA to "Τέλος χρόνου, χωρίς απάντηση.",
                RONDA_TODOS_GUARDADA to "Η απάντηση αποθηκεύτηκε. Μην την πεις ακόμα.",
                RONDA_TODOS_CORRECTA_ERA to "Η ΣΩΣΤΗ ΑΠΑΝΤΗΣΗ ΗΤΑΝ",
                RONDA_TODOS_SIN_RESPONDER to "χωρίς απάντηση",
                RONDA_TODOS_VER_RESULTADOS to "ΔΕΣ ΤΑ ΑΠΟΤΕΛΕΣΜΑΤΑ",
                RESULTADO_SUPERADA to "ΠΕΡΑΣΕ!",
                RESULTADO_NO_HA_PODIDO_SER to "ΟΧΙ ΑΥΤΗ ΤΗ ΦΟΡΑ",
                RESULTADO_FINAL_SUPERADA to "Η ΤΕΛΙΚΗ ΔΟΚΙΜΑΣΙΑ ΠΕΡΑΣΕ!",
                RESULTADO_AVANZAS_A to "Προχωράτε στη θέση %1\$d",
                RESULTADO_TE_QUEDAS_EN to "Μένετε στη θέση %1\$d",
                RESULTADO_LLEGADA_A_META to "Φτάσατε στο τέρμα!",
                RESULTADO_SIGUIENTE_TURNO to "ΕΠΟΜΕΝΗ ΣΕΙΡΑ",
                RESULTADO_VER_RESULTADO to "ΔΕΣ ΤΟ ΑΠΟΤΕΛΕΣΜΑ",
                VICTORIA_TITULO to "Τέλος παρτίδας!",
                VICTORIA_GANADOR to "Κερδίζει ο %1\$s",
                VICTORIA_CLASIFICACION to "Τελική κατάταξη",
                VICTORIA_OTRA_PARTIDA to "ΑΛΛΗ ΠΑΡΤΙΔΑ",
                VICTORIA_AL_MENU to "ΠΙΣΩ ΣΤΟ ΜΕΝΟΥ",
                VICTORIA_SOLITARIO_TITULO to "Η πρόκληση ολοκληρώθηκε",
                VICTORIA_SOLITARIO_PUNTOS to "%1\$d πόντοι",
                VICTORIA_SOLITARIO_MEJOR to "Το ρεκόρ σου: %1\$d",
                VICTORIA_SOLITARIO_RECORD to "Νέο προσωπικό ρεκόρ!",
                SOLITARIO_TITULO to "Μονή πρόκληση",
                SOLITARIO_SUBTITULO to "Δέκα δοκιμασίες. Εσύ εναντίον του χρόνου.",
                SOLITARIO_PROGRESO to "Δοκιμασία %1\$d από %2\$d",
                SOLITARIO_RONDAS to "Δοκιμασίες",
                SOLITARIO_EMPEZAR to "ΞΕΚΙΝΑ ΤΗΝ ΠΡΟΚΛΗΣΗ",
                SOLITARIO_MEJOR_MARCA to "Ρεκόρ: %1\$d",
                SOLITARIO_SIN_MARCA to "Δεν έχεις ρεκόρ ακόμα. Βάλε το πρώτο.",
                AJUSTES_TITULO to "Ρυθμίσεις",
                AJUSTES_SUBTITULO to "Αποθηκεύονται για την επόμενη παρτίδα.",
                AJUSTES_APARIENCIA to "Εμφάνιση",
                AJUSTES_TEMA to "Θέμα",
                AJUSTES_TEMA_DETALLE to "Έξι θέματα: τρία φωτεινά και τρία σκούρα.",
                AJUSTES_TEMA_SISTEMA to "Ακολούθα το σύστημα",
                AJUSTES_IDIOMA to "Γλώσσα",
                AJUSTES_IDIOMA_DETALLE to "Δεκατρείς γλώσσες διαθέσιμες.",
                AJUSTES_PARTIDA to "Η παρτίδα",
                AJUSTES_RITMO to "Ρυθμός δοκιμασιών",
                AJUSTES_RITMO_DETALLE to "Πόσος χρόνος υπάρχει για κάθε δοκιμασία.",
                AJUSTES_MODALIDAD to "Τρόπος παιχνιδιού",
                AJUSTES_MODALIDAD_DETALLE to
                    "Πόσες θέσεις έχει το ταμπλό και πόσες δοκιμασίες έχει η μονή " +
                    "πρόκληση.",
                AJUSTES_JUEGOS_ACTIVOS to "Παιχνίδια της παρτίδας",
                AJUSTES_JUEGOS_ACTIVOS_DETALLE to
                    "Βγάλε όσα δεν σας αρέσουν και θα σταματήσουν να βγαίνουν στο " +
                    "ταμπλό.",
                AJUSTES_JUEGOS_CONTADOR to "%1\$d από %2\$d ενεργά",
                AJUSTES_JUEGOS_MINIMO to "Πρέπει να μείνει ενεργό τουλάχιστον ένα παιχνίδι.",
                AJUSTES_SONIDO to "Ήχος",
                AJUSTES_SONIDO_DETALLE to "Μπιπ αντίστροφης μέτρησης και ειδοποιήσεις",
                AJUSTES_VIBRACION to "Δόνηση",
                AJUSTES_VIBRACION_DETALLE to "Επιτυχίες, αποτυχίες και τέλος χρόνου",
                AJUSTES_ANIMACIONES to "Κινήσεις",
                AJUSTES_ANIMACIONES_DETALLE to "Σβήσε τις αν προτιμάς ακίνητο περιβάλλον",
                AJUSTES_DATOS to "Τα δεδομένα σου",
                AJUSTES_EXPORTAR to "Εξαγωγή",
                AJUSTES_EXPORTAR_DETALLE to "Αποθηκεύει ομάδες, ρυθμίσεις και ρεκόρ σε αρχείο",
                AJUSTES_IMPORTAR to "Εισαγωγή",
                AJUSTES_IMPORTAR_DETALLE to "Ανακτά ένα αντίγραφο που αποθήκευσες πριν",
                AJUSTES_MAS to "Περισσότερα",
                AJUSTES_APOYAR to "Στήριξε την ανάπτυξη",
                AJUSTES_APOYAR_DETALLE to "Κέρασέ με έναν καφέ αν σου φαίνεται χρήσιμο",
                AJUSTES_COMPARTIR to "Κοινοποίησε το Funny",
                AJUSTES_COMPARTIR_DETALLE to "Δώσ' το σε κάποιον που θα το απολαύσει",
                AJUSTES_AYUDA to "Βοήθεια",
                AJUSTES_AYUDA_DETALLE to "Πώς παίζεται και συχνές ερωτήσεις",
                AJUSTES_TOUR to "Ξενάγηση",
                AJUSTES_TOUR_DETALLE to "Τα δώδεκα παιχνίδια και οι τρεις τρόποι, εξηγημένα",
                AJUSTES_ACERCA_DE to "Σχετικά",
                AJUSTES_ACERCA_DE_DETALLE to "Έκδοση, άδειες και απόρρητο",
                TEMA_MODO_CLARO to "Φωτεινά",
                TEMA_MODO_OSCURO to "Σκούρα",
                TEMA_FIESTA to "Πάρτι",
                TEMA_NEON to "Νέον",
                TEMA_MEDIANOCHE to "Μεσάνυχτα",
                TEMA_PAPEL to "Χαρτί",
                TEMA_MENTA to "Μέντα",
                TEMA_ATARDECER to "Ηλιοβασίλεμα",
                IDIOMA_TITULO to "Γλώσσα",
                IDIOMA_SEGUIR_SISTEMA to "Του κινητού",
                IDIOMA_SUBTITULO to "Η αλλαγή εφαρμόζεται αμέσως.",
                CAFE_TITULO to "Έναν καφέ;",
                CAFE_TEXTO to
                    "Αυτή η εφαρμογή είναι δωρεάν, χωρίς διαφημίσεις και δεν συλλέγει τα δεδομένα " +
                    "σου. Αν σου φαίνεται χρήσιμη, μπορείς να με κεράσεις έναν καφέ.",
                CAFE_BOTON to "Κέρασέ με έναν καφέ · 1 €",
                CAFE_NO_VOLVER to "Μην το δείξεις ξανά",
                CAFE_OTRO_DISPOSITIVO to "Από άλλη συσκευή",
                CAFE_QR_DESCRIPCION to
                    "Κωδικός QR με τον σύνδεσμο για να κεράσεις έναν καφέ τον δημιουργό της " +
                    "εφαρμογής",
                CAFE_ILUSTRACION_DESCRIPCION to "Σχέδιο ενός φλιτζανιού καφέ με ατμό",
                CAFE_ENLACE_COPIADO to "Ο σύνδεσμος αντιγράφηκε",
                CAFE_GRACIAS to "Ευχαριστώ που πέρασες 🙂",
                CAFE_SIN_DESBLOQUEOS to
                    "Δεν αλλάζει τίποτα μέσα στο παιχνίδι: το Funny είναι ολόκληρο και θα " +
                    "μείνει.",
                CAFE_ENTRADA_AJUSTES to "Στήριξε την ανάπτυξη",
                CAFE_NO_DISPONIBLE to "Δεν είναι διαθέσιμο σε αυτή τη συσκευή.",
                COPIA_TITULO to "Αντίγραφο των δεδομένων σου",
                COPIA_EXPORTAR_HECHO to "Το αντίγραφο αποθηκεύτηκε.",
                COPIA_EXPORTAR_ERROR to "Δεν ήταν δυνατή η αποθήκευση του αντιγράφου.",
                COPIA_IMPORTAR_TITULO to "Εισαγωγή αντιγράφου",
                COPIA_IMPORTAR_AVISO to
                    "Πριν αλλάξει οτιδήποτε, αποθηκεύεται ένα αντίγραφο όσων έχεις τώρα, " +
                    "ώστε να μπορείς πάντα να γυρίσεις πίσω.",
                COPIA_IMPORTAR_FUSIONAR to "Πρόσθεσε σε όσα έχω",
                COPIA_IMPORTAR_REEMPLAZAR to "Αντικατάστησε όλα",
                COPIA_IMPORTAR_HECHO to "Τα δεδομένα εισήχθησαν.",
                COPIA_IMPORTAR_ERROR_FORMATO to
                    "Αυτό το αρχείο δεν μοιάζει με αντίγραφο του Funny. Δεν άλλαξε " +
                    "τίποτα.",
                COPIA_IMPORTAR_ERROR_VERSION to
                    "Αυτό το αντίγραφο είναι από νεότερη έκδοση του Funny. " +
                    "Ενημέρωσε την εφαρμογή και δοκίμασε ξανά.",
                COPIA_IMPORTAR_RESPALDO to "Πρώτα αποθηκεύτηκε ένα αντίγραφο ασφαλείας.",
                COPIA_CABECERA_DETALLE to "Αντίγραφο της %1\$s · Funny %2\$s",
                AYUDA_TITULO to "Βοήθεια",
                AYUDA_SUBTITULO to "Όλα όσα χρειάζονται για να μη χαθεί κανείς.",
                AYUDA_QUE_ES_TITULO to "Τι είναι το Funny;",
                AYUDA_QUE_ES_TEXTO to
                    "Ένα παιχνίδι πάρτι με δώδεκα διαφορετικές δοκιμασίες. Παίζεται με ένα " +
                    "κινητό που περνάει από χέρι σε χέρι ή, αν είστε πολλοί με κινητό, " +
                    "συνδέοντάς τα μεταξύ τους. Δεν χρειάζεται ποτέ ίντερνετ.",
                AYUDA_COMO_SE_JUEGA_TITULO to "Η παρτίδα",
                AYUDA_PARTIDA_1 to "Διαλέξτε τρόπο: σε ομάδες, ατομικά ή τη μονή πρόκληση.",
                AYUDA_PARTIDA_2 to "Κάθε ομάδα ή παίκτης έχει ένα πιόνι και όλοι ξεκινούν από την ΑΦΕΤΗΡΙΑ.",
                AYUDA_PARTIDA_3 to "Στη σειρά σου ρίχνεις το ζάρι και προχωράς από 1 έως 3 θέσεις.",
                AYUDA_PARTIDA_4 to
                    "Η θέση όπου πέφτεις ορίζει τη δοκιμασία. Αν την περάσεις μένεις, αλλιώς " +
                    "γυρίζεις πίσω.",
                AYUDA_PARTIDA_5 to "Κερδίζει όποιος φτάσει στο ΤΕΡΜΑ και περάσει την τελική δοκιμασία.",
                AYUDA_UN_MOVIL_TITULO to "Με ένα μόνο κινητό",
                AYUDA_UN_MOVIL_1 to "Το κινητό περνάει γύρω: η εφαρμογή λέει πάντα σε ποιον πέφτει.",
                AYUDA_UN_MOVIL_2 to
                    "Στη μίμηση, στο ταμπού, στη ζωγραφική, στο τραγούδι και στις προκλήσεις " +
                    "κοιτάζει μόνο όποιος υποδύεται.",
                AYUDA_UN_MOVIL_3 to "Στις δοκιμασίες με απαντήσεις, η οθόνη μπορεί να δειχθεί σε όλους.",
                AYUDA_VARIOS_MOVILES_TITULO to "Με πολλά κινητά",
                AYUDA_VARIOS_MOVILES_1 to
                    "Ένα κινητό κάνει το τραπέζι (το hub) και τα άλλα συνδέονται σε αυτό. " +
                    "Δεν χρειάζεται ούτε wifi ούτε δεδομένα.",
                AYUDA_VARIOS_MOVILES_2 to
                    "Η μυστική λέξη φτάνει μόνο στο κινητό όποιου υποδύεται, ώστε να μην " +
                    "τη δει κανείς κατά λάθος.",
                AYUDA_VARIOS_MOVILES_3 to
                    "Στις θέσεις «παίζουν όλοι», ο καθένας απαντά στο δικό του κινητό " +
                    "ταυτόχρονα.",
                AYUDA_FAQ_TITULO to "Συχνές ερωτήσεις",
                AYUDA_FAQ_1_P to "Χρειάζεται ίντερνετ;",
                AYUDA_FAQ_1_R to
                    "Όχι. Το Funny δουλεύει ολόκληρο χωρίς σύνδεση, και η σύνδεση πολλών κινητών " +
                    "χρησιμοποιεί Bluetooth και άμεσο wifi μεταξύ τους, χωρίς να περνά από κανένα " +
                    "δίκτυο.",
                AYUDA_FAQ_2_P to "Μπορεί να παίξει ένας μόνο;",
                AYUDA_FAQ_2_R to
                    "Ναι: η μονή πρόκληση είναι δέκα δοκιμασίες στη σειρά με προσωπικό ρεκόρ. " +
                    "Μπαίνουν μόνο τα παιχνίδια που δεν χρειάζονται κοινό.",
                AYUDA_FAQ_3_P to "Κοστίζει κάτι; Υπάρχει κάτι κλειδωμένο;",
                AYUDA_FAQ_3_R to
                    "Δεν υπάρχει τίποτα κλειδωμένο ούτε κάτι επιπλέον να αποκτήσεις. Αν σου " +
                    "αρέσει, μπορείς να με κεράσεις έναν καφέ από τις Ρυθμίσεις, και αυτό δεν " +
                    "αλλάζει απολύτως τίποτα μέσα στο παιχνίδι.",
                AYUDA_FAQ_4_P to "Συλλέγει δεδομένα;",
                AYUDA_FAQ_4_R to
                    "Όχι. Δεν υπάρχουν αναλυτικά, ούτε λογαριασμοί, ούτε διαφημίσεις. Οι ομάδες " +
                    "και οι ρυθμίσεις αποθηκεύονται μόνο στο κινητό σου και φεύγουν από εκεί μόνο " +
                    "αν εξάγεις εσύ ένα αντίγραφο.",
                AYUDA_FAQ_5_P to "Μπορώ να αλλάξω ποια παιχνίδια βγαίνουν;",
                AYUDA_FAQ_5_R to
                    "Ναι, στις Ρυθμίσεις → Παιχνίδια της παρτίδας. Όσα βγάλεις σταματούν να " +
                    "εμφανίζονται στο ταμπλό.",
                AYUDA_PROBLEMAS_TITULO to "Αν κάτι πάει λάθος",
                AYUDA_PROBLEMAS_TEXTO to
                    "Κλείσε και ξανάνοιξε την εφαρμογή: η παρτίδα σε εξέλιξη διατηρείται. " +
                    "Αν το πρόβλημα συνεχίζεται, εξάγαγε τα δεδομένα σου πριν " +
                    "εγκαταστήσεις ξανά και γράψε μας τι συνέβη.",
                AYUDA_ESCRIBENOS to "Γράψε στον δημιουργό",
                ACERCA_TITULO to "Σχετικά",
                ACERCA_VERSION to "Έκδοση",
                ACERCA_COMPILACION to "Build",
                ACERCA_FECHA to "Ημερομηνία",
                ACERCA_COMMIT to "Commit",
                ACERCA_LICENCIA to "Άδεια",
                ACERCA_LICENCIAS_TERCEROS to "Άδειες τρίτων",
                ACERCA_PRIVACIDAD to "Πολιτική απορρήτου",
                ACERCA_CONTACTO to "Επικοινωνία",
                ACERCA_CODIGO to "Πηγαίος κώδικας",
                ACERCA_SIN_ANUNCIOS to "Χωρίς διαφημίσεις, χωρίς αναλυτικά και χωρίς λογαριασμούς.",
                ACERCA_AUTOR to "Ghato Studio · Brais Galdo",
                TOUR_TITULO to "Ξενάγηση",
                TOUR_SUBTITULO to "Τα δώδεκα παιχνίδια και οι τρεις τρόποι, σε δύο λεπτά.",
                TOUR_EMPEZAR to "ΞΕΚΙΝΑ ΤΗΝ ΞΕΝΑΓΗΣΗ",
                TOUR_SALTAR to "Παράλειψη",
                TOUR_ANTERIOR to "Προηγούμενο",
                TOUR_SIGUIENTE to "Επόμενο",
                TOUR_TERMINAR to "ΠΑΜΕ ΝΑ ΠΑΙΞΟΥΜΕ!",
                TOUR_PROGRESO to "%1\$d από %2\$d",
                TOUR_BIENVENIDA_TITULO to "Καλώς όρισες στο Funny",
                TOUR_BIENVENIDA_TEXTO to
                    "Δώδεκα παιχνίδια, τρεις τρόποι να παίξεις και μηδέν ανάγκη για " +
                    "ίντερνετ. Σε δύο λεπτά σου τα λέω όλα· μπορείς να το προσπεράσεις " +
                    "όποτε θέλεις.",
                TOUR_MODOS_TITULO to "Τρεις τρόποι να παίξεις",
                TOUR_MODOS_TEXTO to
                    "Σε ομάδες είναι το κλασικό: από 2 έως 6 ομάδες και αλλάζει ποιος " +
                    "υποδύεται. Ατομικά είναι το ίδιο αλλά ο καθένας έχει το πιόνι του, από 2 " +
                    "έως 8. Και η μονή πρόκληση είναι δέκα δοκιμασίες εναντίον του χρόνου, " +
                    "μόνος, με προσωπικό ρεκόρ.",
                TOUR_TABLERO_TITULO to "Το ταμπλό",
                TOUR_TABLERO_TEXTO to
                    "Κάθε πιόνι ξεκινά από την ΑΦΕΤΗΡΙΑ. Στη σειρά σου ρίχνεις το ζάρι, " +
                    "προχωράς από 1 έως 3 θέσεις και η θέση όπου πέφτεις ορίζει τη δοκιμασία. " +
                    "Αν την περάσεις μένεις εκεί· αν αποτύχεις γυρίζεις όπου ήσουν. Κερδίζει " +
                    "όποιος φτάσει στο ΤΕΡΜΑ και περάσει την τελική δοκιμασία.",
                TOUR_CASILLAS_TITULO to "Οι ειδικές θέσεις",
                TOUR_CASILLAS_TEXTO to
                    "🃏 Μπαλαντέρ: ο αντίπαλος διαλέγει τη δοκιμασία που σου πέφτει.\n👥 " +
                    "Παίζουν όλοι: η ίδια δοκιμασία για όλο το τραπέζι, και όποιος τη βρει " +
                    "προχωρά μία θέση.\n🏁 Τέρμα: τελική δοκιμασία στην τύχη. Χωρίς να την " +
                    "περάσεις δεν κερδίζεις.",
                TOUR_JUEGOS_TITULO to "Τα δώδεκα παιχνίδια",
                TOUR_JUEGOS_TEXTO to
                    "Αυτά είναι όλα. Μπορείς να απενεργοποιήσεις όσα δεν σας αρέσουν στις " +
                    "Ρυθμίσεις.",
                TOUR_SALON_TITULO to "Πολλά κινητά μαζί",
                TOUR_SALON_TEXTO to
                    "Ένα κινητό κάνει το τραπέζι και τα άλλα συνδέονται σε αυτό μέσω Bluetooth " +
                    "ή άμεσου wifi, χωρίς ίντερνετ. Δίνει αυτό που έχει πραγματικά σημασία: η " +
                    "μυστική λέξη φτάνει μόνο στο κινητό όποιου υποδύεται, και στις θέσεις " +
                    "«παίζουν όλοι» ο καθένας απαντά στο δικό του ταυτόχρονα.",
                TOUR_AJUSTES_TITULO to "Στα μέτρα σου",
                TOUR_AJUSTES_TEXTO to
                    "Έξι θέματα, δεκατρείς γλώσσες, τρεις ρυθμοί και τέσσερις τρόποι " +
                    "παιχνιδιού: γρήγορος, κανονικός, ακραίος και ένας στα μέτρα σου. Μπορείς " +
                    "επίσης να απενεργοποιήσεις παιχνίδια, να σβήσεις ήχο και δόνηση, και να " +
                    "αποθηκεύσεις ή να ανακτήσεις τα δεδομένα σου σε αρχείο.",
                TOUR_FINAL_TITULO to "Αυτά ήταν",
                TOUR_FINAL_TEXTO to
                    "Μπορείς να τα δεις ξανά όποτε θέλεις από Ρυθμίσεις → Ξενάγηση. Καλή " +
                    "διασκέδαση.",
                SALON_TITULO to "Πολλά κινητά",
                SALON_SUBTITULO to "Χωρίς ίντερνετ: συνδέονται μεταξύ τους.",
                SALON_CREAR to "ΓΙΝΕ ΤΟ ΤΡΑΠΕΖΙ",
                SALON_CREAR_DETALLE to
                    "Αυτό το κινητό τρέχει την παρτίδα και δείχνει το ταμπλό. Είναι αυτό που " +
                    "μένει στο τραπέζι.",
                SALON_UNIRSE to "ΜΠΕΣ ΣΕ ΤΡΑΠΕΖΙ",
                SALON_UNIRSE_DETALLE to
                    "Αυτό το κινητό μένει στο χέρι σου και λαμβάνει τις δοκιμασίες σου " +
                    "ιδιωτικά.",
                SALON_TU_NOMBRE to "Το όνομά σου",
                SALON_HUB_TITULO to "Είσαι το τραπέζι",
                SALON_HUB_ESPERANDO to "Αναμονή για συνδέσεις…",
                SALON_HUB_CONECTADOS to "Συνδεδεμένα",
                SALON_HUB_EMPEZAR to "ΞΕΚΙΝΑ ΤΗΝ ΠΑΡΤΙΔΑ",
                SALON_CLIENTE_TITULO to "Αναζήτηση τραπεζιού",
                SALON_CLIENTE_BUSCANDO to "Αναζήτηση τραπεζιών κοντά…",
                SALON_CLIENTE_SIN_SALONES to
                    "Δεν φαίνεται κανένα ακόμα. Άνοιξε στο άλλο κινητό το «Γίνε το " +
                    "τραπέζι» και περιμένετε λίγα δευτερόλεπτα.",
                SALON_CLIENTE_CONECTANDO to "Σύνδεση…",
                SALON_CLIENTE_CONECTADO to "Συνδεδεμένο",
                SALON_CLIENTE_ESPERA to "Είσαι μέσα. Κοίτα το τραπέζι: η παρτίδα ξεκινά εκεί.",
                SALON_SALIR to "Έξοδος από την αίθουσα",
                SALON_DESCONECTADO to "Η σύνδεση με το τραπέζι χάθηκε.",
                SALON_ERROR_PERMISOS to "Λείπουν άδειες για να βρεθούν τα κινητά που έχεις δίπλα.",
                SALON_PEDIR_PERMISOS to "ΔΩΣΕ ΑΔΕΙΕΣ",
                SALON_PERMISOS_EXPLICACION to
                    "Για να βρει τα κινητά που έχεις δίπλα, το Android ζητά άδεια για " +
                    "συσκευές σε κοντινή απόσταση και, σε παλιότερες εκδόσεις, και " +
                    "τοποθεσία. Το Funny δεν κοιτάζει ποτέ πού είσαι ούτε το " +
                    "αποθηκεύει κάπου: είναι το τίμημα που βάζει το σύστημα για να " +
                    "χρησιμοποιήσεις Bluetooth και άμεσο wifi.",
                SALON_ERROR_BLUETOOTH to "Άνοιξε το Bluetooth για να συνδεθούν τα κινητά.",
                SALON_ERROR_UBICACION to
                    "Άνοιξε την τοποθεσία: το Android την απαιτεί για αναζήτηση μέσω " +
                    "Bluetooth.",
                SALON_ERROR_SERVICIOS to
                    "Αυτό το κινητό δεν έχει τις υπηρεσίες Google που χρειάζονται για " +
                    "σύνδεση. Μπορείτε να συνεχίσετε να παίζετε δίνοντας ένα μόνο κινητό " +
                    "γύρω.",
                SALON_COMO_FUNCIONA to "Πώς δουλεύει;",
                SALON_ESTE_DISPOSITIVO to "Αυτό το κινητό",
                SALON_ROL_HUB to "Τραπέζι",
                SALON_ROL_MANDO to "Χειριστήριο",
                SALON_SIN_RED to "Το ίντερνετ δεν χρησιμοποιείται ποτέ.",
                SALON_SIN_NOMBRE to "Χωρίς όνομα",
                SALON_TU_TURNO to "Σειρά σου!",
                SALON_MIRA_EL_HUB to "Κοίτα το κινητό του τραπεζιού.",
                JUEGO_MIMICA_NOMBRE to "Μίμηση",
                JUEGO_MIMICA_LEMA to "Δείξ' το χωρίς λόγια",
                JUEGO_MIMICA_INSTRUCCIONES to
                    "Ένα άτομο δείχνει τη λέξη με κινήσεις. Απαγορεύεται να μιλά, να " +
                    "κάνει θορύβους ή να δείχνει αντικείμενα του χώρου.",
                JUEGO_DIBUJO_NOMBRE to "Ζωγραφική",
                JUEGO_DIBUJO_LEMA to "Ζωγράφισέ το στην οθόνη",
                JUEGO_DIBUJO_INSTRUCCIONES to
                    "Ένα άτομο ζωγραφίζει στην οθόνη του κινητού και οι άλλοι " +
                    "μαντεύουν. Ούτε γράμματα, ούτε αριθμοί, ούτε κινήσεις.",
                JUEGO_CUANDO_NOMBRE to "Πότε;",
                JUEGO_CUANDO_LEMA to "Σε ποια χρονιά έγινε;",
                JUEGO_CUANDO_INSTRUCCIONES to
                    "Εμφανίζεται ένα γεγονός και τέσσερις πιθανές χρονιές. Πρέπει να " +
                    "αποφασίσετε σε ποια έγινε.",
                JUEGO_PREGUNTAS_NOMBRE to "Ερωτήσεις",
                JUEGO_PREGUNTAS_LEMA to "Γενικές γνώσεις",
                JUEGO_PREGUNTAS_INSTRUCCIONES to
                    "Μια ερώτηση με τέσσερις απαντήσεις. Διαλέγεις μία μόνο και " +
                    "δεν αλλάζει.",
                JUEGO_TABU_NOMBRE to "Ταμπού",
                JUEGO_TABU_LEMA to "Περίγραψέ το χωρίς να το πεις",
                JUEGO_TABU_INSTRUCCIONES to
                    "Πρέπει να περιγράψεις τη λέξη χωρίς να χρησιμοποιήσεις καμία από " +
                    "τις απαγορευμένες ούτε λέξεις της ίδιας οικογένειας.",
                JUEGO_RETO_NOMBRE to "Γρήγορη πρόκληση",
                JUEGO_RETO_LEMA to "Απαρίθμησε με τον χρόνο",
                JUEGO_RETO_INSTRUCCIONES to
                    "Συνεχίστε να λέτε πράγματα της κατηγορίας μέχρι να φτάσετε τον " +
                    "στόχο πριν τελειώσει ο χρόνος.",
                JUEGO_EMOJIS_NOMBRE to "Emoji",
                JUEGO_EMOJIS_LEMA to "Αποκρυπτογράφησέ το",
                JUEGO_EMOJIS_INSTRUCCIONES to
                    "Μια ταινία, ένα τραγούδι ή μια παροιμία γραμμένη μόνο με emoji, " +
                    "και τέσσερις πιθανές απαντήσεις.",
                JUEGO_VERDADERO_FALSO_NOMBRE to "Το πιστεύεις;",
                JUEGO_VERDADERO_FALSO_LEMA to "Σωστό ή λάθος",
                JUEGO_VERDADERO_FALSO_INSTRUCCIONES to
                    "Τέσσερις παράξενες δηλώσεις στη σειρά. Για κάθε μία " +
                    "πρέπει να πεις αν είναι αλήθεια ή ψέμα, και μετά " +
                    "εξηγείται γιατί.",
                JUEGO_TRABALENGUAS_NOMBRE to "Γλωσσοδέτης",
                JUEGO_TRABALENGUAS_LEMA to "Πες τον χωρίς να μπερδευτείς",
                JUEGO_TRABALENGUAS_INSTRUCCIONES to
                    "Πρέπει να πεις τον γλωσσοδέτη όσες φορές ζητά, ολόκληρο " +
                    "και χωρίς λάθος. Κρίνει το τραπέζι.",
                JUEGO_ORDENA_NOMBRE to "Βάλε σε σειρά",
                JUEGO_ORDENA_LEMA to "Βάλ' το στη θέση του",
                JUEGO_ORDENA_INSTRUCCIONES to
                    "Τέσσερα πράγματα ανακατεμένα και ένα κριτήριο. Πρέπει να τα " +
                    "πατήσεις με τη σωστή σειρά.",
                JUEGO_CANTA_NOMBRE to "Τραγούδα",
                JUEGO_CANTA_LEMA to "Συνέχισε το τραγούδι",
                JUEGO_CANTA_INSTRUCCIONES to
                    "Βγαίνει ο τίτλος και ποιος το τραγουδά, και πρέπει να αρχίσεις το " +
                    "ρεφρέν. Κρίνει το τραπέζι, με όση γενναιοδωρία θέλει.",
                JUEGO_DESAFIO_NOMBRE to "Πρόκληση",
                JUEGO_DESAFIO_LEMA to "Τόλμησε",
                JUEGO_DESAFIO_INSTRUCCIONES to
                    "Μια μικρή πρόκληση μπροστά σε όλους. Τίποτα επικίνδυνο, τίποτα " +
                    "εξευτελιστικό: μόνο η καλή γελοιότητα. Κρίνει το τραπέζι.",
                RITMO_RAPIDO to "Γρήγορο",
                RITMO_NORMAL to "Κανονικό",
                RITMO_TRANQUILO to "Χαλαρό",
                MODALIDAD_RAPIDA to "Γρήγορη παρτίδα",
                MODALIDAD_NORMAL to "Κανονική παρτίδα",
                MODALIDAD_EXTREMA to "Ακραία παρτίδα",
                MODALIDAD_PERSONALIZADA to "Με τον τρόπο μου",
                MODALIDAD_RAPIDA_DETALLE to "Για μια παρτίδα και τέλος, ή για να δοκιμάσεις το παιχνίδι",
                MODALIDAD_NORMAL_DETALLE to "Η συνηθισμένη, αυτή που ταιριάζει καλύτερα",
                MODALIDAD_EXTREMA_DETALLE to "Για ολόκληρο το βράδυ, χωρίς βιασύνη",
                MODALIDAD_PERSONALIZADA_DETALLE to "Τους αριθμούς τους βάζεις εσύ",
                MODALIDAD_RESUMEN to "%1\$d θέσεις · %2\$d δοκιμασίες · περίπου %3\$d λεπτά",
                MODALIDAD_CASILLAS to "Θέσεις μέχρι το τέρμα",
                MODALIDAD_PRUEBAS to "Δοκιμασίες ανά παρτίδα",
                MODALIDAD_PRUEBAS_NOTA to "Μετρούν μόνο στη μονή πρόκληση",
                A11Y_DADO to "Ζάρι: %1\$d",
                A11Y_FICHA to "Πιόνι του %1\$s στη θέση %2\$d",
                A11Y_CASILLA to "Θέση %1\$d, %2\$s",
                A11Y_VOLVER to "Πίσω στην προηγούμενη οθόνη",
                A11Y_CERRAR to "Κλείσιμο",
                A11Y_LIENZO_DIBUJO to "Καμβάς για ζωγραφική με το δάχτυλο",
                A11Y_TEMA_MUESTRA to "Δείγμα χρωμάτων του θέματος %1\$s",
                A11Y_BANDERA_IDIOMA to "Γλώσσα %1\$s",
                A11Y_REDUCIR to "Μείωση",
                A11Y_AUMENTAR to "Αύξηση",
            ),
        plurales =
            mapOf(
                ClavePlural.CASILLAS to
                    mapOf(
                        CategoriaPlural.ONE to "%d θέση",
                        CategoriaPlural.OTHER to "%d θέσεις",
                    ),
                ClavePlural.SEGUNDOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d δευτερόλεπτο",
                        CategoriaPlural.OTHER to "%d δευτερόλεπτα",
                    ),
                ClavePlural.DISPOSITIVOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d κινητό συνδεδεμένο",
                        CategoriaPlural.OTHER to "%d κινητά συνδεδεμένα",
                    ),
                ClavePlural.PUNTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d πόντος",
                        CategoriaPlural.OTHER to "%d πόντοι",
                    ),
                ClavePlural.ACIERTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d σωστή",
                        CategoriaPlural.OTHER to "%d σωστές",
                    ),
                ClavePlural.REPETICIONES to
                    mapOf(
                        CategoriaPlural.ONE to "%d φορά",
                        CategoriaPlural.OTHER to "%d φορές",
                    ),
            ),
    )
