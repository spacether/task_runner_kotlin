package io.taskrunner

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import java.io.IOException
import kotlinx.serialization.json.Json
import io.taskmodels.TaskMessage

class Worker {
    companion object {
        const val TASK_QUEUE_NAME = "tasks"

        fun doWork(message: String) {
            val tasmMessage = Json.decodeFromString<TaskMessage>(message)
            print(tasmMessage)
        }
    }
}

fun main() {
    val factory = ConnectionFactory()
    factory.host = "localhost"
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.queueDeclare(Worker.TASK_QUEUE_NAME, true, false, false, null)
    println(" [*] Waiting for messages. To exit press CTRL+C")

    channel.basicQos(1)

    val consumer = object : DefaultConsumer(channel) {
        @Throws(IOException::class)
        override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: ByteArray) {
            val message = String(body, charset("UTF-8"))

            println(" [x] Received '$message'")
            try {
                Worker.doWork(message)
            } catch (e: Exception) {
                print(e.message)
            } finally {
                println(" [x] Done")
                channel.basicAck(envelope.deliveryTag, false)
            }
        }
    }
    channel.basicConsume(Worker.TASK_QUEUE_NAME, false, consumer)
}