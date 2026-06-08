package dev.mshajkarami.memocraft.feature.task.presentation.model

import androidx.compose.runtime.Immutable


@Immutable
data class TaskCardUiModel(
    val title: String,
    val subtitle: String? = null,
    val progress: Int = 0,
    val priority: TaskPriority = TaskPriority.Normal,
    val status: TaskStatus = TaskStatus.Pending,
    val assigneeInitials: String? = null,
    val isCompleted: Boolean = false
)