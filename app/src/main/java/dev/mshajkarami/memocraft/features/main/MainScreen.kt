package dev.mshajkarami.memocraft.features.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.mshajkarami.memocraft.navigation.AiDestination
import dev.mshajkarami.memocraft.navigation.AppNavHost
import dev.mshajkarami.memocraft.navigation.AppNavigator
import dev.mshajkarami.memocraft.navigation.BottomNavBar
import dev.mshajkarami.memocraft.navigation.BottomNavItem
import dev.mshajkarami.memocraft.navigation.HomeDestination
import dev.mshajkarami.memocraft.navigation.PlannerDestination
import dev.mshajkarami.memocraft.navigation.ProfileDestination
import dev.mshajkarami.memocraft.navigation.TasksDestination

@Composable
fun MainScreen(
    navController: NavHostController,
    navigator: AppNavigator,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentBottomNavItem = when {
        currentDestination.isInHierarchy(HomeDestination.route) -> BottomNavItem.Home
        currentDestination.isInHierarchy(TasksDestination.route) -> BottomNavItem.Tasks
        currentDestination.isInHierarchy(AiDestination.route) -> BottomNavItem.Ai
        currentDestination.isInHierarchy(PlannerDestination.route) -> BottomNavItem.Planner
        currentDestination.isInHierarchy(ProfileDestination.route) -> BottomNavItem.Profile
        else -> BottomNavItem.Home
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                selectedItem = currentBottomNavItem,
                onHomeClick = navigator::navigateToHome,
                onTasksClick = navigator::navigateToTasks,
                onAiClick = navigator::navigateToAi,
                onPlannerClick = navigator::navigateToPlanner,
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