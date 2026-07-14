package dev.mshajkarami.memocraft.app.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.designsystem.theme.MemoCraftTheme

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedItem: BottomNavItem = BottomNavItem.Tasks,
    onTasksClick: () -> Unit = {},
    onAiClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val colors = MemoCraftTheme.colors

    val selectedColor = colors.bottomNavSelectedContent
    val unselectedColor = colors.bottomNavUnselectedContent.copy(alpha = 0.72f)
    val containerColor = colors.bottomNavContainer

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = containerColor,
        shadowElevation = 12.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(76.dp)
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItemButton(
                label = "Tasks",
                selected = selectedItem == BottomNavItem.Tasks,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                onClick = onTasksClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.TaskAlt,
                    contentDescription = "Tasks",
                    modifier = Modifier.size(24.dp)
                )
            }

            BottomNavItemButton(
                label = "AI",
                selected = selectedItem == BottomNavItem.Ai,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                onClick = onAiClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = "AI Assistant",
                    modifier = Modifier.size(24.dp)
                )
            }

            BottomNavItemButton(
                label = "Profile",
                selected = selectedItem == BottomNavItem.Profile,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                onClick = onProfileClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = "Profile",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun BottomNavItemButton(
    label: String,
    selected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    val contentColor = if (selected) {
        selectedColor
    } else {
        unselectedColor
    }

    IconButton(
        modifier = Modifier.size(
            width = 58.dp,
            height = 60.dp
        ),
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CompositionLocalProvider(
                LocalContentColor provides contentColor
            ) {
                icon()
            }

            Text(
                text = label,
                color = contentColor,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
