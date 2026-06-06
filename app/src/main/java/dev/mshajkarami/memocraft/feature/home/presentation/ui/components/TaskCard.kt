package dev.mshajkarami.memocraft.feature.home.presentation.ui.components

import android.R.attr.scaleY
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.feature.home.presentation.ui.TaskItem
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Immutable
data class TaskCardUiModel(
    val title: String,
    val subtitle: String? = null,
    val progress: Int = 0,
    val priority: TaskPriority = TaskPriority.Normal,
    val status: TaskStatus = TaskStatus.Pending,
    val assigneeInitials: String? = null,
    val isCompleted: Boolean = false
)

enum class TaskPriority {
    Low,
    Normal,
    Urgent
}

enum class TaskStatus {
    Pending,
    InProgress,
    Completed
}
@Composable
fun TaskCard(task: TaskItem) {
    val colors = MemoCraftTheme.colors

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.taskCardContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(task.iconBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (task.title) {
                        "Project Proposal" -> "✓"
                        "Backend API Review" -> "</>"
                        else -> "👥"
                    },
                    color = task.iconColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.taskCardTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = task.subtitle,
                    fontSize = 14.sp,
                    color = colors.taskCardSubtitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(task.priorityColor.copy(alpha = 0.18f))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = task.priority,
                        color = when (task.priority) {
                            "Urgent" -> colors.taskPriorityUrgentContent
                            "High Priority" -> colors.taskPriorityHighContent
                            else -> colors.taskPriorityNormalContent
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(72.dp)
            ) {
                Text(
                    text = task.time,
                    color = if (task.time == "1:00 PM") {
                        colors.taskCardTimeHighlighted
                    } else {
                        colors.taskCardTimeDefault
                    },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                when {
                    task.trailingChecked -> {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(colors.taskCompletedContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                color = colors.taskCompletedContent,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    task.trailingText != null -> {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .border(
                                    width = 3.dp,
                                    color = colors.taskProgressBorder,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = task.trailingText,
                                color = colors.taskProgressContent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .border(
                                    width = 2.dp,
                                    color = colors.taskUncheckedBorder,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CompactDashboardTaskCard(
    task: TaskCardUiModel,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val shape = RoundedCornerShape(28.dp)

    val isLightTheme = MaterialTheme.colorScheme.background.luminance() > 0.5f

    val glassBackgroundBrush = if (isLightTheme) {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.82f),
                colors.progressCardBackground.copy(alpha = 0.62f),
                colors.progressCardBackgroundSecondary.copy(alpha = 0.42f),
                colors.progressRingGlowStart.copy(alpha = 0.08f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                colors.progressCardBackground.copy(alpha = 0.68f),
                colors.progressCardBackgroundSecondary.copy(alpha = 0.44f),
                Color.White.copy(alpha = 0.06f)
            )
        )
    }

    val glassBorderBrush = if (isLightTheme) {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.95f),
                colors.progressRingGlowStart.copy(alpha = 0.24f),
                Color(0xFF1E293B).copy(alpha = 0.08f),
                Color.White.copy(alpha = 0.62f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.32f),
                colors.progressCardSurfaceBorder.copy(alpha = 0.34f),
                Color.White.copy(alpha = 0.10f)
            )
        )
    }

    val highlightBrush = if (isLightTheme) {
        Brush.radialGradient(
            colors = listOf(
                colors.progressRingGlowStart.copy(alpha = 0.13f),
                Color.Transparent
            ),
            radius = 520f,
            center = Offset(0f, 0f)
        )
    } else {
        Brush.radialGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.08f),
                Color.Transparent
            ),
            radius = 460f,
            center = Offset(0f, 0f)
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isLightTheme) 16.dp else 20.dp,
                shape = shape,
                clip = false,
                ambientColor = if (isLightTheme) {
                    Color(0xFF64748B).copy(alpha = 0.10f)
                } else {
                    colors.progressRingGlowMiddle.copy(alpha = 0.18f)
                },
                spotColor = if (isLightTheme) {
                    Color(0xFF94A3B8).copy(alpha = 0.16f)
                } else {
                    colors.progressRingGlowEnd.copy(alpha = 0.14f)
                }
            )
            .clip(shape)
            .background(glassBackgroundBrush)
            .border(
                width = 1.dp,
                brush = glassBorderBrush,
                shape = shape
            )
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(highlightBrush)
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(1.dp)
                .clip(RoundedCornerShape(27.dp))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = if (isLightTheme) 0.44f else 0.10f),
                    shape = RoundedCornerShape(27.dp)
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompactTaskProgressRing(
                progress = task.progress,
                isCompleted = task.isCompleted,
                modifier = Modifier.size(72.dp)
            )

            Spacer(modifier = Modifier.width(18.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.progressMiniCardContent,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TaskPriorityChip(priority = task.priority)
                    TaskStatusChip(status = task.status)
                }
            }

            if (!task.assigneeInitials.isNullOrBlank()) {
                Spacer(modifier = Modifier.width(12.dp))

                TaskAvatar(
                    initials = task.assigneeInitials,
                    modifier = Modifier.size(42.dp)
                )
            }
        }
    }
}



@Composable
private fun TaskStatusChip(
    status: TaskStatus,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    val chipColors = when (status) {
        TaskStatus.Pending -> TaskChipColors(
            background = colors.progressSparkOrange.copy(alpha = 0.16f),
            content = colors.progressSparkOrange,
            dot = colors.progressSparkOrange
        )

        TaskStatus.InProgress -> TaskChipColors(
            background = colors.progressSparkBlue.copy(alpha = 0.16f),
            content = colors.progressSparkBlue,
            dot = colors.progressSparkBlue
        )

        TaskStatus.Completed -> TaskChipColors(
            background = colors.progressRingGlowMiddle.copy(alpha = 0.16f),
            content = colors.progressRingGlowMiddle,
            dot = colors.progressRingGlowMiddle
        )
    }

    val text = when (status) {
        TaskStatus.Pending -> "Pending"
        TaskStatus.InProgress -> "In Progress"
        TaskStatus.Completed -> "Completed"
    }

    TaskChip(
        text = text,
        icon = null,
        colors = chipColors,
        modifier = modifier
    )
}

@Composable
private fun CompactTaskProgressRing(
    progress: Int,
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val safeProgress = progress.coerceIn(0, 100)
    val visualProgress = if (isCompleted) 100 else safeProgress

    val completedScale = animateFloatAsState(
        targetValue = if (isCompleted) 1f else 0.92f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "completedScale"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            val strokeWidth = if (isCompleted) 5.dp.toPx() else 6.dp.toPx()
            val glowStrokeWidth = if (isCompleted) 7.dp.toPx() else 10.dp.toPx()

            val arcSize = Size(
                width = size.width - glowStrokeWidth,
                height = size.height - glowStrokeWidth
            )

            val topLeft = Offset(
                x = glowStrokeWidth / 2f,
                y = glowStrokeWidth / 2f
            )

            val centerPoint = Offset(
                x = topLeft.x + arcSize.width / 2f,
                y = topLeft.y + arcSize.height / 2f
            )

            val sweepAngle = 360f * (visualProgress / 100f)

            drawArc(
                color = colors.progressRingLightOuter.copy(
                    alpha = if (isCompleted) 0.30f else 0.55f
                ),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        colors.progressRingGlowStart.copy(alpha = if (isCompleted) 0.12f else 0.26f),
                        colors.progressRingGlowMiddle.copy(alpha = if (isCompleted) 0.16f else 0.34f),
                        colors.progressRingGlowEnd.copy(alpha = if (isCompleted) 0.14f else 0.30f),
                        colors.progressRingGlowStart.copy(alpha = if (isCompleted) 0.12f else 0.26f)
                    ),
                    center = centerPoint
                ),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = glowStrokeWidth,
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        colors.progressRingGlowStart.copy(alpha = if (isCompleted) 0.76f else 1f),
                        colors.progressRingGlowMiddle.copy(alpha = if (isCompleted) 0.76f else 1f),
                        colors.progressRingGlowEnd.copy(alpha = if (isCompleted) 0.76f else 1f),
                        colors.progressRingGlowStart.copy(alpha = if (isCompleted) 0.76f else 1f)
                    ),
                    center = centerPoint
                ),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )

            if (!isCompleted && safeProgress > 0) {
                val radius = arcSize.minDimension / 2f
                val angle = (-90f + sweepAngle) * PI.toFloat() / 180f

                val dotCenter = Offset(
                    x = centerPoint.x + radius * cos(angle),
                    y = centerPoint.y + radius * sin(angle)
                )

                drawCircle(
                    color = colors.progressRingHighlight,
                    radius = 2.4.dp.toPx(),
                    center = dotCenter
                )
            }
        }

        Text(
            text = if (isCompleted) "100%" else "$safeProgress%",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = if (isCompleted) FontWeight.Medium else FontWeight.SemiBold,
            color = colors.progressMiniCardContent.copy(
                alpha = if (isCompleted) 0.72f else 1f
            )
        )

        if (isCompleted) {
            MiniCompletedTick(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 3.dp, end = 3.dp)
                    .graphicsLayer {
                        scaleX = completedScale.value
                        scaleY = completedScale.value
                    }
            )
        }
    }
}


@Composable
private fun MiniCompletedTick(
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.34f),
                        colors.progressMiniCardContent.copy(alpha = 0.06f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = colors.progressMiniCardContent.copy(alpha = 0.16f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Check,
            contentDescription = "Completed",
            tint = colors.progressMiniCardContent.copy(alpha = 0.74f),
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
private fun TaskPriorityChip(
    priority: TaskPriority,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    val chipColors = when (priority) {
        TaskPriority.Low -> TaskChipColors(
            background = colors.progressSparkBlue.copy(alpha = 0.16f),
            content = colors.progressSparkBlue,
            dot = colors.progressSparkBlue
        )

        TaskPriority.Normal -> TaskChipColors(
            background = colors.progressSparkPurple.copy(alpha = 0.16f),
            content = colors.progressSparkPurple,
            dot = colors.progressSparkPurple
        )

        TaskPriority.Urgent -> TaskChipColors(
            background = Color(0xFFFF5A7A).copy(alpha = 0.16f),
            content = Color(0xFFE93355),
            dot = Color(0xFFE93355)
        )
    }

    val text = when (priority) {
        TaskPriority.Low -> "Low"
        TaskPriority.Normal -> "Normal"
        TaskPriority.Urgent -> "Urgent"
    }

    val icon = when (priority) {
        TaskPriority.Low -> Icons.Rounded.Schedule
        TaskPriority.Normal -> Icons.Rounded.Schedule
        TaskPriority.Urgent -> Icons.Rounded.Error
    }

    TaskChip(
        text = text,
        icon = icon,
        colors = chipColors,
        modifier = modifier
    )
}
@Immutable
private data class TaskChipColors(
    val background: Color,
    val content: Color,
    val dot: Color
)

@Composable
private fun TaskChip(
    text: String,
    icon: ImageVector?,
    colors: TaskChipColors,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(colors.background)
            .padding(
                horizontal = 10.dp,
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.content,
                modifier = Modifier.size(15.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(colors.dot)
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = colors.content,
            maxLines = 1
        )
    }
}
@Composable
private fun TaskAvatar(
    initials: String,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        colors.progressRingGlowStart.copy(alpha = 0.85f),
                        colors.progressRingGlowEnd.copy(alpha = 0.85f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = colors.progressCardSurfaceBorder,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials.take(2).uppercase(),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
@Preview(
    name = "Task Cards - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 160
)
@Composable
private fun TaskCardsLightPreview() {
    MemoCraftAppTheme(
        darkTheme = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CompactDashboardTaskCard(
                task = TaskCardUiModel(
                    title = "Develop New API Endpoints",
                    progress = 65,
                    priority = TaskPriority.Urgent,
                    status = TaskStatus.InProgress,
                    assigneeInitials = "MS"
                )
            )
        }
    }
}

@Preview(
    name = "Task Cards - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 160
)
@Composable
private fun TaskCardsDarkPreview() {
    MemoCraftAppTheme(
        darkTheme = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CompactDashboardTaskCard(
                task = TaskCardUiModel(
                    title = "Develop New API Endpoints",
                    progress = 65,
                    priority = TaskPriority.Urgent,
                    status = TaskStatus.InProgress,
                    assigneeInitials = "MS"
                )
            )
        }
    }
}
