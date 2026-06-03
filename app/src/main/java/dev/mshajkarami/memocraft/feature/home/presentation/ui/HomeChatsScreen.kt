package dev.mshajkarami.memocraft.feature.home.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.AiInsightCard
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.GreetingSection
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.HomeTopBar
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.ProgressCard
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.SectionHeader
import dev.mshajkarami.memocraft.feature.home.presentation.ui.components.TaskCard
import dev.mshajkarami.memocraft.navigation.BottomNavBar



@Composable
fun MemoCraftScreen() {
    val colors = MemoCraftTheme.colors

    val tasks = listOf(
        TaskItem(
            title = "Project Proposal",
            subtitle = "Design the proposal deck",
            time = "10:30 AM",
            priority = "High Priority",
            priorityColor = colors.successColor,
            iconBg = colors.successContainer,
            iconColor = colors.successContent,
            trailingChecked = true
        ),
        TaskItem(
            title = "Backend API Review",
            subtitle = "Review endpoints & docs",
            time = "1:00 PM",
            priority = "Urgent",
            priorityColor = colors.errorColor,
            iconBg = colors.errorContainer,
            iconColor = colors.errorContent,
            trailingText = "50%"
        ),
        TaskItem(
            title = "Team Standup",
            subtitle = "Daily sync with team",
            time = "3:00 PM",
            priority = "Medium Priority",
            priorityColor = colors.accentColor,
            iconBg = colors.accentContainer,
            iconColor = colors.accentContent
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
            item { GreetingSection() }
            item { ProgressCard() }

            item {
                SectionHeader(
                    title = "Upcoming Tasks",
                    action = "See all",
                )
            }

            items(tasks.size) { index ->
                TaskCard(task = tasks[index])
            }

            item {
                SectionHeader(
                    title = "AI Insights",
                    action = ""
                )
            }

            item { AiInsightCard() }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
