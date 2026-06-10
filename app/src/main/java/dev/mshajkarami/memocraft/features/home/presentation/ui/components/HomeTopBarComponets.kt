package dev.mshajkarami.memocraft.features.home.presentation.ui.components

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.R
import dev.mshajkarami.memocraft.core.presentation.ui.components.BaseTopBar
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme

@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    hasNotification: Boolean
) {
    BaseTopBar(
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Outlined.Search, "Search", tint = MemoCraftTheme.colors.topBarActionIconColor)
            }
            NotificationAction(
                hasNotification = hasNotification,
                onClick = onNotificationClick
            )
        }
    )
}

@Composable
fun NotificationAction(hasNotification: Boolean, onClick: () -> Unit) {
    Box {
        IconButton(onClick = onClick) {
            Icon(Icons.Outlined.NotificationsNone, "Notifications", tint = MemoCraftTheme.colors.topBarActionIconColor)
        }
        if (hasNotification) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MemoCraftTheme.colors.topBarNotificationBadge)
                    .align(Alignment.TopEnd)
                    .offset(x = (-4).dp, y = 4.dp) // تنظیم دقیق جایگاه نقطه
            )
        }
    }
}

@Preview(
    name = "HomeTopBar - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 88
)
@Composable
private fun HomeTopBarLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
        HomeTopBar(
            onSearchClick = {}, onNotificationClick = {}, hasNotification = true
        )
    }
}

@Preview(
    name = "HomeTopBar - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 88
)
@Composable
private fun HomeTopBarDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
        HomeTopBar(
            onSearchClick = {}, onNotificationClick = {}, hasNotification = true
        )
    }
}
