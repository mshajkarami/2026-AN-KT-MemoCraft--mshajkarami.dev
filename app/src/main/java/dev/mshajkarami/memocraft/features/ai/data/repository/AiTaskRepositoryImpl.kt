package dev.mshajkarami.memocraft.features.ai.data.repository

import android.util.Log
import com.google.gson.Gson
import dev.mshajkarami.memocraft.features.ai.data.remote.dto.GapGptChatMessageDto
import dev.mshajkarami.memocraft.features.ai.data.remote.dto.GapGptChatRequestDto
import dev.mshajkarami.memocraft.features.ai.data.mapper.AiChatResultMapper
import dev.mshajkarami.memocraft.features.ai.data.prompt.AiTaskPromptBuilder
import dev.mshajkarami.memocraft.features.ai.data.remote.api.GapGptApiService
import dev.mshajkarami.memocraft.features.ai.domain.exception.AiTaskException
import dev.mshajkarami.memocraft.features.ai.domain.model.AiChatResult
import dev.mshajkarami.memocraft.features.ai.domain.repository.AiTaskRepository
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AiTaskRepositoryImpl @Inject constructor(
    private val apiService: GapGptApiService,
    private val gson: Gson,
    private val promptBuilder: AiTaskPromptBuilder
) : AiTaskRepository {

    override suspend fun sendMessage(
        prompt: String,
        existingTasks: List<Task>
    ): AiChatResult {
        Log.d(TAG, "sendMessage() called")
        Log.d(TAG, "Original prompt: [$prompt]")
        Log.d(TAG, "Existing tasks count: ${existingTasks.size}")

        existingTasks.forEachIndexed { index, task ->
            logTask(
                label = "Existing task[$index]",
                task = task
            )
        }

        val cleanPrompt = prompt.trim()

        Log.d(TAG, "Clean prompt: [$cleanPrompt]")

        if (cleanPrompt.isBlank()) {
            Log.e(TAG, "Prompt is blank")
            throw AiTaskException.InvalidResponse()
        }

        val finalPrompt = promptBuilder.build(
            userMessage = cleanPrompt,
            existingTasks = existingTasks
        )

        Log.d(TAG, "Final prompt built successfully")
        Log.d(TAG, "Final prompt length: ${finalPrompt.length}")

        if (ENABLE_VERBOSE_LOGS) {
            Log.d(TAG, "Final prompt content:\n$finalPrompt")
        }

        val requestMessages = buildRequestMessages(finalPrompt)

        val request = GapGptChatRequestDto(
            model = MODEL,
            messages = requestMessages,
            temperature = TEMPERATURE
        )

        Log.d(TAG, "Request created")
        Log.d(TAG, "Model: $MODEL")
        Log.d(TAG, "Temperature: $TEMPERATURE")
        Log.d(TAG, "Messages count: ${requestMessages.size}")

        requestMessages.forEachIndexed { index, message ->
            Log.d(
                TAG,
                "Request message[$index]: role=${message.role}, contentLength=${message.content.length}"
            )

            if (ENABLE_VERBOSE_LOGS) {
                Log.d(TAG, "Request message[$index] content:\n${message.content}")
            }
        }

        if (ENABLE_VERBOSE_LOGS) {
            Log.d(TAG, "Full request JSON:\n${gson.toJson(request)}")
        }

        return try {
            Log.d(TAG, "Calling GapGPT API...")

            val response = apiService.createChatCompletion(request)

            Log.d(TAG, "API response received successfully")

            if (ENABLE_VERBOSE_LOGS) {
                Log.d(TAG, "Raw response DTO:\n${gson.toJson(response)}")
            }

            val result = AiChatResultMapper.toAiChatResult(
                responseDto = response,
                gson = gson
            )

            Log.d(TAG, "Response mapped to AiChatResult successfully")
            Log.d(TAG, "Assistant message: ${result.assistantMessage}")
            Log.d(TAG, "Detected tasks count: ${result.detectedTasks.size}")

            result.detectedTasks.forEachIndexed { index, task ->
                logTask(
                    label = "Detected task[$index]",
                    task = task
                )
            }

            result
        } catch (exception: AiTaskException) {
            Log.e(
                TAG,
                "AiTaskException occurred: ${exception::class.java.simpleName}",
                exception
            )
            throw exception
        } catch (exception: HttpException) {
            Log.e(TAG, "HttpException occurred", exception)
            Log.e(TAG, "HTTP status code: ${exception.code()}")
            Log.e(TAG, "HTTP message: ${exception.message()}")

            val errorBody = readHttpErrorBody(exception)

            Log.e(TAG, "HTTP error body: $errorBody")

            throw exception.toAiTaskException()
        } catch (exception: IOException) {
            Log.e(TAG, "IOException / Network error occurred", exception)
            throw AiTaskException.NetworkError(exception)
        } catch (exception: Exception) {
            Log.e(TAG, "Unknown exception occurred", exception)
            throw AiTaskException.Unknown(exception)
        }
    }

    private fun buildRequestMessages(finalPrompt: String): List<GapGptChatMessageDto> {
        return listOf(
            GapGptChatMessageDto(
                role = ROLE_SYSTEM,
                content = SYSTEM_MESSAGE.trimIndent()
            ),
            GapGptChatMessageDto(
                role = ROLE_USER,
                content = finalPrompt
            )
        )
    }

    private fun readHttpErrorBody(exception: HttpException): String? {
        return try {
            exception.response()
                ?.errorBody()
                ?.string()
        } catch (bodyException: Exception) {
            Log.e(TAG, "Failed to read error body", bodyException)
            null
        }
    }

    private fun HttpException.toAiTaskException(): AiTaskException {
        Log.d(TAG, "Mapping HttpException to AiTaskException. code=${code()}")

        return when (code()) {
            401, 403 -> {
                Log.e(TAG, "Unauthorized error. code=${code()}")
                AiTaskException.Unauthorized()
            }

            429 -> {
                Log.e(TAG, "Rate limited. code=${code()}")
                AiTaskException.RateLimited()
            }

            in 500..599 -> {
                Log.e(TAG, "Server error. code=${code()}")
                AiTaskException.ServerError(statusCode = code())
            }

            else -> {
                Log.e(TAG, "HTTP error. code=${code()}")
                AiTaskException.HttpError(
                    statusCode = code(),
                    cause = this
                )
            }
        }
    }

    private fun logTask(
        label: String,
        task: Task
    ) {
        Log.d(
            TAG,
            "$label: " +
                    "id=${task.id}, " +
                    "title=${task.title}, " +
                    "priority=${task.priority}, " +
                    "status=${task.status}, " +
                    "dueAt=${task.dueAt}, " +
                    "startAt=${task.startAt}, " +
                    "endAt=${task.endAt}, " +
                    "estimatedDurationHours=${task.estimatedDurationHours}, " +
                    "subTasks=${task.subTasks.size}"
        )
    }

    private companion object {
        const val TAG = "AiTaskRepository"

        const val MODEL = "gpt-4o-mini"

        const val ROLE_SYSTEM = "system"
        const val ROLE_USER = "user"

        const val TEMPERATURE = 0.3

        const val ENABLE_VERBOSE_LOGS = true

        const val SYSTEM_MESSAGE = """
You are MemoCraft AI assistant.

You can have normal daily conversations with the user.
You can also detect actionable tasks, reminders, plans, deadlines, and scheduled work.

Always return exactly one raw valid JSON object.
Do not return Markdown.
Do not use code fences.
Do not add text before or after the JSON.

The root JSON object must contain exactly:
- assistantMessage
- detectedTasks

Each detected task must use dueAt, startAt, and endAt for date-time fields:
- dueAt means the final deadline.
- startAt means the planned start time.
- endAt means the planned end time.

Do not use dueDate.
All date-time values must be local Gregorian date-time strings in this format:
yyyy-MM-dd'T'HH:mm:ss
"""
    }
}
