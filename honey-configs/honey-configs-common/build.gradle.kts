plugins {
    `honey-java`
    `honey-publish`
    `honey-repositories`
}

dependencies {
    compileOnly(project(":honey-common"))
    compileOnly(libs.bundles.adventure)
}

honeyPublish {
    artifactId = "honey-configs-common"
}