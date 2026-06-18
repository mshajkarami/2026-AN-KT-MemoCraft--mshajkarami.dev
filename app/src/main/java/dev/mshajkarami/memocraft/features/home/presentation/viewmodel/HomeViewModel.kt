package dev.mshajkarami.memocraft.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus
import dev.mshajkarami.memocraft.features.task.domain.usecase.ObserveTasksUseCase
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeTasksUseCase: ObserveTasksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeTasks()
    }

    fun onSearchClick() {
        _uiState.value = _uiState.value.copy(
            isSearchActive = true
        )
    }

    fun onSearchClose() {
        _uiState.value = _uiState.value.copy(
            searchQuery = "",
            isSearchActive = false
        )
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = query
        )
    }

    fun onSearchSubmit() {
        val query = _uiState.value.searchQuery.trim()

        if (query.isBlank()) return

        // فعلاً فقط query را نگه می‌داریم.
        // بعداً اینجا می‌توانی سرچ واقعی، navigation یا analytics اضافه کنی.
    }

    private fun observeTasks() {
        viewModelScope.launch {
            observeTasksUseCase()
                .catch { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Failed to load home data."
                    )
                }
                .collectLatest { tasks ->
                    val sortedTasks = tasks.sortedByDescending { it.createdAt }
                    val latestThreeTasks = sortedTasks.take(3)
                    val upcomingTaskItems = latestThreeTasks.map { it.toTaskCardUiModel() }

                    val totalTasks = tasks.size
                    val completedTasks = tasks.count { it.status == TaskStatus.Completed }
                    val pendingTasks = tasks.count { it.status == TaskStatus.Pending }
                    val inProgressTasks = tasks.count { it.status == TaskStatus.InProgress }

                    val overallProgress = calculateOverallProgress(tasks)

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        upcomingTasks = upcomingTaskItems,
                        totalTasks = totalTasks,
                        completedTasks = completedTasks,
                        pendingTasks = pendingTasks,
                        inProgressTasks = inProgressTasks,
                        overallProgress = overallProgress,
                        focusTimeText = calculateFocusTime(tasks),
                        errorMessage = null
                    )
                }
        }
    }
    private fun calculateOverallProgress(tasks: List<Task>): Int {
        if (tasks.isEmpty()) return 0

        val progressValues = tasks.map { task ->
            val completedSubTasks = task.subTasks.count { it.isCompleted }
            when {
                task.subTasks.isNotEmpty() -> ((completedSubTasks * 100f) / task.subTasks.size).toInt()
                task.status == TaskStatus.Completed -> 100
                task.status == TaskStatus.InProgress -> 50
                else -> 0
            }.coerceIn(0, 100)
        }

        return progressValues.average().toInt()
    }}

private val taskDateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)

private fun calculateFocusTime(tasks: List<Task>): String {
    val totalHours = tasks.sumOf { it.estimatedDurationHours ?: 0 }
    val hours = totalHours
    val minutes = 0
    return "${hours}h ${minutes}m"
}

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
    ).joinToString(" • ").takeIf { it.isNotBlank() }

    return TaskCardUiModel(
        id = this.id, // اضافه کردن فیلد ID
        title = title,
        subtitle = description,
        progress = progress,
        priority = priority,
        status = status,
        isCompleted = status == TaskStatus.Completed,
        timeLabel = timeLabel
    )
}
