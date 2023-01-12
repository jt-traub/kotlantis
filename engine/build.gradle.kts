import java.io.FileOutputStream
import java.util.Properties

// This is a bit of a hack to allow defining some things in the gradle.settings.kts
// file so they can be used there and then still be able to access them here.  In
// order to do that we need to pass them through an object.
fun getVarDefinition(name: String): String {
  return (gradle as ExtensionAware).extra.get(name) as String
}

val projectName = "${rootProject.name}"
val engineVersion = getVarDefinition("engineVersion")
val gameRuleset = getVarDefinition("gameRuleset")

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    kotlin("jvm")
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.testlogger)
}

dependencies {
    implementation(project(":core"))
    implementation(project(":ruleset-${gameRuleset}"))
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
}

application {
    // Define the main class for the application.
    mainClass.set("lemuria.engine.MainKt")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
    // Stop silly warnings when using mocks
    jvmArgs("-Xshare:off")
}

tasks.register<Jar>("fatJar") {
  description = "Create a fat jar containing all dependencies"
  group = "Build"
  dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources"))
  archiveClassifier.set("standalone")
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE

  from(sourceSets.main.get().output)
  dependsOn(configurations.runtimeClasspath)
  from ({
    configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
  })
  manifest { attributes(mapOf("Main-Class" to application.mainClass)) }

}

// To check details how it works, see https://skife.org/java/unix/2011/06/20/really_executable_jars.html
tasks.register<DefaultTask>("executableJar") {
  description = "Create a self-executable file that runs the generated jar"
  group = "Distribution"
  dependsOn(tasks.named<Jar>("fatJar"))
  inputs.files(tasks.named<Jar>("fatJar"))
  outputs.files("$buildDir/distributions/$projectName")
  doLast {
    val execFile = outputs.files.files.first()
    // From this SO answer: https://stackoverflow.com/a/56243046
    // First we get the major Java version as an integer, e.g. 8, 11, 16. It has special handling for the leading 1
    // of older java versions, e.g. 1.8 = Java 8
    // JV = $(java -version 2>&1 | head -1 | cut -d'"' -f2 | sed '/^1\./s///' | cut -d'.' -f1)
    // Then if that java version is >= 16, we add the --add-opens command
    // X = X=$( [ "$JV" -ge "16" ] && echo "--add-opens java.base/java.lang=ALL-UNNAMED" || echo "")
    // exec java $X -Xmx512m -jar "$0" "$@"
    execFile.appendText("#!/bin/sh\n\nJV=\$(java -version 2>&1 | sed -E -n 's/.* version \"([^.-]*).*\".*/\\1/p')\n\nX=\$( [ \"\$JV\" -ge \"16\" ] && echo \"--add-opens java.base/java.lang=ALL-UNNAMED\" || echo \"\")\n\nexec java \$X -Xmx512m -jar \"\$0\" \"\$@\"\n\n")
    execFile.appendBytes(inputs.files.singleFile.readBytes())
    execFile.setExecutable(true, false)
  }
}

// pass some program info into the kotlin code such as program name and version
tasks.register<DefaultTask>("generateBuildProperties") {
  description = "Create a properties file containing version information"
  doLast {
    val propertiesFile = file("$buildDir/resources/build.properties")
    propertiesFile.parentFile.mkdirs()
    val properties = Properties()
    properties.setProperty("version", "$engineVersion")
    properties.setProperty("progname", "$projectName")
    val out = FileOutputStream(propertiesFile)
    properties.store(out, null)
    // Add them to the main resource sourcesets
    val resourceSourceSet: SourceDirectorySet = sourceSets.getByName("main").resources
    resourceSourceSet.srcDir(propertiesFile.parentFile.path)
  }
}

tasks.named("processResources") {
  dependsOn(tasks.named("generateBuildProperties"))
}

tasks.build {
  dependsOn(tasks.named<DefaultTask>("executableJar"))
}