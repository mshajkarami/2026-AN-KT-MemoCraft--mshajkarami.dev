package dev.mshajkarami.memocraft.app.navigation.extensions

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import dev.mshajkarami.memocraft.app.navigation.AiDestination
import dev.mshajkarami.memocraft.app.navigation.BottomNavItem
import dev.mshajkarami.memocraft.app.navigation.ProfileDestination
import dev.mshajkarami.memocraft.app.navigation.TasksDestination

fun NavDestination?.toBottomNavItem(): BottomNavItem? {
    val hasRoute: (String) -> Boolean = { route ->
        this?.hierarchy?.any { destination ->
            destination.route == route
        } == true
    }

    return when {
        hasRoute(TasksDestination.route) -> BottomNavItem.Tasks
        hasRoute(AiDestination.route) -> BottomNavItem.Ai
        hasRoute(ProfileDestination.route) -> BottomNavItem.Profile
        else -> null
    }
}