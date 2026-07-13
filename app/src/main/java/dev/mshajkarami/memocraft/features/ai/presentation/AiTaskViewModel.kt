package dev.mshajkarami.memocraft.features.ai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mshajkarami.memocraft.features.ai.domain.model.AiChatResult
import dev.mshajkarami.memocraft.features.ai.domain.repository.AiTaskRepository
import dev.mshajkarami.memocraft.features.ai.presentation.mapper.toAiChatMessageUiModel
import dev.mshajkarami.memocraft.features.ai.presentation.ui.AiChatMessageUiModel
import dev.mshajkarami.memocraft.features.ai.presentation.ui.DetectedTaskPriority
import dev.mshajkarami.memocraft.features.ai.presentation.ui.DetectedTaskUiModel
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AiTaskViewModel @Inject constructor(
    private val aiTaskRepository: AiTaskRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiTaskUiState())
    val uiState: StateFlow<AiTaskUiState> = _uiState.asStateFlow()

    fun onSendMessage(text: String) {
        val prompt = text.trim()

        if (prompt.isBlank() || _uiState.value.isLoading) return

        val userMessage = AiChatMessageUiModel(
            id = UUID.randomUUID().toString(),
            content = prompt,
            isFromUser = true
        )

        _uiState.update { currentState ->
            currentState.copy(
                messages = currentState.messages + userMessage,
                isLoading = true,
                errorMessage = null
            )
        }

        sendMessageToAi(prompt)
    }

    private fun sendMessageToAi(prompt: String) {
        viewModelScope.launch {
            runCatching {
                val existingTasks = getExistingTasks()

                val result = aiTaskRepository.sendMessage(
                    prompt = prompt,
                    existingTasks = existingTasks
                )

                saveDetectedTasks(result)

                result
            }.onSuccess { result ->
                val aiMessage = result.toAiChatMessageUiModel()

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = null,
                        messages = currentState.messages + aiMessage
                    )
                }
            }.onFailure { throwable ->
                val fallbackMessage = throwable.message
                    ?: "مشکلی در پردازش پیام شما پیش آمد."

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = fallbackMessage,
                        messages = currentState.messages + AiChatMessageUiModel(
                            id = UUID.randomUUID().toString(),
                            content = fallbackMessage,
                            isFromUser = false,
                            detectedTasks = emptyList()
                        )
                    )
                }
            }
        }
    }

    private suspend fun getExistingTasks(): List<Task> {
        return taskRepository.getAllTasks().first()
    }

    private suspend fun saveDetectedTasks(result: AiChatResult) {
        result.detectedTasks.forEach { task ->
            taskRepository.createTask(task)
        }
    }
}
