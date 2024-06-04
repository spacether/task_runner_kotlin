val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.21"
    id("io.ktor.plugin") version "2.3.11"
}

application {
    mainClass.set("io.taskrunner.RestApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor", "ktor-server-thymeleaf-jvm", "2.3.5")
    implementation("ch.qos.logback:logback-classic:$logback_version")
}
