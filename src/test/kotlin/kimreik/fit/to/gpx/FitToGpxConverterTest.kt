package kimreik.fit.to.gpx

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File

internal class FitToGpxConverterTest {

    private val validator: FitFileValidator = mockk()
    private val fitReader: FitReader = mockk()
    private val builder: GpxFileBuilder = mockk()
    private val converter = FitToGpxConverter(validator, fitReader, builder)

    @Nested
    inner class Convert {

        private val expectedResult = File("result_file.gpx")
        private val expectedTrack = Track(TrackSegment(emptyList()))

        @BeforeEach
        fun initSuccessMocks() {
            every { validator.validate(any()) } just runs
            every { fitReader.read(any()) } returns expectedTrack
            every { builder.build(any(), any()) } returns expectedResult
        }

        @Test
        fun `returns path to GPX file`() {

            val result: String = converter.convert("filepath")

            result shouldBe expectedResult.path
        }

        @Test
        fun `validates source file before conversion`() {

            every { validator.validate(any()) } throws ConversionException(ConversionError.FILE_DOES_NOT_EXIST)

            shouldThrow<ConversionException> { converter.convert("fileName") }
        }

        @Test
        fun `reads Track from fit file`() {

            converter.convert("source_file.fit").first()

            verify { fitReader.read(any()) }
        }

        @Test
        fun `builds gpx file`() {

            converter.convert("source_file.fit").first()

            verify { builder.build(any(), any()) }
        }
    }
}