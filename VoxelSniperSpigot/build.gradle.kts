plugins {
    id("voxel-implementation")
}


dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    implementation("net.kyori:adventure-platform-bukkit:4.1.1")

    testImplementation("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
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
}
