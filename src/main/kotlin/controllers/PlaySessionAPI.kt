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

    // Create - CRUD
    fun addSession(session: PlaySession): Boolean {
        session.id = getNextSessionId()
        return playSessions.add(session)
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