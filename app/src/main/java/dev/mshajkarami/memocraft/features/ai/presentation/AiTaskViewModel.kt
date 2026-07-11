package dev.mshajkarami.memocraft.features.ai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mshajkarami.memocraft.features.ai.domain.repository.AiTaskRepository
import dev.mshajkarami.memocraft.features.ai.presentation.ui.AiChatMessageUiModel
import dev.mshajkarami.memocraft.features.ai.presentation.ui.DetectedTaskPriority
import dev.mshajkarami.memocraft.features.ai.presentation.ui.DetectedTaskUiModel
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

        _uiState.update { currentState ->
            currentState.copy(
                messages = currentState.messages + AiChatMessageUiModel(
                    content = prompt,
                    isFromUser = true
                ),
                isLoading = true,
                errorMessage = null
            )
        }

        generateAndSaveTasks(prompt)
    }

    private fun generateAndSaveTasks(prompt: String) {
        viewModelScope.launch {
            runCatching {
                val tasks = aiTaskRepository.generateTasksFromPrompt(prompt)

                tasks.forEach { task ->
                    taskRepository.createTask(task)
                }

                tasks
            }.onSuccess { tasks ->
                val detectedTasks = tasks.map { task ->
                    task.toDetectedTaskUiModel()
                }

                val responseText = when {
                    tasks.isEmpty() ->
                        "I couldn’t detect a task in your message."

                    tasks.size == 1 ->
                        "I found and saved one task."

                    else ->
                        "I found and saved ${tasks.size} tasks."
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = null,
                        messages = currentState.messages + AiChatMessageUiModel(
                            content = responseText,
                            isFromUser = false,
                            detectedTasks = detectedTasks
                        )
                    )
                }
            }.onFailure { throwable ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = throwable.message,
                        messages = currentState.messages + AiChatMessageUiModel(
                            content = throwable.message
                                ?: "Something went wrong while processing your message.",
                            isFromUser = false
                        )
                    )
                }
            }
        }
    }

    private fun addMessage(message: AiChatMessageUiModel) {
        _uiState.update {
            it.copy(messages = it.messages + message)
        }
    }
}

private fun Task.toDetectedTaskUiModel(): DetectedTaskUiModel {
    return DetectedTaskUiModel(
        id = id.toString(),
        title = title,
        dueText = dueDate?.toString() ?: "No date",
        priority = priority.toDetectedTaskPriority()
    )
}

private fun TaskPriority.toDetectedTaskPriority(): DetectedTaskPriority {
    return when (this) {
        TaskPriority.Low -> DetectedTaskPriority.Low
        TaskPriority.Normal -> DetectedTaskPriority.Normal
        TaskPriority.Urgent -> DetectedTaskPriority.Urgent
    }
}
