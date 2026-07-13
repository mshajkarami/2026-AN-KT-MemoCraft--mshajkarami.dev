package dev.mshajkarami.memocraft.features.task.presentation.list.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.designsystem.theme.MemoCraftTheme

@Composable
fun EmptyTasksState() {
    val colors = MemoCraftTheme.colors

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = colors.taskCardContainer,
        border = BorderStroke(1.dp, colors.topBarDivider)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "No tasks found",
                style = MaterialTheme.typography.titleMedium,
                color = colors.taskCardTitle
            )
            Text(
                text = "Try changing the selected filter or create a new task.",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.taskCardSubtitle
            )
        }
    }
}