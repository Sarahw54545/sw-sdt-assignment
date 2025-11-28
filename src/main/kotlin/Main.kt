import controllers.GameAPI
import controllers.PlaySessionAPI
import models.Game
import models.PlaySession
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.readNextBoolean
import utils.readNextInt
import utils.readNextLine
import utils.readValidDay
import utils.readValidGenre
import utils.readValidPositiveDouble
import utils.readValidPositiveInt
import utils.readValidRating
import java.io.File
import java.lang.System.exit

/**
 * The primary Game Tracker application, responsible for handling menus,
 * reading user input, and delegating actions to the `GameAPI` and `PlaySessionAPI`.
 */

private val gameAPI = GameAPI(JSONSerializer(File("games.json")))
private val sessionAPI = PlaySessionAPI(gameAPI, JSONSerializer(File("playSessions.json")))

/**
 * Displays the main menu to the user and reads the selected option.
 *
 * @return the integer option selected by the user.
 */
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

/**
 * Runs the main application loop. The menu is repeatedly displayed until the
 * user chooses to exit. All high-level navigation branches from this function.
 */
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

/**
 * Displays the Games menu and routes the user's selection
 * to the appropriate Game management operation.
 */
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

/**
 * Reads user input to create a new [Game] and attempts to add it
 * to the persistent store through the [GameAPI].
 *
 * Prompts the user for:
 * - name
 * - genre
 * - platform
 * - age rating
 * - price
 * - public rating
 * - ownership status
 * - release year
 *
 * Prints whether the operation succeeded.
 */
fun addGame() {
    val name = readNextLine("Enter Game Name: ")
    val genre = readValidGenre("Enter Game Genre: ")
    val platform = readNextLine("Enter Platform: ")
    val ageRating = readNextLine("Enter Age Rating: ")
    val price = readValidPositiveDouble("Enter Price: ")
    val publicRating = readValidRating("Enter Public Rating (0.1 - 10.0): ")
    val isOwned = readNextBoolean("Is the Game Owned? (Y/N): ")
    val releaseYear = readValidPositiveInt("Enter Release Year: ")

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

/**
 * Prints all stored games, if any exist.
 * If none are stored, a message is displayed indicating that no games are available.
 */
fun listGames() {
    if (gameAPI.numberOfGames() > 0) {
        println(gameAPI.listAllGames())
    } else {
        println("Option Invalid - No Games Stored")
    }
}

/**
 * Prompts the user to select an existing game by ID and enter new values
 * for all editable fields. Attempts to update the game via the [GameAPI].
 *
 * Prints whether the update was successful.
 */
fun updateGame() {
    if (gameAPI.numberOfGames() > 0) {
        listGames()

        val id = readNextInt("Enter the Game ID to Update: ")

        val name = readNextLine("Enter New Game Name: ")
        val genre = readValidGenre("Enter New Game Genre: ")
        val platform = readNextLine("Enter New Platform: ")
        val ageRating = readNextLine("Enter New Age Rating: ")
        val price = readValidPositiveDouble("Enter New Price: ")
        val publicRating = readValidRating("Enter New Public Rating (0.1 - 10.0): ")
        val isOwned = readNextBoolean("Is the Game Owned? (Y/N): ")
        val releaseYear = readValidPositiveInt("Enter New Release Year: ")

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

/**
 * Allows the user to delete a game by its ID.
 * If the game exists, it is removed and the deleted game's name is printed.
 * If it does not exist, an error message is shown.
 */
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

/**
 * Displays the Play Sessions menu and routes the user's selection
 * to the appropriate session management operation.
 */
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

/**
 * Reads user input to add a new [PlaySession] to a selected [Game].
 * Prompts for day, duration, session rating, completion status, and notes.
 *
 * Prints whether the addition was successful.
 */
fun addPlaySession() {
    if (gameAPI.numberOfGames() > 0) {
        println(gameAPI.listAllGames())
        val gameId = readNextInt("Enter the ID of the Game You Would Like to Add a Play Session To: ")

        val game = gameAPI.findGame(gameId)

        if (game == null) {
            println("Game Not Found")
        } else {
            val day = readValidDay("Enter the Day you Played ${game.name}: ")
            val duration = readValidPositiveInt("Enter the Duration of the Play Session (In Minutes): ")
            val sessionRating = readValidRating("Enter the Rating You Would Give The Session From 0.1 - 10: ")
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

/**
 * Lists all play sessions for a selected [Game].
 * If the game or sessions do not exist, appropriate messages are displayed.
 */
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

/**
 * Updates an existing [PlaySession] based on user input.
 * Prompts for new day, duration, rating, completion status, and notes.
 * Displays messages based on success or failure.
 */
fun updatePlaySession() {
    if (sessionAPI.numberOfSessions() > 0) {
        println(sessionAPI.listAllSessions())

        val sessionId = readNextInt("Enter The ID of the Play Session to Update: ")
        val foundSession = sessionAPI.findSession(sessionId)

        if (foundSession == null) {
            println("No Session Found With ID: $sessionId")
        } else {
            val day = readValidDay("Enter New Day (Current: ${foundSession.day}): ")
            val duration = readValidPositiveInt("Enter New Session Duration (Current: ${foundSession.duration}): ")
            val sessionRating = readValidRating("Enter New Session Rating (0.1 - 10) (Current: ${foundSession.sessionRating}):")
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

/**
 * Deletes a [PlaySession] by its ID.
 * Prints confirmation of deletion or failure.
 */
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

/**
 * Displays the Search / Filter menu and routes user input
 * to either searching games by name or filtering owned games by genre.
 */
fun searchMenu() {
    print(
        """
          > ----------------------------------------
          > |           SEARCH / FILTER            |
          > ----------------------------------------
          > |   1) Search Games By Name            |
          > |   2) Filter Owned Games By Genre     |
          > ----------------------------------------
          > |   0) Back                             |
          > ----------------------------------------
          > """.trimMargin(">"),
    )
    when (readNextInt("> ==>> ")) {
        1 -> searchGamesByName()
        2 -> filterOwnedGamesByGenre()
        0 -> return
        else -> println("Invalid Option")
    }
}

/**
 * Searches stored games by name based on user input.
 * Prints matching games or a message if none are found.
 */
fun searchGamesByName() {
    val input = readNextLine("Enter The Game Name to Search By: ")
    val searchResults = gameAPI.searchByGameName(input)

    if (searchResults.isEmpty()) {
        println("No Games Found")
    } else {
        println(searchResults)
    }
}

/**
 * Filters all owned games by a user-specified genre.
 * Prints the matching games or a message if none are found.
 */
fun filterOwnedGamesByGenre() {
    val genre = readValidGenre("Enter Genre: ")
    val results = gameAPI.filterOwnedGamesByGenre(genre)

    if (results.isEmpty()) {
        println("No Owned Games Found For Genre: $genre")
    } else {
        println(results)
    }
}

// Persistence Menu

/**
 * Saves games and play sessions to JSON or XML based on user selection.
 * Overrides the serializers used in [GameAPI] and [PlaySessionAPI] accordingly.
 */
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
        """.trimMargin(">"),
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

/**
 * Loads games and play sessions from JSON or XML based on user selection.
 * Overrides the serializers used in [GameAPI] and [PlaySessionAPI] accordingly.
 */
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
        """.trimMargin(">"),
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

/**
 * Exits the application gracefully, printing a farewell message.
 */
fun exitApp() {
    println("Exiting... happy gaming!!")
    exit(0)
}

/**
 * Entry point for the application. Starts the main menu loop.
 */
fun main() {
    runMenu()
}
