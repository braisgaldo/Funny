package com.fieston

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.fieston.ui.AppFieston
import com.fieston.ui.tema.Fondo
import com.fieston.ui.tema.TemaFieston

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // En una partida el móvil está en la mesa: que no se apague la pantalla.
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            TemaFieston {
                Surface(modifier = Modifier.fillMaxSize(), color = Fondo) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .safeDrawingPadding()
                    ) {
                        AppFieston()
                    }
                }
            }
        }
    }
}
