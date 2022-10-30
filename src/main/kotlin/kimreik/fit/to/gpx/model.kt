package kimreik.fit.to.gpx

import java.util.*

data class Track(val segment: TrackSegment)

data class TrackSegment(val trackPoints: List<TrackPoint>)

data class TrackPoint(
    val lat: Double,
    val lon: Double,
    val elevation: Double,
    val time: Date
)

