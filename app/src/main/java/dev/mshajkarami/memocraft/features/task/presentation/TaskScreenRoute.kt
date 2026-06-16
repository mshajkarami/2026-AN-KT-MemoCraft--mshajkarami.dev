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
        modifier = modifier,
        taskTitle = viewModel.taskTitle,
        onTaskTitleChange = viewModel::onTaskTitleChange,
        dueDateInput = viewModel.dueDateInput,
        onDueDateChange = viewModel::onDueDateChange,
        estimatedDurationHoursInput = viewModel.estimatedDurationHoursInput,
        onEstimatedDurationHoursChange = viewModel::onEstimatedDurationHoursChange,
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
        }
    )

}