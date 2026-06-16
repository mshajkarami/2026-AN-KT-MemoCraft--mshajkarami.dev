package dev.mshajkarami.memocraft.features.task.data.mapper

import dev.mshajkarami.memocraft.features.task.data.local.entity.SubTaskEntity
import dev.mshajkarami.memocraft.features.task.data.local.entity.TaskEntity
import dev.mshajkarami.memocraft.features.task.data.local.entity.TaskWithSubTasks
import dev.mshajkarami.memocraft.features.task.domain.model.SubTask
import dev.mshajkarami.memocraft.features.task.domain.model.Task

fun TaskWithSubTasks.toDomain(): Task {
    return task.toDomain(
        subTasks = subTasks.map { it.toDomain() }
    )
}

fun TaskEntity.toDomain(
    subTasks: List<SubTask> = emptyList()
): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        dueDate = dueDate,
        startAt = startAt,
        endAt = endAt,
        estimatedDurationHours = estimatedDurationHours,
        priority = priority,
        status = status,
        subTasks = subTasks,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        dueDate = dueDate,
        startAt = startAt,
        endAt = endAt,
        estimatedDurationHours = estimatedDurationHours,
        priority = priority,
        status = status,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun SubTaskEntity.toDomain(): SubTask {
    return SubTask(
        id = id,
        title = title,
        isCompleted = isCompleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        completedAt = completedAt
    )
}

fun SubTask.toEntity(
    taskId: String
): SubTaskEntity {
    return SubTaskEntity(
        id = id,
        taskId = taskId,
        title = title,
        isCompleted = isCompleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        completedAt = completedAt
    )
}