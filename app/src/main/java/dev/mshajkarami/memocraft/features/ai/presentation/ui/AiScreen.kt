package dev.mshajkarami.memocraft.features.ai.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
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
        topBar = {
            AiTopBar()
        }
    ) { innerPadding ->
        AiScreenContent(
            messages = uiState.messages,
            onSendMessage = viewModel::onSendMessage,
            onAddDetectedTaskClick = onAddDetectedTaskClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
private fun AiScreenContent(
    messages: List<AiChatMessageUiModel>,
    onSendMessage: (String) -> Unit,
    onAddDetectedTaskClick: (DetectedTaskUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.bottomNavContainer)
            .imePadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = listState,
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 4.dp,
                bottom = 18.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                AiIntroCard()
            }

            items(
                items = messages,
                key = { it.id }
            ) { message ->
                AiMessageBubble(
                    message = message,
                    onAddDetectedTaskClick = onAddDetectedTaskClick
                )
            }
        }

        AiInputBar(
            onSendClick = onSendMessage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 14.dp)
        )
    }
}
