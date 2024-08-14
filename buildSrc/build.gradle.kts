plugins {
    `kotlin-dsl`
}

group = "dev.shiza"
version = "1.0.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(kotlin("script-runtime"))
}

sourceSets {
    main {
        java.setSrcDirs(emptyList<String>())
        groovy.setSrcDirs(emptyList<String>())
        resources.setSrcDirs(emptyList<String>())
    }
    test {
        java.setSrcDirs(emptyList<String>())
        kotlin.setSrcDirs(emptyList<String>())
        groovy.setSrcDirs(emptyList<String>())
        resources.setSrcDirs(emptyList<String>())
    }
}