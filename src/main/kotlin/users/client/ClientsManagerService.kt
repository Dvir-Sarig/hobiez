package users.client

import com.google.inject.Inject
import repositories.ClientRepository

class ClientsManagerService @Inject constructor(
    private val clientRepository: ClientRepository
) {
    fun addClient(client: Client) {
        clientRepository.addClient(client.name, client.email, client.password)
    }

    fun findClientByEmail(email: String): Client?{
        return clientRepository.findClientByEmail(email)
    }
}