package dev.mshajkarami.memocraft.features.task.presentation.model

import androidx.compose.runtime.Immutable
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus

@Immutable
data class TaskCardUiModel(
    val id: String,
    val title: String,
    val description: String? = null,
    val subtitle: String? = null,
    val progress: Int = 0,
    val priority: TaskPriority = TaskPriority.Normal,
    val status: TaskStatus = TaskStatus.Pending,
    val isCompleted: Boolean = false,
    val timeLabel: String? = null
)
