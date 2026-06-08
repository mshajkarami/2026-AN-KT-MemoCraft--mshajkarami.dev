package dev.mshajkarami.memocraft.features.task.presentation.component.chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskStatus

@Composable
fun TaskStatusChip(
    status: TaskStatus,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    val chipColors = when (status) {
        TaskStatus.Pending -> TaskChipColors(
            background = colors.taskStatusPendingContainer,
            content = colors.taskStatusPendingContent,
            dot = colors.taskStatusPendingContent
        )

        TaskStatus.InProgress -> TaskChipColors(
            background = colors.taskStatusInProgressContainer,
            content = colors.taskStatusInProgressContent,
            dot = colors.taskStatusInProgressContent
        )

        TaskStatus.Completed -> TaskChipColors(
            background = colors.taskStatusCompletedContainer,
            content = colors.taskStatusCompletedContent,
            dot = colors.taskStatusCompletedContent
        )
    }

    val text = when (status) {
        TaskStatus.Pending -> "Pending"
        TaskStatus.InProgress -> "In Progress"
        TaskStatus.Completed -> "Completed"
    }

    TaskChip(
        text = text,
        icon = null,
        colors = chipColors,
        modifier = modifier
    )
}
