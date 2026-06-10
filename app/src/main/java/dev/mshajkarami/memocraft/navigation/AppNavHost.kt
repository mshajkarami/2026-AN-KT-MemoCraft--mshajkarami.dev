package dev.mshajkarami.memocraft.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.mshajkarami.memocraft.features.ai.presentation.ui.AiScreen
import dev.mshajkarami.memocraft.features.home.presentation.ui.HomeScreen
import dev.mshajkarami.memocraft.features.planner.presentation.ui.PlannerScreen
import dev.mshajkarami.memocraft.features.profile.presentation.ui.ProfileScreen
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.TasksScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    navigator: AppNavigator,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Graph.MAIN,
        route = Graph.ROOT,
        modifier = modifier
    ) {
        navigation(
            startDestination = HomeDestination.route,
            route = Graph.MAIN
        ) {
            composable(HomeDestination.route) {
                HomeScreen(
                    onNavigateToTasks = navigator::navigateToTasks,
                    onNavigateToAi = navigator::navigateToAi,
                    onNavigateToPlanner = navigator::navigateToPlanner,
                    onNavigateToProfile = navigator::navigateToProfile,
                    onNavigateToHome = navigator::navigateToHome
                )
            }

            composable(TasksDestination.route) {
                TasksScreen(
                    onNavigateToTasks = navigator::navigateToTasks,
                    onNavigateToAi = navigator::navigateToAi,
                    onNavigateToPlanner = navigator::navigateToPlanner,
                    onNavigateToProfile = navigator::navigateToProfile,
                    onNavigateToHome = navigator::navigateToHome
                )
            }

            composable(AiDestination.route) {
                AiScreen(
                    onNavigateToTasks = navigator::navigateToTasks,
                    onNavigateToAi = navigator::navigateToAi,
                    onNavigateToPlanner = navigator::navigateToPlanner,
                    onNavigateToProfile = navigator::navigateToProfile,
                    onNavigateToHome = navigator::navigateToHome
                )
            }

            composable(PlannerDestination.route) {
                PlannerScreen(
                    onNavigateToTasks = navigator::navigateToTasks,
                    onNavigateToAi = navigator::navigateToAi,
                    onNavigateToPlanner = navigator::navigateToPlanner,
                    onNavigateToProfile = navigator::navigateToProfile,
                    onNavigateToHome = navigator::navigateToHome
                )
            }

            composable(ProfileDestination.route) {
                ProfileScreen(
                    onNavigateToTasks = navigator::navigateToTasks,
                    onNavigateToAi = navigator::navigateToAi,
                    onNavigateToPlanner = navigator::navigateToPlanner,
                    onNavigateToProfile = navigator::navigateToProfile,
                    onNavigateToHome = navigator::navigateToHome
                )
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(
    title: String
) {
    Text(text = title)
}
