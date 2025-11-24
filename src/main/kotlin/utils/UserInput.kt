package utils

// Int

fun readIntNotNull() = readlnOrNull()?.toIntOrNull() ?: -1

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

fun readDoubleNotNull() = readlnOrNull()?.toDoubleOrNull() ?: -1

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

fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}

// Boolean

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
