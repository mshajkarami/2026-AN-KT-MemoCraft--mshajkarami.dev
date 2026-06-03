package dev.mshajkarami.memocraft.feature.home.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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

@Composable
fun ProgressCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF132C46),
                        Color(0xFF24194B),
                        Color(0xFF34134D)
                    )
                )
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
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MiniMetricCard(
                            title = "Done",
                            value = "8",
                            lineColor = Color(0xFF45D6FF),
                            modifier = Modifier.weight(1f)
                        )

                        MiniMetricCard(
                            title = "Pending",
                            value = "5",
                            lineColor = Color(0xFFA46CFF),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MiniMetricCard(
                    title = "Tasks",
                    value = "17",
                    lineColor = Color(0xFF54B9FF),
                    modifier = Modifier.weight(1f)
                )

                MiniMetricCard(
                    title = "Velocity",
                    value = "72%",
                    lineColor = Color(0xFFB46DFF),
                    modifier = Modifier.weight(1f)
                )

                MiniMetricCard(
                    title = "Progress",
                    value = "12/17",
                    lineColor = Color(0xFFFFB36A),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ProgressHeader() {
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
                color = Color.White
            )

            Text(
                text = "A minimal overview of your tasks",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.62f)
            )
        }

        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(Color(0xFF5CE1FF))
        )
    }
}

@Composable
fun NeonProgressRing(
    percentage: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            val strokeWidth = 15.dp.toPx()
            val glowStrokeWidth = 24.dp.toPx()
            val sweep = 360f * (percentage / 100f)

            val arcSize = Size(
                width = size.width - glowStrokeWidth,
                height = size.height - glowStrokeWidth
            )

            val topLeft = Offset(
                x = glowStrokeWidth / 2f,
                y = glowStrokeWidth / 2f
            )

            // Soft outer glow
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFF36D8FF).copy(alpha = 0.20f),
                        Color(0xFF755CFF).copy(alpha = 0.28f),
                        Color(0xFFE070FF).copy(alpha = 0.24f),
                        Color(0xFF36D8FF).copy(alpha = 0.20f)
                    )
                ),
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = glowStrokeWidth,
                    cap = StrokeCap.Round
                )
            )

            // Track
            drawArc(
                color = Color.White.copy(alpha = 0.12f),
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

            // Main progress
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFF42D9FF),
                        Color(0xFF576DFF),
                        Color(0xFFB260FF),
                        Color(0xFF42D9FF)
                    )
                ),
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )

            // Small highlight dot at top
            drawCircle(
                color = Color.White.copy(alpha = 0.55f),
                radius = 3.5.dp.toPx(),
                center = Offset(size.width / 2f, glowStrokeWidth / 2f)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Text(
                text = "Completed",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.58f)
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
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RingLight(
                modifier = Modifier.size(46.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.58f)
                )

                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun RingLight(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
    ) {
        val stroke = 5.dp.toPx()

        drawCircle(
            color = Color(0xFF48DFFF).copy(alpha = 0.16f),
            radius = size.minDimension / 2.2f
        )

        drawCircle(
            brush = Brush.sweepGradient(
                colors = listOf(
                    Color(0xFF44DFFF),
                    Color(0xFF756BFF),
                    Color(0xFFD66BFF),
                    Color(0xFF44DFFF)
                )
            ),
            radius = size.minDimension / 2.8f,
            style = Stroke(
                width = stroke,
                cap = StrokeCap.Round
            )
        )

        drawCircle(
            color = Color.White.copy(alpha = 0.10f),
            radius = size.minDimension / 5f
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
    Box(
        modifier = modifier
            .height(82.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.075f))
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.58f)
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
                color = Color.White
            )
        }
    }
}

@Composable
private fun SparkLine(
    color: Color,
    modifier: Modifier = Modifier
) {
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

        drawPath(
            path = path,
            color = color.copy(alpha = 0.30f),
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
}

@Preview(
    name = "ProgressCard - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 310
)
@Composable
private fun ProgressCardLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
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
    heightDp = 310
)
@Composable
private fun ProgressCardDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
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
