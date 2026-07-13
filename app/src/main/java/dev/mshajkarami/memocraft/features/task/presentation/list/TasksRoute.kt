package dev.mshajkarami.memocraft.features.task.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TasksScreenRoute(
    onCreateTaskClick: () -> Unit,
    onTaskClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TasksScreen(
        uiState = uiState,
        onCreateTaskClick = onCreateTaskClick,
        onTaskClick = onTaskClick,
        onFilterSelected = viewModel::onFilterSelected,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onSearchClick = viewModel::onSearchClick,
        onSearchClose = viewModel::onSearchClose,
        onSearchSubmit = viewModel::onSearchSubmit,
        modifier = modifier,
        onCompleteTaskClick = viewModel::completeTask
    )
}
