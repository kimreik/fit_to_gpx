package kimreik.fit.to.gpx

import com.garmin.fit.DateTime
import com.garmin.fit.FitDecoder
import com.garmin.fit.FitMessages
import com.garmin.fit.RecordMesg
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

internal class FitReaderTest {

    val fitDecoder: FitDecoder = spyk()
    val validator: RecordMessageValidator = mockk()

    val fitReader: FitReader = FitReader(fitDecoder, validator)


    @Nested
    inner class Read {

        private val expectedMessages = FitMessages().apply {
            recordMesgs.add(
                RecordMesg().apply {
                    timestamp = DateTime(1030414240)
                    positionLat = 622878121
                    positionLong = 249246255
                    altitude = 42f
                    enhancedAltitude = 110.2f
                }
            )
        }

        private val messagesWithoutEnhancedAltitude = FitMessages().apply {
            recordMesgs.add(
                RecordMesg().apply {
                    timestamp = DateTime(1030414240)
                    positionLat = 622878121
                    positionLong = 249246255
                    altitude = 42f
                }
            )
        }

        private val expectedTrackPoint = TrackPoint(
            lat = 52.20904097,
            lon = 20.89157975,
            elevation = 110.2,
            time = Date(1661479840000)
        )

        @BeforeEach
        fun initSuccessMocks() {
            every { fitDecoder.decode(any()) } returns expectedMessages
            every { validator.validate(any()) } returns true
        }

        @Test
        fun `decodes fit file`() {
            fitReader.read(VALID_FILE)
            verify { fitDecoder.decode(any()) }
        }

        @Test
        fun `ignores corrupted waypoints`() {
            every { validator.validate(any()) } returns false

            val result = fitReader.read(VALID_FILE)

            result.segment.trackPoints.size shouldBe 0
        }

        @Test
        fun `builds TrackPoint from recordMessages`() {
            val result = fitReader.read(VALID_FILE).segment.trackPoints.first()

            assertEquals(expectedTrackPoint.lat, result.lat, 0.00000001)
            assertEquals(expectedTrackPoint.lon, result.lon, 0.00000001)
            assertEquals(expectedTrackPoint.elevation, result.elevation, 0.1)
            result.time shouldBe expectedTrackPoint.time
        }

        @Test
        fun `uses enhanced altitude if present`() {
            val enhanced = fitReader.read(VALID_FILE).segment.trackPoints.first()

            assertEquals(expectedTrackPoint.elevation, enhanced.elevation, 0.1)

            every { fitDecoder.decode(any()) } returns messagesWithoutEnhancedAltitude

            val default = fitReader.read(VALID_FILE).segment.trackPoints.first()

            assertEquals(42.0, default.elevation, 0.1)
        }

    }


}