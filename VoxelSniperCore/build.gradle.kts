import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("voxel-core")
    kotlin("jvm")
}
description = "VoxelSniperCore"
dependencies {
    implementation(kotlin("stdlib-jdk8"))
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