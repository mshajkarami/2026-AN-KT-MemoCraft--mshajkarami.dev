package dev.mshajkarami.memocraft.features.task.presentation.model

import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus

data class TaskCardUiModel(
    val id: String,
    val title: String,
    val description: String? = null,
    val subtitle: String? = null,
    val progress: Int = 0,
    val priority: TaskPriority = TaskPriority.Normal,
    val status: TaskStatus = TaskStatus.Pending,
    val isCompleted: Boolean = false,
    val dateLabel: String? = null,
    val timeLabel: String? = null,
    val createdAtLabel: String? = null
)
