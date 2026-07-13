package dev.mshajkarami.memocraft.features.ai.presentation

import dev.mshajkarami.memocraft.features.ai.presentation.model.AiChatMessageUiModel
import java.util.UUID

data class AiUiState(
    val messages: List<AiChatMessageUiModel> = listOf(
        AiChatMessageUiModel(
            id = UUID.randomUUID().toString(),
            content = "Hi! I’m MemoCraft AI. Tell me what you need to do, and I’ll detect tasks from your text.",
            isFromUser = false,
            detectedTasks = emptyList()
        )
    ),
    val isGeneratingResponse: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
