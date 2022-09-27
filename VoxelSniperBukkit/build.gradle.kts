plugins {
    id("voxel-common")
}


dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    implementation("net.kyori:adventure-platform-bukkit:4.1.1")
    implementation(project(":VoxelSniperCore"))

    testImplementation("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
}

description = "VoxelSniperBukkit"
