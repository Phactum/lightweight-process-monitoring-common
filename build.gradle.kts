import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("jvm") version "1.9.23"
    `java-library`
    id("org.jetbrains.dokka") version "1.9.0"
    id("eu.kakde.gradle.sonatype-maven-central-publisher") version "1.0.6" // for sonatype central publishing
    `maven-publish` // for github-packages
    signing
}

group = "com.phactum.lpm"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    // withSourcesJar()
    // withJavadocJar()
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
    //from(tasks.named("dokkaHtml").get().outputDirectory)
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

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(configurations.archives.get())
}


/*
Repository URL for snapshot deployment and download access:

https://s01.oss.sonatype.org/content/repositories/snapshots/
Repository URL for release deployment, no download access! :

https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/
*/

// github packages
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
            artifact(tasks.named("sourcesJar"))
            artifact(tasks.named("javadocJar"))
            pom {
                name.set("Lightweight Process Monitoring Common")
                description.set("A lightweight library for process monitoring.")
                url.set("https://github.com/Phactum/lightweight-process-monitoring-common.git")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("phactum")
                        name.set("Phactum Developers")
                        email.set("developer@phactum.at")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/Phactum/lightweight-process-monitoring-common.git")
                    developerConnection.set("scm:git:ssh://git@github.com:Phactum/lightweight-process-monitoring-common.git")
                    url.set("https://github.com/Phactum/lightweight-process-monitoring-common.git")
                }
            }
        }
    }

}
/*
uploadArchives {
    repositories {
        mavenDeployer {
            beforeDeployment { MavenDeployment deployment -> signing.signPom(deployment) }

            repository(url: "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
            authentication(userName: ossrhUsername, password: ossrhPassword)
        }

            snapshotRepository(url: "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
            authentication(userName: ossrhUsername, password: ossrhPassword)
        }

            pom.project {
                name 'Example Application'
                packaging 'jar'
                // optionally artifactId can be defined here
                description 'A application used as an example on how to set up
                pushing  its components to the Central Repository.'
                url 'http://www.example.com/example-application'

                scm {
                    connection 'scm:svn:http://foo.googlecode.com/svn/trunk/'
                    developerConnection 'scm:svn:https://foo.googlecode.com/svn/trunk/'
                    url 'http://foo.googlecode.com/svn/trunk/'
                }

                licenses {
                    license {
                        name 'The Apache License, Version 2.0'
                        url 'http://www.apache.org/licenses/LICENSE-2.0.txt'
                    }
                }

                developers {
                    developer {
                        id 'manfred'
                        name 'Manfred Moser'
                        email 'manfred@sonatype.com'
                    }
                }
            }
        }
    }
}
*/

// ----------------------
// PUBLISHING TO SONATYPE
// ----------------------
// eu.kakde.gradle.sonatype-maven-central-publisher
sonatypeCentralPublishExtension {
    // Set group ID, artifact ID, version, and other publication details
    groupId.set(project.group.toString())
    artifactId.set(project.name)
    version.set(project.version.toString())
    componentType.set("java") // "java" or "versionCatalog"
    publishingType.set("USER_MANAGED") // USER_MANAGED or AUTOMATIC

    // Set username and password for Sonatype repository
    username.set(System.getenv("SONATYPE_USERNAME") ?: project.properties["sonatypeUsername"].toString())
    password.set(System.getenv("SONATYPE_PASSWORD") ?: project.properties["sonatypePassword"].toString())

    // Configure POM metadata
    pom {
        name.set(project.name)
        description.set("Support artifact for the lightweight library for process monitoring.")
        url.set("https://github.com/Phactum/lightweight-process-monitoring-common.git")
        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("phactum-developer")
                name.set("Phactum Developer")
                organization.set("Phactum Softwareentwicklung GmbH")
                organizationUrl.set("https://www.phactum.at/")
            }
        }
        scm {
            url.set("https://github.com/Phactum/lightweight-process-monitoring-common.git")
            connection.set("scm:git:https://github.com/Phactum/lightweight-process-monitoring-common.git")
            developerConnection.set("scm:git:https://github.com/Phactum/lightweight-process-monitoring-common.git")
        }
        issueManagement {
            system.set("GitHub")
            url.set("https://github.com/Phactum/lightweight-process-monitoring-common.git/issues")
        }
    }
}