package kimreik.fit.to.gpx

import com.garmin.fit.FitDecoder
import com.garmin.fit.RecordMesg
import com.garmin.fit.util.SemicirclesConverter.semicirclesToDegrees
import java.io.File
import java.io.FileInputStream

class FitReader(
    private val fitDecoder: FitDecoder,
    private val recordMessageValidator: RecordMessageValidator
) {

    fun read(file: File): Track {

        val fitMessages = fitDecoder.decode(FileInputStream(file))

        return Track(
            TrackSegment(
                fitMessages.recordMesgs
                    .filter(recordMessageValidator::validate)
                    .map {
                        TrackPoint(
                            lat = semicirclesToDegrees(it.positionLat),
                            lon = semicirclesToDegrees(it.positionLong),
                            elevation = readElevation(it),
                            time = it.timestamp.date
                        )
                    }
            )
        )
    }


    private fun readElevation(record: RecordMesg): Double {
        val altitudeField = if (record.hasField(78)) {
            record.getField("enhanced_altitude")
        } else {
            record.getField("altitude")
        }

        return altitudeField.doubleValue
    }
}