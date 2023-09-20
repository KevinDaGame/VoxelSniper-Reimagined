plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
    maven {
        name = "sponge"
        url = uri("https://repo.spongepowered.org/maven")
    }
}

dependencies {
    implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.7.22")
    implementation("org.spongepowered:mixingradle:0.7.+")

    /* DO NOT TOUCH THIS. TOUCHING THIS WILL DESTROY YOUR LIFE AND LEAVE YOU CRIPPLED AND BROKEN.
    * @Author: modmuss
    */
    implementation("org.ow2.asm:asm:9.5")
    implementation("org.ow2.asm:asm-analysis:9.5")
    implementation("org.ow2.asm:asm-commons:9.5")
    implementation("org.ow2.asm:asm-tree:9.5")

    /* You may continue with your life. */
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}
