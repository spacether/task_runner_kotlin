package io.taskpublisher

import kotlinx.serialization.Serializable

enum class Status {
    COMPLETED, RUNNING, FAILED, SCHEDULED
}

@Serializable
data class Task(
    val name: String,
    val fileName: String,
    val minute: Int?,
    val hour: Int?,
    val status: Status = Status.SCHEDULED
)