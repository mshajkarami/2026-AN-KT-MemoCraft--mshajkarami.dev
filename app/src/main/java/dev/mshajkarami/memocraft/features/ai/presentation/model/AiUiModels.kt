package dev.mshajkarami.memocraft.features.ai.presentation.model

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime
import java.util.UUID

data class AiChatMessageUiModel(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val isFromUser: Boolean,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val detectedTasks: List<DetectedTaskUiModel> = emptyList()
)

data class DetectedTaskUiModel(
    val id: String = UUID.randomUUID().toString(),

    val savedTaskId: String = id,

    val title: String,

    val description: String? = null,

    val dueAt: LocalDateTime? = null,

    val dueText: String = "No date",

    val priority: DetectedTaskPriority = DetectedTaskPriority.Normal,

    val actionText: String = "View task"
)

enum class DetectedTaskPriority(
    val label: String
) {
    Low("Low"),
    Normal("Normal"),
    Urgent("Urgent");

    fun color(): Color {
        return when (this) {
            Low -> Color(0xFF3BC7D6)
            Normal -> Color(0xFF7C5CFF)
            Urgent -> Color(0xFFFF4D6D)
        }
    }
}
