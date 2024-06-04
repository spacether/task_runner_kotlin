package io.taskrunner

import kotlinx.serialization.Serializable

enum class Status {
    COMPLETED, RUNNING, FAILED, SCHEDULED
}

@Serializable
data class Task(
    val minute: Int?,
    val hour: Int?,
    val name: String,
    val fileName: String,
    val status: Status = Status.SCHEDULED
)
