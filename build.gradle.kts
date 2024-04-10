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

println("Username: ${System.getenv("GITHUB_USERNAME")}")
println("Password: ${System.getenv("GITHUB_TOKEN")}")

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/project-munashii/libhat-gradle")
            credentials {
                println("Username: ${System.getenv("GITHUB_USERNAME")}")
                println("Password: ${System.getenv("GITHUB_TOKEN")}")
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register("libhatGradle", MavenPublication::class) {
            artifact(file("build/lib/main/release/libhat-gradle.lib"))

        }
    }
}

tasks.register("uploadRelease") {
    dependsOn("createRelease")
    mustRunAfter("createRelease")
    dependsOn("publish")
}

tasks.named("publishLibhatGradlePublicationToGitHubPackagesRepository") {
    dependsOn("createRelease")
    mustRunAfter("createRelease")
}