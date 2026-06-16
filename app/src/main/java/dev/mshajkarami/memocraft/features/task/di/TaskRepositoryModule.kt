package dev.mshajkarami.memocraft.features.task.di

import dev.mshajkarami.memocraft.features.task.data.repository.TaskRepositoryImpl
import dev.mshajkarami.memocraft.features.task.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository
}
