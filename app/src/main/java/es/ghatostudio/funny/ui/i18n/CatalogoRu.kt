package es.ghatostudio.funny.ui.i18n

import es.ghatostudio.funny.dominio.textos.Clave
import es.ghatostudio.funny.dominio.textos.Clave.*

/**
 * Catálogo en ruso.
 *
 * Los plurales llevan las tres formas que necesita el ruso —ONE, FEW y MANY—, que
 * es exactamente la razón por la que los plurales de Funny pasan por las reglas
 * de la ICU del sistema en lugar de por un simple «uno o varios».
 *
 * Nota sobre la donación: se evita «купить» (comprar) y «заплатить» (pagar). Se
 * usa «угости меня кофе», que dice lo mismo sin enmarcarlo como una compra. Lo
 * comprueba `PruebaCatalogos`.
 */
internal val catalogoRuso =
    Catalogo(
        idioma = Idioma.RUSO,
        textos =
            mapOf<Clave, String>(
                ACCION_VOLVER to "Назад",
                ACCION_CERRAR to "Закрыть",
                ACCION_CANCELAR to "Отмена",
                ACCION_ACEPTAR to "ОК",
                ACCION_CONTINUAR to "Продолжить",
                ACCION_EMPEZAR to "Начали!",
                ACCION_LISTO to "Готово",
                ACCION_BORRAR to "Удалить",
                ACCION_ANADIR to "Добавить",
                ACCION_REINTENTAR to "Попробовать снова",
                ACCION_COPIAR to "Скопировать ссылку",
                ACCION_COMPARTIR to "Поделиться",
                ACCION_AHORA_NO to "Не сейчас",
                ACCION_SI to "Да",
                ACCION_NO to "Нет",
                ESTADO_CARGANDO to "Загрузка…",
                ESTADO_SIN_CONTENIDO to "Для этой игры нет содержимого.",
                APP_LEMA to "Игра для вечеринки, которая уместится в ваших телефонах",
                MENU_JUGAR to "ИГРАТЬ",
                MENU_SEGUIR_PARTIDA to "ПРОДОЛЖИТЬ ПАРТИЮ",
                MENU_PARTIDA_NUEVA to "НОВАЯ ПАРТИЯ",
                MENU_COMO_JUGAR to "КАК ИГРАТЬ",
                MENU_AJUSTES to "НАСТРОЙКИ",
                MENU_SALON to "ИГРАТЬ НА НЕСКОЛЬКИХ ТЕЛЕФОНАХ",
                MENU_TOUR to "ПОСМОТРЕТЬ ОБЗОР",
                MODO_TITULO to "Как играем?",
                MODO_SUBTITULO to "Можно изменить в любой новой партии.",
                MODO_EQUIPOS to "Командами",
                MODO_EQUIPOS_DETALLE to
                    "От 2 до 6 команд. У каждой команды своя фишка, и тот, кто показывает, " +
                    "меняется по кругу. Классический вариант и самый шумный.",
                MODO_INDIVIDUAL to "По одному",
                MODO_INDIVIDUAL_DETALLE to
                    "От 2 до 8 человек, у каждого своя фишка и без команд. В заданиях на " +
                    "показ показывает тот, чей ход, а остальные угадывают.",
                MODO_SOLITARIO to "Одиночный вызов",
                MODO_SOLITARIO_DETALLE to
                    "Ты против часов: десять заданий подряд и личный рекорд, который надо " +
                    "побить. Без доски и только с играми, в которые можно играть без " +
                    "публики.",
                PARTICIPANTES_TITULO_EQUIPOS to "Команды",
                PARTICIPANTES_TITULO_INDIVIDUAL to "Игроки",
                PARTICIPANTES_TITULO_SOLITARIO to "Как тебя зовут?",
                PARTICIPANTES_SUBTITULO_EQUIPOS to
                    "От %1\$d до %2\$d команд. Запиши, кто играет в каждой, и " +
                    "телефон будет говорить, чья очередь показывать.",
                PARTICIPANTES_SUBTITULO_INDIVIDUAL to "От %1\$d до %2\$d человек, у каждого своя фишка.",
                PARTICIPANTES_SUBTITULO_SOLITARIO to
                    "Только чтобы имя стояло в рекорде. Ничего из этого не " +
                    "покидает телефон.",
                PARTICIPANTES_ANADIR_EQUIPO to "+  ДОБАВИТЬ КОМАНДУ",
                PARTICIPANTES_ANADIR_JUGADOR to "+  ДОБАВИТЬ ИГРОКА",
                PARTICIPANTES_NUEVO_JUGADOR to "Добавить игрока…",
                PARTICIPANTES_SIN_JUGADORES to "Имён нет: игра будет называть только название команды.",
                PARTICIPANTES_NOMBRE_EQUIPO to "Название команды",
                PARTICIPANTES_NOMBRE_JUGADOR to "Имя",
                PARTICIPANTES_QUITAR to "Убрать",
                PARTICIPANTES_EQUIPO_POR_DEFECTO to "Команда %1\$d",
                PARTICIPANTES_JUGADOR_POR_DEFECTO to "Игрок %1\$d",
                PARTICIPANTES_EQUIPO_1_POR_DEFECTO to "Мастера",
                PARTICIPANTES_EQUIPO_2_POR_DEFECTO to "Звери",
                PARTICIPANTES_DESDE_SALON to "Присоединился с другого телефона",
                TABLERO_TIRAR to "БРОСИТЬ КУБИК",
                TABLERO_TURNO_DE to "ХОД %1\$s",
                TABLERO_LE_TOCA to "Ход %1\$s",
                TABLERO_CASILLA to "Клетка %1\$d",
                TABLERO_SALIDA to "СТАРТ",
                TABLERO_META to "ФИНИШ",
                TABLERO_MARCADOR to "Счёт",
                TABLERO_ABANDONAR to "Выйти из партии",
                TABLERO_ABANDONAR_PREGUNTA to "Прогресс этой партии будет потерян. Точно выйти?",
                TABLERO_ESPERANDO_HUB to "Ожидание телефона, который ведёт партию…",
                TABLERO_AVANZA_CASILLAS to "Продвигается на %1\$d",
                CASILLA_COMODIN to "ДЖОКЕР",
                CASILLA_COMODIN_DETALLE to "Соперник выбирает задание для тебя. Без пощады.",
                CASILLA_TODOS to "ИГРАЮТ ВСЕ",
                CASILLA_TODOS_DETALLE to
                    "Одно задание на весь стол. Каждый, кто угадает, продвигается на одну " +
                    "клетку.",
                CASILLA_META_AVISO to "Финальное задание: победа только если его пройти.",
                COMODIN_TITULO to "Клетка-джокер",
                COMODIN_ELIGE to "%1\$s выбирает задание",
                PRUEBA_FINAL to "🏁  ФИНАЛЬНОЕ ЗАДАНИЕ",
                PRUEBA_JUEGAN_TODOS to "👥  ИГРАЮТ ВСЕ",
                PRUEBA_LE_TOCA_ACTUAR_A to "ПОКАЗЫВАЕТ",
                PRUEBA_QUIEN_DECIDA to "тот, кого выберет %1\$s",
                PRUEBA_SOLO_MIRE_ESA_PERSONA to "👀  На экран смотрит только этот человек",
                PRUEBA_MIRA_TU_MOVIL to "📱  Смотри в свой телефон: слово пришло туда",
                PRUEBA_SEGUNDOS to "⏱  %1\$d с",
                PRUEBA_CUANDO_TEMA to "В каком году?",
                PRUEBA_CUANDO_RESPUESTA to "Это было в %1\$d году.",
                PRUEBA_ACIERTOS_DE to "%1\$d из %2\$d",
                PRUEBA_SALTAR to "ПРОПУСТИТЬ",
                PRUEBA_ACERTADA to "✓  УГАДАЛИ",
                PRUEBA_FALLADA to "✗  МИМО",
                PRUEBA_PROHIBIDA to "🚫  ЗАПРЕЩЁННОЕ",
                PRUEBA_TERMINAR to "Закончить задание",
                PRUEBA_ACIERTOS to "✔  %1\$d",
                PRUEBA_VEREDICTO_TITULO to "Получилось?",
                PRUEBA_VEREDICTO_LOGRADO to "✓  ДА!",
                PRUEBA_VEREDICTO_NO_LOGRADO to "✗  НЕТ",
                PRUEBA_VEREDICTO_DECIDE_MESA to "Решает стол, а не приложение.",
                PRUEBA_VEREDICTO_DECIDE_SOLO to "Здесь никто не смотрит: будь честен с собой.",
                PRUEBA_BORRAR_DIBUJO to "Стереть всё",
                PRUEBA_DIBUJO_SOLO_MIRA_QUIEN_DIBUJA to "СМОТРИТ ТОЛЬКО ТОТ, КТО РИСУЕТ",
                PRUEBA_DIBUJO_TIEMPO_AL_PULSAR to "Время начнётся, когда нажмёшь кнопку.",
                PRUEBA_DIBUJO_EMPEZAR to "🎨   НАЧАТЬ РИСОВАТЬ",
                PRUEBA_DIBUJO_LIENZO_VACIO to "Рисуй здесь пальцем",
                PRUEBA_DIBUJO_ESPIAR to "Удерживай, чтобы снова увидеть слово",
                PRUEBA_DIBUJO_GOMA to "Ластик",
                PRUEBA_DESHACER to "Отменить",
                PRUEBA_PINCEL to "Толщина",
                PRUEBA_COLOR to "Цвет",
                PRUEBA_RETO_OBJETIVO to "Нужно дойти до %1\$d",
                PRUEBA_RETO_LLEVAMOS to "Пока",
                PRUEBA_RETO_UNA_MAS to "+1  ЕЩЁ!",
                PRUEBA_RETO_CONSEGUIDO to "ПОЛУЧИЛОСЬ!",
                PRUEBA_RETO_TOCA_PARA_SUMAR to "из %1\$d  ·  нажми, чтобы добавить",
                PRUEBA_RETO_RENDIRSE to "МЫ СДАЁМСЯ",
                PRUEBA_RETO_NOTA to "Каждый ответ считается один раз. Повторы не считаются.",
                PRUEBA_ORDENA_AYUDA to "Нажимай их в правильном порядке, от первого к последнему.",
                PRUEBA_ORDENA_COMPROBAR to "ПРОВЕРИТЬ",
                PRUEBA_ORDENA_CORRECTO to "Правильный порядок был такой:",
                PRUEBA_VF_VERDADERO to "ПРАВДА",
                PRUEBA_VF_FALSO to "ЛОЖЬ",
                PRUEBA_VF_ERA_VERDAD to "Это была правда",
                PRUEBA_VF_ERA_MENTIRA to "Это была ложь",
                PRUEBA_EMOJIS_AYUDA to "Что это, написанное эмодзи?",
                PRUEBA_EMOJIS_ERA to "Это было: %1\$s",
                PRUEBA_CANTA_PISTA to "Начни отсюда",
                PRUEBA_TRABALENGUAS_AYUDA to "Скажи целиком и не запнувшись.",
                PRUEBA_DESAFIO_AYUDA to "Посмотрим, как ты выкрутишься.",
                RONDA_TODOS_PASA_MOVIL to "Передай телефон, не подглядывая в чужой ответ.",
                RONDA_TODOS_RESPONDE to "Отвечает %1\$s",
                RONDA_TODOS_RESUMEN to "Кто угадал",
                RONDA_TODOS_NADIE to "Никто. Ни одна фишка не двигается.",
                RONDA_TODOS_EN_TU_MOVIL to "Отвечай на своём телефоне.",
                RONDA_TODOS_ESPERANDO to "Ожидание остальных…",
                RONDA_TODOS_PASAD_A to "ПЕРЕДАЙТЕ ТЕЛЕФОН",
                RONDA_TODOS_PROGRESO to "%1\$d из %2\$d. До конца никто не узнает, кто угадал.",
                RONDA_TODOS_SIN_RESPUESTA to "Время вышло, без ответа.",
                RONDA_TODOS_GUARDADA to "Ответ сохранён. Пока не говори его.",
                RONDA_TODOS_CORRECTA_ERA to "ПРАВИЛЬНЫЙ ОТВЕТ БЫЛ",
                RONDA_TODOS_SIN_RESPONDER to "без ответа",
                RONDA_TODOS_VER_RESULTADOS to "СМОТРЕТЬ РЕЗУЛЬТАТЫ",
                RESULTADO_SUPERADA to "ПРОЙДЕНО!",
                RESULTADO_NO_HA_PODIDO_SER to "НЕ В ЭТОТ РАЗ",
                RESULTADO_FINAL_SUPERADA to "ФИНАЛЬНОЕ ЗАДАНИЕ ПРОЙДЕНО!",
                RESULTADO_AVANZAS_A to "Вы продвигаетесь на клетку %1\$d",
                RESULTADO_TE_QUEDAS_EN to "Вы остаётесь на клетке %1\$d",
                RESULTADO_LLEGADA_A_META to "Вы добрались до финиша!",
                RESULTADO_SIGUIENTE_TURNO to "СЛЕДУЮЩИЙ ХОД",
                RESULTADO_VER_RESULTADO to "СМОТРЕТЬ РЕЗУЛЬТАТ",
                VICTORIA_TITULO to "Партия закончена!",
                VICTORIA_GANADOR to "Побеждает %1\$s",
                VICTORIA_CLASIFICACION to "Итоговая таблица",
                VICTORIA_OTRA_PARTIDA to "ЕЩЁ ПАРТИЮ",
                VICTORIA_AL_MENU to "В МЕНЮ",
                VICTORIA_SOLITARIO_TITULO to "Вызов завершён",
                VICTORIA_SOLITARIO_PUNTOS to "%1\$d очков",
                VICTORIA_SOLITARIO_MEJOR to "Твой рекорд: %1\$d",
                VICTORIA_SOLITARIO_RECORD to "Новый личный рекорд!",
                SOLITARIO_TITULO to "Одиночный вызов",
                SOLITARIO_SUBTITULO to "Десять заданий. Ты против часов.",
                SOLITARIO_PROGRESO to "Задание %1\$d из %2\$d",
                SOLITARIO_RONDAS to "Задания",
                SOLITARIO_EMPEZAR to "НАЧАТЬ ВЫЗОВ",
                SOLITARIO_MEJOR_MARCA to "Рекорд: %1\$d",
                SOLITARIO_SIN_MARCA to "Рекорда пока нет. Поставь первый.",
                AJUSTES_TITULO to "Настройки",
                AJUSTES_SUBTITULO to "Сохраняются до следующей партии.",
                AJUSTES_APARIENCIA to "Внешний вид",
                AJUSTES_TEMA to "Тема",
                AJUSTES_TEMA_DETALLE to "Шесть тем: три светлые и три тёмные.",
                AJUSTES_TEMA_SISTEMA to "Как в системе",
                AJUSTES_IDIOMA to "Язык",
                AJUSTES_IDIOMA_DETALLE to "Тринадцать языков.",
                AJUSTES_PARTIDA to "Партия",
                AJUSTES_RITMO to "Темп заданий",
                AJUSTES_RITMO_DETALLE to "Сколько времени даётся на каждое задание.",
                AJUSTES_DURACION to "Длина партии",
                AJUSTES_DURACION_DETALLE to "%1\$d клеток до финиша · %2\$s",
                AJUSTES_JUEGOS_ACTIVOS to "Игры партии",
                AJUSTES_JUEGOS_ACTIVOS_DETALLE to
                    "Убери те, которые вам не нравятся, и они перестанут " +
                    "появляться на доске.",
                AJUSTES_JUEGOS_CONTADOR to "%1\$d из %2\$d включено",
                AJUSTES_JUEGOS_MINIMO to "Хотя бы одна игра должна остаться включённой.",
                AJUSTES_SONIDO to "Звук",
                AJUSTES_SONIDO_DETALLE to "Сигналы обратного отсчёта и оповещения",
                AJUSTES_VIBRACION to "Вибрация",
                AJUSTES_VIBRACION_DETALLE to "Попадания, промахи и конец времени",
                AJUSTES_ANIMACIONES to "Анимации",
                AJUSTES_ANIMACIONES_DETALLE to "Выключи, если предпочитаешь неподвижный интерфейс",
                AJUSTES_DATOS to "Твои данные",
                AJUSTES_EXPORTAR to "Экспорт",
                AJUSTES_EXPORTAR_DETALLE to "Сохраняет команды, настройки и рекорды в файл",
                AJUSTES_IMPORTAR to "Импорт",
                AJUSTES_IMPORTAR_DETALLE to "Восстанавливает сохранённую ранее копию",
                AJUSTES_MAS to "Ещё",
                AJUSTES_APOYAR to "Поддержать разработку",
                AJUSTES_APOYAR_DETALLE to "Угости меня кофе, если пригодилось",
                AJUSTES_COMPARTIR to "Поделиться Funny",
                AJUSTES_COMPARTIR_DETALLE to "Передай тому, кому это понравится",
                AJUSTES_AYUDA to "Справка",
                AJUSTES_AYUDA_DETALLE to "Как играть и частые вопросы",
                AJUSTES_TOUR to "Обзор",
                AJUSTES_TOUR_DETALLE to "Все двенадцать игр и три режима, с объяснениями",
                AJUSTES_ACERCA_DE to "О приложении",
                AJUSTES_ACERCA_DE_DETALLE to "Версия, лицензии и конфиденциальность",
                TEMA_MODO_CLARO to "Светлые",
                TEMA_MODO_OSCURO to "Тёмные",
                TEMA_FIESTA to "Вечеринка",
                TEMA_NEON to "Неон",
                TEMA_MEDIANOCHE to "Полночь",
                TEMA_PAPEL to "Бумага",
                TEMA_MENTA to "Мята",
                TEMA_ATARDECER to "Закат",
                IDIOMA_TITULO to "Язык",
                IDIOMA_SEGUIR_SISTEMA to "Как в телефоне",
                IDIOMA_SUBTITULO to "Изменение применяется сразу.",
                CAFE_TITULO to "Кофе?",
                CAFE_TEXTO to
                    "Это приложение бесплатное, без рекламы и не собирает твои данные. Если оно " +
                    "пригодилось, можешь угостить меня кофе.",
                CAFE_BOTON to "Угости меня кофе · 1 €",
                CAFE_NO_VOLVER to "Больше не показывать",
                CAFE_OTRO_DISPOSITIVO to "С другого устройства",
                CAFE_QR_DESCRIPCION to "QR-код со ссылкой, чтобы угостить кофе автора приложения",
                CAFE_ILUSTRACION_DESCRIPCION to "Рисунок чашки кофе с паром",
                CAFE_ENLACE_COPIADO to "Ссылка скопирована",
                CAFE_GRACIAS to "Спасибо, что зашёл 🙂",
                CAFE_SIN_DESBLOQUEOS to "В игре от этого ничего не меняется: Funny целая и такой останется.",
                CAFE_ENTRADA_AJUSTES to "Поддержать разработку",
                CAFE_NO_DISPONIBLE to "На этом устройстве недоступно.",
                COPIA_TITULO to "Копия твоих данных",
                COPIA_EXPORTAR_HECHO to "Копия сохранена.",
                COPIA_EXPORTAR_ERROR to "Не удалось сохранить копию.",
                COPIA_IMPORTAR_TITULO to "Импорт копии",
                COPIA_IMPORTAR_AVISO to
                    "Прежде чем что-то менять, сохраняется копия того, что есть сейчас, так " +
                    "что всегда можно вернуться.",
                COPIA_IMPORTAR_FUSIONAR to "Добавить к тому, что есть",
                COPIA_IMPORTAR_REEMPLAZAR to "Заменить всё",
                COPIA_IMPORTAR_HECHO to "Данные импортированы.",
                COPIA_IMPORTAR_ERROR_FORMATO to "Этот файл не похож на копию Funny. Ничего не изменено.",
                COPIA_IMPORTAR_ERROR_VERSION to
                    "Эта копия из более новой версии Funny. Обнови приложение и " +
                    "попробуй снова.",
                COPIA_IMPORTAR_RESPALDO to "Сначала была сохранена резервная копия.",
                COPIA_CABECERA_DETALLE to "Копия от %1\$s · Funny %2\$s",
                AYUDA_TITULO to "Справка",
                AYUDA_SUBTITULO to "Всё, что нужно, чтобы никто не запутался.",
                AYUDA_QUE_ES_TITULO to "Что такое Funny?",
                AYUDA_QUE_ES_TEXTO to
                    "Игра для вечеринки с двенадцатью разными заданиями. Играют одним " +
                    "телефоном, который передают по кругу, или, если у нескольких есть " +
                    "телефон, соединяя их между собой. Интернет не нужен ни на минуту.",
                AYUDA_COMO_SE_JUEGA_TITULO to "Партия",
                AYUDA_PARTIDA_1 to "Выберите режим: командами, по одному или одиночный вызов.",
                AYUDA_PARTIDA_2 to "У каждой команды или игрока есть фишка, и все начинают со СТАРТА.",
                AYUDA_PARTIDA_3 to "В свой ход бросаешь кубик и продвигаешься на 1–3 клетки.",
                AYUDA_PARTIDA_4 to
                    "Клетка, на которую попадаешь, определяет задание. Пройдёшь — остаёшься, нет " +
                    "— возвращаешься.",
                AYUDA_PARTIDA_5 to "Побеждает тот, кто дойдёт до ФИНИША и пройдёт финальное задание.",
                AYUDA_UN_MOVIL_TITULO to "С одним телефоном",
                AYUDA_UN_MOVIL_1 to "Телефон идёт по кругу: приложение всегда говорит, чья очередь.",
                AYUDA_UN_MOVIL_2 to
                    "В пантомиме, табу, рисунке, пении и вызовах смотрит только тот, кто " +
                    "показывает.",
                AYUDA_UN_MOVIL_3 to "В заданиях с ответами экран можно показать всем.",
                AYUDA_VARIOS_MOVILES_TITULO to "С несколькими телефонами",
                AYUDA_VARIOS_MOVILES_1 to
                    "Один телефон становится столом (хабом), остальные подключаются к " +
                    "нему. Ни Wi-Fi, ни мобильных данных не нужно.",
                AYUDA_VARIOS_MOVILES_2 to
                    "Секретное слово приходит только на телефон того, кто показывает, так " +
                    "что никто не увидит его случайно.",
                AYUDA_VARIOS_MOVILES_3 to "На клетках «играют все» каждый отвечает на своём телефоне одновременно.",
                AYUDA_FAQ_TITULO to "Частые вопросы",
                AYUDA_FAQ_1_P to "Нужен ли интернет?",
                AYUDA_FAQ_1_R to
                    "Нет. Funny полностью работает без сети, а соединение нескольких телефонов " +
                    "идёт через Bluetooth и Wi-Fi Direct между ними, не проходя ни через какую " +
                    "сеть.",
                AYUDA_FAQ_2_P to "Можно играть одному?",
                AYUDA_FAQ_2_R to
                    "Да: одиночный вызов — это десять заданий подряд с личным рекордом. Участвуют " +
                    "только игры, которым не нужна публика.",
                AYUDA_FAQ_3_P to "Это что-то стоит? Есть закрытые части?",
                AYUDA_FAQ_3_R to
                    "Ничего не закрыто и ничего дополнительного получить нельзя. Если понравилось, " +
                    "можешь угостить меня кофе из Настроек, и это совсем ничего не меняет в игре.",
                AYUDA_FAQ_4_P to "Приложение собирает данные?",
                AYUDA_FAQ_4_R to
                    "Нет. Никакой аналитики, никаких аккаунтов, никакой рекламы. Команды и " +
                    "настройки хранятся только на твоём телефоне и покидают его только если ты сам " +
                    "экспортируешь копию.",
                AYUDA_FAQ_5_P to "Можно изменить, какие игры выпадают?",
                AYUDA_FAQ_5_R to "Да, в Настройки → Игры партии. Убранные перестают появляться на доске.",
                AYUDA_PROBLEMAS_TITULO to "Если что-то не работает",
                AYUDA_PROBLEMAS_TEXTO to
                    "Закрой и снова открой приложение: текущая партия сохраняется. Если " +
                    "проблема остаётся, экспортируй данные перед переустановкой и напиши " +
                    "нам, что происходило.",
                AYUDA_ESCRIBENOS to "Написать автору",
                ACERCA_TITULO to "О приложении",
                ACERCA_VERSION to "Версия",
                ACERCA_COMPILACION to "Сборка",
                ACERCA_FECHA to "Дата",
                ACERCA_COMMIT to "Коммит",
                ACERCA_LICENCIA to "Лицензия",
                ACERCA_LICENCIAS_TERCEROS to "Лицензии третьих лиц",
                ACERCA_PRIVACIDAD to "Политика конфиденциальности",
                ACERCA_CONTACTO to "Контакт",
                ACERCA_CODIGO to "Исходный код",
                ACERCA_SIN_ANUNCIOS to "Без рекламы, без аналитики и без аккаунтов.",
                ACERCA_AUTOR to "Ghato Studio · Brais Galdo",
                TOUR_TITULO to "Обзор",
                TOUR_SUBTITULO to "Двенадцать игр и три режима за две минуты.",
                TOUR_EMPEZAR to "НАЧАТЬ ОБЗОР",
                TOUR_SALTAR to "Пропустить",
                TOUR_ANTERIOR to "Назад",
                TOUR_SIGUIENTE to "Далее",
                TOUR_TERMINAR to "ИГРАЕМ!",
                TOUR_PROGRESO to "%1\$d из %2\$d",
                TOUR_BIENVENIDA_TITULO to "Добро пожаловать в Funny",
                TOUR_BIENVENIDA_TEXTO to
                    "Двенадцать игр, три способа играть и никакого интернета. За пару " +
                    "минут расскажу всё; пропустить можно в любой момент.",
                TOUR_MODOS_TITULO to "Три способа играть",
                TOUR_MODOS_TEXTO to
                    "Командами — классика: от 2 до 6 команд, и тот, кто показывает, меняется по " +
                    "кругу. По одному — то же самое, но у каждого своя фишка, от 2 до 8. А " +
                    "одиночный вызов — это десять заданий против часов, в одиночку, с личным " +
                    "рекордом.",
                TOUR_TABLERO_TITULO to "Доска",
                TOUR_TABLERO_TEXTO to
                    "Каждая фишка начинает со СТАРТА. В свой ход бросаешь кубик, " +
                    "продвигаешься на 1–3 клетки, и клетка определяет задание. Пройдёшь — " +
                    "остаёшься; нет — возвращаешься туда, где был. Побеждает тот, кто дойдёт " +
                    "до ФИНИША и пройдёт финальное задание.",
                TOUR_CASILLAS_TITULO to "Особые клетки",
                TOUR_CASILLAS_TEXTO to
                    "🃏 Джокер: соперник выбирает задание для тебя.\n👥 Играют все: одно " +
                    "задание на весь стол, и каждый, кто угадает, продвигается на клетку.\n🏁 " +
                    "Финиш: случайное финальное задание. Без него не победить.",
                TOUR_JUEGOS_TITULO to "Двенадцать игр",
                TOUR_JUEGOS_TEXTO to "Вот они все. Те, которые вам не нравятся, можно отключить в Настройках.",
                TOUR_SALON_TITULO to "Несколько телефонов сразу",
                TOUR_SALON_TEXTO to
                    "Один телефон становится столом, остальные подключаются к нему по Bluetooth " +
                    "или Wi-Fi Direct, без интернета. Это даёт главное: секретное слово " +
                    "приходит только на телефон того, кто показывает, а на клетках «играют все» " +
                    "каждый отвечает на своём одновременно.",
                TOUR_AJUSTES_TITULO to "Настрой под себя",
                TOUR_AJUSTES_TEXTO to
                    "Шесть тем, тринадцать языков, три темпа и три длины. Ещё можно отключать " +
                    "игры, выключать звук и вибрацию, сохранять и восстанавливать данные из " +
                    "файла.",
                TOUR_FINAL_TITULO to "Вот и всё",
                TOUR_FINAL_TEXTO to
                    "Посмотреть это снова можно в любой момент через Настройки → Обзор. Хорошей " +
                    "игры.",
                SALON_TITULO to "Несколько телефонов",
                SALON_SUBTITULO to "Без интернета: они соединяются между собой.",
                SALON_CREAR to "БЫТЬ СТОЛОМ",
                SALON_CREAR_DETALLE to "Этот телефон ведёт партию и показывает доску. Его и оставляют на столе.",
                SALON_UNIRSE to "ПРИСОЕДИНИТЬСЯ К СТОЛУ",
                SALON_UNIRSE_DETALLE to "Этот телефон остаётся в твоей руке и получает твои задания приватно.",
                SALON_TU_NOMBRE to "Твоё имя",
                SALON_HUB_TITULO to "Ты — стол",
                SALON_HUB_ESPERANDO to "Ожидание подключений…",
                SALON_HUB_CONECTADOS to "Подключено",
                SALON_HUB_EMPEZAR to "НАЧАТЬ ПАРТИЮ",
                SALON_CLIENTE_TITULO to "Поиск стола",
                SALON_CLIENTE_BUSCANDO to "Поиск столов рядом…",
                SALON_CLIENTE_SIN_SALONES to
                    "Пока ничего не видно. Пусть другой телефон откроет «Быть столом», " +
                    "и подождите несколько секунд.",
                SALON_CLIENTE_CONECTANDO to "Подключение…",
                SALON_CLIENTE_CONECTADO to "Подключено",
                SALON_CLIENTE_ESPERA to "Ты внутри. Смотри на стол: партия начинается там.",
                SALON_SALIR to "Выйти из комнаты",
                SALON_DESCONECTADO to "Связь со столом потеряна.",
                SALON_ERROR_PERMISOS to "Не хватает разрешений, чтобы найти телефоны рядом.",
                SALON_PEDIR_PERMISOS to "ДАТЬ РАЗРЕШЕНИЯ",
                SALON_PERMISOS_EXPLICACION to
                    "Чтобы найти телефоны рядом, Android просит разрешение на " +
                    "устройства поблизости и, на старых версиях, ещё и геолокацию. " +
                    "Funny никогда не запрашивает, где ты находишься, и нигде это не " +
                    "хранит: это цена, которую система берёт за Bluetooth и Wi-Fi " +
                    "Direct.",
                SALON_ERROR_BLUETOOTH to "Включи Bluetooth, чтобы телефоны могли соединиться.",
                SALON_ERROR_UBICACION to "Включи геолокацию: Android требует её для поиска по Bluetooth.",
                SALON_ERROR_SERVICIOS to
                    "На этом телефоне нет сервисов Google, нужных для подключения. Можете " +
                    "продолжать играть, передавая один телефон.",
                SALON_COMO_FUNCIONA to "Как это работает?",
                SALON_ESTE_DISPOSITIVO to "Этот телефон",
                SALON_ROL_HUB to "Стол",
                SALON_ROL_MANDO to "Пульт",
                SALON_SIN_RED to "Интернет не используется ни в какой момент.",
                SALON_SIN_NOMBRE to "Без имени",
                SALON_TU_TURNO to "Твой ход!",
                SALON_MIRA_EL_HUB to "Смотри на телефон на столе.",
                JUEGO_MIMICA_NOMBRE to "Пантомима",
                JUEGO_MIMICA_LEMA to "Покажи без слов",
                JUEGO_MIMICA_INSTRUCCIONES to
                    "Один человек показывает слово жестами. Нельзя говорить, издавать " +
                    "звуки и указывать на предметы в комнате.",
                JUEGO_DIBUJO_NOMBRE to "Рисунок",
                JUEGO_DIBUJO_LEMA to "Нарисуй на экране",
                JUEGO_DIBUJO_INSTRUCCIONES to
                    "Один человек рисует на экране телефона, остальные угадывают. " +
                    "Никаких букв, цифр и жестов.",
                JUEGO_CUANDO_NOMBRE to "Когда?",
                JUEGO_CUANDO_LEMA to "В каком году это было?",
                JUEGO_CUANDO_INSTRUCCIONES to
                    "Появляется событие и четыре возможных года. Нужно решить, в " +
                    "каком оно произошло.",
                JUEGO_PREGUNTAS_NOMBRE to "Вопросы",
                JUEGO_PREGUNTAS_LEMA to "Общие знания",
                JUEGO_PREGUNTAS_INSTRUCCIONES to
                    "Один вопрос с четырьмя ответами. Выбирается один, и поменять " +
                    "нельзя.",
                JUEGO_TABU_NOMBRE to "Табу",
                JUEGO_TABU_LEMA to "Опиши, не называя",
                JUEGO_TABU_INSTRUCCIONES to
                    "Нужно описать слово, не используя ни одно из запрещённых и ни " +
                    "однокоренных с ними.",
                JUEGO_RETO_NOMBRE to "Блиц",
                JUEGO_RETO_LEMA to "Перечисляй на время",
                JUEGO_RETO_INSTRUCCIONES to
                    "Продолжайте называть вещи из указанной категории, пока не дойдёте " +
                    "до цели, прежде чем кончится время.",
                JUEGO_EMOJIS_NOMBRE to "Эмодзи",
                JUEGO_EMOJIS_LEMA to "Расшифруй",
                JUEGO_EMOJIS_INSTRUCCIONES to
                    "Фильм, песня или пословица, написанная только эмодзи, и четыре " +
                    "возможных ответа.",
                JUEGO_VERDADERO_FALSO_NOMBRE to "Верите?",
                JUEGO_VERDADERO_FALSO_LEMA to "Правда или ложь",
                JUEGO_VERDADERO_FALSO_INSTRUCCIONES to
                    "Четыре странных утверждения подряд. Про каждое надо " +
                    "сказать, правда это или ложь, а потом объясняется " +
                    "почему.",
                JUEGO_TRABALENGUAS_NOMBRE to "Скороговорка",
                JUEGO_TRABALENGUAS_LEMA to "Скажи не запнувшись",
                JUEGO_TRABALENGUAS_INSTRUCCIONES to
                    "Скороговорку надо произнести столько раз, сколько просит, " +
                    "целиком и без ошибок. Судит стол.",
                JUEGO_ORDENA_NOMBRE to "По порядку",
                JUEGO_ORDENA_LEMA to "Поставь на место",
                JUEGO_ORDENA_INSTRUCCIONES to
                    "Четыре вещи в беспорядке и один критерий. Надо нажать их в " +
                    "правильном порядке.",
                JUEGO_CANTA_NOMBRE to "Пой",
                JUEGO_CANTA_LEMA to "Продолжай песню",
                JUEGO_CANTA_INSTRUCCIONES to
                    "Появляются название и исполнитель, и надо запеть припев. Судит " +
                    "стол, с той щедростью, какую сочтёт нужной.",
                JUEGO_DESAFIO_NOMBRE to "Вызов",
                JUEGO_DESAFIO_LEMA to "Рискни",
                JUEGO_DESAFIO_INSTRUCCIONES to
                    "Небольшой вызов на глазах у всех. Ничего опасного, ничего " +
                    "унизительного: только хорошая доля глупости. Судит стол.",
                RITMO_RAPIDO to "Быстрый",
                RITMO_NORMAL to "Обычный",
                RITMO_TRANQUILO to "Спокойный",
                DURACION_CORTA to "Короткая",
                DURACION_NORMAL to "Обычная",
                DURACION_LARGA to "Долгая",
                DURACION_CORTA_DETALLE to "около 15 мин",
                DURACION_NORMAL_DETALLE to "около 30 мин",
                DURACION_LARGA_DETALLE to "около 45 мин",
                A11Y_DADO to "Кубик: %1\$d",
                A11Y_FICHA to "Фишка %1\$s на клетке %2\$d",
                A11Y_CASILLA to "Клетка %1\$d, %2\$s",
                A11Y_VOLVER to "Назад на предыдущий экран",
                A11Y_CERRAR to "Закрыть",
                A11Y_LIENZO_DIBUJO to "Полотно для рисования пальцем",
                A11Y_TEMA_MUESTRA to "Образец цветов темы %1\$s",
                A11Y_BANDERA_IDIOMA to "Язык %1\$s",
            ),
        plurales =
            mapOf(
                ClavePlural.CASILLAS to
                    mapOf(
                        CategoriaPlural.ONE to "%d клетка",
                        CategoriaPlural.FEW to "%d клетки",
                        CategoriaPlural.MANY to "%d клеток",
                        CategoriaPlural.OTHER to "%d клетки",
                    ),
                ClavePlural.SEGUNDOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d секунда",
                        CategoriaPlural.FEW to "%d секунды",
                        CategoriaPlural.MANY to "%d секунд",
                        CategoriaPlural.OTHER to "%d секунды",
                    ),
                ClavePlural.DISPOSITIVOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d телефон подключён",
                        CategoriaPlural.FEW to "%d телефона подключено",
                        CategoriaPlural.MANY to "%d телефонов подключено",
                        CategoriaPlural.OTHER to "%d телефона подключено",
                    ),
                ClavePlural.PUNTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d очко",
                        CategoriaPlural.FEW to "%d очка",
                        CategoriaPlural.MANY to "%d очков",
                        CategoriaPlural.OTHER to "%d очка",
                    ),
                ClavePlural.ACIERTOS to
                    mapOf(
                        CategoriaPlural.ONE to "%d попадание",
                        CategoriaPlural.FEW to "%d попадания",
                        CategoriaPlural.MANY to "%d попаданий",
                        CategoriaPlural.OTHER to "%d попадания",
                    ),
                ClavePlural.REPETICIONES to
                    mapOf(
                        CategoriaPlural.ONE to "%d раз",
                        CategoriaPlural.FEW to "%d раза",
                        CategoriaPlural.MANY to "%d раз",
                        CategoriaPlural.OTHER to "%d раза",
                    ),
            ),
    )
