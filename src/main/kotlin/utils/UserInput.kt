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

// Float

fun readFloatNotNull() = readlnOrNull()?.toFloatOrNull() ?: -1

fun readNextFloat(prompt: String?): Float {
    do {
        try {
            print(prompt)
            return readln().toFloat()
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

fun readNextChar(prompt: String?): Char {
    do {
        try {
            print(prompt)
            return readln().first()
        } catch (e: Exception) {
            System.err.println("\tEnter a Character Please")
        }
    } while (true)
}
