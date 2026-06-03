package dev.mshajkarami.memocraft.core.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = Color.White,

    secondary = BrandSecondary,
    onSecondary = Color.White,

    background = BackgroundLight,
    onBackground = TextPrimaryLight,

    surface = CardBackgroundLight,
    onSurface = TextPrimaryLight,

    surfaceVariant = BrandPrimaryContainer,
    onSurfaceVariant = TextSecondaryLight,

    error = ErrorLight,
    onError = Color.White,

    outline = DividerColorLight
)

private val DarkColorScheme = darkColorScheme(
    primary = BrandSecondaryLight,
    onPrimary = Color.White,

    secondary = BrandAccent,
    onSecondary = Color.White,

    background = BackgroundDark,
    onBackground = TextPrimaryDark,

    surface = CardBackgroundDark,
    onSurface = TextPrimaryDark,

    surfaceVariant = TopBarAvatarBackgroundDark,
    onSurfaceVariant = TextSecondaryDark,

    error = ErrorDark,
    onError = Color.White,

    outline = DividerColorDark
)

private val LightMemoCraftColors = MemoCraftThemeColors(
    // Bottom Navigation
    bottomNavContainer = BottomNavContainerLight,
    bottomNavSelectedItemBackground = BottomNavSelectedItemBackgroundLight,
    bottomNavSelectedIndicator = BottomNavSelectedIndicatorLight,
    bottomNavSelectedContent = BottomNavSelectedContentLight,
    bottomNavUnselectedContent = BottomNavUnselectedContentLight,

    // Top Bar
    topBarBackground = TopBarBackgroundLight,
    topBarContainerBackground = TopBarContainerBackgroundLight,
    topBarAvatarBackground = TopBarAvatarBackgroundLight,
    topBarAvatarContent = TopBarAvatarContentLight,
    topBarAvatarBorder = TopBarAvatarBorderLight,
    topBarTitleColor = TopBarTitleColorLight,
    topBarSubtitleColor = TopBarSubtitleColorLight,
    topBarStatusColor = TopBarStatusColorLight,
    topBarMenuIconColor = TopBarMenuIconColorLight,
    topBarActionIconColor = TopBarActionIconColorLight,
    topBarNotificationBadge = TopBarNotificationBadgeLight,
    topBarStatLabelColor = TopBarStatLabelColorLight,
    topBarStatValueColor = TopBarStatValueColorLight,
    topBarDivider = TopBarDividerLight,
    topBarShadowColor = TopBarShadowColorLight,

    // Profile
    profileBackgroundColor = ProfileBackgroundLight,
    profileBorderColor = ProfileBorderLight,
    profileIconColor = ProfileIconLight,

    // AI Insight Card
    aiInsightCardContainer = AiInsightCardContainerLight,
    aiInsightCardTitle = AiInsightCardTitleLight,
    aiInsightCardDescription = AiInsightCardDescriptionLight,
    aiInsightCardEmoji = AiInsightCardEmojiLight,

    // Semantic Colors
    successColor = SuccessLight,
    successContainer = SuccessContainerLight,
    successContent = SuccessContentLight,

    errorColor = ErrorLight,
    errorContainer = ErrorContainerLight,
    errorContent = ErrorContentLight,

    accentColor = AccentLight,
    accentContainer = AccentContainerLight,
    accentContent = AccentContentLight,

    // Progress Card
    progressCardGradientStart = ProgressCardGradientStartLight,
    progressCardGradientEnd = ProgressCardGradientEndLight,
    progressCardContent = ProgressCardContentLight,
    progressCardContentSecondary = ProgressCardContentSecondaryLight,

    progressRingTrack = ProgressRingTrackLight,
    progressRingProgressStart = ProgressRingProgressStartLight,
    progressRingProgressMiddle = ProgressRingProgressMiddleLight,
    progressRingProgressEnd = ProgressRingProgressEndLight,

    progressCompletedDot = ProgressCompletedDotLight,
    progressInProgressDot = ProgressInProgressDotLight,
    progressPendingDot = ProgressPendingDotLight,
    // Section Header
    sectionHeaderTitleColor = SectionHeaderTitleLight,
    sectionHeaderActionColor = SectionHeaderActionLight,
    // Task Card
    taskCardContainer = TaskCardContainerLight,
    taskCardTitle = TaskCardTitleLight,
    taskCardSubtitle = TaskCardSubtitleLight,
    taskCardTimeDefault = TaskCardTimeDefaultLight,
    taskCardTimeHighlighted = TaskCardTimeHighlightedLight,

    taskPriorityUrgentContent = TaskPriorityUrgentContentLight,
    taskPriorityHighContent = TaskPriorityHighContentLight,
    taskPriorityNormalContent = TaskPriorityNormalContentLight,

    taskCompletedContainer = TaskCompletedContainerLight,
    taskCompletedContent = TaskCompletedContentLight,

    taskProgressBorder = TaskProgressBorderLight,
    taskProgressContent = TaskProgressContentLight,

    taskUncheckedBorder = TaskUncheckedBorderLight,

    )

private val DarkMemoCraftColors = MemoCraftThemeColors(
    // Bottom Navigation
    bottomNavContainer = BottomNavContainerDark,
    bottomNavSelectedItemBackground = BottomNavSelectedItemBackgroundDark,
    bottomNavSelectedIndicator = BottomNavSelectedIndicatorDark,
    bottomNavSelectedContent = BottomNavSelectedContentDark,
    bottomNavUnselectedContent = BottomNavUnselectedContentDark,

    // Top Bar
    topBarBackground = TopBarBackgroundDark,
    topBarContainerBackground = TopBarContainerBackgroundDark,
    topBarAvatarBackground = TopBarAvatarBackgroundDark,
    topBarAvatarContent = TopBarAvatarContentDark,
    topBarAvatarBorder = TopBarAvatarBorderDark,
    topBarTitleColor = TopBarTitleColorDark,
    topBarSubtitleColor = TopBarSubtitleColorDark,
    topBarStatusColor = TopBarStatusColorDark,
    topBarMenuIconColor = TopBarMenuIconColorDark,
    topBarActionIconColor = TopBarActionIconColorDark,
    topBarNotificationBadge = TopBarNotificationBadgeDark,
    topBarStatLabelColor = TopBarStatLabelColorDark,
    topBarStatValueColor = TopBarStatValueColorDark,
    topBarDivider = TopBarDividerDark,
    topBarShadowColor = TopBarShadowColorDark,

    // Profile
    profileBackgroundColor = ProfileBackgroundDark,
    profileBorderColor = ProfileBorderDark,
    profileIconColor = ProfileIconDark,

    // AI Insight Card
    aiInsightCardContainer = AiInsightCardContainerDark,
    aiInsightCardTitle = AiInsightCardTitleDark,
    aiInsightCardDescription = AiInsightCardDescriptionDark,
    aiInsightCardEmoji = AiInsightCardEmojiDark,

    // Semantic Colors
    successColor = SuccessDark,
    successContainer = SuccessContainerDark,
    successContent = SuccessContentDark,

    errorColor = ErrorDark,
    errorContainer = ErrorContainerDark,
    errorContent = ErrorContentDark,

    accentColor = AccentDark,
    accentContainer = AccentContainerDark,
    accentContent = AccentContentDark,
    // Progress Card
    progressCardGradientStart = ProgressCardGradientStartDark,
    progressCardGradientEnd = ProgressCardGradientEndDark,
    progressCardContent = ProgressCardContentDark,
    progressCardContentSecondary = ProgressCardContentSecondaryDark,

    progressRingTrack = ProgressRingTrackDark,
    progressRingProgressStart = ProgressRingProgressStartDark,
    progressRingProgressMiddle = ProgressRingProgressMiddleDark,
    progressRingProgressEnd = ProgressRingProgressEndDark,

    progressCompletedDot = ProgressCompletedDotDark,
    progressInProgressDot = ProgressInProgressDotDark,
    progressPendingDot = ProgressPendingDotDark,
    // Section Header
    sectionHeaderTitleColor = SectionHeaderTitleDark,
    sectionHeaderActionColor = SectionHeaderActionDark,

    // Task Card
    taskCardContainer = TaskCardContainerDark,
    taskCardTitle = TaskCardTitleDark,
    taskCardSubtitle = TaskCardSubtitleDark,
    taskCardTimeDefault = TaskCardTimeDefaultDark,
    taskCardTimeHighlighted = TaskCardTimeHighlightedDark,

    taskPriorityUrgentContent = TaskPriorityUrgentContentDark,
    taskPriorityHighContent = TaskPriorityHighContentDark,
    taskPriorityNormalContent = TaskPriorityNormalContentDark,

    taskCompletedContainer = TaskCompletedContainerDark,
    taskCompletedContent = TaskCompletedContentDark,

    taskProgressBorder = TaskProgressBorderDark,
    taskProgressContent = TaskProgressContentDark,

    taskUncheckedBorder = TaskUncheckedBorderDark,


    )

@Composable
fun MemoCraftAppTheme(
    content: @Composable () -> Unit
) {
    MemoCraftAppTheme(
        darkTheme = isSystemInDarkTheme(),
        content = content
    )
}

@Composable
fun MemoCraftAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = remember(darkTheme) {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }

    val memoCraftColors = remember(darkTheme) {
        if (darkTheme) DarkMemoCraftColors else LightMemoCraftColors
    }

    CompositionLocalProvider(
        LocalMemoCraftThemeColors provides memoCraftColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
