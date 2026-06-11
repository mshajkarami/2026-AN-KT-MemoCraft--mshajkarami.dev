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
import androidx.compose.runtime.remember
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
import dev.mshajkarami.memocraft.features.task.presentation.component.card.CompactDashboardTaskCard
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskStatus
import dev.mshajkarami.memocraft.navigation.BottomNavBar
import dev.mshajkarami.memocraft.navigation.BottomNavItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val tasks = remember { homeMockTasks() }

    Scaffold(
        modifier = modifier,
        containerColor = colors.bottomNavContainer,
        topBar = {
            HomeTopBar(
                onNotificationClick = {},
                onSearchClick = {},
                hasNotification = false
            )
        }
    ) { innerPadding ->
        HomeScreenContent(
            tasks = tasks,
            bottomPadding = innerPadding.calculateBottomPadding(),
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        )
    }

}


@Composable
private fun HomeScreenContent(
    tasks: List<TaskCardUiModel>,
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
            ProgressCard()
        }

        item {
            SectionHeader(
                title = "Upcoming Tasks",
                action = "See all"
            )
        }

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
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

private fun homeMockTasks(): List<TaskCardUiModel> {
    return listOf(
        TaskCardUiModel(
            title = "Project Proposal",
            subtitle = "Design the proposal deck",
            progress = 100,
            priority = TaskPriority.Normal,
            status = TaskStatus.Completed,
            isCompleted = true
        ),
        TaskCardUiModel(
            title = "Backend API Review",
            subtitle = "Review endpoints & docs",
            progress = 50,
            priority = TaskPriority.Urgent,
            status = TaskStatus.InProgress,
            isCompleted = false
        ),
        TaskCardUiModel(
            title = "Team Standup",
            subtitle = "Daily sync with team",
            progress = 20,
            priority = TaskPriority.Low,
            status = TaskStatus.Pending,
            isCompleted = false
        )
    )
}

@Preview(
    name = "MemoCraft Home - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun HomeScreenLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
        HomeScreen()
    }
}

@Preview(
    name = "MemoCraft Home - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun HomeScreenDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
        HomeScreen()
    }
}
