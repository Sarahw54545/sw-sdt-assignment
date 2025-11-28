package models

/**
 * Represents a single play session for a game, including details about
 * when it was played, its duration, rating, completion status, and optional notes.
 *
 * @property id a unique identifier for the play session, assigned automatically by [PlaySessionAPI]
 * @property day the day of the week when the session occurred (e.g., Monday, Tuesday)
 * @property duration the duration of the session in minutes
 * @property sessionRating the user's rating for this session (0.1 to 10.0)
 * @property isGameFinished indicates whether the game was completed during this session
 * @property notes optional notes or comments about the session
 * @property gameId the ID of the game this session belongs to
 */
data class PlaySession(
    var id: Int = 0,
    var day: String,
    var duration: Int,
    var sessionRating: Double,
    var isGameFinished: Boolean = false,
    var notes: String?,
    var gameId: Int = -1,
)
