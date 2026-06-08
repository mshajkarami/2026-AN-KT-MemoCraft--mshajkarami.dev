package dev.mshajkarami.memocraft.features.task.presentation.component.card

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CompactTaskProgressRing(
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
        Canvas(modifier = Modifier.matchParentSize()) {
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
