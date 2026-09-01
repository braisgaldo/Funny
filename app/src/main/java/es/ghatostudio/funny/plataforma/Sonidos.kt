package es.ghatostudio.funny.plataforma

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import es.ghatostudio.funny.dominio.Ajustes

/** Pitidos y vibraciones del juego. Todo es opcional y se apaga desde ajustes. */
class Sonidos(context: Context) {

    var sonidoActivo: Boolean = true
    var vibracionActiva: Boolean = true

    private val tono: ToneGenerator? =
        runCatching { ToneGenerator(AudioManager.STREAM_MUSIC, 70) }.getOrNull()

    private val vibrador: Vibrator? = runCatching {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val gestor = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            gestor.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }.getOrNull()

    fun tic() = reproducir(ToneGenerator.TONE_PROP_BEEP, 90)

    fun acierto() {
        reproducir(ToneGenerator.TONE_PROP_ACK, 200)
        vibrar(60)
    }

    fun fallo() {
        reproducir(ToneGenerator.TONE_PROP_NACK, 300)
        vibrar(220)
    }

    fun finDeTiempo() {
        reproducir(ToneGenerator.TONE_PROP_BEEP2, 500)
        vibrar(500)
    }

    fun toque() = vibrar(18)

    /** Toque suave, el del botón de la donación. */
    fun caricia() = vibrar(30)

    private fun reproducir(tipo: Int, duracion: Int) {
        if (!sonidoActivo) return
        runCatching { tono?.startTone(tipo, duracion) }
    }

    private fun vibrar(milisegundos: Long) {
        if (!vibracionActiva) return
        val v = vibrador ?: return
        // minSdk es 26, así que `VibrationEffect` está siempre disponible y no
        // hace falta la rama antigua de `vibrate(Long)`.
        runCatching {
            v.vibrate(
                VibrationEffect.createOneShot(milisegundos, VibrationEffect.DEFAULT_AMPLITUDE)
            )
        }
    }

    fun liberar() {
        runCatching { tono?.release() }
    }
}

@Composable
fun recordarSonidos(ajustes: Ajustes): Sonidos {
    val context = LocalContext.current.applicationContext
    val sonidos = remember { Sonidos(context) }
    sonidos.sonidoActivo = ajustes.sonido
    sonidos.vibracionActiva = ajustes.vibracion
    DisposableEffect(Unit) {
        onDispose { sonidos.liberar() }
    }
    return sonidos
}
