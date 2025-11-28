package utils

/**
 * Reads an integer from the user and ensures it is positive (>= 0).
 *
 * @param prompt the message to display to the user
 * @return a validated positive integer
 */
fun readValidPositiveInt(prompt: String): Int {
    var input = readNextInt(prompt)

    do {
        if (validPositiveInt(input)) {
            return input
        } else {
            print("Invalid Value: $input. This Value Must be Not be a Negative Number. Please Try Again: ")
            input = readNextInt(prompt)
        }
    } while (true)
}

/**
 * Reads a double from the user and ensures it is positive (>= 0.0).
 *
 * @param prompt the message to display to the user
 * @return a validated positive double
 */
fun readValidPositiveDouble(prompt: String): Double {
    var input = readNextDouble(prompt)

    do {
        if (validPositiveDouble(input)) {
            return input
        } else {
            print("Invalid Value: $input. This Value Must be Not be a Negative Number. Please Try Again: ")
            input = readNextDouble(prompt)
        }
    } while (true)
}

/**
 * Reads a double from the user and ensures it is within the valid rating range 0.1 to 10.0.
 *
 * @param prompt the message to display to the user
 * @return a validated rating double
 */
fun readValidRating(prompt: String): Double {
    var input = readNextDouble(prompt)
    do {
        if (validDoubleRange(input, 0.1, 10.0)) {
            return input
        } else {
            print("Invalid Value: $input. Please Enter a Value Between 0.1 - 10: ")
            input = readNextDouble(prompt)
        }
    } while (true)
}

/**
 * Reads a string from the user and ensures it matches a valid genre.
 *
 * @param prompt the message to display to the user
 * @return a validated genre string
 */
fun readValidGenre(prompt: String?): String {
    var input = readNextLine(prompt)
    do {
        if (isValidGenre(input)) {
            return input
        } else {
            print(
                "Invalid Genre $input. Please input one of the following (${genres.joinToString()}): ",
            )
            input = readNextLine(prompt)
        }
    } while (true)
}

/**
 * Reads a string from the user and ensures it matches a valid day of the week.
 *
 * @param prompt the message to display to the user
 * @return a validated day string
 */
fun readValidDay(prompt: String?): String {
    var input = readNextLine(prompt)
    do {
        if (isValidDay(input)) {
            return input
        } else {
            print(
                "Invalid Day $input. Please input one of the following: (${daysOfWeek.joinToString()}): ",
            )
            input = readNextLine(prompt)
        }
    } while (true)
}
