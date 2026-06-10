package dev.mshajkarami.memocraft.features.ai.presentation.ui

import androidx.compose.ui.graphics.Color
import java.util.UUID

data class AiChatMessageUiModel(
    val id: String,
    val content: String,
    val isFromUser: Boolean,
    val detectedTasks: List<DetectedTaskUiModel> = emptyList()
)

data class DetectedTaskUiModel(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val dueText: String = "No date",
    val priority: DetectedTaskPriority = DetectedTaskPriority.Normal
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

fun detectTasksFromText(
    text: String
): List<DetectedTaskUiModel> {
    val normalizedText = text.trim()

    if (normalizedText.isBlank()) return emptyList()

    val taskSeparators = listOf("،", ",", ".", "\n", " و ", " and ")

    var parts = listOf(normalizedText)

    taskSeparators.forEach { separator ->
        parts = parts.flatMap { part ->
            part.split(separator)
        }
    }

    val actionKeywords = listOf(
        "باید",
        "انجام",
        "آماده",
        "آماده کنم",
        "بسازم",
        "بنویسم",
        "ارسال",
        "ارسال کنم",
        "زنگ",
        "تماس",
        "جلسه",
        "یادآوری",
        "review",
        "prepare",
        "send",
        "call",
        "write",
        "create",
        "fix",
        "meeting",
        "todo",
        "task"
    )

    val dueText = extractDueText(normalizedText)
    val priority = extractPriority(normalizedText)

    return parts
        .map { it.trim() }
        .filter { part ->
            part.length >= 4 && actionKeywords.any { keyword ->
                part.contains(keyword, ignoreCase = true)
            }
        }
        .map { part ->
            DetectedTaskUiModel(
                title = cleanTaskTitle(part),
                dueText = dueText,
                priority = priority
            )
        }
        .distinctBy { it.title.lowercase() }
        .take(5)
}

private fun extractDueText(
    text: String
): String {
    return when {
        text.contains("امروز") -> "Today"
        text.contains("فردا") -> "Tomorrow"
        text.contains("پس فردا") -> "After tomorrow"
        text.contains("هفته بعد") -> "Next week"
        text.contains("today", ignoreCase = true) -> "Today"
        text.contains("tomorrow", ignoreCase = true) -> "Tomorrow"
        text.contains("next week", ignoreCase = true) -> "Next week"
        Regex("\\b\\d{1,2}:\\d{2}\\b").containsMatchIn(text) -> {
            Regex("\\b\\d{1,2}:\\d{2}\\b").find(text)?.value ?: "Scheduled"
        }
        Regex("\\b\\d{1,2}\\s?(AM|PM|am|pm)\\b").containsMatchIn(text) -> {
            Regex("\\b\\d{1,2}\\s?(AM|PM|am|pm)\\b").find(text)?.value ?: "Scheduled"
        }
        else -> "No date"
    }
}

private fun extractPriority(
    text: String
): DetectedTaskPriority {
    return when {
        text.contains("فوری") ||
                text.contains("ضروری") ||
                text.contains("urgent", ignoreCase = true) ||
                text.contains("important", ignoreCase = true) -> DetectedTaskPriority.Urgent

        text.contains("کم اهمیت") ||
                text.contains("low", ignoreCase = true) -> DetectedTaskPriority.Low

        else -> DetectedTaskPriority.Normal
    }
}

private fun cleanTaskTitle(
    value: String
): String {
    return value
        .replace("باید", "")
        .replace("رو", "")
        .replace("را", "")
        .replace("todo", "", ignoreCase = true)
        .replace("task", "", ignoreCase = true)
        .trim()
        .replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase() else char.toString()
        }
}
