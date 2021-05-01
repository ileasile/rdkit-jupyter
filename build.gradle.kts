import ru.ileasile.kotlin.apache2
import ru.ileasile.kotlin.developer
import ru.ileasile.kotlin.githubRepo

plugins {
    kotlin("jvm")
    kotlin("jupyter.api")
    id("com.github.johnrengelman.shadow")
    id("ru.ileasile.kotlin.publisher")
}

group = "org.jetbrains.kotlinx"
version = "1.0.0"
extra.set("localPublicationsRepo", rootDir.toPath().resolve("build/artifacts"))

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(files("lib/org.RDKit.jar"))
}

tasks.shadowJar {
    archiveBaseName.set(rootProject.name)
    archiveClassifier.set("")
    into("resources/lib") {
        from("lib")
    }

    manifest {
        attributes(tasks.jar.get().manifest.attributes)
    }
}

kotlinPublications {
    fun prop(name: String) = project.findProperty(name) as? String?

    sonatypeSettings(
        prop("kds.sonatype.user"),
        prop("kds.sonatype.password"),
        "rdkit-jupyter project, v. ${project.version}"
    )

    signingCredentials(
        prop("kds.sign.key.id"),
        prop("kds.sign.key.private"),
        prop("kds.sign.key.passphrase")
    )

    pom {
        githubRepo("ileasile", "rdkit-jupyter")
        inceptionYear.set("2021")
        licenses {
            apache2()
        }
        developers {
            developer("ileasile", "Ilya Muradyan", "Ilya.Muradyan@jetbrains.com")
        }
    }

    publication {
        publicationName = "jar"
        artifactId = "rdkit-jupyter"
        description = "RDKit for Jupyter Notebooks"
        packageName = artifactId
    }
}
