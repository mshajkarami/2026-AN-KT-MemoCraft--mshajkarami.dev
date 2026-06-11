package dev.mshajkarami.memocraft.features.task.presentation.component.card


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.task.presentation.component.chip.TaskPriorityChip
import dev.mshajkarami.memocraft.features.task.presentation.component.chip.TaskStatusChip
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel


@Composable
fun CompactDashboardTaskCard(
    task: TaskCardUiModel,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val shape = RoundedCornerShape(24.dp)
    val brushes = rememberTaskCardBrushes()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (brushes.isLightTheme) 12.dp else 16.dp,
                shape = shape,
                clip = false,
                ambientColor = colors.compactTaskCardShadowAmbient,
                spotColor = colors.compactTaskCardShadowSpot
            )
            .clip(shape)
            .background(brushes.backgroundBrush)
            .border(
                width = 1.dp,
                brush = brushes.borderBrush,
                shape = shape
            )
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(brushes.highlightBrush)
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(1.dp)
                .clip(RoundedCornerShape(23.dp))
                .border(
                    width = 1.dp,
                    color = colors.compactTaskCardInnerBorder,
                    shape = RoundedCornerShape(23.dp)
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompactTaskProgressRing(
                progress = task.progress,
                isCompleted = task.isCompleted,
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.progressMiniCardContent,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    TaskPriorityChip(priority = task.priority)
                    TaskStatusChip(status = task.status)

                    task.timeLabel?.takeIf { it.isNotBlank() }?.let { timeLabel ->
                        Text(
                            text = timeLabel,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.progressMiniCardContent.copy(alpha = 0.72f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
