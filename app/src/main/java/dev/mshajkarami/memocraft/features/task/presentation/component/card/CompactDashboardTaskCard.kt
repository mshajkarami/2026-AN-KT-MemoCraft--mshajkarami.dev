package dev.mshajkarami.memocraft.features.task.presentation.component.card

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.task.presentation.component.chip.TaskPriorityChip
import dev.mshajkarami.memocraft.features.task.presentation.component.chip.TaskStatusChip
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel

@Composable
fun CompactDashboardTaskCard(
    task: TaskCardUiModel,
    onTaskClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val shape = RoundedCornerShape(24.dp)
    val brushes = rememberTaskCardBrushes()
    val description = task.description.orEmpty().takeIf { it.isNotBlank() }

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
            .clickable { onTaskClick(task.id) }
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
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                TaskPriorityChip(priority = task.priority)

                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.progressMiniCardContent,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.progressMiniCardContent.copy(alpha = 0.68f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    task.timeLabel?.takeIf { it.isNotBlank() }?.let { timeLabel ->
                        TaskTimeLabelChip(timeLabel = timeLabel)
                    }

                    TaskStatusChip(status = task.status)
                }
            }



            Spacer(modifier = Modifier.width(16.dp))

            CompactTaskProgressRing(
                progress = task.progress,
                isCompleted = task.isCompleted,
                modifier = Modifier.size(68.dp)
            )
        }
    }
}

@Composable
private fun TaskTimeLabelChip(
    timeLabel: String,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val contentColor = colors.progressMiniCardContent.copy(alpha = 0.68f)

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(colors.progressMiniCardContent.copy(alpha = 0.08f))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        CalendarMiniIcon(
            color = contentColor,
            modifier = Modifier.size(14.dp)
        )

        Text(
            text = timeLabel,
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CalendarMiniIcon(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 1.4.dp.toPx()
        val cornerRadius = 3.dp.toPx()

        drawRoundRect(
            color = color,
            topLeft = Offset(size.width * 0.14f, size.height * 0.18f),
            size = Size(size.width * 0.72f, size.height * 0.68f),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
            style = Stroke(width = strokeWidth)
        )

        drawLine(
            color = color,
            start = Offset(size.width * 0.14f, size.height * 0.38f),
            end = Offset(size.width * 0.86f, size.height * 0.38f),
            strokeWidth = strokeWidth
        )

        drawLine(
            color = color,
            start = Offset(size.width * 0.34f, size.height * 0.1f),
            end = Offset(size.width * 0.34f, size.height * 0.26f),
            strokeWidth = strokeWidth
        )

        drawLine(
            color = color,
            start = Offset(size.width * 0.66f, size.height * 0.1f),
            end = Offset(size.width * 0.66f, size.height * 0.26f),
            strokeWidth = strokeWidth
        )
    }
}
