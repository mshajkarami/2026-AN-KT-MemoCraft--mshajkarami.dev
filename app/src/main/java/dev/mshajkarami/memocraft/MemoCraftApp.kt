package dev.mshajkarami.memocraft

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import dev.mshajkarami.memocraft.navigation.AppNavHost
import dev.mshajkarami.memocraft.navigation.AppNavigator

@Composable
fun MemoCraftApp() {
    val navController = rememberNavController()
    val navigator = remember(navController) { AppNavigator(navController) }

    AppNavHost(
        navController = navController,
        navigator = navigator
    )
}