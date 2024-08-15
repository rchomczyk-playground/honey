plugins {
    `honey-java`
    `honey-publish`
    `honey-repositories`
}

dependencies {
    api(libs.futures)
    compileOnly(libs.bundles.adventure)
}

honeyPublish {
    artifactId = "honey"
}