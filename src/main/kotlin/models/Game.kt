package models

/**
 * Represents a game with its key properties, including metadata, ownership,
 * and rating information.
 *
 * @property id a unique identifier for the game, assigned automatically by [GameAPI]
 * @property name the name/title of the game
 * @property genre the genre of the game (e.g., Action, RPG, Strategy)
 * @property platform the platform(s) the game is available on (e.g., PC, PS5)
 * @property ageRating the recommended age rating for the game (e.g., E, T, M)
 * @property price the cost of the game in Euros
 * @property publicRating the official rating of the game (e.g., from online sources), optional
 * @property isOwned indicates whether the user owns the game
 * @property releaseYear the year the game was released
 */
data class Game(
    var id: Int = 0,
    var name: String,
    var genre: String,
    var platform: String,
    var ageRating: String,
    var price: Double,
    var publicRating: Double?,
    var isOwned: Boolean = false,
    var releaseYear: Int,
)
