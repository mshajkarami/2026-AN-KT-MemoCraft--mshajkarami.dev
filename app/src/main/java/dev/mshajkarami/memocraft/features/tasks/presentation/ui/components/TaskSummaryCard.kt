package dev.mshajkarami.memocraft.features.tasks.presentation.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ListAlt
import androidx.compose.material.icons.rounded.PendingActions
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun TaskSummaryCard(
    totalTasks: Int,
    completedTasks: Int,
    inProgressTasks: Int,
    pendingTasks: Int,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val isLightTheme = MaterialTheme.colorScheme.background.luminance() > 0.5f
    val shape = RoundedCornerShape(24.dp)

    val completionRatio = remember(totalTasks, completedTasks) {
        if (totalTasks == 0) {
            0f
        } else {
            completedTasks.toFloat() / totalTasks.toFloat()
        }
    }

    val completionPercent = remember(completionRatio) {
        (completionRatio * 100).toInt().coerceIn(0, 100)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = completionRatio.coerceIn(0f, 1f),
        label = "task_summary_progress"
    )

    val animatedPercent by animateIntAsState(
        targetValue = completionPercent,
        label = "task_summary_percent"
    )

    val cardBackground = if (isLightTheme) {
        Brush.linearGradient(
            colors = listOf(
                colors.taskCardContainer,
                colors.compactTaskCardGlassOverlay.copy(alpha = 0.88f),
                Color.White.copy(alpha = 0.84f)
            ),
            start = Offset(0f, 0f),
            end = Offset(820f, 520f)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                colors.taskCardContainer.copy(alpha = 0.98f),
                colors.taskCardContainer.copy(alpha = 0.94f),
                colors.bottomNavSelectedIndicator.copy(alpha = 0.095f),
                colors.taskCardContainer.copy(alpha = 0.98f)
            ),
            start = Offset(0f, 0f),
            end = Offset(820f, 620f)
        )
    }

    val borderBrush = if (isLightTheme) {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.92f),
                colors.bottomNavSelectedIndicator.copy(alpha = 0.36f),
                colors.compactTaskCardInnerBorder.copy(alpha = 0.64f)
            ),
            start = Offset(0f, 0f),
            end = Offset(780f, 460f)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.14f),
                colors.bottomNavSelectedIndicator.copy(alpha = 0.30f),
                Color.White.copy(alpha = 0.065f)
            ),
            start = Offset(0f, 0f),
            end = Offset(780f, 460f)
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isLightTheme) 14.dp else 20.dp,
                shape = shape,
                clip = false,
                ambientColor = colors.compactTaskCardShadowAmbient.copy(
                    alpha = if (isLightTheme) 0.18f else 0.32f
                ),
                spotColor = colors.compactTaskCardShadowSpot.copy(
                    alpha = if (isLightTheme) 0.15f else 0.36f
                )
            )
            .clip(shape)
            .background(cardBackground)
            .border(
                width = 1.dp,
                brush = borderBrush,
                shape = shape
            )
    ) {
        SummaryGlowLayer(
            isLightTheme = isLightTheme
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            SummaryHeader(
                completionPercent = animatedPercent
            )

            Spacer(modifier = Modifier.height(12.dp))

            PremiumProgressBar(
                progress = animatedProgress,
                isLightTheme = isLightTheme
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SummaryMetricTile(
                    title = "Total",
                    value = totalTasks,
                    icon = Icons.Rounded.ListAlt,
                    valueColor = colors.progressMiniCardContent,
                    iconColor = colors.progressMiniCardContent,
                    modifier = Modifier.weight(1f)
                )

                SummaryMetricTile(
                    title = "Done",
                    value = completedTasks,
                    icon = Icons.Rounded.CheckCircle,
                    valueColor = colors.successColor,
                    iconColor = colors.successColor,
                    modifier = Modifier.weight(1f)
                )

                SummaryMetricTile(
                    title = "Active",
                    value = inProgressTasks,
                    icon = Icons.Rounded.PlayCircle,
                    valueColor = colors.taskStatusInProgressContent,
                    iconColor = colors.taskStatusInProgressContent,
                    modifier = Modifier.weight(1f)
                )

                SummaryMetricTile(
                    title = "Pending",
                    value = pendingTasks,
                    icon = Icons.Rounded.PendingActions,
                    valueColor = colors.taskStatusPendingContent,
                    iconColor = colors.taskStatusPendingContent,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BoxScope.SummaryGlowLayer(
    isLightTheme: Boolean
) {
    val colors = MemoCraftTheme.colors

    Box(
        modifier = Modifier
            .matchParentSize()
            .background(
                Brush.radialGradient(
                    colors = if (isLightTheme) {
                        listOf(
                            colors.bottomNavSelectedItemBackground.copy(alpha = 0.30f),
                            Color.Transparent
                        )
                    } else {
                        listOf(
                            colors.bottomNavSelectedIndicator.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    },
                    center = Offset(110f, 24f),
                    radius = if (isLightTheme) 500f else 420f
                )
            )
    )

    Box(
        modifier = Modifier
            .matchParentSize()
            .background(
                Brush.radialGradient(
                    colors = if (isLightTheme) {
                        listOf(
                            Color.White.copy(alpha = 0.34f),
                            Color.Transparent
                        )
                    } else {
                        listOf(
                            Color.White.copy(alpha = 0.045f),
                            Color.Transparent
                        )
                    },
                    center = Offset(720f, 24f),
                    radius = 360f
                )
            )
    )
}

@Composable
private fun SummaryHeader(
    completionPercent: Int
) {
    val colors = MemoCraftTheme.colors
    val isLightTheme = MaterialTheme.colorScheme.background.luminance() > 0.5f

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Task Overview",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = colors.progressMiniCardContent
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Workload and completion flow",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = colors.progressMiniCardContentSecondary
            )
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(
                    Brush.linearGradient(
                        colors = if (isLightTheme) {
                            listOf(
                                colors.bottomNavSelectedItemBackground.copy(alpha = 0.92f),
                                colors.bottomNavSelectedIndicator.copy(alpha = 0.16f)
                            )
                        } else {
                            listOf(
                                colors.bottomNavSelectedIndicator.copy(alpha = 0.22f),
                                Color.White.copy(alpha = 0.055f)
                            )
                        }
                    )
                )
                .border(
                    width = 1.dp,
                    color = if (isLightTheme) {
                        Color.White.copy(alpha = 0.76f)
                    } else {
                        Color.White.copy(alpha = 0.09f)
                    },
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(horizontal = 9.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.TrendingUp,
                contentDescription = null,
                tint = colors.bottomNavSelectedIndicator,
                modifier = Modifier.size(14.dp)
            )

            Text(
                text = "$completionPercent%",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = colors.progressMiniCardContent
            )
        }
    }
}

@Composable
private fun PremiumProgressBar(
    progress: Float,
    isLightTheme: Boolean
) {
    val colors = MemoCraftTheme.colors
    val trackShape = RoundedCornerShape(100.dp)
    val safeProgress = progress.coerceIn(0f, 1f)

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Overall completion",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.progressMiniCardContentSecondary
            )

            Text(
                text = "${(safeProgress * 100).toInt().coerceIn(0, 100)}%",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = colors.progressMiniCardContent
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
                .clip(trackShape)
                .background(
                    if (isLightTheme) {
                        colors.progressMiniCardContent.copy(alpha = 0.075f)
                    } else {
                        Color.White.copy(alpha = 0.07f)
                    }
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(safeProgress)
                    .height(7.dp)
                    .clip(trackShape)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                colors.bottomNavSelectedIndicator,
                                colors.taskStatusInProgressContent,
                                colors.successColor
                            )
                        )
                    )
            )
        }
    }
}

@Composable
private fun SummaryMetricTile(
    title: String,
    value: Int,
    icon: ImageVector,
    valueColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val isLightTheme = MaterialTheme.colorScheme.background.luminance() > 0.5f

    val animatedValue by animateIntAsState(
        targetValue = value,
        label = "summary_metric_$title"
    )

    val tileShape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .clip(tileShape)
            .background(
                Brush.linearGradient(
                    colors = if (isLightTheme) {
                        listOf(
                            Color.White.copy(alpha = 0.70f),
                            colors.compactTaskCardGlassOverlay.copy(alpha = 0.45f)
                        )
                    } else {
                        listOf(
                            Color.White.copy(alpha = 0.065f),
                            Color.White.copy(alpha = 0.028f)
                        )
                    },
                    start = Offset(0f, 0f),
                    end = Offset(180f, 150f)
                )
            )
            .border(
                width = 1.dp,
                color = if (isLightTheme) {
                    Color.White.copy(alpha = 0.72f)
                } else {
                    Color.White.copy(alpha = 0.075f)
                },
                shape = tileShape
            )
            .padding(horizontal = 8.dp, vertical = 9.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        iconColor.copy(
                            alpha = if (isLightTheme) 0.12f else 0.16f
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(13.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Text(
                    text = animatedValue.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = valueColor
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.progressMiniCardContentSecondary
                )
            }
        }
    }
}
