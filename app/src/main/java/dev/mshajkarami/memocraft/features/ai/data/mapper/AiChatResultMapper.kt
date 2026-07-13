package dev.mshajkarami.memocraft.features.ai.data.mapper

import com.google.gson.Gson
import dev.mshajkarami.memocraft.features.ai.data.remote.dto.AiGeneratedTasksResponseDto
import dev.mshajkarami.memocraft.features.ai.data.remote.dto.GapGptChatResponseDto
import dev.mshajkarami.memocraft.features.ai.domain.exception.AiTaskException
import dev.mshajkarami.memocraft.features.ai.domain.model.AiChatResult
import dev.mshajkarami.memocraft.features.ai.domain.toTask

object AiChatResultMapper {

    fun toAiChatResult(
        responseDto: GapGptChatResponseDto,
        gson: Gson
    ): AiChatResult {
        val content = responseDto.choices
            .firstOrNull()
            ?.message
            ?.content
            ?.trim()
            .takeIf { content -> !content.isNullOrBlank() }
            ?: throw AiTaskException.InvalidResponse()

        return try {
            val cleanJsonContent = content.removeMarkdownJsonFence()

            val parsedResponse = gson.fromJson(
                cleanJsonContent,
                AiGeneratedTasksResponseDto::class.java
            ) ?: throw AiTaskException.InvalidResponse()

            AiChatResult(
                assistantMessage = parsedResponse.assistantMessage.trim(),
                detectedTasks = parsedResponse.detectedTasks.map { aiGeneratedTaskDto ->
                    aiGeneratedTaskDto.toTask()
                }
            )
        } catch (exception: AiTaskException) {
            throw exception
        } catch (exception: Exception) {
            throw AiTaskException.InvalidResponse(exception)
        }
    }

    private fun String.removeMarkdownJsonFence(): String {
        val trimmedContent = trim()
        val fence = "`".repeat(3)

        if (!trimmedContent.startsWith(fence)) {
            return trimmedContent
        }

        return trimmedContent
            .lines()
            .filterNot { line ->
                val trimmedLine = line.trim()

                trimmedLine == fence ||
                        trimmedLine.equals("${fence}json", ignoreCase = true)
            }
            .joinToString(separator = "\n")
            .trim()
    }
}
