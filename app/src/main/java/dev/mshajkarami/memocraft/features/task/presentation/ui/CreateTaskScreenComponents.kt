package dev.mshajkarami.memocraft.features.task.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.presentation.component.card.rememberTaskCardBrushes

@Composable
fun SaveTaskButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (enabled) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
    }

    val contentColor = if (enabled) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(containerColor)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = contentColor
            )

            Text(
                text = "Save Task",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}

@Composable
fun TaskInputContainer(
    label: String,
    content: @Composable () -> Unit
) {
    val colors = MemoCraftTheme.colors
    val brushes = rememberTaskCardBrushes()
    val shape = RoundedCornerShape(20.dp)

    androidx.compose.foundation.layout.Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = colors.progressMiniCardContent.copy(alpha = 0.7f),
            modifier = Modifier.padding(start = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = if (brushes.isLightTheme) 8.dp else 12.dp,
                    shape = shape,
                    ambientColor = colors.compactTaskCardShadowAmbient,
                    spotColor = colors.compactTaskCardShadowSpot
                )
                .clip(shape)
                .background(brushes.backgroundBrush)
                .border(1.dp, brushes.borderBrush, shape)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(brushes.highlightBrush)
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(1.dp)
                    .clip(RoundedCornerShape(19.dp))
                    .border(
                        width = 1.dp,
                        color = colors.compactTaskCardInnerBorder,
                        shape = RoundedCornerShape(19.dp)
                    )
            )

            Box(
                modifier = Modifier.padding(4.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun PrioritySelector(
    selectedPriority: TaskPriority,
    onPrioritySelected: (TaskPriority) -> Unit
) {
    val colors = MemoCraftTheme.colors

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TaskPriority.entries.forEach { priority ->
            val isSelected = priority == selectedPriority

            val contentColor = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                colors.progressMiniCardContent
            }

            val containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                colors.bottomNavContainer.copy(alpha = 0.5f)
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(containerColor)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color.Transparent else colors.compactTaskCardInnerBorder,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onPrioritySelected(priority) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = priority.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = contentColor,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}

@Composable
internal fun TransparentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    suffix: @Composable (() -> Unit)? = null
) {
    val colors = MemoCraftTheme.colors

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = colors.progressMiniCardContent.copy(alpha = 0.5f)
            )
        },
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = colors.progressMiniCardContent,
            unfocusedTextColor = colors.progressMiniCardContent,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        suffix = suffix
    )
}
