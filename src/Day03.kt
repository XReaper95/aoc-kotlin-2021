fun onesCompliment(binaryStr: String): String {
    return binaryStr.map{ when (it) {
        '1'  -> '0'
        '0'  -> '1'
        else -> '?'
    }}.joinToString("")
}

fun mostCommonDigitAtIndex(input: List<String>, index: Int): Char {
    val sumAtIndex = input.sumOf { it[index].toString().toInt() }
    val inputSize = input.size
    val halfInputSize = inputSize / 2

    return when {
        inputSize - sumAtIndex <= halfInputSize -> '1'
        else -> '0'
    }
}

fun filterByCriteriaRecursive(input: List<String>, index: Int, least: Boolean ): String {
    when {
        input.size == 1 -> return input[0]
        input.isEmpty() -> return "0"
        else            -> {}
    }

    var criteria = mostCommonDigitAtIndex(input, index)

    if (least) {
        criteria = if (criteria == '1') '0' else '1'
    }

    val remainingInput = input.filter { it[index] == criteria }

    return filterByCriteriaRecursive(remainingInput, index + 1, least)
}

fun filterMostCommonByCriteria(input: List<String>): String {
    return filterByCriteriaRecursive(input, 0, false)
}

fun filterLeastCommonByCriteria(input: List<String>): String {
    return filterByCriteriaRecursive(input, 0, true)
}

fun main() {
    fun part1(input: List<String>): Int {
        val individualInputSize = input.first().length

        val binaryStrGammaRate = (0 until individualInputSize)
            .map { idx -> mostCommonDigitAtIndex(input, idx) }
            .joinToString("")

        val gammaRate = binaryStrGammaRate.toInt(2)
        val epsilonRate = onesCompliment(binaryStrGammaRate).toInt(2)

        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val oxygenValue = filterMostCommonByCriteria(input)
        val co2Value = filterLeastCommonByCriteria(input)

        val oxygenGeneratorRaring = oxygenValue.toInt(2)
        val co2ScrubberRating = co2Value.toInt(2)

        return oxygenGeneratorRaring * co2ScrubberRating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))

}
