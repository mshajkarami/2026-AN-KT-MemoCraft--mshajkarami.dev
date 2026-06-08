package dev.mshajkarami.memocraft.features.planner.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.navigation.BottomNavBar
import dev.mshajkarami.memocraft.navigation.BottomNavItem

@Composable
fun PlannerScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    onNavigateToTasks: () -> Unit = {},
    onNavigateToAi: () -> Unit = {},
    onNavigateToPlanner: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val colors = MemoCraftTheme.colors
    val weekDays = remember { plannerWeekMockData() }
    val scheduleItems = remember { plannerScheduleMockData() }

    var selectedDay by remember { mutableStateOf(weekDays.first { it.isSelected }) }

    Scaffold(
        modifier = modifier,
        containerColor = colors.bottomNavContainer,
        bottomBar = {
            BottomNavBar(
                selectedItem = BottomNavItem.Planner,
                onHomeClick = onNavigateToHome,
                onTasksClick = onNavigateToTasks,
                onAiClick = onNavigateToAi,
                onPlannerClick = onNavigateToPlanner,
                onProfileClick = onNavigateToProfile
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Add planner event */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add plan",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        PlannerScreenContent(
            selectedDay = selectedDay,
            weekDays = weekDays.map {
                it.copy(isSelected = it.dayNumber == selectedDay.dayNumber)
            },
            scheduleItems = scheduleItems,
            onDaySelected = { selectedDay = it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun PlannerScreenContent(
    selectedDay: PlannerDayUiModel,
    weekDays: List<PlannerDayUiModel>,
    scheduleItems: List<PlannerScheduleUiModel>,
    onDaySelected: (PlannerDayUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.bottomNavContainer),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 20.dp,
            bottom = 110.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            PlannerHeader()
        }

        item {
            WeeklyCalendarSection(
                days = weekDays,
                onDaySelected = onDaySelected
            )
        }

        item {
            PlannerSectionHeader(
                title = "Today's Schedule",
                action = "${scheduleItems.size} items"
            )
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                scheduleItems.forEach { item ->
                    PlannerTimelineCard(item = item)
                }
            }
        }
    }
}

@Composable
private fun PlannerHeader() {
    val colors = MemoCraftTheme.colors

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = "Planner",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Organize your day with clarity",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f)
            )
        }

        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.CalendarMonth,
                contentDescription = "Calendar",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun WeeklyCalendarSection(
    days: List<PlannerDayUiModel>,
    onDaySelected: (PlannerDayUiModel) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(days) { day ->
            PlannerDayChip(
                day = day,
                onClick = { onDaySelected(day) }
            )
        }
    }
}

@Composable
private fun PlannerDayChip(
    day: PlannerDayUiModel,
    onClick: () -> Unit
) {
    val selected = day.isSelected

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 2.dp else 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .width(64.dp)
                .padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.dayName,
                style = MaterialTheme.typography.labelMedium,
                color = if (selected) {
                    MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = day.dayNumber.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (selected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@Composable
private fun PlannerSectionHeader(
    title: String,
    action: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = action,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun PlannerTimelineCard(
    item: PlannerScheduleUiModel
) {
    val accent = item.accentColor

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.width(62.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.timeLabel,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(2.dp)
                    .height(92.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(accent.copy(alpha = 0.25f))
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = accent.copy(alpha = 0.12f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(accent)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = item.timeRange,
                        style = MaterialTheme.typography.labelMedium,
                        color = accent,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

data class PlannerDayUiModel(
    val dayName: String,
    val dayNumber: Int,
    val isSelected: Boolean = false
)

data class PlannerScheduleUiModel(
    val timeLabel: String,
    val title: String,
    val subtitle: String,
    val timeRange: String,
    val accentColor: Color
)

private fun plannerWeekMockData(): List<PlannerDayUiModel> {
    return listOf(
        PlannerDayUiModel(dayName = "Mon", dayNumber = 20),
        PlannerDayUiModel(dayName = "Tue", dayNumber = 21),
        PlannerDayUiModel(dayName = "Wed", dayNumber = 22, isSelected = true),
        PlannerDayUiModel(dayName = "Thu", dayNumber = 23),
        PlannerDayUiModel(dayName = "Fri", dayNumber = 24),
        PlannerDayUiModel(dayName = "Sat", dayNumber = 25),
        PlannerDayUiModel(dayName = "Sun", dayNumber = 26)
    )
}

private fun plannerScheduleMockData(): List<PlannerScheduleUiModel> {
    return listOf(
        PlannerScheduleUiModel(
            timeLabel = "09:00",
            title = "Project Proposal",
            subtitle = "Design the proposal deck",
            timeRange = "09:00 - 10:00 AM",
            accentColor = Color(0xFF33C27F)
        ),
        PlannerScheduleUiModel(
            timeLabel = "10:15",
            title = "Data Analysis",
            subtitle = "Analyze last quarter data",
            timeRange = "10:15 - 11:15 AM",
            accentColor = Color(0xFF4A90E2)
        ),
        PlannerScheduleUiModel(
            timeLabel = "11:30",
            title = "Client Call",
            subtitle = "Conflict detected with another event",
            timeRange = "11:30 AM - 12:30 PM",
            accentColor = Color(0xFFFF6B6B)
        ),
        PlannerScheduleUiModel(
            timeLabel = "02:30",
            title = "Create Slides",
            subtitle = "Prepare presentation slides",
            timeRange = "02:30 - 03:30 PM",
            accentColor = Color(0xFF8B5CF6)
        ),
        PlannerScheduleUiModel(
            timeLabel = "04:30",
            title = "Review Report",
            subtitle = "Review and finalize",
            timeRange = "04:30 - 05:15 PM",
            accentColor = Color(0xFF20C997)
        )
    )
}

@Preview(
    name = "Planner Screen - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun PlannerScreenLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
        PlannerScreen()
    }
}

@Preview(
    name = "Planner Screen - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun PlannerScreenDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
        PlannerScreen()
    }
}
