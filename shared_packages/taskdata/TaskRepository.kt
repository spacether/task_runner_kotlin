package io.taskdata

interface TaskRepository {
    suspend fun allTasks(): List<Task>
    suspend fun addTask(task: Task)
}