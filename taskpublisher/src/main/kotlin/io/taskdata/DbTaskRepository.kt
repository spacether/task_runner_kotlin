package io.taskdata

import io.taskdata.db.TaskDAO
import io.taskdata.db.TaskTable
import io.taskdata.db.daoToModel
import io.taskdata.db.suspendTransaction
import org.jetbrains.exposed.sql.*

class DbTaskRepository: TaskRepository {
    override suspend fun allTasks(): List<Task> = suspendTransaction {
        TaskDAO.all().map(::daoToModel)
    }

    override suspend fun queryTasks(hour: Int, minute: Int): List<Task> {
        return suspendTransaction {
            val condition = Op.build {
                ((TaskTable.hour eq null) or (TaskTable.hour eq hour))
                    .and((TaskTable.minute eq null) or (TaskTable.minute eq minute))
            }
            val query = TaskTable.select(condition)
            val taskDaos = query.map{ TaskDAO.wrapRow(it) }
            taskDaos.map { daoToModel(it) }
        }
    }
}