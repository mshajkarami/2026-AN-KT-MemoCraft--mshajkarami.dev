package dev.mshajkarami.memocraft.features.ai.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GapGptChatRequestDto(
    @SerializedName("model")
    val model: String,

    @SerializedName("messages")
    val messages: List<GapGptChatMessageDto>,

    @SerializedName("temperature")
    val temperature: Double = 0.2
)

data class GapGptChatMessageDto(
    @SerializedName("role")
    val role: String,

    @SerializedName("content")
    val content: String
)
