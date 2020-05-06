import io.kotless.plugin.gradle.dsl.kotless

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.3.72"
    id("io.kotless") version "0.1.3" apply true
}

repositories {
    maven(
        url = "https://s3-ap-northeast-1.amazonaws.com/dynamodb-local-tokyo/release"
    )
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
    implementation("io.kotless", "ktor-lang", "0.1.3")
    implementation("io.github.config4k", "config4k", "0.4.1")
    implementation("com.amazonaws", "aws-java-sdk-dynamodb", "1.11.774")
    testImplementation("org.jetbrains.kotlin", "kotlin-test", "1.3.72")
    testImplementation("org.spekframework.spek2", "spek-dsl-jvm", "2.0.10")
    testImplementation("com.amazonaws", "DynamoDBLocal", "1.12.0")
    testImplementation("com.almworks.sqlite4java", "sqlite4java", "1.0.392")
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
