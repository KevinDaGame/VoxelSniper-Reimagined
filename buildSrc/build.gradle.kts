plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
}

dependencies {
     implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
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
