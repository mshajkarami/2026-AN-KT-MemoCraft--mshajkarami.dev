package dev.mshajkarami.memocraft.features.task.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.mshajkarami.memocraft.features.task.data.local.entity.SubTaskEntity
import dev.mshajkarami.memocraft.features.task.data.local.entity.TaskEntity
import dev.mshajkarami.memocraft.features.task.data.local.entity.TaskWithSubTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubTasks(subTasks: List<SubTaskEntity>)

    @Transaction
    suspend fun insertTaskWithSubTasks(
        task: TaskEntity,
        subTasks: List<SubTaskEntity>
    ) {
        insertTask(task)
        if (subTasks.isNotEmpty()) {
            insertSubTasks(subTasks)
        }
    }

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun observeTasks(): Flow<List<TaskWithSubTasks>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    fun observeTaskById(taskId: String): Flow<TaskWithSubTasks?>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: String): TaskWithSubTasks?

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: String)
}
