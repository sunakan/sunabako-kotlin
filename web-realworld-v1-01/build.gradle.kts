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

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //
    // Spring JDBC
    // URL
    // - https://spring.pleiades.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/package-summary.html
    // MavenCentral
    // - https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jdbc
    // Main用途
    // - DBへ保存
    // Sub用途
    // - 無し
    //
    // これを入れるだけで、application.properties/yamlや@ConfigurationによるDB接続設定が必要になる
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    //
    // postgresql
    // URL
    // - https://jdbc.postgresql.org/
    // MavenCentral
    // - https://mvnrepository.com/artifact/org.postgresql/postgresql
    // Main用途
    // - DBつなぐ時のドライバ
    // Sub用途
    // -
    //
    implementation("org.postgresql:postgresql")

    //
    // Arrow Core
    //
    // URL
    // - https://arrow-kt.io/docs/core/
    // MavenCentral
    // - https://mvnrepository.com/artifact/io.arrow-kt/arrow-core
    // Main用途
    // - Eitherを使ったRail Oriented Programming
    // Sub用途
    // - Optionを使ったletの代替
    //
    implementation("io.arrow-kt:arrow-core:1.1.2")

    //
    // jqwik
    //
    // URL
    // - https://jqwik.net/
    // MavenCentral
    // - https://mvnrepository.com/artifact/net.jqwik/jqwik
    // - https://mvnrepository.com/artifact/net.jqwik/jqwik-kotlin
    // Main用途
    // - Property Based Testing(pbt)
    // 概要
    // - https://medium.com/criteo-engineering/introduction-to-property-based-testing-f5236229d237
    // - https://johanneslink.net/property-based-testing-in-kotlin/#jqwiks-kotlin-support
    //
    testImplementation("net.jqwik:jqwik:1.6.5")
    testImplementation("net.jqwik:jqwik-kotlin:1.6.5")

    //
    // AssertJ
    //
    // 概要
    // - JUnitでListの中身とか見るときに便利
    // MavenCentral
    // - https://mvnrepository.com/artifact/org.assertj/assertj-core
    // Main用途
    // - JUnitでassertThat(xxx).isEqualTo(yyy)みたいな感じで比較時に使う
    //
    testImplementation("org.assertj:assertj-core:3.23.1")

    //
    // Main用途
    // - ホットリロードで利用
    //
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}
//
// ノート
//
// spring-boot-starter-* 一覧
// https://spring.pleiades.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters
//

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xjsr305=strict", // 後述
            "-Xemit-jvm-type-annotations" // 型アノテーションの有効化
        )
        jvmTarget = "17"
    }
}
//
// ノート
//
// -Xjsr305コンパイラフラグ
// https://qiita.com/rubytomato@github/items/daa723db5deffc908df7#-xjsr305-compiler-flag
// > strictモードでは、Java側のオブジェクトのフィールドにNullableアノテーションが付いている場合、Kotlin側でNullableとして扱わないとコンパイルエラーになります。
//

tasks.withType<Test> {
    useJUnitPlatform()
    this.testLogging {
        // Test時に標準出力を出力させる
        this.showStandardStreams = true
    }
}
