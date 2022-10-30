package kimreik.fit.to.gpx

enum class ConversionError(val message: String) {
    FILE_DOES_NOT_EXIST("Source file does not exists"),
    INVALID_EXTENSION("Source file should have 'fit' extension"),
    FILE_READING_ERROR("Failed to read from the source file")
}