package dev.mshajkarami.memocraft.features.task.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import dev.mshajkarami.memocraft.core.designsystem.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus
import dev.mshajkarami.memocraft.features.task.presentation.component.card.CompactDashboardTaskCard
import dev.mshajkarami.memocraft.features.task.presentation.list.component.EmptyTasksState
import dev.mshajkarami.memocraft.features.task.presentation.list.component.SectionHeader
import dev.mshajkarami.memocraft.features.task.presentation.list.component.TaskFilterChips
import dev.mshajkarami.memocraft.features.task.presentation.list.component.TaskSummaryCard
import dev.mshajkarami.memocraft.features.task.presentation.list.component.TasksTopBar
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel

@Composable
fun TasksScreen(
    uiState: TasksUiState,
    onCreateTaskClick: () -> Unit,
    onTaskClick: (String) -> Unit,
    onCompleteTaskClick: (String) -> Unit,
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
            onCompleteTaskClick = onCompleteTaskClick,
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
    onCompleteTaskClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeTasks = tasks.filterNot { task ->
        task.status == TaskStatus.Completed
    }

    val completedTasks = tasks.filter { task ->
        task.status == TaskStatus.Completed
    }

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
                if (selectedFilter == TaskFilter.Completed) {
                    completedTasksSection(
                        completedTasks = completedTasks,
                        onTaskClick = onTaskClick,
                        onCompleteTaskClick = onCompleteTaskClick
                    )
                } else {
                    activeTasksSection(
                        title = if (searchQuery.isBlank()) {
                            "Your Tasks"
                        } else {
                            "Search Results"
                        },
                        tasks = activeTasks,
                        onTaskClick = onTaskClick,
                        onCompleteTaskClick = onCompleteTaskClick
                    )

                    if (completedTasks.isNotEmpty()) {
                        completedTasksSection(
                            completedTasks = completedTasks,
                            onTaskClick = onTaskClick,
                            onCompleteTaskClick = onCompleteTaskClick
                        )
                    }
                }
            }
        }
    }
}

private fun androidx.compose.foundation.lazy.LazyListScope.activeTasksSection(
    title: String,
    tasks: List<TaskCardUiModel>,
    onTaskClick: (String) -> Unit,
    onCompleteTaskClick: (String) -> Unit
) {
    item {
        SectionHeader(
            title = title,
            action = "${tasks.size} items"
        )
    }

    if (tasks.isEmpty()) {
        item {
            EmptyTasksState()
        }
    } else {
        items(
            items = tasks,
            key = { task -> task.id }
        ) { task ->
            CompactDashboardTaskCard(
                task = task,
                onTaskClick = onTaskClick,
                onConfirmClick = {
                    onCompleteTaskClick(task.id)
                }
            )
        }
    }
}

private fun androidx.compose.foundation.lazy.LazyListScope.completedTasksSection(
    completedTasks: List<TaskCardUiModel>,
    onTaskClick: (String) -> Unit,
    onCompleteTaskClick: (String) -> Unit
) {
    item {
        SectionHeader(
            title = "Completed",
            action = completedTasks.size.toString()
        )
    }

    items(
        items = completedTasks,
        key = { task -> task.id }
    ) { task ->
        CompactDashboardTaskCard(
            task = task,
            onTaskClick = onTaskClick,
            onConfirmClick = {
                onCompleteTaskClick(task.id)
            }
        )
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
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodyMedium
    )
}

enum class TaskFilter {
    All,
    InProgress,
    Pending,
    Completed
}
