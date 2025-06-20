package com.clubdeportivo

/**
 * Data Transfer Object para exponer las actividades a través de la API.
 */
data class ActivityDTO(
    val id: Int,
    val name: String,
    val capacity: Int,
    val available: Boolean
)
