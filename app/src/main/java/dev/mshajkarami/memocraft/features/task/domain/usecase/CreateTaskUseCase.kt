package dev.mshajkarami.memocraft.features.task.domain.usecase

import dev.mshajkarami.memocraft.features.task.domain.model.Task
import dev.mshajkarami.memocraft.features.task.domain.repository.TaskRepository
import java.time.LocalDateTime
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {
        val trimmedTitle = task.title.trim()

        require(trimmedTitle.isNotBlank()) {
            "Task title cannot be blank."
        }

        require(task.estimatedDurationHours == null || task.estimatedDurationHours > 0) {
            "Estimated duration hours must be greater than zero."
        }

        val now = LocalDateTime.now()

        val normalizedTask = task.copy(
            title = trimmedTitle,
            createdAt = task.createdAt,
            updatedAt = now
        )

        taskRepository.createTask(normalizedTask)
    }
}