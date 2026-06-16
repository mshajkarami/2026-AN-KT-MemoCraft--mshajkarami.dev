package dev.mshajkarami.memocraft.features.task.di

import android.content.Context
import androidx.room.Room
import dev.mshajkarami.memocraft.features.task.data.local.dao.TaskDao
import dev.mshajkarami.memocraft.features.task.data.local.database.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskDatabaseModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TaskDatabase::class.java,
            name = TaskDatabase.DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(
        database: TaskDatabase
    ): TaskDao {
        return database.taskDao()
    }
}