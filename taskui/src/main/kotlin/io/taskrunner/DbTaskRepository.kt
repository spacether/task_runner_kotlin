package io.taskrunner

import io.taskrunner.db.TaskDAO
import io.taskrunner.db.TaskTable
import io.taskrunner.db.daoToModel
import io.taskrunner.db.suspendTransaction
import org.jetbrains.exposed.dao.id.EntityID

class DbTaskRepository: TaskRepository {
    override suspend fun allTasks(): List<Task> = suspendTransaction {
        TaskDAO.all().map(::daoToModel)
    }

    override suspend fun addTask(task: Task): Unit = suspendTransaction {
        TaskDAO.new {
            name = EntityID(task.name, TaskTable)
            fileName = task.fileName
            hour = task.hour
            minute = task.minute
            status = task.status.name
        }
    }
}