package dev.mshajkarami.memocraft.navigation

sealed interface Destination {
    val route: String
}

sealed interface BottomBarDestination : Destination

data object HomeDestination : BottomBarDestination {
    override val route: String = "home"
}

data object TasksDestination : BottomBarDestination {
    override val route: String = "tasks"
}

data object AiDestination : BottomBarDestination {
    override val route: String = "ai"
}

data object PlannerDestination : BottomBarDestination {
    override val route: String = "planner"
}

data object ProfileDestination : BottomBarDestination {
    override val route: String = "profile"
}

data object CreateTaskDestination : Destination {
    override val route: String = "create_task"
}

data object EditTaskDestination : Destination {
    const val taskIdArg = "taskId"

    override val route: String = "edit_task_screen/{$taskIdArg}"

    fun createRoute(taskId: String): String {
        return "edit_task_screen/$taskId"
    }
}
