plugins {
    id("voxel-implementation")
    id("xyz.jpenilla.run-paper") version "1.0.6"
}


dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    shadow("net.kyori:adventure-platform-bukkit:4.1.1")

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
    minecraftVersion("1.19.2")
}