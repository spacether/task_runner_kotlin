package io.tasks

import kotlinx.coroutines.Runnable

class TaskC: Runnable {
    override fun run() {
        throw RuntimeException("some error in the task")
    }
}