package dev.mshajkarami.memocraft.features.home.presentation.ui.components.progress

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp

@Composable
internal fun SparkLine(
    color: Color,
    modifier: Modifier = Modifier,
    durationMillis: Int = 1600
) {
    val infiniteTransition = rememberInfiniteTransition(
        label = "sparkline_transition"
    )

    val progress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "sparkline_progress"
    )

    Canvas(
        modifier = modifier
    ) {
        val points = listOf(
            0.15f to 0.65f,
            0.28f to 0.48f,
            0.42f to 0.56f,
            0.56f to 0.28f,
            0.72f to 0.42f,
            0.88f to 0.34f
        )

        val path = Path()

        points.forEachIndexed { index, point ->
            val x = size.width * point.first
            val y = size.height * point.second

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        val minX = points.first().first
        val maxX = points.last().first
        val animatedNormalizedX = minX + ((maxX - minX) * progress.value)
        val clipRight = size.width * animatedNormalizedX

        drawPath(
            path = path,
            color = color.copy(alpha = 0.12f),
            style = Stroke(
                width = 5.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        clipRect(
            left = 0f,
            top = 0f,
            right = clipRight,
            bottom = size.height
        ) {
            drawPath(
                path = path,
                color = color.copy(alpha = 0.26f),
                style = Stroke(
                    width = 5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )

            drawPath(
                path = path,
                color = color,
                style = Stroke(
                    width = 2.2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }

        val currentXNormalized = animatedNormalizedX.coerceIn(minX, maxX)

        val segmentIndex = points
            .windowed(2)
            .indexOfFirst { segment ->
                currentXNormalized >= segment[0].first &&
                        currentXNormalized <= segment[1].first
            }
            .coerceAtLeast(0)

        val startPoint = points[segmentIndex]
        val endPoint = points[(segmentIndex + 1).coerceAtMost(points.lastIndex)]

        val segmentProgress = if (endPoint.first != startPoint.first) {
            ((currentXNormalized - startPoint.first) / (endPoint.first - startPoint.first))
                .coerceIn(0f, 1f)
        } else {
            0f
        }

        val dotX = size.width * currentXNormalized
        val dotY = size.height * (
                startPoint.second + ((endPoint.second - startPoint.second) * segmentProgress)
                )

        drawCircle(
            color = color.copy(alpha = 0.24f),
            radius = 6.dp.toPx(),
            center = Offset(dotX, dotY)
        )

        drawCircle(
            color = color,
            radius = 2.6.dp.toPx(),
            center = Offset(dotX, dotY)
        )
    }
}
