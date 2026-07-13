package dev.mshajkarami.memocraft.features.ai.presentation.mapper

import dev.mshajkarami.memocraft.features.ai.domain.model.AiChatResult
import dev.mshajkarami.memocraft.features.ai.presentation.model.AiChatMessageUiModel
import dev.mshajkarami.memocraft.features.ai.presentation.model.DetectedTaskPriority
import dev.mshajkarami.memocraft.features.ai.presentation.model.DetectedTaskUiModel
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.UUID

fun AiChatResult.toAiChatMessageUiModel(): AiChatMessageUiModel {
    val taskUiModels = detectedTasks.map { task ->
        task.toDetectedTaskUiModel()
    }

    return AiChatMessageUiModel(
        id = UUID.randomUUID().toString(),
        content = assistantMessage.toAssistantMessageOrDefault(
            tasksCount = taskUiModels.size
        ),
        isFromUser = false,
        createdAt = LocalDateTime.now(),
        detectedTasks = taskUiModels
    )
}

fun AiChatResult.toAiChatMessageUiModel(
    detectedTaskUiModels: List<DetectedTaskUiModel>
): AiChatMessageUiModel {
    return AiChatMessageUiModel(
        id = UUID.randomUUID().toString(),
        content = assistantMessage.toAssistantMessageOrDefault(
            tasksCount = detectedTaskUiModels.size
        ),
        isFromUser = false,
        createdAt = LocalDateTime.now(),
        detectedTasks = detectedTaskUiModels
    )
}

fun Task.toDetectedTaskUiModel(): DetectedTaskUiModel {
    return DetectedTaskUiModel(
        id = id,
        savedTaskId = id,
        title = title.toTaskTitleOrDefault(),
        description = description.toTaskDescriptionOrNull(),
        dueAt = dueAt,
        dueText = dueAt.toDueText(),
        priority = priority.toDetectedTaskPriority(),
        actionText = VIEW_TASK_TEXT
    )
}

fun TaskPriority.toDetectedTaskPriority(): DetectedTaskPriority {
    return when (this) {
        TaskPriority.Low -> DetectedTaskPriority.Low
        TaskPriority.Normal -> DetectedTaskPriority.Normal
        TaskPriority.Urgent -> DetectedTaskPriority.Urgent
    }
}

private fun String.toAssistantMessageOrDefault(
    tasksCount: Int
): String {
    val normalizedMessage = trim()

    if (normalizedMessage.isNotBlank()) {
        return normalizedMessage
    }

    return when (tasksCount) {
        0 -> "I checked your message, but no task was created."
        1 -> "Done. I created and saved 1 task for you."
        else -> "Done. I created and saved $tasksCount tasks for you."
    }
}

private fun String.toTaskTitleOrDefault(): String {
    return trim().takeIf { it.isNotBlank() } ?: UNTITLED_TASK_TEXT
}

private fun String?.toTaskDescriptionOrNull(): String? {
    return this
        ?.trim()
        ?.takeIf { it.isNotBlank() }
}

private fun LocalDateTime?.toDueText(): String {
    if (this == null) {
        return NO_DATE_TEXT
    }

    val today = LocalDate.now()
    val targetDate = toLocalDate()

    return when {
        targetDate.isEqual(today) -> TODAY_TEXT

        targetDate.isEqual(today.plusDays(1)) -> TOMORROW_TEXT

        targetDate.isEqual(today.minusDays(1)) -> YESTERDAY_TEXT

        targetDate.isAfter(today) && targetDate.isBefore(today.plusDays(7)) -> {
            val days = ChronoUnit.DAYS.between(today, targetDate)
            "In $days days"
        }

        targetDate.isBefore(today) && targetDate.isAfter(today.minusDays(7)) -> {
            val days = ChronoUnit.DAYS.between(targetDate, today)
            "$days days ago"
        }

        else -> format(DUE_TEXT_FORMATTER)
    }
}

private val DUE_TEXT_FORMATTER: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")

private const val NO_DATE_TEXT = "No date"
private const val TODAY_TEXT = "Today"
private const val TOMORROW_TEXT = "Tomorrow"
private const val YESTERDAY_TEXT = "Yesterday"
private const val VIEW_TASK_TEXT = "View task"
private const val UNTITLED_TASK_TEXT = "Untitled task"
