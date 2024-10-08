package lesson

import repositories.LessonRepository
import javax.inject.Inject

class LessonsManagerService @Inject constructor(
    private val lessonRepository: LessonRepository
) {
    fun addLesson(lesson: Lesson){
        lessonRepository.addLesson(lesson.title, lesson.description, lesson.time, lesson.coachId, lesson.price, lesson.capacityLimit)
    }
}