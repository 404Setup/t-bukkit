import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-library`
    idea
    signing

    id("com.vanniktech.maven.publish") version "0.31.0"
}

group = "one.tranic"
version = "1.0.3"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://repo.viaversion.com")
    maven("https://repo.opencollab.dev/main/")
}

dependencies {
    compileOnly("org.jetbrains:annotations:26.0.2")
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")

    compileOnly("org.geysermc.geyser:api:2.4.2-SNAPSHOT")
    compileOnly("org.geysermc.floodgate:api:2.2.3-SNAPSHOT")

    compileOnly("com.viaversion:viaversion-api:5.2.2-SNAPSHOT")

    implementation("one.tranic:t-base:1.2.8")
    implementation("one.tranic:t-utils:1.2.3")
    implementation("net.kyori:adventure-api:4.21.0")
    implementation("net.kyori:adventure-text-minimessage:4.21.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
}

val targetJavaVersion = 17

java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
}

tasks.withType<JavaCompile> {
    options.encoding = Charsets.UTF_8.name()
    options.release = targetJavaVersion
}

tasks.withType<ProcessResources> {
    filteringCharset = Charsets.UTF_8.name()
}

val apiAndDocs: Configuration by configurations.creating {
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named(DocsType.SOURCES))
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
    }
}

configurations.api {
    extendsFrom(apiAndDocs)
}

mavenPublishing {
    coordinates(group as String, "t-bukkit", version as String)

    pom {
        name.set("TLIB Bukkit")
        description.set("Basic Development Library")
        inceptionYear.set("2025")
        url.set("https://github.com/404Setup/t-bukkit")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("404")
                name.set("404Setup")
                url.set("https://github.com/404Setup")
            }
        }
        scm {
            url.set("https://github.com/404Setup/t-bukkit")
            connection.set("scm:git:git://github.com/404Setup/t-bukkit.git")
            developerConnection.set("scm:git:ssh://git@github.com/404Setup/t-bukkit.git")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}
