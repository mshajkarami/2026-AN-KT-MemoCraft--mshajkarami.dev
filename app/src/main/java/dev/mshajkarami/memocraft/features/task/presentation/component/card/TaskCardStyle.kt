package dev.mshajkarami.memocraft.features.task.presentation.component.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.material3.MaterialTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
internal data class TaskCardBrushes(
    val backgroundBrush: Brush,
    val borderBrush: Brush,
    val highlightBrush: Brush,
    val isLightTheme: Boolean
)

@Composable
internal fun rememberTaskCardBrushes(): TaskCardBrushes {
    val colors = MemoCraftTheme.colors
    val isLightTheme = MaterialTheme.colorScheme.background.luminance() > 0.5f
    val overlayColor = colors.compactTaskCardGlassOverlay

    val backgroundBrush = if (isLightTheme) {
        Brush.linearGradient(
            colors = listOf(
                overlayColor.copy(alpha = 0.82f),
                colors.progressCardBackground.copy(alpha = 0.62f),
                colors.progressCardBackgroundSecondary.copy(alpha = 0.42f),
                colors.progressRingGlowStart.copy(alpha = 0.08f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                colors.progressCardBackground.copy(alpha = 0.68f),
                colors.progressCardBackgroundSecondary.copy(alpha = 0.44f),
                overlayColor.copy(alpha = 0.06f)
            )
        )
    }

    val borderBrush = if (isLightTheme) {
        Brush.linearGradient(
            colors = listOf(
                overlayColor.copy(alpha = 0.95f),
                colors.progressRingGlowStart.copy(alpha = 0.24f),
                colors.progressMiniCardContent.copy(alpha = 0.08f),
                overlayColor.copy(alpha = 0.62f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                overlayColor.copy(alpha = 0.32f),
                colors.progressCardSurfaceBorder.copy(alpha = 0.34f),
                overlayColor.copy(alpha = 0.10f)
            )
        )
    }

    val highlightBrush = if (isLightTheme) {
        Brush.radialGradient(
            colors = listOf(
                colors.progressRingGlowStart.copy(alpha = 0.13f),
                Color.Transparent
            ),
            radius = 520f,
            center = Offset.Zero
        )
    } else {
        Brush.radialGradient(
            colors = listOf(
                overlayColor.copy(alpha = 0.08f),
                Color.Transparent
            ),
            radius = 460f,
            center = Offset.Zero
        )
    }

    return TaskCardBrushes(
        backgroundBrush = backgroundBrush,
        borderBrush = borderBrush,
        highlightBrush = highlightBrush,
        isLightTheme = isLightTheme
    )
}