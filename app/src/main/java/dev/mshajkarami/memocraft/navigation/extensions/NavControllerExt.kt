package dev.mshajkarami.memocraft.navigation.extensions

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import dev.mshajkarami.memocraft.navigation.AiDestination
import dev.mshajkarami.memocraft.navigation.BottomNavItem
import dev.mshajkarami.memocraft.navigation.ProfileDestination
import dev.mshajkarami.memocraft.navigation.TasksDestination

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