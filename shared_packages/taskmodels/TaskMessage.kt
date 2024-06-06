package io.taskmodels

import kotlinx.serialization.Serializable

@Serializable
data class TaskMessage(
    val task: Task,
    val triggerTime: String, // "13:01"
    var timesRun: Int
)
