package controllers
import models.Game

class GameAPI() {
    private var games = ArrayList<Game>()

    // Handling IDs for Games
    private var lastId = 0

    private fun getId() = lastId++

    private fun formatListString(gamesToFormat: List<Game>): String =
        gamesToFormat
            .joinToString(separator = "/n") { game ->
                games.indexOf(game).toString() + ": " + game.toString()
            }

    fun numberOfOwnedGames() = games.count { game: Game -> game.isOwned }

    fun numberOfUnownedGames() = games.count { game: Game -> !game.isOwned }

    // Create - CRUD
    fun add(game: Game): Boolean {
        game.gameId = getId()
        return games.add(game)
    }

    // Read - CRUD
    fun findGame(id: Int): Game? {
        return games.find { game -> game.gameId == id }
    }

    // Update - CRUD
    fun update(
        id: Int,
        game: Game?,
    ): Boolean {
        val foundGame = findGame(id)

        if ((foundGame != null) && (game != null)) {
            foundGame.name = game.name
            foundGame.genre = game.genre
            foundGame.platform = game.platform
            foundGame.ageRating = game.ageRating
            foundGame.price = game.price
            foundGame.publicRating = game.publicRating
            foundGame.isOwned = game.isOwned
            foundGame.releaseYear = game.releaseYear
        }
        return false
    }

    // Delete - CRUD
    fun delete(id: Int) = games.removeIf { game -> game.gameId == id }

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
}
