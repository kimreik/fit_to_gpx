package kimreik.fit.to.gpx

class ConversionException(val error: ConversionError) : RuntimeException(error.message)