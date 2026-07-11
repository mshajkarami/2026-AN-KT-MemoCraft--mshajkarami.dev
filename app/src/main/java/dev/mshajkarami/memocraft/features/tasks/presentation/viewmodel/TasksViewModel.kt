package dev.mshajkarami.memocraft.features.tasks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus
import dev.mshajkarami.memocraft.features.task.domain.usecase.ObserveTasksUseCase
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.TaskFilter
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
    private val observeTasksUseCase: ObserveTasksUseCase
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
                        .sortedByDescending { it.createdAt }
                        .map { it.toTaskCardUiModel() }

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
                TaskFilter.InProgress -> allTasks.filter { it.status == TaskStatus.InProgress }
                TaskFilter.Pending -> allTasks.filter { it.status == TaskStatus.Pending }
                TaskFilter.Completed -> allTasks.filter { it.status == TaskStatus.Completed }
            }

            val normalizedQuery = searchQuery.trim()

            if (normalizedQuery.isBlank()) {
                return filterAppliedTasks
            }

            return filterAppliedTasks.filter { task ->
                task.title.contains(normalizedQuery, ignoreCase = true) ||
                        task.subtitle.orEmpty().contains(normalizedQuery, ignoreCase = true) ||
                        task.status.name.contains(normalizedQuery, ignoreCase = true) ||
                        task.priority.name.contains(normalizedQuery, ignoreCase = true) ||
                        task.timeLabel.orEmpty().contains(normalizedQuery, ignoreCase = true)
            }
        }
}

private val taskDateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)

private fun Task.toTaskCardUiModel(): TaskCardUiModel {
    val completedSubTasks = subTasks.count { it.isCompleted }

    val progress = when {
        subTasks.isNotEmpty() -> ((completedSubTasks * 100f) / subTasks.size).toInt()
        status == TaskStatus.Completed -> 100
        status == TaskStatus.InProgress -> 50
        else -> 0
    }.coerceIn(0, 100)

    val timeLabel = listOfNotNull(
        dueDate?.format(taskDateFormatter),
        estimatedDurationHours?.let { "$it h" }
    ).joinToString(" • ").ifBlank { "Just now" }

    return TaskCardUiModel(
        id = id,
        title = title,
        description = description,
        subtitle = description,
        progress = progress,
        priority = priority,
        status = status,
        isCompleted = status == TaskStatus.Completed,
        timeLabel = timeLabel
    )
}
