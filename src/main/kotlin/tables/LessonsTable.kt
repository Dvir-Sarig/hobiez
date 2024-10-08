package tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object LessonsTable : Table("lessons") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val description = text("description")
    val time = datetime("time")
    val coachId = integer("coach_id") references CoachesTable.id
    val price = double("price")
    val capacityLimit = integer("capacity_limit")

    override val primaryKey = PrimaryKey(id)
}


