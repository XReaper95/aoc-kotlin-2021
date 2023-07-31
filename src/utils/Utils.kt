package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file and a parent path.
 */
private fun readInput(name: String, parentPath: String) = File(parentPath, "$name.txt").readLines()

/**
 * Converts string to utils.md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Get numbers in comma separated format (1,2,3 etc.) as integer array.
 */
fun parseSingleNumbers(input: List<String>, delimiter: String): IntArray {
    return input.first().split(delimiter).map { it.toInt() }.toIntArray()
}

/**
 * Get integer as string, with leading zero if is a single digit
 */
private fun formatDay(day: Int) = if (day > 9) day.toString() else "0$day"

/**
 * Get input of the day
 */
fun loadInputForDay(day: Int): List<String> {
  return readInput("Day${formatDay(day)}", "src/main/resources")
}

/**
 * Get test input of the day
 */
fun loadTestInputForDay(day: Int): List<String> {
    return readInput("Day${formatDay(day)}_test", "src/test/resources")
}
