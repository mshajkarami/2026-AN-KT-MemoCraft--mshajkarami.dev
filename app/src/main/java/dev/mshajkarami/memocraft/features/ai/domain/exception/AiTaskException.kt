package dev.mshajkarami.memocraft.features.ai.domain.exception

sealed class AiTaskException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {

    class EmptyResponse : AiTaskException(
        message = "The AI server returned an empty response."
    )

    class InvalidResponse(
        cause: Throwable? = null
    ) : AiTaskException(
        message = "The AI response is invalid.",
        cause = cause
    )

    class Unauthorized : AiTaskException(
        message = "The API key is invalid or unauthorized."
    )

    class RateLimited : AiTaskException(
        message = "Too many AI requests. Please try again later."
    )

    class ServerError(
        val statusCode: Int
    ) : AiTaskException(
        message = "The AI server encountered an error. Status: $statusCode"
    )

    class HttpError(
        val statusCode: Int,
        cause: Throwable? = null
    ) : AiTaskException(
        message = "The AI request failed. Status: $statusCode",
        cause = cause
    )

    class NetworkError(
        cause: Throwable? = null
    ) : AiTaskException(
        message = "Unable to connect to the AI server.",
        cause = cause
    )

    class Unknown(
        cause: Throwable? = null
    ) : AiTaskException(
        message = cause?.message ?: "An unknown AI error occurred.",
        cause = cause
    )
}
