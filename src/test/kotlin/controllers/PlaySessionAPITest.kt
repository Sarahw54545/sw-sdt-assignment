package controllers

import models.Game
import models.PlaySession
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

class PlaySessionAPITest {
    private var gameAPI: GameAPI? = null
    private var populatedSessions: PlaySessionAPI? = null
    private var emptySessions: PlaySessionAPI? = null

    private var superMario: Game? = null
    private var pokemonYellow: Game? = null
    private var minecraft: Game? = null
    private var marvelRivals: Game? = null
    private var redDead2: Game? = null

    private var marioSession1: PlaySession? = null
    private var pokemonSession1: PlaySession? = null
    private var minecraftSession1: PlaySession? = null
    private var minecraftSession2: PlaySession? = null
    private var rdr2Session1: PlaySession? = null

    @BeforeEach
    fun setup() {
        // Initialise GameAPI reference for PlaySessionAPI to Access
        gameAPI = GameAPI(XMLSerializer(File("games.xml")))

        populatedSessions = PlaySessionAPI(gameAPI!!, XMLSerializer(File("playSessions.xml")))
        emptySessions = PlaySessionAPI(gameAPI!!, XMLSerializer(File("playSessions.xml")))

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

        marioSession1 =
            PlaySession(
                day = "Monday",
                duration = 45,
                sessionRating = 8.5,
                isGameFinished = false,
                notes = "Reached World 2-1",
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

        pokemonSession1 =
            PlaySession(
                day = "Wednesday",
                duration = 120,
                sessionRating = 9.0,
                isGameFinished = false,
                notes = "Caught Pikachu",
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

        minecraftSession1 =
            PlaySession(
                day = "Friday",
                duration = 90,
                sessionRating = 8.2,
                isGameFinished = false,
                notes = "Built starter house",
            )

        minecraftSession2 =
            PlaySession(
                day = "Saturday",
                duration = 180,
                sessionRating = 9.1,
                isGameFinished = false,
                notes = "Explored a cave and found diamonds",
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

        rdr2Session1 =
            PlaySession(
                day = "Sunday",
                duration = 200,
                sessionRating = 9.8,
                isGameFinished = true,
                notes = "Completed the main story",
            )

        // Add games + sessions via API
        gameAPI!!.addGame(superMario!!)
        gameAPI!!.addGame(pokemonYellow!!)
        gameAPI!!.addGame(minecraft!!)
        gameAPI!!.addGame(marvelRivals!!)
        gameAPI!!.addGame(redDead2!!)

        populatedSessions!!.addSessionToGame(0, marioSession1!!)
        populatedSessions!!.addSessionToGame(1, pokemonSession1!!)
        populatedSessions!!.addSessionToGame(2, minecraftSession1!!)
        populatedSessions!!.addSessionToGame(2, minecraftSession2!!)
        populatedSessions!!.addSessionToGame(4, rdr2Session1!!)
    }

    @AfterEach
    fun tearDown() {
        gameAPI = null
        populatedSessions = null
        emptySessions = null
        superMario = null
        marioSession1 = null
        pokemonYellow = null
        pokemonSession1 = null
        minecraft = null
        minecraftSession1 = null
        minecraftSession2 = null
        marvelRivals = null
        redDead2 = null
        rdr2Session1 = null
    }

    @Nested
    inner class CountingMethods {
        // Sessions: 5

        @Test
        fun `Counting number of sessions correctly`() {
            assertEquals(5, populatedSessions!!.numberOfSessions())
            assertEquals(0, emptySessions!!.numberOfSessions())
        }

        @Test
        fun `Counting number of sessions per game correctly`() {
            // Marvel Rivals = 0 Sessions, Mario = 1 Session, Minecraft = 2 Sessions
            assertEquals(0, populatedSessions!!.numberOfSessionsPerGame(3))
            assertEquals(1, populatedSessions!!.numberOfSessionsPerGame(0))
            assertEquals(2, populatedSessions!!.numberOfSessionsPerGame(2))
        }
    }

    @Nested
    inner class AddSessions {
        @Test
        fun `Adding a Session in an existing session list adds to the Array`() {
            val newSession =
                PlaySession(
                    day = "Thursday",
                    duration = 60,
                    sessionRating = 7.5,
                    isGameFinished = false,
                    notes = "Practiced speedrunning World 1",
                )

            assertEquals(5, populatedSessions!!.numberOfSessions())
            assertTrue(populatedSessions!!.addSessionToGame(0, newSession))
            assertEquals(6, populatedSessions!!.numberOfSessions())
            assertEquals(newSession, populatedSessions!!.findSession(populatedSessions!!.numberOfSessions() - 1))
        }

        @Test
        fun `Adding a Session in an empty session list adds to the Array`() {
            val newSession =
                PlaySession(
                    day = "Thursday",
                    duration = 60,
                    sessionRating = 7.5,
                    isGameFinished = false,
                    notes = "Practiced speedrunning World 1",
                )
            assertEquals(0, emptySessions!!.numberOfSessions())
            assertTrue(emptySessions!!.addSessionToGame(0, newSession))
            assertEquals(1, emptySessions!!.numberOfSessions())
            assertEquals(newSession, emptySessions!!.findSession(emptySessions!!.numberOfSessions() - 1))
        }
    }

    @Nested
    inner class UpdateSessions {
        @Test
        fun `Updating a session that doesn't exist returns false`() {
            assertFalse(
                populatedSessions!!.updateSession(
                    6,
                    PlaySession(
                        6,
                        "Updating Day",
                        5,
                        3.0,
                        false,
                        null,
                    ),
                ),
            )

            assertFalse(
                populatedSessions!!.updateSession(
                    -1,
                    PlaySession(
                        -1,
                        "Updating Day",
                        5,
                        3.0,
                        false,
                        null,
                    ),
                ),
            )

            assertFalse(
                emptySessions!!.updateSession(
                    0,
                    PlaySession(
                        0,
                        "Updating Day",
                        5,
                        3.0,
                        false,
                        null,
                    ),
                ),
            )
        }

        @Test
        fun `Updating a session that exists returns true and updates successfully`() {
            assertEquals(marioSession1, populatedSessions!!.findSession(0))
            assertEquals("Monday", populatedSessions!!.findSession(0)!!.day)
            assertEquals(45, populatedSessions!!.findSession(0)!!.duration)
            assertEquals(8.5, populatedSessions!!.findSession(0)!!.sessionRating)
            assertEquals(false, populatedSessions!!.findSession(0)!!.isGameFinished)
            assertEquals("Reached World 2-1", populatedSessions!!.findSession(0)!!.notes)
            assertEquals(0, populatedSessions!!.findSession(0)!!.gameId)

            assertTrue(
                populatedSessions!!.updateSession(
                    0,
                    PlaySession(
                        0,
                        "Updating Day",
                        5,
                        3.0,
                        false,
                        null,
                        0,
                    ),
                ),
            )

            assertEquals("Updating Day", populatedSessions!!.findSession(0)!!.day)
            assertEquals(5, populatedSessions!!.findSession(0)!!.duration)
            assertEquals(3.0, populatedSessions!!.findSession(0)!!.sessionRating)
            assertEquals(false, populatedSessions!!.findSession(0)!!.isGameFinished)
            assertEquals(null, populatedSessions!!.findSession(0)!!.notes)
            assertEquals(0, populatedSessions!!.findSession(0)!!.gameId)
        }
    }

    @Nested
    inner class DeleteSessions {
        @Test
        fun `deleting a Session that doesn't exist returns null`() {
            assertNull(emptySessions!!.deleteSession(0))
            assertNull(populatedSessions!!.deleteSession(-1))
            assertNull(populatedSessions!!.deleteSession(5))
        }

        @Test
        fun `deleting a Session that exists deletes successfully and returns deleted object`() {
            assertEquals(5, populatedSessions!!.numberOfSessions())
            assertEquals(rdr2Session1, populatedSessions!!.deleteSession(4))
            assertEquals(4, populatedSessions!!.numberOfSessions())
            assertEquals(marioSession1, populatedSessions!!.deleteSession(0))
            assertEquals(3, populatedSessions!!.numberOfSessions())
        }
    }

    @Nested
    inner class ListingMethods {
        @Nested
        inner class ListAllSessions {
            @Test
            fun `ListAllSessions returns No Sessions Stored Message when Array is Empty`() {
                assertEquals(0, emptySessions!!.numberOfSessions())
                assertTrue(emptySessions!!.listAllSessions().lowercase().contains("no play sessions stored"))
            }

            @Test
            fun `ListAllSessions returns Sessions when Array has Sessions Stored`() {
                assertEquals(5, populatedSessions!!.numberOfSessions())
                val sessionsString = populatedSessions!!.listAllSessions().lowercase()

                assertTrue(sessionsString.contains("reached world 2-1"))
                assertTrue(sessionsString.contains("caught pikachu"))
                assertTrue(sessionsString.contains("built starter house"))
                assertTrue(sessionsString.contains("explored a cave and found diamonds"))
                assertTrue(sessionsString.contains("completed the main story"))
            }
        }

        @Nested
        inner class ListForGame {
            @Test
            fun `ListAllSessionForGame returns No Sessions Stored With This Game ID Message when Game exists with no sessions`() {
                // Empty Sessions Array With Valid Game ID
                assertEquals(0, emptySessions!!.numberOfSessions())
                assertEquals(superMario, gameAPI!!.findGame(0))
                assertTrue(emptySessions!!.listAllSessionsForGame(0).lowercase().contains("no play sessions stored for game id"))

                // Marvel Rivals has 0 sessions
                assertEquals(5, populatedSessions!!.numberOfSessions())
                assertTrue(populatedSessions!!.listAllSessionsForGame(3).lowercase().contains("no play sessions stored for game id"))
            }

            @Test
            fun `listAllSessionsForGame returns sessions for a specific game when sessions exist`() {
                // Checking Mario Sessions (Should be 1)
                val sessionsForMarioString = populatedSessions!!.listAllSessionsForGame(0).lowercase()
                assertTrue(sessionsForMarioString.contains("reached world 2-1"))
                assertFalse(sessionsForMarioString.contains("built starter house"))
                assertFalse(sessionsForMarioString.contains("explored a cave and found diamonds"))

                // Checking Sessions for Minecraft (Should be 2)
                val sessionsForMinecraftString = populatedSessions!!.listAllSessionsForGame(2).lowercase()
                assertFalse(sessionsForMinecraftString.contains("reached world 2-1"))
                assertTrue(sessionsForMinecraftString.contains("built starter house"))
                assertTrue(sessionsForMinecraftString.contains("explored a cave and found diamonds"))
            }
        }
    }

    @Nested
    inner class PersistenceTests {
        @Nested
        inner class XMLTests {
            @Test
            fun `saving and loading an empty collection in XML doesn't crash app`() {
                // Saving an empty playSessions.XML file.
                val storingSessions = PlaySessionAPI(gameAPI!!,XMLSerializer(File("playSessions.xml")))
                storingSessions.store()

                //Loading the empty playSessions.xml file into a new object
                val loadedSessions = PlaySessionAPI(gameAPI!!,XMLSerializer(File("playSessions.xml")))
                loadedSessions.load()

                //Comparing the source of the PlaySessions (storingSessions) with the XML loaded PlaySessions (loadedSessions)
                assertEquals(0, storingSessions.numberOfSessions())
                assertEquals(0, loadedSessions.numberOfSessions())
                assertEquals(storingSessions.numberOfSessions(), loadedSessions.numberOfSessions())
            }

            @Test
            fun `saving and loading an loaded collection in XML doesn't loose data`() {
                // Storing 3 notes to the playSessions.XML file.
                val storingSessions = PlaySessionAPI(gameAPI!!, XMLSerializer(File("playSessions.xml")))
                storingSessions.addSessionToGame(0,marioSession1!!)
                storingSessions.addSessionToGame(1, pokemonSession1!!)
                storingSessions.addSessionToGame(2, minecraftSession1!!)
                storingSessions.store()

                //Loading playSessions.xml into a different collection
                val loadedSessions = PlaySessionAPI( gameAPI!!, XMLSerializer(File("playSessions.xml")))
                loadedSessions.load()

                //Comparing the source of the playSessions (storingSessions) with the XML loaded playSessions (loadedSessions)
                assertEquals(3, storingSessions.numberOfSessions())
                assertEquals(3, loadedSessions.numberOfSessions())
                assertEquals(storingSessions.numberOfSessions(), loadedSessions.numberOfSessions())
                assertEquals(storingSessions.findSession(0), loadedSessions.findSession(0))
                assertEquals(storingSessions.findSession(1), loadedSessions.findSession(1))
                assertEquals(storingSessions.findSession(2), loadedSessions.findSession(2))
            }
        }

        @Nested
        inner class JSONTests {
            @Test
            fun `saving and loading an empty collection in JSON doesn't crash app`() {
                // Saving an empty playSessions.json file.
                val storingSessions = PlaySessionAPI(gameAPI!!, JSONSerializer(File("playSessions.json")))
                storingSessions.store()

                //Loading the empty playSessions.json file into a new object
                val loadedSessions = PlaySessionAPI(gameAPI!!,JSONSerializer(File("playSessions.json")))
                loadedSessions.load()

                //Comparing the source of the PlaySessions (storingSessions) with the JSON loaded PlaySessions (loadedSessions)
                assertEquals(0, storingSessions.numberOfSessions())
                assertEquals(0, loadedSessions.numberOfSessions())
                assertEquals(storingSessions.numberOfSessions(), loadedSessions.numberOfSessions())
            }

            @Test
            fun `saving and loading an loaded collection in XML doesn't loose data`() {
                // Storing 3 notes to the playSessions.json file.
                val storingSessions = PlaySessionAPI(gameAPI!!, JSONSerializer(File("playSessions.json")))
                storingSessions.addSessionToGame(0,marioSession1!!)
                storingSessions.addSessionToGame(1, pokemonSession1!!)
                storingSessions.addSessionToGame(2, minecraftSession1!!)
                storingSessions.store()

                //Loading playSessions.json into a different collection
                val loadedSessions = PlaySessionAPI( gameAPI!!, JSONSerializer(File("playSessions.json")))
                loadedSessions.load()

                //Comparing the source of the playSessions (storingSessions) with the JSON loaded playSessions (loadedSessions)
                assertEquals(3, storingSessions.numberOfSessions())
                assertEquals(3, loadedSessions.numberOfSessions())
                assertEquals(storingSessions.numberOfSessions(), loadedSessions.numberOfSessions())
                assertEquals(storingSessions.findSession(0), loadedSessions.findSession(0))
                assertEquals(storingSessions.findSession(1), loadedSessions.findSession(1))
                assertEquals(storingSessions.findSession(2), loadedSessions.findSession(2))
            }
        }
    }
}
