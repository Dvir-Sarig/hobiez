package users.client

data class Client(
    val id: Int? = null,
    val name: String,
    val email: String,
    val password: String,
)