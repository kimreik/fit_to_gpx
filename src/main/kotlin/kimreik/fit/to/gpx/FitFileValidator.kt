package kimreik.fit.to.gpx

import kimreik.fit.to.gpx.ConversionError.FILE_DOES_NOT_EXIST
import kimreik.fit.to.gpx.ConversionError.INVALID_EXTENSION
import java.io.File

class FitFileValidator {

    fun validate(file: File) {
        if (!file.exists()) {
            throw ConversionException(FILE_DOES_NOT_EXIST)
        }
        if (file.extension != "fit") {
            throw ConversionException(INVALID_EXTENSION)
        }
    }
}