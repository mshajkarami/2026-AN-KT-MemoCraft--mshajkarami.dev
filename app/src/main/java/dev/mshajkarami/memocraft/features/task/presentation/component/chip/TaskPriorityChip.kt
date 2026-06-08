package dev.mshajkarami.memocraft.features.task.presentation.component.chip

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskPriority

@Composable
fun TaskPriorityChip(
    priority: TaskPriority,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    val chipColors = when (priority) {
        TaskPriority.Low -> TaskChipColors(
            background = colors.taskPriorityLowContainer,
            content = colors.taskPriorityLowContent,
            dot = colors.taskPriorityLowContent
        )

        TaskPriority.Normal -> TaskChipColors(
            background = colors.taskPriorityNormalContainer,
            content = colors.taskPriorityNormalContent,
            dot = colors.taskPriorityNormalContent
        )

        TaskPriority.Urgent -> TaskChipColors(
            background = colors.taskPriorityUrgentContainer,
            content = colors.taskPriorityUrgentContentAlt,
            dot = colors.taskPriorityUrgentContentAlt
        )
    }

    val text = when (priority) {
        TaskPriority.Low -> "Low"
        TaskPriority.Normal -> "Normal"
        TaskPriority.Urgent -> "Urgent"
    }

    val icon = when (priority) {
        TaskPriority.Low -> Icons.Rounded.Schedule
        TaskPriority.Normal -> Icons.Rounded.Schedule
        TaskPriority.Urgent -> Icons.Rounded.Error
    }

    TaskChip(
        text = text,
        icon = icon,
        colors = chipColors,
        modifier = modifier
    )
}