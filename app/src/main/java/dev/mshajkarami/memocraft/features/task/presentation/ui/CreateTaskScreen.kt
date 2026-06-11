package dev.mshajkarami.memocraft.features.task.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftAppTheme
import dev.mshajkarami.memocraft.core.presentation.ui.theme.MemoCraftTheme
import dev.mshajkarami.memocraft.features.task.presentation.component.card.CompactDashboardTaskCard
import dev.mshajkarami.memocraft.features.task.presentation.component.card.rememberTaskCardBrushes
import dev.mshajkarami.memocraft.features.task.presentation.model.SubTaskUiModel
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskCardUiModel
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.presentation.model.TaskStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MemoCraftTheme.colors

    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf(TaskPriority.Normal) }
    var newSubTaskTitle by remember { mutableStateOf("") }
    val subTasks = remember { mutableStateListOf<SubTaskUiModel>() }

    val completedSubTasksCount = subTasks.count { it.isCompleted }
    val progress = if (subTasks.isEmpty()) {
        0
    } else {
        ((completedSubTasksCount.toFloat() / subTasks.size.toFloat()) * 100).toInt()
    }

    val isFormValid = taskTitle.isNotBlank() && taskDescription.isNotBlank()

    val livePreviewTask = remember(
        taskTitle,
        selectedPriority,
        progress
    ) {
        TaskCardUiModel(
            title = if (taskTitle.isBlank()) "Task Title Preview" else taskTitle,
            progress = progress,
            priority = selectedPriority,
            status = TaskStatus.Pending,
            isCompleted = progress == 100 && subTasks.isNotEmpty(),
            timeLabel = "Just now"
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colors.bottomNavContainer,
        topBar = {
            CreateTaskTopBar(onBackClick = onBackClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
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

                CompactDashboardTaskCard(task = livePreviewTask)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.compactTaskCardInnerBorder.copy(alpha = 0.5f))
            )

            TaskInputContainer(label = "Task Title") {
                TextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    placeholder = {
                        Text(
                            text = "What needs to be done?",
                            color = colors.progressMiniCardContent.copy(alpha = 0.5f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = colors.progressMiniCardContent,
                        unfocusedTextColor = colors.progressMiniCardContent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    singleLine = true
                )
            }

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
                    onPrioritySelected = { selectedPriority = it }
                )
            }

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
                    onNewSubTaskTitleChange = { newSubTaskTitle = it },
                    onAddSubTask = {
                        val trimmedTitle = newSubTaskTitle.trim()
                        if (trimmedTitle.isNotEmpty()) {
                            subTasks.add(
                                SubTaskUiModel(
                                    title = trimmedTitle,
                                    isCompleted = false
                                )
                            )
                            newSubTaskTitle = ""
                        }
                    },
                    onSubTaskCheckedChange = { index, isCompleted ->
                        val current = subTasks[index]
                        subTasks[index] = current.copy(isCompleted = isCompleted)
                    },
                    onRemoveSubTask = { index ->
                        subTasks.removeAt(index)
                    }
                )
            }

            TaskInputContainer(label = "Description") {
                TextField(
                    value = taskDescription,
                    onValueChange = { taskDescription = it },
                    placeholder = {
                        Text(
                            text = "Add more details...",
                            color = colors.progressMiniCardContent.copy(alpha = 0.5f)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = colors.progressMiniCardContent,
                        unfocusedTextColor = colors.progressMiniCardContent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

            SaveTaskButton(
                enabled = isFormValid,
                onClick = {
                    // Save Logic
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
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
                TextField(
                    value = newSubTaskTitle,
                    onValueChange = onNewSubTaskTitleChange,
                    placeholder = {
                        Text(
                            text = "Add a sub task...",
                            color = colors.progressMiniCardContent.copy(alpha = 0.5f)
                        )
                    },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = colors.progressMiniCardContent,
                        unfocusedTextColor = colors.progressMiniCardContent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    singleLine = true
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
                    if (isCompleted) MaterialTheme.colorScheme.primary
                    else Color.Transparent
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

@Composable
fun SaveTaskButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (enabled) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
    }

    val contentColor = if (enabled) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(containerColor)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = contentColor
            )

            Text(
                text = "Save Task",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}

@Composable
fun TaskInputContainer(
    label: String,
    content: @Composable () -> Unit
) {
    val colors = MemoCraftTheme.colors
    val brushes = rememberTaskCardBrushes()
    val shape = RoundedCornerShape(20.dp)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = colors.progressMiniCardContent.copy(alpha = 0.7f),
            modifier = Modifier.padding(start = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = if (brushes.isLightTheme) 8.dp else 12.dp,
                    shape = shape,
                    ambientColor = colors.compactTaskCardShadowAmbient,
                    spotColor = colors.compactTaskCardShadowSpot
                )
                .clip(shape)
                .background(brushes.backgroundBrush)
                .border(1.dp, brushes.borderBrush, shape)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(brushes.highlightBrush)
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(1.dp)
                    .clip(RoundedCornerShape(19.dp))
                    .border(
                        1.dp,
                        colors.compactTaskCardInnerBorder,
                        RoundedCornerShape(19.dp)
                    )
            )

            Box(
                modifier = Modifier.padding(4.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun PrioritySelector(
    selectedPriority: TaskPriority,
    onPrioritySelected: (TaskPriority) -> Unit
) {
    val colors = MemoCraftTheme.colors

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TaskPriority.entries.forEach { priority ->
            val isSelected = priority == selectedPriority
            val contentColor = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                colors.progressMiniCardContent
            }

            val containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                colors.bottomNavContainer.copy(alpha = 0.5f)
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(containerColor)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color.Transparent else colors.compactTaskCardInnerBorder,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onPrioritySelected(priority) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = priority.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = contentColor,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}

@Preview(
    name = "Create Task Screen - Light",
    showBackground = true,
    backgroundColor = 0xFFF8F8FF,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun CreateTaskScreenLightPreview() {
    MemoCraftAppTheme(darkTheme = false) {
        CreateTaskScreen(
            onBackClick = {}
        )
    }
}

@Preview(
    name = "Create Task Screen - Dark",
    showBackground = true,
    backgroundColor = 0xFF0F1226,
    widthDp = 430,
    heightDp = 932
)
@Composable
private fun CreateTaskScreenDarkPreview() {
    MemoCraftAppTheme(darkTheme = true) {
        CreateTaskScreen(
            onBackClick = {}
        )
    }
}
