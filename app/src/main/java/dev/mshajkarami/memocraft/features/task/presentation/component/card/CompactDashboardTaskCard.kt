package dev.mshajkarami.memocraft.features.task.presentation.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel

@Composable
fun CompactDashboardTaskCard(
    task: TaskCardUiModel,
    onTaskClick: (String) -> Unit,
    onConfirmClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val detailsText = remember(task.dateLabel, task.timeLabel) {
        buildTaskDetailsText(
            dateLabel = task.dateLabel,
            timeLabel = task.timeLabel
        )
    }

    val contentAlpha = if (task.isCompleted) 0.5f else 1f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTaskClick(task.id) }
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TaskConfirmButton(
            isCompleted = task.isCompleted,
            onClick = { onConfirmClick(task.id) }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (detailsText != null) {
                Text(
                    text = detailsText,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.75f * contentAlpha
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun buildTaskDetailsText(
    dateLabel: String?,
    timeLabel: String?
): String? {
    return listOfNotNull(
        dateLabel?.takeIf { it.isNotBlank() },
        timeLabel?.takeIf { it.isNotBlank() }
    )
        .joinToString(separator = " • ")
        .takeIf { it.isNotBlank() }
}

@Composable
private fun TaskConfirmButton(
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = if (isCompleted) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                },
                shape = CircleShape
            )
            .background(
                color = if (isCompleted) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                },
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isCompleted) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
