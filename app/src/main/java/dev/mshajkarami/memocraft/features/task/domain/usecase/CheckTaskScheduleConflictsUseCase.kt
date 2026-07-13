package dev.mshajkarami.memocraft.features.task.domain.usecase

import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.model.TaskScheduleConflictResult
import dev.mshajkarami.memocraft.features.task.domain.model.TaskTimeSuggestion
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

class CheckTaskScheduleConflictsUseCase @Inject constructor() {

    operator fun invoke(
        newTasks: List<Task>,
        existingTasks: List<Task>
    ): List<TaskScheduleConflictResult> {
        val unfinishedExistingTasks = existingTasks
            .filterNot { it.isCompleted }
            .filter { resolveTimeRange(it) != null }

        return newTasks.map { newTask ->
            val newTaskRange = resolveTimeRange(newTask)

            if (newTaskRange == null) {
                return@map TaskScheduleConflictResult(
                    task = newTask,
                    hasConflict = false
                )
            }

            val conflictingTasks = unfinishedExistingTasks.filter { existingTask ->
                val existingRange = resolveTimeRange(existingTask) ?: return@filter false

                isOverlapping(
                    firstStart = newTaskRange.startAt,
                    firstEnd = newTaskRange.endAt,
                    secondStart = existingRange.startAt,
                    secondEnd = existingRange.endAt
                )
            }

            if (conflictingTasks.isEmpty()) {
                TaskScheduleConflictResult(
                    task = newTask,
                    hasConflict = false
                )
            } else {
                TaskScheduleConflictResult(
                    task = newTask,
                    hasConflict = true,
                    conflictingTasks = conflictingTasks,
                    suggestions = suggestAlternativeTimes(
                        task = newTask,
                        existingTasks = unfinishedExistingTasks
                    )
                )
            }
        }
    }

    private fun resolveTimeRange(task: Task): TimeRange? {
        val startAt = task.startAt
        val endAt = task.endAt
        val dueAt = task.dueAt
        val estimatedDurationHours = task.estimatedDurationHours

        return when {
            startAt != null && endAt != null && endAt > startAt -> {
                TimeRange(
                    startAt = startAt,
                    endAt = endAt
                )
            }

            startAt != null && estimatedDurationHours != null && estimatedDurationHours > 0 -> {
                TimeRange(
                    startAt = startAt,
                    endAt = startAt.plusHours(estimatedDurationHours.toLong())
                )
            }

            endAt != null && estimatedDurationHours != null && estimatedDurationHours > 0 -> {
                TimeRange(
                    startAt = endAt.minusHours(estimatedDurationHours.toLong()),
                    endAt = endAt
                )
            }

            dueAt != null && estimatedDurationHours != null && estimatedDurationHours > 0 -> {
                TimeRange(
                    startAt = dueAt.minusHours(estimatedDurationHours.toLong()),
                    endAt = dueAt
                )
            }

            else -> null
        }
    }

    private fun isOverlapping(
        firstStart: LocalDateTime,
        firstEnd: LocalDateTime,
        secondStart: LocalDateTime,
        secondEnd: LocalDateTime
    ): Boolean {
        return firstStart < secondEnd && firstEnd > secondStart
    }

    private fun suggestAlternativeTimes(
        task: Task,
        existingTasks: List<Task>
    ): List<TaskTimeSuggestion> {
        val taskRange = resolveTimeRange(task) ?: return emptyList()

        val duration = Duration.between(taskRange.startAt, taskRange.endAt)

        if (duration.isZero || duration.isNegative) {
            return emptyList()
        }

        val busyRanges = existingTasks
            .mapNotNull { resolveTimeRange(it) }
            .sortedBy { it.startAt }

        val mergedBusyRanges = mergeBusyRanges(busyRanges)

        val suggestions = mutableListOf<TaskTimeSuggestion>()

        var searchCursor = taskRange.startAt

        while (suggestions.size < MAX_SUGGESTIONS) {
            val nextSlot = findNextAvailableSlot(
                searchStart = searchCursor,
                duration = duration,
                busyRanges = mergedBusyRanges
            )

            suggestions.add(
                TaskTimeSuggestion(
                    startAt = nextSlot.startAt,
                    endAt = nextSlot.endAt
                )
            )

            searchCursor = nextSlot.endAt.plusMinutes(GAP_BETWEEN_SUGGESTIONS_MINUTES)
        }

        return suggestions
    }

    private fun findNextAvailableSlot(
        searchStart: LocalDateTime,
        duration: Duration,
        busyRanges: List<TimeRange>
    ): TimeRange {
        var candidateStart = searchStart

        for (busyRange in busyRanges) {
            val candidateEnd = candidateStart.plus(duration)

            val candidateIsBeforeBusyRange = candidateEnd <= busyRange.startAt

            if (candidateIsBeforeBusyRange) {
                return TimeRange(
                    startAt = candidateStart,
                    endAt = candidateEnd
                )
            }

            val candidateOverlapsBusyRange = isOverlapping(
                firstStart = candidateStart,
                firstEnd = candidateEnd,
                secondStart = busyRange.startAt,
                secondEnd = busyRange.endAt
            )

            if (candidateOverlapsBusyRange) {
                candidateStart = busyRange.endAt
            }
        }

        return TimeRange(
            startAt = candidateStart,
            endAt = candidateStart.plus(duration)
        )
    }

    private fun mergeBusyRanges(
        ranges: List<TimeRange>
    ): List<TimeRange> {
        if (ranges.isEmpty()) return emptyList()

        val sortedRanges = ranges.sortedBy { it.startAt }
        val mergedRanges = mutableListOf<TimeRange>()

        var current = sortedRanges.first()

        for (index in 1 until sortedRanges.size) {
            val next = sortedRanges[index]

            val overlapsOrTouches = next.startAt <= current.endAt

            current = if (overlapsOrTouches) {
                TimeRange(
                    startAt = current.startAt,
                    endAt = maxOf(current.endAt, next.endAt)
                )
            } else {
                mergedRanges.add(current)
                next
            }
        }

        mergedRanges.add(current)

        return mergedRanges
    }

    private data class TimeRange(
        val startAt: LocalDateTime,
        val endAt: LocalDateTime
    )

    private companion object {
        const val MAX_SUGGESTIONS = 3
        const val GAP_BETWEEN_SUGGESTIONS_MINUTES = 15L
    }
}
