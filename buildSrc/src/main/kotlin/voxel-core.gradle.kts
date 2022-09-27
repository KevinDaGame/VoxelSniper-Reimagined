plugins {
    `java-library`
    id("com.github.johnrengelman.shadow")
}

// Projects should use Maven Central for external dependencies
// This could be the organization's private repository
repositories {
    mavenLocal()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations-java5:23.0.0")

    implementation("net.kyori:adventure-api:4.11.0")
    implementation("net.kyori:adventure-text-minimessage:4.11.0")

    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.yaml:snakeyaml:1.31")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.5.1")
    testImplementation("org.mockito:mockito-inline:4.5.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
    }
}

group = "com.github.kevindagame"
version = "8.1.3"
//java.sourceCompatibility = JavaVersion.VERSION_16

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(16)
    }

    shadowJar {
        minimize()
        relocate("com.google.common", "com.github.kevindagame.voxelsniper.libs.com.google.common")
        relocate("net.kyori", "com.github.kevindagame.voxelsniper.libs.net.kyori")
        relocate("org.yaml.snakeyaml", "com.github.kevindagame.voxelsniper.libs.org.yaml.snakeyaml")
    }

    build {
        dependsOn(shadowJar)
    }

    clean {
        delete("../output")
    }
}