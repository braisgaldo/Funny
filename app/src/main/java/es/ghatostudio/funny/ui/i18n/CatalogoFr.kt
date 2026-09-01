package es.ghatostudio.funny.ui.i18n

import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.dominio.textos.Clave.*

/**
 * Catálogo en francés.
 *
 * Nota sobre la donación: se evita «acheter» y «payer» en toda la app, igual que
 * en los demás idiomas. Se usa «offre-moi un café», que dice lo mismo sin
 * enmarcarlo como una compra. Lo comprueba `PruebaCatalogos`.
 */
internal val catalogoFrances =
    Catalogo(
        idioma = Idioma.FRANCES,
        textos =
            mapOf<Clave, String>(
                ACCION_VOLVER to "Retour",
                ACCION_CERRAR to "Fermer",
                ACCION_CANCELAR to "Annuler",
                ACCION_ACEPTAR to "OK",
                ACCION_CONTINUAR to "Continuer",
                ACCION_EMPEZAR to "C'est parti !",
                ACCION_LISTO to "Prêt",
                ACCION_BORRAR to "Supprimer",
                ACCION_ANADIR to "Ajouter",
                ACCION_REINTENTAR to "Réessayer",
                ACCION_COPIAR to "Copier le lien",
                ACCION_COMPARTIR to "Partager",
                ACCION_AHORA_NO to "Pas maintenant",
                ACCION_SI to "Oui",
                ACCION_NO to "Non",
                ESTADO_CARGANDO to "Chargement…",
                ESTADO_SIN_CONTENIDO to "Il n'y a pas de contenu pour ce jeu.",
                APP_LEMA to "Le jeu d'ambiance qui tient dans vos téléphones",
                MENU_JUGAR to "JOUER",
                MENU_SEGUIR_PARTIDA to "REPRENDRE LA PARTIE",
                MENU_PARTIDA_NUEVA to "NOUVELLE PARTIE",
                MENU_COMO_JUGAR to "COMMENT JOUER",
                MENU_AJUSTES to "RÉGLAGES",
                MENU_SALON to "JOUER AVEC PLUSIEURS TÉLÉPHONES",
                MENU_TOUR to "VOIR LA VISITE GUIDÉE",
                MODO_TITULO to "On joue comment ?",
                MODO_SUBTITULO to "Vous pouvez changer à chaque nouvelle partie.",
                MODO_EQUIPOS to "En équipes",
                MODO_EQUIPOS_DETALLE to
                    "De 2 à 6 équipes. Chaque équipe a son pion et c'est chacun son tour de " +
                    "mimer. La façon classique, et la plus bruyante.",
                MODO_INDIVIDUAL to "Individuel",
                MODO_INDIVIDUAL_DETALLE to
                    "De 2 à 8 personnes, chacune avec son pion et sans équipes. Dans les " +
                    "jeux où il faut mimer, celui dont c'est le tour joue et les autres " +
                    "devinent.",
                MODO_SOLITARIO to "Défi en solo",
                MODO_SOLITARIO_DETALLE to
                    "Vous contre le chrono : dix épreuves d'affilée et un record " +
                    "personnel à battre. Sans plateau et seulement avec les jeux qui " +
                    "marchent sans public.",
                PARTICIPANTES_TITULO_EQUIPOS to "Équipes",
                PARTICIPANTES_TITULO_INDIVIDUAL to "Joueurs",
                PARTICIPANTES_TITULO_SOLITARIO to "Comment vous appelez-vous ?",
                PARTICIPANTES_SUBTITULO_EQUIPOS to
                    "De %1\$d à %2\$d équipes. Notez qui joue dans chacune et le " +
                    "téléphone dira à qui c'est le tour de jouer.",
                PARTICIPANTES_SUBTITULO_INDIVIDUAL to "De %1\$d à %2\$d personnes, chacune avec son pion.",
                PARTICIPANTES_SUBTITULO_SOLITARIO to
                    "Uniquement pour mettre votre nom sur le score. Rien de " +
                    "tout cela ne quitte le téléphone.",
                PARTICIPANTES_ANADIR_EQUIPO to "+  AJOUTER UNE ÉQUIPE",
                PARTICIPANTES_ANADIR_JUGADOR to "+  AJOUTER UN JOUEUR",
                PARTICIPANTES_NUEVO_JUGADOR to "Ajouter un joueur…",
                PARTICIPANTES_SIN_JUGADORES to "Aucun nom noté : le jeu dira seulement le nom de l'équipe.",
                PARTICIPANTES_NOMBRE_EQUIPO to "Nom de l'équipe",
                PARTICIPANTES_NOMBRE_JUGADOR to "Nom",
                PARTICIPANTES_QUITAR to "Retirer",
                PARTICIPANTES_EQUIPO_POR_DEFECTO to "Équipe %1\$d",
                PARTICIPANTES_JUGADOR_POR_DEFECTO to "Joueur %1\$d",
                PARTICIPANTES_EQUIPO_1_POR_DEFECTO to "Les Cracks",
                PARTICIPANTES_EQUIPO_2_POR_DEFECTO to "Les Fauves",
                PARTICIPANTES_DESDE_SALON to "A rejoint depuis un autre téléphone",
                TABLERO_TIRAR to "LANCER LE DÉ",
                TABLERO_TURNO_DE to "AU TOUR DE %1\$s",
                TABLERO_LE_TOCA to "C'est à %1\$s",
                TABLERO_CASILLA to "Case %1\$d",
                TABLERO_SALIDA to "DÉPART",
                TABLERO_META to "ARRIVÉE",
                TABLERO_MARCADOR to "Scores",
                TABLERO_ABANDONAR to "Abandonner la partie",
                TABLERO_ABANDONAR_PREGUNTA to
                    "Vous perdrez la progression de cette partie. Voulez-vous " +
                    "vraiment quitter ?",
                TABLERO_ESPERANDO_HUB to "En attente du téléphone qui mène la partie…",
                TABLERO_AVANZA_CASILLAS to "Avance de %1\$d",
                CASILLA_COMODIN to "JOKER",
                CASILLA_COMODIN_DETALLE to "L'adversaire choisit l'épreuve qui vous tombe dessus. Sans pitié.",
                CASILLA_TODOS to "TOUT LE MONDE JOUE",
                CASILLA_TODOS_DETALLE to "La même épreuve pour toute la table. Chacun qui trouve avance d'une case.",
                CASILLA_META_AVISO to "Épreuve finale : on ne gagne qu'en la réussissant.",
                COMODIN_TITULO to "Case joker",
                COMODIN_ELIGE to "%1\$s choisit l'épreuve",
                PRUEBA_FINAL to "🏁  ÉPREUVE FINALE",
                PRUEBA_JUEGAN_TODOS to "👥  TOUT LE MONDE JOUE",
                PRUEBA_LE_TOCA_ACTUAR_A to "C'EST À",
                PRUEBA_QUIEN_DECIDA to "qui %1\$s voudra",
                PRUEBA_SOLO_MIRE_ESA_PERSONA to "👀  Seule cette personne doit regarder l'écran",
                PRUEBA_MIRA_TU_MOVIL to "📱  Regardez votre propre téléphone : le mot y est arrivé",
                PRUEBA_SEGUNDOS to "⏱  %1\$d secondes",
                PRUEBA_CUANDO_TEMA to "En quelle année ?",
                PRUEBA_CUANDO_RESPUESTA to "C'était en %1\$d.",
                PRUEBA_ACIERTOS_DE to "%1\$d sur %2\$d",
                PRUEBA_SALTAR to "PASSER",
                PRUEBA_ACERTADA to "✓  TROUVÉ",
                PRUEBA_FALLADA to "✗  RATÉ",
                PRUEBA_PROHIBIDA to "🚫  INTERDIT",
                PRUEBA_TERMINAR to "Terminer l'épreuve",
                PRUEBA_ACIERTOS to "✔  %1\$d",
                PRUEBA_VEREDICTO_TITULO to "C'est réussi ?",
                PRUEBA_VEREDICTO_LOGRADO to "✓  OUI !",
                PRUEBA_VEREDICTO_NO_LOGRADO to "✗  NON",
                PRUEBA_VEREDICTO_DECIDE_MESA to "C'est la table qui décide, pas l'appli.",
                PRUEBA_VEREDICTO_DECIDE_SOLO to "Personne ne regarde ici : soyez honnête avec vous-même.",
                PRUEBA_BORRAR_DIBUJO to "Tout effacer",
                PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA to "SEUL LE DESSINATEUR REGARDE",
                PRUEBA_DIBUJO_TIEMPO_AL_PULSAR to "Le chrono démarre quand vous appuyez sur le bouton.",
                PRUEBA_DIBUJO_EMPEZAR to "🎨   COMMENCER À DESSINER",
                PRUEBA_DIBUJO_LIENZO_VACIO to "Dessinez ici avec le doigt",
                PRUEBA_DIBUJO_ESPIAR to "Maintenir appuyé pour revoir le mot",
                PRUEBA_DIBUJO_GOMA to "Gomme",
                PRUEBA_DESHACER to "Annuler",
                PRUEBA_PINCEL to "Épaisseur",
                PRUEBA_COLOR to "Couleur",
                PRUEBA_RETO_OBJETIVO to "Il faut arriver à %1\$d",
                PRUEBA_RETO_LLEVAMOS to "Pour l'instant",
                PRUEBA_RETO_UNA_MAS to "+1  ENCORE !",
                PRUEBA_RETO_CONSEGUIDO to "RÉUSSI !",
                PRUEBA_RETO_TOCA_PARA_SUMAR to "sur %1\$d  ·  touchez pour ajouter",
                PRUEBA_RETO_RENDIRSE to "ON ABANDONNE",
                PRUEBA_RETO_NOTA to "Chaque réponse compte une fois. Les répétitions ne comptent pas.",
                PRUEBA_ORDENA_AYUDA to "Touchez-les dans le bon ordre, du premier au dernier.",
                PRUEBA_ORDENA_COMPROBAR to "VÉRIFIER",
                PRUEBA_ORDENA_CORRECTO to "Le bon ordre était :",
                PRUEBA_VF_VERDADERO to "VRAI",
                PRUEBA_VF_FALSO to "FAUX",
                PRUEBA_VF_ERA_VERDAD to "C'était vrai",
                PRUEBA_VF_ERA_MENTIRA to "C'était faux",
                PRUEBA_EMOJIS_AYUDA to "Qu'est-ce que c'est, écrit en émojis ?",
                PRUEBA_EMOJIS_ERA to "C'était : %1\$s",
                PRUEBA_CANTA_PISTA to "Commencez par là",
                PRUEBA_TRABALENGUAS_AYUDA to "Dites-le en entier et sans bafouiller.",
                PRUEBA_DESAFIO_AYUDA to "On va voir comment vous vous en sortez.",
                RONDA_TODOS_PASA_MOVIL to "Passez le téléphone sans regarder la réponse des autres.",
                RONDA_TODOS_RESPONDE to "%1\$s répond",
                RONDA_TODOS_RESUMEN to "Qui a trouvé",
                RONDA_TODOS_NADIE to "Personne. Aucun pion ne bouge.",
                RONDA_TODOS_EN_TU_MOVIL to "Répondez sur votre propre téléphone.",
                RONDA_TODOS_ESPERANDO to "En attente des autres…",
                RONDA_TODOS_PASAD_A to "PASSEZ LE TÉLÉPHONE À",
                RONDA_TODOS_PROGRESO to "%1\$d sur %2\$d. Personne ne saura qui a trouvé avant la fin.",
                RONDA_TODOS_SIN_RESPUESTA to "Temps écoulé, sans réponse.",
                RONDA_TODOS_GUARDADA to "Réponse enregistrée. Ne la dites pas encore.",
                RONDA_TODOS_CORRECTA_ERA to "LA BONNE RÉPONSE ÉTAIT",
                RONDA_TODOS_SIN_RESPONDER to "sans réponse",
                RONDA_TODOS_VER_RESULTADOS to "VOIR LES RÉSULTATS",
                RESULTADO_SUPERADA to "RÉUSSI !",
                RESULTADO_NO_HA_PODIDO_SER to "PAS CETTE FOIS",
                RESULTADO_FINAL_SUPERADA to "ÉPREUVE FINALE RÉUSSIE !",
                RESULTADO_AVANZAS_A to "Vous avancez à la case %1\$d",
                RESULTADO_TE_QUEDAS_EN to "Vous restez sur la case %1\$d",
                RESULTADO_LLEGADA_A_META to "Vous êtes arrivés à l'arrivée !",
                RESULTADO_SIGUIENTE_TURNO to "TOUR SUIVANT",
                RESULTADO_VER_RESULTADO to "VOIR LE RÉSULTAT",
                VICTORIA_TITULO to "Fin de la partie !",
                VICTORIA_GANADOR to "%1\$s gagne",
                VICTORIA_CLASIFICACION to "Classement final",
                VICTORIA_OTRA_PARTIDA to "UNE AUTRE PARTIE",
                VICTORIA_AL_MENU to "RETOUR AU MENU",
                VICTORIA_SOLITARIO_TITULO to "Défi terminé",
                VICTORIA_SOLITARIO_PUNTOS to "%1\$d points",
                VICTORIA_SOLITARIO_MEJOR to "Votre record : %1\$d",
                VICTORIA_SOLITARIO_RECORD to "Nouveau record personnel !",
                SOLITARIO_TITULO to "Défi en solo",
                SOLITARIO_SUBTITULO to "Dix épreuves. Vous contre le chrono.",
                SOLITARIO_PROGRESO to "Épreuve %1\$d sur %2\$d",
                SOLITARIO_RONDAS to "Épreuves",
                SOLITARIO_EMPEZAR to "COMMENCER LE DÉFI",
                SOLITARIO_MEJOR_MARCA to "Record : %1\$d",
                SOLITARIO_SIN_MARCA to "Pas encore de record. À vous de l'établir.",
                AJUSTES_TITULO to "Réglages",
                AJUSTES_SUBTITULO to "Conservés pour la prochaine partie.",
                AJUSTES_APARIENCIA to "Apparence",
                AJUSTES_TEMA to "Thème",
                AJUSTES_TEMA_DETALLE to "Six thèmes : trois clairs et trois sombres.",
                AJUSTES_TEMA_SISTEMA to "Suivre le système",
                AJUSTES_IDIOMA to "Langue",
                AJUSTES_IDIOMA_DETALLE to "Treize langues disponibles.",
                AJUSTES_PARTIDA to "La partie",
                AJUSTES_RITMO to "Rythme des épreuves",
                AJUSTES_RITMO_DETALLE to "Combien de temps vous avez pour chaque épreuve.",
                AJUSTES_DURACION to "Durée de la partie",
                AJUSTES_DURACION_DETALLE to "%1\$d cases jusqu'à l'arrivée · %2\$s",
                AJUSTES_JUEGOS_ACTIVOS to "Jeux de la partie",
                AJUSTES_JUEGOS_ACTIVOS_DETALLE to
                    "Retirez ceux qui ne vous plaisent pas et ils cesseront " +
                    "d'apparaître sur le plateau.",
                AJUSTES_JUEGOS_CONTADOR to "%1\$d sur %2\$d activés",
                AJUSTES_JUEGOS_MINIMO to "Il faut garder au moins un jeu activé.",
                AJUSTES_SONIDO to "Son",
                AJUSTES_SONIDO_DETALLE to "Bips du compte à rebours et alertes",
                AJUSTES_VIBRACION to "Vibration",
                AJUSTES_VIBRACION_DETALLE to "Réussites, échecs et fin du temps",
                AJUSTES_ANIMACIONES to "Animations",
                AJUSTES_ANIMACIONES_DETALLE to "Désactivez-les si vous préférez une interface immobile",
                AJUSTES_DATOS to "Vos données",
                AJUSTES_EXPORTAR to "Exporter",
                AJUSTES_EXPORTAR_DETALLE to "Enregistre équipes, réglages et records dans un fichier",
                AJUSTES_IMPORTAR to "Importer",
                AJUSTES_IMPORTAR_DETALLE to "Restaure une copie enregistrée avant",
                AJUSTES_MAS to "Plus",
                AJUSTES_APOYAR to "Soutenir le développement",
                AJUSTES_APOYAR_DETALLE to "Offrez-moi un café si vous trouvez ça utile",
                AJUSTES_COMPARTIR to "Partager Funny",
                AJUSTES_COMPARTIR_DETALLE to "Faites-en profiter quelqu'un qui va aimer",
                AJUSTES_AYUDA to "Aide",
                AJUSTES_AYUDA_DETALLE to "Comment jouer et questions fréquentes",
                AJUSTES_TOUR to "Visite guidée",
                AJUSTES_TOUR_DETALLE to "Les douze jeux et les trois modes, expliqués",
                AJUSTES_ACERCA_DE to "À propos",
                AJUSTES_ACERCA_DE_DETALLE to "Version, licences et confidentialité",
                TEMA_MODO_CLARO to "Clairs",
                TEMA_MODO_OSCURO to "Sombres",
                TEMA_FIESTA to "Fête",
                TEMA_NEON to "Néon",
                TEMA_MEDIANOCHE to "Minuit",
                TEMA_PAPEL to "Papier",
                TEMA_MENTA to "Menthe",
                TEMA_ATARDECER to "Couchant",
                IDIOMA_TITULO to "Langue",
                IDIOMA_SEGUIR_SISTEMA to "Celle du téléphone",
                IDIOMA_SUBTITULO to "Le changement s'applique immédiatement.",
                CAFE_TITULO to "Un café ?",
                CAFE_TEXTO to
                    "Cette appli est gratuite, sans publicité et ne collecte pas vos données. Si vous " +
                    "la trouvez utile, vous pouvez m'offrir un café.",
                CAFE_BOTON to "Offrez-moi un café · 1 €",
                CAFE_NO_VOLVER to "Ne plus afficher",
                CAFE_OTRO_DISPOSITIVO to "Depuis un autre appareil",
                CAFE_QR_DESCRIPCION to "Code QR avec le lien pour offrir un café à l'auteur de l'appli",
                CAFE_ILUSTRACION_DESCRIPCION to "Dessin d'une tasse de café avec de la vapeur",
                CAFE_ENLACE_COPIADO to "Lien copié",
                CAFE_GRACIAS to "Merci d'être passé par là 🙂",
                CAFE_SIN_DESBLOQUEOS to "Ça ne change rien dans le jeu : Funny est complet et le restera.",
                CAFE_ENTRADA_AJUSTES to "Soutenir le développement",
                CAFE_NO_DISPONIBLE to "Pas disponible sur cet appareil.",
                COPIA_TITULO to "Copie de vos données",
                COPIA_EXPORTAR_HECHO to "Copie enregistrée.",
                COPIA_EXPORTAR_ERROR to "La copie n'a pas pu être enregistrée.",
                COPIA_IMPORTAR_TITULO to "Importer une copie",
                COPIA_IMPORTAR_AVISO to
                    "Avant de toucher à quoi que ce soit, une copie de ce que vous avez est " +
                    "enregistrée, donc on peut toujours revenir en arrière.",
                COPIA_IMPORTAR_FUSIONAR to "Ajouter à ce que j'ai",
                COPIA_IMPORTAR_REEMPLAZAR to "Tout remplacer",
                COPIA_IMPORTAR_HECHO to "Données importées.",
                COPIA_IMPORTAR_ERROR_FORMATO to
                    "Ce fichier ne ressemble pas à une copie de Funny. Rien n'a été " +
                    "modifié.",
                COPIA_IMPORTAR_ERROR_VERSION to
                    "Cette copie vient d'une version plus récente de Funny. Mettez " +
                    "l'appli à jour et réessayez.",
                COPIA_IMPORTAR_RESPALDO to "Une sauvegarde a été enregistrée d'abord.",
                COPIA_CABECERA_DETALLE to "Copie du %1\$s · Funny %2\$s",
                AYUDA_TITULO to "Aide",
                AYUDA_SUBTITULO to "Tout ce qu'il faut pour que personne ne se perde.",
                AYUDA_QUE_ES_TITULO to "Qu'est-ce que Funny ?",
                AYUDA_QUE_ES_TEXTO to
                    "Un jeu d'ambiance avec douze épreuves différentes. On joue avec un " +
                    "téléphone qui passe de main en main ou, si plusieurs d'entre vous en ont " +
                    "un, en les connectant entre eux. Internet n'est jamais nécessaire.",
                AYUDA_COMO_SE_JUEGA_TITULO to "La partie",
                AYUDA_PARTIDA_1 to "Choisissez le mode : en équipes, individuel ou le défi en solo.",
                AYUDA_PARTIDA_2 to "Chaque équipe ou joueur a un pion et tout le monde part du DÉPART.",
                AYUDA_PARTIDA_3 to "À votre tour, vous lancez le dé et avancez de 1 à 3 cases.",
                AYUDA_PARTIDA_4 to
                    "La case où vous tombez décide de l'épreuve. Réussie, vous restez ; ratée, " +
                    "vous reculez.",
                AYUDA_PARTIDA_5 to "Gagne celui qui arrive à l'ARRIVÉE et réussit l'épreuve finale.",
                AYUDA_UN_MOVIL_TITULO to "Avec un seul téléphone",
                AYUDA_UN_MOVIL_1 to "Le téléphone circule : l'appli dit toujours à qui c'est le tour.",
                AYUDA_UN_MOVIL_2 to
                    "Au mime, au tabou, au dessin, au chant et aux défis, seul celui qui joue " +
                    "regarde.",
                AYUDA_UN_MOVIL_3 to "Dans les épreuves à répondre, l'écran peut être montré à tout le monde.",
                AYUDA_VARIOS_MOVILES_TITULO to "Avec plusieurs téléphones",
                AYUDA_VARIOS_MOVILES_1 to
                    "Un téléphone fait la table (le hub) et les autres s'y connectent. Ni " +
                    "wifi ni données mobiles.",
                AYUDA_VARIOS_MOVILES_2 to
                    "Le mot secret n'arrive que sur le téléphone de celui qui joue, donc " +
                    "personne ne le voit par erreur.",
                AYUDA_VARIOS_MOVILES_3 to
                    "Sur les cases « tout le monde joue », chacun répond sur son " +
                    "téléphone en même temps.",
                AYUDA_FAQ_TITULO to "Questions fréquentes",
                AYUDA_FAQ_1_P to "Faut-il internet ?",
                AYUDA_FAQ_1_R to
                    "Non. Funny fonctionne entièrement hors ligne, et connecter plusieurs " +
                    "téléphones utilise le Bluetooth et le wifi direct entre eux, sans passer par " +
                    "aucun réseau.",
                AYUDA_FAQ_2_P to "Peut-on jouer seul ?",
                AYUDA_FAQ_2_R to
                    "Oui : le défi en solo, ce sont dix épreuves d'affilée avec un record " +
                    "personnel. Seuls les jeux qui n'ont pas besoin de public y participent.",
                AYUDA_FAQ_3_P to "Est-ce que ça coûte quelque chose ? Y a-t-il du contenu bloqué ?",
                AYUDA_FAQ_3_R to
                    "Rien n'est bloqué et il n'y a rien de plus à obtenir. Si ça vous plaît, vous " +
                    "pouvez m'offrir un café depuis les Réglages, et ça ne change absolument rien " +
                    "dans le jeu.",
                AYUDA_FAQ_4_P to "Est-ce que ça collecte des données ?",
                AYUDA_FAQ_4_R to
                    "Non. Pas d'analytique, pas de comptes, pas de publicité. Les équipes et les " +
                    "réglages sont enregistrés seulement sur votre téléphone et n'en sortent que " +
                    "si vous exportez une copie vous-même.",
                AYUDA_FAQ_5_P to "Puis-je changer les jeux qui sortent ?",
                AYUDA_FAQ_5_R to
                    "Oui, dans Réglages → Jeux de la partie. Ceux que vous retirez cessent " +
                    "d'apparaître sur le plateau.",
                AYUDA_PROBLEMAS_TITULO to "Si quelque chose ne va pas",
                AYUDA_PROBLEMAS_TEXTO to
                    "Fermez l'appli et rouvrez-la : la partie en cours est conservée. Si " +
                    "le problème persiste, exportez vos données avant de réinstaller et " +
                    "écrivez-nous en racontant ce qui s'est passé.",
                AYUDA_ESCRIBENOS to "Écrire à l'auteur",
                ACERCA_TITULO to "À propos",
                ACERCA_VERSION to "Version",
                ACERCA_COMPILACION to "Build",
                ACERCA_FECHA to "Date",
                ACERCA_COMMIT to "Commit",
                ACERCA_LICENCIA to "Licence",
                ACERCA_LICENCIAS_TERCEROS to "Licences tierces",
                ACERCA_PRIVACIDAD to "Politique de confidentialité",
                ACERCA_CONTACTO to "Contact",
                ACERCA_CODIGO to "Code source",
                ACERCA_SIN_ANUNCIOS to "Sans publicité, sans analytique et sans comptes.",
                ACERCA_AUTOR to "Ghato Studio · Brais Galdo",
                TOUR_TITULO to "Visite guidée",
                TOUR_SUBTITULO to "Les douze jeux et les trois modes, en deux minutes.",
                TOUR_EMPEZAR to "COMMENCER LA VISITE",
                TOUR_SALTAR to "Passer",
                TOUR_ANTERIOR to "Précédent",
                TOUR_SIGUIENTE to "Suivant",
                TOUR_TERMINAR to "ON JOUE !",
                TOUR_PROGRESO to "%1\$d sur %2\$d",
                TOUR_BIENVENIDA_TITULO to "Bienvenue dans Funny",
                TOUR_BIENVENIDA_TEXTO to
                    "Douze jeux, trois façons de jouer et aucun besoin d'internet. Je vous " +
                    "explique tout en deux minutes ; vous pouvez passer quand vous voulez.",
                TOUR_MODOS_TITULO to "Trois façons de jouer",
                TOUR_MODOS_TEXTO to
                    "En équipes, c'est le classique : de 2 à 6 équipes, chacun son tour de " +
                    "jouer. Individuel, c'est pareil mais chaque personne a son pion, de 2 à 8. " +
                    "Et le défi en solo, ce sont dix épreuves contre le chrono, tout seul, avec " +
                    "un record personnel.",
                TOUR_TABLERO_TITULO to "Le plateau",
                TOUR_TABLERO_TEXTO to
                    "Chaque pion part du DÉPART. À votre tour, vous lancez le dé, avancez de " +
                    "1 à 3 cases, et la case où vous tombez décide de l'épreuve. Réussie, " +
                    "vous restez ; ratée, vous revenez où vous étiez. Gagne celui qui arrive " +
                    "à l'ARRIVÉE et réussit l'épreuve finale.",
                TOUR_CASILLAS_TITULO to "Les cases spéciales",
                TOUR_CASILLAS_TEXTO to
                    "🃏 Joker : l'adversaire choisit l'épreuve qui vous tombe dessus.\n👥 Tout " +
                    "le monde joue : la même épreuve pour toute la table, et chacun qui " +
                    "trouve avance d'une case.\n🏁 Arrivée : une épreuve finale au hasard. " +
                    "Sans la réussir, on ne gagne pas.",
                TOUR_JUEGOS_TITULO to "Les douze jeux",
                TOUR_JUEGOS_TEXTO to
                    "Les voici tous. Vous pouvez désactiver ceux qui ne vous plaisent pas dans " +
                    "les Réglages.",
                TOUR_SALON_TITULO to "Plusieurs téléphones à la fois",
                TOUR_SALON_TEXTO to
                    "Un téléphone fait la table et les autres s'y connectent par Bluetooth ou " +
                    "wifi direct, sans internet. Ça apporte ce qui compte vraiment : le mot " +
                    "secret n'arrive que sur le téléphone de celui qui joue, et sur les cases « " +
                    "tout le monde joue » chacun répond sur le sien en même temps.",
                TOUR_AJUSTES_TITULO to "À votre goût",
                TOUR_AJUSTES_TEXTO to
                    "Six thèmes, treize langues, trois rythmes et trois durées. Vous pouvez " +
                    "aussi désactiver des jeux, couper le son et la vibration, et enregistrer " +
                    "ou récupérer vos données dans un fichier.",
                TOUR_FINAL_TITULO to "C'est tout",
                TOUR_FINAL_TEXTO to
                    "Vous pouvez revoir ceci quand vous voulez depuis Réglages → Visite guidée. " +
                    "Amusez-vous bien.",
                SALON_TITULO to "Plusieurs téléphones",
                SALON_SUBTITULO to "Sans internet : ils se connectent entre eux.",
                SALON_CREAR to "FAIRE LA TABLE",
                SALON_CREAR_DETALLE to
                    "Ce téléphone mène la partie et affiche le plateau. C'est celui qu'on " +
                    "laisse sur la table.",
                SALON_UNIRSE to "REJOINDRE UNE TABLE",
                SALON_UNIRSE_DETALLE to "Ce téléphone reste dans votre main et reçoit vos épreuves en privé.",
                SALON_TU_NOMBRE to "Votre nom",
                SALON_HUB_TITULO to "Vous êtes la table",
                SALON_HUB_ESPERANDO to "En attente de connexions…",
                SALON_HUB_CONECTADOS to "Connectés",
                SALON_HUB_EMPEZAR to "COMMENCER LA PARTIE",
                SALON_CLIENTE_TITULO to "Recherche d'une table",
                SALON_CLIENTE_BUSCANDO to "Recherche de tables à proximité…",
                SALON_CLIENTE_SIN_SALONES to
                    "Aucune pour l'instant. Que l'autre téléphone ouvre « Faire la " +
                    "table » et attendez quelques secondes.",
                SALON_CLIENTE_CONECTANDO to "Connexion…",
                SALON_CLIENTE_CONECTADO to "Connecté",
                SALON_CLIENTE_ESPERA to "Vous êtes dedans. Regardez la table : la partie commence là-bas.",
                SALON_SALIR to "Quitter le salon",
                SALON_DESCONECTADO to "La connexion avec la table a été perdue.",
                SALON_ERROR_PERMISOS to "Il manque des autorisations pour trouver les téléphones à côté de vous.",
                SALON_PEDIR_PERMISOS to "DONNER LES AUTORISATIONS",
                SALON_PERMISOS_EXPLICACION to
                    "Pour trouver les téléphones qui sont à côté, Android demande " +
                    "l'autorisation d'appareils à proximité et, sur les anciennes " +
                    "versions, aussi la position. Funny ne consulte jamais où vous " +
                    "êtes et ne l'enregistre nulle part : c'est le prix que le " +
                    "système met pour utiliser le Bluetooth et le wifi direct.",
                SALON_ERROR_BLUETOOTH to "Activez le Bluetooth pour pouvoir connecter les téléphones.",
                SALON_ERROR_UBICACION to "Activez la position : Android l'exige pour chercher en Bluetooth.",
                SALON_ERROR_SERVICIOS to
                    "Ce téléphone n'a pas les services Google nécessaires pour se " +
                    "connecter. Vous pouvez continuer à jouer en vous passant un seul " +
                    "téléphone.",
                SALON_COMO_FUNCIONA to "Comment ça marche ?",
                SALON_ESTE_DISPOSITIVO to "Ce téléphone",
                SALON_ROL_HUB to "Table",
                SALON_ROL_MANDO to "Manette",
                SALON_SIN_RED to "Internet n'est utilisé à aucun moment.",
                SALON_SIN_NOMBRE to "Sans nom",
                SALON_TU_TURNO to "À vous !",
                SALON_MIRA_EL_HUB to "Regardez le téléphone de la table.",
                JUEGO_MIMICA_NOMBRE to "Mime",
                JUEGO_MIMICA_LEMA to "Mimez-le sans parler",
                JUEGO_MIMICA_INSTRUCCIONES to
                    "Une personne mime le mot avec des gestes. Interdit de parler, de " +
                    "faire des bruits ou de montrer des objets de la pièce.",
                JUEGO_DIBUJO_NOMBRE to "Croquis",
                JUEGO_DIBUJO_LEMA to "Dessinez-le sur l'écran",
                JUEGO_DIBUJO_INSTRUCCIONES to
                    "Une personne dessine sur l'écran du téléphone et les autres " +
                    "devinent. Ni lettres, ni chiffres, ni gestes.",
                JUEGO_CUANDO_NOMBRE to "Quand ?",
                JUEGO_CUANDO_LEMA to "En quelle année c'est arrivé ?",
                JUEGO_CUANDO_INSTRUCCIONES to
                    "Un événement apparaît avec quatre années possibles. Il faut " +
                    "décider laquelle est la bonne.",
                JUEGO_PREGUNTAS_NOMBRE to "Questions",
                JUEGO_PREGUNTAS_LEMA to "Culture générale",
                JUEGO_PREGUNTAS_INSTRUCCIONES to
                    "Une question avec quatre réponses. On en choisit une seule et " +
                    "on ne peut pas changer.",
                JUEGO_TABU_NOMBRE to "Tabou",
                JUEGO_TABU_LEMA to "Décrivez-le sans le dire",
                JUEGO_TABU_INSTRUCCIONES to
                    "Il faut décrire le mot sans utiliser aucun des mots interdits ni " +
                    "des mots de la même famille.",
                JUEGO_RETO_NOMBRE to "Défi éclair",
                JUEGO_RETO_LEMA to "Énumérez contre le chrono",
                JUEGO_RETO_INSTRUCCIONES to
                    "Continuez à nommer des choses de la catégorie indiquée jusqu'à " +
                    "atteindre l'objectif avant la fin du temps.",
                JUEGO_EMOJIS_NOMBRE to "Émojis",
                JUEGO_EMOJIS_LEMA to "Déchiffrez-le",
                JUEGO_EMOJIS_INSTRUCCIONES to
                    "Un film, une chanson ou un proverbe écrit uniquement en émojis, " +
                    "avec quatre réponses possibles.",
                JUEGO_VERDADERO_FALSO_NOMBRE to "Vous y croyez ?",
                JUEGO_VERDADERO_FALSO_LEMA to "Vrai ou faux",
                JUEGO_VERDADERO_FALSO_INSTRUCCIONES to
                    "Quatre affirmations bizarres à la suite. Pour chacune, " +
                    "il faut dire si c'est vrai ou faux, et ensuite on " +
                    "explique pourquoi.",
                JUEGO_TRABALENGUAS_NOMBRE to "Virelangue",
                JUEGO_TRABALENGUAS_LEMA to "Dites-le sans bafouiller",
                JUEGO_TRABALENGUAS_INSTRUCCIONES to
                    "Il faut dire le virelangue autant de fois qu'il le " +
                    "demande, en entier et sans se tromper. C'est la table qui " +
                    "juge.",
                JUEGO_ORDENA_NOMBRE to "Dans l'ordre",
                JUEGO_ORDENA_LEMA to "Remettez-le à sa place",
                JUEGO_ORDENA_INSTRUCCIONES to
                    "Quatre choses en désordre et un critère. Il faut les toucher " +
                    "dans le bon ordre.",
                JUEGO_CANTA_NOMBRE to "Chantez",
                JUEGO_CANTA_LEMA to "Continuez la chanson",
                JUEGO_CANTA_INSTRUCCIONES to
                    "Le titre et l'interprète apparaissent, et il faut se lancer dans " +
                    "le refrain. C'est la table qui juge, avec la générosité qu'elle " +
                    "voudra.",
                JUEGO_DESAFIO_NOMBRE to "Défi",
                JUEGO_DESAFIO_LEMA to "Osez",
                JUEGO_DESAFIO_INSTRUCCIONES to
                    "Un petit défi devant tout le monde. Rien de dangereux, rien " +
                    "d'humiliant : juste du ridicule bon enfant. C'est la table qui " +
                    "juge.",
                RITMO_RAPIDO to "Rapide",
                RITMO_NORMAL to "Normal",
                RITMO_TRANQUILO to "Tranquille",
                DURACION_CORTA to "Courte",
                DURACION_NORMAL to "Normale",
                DURACION_LARGA to "Longue",
                DURACION_CORTA_DETALLE to "environ 15 min",
                DURACION_NORMAL_DETALLE to "environ 30 min",
                DURACION_LARGA_DETALLE to "environ 45 min",
                A11Y_DADO to "Dé : %1\$d",
                A11Y_FICHA to "Pion de %1\$s sur la case %2\$d",
                A11Y_CASILLA to "Case %1\$d, %2\$s",
                A11Y_VOLVER to "Retour à l'écran précédent",
                A11Y_CERRAR to "Fermer",
                A11Y_LIENZO_DIBUJO to "Zone pour dessiner avec le doigt",
                A11Y_TEMA_MUESTRA to "Échantillon de couleurs du thème %1\$s",
                A11Y_BANDERA_IDIOMA to "Langue %1\$s",
            ),
        plurales =
            mapOf(
                ClavePlural.CASILLAS to
                    mapOf(
                        CategoriaPlural.ONE to "%d case",
                        CategoriaPlural.OTHER to "%d cases",
                    ),
                ClavePlural.SEGUNDOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d seconde",
                        CategoriaPlural.OTHER to "%d secondes",
                    ),
                ClavePlural.DISPOSITIVOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d téléphone connecté",
                        CategoriaPlural.OTHER to "%d téléphones connectés",
                    ),
                ClavePlural.PUNTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d point",
                        CategoriaPlural.OTHER to "%d points",
                    ),
                ClavePlural.ACIERTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d bonne réponse",
                        CategoriaPlural.OTHER to "%d bonnes réponses",
                    ),
                ClavePlural.REPETICIONES to
                    mapOf(
                        CategoriaPlural.ONE to "%d fois",
                        CategoriaPlural.OTHER to "%d fois",
                    ),
            ),
    )
