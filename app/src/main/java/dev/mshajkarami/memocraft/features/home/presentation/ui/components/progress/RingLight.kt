package dev.mshajkarami.memocraft.features.home.presentation.ui.components.progress

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
internal fun RingLight(
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
