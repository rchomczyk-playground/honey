plugins {
    `honey-java`
    `honey-publish`
    `honey-repositories`
}

dependencies {
    api(libs.opel)
    api(libs.lang)
    api(libs.futures)
    api(libs.parboiled)
    api(libs.bundles.adventure)
}

honeyPublish {
    artifactId = "honey"
}