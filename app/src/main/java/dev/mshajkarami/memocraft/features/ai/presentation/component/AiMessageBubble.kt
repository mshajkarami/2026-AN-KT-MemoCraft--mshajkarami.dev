package dev.mshajkarami.memocraft.features.ai.presentation.component

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.features.ai.presentation.model.AiChatMessageUiModel
import dev.mshajkarami.memocraft.features.ai.presentation.model.DetectedTaskUiModel

@Composable
internal fun AiMessageBubble(
    message: AiChatMessageUiModel,
    onAddDetectedTaskClick: (DetectedTaskUiModel) -> Unit
) {
    val isUser = message.isFromUser

    if (isUser) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(0.82f),
                shape = RoundedCornerShape(
                    topStart = 22.dp,
                    topEnd = 22.dp,
                    bottomStart = 22.dp,
                    bottomEnd = 6.dp
                ),
                color = MaterialTheme.colorScheme.primary,
                tonalElevation = 0.dp
            ) {
                Text(
                    text = message.content,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 13.dp
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        return
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            AiAvatar()

            Spacer(modifier = Modifier.width(10.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(0.82f),
                shape = RoundedCornerShape(
                    topStart = 22.dp,
                    topEnd = 22.dp,
                    bottomStart = 6.dp,
                    bottomEnd = 22.dp
                ),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 0.dp
            ) {
                Text(
                    text = message.content,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 13.dp
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        if (message.detectedTasks.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))

            DetectedTasksCard(
                tasks = message.detectedTasks,
                onDetectedTaskClick = onAddDetectedTaskClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 44.dp,
                        end = 0.dp
                    )
            )
        }
    }
}

@Composable
private fun AiAvatar() {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.AutoAwesome,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp)
        )
    }
}
