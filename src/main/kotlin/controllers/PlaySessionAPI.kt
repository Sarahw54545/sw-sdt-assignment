package controllers
import models.PlaySession

class PlaySessionAPI(private val gameAPI: GameAPI) {
    private var playSessions = mutableListOf<PlaySession>()

    // Handling IDs for PlaySessions
    private var lastSessionId = 0

    private fun getNextSessionId() = lastSessionId++

    // Counting Methods

    fun numberOfSessions(): Int {
        return playSessions.size
    }

    // CRUD

    // Create - CRUD (WIP)
    fun addSession(session: PlaySession): Boolean {
        session.id = getNextSessionId()
        return playSessions.add(session)
    }

    // Refactor needed
    fun addSessionToGame(
        sessionId: Int,
        gameId: Int,
    ): String {
        val session = playSessions.find { it.id == sessionId }
        if (session == null) {
            return "Session with ID $sessionId doesn't exist"
        } else if (gameAPI.findGame(gameId) != null) {
            return "Game with ID $gameId doesn't exist"
        } else {
            playSessions[playSessions.indexOf(session)] = session.copy(gameId = gameId)
            return "Session $sessionId was moved to Game with ID $gameId"
        }
    }

    // Read - CRUD
    fun findSession(id: Int): PlaySession? {
        return playSessions.find { session -> session.id == id }
    }

    // Update - CRUD
    fun update(
        id: Int,
        newSession: PlaySession,
    ): Boolean {
        val foundSession = findSession(id)

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
    fun delete(id: Int): Boolean {
        return playSessions.removeIf { session -> session.id == id }
    }

    // Listing Methods
}
