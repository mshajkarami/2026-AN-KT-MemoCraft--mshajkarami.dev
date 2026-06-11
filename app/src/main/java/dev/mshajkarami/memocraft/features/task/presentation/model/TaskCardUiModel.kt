package dev.mshajkarami.memocraft.features.task.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class TaskCardUiModel(
    val title: String,
    val subtitle: String? = null,
    val progress: Int = 0,
    val priority: TaskPriority = TaskPriority.Normal,
    val status: TaskStatus = TaskStatus.Pending,
    val isCompleted: Boolean = false,
    val timeLabel: String? = null
)
