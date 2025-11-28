package utils

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
