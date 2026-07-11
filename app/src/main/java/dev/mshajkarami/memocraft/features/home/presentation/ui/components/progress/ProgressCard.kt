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
    completedTasks: Int,
    totalTasks: Int,
    focusTimeText: String,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val cardShape = RoundedCornerShape(24.dp)

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            NeonProgressRing(
                percentage = overallProgress,
                modifier = Modifier.size(132.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // نمایش زمان تمرکز
                RingLightMiniCard(
                    title = "Focus Time",
                    value = focusTimeText,
                    modifier = Modifier.fillMaxWidth()
                )

                // فقط مهم‌ترین اطلاعات: انجام شده / کل
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MiniMetricCard(
                        title = "Tasks Done",
                        value = "$completedTasks/$totalTasks",
                        lineColor = colors.progressSparkBlue,
                        modifier = Modifier.weight(1f)
                    )

                    // این آیتم می‌تواند Velocity یا هر چیز مهم دیگری باشد،
                    // اما برای خلوت شدن فقط یک آیتم دیگر کنار تسک‌ها گذاشتیم
                    MiniMetricCard(
                        title = "Efficiency",
                        value = "$overallProgress%",
                        lineColor = colors.progressSparkOrange,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
