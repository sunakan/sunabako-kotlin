import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    application
    // --------------------------------------------------------------------------
    // ktlint
    // https://plugins.gradle.org/plugin/org.jlleitschuh.gradle.ktlint
    // Linter/Formatter
    // $ ./gradlew ktlintFormat
    // --------------------------------------------------------------------------
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

application {
    mainClass.set("demo.DemoApplicationKt")
}

tasks.withType<Test> {
    this.testLogging {
        // Test時に標準出力を出力させる
        this.showStandardStreams = true
    }
}