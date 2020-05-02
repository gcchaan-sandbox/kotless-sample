import io.kotless.plugin.gradle.dsl.kotless

plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.70"
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
// project.gradle.startParameter.excludedTaskNames.add("deploy")
