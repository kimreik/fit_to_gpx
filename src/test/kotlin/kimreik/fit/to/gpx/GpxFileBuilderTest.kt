package kimreik.fit.to.gpx

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

internal class GpxFileBuilderTest {

    val builder = GpxFileBuilder()

    val expectedGPXName = VALID_FILE.path.replace(".fit", ".gpx")

    val track = Track(
        TrackSegment(
            listOf(
                TrackPoint(
                    lat = 52.20904097,
                    lon = 20.89157975,
                    elevation = 110.2,
                    time = Date(1661479840000)
                )
            )
        )
    )

    @Nested
    inner class Build {

        @AfterEach
        fun cleanup() {
            File(expectedGPXName).delete()
        }

        @Test
        fun `creates GPX file with the same name`() {
            val result = builder.build(VALID_FILE, track)

            result.exists() shouldBe true
            result.path shouldBe expectedGPXName
        }

        @Test
        fun `creates valid gpx file`() {

            val resultFile = builder.build(VALID_FILE, track)

            resultFile.isValidXML(GPX_XSD) shouldBe true
        }

        private fun File.isValidXML(xsdFile: File): Boolean {
            return try {
                val validator =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                        .newSchema(xsdFile)
                        .newValidator()

                val source = StreamSource(this)

                validator.validate(source)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

    }
}