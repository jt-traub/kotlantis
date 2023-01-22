plugins {
    val kotlinVer = libs.versions.kotlin.get()
    base
    kotlin("jvm") version kotlinVer apply false
    kotlin("plugin.serialization") version kotlinVer apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.testlogger) apply false
}

subprojects {
    repositories {
        mavenCentral()
    }
}

tasks.register<Copy>("dist") {
    dependsOn(":engine:build")
    from(
        project(":engine").fileTree("build/distributions").include("lemuria"),
        rootProject.fileTree("distribution/rulesets"),
        rootProject.fileTree("distribution/windows")
    )
    into("build/distribution")
    println("Creating distribution directory")
}

tasks.named("build") { finalizedBy("dist") }