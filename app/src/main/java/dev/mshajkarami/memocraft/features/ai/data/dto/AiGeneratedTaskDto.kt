package dev.mshajkarami.memocraft.features.ai.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiGeneratedTasksResponseDto(
    val assistantMessage: String = "",
    val detectedTasks: List<AiGeneratedTaskDto> = emptyList()
)

@Serializable
data class AiGeneratedTaskDto(
    val id: String? = null,
    val title: String = "",
    val description: String? = null,
    val dueDate: String? = null,
    val startAt: String? = null,
    val endAt: String? = null,
    val estimatedDurationHours: Double? = null,
    val priority: String? = null,
    val status: String? = null,
    val subTasks: List<AiGeneratedSubTaskDto> = emptyList(),
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class AiGeneratedSubTaskDto(
    val title: String = ""
)
