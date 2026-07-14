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
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mshajkarami.memocraft.R

private val CardOuterHorizontalPadding = 12.dp
private val CardOuterVerticalPadding = 6.dp
private val CardCornerRadius = 14.dp
private val CardInnerHorizontalPadding = 14.dp
private val CardInnerVerticalPadding = 12.dp

private val HeaderStartPadding = 2.dp
private val HeaderIconSize = 17.dp
private val HeaderDotSize = 4.dp
private val HeaderSpacing = 6.dp

private val TaskIconSize = 15.dp
private val TaskStatusCheckSize = 14.dp
private val TaskPulseDotSize = 6.dp
private val TaskIconSpacing = 9.dp
private val TaskTrailingSpacing = 8.dp
private val TaskVerticalPadding = 4.dp

private val DotsDefaultSize = 5.dp
private val DotsDefaultSpacing = 4.dp

private val SpacerTiny = 3.dp
private val SpacerSmall = 6.dp
private val SpacerLarge = 12.dp

private val TitleTextSize = 14.sp
private val ContextTextSize = 12.sp
private val ContextLineHeight = 16.sp
private val HeaderNameTextSize = 13.sp
private val HeaderStatusTextSize = 12.sp
private val TaskTextSize = 12.5.sp
private val TaskLineHeight = 16.sp

private const val CardContainerAlpha = 0.72f
private const val HeaderDotAlpha = 0.45f
private const val CompletedIconAlpha = 0.85f
private const val InactiveDotColorAlpha = 0.22f
private const val InactiveDotAnimationAlpha = 0.3f

private const val PulseInitialAlpha = 0.35f
private const val PulseTargetAlpha = 1f
private const val PulseDurationMillis = 750
private const val DotAnimationDurationMillis = 380
private const val DotAlphaAnimationDurationMillis = 220
private const val DefaultDotCount = 3

@Composable
fun AiThinkingCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    context: String? = null,
    aiName: String? = null
) {
    val colorScheme = MaterialTheme.colorScheme

    val displayedTitle = title ?: stringResource(R.string.ai_thinking_title)
    val displayedContext = context ?: stringResource(R.string.ai_thinking_context)
    val displayedAiName = aiName ?: stringResource(R.string.ai_thinking_default_name)

    val infiniteTransition = rememberInfiniteTransition(
        label = "ai_thinking_transition"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = PulseInitialAlpha,
        targetValue = PulseTargetAlpha,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = PulseDurationMillis,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ai_thinking_pulse_alpha"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = CardOuterHorizontalPadding,
                vertical = CardOuterVerticalPadding
            )
    ) {
        AiThinkingHeader(
            aiName = displayedAiName,
            status = stringResource(R.string.ai_thinking_status),
            primaryColor = colorScheme.primary,
            bodyColor = colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(SpacerSmall))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(CardCornerRadius),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surfaceContainerHighest.copy(
                    alpha = CardContainerAlpha
                )
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = CardInnerHorizontalPadding,
                    vertical = CardInnerVerticalPadding
                )
            ) {
                Text(
                    text = displayedTitle,
                    color = colorScheme.onSurface,
                    fontSize = TitleTextSize,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(SpacerTiny))

                Text(
                    text = displayedContext,
                    color = colorScheme.onSurfaceVariant,
                    fontSize = ContextTextSize,
                    lineHeight = ContextLineHeight
                )

                Spacer(modifier = Modifier.height(SpacerLarge))

                ThinkingTaskItem(
                    icon = Icons.Outlined.Send,
                    text = stringResource(R.string.ai_thinking_message_received),
                    textColor = colorScheme.onSurface,
                    iconTint = colorScheme.onSurfaceVariant,
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = colorScheme.primary.copy(
                                alpha = CompletedIconAlpha
                            ),
                            modifier = Modifier.size(TaskStatusCheckSize)
                        )
                    }
                )

                ThinkingTaskItem(
                    icon = Icons.Outlined.Notifications,
                    text = stringResource(R.string.ai_thinking_detecting_tasks),
                    textColor = colorScheme.onSurface,
                    iconTint = colorScheme.onSurfaceVariant,
                    trailingContent = {
                        Box(
                            modifier = Modifier
                                .size(TaskPulseDotSize)
                                .clip(CircleShape)
                                .background(
                                    color = colorScheme.primary.copy(
                                        alpha = pulseAlpha
                                    )
                                )
                        )
                    }
                )

                ThinkingTaskItem(
                    icon = Icons.Outlined.Edit,
                    text = stringResource(R.string.ai_thinking_preparing_response),
                    textColor = colorScheme.primary,
                    iconTint = colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(SpacerLarge))

                ThinkingDotsIndicator(
                    activeColor = colorScheme.primary,
                    inactiveColor = colorScheme.onSurfaceVariant.copy(
                        alpha = InactiveDotColorAlpha
                    )
                )
            }
        }
    }
}

@Composable
private fun AiThinkingHeader(
    aiName: String,
    status: String,
    primaryColor: Color,
    bodyColor: Color
) {
    Row(
        modifier = Modifier.padding(start = HeaderStartPadding),
        verticalAlignment = Alignment.CenterVertically
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
            fontSize = HeaderNameTextSize,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.width(HeaderSpacing))

        Box(
            modifier = Modifier
                .size(HeaderDotSize)
                .clip(CircleShape)
                .background(
                    color = bodyColor.copy(alpha = HeaderDotAlpha)
                )
        )

        Spacer(modifier = Modifier.width(HeaderSpacing))

        Text(
            text = status,
            color = bodyColor,
            fontSize = HeaderStatusTextSize,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ThinkingTaskItem(
    icon: ImageVector,
    text: String,
    textColor: Color,
    iconTint: Color,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TaskVerticalPadding),
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

        trailingContent?.let { content ->
            Spacer(modifier = Modifier.width(TaskTrailingSpacing))
            content()
        }
    }
}

@Composable
private fun ThinkingDotsIndicator(
    activeColor: Color,
    inactiveColor: Color,
    modifier: Modifier = Modifier,
    dotCount: Int = DefaultDotCount,
    dotSize: Dp = DotsDefaultSize,
    spacing: Dp = DotsDefaultSpacing,
    animationDurationMillis: Int = DotAnimationDurationMillis
) {
    if (dotCount <= 0) return

    val transition = rememberInfiniteTransition(
        label = "ai_thinking_dots_transition"
    )

    val activeIndex by transition.animateFloat(
        initialValue = 0f,
        targetValue = dotCount.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = dotCount * animationDurationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "ai_thinking_active_dot_index"
    )

    val currentIndex = activeIndex.toInt() % dotCount

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(dotCount) { index ->
            val isActive = index == currentIndex

            val animatedAlpha by animateFloatAsState(
                targetValue = if (isActive) {
                    PulseTargetAlpha
                } else {
                    InactiveDotAnimationAlpha
                },
                animationSpec = tween(
                    durationMillis = DotAlphaAnimationDurationMillis
                ),
                label = "ai_thinking_dot_alpha_$index"
            )

            val dotColor = if (isActive) activeColor else inactiveColor

            Box(
                modifier = Modifier
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(
                        color = dotColor.copy(alpha = animatedAlpha)
                    )
            )

            if (index < dotCount - 1) {
                Spacer(modifier = Modifier.width(spacing))
            }
        }
    }
}
