plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.amazonaws", "aws-lambda-java-core", "1.2.1")
    implementation("com.amazonaws", "aws-lambda-java-events", "2.2.8")
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

tasks.test {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}
