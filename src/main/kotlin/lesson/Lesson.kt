package lesson

import java.time.LocalDateTime

data class Lesson(
    val id: Int? = null,
    val title: LessonsType,
    val description: String,
    val time: LocalDateTime,
    val coachId: Int,
    val price: Double,
    val capacityLimit: Int
)
