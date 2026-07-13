package dev.mshajkarami.memocraft.features.ai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mshajkarami.memocraft.features.ai.domain.model.AiChatResult
import dev.mshajkarami.memocraft.features.ai.domain.repository.AiTaskRepository
import dev.mshajkarami.memocraft.features.ai.presentation.mapper.toAiChatMessageUiModel
import dev.mshajkarami.memocraft.features.ai.presentation.mapper.toDetectedTaskUiModel
import dev.mshajkarami.memocraft.features.ai.presentation.model.AiChatMessageUiModel
import dev.mshajkarami.memocraft.features.ai.presentation.model.DetectedTaskUiModel
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.repository.TaskRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AiViewModel @Inject constructor(
    private val aiTaskRepository: AiTaskRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiUiState())
    val uiState: StateFlow<AiUiState> = _uiState.asStateFlow()

    fun onSendMessage(text: String) {
        val prompt = text.trim()

        if (prompt.isBlank() || _uiState.value.isGeneratingResponse) {
            return
        }

        val userMessage = createUserMessage(prompt)

        _uiState.update { currentState ->
            currentState.copy(
                messages = currentState.messages + userMessage,
                isLoading = true,
                isGeneratingResponse = true,
                errorMessage = null
            )
        }

        sendMessageToAi(prompt)
    }

    private fun sendMessageToAi(prompt: String) {
        viewModelScope.launch {
            try {
                val existingTasks = getExistingTasks()

                val result = aiTaskRepository.sendMessage(
                    prompt = prompt,
                    existingTasks = existingTasks
                )

                val savedTaskUiModels = saveDetectedTasksAndMapToUi(result)

                val aiMessage = result.toAiChatMessageUiModel(
                    detectedTaskUiModels = savedTaskUiModels
                )

                _uiState.update { currentState ->
                    currentState.copy(
                        messages = currentState.messages + aiMessage,
                        isLoading = false,
                        isGeneratingResponse = false,
                        errorMessage = null
                    )
                }
            } catch (exception: CancellationException) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isGeneratingResponse = false
                    )
                }

                throw exception
            } catch (throwable: Throwable) {
                handleSendMessageFailure(throwable)
            }
        }
    }

    private fun createUserMessage(prompt: String): AiChatMessageUiModel {
        return AiChatMessageUiModel(
            id = UUID.randomUUID().toString(),
            content = prompt,
            isFromUser = true,
            createdAt = LocalDateTime.now(),
            detectedTasks = emptyList()
        )
    }

    private suspend fun getExistingTasks(): List<Task> {
        return taskRepository
            .getAllTasks()
            .first()
    }

    private suspend fun saveDetectedTasksAndMapToUi(
        result: AiChatResult
    ): List<DetectedTaskUiModel> {
        if (result.detectedTasks.isEmpty()) {
            return emptyList()
        }

        return result.detectedTasks.map { task ->
            taskRepository.createTask(task)

            task.toDetectedTaskUiModel()
        }
    }

    private fun handleSendMessageFailure(throwable: Throwable) {
        val fallbackMessage = throwable.message
            ?.takeIf { message -> message.isNotBlank() }
            ?: DEFAULT_ERROR_MESSAGE

        val errorMessage = AiChatMessageUiModel(
            id = UUID.randomUUID().toString(),
            content = fallbackMessage,
            isFromUser = false,
            createdAt = LocalDateTime.now(),
            detectedTasks = emptyList()
        )

        _uiState.update { currentState ->
            currentState.copy(
                messages = currentState.messages + errorMessage,
                isLoading = false,
                isGeneratingResponse = false,
                errorMessage = fallbackMessage
            )
        }
    }

    private companion object {
        const val DEFAULT_ERROR_MESSAGE =
            "مشکلی در پردازش پیام شما پیش آمد. لطفاً دوباره تلاش کنید."
    }
}
