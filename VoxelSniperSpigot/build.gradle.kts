import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("voxel-implementation")
    id("xyz.jpenilla.run-paper") version "1.0.6"
    kotlin("jvm")
}

repositories {
    maven { url = uri("https://maven.enginehub.org/repo/") }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.5") // newest worldguard that supports 1.16.5

    implementation("org.bstats:bstats-bukkit:3.0.0")

    implementation("net.kyori:adventure-platform-bukkit:4.2.0")
    implementation(kotlin("stdlib-jdk8"))

    shadow("org.bstats:bstats-bukkit:3.0.0")

    testImplementation("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
}

description = "VoxelSniperSpigot"
val platform by extra("spigot")

tasks {
    processResources {
        // replace stuff in plugin.yml, nothing else
        filesMatching("plugin.yml") {
            // replace version
            expand("projectName" to rootProject.name, "version" to version)
        }
    }
    shadowJar {
        dependencies {
//            include(dependency("org.bstats:bstats-bukkit"))
        }
        relocate("org.bstats", "com.github.kevindagame.voxelsniper.libs.org.bstats")
    }
    runServer {
        minecraftVersion("1.16.5")
    }
}
tasks.register<xyz.jpenilla.runpaper.task.RunServerTask>("run17") {
    dependsOn(tasks.shadowJar)
    pluginJars(File("../output/voxelsniper-${version}-${platform}.jar"))
    minecraftVersion("1.17.1")
}
tasks.register<xyz.jpenilla.runpaper.task.RunServerTask>("run18") {
    dependsOn(tasks.shadowJar)
    pluginJars(File("../output/voxelsniper-${version}-${platform}.jar"))
    minecraftVersion("1.18.2")
}
tasks.register<xyz.jpenilla.runpaper.task.RunServerTask>("run19") {
    dependsOn(tasks.shadowJar)
    pluginJars(File("../output/voxelsniper-${version}-${platform}.jar"))
    minecraftVersion("1.19.3")
}