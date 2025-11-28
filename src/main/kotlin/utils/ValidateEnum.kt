package utils

/**
 * Set of valid game genres supported by the application.
 */
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

/**
 * Set of valid days of the week used for play sessions.
 */
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

/**
 * Checks if the provided genre string is a valid genre.
 *
 * Uses a case-insensitive comparison against the [genres] set.
 *
 * @param genreToCheck the genre string to validate
 * @return true if the genre is valid, false otherwise
 */
fun isValidGenre(genreToCheck: String?): Boolean = genres.any { it.equals(genreToCheck, ignoreCase = true) }

/**
 * Checks if the provided day string is a valid day of the week.
 *
 * Uses a case-insensitive comparison against the [daysOfWeek] set.
 *
 * @param dayToCheck the day string to validate
 * @return true if the day is valid, false otherwise
 */
fun isValidDay(dayToCheck: String?): Boolean = daysOfWeek.any { it.equals(dayToCheck, ignoreCase = true) }
