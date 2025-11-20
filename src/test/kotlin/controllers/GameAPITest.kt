package controllers

import models.Game
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
        superMario =
            Game(
                name = "Super Mario Bros",
                genre = "Platformer", platform = "NES",
                ageRating = "3+", price = 29.99,
                publicRating = 9.4,
                isOwned = true,
                releaseYear = 1985,
            )

        pokemonYellow =
            Game(
                name = "Pokemon Yellow",
                genre = "RPG",
                platform = "Game Boy",
                ageRating = "3+",
                price = 39.99,
                publicRating = 9.1,
                isOwned = false,
                releaseYear = 1998,
            )

        minecraft =
            Game(
                name = "Minecraft",
                genre = "Sandbox",
                platform = "PC",
                ageRating = "7+",
                price = 19.99,
                publicRating = 9.2,
                isOwned = true,
                releaseYear = 2011,
            )

        marvelRivals =
            Game(
                name = "Marvel Rivals",
                genre = "Shooter",
                platform = "PC",
                ageRating = "16+",
                price = 0.0,
                publicRating = 7.5,
                isOwned = true,
                releaseYear = 2024,
            )

        redDead2 =
            Game(
                name = "Red Dead Redemption 2",
                genre = "Action-Adventure",
                platform = "PS4",
                ageRating = "18+",
                price = 59.99,
                publicRating = 9.8,
                isOwned = false,
                releaseYear = 2018,
            )

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
        fun `Counting number of owned games correctly`() {
            assertEquals(3, populatedGames!!.numberOfOwnedGames())
            assertEquals(0, emptyGames!!.numberOfOwnedGames())
        }

        @Test
        fun `Counting number of unowned games correctly`() {
            assertEquals(2, populatedGames!!.numberOfUnownedGames())
            assertEquals(0, emptyGames!!.numberOfUnownedGames())
        }
    }

    @Nested
    inner class AddGames {

        @Test
        fun `Adding a Game to a populated list adds to the Array`() {
            val newGame = Game(
                name = "Animal Crossing: New Leaf",
                genre = "Simulation",
                platform = "Nintendo 3DS",
                ageRating = "3+",
                price = 34.99,
                publicRating = 9.0,
                isOwned = true,
                releaseYear = 2012
            )

            assertEquals(5, populatedGames!!.numberOfGames())
            assertTrue(populatedGames!!.add(newGame))
            assertEquals(6, populatedGames!!.numberOfGames())
            assertEquals(newGame, populatedGames!!.findGame(populatedGames!!.numberOfGames() - 1))
        }

        @Test
        fun `Adding a Game to an empty list adds to the Array`() {
            val newGame = Game(
                name = "Animal Crossing: New Leaf",
                genre = "Simulation",
                platform = "Nintendo 3DS",
                ageRating = "3+",
                price = 34.99,
                publicRating = 9.0,
                isOwned = true,
                releaseYear = 2012
            )
            assertEquals(0, emptyGames!!.numberOfGames())
            assertTrue(emptyGames!!.add(newGame))
            assertEquals(1, emptyGames!!.numberOfGames())
            assertEquals(newGame, emptyGames!!.findGame(emptyGames!!.numberOfGames() - 1))
        }
    }

    @Nested
    inner class ListGames {
        @Test
        fun `listAllGames returns No Games Stored Message when Array is Empty`() {
            assertEquals(0, emptyGames!!.numberOfGames())
            assertTrue(emptyGames!!.listAllGames().lowercase().contains("no games"))
        }

        @Test
        fun `listAllGames returns Games when Array has Games Stored`() {
            assertEquals(5, populatedGames!!.numberOfGames())
            val gamesString = populatedGames!!.listAllGames().lowercase()
            assertTrue(gamesString.contains("super mario bros"))
            assertTrue(gamesString.contains("pokemon yellow"))
            assertTrue(gamesString.contains("minecraft"))
            assertTrue(gamesString.contains("marvel rivals"))
            assertTrue(gamesString.contains("red dead redemption 2"))
        }
    }
}
