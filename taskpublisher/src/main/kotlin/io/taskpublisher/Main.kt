package io.taskpublisher

import java.time.LocalDateTime
import org.jetbrains.exposed.sql.Database
import kotlinx.coroutines.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import com.rabbitmq.client.ConnectionFactory

class Settings {
    companion object {
        const val QUEUE_NAME = "tasks"
        val DELAY_TIME = 1.toDuration(DurationUnit.MINUTES)
    }
}

suspend fun publishTask(repository: TaskRepository) {
    val now = LocalDateTime.now()
    val hour = now.hour
    val minute = now.minute
    val tasks = repository.queryTasks(hour, minute)
    print(tasks)
    print("I ran. hour = ${hour} minute=${minute} \n")

    val factory = ConnectionFactory()
    factory.host = "localhost"
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.queueDeclare(Settings.QUEUE_NAME, true, false, false, null)
    for (task in tasks) {
        val message = "Hello World!"
        channel.basicPublish("", Settings.QUEUE_NAME, null, message.toByteArray(charset("UTF-8")))
        println(" [x] Sent '$message'")
    }
    channel.close()
    connection.close()
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
        delay(Settings.DELAY_TIME)
    }
}