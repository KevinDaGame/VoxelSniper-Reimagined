plugins {
    id("voxel-implementation")
    id("xyz.jpenilla.run-paper")
}

repositories {
    maven { url = uri("https://maven.enginehub.org/repo/") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.8")
    compileOnly("com.plotsquared:PlotSquared-Core")

    shadow(platform("com.intellectualsites.bom:bom-1.18.x:1.20"))
    shadow("org.bstats:bstats-bukkit:3.0.0")
    shadow("net.kyori:adventure-platform-bukkit:4.2.0")
    implementation(kotlin("stdlib-jdk8"))

    shadow("org.bstats:bstats-bukkit:3.0.0")

    testImplementation("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
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
        minecraftVersion("1.19.4")
    }
}

tasks.register<xyz.jpenilla.runpaper.task.RunServerTask>("run17") {
    dependsOn(tasks.shadowJar)
    pluginJars(File("../output/voxelsniper-${version}-${platform}.jar"))
    minecraftVersion("1.17.1")
    group = "run paper"
}
tasks.register<xyz.jpenilla.runpaper.task.RunServerTask>("run18") {
    dependsOn(tasks.shadowJar)
    pluginJars(File("../output/voxelsniper-${version}-${platform}.jar"))
    minecraftVersion("1.18.2")
    group = "run paper"
}
tasks.register<xyz.jpenilla.runpaper.task.RunServerTask>("run19") {
    dependsOn(tasks.shadowJar)
    pluginJars(File("../output/voxelsniper-${version}-${platform}.jar"))
    minecraftVersion("1.19.4")
    group = "run paper"

}
tasks.register<xyz.jpenilla.runpaper.task.RunServerTask>("run20") {
    dependsOn(tasks.shadowJar)
    pluginJars(File("../output/voxelsniper-${version}-${platform}.jar"))
    minecraftVersion("1.20.1")
    group = "run paper"
}