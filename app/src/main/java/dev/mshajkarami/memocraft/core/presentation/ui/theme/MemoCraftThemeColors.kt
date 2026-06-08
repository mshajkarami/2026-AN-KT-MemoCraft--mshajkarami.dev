package dev.mshajkarami.memocraft.core.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class MemoCraftThemeColors(
    // Bottom Navigation
    val bottomNavContainer: Color,
    val bottomNavSelectedItemBackground: Color,
    val bottomNavSelectedIndicator: Color,
    val bottomNavSelectedContent: Color,
    val bottomNavUnselectedContent: Color,

    // Top Bar
    val topBarBackground: Color,
    val topBarContainerBackground: Color,
    val topBarAvatarBackground: Color,
    val topBarAvatarContent: Color,
    val topBarAvatarBorder: Color,
    val topBarTitleColor: Color,
    val topBarSubtitleColor: Color,
    val topBarStatusColor: Color,
    val topBarMenuIconColor: Color,
    val topBarActionIconColor: Color,
    val topBarNotificationBadge: Color,
    val topBarStatLabelColor: Color,
    val topBarStatValueColor: Color,
    val topBarDivider: Color,
    val topBarShadowColor: Color,

    // Profile
    val profileBackgroundColor: Color,
    val profileBorderColor: Color,
    val profileIconColor: Color,

    // AI Insight Card
    val aiInsightCardContainer: Color,
    val aiInsightCardTitle: Color,
    val aiInsightCardDescription: Color,
    val aiInsightCardEmoji: Color,

    // Semantic Colors
    val successColor: Color,
    val successContainer: Color,
    val successContent: Color,

    val errorColor: Color,
    val errorContainer: Color,
    val errorContent: Color,

    val accentColor: Color,
    val accentContainer: Color,
    val accentContent: Color,

    // Section Header
    val sectionHeaderTitleColor: Color,
    val sectionHeaderActionColor: Color,

    // Task Card
    val taskCardContainer: Color,
    val taskCardTitle: Color,
    val taskCardSubtitle: Color,
    val taskCardTimeDefault: Color,
    val taskCardTimeHighlighted: Color,

    val taskPriorityUrgentContent: Color,
    val taskPriorityHighContent: Color,
    val taskPriorityNormalContent: Color,

    val taskCompletedContainer: Color,
    val taskCompletedContent: Color,

    val taskProgressBorder: Color,
    val taskProgressContent: Color,

    val taskUncheckedBorder: Color,

    // Progress Card V2
    val progressCardBackground: Color,
    val progressCardBackgroundSecondary: Color,
    val progressCardBadge: Color,

    val progressCardSurface: Color,
    val progressCardSurfaceBorder: Color,

    val progressRingGlowStart: Color,
    val progressRingGlowMiddle: Color,
    val progressRingGlowEnd: Color,

    val progressRingCenterFill: Color,
    val progressRingHighlight: Color,

    val progressMiniCardBackground: Color,
    val progressMiniCardContent: Color,
    val progressMiniCardContentSecondary: Color,

    val progressRingLightOuter: Color,
    val progressRingLightInner: Color,

    val progressSparkBlue: Color,
    val progressSparkPurple: Color,
    val progressSparkOrange: Color,

    // Compact Dashboard Task Card
    val compactTaskCardShadowAmbient: Color,
    val compactTaskCardShadowSpot: Color,
    val compactTaskCardGlassOverlay: Color,
    val compactTaskCardInnerBorder: Color,

    val taskStatusPendingContainer: Color,
    val taskStatusPendingContent: Color,

    val taskStatusInProgressContainer: Color,
    val taskStatusInProgressContent: Color,

    val taskStatusCompletedContainer: Color,
    val taskStatusCompletedContent: Color,

    val taskPriorityLowContainer: Color,
    val taskPriorityLowContent: Color,

    val taskPriorityNormalContainer: Color,

    val taskPriorityUrgentContainer: Color,
    val taskPriorityUrgentContentAlt: Color,

    val taskAvatarText: Color,
    val taskCompletionBadgeBackground: Color,
    val taskCompletionBadgeIcon: Color,



    )
internal val LocalMemoCraftThemeColors = staticCompositionLocalOf<MemoCraftThemeColors> {
    error("MemoCraftThemeColors was not provided. Make sure you wrap your UI with MemoCraftAppTheme.")
}

object MemoCraftTheme {

    val colors: MemoCraftThemeColors
        @Composable @ReadOnlyComposable get() = LocalMemoCraftThemeColors.current
}
