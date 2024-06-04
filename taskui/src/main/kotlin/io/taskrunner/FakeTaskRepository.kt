package io.taskrunner

class FakeTaskRepository : TaskRepository {
    private val tasks = mutableListOf(
        Task(null, null, "taskA", "TaskA.kt" ),
        Task(0, 10, "taskB", "TaskB.kt" )
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