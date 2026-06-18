package dev.mshajkarami.memocraft.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class AppNavigator(
    private val navController: NavHostController
) {
    fun navigateToBottomBarDestination(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToHome() = navigateToBottomBarDestination(HomeDestination.route)
    fun navigateToTasks() = navigateToBottomBarDestination(TasksDestination.route)
    fun navigateToAi() = navigateToBottomBarDestination(AiDestination.route)
    fun navigateToPlanner() = navigateToBottomBarDestination(PlannerDestination.route)
    fun navigateToProfile() = navigateToBottomBarDestination(ProfileDestination.route)

    fun navigateToCreateTask() {
        navController.navigate(CreateTaskDestination.route)
    }

    fun navigateToEditTask(taskId: String) {
        navController.navigate(EditTaskDestination.createRoute(taskId))
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}
