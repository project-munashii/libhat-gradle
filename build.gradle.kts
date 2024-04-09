plugins {
    `cpp-library`
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
    publicHeaders.from("include/")
}