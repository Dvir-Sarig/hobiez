package repositories

import com.google.inject.name.Named
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import tables.ClientsTable
import users.client.Client
import javax.inject.Inject

class ClientRepository @Inject constructor(
    @Named("clientDatabase") private val clientDatabase: Database
) {

    fun addClient(name: String, email: String, password: String){
        transaction(clientDatabase) {
            ClientsTable.insert {
                it[ClientsTable.name] = name
                it[ClientsTable.email] = email
                it[ClientsTable.password] = password
            }
        }
    }

    fun updateClient(id: Int, name: String, email: String, password: String): Boolean {
        return transaction(clientDatabase) {
            ClientsTable.update({ ClientsTable.id eq id }) {
                it[ClientsTable.name] = name
                it[ClientsTable.email] = email
                it[ClientsTable.password] = password
            } > 0
        }
    }

    fun deleteClient(id: Int): Boolean {
        return transaction(clientDatabase) {
            ClientsTable.deleteWhere { ClientsTable.id eq id } > 0
        }
    }

    fun findClientByEmail(email: String): Client? {
        return transaction(clientDatabase) {
            ClientsTable.select { ClientsTable.email eq email }
                .map { rowToClient(it) }
                .singleOrNull()
        }
    }

    fun findClientById(clientId: Int): Client?{
        return transaction(clientDatabase) {
            ClientsTable.select { ClientsTable.id eq clientId }
                .map { rowToClient(it) }
                .singleOrNull()
        }
    }

    private fun rowToClient(row: ResultRow): Client {
        return Client(
            id = row[ClientsTable.id],
            name = row[ClientsTable.name],
            email = row[ClientsTable.email],
            password = row[ClientsTable.password]
        )
    }
}
