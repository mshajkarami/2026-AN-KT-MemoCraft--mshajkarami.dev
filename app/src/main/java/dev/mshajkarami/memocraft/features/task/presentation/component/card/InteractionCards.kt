package dev.mshajkarami.memocraft.features.task.presentation.component.card

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Snooze
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CardOuterHorizontalPadding = 12.dp
private val CardOuterVerticalPadding = 6.dp
private val CardCornerRadius = 14.dp
private val CardInnerHorizontalPadding = 14.dp
private val CardInnerVerticalPadding = 12.dp

private val HeaderStartPadding = 2.dp
private val HeaderIconSize = 17.dp
private val HeaderDotSize = 4.dp
private val HeaderSpacing = 6.dp

private val ReminderHeaderIconSize = 16.dp
private val ReminderDateIconSize = 14.dp
private val ReminderActionIconSize = 13.dp

private val TaskIconSize = 15.dp
private val TaskStatusCheckSize = 14.dp
private val TaskPulseDotSize = 6.dp
private val TaskIconSpacing = 9.dp
private val TaskTrailingSpacing = 8.dp

private val DotsDefaultSize = 5.dp
private val DotsDefaultSpacing = 4.dp

private val ActionHeight = 32.dp
private val ActionCornerRadius = 10.dp
private val ActionHorizontalPadding = 8.dp
private val ActionIconSpacing = 4.dp
private val ReminderActionsSpacing = 6.dp

private val SpacerTiny = 3.dp
private val SpacerSmall = 6.dp
private val SpacerMedium = 10.dp
private val SpacerLarge = 12.dp

private val TitleTextSize = 14.sp
private val ContextTextSize = 12.sp
private val ContextLineHeight = 16.sp
private val HeaderNameTextSize = 13.sp
private val HeaderStatusTextSize = 12.sp
private val TaskTextSize = 12.5.sp
private val TaskLineHeight = 16.sp

private val ReminderTitleTextSize = 15.sp
private val ReminderTitleLineHeight = 19.sp
private val ReminderDescriptionTextSize = 12.5.sp
private val ReminderDescriptionLineHeight = 17.sp
private val ReminderHeaderTextSize = 12.sp
private val ReminderDateTextSize = 12.5.sp
private val ReminderActionTextSize = 11.5.sp

private const val PulseDurationMillis = 750
private const val DotAnimationDurationMillis = 380
private const val DotAlphaAnimationDurationMillis = 220

@Composable
fun AiThinkingCard(
    modifier: Modifier = Modifier,
    title: String = "در حال تحلیل درخواست شما",
    context: String = "MemoCraft AI در حال بررسی متن، استخراج کارها و آماده‌سازی پاسخ است.",
    aiName: String = "MemoCraft AI"
) {
    val colorScheme = MaterialTheme.colorScheme

    val infiniteTransition = rememberInfiniteTransition(label = "ai_thinking_transition")

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = PulseDurationMillis,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = CardOuterHorizontalPadding,
                vertical = CardOuterVerticalPadding
            )
    ) {
        AiThinkingHeaderMinimal(
            aiName = aiName,
            primaryColor = colorScheme.primary,
            bodyColor = colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(SpacerSmall))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surfaceContainerHighest.copy(alpha = 0.72f)
            ),
            shape = RoundedCornerShape(CardCornerRadius),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = CardInnerHorizontalPadding,
                    vertical = CardInnerVerticalPadding
                )
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = TitleTextSize,
                    color = colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(SpacerTiny))

                Text(
                    text = context,
                    fontSize = ContextTextSize,
                    lineHeight = ContextLineHeight,
                    color = colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(SpacerLarge))

                ThinkingTaskItemMinimal(
                    icon = Icons.Outlined.Send,
                    text = "پیام شما دریافت شد",
                    textColor = colorScheme.onSurface,
                    iconTint = colorScheme.onSurfaceVariant,
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = colorScheme.primary.copy(alpha = 0.85f),
                            modifier = Modifier.size(TaskStatusCheckSize)
                        )
                    }
                )

                ThinkingTaskItemMinimal(
                    icon = Icons.Outlined.Notifications,
                    text = "در حال تشخیص تسک‌ها و زمان‌بندی",
                    textColor = colorScheme.onSurface,
                    iconTint = colorScheme.onSurfaceVariant,
                    trailingContent = {
                        Box(
                            modifier = Modifier
                                .size(TaskPulseDotSize)
                                .clip(CircleShape)
                                .background(colorScheme.primary.copy(alpha = pulseAlpha))
                        )
                    }
                )

                ThinkingTaskItemMinimal(
                    icon = Icons.Outlined.Edit,
                    text = "در حال آماده‌سازی پاسخ نهایی",
                    textColor = colorScheme.primary,
                    iconTint = colorScheme.onSurfaceVariant,
                    trailingContent = null
                )

                Spacer(modifier = Modifier.height(SpacerLarge))

                ThinkingDotsIndicatorMinimal(
                    activeColor = colorScheme.primary,
                    inactiveColor = colorScheme.onSurfaceVariant.copy(alpha = 0.22f)
                )
            }
        }
    }
}

@Composable
private fun AiThinkingHeaderMinimal(
    aiName: String,
    primaryColor: Color,
    bodyColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = HeaderStartPadding)
    ) {
        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = null,
            tint = primaryColor,
            modifier = Modifier.size(HeaderIconSize)
        )

        Spacer(modifier = Modifier.width(HeaderSpacing))

        Text(
            text = aiName,
            color = primaryColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = HeaderNameTextSize
        )

        Spacer(modifier = Modifier.width(HeaderSpacing))

        Box(
            modifier = Modifier
                .size(HeaderDotSize)
                .clip(CircleShape)
                .background(bodyColor.copy(alpha = 0.45f))
        )

        Spacer(modifier = Modifier.width(HeaderSpacing))

        Text(
            text = "در حال فکر کردن",
            color = bodyColor,
            fontSize = HeaderStatusTextSize,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ThinkingTaskItemMinimal(
    icon: ImageVector,
    text: String,
    textColor: Color,
    iconTint: Color,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(TaskIconSize)
        )

        Spacer(modifier = Modifier.width(TaskIconSpacing))

        Text(
            text = text,
            color = textColor,
            fontSize = TaskTextSize,
            lineHeight = TaskLineHeight,
            modifier = Modifier.weight(1f)
        )

        if (trailingContent != null) {
            Spacer(modifier = Modifier.width(TaskTrailingSpacing))
            trailingContent()
        }
    }
}

@Composable
private fun ThinkingDotsIndicatorMinimal(
    modifier: Modifier = Modifier,
    dotCount: Int = 3,
    activeColor: Color,
    inactiveColor: Color,
    dotSize: Dp = DotsDefaultSize,
    spacing: Dp = DotsDefaultSpacing,
    animationDuration: Int = DotAnimationDurationMillis
) {
    val transition = rememberInfiniteTransition(label = "thinking_dots")

    val activeIndex by transition.animateFloat(
        initialValue = 0f,
        targetValue = dotCount.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = dotCount * animationDuration,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "active_dot_index"
    )

    val currentIndex = activeIndex.toInt() % dotCount

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(dotCount) { index ->
            val isActive = index == currentIndex

            val animatedAlpha by animateFloatAsState(
                targetValue = if (isActive) 1f else 0.3f,
                animationSpec = tween(durationMillis = DotAlphaAnimationDurationMillis),
                label = "dot_alpha_$index"
            )

            Box(
                modifier = Modifier
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(
                        color = if (isActive) {
                            activeColor.copy(alpha = animatedAlpha)
                        } else {
                            inactiveColor.copy(alpha = animatedAlpha)
                        }
                    )
            )

            if (index != dotCount - 1) {
                Spacer(modifier = Modifier.width(spacing))
            }
        }
    }
}

@Composable
fun SmartReminderCard(
    title: String,
    description: String,
    dateTimeText: String,
    onSnoozeClicked: () -> Unit = {},
    onRescheduleClicked: () -> Unit = {},
    onMarkDoneClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainerHighest.copy(alpha = 0.72f)
        ),
        shape = RoundedCornerShape(CardCornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = CardInnerHorizontalPadding,
                vertical = CardInnerVerticalPadding
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = colorScheme.primary,
                        modifier = Modifier.size(ReminderHeaderIconSize)
                    )

                    Spacer(modifier = Modifier.width(SpacerSmall))

                    Text(
                        text = "یادآور هوشمند",
                        color = colorScheme.primary,
                        fontSize = ReminderHeaderTextSize,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(SpacerMedium))

            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = ReminderTitleTextSize,
                lineHeight = ReminderTitleLineHeight,
                color = colorScheme.onSurface
            )

            if (description.isNotBlank()) {
                Spacer(modifier = Modifier.height(SpacerTiny))

                Text(
                    text = description,
                    color = colorScheme.onSurfaceVariant,
                    fontSize = ReminderDescriptionTextSize,
                    lineHeight = ReminderDescriptionLineHeight
                )
            }

            if (dateTimeText.isNotBlank()) {
                Spacer(modifier = Modifier.height(SpacerMedium))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = null,
                        tint = colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(ReminderDateIconSize)
                    )

                    Spacer(modifier = Modifier.width(SpacerSmall))

                    Text(
                        text = dateTimeText,
                        fontWeight = FontWeight.Medium,
                        fontSize = ReminderDateTextSize,
                        color = colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(SpacerLarge))

            Row(
                horizontalArrangement = Arrangement.spacedBy(ReminderActionsSpacing),
                modifier = Modifier.fillMaxWidth()
            ) {
                MinimalReminderAction(
                    text = "تعویق",
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Snooze,
                            contentDescription = null,
                            tint = colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(ReminderActionIconSize)
                        )
                    },
                    backgroundColor = colorScheme.surface,
                    textColor = colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                    onClick = onSnoozeClicked
                )

                MinimalReminderAction(
                    text = "تغییر زمان",
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = null,
                            tint = colorScheme.onSurface,
                            modifier = Modifier.size(ReminderActionIconSize)
                        )
                    },
                    backgroundColor = colorScheme.surface,
                    textColor = colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                    onClick = onRescheduleClicked
                )

                MinimalReminderAction(
                    text = "انجام شد",
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = colorScheme.primary,
                            modifier = Modifier.size(ReminderActionIconSize)
                        )
                    },
                    backgroundColor = colorScheme.surface,
                    textColor = colorScheme.primary,
                    modifier = Modifier.weight(1f),
                    onClick = onMarkDoneClicked
                )
            }
        }
    }
}

@Composable
fun MinimalReminderAction(
    text: String,
    icon: @Composable () -> Unit,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(ActionHeight)
            .clip(RoundedCornerShape(ActionCornerRadius))
            .clickable { onClick() },
        color = backgroundColor,
        shape = RoundedCornerShape(ActionCornerRadius)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = ActionHorizontalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon()

            Spacer(modifier = Modifier.width(ActionIconSpacing))

            Text(
                text = text,
                color = textColor,
                fontSize = ReminderActionTextSize,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
