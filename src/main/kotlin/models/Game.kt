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
) {
    // Formatting Listing Output
    override fun toString(): String {
        val owned = if (isOwned) "Yes" else "No"
        return """
            |$id: $name
            |Genre: $genre
            |Platform: $platform
            |Age Rating: $ageRating
            |Price: $price
            |Public Rating: $publicRating
            |Owned: $owned
            |Release Year: $releaseYear""".trimMargin("|")
    }
}
