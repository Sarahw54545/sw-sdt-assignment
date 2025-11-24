import controllers.GameAPI
import controllers.PlaySessionAPI
import models.Game
import utils.readNextBoolean
import utils.readNextDouble
import utils.readNextInt
import utils.readNextLine
import java.lang.System.exit

private val gameAPI = GameAPI()
private val sessionAPI = PlaySessionAPI(gameAPI)

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
}

fun listSessionsForGame() {
}

fun updatePlaySession() {
}

fun deletePlaySession() {
}

// Search Menu

fun searchMenu() {
    print(
        """
          > ----------------------------------------
          > |             SEARCH / FILTER           |
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
}

fun load() {
}

fun exitApp() {
    println("Exiting... happy gaming!!")
    exit(0)
}

fun main() {
    runMenu()
}
