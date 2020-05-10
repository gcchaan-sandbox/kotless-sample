plugins {
    kotlin("jvm") version Versions.kotlin
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1" apply false
}

allprojects {
    repositories {
        jcenter()
    }

    group = "com.gcchaan"
    version = "1.0-SNAPSHOT"
}

subprojects {
    apply { plugin("org.jlleitschuh.gradle.ktlint") }
}
