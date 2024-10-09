plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.10"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    // Kotlin standard library
    implementation(kotlin("stdlib"))

    // Ktor core and Netty server
    implementation("io.ktor:ktor-server-core:2.3.3")
    implementation("io.ktor:ktor-server-netty:2.3.3")

    // Ktor JSON support (serialization)
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.3")
    implementation("io.ktor:ktor-serialization-jackson:2.3.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.3")

    // Ktor Status Pages
    implementation("io.ktor:ktor-server-status-pages:2.3.3")

    // Ktor Content Negotiation
    implementation("io.ktor:ktor-server-content-negotiation:2.3.3")

    // Dependency Injection
    implementation("com.google.inject:guice:5.1.0")

    // Logging (Optional, אבל מומלץ)
    implementation("ch.qos.logback:logback-classic:1.2.10")

    // Exposed SQL (for database interaction)
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.41.1")

    // MySQL JDBC driver
    implementation("mysql:mysql-connector-java:8.0.26")

    // Test dependencies
    testImplementation("io.ktor:ktor-server-tests:2.3.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.10")
}





