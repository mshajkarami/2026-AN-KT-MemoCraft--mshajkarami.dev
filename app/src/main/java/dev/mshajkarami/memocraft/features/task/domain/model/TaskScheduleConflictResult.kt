package dev.mshajkarami.memocraft.features.task.domain.model

import java.time.LocalDateTime

data class TaskScheduleConflictResult(
    val task: Task,
    val hasConflict: Boolean,
    val conflictingTasks: List<Task> = emptyList(),
    val suggestions: List<TaskTimeSuggestion> = emptyList()
)

data class TaskTimeSuggestion(
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)
