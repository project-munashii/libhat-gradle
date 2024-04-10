import org.gradle.api.credentials.PasswordCredentials

plugins {
    `cpp-library`
    `maven-publish`
}

group = "com.github.ZeroMemes"
version = "1.0.0"

repositories {

}

dependencies {

}

tasks.withType(CppCompile::class.java).configureEach {
    compilerArgs.addAll(toolChain.map { toolChain ->
        when (toolChain) {
            is VisualCpp -> listOf("/std:c++20")
            else -> listOf("-std=++20")
        }
    })
}

library {
    source.from("src/")
    privateHeaders.from("src/")
    publicHeaders.from("include/")
    linkage = listOf(Linkage.STATIC)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/project-munashii/libhat-gradle")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        //register("libhatGradle", MavenPublication::class) {
        //    artifact(file("build/lib/main/release/libhat-gradle.lib"))
//
        //}
    }
}

tasks.register("uploadRelease") {
    dependsOn("createRelease")
    mustRunAfter("createRelease")
}

/*
tasks.named("publishLibhatGradlePublicationToGitHubPackagesRepository") {
    dependsOn("createRelease")
    mustRunAfter("createRelease")
}
*/

tasks.named("publish").configure {
    onlyIf {
        !project.version.toString().endsWith("-SNAPSHOT").also {
            if (it) {
                println("Not publishing snapshot version")
            } else {
                println("Publishing release version")
            }
        }
    }
}