package dev.mshajkarami.memocraft.features.task.domain.usecase

import dev.mshajkarami.memocraft.features.task.domain.model.TaskStatus
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) {

    suspend operator fun invoke(taskId: String) {
        val task = getTaskByIdUseCase(taskId)
            ?: throw IllegalArgumentException(
                "Task with id=$taskId was not found."
            )

        if (task.status == TaskStatus.Completed) {
            return
        }

        updateTaskUseCase(
            task.copy(
                status = TaskStatus.Completed
            )
        )
    }
}
