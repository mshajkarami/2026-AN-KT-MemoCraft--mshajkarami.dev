package dev.mshajkarami.memocraft.core.presentation.ui.theme

import androidx.compose.ui.graphics.Color

/* ===========================================================
 * Brand Colors
 * =========================================================== */

val BrandPrimary = Color(0xFF2F2FA2)
val BrandPrimaryDark = Color(0xFF25257F)
val BrandPrimaryContainer = Color(0xFFE9E7FF)

val BrandSecondary = Color(0xFF6D5DF6)
val BrandSecondaryLight = Color(0xFF7B5CFF)
val BrandAccent = Color(0xFF4E54F7)

val UnSelectedColor = Color(0xFF9B9BA1)

/* ===========================================================
 * Backgrounds
 * =========================================================== */

val BackgroundLight = Color(0xFFF8F8FF)
val BackgroundDark = Color(0xFF0F1226)

val CardBackgroundLight = Color(0xFFFFFFFF)
val CardBackgroundDark = Color(0xFF1A1D35)

/* ===========================================================
 * Text Colors
 * =========================================================== */

val TextPrimaryLight = Color(0xFF1E1F3A)
val TextSecondaryLight = Color(0xFF6F7285)

val TextPrimaryDark = Color(0xFFF3F4FF)
val TextSecondaryDark = Color(0xFFB2B6CC)

/* ===========================================================
 * Divider / Outline
 * =========================================================== */

val DividerColorLight = Color(0xFFE4E4F2)
val DividerColorDark = Color(0xFF34385A)

/* ===========================================================
 * Status / Semantic Colors
 * =========================================================== */

val SuccessLight = Color(0xFF16A34A)
val SuccessContainerLight = Color(0xFFEAF8EF)
val SuccessContentLight = Color(0xFF166534)

val SuccessDark = Color(0xFF4ADE80)
val SuccessContainerDark = Color(0xFF1A3526)
val SuccessContentDark = Color(0xFFD8FFE5)

val ErrorLight = Color(0xFFDC2626)
val ErrorContainerLight = Color(0xFFFDECEC)
val ErrorContentLight = Color(0xFF991B1B)

val ErrorDark = Color(0xFFFF6B6B)
val ErrorContainerDark = Color(0xFF3B1F24)
val ErrorContentDark = Color(0xFFFFDADA)

val AccentLight = Color(0xFF4E54F7)
val AccentContainerLight = Color(0xFFEEF0FF)
val AccentContentLight = Color(0xFF2F2FA2)

val AccentDark = Color(0xFF8E93FF)
val AccentContainerDark = Color(0xFF2A315F)
val AccentContentDark = Color(0xFFE4E6FF)

/* ===========================================================
 * Bottom Navigation - Light
 * =========================================================== */

val BottomNavContainerLight = Color(0xFFFFFFFF)
val BottomNavSelectedItemBackgroundLight = Color(0xFFE9E7FF)
val BottomNavSelectedIndicatorLight = BrandPrimary
val BottomNavSelectedContentLight = BrandPrimary
val BottomNavUnselectedContentLight = UnSelectedColor

/* ===========================================================
 * Bottom Navigation - Dark
 * =========================================================== */

val BottomNavContainerDark = Color(0xFF171A2F)
val BottomNavSelectedItemBackgroundDark = Color(0xFF2B2F55)
val BottomNavSelectedIndicatorDark = Color(0xFF9C8CFF)
val BottomNavSelectedContentDark = Color(0xFFD9D4FF)
val BottomNavUnselectedContentDark = Color(0xFF8D92B3)

/* ===========================================================
 * Top Bar - Light
 * =========================================================== */

val TopBarBackgroundLight = Color(0xFFFFFFFF)
val TopBarContainerBackgroundLight = Color(0xFFFFFFFF)

val TopBarAvatarBackgroundLight = Color(0xFFEAE7FF)
val TopBarAvatarContentLight = Color(0xFF5B5478)
val TopBarAvatarBorderLight = Color(0xFF8B80F8)

val TopBarTitleColorLight = Color(0xFF101124)
val TopBarSubtitleColorLight = Color(0xFF4F5368)
val TopBarStatusColorLight = TextSecondaryLight

val TopBarMenuIconColorLight = Color(0xFF3E4054)
val TopBarActionIconColorLight = Color(0xFF3E4054)

val TopBarNotificationBadgeLight = Color(0xFFE5484D)

val TopBarStatLabelColorLight = TextSecondaryLight
val TopBarStatValueColorLight = TextPrimaryLight

val TopBarDividerLight = DividerColorLight
val TopBarShadowColorLight = Color(0x14000000)

/* ===========================================================
 * Top Bar - Dark
 * =========================================================== */

val TopBarBackgroundDark = Color(0xFF0F1226)
val TopBarContainerBackgroundDark = Color(0xFF171A2F)

val TopBarAvatarBackgroundDark = Color(0xFF2A2E4D)
val TopBarAvatarContentDark = Color(0xFFE3DEFF)
val TopBarAvatarBorderDark = Color(0xFF8E83FF)

val TopBarTitleColorDark = Color(0xFFF5F6FF)
val TopBarSubtitleColorDark = Color(0xFFB2B6CC)
val TopBarStatusColorDark = TextSecondaryDark

val TopBarMenuIconColorDark = Color(0xFFE1E5FF)
val TopBarActionIconColorDark = Color(0xFFD7DBF7)

val TopBarNotificationBadgeDark = Color(0xFFFF6B6B)

val TopBarStatLabelColorDark = TextSecondaryDark
val TopBarStatValueColorDark = TextPrimaryDark

val TopBarDividerDark = DividerColorDark
val TopBarShadowColorDark = Color(0x33000000)

/* ===========================================================
 * Profile - Light
 * =========================================================== */

val ProfileBackgroundLight = TopBarAvatarBackgroundLight
val ProfileBorderLight = TopBarAvatarBorderLight
val ProfileIconLight = TopBarAvatarContentLight

/* ===========================================================
 * Profile - Dark
 * =========================================================== */

val ProfileBackgroundDark = TopBarAvatarBackgroundDark
val ProfileBorderDark = TopBarAvatarBorderDark
val ProfileIconDark = TopBarAvatarContentDark

// AI Insight Card - Light
val AiInsightCardContainerLight = Color(0xFFF3E8FF)
val AiInsightCardTitleLight = Color(0xFF4C1D95)
val AiInsightCardDescriptionLight = Color(0xFF6B7280)
val AiInsightCardEmojiLight = Color(0xFF7C3AED)

// AI Insight Card - Dark
val AiInsightCardContainerDark = Color(0xFF2E1065)
val AiInsightCardTitleDark = Color(0xFFF5EFFF)
val AiInsightCardDescriptionDark = Color(0xFFD1C4E9)
val AiInsightCardEmojiDark = Color(0xFFC4B5FD)

// Progress Card - Light
val ProgressCardGradientStartLight = Color(0xFF7C3AED)
val ProgressCardGradientEndLight = Color(0xFF2563EB)

val ProgressCardContentLight = Color(0xFFFFFFFF)
val ProgressCardContentSecondaryLight = Color(0xCCFFFFFF)

val ProgressRingTrackLight = Color(0x40FFFFFF)
val ProgressRingProgressStartLight = Color(0xFFFFFFFF)
val ProgressRingProgressMiddleLight = Color(0xFFE0E7FF)
val ProgressRingProgressEndLight = Color(0xFFFFFFFF)

val ProgressCompletedDotLight = Color(0xFF22C55E)
val ProgressInProgressDotLight = Color(0xFFFACC15)
val ProgressPendingDotLight = Color(0xFFFF6B6B)


// Progress Card - Dark
val ProgressCardGradientStartDark = Color(0xFF4C1D95)
val ProgressCardGradientEndDark = Color(0xFF1E3A8A)

val ProgressCardContentDark = Color(0xFFFFFFFF)
val ProgressCardContentSecondaryDark = Color(0xCCFFFFFF)

val ProgressRingTrackDark = Color(0x33FFFFFF)
val ProgressRingProgressStartDark = Color(0xFFFFFFFF)
val ProgressRingProgressMiddleDark = Color(0xFFC4B5FD)
val ProgressRingProgressEndDark = Color(0xFFFFFFFF)

val ProgressCompletedDotDark = Color(0xFF4ADE80)
val ProgressInProgressDotDark = Color(0xFFFDE047)
val ProgressPendingDotDark = Color(0xFFFB7185)

// Section Header - Light
val SectionHeaderTitleLight = Color(0xFF111827)
val SectionHeaderActionLight = Color(0xFF7C3AED)

// Section Header - Dark
val SectionHeaderTitleDark = Color(0xFFF9FAFB)
val SectionHeaderActionDark = Color(0xFFC4B5FD)

// Task Card - Light
val TaskCardContainerLight = Color(0xFFFFFFFF)
val TaskCardTitleLight = Color(0xFF111827)
val TaskCardSubtitleLight = Color(0xFF6B7280)

val TaskCardTimeDefaultLight = Color(0xFF6B7280)
val TaskCardTimeHighlightedLight = Color(0xFFDC2626)

val TaskPriorityUrgentContentLight = Color(0xFFDC2626)
val TaskPriorityHighContentLight = Color(0xFFD97706)
val TaskPriorityNormalContentLight = Color(0xFF2563EB)

val TaskCompletedContainerLight = Color(0xFF22C55E)
val TaskCompletedContentLight = Color(0xFFFFFFFF)

val TaskProgressBorderLight = Color(0xFF7C3AED)
val TaskProgressContentLight = Color(0xFF7C3AED)

val TaskUncheckedBorderLight = Color(0xFFE5E7EB)


// Task Card - Dark
val TaskCardContainerDark = Color(0xFF1F2937)
val TaskCardTitleDark = Color(0xFFF9FAFB)
val TaskCardSubtitleDark = Color(0xFF9CA3AF)

val TaskCardTimeDefaultDark = Color(0xFF9CA3AF)
val TaskCardTimeHighlightedDark = Color(0xFFFCA5A5)

val TaskPriorityUrgentContentDark = Color(0xFFFCA5A5)
val TaskPriorityHighContentDark = Color(0xFFFCD34D)
val TaskPriorityNormalContentDark = Color(0xFF93C5FD)

val TaskCompletedContainerDark = Color(0xFF4ADE80)
val TaskCompletedContentDark = Color(0xFF052E16)

val TaskProgressBorderDark = Color(0xFFC4B5FD)
val TaskProgressContentDark = Color(0xFFC4B5FD)

val TaskUncheckedBorderDark = Color(0xFF374151)
