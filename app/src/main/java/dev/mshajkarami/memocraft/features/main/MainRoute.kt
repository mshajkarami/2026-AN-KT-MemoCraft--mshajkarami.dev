package dev.mshajkarami.memocraft.features.main

sealed class MainRoute(val route: String) {
    data object Home : MainRoute("home")
    data object Tasks : MainRoute("tasks")
    data object Ai : MainRoute("ai")
    data object Planner : MainRoute("planner")
    data object Profile : MainRoute("profile")
}
