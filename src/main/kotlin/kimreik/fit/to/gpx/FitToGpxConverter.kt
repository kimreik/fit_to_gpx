package kimreik.fit.to.gpx

import java.io.File

class FitToGpxConverter(
    private val fitFileValidator: FitFileValidator,
    private val fitReader: FitReader,
    private val gpxFileBuilder: GpxFileBuilder
) {

    fun convert(path: String): String {

        val sourceFile = File(path)

        fitFileValidator.validate(sourceFile)

        val track = fitReader.read(sourceFile)

        val gpxFile = gpxFileBuilder.build(sourceFile, track)

        return gpxFile.path
    }

}