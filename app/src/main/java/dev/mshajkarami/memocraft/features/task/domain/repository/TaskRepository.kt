package dev.mshajkarami.memocraft.features.task.domain.repository

import dev.mshajkarami.memocraft.features.task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun createTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun getTaskById(taskId: String): Task?
    fun observeTasks(): Flow<List<Task>>
    suspend fun deleteTaskById(taskId: String)

    fun getAllTasks(): Flow<List<Task>>

}
