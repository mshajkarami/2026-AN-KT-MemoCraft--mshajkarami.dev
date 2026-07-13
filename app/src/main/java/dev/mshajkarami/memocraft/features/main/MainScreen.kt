package dev.mshajkarami.memocraft.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.mshajkarami.memocraft.app.navigation.AiDestination
import dev.mshajkarami.memocraft.app.navigation.AppNavHost
import dev.mshajkarami.memocraft.app.navigation.AppNavigator
import dev.mshajkarami.memocraft.app.navigation.BottomNavBar
import dev.mshajkarami.memocraft.app.navigation.BottomNavItem
import dev.mshajkarami.memocraft.app.navigation.ProfileDestination
import dev.mshajkarami.memocraft.app.navigation.TasksDestination

@Composable
fun MainScreen(
    navController: NavHostController,
    navigator: AppNavigator,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentBottomNavItem = when {
        currentDestination.isInHierarchy(TasksDestination.route) -> BottomNavItem.Tasks
        currentDestination.isInHierarchy(AiDestination.route) -> BottomNavItem.Ai
        currentDestination.isInHierarchy(ProfileDestination.route) -> BottomNavItem.Profile
        else -> BottomNavItem.Tasks
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                selectedItem = currentBottomNavItem,
                onTasksClick = navigator::navigateToTasks,
                onAiClick = navigator::navigateToAi,
                onProfileClick = navigator::navigateToProfile
            )
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            navigator = navigator,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        )
    }
}

private fun NavDestination?.isInHierarchy(route: String): Boolean {
    return this?.hierarchy?.any { it.route == route } == true
}
