package dev.mshajkarami.memocraft.features.ai.data.dto

import com.google.gson.annotations.SerializedName

data class GapGptChatResponseDto(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("choices")
    val choices: List<GapGptChoiceDto> = emptyList(),

    @SerializedName("usage")
    val usage: GapGptUsageDto? = null
)

data class GapGptChoiceDto(
    @SerializedName("index")
    val index: Int? = null,

    @SerializedName("message")
    val message: GapGptResponseMessageDto? = null,

    @SerializedName("finish_reason")
    val finishReason: String? = null
)

data class GapGptResponseMessageDto(
    @SerializedName("role")
    val role: String? = null,

    @SerializedName("content")
    val content: String? = null
)

data class GapGptUsageDto(
    @SerializedName("prompt_tokens")
    val promptTokens: Int? = null,

    @SerializedName("completion_tokens")
    val completionTokens: Int? = null,

    @SerializedName("total_tokens")
    val totalTokens: Int? = null
)
