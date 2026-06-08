package dev.mshajkarami.memocraft.features.task.presentation.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun MiniCompletedTick(
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        colors.taskCompletionBadgeBackground,
                        colors.progressMiniCardContent.copy(alpha = 0.06f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = colors.progressMiniCardContent.copy(alpha = 0.16f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Check,
            contentDescription = "Completed",
            tint = colors.taskCompletionBadgeIcon.copy(alpha = 0.74f),
            modifier = Modifier.size(14.dp)
        )
    }
}
