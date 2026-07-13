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
import dev.mshajkarami.memocraft.app.navigation.EditTaskDestination
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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

        private const val ERROR_TASK_TITLE_EMPTY = "Task title cannot be empty."
        private const val ERROR_DUE_DATE_EMPTY = "Task due date cannot be empty."
        private const val ERROR_DUE_DATE_FORMAT = "Due date format must be yyyy-MM-dd."
        private const val ERROR_DUE_TIME_EMPTY = "Task due time cannot be empty."
        private const val ERROR_DUE_TIME_FORMAT = "Due time format must be HH:mm."
        private const val ERROR_TASK_NOT_FOUND = "Task not found."
        private const val ERROR_SAVE_FAILED = "Failed to save task."
        private const val ERROR_LOAD_FAILED = "Failed to load task."

        private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        private val TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    }

    private val editingTaskId: String? =
        savedStateHandle[EditTaskDestination.taskIdArg]

    val isEditMode: Boolean = editingTaskId != null

    private var loadedTask: Task? = null

    var dueDateInput by mutableStateOf("")
        private set

    var dueTimeInput by mutableStateOf("")
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

    fun onDueDateChange(value: String) {
        dueDateInput = value
    }

    fun onDueTimeChange(value: String) {
        /*
         * ورودی مجاز برای ساعت:
         * 09:30
         * 23:59
         *
         * این کنترل سبک است و اعتبارسنجی اصلی در validateForm انجام می‌شود.
         */
        val filtered = value.filter { character ->
            character.isDigit() || character == ':'
        }

        if (filtered.length <= 5) {
            dueTimeInput = filtered
        }
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
                errorMessage = throwable.message ?: ERROR_SAVE_FAILED
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
                    errorMessage = ERROR_TASK_NOT_FOUND
                    return@onSuccess
                }

                loadedTask = task
                fillForm(task)
            }.onFailure { throwable ->
                Log.e(TAG, "Failed to load task", throwable)
                errorMessage = throwable.message ?: ERROR_LOAD_FAILED
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
                dueAt = formData.dueAt,
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
            ?: throw IllegalStateException(ERROR_TASK_NOT_FOUND)

        updateTaskUseCase(
            currentTask.copy(
                title = formData.title,
                description = formData.description,
                estimatedDurationHours = formData.estimatedDurationHours,
                dueAt = formData.dueAt,
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

        dueDateInput = task.dueAt
            ?.toLocalDate()
            ?.format(DATE_FORMATTER)
            .orEmpty()

        dueTimeInput = task.dueAt
            ?.toLocalTime()
            ?.format(TIME_FORMATTER)
            .orEmpty()

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
        val trimmedDueTime = dueTimeInput.trim()

        if (trimmedTitle.isEmpty()) {
            errorMessage = ERROR_TASK_TITLE_EMPTY
            Log.d(TAG, "Title is empty")
            return null
        }

        if (trimmedDueDate.isEmpty()) {
            errorMessage = ERROR_DUE_DATE_EMPTY
            Log.d(TAG, "Due date is empty")
            return null
        }

        if (trimmedDueTime.isEmpty()) {
            errorMessage = ERROR_DUE_TIME_EMPTY
            Log.d(TAG, "Due time is empty")
            return null
        }

        val dueDate = try {
            LocalDate.parse(trimmedDueDate, DATE_FORMATTER)
        } catch (exception: DateTimeParseException) {
            errorMessage = ERROR_DUE_DATE_FORMAT
            Log.d(TAG, "Invalid due date format", exception)
            return null
        }

        val dueTime = try {
            LocalTime.parse(trimmedDueTime, TIME_FORMATTER)
        } catch (exception: DateTimeParseException) {
            errorMessage = ERROR_DUE_TIME_FORMAT
            Log.d(TAG, "Invalid due time format", exception)
            return null
        }

        val dueAt = LocalDateTime.of(dueDate, dueTime)

        return TaskFormData(
            title = trimmedTitle,
            description = trimmedDescription.ifBlank { null },
            dueAt = dueAt,
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
        val description: String?,
        val dueAt: LocalDateTime,
        val estimatedDurationHours: Int?,
        val priority: TaskPriority,
        val subTasks: List<SubTask>
    )
}
