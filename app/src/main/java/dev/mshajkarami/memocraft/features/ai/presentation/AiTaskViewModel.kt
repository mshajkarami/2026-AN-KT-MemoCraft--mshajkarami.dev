package dev.mshajkarami.memocraft.features.ai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mshajkarami.memocraft.features.ai.domain.model.AiChatResult
import dev.mshajkarami.memocraft.features.ai.domain.repository.AiTaskRepository
import dev.mshajkarami.memocraft.features.ai.presentation.mapper.toAiChatMessageUiModel
import dev.mshajkarami.memocraft.features.ai.presentation.ui.AiChatMessageUiModel
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.repository.TaskRepository
import kotlinx.coroutines.CancellationException
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

                saveDetectedTasks(result)

                val aiMessage = result.toAiChatMessageUiModel()

                _uiState.update { currentState ->
                    currentState.copy(
                        messages = currentState.messages + aiMessage,
                        isLoading = false,
                        isGeneratingResponse = false,
                        errorMessage = null
                    )
                }
            } catch (exception: CancellationException) {
                // لغو Coroutine نباید به‌عنوان خطای معمولی نمایش داده شود.
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
            detectedTasks = emptyList()
        )
    }

    private fun handleSendMessageFailure(throwable: Throwable) {
        val fallbackMessage = throwable.message
            ?.takeIf { message -> message.isNotBlank() }
            ?: DEFAULT_ERROR_MESSAGE

        val errorMessage = AiChatMessageUiModel(
            id = UUID.randomUUID().toString(),
            content = fallbackMessage,
            isFromUser = false,
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

    private suspend fun getExistingTasks(): List<Task> {
        return taskRepository
            .getAllTasks()
            .first()
    }

    private suspend fun saveDetectedTasks(result: AiChatResult) {
        result.detectedTasks.forEach { task ->
            taskRepository.createTask(task)
        }
    }

    private companion object {
        const val DEFAULT_ERROR_MESSAGE =
            "مشکلی در پردازش پیام شما پیش آمد. لطفاً دوباره تلاش کنید."
    }
}
