package kimreik.fit.to.gpx

import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.XmlVersion
import org.redundent.kotlin.xml.xml
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class GpxFileBuilder {

    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun build(source: File, track: Track): File {

        val xml = xml("gpx") {
            version = XmlVersion.V11
            attributes(
                "version" to "1.1",
                "creator" to "https://github.com/kimreik/fit_to_gpx",
                "xmlns:xsi" to "http://www.w3.org/2001/XMLSchema-instance",
                "xmlns" to "http://www.topografix.com/GPX/1/1",
                "xsi:schemaLocation" to
                        "http://www.topografix.com/GPX/1/1 " +
                        "http://www.topografix.com/GPX/1/1/gpx.xsd"
            )
            "trk" {
                "trkseg" {
                    track.segment.trackPoints.map {
                        "trkpt" {
                            attributes(
                                "lat" to it.lat,
                                "lon" to it.lon
                            )
                            "ele" {
                                -it.elevation.toString()
                            }
                            "time" {
                                -dateFormat.format(it.time)
                            }
                        }
                    }
                }

            }
        }.toString(PrintOptions(singleLineTextElements = true))

        val result = File(source.path.replace(".fit", ".gpx"))
        result.writeText(xml)

        return result
    }
}