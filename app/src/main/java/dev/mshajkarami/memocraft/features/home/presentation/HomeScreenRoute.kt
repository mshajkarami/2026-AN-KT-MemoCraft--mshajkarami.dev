package dev.mshajkarami.memocraft.features.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.mshajkarami.memocraft.features.home.presentation.ui.HomeScreen
import dev.mshajkarami.memocraft.features.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onSeeAllTasksClick: () -> Unit,
    onTaskClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onSeeAllTasksClick = onSeeAllTasksClick,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onSearchClick = viewModel::onSearchClick,
        onSearchClose = viewModel::onSearchClose,
        onSearchSubmit = viewModel::onSearchSubmit,
        modifier = modifier,
        onTaskClick = onTaskClick
    )
}
