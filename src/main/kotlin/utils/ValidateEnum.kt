package utils

val genres =
    setOf(
        "Action",
        "Adventure",
        "RPG",
        "Platformer",
        "Strategy",
        "Simulation",
        "Horror",
        "Puzzle",
        "Sports",
        "Shooter",
        "Other",
    )

val daysOfWeek =
    setOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday",
    )

// Checks if any of the values in the genre array match the genre provided (.any will stop searching if it finds a match and will return true (.forEach will keep iterating))
fun isValidGenre(genreToCheck: String?): Boolean = genres.any { it.equals(genreToCheck, ignoreCase = true) }

fun isValidDay(dayToCheck: String?): Boolean = daysOfWeek.any { it.equals(dayToCheck, ignoreCase = true) }
