package dev.mshajkarami.memocraft.features.ai.domain.model

import dev.mshajkarami.memocraft.features.task.domain.model.Task

data class AiChatResult(
    val assistantMessage: String,
    val detectedTasks: List<Task>
)