package controllers

import models.Game
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GameAPITest {
    private var superMario: Game? = null
    private var pokemonYellow: Game? = null
    private var minecraft: Game? = null
    private var marvelRivals: Game? = null
    private var redDead2: Game? = null

    private var populatedGames: GameAPI? = GameAPI()
    private var emptyGames: GameAPI? = GameAPI()

    @BeforeEach
    fun setup() {

        superMario = Game(name = "Super Mario Bros", genre = "Platformer", platform = "NES", ageRating = "3+", price = 29.99, publicRating = 9.4, isOwned = true, releaseYear = 1985)
        pokemonYellow = Game(name = "Pokemon Yellow", genre = "RPG", platform = "Game Boy", ageRating = "3+", price = 39.99, publicRating = 9.1, isOwned = false, releaseYear = 1998)
        minecraft = Game(name = "Minecraft", genre = "Sandbox", platform = "PC", ageRating = "7+", price = 19.99, publicRating = 9.2, isOwned = true, releaseYear = 2011)
        marvelRivals = Game(name = "Marvel Rivals", genre = "Shooter", platform = "PC", ageRating = "16+", price = 0.0, publicRating = 7.5, isOwned = true, releaseYear = 2024)
        redDead2 = Game(name = "Red Dead Redemption 2", genre = "Action-Adventure", platform = "PS4", ageRating = "18+", price = 59.99, publicRating = 9.8, isOwned = false, releaseYear = 2018)

        // Add 5 Games
        populatedGames!!.add(superMario!!)
        populatedGames!!.add(pokemonYellow!!)
        populatedGames!!.add(minecraft!!)
        populatedGames!!.add(marvelRivals!!)
        populatedGames!!.add(redDead2!!)
    }

    @AfterEach
    fun tearDown() {
        superMario = null
        pokemonYellow = null
        minecraft = null
        marvelRivals = null
        redDead2 = null
        populatedGames = null
        emptyGames = null
    }

    @Nested
    inner class CountingMethods {

        // Owned Games = 3, Unowned Games = 2

        @Test
        fun numberOfOwnedGamesCalculatedCorrectly() {
            assertEquals(3, populatedGames!!.numberOfOwnedGames())
            assertEquals(0, emptyGames!!.numberOfOwnedGames())
        }

        @Test
        fun numberOfUnownedGamesCalculatedCorrectly() {
            assertEquals(2, populatedGames!!.numberOfUnownedGames())
            assertEquals(0, emptyGames!!.numberOfUnownedGames())
        }
    }
}