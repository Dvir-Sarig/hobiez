package controllers

import com.google.inject.Inject
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import lesson.LessonsManagerService
import lesson.requests.LessonRegistrationRequest
import users.client.exceptions.LessonsCollisionException

class LessonRegistrationController @Inject constructor(
    private val lessonsManagerService: LessonsManagerService,
) {
    fun startRouting(route: Route) = with(route) {
        lessonRegistrationRoute()
    }

    private fun Route.lessonRegistrationRoute() {
        post("/register-client-to-lesson") {
            val registrationRequest = call.receive<LessonRegistrationRequest>()
            try {
                lessonsManagerService.addClientToLesson(registrationRequest.clientId, registrationRequest.lessonId)
                call.respond(HttpStatusCode.OK, "Client registered to lesson successfully")
            } catch (e: LessonsCollisionException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Registration failed")
            }
        }
    }
}
