package utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidateEnumTest {
    @Nested
    inner class GenreValidation {
        @Test
        fun genresReturnsFullGenresSet() {
            assertEquals(11, genres.size)
            assertTrue(genres.contains("Action"))
            assertTrue(genres.contains("Adventure"))
            assertTrue(genres.contains("RPG"))
            assertTrue(genres.contains("Platformer"))
            assertTrue(genres.contains("Strategy"))
            assertTrue(genres.contains("Simulation"))
            assertTrue(genres.contains("Horror"))
            assertTrue(genres.contains("Puzzle"))
            assertTrue(genres.contains("Sports"))
            assertTrue(genres.contains("Shooter"))
            assertTrue(genres.contains("Other"))
            assertFalse(genres.contains(""))
        }

        @Test
        fun validGenreReturnTrue() {
            assertTrue(isValidGenre("Action"))
            assertTrue(isValidGenre("action")) // checking case sensitivity
            assertTrue(isValidGenre("RPG"))
            assertTrue(isValidGenre("pUzZle"))
        }

        @Test
        fun validGenreReturnFalse() {
            assertFalse(isValidGenre("Invalid"))
            assertFalse(isValidGenre("Aaction"))
            assertFalse(isValidGenre("Roleplay"))
            assertFalse(isValidGenre(""))
            assertFalse(isValidGenre(null))
        }
    }

    @Nested
    inner class DayValidation {
        @Test
        fun daysReturnsFullDaysSet() {
            assertEquals(7, daysOfWeek.size)
            assertTrue(daysOfWeek.contains("Monday"))
            assertTrue(daysOfWeek.contains("Tuesday"))
            assertTrue(daysOfWeek.contains("Wednesday"))
            assertTrue(daysOfWeek.contains("Thursday"))
            assertTrue(daysOfWeek.contains("Friday"))
            assertTrue(daysOfWeek.contains("Saturday"))
            assertTrue(daysOfWeek.contains("Sunday"))
            assertFalse(daysOfWeek.contains(""))
        }

        @Test
        fun validDayReturnTrue() {
            assertTrue(isValidDay("Monday"))
            assertTrue(isValidDay("monday")) // checking case sensitivity
            assertTrue(isValidDay("TUESDAY"))
            assertTrue(isValidDay("wEdNEsDAY"))
        }

        @Test
        fun validGenreReturnFalse() {
            assertFalse(isValidDay("Invalid"))
            assertFalse(isValidDay("MMonday"))
            assertFalse(isValidDay(""))
            assertFalse(isValidDay(null))
        }
    }
}
