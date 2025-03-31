plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "task-1"

include("application", "usecase", "integration-test", "repository", "domain")
