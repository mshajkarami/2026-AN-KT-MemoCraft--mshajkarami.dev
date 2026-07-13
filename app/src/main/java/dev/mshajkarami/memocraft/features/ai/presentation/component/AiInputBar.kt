package dev.mshajkarami.memocraft.features.ai.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
internal fun AiInputBar(
    isLoading: Boolean,
    onSendClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var message by rememberSaveable { mutableStateOf("") }

    val canSend = message.isNotBlank() && !isLoading

    val buttonContainerColor = when {
        isLoading -> MaterialTheme.colorScheme.primary
        message.isBlank() -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        else -> MaterialTheme.colorScheme.primary
    }

    val buttonContentColor = when {
        isLoading -> MaterialTheme.colorScheme.onPrimary
        message.isBlank() -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
        else -> MaterialTheme.colorScheme.onPrimary
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.07f),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (message.isBlank()) {
                    Text(
                        text = "Tell AI what you need to do...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
                    )
                }

                BasicTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    maxLines = 4
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            FilledIconButton(
                onClick = {
                    val text = message.trim()

                    if (text.isNotEmpty() && !isLoading) {
                        onSendClick(text)
                        message = ""
                    }
                },
                enabled = canSend,
                modifier = Modifier.size(44.dp),
                shape = CircleShape,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = buttonContainerColor,
                    contentColor = buttonContentColor,
                    disabledContainerColor = buttonContainerColor,
                    disabledContentColor = buttonContentColor
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.Send,
                        contentDescription = "Send",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
