package dev.mshajkarami.memocraft.features.task.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class SubTask(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val completedAt: LocalDateTime? = null
)