package dev.mshajkarami.memocraft.features.ai.domain.repository

import dev.mshajkarami.memocraft.features.task.domain.model.Task

interface AiTaskRepository {

    suspend fun generateTasksFromPrompt(
        prompt: String
    ): List<Task>
}
