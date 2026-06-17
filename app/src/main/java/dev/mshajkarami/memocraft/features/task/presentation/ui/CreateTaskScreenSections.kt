package dev.mshajkarami.memocraft.features.task.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.presentation.component.card.CompactDashboardTaskCard
import dev.mshajkarami.memocraft.features.task.presentation.model.SubTaskUiModel
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel

@Composable
internal fun PreviewSection(task: TaskCardUiModel) {
    val colors = MemoCraftTheme.colors

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Live Preview",
            style = MaterialTheme.typography.labelMedium,
            color = colors.progressMiniCardContent.copy(alpha = 0.6f),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp)
        )

        CompactDashboardTaskCard(task = task)
    }
}

@Composable
internal fun DividerLine() {
    val colors = MemoCraftTheme.colors

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.compactTaskCardInnerBorder.copy(alpha = 0.5f))
    )
}

@Composable
internal fun PrioritySection(
    selectedPriority: TaskPriority,
    onPrioritySelected: (TaskPriority) -> Unit
) {
    val colors = MemoCraftTheme.colors

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Priority",
            style = MaterialTheme.typography.labelMedium,
            color = colors.progressMiniCardContent.copy(alpha = 0.7f),
            modifier = Modifier.padding(start = 4.dp)
        )

        PrioritySelector(
            selectedPriority = selectedPriority,
            onPrioritySelected = onPrioritySelected
        )
    }
}

@Composable
internal fun ProgressSection(
    progress: Int,
    subTasks: List<SubTaskUiModel>,
    newSubTaskTitle: String,
    onNewSubTaskTitleChange: (String) -> Unit,
    onAddSubTask: () -> Unit,
    onSubTaskCheckedChange: (Int, Boolean) -> Unit,
    onRemoveSubTask: (Int) -> Unit
) {
    val colors = MemoCraftTheme.colors

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Progress",
                style = MaterialTheme.typography.labelMedium,
                color = colors.progressMiniCardContent.copy(alpha = 0.7f)
            )

            Text(
                text = "$progress%",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        SubTaskSection(
            subTasks = subTasks,
            newSubTaskTitle = newSubTaskTitle,
            onNewSubTaskTitleChange = onNewSubTaskTitleChange,
            onAddSubTask = onAddSubTask,
            onSubTaskCheckedChange = onSubTaskCheckedChange,
            onRemoveSubTask = onRemoveSubTask
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DueDateSection(
    dueDateInput: String,
    onDueDateChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    TaskInputContainer(
        label = "Due Date",
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
        ) {
            TransparentTextField(
                value = dueDateInput,
                onValueChange = {},
                placeholder = "22 Feb",
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                readOnly = true,
                enabled = false
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { selectedMillis ->
                            onDueDateChange(formatDueDate(selectedMillis))
                        }

                        showDatePicker = false
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}


private fun formatDueDate(millis: Long): String {
    val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH)
    formatter.timeZone = java.util.TimeZone.getTimeZone("UTC")
    return formatter.format(java.util.Date(millis))
}

@Composable
fun EstimatedTimeSection(
    estimatedDurationHoursInput: String,
    onEstimatedDurationHoursChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    TaskInputContainer(
        label = "Estimated Time",
        modifier = modifier
    ) {
        TransparentTextField(
            value = estimatedDurationHoursInput,
            onValueChange = { value ->
                onEstimatedDurationHoursChange(value.filter { it.isDigit() })
            },
            placeholder = "10",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            suffix = {
                Text(
                    text = "h",
                    color = colors.progressMiniCardContent.copy(alpha = 0.7f)
                )
            }
        )
    }
}


@Composable
fun SubTaskSection(
    subTasks: List<SubTaskUiModel>,
    newSubTaskTitle: String,
    onNewSubTaskTitleChange: (String) -> Unit,
    onAddSubTask: () -> Unit,
    onSubTaskCheckedChange: (Int, Boolean) -> Unit,
    onRemoveSubTask: (Int) -> Unit
) {
    val colors = MemoCraftTheme.colors

    TaskInputContainer(label = "Sub Tasks") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TransparentTextField(
                    value = newSubTaskTitle,
                    onValueChange = onNewSubTaskTitleChange,
                    placeholder = "Add a sub task...",
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { onAddSubTask() }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Add",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            if (subTasks.isEmpty()) {
                Text(
                    text = "No sub tasks yet.",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.progressMiniCardContent.copy(alpha = 0.5f)
                )
            } else {
                subTasks.forEachIndexed { index, subTask ->
                    SubTaskItem(
                        title = subTask.title,
                        isCompleted = subTask.isCompleted,
                        onCheckedChange = { checked ->
                            onSubTaskCheckedChange(index, checked)
                        },
                        onRemoveClick = {
                            onRemoveSubTask(index)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SubTaskItem(
    title: String,
    isCompleted: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onRemoveClick: () -> Unit
) {
    val colors = MemoCraftTheme.colors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colors.bottomNavContainer.copy(alpha = 0.4f))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(
                    if (isCompleted) MaterialTheme.colorScheme.primary else Color.Transparent
                )
                .border(
                    width = 1.dp,
                    color = if (isCompleted) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        colors.compactTaskCardInnerBorder
                    },
                    shape = RoundedCornerShape(7.dp)
                )
                .clickable { onCheckedChange(!isCompleted) },
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = colors.progressMiniCardContent,
            fontWeight = if (isCompleted) FontWeight.Bold else FontWeight.Normal
        )

        Text(
            text = "Remove",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.clickable { onRemoveClick() }
        )
    }
}
