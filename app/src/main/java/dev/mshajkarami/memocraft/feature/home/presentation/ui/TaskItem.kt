package dev.mshajkarami.memocraft.feature.home.presentation.ui

import androidx.compose.ui.graphics.Color

data class TaskItem(
    val title: String,
    val subtitle: String,
    val time: String,
    val priority: String,
    val priorityColor: Color,
    val iconBg: Color,
    val iconColor: Color,
    val trailingText: String? = null,
    val trailingChecked: Boolean = false
)