package dev.mshajkarami.memocraft.features.tasks.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.components.SectionHeader
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus
import dev.mshajkarami.memocraft.features.task.presentation.component.card.CompactDashboardTaskCard
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.components.EmptyTasksState
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.components.TaskFilterChips
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.components.TaskSummaryCard
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.components.TasksTopBar
import dev.mshajkarami.memocraft.features.tasks.presentation.viewmodel.TasksUiState


@Composable
fun TasksScreen(
    uiState: TasksUiState,
    onCreateTaskClick: () -> Unit,
    onTaskClick: (String) -> Unit,
    onFilterSelected: (TaskFilter) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onSearchClose: () -> Unit,
    onSearchSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    Scaffold(
        modifier = modifier,
        containerColor = colors.bottomNavContainer,
        topBar = {
            TasksTopBar(
                searchQuery = uiState.searchQuery,
                isSearchActive = uiState.isSearchActive,
                onSearchQueryChange = onSearchQueryChange,
                onSearchClick = onSearchClick,
                onSearchClose = onSearchClose,
                onSearchSubmit = onSearchSubmit
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateTaskClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add task",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        TasksScreenContent(
            tasks = uiState.filteredTasks,
            allTasks = uiState.allTasks,
            selectedFilter = uiState.selectedFilter,
            searchQuery = uiState.searchQuery,
            isSearchActive = uiState.isSearchActive,
            isLoading = uiState.isLoading,
            errorMessage = uiState.errorMessage,
            onFilterSelected = onFilterSelected,
            onTaskClick = onTaskClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
private fun TasksScreenContent(
    tasks: List<TaskCardUiModel>,
    allTasks: List<TaskCardUiModel>,
    selectedFilter: TaskFilter,
    searchQuery: String,
    isSearchActive: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    onFilterSelected: (TaskFilter) -> Unit,
    onTaskClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 18.dp,
            bottom = 100.dp
        ),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            TaskSummaryCard(
                totalTasks = allTasks.size,
                completedTasks = allTasks.count { it.status == TaskStatus.Completed },
                inProgressTasks = allTasks.count { it.status == TaskStatus.InProgress },
                pendingTasks = allTasks.count { it.status == TaskStatus.Pending }
            )
        }

        item {
            TaskFilterChips(
                selectedFilter = selectedFilter,
                onFilterSelected = onFilterSelected
            )
        }

        item {
            SectionHeader(
                title = if (searchQuery.isBlank()) {
                    "Your Tasks"
                } else {
                    "Search Results"
                },
                action = "${tasks.size} items"
            )
        }

        when {
            isLoading -> {
                item {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                item {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            tasks.isEmpty() && isSearchActive && searchQuery.isNotBlank() -> {
                item {
                    EmptySearchResultState(
                        query = searchQuery
                    )
                }
            }

            tasks.isEmpty() -> {
                item {
                    EmptyTasksState()
                }
            }

            else -> {
                itemsIndexed(
                    items = tasks,
                    key = { index, task ->
                        "${task.title}_${task.subtitle}_${task.status}_${task.priority}_${task.priority}_$index"
                    }
                ) { _, task ->
                    CompactDashboardTaskCard(
                        task = task,
                        onTaskClick = onTaskClick,
                        onConfirmClick = {}
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptySearchResultState(
    query: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "No tasks found for \"$query\"",
        modifier = modifier,
        color =  MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodyMedium
    )
}

enum class TaskFilter {
    All,
    InProgress,
    Pending,
    Completed
}