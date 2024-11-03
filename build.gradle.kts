plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.3"
}

group = "xyz.acrylicstyle"
version = "1.0-SNAPSHOT"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.azisaba.net/repository/maven-public/")
}

dependencies {
    implementation("org.eclipse.jgit:org.eclipse.jgit:7.0.0.202409031743-r")
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        relocate("org.eclipse.jgit", "xyz.acrylicstyle.gitupdater.libs.jgit")
    }
}
