import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("org.jlleitschuh.gradle.ktlint") version "11.6.0"
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
    kotlin("jvm") version "1.9.10"
    id("nu.studer.jooq") version "9.0"
}

val http4kVersion: String by project
val http4kConnectVersion: String by project
val junitVersion: String by project
val kotlinVersion: String by project
val kotestVersion: String by project
val slf4jVersion: String by project
val javajwtVersion: String by project
val exposedVersion: String by project
val ktlintVersion: String by project
val dotenvVersion: String by project
val mysqlConnVersion: String by project
val mariaClient: String by project

ktlint {
    version = ktlintVersion
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
    }

    configurations["classpath"].resolutionStrategy.eachDependency {
        if (requested.group.startsWith("org.jooq") && requested.name.startsWith("jooq")) {
            useVersion("3.17.3")
        }
    }
}

application {
    mainClass = "ru.uniyar.DnDGamesKt"
}

repositories {
    mavenCentral()
}

apply(plugin = "kotlin")

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            allWarningsAsErrors = false
            jvmTarget = "17"
            freeCompilerArgs += "-Xjvm-default=all"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = VERSION_17
        targetCompatibility = VERSION_17
    }
}

jooq {
    version.set("3.19.1")
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
}

dependencies {
    jooqGenerator("com.mysql:mysql-connector-j:$mysqlConnVersion")
    jooqGenerator("org.mariadb.jdbc:mariadb-java-client:$mariaClient")
    implementation("org.http4k:http4k-client-okhttp:$http4kVersion")
    implementation("org.http4k:http4k-core:$http4kVersion")
    implementation("org.http4k:http4k-format-jackson:$http4kVersion")
    implementation("org.http4k:http4k-multipart:$http4kVersion")
    implementation("org.http4k:http4k-server-netty:$http4kVersion")
    implementation("org.http4k:http4k-template-pebble:$http4kVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
    implementation("org.http4k:http4k-cloudnative:$http4kVersion")
    implementation("com.auth0:java-jwt:$javajwtVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("io.github.cdimascio:dotenv-kotlin:$dotenvVersion")
    implementation("com.mysql:mysql-connector-j:$mysqlConnVersion")
    implementation("org.mariadb.jdbc:mariadb-java-client:$mariaClient")
    testImplementation("org.http4k:http4k-testing-approval:$http4kVersion")
    testImplementation("org.http4k:http4k-testing-hamkrest:$http4kVersion")
    testImplementation("org.http4k:http4k-testing-kotest:$http4kVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}
