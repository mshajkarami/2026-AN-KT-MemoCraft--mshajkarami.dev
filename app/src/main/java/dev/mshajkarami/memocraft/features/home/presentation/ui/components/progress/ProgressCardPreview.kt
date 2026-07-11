package dev.mshajkarami.memocraft.features.home.presentation.ui.components.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme

@Preview(
    name = "ProgressCard - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 300
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
            ProgressCard(
                overallProgress = 72,
                totalTasks = 17,
                completedTasks = 8,
                focusTimeText = "4h 12m"
            )
        }
    }
}


@Preview(
    name = "ProgressCard - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 300
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
            ProgressCard(
                overallProgress = 72,
                totalTasks = 17,
                completedTasks = 8,
                focusTimeText = "4h 12m"
            )
        }
    }
}

