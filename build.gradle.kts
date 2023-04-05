plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.example.heap"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.20")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events("passed")
    }
}

kotlin {
    jvmToolchain(11)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

application {
    mainClass.set("MainKt")
}
