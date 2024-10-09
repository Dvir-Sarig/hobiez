package users.client

import users.User

data class Client(
    override val id: Int? = null,
    override val name: String,
    override val email: String,
    override val password: String,
) : User(id, name, email, password)
