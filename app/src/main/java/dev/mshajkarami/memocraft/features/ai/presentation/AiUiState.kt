package dev.mshajkarami.memocraft.features.ai.presentation

import dev.mshajkarami.memocraft.features.ai.presentation.model.AiChatMessageUiModel

data class AiUiState(
    val messages: List<AiChatMessageUiModel> = emptyList(),
    val isGeneratingResponse: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
