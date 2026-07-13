package dev.mshajkarami.memocraft.features.task.presentation.list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.designsystem.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.task.presentation.list.TaskFilter

@Composable
fun TaskFilterChips(
    selectedFilter: TaskFilter,
    onFilterSelected: (TaskFilter) -> Unit
) {
    val filters = listOf(
        TaskFilter.All,
        TaskFilter.InProgress,
        TaskFilter.Pending,
        TaskFilter.Completed
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(filters) { filter ->
            TaskFilterChip(
                title = filter.label(),
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) }
            )
        }
    }
}

@Composable
private fun TaskFilterChip(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = MemoCraftTheme.colors

    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(text = title)
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = colors.bottomNavSelectedItemBackground,
            selectedLabelColor = colors.bottomNavSelectedContent,
            containerColor = colors.taskCardContainer,
            labelColor = colors.taskCardTitle
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = colors.topBarDivider,
            selectedBorderColor = colors.bottomNavSelectedIndicator
        )
    )
}

private fun TaskFilter.label(): String {
    return when (this) {
        TaskFilter.All -> "All"
        TaskFilter.InProgress -> "In Progress"
        TaskFilter.Pending -> "Pending"
        TaskFilter.Completed -> "Completed"
    }
}
