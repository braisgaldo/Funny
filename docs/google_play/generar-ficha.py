#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Escribe los textos de la ficha de Google Play en los trece idiomas y los valida.

    python docs/google_play/generar-ficha.py

Salida en `ficha/<codigo>/`:

    titulo.txt   maximo 30 caracteres
    corta.txt    maximo 80
    larga.txt    maximo 4000

El script no es solo un volcado: **valida**. Un titulo de 31 caracteres o una
descripcion corta de 81 los rechaza Play Console, y descubrirlo pegando texto en
un formulario del navegador, idioma a idioma, es una perdida de tiempo evitable.

Tambien comprueba el vocabulario prohibido del punto 4.4.1 de la plantilla
—comprar, pagar, desbloquear, pro, premium, suscripcion, precio— en cada idioma.
La regla vale para la ficha de tienda igual que para la app: si la ficha dijera
«desbloquea temas», la declaracion de «compras integradas: No» dejaria de ser
coherente.
"""
import io
import os
import sys

# La consola de Windows es cp1252: sin esto, imprimir chino o arabe revienta.
if hasattr(sys.stdout, "reconfigure"):
    sys.stdout.reconfigure(encoding="utf-8", errors="replace")

AQUI = os.path.dirname(os.path.abspath(__file__))
SALIDA = os.path.join(AQUI, "ficha")

# Los emojis de los doce juegos, en el orden del enum `Juego`. Sirven para
# comprobar que la ficha los enumera todos sin depender del idioma.
EMOJIS_DE_LOS_JUEGOS = ["🎭", "🎨", "📅", "❓",
                        "🤐", "⚡", "🍿", "🤥",
                        "👅", "🔢", "🎤", "🤸"]

MAX_TITULO = 30
MAX_CORTA = 80
MAX_LARGA = 4000

# Palabras que no pueden aparecer, por idioma. No es una lista exhaustiva de
# traducciones: son las que de verdad se colarian al escribir sobre una donacion.
PROHIBIDAS = {
    "es-ES": ["comprar", "compra", "pagar", "pago", "desbloquea", "premium",
               "suscripción", "precio"],
    "en-US": ["buy", "purchase", "unlock", "premium", "subscription", "price", " pro "],
    "fr-FR": ["acheter", "achat", "payer", "débloque", "premium", "abonnement", "prix"],
    "de-DE": ["kaufen", "kauf ", "bezahlen", "freischalt", "premium", "abo", "preis"],
    "it-IT": ["comprare", "acquist", "pagare", "sblocca", "premium", "abbonamento", "prezzo"],
    "gl-ES": ["comprar", "pagar", "desbloquea", "premium", "subscrición", "prezo"],
    "ca-ES": ["comprar", "pagar", "desbloqueja", "premium", "subscripció", "preu"],
    "eu-ES": ["erosi", "ordaindu", "desblokea", "premium", "harpidetza", "prezio"],
    "el-GR": ["αγορά", "αγοράσ", "πληρώ", "ξεκλειδών", "premium", "συνδρομή", "τιμή"],
    "ru-RU": ["купить", "покупка", "оплат", "разблокир", "премиум", "подписка", "цена"],
    "ar": ["شراء", "اشتر", "دفع", "فتح ميزة", "بريميوم", "اشتراك", "سعر"],
    "zh-CN": ["购买", "付费", "解锁", "高级版", "订阅", "价格"],
    "ja-JP": ["購入", "課金", "アンロック", "プレミアム", "サブスク", "価格"],
}

TITULO = "Funny"

# --------------------------------------------------------------------------
# Los textos
#
# La descripcion larga tiene la misma estructura en los trece idiomas: que es,
# los doce juegos, los tres modos, las cuatro modalidades, varios moviles, sin
# internet, los idiomas y el apartado de honestidad sobre el cafe.
#
# El ultimo apartado no es relleno de marketing: es lo que hace que la
# declaracion de «compras integradas: No» se sostenga. Si alguien de la revision
# lee la ficha, tiene que encontrar ahi que la donacion no da nada a cambio.
# --------------------------------------------------------------------------

FICHA = {}

FICHA["es-ES"] = {
    "corta": "Juego de fiesta con 12 pruebas. Sin internet, sin anuncios, sin extras.",
    "larga": """Funny es un juego de fiesta para jugar alrededor de una mesa, con la gente que tengas delante. Con un solo móvil que va pasando de mano en mano, o con un móvil por persona.

DOCE PRUEBAS

🎭 Mímica — represéntalo sin hablar
🎨 Pinturillo — dibújalo en la pantalla
📅 ¿Cuándo? — acierta en qué año ocurrió
❓ Preguntas — cultura general
🤐 Tabú — descríbelo sin decirlo, y sin cuatro palabras más
⚡ Reto rápido — enumera contrarreloj
🍿 Emojis — descifra la película o la serie
🤥 ¿Te lo crees? — verdadero o falso, con la explicación al final
👅 Trabalenguas — dilo sin trabarte
🔢 Ordena — ponlo en su sitio
🎤 Canta — sigue la canción
🤸 Desafío — atrévete

Más de 1.300 cartas escritas a mano, no generadas.

TRES MODOS

• Por equipos, de 2 a 6 equipos, rotando quién actúa
• Individual, de 2 a 8 jugadores, cada uno con su ficha
• Reto en solitario, para una sola persona: una tanda de pruebas contra el reloj y una marca personal que batir

CUATRO MODALIDADES

Partida rápida (unos 18 min), normal (unos 30), extrema (unos 48) o a tu medida, eligiendo cuántas casillas y cuántas pruebas. Y puedes quitar los juegos que no os gusten.

VARIOS MÓVILES A LA VEZ

Hasta cinco móviles en la misma partida: uno hace de mesa y los demás se conectan por Bluetooth o Wi-Fi Direct. Sin internet, sin router y sin emparejar nada.

No es un adorno. Es lo que hace que la palabra secreta del tabú llegue solo al móvil de quien actúa, en lugar de que la vea media mesa de refilón. Y que en las casillas de «juegan todos» cada uno responda en el suyo al mismo tiempo.

SIN INTERNET Y SIN DATOS

Funny no tiene ni acceso a internet: no declara ese permiso, así que Android le impide conectarse aunque quisiera. Sin analítica, sin publicidad, sin cuentas y sin registro. Todo lo que guarda se queda en tu móvil, y puedes llevártelo a otro con un fichero.

SEIS TEMAS Y TRECE IDIOMAS

Tres temas oscuros y tres claros, o que siga al del sistema. Y trece idiomas completos: castellano, inglés, francés, alemán, chino simplificado, japonés, ruso, italiano, griego, árabe, gallego, catalán y euskera. El árabe, con la interfaz de derecha a izquierda.

GRATIS, Y GRATIS DE VERDAD

Los doce juegos, los seis temas, los trece idiomas y el modo de varios móviles están ahí desde la primera vez que abres la app. No hay versión reducida, no hay contenido reservado y no hay nada que conseguir aparte.

Hay una opción para invitarme a un café si te apetece, y no da absolutamente nada a cambio: no cambia nada dentro del juego. Se abre en el navegador del sistema y la app no gestiona ningún cobro.

Hecho por una persona, en Galicia.""",
}

FICHA["en-US"] = {
    "corta": "Party game with 12 challenges. No internet, no ads, nothing held back.",
    "larga": """Funny is a party game for playing around a table, with the people in front of you. With one phone passed from hand to hand, or with a phone each.

TWELVE CHALLENGES

🎭 Mime — act it out without speaking
🎨 Draw it — sketch on the screen
📅 What year? — guess when it happened
❓ Trivia — general knowledge
🤐 Taboo — describe it without saying it, or four other words
⚡ Quick fire — list things against the clock
🍿 Emoji — decode the film or the series
🤥 True or false — with the explanation afterwards
👅 Tongue twisters — say it without stumbling
🔢 Put in order — get the sequence right
🎤 Sing — carry on the song
🤸 Dare — go on, then

Over 1,200 cards written by hand, not generated.

THREE MODES

• Teams, 2 to 6 of them, taking turns to act
• Individual, 2 to 8 players, one token each
• Solo challenge, for one person: a run of rounds against the clock and a personal best to beat

FOUR GAME MODES

Quick game (about 18 min), normal (about 30), extreme (about 48), or your own, choosing how many squares and how many rounds. And you can switch off any games you don't like.

SEVERAL PHONES AT ONCE

Up to five phones in the same game: one acts as the table and the rest connect over Bluetooth or Wi-Fi Direct. No internet, no router, no pairing anything.

It isn't decoration. It's what makes the secret word in Taboo reach only the phone of whoever is acting, instead of half the table catching a glimpse. And in the "everyone plays" squares, each person answers on their own phone at the same time.

NO INTERNET, NO DATA

Funny doesn't even have internet access: it doesn't declare that permission, so Android stops it from connecting even if it wanted to. No analytics, no ads, no accounts, no sign-up. Everything it stores stays on your phone, and you can move it to another one with a file.

SIX THEMES, THIRTEEN LANGUAGES

Three dark themes and three light ones, or follow the system. And thirteen complete languages: English, Spanish, French, German, Simplified Chinese, Japanese, Russian, Italian, Greek, Arabic, Galician, Catalan and Basque. Arabic comes with the interface mirrored right to left.

FREE, AND ACTUALLY FREE

The twelve games, the six themes, the thirteen languages and the multi-phone mode are all there the first time you open the app. There's no cut-down version, no reserved content and nothing to obtain separately.

There's an option to treat me to a coffee if you feel like it, and it gives you absolutely nothing in return: it changes nothing inside the game. It opens in the system browser and the app handles no payment at all.

Made by one person, in Galicia.""",
}

FICHA["fr-FR"] = {
    "corta": "Jeu d'ambiance, 12 épreuves. Sans internet, sans publicité, rien de réservé.",
    "larga": """Funny est un jeu d'ambiance pour jouer autour d'une table, avec les gens en face de vous. Avec un seul téléphone qui passe de main en main, ou un téléphone chacun.

DOUZE ÉPREUVES

🎭 Mime — sans parler
🎨 Dessine — sur l'écran
📅 En quelle année ? — devinez quand ça s'est passé
❓ Culture générale
🤐 Tabou — décrivez-le sans le dire, ni quatre autres mots
⚡ Défi rapide — énumérez contre le chrono
🍿 Emojis — déchiffrez le film ou la série
🤥 Vrai ou faux — avec l'explication à la fin
👅 Virelangues — sans buter
🔢 Dans l'ordre — remettez tout à sa place
🎤 Chante — reprenez la chanson
🤸 Cap ou pas cap

Plus de 1 200 cartes écrites à la main, pas générées.

TROIS MODES

• En équipes, de 2 à 6, chacun son tour de jouer
• Individuel, de 2 à 8 joueurs, chacun son pion
• Défi en solo, pour une personne : une série d'épreuves contre le chrono et un record personnel à battre

QUATRE MODES DE PARTIE

Partie rapide (environ 18 min), normale (environ 30), extrême (environ 48), ou à votre mesure, en choisissant le nombre de cases et d'épreuves. Et vous pouvez désactiver les jeux qui ne vous plaisent pas.

PLUSIEURS TÉLÉPHONES À LA FOIS

Jusqu'à cinq téléphones dans la même partie : l'un fait la table et les autres se connectent en Bluetooth ou Wi-Fi Direct. Sans internet, sans box et sans rien appairer.

Ce n'est pas un gadget. C'est ce qui fait que le mot secret du Tabou n'arrive que sur le téléphone de la personne qui joue, au lieu que la moitié de la table l'aperçoive. Et sur les cases « tout le monde joue », chacun répond sur le sien en même temps.

SANS INTERNET ET SANS DONNÉES

Funny n'a même pas accès à internet : l'autorisation n'est pas déclarée, donc Android l'empêche de se connecter même si elle le voulait. Aucune analyse d'audience, aucune publicité, aucun compte, aucune inscription. Tout ce qu'elle garde reste sur votre téléphone, et vous pouvez l'emporter ailleurs dans un fichier.

SIX THÈMES, TREIZE LANGUES

Trois thèmes sombres et trois clairs, ou suivre le système. Et treize langues complètes : français, anglais, espagnol, allemand, chinois simplifié, japonais, russe, italien, grec, arabe, galicien, catalan et basque. L'arabe avec l'interface de droite à gauche.

GRATUIT, ET VRAIMENT GRATUIT

Les douze jeux, les six thèmes, les treize langues et le mode multi-téléphone sont là dès la première ouverture. Pas de version réduite, pas de contenu réservé, rien à obtenir à part.

Il y a une option pour m'offrir un café si l'envie vous prend, et elle ne donne absolument rien en échange : elle ne change rien dans le jeu. Elle s'ouvre dans le navigateur du système et l'application ne gère aucune transaction.

Fait par une seule personne, en Galice.""",
}

FICHA["de-DE"] = {
    "corta": "Partyspiel mit 12 Aufgaben. Ohne Internet, ohne Werbung, ohne Extras.",
    "larga": """Funny ist ein Partyspiel für den Tisch, mit den Leuten, die dir gegenübersitzen. Mit einem Handy, das herumgeht, oder mit einem Handy pro Person.

ZWÖLF AUFGABEN

🎭 Pantomime — ohne zu sprechen
🎨 Zeichnen — auf dem Bildschirm
📅 Welches Jahr? — wann war das?
❓ Wissensfragen
🤐 Tabu — beschreiben, ohne es zu sagen, und ohne vier weitere Wörter
⚡ Schnellrunde — aufzählen gegen die Uhr
🍿 Emojis — Film oder Serie entschlüsseln
🤥 Wahr oder falsch — mit Erklärung danach
👅 Zungenbrecher — ohne zu stolpern
🔢 Sortieren — in die richtige Reihenfolge
🎤 Singen — das Lied weiterführen
🤸 Wagnis — trau dich

Über 1.200 handgeschriebene Karten, nicht generiert.

DREI MODI

• In Teams, 2 bis 6, abwechselnd wer vorspielt
• Einzeln, 2 bis 8 Spieler, jeder mit eigener Figur
• Solo-Herausforderung für eine Person: eine Reihe Aufgaben gegen die Uhr und ein Rekord zum Schlagen

VIER SPIELMODI

Schnelle Partie (etwa 18 Min.), normale (etwa 30), extreme (etwa 48) oder nach Maß, mit selbst gewählter Anzahl von Feldern und Aufgaben. Und Spiele, die euch nicht gefallen, lassen sich abschalten.

MEHRERE HANDYS GLEICHZEITIG

Bis zu fünf Handys in derselben Partie: eines ist der Tisch, die anderen verbinden sich per Bluetooth oder Wi-Fi Direct. Ohne Internet, ohne Router, ohne Kopplung.

Das ist keine Zierde. Es sorgt dafür, dass das geheime Wort bei Tabu nur auf dem Handy der Person landet, die dran ist, statt dass es der halbe Tisch mitbekommt. Und auf den Feldern „alle spielen" antwortet jeder gleichzeitig auf seinem eigenen.

OHNE INTERNET UND OHNE DATEN

Funny hat gar keinen Internetzugang: die Berechtigung wird nicht deklariert, also verhindert Android jede Verbindung. Keine Analyse, keine Werbung, keine Konten, keine Anmeldung. Alles bleibt auf dem Handy, und du kannst es per Datei mitnehmen.

SECHS DESIGNS, DREIZEHN SPRACHEN

Drei dunkle und drei helle Designs, oder dem System folgen. Und dreizehn vollständige Sprachen: Deutsch, Englisch, Spanisch, Französisch, Chinesisch (vereinfacht), Japanisch, Russisch, Italienisch, Griechisch, Arabisch, Galicisch, Katalanisch und Baskisch. Arabisch mit gespiegelter Oberfläche.

KOSTENLOS, UND WIRKLICH KOSTENLOS

Die zwölf Spiele, die sechs Designs, die dreizehn Sprachen und der Mehrgeräte-Modus sind beim ersten Start alle da. Keine abgespeckte Fassung, keine zurückgehaltenen Inhalte, nichts extra zu holen.

Es gibt eine Möglichkeit, mir einen Kaffee zu spendieren, wenn du magst, und sie gibt absolut nichts zurück: sie ändert nichts im Spiel. Sie öffnet den System-Browser, und die App verarbeitet keine Zahlung.

Von einer Person gemacht, in Galicien.""",
}

FICHA["it-IT"] = {
    "corta": "Gioco di società, 12 prove. Senza internet, senza pubblicità, senza extra.",
    "larga": """Funny è un gioco di società da fare intorno a un tavolo, con le persone che hai davanti. Con un telefono che passa di mano in mano, o con un telefono ciascuno.

DODICI PROVE

🎭 Mimo — senza parlare
🎨 Disegna — sullo schermo
📅 In che anno? — indovina quando è successo
❓ Cultura generale
🤐 Tabù — descrivilo senza dirlo, e senza altre quattro parole
⚡ Prova lampo — elenca contro il cronometro
🍿 Emoji — decifra il film o la serie
🤥 Vero o falso — con la spiegazione alla fine
👅 Scioglilingua — senza inciampare
🔢 Metti in ordine
🎤 Canta — vai avanti tu
🤸 Sfida — coraggio

Più di 1.200 carte scritte a mano, non generate.

TRE MODALITÀ

• A squadre, da 2 a 6, alternando chi recita
• Individuale, da 2 a 8 giocatori, ognuno con la sua pedina
• Sfida in solitaria, per una persona: una serie di prove contro il cronometro e un record personale da battere

QUATTRO MODALITÀ DI PARTITA

Partita rapida (circa 18 min), normale (circa 30), estrema (circa 48) o su misura, scegliendo quante caselle e quante prove. E i giochi che non vi piacciono si possono disattivare.

PIÙ TELEFONI ALLA VOLTA

Fino a cinque telefoni nella stessa partita: uno fa da tavolo e gli altri si collegano via Bluetooth o Wi-Fi Direct. Senza internet, senza router e senza accoppiare niente.

Non è un ornamento. È ciò che fa arrivare la parola segreta del tabù solo al telefono di chi recita, invece di farla vedere a mezzo tavolo. E nelle caselle «giocano tutti» ognuno risponde sul proprio, nello stesso momento.

SENZA INTERNET E SENZA DATI

Funny non ha nemmeno accesso a internet: quel permesso non è dichiarato, quindi Android le impedisce di collegarsi anche se volesse. Nessuna analisi, nessuna pubblicità, nessun account, nessuna registrazione. Tutto resta sul telefono, e puoi portartelo altrove con un file.

SEI TEMI, TREDICI LINGUE

Tre temi scuri e tre chiari, o seguire il sistema. E tredici lingue complete: italiano, inglese, spagnolo, francese, tedesco, cinese semplificato, giapponese, russo, greco, arabo, galiziano, catalano e basco. L'arabo con l'interfaccia da destra a sinistra.

GRATIS, E GRATIS DAVVERO

I dodici giochi, i sei temi, le tredici lingue e la modalità multi-telefono sono lì dalla prima volta che apri l'app. Nessuna versione ridotta, nessun contenuto riservato, niente da ottenere a parte.

C'è un'opzione per offrirmi un caffè, se ti va, e non dà assolutamente nulla in cambio: non cambia niente dentro il gioco. Si apre nel browser di sistema e l'app non gestisce alcuna transazione.

Fatto da una persona sola, in Galizia.""",
}

FICHA["gl-ES"] = {
    "corta": "Xogo de festa con 12 probas. Sen internet, sen anuncios e sen extras.",
    "larga": """Funny é un xogo de festa para xogar ao redor dunha mesa, coa xente que teñas diante. Cun só móbil que vai pasando de man en man, ou cun móbil por persoa.

DOCE PROBAS

🎭 Mímica — represéntao sen falar
🎨 Pinturillo — debúxao na pantalla
📅 ¿Cando? — acerta en que ano ocorreu
❓ Preguntas — cultura xeral
🤐 Tabú — descríbeo sen dicilo, e sen catro palabras máis
⚡ Reto rápido — enumera contrarreloxo
🍿 Emojis — descifra a película ou a serie
🤥 ¿Cres iso? — verdadeiro ou falso, coa explicación ao final
👅 Trabalinguas — dío sen trabarte
🔢 Ordena — ponno no seu sitio
🎤 Canta — segue a canción
🤸 Desafío — atrévete

Máis de 1.300 cartas escritas a man, non xeradas.

TRES MODOS

• Por equipos, de 2 a 6, rotando quen actúa
• Individual, de 2 a 8 xogadores, cada un coa súa ficha
• Reto en solitario, para unha soa persoa: unha tanda de probas contra o reloxo e unha marca persoal que bater

CATRO MODALIDADES

Partida rápida (uns 18 min), normal (uns 30), extrema (uns 48) ou á túa medida, escollendo cantas casas e cantas probas. E podes quitar os xogos que non vos gusten.

VARIOS MÓBILES Á VEZ

Ata cinco móbiles na mesma partida: un fai de mesa e os demais conéctanse por Bluetooth ou Wi-Fi Direct. Sen internet, sen router e sen emparellar nada.

Non é un adorno. É o que fai que a palabra secreta do tabú chegue só ao móbil de quen actúa, en vez de que a vexa media mesa de esguello. E que nas casas de «xogan todos» cada un responda no seu ao mesmo tempo.

SEN INTERNET E SEN DATOS

Funny non ten nin acceso a internet: non declara ese permiso, así que Android impídelle conectarse aínda que quixese. Sen analítica, sen publicidade, sen contas e sen rexistro. Todo o que garda queda no teu móbil, e podes levalo a outro cun ficheiro.

SEIS TEMAS E TRECE IDIOMAS

Tres temas escuros e tres claros, ou que siga ao do sistema. E trece idiomas completos: galego, castelán, inglés, francés, alemán, chinés simplificado, xaponés, ruso, italiano, grego, árabe, catalán e éuscaro. O árabe, coa interface de dereita a esquerda.

DE GRAZA, E DE GRAZA DE VERDADE

Os doce xogos, os seis temas, os trece idiomas e o modo de varios móbiles están aí desde a primeira vez que abres a app. Non hai versión reducida, non hai contido reservado e non hai nada que conseguir aparte.

Hai unha opción para convidarme a un café se che apetece, e non dá absolutamente nada a cambio: non cambia nada dentro do xogo. Ábrese no navegador do sistema e a app non xestiona ningún cobro.

Feito por unha persoa, en Galicia.""",
}

FICHA["ca-ES"] = {
    "corta": "Joc de festa amb 12 proves. Sense internet, sense anuncis i sense extres.",
    "larga": """Funny és un joc de festa per jugar al voltant d'una taula, amb la gent que tens al davant. Amb un sol mòbil que va passant de mà en mà, o amb un mòbil per persona.

DOTZE PROVES

🎭 Mímica — representa-ho sense parlar
🎨 Pinturillo — dibuixa-ho a la pantalla
📅 Quan? — encerta en quin any va passar
❓ Preguntes — cultura general
🤐 Tabú — descriu-ho sense dir-ho, i sense quatre paraules més
⚡ Repte ràpid — enumera contrarellotge
🍿 Emojis — desxifra la pel·lícula o la sèrie
🤥 T'ho creus? — vertader o fals, amb l'explicació al final
👅 Embarbussaments — digue-ho sense travar-te
🔢 Ordena — posa-ho al seu lloc
🎤 Canta — segueix la cançó
🤸 Desafiament — atreveix-t'hi

Més de 1.200 cartes escrites a mà, no generades.

TRES MODES

• Per equips, de 2 a 6, rotant qui actua
• Individual, de 2 a 8 jugadors, cadascun amb la seva fitxa
• Repte en solitari, per a una sola persona: una tanda de proves contra el rellotge i una marca personal per batre

QUATRE MODALITATS

Partida ràpida (uns 18 min), normal (uns 30), extrema (uns 48) o a la teva mida, triant quantes caselles i quantes proves. I pots treure els jocs que no us agradin.

DIVERSOS MÒBILS A L'HORA

Fins a cinc mòbils en la mateixa partida: un fa de taula i els altres es connecten per Bluetooth o Wi-Fi Direct. Sense internet, sense router i sense aparellar res.

No és un adorn. És el que fa que la paraula secreta del tabú arribi només al mòbil de qui actua, en lloc que la vegi mitja taula de reüll. I que a les caselles de «juguen tots» cadascú respongui al seu, al mateix temps.

SENSE INTERNET I SENSE DADES

Funny no té ni accés a internet: no declara aquest permís, així que Android li impedeix connectar-se encara que volgués. Sense analítica, sense publicitat, sense comptes i sense registre. Tot el que guarda es queda al teu mòbil, i pots endur-t'ho a un altre amb un fitxer.

SIS TEMES I TRETZE IDIOMES

Tres temes foscos i tres clars, o que segueixi el del sistema. I tretze idiomes complets: català, castellà, anglès, francès, alemany, xinès simplificat, japonès, rus, italià, grec, àrab, gallec i basc. L'àrab, amb la interfície de dreta a esquerra.

GRATIS, I GRATIS DE VERITAT

Els dotze jocs, els sis temes, els tretze idiomes i el mode de diversos mòbils hi són des de la primera vegada que obres l'app. No hi ha versió reduïda, no hi ha contingut reservat i no hi ha res a aconseguir a part.

Hi ha una opció per convidar-me a un cafè si et ve de gust, i no dona absolutament res a canvi: no canvia res dins del joc. S'obre al navegador del sistema i l'app no gestiona cap cobrament.

Fet per una persona, a Galícia.""",
}

FICHA["eu-ES"] = {
    "corta": "Festa-jokoa, 12 proba. Internetik gabe, iragarkirik gabe, gehigarririk gabe.",
    "larga": """Funny mahai baten inguruan jokatzeko festa-jokoa da, aurrean duzun jendearekin. Esku batetik bestera pasatzen den mugikor bakarrarekin, edo pertsona bakoitzak bere mugikorrarekin.

HAMABI PROBA

🎭 Mimika — antzeztu hitz egin gabe
🎨 Marraztu — pantailan
📅 Noiz? — asmatu zein urtean gertatu zen
❓ Galderak — kultura orokorra
🤐 Tabu — deskribatu esan gabe, eta beste lau hitz erabili gabe
⚡ Proba azkarra — zerrendatu erlojuaren aurka
🍿 Emojiak — deszifratu filma edo seriea
🤥 Sinesten duzu? — egia ala gezurra, azalpenarekin amaieran
👅 Aho-korapiloak — esan trabatu gabe
🔢 Ordenatu — jarri bere lekuan
🎤 Kantatu — jarraitu abestia
🤸 Desafioa — ausartu

1.200 karta baino gehiago eskuz idatzita, ez sortuta.

HIRU MODU

• Taldeka, 2 eta 6 talde artean, nork antzezten duen txandaka
• Banaka, 2 eta 8 jokalari artean, bakoitzak bere fitxa
• Bakarkako erronka, pertsona bakarrentzat: proba sail bat erlojuaren aurka eta hausteko marka pertsonal bat

LAU PARTIDA MODU

Partida azkarra (18 minutu inguru), normala (30 inguru), muturrekoa (48 inguru) edo zure neurrira, zenbat lauki eta zenbat proba aukeratuz. Eta gustatzen ez zaizuen jokoak kendu ditzakezue.

MUGIKOR BAT BAINO GEHIAGO ALDI BEREAN

Bost mugikorrera arte partida berean: batek mahaia egiten du eta besteak Bluetooth edo Wi-Fi Direct bidez konektatzen dira. Internetik gabe, routerrik gabe eta ezer parekatu gabe.

Ez da apaingarria. Horri esker tabuaren hitz sekretua antzezten duenaren mugikorrera bakarrik iristen da, mahaiaren erdiak zeharka ikusi beharrean. Eta «denek jokatzen dute» laukietan bakoitzak berean erantzuten du, aldi berean.

INTERNETIK GABE ETA DATURIK GABE

Funny-k ez du interneteko sarbiderik ere: baimen hori ez du deklaratzen, beraz Androidek konektatzea galarazten dio nahi izanda ere. Analitikarik gabe, publizitaterik gabe, konturik gabe eta izen-ematerik gabe. Gordetzen duen guztia zure mugikorrean geratzen da, eta fitxategi batekin beste batera eraman dezakezu.

SEI ITXURA ETA HAMAHIRU HIZKUNTZA

Hiru itxura ilun eta hiru argi, edo sistemarena jarraitu. Eta hamahiru hizkuntza osoak: euskara, gaztelania, ingelesa, frantsesa, alemana, txinera sinplifikatua, japoniera, errusiera, italiera, greziera, arabiera, galegoa eta katalana. Arabiera, interfazea eskuinetik ezkerrera.

DOAN, ETA BENETAN DOAN

Hamabi jokoak, sei itxurak, hamahiru hizkuntzak eta mugikor anitzeko modua hor daude app-a lehen aldiz irekitzen duzunetik. Ez dago bertsio murriztua, ez dago eduki gordea eta ez dago aparte lortu beharreko ezer.

Kafe bat gonbidatzeko aukera bat dago, gogoa baduzu, eta ez du ezer ematen ordainetan: ez du ezer aldatzen jokoaren barruan. Sistemaren nabigatzailean irekitzen da eta app-ak ez du inolako kobrantza kudeatzen.

Pertsona bakar batek egina, Galizian.""",
}

FICHA["el-GR"] = {
    "corta": "Παιχνίδι πάρτι με 12 δοκιμασίες. Χωρίς internet, χωρίς διαφημίσεις.",
    "larga": """Το Funny είναι ένα παιχνίδι πάρτι για να παίζεται γύρω από ένα τραπέζι, με τους ανθρώπους που έχεις μπροστά σου. Με ένα κινητό που περνάει από χέρι σε χέρι, ή με ένα κινητό ανά άτομο.

ΔΩΔΕΚΑ ΔΟΚΙΜΑΣΙΕΣ

🎭 Παντομίμα — χωρίς να μιλάς
🎨 Ζωγράφισέ το — στην οθόνη
📅 Πότε; — βρες σε ποια χρονιά έγινε
❓ Ερωτήσεις — γενικές γνώσεις
🤐 Ταμπού — περιέγραψέ το χωρίς να το πεις, και χωρίς άλλες τέσσερις λέξεις
⚡ Γρήγορη δοκιμασία — απαρίθμησε με το χρονόμετρο
🍿 Emoji — αποκρυπτογράφησε την ταινία ή τη σειρά
🤥 Το πιστεύεις; — σωστό ή λάθος, με την εξήγηση στο τέλος
👅 Γλωσσοδέτες — πες το χωρίς να σκοντάψεις
🔢 Βάλε σε σειρά
🎤 Τραγούδα — συνέχισε το τραγούδι
🤸 Πρόκληση — τόλμησε

Πάνω από 1.200 κάρτες γραμμένες στο χέρι, όχι παραγόμενες.

ΤΡΕΙΣ ΤΡΟΠΟΙ

• Σε ομάδες, από 2 έως 6, αλλάζοντας ποιος υποδύεται
• Ατομικά, από 2 έως 8 παίκτες, ο καθένας με το πιόνι του
• Μονή πρόκληση, για ένα άτομο: μια σειρά δοκιμασιών εναντίον του χρόνου και ένα προσωπικό ρεκόρ να σπάσεις

ΤΕΣΣΕΡΙΣ ΤΡΟΠΟΙ ΠΑΙΧΝΙΔΙΟΥ

Γρήγορη παρτίδα (περίπου 18 λεπτά), κανονική (περίπου 30), ακραία (περίπου 48) ή στα μέτρα σου, επιλέγοντας πόσες θέσεις και πόσες δοκιμασίες. Και μπορείς να βγάλεις τα παιχνίδια που δεν σας αρέσουν.

ΠΟΛΛΑ ΚΙΝΗΤΑ ΜΑΖΙ

Έως πέντε κινητά στην ίδια παρτίδα: το ένα κάνει το τραπέζι και τα άλλα συνδέονται με Bluetooth ή Wi-Fi Direct. Χωρίς internet, χωρίς router και χωρίς να συζευχθεί τίποτα.

Δεν είναι στολίδι. Είναι αυτό που κάνει τη μυστική λέξη του Ταμπού να φτάνει μόνο στο κινητό αυτού που υποδύεται, αντί να τη δει μισό τραπέζι με την άκρη του ματιού. Και στις θέσεις «παίζουν όλοι», ο καθένας απαντά στο δικό του, ταυτόχρονα.

ΧΩΡΙΣ INTERNET ΚΑΙ ΧΩΡΙΣ ΔΕΔΟΜΕΝΑ

Το Funny δεν έχει ούτε πρόσβαση στο internet: δεν δηλώνει αυτή την άδεια, οπότε το Android το εμποδίζει να συνδεθεί ακόμα κι αν το ήθελε. Χωρίς αναλυτικά, χωρίς διαφημίσεις, χωρίς λογαριασμούς και χωρίς εγγραφή. Ό,τι αποθηκεύει μένει στο κινητό σου, και μπορείς να το πάρεις σε άλλο με ένα αρχείο.

ΕΞΙ ΘΕΜΑΤΑ ΚΑΙ ΔΕΚΑΤΡΕΙΣ ΓΛΩΣΣΕΣ

Τρία σκούρα θέματα και τρία φωτεινά, ή να ακολουθεί το σύστημα. Και δεκατρείς πλήρεις γλώσσες: ελληνικά, ισπανικά, αγγλικά, γαλλικά, γερμανικά, απλοποιημένα κινεζικά, ιαπωνικά, ρωσικά, ιταλικά, αραβικά, γαλικιανά, καταλανικά και βασκικά. Τα αραβικά, με τη διεπαφή από δεξιά προς αριστερά.

ΔΩΡΕΑΝ, ΚΑΙ ΔΩΡΕΑΝ ΣΤΑ ΑΛΗΘΕΙΑ

Τα δώδεκα παιχνίδια, τα έξι θέματα, οι δεκατρείς γλώσσες και ο τρόπος με πολλά κινητά είναι εκεί από την πρώτη φορά που ανοίγεις την εφαρμογή. Δεν υπάρχει περιορισμένη έκδοση, δεν υπάρχει δεσμευμένο περιεχόμενο και δεν υπάρχει τίποτα να αποκτήσεις ξεχωριστά.

Υπάρχει μια επιλογή να μου κεράσεις έναν καφέ, αν σου κάνει κέφι, και δεν δίνει απολύτως τίποτα σε αντάλλαγμα: δεν αλλάζει τίποτα μέσα στο παιχνίδι. Ανοίγει στο πρόγραμμα περιήγησης του συστήματος και η εφαρμογή δεν διαχειρίζεται καμία χρέωση.

Φτιαγμένο από ένα άτομο, στη Γαλικία.""",
}

FICHA["ru-RU"] = {
    "corta": "Игра для компании, 12 заданий. Без интернета, без рекламы, всё сразу.",
    "larga": """Funny — игра для компании за одним столом, с теми, кто сидит напротив. С одним телефоном, который передают из рук в руки, или с телефоном у каждого.

ДВЕНАДЦАТЬ ЗАДАНИЙ

🎭 Пантомима — покажи без слов
🎨 Нарисуй — на экране
📅 Когда? — угадай, в каком году это было
❓ Вопросы — общие знания
🤐 Табу — опиши, не называя, и без четырёх других слов
⚡ Быстрое задание — перечисляй против часов
🍿 Эмодзи — расшифруй фильм или сериал
🤥 Верите? — правда или ложь, с объяснением в конце
👅 Скороговорки — скажи не запнувшись
🔢 Расставь по порядку
🎤 Пой — продолжи песню
🤸 Вызов — рискни

Больше 1200 карточек, написанных вручную, а не сгенерированных.

ТРИ РЕЖИМА

• Командами, от 2 до 6, показывает каждый по очереди
• По одному, от 2 до 8 игроков, у каждого своя фишка
• Одиночный вызов, для одного человека: серия заданий против часов и личный рекорд, который надо побить

ЧЕТЫРЕ РЕЖИМА ПАРТИИ

Быстрая партия (около 18 мин), обычная (около 30), экстремальная (около 48) или по твоей мерке, с выбором числа клеток и заданий. И игры, которые вам не нравятся, можно отключить.

НЕСКОЛЬКО ТЕЛЕФОНОВ СРАЗУ

До пяти телефонов в одной партии: один играет роль стола, остальные подключаются по Bluetooth или Wi-Fi Direct. Без интернета, без роутера и без сопряжения.

Это не украшение. Именно поэтому секретное слово в табу приходит только на телефон того, кто показывает, а не попадается на глаза половине стола. А на клетках «играют все» каждый отвечает на своём, одновременно.

БЕЗ ИНТЕРНЕТА И БЕЗ ДАННЫХ

У Funny нет даже доступа в интернет: это разрешение не объявлено, поэтому Android не даст приложению подключиться, даже если бы оно захотело. Без аналитики, без рекламы, без аккаунтов и без регистрации. Всё, что сохраняется, остаётся в твоём телефоне, и его можно перенести файлом.

ШЕСТЬ ТЕМ И ТРИНАДЦАТЬ ЯЗЫКОВ

Три тёмные темы и три светлые, или следовать системной. И тринадцать полных языков: русский, испанский, английский, французский, немецкий, китайский упрощённый, японский, итальянский, греческий, арабский, галисийский, каталанский и басконский. Арабский — с интерфейсом справа налево.

БЕСПЛАТНО, И БЕСПЛАТНО ПО-НАСТОЯЩЕМУ

Двенадцать игр, шесть тем, тринадцать языков и режим нескольких телефонов есть с первого запуска. Нет урезанной версии, нет закрытого содержимого и нечего получать отдельно.

Есть возможность угостить меня кофе, если захочется, и она не даёт совершенно ничего взамен: внутри игры не меняется ничего. Она открывает системный браузер, и приложение в этом никак не участвует.

Сделано одним человеком, в Галисии.""",
}

FICHA["ar"] = {
    "corta": "لعبة جماعية بـ 12 تحديًا. بلا إنترنت، بلا إعلانات، بلا إضافات.",
    "larga": """‏Funny لعبة جماعية تُلعب حول طاولة، مع من أمامك. بهاتف واحد يتناقله الجميع، أو بهاتف لكل شخص.

اثنا عشر تحديًا

🎭 تمثيل صامت — مثّلها دون كلام
🎨 ارسمها — على الشاشة
📅 في أي عام؟ — خمّن متى حدث ذلك
❓ أسئلة — معلومات عامة
🤐 تابو — صِفها دون قولها، ودون أربع كلمات أخرى
⚡ تحدٍ سريع — عُدّ في مواجهة الوقت
🍿 رموز تعبيرية — فُكّ شفرة الفيلم أو المسلسل
🤥 أتصدق ذلك؟ — صحيح أم خطأ، مع التفسير في النهاية
👅 عقد اللسان — قُلها دون تعثر
🔢 رتّبها — ضعها في مكانها
🎤 غنِّ — أكمل الأغنية
🤸 جرأة — تجرّأ

أكثر من 1200 بطاقة مكتوبة يدويًا، لا مُولَّدة.

ثلاثة أنماط

• بالفرق، من 2 إلى 6 فرق، ويتبادل اللاعبون دور التمثيل
• الفردي، من 2 إلى 8 لاعبين، لكل واحد قطعته
• التحدي الفردي، لشخص واحد: سلسلة من التحديات في مواجهة الوقت ورقم شخصي لتتجاوزه

أربعة أنماط للجولة

جولة سريعة (نحو 18 دقيقة)، عادية (نحو 30)، قصوى (نحو 48) أو على مقاسك، باختيار عدد المربعات وعدد التحديات. ويمكنكم إزالة الألعاب التي لا تعجبكم.

عدة هواتف في الوقت نفسه

حتى خمسة هواتف في الجولة نفسها: واحد يكون الطاولة والبقية تتصل عبر البلوتوث أو Wi-Fi Direct. بلا إنترنت، بلا راوتر وبلا إقران أي شيء.

ليست زينة. هي ما يجعل الكلمة السرية في تابو تصل إلى هاتف من يمثّل فقط، بدلًا من أن يراها نصف الطاولة خِلسة. وفي مربعات «يلعب الجميع» يجيب كل واحد على هاتفه، في الوقت نفسه.

بلا إنترنت وبلا بيانات

‏Funny لا تملك حتى صلاحية الإنترنت: لم تُعلَن، لذا يمنعها أندرويد من الاتصال ولو أرادت. بلا تحليلات، بلا إعلانات، بلا حسابات وبلا تسجيل. كل ما تحفظه يبقى في هاتفك، ويمكنك نقله إلى هاتف آخر بملف.

ستة أنماط مظهر وثلاث عشرة لغة

ثلاثة أنماط داكنة وثلاثة فاتحة، أو اتباع نمط النظام. وثلاث عشرة لغة كاملة: العربية والإسبانية والإنجليزية والفرنسية والألمانية والصينية المبسطة واليابانية والروسية والإيطالية واليونانية والغاليسية والكاتالونية والباسكية. والعربية بواجهة من اليمين إلى اليسار.

مجانية، ومجانية فعلًا

الألعاب الاثنتا عشرة وأنماط المظهر الستة واللغات الثلاث عشرة ونمط الهواتف المتعددة كلها موجودة من أول مرة تفتح فيها التطبيق. لا نسخة مختصرة، ولا محتوى محجوز، ولا شيء يُحصَّل على حدة.

هناك خيار لتدعوني إلى قهوة إن رغبت، ولا يمنح أي شيء بالمقابل: لا يغيّر شيئًا داخل اللعبة. يُفتح في متصفح النظام، والتطبيق لا يتولى أي تحصيل.

صنعه شخص واحد، في غاليسيا.""",
}

FICHA["zh-CN"] = {
    "corta": "12 个项目的聚会游戏。无需联网，没有广告，没有额外内容。",
    "larga": """Funny 是一款围着桌子玩的聚会游戏，和坐在你对面的人一起玩。可以只用一部手机轮流传递，也可以每人一部。

十二个项目

🎭 你演我猜 — 不许说话
🎨 你画我猜 — 在屏幕上画
📅 哪一年？ — 猜猜那件事发生在哪一年
❓ 常识问答
🤐 妙语说尽 — 描述它，但不能说出它，也不能用另外四个词
⚡ 快速挑战 — 计时列举
🍿 表情猜猜 — 破解电影或剧集
🤥 你信吗？ — 判断真假，最后有解释
👅 绕口令 — 说清楚，别打结
🔢 排排序 — 放到正确的位置
🎤 接着唱 — 把歌接下去
🤸 敢不敢 — 来吧

1200 多张手写卡片，不是生成的。

三种模式

• 分队，2 到 6 队，轮流由不同的人表演
• 个人，2 到 8 人，每人一个棋子
• 单人挑战，一个人玩：一连串项目和时间比赛，还有要打破的个人纪录

四种对局模式

快速对局（约 18 分钟）、普通（约 30）、极限（约 48），或者自己来定，选择格数和题数。不喜欢的游戏也可以关掉。

多部手机一起玩

同一局最多五部手机：一部当作棋桌，其他通过蓝牙或 Wi-Fi Direct 连上。不用联网，不用路由器，也不用配对。

这不是装饰。正因为这样，妙语说尽的秘密词只会送到正在表演的那个人手机上，而不会被半桌人瞄到。在"大家一起玩"的格子上，每个人在自己手机上同时作答。

不联网，不收集数据

Funny 连联网权限都没有声明，所以就算它想连，Android 也不会允许。没有统计分析，没有广告，没有账号，也不用注册。它保存的一切都留在你的手机里，可以用一个文件带到别的手机上。

六种主题，十三种语言

三种深色主题和三种浅色主题，也可以跟随系统。十三种完整语言：简体中文、西班牙语、英语、法语、德语、日语、俄语、意大利语、希腊语、阿拉伯语、加利西亚语、加泰罗尼亚语和巴斯克语。阿拉伯语界面从右到左。

免费，而且是真的免费

十二个游戏、六种主题、十三种语言和多手机模式，第一次打开就全都在。没有精简版，没有保留内容，也没有需要另外获取的东西。

如果你愿意，可以请我喝杯咖啡，而这不会带来任何回报：游戏里什么都不会改变。它会打开系统浏览器，应用本身不处理任何收款。

由一个人做成，在加利西亚。""",
}

FICHA["ja-JP"] = {
    "corta": "12種類のお題で遊ぶパーティーゲーム。ネット接続なし、広告なし。",
    "larga": """Funny は、テーブルを囲んで、目の前にいる人たちと遊ぶパーティーゲームです。1台のスマホを回して遊んでも、ひとり1台で遊んでも大丈夫。

12種類のお題

🎭 ジェスチャー — しゃべらずに演じる
🎨 お絵かき — 画面に描く
📅 何年？ — いつのことか当てる
❓ クイズ — 一般常識
🤐 タブー — その言葉を使わず、ほかの4語も使わずに説明
⚡ 早撃ちチャレンジ — 時間内に挙げる
🍿 絵文字 — 映画やドラマを解読
🤥 信じる？ — 正しいか間違いか、最後に解説つき
👅 早口言葉 — かまずに言う
🔢 並べかえ — 正しい順番に
🎤 歌って — 続きを歌う
🤸 チャレンジ — やってみる

1200枚以上のカードは、生成ではなく手書きです。

3つのモード

• チーム戦、2〜6チーム、演じる人が順番に回ります
• 個人戦、2〜8人、それぞれが自分のコマを持ちます
• ひとりチャレンジ、1人用：お題を連続で時計と勝負、破るべき自己記録つき

4つのゲームモード

クイックゲーム（約18分）、ふつう（約30分）、エクストリーム（約48分）、または自分で決める（マス数とお題数を選択）。気に入らないゲームはオフにできます。

複数のスマホで同時に

同じゲームに最大5台：1台が「テーブル」役になり、ほかはBluetoothまたはWi-Fi Directでつながります。インターネットもルーターも不要、ペアリングも不要。

飾りではありません。タブーの秘密の言葉が、演じている人のスマホだけに届くようになります。1台を回していると、テーブルの半分に見えてしまいますから。そして「全員が遊ぶ」マスでは、それぞれが自分のスマホで同時に答えます。

インターネットなし、データ収集なし

Funny はインターネット権限そのものを宣言していないので、たとえ接続しようとしてもAndroidが許しません。解析もなし、広告もなし、アカウントも登録もなし。保存されるものはすべてスマホの中に残り、ファイルで別のスマホに持っていけます。

6つのテーマと13の言語

ダーク3種、ライト3種、またはシステムに合わせる。13の言語がすべて揃っています：日本語、スペイン語、英語、フランス語、ドイツ語、簡体中国語、ロシア語、イタリア語、ギリシャ語、アラビア語、ガリシア語、カタルーニャ語、バスク語。アラビア語は右から左のインターフェイスです。

無料、それも本当に無料

12のゲーム、6つのテーマ、13の言語、複数スマホモード。アプリを最初に開いた時点で全部揃っています。制限版もなく、取り置きの内容もなく、別に手に入れるものもありません。

よければコーヒーをおごる、という選択肢がありますが、見返りは一切ありません。ゲームの中は何も変わりません。システムのブラウザが開くだけで、アプリは支払いを一切扱いません。

ガリシアで、ひとりで作りました。""",
}


def validar(codigo, textos):
    """Devuelve la lista de problemas. Vacia significa que la ficha es valida."""
    problemas = []
    if len(TITULO) > MAX_TITULO:
        problemas.append("el titulo tiene %d caracteres (max %d)" % (len(TITULO), MAX_TITULO))
    corta, larga = textos["corta"], textos["larga"]
    if len(corta) > MAX_CORTA:
        problemas.append("la corta tiene %d caracteres (max %d)" % (len(corta), MAX_CORTA))
    if len(larga) > MAX_LARGA:
        problemas.append("la larga tiene %d caracteres (max %d)" % (len(larga), MAX_LARGA))
    if not corta.strip() or not larga.strip():
        problemas.append("texto vacio")

    todo = (corta + "\n" + larga).lower()
    for palabra in PROHIBIDAS.get(codigo, []):
        if palabra.lower() in todo:
            problemas.append("vocabulario prohibido: «%s»" % palabra.strip())

    # Coherencia con la app: los doce juegos tienen que estar enumerados. Se
    # comprueban por su emoji y no por su nombre, porque el nombre cambia con el
    # idioma y el emoji no. La primera version buscaba la cifra «12» y suspendia
    # en todos los idiomas que escriben «DOCE PRUEBAS» con letras.
    faltan = [e for e in EMOJIS_DE_LOS_JUEGOS if e not in larga]
    if faltan:
        problemas.append("faltan %d juegos en la enumeracion: %s" % (len(faltan), " ".join(faltan)))
    return problemas


def main():
    fallos = 0
    print("%-8s %6s %6s  %s" % ("idioma", "corta", "larga", "estado"))
    print("-" * 58)
    for codigo in sorted(FICHA):
        textos = FICHA[codigo]
        carpeta = os.path.join(SALIDA, codigo)
        os.makedirs(carpeta, exist_ok=True)
        for nombre, contenido in (
            ("titulo.txt", TITULO),
            ("corta.txt", textos["corta"]),
            ("larga.txt", textos["larga"]),
        ):
            with io.open(os.path.join(carpeta, nombre), "w", encoding="utf-8", newline="\n") as f:
                f.write(contenido.strip() + "\n")

        problemas = validar(codigo, textos)
        estado = "correcto" if not problemas else "; ".join(problemas)
        if problemas:
            fallos += 1
        print(
            "%-8s %6d %6d  %s"
            % (codigo, len(textos["corta"]), len(textos["larga"]), estado)
        )

    print("-" * 58)
    print("%d idiomas escritos en ficha/" % len(FICHA))
    if len(FICHA) != 13:
        print("OJO: son %d y tendrian que ser 13" % len(FICHA))
        fallos += 1
    return 1 if fallos else 0


if __name__ == "__main__":
    sys.exit(main())
