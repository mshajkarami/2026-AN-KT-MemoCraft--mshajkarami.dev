package dev.mshajkarami.memocraft.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.mshajkarami.memocraft.features.ai.presentation.ui.AiScreen
import dev.mshajkarami.memocraft.features.home.presentation.HomeScreenRoute
import dev.mshajkarami.memocraft.features.home.presentation.ui.HomeScreen
import dev.mshajkarami.memocraft.features.planner.presentation.ui.PlannerScreen
import dev.mshajkarami.memocraft.features.profile.presentation.ui.ProfileScreen
import dev.mshajkarami.memocraft.features.task.presentation.CreateTaskScreenRoute
import dev.mshajkarami.memocraft.features.tasks.presentation.TasksScreenRoute

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
                HomeScreenRoute(
                    onSeeAllTasksClick = {

                    }
                )
            }

            composable(TasksDestination.route) {
                TasksScreenRoute(
                    onCreateTaskClick = navigator::navigateToCreateTask
                )
            }

            composable(AiDestination.route) {
                AiScreen()
            }

            composable(PlannerDestination.route) {
                PlannerScreen()
            }

            composable(ProfileDestination.route) {
                ProfileScreen()
            }
            composable(CreateTaskDestination.route) {
                CreateTaskScreenRoute(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onTaskSaved = {
                        Log.d("CreateTask", "onTaskSaved called")
                        navController.navigate(TasksDestination.route) {
                            popUpTo(CreateTaskDestination.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }


        }
    }
}
