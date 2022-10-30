package kimreik.fit.to.gpx

import java.io.File


val VALID_FILE = ResourceFileLoader().getResourceFile("/valid.fit")
val CORRUPTED_FILE = ResourceFileLoader().getResourceFile("/corrupted_fit_file.fit")
val GPX_XSD = ResourceFileLoader().getResourceFile("/gpx.xsd")

internal class ResourceFileLoader {

    fun getResourceFile(fileName: String): File =
        this::class.java
            .getResource(fileName)
            ?.path
            ?.substring(1)
            ?.let { it: String -> File(it) }
            ?: throw java.lang.IllegalStateException("$fileName is missing")
}