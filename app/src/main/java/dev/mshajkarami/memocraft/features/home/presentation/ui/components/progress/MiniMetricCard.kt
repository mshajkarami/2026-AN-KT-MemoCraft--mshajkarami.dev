package dev.mshajkarami.memocraft.features.home.presentation.ui.components.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
internal fun MiniMetricCard(
    title: String,
    value: String,
    lineColor: Color,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val shape = RoundedCornerShape(18.dp)

    Box(
        modifier = modifier
            .height(96.dp)
            .clip(shape)
            .background(colors.progressMiniCardBackground)
            .border(
                width = 1.dp,
                color = colors.progressCardSurfaceBorder,
                shape = shape
            )
            .padding(
                horizontal = 12.dp,
                vertical = 10.dp
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = colors.progressMiniCardContentSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            SparkLine(
                color = lineColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = colors.progressMiniCardContent,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
    }
}
