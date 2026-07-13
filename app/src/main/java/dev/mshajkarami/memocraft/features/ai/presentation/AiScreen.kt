package dev.mshajkarami.memocraft.features.ai.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.designsystem.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.ai.presentation.component.AiInputBar
import dev.mshajkarami.memocraft.features.ai.presentation.component.AiIntroCard
import dev.mshajkarami.memocraft.features.ai.presentation.component.AiMessageBubble
import dev.mshajkarami.memocraft.features.ai.presentation.component.AiTopBar
import dev.mshajkarami.memocraft.features.ai.presentation.model.AiChatMessageUiModel
import dev.mshajkarami.memocraft.features.ai.presentation.model.DetectedTaskUiModel
import dev.mshajkarami.memocraft.features.task.presentation.component.card.AiThinkingCard

private object AiScreenSpacing {
    val ScreenHorizontalPadding = 24.dp

    val ContentTopPadding = 12.dp
    val ContentBottomPadding = 14.dp

    val MessageSpacing = 14.dp

    val InputTopPadding = 8.dp
    val InputBottomPadding = 6.dp
}

@Composable
fun AiScreen(
    messages: List<AiChatMessageUiModel>,
    isGeneratingResponse: Boolean,
    onSendMessage: (String) -> Unit,
    onAddDetectedTaskClick: (DetectedTaskUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colors.bottomNavContainer,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AiTopBar()
        },
        bottomBar = {
            AiInputSection(
                isLoading = isGeneratingResponse,
                onSendMessage = onSendMessage
            )
        }
    ) { innerPadding ->
        AiScreenContent(
            messages = messages,
            isGeneratingResponse = isGeneratingResponse,
            onAddDetectedTaskClick = onAddDetectedTaskClick,
            modifier = Modifier
                .fillMaxSize()
                .background(colors.bottomNavContainer)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        )
    }
}

@Composable
private fun AiInputSection(
    isLoading: Boolean,
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    AiInputBar(
        isLoading = isLoading,
        onSendClick = onSendMessage,
        modifier = modifier
            .fillMaxWidth()
            .background(colors.bottomNavContainer)
            .windowInsetsPadding(WindowInsets.ime)
            .padding(
                start = AiScreenSpacing.ScreenHorizontalPadding,
                end = AiScreenSpacing.ScreenHorizontalPadding,
                top = AiScreenSpacing.InputTopPadding,
                bottom = AiScreenSpacing.InputBottomPadding
            )
    )
}

@Composable
private fun AiScreenContent(
    messages: List<AiChatMessageUiModel>,
    isGeneratingResponse: Boolean,
    onAddDetectedTaskClick: (DetectedTaskUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val listState = rememberLazyListState()

    LaunchedEffect(
        messages.size,
        isGeneratingResponse
    ) {
        val totalItemsCount =
            1 + messages.size + if (isGeneratingResponse) 1 else 0

        val targetIndex = (totalItemsCount - 1).coerceAtLeast(0)

        if (totalItemsCount > 0) {
            listState.animateScrollToItem(targetIndex)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.bottomNavContainer),
        state = listState,
        contentPadding = PaddingValues(
            start = AiScreenSpacing.ScreenHorizontalPadding,
            end = AiScreenSpacing.ScreenHorizontalPadding,
            top = AiScreenSpacing.ContentTopPadding,
            bottom = AiScreenSpacing.ContentBottomPadding
        ),
        verticalArrangement = Arrangement.spacedBy(
            AiScreenSpacing.MessageSpacing
        )
    ) {
        item(key = "ai_intro_card") {
            AiIntroCard()
        }

        items(
            items = messages,
            key = { message -> message.id }
        ) { message ->
            AiMessageBubble(
                message = message,
                onAddDetectedTaskClick = onAddDetectedTaskClick
            )
        }

        if (isGeneratingResponse) {
            item(key = "ai_thinking_card") {
                AiThinkingCard(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
