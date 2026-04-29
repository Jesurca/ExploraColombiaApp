package me.jesusurbinez.exploracolombiaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.jesusurbinez.exploracolombiaapp.ui.theme.ExploraColombiaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExploraColombiaAppTheme {
                // Ahora MainActivity solo llama a AppNavigation
                // La lógica de a dónde ir está encapsulada allí.
                AppNavigation()
            }
        }
    }
}
