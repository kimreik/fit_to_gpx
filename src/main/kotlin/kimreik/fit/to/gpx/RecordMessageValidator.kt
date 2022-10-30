package kimreik.fit.to.gpx

import com.garmin.fit.RecordMesg

class RecordMessageValidator {

    fun validate(message: RecordMesg): Boolean {
        return message.getField("position_lat") != null
                && message.getField("position_long") != null
                && message.getField("timestamp") != null
                && message.getField("altitude") != null
    }
}