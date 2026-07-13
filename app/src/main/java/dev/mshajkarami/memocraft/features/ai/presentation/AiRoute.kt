package dev.mshajkarami.memocraft.features.ai.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mshajkarami.memocraft.features.ai.presentation.model.DetectedTaskUiModel

@Composable
fun AiRoute(
    onAddDetectedTaskClick: (DetectedTaskUiModel) -> Unit,
    viewModel: AiViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AiScreen(
        messages = uiState.messages,
        isGeneratingResponse = uiState.isGeneratingResponse,
        onSendMessage = viewModel::onSendMessage,
        onAddDetectedTaskClick = onAddDetectedTaskClick
    )
}
