plugins {
    kotlin("jvm") version "2.1.0"
}

allprojects {
    group = "org.study"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        implementation(project(":application"))
    }
}

project(":usecase") {
    dependencies {
        implementation(project(":repository"))
        implementation(project(":domain"))
    }
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")  // Kotest assertions
    testImplementation("io.kotest:kotest-property:5.7.2")         // Kotest property testing
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(23)
}
