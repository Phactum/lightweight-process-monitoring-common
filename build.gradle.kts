import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("jvm") version "1.9.23"
    `java-library`
    id("org.jetbrains.dokka") version "1.9.0"
    id("eu.kakde.gradle.sonatype-maven-central-publisher") version "1.0.6" // for sonatype central publishing
    signing
}

group = "com.phactum.lpm"
version = "0.9.3-SNAPSHOT"

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

// Use Dokka to generate KDoc-compatible Javadocs (optional, for Kotlin projects)
tasks.register("javadocJar", Jar::class) {
    dependsOn("dokkaHtml") // Generates Javadoc using Dokka
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("dokka"))
}

tasks.register("sourcesJar", Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

artifacts {
    archives(tasks.named("sourcesJar"))
    archives(tasks.named("javadocJar"))
}

val pomAction = object : Action<MavenPom> {
    override fun execute(p0: MavenPom) {
        p0.name.set(project.name)
        p0.description.set("Support artifact for the lightweight library for process monitoring.")
        p0.url.set("https://github.com/Phactum/lightweight-process-monitoring-common.git")
        p0.licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        p0.developers {
            developer {
                id.set("phactum-developer")
                name.set("Phactum Developers")
                organization.set("Phactum Softwareentwicklung GmbH")
                organizationUrl.set("https://www.phactum.at/")
            }
        }
        p0.scm {
            connection.set("scm:git:git://github.com/Phactum/lightweight-process-monitoring-common.git")
            developerConnection.set("scm:git:ssh://git@github.com:Phactum/lightweight-process-monitoring-common.git")
            url.set("https://github.com/Phactum/lightweight-process-monitoring-common")
        }
        p0.issueManagement {
            system.set("GitHub")
            url.set("https://github.com/Phactum/lightweight-process-monitoring-common/issues")
        }
    }
}

// ----------------------
// PUBLISHING TO SONATYPE
// ----------------------
// eu.kakde.gradle.sonatype-maven-central-publisher
sonatypeCentralPublishExtension {
    signing {
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
    // Set group ID, artifact ID, version, and other publication details
    groupId.set(project.group.toString())
    artifactId.set(project.name)
    version.set(project.version.toString())
    componentType.set("java") // "java" or "versionCatalog"
    publishingType.set("USER_MANAGED") // USER_MANAGED or AUTOMATIC

    // Set username and password for Sonatype repository
    username.set(System.getenv("SONATYPE_USERNAME") ?: project.properties["sonatypeUsername"].toString())
    password.set(System.getenv("SONATYPE_PASSWORD") ?: project.properties["sonatypePassword"].toString())

    pom(pomAction)
}

publishing {
    repositories {
        mavenCentral {
            name = "SonatypeCentral"
            url = URI("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("SONATYPE_USERNAME") ?: project.properties["sonatypeUsername"].toString()
                password = System.getenv("SONATYPE_PASSWORD") ?: project.properties["sonatypePassword"].toString()
            }
        }
    }

    publications {
        register("jar", MavenPublication::class) {
            from(components["java"])
            //artifact(tasks.named("sourcesJar"))
            //artifact(tasks.named("javadocJar"))
            pom(pomAction)
        }
    }

}
