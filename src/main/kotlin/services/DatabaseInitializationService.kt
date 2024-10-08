package services

import configurations.DatabaseConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import tables.CoachesTable
import javax.inject.Inject

class DatabaseInitializationService @Inject constructor(
    private val databaseConfig: DatabaseConfig
) {
    private val logger = LoggerFactory.getLogger(DatabaseInitializationService::class.java)
    fun connectToDatabase(databaseName: String): Database {
        return Database.connect(
            url = databaseConfig.dbUrl + databaseName,
            driver = "com.mysql.cj.jdbc.Driver",
            user = databaseConfig.dbUser,
            password = databaseConfig.dbPassword
        ).also {
            logger.info("Successfully connected to coaches database.")
            transaction(it) {
                SchemaUtils.create(CoachesTable)
                logger.info("Coaches table created successfully.")
            }
        }
    }
}