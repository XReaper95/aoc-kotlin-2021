const val FISH_INITIAL_INTERNAL_COUNTER = 8

fun parseFishesState(input: List<String>): List<Int> {
    return input.first().split(",").map { it.toInt() }
}

fun simulateFishesGrowthRecursive(initialFishesState: List<Int>, days: Long): Long {
    if (days <= 0) {
        return initialFishesState.size.toLong()
    }

    var newFishes = 0

    val nextFishesState = initialFishesState.map {
        var result = it - 1

        if (result < 0) {
            result = 6
            newFishes += 1
        }

        result
    }.toMutableList()

    repeat(newFishes) {
        nextFishesState.add(FISH_INITIAL_INTERNAL_COUNTER)
    }

    return simulateFishesGrowthRecursive(nextFishesState, days - 1)
}


fun main() {
    fun part1(input: List<String>, days: Long): Long {
        val initialFishesState = parseFishesState(input)
        return simulateFishesGrowthRecursive(initialFishesState, days)
    }

    fun part2(input: List<String>, days: Long): Long {
        val initialFishesState = parseFishesState(input)
        return simulateFishesGrowthRecursive(initialFishesState, days)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput, 18) == 26L)
    check(part1(testInput, 80) == 5934L)
//    check(part2(testInput, 256) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input, 80))
//    println(part2(input, 256))

}
