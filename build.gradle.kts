plugins {
    `build-scan`
    `maven-publish`
    kotlin("jvm") version "1.3.21"
}

repositories {
    jcenter()
}

group = "org.kwebparser"
version = "0.0.15"

dependencies {
    val kotlinVersion: String by rootProject.extra
    val htmlUnitVersion: String by rootProject.extra
    val jsoupVersion: String by rootProject.extra
    val junitVersion: String by rootProject.extra

    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
    implementation("net.sourceforge.htmlunit:htmlunit:${htmlUnitVersion}")
    implementation("org.jsoup:jsoup:${jsoupVersion}")

    testImplementation("org.jetbrains.kotlin:kotlin-test:${kotlinVersion}")
    testImplementation("org.mockito:mockito-inline:2.8.47")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-alpha01")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${junitVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

buildScan {
    setTermsOfServiceAgree("yes")
    setTermsOfServiceUrl("https://gradle.com/terms-of-service")
    publishAlways()
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("/Users/gdaley/maven")
        }
    }
}
