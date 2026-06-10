package dev.mshajkarami.memocraft.navigation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NotificationsNone
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedItem: BottomNavItem = BottomNavItem.Home,
    onHomeClick: () -> Unit = {},
    onTasksClick: () -> Unit = {},
    onAiClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItemButton(
                label = "Home",
                selected = selectedItem == BottomNavItem.Home,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                onClick = onHomeClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(24.dp)
                )
            }

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

            CenterNavItemButton(
                label = "AI",
                selected = selectedItem == BottomNavItem.Ai,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                indicatorColor = colors.bottomNavSelectedIndicator,
                onClick = onAiClick
            )


            BottomNavItemButton(
                label = "Planner",
                selected = selectedItem == BottomNavItem.Planner,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                onClick = onPlannerClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.NotificationsNone,
                    contentDescription = "Planner",
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
    val contentColor = if (selected) selectedColor else unselectedColor

    IconButton(
        modifier = Modifier.size(width = 58.dp, height = 60.dp),
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
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

@Composable
private fun CenterNavItemButton(
    label: String,
    selected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    indicatorColor: Color,
    onClick: () -> Unit
) {
    val labelColor = if (selected) selectedColor else unselectedColor

    Column(
        modifier = Modifier.size(width = 72.dp, height = 86.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Surface(
            modifier = Modifier
                .size(54.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    clip = false
                ),
            shape = CircleShape,
            color = indicatorColor,
            contentColor = Color.White,
            onClick = onClick
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Text(
            text = label,
            color = labelColor,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}


@Preview(
    name = "Bottom Navigation Bar - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF
)
@Composable
private fun BottomNavBarLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavBar(selectedItem = BottomNavItem.Ai)
        }
    }
}

@Preview(
    name = "Bottom Navigation Bar - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun BottomNavBarDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavBar(selectedItem = BottomNavItem.Ai)
        }
    }
}
