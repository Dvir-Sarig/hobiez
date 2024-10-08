package users.coach

import lesson.Lesson

class Coach(
    val name: String,
    val email: String,
    val password: String,
    val offeredLessons: MutableList<Lesson>
)
