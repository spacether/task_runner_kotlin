package io.taskdata

interface TaskRepository {
    suspend fun allTasks(): List<Task>
    suspend fun queryTasks(hour: Int, minute: Int): List<Task>
}