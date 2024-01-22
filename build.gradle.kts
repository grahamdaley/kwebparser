group = "io.github.grahamdaley"
version = "1.0.1"

object Meta {
    const val NAME = "kwebparser"
    const val DESC = "A simple webparser using jsoup, written in Kotlin"
    const val LICENSE = "Apache-2.0"
    const val GITHUB_REPO = "grahamdaley/kwebparser"
    const val RELEASE = "https://s01.oss.sonatype.org/service/local/"
    const val SNAPSHOT = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
}

// ------------------------------------------------------ plugins

// https://youtrack.jetbrains.com/issue/KTIJ-19369#focus=Comments-27-5181027.0-0
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-library`
    `maven-publish`
    signing
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.nexus.publish)
}

// ------------------------------------------------------ repositories

val repositories =
    arrayOf(
        "https://oss.sonatype.org/content/repositories/snapshots/",
        "https://s01.oss.sonatype.org/content/repositories/snapshots/",
    )

repositories {
    mavenLocal()
    mavenCentral()
    repositories.forEach { maven(it) }
}

// ------------------------------------------------------ dependencies

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    api(libs.jsoup)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.jupiter.api)
    testImplementation(libs.jupiter.params)
    testImplementation(libs.jupiter.engine)
}

repositories {
    mavenCentral()
}

java {
    withSourcesJar()
    withJavadocJar()
}

signing {
    val signingKey =
        providers
            .environmentVariable("GPG_SIGNING_KEY")
            .orElse(providers.gradleProperty("gpg.key"))
    val signingPassphrase =
        providers
            .environmentVariable("GPG_SIGNING_PASSPHRASE")
            .orElse(providers.gradleProperty("gpg.passphrase"))

    if (signingKey.isPresent && signingPassphrase.isPresent) {
        useInMemoryPgpKeys(signingKey.get(), signingPassphrase.get())
        val extension = extensions.getByName("publishing") as PublishingExtension
        sign(extension.publications)
    }
}

publishing {
    publications {
        create<MavenPublication>(Meta.NAME) {
            groupId = project.group as String
            artifactId = Meta.NAME
            version = project.version as String
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
            pom {
                name.set(project.name)
                description.set(Meta.DESC)
                url.set("https://github.com/${Meta.GITHUB_REPO}")
                licenses {
                    license {
                        name.set(Meta.LICENSE)
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("grahamdaley")
                        name.set("Graham Daley")
                        email.set("graham@daleybread.com")
                    }
                }
                scm {
                    url.set("https://github.com/${Meta.GITHUB_REPO}.git")
                    connection.set("scm:git:git://github.com/${Meta.GITHUB_REPO}.git")
                    developerConnection.set("scm:git:git://github.com/#${Meta.GITHUB_REPO}.git")
                }
                issueManagement {
                    url.set("https://github.com/${Meta.GITHUB_REPO}/issues")
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri(Meta.RELEASE))
            snapshotRepositoryUrl.set(uri(Meta.SNAPSHOT))
            val ossrhUsername =
                providers
                    .environmentVariable("OSSRH_USERNAME")
                    .orElse(providers.gradleProperty("ossrh.username"))
            val ossrhPassword =
                providers
                    .environmentVariable("OSSRH_PASSWORD")
                    .orElse(providers.gradleProperty("ossrh.password"))
            if (ossrhUsername.isPresent && ossrhPassword.isPresent) {
                username.set(ossrhUsername.get())
                password.set(ossrhPassword.get())
            }
        }
    }
}
