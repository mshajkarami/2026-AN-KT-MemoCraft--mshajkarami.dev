package dev.mshajkarami.memocraft.features.ai.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.ai.presentation.AiTaskViewModel
import dev.mshajkarami.memocraft.features.task.presentation.component.card.AiThinkingCard

@Composable
fun AiScreen(
    modifier: Modifier = Modifier,
    viewModel: AiTaskViewModel = hiltViewModel(),
    onAddDetectedTaskClick: (DetectedTaskUiModel) -> Unit = {}
) {
    val colors = MemoCraftTheme.colors
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colors.bottomNavContainer,

        contentWindowInsets = WindowInsets(0, 0, 0, 0),

        topBar = {
            AiTopBar()
        },

        bottomBar = {
            AiInputSection(
                onSendMessage = viewModel::onSendMessage
            )
        }
    ) { innerPadding ->
        AiScreenContent(
            messages = uiState.messages,
            isGeneratingResponse = uiState.isGeneratingResponse,
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
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    val bottomInsets = WindowInsets.ime.union(WindowInsets.navigationBars)

    AiInputBar(
        onSendClick = onSendMessage,
        modifier = modifier
            .fillMaxWidth()
            .background(colors.bottomNavContainer)
            .windowInsetsPadding(bottomInsets)
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 8.dp,
                bottom = 4.dp
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

    LaunchedEffect(messages.size, isGeneratingResponse) {
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
            start = 20.dp,
            end = 20.dp,
            top = 4.dp,
            bottom = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
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
