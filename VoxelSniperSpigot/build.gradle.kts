import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("voxel-implementation")
    id("xyz.jpenilla.run-paper") version "1.0.6"
    kotlin("jvm") version "1.7.22"
}


dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    implementation("net.kyori:adventure-platform-bukkit:4.1.1")

    testImplementation("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib-jdk8"))
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

    }
    runServer {
        minecraftVersion("1.16.5")
    }
}

//tasks.register<xyz.jpenilla.runpaper.task.RunServerTask>("run16") {
//    dependsOn(tasks.shadowJar)
////    pluginJars(tasks.shadowJar.getArchiveFile())
//    minecraftVersion("1.16.5")
//}
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
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "16"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "16"
}