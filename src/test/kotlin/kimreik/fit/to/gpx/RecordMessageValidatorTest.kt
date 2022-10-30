package kimreik.fit.to.gpx

import com.garmin.fit.DateTime
import com.garmin.fit.RecordMesg
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class RecordMessageValidatorTest {

    val validator = RecordMessageValidator()

    @Nested
    inner class Validate {

        @Test
        fun `returns true on valid record message`() {
            validator.validate(valid()) shouldBe true
        }

        @Test
        fun `validates lat is present`() {
            val message = valid().apply { fields.removeIf { f -> f.name.equals("position_lat") } }
            validator.validate(message) shouldBe false
        }

        @Test
        fun `validates lon is present`() {
            val message = valid().apply { fields.removeIf { f -> f.name.equals("position_long") } }
            validator.validate(message) shouldBe false
        }

        @Test
        fun `validates altitude is present`() {
            val message = valid().apply { fields.removeIf { f -> f.name.equals("altitude") } }
            validator.validate(message) shouldBe false
        }

        @Test
        fun `validates timestamp is present`() {
            val message = valid().apply { fields.removeIf { f -> f.name.equals("timestamp") } }
            validator.validate(message) shouldBe false
        }

        private fun valid() = RecordMesg().apply {
            timestamp = DateTime(1030414240)
            positionLat = 622878121
            positionLong = 249246255
            altitude = 110.2f
        }
    }
}