package dev.mshajkarami.memocraft.features.ai.presentation.ui

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

    /**
     * آیدی واقعی Task ذخیره‌شده.
     * برای دکمه View task و Navigation از این استفاده کن.
     */
    val savedTaskId: String = id,

    val title: String,

    /**
     * توضیحات واقعی Task.
     * برای نمایش زیر عنوان و چیپ Has details استفاده می‌شود.
     */
    val description: String? = null,

    /**
     * تاریخ واقعی تسک برای پردازش‌های بعدی.
     */
    val dueAt: LocalDateTime? = null,

    /**
     * متن آماده نمایش تاریخ.
     */
    val dueText: String = "No date",

    val priority: DetectedTaskPriority = DetectedTaskPriority.Normal,

    /**
     * متن دکمه.
     */
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
