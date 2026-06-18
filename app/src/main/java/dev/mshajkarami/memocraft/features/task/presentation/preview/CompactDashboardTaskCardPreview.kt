package dev.mshajkarami.memocraft.features.task.presentation.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.features.task.presentation.component.card.CompactDashboardTaskCard
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus


@Preview(
    name = "Task Card - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 160
)
@Composable
private fun CompactDashboardTaskCardLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CompactDashboardTaskCard(
                task = TaskCardUiModel(
                    id = "1",
                    title = "Develop New API Endpoints",
                    progress = 65,
                    priority = TaskPriority.Urgent,
                    status = TaskStatus.InProgress
                ),
                onTaskClick = {}
            )
        }
    }
}

@Preview(
    name = "Task Card - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 160
)
@Composable
private fun CompactDashboardTaskCardDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CompactDashboardTaskCard(
                task = TaskCardUiModel(
                    id = "1",
                    title = "Develop New API Endpoints",
                    progress = 65,
                    priority = TaskPriority.Urgent,
                    status = TaskStatus.InProgress
                ),
                onTaskClick = {}
            )
        }
    }
}
