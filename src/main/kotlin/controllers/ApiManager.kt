package controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import javax.inject.Inject
import io.ktor.http.*
import org.slf4j.LoggerFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.serialization.jackson.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import services.TokenService

class ApiManager @Inject constructor(
    private val createUserController: CreateUserController,
    private val createLessonController: CreateLessonController,
    private val lessonRegistrationController: LessonRegistrationController,
    private val loginController: LoginController
) {
    private val logger = LoggerFactory.getLogger(ApiManager::class.java)

    fun registerRoutes(application: Application) {
        // הגדרת JSON ו-Status Pages
        application.install(ContentNegotiation) {
            jackson {
                registerKotlinModule()  // תמיכה ב-Kotlin
                registerModule(JavaTimeModule())  // תמיכה ב-LocalDateTime
                disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)  // פורמט תאריכים קריא
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

        // התקנת Authentication עבור JWT
        application.install(Authentication) {
            jwt("auth-jwt") {
                verifier(JWT.require(Algorithm.HMAC256(TokenService.SECRET_KEY.encoded)).build())
                validate { credential ->
                    if (credential.payload.subject != null) {
                        JWTPrincipal(credential.payload)
                    } else null
                }
                challenge { _, _ ->
                    call.respond(HttpStatusCode.Unauthorized, "Token is invalid or expired")
                }
            }
        }

        // הגדרת הנתיבים
        application.routing {
            // נתיבים לא מוגנים
            createUserController.startRouting(this)
            createLessonController.startRouting(this)
            loginController.startRouting(this)

            // נתיבים מוגנים על ידי JWT
            authenticate("auth-jwt") {
                lessonRegistrationController.startRouting(this)
            }
        }
    }
}


