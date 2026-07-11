package dev.mshajkarami.memocraft.features.home.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.home.presentation.ui.components.AiInsightCard
import dev.mshajkarami.memocraft.features.home.presentation.ui.components.GreetingSection
import dev.mshajkarami.memocraft.features.home.presentation.ui.components.HomeTopBar
import dev.mshajkarami.memocraft.features.home.presentation.ui.components.SectionHeader
import dev.mshajkarami.memocraft.features.home.presentation.ui.components.progress.ProgressCard
import dev.mshajkarami.memocraft.features.home.presentation.viewmodel.HomeUiState
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus
import dev.mshajkarami.memocraft.features.task.presentation.component.card.CompactDashboardTaskCard
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSeeAllTasksClick: () -> Unit,
    onTaskClick: (String) -> Unit,
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
            HomeTopBar(
                searchQuery = uiState.searchQuery,
                isSearchActive = uiState.isSearchActive,
                onSearchQueryChange = onSearchQueryChange,
                onSearchClick = onSearchClick,
                onSearchClose = onSearchClose,
                onSearchSubmit = onSearchSubmit
            )
        }
    ) { innerPadding ->
        HomeScreenContent(
            uiState = uiState,
            onSeeAllTasksClick = onSeeAllTasksClick,
            onTaskClick = onTaskClick,
            bottomPadding = innerPadding.calculateBottomPadding(),
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding()
            )
        )
    }
}


@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onSeeAllTasksClick: () -> Unit,
    onTaskClick: (String) -> Unit,
    bottomPadding: Dp,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 18.dp,
            bottom = bottomPadding + 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            GreetingSection()
        }

        item {
            ProgressCard(
                overallProgress = uiState.overallProgress,
                totalTasks = uiState.totalTasks,
                completedTasks = uiState.completedTasks,
                focusTimeText = uiState.focusTimeText
            )
        }

        item {
            SectionHeader(
                title = "Upcoming Tasks",
                action = "See all",
                onActionClick = onSeeAllTasksClick
            )
        }

        itemsIndexed(
            items = uiState.upcomingTasks,
            key = { _, task -> task.id }
        ) { _, task ->
            CompactDashboardTaskCard(
                task = task,
                onTaskClick = onTaskClick
            )
        }

        item {
            SectionHeader(
                title = "AI Insights",
                action = ""
            )
        }

        item {
            AiInsightCard()
        }

        item {
            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }
}

private fun homePreviewUiState(): HomeUiState {
    return HomeUiState(
        isLoading = false,

        totalTasks = 17,
        completedTasks = 8,
        pendingTasks = 5,
        inProgressTasks = 4,
        overallProgress = 72,
        focusTimeText = "4h 12m",

        upcomingTasks = listOf(
            TaskCardUiModel(
                id = "1",
                title = "Project Proposal",
                subtitle = "Design the proposal deck",
                progress = 100,
                priority = TaskPriority.Normal,
                status = TaskStatus.Completed,
                isCompleted = true,
                timeLabel = "22 Feb 2026 • 10 h"
            ),
            TaskCardUiModel(
                id = "2",
                title = "Backend API Review",
                subtitle = "Review endpoints & docs",
                progress = 50,
                priority = TaskPriority.Urgent,
                status = TaskStatus.InProgress,
                isCompleted = false,
                timeLabel = "24 Feb 2026 • 4 h"
            ),
            TaskCardUiModel(
                id = "3",
                title = "Team Standup",
                subtitle = "Daily sync with team",
                progress = 20,
                priority = TaskPriority.Low,
                status = TaskStatus.Pending,
                isCompleted = false,
                timeLabel = "25 Feb 2026 • 2 h"
            )
        )
    )
}

@Preview(
    name = "MemoCraft Home - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 1350
)
@Composable
private fun HomeScreenLightPreview() {
    MemoCraftAppTheme(
        darkTheme = false
    ) {
        HomeScreen(
            uiState = homePreviewUiState(),
            onSeeAllTasksClick = {},
            onSearchQueryChange = {},
            onSearchClick = {},
            onSearchClose = {  },
            onSearchSubmit = {},
            onTaskClick = {}
        )
    }
}

@Preview(
    name = "MemoCraft Home - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 1350
)
@Composable
private fun HomeScreenDarkPreview() {
    MemoCraftAppTheme(
        darkTheme = true
    ) {
        HomeScreen(
            uiState = homePreviewUiState(),
            onSeeAllTasksClick = {},
            onSearchQueryChange = {},
            onSearchClick = {},
            onSearchClose = {  },
            onSearchSubmit = {},
            onTaskClick = {}
        )
    }
}
