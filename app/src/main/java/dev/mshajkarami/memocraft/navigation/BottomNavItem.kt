package dev.mshajkarami.memocraft.navigation

enum class BottomNavItem(
    val route: String
) {
    Home(HomeDestination.route),
    Tasks(TasksDestination.route),
    Ai(AiDestination.route),
    Profile(ProfileDestination.route)
}