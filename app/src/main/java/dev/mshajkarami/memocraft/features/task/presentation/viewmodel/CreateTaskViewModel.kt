package dev.mshajkarami.memocraft.features.task.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mshajkarami.memocraft.features.task.domain.model.SubTask
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.usecase.CreateTaskUseCase
import dev.mshajkarami.memocraft.features.task.presentation.model.SubTaskUiModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase
) : ViewModel() {

    companion object {
        private const val MAX_ESTIMATED_HOURS = 999
    }

    var taskTitle by mutableStateOf("")
        private set

    var taskDescription by mutableStateOf("")
        private set

    var selectedPriority by mutableStateOf(TaskPriority.Normal)
        private set

    var estimatedDurationHoursInput by mutableStateOf("")
        private set

    var newSubTaskTitle by mutableStateOf("")
        private set

    val subTasks = mutableStateListOf<SubTaskUiModel>()

    var isSaving by mutableStateOf(false)
        private set

    var saveSuccess by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onTaskTitleChange(value: String) {
        taskTitle = value
    }

    fun onTaskDescriptionChange(value: String) {
        taskDescription = value
    }

    fun onPrioritySelected(priority: TaskPriority) {
        selectedPriority = priority
    }

    fun onEstimatedDurationHoursChange(value: String) {
        val filtered = value.filter { it.isDigit() }

        if (filtered.isEmpty()) {
            estimatedDurationHoursInput = ""
            return
        }

        val numericValue = filtered.toIntOrNull() ?: return
        if (numericValue <= MAX_ESTIMATED_HOURS) {
            estimatedDurationHoursInput = numericValue.toString()
        }
    }

    fun onNewSubTaskTitleChange(value: String) {
        newSubTaskTitle = value
    }

    fun addSubTask() {
        val trimmedTitle = newSubTaskTitle.trim()
        if (trimmedTitle.isEmpty()) return

        subTasks.add(
            SubTaskUiModel(
                title = trimmedTitle,
                isCompleted = false
            )
        )
        newSubTaskTitle = ""
    }

    fun updateSubTaskChecked(index: Int, isCompleted: Boolean) {
        val current = subTasks[index]
        subTasks[index] = current.copy(isCompleted = isCompleted)
    }

    fun removeSubTask(index: Int) {
        subTasks.removeAt(index)
    }

    fun clearError() {
        errorMessage = null
    }

    fun saveTask(
        onSaved: (() -> Unit)? = null
    ) {
        Log.d("CreateTask", "Save clicked")

        val trimmedTitle = taskTitle.trim()
        val trimmedDescription = taskDescription.trim()

        if (trimmedTitle.isEmpty()) {
            errorMessage = "Task title cannot be empty."
            Log.d("CreateTask", "Title is empty")
            return
        }

        if (trimmedDescription.isEmpty()) {
            errorMessage = "Task description cannot be empty."
            Log.d("CreateTask", "Description is empty")
            return
        }

        val estimatedHours = estimatedDurationHoursInput.toIntOrNull()

        viewModelScope.launch {
            isSaving = true
            errorMessage = null

            runCatching {
                Log.d("CreateTask", "Calling usecase")

                createTaskUseCase(
                    Task(
                        title = trimmedTitle,
                        description = trimmedDescription,
                        estimatedDurationHours = estimatedHours,
                        priority = selectedPriority,
                        subTasks = subTasks.map {
                            SubTask(
                                title = it.title,
                                isCompleted = it.isCompleted
                            )
                        }
                    )
                )
            }.onSuccess {
                Log.d("CreateTask", "Task saved successfully")
                saveSuccess = true
                onSaved?.invoke()
            }.onFailure { throwable ->
                Log.e("CreateTask", "Failed to save task", throwable)
                errorMessage = throwable.message ?: "Failed to save task."
            }

            isSaving = false
        }
    }

}
