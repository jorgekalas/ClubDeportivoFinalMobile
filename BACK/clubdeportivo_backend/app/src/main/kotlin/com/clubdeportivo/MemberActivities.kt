
package com.clubdeportivo

import org.jetbrains.exposed.sql.Table

/**
 * Tabla intermedia que relaciona socios con actividades
 */
object MemberActivities : Table("member_activities") {
    val id         = integer("id").autoIncrement()
    val memberId   = integer("member_id")   references Members.id
    val activityId = integer("activity_id") references Activities.id

    override val primaryKey = PrimaryKey(id, name = "PK_MemberActivities_ID")
}