package dev.mshajkarami.memocraft.features.ai.presentation.mapper

import dev.mshajkarami.memocraft.features.ai.domain.model.AiChatResult
import dev.mshajkarami.memocraft.features.ai.presentation.ui.AiChatMessageUiModel
import dev.mshajkarami.memocraft.features.ai.presentation.ui.DetectedTaskPriority
import dev.mshajkarami.memocraft.features.ai.presentation.ui.DetectedTaskUiModel
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

fun AiChatResult.toAiChatMessageUiModel(): AiChatMessageUiModel {
    return AiChatMessageUiModel(
        id = UUID.randomUUID().toString(),
        content = assistantMessage,
        isFromUser = false,
        detectedTasks = detectedTasks.map { task ->
            task.toDetectedTaskUiModel()
        }
    )
}

fun Task.toDetectedTaskUiModel(): DetectedTaskUiModel {
    return DetectedTaskUiModel(
        id = id,
        title = title,
        dueText = dueAt.toDueText(),
        priority = priority.toDetectedTaskPriority()
    )
}

fun TaskPriority.toDetectedTaskPriority(): DetectedTaskPriority {
    return when (this) {
        TaskPriority.Low -> DetectedTaskPriority.Low
        TaskPriority.Normal -> DetectedTaskPriority.Normal
        TaskPriority.Urgent -> DetectedTaskPriority.Urgent
    }
}

private fun LocalDateTime?.toDueText(): String {
    return this?.format(DUE_TEXT_FORMATTER) ?: NO_DATE_TEXT
}

private val DUE_TEXT_FORMATTER: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")

private const val NO_DATE_TEXT = "No date"
