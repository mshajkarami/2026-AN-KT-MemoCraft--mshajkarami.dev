package dev.mshajkarami.memocraft.features.profile.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.profile.presentation.ui.component.ProfileTopBar

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onEditProfileClick: () -> Unit = {},
    onAccountClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onAppearanceClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onRateAppClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val preferences = remember {
        listOf(
            ProfileMenuItem(
                title = "Account",
                subtitle = "Manage your personal information",
                icon = Icons.Outlined.PersonOutline,
                onClick = onAccountClick
            ),
            ProfileMenuItem(
                title = "Notifications",
                subtitle = "Control reminders and alerts",
                icon = Icons.Outlined.Notifications,
                onClick = onNotificationsClick
            ),
            ProfileMenuItem(
                title = "Appearance",
                subtitle = "Theme, colors and display preferences",
                icon = Icons.Outlined.ColorLens,
                onClick = onAppearanceClick
            ),
            ProfileMenuItem(
                title = "Language",
                subtitle = "Choose your preferred app language",
                icon = Icons.Outlined.Language,
                onClick = onLanguageClick
            ),
            ProfileMenuItem(
                title = "Privacy",
                subtitle = "Privacy, security and permissions",
                icon = Icons.Outlined.PrivacyTip,
                onClick = onPrivacyClick
            )
        )
    }

    val appSettings = remember {
        listOf(
            ProfileMenuItem(
                title = "About App",
                subtitle = "Learn more about MemoCraft",
                icon = Icons.Outlined.Info,
                onClick = onAboutClick
            ),
            ProfileMenuItem(
                title = "Help & Support",
                subtitle = "Get help or contact support",
                icon = Icons.Outlined.HelpOutline,
                onClick = onHelpClick
            ),
            ProfileMenuItem(
                title = "Rate App",
                subtitle = "Share your feedback with us",
                icon = Icons.Outlined.StarOutline,
                onClick = onRateAppClick
            ),
            ProfileMenuItem(
                title = "Logout",
                subtitle = "Sign out of your account",
                icon = Icons.Outlined.Logout,
                onClick = onLogoutClick,
                isDestructive = true
            )
        )
    }

    ProfileScreenContent(
        preferences = preferences,
        appSettings = appSettings,
        onEditProfileClick = onEditProfileClick,
        modifier = modifier
    )
}

@Composable
private fun ProfileScreenContent(
    preferences: List<ProfileMenuItem>,
    appSettings: List<ProfileMenuItem>,
    onEditProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.bottomNavContainer)
    ) {
        ProfileTopBar(
            onEditProfileClick = onEditProfileClick
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 20.dp,
                bottom = 110.dp
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                ProfileInfoCard()
            }

            item {
                ProfileStatsRow()
            }

            item {
                SectionTitle(title = "Preferences")
            }

            item {
                SettingsGroupCard(items = preferences)
            }

            item {
                SectionTitle(title = "General Settings")
            }

            item {
                SettingsGroupCard(items = appSettings)
            }

            item {
                AppVersionFooter(version = "MemoCraft v1.0.0")
            }
        }
    }

}

@Composable
private fun ProfileHeader(
    onEditProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Manage your account and app settings",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f)
            )
        }

        Surface(
            modifier = Modifier
                .size(44.dp)
                .clickable(onClick = onEditProfileClick),
            shape = RoundedCornerShape(14.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "Edit profile",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun ProfileInfoCard() {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MS",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Mohammad Shajkarami",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "m.shajkarami@example.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Productive thinker • Daily planner enthusiast",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileStatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfileStatCard(
            modifier = Modifier.weight(1f),
            value = "24",
            label = "Done Tasks"
        )
        ProfileStatCard(
            modifier = Modifier.weight(1f),
            value = "18h",
            label = "Focus Time"
        )
        ProfileStatCard(
            modifier = Modifier.weight(1f),
            value = "12",
            label = "Day Streak"
        )
    }
}

@Composable
private fun ProfileStatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun SectionTitle(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun SettingsGroupCard(
    items: List<ProfileMenuItem>
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                SettingRow(
                    item = item
                )

                if (index != items.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingRow(
    item: ProfileMenuItem
) {
    val titleColor = if (item.isDestructive) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    val subtitleColor = if (item.isDestructive) {
        MaterialTheme.colorScheme.error.copy(alpha = 0.75f)
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
    }

    val iconTint = if (item.isDestructive) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = item.onClick)
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(iconTint.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = iconTint
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = titleColor
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = subtitleColor
            )
        }

        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
        )
    }
}

@Composable
private fun AppVersionFooter(
    version: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = version,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
    }
}

private data class ProfileMenuItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val isDestructive: Boolean = false
)

@Preview(
    name = "Profile Screen - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun ProfileScreenLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
        ProfileScreen()
    }
}

@Preview(
    name = "Profile Screen - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun ProfileScreenDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
        ProfileScreen()
    }
}
