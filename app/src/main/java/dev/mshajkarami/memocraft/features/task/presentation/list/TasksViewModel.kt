package dev.mshajkarami.memocraft.features.task.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus
import dev.mshajkarami.memocraft.features.task.domain.usecase.CompleteTaskUseCase
import dev.mshajkarami.memocraft.features.task.domain.usecase.ObserveTasksUseCase
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState(isLoading = true))
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {
        observeTasks()
    }

    fun onFilterSelected(filter: TaskFilter) {
        _uiState.update { state ->
            state.copy(selectedFilter = filter)
        }
    }

    fun onSearchClick() {
        _uiState.update { state ->
            state.copy(isSearchActive = true)
        }
    }

    fun onSearchClose() {
        _uiState.update { state ->
            state.copy(
                searchQuery = "",
                isSearchActive = false
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { state ->
            state.copy(searchQuery = query)
        }
    }

    fun onSearchSubmit() {
        val query = _uiState.value.searchQuery.trim()

        if (query.isBlank()) return

    }

    fun completeTask(taskId: String) {
        viewModelScope.launch {
            runCatching {
                completeTaskUseCase(taskId)
            }.onFailure { throwable ->
                _uiState.update { state ->
                    state.copy(
                        errorMessage = throwable.message
                            ?: "Failed to complete the task."
                    )
                }
            }
        }
    }



    private fun observeTasks() {
        viewModelScope.launch {
            observeTasksUseCase()
                .catch { throwable ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Failed to load tasks."
                        )
                    }
                }
                .collectLatest { tasks ->
                    val taskItems = tasks
                        .sortedWith(
                            compareByDescending<Task> { it.dueAt }
                                .thenByDescending { it.createdAt }
                        )
                        .map { task ->
                            task.toTaskCardUiModel()
                        }

                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            allTasks = taskItems,
                            errorMessage = null
                        )
                    }
                }
        }
    }
}

data class TasksUiState(
    val isLoading: Boolean = false,
    val allTasks: List<TaskCardUiModel> = emptyList(),
    val selectedFilter: TaskFilter = TaskFilter.All,
    val errorMessage: String? = null,

    val searchQuery: String = "",
    val isSearchActive: Boolean = false
) {
    val filteredTasks: List<TaskCardUiModel>
        get() {
            val filterAppliedTasks = when (selectedFilter) {
                TaskFilter.All -> allTasks
                TaskFilter.InProgress -> allTasks.filter { task ->
                    task.status == TaskStatus.InProgress
                }

                TaskFilter.Pending -> allTasks.filter { task ->
                    task.status == TaskStatus.Pending
                }

                TaskFilter.Completed -> allTasks.filter { task ->
                    task.status == TaskStatus.Completed
                }
            }

            val normalizedQuery = searchQuery.trim()

            if (normalizedQuery.isBlank()) {
                return filterAppliedTasks
            }

            return filterAppliedTasks.filter { task ->
                task.title.contains(normalizedQuery, ignoreCase = true) ||
                        task.description.orEmpty().contains(normalizedQuery, ignoreCase = true) ||
                        task.subtitle.orEmpty().contains(normalizedQuery, ignoreCase = true) ||
                        task.status.name.contains(normalizedQuery, ignoreCase = true) ||
                        task.priority.name.contains(normalizedQuery, ignoreCase = true) ||
                        task.dateLabel.orEmpty().contains(normalizedQuery, ignoreCase = true) ||
                        task.timeLabel.orEmpty().contains(normalizedQuery, ignoreCase = true) ||
                        task.createdAtLabel.orEmpty().contains(normalizedQuery, ignoreCase = true)
            }
        }
}

private val taskDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)

private val taskTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)

private fun Task.toTaskCardUiModel(): TaskCardUiModel {
    val completedSubTasks = subTasks.count { subTask ->
        subTask.isCompleted
    }

    val progress = when {
        subTasks.isNotEmpty() -> {
            ((completedSubTasks * 100f) / subTasks.size).toInt()
        }

        status == TaskStatus.Completed -> 100

        status == TaskStatus.InProgress -> 50

        else -> 0
    }.coerceIn(0, 100)

    val dateLabel = dueAt
        ?.toLocalDate()
        ?.format(taskDateFormatter)

    val timeLabel = dueAt
        ?.toLocalTime()
        ?.format(taskTimeFormatter)

    val createdAtLabel = createdAt
        ?.toLocalDate()
        ?.format(taskDateFormatter)

    return TaskCardUiModel(
        id = id,
        title = title,
        description = description,
        subtitle = buildTaskSubtitle(
            description = description,
            estimatedDurationHours = estimatedDurationHours
        ),
        progress = progress,
        priority = priority,
        status = status,
        isCompleted = status == TaskStatus.Completed,
        dateLabel = dateLabel,
        timeLabel = timeLabel,
        createdAtLabel = createdAtLabel
    )
}

private fun buildTaskSubtitle(
    description: String?,
    estimatedDurationHours: Int?
): String? {
    val durationLabel = estimatedDurationHours?.let { hours ->
        "$hours h"
    }

    return listOfNotNull(
        description?.takeIf { it.isNotBlank() },
        durationLabel
    )
        .joinToString(separator = " • ")
        .takeIf { it.isNotBlank() }
}
