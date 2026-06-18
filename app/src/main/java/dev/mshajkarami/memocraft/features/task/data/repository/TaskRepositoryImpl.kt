package dev.mshajkarami.memocraft.features.task.data.repository

import android.util.Log
import dev.mshajkarami.memocraft.features.task.data.local.dao.TaskDao
import dev.mshajkarami.memocraft.features.task.data.mapper.toDomain
import dev.mshajkarami.memocraft.features.task.data.mapper.toEntity
import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun createTask(task: Task) {
        Log.d(
            "CreateTask",
            "Repository createTask: id=${task.id}, title=${task.title}, subTasks=${task.subTasks.size}"
        )

        val taskEntity = task.toEntity()
        val subTaskEntities = task.subTasks.map { subTask ->
            subTask.toEntity(taskId = task.id)
        }

        taskDao.insertTaskWithSubTasks(
            task = taskEntity,
            subTasks = subTaskEntities
        )

        val savedTask = taskDao.getTaskById(task.id)
        Log.d("CreateTask", "Saved task from DB: $savedTask")
    }

    override suspend fun updateTask(task: Task) {
        Log.d(
            "CreateTask",
            "Repository updateTask: id=${task.id}, title=${task.title}, subTasks=${task.subTasks.size}"
        )

        val taskEntity = task.toEntity()
        val subTaskEntities = task.subTasks.map { subTask ->
            subTask.toEntity(taskId = task.id)
        }

        taskDao.updateTaskWithSubTasks(
            task = taskEntity,
            subTasks = subTaskEntities
        )

        val updatedTask = taskDao.getTaskById(task.id)
        Log.d("CreateTask", "Updated task from DB: $updatedTask")
    }

    override suspend fun getTaskById(taskId: String): Task? {
        return taskDao.getTaskById(taskId)?.toDomain()
    }

    override fun observeTasks(): Flow<List<Task>> {
        return taskDao.observeTasks()
            .map { tasksWithSubTasks ->
                tasksWithSubTasks.map { taskWithSubTasks ->
                    taskWithSubTasks.toDomain()
                }
            }
    }

    override suspend fun deleteTaskById(taskId: String) {
        taskDao.deleteTaskById(taskId)
    }
}
