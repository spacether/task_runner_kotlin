package io.taskrunner

interface TaskRepository {
    suspend fun allTasks(): List<Task>
    suspend fun addTask(task: Task)
}