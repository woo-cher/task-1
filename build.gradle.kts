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

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

project(":usecase") {
    dependencies {
        implementation(project(":application"))
        implementation(project(":repository"))
        implementation(project(":domain"))
    }
}

project(":domain") {
    dependencies {
        implementation(project(":application"))
        implementation(project(":repository")) // temp
    }
}

project(":repository") {
    dependencies {
        implementation(project(":application"))
    }
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")  // Kotest assertions
    testImplementation("io.kotest:kotest-property:5.7.2")         // Kotest property testing
}

kotlin {
    jvmToolchain(23)
}
