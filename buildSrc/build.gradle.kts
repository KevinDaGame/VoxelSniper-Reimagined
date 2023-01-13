plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
    maven {
        name = "sponge"
        url = uri("https://repo.spongepowered.org/maven")
    }
}

dependencies {
    implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.7.22")
    implementation("org.spongepowered:mixingradle:0.7.+")
    implementation("com.google.guava:guava:31.1-jre")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}
