package dev.mshajkarami.memocraft.features.task.domain.model

import java.time.LocalDateTime
import java.util.UUID
import kotlin.math.roundToInt

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val dueAt: LocalDateTime? = null,
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null,
    val estimatedDurationHours: Int? = null,
    val priority: TaskPriority = TaskPriority.Normal,
    val status: TaskStatus = TaskStatus.Pending,
    val subTasks: List<SubTask> = emptyList(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val totalSubTasksCount: Int
        get() = subTasks.size

    val completedSubTasksCount: Int
        get() = subTasks.count { it.isCompleted }

    val progress: Int
        get() {
            if (subTasks.isEmpty()) {
                return if (status == TaskStatus.Completed) 100 else 0
            }

            return ((completedSubTasksCount.toFloat() / totalSubTasksCount.toFloat()) * 100)
                .roundToInt()
                .coerceIn(0, 100)
        }

    val isCompleted: Boolean
        get() = status == TaskStatus.Completed ||
                (subTasks.isNotEmpty() && completedSubTasksCount == totalSubTasksCount)

    fun addSubTask(title: String): Task {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isBlank()) return this

        return copy(
            subTasks = subTasks + SubTask(title = trimmedTitle),
            updatedAt = LocalDateTime.now()
        )
    }

    fun updateSubTask(subTaskId: String, newTitle: String): Task {
        val trimmedTitle = newTitle.trim()
        if (trimmedTitle.isBlank()) return this

        return copy(
            subTasks = subTasks.map { subTask ->
                if (subTask.id == subTaskId) {
                    subTask.copy(
                        title = trimmedTitle,
                        updatedAt = LocalDateTime.now()
                    )
                } else {
                    subTask
                }
            },
            updatedAt = LocalDateTime.now()
        )
    }

    fun toggleSubTask(subTaskId: String): Task {
        val now = LocalDateTime.now()

        return copy(
            subTasks = subTasks.map { subTask ->
                if (subTask.id == subTaskId) {
                    val completed = !subTask.isCompleted
                    subTask.copy(
                        isCompleted = completed,
                        completedAt = if (completed) now else null,
                        updatedAt = now
                    )
                } else {
                    subTask
                }
            },
            updatedAt = now
        )
    }

    fun removeSubTask(subTaskId: String): Task {
        return copy(
            subTasks = subTasks.filterNot { it.id == subTaskId },
            updatedAt = LocalDateTime.now()
        )
    }

    fun markAllSubTasksCompleted(): Task {
        val now = LocalDateTime.now()

        return copy(
            status = TaskStatus.Completed,
            subTasks = subTasks.map { subTask ->
                subTask.copy(
                    isCompleted = true,
                    completedAt = subTask.completedAt ?: now,
                    updatedAt = now
                )
            },
            updatedAt = now
        )
    }

    fun markAllSubTasksPending(): Task {
        val now = LocalDateTime.now()

        return copy(
            status = TaskStatus.Pending,
            subTasks = subTasks.map { subTask ->
                subTask.copy(
                    isCompleted = false,
                    completedAt = null,
                    updatedAt = now
                )
            },
            updatedAt = now
        )
    }
}
