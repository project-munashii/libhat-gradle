# libhat-gradle
Libhat Gradle is a fork of libhat mirroring the original libhat repository, but with the Gradle build system instead of CMake.

## Prebuilt releases

Prebuilt releases are available on the GitHub Packages registry.

```kts
repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/project-munashii/libhat-gradle")
        credentials {
            // Set this as your GitHub username.
            username = "username"
            // Create a Personal Access Token that can read packages and set it here.
            password = "ghp_..."
            // Generally, you want these to be set as environment variables in your projects.
        }
    }
}

dependencies {
    // Get the version from the Packages page for the `libhat-gradle` package.
    // Avoid -SNAPSHOT versions.
    implementation("com.github.ZeroMemes:libhat-gradle:VERSION")
}
```

## libhat
Libhat is a modern, high-performance library for C++20 designed around game hacking, made by Brady Hahn under Based Inc.

For more info, [see the original repository](https://github.com/BasedInc/libhat). **This repository contains 0 code or functionality changes.**
