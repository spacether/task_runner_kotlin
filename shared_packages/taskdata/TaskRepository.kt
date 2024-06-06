package io.taskdata

import io.taskmodels.Task

interface TaskRepository {
    suspend fun allTasks(): List<Task>
    suspend fun addTask(task: Task)

    suspend fun queryTasks(hour: Int, minute: Int): List<Task>
}