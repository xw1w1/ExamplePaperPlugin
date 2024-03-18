plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

tasks.processResources {
    filteringCharset = "UTF-8"
    val properties = inputs.properties.map {
        it.key to it.value
    }.toMap(hashMapOf()).apply { this["version"] = version }
    filesMatching("plugin.yml") { expand(properties) }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}