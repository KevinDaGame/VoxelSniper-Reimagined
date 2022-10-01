plugins {
    id("voxel-core")
}

dependencies {
    implementation(project(":VoxelSniperCore"))
}


val platform by extra("PLATFORM")

tasks {
    shadowJar {
        destinationDirectory.set(File("../output"))
        archiveFileName.set("voxelsniper-${archiveVersion.get()}-${platform}.jar")
    }
    clean {
        delete("../output")
    }
}