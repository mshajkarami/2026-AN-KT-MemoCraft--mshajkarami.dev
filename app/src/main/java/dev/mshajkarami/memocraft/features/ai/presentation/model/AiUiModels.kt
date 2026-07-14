package dev.mshajkarami.memocraft.features.ai.presentation.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import dev.mshajkarami.memocraft.R
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
    val dueText: String? = null,
    val priority: DetectedTaskPriority = DetectedTaskPriority.Normal,
    val actionText: String? = null
)

enum class DetectedTaskPriority(
    @StringRes val labelResId: Int
) {
    Low(R.string.task_priority_low),
    Normal(R.string.task_priority_normal),
    Urgent(R.string.task_priority_urgent);

    fun color(): Color {
        return when (this) {
            Low -> Color(0xFF3BC7D6)
            Normal -> Color(0xFF7C5CFF)
            Urgent -> Color(0xFFFF4D6D)
        }
    }
}
