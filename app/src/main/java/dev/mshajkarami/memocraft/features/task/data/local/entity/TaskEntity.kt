package dev.mshajkarami.memocraft.features.task.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val dueDate: LocalDate?,
    val startAt: LocalDateTime?,
    val endAt: LocalDateTime?,
    val priority: TaskPriority,
    val status: TaskStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
