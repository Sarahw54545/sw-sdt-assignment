package utils

/**
 * Checks if a given [numberToCheck] is within the inclusive range
 * between [min] and [max].
 *
 * @param numberToCheck the number to validate
 * @param min the minimum allowed value (inclusive)
 * @param max the maximum allowed value (inclusive)
 * @return `true` if the number is within range, `false` otherwise
 */
fun validDoubleRange(
    numberToCheck: Double,
    min: Double,
    max: Double,
): Boolean {
    return numberToCheck in min..max
}

/**
 * Checks if the given integer [value] is positive or zero.
 *
 * @param value the integer to validate
 * @return `true` if [value] >= 0, `false` otherwise
 */
fun validPositiveInt(value: Int): Boolean {
    return value >= 0
}

/**
 * Checks if the given double [value] is positive or zero.
 *
 * @param value the double to validate
 * @return `true` if [value] >= 0.0, `false` otherwise
 */
fun validPositiveDouble(value: Double): Boolean {
    return value >= 0.0
}
