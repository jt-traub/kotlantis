plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    `java-library`
    kotlin("jvm")
    kotlin("plugin.serialization")
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.testlogger)
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(libs.kotlinx.serialization)
    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
    // Stop silly warnings when using mocks
    jvmArgs("-Xshare:off")
}
