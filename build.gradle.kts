import io.kotless.plugin.gradle.dsl.kotless

plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.72"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("io.kotless") version "0.1.3" apply true
    application
}

group = "io.kotless.examples"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
    implementation("io.kotless", "ktor-lang", "0.1.3")
    implementation("io.github.config4k", "config4k", "0.4.1")
    testImplementation("org.jetbrains.kotlin", "kotlin-test", "1.3.72")
    testImplementation("org.spekframework.spek2", "spek-dsl-jvm", "2.0.10")
    testRuntimeOnly("org.spekframework.spek2", "spek-runner-junit5", "2.0.10")
    testRuntimeOnly("org.jetbrains.kotlin", "kotlin-reflect", "1.3.72")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

kotless {
    config {
        bucket = project.property("KOTLESS_BUCKET_NAME").toString()
        prefix = "dev"

        // terraformの設定
        terraform {
            profile = "kotless"
            region = "ap-northeast-1"
        }
    }

    webapp {
        lambda {
            memoryMb = 1024
            timeoutSec = 120
        }
    }

    extensions {
        terraform {
            allowDestroy = true
        }
    }
}

tasks.test {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}
// project.gradle.startParameter.excludedTaskNames.add("deploy")
