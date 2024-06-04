package io.taskrunner

enum class Status {
    COMPLETED, RUNNING, FAILED, SCHEDULED
}

data class Task(
    val minute: Int?,
    val hour: Int?,
    val name: String,
    val fileName: String,
    val status: Status = Status.SCHEDULED
)
