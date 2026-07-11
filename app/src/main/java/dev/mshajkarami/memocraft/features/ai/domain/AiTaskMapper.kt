package dev.mshajkarami.memocraft.features.ai.domain

import dev.mshajkarami.memocraft.features.ai.data.dto.AiGeneratedTaskDto
import dev.mshajkarami.memocraft.features.task.domain.model.SubTask
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import java.time.LocalDate
import java.time.LocalDateTime

fun AiGeneratedTaskDto.toTask(): Task {
    return Task(
        title = title.trim(),
        description = description?.trim()?.takeIf { it.isNotBlank() },
        dueDate = dueDate.toLocalDateOrNull(),
        startAt = startAt.toLocalDateTimeOrNull(),
        endAt = endAt.toLocalDateTimeOrNull(),
        estimatedDurationHours = estimatedDurationHours,
        priority = priority.toTaskPriority(),
        subTasks = subTasks
            .mapNotNull { subTask ->
                val trimmedTitle = subTask.title.trim()

                if (trimmedTitle.isBlank()) {
                    null
                } else {
                    SubTask(title = trimmedTitle)
                }
            }
    )
}

private fun String?.toLocalDateOrNull(): LocalDate? {
    if (this.isNullOrBlank()) return null

    return runCatching {
        LocalDate.parse(this)
    }.getOrNull()
}

private fun String?.toLocalDateTimeOrNull(): LocalDateTime? {
    if (this.isNullOrBlank()) return null

    return runCatching {
        LocalDateTime.parse(this)
    }.getOrNull()
}

private fun String?.toTaskPriority(): TaskPriority {
    return when (this?.trim()?.lowercase()) {
        "low" -> TaskPriority.Low
        "urgent", "high" -> TaskPriority.Urgent
        else -> TaskPriority.Normal
    }
}
