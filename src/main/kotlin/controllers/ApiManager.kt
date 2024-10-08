package controllers

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import javax.inject.Inject
import io.ktor.http.*
import org.slf4j.LoggerFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.serialization.jackson.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

class ApiManager @Inject constructor(
    private val createUserController: CreateUserController,
    private val createLessonController: CreateLessonController
) {
    private val logger = LoggerFactory.getLogger(ApiManager::class.java)

    fun registerRoutes(application: Application) {
        application.install(ContentNegotiation) {
            jackson {
                registerKotlinModule() // תמיכה ב- Kotlin
            }
        }

        application.install(StatusPages) {
            exception<BadRequestException> { call, e ->
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Bad request")
            }
            exception<Exception> { call, e ->
                logger.error("An error occurred while processing a request", e)
                call.respond(HttpStatusCode.InternalServerError, "Internal server error")
            }
        }

        application.routing {
            createUserController.startRouting(this)
            createLessonController.startRouting(this)
        }
    }
}

