package es.ghatostudio.funny

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import es.ghatostudio.funny.ui.AppFunny

/**
 * La única actividad de Funny.
 *
 * Hereda de `AppCompatActivity` y no de `ComponentActivity` para poder usar la
 * preferencia de idioma por app de AndroidX (`AppCompatDelegate`), que es lo que
 * hace que Android 13+ muestre Funny en «Idiomas de las aplicaciones» de los
 * ajustes del sistema.
 *
 * `configChanges` incluye `locale` y `layoutDirection` a propósito: así cambiar
 * de idioma —incluido pasar a árabe, que gira toda la interfaz— no recrea la
 * actividad y no se ve ningún parpadeo. La partida en curso sobrevive porque
 * vive en el ViewModel, pero un salto visual en mitad de una fiesta se nota.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // En una partida el móvil está en la mesa boca arriba durante minutos:
        // que no se apague la pantalla en mitad de una mímica.
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            AppFunny()
        }
    }
}
