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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.home.presentation.ui.components.SectionHeader
import dev.mshajkarami.memocraft.features.task.presentation.component.card.CompactDashboardTaskCard
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskStatus
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.components.EmptyTasksState
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.components.TaskFilterChips
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.components.TaskSummaryCard
import dev.mshajkarami.memocraft.features.tasks.presentation.ui.components.TasksTopBar
import dev.mshajkarami.memocraft.navigation.BottomNavBar
import dev.mshajkarami.memocraft.navigation.BottomNavItem

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    onNavigateToTasks: () -> Unit = {},
    onNavigateToAi: () -> Unit = {},
    onNavigateToPlanner: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val colors = MemoCraftTheme.colors
    val tasks = remember { taskMockData() }

    var selectedFilter by remember { mutableStateOf(TaskFilter.All) }

    val filteredTasks = remember(tasks, selectedFilter) {
        when (selectedFilter) {
            TaskFilter.All -> tasks
            TaskFilter.InProgress -> tasks.filter { it.status == TaskStatus.InProgress }
            TaskFilter.Pending -> tasks.filter { it.status == TaskStatus.Pending }
            TaskFilter.Completed -> tasks.filter { it.status == TaskStatus.Completed }
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = colors.bottomNavContainer,
        topBar = {
            TasksTopBar(
                onSearchClick = {}
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedItem = BottomNavItem.Tasks,
                onHomeClick = onNavigateToHome,
                onTasksClick = onNavigateToTasks,
                onAiClick = onNavigateToAi,
                onPlannerClick = onNavigateToPlanner,
                onProfileClick = onNavigateToProfile
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Navigate to create task */ },
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
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
            tasks = filteredTasks,
            allTasks = tasks,
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
@Composable
private fun TasksScreenContent(
    tasks: List<TaskCardUiModel>,
    allTasks: List<TaskCardUiModel>,
    selectedFilter: TaskFilter,
    onFilterSelected: (TaskFilter) -> Unit,
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
                title = "Your Tasks",
                action = "${tasks.size} items"
            )
        }

        if (tasks.isEmpty()) {
            item {
                EmptyTasksState()
            }
        } else {
            itemsIndexed(
                items = tasks,
                key = { index, task ->
                    "${task.title}_${task.subtitle}_${index}"
                }
            ) { _, task ->
                CompactDashboardTaskCard(
                    task = task
                )
            }
        }
    }
}

enum class TaskFilter {
    All,
    InProgress,
    Pending,
    Completed
}

private fun taskMockData(): List<TaskCardUiModel> {
    return listOf(
        TaskCardUiModel(
            title = "Design System Update",
            subtitle = "Refine typography and spacing tokens",
            progress = 80,
            priority = TaskPriority.Normal,
            status = TaskStatus.InProgress,
            assigneeInitials = "MS",
            isCompleted = false
        ),
        TaskCardUiModel(
            title = "Fix Login Flow",
            subtitle = "Resolve session persistence issue",
            progress = 30,
            priority = TaskPriority.Urgent,
            status = TaskStatus.Pending,
            assigneeInitials = "AL",
            isCompleted = false
        ),
        TaskCardUiModel(
            title = "Write Unit Tests",
            subtitle = "Cover repository and use case logic",
            progress = 100,
            priority = TaskPriority.Low,
            status = TaskStatus.Completed,
            assigneeInitials = "BK",
            isCompleted = true
        ),
        TaskCardUiModel(
            title = "Prepare Sprint Demo",
            subtitle = "Collect updates for presentation",
            progress = 60,
            priority = TaskPriority.Normal,
            status = TaskStatus.InProgress,
            assigneeInitials = "TM",
            isCompleted = false
        )
    )
}

@Preview(
    name = "Tasks Screen - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun TasksScreenLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
        TasksScreen()
    }
}

@Preview(
    name = "Tasks Screen - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun TasksScreenDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
        TasksScreen()
    }
}
