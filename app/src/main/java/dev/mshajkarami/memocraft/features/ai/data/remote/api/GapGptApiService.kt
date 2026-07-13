package dev.mshajkarami.memocraft.features.ai.data.remote.api

import dev.mshajkarami.memocraft.features.ai.data.remote.dto.GapGptChatRequestDto
import dev.mshajkarami.memocraft.features.ai.data.remote.dto.GapGptChatResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface GapGptApiService {

    @POST("chat/completions")
    suspend fun createChatCompletion(
        @Body request: GapGptChatRequestDto
    ): GapGptChatResponseDto
}