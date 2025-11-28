package controllers

import models.Game
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File

class GameAPITest {
    private var superMario: Game? = null
    private var pokemonYellow: Game? = null
    private var minecraft: Game? = null
    private var marvelRivals: Game? = null
    private var redDead2: Game? = null

    private var populatedGames: GameAPI? = GameAPI(XMLSerializer(File("games.xml")))
    private var emptyGames: GameAPI? = GameAPI(XMLSerializer(File("empty-games.xml")))

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
    inner class ListingMethods {
        @Nested
        inner class ListAll {
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

        @Nested
        inner class ListOwnedGames {
            // 3 Owned
            @Test
            fun `listOwnedGames Returns No Owned Games Message when Array is Empty`() {
                assertEquals(0, emptyGames!!.numberOfOwnedGames())
                assertTrue(emptyGames!!.listOwnedGames().lowercase().contains("no owned games"))
            }

            @Test
            fun `ListOwnedGames Returns All Games that are Owned When There Are Owned Games Stored(isOwned = true)`() {
                assertEquals(3, populatedGames!!.numberOfOwnedGames())

                val ownedGamesString = populatedGames!!.listOwnedGames().lowercase()
                assertTrue(ownedGamesString.contains("super mario bros"))
                assertFalse(ownedGamesString.contains("pokemon yellow"))
                assertTrue(ownedGamesString.contains("minecraft"))
                assertTrue(ownedGamesString.contains("marvel rivals"))
                assertFalse(ownedGamesString.contains("red dead redemption 2"))
            }
        }

        @Nested
        inner class ListUnOwnedGames {
            // 2 Unowned
            @Test
            fun `listUnownedGames Returns No Unowned Games Message when Array is Empty`() {
                assertEquals(0, emptyGames!!.numberOfUnownedGames())
                assertTrue(emptyGames!!.listUnownedGames().lowercase().contains("no unowned games"))
            }

            @Test
            fun `ListOwnedGames Returns All Games that are Unowned When There Are Unowned Games Stored(isOwned = false)`() {
                assertEquals(2, populatedGames!!.numberOfUnownedGames())

                val unownedGamesString = populatedGames!!.listUnownedGames().lowercase()
                assertFalse(unownedGamesString.contains("super mario bros"))
                assertTrue(unownedGamesString.contains("pokemon yellow"))
                assertFalse(unownedGamesString.contains("minecraft"))
                assertFalse(unownedGamesString.contains("marvel rivals"))
                assertTrue(unownedGamesString.contains("red dead redemption 2"))
            }
        }
    }

    @Nested
    inner class PersistenceTests {
//        @Nested
//        inner class XMLTests {
//            @Test
//            fun `saving and loading an empty collection in XML doesn't crash app`() {
//                // Saving an empty games.XML file.
//                val storingGames = GameAPI(XMLSerializer(File("games.xml")))
//                storingGames.store()
//
//                // Loading the empty games.xml file into a new object
//                val loadedGames = GameAPI(XMLSerializer(File("games.xml")))
//                loadedGames.load()
//
//                // Comparing the source of the games (storingGames) with the XML loaded games (loadedGames)
//                assertEquals(0, storingGames.numberOfGames())
//                assertEquals(0, loadedGames.numberOfGames())
//                assertEquals(storingGames.numberOfGames(), loadedGames.numberOfGames())
//            }
//
//            @Test
//            fun `saving and loading an loaded collection in XML doesn't loose data`() {
//                // Storing 3 notes to the games.XML file.
//                val storingGames = GameAPI(XMLSerializer(File("games.xml")))
//                storingGames.addGame(superMario!!)
//                storingGames.addGame(pokemonYellow!!)
//                storingGames.addGame(minecraft!!)
//                storingGames.store()
//
//                // Loading games.xml into a different collection
//                val loadedGames = GameAPI(XMLSerializer(File("games.xml")))
//                loadedGames.load()
//
//                // Comparing the source of the games (storingGames) with the XML loaded games (loadedGames)
//                assertEquals(3, storingGames.numberOfGames())
//                assertEquals(3, loadedGames.numberOfGames())
//                assertEquals(storingGames.numberOfGames(), loadedGames.numberOfGames())
//                assertEquals(storingGames.findGame(0), loadedGames.findGame(0))
//                assertEquals(storingGames.findGame(1), loadedGames.findGame(1))
//                assertEquals(storingGames.findGame(2), loadedGames.findGame(2))
//            }
//        }

        @Nested
        inner class JSONTests {
            @Test
            fun `saving and loading an empty collection in JSON doesn't crash app`() {
                // Saving an empty games.json file.
                val storingGames = GameAPI(JSONSerializer(File("games.json")))
                storingGames.store()

                // Loading the empty games.json file into a new object
                val loadedGames = GameAPI(JSONSerializer(File("games.json")))
                loadedGames.load()

                // Comparing the source of the games (storingGames) with the JSON loaded games (loadedGames)
                assertEquals(0, storingGames.numberOfGames())
                assertEquals(0, loadedGames.numberOfGames())
                assertEquals(storingGames.numberOfGames(), loadedGames.numberOfGames())
            }

            @Test
            fun `saving and loading an loaded collection in JSON doesn't loose data`() {
                // Storing 3 games to the games.JSON file.
                val storingGames = GameAPI(JSONSerializer(File("games.json")))
                storingGames.addGame(superMario!!)
                storingGames.addGame(pokemonYellow!!)
                storingGames.addGame(minecraft!!)
                storingGames.store()

                // Loading games.json into a different collection
                val loadedGames = GameAPI(JSONSerializer(File("games.json")))
                loadedGames.load()

                // Comparing the source of the games (storingGames) with the XML loaded games (loadedGames)
                assertEquals(3, storingGames.numberOfGames())
                assertEquals(3, loadedGames.numberOfGames())
                assertEquals(storingGames.numberOfGames(), loadedGames.numberOfGames())
                assertEquals(storingGames.findGame(0), loadedGames.findGame(0))
                assertEquals(storingGames.findGame(1), loadedGames.findGame(1))
                assertEquals(storingGames.findGame(2), loadedGames.findGame(2))
            }
        }
    }
}
