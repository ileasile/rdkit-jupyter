package org.jetbrains.kotlinx.jupyter.rdkit

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

object Loader {
    private const val jarLibPath = "/resources/lib/"
    private const val libName = "GraphMolWrap"

    fun load() {
        val libraryDir: Path = run {
            val path = Files.createTempDirectory("jni_rdkit")
            path.toFile().deleteOnExit()
            path
        }

        val resource = System.mapLibraryName(libName)
        val inputStream = Loader::class.java.getResourceAsStream("$jarLibPath$resource")
        var libraryPath: Path? = null
        try {
            if (inputStream != null) {
                libraryPath = libraryDir.resolve(resource)
                Files.copy(inputStream, libraryPath!!, StandardCopyOption.REPLACE_EXISTING)
                System.load(libraryPath.toString())
            } else {
                System.loadLibrary(libName)
            }
        } catch (e: Throwable) {
            libraryPath?.toFile()?.delete()
            throw e
        }
    }
}
