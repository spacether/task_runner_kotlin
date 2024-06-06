package io.taskrunner

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import io.taskdata.DbTaskRepository
import io.taskdata.Task
import io.taskdata.TaskRepository
import org.jetbrains.exposed.sql.Database
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

/**
 * Main entrypoint of the executable that starts a Netty webserver at port 8080
 * and registers the [module].
 *
 */

fun main() {
    embeddedServer(Netty, port = 8080) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    val repository = DbTaskRepository()
    configureRouting(repository)
    configureDatabases()
    configureTemplating()
}

fun configureDatabases() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/taskrunner_db",
        user = "taskrunner_readwriter",
        password = "xfdz8t-mds-V"
    )
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


fun Application.configureRouting(repository: TaskRepository) {
    routing {
        post("/tasks") {
            val formContent = call.receiveParameters()
            val name = formContent["name"].toString()
            val fileName = formContent["fileName"].toString()
            val minute = formContent["minute"]?.toIntOrNull()
            val hour = formContent["hour"]?.toIntOrNull()

            val task = Task(
                name, fileName, minute, hour
            )
            try {
                repository.addTask(task)
                call.respond(
                    ThymeleafContent("tasks", mapOf("tasks" to repository.allTasks()))
                )
            } catch (ex: Throwable) {
                call.respond(HttpStatusCode.BadRequest, message=ex.message!!)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest, message=ex.message!!)
            }
        }
        get("/tasks") {
            call.respond(ThymeleafContent("tasks", mapOf("tasks" to repository.allTasks())))
        }
    }
}