plugins {
    `honey-java`
    `honey-publish`
    `honey-repositories`
}

dependencies {
    compileOnly(project(":honey-common"))
    compileOnly(libs.bundles.adventure)
    api(project(":honey-configs:honey-configs-common"))
    api(libs.bundles.okaeri.configs)
}

honeyPublish {
    artifactId = "honey-configs-okaeri"
}