package dev.mshajkarami.memocraft.features.task.presentation.ui


import androidx.compose.runtime.Composable
import dev.mshajkarami.memocraft.core.designsystem.component.BaseBackTopBar

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