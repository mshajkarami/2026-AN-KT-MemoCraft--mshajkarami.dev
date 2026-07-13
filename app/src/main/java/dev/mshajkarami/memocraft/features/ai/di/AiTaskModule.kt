package dev.mshajkarami.memocraft.features.ai.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mshajkarami.memocraft.features.ai.data.repository.AiTaskRepositoryImpl
import dev.mshajkarami.memocraft.features.ai.domain.repository.AiTaskRepository
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AiTaskBindModule {

    @Binds
    @Singleton
    abstract fun bindAiTaskRepository(
        impl: AiTaskRepositoryImpl
    ): AiTaskRepository
}

@Module
@InstallIn(SingletonComponent::class)
object AiTaskJsonModule {

    @Provides
    @Singleton
    fun provideAiJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
        }
    }
}
