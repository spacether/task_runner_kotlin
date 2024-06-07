package io.taskmodels

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val name: String,
    val fileName: String,
    val minute: Int?,
    val hour: Int?
)
