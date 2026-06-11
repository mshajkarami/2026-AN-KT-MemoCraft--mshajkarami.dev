package dev.mshajkarami.memocraft.features.task.presentation.ui


import androidx.compose.runtime.Composable
import dev.mshajkarami.memocraft.core.presentation.ui.components.BaseBackTopBar

@Composable
fun CreateTaskTopBar(
    onBackClick: () -> Unit
) {
    BaseBackTopBar(
        title = "Create Task",
        subtitle = "Add a new task",
        onBackClick = onBackClick,
    )
}