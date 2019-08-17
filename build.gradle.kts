import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    id("com.jfrog.bintray") version "1.8.1"
    `maven-publish`
    `java-library`
}

project.group = "org.kwebparser"
project.version = "0.0.16"
val artifactID = "kwebparser"
val kotlinVersion = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion
val junitVersion = "5.3.1"
val htmlUnitVersion = "2.33"
val jsoupVersion = "1.10.2"

dependencies {
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
    implementation("net.sourceforge.htmlunit:htmlunit:$htmlUnitVersion")
    implementation("org.jsoup:jsoup:$jsoupVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation("org.mockito:mockito-inline:2.8.47")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-alpha01")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

repositories {
    jcenter()
}

publishing {
    publications {
        create<MavenPublication>(artifactID) {
            groupId = project.group as String
            artifactId = artifactID
            version = project.version as String
            from(components["java"])
        }
    }
}

fun findProperty(s: String) = project.findProperty(s) as String?
bintray {
    user = findProperty("bintrayUser")
    key = findProperty("bintrayApiKey")
    publish = true
    override = true
    setPublications(artifactID)
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = project.group as String
        name = artifactID
        githubRepo = "grahamdaley/kwebparser"
        vcsUrl = "https://github.com/grahamdaley/kwebparser.git"
        desc = "A simple webparser using htmlunit and written in Kotlin"
        setLicenses("Apache-2.0")
        version(delegateClosureOf<BintrayExtension.VersionConfig>{
            name = project.version as String
            vcsTag = project.version as String
        })
    })
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}