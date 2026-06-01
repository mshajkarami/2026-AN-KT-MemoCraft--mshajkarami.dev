package dev.mshajkarami.memocraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.mshajkarami.memocraft.core.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.feature.home.presentation.ui.MemoCraftScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoCraftTheme {
                MemoCraftScreen()
            }
        }
    }
}