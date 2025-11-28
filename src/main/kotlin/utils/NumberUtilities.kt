package utils

fun validDoubleRange(
    numberToCheck: Double,
    min: Double,
    max: Double,
): Boolean {
    return numberToCheck in min..max
}

fun validPositiveInt(value: Int): Boolean {
    return value >= 0
}

fun validPositiveDouble(value: Double): Boolean {
    return value >= 0.0
}
