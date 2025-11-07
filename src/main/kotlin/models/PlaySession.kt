package models

data class PlaySession (
    var sessionId: Int = 0,
    var gameId: Int,
    var day: String,
    var duration: Int,
    var sessionRating: Double,
    var isGameFinished: Boolean = false,
    var notes: String?
) {

    // Formatting Listing Output
    override fun toString() =
        if (isGameFinished)
            "$sessionId: Game:$gameId (Completed), Day Played: $day, Session Duration: $duration, Session Rating: $sessionRating, Notes: $notes"
        else
            "$sessionId: Game:$gameId (Not Completed), Day Played: $day, Session Duration: $duration, Session Rating: $sessionRating, Notes: $notes"
}