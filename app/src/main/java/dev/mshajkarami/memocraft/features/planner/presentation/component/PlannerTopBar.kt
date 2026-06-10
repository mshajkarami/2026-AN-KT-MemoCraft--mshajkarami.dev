package dev.mshajkarami.memocraft.features.planner.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.components.BaseTopBar

@Composable
fun PlannerTopBar(
    onCalendarClick: () -> Unit = {}
) {
    BaseTopBar(
        title = "Planner",
        subtitle = "Organize your day with clarity",
        actions = {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onCalendarClick) {
                    Icon(
                        imageVector = Icons.Rounded.CalendarMonth,
                        contentDescription = "Calendar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    )
}
