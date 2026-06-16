package dev.mshajkarami.memocraft.features.task.domain.usecase

import dev.mshajkarami.memocraft.features.task.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(taskId: String) {
        require(taskId.isNotBlank()) {
            "Task id cannot be blank."
        }

        taskRepository.deleteTaskById(taskId)
    }
}