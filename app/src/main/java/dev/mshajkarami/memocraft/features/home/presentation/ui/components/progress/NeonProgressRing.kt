package dev.mshajkarami.memocraft.features.home.presentation.ui.components.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun NeonProgressRing(
    percentage: Int,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val safePercentage = percentage.coerceIn(0, 100)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            val strokeWidth = 15.dp.toPx()
            val glowStrokeWidth = 24.dp.toPx()
            val sweepAngle = 360f * (safePercentage / 100f)

            val arcSize = Size(
                width = size.width - glowStrokeWidth,
                height = size.height - glowStrokeWidth
            )

            val topLeft = Offset(
                x = glowStrokeWidth / 2f,
                y = glowStrokeWidth / 2f
            )

            val arcCenter = Offset(
                x = topLeft.x + arcSize.width / 2f,
                y = topLeft.y + arcSize.height / 2f
            )

            val arcRadius = arcSize.minDimension / 2f

            val endAngleInDegrees = -90f + sweepAngle
            val endAngleInRadians = endAngleInDegrees * PI.toFloat() / 180f

            val highlightCenter = Offset(
                x = arcCenter.x + arcRadius * cos(endAngleInRadians),
                y = arcCenter.y + arcRadius * sin(endAngleInRadians)
            )

            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        colors.progressRingGlowStart.copy(alpha = 0.20f),
                        colors.progressRingGlowMiddle.copy(alpha = 0.28f),
                        colors.progressRingGlowEnd.copy(alpha = 0.24f),
                        colors.progressRingGlowStart.copy(alpha = 0.20f)
                    ),
                    center = arcCenter
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
                color = colors.progressRingLightOuter,
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
                        colors.progressRingGlowStart,
                        colors.progressRingGlowMiddle,
                        colors.progressRingGlowEnd,
                        colors.progressRingGlowStart
                    ),
                    center = arcCenter
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

            if (safePercentage > 0) {
                drawCircle(
                    color = colors.progressRingHighlight,
                    radius = 3.5.dp.toPx(),
                    center = highlightCenter
                )
            }

            drawCircle(
                color = colors.progressRingCenterFill,
                radius = size.minDimension / 3.6f,
                center = center
            )

            drawCircle(
                color = colors.progressCardSurfaceBorder,
                radius = size.minDimension / 3.6f,
                center = center,
                style = Stroke(
                    width = 1.dp.toPx()
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$safePercentage%",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.progressMiniCardContent
            )

            Text(
                text = "Completed",
                style = MaterialTheme.typography.bodySmall,
                color = colors.progressMiniCardContentSecondary
            )
        }
    }
}
