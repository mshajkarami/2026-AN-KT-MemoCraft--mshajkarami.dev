package dev.mshajkarami.memocraft.features.ai.data.remote

import dev.mshajkarami.memocraft.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class GapGptAuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = BuildConfig.GAPGPT_API_KEY

        check(apiKey.isNotBlank()) {
            "GAPGPT_API_KEY is missing. Add it to local.properties."
        }

        val authenticatedRequest = chain.request()
            .newBuilder()
            .header(
                "Authorization",
                "Bearer $apiKey"
            )
            .header(
                "Content-Type",
                "application/json"
            )
            .header(
                "Accept",
                "application/json"
            )
            .build()

        return chain.proceed(authenticatedRequest)
    }
}
