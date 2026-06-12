package dev.mshajkarami.memocraft.features.task.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.mshajkarami.memocraft.features.task.data.local.converter.TaskTypeConverters
import dev.mshajkarami.memocraft.features.task.data.local.dao.TaskDao
import dev.mshajkarami.memocraft.features.task.data.local.entity.SubTaskEntity
import dev.mshajkarami.memocraft.features.task.data.local.entity.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        SubTaskEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(TaskTypeConverters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "memocraft_tasks.db"
    }
}
