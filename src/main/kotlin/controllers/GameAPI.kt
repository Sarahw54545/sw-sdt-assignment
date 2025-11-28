package controllers
import models.Game
import persistence.Serializer

class GameAPI(serializerType: Serializer) {
    private var games = ArrayList<Game>()
    private var serializer: Serializer = serializerType

    // Handling IDs for Games
    private var lastId = 0

    private fun getId() = lastId++

    // For formatting the list of outputs
    private fun formatListString(gamesToFormat: List<Game>): String =
        gamesToFormat
            .joinToString(separator = "\n") { game ->
                "${game.id}: " +
                    "${game.name} (${game.platform}) | " +
                    "Genre: ${game.genre} | " +
                    "Age Rating: ${game.ageRating} | " +
                    "Price: \u20AC${"%.2f".format(game.price)} | " +
                    // Formats Price Output to 2 Decimal Places Reference: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.text/format.html
                    "Public Rating: ${game.publicRating} | " +
                    "Owned: ${game.isOwned} | " +
                    "Release Year: ${game.releaseYear}"
            }

    // Count Methods

    // Count total number of games
    fun numberOfGames() = games.size

    // Count number of games that are owned
    fun numberOfOwnedGames() = games.count { game: Game -> game.isOwned }

    // Count number of games that are NOT owned
    fun numberOfUnownedGames() = games.count { game: Game -> !game.isOwned }

    // CRUD

    // Create (Add) - CRUD
    fun addGame(game: Game): Boolean {
        game.id = getId()
        return games.add(game)
    }

    // Read - CRUD
    fun findGame(id: Int): Game? {
        return games.find { game -> game.id == id }
    }

    // Update - CRUD
    fun updateGame(
        idToUpdate: Int,
        game: Game?,
    ): Boolean {
        val foundGame = findGame(idToUpdate)

        if ((foundGame != null) && (game != null)) {
            foundGame.name = game.name
            foundGame.genre = game.genre
            foundGame.platform = game.platform
            foundGame.ageRating = game.ageRating
            foundGame.price = game.price
            foundGame.publicRating = game.publicRating
            foundGame.isOwned = game.isOwned
            foundGame.releaseYear = game.releaseYear
            return true
        }
        return false
    }

    // Delete - CRUD
    // Will search for game with ID to delete, if found also delete the game and return the deleted game object
    fun deleteGame(idToDelete: Int) = findGame(idToDelete)?.also { games.remove(it) }

    // Listing Methods

    // Listing All Games
    fun listAllGames() =
        if (games.isEmpty()) {
            "No Games Stored"
        } else {
            formatListString(games)
        }

    // Listing Games That Are Owned
    fun listOwnedGames() =
        if (numberOfOwnedGames() == 0) {
            "No Owned Games"
        } else {
            formatListString(games.filter { game -> game.isOwned })
        }

    // Listing Games That Are Unowned
    fun listUnownedGames() =
        if (numberOfUnownedGames() == 0) {
            "No Unowned Games"
        } else {
            formatListString(games.filter { game -> !game.isOwned })
        }

    // Persistence Methods
    @Throws(Exception::class)
    fun load() {
        games = serializer.read() as ArrayList<Game>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(games)
    }

    fun overrideSerializer(newSerializer: Serializer) {
        serializer = newSerializer
    }

    fun searchByGameName(searchString: String) =
        formatListString(games.filter { game -> game.name.contains(searchString, ignoreCase = true) })
}
