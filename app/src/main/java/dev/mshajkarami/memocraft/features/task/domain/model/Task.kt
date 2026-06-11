package dev.mshajkarami.memocraft.features.task.domain.model

import dev.mshajkarami.memocraft.features.task.presentation.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class Task(
    val id: String,
    val title: String,
    val description: String? = null,
    val dueDate: LocalDate? = null,
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null,
    val priority: TaskPriority = TaskPriority.Normal,
    val status: TaskStatus = TaskStatus.Pending,
    val progress: Int = 0,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
