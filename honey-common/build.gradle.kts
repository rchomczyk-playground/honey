plugins {
    `honey-java`
    `honey-publish`
    `honey-repositories`
}

dependencies {
    api(libs.futures)
    api(libs.lang)
    api(libs.opel)
    api(libs.parboiled)
    compileOnly(libs.bundles.adventure)
}

honeyPublish {
    artifactId = "honey"
}