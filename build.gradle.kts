plugins {
    kotlin("jvm")
    kotlin("jupyter.api")
    id("com.github.johnrengelman.shadow")
}

group = "org.jetbrains.kotlinx"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(files("lib/org.RDKit.jar"))
}

tasks.shadowJar {
    archiveBaseName.set(rootProject.name)
    archiveClassifier.set("")
    //mergeServiceFiles()
    into("resources/lib") {
        from("lib")
    }

    manifest {
        attributes(tasks.jar.get().manifest.attributes)
    }
}
