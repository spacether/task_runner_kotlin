package io.taskdata

import io.taskdata.db.TaskDAO
import io.taskdata.db.TaskTable
import io.taskdata.db.daoToModel
import io.taskdata.db.suspendTransaction
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