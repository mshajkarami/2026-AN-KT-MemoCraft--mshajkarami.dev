package dev.mshajkarami.memocraft.features.task.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mshajkarami.memocraft.features.task.domain.model.SubTask
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.usecase.CreateTaskUseCase
import dev.mshajkarami.memocraft.features.task.domain.usecase.GetTaskByIdUseCase
import dev.mshajkarami.memocraft.features.task.domain.usecase.UpdateTaskUseCase
import dev.mshajkarami.memocraft.features.task.presentation.model.SubTaskUiModel
import dev.mshajkarami.memocraft.navigation.EditTaskDestination
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createTaskUseCase: CreateTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "CreateTask"
        private const val MAX_ESTIMATED_HOURS = 999
    }

    private val editingTaskId: String? =
        savedStateHandle[EditTaskDestination.taskIdArg]

    val isEditMode: Boolean = editingTaskId != null

    private var loadedTask: Task? = null

    var dueDateInput by mutableStateOf("")
        private set

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

    var isLoading by mutableStateOf(false)
        private set

    var isSaving by mutableStateOf(false)
        private set

    var saveSuccess by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        editingTaskId?.let { taskId ->
            loadTask(taskId)
        }
    }

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

    fun onDueDateChange(value: String) {
        dueDateInput = value
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
        if (index !in subTasks.indices) return

        val current = subTasks[index]
        subTasks[index] = current.copy(isCompleted = isCompleted)
    }

    fun removeSubTask(index: Int) {
        if (index !in subTasks.indices) return

        subTasks.removeAt(index)
    }

    fun clearError() {
        errorMessage = null
    }

    fun saveTask(onSaved: (() -> Unit)? = null) {
        Log.d(TAG, "Save clicked")

        val formData = validateForm() ?: return

        viewModelScope.launch {
            isSaving = true
            errorMessage = null

            runCatching {
                if (isEditMode) {
                    updateTask(formData)
                } else {
                    createTask(formData)
                }
            }.onSuccess {
                Log.d(TAG, "Task saved successfully")
                saveSuccess = true
                onSaved?.invoke()
            }.onFailure { throwable ->
                Log.e(TAG, "Failed to save task", throwable)
                errorMessage = throwable.message ?: "Failed to save task."
            }

            isSaving = false
        }
    }

    private fun loadTask(taskId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            runCatching {
                getTaskByIdUseCase(taskId)
            }.onSuccess { task ->
                if (task == null) {
                    errorMessage = "Task not found."
                    return@onSuccess
                }

                loadedTask = task
                fillForm(task)
            }.onFailure { throwable ->
                Log.e(TAG, "Failed to load task", throwable)
                errorMessage = throwable.message ?: "Failed to load task."
            }

            isLoading = false
        }
    }

    private suspend fun createTask(formData: TaskFormData) {
        Log.d(TAG, "Creating task")

        createTaskUseCase(
            Task(
                title = formData.title,
                description = formData.description,
                estimatedDurationHours = formData.estimatedDurationHours,
                dueDate = formData.dueDate,
                priority = formData.priority,
                subTasks = formData.subTasks
            )
        )
    }

    private suspend fun updateTask(formData: TaskFormData) {
        Log.d(TAG, "Updating task")

        val taskId = editingTaskId
            ?: throw IllegalStateException("Task id is required for edit mode.")

        val currentTask = loadedTask
            ?: getTaskByIdUseCase(taskId)
            ?: throw IllegalStateException("Task not found.")

        updateTaskUseCase(
            currentTask.copy(
                title = formData.title,
                description = formData.description,
                estimatedDurationHours = formData.estimatedDurationHours,
                dueDate = formData.dueDate,
                priority = formData.priority,
                subTasks = formData.subTasks
            )
        )
    }

    private fun fillForm(task: Task) {
        taskTitle = task.title
        taskDescription = task.description.orEmpty()
        selectedPriority = task.priority
        estimatedDurationHoursInput = task.estimatedDurationHours?.toString().orEmpty()
        dueDateInput = task.dueDate?.format(DateTimeFormatter.ISO_LOCAL_DATE).orEmpty()

        subTasks.clear()
        subTasks.addAll(
            task.subTasks.map { subTask ->
                SubTaskUiModel(
                    title = subTask.title,
                    isCompleted = subTask.isCompleted
                )
            }
        )
    }

    private fun validateForm(): TaskFormData? {
        val trimmedTitle = taskTitle.trim()
        val trimmedDescription = taskDescription.trim()
        val trimmedDueDate = dueDateInput.trim()

        if (trimmedTitle.isEmpty()) {
            errorMessage = "Task title cannot be empty."
            Log.d(TAG, "Title is empty")
            return null
        }

        if (trimmedDescription.isEmpty()) {
            errorMessage = "Task description cannot be empty."
            Log.d(TAG, "Description is empty")
            return null
        }

        if (trimmedDueDate.isEmpty()) {
            errorMessage = "Task DueDate cannot be empty."
            Log.d(TAG, "Due date is empty")
            return null
        }

        val dueDate = try {
            LocalDate.parse(trimmedDueDate)
        } catch (exception: DateTimeParseException) {
            errorMessage = "Due date format must be yyyy-MM-dd."
            return null
        }

        return TaskFormData(
            title = trimmedTitle,
            description = trimmedDescription,
            dueDate = dueDate,
            estimatedDurationHours = estimatedDurationHoursInput.toIntOrNull(),
            priority = selectedPriority,
            subTasks = subTasks.map { subTask ->
                SubTask(
                    title = subTask.title,
                    isCompleted = subTask.isCompleted
                )
            }
        )
    }

    private data class TaskFormData(
        val title: String,
        val description: String,
        val dueDate: LocalDate,
        val estimatedDurationHours: Int?,
        val priority: TaskPriority,
        val subTasks: List<SubTask>
    )
}
