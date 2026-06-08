package dev.mshajkarami.memocraft.features.home.presentation.ui.components.progress

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.drawscope.clipRect


@Composable
fun ProgressCard(
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
            .padding(18.dp)
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
                    percentage = 72,
                    modifier = Modifier.size(142.dp)
                )

                Spacer(modifier = Modifier.width(18.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RingLightMiniCard(
                        title = "Focus Light",
                        value = "4h 12m",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MiniMetricCard(
                            title = "Done",
                            value = "8",
                            lineColor = colors.progressSparkBlue,
                            modifier = Modifier.weight(1f)
                        )

                        MiniMetricCard(
                            title = "Pending",
                            value = "5",
                            lineColor = colors.progressSparkPurple,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MiniMetricCard(
                    title = "Tasks",
                    value = "17",
                    lineColor = colors.progressSparkBlue,
                    modifier = Modifier.weight(1f)
                )

                MiniMetricCard(
                    title = "Velocity",
                    value = "72%",
                    lineColor = colors.progressSparkPurple,
                    modifier = Modifier.weight(1f)
                )

                MiniMetricCard(
                    title = "Progress",
                    value = "12/17",
                    lineColor = colors.progressSparkOrange,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ProgressHeader() {
    val colors = MemoCraftTheme.colors

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Today's Progress",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = colors.progressMiniCardContent
            )

            Text(
                text = "A minimal overview of your tasks",
                style = MaterialTheme.typography.bodySmall,
                color = colors.progressMiniCardContentSecondary
            )
        }

        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(colors.progressCardBadge)
        )
    }
}

@Composable
private fun NeonProgressRing(
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

@Composable
private fun RingLightMiniCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val shape = RoundedCornerShape(18.dp)

    Box(
        modifier = modifier
            .height(82.dp)
            .clip(shape)
            .background(colors.progressMiniCardBackground)
            .border(
                width = 1.dp,
                color = colors.progressCardSurfaceBorder,
                shape = shape
            )
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RingLight(
                modifier = Modifier.size(46.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.progressMiniCardContentSecondary
                )

                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.progressMiniCardContent
                )
            }
        }
    }
}

@Composable
private fun RingLight(
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    Canvas(
        modifier = modifier
    ) {
        val strokeWidth = 5.dp.toPx()

        drawCircle(
            color = colors.progressRingLightOuter,
            radius = size.minDimension / 2.2f,
            center = center
        )

        drawCircle(
            brush = Brush.sweepGradient(
                colors = listOf(
                    colors.progressRingGlowStart,
                    colors.progressRingGlowMiddle,
                    colors.progressRingGlowEnd,
                    colors.progressRingGlowStart
                ),
                center = center
            ),
            radius = size.minDimension / 2.8f,
            center = center,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )

        drawCircle(
            color = colors.progressRingLightInner,
            radius = size.minDimension / 5f,
            center = center
        )

        drawCircle(
            color = colors.progressCardSurfaceBorder,
            radius = size.minDimension / 5f,
            center = center,
            style = Stroke(
                width = 0.8.dp.toPx()
            )
        )
    }
}

@Composable
private fun MiniMetricCard(
    title: String,
    value: String,
    lineColor: Color,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val shape = RoundedCornerShape(18.dp)

    Box(
        modifier = modifier
            .height(82.dp)
            .clip(shape)
            .background(colors.progressMiniCardBackground)
            .border(
                width = 1.dp,
                color = colors.progressCardSurfaceBorder,
                shape = shape
            )
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = colors.progressMiniCardContentSecondary
            )

            Spacer(modifier = Modifier.height(4.dp))

            SparkLine(
                color = lineColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.progressMiniCardContent
            )
        }
    }
}

@Composable
private fun SparkLine(
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

        // خط کم‌رنگ پس‌زمینه
        drawPath(
            path = path,
            color = color.copy(alpha = 0.12f),
            style = Stroke(
                width = 5.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        // خط اصلی که به صورت متحرک از چپ به راست ظاهر می‌شود
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

        // محاسبه موقعیت نقطه‌ی متحرک روی خط
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

        // Glow نقطه‌ی متحرک
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


@Preview(
    name = "ProgressCard - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 330
)
@Composable
private fun ProgressCardLightPreview() {
    MemoCraftAppTheme(
        darkTheme = false
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            ProgressCard()
        }
    }
}

@Preview(
    name = "ProgressCard - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 330
)
@Composable
private fun ProgressCardDarkPreview() {
    MemoCraftAppTheme(
        darkTheme = true
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            ProgressCard()
        }
    }
}
