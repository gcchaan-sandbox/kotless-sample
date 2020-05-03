package io.kotless.examples

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import io.kotless.dsl.ktor.Kotless
import io.kotless.dsl.ktor.lang.LambdaWarming
import io.kotless.dsl.ktor.lang.event.events
import io.kotless.examples.controllers.TaskController
import io.kotless.examples.models.PostTaskParams
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.* // ktlint-disable no-wildcard-imports

class Server : Kotless() {
    override fun prepare(app: Application) {
        val config = ConfigFactory.load()
        val settings = config.extract<Map<String, String>>("settings")

        val taskController = TaskController()

        app.routing {
            get("/health_check") {
                call.respondText("OK")
            }
            get("/env") {
                call.respondText(settings["environment"] ?: "dev")
            }
            route("/tasks") {
                get("list") {
                    call.respond(taskController.findAll())
                }
                get("{id}") {
                    val ret = call.parameters["id"]?.let { id ->
                        taskController.findById(id)
                    }
                    ret?.let {
                        call.respondText(ret)
                    } ?: run {
                        call.respond(HttpStatusCode.NotFound) }
                }
                post {
                    val post = call.receive<PostTaskParams>()
                    taskController.create(post.title, post.description)
                    call.respond(HttpStatusCode.OK)
                }
                put("{id}") {
                    val put = call.receive<PostTaskParams>()
                    val id = call.parameters["id"]
                    if (id != null) {
                        taskController.update(id, put.title, put.description)
                    }
                    call.respond(HttpStatusCode.OK)
                }
                delete("{id}") {
                    call.parameters["id"]?.let { id ->
                        taskController.delete(id)
                    }
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
        app.events {
            subscribe(LambdaWarming) {
                println("Lambda warming execution")
            }
        }
    }
}
