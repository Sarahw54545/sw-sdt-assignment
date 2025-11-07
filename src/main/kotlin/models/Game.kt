package models

data class Game (
    var gameId: Int = 0,
    var name: String,
    var genre: String,
    var platform: String,
    var ageRating: String,
    var price: Double,
    var publicRating: Double?,
    var isOwned: Boolean = false,
    var releaseYear: Int,
    var playSessions: MutableList<PlaySession> = mutableListOf()
) {
    // Handling IDs for PlaySessions
    private var lastSessionId = 0
    private fun getNextSessionId() = lastSessionId++

    fun numberOfSessions() = playSessions.size

    // Create - CRUD
    fun addSession(session: PlaySession): Boolean {
    session.sessionId = getNextSessionId()
        return playSessions.add(session)
    }

    // Read - CRUD
    fun findSession(id: Int): PlaySession? {
        return playSessions.find{ session -> session.sessionId == id}
    }

    // Update - CRUD
    fun update(id: Int, newSession: PlaySession): Boolean {
        val foundSession = findSession(id)

        if(foundSession != null) {
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
        return playSessions.removeIf { session -> session.sessionId == id}
    }

    // Formatting Listing Output
    override fun toString(): String {
        val owned = if(isOwned) "Yes" else "No"
        return "$gameId: $name, Genre: $genre, Platform: $platform, Age Rating: $ageRating, Price: $price, Public Rating: $publicRating, Owned: $owned, Release Year: $releaseYear"
    }
}