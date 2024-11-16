plugins {
    `honey-java`
    `honey-repositories`
    id("com.gradleup.shadow") version "8.3.5"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
}

dependencies {
    implementation(project(":honey-common"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

bukkit {
    main = "dev.shiza.honey.ExamplePlugin"
    name = "honey-test-plugin"
    version = "2.0.0-SNAPSHOT"
    apiVersion = "1.21.3"
    authors = listOf("rchomczyk")
}

tasks {
    assemble {
        dependsOn("shadowJar")
    }

    runServer {
        minecraftVersion("1.21.3")
    }
}