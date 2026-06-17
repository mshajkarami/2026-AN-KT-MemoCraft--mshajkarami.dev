package dev.mshajkarami.memocraft.features.home.presentation.ui.components.progress

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun ProgressCard(
    overallProgress: Int,
    totalTasks: Int,
    completedTasks: Int,
    pendingTasks: Int,
    inProgressTasks: Int,
    focusTimeText: String,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val cardShape = RoundedCornerShape(28.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(cardShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        colors.progressCardBackground,
                        colors.progressCardBackgroundSecondary
                    )
                )
            )
            .border(
                width = 1.dp,
                color = colors.progressCardSurfaceBorder,
                shape = cardShape
            )
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            ProgressHeader()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NeonProgressRing(
                    percentage = overallProgress,
                    modifier = Modifier.size(142.dp)
                )

                Spacer(modifier = Modifier.width(18.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RingLightMiniCard(
                        title = "Focus Light",
                        value = focusTimeText,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MiniMetricCard(
                            title = "Done",
                            value = completedTasks.toString(),
                            lineColor = colors.progressSparkBlue,
                            modifier = Modifier.weight(1f)
                        )

                        MiniMetricCard(
                            title = "Pending",
                            value = pendingTasks.toString(),
                            lineColor = colors.progressSparkPurple,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MiniMetricCard(
                        title = "Tasks",
                        value = totalTasks.toString(),
                        lineColor = colors.progressSparkBlue,
                        modifier = Modifier.weight(1f)
                    )

                    MiniMetricCard(
                        title = "In Progress",
                        value = inProgressTasks.toString(),
                        lineColor = colors.progressSparkPurple,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MiniMetricCard(
                        title = "Velocity",
                        value = "$overallProgress%",
                        lineColor = colors.progressSparkOrange,
                        modifier = Modifier.weight(1f)
                    )

                    MiniMetricCard(
                        title = "Progress",
                        value = "$completedTasks/$totalTasks",
                        lineColor = colors.progressSparkBlue,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

        }
    }
}
