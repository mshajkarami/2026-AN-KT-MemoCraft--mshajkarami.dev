package dev.mshajkarami.memocraft.navigation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Home
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.navigation.BottomNavItem

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
    val navShape = remember { bottomBarWithCenterNotchShape() }
    val colors = MemoCraftTheme.colors

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = navShape,
                    clip = false
                ),
            color = colors.bottomNavContainer,
            shape = navShape
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItemView(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Home"
                        )
                    },
                    text = "Home",
                    selected = selectedItem == BottomNavItem.Home,
                    onClick = onHomeClick
                )

                BottomNavItemView(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.TaskAlt,
                            contentDescription = "Tasks"
                        )
                    },
                    text = "Tasks",
                    selected = selectedItem == BottomNavItem.Tasks,
                    onClick = onTasksClick
                )

                Spacer(modifier = Modifier.size(86.dp))

                BottomNavItemView(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.CalendarToday,
                            contentDescription = "Planner"
                        )
                    },
                    text = "Planner",
                    selected = selectedItem == BottomNavItem.Planner,
                    onClick = onPlannerClick
                )

                BottomNavItemView(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.PersonOutline,
                            contentDescription = "Profile"
                        )
                    },
                    text = "Profile",
                    selected = selectedItem == BottomNavItem.Profile,
                    onClick = onProfileClick
                )
            }
        }

        AiCenterButton(
            selected = selectedItem == BottomNavItem.Ai,
            onClick = onAiClick
        )
    }
}


@Composable
private fun BottomNavItemView(
    icon: @Composable () -> Unit,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = MemoCraftTheme.colors

    val contentColor = if (selected) {
        colors.bottomNavSelectedContent
    } else {
        colors.bottomNavUnselectedContent
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = onClick
        ) {
            CompositionLocalProvider(
                LocalContentColor provides contentColor
            ) {
                icon()
            }
        }

        Text(
            text = text,
            color = contentColor,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}


@Composable
private fun AiCenterButton(
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = MemoCraftTheme.colors

    val mainColor = colors.bottomNavSelectedIndicator
    val textColor = if (selected) {
        colors.bottomNavSelectedContent
    } else {
        colors.bottomNavUnselectedContent
    }

    Column(
        modifier = Modifier.offset(y = (-20).dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .size(52.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    clip = false
                ),
            shape = CircleShape,
            color = mainColor,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            onClick = onClick
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = "AI",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "AI",
            color = textColor,
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }

}



private fun bottomBarWithCenterNotchShape(): Shape {
    return object : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            val path = Path()

            with(density) {
                val cornerRadius = 26.dp.toPx()
                val notchRadius = 36.dp.toPx()
                val notchDepth = 38.dp.toPx()
                val notchShoulder = 16.dp.toPx()
                val centerX = size.width / 2f

                path.moveTo(0f, size.height)
                path.lineTo(0f, cornerRadius)

                path.quadraticBezierTo(
                    0f, 0f,
                    cornerRadius, 0f
                )

                path.lineTo(centerX - notchRadius - notchShoulder, 0f)

                path.cubicTo(
                    centerX - notchRadius,
                    0f,
                    centerX - notchRadius + 4.dp.toPx(),
                    notchDepth,
                    centerX,
                    notchDepth
                )

                path.cubicTo(
                    centerX + notchRadius - 4.dp.toPx(),
                    notchDepth,
                    centerX + notchRadius,
                    0f,
                    centerX + notchRadius + notchShoulder,
                    0f
                )

                path.lineTo(size.width - cornerRadius, 0f)

                path.quadraticBezierTo(
                    size.width, 0f,
                    size.width, cornerRadius
                )

                path.lineTo(size.width, size.height)
                path.lineTo(0f, size.height)
                path.close()
            }

            return Outline.Generic(path)
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
    MemoCraftAppTheme(
        darkTheme = false
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavBar(
                selectedItem = BottomNavItem.Home
            )
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
    MemoCraftAppTheme(
        darkTheme = true
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavBar(
                selectedItem = BottomNavItem.Ai
            )
        }
    }
}

