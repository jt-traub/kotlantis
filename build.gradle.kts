plugins {
    base
    kotlin("jvm") version "1.7.21" apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.testlogger) apply false
}

subprojects {
    repositories {
        mavenCentral()
    }
}

tasks.register<Copy>("dist") {
    println("Creating distribution directory")
    from(
        project(":engine").fileTree("build/distributions").include("lemuria"),
        rootProject.fileTree("distribution/rulesets"),
        rootProject.fileTree("distribution/windows")
    )
    into("build/distribution")    
}

tasks.named("build") { finalizedBy("dist") }