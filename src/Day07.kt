import kotlin.math.abs
import kotlin.math.roundToInt

fun median(list: IntArray) = list.sorted().let {
    if (it.size % 2 == 0)
        (it[it.size / 2] + it[(it.size - 1) / 2]) / 2
    else
        it[it.size / 2]
}

fun fuelConsumptionAlignedByMedian(positions: IntArray): Int {
    val positionsMedian = median(positions)
    return positions.sumOf { abs(it - positionsMedian) }
}

fun fuelConsumptionAlignedByAverage(positions: IntArray, offset: Int): Int {
    val positionsAverage = positions.average()
    return positions.sumOf {
        (1..abs(it - positionsAverage.roundToInt() + offset)).sum()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val positions = parseSingleNumbers(input, ",")
        return fuelConsumptionAlignedByMedian(positions)
    }

    fun part2(input: List<String>): Int {
        val positions = parseSingleNumbers(input, ",")
        val resultCandidates = intArrayOf(
            fuelConsumptionAlignedByAverage(positions, -2),
            fuelConsumptionAlignedByAverage(positions, -1),
            fuelConsumptionAlignedByAverage(positions, 0),
            fuelConsumptionAlignedByAverage(positions, 1),
            fuelConsumptionAlignedByAverage(positions, 2)
        )

        return resultCandidates.minOrNull()!!
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
