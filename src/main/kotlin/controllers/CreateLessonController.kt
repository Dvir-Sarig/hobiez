package controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import lesson.Lesson
import lesson.LessonsManagerService
import javax.inject.Inject

class CreateLessonController @Inject constructor(
    private val lessonsManagerService: LessonsManagerService,
) {
    fun startRouting(route: Route) = with(route) {
        lessonCreationRoute()
    }

    private fun Route.lessonCreationRoute() {
        post("/create-lesson") {
            val lesson = call.receive<Lesson>()
            lessonsManagerService.addLesson(lesson)
            call.respond(HttpStatusCode.Created, "Coach created successfully")
        }
    }
}