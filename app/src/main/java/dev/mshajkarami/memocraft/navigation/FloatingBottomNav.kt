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

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 14.dp,
                bottom = 28.dp
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .shadow(
                    elevation = 18.dp,
                    shape = RoundedCornerShape(38.dp),
                    clip = false
                ),
            color = containerColor,
            shape = RoundedCornerShape(38.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavIconButton(
                    selected = selectedItem == BottomNavItem.Home,
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onHomeClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "Home",
                        modifier = Modifier.size(26.dp)
                    )
                }

                BottomNavIconButton(
                    selected = selectedItem == BottomNavItem.Tasks,
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onTasksClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.TaskAlt,
                        contentDescription = "Tasks",
                        modifier = Modifier.size(25.dp)
                    )
                }

                CenterNavIconButton(
                    selected = selectedItem == BottomNavItem.Ai,
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    indicatorColor = colors.bottomNavSelectedIndicator,
                    onClick = onAiClick
                )

                BottomNavIconButton(
                    selected = selectedItem == BottomNavItem.Planner,
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onPlannerClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.NotificationsNone,
                        contentDescription = "Planner",
                        modifier = Modifier.size(26.dp)
                    )
                }

                BottomNavIconButton(
                    selected = selectedItem == BottomNavItem.Profile,
                    selectedColor = selectedColor,
                    unselectedColor = unselectedColor,
                    onClick = onProfileClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PersonOutline,
                        contentDescription = "Profile",
                        modifier = Modifier.size(27.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavIconButton(
    selected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    val contentColor = if (selected) selectedColor else unselectedColor

    Box(
        modifier = Modifier.size(width = 48.dp, height = 56.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        IconButton(
            modifier = Modifier
                .size(42.dp)
                .align(Alignment.TopCenter),
            onClick = onClick
        ) {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                icon()
            }
        }

        if (selected) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 2.dp)
                    .size(5.dp)
                    .background(selectedColor, CircleShape)
            )
        }
    }
}



@Composable
private fun CenterNavIconButton(
    selected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    indicatorColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(width = 72.dp, height = 78.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = Modifier
                .size(58.dp)
                .align(Alignment.TopCenter),
            shape = CircleShape,
            color = indicatorColor.copy(alpha = 0.72f),
            contentColor = selectedColor,
            onClick = onClick
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = "AI",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        if (selected) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 1.dp)
                    .size(5.dp)
                    .background(selectedColor, CircleShape)
            )
        }
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
