package lesson

import repositories.LessonRepository
import users.client.exceptions.LessonsCollisionException
import javax.inject.Inject

class LessonsManagerService @Inject constructor(
    private val lessonRepository: LessonRepository
) {
    fun addLesson(lesson: Lesson){
        lessonRepository.addLesson(lesson.title, lesson.description, lesson.time, lesson.coachId, lesson.price, lesson.capacityLimit)
    }

    fun addClientToLesson(clientId: Int, lessonId: Int) {
        val lesson = lessonRepository.getLessonById(lessonId)
        if (lessonRepository.isClientAlreadyRegistered(clientId, lesson!!.time)) {
            throw LessonsCollisionException("Client is already registered for a lesson at the same time")
        }
        lessonRepository.registerClientToLesson(clientId, lessonId)
    }
}