plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.3.8"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

group = "dev.omega24.blockedit"
version = "0.1.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    paperDevBundle("1.19.1-R0.1-SNAPSHOT")
    implementation("cloud.commandframework:cloud-paper:1.7.0")
}

tasks {
    shadowJar {
        listOf(
            "cloud.commandframework",
            "io.leangen.geantyref"
        ).forEach { relocate(it, "${project.group}.lib.$it") }
    }

    runServer {
        minecraftVersion("1.19.1")
    }

    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("plugin.yml") {
            expand(
                "name" to project.name,
                "group" to project.group,
                "version" to project.version
            )
        }
    }
}
