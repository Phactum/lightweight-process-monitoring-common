import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    id("maven-publish")
    kotlin("jvm") version "1.9.23"
    `java-library`
}

group = "com.phactum"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

publishing {



    repositories {
        maven {
            name = "GitHubPackages"
            url = URI("https://maven.pkg.github.com/Phactum/lightweight-process-monitoring-common")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        register("jar", MavenPublication::class) {
            from(components["java"])
            pom {
                url.set("https://github.com/Phactum/lightweight-process-monitoring-common.git")
            }
        }
    }

}