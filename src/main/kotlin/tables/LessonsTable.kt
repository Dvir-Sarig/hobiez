package tables

import lesson.LessonsType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object LessonsTable : Table("lessons") {
    val id = integer("id").autoIncrement()
    val title = enumerationByName("title", 50, LessonsType::class)
    val description = text("description")
    val time = datetime("time")
    val coachId = integer("coach_id") references CoachesTable.id
    val price = double("price")
    val capacityLimit = integer("capacity_limit")

    override val primaryKey = PrimaryKey(id)
}


