package dev.mshajkarami.memocraft.navigation

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
            color = Color.White,
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
    val selectedColor = Color(0xFF2F2FA2)
    val unSelectedColor = Color(0xFF9B9BA1)
    val contentColor = if (selected) selectedColor else unSelectedColor

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
    val mainColor = Color(0xFF2F2FA2)
    val unSelectedColor = Color(0xFF9B9BA1)

    Column(
        modifier = Modifier.offset(y = (-28).dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .size(66.dp)
                .shadow(
                    elevation = 18.dp,
                    shape = CircleShape,
                    clip = false
                ),
            shape = CircleShape,
            color = mainColor,
            onClick = onClick
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = "AI",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "AI",
            color = if (selected) mainColor else unSelectedColor,
            fontSize = 11.sp,
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
                val notchRadius = 38.dp.toPx()
                val notchDepth = 34.dp.toPx()
                val centerX = size.width / 2f

                path.moveTo(0f, size.height)
                path.lineTo(0f, cornerRadius)

                path.quadraticBezierTo(
                    0f, 0f,
                    cornerRadius, 0f
                )

                path.lineTo(centerX - notchRadius - 18.dp.toPx(), 0f)

                path.cubicTo(
                    centerX - notchRadius,
                    0f,
                    centerX - notchRadius + 6.dp.toPx(),
                    notchDepth,
                    centerX,
                    notchDepth
                )

                path.cubicTo(
                    centerX + notchRadius - 6.dp.toPx(),
                    notchDepth,
                    centerX + notchRadius,
                    0f,
                    centerX + notchRadius + 18.dp.toPx(),
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
    name = "Bottom Navigation Bar",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
private fun BottomNavBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomNavBar()
    }
}