package lesson

import lesson.requests.LessonSearchRequest
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

    fun getAvailableLessons(): List<Lesson>{
        return lessonRepository.getAvailableLessons()
    }

    fun getFilteredLessons(lessonSearchRequest: LessonSearchRequest): List<Lesson> {
        return getAvailableLessons().filter { lesson ->
            (lessonSearchRequest.maxPrice == null || lesson.price <= lessonSearchRequest.maxPrice) &&
            (lessonSearchRequest.lessonType == null || lesson.title == lessonSearchRequest.lessonType) &&
            (lessonSearchRequest.maxParticipants == null || lesson.capacityLimit <= lessonSearchRequest.maxParticipants)
        }
    }

    fun getAvailableLessonsSortedByPriceAsc(): List<Lesson> {
        return getAvailableLessons().sortedBy { it.price }
    }

    fun getAvailableLessonsSortedByPriceDesc(): List<Lesson> {
        return getAvailableLessons().sortedByDescending { it.price }
    }

    fun getLessonsForClient(clientId: Int): List<Lesson> {
        return lessonRepository.getLessonsForClient(clientId)
    }

    fun getLessonsForCoach(coachId: Int): List<Lesson> {
        return lessonRepository.getLessonsForCoach(coachId)
    }
}