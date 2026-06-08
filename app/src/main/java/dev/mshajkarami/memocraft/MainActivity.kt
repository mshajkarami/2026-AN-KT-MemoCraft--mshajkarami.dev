package dev.mshajkarami.memocraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.features.home.presentation.ui.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoCraftAppTheme {
                MemoCraftApp()
            }
        }
    }
}