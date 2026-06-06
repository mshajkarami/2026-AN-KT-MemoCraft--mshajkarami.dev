package dev.mshajkarami.memocraft.feature.home.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.AiInsightCard
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.CompactDashboardTaskCard
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.GreetingSection
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.HomeTopBar
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.SectionHeader
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.TaskCardUiModel
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.TaskPriority
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.TaskStatus
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.progress.ProgressCard
import dev.mshajkarami.memocraft.navigation.BottomNavBar

@Composable
fun HomeScreen() {
    val colors = MemoCraftTheme.colors

    val tasks = listOf(
        TaskCardUiModel(
            title = "Project Proposal",
            subtitle = "Design the proposal deck",
            progress = 100,
            priority = TaskPriority.Normal,
            status = TaskStatus.Completed,
            assigneeInitials = "MS",
            isCompleted = true
        ),
        TaskCardUiModel(
            title = "Backend API Review",
            subtitle = "Review endpoints & docs",
            progress = 50,
            priority = TaskPriority.Urgent,
            status = TaskStatus.InProgress,
            assigneeInitials = "BK",
            isCompleted = false
        ),
        TaskCardUiModel(
            title = "Team Standup",
            subtitle = "Daily sync with team",
            progress = 20,
            priority = TaskPriority.Low,
            status = TaskStatus.Pending,
            assigneeInitials = "TM",
            isCompleted = false
        )
    )

    Scaffold(
        containerColor = colors.bottomNavContainer,
        topBar = {
            HomeTopBar()
        },
        bottomBar = {
            BottomNavBar()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 18.dp,
                bottom = 16.dp
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

            items(
                items = tasks,
                key = { task -> task.title }
            ) { task ->
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
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
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
    MemoCraftAppTheme(
        darkTheme = false
    ) {
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
    MemoCraftAppTheme(
        darkTheme = true
    ) {
        HomeScreen()
    }
}
