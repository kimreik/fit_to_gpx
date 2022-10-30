package kimreik.fit.to.gpx

import com.garmin.fit.FitDecoder
import com.garmin.fit.FitRuntimeException

class FitToGpx {
    private val converter = FitToGpxConverter(
        FitFileValidator(),
        FitReader(
            FitDecoder(),
            RecordMessageValidator()
        ),
        GpxFileBuilder()
    )

    fun convert(vararg paths: String): List<ConversionResult> =
        paths.map { path ->
            try {
                ConversionResult(path, converter.convert(path))
            } catch (conversionException: ConversionException) {
                ConversionResult(path, conversionException.error)
            } catch (fitException: FitRuntimeException) {
                ConversionResult(path, ConversionError.FILE_READING_ERROR)
            }
        }
}




fun main(args: Array<String>) {
    FitToGpx().convert(*args)
        .map { "${it.sourcePath} : ${buildStatus(it)}" }
        .forEach { println(it) }
}

private fun buildStatus(result: ConversionResult) =
    if (result.success()) {
        "SUCCESS: ${result.targetPath}"
    } else {
        "FAILED: ${result.error?.message}"
    }
