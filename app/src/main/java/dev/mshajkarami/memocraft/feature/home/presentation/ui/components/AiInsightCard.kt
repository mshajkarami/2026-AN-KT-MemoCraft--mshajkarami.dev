package dev.mshajkarami.memocraft.feature.home.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun AiInsightCard() {
    val colors = MemoCraftTheme.colors

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.aiInsightCardContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "✨ You're on track! 🎯",
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.aiInsightCardTitle
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Focus on 2 high-priority tasks\nto finish your day strong.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.aiInsightCardDescription,
                    lineHeight = 20.sp
                )
            }

            Text(
                text = "\uD83E\uDD16",
                fontSize = 54.sp,
                color = colors.aiInsightCardEmoji
            )
        }
    }
}
