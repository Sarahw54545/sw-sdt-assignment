package controllers
import models.PlaySession

class PlaySessionAPI(private val gameAPI: GameAPI) {
    private var playSessions = mutableListOf<PlaySession>()

    // Handling IDs for PlaySessions
    private var lastSessionId = 0

    private fun getNextSessionId() = lastSessionId++

    // For formatting the list of outputs
    private fun formatListString(sessionsToFormat: List<PlaySession>): String =
        sessionsToFormat
            .joinToString(separator = "\n") { session ->
                playSessions.indexOf(session).toString() + ": " + session.toString()
            }

    // Counting Methods

    fun numberOfSessions(): Int {
        return playSessions.size
    }

    fun numberOfSessionsPerGame(gameId: Int): Int {
        return playSessions.count { it.gameId == gameId }
    }

    // CRUD

    // Create - CRUD (Refactor)
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
    fun findSession(id: Int): PlaySession? {
        return playSessions.find { session -> session.id == id }
    }

    // Update - CRUD
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
    fun deleteSession(idToDelete: Int) = findSession(idToDelete)?.also { playSessions.remove(it) }

    // Listing Methods

    fun listAllSessions(): String =
        if (playSessions.isEmpty()) {
            "No Play Sessions Stored"
        } else {
            formatListString(playSessions)
        }

    // Refactor with Lambdas
    fun listAllSessionsForGame(gameId: Int): String {
        if (gameAPI.findGame(gameId) == null) {
            return "No Games Stored with ID $gameId"
        }

        val sessionsForGame = playSessions.filter { it.gameId == gameId }
        return if (sessionsForGame.isEmpty()) {
            "No Play Sessions Stored for Game ID $gameId"
        } else {
            formatListString(sessionsForGame)
        }
    }
}
