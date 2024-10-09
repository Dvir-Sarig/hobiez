package users

abstract class User(
    open val id: Int?,
    open val name: String,
    open val email: String,
    open val password: String
)
