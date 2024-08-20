plugins {
    `java-library`
    `maven-publish`
}

group = "dev.shiza"
version = "1.0.4-SNAPSHOT"

java {
    withSourcesJar()
}

publishing {
    repositories {
        mavenLocal()
        maven(
            name = "shiza",
            url = "https://repo.shiza.dev",
            username = "MAVEN_USERNAME",
            password = "MAVEN_PASSWORD"
        )
    }
}

fun RepositoryHandler.maven(
    name: String,
    url: String,
    username: String,
    password: String,
    snapshots: Boolean = true
) {
    val isSnapshot = version.toString().endsWith("-SNAPSHOT")
    if (isSnapshot && !snapshots) {
        return
    }

    this.maven {
        this.name =
            if (isSnapshot) "${name}Snapshots" else "${name}Releases"
        this.url =
            if (isSnapshot) uri("$url/snapshots") else uri("$url/releases")
        this.credentials {
            this.username = System.getenv(username)
            this.password = System.getenv(password)
        }
    }
}

interface HoneyPublishExtension {
    var artifactId: String
}

val extension = extensions.create<HoneyPublishExtension>("honeyPublish")

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                artifactId = extension.artifactId
                from(project.components["java"])
            }
        }
    }
}