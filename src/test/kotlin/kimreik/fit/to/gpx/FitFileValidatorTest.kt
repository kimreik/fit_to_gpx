package kimreik.fit.to.gpx

import io.kotest.assertions.throwables.shouldThrowMessage
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File

private val NON_EXISTENT_FILE = File("non-existent_file.fit")
private val WRONG_EXTENSION_FILE = ResourceFileLoader().getResourceFile("/wrong_extension.txt")


internal class FitFileValidatorTest {

    val validator = FitFileValidator()

    @Nested
    inner class Validate {

        @Test
        fun `validates that file exists`() {
            shouldThrowMessage(ConversionError.FILE_DOES_NOT_EXIST.message) {
                validator.validate(NON_EXISTENT_FILE)
            }
        }

        @Test
        fun `validates that file has fit extension`() {
            shouldThrowMessage(ConversionError.INVALID_EXTENSION.message) {
                validator.validate(WRONG_EXTENSION_FILE)
            }
        }

    }
}