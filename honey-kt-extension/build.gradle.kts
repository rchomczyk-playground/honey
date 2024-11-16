plugins {
    `honey-kotlin`
    `honey-publish`
    `honey-repositories`
    kotlin("jvm") version "2.0.21"
}

honeyPublish {
    artifactId = "honey-kt-extension"
}

kotlin {
    jvmToolchain(17)
}