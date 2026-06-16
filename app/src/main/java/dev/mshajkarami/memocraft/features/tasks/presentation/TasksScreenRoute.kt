package dev.mshajkarami.memocraft.features.tasks.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.TasksScreen
import dev.mshajkarami.memocraft.features.tasks.presentation.viewmodel.TasksViewModel

@Composable
fun TasksScreenRoute(
    onCreateTaskClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    TasksScreen(
        uiState = uiState.value,
        onCreateTaskClick = onCreateTaskClick,
        onFilterSelected = viewModel::onFilterSelected,
        modifier = modifier
    )
}