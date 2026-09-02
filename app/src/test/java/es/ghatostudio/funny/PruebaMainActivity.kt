package es.ghatostudio.funny

import android.app.Application
import android.content.Context
import android.util.TypedValue
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ApplicationProvider
import es.ghatostudio.funny.ui.JuegoViewModel
import es.ghatostudio.funny.ui.SalonViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import androidx.appcompat.R as AppCompatR

/**
 * Que la app pueda arrancar. Nada más, y resultó ser lo que faltaba.
 *
 * Este fichero existe por dos caídas reales en el primer arranque en un móvil,
 * con 164 pruebas en verde, ktlint limpio y el lint de Android sin errores.
 * Ninguna de las 164 instanciaba la Activity ni el ViewModel, así que ninguna
 * podía verlas:
 *
 * 1. **El tema.** El manifiesto usaba `android:Theme.Material.NoActionBar`, que
 *    es el del sistema y no desciende de AppCompat. [MainActivity] hereda de
 *    `AppCompatActivity` —hace falta para la preferencia de idioma por app— y
 *    AppCompat comprueba el tema en `setContentView`:
 *    `IllegalStateException: You need to use a Theme.AppCompat theme`.
 *
 * 2. **El constructor del ViewModel.** `AndroidViewModelFactory` hace
 *    `getConstructor(Application::class.java)`, y los parámetros por defecto de
 *    Kotlin no generan ese overload: `NoSuchMethodException`. Hacía falta
 *    `@JvmOverloads`.
 *
 * Las dos rompían la app **en debug y en release por igual**. Compilaba y se
 * caía al abrirla.
 *
 * La moraleja es la del punto 12 de la plantilla, y ha salido cara: «un
 * "debería funcionar" no cuenta». Lo que sí se puede automatizar de esa
 * comprobación está aquí; el resto sigue necesitando un móvil delante.
 */
@RunWith(RobolectricTestRunner::class)
class PruebaMainActivity {
    private val contexto: Context get() = ApplicationProvider.getApplicationContext()

    private fun temaDeLaApp() =
        contexto.resources.newTheme().apply { applyStyle(R.style.Theme_Funny, true) }

    // ------------------------------------------------------------ el tema

    @Test
    fun `el tema de la aplicacion desciende de Theme AppCompat`() {
        // Se comprueba igual que lo comprueba AppCompat: resolviendo un atributo
        // que solo existe en sus temas. Si no resuelve, el tema no es AppCompat.
        val valor = TypedValue()
        val resuelve = temaDeLaApp().resolveAttribute(AppCompatR.attr.windowActionBar, valor, true)

        assertTrue(
            resuelve,
            "Theme.Funny no resuelve windowActionBar, así que NO desciende de " +
                "Theme.AppCompat. MainActivity es una AppCompatActivity y la app " +
                "reventará al arrancar con IllegalStateException. Ver themes.xml.",
        )
    }

    @Test
    fun `el tema de arranque no pinta barra de accion`() {
        // Compose pinta la interfaz entera, incluida su propia cabecera. Una
        // barra de acción del sistema encima sería una franja duplicada.
        val valor = TypedValue()
        temaDeLaApp().resolveAttribute(AppCompatR.attr.windowActionBar, valor, true)
        assertTrue(valor.data == 0, "el tema declara windowActionBar = ${valor.data}")
    }

    @Test
    fun `el tema declara un fondo de arranque`() {
        // Sin él, entre el toque en el icono y el primer fotograma de Compose se
        // ve el fondo del sistema, que en claro es un flash blanco.
        val valor = TypedValue()
        val resuelve = temaDeLaApp().resolveAttribute(android.R.attr.windowBackground, valor, true)
        assertTrue(resuelve, "el tema no declara windowBackground")
    }

    // ------------------------------------------------- los ViewModels

    @Test
    fun `JuegoViewModel tiene el constructor de un solo Application`() {
        // Esta es la comprobación que faltaba. `AndroidViewModelFactory` hace
        // exactamente esto por reflexión, así que el test hace lo mismo: si
        // lanza NoSuchMethodException, la app no arranca.
        val constructor = JuegoViewModel::class.java.getConstructor(Application::class.java)
        assertNotNull(constructor)
    }

    @Test
    fun `SalonViewModel se puede crear sin argumentos`() {
        // El otro camino de `viewModel()`: para un ViewModel que no es
        // AndroidViewModel, la factoría busca el constructor vacío.
        val constructor = SalonViewModel::class.java.getConstructor()
        assertNotNull(constructor)
    }

    @Test
    fun `los ViewModels se instancian de verdad, no solo declaran el constructor`() {
        // Tener el constructor y que funcione no son lo mismo: podría lanzar al
        // cargar los assets o al leer las preferencias. Se crean de verdad.
        val app = ApplicationProvider.getApplicationContext<Application>()
        val juego: ViewModel = JuegoViewModel(app)
        val salon: ViewModel = SalonViewModel()
        assertNotNull(juego)
        assertNotNull(salon)
    }

    @Test
    fun `el estado inicial del juego es coherente`() {
        // Que arranque en el menú y sin partida en curso. Si el ViewModel
        // apareciera a mitad de una partida fantasma, se vería aquí.
        val vm = JuegoViewModel(ApplicationProvider.getApplicationContext())
        assertTrue(!vm.estado.partidaEnCurso, "arranca con una partida en curso")
        assertNotNull(vm.estado.ajustes)
    }
}
