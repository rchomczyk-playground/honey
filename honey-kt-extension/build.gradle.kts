plugins {
    `honey-kotlin`
    `honey-publish`
    `honey-repositories`
    kotlin("jvm") version "2.0.21"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(project(":honey-common"))
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

honeyPublish {
    artifactId = "honey-kt-extension"
}

kotlin {
    jvmToolchain(17)
}