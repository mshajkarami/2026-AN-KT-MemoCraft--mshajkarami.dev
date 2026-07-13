package dev.mshajkarami.memocraft.features.ai.domain.repository

import dev.mshajkarami.memocraft.features.ai.domain.model.AiChatResult
import dev.mshajkarami.memocraft.features.task.domain.model.Task

interface AiTaskRepository {

    suspend fun sendMessage(
        prompt: String,
        existingTasks: List<Task>
    ): AiChatResult
}
