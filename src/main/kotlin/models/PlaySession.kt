package models

data class PlaySession(
    var id: Int = 0,
    var day: String,
    var duration: Int,
    var sessionRating: Double,
    var isGameFinished: Boolean = false,
    var notes: String?,
    var gameId: Int = -1,
)
