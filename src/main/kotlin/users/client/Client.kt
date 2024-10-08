package users.client

import lesson.Lesson

data class Client(
    val name: String,
    val email: String,
    val password: String,
    val registeredLessons: MutableList<Lesson> = mutableListOf()
)