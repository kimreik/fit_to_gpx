package kimreik.fit.to.gpx

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * integration tests
 */
internal class FitToGpxTest {

    val fitToGpx = FitToGpx()
    @Nested
    inner class Convert {

        @Test
        fun `results size always matches sources size no matter what`() {
            fitToGpx.convert().size shouldBe 0

            fitToGpx.convert(
                VALID_FILE.path,
                CORRUPTED_FILE.path,
                "non_existent_file.fit",
                VALID_FILE.path //duplicate
            ).size shouldBe 4
        }

        @Test
        fun `returns source-target paths pair on success`() {
            val result: ConversionResult = fitToGpx.convert(VALID_FILE.path).first()

            result.success() shouldBe true
            result.sourcePath shouldBe VALID_FILE.path
            result.targetPath shouldNotBe null
            result.error shouldBe null
        }

        @Test
        fun `returns ConversionError enum in case of error`() {
            val result: ConversionResult = fitToGpx.convert(CORRUPTED_FILE.path).first()

            result.success() shouldBe false
            result.sourcePath shouldBe CORRUPTED_FILE.path
            result.targetPath shouldBe null
            result.error shouldBe ConversionError.FILE_READING_ERROR
        }

    }

}