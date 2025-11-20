plugins {
    kotlin("jvm") version "2.2.10"
    // Plugin for Dokka - KDoc generating tool
    id("org.jetbrains.dokka") version "1.9.20"
    jacoco
    // Plugin for Ktlint
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    application
}

group = "ie.setu"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // For generating a Dokka Site from KDoc
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20")
}

tasks.test {
    useJUnitPlatform()
    // report is always generated after tests run
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    // for building a fat jar - include all dependencies
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}

kotlin {
    jvmToolchain(21)
}
