package dev.mshajkarami.memocraft.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import dev.mshajkarami.memocraft.features.main.MainScreen
import dev.mshajkarami.memocraft.app.navigation.AppNavigator

@Composable
fun MemoCraftApp() {
    val navController = rememberNavController()
    val navigator = remember(navController) { AppNavigator(navController) }

    MainScreen(
        navController = navController,
        navigator = navigator
    )
}
