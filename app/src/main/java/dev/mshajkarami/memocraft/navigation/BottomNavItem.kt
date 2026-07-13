package dev.mshajkarami.memocraft.navigation

enum class BottomNavItem(
    val route: String
) {
    Tasks(TasksDestination.route),
    Ai(AiDestination.route),
    Profile(ProfileDestination.route)
}