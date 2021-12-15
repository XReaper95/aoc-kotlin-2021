const val CURRENT_FISH_INTERNAL_CLOCK = 6
const val NEW_FISH_INTERNAL_CLOCK = 8

fun parseFishesState(input: List<String>): IntArray {
    return input.first().split(",").map { it.toInt() }.toIntArray()
}

fun simulateFishesGrowthRate(initialFishesState: IntArray, lastGenerationDay: Int): Long {
    val newFishesByDate = LongArray(lastGenerationDay + 1) { 0 }

    initialFishesState.forEach { currentFishAge -> ++newFishesByDate[currentFishAge + 1] }

    (0 .. lastGenerationDay).forEach { day ->
        val nextDayToPopulateInitial = day + CURRENT_FISH_INTERNAL_CLOCK + 1
        val nextDayToPopulateNew = day + NEW_FISH_INTERNAL_CLOCK + 1
        val currentFishesAtDay = newFishesByDate[day]

        if (nextDayToPopulateNew < newFishesByDate.size){
            newFishesByDate[nextDayToPopulateNew] += currentFishesAtDay
        }

        if (nextDayToPopulateInitial < newFishesByDate.size){
            newFishesByDate[nextDayToPopulateInitial] += currentFishesAtDay
        }
    }

    return newFishesByDate.sum() + initialFishesState.size
}


fun main() {
    fun part1and2(input: List<String>, days: Int): Long {
        val initialFishesState = parseFishesState(input)
        return simulateFishesGrowthRate(initialFishesState, days)
    }

    val testInput = readInput("Day06_test")
    check(part1and2(testInput, 18) == 26L)
    check(part1and2(testInput, 80) == 5934L)
    check(part1and2(testInput, 256) == 26_984_457_539L)

    val input = readInput("Day06")
    println(part1and2(input, 80))
    println(part1and2(input, 256))
}
