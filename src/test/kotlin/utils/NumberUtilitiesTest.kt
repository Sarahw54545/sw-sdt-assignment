package utils
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NumberUtilitiesTest {

    @Nested
    inner class ValidDoubleRange {
        @Test
        fun validDoubleRangeWorksWithPositiveTestData() {
            assertTrue(validDoubleRange(1.0, 1.0, 1.0)) //equal
            assertTrue(validDoubleRange(1.5, 1.0, 2.0)) // within the range
            assertTrue(validDoubleRange(0.0, 0.0, 10.0)) // lowest in range
            assertTrue(validDoubleRange(10.0, 0.0, 10.0)) // highest in range
            assertTrue(validDoubleRange(-1.0, -2.0, -1.0)) // negative values
            assertTrue(validDoubleRange(5.7, 0.0, 10.0)) // within the whole number
        }
        @Test
        fun validDoubleRangeWorksWithNegativeTestData() {
            assertFalse(validDoubleRange(1.0, 2.0, 3.0)) // below range
            assertFalse(validDoubleRange(11.0, 0.0, 10.0)) // above range
            assertFalse(validDoubleRange(1.0, 1.0, 0.0)) // max lower than min
            assertFalse(validDoubleRange(-3.0, -2.0, -1.0)) // below neg range
            assertFalse(validDoubleRange(1.0, 1.1, 1.5)) // within the whole number
        }
    }

    @Nested
    inner class ValidPositiveInt {
        @Test
        fun validPositiveIntWorksWithPositiveTestData() {
            assertTrue(validPositiveInt(0))
            assertTrue(validPositiveInt(5))
        }
        @Test
        fun validPositiveIntWorksWithNegativeTestData() {
            assertFalse(validPositiveInt(-1))
            assertFalse(validPositiveInt(-100))
        }
    }

    @Nested
    inner class ValidPositiveDouble {
        @Test
        fun validPositiveDoubleWorksWithPositiveTestData() {
            assertTrue(validPositiveDouble(0.0))
            assertTrue(validPositiveDouble(3.14))
        }
        @Test
        fun validPositiveDoubleWorksWithNegativeTestData() {
            assertFalse(validPositiveDouble(-0.01))
            assertFalse(validPositiveDouble(-10.0))
        }
    }
}