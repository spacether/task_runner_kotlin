package io.taskdata

import io.taskmodels.Task


class FakeTaskRepository : TaskRepository {
    private val tasks = mutableListOf(
        Task("taskA", "TaskA.kt" , null, null),
        Task( "taskB", "TaskB.kt" , 99, 100)
    )

    fun taskByName(name: String) = tasks.find {
        it.name.equals(name, ignoreCase = true)
    }

    override suspend fun allTasks(): List<Task> = tasks

    override suspend fun queryTasks(hour: Int, minute: Int): List<Task> {
        return listOf()
    }

    override suspend fun addTask(task: Task) {
        if (taskByName(task.name) != null) {
            throw IllegalStateException("Cannot duplicate task names!")
        }
        tasks.add(task)
    }
}