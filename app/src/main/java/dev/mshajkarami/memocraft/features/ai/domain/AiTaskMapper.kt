package dev.mshajkarami.memocraft.features.ai.domain

import dev.mshajkarami.memocraft.features.ai.data.dto.AiGeneratedSubTaskDto
import dev.mshajkarami.memocraft.features.ai.data.dto.AiGeneratedTaskDto
import dev.mshajkarami.memocraft.features.task.domain.model.SubTask
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import kotlin.math.roundToInt

fun AiGeneratedTaskDto.toTask(): Task {
    val now = LocalDateTime.now()

    val safeId = id
        ?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?: UUID.randomUUID().toString()

    val safeTitle = title
        .trim()
        .takeIf { it.isNotEmpty() }
        ?: DEFAULT_TASK_TITLE

    val safeDescription = description
        ?.trim()
        ?.takeIf { it.isNotEmpty() }

    val safeCreatedAt = createdAt.toLocalDateTimeOrNull() ?: now
    val safeUpdatedAt = updatedAt.toLocalDateTimeOrNull() ?: safeCreatedAt

    return Task(
        id = safeId,
        title = safeTitle,
        description = safeDescription,
        dueAt = dueDate.toDueAtOrNull(),
        startAt = startAt.toLocalDateTimeOrNull(),
        endAt = endAt.toLocalDateTimeOrNull(),
        estimatedDurationHours = estimatedDurationHours.toPositiveIntHoursOrNull(),
        priority = priority.toTaskPriority(),
        status = status.toTaskStatus(),
        subTasks = subTasks.mapNotNull { it.toDomainOrNull() },
        createdAt = safeCreatedAt,
        updatedAt = safeUpdatedAt
    )
}

private fun AiGeneratedSubTaskDto.toDomainOrNull(): SubTask? {
    val safeTitle = title
        .trim()
        .takeIf { it.isNotEmpty() }
        ?: return null

    return SubTask(
        title = safeTitle
    )
}

private fun String?.toDueAtOrNull(): LocalDateTime? {
    val value = this
        ?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?: return null

    return runCatching {
        LocalDateTime.parse(value)
    }.recoverCatching {
        LocalDate.parse(value).atTime(23, 59, 59)
    }.getOrNull()
}

private fun String?.toLocalDateTimeOrNull(): LocalDateTime? {
    val value = this
        ?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?: return null

    return runCatching {
        LocalDateTime.parse(value)
    }.getOrNull()
}

private fun Double?.toPositiveIntHoursOrNull(): Int? {
    return this
        ?.takeIf { it > 0.0 }
        ?.roundToInt()
        ?.takeIf { it > 0 }
}

private fun String?.toTaskPriority(): TaskPriority {
    return when (this?.trim()?.lowercase()) {
        "low" -> TaskPriority.Low
        "normal", "medium" -> TaskPriority.Normal
        "urgent", "high" -> TaskPriority.Urgent
        else -> TaskPriority.Normal
    }
}

private fun String?.toTaskStatus(): TaskStatus {
    return when (this?.trim()?.lowercase()) {
        "pending", "todo", "open" -> TaskStatus.Pending
        "in_progress", "in progress", "doing", "started" -> TaskStatus.InProgress
        "done", "completed", "complete" -> TaskStatus.Completed
        else -> TaskStatus.Pending
    }
}

private const val DEFAULT_TASK_TITLE = "Untitled task"
