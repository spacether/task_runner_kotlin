import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.fixedRateTimer

const val oneMinuteInMilliseconds = 60000L



val publisherTask: TimerTask.() -> Unit = {
    // todo use PT
    val now = LocalDateTime.now()
    print("I ran. hour = ${now.hour} minute=${now.minute} \n")
}

fun main(args: Array<String>) {
    fixedRateTimer(
        name="publishEverySecond",
        period=oneMinuteInMilliseconds,
        action=publisherTask
    )
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}