package dev.mshajkarami.memocraft.app.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import dev.mshajkarami.memocraft.features.ai.presentation.AiRoute
import dev.mshajkarami.memocraft.features.profile.presentation.ui.ProfileScreen
import dev.mshajkarami.memocraft.features.task.presentation.create.CreateTaskScreenRoute
import dev.mshajkarami.memocraft.features.task.presentation.list.TasksScreenRoute

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
            startDestination = TasksDestination.route,
            route = Graph.MAIN
        ) {
            composable(TasksDestination.route) {
                TasksScreenRoute(
                    onCreateTaskClick = navigator::navigateToCreateTask,
                    onTaskClick = navigator::navigateToEditTask
                )
            }

            composable(AiDestination.route) {
                AiRoute(
                    onAddDetectedTaskClick = {
                        navigator.navigateToCreateTask()
                    }
                )
            }

            composable(ProfileDestination.route) {
                ProfileScreen()
            }

            composable(CreateTaskDestination.route) {
                CreateTaskScreenRoute(
                    onBackClick = navigator::navigateBack,
                    onTaskSaved = {
                        Log.d(
                            "CreateTask",
                            "onTaskSaved called"
                        )

                        navController.navigate(TasksDestination.route) {
                            popUpTo(CreateTaskDestination.route) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(
                route = EditTaskDestination.route,
                arguments = listOf(
                    navArgument(EditTaskDestination.taskIdArg) {
                        type = NavType.StringType
                    }
                )
            ) {
                CreateTaskScreenRoute(
                    onBackClick = navigator::navigateBack,
                    onTaskSaved = navigator::navigateBack
                )
            }
        }
    }
}
