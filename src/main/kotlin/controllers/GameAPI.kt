package controllers
import models.Game
import persistence.Serializer

/**
 * The `GameAPI` class manages a collection of [Game] objects, providing
 * CRUD operations, search, filtering, and persistence functionality.
 *
 * @property serializer the [Serializer] used to load and store games
 */
class GameAPI(serializerType: Serializer) {
    /** The internal list of games managed by the API */
    private var games = ArrayList<Game>()

    /** The serializer used to read/write games to persistent storage */
    private var serializer: Serializer = serializerType

    // Handling IDs for Games

    /** The last ID assigned to a game. Used for auto-incrementing IDs. */
    private var lastId = 0

    /** Generates a unique ID for a new game */
    private fun getId() = lastId++

    // For formatting the list of outputs

    /**
     * Formats a list of games into a human-readable string.
     *
     * @param gamesToFormat the list of [Game] objects to format
     * @return a formatted string representing all games
     */
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

    /** Returns the total number of games in the system */
    fun numberOfGames() = games.size

    // Count number of games that are owned

    /** Returns the number of games marked as owned */
    fun numberOfOwnedGames() = games.count { game: Game -> game.isOwned }

    // Count number of games that are NOT owned

    /** Returns the number of games not owned */
    fun numberOfUnownedGames() = games.count { game: Game -> !game.isOwned }

    // CRUD

    // Create (Add) - CRUD

    /**
     * Adds a new game to the system.
     *
     * @param game the [Game] object to add
     * @return `true` if the game was added successfully, `false` otherwise
     */
    fun addGame(game: Game): Boolean {
        game.id = getId()
        return games.add(game)
    }

    // Read - CRUD

    /**
     * Finds a game by its unique ID.
     *
     * @param id the ID of the game to find
     * @return the [Game] object if found, or `null` if not found
     */
    fun findGame(id: Int): Game? {
        return games.find { game -> game.id == id }
    }

    // Update - CRUD

    /**
     * Updates an existing game's details.
     *
     * @param idToUpdate the ID of the game to update
     * @param game a [Game] object containing the new values
     * @return `true` if the update was successful, `false` otherwise
     */
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

    /**
     * Deletes a game by its ID.
     *
     * @param idToDelete the ID of the game to delete
     * @return the deleted [Game] object if found and removed, `null` otherwise
     */
    fun deleteGame(idToDelete: Int) = findGame(idToDelete)?.also { games.remove(it) }

    // Listing Methods

    // Listing All Games

    /**
     * Lists all games in the system.
     *
     * @return a formatted string of all games or a message if no games exist
     */
    fun listAllGames() =
        if (games.isEmpty()) {
            "No Games Stored"
        } else {
            formatListString(games)
        }

    // Listing Games That Are Owned

    /**
     * Lists all owned games.
     *
     * @return a formatted string of owned games or a message if none are owned
     */
    fun listOwnedGames() =
        if (numberOfOwnedGames() == 0) {
            "No Owned Games"
        } else {
            formatListString(games.filter { game -> game.isOwned })
        }

    // Listing Games That Are Unowned

    /**
     * Lists all unowned games.
     *
     * @return a formatted string of unowned games or a message if all are owned
     */
    fun listUnownedGames() =
        if (numberOfUnownedGames() == 0) {
            "No Unowned Games"
        } else {
            formatListString(games.filter { game -> !game.isOwned })
        }

    // Persistence Methods

    /**
     * Loads games from the persistent storage using the current serializer.
     *
     * @throws Exception if there is an error reading from storage
     */
    @Throws(Exception::class)
    fun load() {
        games = serializer.read() as ArrayList<Game>
    }

    /**
     * Saves the current list of games to persistent storage.
     *
     * @throws Exception if there is an error writing to storage
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(games)
    }

    /**
     * Overrides the current serializer with a new one.
     *
     * @param newSerializer the new [Serializer] to use
     */
    fun overrideSerializer(newSerializer: Serializer) {
        serializer = newSerializer
    }

    /**
     * Searches games by name using a case-insensitive partial match.
     *
     * @param searchString the substring to search for in game names
     * @return a formatted string of matching games
     */
    fun searchByGameName(searchString: String) =
        formatListString(games.filter { game -> game.name.contains(searchString, ignoreCase = true) })

    /**
     * Filters all owned games by a specific genre.
     *
     * @param genre the genre to filter by (case-insensitive)
     * @return a formatted string of owned games matching the genre
     */
    fun filterOwnedGamesByGenre(genre: String): String {
        val filteredGames =
            games.filter { game ->
                game.isOwned &&
                    game.genre.equals(genre, ignoreCase = true)
            }
        return formatListString(filteredGames)
    }
}
