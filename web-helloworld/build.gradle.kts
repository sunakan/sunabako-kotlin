import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.7"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"

    // --------------------------------------------------------------------------
    // ktlint
    // https://plugins.gradle.org/plugin/org.jlleitschuh.gradle.ktlint
    // Linter/Formatter
    // $ ./gradlew ktlintFormat
    // --------------------------------------------------------------------------
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

//
// Note.01
//
// spring-boot-starter-* 一覧
// https://spring.pleiades.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters
//

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // ホットリロードで利用
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
