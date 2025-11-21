package controllers

import models.Game
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertNull
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
                genre = "Platformer",
                platform = "NES",
                ageRating = "3+",
                price = 29.99,
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
        populatedGames!!.addGame(superMario!!)
        populatedGames!!.addGame(pokemonYellow!!)
        populatedGames!!.addGame(minecraft!!)
        populatedGames!!.addGame(marvelRivals!!)
        populatedGames!!.addGame(redDead2!!)
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
            val newGame =
                Game(
                    name = "Animal Crossing: New Leaf",
                    genre = "Simulation",
                    platform = "Nintendo 3DS",
                    ageRating = "3+",
                    price = 34.99,
                    publicRating = 9.0,
                    isOwned = true,
                    releaseYear = 2012,
                )

            assertEquals(5, populatedGames!!.numberOfGames())
            assertTrue(populatedGames!!.addGame(newGame))
            assertEquals(6, populatedGames!!.numberOfGames())
            assertEquals(newGame, populatedGames!!.findGame(populatedGames!!.numberOfGames() - 1))
        }

        @Test
        fun `Adding a Game to an empty list adds to the Array`() {
            val newGame =
                Game(
                    name = "Animal Crossing: New Leaf",
                    genre = "Simulation",
                    platform = "Nintendo 3DS",
                    ageRating = "3+",
                    price = 34.99,
                    publicRating = 9.0,
                    isOwned = true,
                    releaseYear = 2012,
                )
            assertEquals(0, emptyGames!!.numberOfGames())
            assertTrue(emptyGames!!.addGame(newGame))
            assertEquals(1, emptyGames!!.numberOfGames())
            assertEquals(newGame, emptyGames!!.findGame(emptyGames!!.numberOfGames() - 1))
        }
    }

    @Nested
    inner class UpdateGames {
        @Test
        fun `Updating a game that doesn't exist returns false`() {
            assertFalse(
                populatedGames!!.updateGame(
                    6,
                    Game(
                        6,
                        "Updating Game",
                        "Updating Genre",
                        "Updating Platform",
                        "Updating Age Rating",
                        60.00,
                        6.0,
                        false,
                        2000,
                    ),
                ),
            )

            assertFalse(
                populatedGames!!.updateGame(
                    -1,
                    Game(
                        -1,
                        "Updating Game",
                        "Updating Genre",
                        "Updating Platform",
                        "Updating Age Rating",
                        60.00,
                        6.0,
                        false,
                        2000,
                    ),
                ),
            )

            assertFalse(
                emptyGames!!.updateGame(
                    0,
                    Game(
                        0,
                        "Updating Game",
                        "Updating Genre",
                        "Updating Platform",
                        "Updating Age Rating",
                        60.00,
                        6.0,
                        false,
                        2000,
                    ),
                ),
            )
        }

        @Test
        fun `Updating a game that exists returns true and updates successfully`() {
            assertEquals(superMario, populatedGames!!.findGame(0))
            assertEquals("Super Mario Bros", populatedGames!!.findGame(0)!!.name)
            assertEquals("Platformer", populatedGames!!.findGame(0)!!.genre)
            assertEquals("NES", populatedGames!!.findGame(0)!!.platform)
            assertEquals("3+", populatedGames!!.findGame(0)!!.ageRating)
            assertEquals(29.99, populatedGames!!.findGame(0)!!.price)
            assertEquals(9.4, populatedGames!!.findGame(0)!!.publicRating)
            assertEquals(true, populatedGames!!.findGame(0)!!.isOwned)
            assertEquals(1985, populatedGames!!.findGame(0)!!.releaseYear)

            assertTrue(
                populatedGames!!.updateGame(
                    0,
                    Game(
                        0,
                        "Updating Game",
                        "Updating Genre",
                        "Updating Platform",
                        "Updating Age Rating",
                        60.00,
                        6.0,
                        false,
                        2000,
                    ),
                ),
            )

            assertEquals("Updating Game", populatedGames!!.findGame(0)!!.name)
            assertEquals("Updating Genre", populatedGames!!.findGame(0)!!.genre)
            assertEquals("Updating Platform", populatedGames!!.findGame(0)!!.platform)
            assertEquals("Updating Age Rating", populatedGames!!.findGame(0)!!.ageRating)
            assertEquals(60.00, populatedGames!!.findGame(0)!!.price)
            assertEquals(6.0, populatedGames!!.findGame(0)!!.publicRating)
            assertEquals(false, populatedGames!!.findGame(0)!!.isOwned)
            assertEquals(2000, populatedGames!!.findGame(0)!!.releaseYear)
        }
    }

@Nested
inner class DeleteGames {
    @Test
    fun `deleting a Game that doesn't exist returns null`() {
        assertNull(emptyGames!!.deleteGame(0))
        assertNull(populatedGames!!.deleteGame(-1))
        assertNull(populatedGames!!.deleteGame(5))
    }

    @Test
    fun `deleting a Game that exists deletes successfully and returns deleted object`() {
        assertEquals(5, populatedGames!!.numberOfGames())
        assertEquals(redDead2, populatedGames!!.deleteGame(4))
        assertEquals(4, populatedGames!!.numberOfGames())
        assertEquals(superMario, populatedGames!!.deleteGame(0))
        assertEquals(3, populatedGames!!.numberOfGames())
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
