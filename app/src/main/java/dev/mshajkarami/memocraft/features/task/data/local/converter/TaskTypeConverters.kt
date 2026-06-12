package dev.mshajkarami.memocraft.features.task.data.local.converter

import androidx.room.TypeConverter
import dev.mshajkarami.memocraft.features.task.domain.model.TaskPriority
import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus
import java.time.LocalDate
import java.time.LocalDateTime

class TaskTypeConverters {

    @TypeConverter
    fun localDateToString(value: LocalDate?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun stringToLocalDate(value: String?): LocalDate? {
        return value?.let(LocalDate::parse)
    }

    @TypeConverter
    fun localDateTimeToString(value: LocalDateTime?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun stringToLocalDateTime(value: String?): LocalDateTime? {
        return value?.let(LocalDateTime::parse)
    }

    @TypeConverter
    fun taskPriorityToString(value: TaskPriority): String {
        return value.name
    }

    @TypeConverter
    fun stringToTaskPriority(value: String): TaskPriority {
        return TaskPriority.valueOf(value)
    }

    @TypeConverter
    fun taskStatusToString(value: TaskStatus): String {
        return value.name
    }

    @TypeConverter
    fun stringToTaskStatus(value: String): TaskStatus {
        return TaskStatus.valueOf(value)
    }
}
