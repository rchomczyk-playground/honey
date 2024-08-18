plugins {
    `honey-java`
    `honey-publish`
    `honey-unit-test`
    `honey-repositories`
}

dependencies {
    api(libs.futures)
    compileOnly(libs.bundles.adventure)
}

honeyPublish {
    artifactId = "honey"
}