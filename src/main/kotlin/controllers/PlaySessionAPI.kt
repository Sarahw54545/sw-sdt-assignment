package controllers
import models.PlaySession
import persistence.Serializer

/**
 * The `PlaySessionAPI` class manages a collection of [PlaySession] objects,
 * providing CRUD operations, listing, and persistence functionality.
 *
 * Each session is associated with a game through the [GameAPI] provided.
 *
 * @property gameAPI the [GameAPI] instance used to link sessions to games
 * @property serializer the [Serializer] used to load and store play sessions
 */
class PlaySessionAPI(private val gameAPI: GameAPI, serializerType: Serializer) {
    /** The internal list of play sessions managed by this API */
    private var playSessions = mutableListOf<PlaySession>()

    /** The serializer used to read/write play sessions to persistent storage */
    private var serializer: Serializer = serializerType

    // Handling IDs for PlaySessions

    /** The last ID assigned to a play session. Used for auto-incrementing IDs. */
    private var lastSessionId = 0

    /** Generates a unique ID for a new play session */
    private fun getNextSessionId() = lastSessionId++

    // For formatting the list of outputs

    /**
     * Formats a list of play sessions into a human-readable string.
     *
     * @param sessionsToFormat the list of [PlaySession] objects to format
     * @return a formatted string representing all play sessions
     */
    private fun formatListString(sessionsToFormat: List<PlaySession>): String =
        sessionsToFormat
            .joinToString(separator = "\n") { session ->
                "${session.id}: " +
                    "Day Played: ${session.day} | " +
                    "Session Duration (Mins): ${session.duration} | " +
                    "Session Rating: ${session.sessionRating} | " +
                    "Game Completed: ${session.isGameFinished} | " +
                    "Session Notes: ${session.notes}"
            }

    // Count Methods

    /** Returns the total number of play sessions stored */
    fun numberOfSessions(): Int {
        return playSessions.size
    }

    /**
     * Returns the number of play sessions associated with a specific game.
     *
     * @param gameId the ID of the game to count sessions for
     * @return the number of sessions linked to the game
     */
    fun numberOfSessionsPerGame(gameId: Int): Int {
        return playSessions.count { it.gameId == gameId }
    }

    // CRUD

    // Create - CRUD

    /**
     * Adds a new play session to a specific game.
     *
     * @param gameId the ID of the game to associate with the session
     * @param session the [PlaySession] object to add
     * @return `true` if the session was added successfully, `false` if the game does not exist
     */
    fun addSessionToGame(
        gameId: Int,
        session: PlaySession,
    ): Boolean {
        if (gameAPI.findGame(gameId) == null) return false
        session.id = getNextSessionId()
        session.gameId = gameId

        return playSessions.add(session)
    }

    // Read - CRUD

    /**
     * Finds a play session by its unique ID.
     *
     * @param id the ID of the session to find
     * @return the [PlaySession] object if found, or `null` if not found
     */
    fun findSession(id: Int): PlaySession? {
        return playSessions.find { session -> session.id == id }
    }

    // Update - CRUD

    /**
     * Updates an existing play session with new values.
     *
     * @param idToUpdate the ID of the session to update
     * @param newSession a [PlaySession] object containing updated values
     * @return `true` if the update was successful, `false` otherwise
     */
    fun updateSession(
        idToUpdate: Int,
        newSession: PlaySession,
    ): Boolean {
        val foundSession = findSession(idToUpdate)

        if (foundSession != null) {
            foundSession.day = newSession.day
            foundSession.duration = newSession.duration
            foundSession.sessionRating = newSession.sessionRating
            foundSession.isGameFinished = newSession.isGameFinished
            foundSession.notes = newSession.notes
            return true
        }
        return false
    }

    // Delete - CRUD
    // Will search for session with ID to delete, if found also delete the session and return the deleted session object
    // Reference: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/also.html

    /**
     * Deletes a play session by its ID.
     *
     * @param idToDelete the ID of the session to delete
     * @return the deleted [PlaySession] object if found and removed, `null` otherwise
     */
    fun deleteSession(idToDelete: Int) = findSession(idToDelete)?.also { playSessions.remove(it) }

    // Listing Methods

    /**
     * Lists all play sessions stored in the system.
     *
     * @return a formatted string of all play sessions or a message if none exist
     */
    fun listAllSessions(): String =
        if (playSessions.isEmpty()) {
            "No Play Sessions Stored"
        } else {
            formatListString(playSessions)
        }

    /**
     * Lists all play sessions associated with a specific game.
     *
     * @param gameId the ID of the game to list sessions for
     * @return a formatted string of sessions for the game or a message if none exist
     */
    fun listAllSessionsForGame(gameId: Int): String {
        val sessionsForGame = playSessions.filter { it.gameId == gameId }
        return if (sessionsForGame.isEmpty()) {
            "No Play Sessions Stored for Game ID $gameId"
        } else {
            formatListString(sessionsForGame)
        }
    }

    /**
     * Loads play sessions from persistent storage using the current serializer.
     *
     * @throws Exception if there is an error reading from storage
     */
    @Throws(Exception::class)
    fun load() {
        playSessions = serializer.read() as ArrayList<PlaySession>
    }

    /**
     * Saves the current list of play sessions to persistent storage.
     *
     * @throws Exception if there is an error writing to storage
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(playSessions)
    }

    /**
     * Overrides the current serializer with a new one.
     *
     * @param newSerializer the new [Serializer] to use
     */
    fun overrideSerializer(newSerializer: Serializer) {
        serializer = newSerializer
    }
}
