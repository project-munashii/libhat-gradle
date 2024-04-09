plugins {
    `cpp-library`
    `maven-publish`
}

group = "com.github.ZeroMemes"
version = "1.0.0-SNAPSHOT"

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
            url = uri("https://maven.pkg.github.com/GITHUB_USERNAME/GITHUB_REPO")
            credentials {
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
    dependsOn("createRelease", "publish")
}