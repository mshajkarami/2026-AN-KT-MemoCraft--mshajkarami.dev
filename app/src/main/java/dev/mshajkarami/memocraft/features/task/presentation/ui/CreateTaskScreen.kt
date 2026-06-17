package dev.mshajkarami.memocraft.features.task.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus
import dev.mshajkarami.memocraft.features.task.presentation.model.SubTaskUiModel
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel

@Composable
fun CreateTaskScreen(
    onBackClick: () -> Unit,
    taskTitle: String,
    onTaskTitleChange: (String) -> Unit,
    dueDateInput: String,
    onDueDateChange: (String) -> Unit,
    estimatedDurationHoursInput: String,
    onEstimatedDurationHoursChange: (String) -> Unit,
    selectedPriority: TaskPriority,
    onPrioritySelected: (TaskPriority) -> Unit,
    subTasks: List<SubTaskUiModel>,
    newSubTaskTitle: String,
    onNewSubTaskTitleChange: (String) -> Unit,
    onAddSubTask: () -> Unit,
    onSubTaskCheckedChange: (Int, Boolean) -> Unit,
    onRemoveSubTask: (Int) -> Unit,
    taskDescription: String,
    onTaskDescriptionChange: (String) -> Unit,
    errorMessage: String?,
    isSaving: Boolean,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    val completedSubTasksCount = subTasks.count { it.isCompleted }
    val progress = if (subTasks.isEmpty()) {
        0
    } else {
        ((completedSubTasksCount.toFloat() / subTasks.size.toFloat()) * 100f).toInt()
    }

    val isFormValid = taskTitle.isNotBlank() && taskDescription.isNotBlank()

    val livePreviewTask = TaskCardUiModel(
        title = taskTitle.ifBlank { "Task Title Preview" },
        progress = progress,
        priority = selectedPriority,
        status = TaskStatus.Pending,
        isCompleted = progress == 100 && subTasks.isNotEmpty(),
        timeLabel = listOfNotNull(
            dueDateInput.takeIf { it.isNotBlank() },
            estimatedDurationHoursInput.takeIf { it.isNotBlank() }?.let { "$it h" }
        ).joinToString(" • ").ifBlank { "Just now" },
        // در صفحه ایجاد تسک، چون هنوز ID تولید نشده، از یک مقدار ثابت یا خالی استفاده می‌کنیم
        id = "preview_id",
        // subtitle معمولاً خلاصه یا بخشی از دیسکریپشن است
        subtitle = taskDescription.take(50).ifBlank { "No description added yet" }
    )



    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colors.bottomNavContainer,
        topBar = {
            CreateTaskTopBar(onBackClick = onBackClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            PreviewSection(task = livePreviewTask)

            DividerLine()

            TaskInputContainer(label = "Task Title") {
                TransparentTextField(
                    value = taskTitle,
                    onValueChange = onTaskTitleChange,
                    placeholder = "What needs to be done?"
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DueDateSection(
                    dueDateInput = dueDateInput,
                    onDueDateChange = onDueDateChange,
                    modifier = Modifier.weight(1f)
                )

                EstimatedTimeSection(
                    estimatedDurationHoursInput = estimatedDurationHoursInput,
                    onEstimatedDurationHoursChange = onEstimatedDurationHoursChange,
                    modifier = Modifier.weight(1f)
                )
            }


            PrioritySection(
                selectedPriority = selectedPriority,
                onPrioritySelected = onPrioritySelected
            )

            ProgressSection(
                progress = progress,
                subTasks = subTasks,
                newSubTaskTitle = newSubTaskTitle,
                onNewSubTaskTitleChange = onNewSubTaskTitleChange,
                onAddSubTask = onAddSubTask,
                onSubTaskCheckedChange = onSubTaskCheckedChange,
                onRemoveSubTask = onRemoveSubTask
            )

            TaskInputContainer(label = "Description") {
                TransparentTextField(
                    value = taskDescription,
                    onValueChange = onTaskDescriptionChange,
                    placeholder = "Add more details...",
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp)
                )
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SaveTaskButton(
                enabled = isFormValid && !isSaving,
                onClick = onSaveClick
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(
    name = "Create Task Screen - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 1250
)
@Composable
private fun CreateTaskScreenLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
        CreateTaskScreenPreviewContent()
    }
}

@Preview(
    name = "Create Task Screen - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 1250
)
@Composable
private fun CreateTaskScreenDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
        CreateTaskScreenPreviewContent()
    }
}

@Composable
private fun CreateTaskScreenPreviewContent() {
    var taskTitle by remember { mutableStateOf("Design MemoCraft task flow") }
    var dueDateInput by remember { mutableStateOf("22 Feb") }
    var estimatedDurationHoursInput by remember { mutableStateOf("10") }
    var selectedPriority by remember { mutableStateOf(TaskPriority.Urgent) }
    var newSubTaskTitle by remember { mutableStateOf("") }
    var taskDescription by remember {
        mutableStateOf("Create a clean and polished task creation experience with live preview.")
    }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSaving by remember { mutableStateOf(false) }

    val subTasks = remember {
        mutableStateListOf(
            SubTaskUiModel(title = "Write UI structure", isCompleted = true),
            SubTaskUiModel(title = "Connect ViewModel", isCompleted = false),
            SubTaskUiModel(title = "Polish preview", isCompleted = false)
        )
    }

    CreateTaskScreen(
        onBackClick = {},
        taskTitle = taskTitle,
        onTaskTitleChange = { taskTitle = it },
        dueDateInput = dueDateInput,
        onDueDateChange = { dueDateInput = it },
        estimatedDurationHoursInput = estimatedDurationHoursInput,
        onEstimatedDurationHoursChange = { estimatedDurationHoursInput = it },
        selectedPriority = selectedPriority,
        onPrioritySelected = { selectedPriority = it },
        subTasks = subTasks,
        newSubTaskTitle = newSubTaskTitle,
        onNewSubTaskTitleChange = { newSubTaskTitle = it },
        onAddSubTask = {
            val trimmedTitle = newSubTaskTitle.trim()

            if (trimmedTitle.isNotEmpty()) {
                subTasks.add(
                    SubTaskUiModel(
                        title = trimmedTitle,
                        isCompleted = false
                    )
                )
                newSubTaskTitle = ""
            }
        },
        onSubTaskCheckedChange = { index, checked ->
            subTasks.getOrNull(index)?.let { subTask ->
                subTasks[index] = subTask.copy(isCompleted = checked)
            }
        },
        onRemoveSubTask = { index ->
            if (index in subTasks.indices) {
                subTasks.removeAt(index)
            }
        },
        taskDescription = taskDescription,
        onTaskDescriptionChange = { taskDescription = it },
        errorMessage = errorMessage,
        isSaving = isSaving,
        onSaveClick = {
            isSaving = true
            errorMessage = null
        }
    )
}
