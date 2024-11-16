plugins {
    `honey-java`
    `honey-publish`
    `honey-unit-test`
    `honey-repositories`
}

dependencies {
    api(libs.futures)
    api(libs.guava)
    api(libs.bundles.adventure)
}

honeyPublish {
    artifactId = "honey"
}