package utils

// Int

/**
 * Reads a line of input from the user and attempts to convert it to an [Int].
 * Returns -1 if the input is null or cannot be converted.
 *
 * @return the integer value or -1 on failure
 */
fun readIntNotNull() = readlnOrNull()?.toIntOrNull() ?: -1

/**
 * Prompts the user to enter an integer until a valid number is provided.
 *
 * @param prompt the message displayed to the user
 * @return the integer entered by the user
 */
fun readNextInt(prompt: String?): Int {
    do {
        try {
            print(prompt)
            return readln().toInt()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a Number Please")
        }
    } while (true)
}

// Double

/**
 * Reads a line of input from the user and attempts to convert it to a [Double].
 * Returns -1 if the input is null or cannot be converted.
 *
 * @return the double value or -1 on failure
 */
fun readDoubleNotNull() = readlnOrNull()?.toDoubleOrNull() ?: -1

/**
 * Prompts the user to enter a double until a valid number is provided.
 *
 * @param prompt the message displayed to the user
 * @return the double entered by the user
 */
fun readNextDouble(prompt: String?): Double {
    do {
        try {
            print(prompt)
            return readln().toDouble()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a Number Please")
        }
    } while (true)
}

// String

/**
 * Prompts the user to enter a string and reads the next line of input.
 *
 * @param prompt the message displayed to the user
 * @return the string entered by the user
 */
fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}

// Boolean

/**
 * Prompts the user to enter a boolean value (yes/no, true/false) until valid input is provided.
 * Accepts "y", "yes", "true", "t" as true and "n", "no", "false", "f" as false (case-insensitive).
 *
 * @param prompt the message displayed to the user
 * @return the boolean value entered by the user
 */
fun readNextBoolean(prompt: String?): Boolean {
    do {
        try {
            print(prompt)

            return when (readln().trim().lowercase()) {
                "y", "yes", "true", "t" -> true
                "n", "no", "false", "f" -> false
                else -> {
                    System.err.println("\tEnter Yes/No (Y/N) or True/False Please")
                    continue
                    // 'continue' Needed as return type has to be a boolean (not just the error prompt string) reference: https://kotlinlang.org/docs/returns.html#break-and-continue-labels
                }
            }
        } catch (e: Exception) {
            System.err.println("\tEnter Yes/No (Y/N) or True/False Please") // For other unexpected exceptions
        }
    } while (true)
}
