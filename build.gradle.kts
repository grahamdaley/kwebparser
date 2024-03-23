import com.vanniktech.maven.publish.SonatypeHost

group = "io.github.grahamdaley"
version = "1.0.5-SNAPSHOT"

object Meta {
    const val NAME = "kwebparser"
    const val DESC = "A simple web parser inspired by Selenium's Page Factory, written in Kotlin"
    const val LICENSE = "Apache-2.0"
    const val GITHUB_REPO = "grahamdaley/kwebparser"
}

// ------------------------------------------------------ plugins

// https://youtrack.jetbrains.com/issue/KTIJ-19369#focus=Comments-27-5181027.0-0
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-library`
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.maven.publish)
}

// ------------------------------------------------------ repositories

repositories {
    mavenCentral()
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

// ------------------------------------------------------ tasks

tasks.withType<Test> {
    useJUnitPlatform()
}

// ------------------------------------------------------ publish

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
    signAllPublications()
    coordinates(project.group.toString(), Meta.NAME, project.version.toString())

    pom {
        name.set(rootProject.name)
        description.set(Meta.DESC)
        inceptionYear.set("2024")
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
