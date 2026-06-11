package dev.mshajkarami.memocraft.features.tasks.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.components.BaseTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksTopBar(
    onSearchClick: () -> Unit
) {
    BaseTopBar(
        title = "Tasks",
        subtitle = "Manage your daily work",
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search tasks",
                    tint = MemoCraftTheme.colors.topBarActionIconColor
                )
            }
        }
    )
}
