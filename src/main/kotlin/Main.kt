import utils.readNextInt
import java.lang.System.exit

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
          > ==>> """.trimMargin(">"),
    )
    return readNextInt("> ==>>")
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
          > ==>> """.trimMargin(">")
    )
    when(readNextInt("> ==>")) {
        1 -> addGame()
        2 -> listGames()
        3 -> updateGame()
        4 -> deleteGame()
        0 -> return
        else -> println("Invalid Option")
    }
}

fun addGame() {
}

fun listGames() {
}

fun updateGame() {
}

fun deleteGame() {
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
          > ==>> """.trimMargin(">")
    )
    when(readNextInt("> ==>>")) {
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
          > ==>> """.trimMargin(">")
    )
    when(readNextInt("> ==>>")) {
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
