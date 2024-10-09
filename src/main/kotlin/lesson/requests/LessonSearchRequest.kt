package lesson.requests

import lesson.LessonsType

data class LessonSearchRequest(
    val maxPrice: Double? = null,
    val lessonType: LessonsType? = null,
    val maxParticipants: Int? = null
)
