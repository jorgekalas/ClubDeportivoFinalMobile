// src/main/kotlin/com/clubdeportivo/Participant.kt
package com.clubdeportivo

import org.jetbrains.exposed.sql.Table

object Participants : Table("MemberActivities") {
    val memberId   = integer("member_id").references(Members.id)
    val activityId = integer("activity_id").references(Activities.id)
    override val primaryKey = PrimaryKey(memberId, activityId)
}
