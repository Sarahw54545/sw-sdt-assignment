package models

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
