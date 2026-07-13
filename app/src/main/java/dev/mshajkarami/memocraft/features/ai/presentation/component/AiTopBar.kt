package dev.mshajkarami.memocraft.features.ai.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import dev.mshajkarami.memocraft.core.designsystem.component.BaseTopBar
import dev.mshajkarami.memocraft.core.designsystem.theme.MemoCraftTheme

@Composable
fun AiTopBar(
    onActionClick: () -> Unit = {}
) {
    BaseTopBar(
        title = "AI Assistant",
        subtitle = "Turn your ideas into actionable tasks",
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = "AI Assistant",
                    tint = MemoCraftTheme.colors.topBarActionIconColor
                )
            }
        }
    )
}
