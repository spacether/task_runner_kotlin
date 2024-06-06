package io.tasks

import kotlinx.coroutines.Runnable

class TaskA: Runnable {
    private val fiveSecondsInMilliSeconds = 5000L

    override fun run() {
        Thread.sleep(fiveSecondsInMilliSeconds)
    }
}