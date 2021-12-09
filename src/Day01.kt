fun main() {
    fun calculation(input: List<Int>): Int {
        var previousMeasurement: Int? = null
        var count = 0

        input.forEach { currentMeasurement ->
            previousMeasurement?.let { prev ->
                if (currentMeasurement > prev){
                    count += 1
                }
            }

            previousMeasurement = currentMeasurement
        }

        return count
    }

    fun part1(input: List<String>): Int {
        return calculation(input.map { s -> s.toInt() })
    }

    fun part2(input: List<String>): Int {
        val chunkedInput = input.windowed(3) {
            it.sumOf { s -> s.toInt() }
        }

        return calculation(chunkedInput)
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
