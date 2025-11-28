import controllers.GameAPI
import controllers.PlaySessionAPI
import models.Game
import models.PlaySession
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.readNextBoolean
import utils.readNextDouble
import utils.readNextInt
import utils.readNextLine
import java.io.File
import java.lang.System.exit

private val gameAPI = GameAPI(JSONSerializer(File("games.json")))
private val sessionAPI = PlaySessionAPI(gameAPI, JSONSerializer(File("playSessions.json")))

fun mainMenu(): Int {
    print(
        """
          > --------------------------------------------
          > |        GAME TRACKER APPLICATION           |
          > --------------------------------------------
          > | MAIN MENU                                 |
          > |   1) Manage Games                         |
          > |   2) Manage Play Sessions                 |
          > |   3) Search / Filter                      |
          > --------------------------------------------
          > |   20) Save Data                           |
          > |   21) Load Data                           |
          > --------------------------------------------
          > |   0) Exit                                 |
          > --------------------------------------------
          > """.trimMargin(">"),
    )
    return readNextInt("> ==>> ")
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> gamesMenu()
            2 -> sessionsMenu()
            3 -> searchMenu()
            20 -> save()
            21 -> load()
            0 -> exitApp()
            else -> println("Invalid Option Entered: $option")
        }
    } while (true)
}

// Games Menu

fun gamesMenu() {
    print(
        """
          > ----------------------------------------
          > |             MANAGE GAMES              |
          > ----------------------------------------
          > |   1) Add Game                         |
          > |   2) List Games                       |
          > |   3) Update Game                      |
          > |   4) Delete Game                      |
          > ----------------------------------------
          > |   0) Back                             |
          > ----------------------------------------
          > """.trimMargin(">"),
    )
    when (readNextInt("> ==> ")) {
        1 -> addGame()
        2 -> listGames()
        3 -> updateGame()
        4 -> deleteGame()
        0 -> return
        else -> println("Invalid Option")
    }
}

fun addGame() {
    val name = readNextLine("Enter Game Name: ")
    val genre = readNextLine("Enter Game Genre: ")
    val platform = readNextLine("Enter Platform: ")
    val ageRating = readNextLine("Enter Age Rating: ")
    val price = readNextDouble("Enter Price: ")
    val publicRating = readNextDouble("Enter Public Rating (0.1 - 10.0): ")
    val isOwned = readNextBoolean("Is the Game Owned? (Y/N): ")
    val releaseYear = readNextInt("Enter Release Year: ")

    val isAdded =
        gameAPI.addGame(
            Game(
                name = name,
                genre = genre,
                platform = platform,
                ageRating = ageRating,
                price = price,
                publicRating = publicRating,
                isOwned = isOwned,
                releaseYear = releaseYear,
            ),
        )
    println(if (isAdded) "Game Added Successfully" else "Add Failed")
}

fun listGames() {
    if (gameAPI.numberOfGames() > 0) {
        println(gameAPI.listAllGames())
    } else {
        println("Option Invalid - No Games Stored")
    }
}

fun updateGame() {
    if (gameAPI.numberOfGames() > 0) {
        listGames()

        val id = readNextInt("Enter the Game ID to Update: ")

        val name = readNextLine("Enter New Game Name: ")
        val genre = readNextLine("Enter New Game Genre: ")
        val platform = readNextLine("Enter New Platform: ")
        val ageRating = readNextLine("Enter New Age Rating: ")
        val price = readNextDouble("Enter New Price: ")
        val publicRating = readNextDouble("Enter New Public Rating (0.1 - 10.0): ")
        val isOwned = readNextBoolean("Is the Game Owned? (Y/N): ")
        val releaseYear = readNextInt("Enter New Release Year: ")

        val updated =
            gameAPI.updateGame(
                id,
                Game(
                    name = name,
                    genre = genre,
                    platform = platform,
                    ageRating = ageRating,
                    price = price,
                    publicRating = publicRating,
                    isOwned = isOwned,
                    releaseYear = releaseYear,
                ),
            )
        println(if (updated) "Update Successful" else "Update Failed")
    } else {
        println("Option Invalid - No Games Stored")
    }
}

fun deleteGame() {
    if (gameAPI.numberOfGames() > 0) {
        listGames()
        val id = readNextInt("Enter ID of Game to Delete: ")
        val deleted = gameAPI.deleteGame(id)

        println(if (deleted != null) "Delete Successful! Deleted Game: ${deleted.name}" else "Delete Failed, Please Try Again")
    } else {
        println("Option Invalid - No Games Stored")
    }
}

// Play Session Menu

fun sessionsMenu() {
    print(
        """
          > ----------------------------------------
          > |          MANAGE PLAY SESSIONS         |
          > ----------------------------------------
          > |   1) Add Play Session to Game         |
          > |   2) List Sessions for Game           |
          > |   3) Update Play Session              |
          > |   4) Delete Play Session              |
          > ----------------------------------------
          > |   0) Back                             |
          > ----------------------------------------
          > """.trimMargin(">"),
    )
    when (readNextInt("> ==>> ")) {
        1 -> addPlaySession()
        2 -> listSessionsForGame()
        3 -> updatePlaySession()
        4 -> deletePlaySession()
        0 -> return
        else -> println("Invalid Option")
    }
}

fun addPlaySession() {
    if (gameAPI.numberOfGames() > 0) {
        println(gameAPI.listAllGames())
        val gameId = readNextInt("Enter the ID of the Game You Would Like to Add a Play Session To: ")

        val game = gameAPI.findGame(gameId)

        if (game == null) {
            println("Game Not Found")
        } else {
            val day = readNextLine("Enter the Day you Played ${game.name}: ")
            val duration = readNextInt("Enter the Duration of the Play Session (In Minutes): ")
            val sessionRating = readNextDouble("Enter the Rating You Would Give The Session From 0.1 - 10: ")
            val isGameFinished = readNextBoolean("Did You Complete ${game.name} In This Session: ")
            val notes = readNextLine("Enter Any Extra Notes From the Session: ")

            val isAdded =
                sessionAPI.addSessionToGame(
                    gameId,
                    PlaySession(
                        day = day,
                        duration = duration,
                        sessionRating = sessionRating,
                        isGameFinished = isGameFinished,
                        notes = notes,
                    ),
                )
            println(if (isAdded) "Session Added Successfully" else "Add Failed")
        }
    } else {
        println("No Games Added Yet - Please Add a Game First")
    }
}

fun listSessionsForGame() {
    if (gameAPI.numberOfGames() > 0) {
        println(gameAPI.listAllGames())
        val gameId = readNextInt("Enter the ID of the Game You Would Like to List Play Sessions For: ")

        val game = gameAPI.findGame(gameId)
        if (game == null) {
            println("Game Not Found")
        } else {
            println("Play Sessions for ${game.name}\n${sessionAPI.listAllSessionsForGame(gameId)}")
        }
    } else {
        println("No Games Added Yet - Please Add a Game First")
    }
}

fun updatePlaySession() {
    if (sessionAPI.numberOfSessions() > 0) {
        println(sessionAPI.listAllSessions())

        val sessionId = readNextInt("Enter The ID of the Play Session to Update: ")
        val foundSession = sessionAPI.findSession(sessionId)

        if (foundSession == null) {
            println("No Session Found With ID: $sessionId")
        } else {
            val day = readNextLine("Enter New Day (Current: ${foundSession.day}): ")
            val duration = readNextInt("Enter New Session Duration (Current: ${foundSession.duration}): ")
            val sessionRating = readNextDouble("Enter New Session Rating (0.1 - 10) (Current: ${foundSession.sessionRating}):")
            val isGameFinished = readNextBoolean("Did You Complete The Game In This Session (Current: ${foundSession.isGameFinished}): ")
            val notes = readNextLine("Enter Any Extra Notes From The Session (Current: ${foundSession.notes}): ")

            val updated =
                sessionAPI.updateSession(
                    sessionId,
                    PlaySession(
                        day = day,
                        duration = duration,
                        sessionRating = sessionRating,
                        isGameFinished = isGameFinished,
                        notes = notes,
                    ),
                )
            println(if (updated) "Session was Updated Successfully" else "Update Failed")
        }
    } else {
        println("No Play Sessions Added Yet - Please Add a Play Session To A Game First")
    }
}

fun deletePlaySession() {
    if (sessionAPI.numberOfSessions() > 0) {
        println(sessionAPI.listAllSessions())

        val id = readNextInt("Enter the ID of the Play Session to Delete: ")
        val deleted = sessionAPI.deleteSession(id)

        println(if (deleted != null) "Delete Successful! Deleted Session ID: ${deleted.id}" else "Delete Failed, Please Try Again")
    } else {
        println("Option Invalid - No Play Sessions Stored")
    }
}

// Search Menu

fun searchMenu() {
    print(
        """
          > ----------------------------------------
          > |           SEARCH / FILTER            |
          > ----------------------------------------
          > |   1) Search Games By Name            |
          > |   2) Filter Games By Owned Status    |
          > |   3) Filter Games By Genre           |
          > |   4) Filter Games By Rating          |
          > ----------------------------------------
          > |   0) Back                             |
          > ----------------------------------------
          > """.trimMargin(">"),
    )
    when (readNextInt("> ==>> ")) {
        0 -> return
        else -> println("Invalid Option")
    }
}

// Persistence Menu

fun save() {
    println(
        """
        > ----------------------------------------
        > |         CHOOSE FORMAT TO SAVE         |
        > ----------------------------------------
        > |   1) JSON                             |
        > |   2) XML                              |
        > ----------------------------------------
        > |   0) Back                             |
        > ----------------------------------------
        """.trimMargin(">")
    )

    val choice = readNextInt("> ==> ")

    try {
        when (choice) {
            1 -> {
                gameAPI.overrideSerializer(JSONSerializer(File("games.json")))
                sessionAPI.overrideSerializer(JSONSerializer(File("playSessions.json")))
            }
            2 -> {
                gameAPI.overrideSerializer(XMLSerializer(File("games.xml")))
                sessionAPI.overrideSerializer(XMLSerializer(File("playSessions.xml")))
            }
            0 -> return
            else -> {
                println("Invalid Option")
            }
        }
        gameAPI.store()
        sessionAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    println(
        """
        > ----------------------------------------
        > |         CHOOSE FORMAT TO LOAD         |
        > ----------------------------------------
        > |   1) JSON                             |
        > |   2) XML                              |
        > ----------------------------------------
        > |   0) Back                             |
        > ----------------------------------------
        """.trimMargin(">")
    )

    val choice = readNextInt("> ==> ")

    try {
        when (choice) {
            1 -> {
                gameAPI.overrideSerializer(JSONSerializer(File("games.json")))
                sessionAPI.overrideSerializer(JSONSerializer(File("playSessions.json")))
            }
            2 -> {
                gameAPI.overrideSerializer(XMLSerializer(File("games.xml")))
                sessionAPI.overrideSerializer(XMLSerializer(File("playSessions.xml")))
            }
            0 -> return
            else -> {
                println("Invalid Option")
            }
        }
        gameAPI.load()
        sessionAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp() {
    println("Exiting... happy gaming!!")
    exit(0)
}

fun main() {
    runMenu()
}
