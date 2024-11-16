plugins {
    `honey-java`
    `honey-publish`
    `honey-unit-test`
    `honey-repositories`
}

dependencies {
    api(libs.futures)
    compileOnly(libs.guava)
    compileOnly(libs.bundles.adventure)
}

honeyPublish {
    artifactId = "honey"
}