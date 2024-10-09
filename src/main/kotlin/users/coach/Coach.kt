package users.coach

import users.User

data class Coach(
    override val id: Int? = null,
    override val name: String,
    override val email: String,
    override val password: String,
) : User(id, name, email, password)
