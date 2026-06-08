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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksTopBar() {
    val colors = MemoCraftTheme.colors

    TopAppBar(
        title = {
            Text(
                text = "Tasks",
                color = colors.topBarTitleColor
            )
        },
        actions = {
            Row {
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search tasks",
                        tint = colors.topBarActionIconColor
                    )
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Icons.Outlined.NotificationsNone,
                        contentDescription = "Notifications",
                        tint = colors.topBarActionIconColor
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colors.topBarContainerBackground,
            titleContentColor = colors.topBarTitleColor,
            actionIconContentColor = colors.topBarActionIconColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )
}