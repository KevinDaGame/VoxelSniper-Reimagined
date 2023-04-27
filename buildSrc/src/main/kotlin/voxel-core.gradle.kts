import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow")
    kotlin("jvm")
}

// Projects should use Maven Central for external dependencies
// This could be the organization's private repository
repositories {
    mavenLocal()
    mavenCentral()
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

    implementation("net.kyori:adventure-api:4.13.1")
    implementation("net.kyori:adventure-text-minimessage:4.13.1")
    implementation("net.kyori:adventure-text-serializer-legacy:4.13.1")

    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.yaml:snakeyaml:1.33")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.5.1")
    testImplementation("org.mockito:mockito-inline:4.5.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

group = "com.github.kevindagame"
version = "8.6.0"
//java.sourceCompatibility = JavaVersion.VERSION_16

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "17"
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    shadowJar {
        minimize()
        relocate("com.google", "com.github.kevindagame.voxelsniper.libs.com.google")
        relocate("net.kyori", "com.github.kevindagame.voxelsniper.libs.net.kyori")
        relocate("org.yaml.snakeyaml", "com.github.kevindagame.voxelsniper.libs.org.yaml.snakeyaml")
        relocate("kotlin", "com.github.kevindagame.voxelsniper.libs.kotlin")
    }

    build {
        dependsOn(shadowJar)
    }

    clean {
        delete("../output")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {

            from(components["java"])
        }
    }
}
