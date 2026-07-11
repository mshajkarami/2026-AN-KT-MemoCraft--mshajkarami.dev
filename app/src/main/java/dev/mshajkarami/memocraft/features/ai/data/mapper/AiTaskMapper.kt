package dev.mshajkarami.memocraft.features.ai.data.mapper

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import dev.mshajkarami.memocraft.features.ai.data.dto.AiGeneratedTaskDto
import dev.mshajkarami.memocraft.features.ai.data.dto.AiGeneratedTasksResponseDto
import dev.mshajkarami.memocraft.features.ai.data.dto.GapGptChatResponseDto
import dev.mshajkarami.memocraft.features.ai.domain.exception.AiTaskException
import dev.mshajkarami.memocraft.features.ai.domain.toTask
import dev.mshajkarami.memocraft.features.task.domain.model.Task

object AiTaskMapper {

    private const val TAG = "AiTaskMapper"

    fun toTasks(
        responseDto: GapGptChatResponseDto,
        gson: Gson
    ): List<Task> {
        val rawContent = responseDto.choices
            ?.firstOrNull()
            ?.message
            ?.content
            ?.trim()
            .orEmpty()

        if (rawContent.isBlank()) {
            throw AiTaskException.EmptyResponse()
        }

        Log.d(TAG, "Raw AI response: $rawContent")

        val jsonContent = extractJson(rawContent)
            ?: throw AiTaskException.InvalidResponse()

        Log.d(TAG, "Extracted AI JSON: $jsonContent")

        return try {
            // حالت ۱: اگر آرایه خالی بود
            if (jsonContent == "[]") return emptyList()

            // تشخیص ساختار (آرایه یا آبجکت)
            if (jsonContent.startsWith("[")) {
                // تبدیل مستقیم آرایه به لیست
                val listType = object : TypeToken<List<AiGeneratedTaskDto>>() {}.type
                val taskDtos: List<AiGeneratedTaskDto> = gson.fromJson(jsonContent, listType)
                taskDtos.filter { it.title.isNotBlank() }.map { it.toTask() }
            } else {
                // تبدیل به آبجکتِ کانتینر
                val response = gson.fromJson(jsonContent, AiGeneratedTasksResponseDto::class.java)
                response.tasks.orEmpty()
                    .filter { it.title.isNotBlank() }
                    .map { it.toTask() }
            }
        } catch (exception: JsonSyntaxException) {
            Log.e(TAG, "Failed to parse JSON: $jsonContent", exception)
            throw AiTaskException.InvalidResponse(exception)
        } catch (exception: Exception) {
            Log.e(TAG, "Unexpected error in mapping: $jsonContent", exception)
            throw AiTaskException.InvalidResponse(exception)
        }
    }

    private fun extractJson(rawContent: String): String? {
        val trimmed = rawContent.trim()

        // پیدا کردن شروع و پایان بر اساس نوع محتوا
        val startChar = trimmed.firstOrNull() ?: return null

        val startIndex = if (startChar == '{') trimmed.indexOf('{') else if (startChar == '[') trimmed.indexOf('[') else -1
        val endIndex = if (startChar == '{') trimmed.lastIndexOf('}') else if (startChar == '[') trimmed.lastIndexOf(']') else -1

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return trimmed.substring(startIndex, endIndex + 1)
        }

        return null
    }
}
