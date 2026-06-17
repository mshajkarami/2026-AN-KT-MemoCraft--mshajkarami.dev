package dev.mshajkarami.memocraft.features.home.presentation.viewmodel

import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val upcomingTasks: List<TaskCardUiModel> = emptyList(),
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val pendingTasks: Int = 0,
    val inProgressTasks: Int = 0,
    val overallProgress: Int = 0,
    val focusTimeText: String = "0h 0m",
    val errorMessage: String? = null,

    val searchQuery: String = "",
    val isSearchActive: Boolean = false
)

