package kimreik.fit.to.gpx

class ConversionResult(
    val sourcePath: String,
    val targetPath: String?,
    val error: ConversionError? = null
) {

    fun success(): Boolean {
        return error == null
    }

    constructor(sourcePath: String, error: ConversionError) : this(sourcePath, null, error)
}