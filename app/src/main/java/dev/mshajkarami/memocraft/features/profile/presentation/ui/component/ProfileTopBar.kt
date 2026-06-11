package dev.mshajkarami.memocraft.features.profile.presentation.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import dev.mshajkarami.memocraft.core.presentation.ui.components.BaseTopBar
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun ProfileTopBar(
    onEditProfileClick: () -> Unit
) {
    BaseTopBar(
        actions = {
            IconButton(onClick = onEditProfileClick) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "Edit profile",
                    tint = MemoCraftTheme.colors.topBarActionIconColor
                )
            }
        }
    )
}
