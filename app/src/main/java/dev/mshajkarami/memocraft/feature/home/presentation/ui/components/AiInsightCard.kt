package dev.mshajkarami.memocraft.feature.home.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import dev.mshajkarami.memocraft.R
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun AiInsightCard(
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val cardShape = RoundedCornerShape(28.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
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
        ) {
            AiInsightCardDecorations()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        top = 14.dp,
                        end = 82.dp,
                        bottom = 14.dp
                    )
            ) {
                AiInsightBadge()

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "✨ You're on track! 🎯",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.progressMiniCardContent
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Focus on 2 high-priority tasks\nto finish your day strong.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.progressMiniCardContentSecondary,
                    lineHeight = 20.sp
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 18.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(colors.progressCardBadge)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ai_robot_3d),
            contentDescription = "AI robot",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = (-10).dp, y = (-4).dp)
                .size(82.dp)
                .rotate(-4f)
                .zIndex(2f)
        )
    }
}

@Composable
private fun BoxScope.AiInsightCardDecorations() {
    val colors = MemoCraftTheme.colors

    Canvas(
        modifier = Modifier.matchParentSize()
    ) {
        val width = size.width
        val height = size.height

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    colors.progressRingGlowEnd.copy(alpha = 0.22f),
                    colors.progressRingGlowMiddle.copy(alpha = 0.10f),
                    androidx.compose.ui.graphics.Color.Transparent
                ),
                center = Offset(width * 0.88f, height * 0.35f),
                radius = width * 0.45f
            ),
            radius = width * 0.45f,
            center = Offset(width * 0.88f, height * 0.35f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    colors.progressRingGlowStart.copy(alpha = 0.12f),
                    androidx.compose.ui.graphics.Color.Transparent
                ),
                center = Offset(width * 0.10f, height * 0.20f),
                radius = width * 0.35f
            ),
            radius = width * 0.35f,
            center = Offset(width * 0.10f, height * 0.20f)
        )

        drawLine(
            brush = Brush.linearGradient(
                colors = listOf(
                    colors.progressRingGlowStart.copy(alpha = 0.00f),
                    colors.progressRingGlowStart.copy(alpha = 0.35f),
                    colors.progressRingGlowMiddle.copy(alpha = 0.30f),
                    colors.progressRingGlowEnd.copy(alpha = 0.00f)
                )
            ),
            start = Offset(width * 0.06f, height * 0.88f),
            end = Offset(width * 0.72f, height * 0.88f),
            strokeWidth = 1.4.dp.toPx()
        )

        drawCircle(
            color = colors.progressCardSurfaceBorder.copy(alpha = 0.45f),
            radius = width * 0.18f,
            center = Offset(width * 0.87f, height * 0.48f),
            style = Stroke(
                width = 1.dp.toPx()
            )
        )
    }
}

@Composable
private fun AiInsightBadge() {
    val colors = MemoCraftTheme.colors
    val shape = RoundedCornerShape(999.dp)

    Box(
        modifier = Modifier
            .clip(shape)
            .background(colors.progressMiniCardBackground)
            .border(
                width = 1.dp,
                color = colors.progressCardSurfaceBorder,
                shape = shape
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = "AI Insight",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = colors.progressMiniCardContentSecondary
        )
    }
}


@Preview(
    name = "AiInsightCard - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430
)
@Composable
private fun AiInsightCardLightPreview() {
    MemoCraftAppTheme(
        darkTheme = false
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                AiInsightCard()
            }
        }
    }
}

@Preview(
    name = "AiInsightCard - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430
)
@Composable
private fun AiInsightCardDarkPreview() {
    MemoCraftAppTheme(
        darkTheme = true
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                AiInsightCard()
            }
        }
    }
}
