package dev.mshajkarami.memocraft.features.home.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun GreetingSection(
    userName: String = "Mohamad Saleh"
) {
    val colors = MemoCraftTheme.colors

    Column {
        Text(
            text = "Good morning,",
            style = MaterialTheme.typography.titleMedium,
            color = colors.topBarStatusColor
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$userName!",
                style = MaterialTheme.typography.headlineLarge,
                color = colors.topBarTitleColor
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "\uD83D\uDC4B",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Text(
            text = "Let's make today productive.",
            style = MaterialTheme.typography.bodyLarge,
            color = colors.topBarStatusColor
        )
    }
}
