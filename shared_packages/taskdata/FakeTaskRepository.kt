package io.taskdata

class FakeTaskRepository : TaskRepository {
    private val tasks = mutableListOf(
        Task("taskA", "TaskA.kt" , null, null),
        Task( "taskB", "TaskB.kt" , 99, 100)
    )

    fun taskByName(name: String) = tasks.find {
        it.name.equals(name, ignoreCase = true)
    }

    override suspend fun allTasks(): List<Task> = tasks

    override suspend fun addTask(task: Task) {
        if (taskByName(task.name) != null) {
            throw IllegalStateException("Cannot duplicate task names!")
        }
        tasks.add(task)
    }
}