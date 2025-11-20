package models

data class PlaySession(
    var id: Int = 0,
    var day: String,
    var duration: Int,
    var sessionRating: Double,
    var isGameFinished: Boolean = false,
    var notes: String?,
    var gameId: Int,
) {
    // Formatting Listing Output (Can refactor)
    override fun toString() =
        if (isGameFinished) {
            """$id: Game:$gameId (Completed)
                |Day Played: $day
                |Session Duration: $duration
                |Session Rating: $sessionRating
                |Notes: $notes""".trimMargin("|")
        } else {
            """$id: Game:$gameId (Not Completed)
                |Day Played: $day
                |Session Duration: $duration
                |Session Rating: $sessionRating
                |Notes: $notes""".trimMargin("|")
        }
}
