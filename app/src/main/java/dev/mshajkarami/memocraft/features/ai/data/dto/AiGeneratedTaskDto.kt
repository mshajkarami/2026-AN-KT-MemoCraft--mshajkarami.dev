package dev.mshajkarami.memocraft.features.ai.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiGeneratedTasksResponseDto(
    val tasks: List<AiGeneratedTaskDto> = emptyList()
)

@Serializable
data class AiGeneratedTaskDto(
    val title: String,
    val description: String? = null,
    val dueDate: String? = null,
    val startAt: String? = null,
    val endAt: String? = null,
    val estimatedDurationHours: Int? = null,
    val priority: String? = null,
    val subTasks: List<AiGeneratedSubTaskDto> = emptyList()
)

@Serializable
data class AiGeneratedSubTaskDto(
    val title: String
)
