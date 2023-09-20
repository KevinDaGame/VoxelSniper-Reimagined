buildscript {
    dependencies {
        classpath("org.ow2.asm:asm:9.5")
        classpath("org.ow2.asm:asm-analysis:9.5")
        classpath("org.ow2.asm:asm-commons:9.5")
        classpath("org.ow2.asm:asm-tree:9.5")
    }
}

plugins {
    id("fabric-loom") version "1.3-SNAPSHOT" apply false
    id("net.minecraftforge.gradle") version "[6.0,6.2]" apply false
    id ("org.spongepowered.mixin") apply false
    id("xyz.jpenilla.run-paper") version "1.0.6" apply false
}
