package dev.mshajkarami.memocraft.features.task.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dev.mshajkarami.memocraft.features.task.presentation.ui.CreateTaskScreen
import dev.mshajkarami.memocraft.features.task.presentation.viewmodel.CreateTaskViewModel

@Composable
fun CreateTaskScreenRoute(
    onBackClick: () -> Unit,
    onTaskSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateTaskViewModel = hiltViewModel()
) {
    CreateTaskScreen(
        onBackClick = onBackClick,
        taskTitle = viewModel.taskTitle,
        onTaskTitleChange = viewModel::onTaskTitleChange,
        dueDateInput = viewModel.dueDateInput,
        onDueDateChange = viewModel::onDueDateChange,
        dueTimeInput = viewModel.dueTimeInput,
        onDueTimeChange = viewModel::onDueTimeChange,
        selectedPriority = viewModel.selectedPriority,
        onPrioritySelected = viewModel::onPrioritySelected,
        subTasks = viewModel.subTasks,
        newSubTaskTitle = viewModel.newSubTaskTitle,
        onNewSubTaskTitleChange = viewModel::onNewSubTaskTitleChange,
        onAddSubTask = viewModel::addSubTask,
        onSubTaskCheckedChange = viewModel::updateSubTaskChecked,
        onRemoveSubTask = viewModel::removeSubTask,
        taskDescription = viewModel.taskDescription,
        onTaskDescriptionChange = viewModel::onTaskDescriptionChange,
        errorMessage = viewModel.errorMessage,
        isSaving = viewModel.isSaving,
        onSaveClick = {
            viewModel.saveTask(onSaved = onTaskSaved)
        },
        modifier = modifier
    )
}
