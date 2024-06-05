package io.taskpublisher

import java.time.LocalDateTime
import org.jetbrains.exposed.sql.Database
import kotlinx.coroutines.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration

suspend fun publishTask(repository: TaskRepository) {
    val now = LocalDateTime.now()
    val hour = now.hour
    val minute = now.minute
    val tasks = repository.queryTasks(hour, minute)
    print(tasks)
    print("I ran. hour = ${hour} minute=${minute} \n")
}
fun configureDatabases() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/taskrunner_db",
        user = "taskrunner_readwriter",
        password = "xfdz8t-mds-V"
    )
}

suspend fun main() {
    configureDatabases()
    val repository = DbTaskRepository()
    while (true) {
        publishTask(repository)
        delay(1.toDuration(DurationUnit.MINUTES))
    }
}