package io.taskrunner

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.receiveParameters
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

/**
 * Main entrypoint of the executable that starts a Netty webserver at port 8080
 * and registers the [module].
 *
 */

fun main() {
    embeddedServer(Netty, port = 8080) {
        module()
        configureTemplating()
    }.start(wait = true)
}

fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }
}

fun Application.module() {
    val tasksByName = mutableMapOf(
        "taskA" to Task(null, null, "taskA", "TaskA.kt" ),
        "taskB" to Task(0, 10, "taskB", "TaskB.kt" )
    )
    routing {
        post("/tasks") {
            val formContent = call.receiveParameters()
            val minute = formContent["minute"]?.toIntOrNull()
            val hour = formContent["hour"]?.toIntOrNull()
            val name = formContent["name"].toString()
            val fileName = formContent["fileName"].toString()

            val task = Task(
                minute, hour, name, fileName
            )
            try {
                if (tasksByName.contains(task.name)) {
                   throw IllegalArgumentException("Invalid name. Name already exists.")
                }
                tasksByName[task.name] = task
                call.respond(
                    ThymeleafContent("tasks", mapOf("tasks" to tasksByName.values))
                )
            } catch (ex: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, message=ex.message!!)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        get("/tasks") {
            call.respond(ThymeleafContent("tasks", mapOf("tasks" to tasksByName.values)))
        }
    }
}