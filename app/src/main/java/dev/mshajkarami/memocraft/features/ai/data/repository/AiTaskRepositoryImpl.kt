package dev.mshajkarami.memocraft.features.ai.data.repository

import com.google.gson.Gson
import dev.mshajkarami.memocraft.features.ai.data.dto.GapGptChatMessageDto
import dev.mshajkarami.memocraft.features.ai.data.dto.GapGptChatRequestDto
import dev.mshajkarami.memocraft.features.ai.data.mapper.AiTaskMapper
import dev.mshajkarami.memocraft.features.ai.data.prompt.AiTaskPromptBuilder
import dev.mshajkarami.memocraft.features.ai.data.remote.GapGptApiService
import dev.mshajkarami.memocraft.features.ai.domain.exception.AiTaskException
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

    override suspend fun generateTasksFromPrompt(
        prompt: String
    ): List<Task> {
        val cleanPrompt = prompt.trim()

        require(cleanPrompt.isNotBlank()) {
            "Prompt cannot be blank."
        }

        val finalPrompt = promptBuilder.build(cleanPrompt)

        val requestMessages = buildList {
            add(
                GapGptChatMessageDto(
                    role = ROLE_SYSTEM,
                    content = SYSTEM_MESSAGE
                )
            )

            add(
                GapGptChatMessageDto(
                    role = ROLE_USER,
                    content = finalPrompt
                )
            )
        }

        val request = GapGptChatRequestDto(
            model = MODEL,
            messages = requestMessages,
            temperature = TEMPERATURE
        )

        return try {
            val response = apiService.createChatCompletion(request)

            AiTaskMapper.toTasks(
                responseDto = response,
                gson = gson
            )
        } catch (exception: AiTaskException) {
            throw exception
        } catch (exception: HttpException) {
            throw exception.toAiTaskException()
        } catch (exception: IOException) {
            throw AiTaskException.NetworkError(exception)
        } catch (exception: Exception) {
            throw AiTaskException.Unknown(exception)
        }
    }

    private fun HttpException.toAiTaskException(): AiTaskException {
        return when (code()) {
            401, 403 -> {
                AiTaskException.Unauthorized()
            }

            429 -> {
                AiTaskException.RateLimited()
            }

            in 500..599 -> {
                AiTaskException.ServerError(
                    statusCode = code()
                )
            }

            else -> {
                AiTaskException.HttpError(
                    statusCode = code(),
                    cause = this
                )
            }
        }
    }

    private companion object {
        const val MODEL = "gpt-4o-mini"

        const val ROLE_SYSTEM = "system"
        const val ROLE_USER = "user"

        const val TEMPERATURE = 0.2

        const val SYSTEM_MESSAGE = """
            You are the task extraction engine of MemoCraft.
            Analyze the user's message and extract actionable tasks.
            Follow the requested JSON schema exactly.
            Return only one valid JSON object.
            Do not return Markdown.
            Do not use code fences.
            Do not add any text before or after the JSON.
        """
    }
}
